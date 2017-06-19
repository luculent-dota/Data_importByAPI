package com.luculent.data.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.constant.ApiType;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.BackBean;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysMenu;
import com.luculent.data.model.SysMenuChild;
import com.luculent.data.model.SysParam;
import com.luculent.data.model.SysProject;
import com.luculent.data.utils.util.OkHttpUtils;

@Service
@Transactional
public class SysApiService {
	private final static  Logger logger = LogManager.getLogger("run_long");
	
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
	public Object getMenuJSON(String projectId,Set<String> apiProjectIds){
		SysProject sysProject  = sysProjectMapper.selectById(projectId);
		Set<String> apiSet = new HashSet<String>();
		if(apiProjectIds !=null && apiProjectIds.size() !=0){
			for(String apiProjectId:apiProjectIds){
				if(apiProjectId.indexOf(projectId)!=-1){
					apiSet.add(apiProjectId.substring(0, 32));
				}
			}
		}
		List<SysApi> apiList =  sysApiMapper.selectList(new EntityWrapper<SysApi>().in("id", apiSet).orderBy("sort,scrq", true));
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
	
	/**
	 * 
	 *@Description: 自动登陆
	 *@Author: zhangy
	 *@Since: 2017年5月2日下午3:52:19
	 *@param projectId
	 *@return
	 */
	public boolean autoLoginByProjectId(String projectId){
		SysProject sysProject = sysProjectMapper.selectById(projectId);
		String projectName = sysProject.getName();
		logger.info("项目【"+projectName+"】自动登陆开始...");
		//验证码
		List<SysApi> codeList =sysApiMapper.selectList(new EntityWrapper<SysApi>().eq("project_id", projectId).eq("api_type",ApiType.CODE.getVal()));
		if(codeList !=null && codeList.size() !=0){
		    boolean temp = false;
		    for(int i=1;i<DataConstant.AUTO_LOGIN_NUM;i++){
			String code =OkHttpUtils.getCodeResult(codeList.get(0).getUrl());
			if(StringUtils.isNotEmpty(code)){
				List<SysApi> loginList =sysApiMapper.selectList(new EntityWrapper<SysApi>().eq("project_id", projectId).eq("api_type", ApiType.LOGIN.getVal()));
				if(loginList !=null && loginList.size() !=0){
				    SysApi sysApi = loginList.get(0);
				    BackBean res= OkHttpUtils.getBeanContent(getJoinUrl(sysApi,code));
				    if(res != null){
				    	if(DataConstant.RES_CODE_SUCCESS.equals(res.getRtnCode())){
				    		temp = true;
				    		break;
				    	}
				    	logger.debug("项目【"+projectName+"】自动登陆第"+i+"次失败..."+"失败原因:"+res.getRtnMsg()+",错误代码:"+res.getRtnCode());
				    }
				}
			}
			logger.error("项目【"+projectName+"】自动登陆第"+i+"次失败...失败原因:获取验证码失败...");
	    	
		    }
		    if(temp){
			logger.info("项目【"+projectName+"】自动登陆成功...");
			return true;
		    }
		    logger.info("项目【"+projectName+"】自动登陆失败...");
		    return false;
		
		}
		logger.error("项目【"+projectName+"】下无验证码api,登陆失败...");
		return false;
	}
	
	
	public Set<String> queryAllApiIdWithProjectId(){
		return new HashSet<String>(sysApiMapper.queryAllApiIdWithProjectId());
	}
	
	

	
}
