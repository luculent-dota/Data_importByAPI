package com.luculent.data.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.constant.ApiType;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.constant.ParamType;
import com.luculent.data.export.ExportDataService;
import com.luculent.data.mapper.RunRecordMapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.BackBean;
import com.luculent.data.model.RunParams;
import com.luculent.data.model.RunRecord;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysMenu;
import com.luculent.data.model.SysMenuChild;
import com.luculent.data.model.SysParam;
import com.luculent.data.model.SysProject;
import com.luculent.data.utils.util.ConventionUtils;
import com.luculent.data.utils.util.OkHttpUtil;

@Service
@Transactional(value="datain")
public class SysApiService {
	private final static  Logger logger = LogManager.getLogger("run_long");
	
	@Autowired
	private SysProjectMapper sysProjectMapper;
	@Autowired
	private SysApiMapper sysApiMapper;
	@Autowired
	private SysParamMapper sysParamMapper;
	@Autowired
	private BasisKeyService basisKeyService;
	@Autowired
	private ExportDataService exportDataService;
	@Autowired
	private RunRecordMapper runRecordMapper;
	
	
	

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
				    BackBean res= OkHttpUtil.getBeanContent(getJoinUrl(sysApi,code));
				    if(res != null){
				    	if(DataConstant.RES_CODE_SUCCESS.equals(res.getRtnCode())){
				    		logger.info("项目【"+sysProject.getName()+"】自动登陆成功...");
				    		return true;
				    	}
				    	logger.error("项目【"+sysProject.getName()+"】自动登陆失败..."+"失败原因:"+res.getRtnMsg()+",错误代码:"+res.getRtnCode());
				    	return false;
				    }
				}
			}
			logger.error("项目【"+sysProject.getName()+"】获取验证码失败...");
	    	return false;
		
		}
		logger.error("项目【"+sysProject.getName()+"】下无验证码api,登陆失败...");
		return false;
	}
	
	
	public RunParams paramAnalysis(String json){
	    JSONObject jsonObj = JSONObject.parseObject(json);
	    String apiId = jsonObj.getString("APIID");
	    SysApi sysApi = sysApiMapper.selectById(apiId);
	    List<SysParam> params  = sysParamMapper.selectList(new EntityWrapper<SysParam>().eq("api_id", apiId));
	    if(params !=null && params.size() !=0){
		Map<String,List<String>> paramMap = new HashMap<String,List<String>>();
		String pageName = null;
		for(SysParam param:params){
		    if(!ParamType.PAGE.name().equals(param.getParamType())){
			  String paramValue =jsonObj.getString(param.getName());
			    if(StringUtils.isNotEmpty(paramValue)){
				paramMap.put(param.getName(), Arrays.asList(StringUtils.split(paramValue, ",")));
			    }else if(1==param.getRequired()){
				//基础值
				if(ParamType.BASIS.name().equals(param.getParamType())){
				    paramMap.put(param.getName(), basisKeyService.getCacheBykey(param.getDataSource()));
				//数据库值
				}else if(ParamType.DATABASE.name().equals(param.getParamType())){
				    paramMap.put(param.getName(), exportDataService.exportColumnByKeys(param.getDataSource()));
				}
				
			    }
		    }else{
			pageName = param.getName();
		    }
		    
		}
		List<Map<String,String>> paramList = ConventionUtils.toParamsMap(paramMap);
		jsonObj.remove("APIID");
		String paramStr =jsonObj.toJSONString();
		
		RunRecord record = new RunRecord(apiId,paramStr);
		runRecordMapper.insert(record);
		record = runRecordMapper.selectById(record.getId());
		logger.info("数据处理开始,纪录Id为【"+record.getId()+"】,开始时间为 :"+record.getScrq()+",执行参数为:"+paramStr);
		
		RunParams.Builder builder = new RunParams.Builder(sysApi.getProjectId(), apiId, sysApi.getUrl(), record.getId());
		if(paramList!=null&&paramList.size()!=0){
		    builder.params(paramList);
		}
		if(StringUtils.isNotEmpty(pageName)){
		    builder.needPage(true).pageName(pageName);
		}
		return builder.build();
		
	    }
	    logger.error("数据处理失败,id为【"+apiId+"】,名称为【"+sysApi.getName()+"】的接口不存在参数");
	    return null;
	}
	
	

	
}
