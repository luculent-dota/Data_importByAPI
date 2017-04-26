package com.luculent.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luculent.data.mapper.SysConfigMapper;

@Service
@Transactional(value="datain")
public class SysConfigService{
	@Autowired
	private SysConfigMapper sysConfigMapper;
}
