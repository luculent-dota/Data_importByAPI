package com.luculent.data.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.DataConstant;
import com.luculent.data.base.BaseController;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysParam;
import com.luculent.data.model.SysProject;
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
	@Autowired
	private SysProjectMapper sysProjectMapper;
	

	
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView modelAndView,String apiId) {
        modelAndView.setViewName("api/index");
        SysApi sysApi =  sysApiMapper.selectById(apiId);
        modelAndView.addObject("sysApiBean", sysApi);
        modelAndView.addObject("apiId",apiId);
        SysProject sysProject=sysProjectMapper.selectById(sysApi.getProjectId());
        modelAndView.addObject("projectBean", sysProject);
        List<SysParam> params = sysParamMapper.selectList(new EntityWrapper<SysParam>().eq("api_id", sysApi.getId()).orderBy("scrq"));
        modelAndView.addObject("params", params);
        StringBuffer paramBuf = new StringBuffer();
        if(params !=null && params.size()!=0){
        	
        	for(SysParam param:params){
        		paramBuf.append(DataConstant.URL_AND).append(param.getName()).append(DataConstant.URL_EQUAL).append(param.getDefaultValue());
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
	
	
	@ResponseBody
	@RequestMapping("/auto-login")
	public Object autoLogin(String projectId) {
		boolean loginIn = false;
		for(int i=0;i<3;i++){
			if(sysApiService.autoLoginByProjectId(projectId)){
				loginIn = true;
				break;
			}
		}
		return loginIn?renderSuccess("登陆成功"):renderError("登陆失败");
	}
	
	
	@ResponseBody
	@RequestMapping("/test-run")
    public String testRun(String url,Integer apiType) {
		if(StringUtils.isEmpty(url)){
			return null;
		}
		String res = null;
		if(apiType == DataConstant.API_TYPE_CODE){
			res =HttpClientUtil.getImageDownLoad(url);
		}else{
			res = HttpClientUtil.getContent(url);
		}
		return res;
    }
}
