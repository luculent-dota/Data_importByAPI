package com.luculent.data.model;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

/**
 * 
* @ClassName: SysApi 
* @Description: 接口类 
* @author zhangy
* @date 2017年3月28日 下午3:00:35
 */
@TableName("sys_api")
public class SysApi extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4913953256757403823L;
	/** 主键. */
	private String id;
	
	/** 接口名称. */
	private String name;
	
	/** 接口别名 (拼接URL用). */
	private String alias;
	
	/** 所属项目. */
	private String projectId;
	
	/** 路径. */
	private String url;
	
	/** 数据处理类名.*/
	private String schedulerClass;
	
	/** 请求类型 1get 2post. */
	private Integer askType;
	
	/** 返回值. */
	private String res;
	
	/** 接口类型 1登陆 2验证码 0其他. */
	private Integer apiType;
	
	/** 排序. */
	private Integer sort;
	
	/** 生成日期. */
	private String scrq;
	
	
	/** 参数列表. */
	@TableField(exist=false)
	private List<SysParam> paramList;

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
	 * @param id the new 主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the 接口名称.
	 *
	 * @return the 接口名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 接口名称.
	 *
	 * @param name the new 接口名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 接口别名 (拼接URL用).
	 *
	 * @return the 接口别名 (拼接URL用)
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the 接口别名 (拼接URL用).
	 *
	 * @param alias the new 接口别名 (拼接URL用)
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the 所属项目.
	 *
	 * @return the 所属项目
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * Sets the 所属项目.
	 *
	 * @param projectId the new 所属项目
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * Gets the 路径.
	 *
	 * @return the 路径
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the 路径.
	 *
	 * @param url the new 路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	/**
	 * Gets the 数据处理类名.
	 *
	 * @return the 数据处理类名
	 */
	public String getSchedulerClass() {
	    return schedulerClass;
	}

	/**
	 * Sets the 数据处理类名.
	 *
	 * @param schedulerClass
	 *            the new 数据处理类名
	 */
	public void setSchedulerClass(String schedulerClass) {
	    this.schedulerClass = schedulerClass;
	}

	/**
	 * Gets the 请求类型 1get 2post.
	 *
	 * @return the 请求类型 1get 2post
	 */
	public Integer getAskType() {
		return askType;
	}

	/**
	 * Sets the 请求类型 1get 2post.
	 *
	 * @param askType the new 请求类型 1get 2post
	 */
	public void setAskType(Integer askType) {
		this.askType = askType;
	}

	/**
	 * Gets the 返回值.
	 *
	 * @return the 返回值
	 */
	public String getRes() {
		return res;
	}

	/**
	 * Sets the 返回值.
	 *
	 * @param res the new 返回值
	 */
	public void setRes(String res) {
		this.res = res;
	}
	
	/**
	 * Gets the 接口类型 1登陆 2验证码 3其他.
	 *
	 * @return the 接口类型 1登陆 2验证码 3其他
	 */
	public Integer getApiType() {
		return apiType;
	}

	/**
	 * Sets the 接口类型 1登陆 2验证码 3其他.
	 *
	 * @param apiType the new 接口类型 1登陆 2验证码 3其他
	 */
	public void setApiType(Integer apiType) {
		this.apiType = apiType;
	}

	/**
	 * Gets the 排序.
	 *
	 * @return the 排序
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * Sets the 排序.
	 *
	 * @param sort the new 排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
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
	 * @param scrq the new 生成日期
	 */
	public void setScrq(String scrq) {
		this.scrq = scrq;
	}

	/**
	 * Gets the 参数列表.
	 *
	 * @return the 参数列表
	 */
	public List<SysParam> getParamList() {
		return paramList;
	}

	/**
	 * Sets the 参数列表.
	 *
	 * @param paramList the new 参数列表
	 */
	public void setParamList(List<SysParam> paramList) {
		this.paramList = paramList;
	}
	
	
}
