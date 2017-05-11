package com.luculent.data.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.base.JsonResult;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.constant.JobStatus;
import com.luculent.data.exception.SchedulerJobAboutException;
import com.luculent.data.mapper.SchedulerJobMapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.model.SchedulerJob;
import com.luculent.data.model.SysApi;
import com.luculent.data.scheduler.QuartzService;
import com.luculent.data.utils.util.DynamicCompileUtils;

@Service
@Transactional
public class SchedulerJobService {

    private final static Logger logger = LogManager.getLogger(SchedulerJobService.class);

    @Autowired
    private SchedulerJobMapper schedulerJobMapper;

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private SysApiMapper sysApiMapper;

    /**
     * 查询为开启状态的任务
     */
    public List<SchedulerJob> queryEnabled() {
	return schedulerJobMapper
		.selectList(new EntityWrapper<SchedulerJob>().eq("job_status", JobStatus.ENABLED.getVal()));
    }

    public SchedulerJob queryJobByClassName(String className) {
	List<SchedulerJob> list = schedulerJobMapper.queryListByClassName(className);
	if (list != null && list.size() != 0) {
	    return list.get(0);
	}
	return null;

    }

    public JsonResult createJob(String json) {
	JSONObject jsonObj = JSONObject.parseObject(json);
	
	String apiId = jsonObj.getString(DataConstant.APIID);
	JsonResult result = new JsonResult();
	int existCount = schedulerJobMapper.selectCount(new EntityWrapper<SchedulerJob>().eq("api_id", apiId));
	if(existCount !=0){
	    result.setSuccess(false);
		result.setStatus("500");
		result.setMsg("此接口已存在定时任务配置，创建定时任务失败。");
		return result;
	}
	SysApi sysApi = sysApiMapper.selectById(apiId);
	SchedulerJob job = new SchedulerJob();
	if (sysApi != null && StringUtils.isNotEmpty(sysApi.getSchedulerClass())&& DynamicCompileUtils.existClass(DataConstant.SCHEDULER_PACKAGE_PATH+sysApi.getSchedulerClass())) {
	    job.setApiId(apiId);
	    job.setCronExpression(jsonObj.getString(DataConstant.CRONEXPRESSION));
	    job.setSchedulerClass(DataConstant.SCHEDULER_PACKAGE_PATH+sysApi.getSchedulerClass());
	    job.setJobName(sysApi.getSchedulerClass() + DataConstant.JOB_SUFFIX);
	    job.setTriggerName(sysApi.getSchedulerClass() + DataConstant.JOB_TRIGGER_SUFFIX);
	    job.setJobStatus(JobStatus.ENABLED.getVal());
	    jsonObj.remove(DataConstant.CRONEXPRESSION);
	    job.setRunParams(jsonObj.toJSONString());
	    schedulerJobMapper.insert(job);
	    try {
		quartzService.addJob(job);
	    } catch (ClassNotFoundException | SchedulerException e) {
		// TODO Auto-generated catch block
		throw new SchedulerJobAboutException();
	    }
	    result.setSuccess(true);
	    result.setStatus("200");
	    result.setMsg("创建成功");
	    result.setObj(job);
	    return result;
	}
	result.setSuccess(false);
	result.setStatus("500");
	result.setMsg("所需类不存在,可能类未创建或加载，重启web服务再试。");
	return result;
    }

    public boolean changeJobStatus(String jobId, int status) {
	if (StringUtils.isEmpty(jobId)) {
	    return false;
	}
	SchedulerJob job = schedulerJobMapper.selectById(jobId);
	if (job.getJobStatus() == status) {
	    return true;
	}
	job.setJobStatus(status);
	try {
	    if (status == 1) {
		quartzService.resumeTrigger(job);
		quartzService.resumeJob(job);
		
	    } else {
		quartzService.pauseTrigger(job);
		quartzService.pauseJob(job);
	    }
	} catch (SchedulerException e) {
	    // TODO Auto-generated catch block
	    throw new SchedulerJobAboutException();
	}
	schedulerJobMapper.updateById(job);
	return true;

    }
    
    public boolean removeJob(String jobId){
	if (StringUtils.isEmpty(jobId)) {
	    return false;
	}
	SchedulerJob job = schedulerJobMapper.selectById(jobId);
	if(job == null){
	    return true;
	}
	try {
	    quartzService.deleteJobTrigger(job);
	} catch (SchedulerException e) {
	    // TODO Auto-generated catch block
	    throw new SchedulerJobAboutException();
	}
	schedulerJobMapper.deleteById(jobId);
	return true;
	
    }
}
