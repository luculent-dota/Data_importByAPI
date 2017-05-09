package com.luculent.data.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.constant.ApiType;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.constant.FailParamType;
import com.luculent.data.constant.ParamType;
import com.luculent.data.exception.APIParamsNotFoundException;
import com.luculent.data.mapper.RunRecordMapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.model.BackBean;
import com.luculent.data.model.RunParams;
import com.luculent.data.model.RunRecord;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysParam;
import com.luculent.data.utils.util.ConventionUtils;
import com.luculent.data.utils.util.OkHttpUtils;

@Service
public class SchedulerService {

    private final static Logger logger = LogManager.getLogger("run_long");
    @Autowired
    private SysApiMapper sysApiMapper;
    @Autowired
    private SysParamMapper sysParamMapper;
    @Autowired
    private SysApiService sysApiService;
    @Autowired
    private BasisKeyService basisKeyService;
    @Autowired
    private DataBaseKeyService dataBaseKeyService;
    @Autowired
    private ExportDataService exportDataService;
    @Autowired
    private RunRecordMapper runRecordMapper;
    @Autowired
    private TaskExecutor threadPoolTaskExecutor;

    private ConcurrentHashMap<String, AtomicLong> expectTotalMap = new ConcurrentHashMap<String, AtomicLong>();

    private ConcurrentHashMap<String, AtomicLong> actualTotalMap = new ConcurrentHashMap<String, AtomicLong>();

    private ConcurrentHashMap<String, StringBuffer> failParamsMap = new ConcurrentHashMap<String, StringBuffer>();

    /** 立即执行任务 */
    @Async
    public void rightNowExecuteByJSON(String json) {
	paramsHandler(paramAnalysisByJSON(json));
    }

    /** 任务重试 */
    @Async
    public void retryExecuteByRecordId(RunRecord runRecord, String deleteSql) {
	paramsHandler(paramAnalysisByRunRecord(runRecord, deleteSql));
    }

    /**
     * 
     * @Description: 参数执行
     * @Author: zhangyang
     * @Since: 2017年5月6日下午4:04:22
     * @param runParams
     */
    public void paramsHandler(RunParams runParams) {
	String recordId = runParams.getRecordId();
	LocalDateTime startTime = runParams.getStartTime();
	List<ConcurrentHashMap<String, String>> params = runParams.getParams();
	CountDownLatch countDownLatch = null;
	expectTotalMap.put(recordId, new AtomicLong(0l));
	actualTotalMap.put(recordId, new AtomicLong(0l));
	failParamsMap.put(recordId, new StringBuffer());
	if (params.size() != 0) {
	    countDownLatch = new CountDownLatch(params.size());
	    for (ConcurrentHashMap<String, String> param : params) {
		threadPoolTaskExecutor.execute(new SchedulerTaskRunnable(countDownLatch, runParams, param));
	    }
	} else if (runParams.getNeedPage()) {
	    countDownLatch = new CountDownLatch(1);
	    threadPoolTaskExecutor.execute(new SchedulerTaskRunnable(countDownLatch, runParams));
	} else {
	    return;
	}
	try {
	    countDownLatch.await();
	    // 线程完成
	    RunRecord record = runRecordMapper.selectById(runParams.getRecordId());
	    // 截至时间
	    if (record != null) {
		LocalDateTime endTime = LocalDateTime.now();
		String endTimeStr = endTime.format(DataConstant.formatter);
		record.setCarryTime(Duration.between(startTime, endTime).toMillis());
		record.setEndTime(endTimeStr);
		record.setExpectTotal(expectTotalMap.get(recordId).get());
		record.setActualTotal(actualTotalMap.get(recordId).get());
		if (failParamsMap.containsKey(recordId) && failParamsMap.get(recordId).toString().length() != 0) {
		    String failParamsStr = failParamsMap.get(recordId).toString();
		    failParamsStr = "[" + failParamsStr.substring(0, failParamsStr.length() - 1) + "]";
		    record.setFailLog(failParamsStr);
		}

		runRecordMapper.updateById(record);
	    }
	    expectTotalMap.remove(recordId);
	    actualTotalMap.remove(record);
	    failParamsMap.remove(recordId);

	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    // 参数处理
    @Transactional
    public RunParams paramAnalysisByJSON(String json) {
	JSONObject jsonObj = JSONObject.parseObject(json);
	String apiId = jsonObj.getString(DataConstant.APIID);
	SysApi sysApi = sysApiMapper.selectById(apiId);
	List<SysParam> params = sysParamMapper.selectList(new EntityWrapper<SysParam>().eq("api_id", apiId));
	if (params != null && params.size() != 0) {
	    Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
	    String pageName = null;
	    for (SysParam param : params) {
		if (!ParamType.PAGE.name().equals(param.getParamType())) {
		    String paramValue = jsonObj.getString(param.getName());
		    if (StringUtils.isNotEmpty(paramValue)) {
			paramMap.put(param.getName(), Arrays.asList(StringUtils.split(paramValue, ",")));
		    } else if (ApiType.ISREQUIRED.getVal() == param.getRequired()) {
			// 基础值
			if (ParamType.BASIS.name().equals(param.getParamType())) {
			    paramMap.put(param.getName(), basisKeyService.getCacheBykey(param.getDataSource()));
			    // 数据库值
			} else if (ParamType.DATABASE.name().equals(param.getParamType())) {
			    paramMap.put(param.getName(), dataBaseKeyService.exportColumnByKeys(param.getDataSource()));
			}

		    }
		} else {
		    pageName = param.getName();
		}

	    }
	    List<ConcurrentHashMap<String, String>> paramList = ConventionUtils.toParamsMap(paramMap);
	    jsonObj.remove(DataConstant.APIID);
	    //执行预删除语句
	    String deleteSql = jsonObj.getString(DataConstant.SENTENCE);
	    if (StringUtils.isNotEmpty(deleteSql)) {
		int rows = 0;
		 rows = exportDataService.deleteDataBySql(deleteSql);
		    logger.info("预删除结束，执行语句为：【" + deleteSql + "】,删除数据" + rows + "条");

	    }
	    jsonObj.remove(DataConstant.SENTENCE);

	    String paramStr = jsonObj.toJSONString();
	    LocalDateTime startTime = LocalDateTime.now();
	    RunRecord record = new RunRecord(apiId, paramStr, deleteSql, startTime.format(DataConstant.formatter));
	    runRecordMapper.insert(record);
	    record = runRecordMapper.selectById(record.getId());
	    logger.info("数据处理开始,纪录Id为【" + record.getId() + "】,开始时间为 :" + record.getStartTime() + ",执行参数为:" + paramStr);

	    RunParams.Builder builder = new RunParams.Builder(sysApi.getProjectId(), sysApi.getUrl(), record.getId(),
		    startTime);
	    if (paramList != null && paramList.size() != 0) {
		builder.params(paramList);
	    }
	    if (StringUtils.isNotEmpty(pageName)) {
		builder.needPage(true).pageName(pageName);
	    }
	    return builder.build();

	}
	String errMsg = "数据处理失败,id为【" + apiId + "】,名称为【" + sysApi.getName() + "】的接口不存在参数";
	logger.error(errMsg);
	throw new APIParamsNotFoundException(errMsg);
    }

    // 重试参数处理
    @Transactional
    public RunParams paramAnalysisByRunRecord(RunRecord runRecord, String deleteSql) {
	List<ConcurrentHashMap<String, String>> paramList = new ArrayList<ConcurrentHashMap<String, String>>();
	JSONArray arr = (JSONArray) JSONArray.parse(runRecord.getFailLog());
	for (Object obj : arr) {
	    JSONObject jsonObj = (JSONObject) obj;
	    if (FailParamType.NETWORK.name().equals(jsonObj.getString(DataConstant.REA010SONKEY))
		    || FailParamType.LOGIN.name().equals(jsonObj.getString(DataConstant.REA010SONKEY))) {
		jsonObj.remove(DataConstant.REA010SONKEY);
		jsonObj.remove(DataConstant.REA010SON);
		Map<String, String> map = JSONObject.toJavaObject(jsonObj, Map.class);
		ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>(map);
		paramList.add(concurrentMap);
	    }
	}
	SysApi sysApi = sysApiMapper.selectById(runRecord.getApiId());
	List<SysParam> params = sysParamMapper.selectList(new EntityWrapper<SysParam>()
		.eq("api_id", runRecord.getApiId()).eq("param_type", ParamType.PAGE.name()));

	if (StringUtils.isNotEmpty(deleteSql)) {
	    int rows = 0;
	    try {
		rows = exportDataService.deleteDataBySql(deleteSql);
		logger.info("预删除结束，执行语句为：【" + deleteSql + "】,删除数据" + rows + "条");
	    } catch (RuntimeException e) {
		logger.info("预删除失败，执行语句为：【" + deleteSql + "】, 出现异常" + e);
	    }
	}

	String paramStr = JSON.toJSONString(paramList);
	LocalDateTime startTime = LocalDateTime.now();
	RunRecord retryRecord = new RunRecord(runRecord.getApiId(), paramStr, deleteSql,
		startTime.format(DataConstant.formatter));
	runRecordMapper.insert(retryRecord);
	retryRecord = runRecordMapper.selectById(retryRecord.getId());
	logger.info("任务Id为【" + runRecord.getApiId() + "】重试开始,重试任务Id为【" + retryRecord.getId() + "】,开始时间为 :"
		+ retryRecord.getStartTime() + ",执行参数为:" + paramStr);

	RunParams.Builder builder = new RunParams.Builder(sysApi.getProjectId(), sysApi.getUrl(), retryRecord.getId(),
		startTime);
	if (paramList != null && paramList.size() != 0) {
	    builder.params(paramList);
	}
	if (params != null && params.size() != 0) {
	    builder.needPage(true).pageName(params.get(0).getName());
	}
	return builder.build();

    }

    // 并发线程自动登陆
    private synchronized boolean autoLogin(String projectId, ConcurrentHashMap<String, String> params, String url) {
	BackBean back = OkHttpUtils.getBeanContent(url, params);
	if (back != null && !DataConstant.RES_CODE_NOTLOGIN.equals(back.getRtnCode())) {
	    return true;
	}
	return sysApiService.autoLoginByProjectId(projectId);
    }

    // 数据执行线程
    private class SchedulerTaskRunnable implements Runnable {

	@Override
	public void run() {
	    // TODO Auto-generated method stub
	    // 参数和分页
	    ConcurrentHashMap<String, String> params = new ConcurrentHashMap<String, String>();
	    // params.putAll(null); 空指针异常 map的key值不允许为空
	    if (this.param != null) {
		params.putAll(param);
	    }
	    if (this.needPage) {
		int num = DataConstant.PAGENUM.get();
		for (;;) {
		    params.put(this.pageName, String.valueOf(num));
		    BackBean back = OkHttpUtils.getBeanContent(this.url, params);
		    boolean res = handlerBackBean(back, params);
		    if (!res) {
			break;
		    }
		    num++;

		}
	    } else {
		BackBean back = OkHttpUtils.getBeanContent(this.url, params);
		handlerBackBean(back, params);
	    }

	    expectTotalMap.get(this.recordId).addAndGet(expectNum);
	    actualTotalMap.get(this.recordId).addAndGet(actualNum);
	    countDown.countDown();
	}

	/**
	 * 
	 * @Description: 处理请求返回值 如果请求为空或自动登陆失败或报参数不存在则停止任务
	 * @Author: zhangy
	 * @Since: 2017年5月3日下午3:46:50
	 * @param back
	 * @param params
	 * @param projectId
	 * @param recordId
	 * @return
	 */
	private boolean handlerBackBean(BackBean back, ConcurrentHashMap<String, String> params) {

	    boolean res = false;
	    // 返回空
	    if (back == null) {
		params.putAll(DataConstant.FAILPARAMS_NETWORK);
		if (this.needPage) {
		    params.remove(this.pageName);
		}
		failParamsMap.get(recordId).append(JSON.toJSONString(params)).append(DataConstant.FAILPARAMS_LINK);
		logger.error("Id为:【" + recordId + "】的任务，网络连接不稳定，请求的参数为:" + JSON.toJSONString(params));
		return false;

	    }
	    switch (back.getRtnCode()) {
	    // 成功
	    case DataConstant.RES_CODE_SUCCESS:
		//
		if (StringUtils.isNotEmpty(back.getSql())) {

		    int ress = exportDataService.exportDataBySql(StringUtils.split(back.getSql(), ";"));
		    logger.debug("Id为:【" + this.recordId + "】的任务，请求数据成功！，请求的参数为:" + JSON.toJSONString(params)
			    + "执行成功的条数为" + ress + "条");
		    // 期望总数
		    if (expectStatus == 0) {
			if (StringUtils.isNotEmpty(back.getTotal())) {
			    expectNum += Long.valueOf(back.getTotal());
			} else {
			    expectNum += ress;
			}
			expectStatus = 1;
		    }
		    // 实际总数
		    actualNum += ress;
		}

		res = true;
		break;
	    // 未登陆
	    case DataConstant.RES_CODE_NOTLOGIN:
		//
		logger.warn("Id为:【" + recordId + "】的任务，请求数据时尚未登陆，正在自动登陆中.....");

		boolean auto = autoLogin(this.projectId, params, this.url);
		if (auto) {
		    BackBean autoBack = OkHttpUtils.getBeanContent(this.url, params);
		    return handlerBackBean(autoBack, params);
		} else {
		    params.putAll(DataConstant.FAILPARAMS_LOGIN);
		    if (this.needPage) {
			params.remove(this.pageName);
		    }
		    failParamsMap.get(this.recordId).append(JSON.toJSONString(params))
			    .append(DataConstant.FAILPARAMS_LINK);
		    logger.error("Id为:【" + this.recordId + "】的任务，自动登陆失败，请求的参数为:" + JSON.toJSONString(params));
		    return false;
		}

		// 其他
	    default:
		params.putAll(DataConstant.FAILPARAMS_NOEXISTS);
		if (this.needPage) {
		    params.remove(this.pageName);
		}
		failParamsMap.get(this.recordId).append(JSON.toJSONString(params)).append(DataConstant.FAILPARAMS_LINK);
		logger.error(
			"Id为:【" + this.recordId + "】的任务，" + back.getRtnMsg() + "，请求的参数为:" + JSON.toJSONString(params));
		break;
	    }
	    return res;
	}

	/** projectId. */
	private String projectId;

	/** url. */
	private String url;

	/** 参数列表. */
	private ConcurrentHashMap<String, String> param;

	/** 是否需要页码. */
	private Boolean needPage;

	/** 页码名称. */
	private String pageName;

	/** recordId. */
	private String recordId;

	private CountDownLatch countDown;

	// 期望总数赋值状态
	private long expectStatus = 0l;
	// 期望总数
	private long expectNum = 0l;
	// 实际总数
	private long actualNum = 0l;

	public SchedulerTaskRunnable() {
	    // TODO Auto-generated constructor stub
	}

	public SchedulerTaskRunnable(CountDownLatch countDown, RunParams runParams) {
	    // TODO Auto-generated constructor stub
	    this.countDown = countDown;
	    this.projectId = runParams.getProjectId();
	    this.url = runParams.getUrl();
	    this.needPage = runParams.getNeedPage();
	    this.pageName = runParams.getPageName();
	    this.recordId = runParams.getRecordId();
	    this.param = null;
	}

	public SchedulerTaskRunnable(CountDownLatch countDown, RunParams runParams,
		ConcurrentHashMap<String, String> param) {
	    // TODO Auto-generated constructor stub
	    this.countDown = countDown;
	    this.projectId = runParams.getProjectId();
	    this.url = runParams.getUrl();
	    this.needPage = runParams.getNeedPage();
	    this.pageName = runParams.getPageName();
	    this.recordId = runParams.getRecordId();
	    this.param = param;

	}

    }

}
