package com.luculent.data.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

/**
 * 
 *@Description:运行任务类
 *@Author:zhangyang
 *@Since:2017年5月10日下午1:58:33
 */
@TableName("scheduler_job")
public class SchedulerJob extends BaseModel{

    /** 
     *@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */ 
    private static final long serialVersionUID = -4508691522955679437L;

    /** 主键. */
    private String id;
    
    /** apiId. */
    private String apiId;
    
    /** 任务名称. */
    private String jobName;
    
    /** 触发器名称. */
    private String triggerName;
    
    /** cron表达式. */
    private String cronExpression;
    
    /** 任务状态 0未开启 1开启. */
    private Integer jobStatus;
    
    /** 运行参数. */
    private String runParams;
    
    /** 生成日期. */
    private String scrq;
    
    /** 任务类名 .*/
    @TableField(exist=false)
    private String schedulerClass;

    /**
     * Gets the 主键.
     *
     * @return the 主键
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the 主键.
     *
     * @param id
     *            the new 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the apiId.
     *
     * @return the apiId
     */
    public String getApiId() {
        return apiId;
    }

    /**
     * Sets the apiId.
     *
     * @param apiId
     *            the new apiId
     */
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    /**
     * Gets the 任务名称.
     *
     * @return the 任务名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Sets the 任务名称.
     *
     * @param jobName
     *            the new 任务名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    

    /**
     * Gets the 触发器名称.
     *
     * @return the 触发器名称
     */
    public String getTriggerName() {
        return triggerName;
    }

    /**
     * Sets the 触发器名称.
     *
     * @param triggerName
     *            the new 触发器名称
     */
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    /**
     * Gets the cron表达式.
     *
     * @return the cron表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Sets the cron表达式.
     *
     * @param cronExpression
     *            the new cron表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * Gets the 任务状态 0未开启 1开启.
     *
     * @return the 任务状态 0未开启 1开启
     */
    public Integer getJobStatus() {
        return jobStatus;
    }

    /**
     * Sets the 任务状态 0未开启 1开启.
     *
     * @param jobStatus
     *            the new 任务状态 0未开启 1开启
     */
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * Gets the 运行参数.
     *
     * @return the 运行参数
     */
    public String getRunParams() {
        return runParams;
    }

    /**
     * Sets the 运行参数.
     *
     * @param runParams
     *            the new 运行参数
     */
    public void setRunParams(String runParams) {
        this.runParams = runParams;
    }

    /**
     * Gets the 生成日期.
     *
     * @return the 生成日期
     */
    public String getScrq() {
        return scrq;
    }

    /**
     * Sets the 生成日期.
     *
     * @param scrq
     *            the new 生成日期
     */
    public void setScrq(String scrq) {
        this.scrq = scrq;
    }

    /**
     * Gets the 任务类名 .
     *
     * @return the 任务类名
     */
    public String getSchedulerClass() {
        return schedulerClass;
    }

    /**
     * Sets the 任务类名 .
     *
     * @param schedulerClass
     *            the new 任务类名
     */
    public void setSchedulerClass(String schedulerClass) {
        this.schedulerClass = schedulerClass;
    }
    
    
    
    
    
}
