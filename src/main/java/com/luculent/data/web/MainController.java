package com.luculent.data.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.base.BaseController;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysProject;

@Controller
public class MainController extends BaseController{
	
	@Autowired
	private SysProjectMapper sysProjectMapper;
	@Autowired
	private SysApiMapper sysApiMapper;
	
	@RequestMapping("main/index")
	public ModelAndView mainIndex(ModelAndView modelAndView) throws Exception{
		   modelAndView.setViewName("index");
		   List<SysProject> projectList = sysProjectMapper.selectList(new EntityWrapper<SysProject>().orderBy("sort,scrq", true));
		   modelAndView.addObject("projectList", projectList);
		   List<SysApi> apiList = sysApiMapper.queryProjectWithApiName();
		   modelAndView.addObject("apiList", apiList);
	       return modelAndView;
		}
	
	@RequestMapping(value = "/404")
	public ModelAndView error_404(ModelAndView modelAndView) throws Exception{
	   modelAndView.setViewName("common/error/404");
       return modelAndView;
	}
}
