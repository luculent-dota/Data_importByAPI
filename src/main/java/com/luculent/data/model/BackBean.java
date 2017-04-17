package com.luculent.data.model;

/**
 * 
 *@Description:请求返回数据类
 *@Author:zhangy
 *@Since:2017年4月17日下午2:13:22
 */
public class BackBean {
    
    
    /** 返回头编码. */
    private String rtnCode;
    
    /** 返回头消息. */
    private String rtnMsg;
    
    /** 返回体总数. */
    private String total;
    
    /** 返回体页码. */
    private String page;
    
    /** 返回体sql. */
    private String sql;
    
    /** 返回体list. */
    private String list;
    
    
    public BackBean() {
	// TODO Auto-generated constructor stub
    }
    
    /**
     * The Class Builder.
     */
    public static class Builder{
	
	/** The rtn code. */
	private String rtnCode;
	
	/** The rtn msg. */
	private String rtnMsg;
	
	/** The sql. */
	private String sql=null;
	
	/** The total. */
	private String total = null;
	
	/** The page. */
	private String page = null;
	
	/** The list. */
	private String list = null;
	
	public Builder(String rtnCode,String rtnMsg) {
	    // TODO Auto-generated constructor stub
	    this.rtnCode = rtnCode;
	    this.rtnMsg = rtnMsg;
	}
	
	public Builder sql(String val){
	    sql = val;
	    return this;
	}
	public Builder total(String val){
	    total = val;
	    return this;
	}
	public Builder page(String val){
	    page = val;
	    return this;
	}
	
	public Builder list(String val){
	    list = val;
	    return this;
	}
	
	public BackBean build(){
	    return new BackBean(this);
	}
	
    }
    
    private BackBean(Builder builder){
	this.rtnCode = builder.rtnCode;
	this.rtnMsg = builder.rtnMsg;
	this.sql = builder.sql;
	this.total = builder.sql;
	this.list = builder.list;
    }
    /**
     * Gets the 返回头编码.
     *
     * @return the 返回头编码
     */
    public String getRtnCode() {
        return rtnCode;
    }

    /**
     * Sets the 返回头编码.
     *
     * @param rtnCode
     *            the new 返回头编码
     */
    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    /**
     * Gets the 返回头消息.
     *
     * @return the 返回头消息
     */
    public String getRtnMsg() {
        return rtnMsg;
    }

    /**
     * Sets the 返回头消息.
     *
     * @param rtnMsg
     *            the new 返回头消息
     */
    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    /**
     * Gets the 返回体总数.
     *
     * @return the 返回体总数
     */
    public String getTotal() {
        return total;
    }

    /**
     * Sets the 返回体总数.
     *
     * @param total
     *            the new 返回体总数
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * Gets the 返回体页码.
     *
     * @return the 返回体页码
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets the 返回体页码.
     *
     * @param page
     *            the new 返回体页码
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Gets the 返回体sql.
     *
     * @return the 返回体sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * Sets the 返回体sql.
     *
     * @param sql
     *            the new 返回体sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }
    
    
    /**
     * Gets the 返回体list.
     *
     * @return the 返回体list
     */
    public String getList() {
        return list;
    }
    
    /**
     * Sets the 返回体list.
     *
     * @param list
     *            the new 返回体list
     */
    public void setList(String list) {
        this.list = list;
    }
    
    
    
    
}
