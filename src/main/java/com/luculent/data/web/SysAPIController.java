package com.luculent.data.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.base.BaseController;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysParam;
import com.luculent.data.service.SysApiService;
import com.luculent.data.utils.util.HttpClientUtil;

@Controller
@RequestMapping("/api")
public class SysAPIController  extends BaseController{

	@Autowired
	private SysApiService sysApiService;
	@Autowired
	private SysApiMapper sysApiMapper;
	@Autowired
	private SysParamMapper sysParamMapper;
	
	private final String CODE_NAME="验证码";
	private final Integer CODE_TYPE =2;
	private final Integer LOGIN_TYPE =1;

	
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView modelAndView,String apiId) {
        modelAndView.setViewName("api/index");
        SysApi sysApi =  sysApiMapper.selectById(apiId);
        modelAndView.addObject("sysApiBean", sysApi);
        modelAndView.addObject("apiId",apiId);
        List<SysParam> params = sysParamMapper.selectList(new EntityWrapper<SysParam>().eq("api_id", sysApi.getId()).orderBy("scrq"));
        modelAndView.addObject("params", params);
        StringBuffer paramBuf = new StringBuffer();
        if(params !=null && params.size()!=0){
        	
        	for(SysParam param:params){
        		paramBuf.append("&").append(param.getName()).append("=").append(param.getDefaultValue());
        	}
        }
        modelAndView.addObject("paramStr", paramBuf.toString());
        return modelAndView;
    }
	
	@ResponseBody
	@RequestMapping("/menu-json")
    public Object getMenuJSON(String projectId) {
		return sysApiService.getMenuJSON(projectId);
    }
	
	@RequestMapping("/project-login")
	public ModelAndView autoLoginPage(ModelAndView modelAndView,String projectId){
		modelAndView.setViewName("api/auto-login");
		modelAndView.addObject("projectId", projectId);
		List<SysApi> list =sysApiMapper.selectList(new EntityWrapper<SysApi>().eq("project_id", projectId).eq("api_type", CODE_TYPE));
		if(list !=null && list.size() !=0){
			modelAndView.addObject("msg", "success");
			String uuid =HttpClientUtil.getImageDownLoad(list.get(0).getUrl());
			modelAndView.addObject("uuid", uuid);
		}else{
			modelAndView.addObject("msg", "fail");
		}
	    
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("/auto-login")
	public String autoLogin(String code,String projectId) {
		List<SysApi> list =sysApiMapper.selectList(new EntityWrapper<SysApi>().eq("project_id", projectId).eq("api_type", LOGIN_TYPE));
		if(list !=null && list.size() !=0){
			SysApi sysApi = list.get(0);
			List<SysParam> params = sysParamMapper.selectList(new EntityWrapper<SysParam>().eq("api_id", sysApi.getId()));
			 StringBuffer loginUrl = new StringBuffer(sysApi.getUrl());
		     if(params !=null && params.size()!=0){
		        	for(SysParam param:params){
		        		loginUrl.append("&").append(param.getName()).append("=");
		        		if(CODE_NAME.equals(param.getDetail())){
		        			loginUrl.append(code);
		        		}else{
		        			loginUrl.append(param.getDefaultValue());
		        		}
		        	}
		    }
		    System.err.println(loginUrl.toString());
		    String res= HttpClientUtil.getContent(loginUrl.toString());
		    if(StringUtils.isNotEmpty(res)){
		    	String result = JSONObject.parseObject(res).getJSONObject("head").getString("rtnCode");
		    	if(result.equals("000000")){
		    		return "登陆成功";
		    	}
		    	return "登陆失败";
		    }
			//String res =HttpClientUtil.getImageDownLoad();
		}
		return "登陆失败";
	}
	
	
	@ResponseBody
	@RequestMapping("/test-run")
    public String testRun(String url,Integer apiType) {
		if(StringUtils.isEmpty(url)){
			return null;
		}
		String res = null;
		if(apiType == CODE_TYPE){
			res =HttpClientUtil.getImageDownLoad(url);
		}else{
			res = HttpClientUtil.getContent(url);
		}
		
		return res;
    }
}
