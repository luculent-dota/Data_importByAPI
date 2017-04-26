package com.luculent.data.export;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value="dataout")
public class ExportDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<String> exportColumnByKeys(String sql){
	return jdbcTemplate.queryForList(sql, String.class);
    }
    
    public void exportDataBySql(String[] sql){
	jdbcTemplate.batchUpdate(sql);
    }
    
}
