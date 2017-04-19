package com.luculent.data.service;

import com.luculent.data.entity.Job;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;

/**
 * Created by luculent on 2017/4/19.
 */
public class QrtzService {
    Logger logger = LoggerFactory.getLogger(QrtzService.class);
    @Autowired
    private static Scheduler scheduler;

    public QrtzService() {
        super();
       // this.start();
    }

    /**
     *
     */
    public void start(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("定时任务启动失败:"+e);
            e.printStackTrace();
        }

    }

    /**
     * 发布job
     * @param model    job
     * @param dataMap 参数
     */
    public void deploy(Job model, JobDataMap dataMap){
        JobDetail jobDetail = newJob(model.getJobClass()).withIdentity(model.getJobName(),model.getGroup()).build();
        jobDetail.getJobDataMap().putAll(dataMap);
        try {
            scheduler.scheduleJob(jobDetail,model.getTrigger());
        } catch (SchedulerException e) {
            logger.error("定时任务发布失败:"+e);
            e.printStackTrace();
        }
    }

    /**
     * 数据库恢复job
     */
    public  void resumeJob() {
        try {
            List<String> triggerGroups = scheduler.getTriggerGroupNames();
            for (int i = 0; i < triggerGroups.size(); i++) {
                List<String> triggers = scheduler.getTriggerGroupNames();
                for (int j = 0; j < triggers.size(); j++) {
                    Trigger tg = scheduler.getTrigger(new TriggerKey(triggers
                            .get(j), triggerGroups.get(i)));
                    if (tg instanceof SimpleTrigger) {
                        // ②-1:恢复运行
                        scheduler.resumeJob(new JobKey(triggers.get(j),
                                triggerGroups.get(i)));
                    }
                }

            }
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除job
     * @param jobName
     * @param group
     */
        public void unDeploy(String jobName,String group){
        if (jobName.equals("") || group.equals("")) {
            return ;
        }
        try {
            scheduler.deleteJob(jobKey(jobName,group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
