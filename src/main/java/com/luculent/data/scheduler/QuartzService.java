package com.luculent.data.scheduler;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.luculent.data.constant.DataConstant;
import com.luculent.data.model.SchedulerJob;
import com.luculent.data.service.SchedulerJobService;

@Component
public class QuartzService {

    private final static Logger logger = LogManager.getLogger("run_long");

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private SchedulerJobService schedulerJobService;
    
    
    @PostConstruct  
    public void  quartzInit(){
	List<SchedulerJob> list = schedulerJobService.queryEnabled();
	try{
	    schedulerFactoryBean.getScheduler().clear();
	}catch (SchedulerException e) {
	    logger.error("初始化任务失败，"+e);
	}
	
	for(SchedulerJob job:list){
	    try{
		this.addJob(job);
	    } catch (ClassNotFoundException e) {
		logger.error("初始化任务失败，CLASS未找到，请检查数据库中classname是否正确，"+e);
	    } catch (SchedulerException e) {
    	    	logger.error("初始化任务失败，调度异常，"+e);
    	    
	    }
	}
    } 

    /**
     * @throws SchedulerException
     *             修改JOB的CORN表达式
     */
    public void modifyJobCron(SchedulerJob job) throws SchedulerException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	TriggerKey triggerKey = getTriggerKeyByName(job.getTriggerName());
	JobKey jobkey = getJobKeyByName(job.getJobName());
	if (!scheduler.checkExists(triggerKey)) {
	    return;
	}
	CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	String oldCron = trigger.getCronExpression();
	if (!StringUtils.equalsIgnoreCase(oldCron, job.getCronExpression())) {
	    // this.deleteJob(job);
	    // this.addJob(job);
	    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
	    trigger.getTriggerBuilder().forJob(jobkey).withSchedule(scheduleBuilder);
	    scheduler.resumeTrigger(triggerKey);
	}

    }

    /**
     * 删除一个JOB的触发器，如果JOB已经没有触发器，清理掉job
     * 
     */
    public void deleteJobTrigger(SchedulerJob job) throws SchedulerException {
	Scheduler sched = schedulerFactoryBean.getScheduler();
	TriggerKey triggerKey = getTriggerKeyByName(job.getTriggerName());
	JobKey jobkey = getJobKeyByName(job.getJobName());
	// 暂停触发器
	sched.pauseTrigger(triggerKey);
	// 移除job
	sched.unscheduleJob(triggerKey);
	List<CronTrigger> list = (List<CronTrigger>) sched.getTriggersOfJob(jobkey);
	if (list.isEmpty()) {
	    sched.deleteJob(jobkey);
	}
    }

    /**
     * 添加任务
     */
    public void addJob(SchedulerJob job) throws SchedulerException, ClassNotFoundException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	TriggerKey triggerKey = getTriggerKeyByName(job.getTriggerName());
	JobKey jobkey = getJobKeyByName(job.getJobName());
	if (scheduler.checkExists(jobkey) && scheduler.checkExists(triggerKey)) {
	    return;
	}
	JobDetail jobDetail = this.getJobDetail(jobkey, job);

	CronTrigger trigger = this.getCornTrigger(triggerKey, job);
	scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 恢复触发器
     */
    public void resumeTrigger(SchedulerJob job) throws SchedulerException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	TriggerKey triggerKey = getTriggerKeyByName(job.getTriggerName());
	scheduler.resumeTrigger(triggerKey);
    }

    /**
     * 恢复任务
     */
    public void resumeJob(SchedulerJob job) throws SchedulerException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	JobKey jobkey = getJobKeyByName(job.getJobName());
	scheduler.resumeJob(jobkey);
    }

    /**
     * 暂停触发器
     */
    public void pauseTrigger(SchedulerJob job) throws SchedulerException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	TriggerKey triggerKey = getTriggerKeyByName(job.getTriggerName());
	scheduler.pauseTrigger(triggerKey);
    }

    /**
     * 暂停某任务，不管太在哪个触发器里，主要看JOBName
     */
    public void pauseJob(SchedulerJob job) throws SchedulerException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	JobKey jobkey = getJobKeyByName(job.getJobName());
	scheduler.pauseJob(jobkey);
    }

    /**
     * 开启所有调度
     */
    public void startJobs() throws SchedulerException {
	Scheduler sched = schedulerFactoryBean.getScheduler();
	if (!sched.isStarted()) {
	    sched.start();
	}
    }

    /**
     * 停止所有调度
     */
    public void shutdownJobs() throws SchedulerException {
	Scheduler sched = schedulerFactoryBean.getScheduler();
	if (!sched.isShutdown()) {
	    sched.shutdown();
	}
    }

    private TriggerKey getTriggerKeyByName(String triggerName) {
	return TriggerKey.triggerKey(triggerName, DataConstant.TRIGGER_GROUP_NAME);
    }

    private JobKey getJobKeyByName(String jobName) {
	return JobKey.jobKey(jobName, DataConstant.JOB_GROUP_NAME);
    }

    private CronTrigger getCornTrigger(TriggerKey triggerKey, SchedulerJob job) throws SchedulerException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	CronTrigger trigger;
	if (scheduler.checkExists(triggerKey)) {
	    trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	} else {
	    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
	    trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
	}
	return trigger;

    }

    private JobDetail getJobDetail(JobKey jobkey, SchedulerJob job) throws ClassNotFoundException, SchedulerException {
	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	JobDetail jobDetail;
	if (scheduler.checkExists(jobkey)) {
	    jobDetail = scheduler.getJobDetail(jobkey);
	} else {
	    jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getSchedulerClass()))
		    .withIdentity(jobkey).build();
	    // jobDetail.getJobDataMap().put("schedulerJob", job);
	}
	return jobDetail;
    }

}
