package com.luculent.data.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

/**
 * 
* @ClassName: SysProject 
* @Description: 项目实体类
* @author zhangy
* @date 2017年3月28日 下午3:18:26
 */
@TableName("sys_project")
public class SysProject extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1811108768345248200L;

	/** 主键. */
	private String id;
	/** 项目名称. */
	private String name;
	/** 项目根路径. */
	private String baseUrl;
	/** 项目用户名 （没用）. */
	private String username;
	/** 项目密码 （没用）. */
	private String password;
	/** daas标识 选填 填时为daas项目 */
	private String iwApikey;
	/** 排序 */
	private Integer sort;
	/** 生成日期 */
	private String scrq;

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
	 * Gets the 项目名称.
	 *
	 * @return the 项目名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 项目名称.
	 *
	 * @param name
	 *            the new 项目名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 项目根路径.
	 *
	 * @return the 项目根路径
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the 项目根路径.
	 *
	 * @param baseUrl
	 *            the new 项目根路径
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Gets the 项目用户名 （没用）.
	 *
	 * @return the 项目用户名 （没用）
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the 项目用户名 （没用）.
	 *
	 * @param username
	 *            the new 项目用户名 （没用）
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the 项目密码 （没用）.
	 *
	 * @return the 项目密码 （没用）
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the 项目密码 （没用）.
	 *
	 * @param password
	 *            the new 项目密码 （没用）
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the daas标识 选填 填时为daas项目.
	 *
	 * @return the daas标识 选填 填时为daas项目
	 */
	public String getIwApikey() {
		return iwApikey;
	}

	/**
	 * Sets the daas标识 选填 填时为daas项目.
	 *
	 * @param iwApikey
	 *            the new daas标识 选填 填时为daas项目
	 */
	public void setIwApikey(String iwApikey) {
		this.iwApikey = iwApikey;
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
	 * @param sort
	 *            the new 排序
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
	 * @param scrq
	 *            the new 生成日期
	 */
	public void setScrq(String scrq) {
		this.scrq = scrq;
	}
	
	
}
