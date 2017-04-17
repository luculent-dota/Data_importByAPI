package com.luculent.data.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.DataConstant;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.ApiType;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysMenu;
import com.luculent.data.model.SysMenuChild;
import com.luculent.data.model.SysParam;
import com.luculent.data.model.SysProject;
import com.luculent.data.utils.util.OkHttpUtil;

@Service
@Transactional
public class SysApiService {
	private final static  Logger logger = LogManager.getLogger(SysApiService.class);
	
	@Autowired
	private SysProjectMapper sysProjectMapper;
	@Autowired
	private SysApiMapper sysApiMapper;
	@Autowired
	private SysParamMapper sysParamMapper;
	
	
	
	

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
				children.add(new SysMenuChild(apiBean.getId(),apiBean.getName(),DataConstant.MENU_HREF+apiBean.getId()));
			}
		}
		
		SysMenu sysMenu = new SysMenu(sysProject.getName(),children);
		List<SysMenu> menu = new ArrayList<SysMenu>(){{add(sysMenu);}};
		return JSONArray.toJSON(menu);
		
		
	}
	
	public String getJoinUrl(SysApi sysApi,String code){
		List<SysParam> params = sysParamMapper.selectList(new EntityWrapper<SysParam>().eq("api_id", sysApi.getId()));
		StringBuilder joinUrl = new StringBuilder(sysApi.getUrl());
	     if(params !=null && params.size()!=0){
	        	for(SysParam param:params){
	        		joinUrl.append(DataConstant.URL_AND).append(param.getName()).append(DataConstant.URL_EQUAL);
	        		if(StringUtils.isNotEmpty(code)&&DataConstant.CODE_NAME.equals(param.getDetail())){
	        			joinUrl.append(code);
	        		}else{
	        			joinUrl.append(param.getDefaultValue());
	        		}
	        	}
	    }
	    return joinUrl.toString();
	}
	
	public boolean autoLoginByProjectId(String projectId){
		SysProject sysProject = sysProjectMapper.selectById(projectId);
		logger.info("项目【"+sysProject.getName()+"】自动登陆开始...");
		//验证码
		List<SysApi> codeList =sysApiMapper.selectList(new EntityWrapper<SysApi>().eq("project_id", projectId).eq("api_type",ApiType.CODE.getVal()));
		if(codeList !=null && codeList.size() !=0){
			String code =OkHttpUtil.getCodeResult(codeList.get(0).getUrl());
			if(StringUtils.isNotEmpty(code)){
				List<SysApi> loginList =sysApiMapper.selectList(new EntityWrapper<SysApi>().eq("project_id", projectId).eq("api_type", ApiType.LOGIN.getVal()));
				if(loginList !=null && loginList.size() !=0){
					SysApi sysApi = loginList.get(0);
				    String res= OkHttpUtil.getContent(getJoinUrl(sysApi,code));
				    if(StringUtils.isNotEmpty(res)){
				    	String result = JSONObject.parseObject(res).getJSONObject("head").getString("rtnCode");
				    	if(DataConstant.RES_CODE_SUCCESS.equals(result)){
				    		logger.info("项目【"+sysProject.getName()+"】自动登陆成功...");
				    		return true;
				    	}
				    	logger.info("项目【"+sysProject.getName()+"】自动登陆失败..."+"错误代码:"+result);
				    	return false;
				    }
				}
			}
			logger.info("项目【"+sysProject.getName()+"】获取验证码失败...");
	    	return false;
		
		}
		logger.info("项目【"+sysProject.getName()+"】下无验证码api,登陆失败...");
		return false;
	}

	
}
