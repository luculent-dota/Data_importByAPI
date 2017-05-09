package com.luculent.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataBaseKeyService {

    @Autowired
    private JdbcTemplate jdbcTemplateOut;
    
    public List<String> exportColumnByKeys(String sql) {
   	return jdbcTemplateOut.queryForList(sql, String.class);
    }
}
