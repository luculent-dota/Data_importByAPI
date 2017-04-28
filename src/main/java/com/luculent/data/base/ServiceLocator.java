package com.luculent.data.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 *@Description:获取上下文组件
 *@Author:zhangy
 *@Since:2017年4月27日上午11:01:21
 */
@Component
public class ServiceLocator implements ApplicationContextAware {

    public static ApplicationContext context;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
	    throws BeansException {
	// TODO Auto-generated method stub
	context = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
               return (T) context.getBean(name);
               
     }
    
    public static boolean containsBean(String name){
	return context.containsBean(name);
    }
    
}
