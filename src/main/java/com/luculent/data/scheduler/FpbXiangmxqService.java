package com.luculent.data.scheduler;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luculent.data.model.RunParams;
import com.luculent.data.service.SchedulerService;

/**
 * 
 *@Description:扶贫办-项目详情 数据处理类
 *@Author:zhangy
 */
@Service
public class FpbXiangmxqService implements IBaseScheduler{
	
	@Autowired
	private SchedulerService schedulerService;
	
	@Override
	public void test(String json){
	    schedulerService.paramsHandler(json);
	}
	
	@Override
	public void test1() {
	    System.out.println(new Date().getTime());
	    
	}

}