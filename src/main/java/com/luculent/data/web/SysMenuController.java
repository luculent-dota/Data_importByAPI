package com.luculent.data.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.luculent.data.base.BaseController;
import com.luculent.data.constant.ApiType;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysParamMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysParam;
import com.luculent.data.model.SysProject;
import com.luculent.data.service.SysMenuService;
import com.luculent.data.utils.util.DynamicCompileUtils;
import com.luculent.data.utils.util.TemplateUtils;

@Controller
@RequestMapping("/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysApiMapper sysApiMapper;
    @Autowired
    private SysProjectMapper sysProjectMapper;
    @Autowired
    private SysParamMapper sysParamMapper;

    @RequestMapping("/index")
    public ModelAndView mainIndex(ModelAndView modelAndView) throws Exception {
	modelAndView.setViewName("menu/index");
	return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/tree")
    public Object getMenuTree() {
	return sysMenuService.getMenuTree();
    }

    @ResponseBody
    @RequestMapping("/project-get")
    public Object getProject(String id) {
	if (StringUtils.isNotEmpty(id)) {
	    return sysProjectMapper.selectById(id);
	}
	return null;
    }

    @ResponseBody
    @RequestMapping("/api-get")
    public Object getApi(String id) {
	if (StringUtils.isNotEmpty(id)) {
	    SysApi sysApi = sysApiMapper.selectById(id);
	    sysApi.setParamList(
		    sysParamMapper.selectList(new EntityWrapper<SysParam>().eq("api_id", id).orderBy("scrq")));
	    return sysApi;
	}
	return null;
    }

    @ResponseBody
    @RequestMapping("/project-save")
    public Object saveProject(SysProject sysProject) {
	if (StringUtils.isEmpty(sysProject.getId())) {
	    return sysProjectMapper.insert(sysProject) != null ? renderSuccess("配置成功") : renderError("配置失败");
	} else {
	    List<SysApi> apis = sysApiMapper
		    .selectList(new EntityWrapper<SysApi>().eq("project_id", sysProject.getId()));
	    for (SysApi api : apis) {
		api.setUrl(String.format(sysProject.getBaseUrl() + "?iw-apikey=%s&iw-cmd=%s", sysProject.getIwApikey(),
			api.getAlias()));
		sysApiMapper.updateById(api);
	    }
	    return sysProjectMapper.updateById(sysProject) != null ? renderSuccess("修改成功") : renderError("修改失败");
	}
    }

    @ResponseBody
    @RequestMapping("/api-save")
    public Object save(@RequestBody SysApi sysApi) {
	String projectId = sysApi.getProjectId();
	SysProject sysProject = sysProjectMapper.selectById(projectId);
	if (StringUtils.isNotEmpty(sysProject.getIwApikey())) {
	    sysApi.setUrl(String.format(sysProject.getBaseUrl() + "?iw-apikey=%s&iw-cmd=%s", sysProject.getIwApikey(),
		    sysApi.getAlias()));
	}
	if (StringUtils.isEmpty(sysApi.getId())) {
	    if(ApiType.OTHER.getVal() == sysApi.getApiType()){
		sysApi.setSchedulerClass(TemplateUtils.createClass(sysProject.getName(), sysApi.getName()));
	    }
	    sysApiMapper.insert(sysApi);
	} else {
	    if(ApiType.OTHER.getVal() == sysApi.getApiType()){
		sysApi.setSchedulerClass(TemplateUtils.createClass(sysProject.getName(), sysApi.getName()));
	    }
	    sysApiMapper.updateById(sysApi);
	}
	sysParamMapper.delete(new EntityWrapper<SysParam>().eq("api_id", sysApi.getId()));
	for (SysParam param : sysApi.getParamList()) {
	    param.setApiId(sysApi.getId());
	    sysParamMapper.insert(param);
	}
	return renderSuccess("修改成功");

    }

    @ResponseBody
    @RequestMapping("/api-del")
    public Object delApi(String id) {
	if (StringUtils.isNotEmpty(id)) {
	    return sysApiMapper.deleteById(id) != null ? renderSuccess("配置成功") : renderError("配置失败");
	}
	return renderError("配置失败");
    }

    @ResponseBody
    @RequestMapping("/project-del")
    public Object delProject(String id) {
	if (StringUtils.isNotEmpty(id)) {
	    sysApiMapper.delete(new EntityWrapper<SysApi>().eq("project_id", id));
	    return sysProjectMapper.deleteById(id) != null ? renderSuccess("配置成功") : renderError("配置失败");
	}
	return renderError("配置失败");
    }

}
