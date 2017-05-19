package com.luculent.data.scheduler;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.model.SchedulerJob;
import com.luculent.data.service.DataHandleService;
import com.luculent.data.service.SchedulerJobService;

/**
 * 
 *@Description:住建委-农户列表 定时任务类
 *@Author:zhangy
 */

@DisallowConcurrentExecution
public class ZjwNonghlbJob  implements Job{
    
    private final static Logger logger = LogManager.getLogger("run_long");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
	// TODO Auto-generated method stub
	SchedulerContext schCtx;
	try {
		schCtx = context.getScheduler().getContext();
		ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");
		SchedulerJobService schedulerJobService= (SchedulerJobService)appCtx.getBean("schedulerJobService");  
		SchedulerJob job = schedulerJobService.queryJobByClassName(getCurrentClassName());
		if(job != null && StringUtils.isNotEmpty(job.getRunParams())){
		    logger.error("定时任务启动成功！");
		    DataHandleService dataHandleService=(DataHandleService) appCtx.getBean("dataHandleService");
		    JSONObject jsonObj = JSONObject.parseObject(job.getRunParams());
		    jsonObj.putIfAbsent(DataConstant.APIID, job.getApiId());
		    dataHandleService.nowRun(jsonObj.toJSONString());
		}else{
		    logger.error("定时任务启动失败，找不到当前的任务类。");
		}
	} catch (SchedulerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
    }
    
   
    private static String getCurrentClassName(){
	 return new SecurityManager() {  
	        public String getClassName() {
	            String fullpath = getClassContext()[1].getName();
	            if(StringUtils.isNotEmpty(fullpath)&&fullpath.length()>DataConstant.SCHEDULER_PACK_LENGTH){
	        	return fullpath.substring(DataConstant.SCHEDULER_PACK_LENGTH);
	            }
	            return null;  
	        }  
	    }.getClassName();  
   }
    
    

}