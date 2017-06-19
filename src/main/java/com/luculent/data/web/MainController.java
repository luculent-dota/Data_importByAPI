package com.luculent.data.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.luculent.data.shiro.ShiroUser;

@Controller
public class MainController extends BaseController{
	
	@Autowired
	private SysProjectMapper sysProjectMapper;
	@Autowired
	private SysApiMapper sysApiMapper;
	
	@RequestMapping("main/index")
	public ModelAndView mainIndex(ModelAndView modelAndView) throws Exception{
		   modelAndView.setViewName("index");
		   ShiroUser user = getShiroUser();
		   List<SysProject> projectList = sysProjectMapper.selectList(new EntityWrapper<SysProject>().in("id", user.getProjectIds()).orderBy("sort,scrq", true));
		   modelAndView.addObject("projectList", projectList);
		   Set<String> apiSet = new HashSet<String>();
			if(user.getApiProjectIds() !=null && user.getApiProjectIds().size() !=0){
				for(String apiProjectId:user.getApiProjectIds()){
					apiSet.add(apiProjectId.substring(0, 32));
				}
			}
		   List<SysApi> apiList = sysApiMapper.queryProjectWithApiName(apiSet.toArray(new String[] {}));
		   modelAndView.addObject("apiList", apiList);
		   modelAndView.addObject("userName",user.getUserName());
	       return modelAndView;
		}
	
	@RequestMapping(value = "/404")
	public ModelAndView error_404(ModelAndView modelAndView) throws Exception{
	   modelAndView.setViewName("common/error/404");
       return modelAndView;
	}
	
	@RequestMapping(value = "/500")
	public ModelAndView error_500(ModelAndView modelAndView) throws Exception{
	   modelAndView.setViewName("common/error/500");
       return modelAndView;
	}
}
