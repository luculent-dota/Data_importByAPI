package com.luculent.data.model;

import java.util.List;
import java.util.Map;

import com.luculent.data.model.BackBean.Builder;

/**
 * 
 *@Description:运行时的参数
 *@Author:zhangy
 *@Since:2017年4月26日下午4:08:54
 */
public class RunParams {

    /** apiId. */
    private String apiId;
    
    /** projectId. */
    private String projectId;
    
    /** url. */
    private String url;
    
    /** 参数列表. */
    private List<Map<String,String>> params;
    
    /** 是否需要页码. */
    private Boolean needPage;
    
    /** 页码名称. */
    private String pageName;
    
    /** recordId. */
    private String recordId;
    
    public RunParams() {
	// TODO Auto-generated constructor stub
    }
    
    /**
     * The Class Builder.
     */
    public static class Builder{
	
	/** apiId. */
	private String apiId;
	    
	/** projectId. */
	private String projectId;
	    
	/** url. */
	private String url;
	
	/** recordId. */
	private String recordId;
	    
	/** 参数列表. */
	private List<Map<String,String>> params =null;
	    
	/** 是否需要页码. */
	private Boolean needPage = false;
	    
	/** 页码名称. */
	private String pageName = "YM";
	    
	
	
	public Builder(String projectId,String apiId,String url,String recordId) {
	    // TODO Auto-generated constructor stub
	    this.projectId = projectId;
	    this.apiId = apiId;
	    this.url = url;
	    this.recordId = recordId;
	}
	
	public Builder params(List<Map<String,String>> val){
	    params = val;
	    return this;
	}
	public Builder needPage(Boolean val){
	    needPage = val;
	    return this;
	}
	public Builder pageName(String val){
	    pageName = val;
	    return this;
	}
	
	public RunParams build(){
	    return new RunParams(this);
	}
	
    }
    
    private RunParams(Builder builder){
	this.apiId = builder.apiId;
	this.url = builder.url;
	this.projectId = builder.projectId;
	this.recordId = builder.recordId;
	this.params = builder.params;
	this.needPage = builder.needPage;
	this.pageName = builder.pageName;
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
     * Gets the projectId.
     *
     * @return the projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Sets the projectId.
     *
     * @param projectId
     *            the new projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url
     *            the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the 参数列表.
     *
     * @return the 参数列表
     */
    public List<Map<String, String>> getParams() {
        return params;
    }

    /**
     * Sets the 参数列表.
     *
     * @param params
     *            the new 参数列表
     */
    public void setParams(List<Map<String, String>> params) {
        this.params = params;
    }

    /**
     * Gets the 是否需要页码.
     *
     * @return the 是否需要页码
     */
    public Boolean getNeedPage() {
        return needPage;
    }

    /**
     * Sets the 是否需要页码.
     *
     * @param needPage
     *            the new 是否需要页码
     */
    public void setNeedPage(Boolean needPage) {
        this.needPage = needPage;
    }

    /**
     * Gets the 页码名称.
     *
     * @return the 页码名称
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * Sets the 页码名称.
     *
     * @param pageName
     *            the new 页码名称
     */
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    /**
     * Gets the recordId.
     *
     * @return the recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * Sets the recordId.
     *
     * @param recordId
     *            the new recordId
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    
    
    
}
