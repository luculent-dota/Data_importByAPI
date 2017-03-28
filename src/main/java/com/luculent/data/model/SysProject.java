package com.luculent.data.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

@TableName("sys_project")
public class SysProject extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1811108768345248200L;

	private String id;
	
	private String name;
	
	private String baseUrl;
	
	private String username;
	
	private String password;
	
	private String iwApikey;
	
	private Integer needLogin;
	
	private Integer sort;
	
	private String scrq;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
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

	public String getIwApikey() {
		return iwApikey;
	}

	public void setIwApikey(String iwApikey) {
		this.iwApikey = iwApikey;
	}


	public Integer getNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(Integer needLogin) {
		this.needLogin = needLogin;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getScrq() {
		return scrq;
	}

	public void setScrq(String scrq) {
		this.scrq = scrq;
	}
	
	
}
