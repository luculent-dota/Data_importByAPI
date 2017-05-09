package com.luculent.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.luculent.data.exception.APIParamsNotFoundException;

@Service
public class ExportDataService {

    @Autowired
    private JdbcTemplate jdbcTemplateIn;
    
    
    public int exportDataBySql(String[] sql) {
	int sum = 0;
	int rowsAffected[] = jdbcTemplateIn.batchUpdate(sql);
	for (int i = 0; i < rowsAffected.length; i++) {
	    sum += rowsAffected[i];
	}
	return sum;
    }

}
