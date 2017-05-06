package com.luculent.data.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.base.BaseController;
import com.luculent.data.constant.ApiType;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.mapper.RunRecordMapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.RunRecord;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysParam;
import com.luculent.data.model.SysProject;
import com.luculent.data.scheduler.FpbXiangmxqService;
import com.luculent.data.service.SysApiService;
import com.luculent.data.utils.util.OkHttpUtils;

@Controller
@RequestMapping("/api")
public class SysAPIController extends BaseController {

    @Autowired
    private SysApiService sysApiService;
    @Autowired
    private SysApiMapper sysApiMapper;
    @Autowired
    private SysParamMapper sysParamMapper;
    @Autowired
    private SysProjectMapper sysProjectMapper;
    @Autowired
    private FpbXiangmxqService fpbXiangmxqService;
    @Autowired
    private RunRecordMapper runRecordMapper;	
    
    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView, String apiId) {
	modelAndView.setViewName("api/index");
	SysApi sysApi = sysApiMapper.selectById(apiId);
	modelAndView.addObject("sysApiBean", sysApi);
	modelAndView.addObject("apiId", apiId);
	SysProject sysProject = sysProjectMapper.selectById(sysApi.getProjectId());
	modelAndView.addObject("projectBean", sysProject);
	List<SysParam> params = sysParamMapper
		.selectList(new EntityWrapper<SysParam>().eq("api_id", apiId).orderBy("scrq"));
	modelAndView.addObject("params", params);
	StringBuilder paramBuf = new StringBuilder();
	if (params != null && params.size() != 0) {

	    for (SysParam param : params) {
		if (ApiType.ISREQUIRED.getVal() == param.getRequired()) {
		    paramBuf.append(DataConstant.URL_AND).append(param.getName()).append(DataConstant.URL_EQUAL)
			    .append(param.getDefaultValue());
		}
	    }
	}
	modelAndView.addObject("paramStr", paramBuf.toString());
	//数据处理
	int nowRunCount = runRecordMapper.selectCount(new EntityWrapper<RunRecord>().eq("api_id", apiId).isNull("carry_time"));
	modelAndView.addObject("nowRunCount", nowRunCount);
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
	return sysApiService.autoLoginByProjectId(projectId) ? renderSuccess("登陆成功") : renderError("登陆失败");
    }

    @ResponseBody
    @RequestMapping("/test-run")
    public String testRun(String url, Integer apiType) {
	if (StringUtils.isEmpty(url)) {
	    return null;
	}
	String res = "";
	if (apiType == ApiType.CODE.getVal()) {
	    res = OkHttpUtils.getImageDownLoad(url);
	} else {
	    res = OkHttpUtils.getContent(url);
	}
	return res;
    }

    @ResponseBody
    @RequestMapping("/real-run")
    public Object realRun(@RequestBody String json) {
	JSONObject jsonObj = JSONObject.parseObject(json);
	String apiId = jsonObj.getString("APIID");
	SysApi sysApi = sysApiMapper.selectById(apiId);
//	String schedulerClass = ConventionUtils.firstSpellToLow(sysApi.getSchedulerClass());
//	if(!ServiceLocator.containsBean(schedulerClass)){
//	    return renderError("启动失败！任务类未加载,请重启服务器");
//	}
//	IBaseScheduler scheduler = (IBaseScheduler) ServiceLocator.getBean(schedulerClass);
	System.out.println(sysApi.getSchedulerClass());
	fpbXiangmxqService.test(json);
	return renderSuccess("启动成功");
    }
}
