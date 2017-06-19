package com.luculent.data.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.luculent.data.base.JsonResult;
import com.luculent.data.base.Result;
import com.luculent.data.utils.util.WebUtils;

@Component
public class ExceptionResolver implements HandlerExceptionResolver{

	
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		if(!(handler instanceof HandlerMethod)){
			return new ModelAndView("common/error/500");
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		if(WebUtils.isAjax(handlerMethod)){
			JsonResult result = new JsonResult();
			result.setSuccess(false);
			result.setMsg(ex.getMessage());
			FastJsonJsonView view = new FastJsonJsonView();
			return new ModelAndView(view,BeanMap.create(result));
		}
		return new ModelAndView("common/error/500").addObject("error",ex.getMessage());
	}

}
