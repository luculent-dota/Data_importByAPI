package com.luculent.data.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.base.BaseController;
import com.luculent.data.constant.ApiType;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.constant.FailParamType;
import com.luculent.data.mapper.RunRecordMapper;
import com.luculent.data.mapper.SchedulerJobMapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.RunRecord;
import com.luculent.data.model.SchedulerJob;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysParam;
import com.luculent.data.model.SysProject;
import com.luculent.data.service.DataHandleService;
import com.luculent.data.service.SchedulerJobService;
import com.luculent.data.service.SysApiService;
import com.luculent.data.utils.util.OkHttpUtils;

@Controller
@RequestMapping("/api")
public class SysAPIController extends BaseController {

    @Autowired
    private SysApiService sysApiService;
    @Autowired
    private SysApiMapper sysApiMapper;
    @Autowired
    private SysParamMapper sysParamMapper;
    @Autowired
    private SysProjectMapper sysProjectMapper;
    @Autowired
    private DataHandleService dataHandleService;
    @Autowired
    private RunRecordMapper runRecordMapper;
    @Autowired
    private SchedulerJobMapper schedulerJobMapper;
    @Autowired
    private SchedulerJobService schedulerJobService;
    
    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView, String apiId) {
	modelAndView.setViewName("api/index");
	SysApi sysApi = sysApiMapper.selectById(apiId);
	modelAndView.addObject("sysApiBean", sysApi);
	modelAndView.addObject("apiId", apiId);
	SysProject sysProject = sysProjectMapper.selectById(sysApi.getProjectId());
	modelAndView.addObject("projectBean", sysProject);
	List<SysParam> params = sysParamMapper
		.selectList(new EntityWrapper<SysParam>().eq("api_id", apiId).orderBy("scrq"));
	modelAndView.addObject("params", params);
	StringBuilder paramBuf = new StringBuilder();
	if (params != null && params.size() != 0) {

	    for (SysParam param : params) {
		if (ApiType.ISREQUIRED.getVal() == param.getRequired()) {
		    paramBuf.append(DataConstant.URL_AND).append(param.getName()).append(DataConstant.URL_EQUAL)
			    .append(param.getDefaultValue());
		}
	    }
	}
	modelAndView.addObject("paramStr", paramBuf.toString());
	//数据处理
	int nowRunCount = runRecordMapper.selectCount(new EntityWrapper<RunRecord>().eq("api_id", apiId).isNull("end_time"));
	modelAndView.addObject("nowRunCount", nowRunCount);
	//是否存在任务
	List<SchedulerJob> schedulerJobs = schedulerJobMapper.selectList(new EntityWrapper<SchedulerJob>().eq("api_id", apiId));
	modelAndView.addObject("existJob", schedulerJobs.size());
	if(schedulerJobs !=null && schedulerJobs.size() !=0){
	    modelAndView.addObject("schedulerJob", schedulerJobs.get(0));
	}
	return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/menu-json")
    public Object getMenuJSON(String projectId) {
	return sysApiService.getMenuJSON(projectId);
    }

    @ResponseBody
    @RequestMapping("/auto-login")
    public Object autoLogin(String projectId) {
	return sysApiService.autoLoginByProjectId(projectId) ? renderSuccess("登陆成功") : renderError("登陆失败");
    }

    @ResponseBody
    @RequestMapping("/test-run")
    public String testRun(String url, Integer apiType) {
	if (StringUtils.isEmpty(url)) {
	    return null;
	}
	String res = "";
	if (apiType == ApiType.CODE.getVal()) {
	    res = OkHttpUtils.getImageDownLoad(url);
	} else {
	    res = OkHttpUtils.getContent(url);
	}
	return res;
    }

    @ResponseBody
    @RequestMapping("/now-run")
    public Object nowRun(@RequestBody String json) {
//	JSONObject jsonObj = JSONObject.parseObject(json);
//	String apiId = jsonObj.getString("APIID");
//	SysApi sysApi = sysApiMapper.selectById(apiId);
//	String schedulerClass = ConventionUtils.firstSpellToLow(sysApi.getSchedulerClass());
//	if(!ServiceLocator.containsBean(schedulerClass)){
//	    return renderError("启动失败！任务类未加载,请重启服务器");
//	}
//	IBaseScheduler scheduler = (IBaseScheduler) ServiceLocator.getBean(schedulerClass);
//	System.out.println(sysApi.getSchedulerClass());
	dataHandleService.nowRun(json);
	return renderSuccess("启动成功");
    }
    
    @RequestMapping("/cron-config")
    public ModelAndView cronConfig(ModelAndView modelAndView) {
	modelAndView.setViewName("api/cron");
	return modelAndView;
    }
    
    @ResponseBody
    @RequestMapping("/save-job")
    public Object saveJob(@RequestBody String json) {
	return schedulerJobService.createJob(json);
    }
    
    
    @ResponseBody
    @RequestMapping("/run-history")
    public Object runHistory(String apiId) {
	return runRecordMapper.selectList(new EntityWrapper<RunRecord>().eq("api_id", apiId).orderBy("start_time", false));
    }
    
    
    @ResponseBody
    @RequestMapping("/params-check")
    public Object paramsCheck(String apiId,String recordId) {
	int nowCount = runRecordMapper.selectCount(new EntityWrapper<RunRecord>().eq("api_id", apiId).isNull("end_time"));
	if(nowCount != 0){
	    return renderError("该接口下有任务正在执行，稍后在重试吧！");
	}
	RunRecord runRecord = runRecordMapper.selectById(recordId);
	if(StringUtils.isEmpty(runRecord.getFailLog())){
	    return renderError("该任务下不含失败参数，重试失败！");
	}else if(!StringUtils.contains(runRecord.getFailLog(), FailParamType.NETWORK.name()) && !StringUtils.contains(runRecord.getFailLog(), FailParamType.LOGIN.name())){
	    return renderError("该任务下没有可重试的失败参数，重试失败！");
	}
	
	return renderSuccess();
    }
    
    
    @ResponseBody
    @RequestMapping("/params-retry")
    public Object paramsRetry(String recordId,String deleteSql) {
	RunRecord runRecord = runRecordMapper.selectById(recordId);
	dataHandleService.retryRun(runRecord,deleteSql);
	return renderSuccess("启动成功");
    }
    
    
    
    
}
