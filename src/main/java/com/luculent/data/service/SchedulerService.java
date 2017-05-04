package com.luculent.data.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
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
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.constant.ApiType;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.constant.ParamType;
import com.luculent.data.exception.APIParamsNotFoundException;
import com.luculent.data.mapper.RunRecordMapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
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
    private ExportDataService exportDataService;
    @Autowired
    private RunRecordMapper runRecordMapper;
    @Autowired
    private TaskExecutor threadPoolTaskExecutor;
    
    private ConcurrentHashMap<String,AtomicLong> expectTotalMap =new ConcurrentHashMap<String,AtomicLong>();
    
    private ConcurrentHashMap<String,AtomicLong> actualTotalMap =new ConcurrentHashMap<String,AtomicLong>();
    
    private ConcurrentHashMap<String,StringBuffer> failParamsMap =new ConcurrentHashMap<String,StringBuffer>();
    
    
    
    @Async
    public void paramsHandler(String json){
	RunParams runParams = this.paramAnalysis(json);
	String recordId = runParams.getRecordId();
	List<ConcurrentHashMap<String,String>> params = runParams.getParams();
	CountDownLatch countDownLatch =null;
	expectTotalMap.put(recordId, new AtomicLong(0l));
	actualTotalMap.put(recordId, new AtomicLong(0l));
	failParamsMap.put(recordId, new StringBuffer());
	if(params.size() !=0){
	    countDownLatch = new CountDownLatch(params.size());
	    for(ConcurrentHashMap<String,String> param:params){
		threadPoolTaskExecutor.execute(new SchedulerTaskRunnable(countDownLatch,runParams,param));
	    }
	}else if(runParams.getNeedPage()){
	    countDownLatch = new CountDownLatch(1);
	    threadPoolTaskExecutor.execute(new SchedulerTaskRunnable(countDownLatch,runParams));
	}else{
	    return ;
	}
	try {
	    countDownLatch.await();
	    //线程完成
	    RunRecord record = runRecordMapper.selectById(runParams.getRecordId());
	    //截至时间
	    record.setExpectTotal(expectTotalMap.get(recordId).get());
	    record.setActualTotal(actualTotalMap.get(recordId).get());
	    record.setFailLog(failParamsMap.get(recordId).toString());
	    
	    runRecordMapper.updateById(record);
	    
	    expectTotalMap.remove(recordId);
	    actualTotalMap.remove(record);
	    failParamsMap.remove(recordId);
	    
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    
    @Transactional(value="datain")
    public RunParams paramAnalysis(String json) {
	JSONObject jsonObj = JSONObject.parseObject(json);
	String apiId = jsonObj.getString("APIID");
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
			    paramMap.put(param.getName(), exportDataService.exportColumnByKeys(param.getDataSource()));
			}

		    }
		} else {
		    pageName = param.getName();
		}

	    }
	    List<ConcurrentHashMap<String, String>> paramList = ConventionUtils.toParamsMap(paramMap);
	    jsonObj.remove("APIID");
	    String paramStr = jsonObj.toJSONString();

	    RunRecord record = new RunRecord(apiId, paramStr);
	    runRecordMapper.insert(record);
	    record = runRecordMapper.selectById(record.getId());
	    logger.info("数据处理开始,纪录Id为【" + record.getId() + "】,开始时间为 :" + record.getScrq() + ",执行参数为:" + paramStr);

	    RunParams.Builder builder = new RunParams.Builder(sysApi.getProjectId(), sysApi.getUrl(),record.getId());
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
    
    //并发线程自动登陆
    private synchronized boolean autoLogin(String projectId,ConcurrentHashMap<String,String> params,String url){
	BackBean back =OkHttpUtils.getBeanContent(url, params);
	if(back!=null &&!DataConstant.RES_CODE_NOTLOGIN.equals(back.getRtnCode())){
	    return true;
	}
	return sysApiService.autoLoginByProjectId(projectId);
    }
    
    //数据执行线程
    private class SchedulerTaskRunnable implements Runnable{
	    
	    
	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		//参数和分页
		ConcurrentHashMap<String,String> params = new ConcurrentHashMap<String,String>();
		//params.putAll(null);  空指针异常  map的key值不允许为空
		if(this.param !=null){
		    params.putAll(param);
		}
		if(this.needPage){
		    int num = DataConstant.PAGENUM.get();
		    for(;;){
			params.put(this.pageName, String.valueOf(num));
			BackBean back =OkHttpUtils.getBeanContent(this.url, params);
			boolean res = handlerBackBean(back,params,projectId,recordId);
			if(!res){
			    break;
			}
			num++;
			
		    }
		}else{
		    BackBean back =OkHttpUtils.getBeanContent(this.url, params);
		    handlerBackBean(back,params,projectId,recordId);
		}
		countDown.countDown();
	    }
	    
	    /**
	     * 
	     *@Description: 处理请求返回值 如果请求为空或自动登陆失败满足WAIT_RUNTASK_NUM值则停止任务
	     *@Author: zhangy
	     *@Since: 2017年5月3日下午3:46:50
	     *@param back
	     *@param params
	     *@param projectId
	     *@param recordId
	     *@return
	     */
	    private boolean handlerBackBean(BackBean back,ConcurrentHashMap<String,String> params,String projectId,String recordId){
		
		boolean res = false;
		int waitNum = DataConstant.WAITNUM.get();
		if(DataConstant.WAIT_RUNTASK_NUM == waitNum){
			logger.error("Id为:【"+recordId+"】的任务，请求数据失败，请求的参数为:"+JSON.toJSONString(params)+",请求已超过最大等待次数，任务终止");
			return false;
		}
		if(back == null){
		    failParamsMap.get(recordId).append(JSON.toJSONString(params));
		    logger.error("Id为:【"+recordId+"】的任务，请求数据失败，请求的参数为:"+JSON.toJSONString(params));
		    waitNum++;
		    DataConstant.WAITNUM.set(waitNum);
		    return true;
		    
		}
		switch (back.getRtnCode()) {
		//成功
		case DataConstant.RES_CODE_SUCCESS:
		    //
		    if(StringUtils.isNotEmpty(back.getSql())){
			
			 // int ress =exportDataService.exportDataBySql(StringUtils.split(back.getSql(), ";"));
			int ress =StringUtils.split(back.getSql(), ";").length;
			  logger.debug("Id为:【"+recordId+"】的任务，请求数据成功！，请求的参数为:"+JSON.toJSONString(params)+"执行成功的条数为"+ress+"条");
			  //期望总数
			  if(expectStatus ==0){
			      if(StringUtils.isNotEmpty(back.getTotal())){
				  expectTotalMap.get(recordId).addAndGet(Long.valueOf(back.getTotal()));
			      }else{
				  expectTotalMap.get(recordId).addAndGet(ress);
			      }
    			      expectStatus=1;
			  }
			  //实际总数
			  actualTotalMap.get(recordId).addAndGet(ress);
		    }
		    
		    res = true;
		    break;
		//未登陆
		case DataConstant.RES_CODE_NOTLOGIN:
		    //
		    logger.warn("Id为:【"+recordId+"】的任务，请求数据时尚未登陆，正在自动登陆中.....");
		    
		    boolean auto =autoLogin(projectId,params,this.url);
		    if(auto){
			BackBean autoBack =OkHttpUtils.getBeanContent(this.url, params);
			return handlerBackBean(autoBack,params,projectId,recordId);
		    }else{
			failParamsMap.get(recordId).append(JSON.toJSONString(params));
			waitNum++;
			DataConstant.WAITNUM.set(waitNum);
			logger.error("Id为:【"+recordId+"】的任务，自动登陆失败，请求的参数为:"+JSON.toJSONString(params));
		    }
		    break;
		//其他
		default:
		    failParamsMap.get(recordId).append(JSON.toJSONString(params));
		    logger.error("Id为:【"+recordId+"】的任务，请求数据失败，请求的参数为:"+JSON.toJSONString(params)+",失败原因为:"+back.getRtnMsg());
		    break;
		}
		return res;
	    }
	    
	    
	    /** projectId. */
	    private String projectId;
	    
	    /** url. */
	    private String url;
	    
	    /** 参数列表. */
	    private ConcurrentHashMap<String,String> param;
	    
	    /** 是否需要页码. */
	    private Boolean needPage;
	    
	    /** 页码名称. */
	    private String pageName;
	    
	    /** recordId. */
	    private String recordId;
	    
	    private CountDownLatch countDown;
	    
	    private int expectStatus = 0;
	    
	    public SchedulerTaskRunnable() {
		// TODO Auto-generated constructor stub
	    }
	    
	    public SchedulerTaskRunnable(CountDownLatch countDown,RunParams runParams) {
		// TODO Auto-generated constructor stub
		this.countDown =countDown;
		this.projectId = runParams.getProjectId();
		this.url = runParams.getUrl();
		this.needPage = runParams.getNeedPage();
		this.pageName = runParams.getPageName();
		this.recordId = runParams.getRecordId();
		this.param = null;
	    }
	    
	    public SchedulerTaskRunnable(CountDownLatch countDown,RunParams runParams,ConcurrentHashMap<String,String> param) {
		// TODO Auto-generated constructor stub
		this.countDown =countDown;
		this.projectId = runParams.getProjectId();
		this.url = runParams.getUrl();
		this.needPage = runParams.getNeedPage();
		this.pageName = runParams.getPageName();
		this.recordId = runParams.getRecordId();
		this.param = param;
		
	    }

	}
    
    
}
