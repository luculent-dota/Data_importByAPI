package com.luculent.data.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luculent.data.model.RunRecord;

@Service
@Transactional
public class DataHandleService {

    @Autowired
    private SchedulerService schedulerService;

    public void nowRun(String json) {
	schedulerService.rightNowExecuteByJSON(json);
    }

    public void retryRun(RunRecord runRecord, String deleteSql) {
	schedulerService.retryExecuteByRecordId(runRecord, deleteSql);
    }
}
