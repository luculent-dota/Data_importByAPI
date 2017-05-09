package com.luculent.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luculent.data.mapper.SysProjectMapper;

@Service
@Transactional
public class SysProjectService {
	@Autowired
	private SysProjectMapper sysProjectMapper;
	
}
