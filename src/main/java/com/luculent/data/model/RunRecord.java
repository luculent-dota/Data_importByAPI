package com.luculent.data.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;
import com.luculent.data.constant.DataConstant;

/**
 * 
 *@Description:运行纪录表
 *@Author:zhangy
 *@Since:2017年4月26日下午4:05:45
 */
@TableName("run_record")
public class RunRecord extends BaseModel{

    /** 
     *@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */ 
    private static final long serialVersionUID = -1900494245833401919L;
    
    /** The id. */
    private String id;
    
    /** apiId. */
    private String apiId;
    
    /** 运行时参数. */
    private String runParams;
    
    /** 期望条数. */
    private Long expectTotal;
    
    /** 实际条数. */
    private Long actualTotal;
    
    /** 失败的日志. */
    private String failLog;
    
    /** 开始时间. */
    private String startTime;
    
    /** 结束时间. */
    private String endTime;
    
    /** 总用时. */
    private Long carryTime;
    
    public RunRecord() {
	// TODO Auto-generated constructor stub
    }
    
    
    public RunRecord(String apiId,String runParams,String startTime) {
   	// TODO Auto-generated constructor stub
	this.apiId = apiId;
	this.runParams = runParams;
	this.startTime = startTime;
    }
    

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
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
     * Gets the 运行时参数.
     *
     * @return the 运行时参数
     */
    public String getRunParams() {
        return runParams;
    }

    /**
     * Sets the 运行时参数.
     *
     * @param runParams
     *            the new 运行时参数
     */
    public void setRunParams(String runParams) {
        this.runParams = runParams;
    }

    /**
     * Gets the 期望条数.
     *
     * @return the 期望条数
     */
    public Long getExpectTotal() {
        return expectTotal;
    }

    /**
     * Sets the 期望条数.
     *
     * @param expectTotal
     *            the new 期望条数
     */
    public void setExpectTotal(Long expectTotal) {
        this.expectTotal = expectTotal;
    }

    /**
     * Gets the 实际条数.
     *
     * @return the 实际条数
     */
    public Long getActualTotal() {
        return actualTotal;
    }

    /**
     * Sets the 实际条数.
     *
     * @param actualTotal
     *            the new 实际条数
     */
    public void setActualTotal(Long actualTotal) {
        this.actualTotal = actualTotal;
    }

    /**
     * Gets the 失败的日志.
     *
     * @return the 失败的日志
     */
    public String getFailLog() {
        return failLog;
    }

    /**
     * Sets the 失败的日志.
     *
     * @param failLog
     *            the new 失败的日志
     */
    public void setFailLog(String failLog) {
        this.failLog = failLog;
    }


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Long getCarryTime() {
		return carryTime;
	}


	public void setCarryTime(Long carryTime) {
		this.carryTime = carryTime;
	}

    
    
    

}
