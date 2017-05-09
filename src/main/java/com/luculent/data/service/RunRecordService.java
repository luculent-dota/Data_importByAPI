package com.luculent.data.service;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.mapper.RunRecordMapper;
import com.luculent.data.model.RunRecord;
import com.luculent.data.model.SysApi;

@Service
@Transactional
public class RunRecordService {
    
    private final static Logger logger = LogManager.getLogger(RunRecordService.class);

    @Autowired
    private RunRecordMapper runRecordMapper;
    
    @PostConstruct  
    public void  deviceInit(){  
	runRecordMapper.delete(new EntityWrapper<RunRecord>().isNull("end_time"));
	if(logger.isDebugEnabled()){
	    logger.debug("任务日志表中的脏数据清除完成");
	}
    } 
}
