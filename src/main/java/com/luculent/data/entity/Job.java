package com.luculent.data.entity;

import org.quartz.Trigger;

/**
 * Created by luculent on 2017/4/19.
 */
public class Job {
    private String jobName;
    private String group;//对于job，trigger相互绑定的，采用相同的group
    private Class<? extends org.quartz.Job> jobClass;
    private Trigger trigger;

    public Job() {
        super();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Class<? extends org.quartz.Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends org.quartz.Job> jobClass) {
        this.jobClass = jobClass;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }
}
