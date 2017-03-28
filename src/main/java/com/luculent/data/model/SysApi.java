package com.luculent.data.model;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

@TableName("sys_api")
public class SysApi extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4913953256757403823L;

	private String id;
	
	private String name;
	
	private String alias;
	
	private String projectId;
	
	private String url;
	
	private Integer askType;
	
	private String res;
	
	private Integer apiType;
	
	private Integer sort;
	
	private String scrq;
	
	
	
	@TableField(exist=false)
	private List<SysParam> paramList;

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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getAskType() {
		return askType;
	}

	public void setAskType(Integer askType) {
		this.askType = askType;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}
	
	public Integer getApiType() {
		return apiType;
	}

	public void setApiType(Integer apiType) {
		this.apiType = apiType;
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

	public List<SysParam> getParamList() {
		return paramList;
	}

	public void setParamList(List<SysParam> paramList) {
		this.paramList = paramList;
	}
	
	
}
