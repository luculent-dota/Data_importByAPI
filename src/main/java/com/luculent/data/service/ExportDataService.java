package com.luculent.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExportDataService {

    @Autowired
    private JdbcTemplate jdbcTemplateOut;

    @Autowired
    private JdbcTemplate jdbcTemplateIn;

    @Transactional(value = "dataout")
    public List<String> exportColumnByKeys(String sql) {
	return jdbcTemplateOut.queryForList(sql, String.class);
    }

    @Transactional(value = "datain")
    public int exportDataBySql(String[] sql) {
	int[] rowsAffected = jdbcTemplateIn.batchUpdate(sql);
	int sum =0;
	for (int i = 0; i < rowsAffected.length; i++) {
	    sum+=rowsAffected[i];
	}
	return sum;
    }

}
