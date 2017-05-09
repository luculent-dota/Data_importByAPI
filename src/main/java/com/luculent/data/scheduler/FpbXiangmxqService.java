package com.luculent.data.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luculent.data.model.RunRecord;
import com.luculent.data.service.SchedulerService;

/**
 * 
 *@Description:扶贫办-项目详情 数据处理类
 *@Author:zhangy
 */
@Service
@Transactional
public class FpbXiangmxqService {
	
	@Autowired
	private SchedulerService schedulerService;
	
	public void test(String json){
	    schedulerService.rightNowExecuteByJSON(json);
	}
	
	public void test1() {
	    System.out.println(new Date().getTime());
	    
	}
	
	public void retry(RunRecord runRecord){
	    schedulerService.retryExecuteByRecordId(runRecord);
	}

}