package com.luculent.data.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luculent.data.service.SysApiService;

@Service
@Transactional(value="datain")
public class SysTestService {
       private final static  Logger logger = LogManager.getLogger("run_long");
	
	@Autowired
	private SysApiService sysApiService;
	
	public void test(String json){
	    sysApiService.paramAnalysis(json);
	}
}
