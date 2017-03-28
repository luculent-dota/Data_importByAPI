package com.luculent.data.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

@TableName("sys_config")
public class SysConfig extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1713258805727300079L;
	
	private String id;
	
	private String url;
	
	private String driverClassName;
	
	private String username;
	
	private String password;
	
	private String scrq;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScrq() {
		return scrq;
	}

	public void setScrq(String scrq) {
		this.scrq = scrq;
	}
	
	
	
	
	
}
