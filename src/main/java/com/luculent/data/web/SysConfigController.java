package com.luculent.data.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.base.BaseController;
import com.luculent.data.mapper.SysConfigMapper;
import com.luculent.data.model.SysConfig;
import com.luculent.data.model.SysProject;

@Controller
@RequestMapping("/config")
public class SysConfigController extends BaseController{

	@Autowired
	private SysConfigMapper sysConfigMapper;

	@RequestMapping("/index")
	public ModelAndView index(ModelAndView modelAndView) {
		SysConfig sysConfig=sysConfigMapper.selectOne(new SysConfig());
		modelAndView.addObject("config", sysConfig);
        modelAndView.setViewName("config/index");
        return modelAndView;
    }
	
	@ResponseBody
	@RequestMapping("/save")
    public Object save(SysConfig sysConfig) {
        if (StringUtils.isEmpty(sysConfig.getId())) {
            return sysConfigMapper.insert(sysConfig) != null ? renderSuccess("配置成功") : renderError("配置失败");
        } else {
            return sysConfigMapper.updateById(sysConfig) != null ? renderSuccess("修改成功"):renderError("修改失败");
        }
    }
	
	
	
}
