package com.luculent.data.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

/**
 * 
* @ClassName: SysConfig 
* @Description: 全局配置 导出数据库
* @author zhangy
* @date 2017年3月28日 下午3:09:08
 */
@TableName("sys_config")
public class SysConfig extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1713258805727300079L;
	
	/** 主键. */
	private String id;
	/** 路径. */
	private String url;
	/** classname. */
	private String driverClassName;
	/** 用户名. */
	private String username;
	/** 密码. */
	private String password;
	/** 生成时间. */
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
	 * @param url
	 *            the new 路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the classname.
	 *
	 * @return the classname
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * Sets the classname.
	 *
	 * @param driverClassName
	 *            the new classname
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Gets the 用户名.
	 *
	 * @return the 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the 用户名.
	 *
	 * @param username
	 *            the new 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the 密码.
	 *
	 * @return the 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the 密码.
	 *
	 * @param password
	 *            the new 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the 生成时间.
	 *
	 * @return the 生成时间
	 */
	public String getScrq() {
		return scrq;
	}

	/**
	 * Sets the 生成时间.
	 *
	 * @param scrq
	 *            the new 生成时间
	 */
	public void setScrq(String scrq) {
		this.scrq = scrq;
	}
	
	
	
	
	
}
