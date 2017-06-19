package com.luculent.data.shiro;

import java.util.HashSet;
import java.util.Set;

import com.luculent.data.base.BaseModel;

public class ShiroUser extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2318759612354768059L;
	
	private String userName;
	
	private Set<String> projectIds;
	
	private Set<String> apiProjectIds;
	
	public ShiroUser() {
		// TODO Auto-generated constructor stub
	}
	
	public ShiroUser(String userName,Set<String> projectIds,Set<String> apiProjectIds) {
		this.userName = userName;
		this.projectIds = projectIds;
		this.apiProjectIds = apiProjectIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<String> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(Set<String> projectIds) {
		this.projectIds = projectIds;
	}

	public Set<String> getApiProjectIds() {
		return apiProjectIds;
	}

	public void setApiProjectIds(Set<String> apiProjectIds) {
		this.apiProjectIds = apiProjectIds;
	}
	
	@Override
	public String toString(){
		return userName;
		
	}
	
	
}
