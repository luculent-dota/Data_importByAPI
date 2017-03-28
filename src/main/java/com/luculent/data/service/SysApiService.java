package com.luculent.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysMenu;
import com.luculent.data.model.SysMenuChild;
import com.luculent.data.model.SysProject;

@Service
@Transactional
public class SysApiService {
	
	@Autowired
	private SysProjectMapper sysProjectMapper;
	@Autowired
	private SysApiMapper sysApiMapper;
	
	private String MenuHref ="/api/index.htm?apiId=";

	/**
	 * 获取菜单json
	 * @param projectId
	 * @return
	 */
	public Object getMenuJSON(String projectId){
		SysProject sysProject  = sysProjectMapper.selectById(projectId);
		List<SysApi> apiList =  sysApiMapper.selectList(new EntityWrapper<SysApi>().eq("project_id", projectId).orderBy("sort,scrq", true));
		List<SysMenuChild> children = new ArrayList<SysMenuChild>();
		
		if(apiList !=null && apiList.size()!=0){
			for(SysApi apiBean:apiList){
				children.add(new SysMenuChild(apiBean.getName(),MenuHref+apiBean.getId()));
			}
		}
		
		SysMenu sysMenu = new SysMenu(sysProject.getName(),children);
		List<SysMenu> menu = new ArrayList<SysMenu>(){{add(sysMenu);}};
		return JSONArray.toJSON(menu);
		
		
	}

	
}
