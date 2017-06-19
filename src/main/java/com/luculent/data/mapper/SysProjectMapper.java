package com.luculent.data.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.luculent.data.model.SysProject;

public interface SysProjectMapper  extends BaseMapper<SysProject>{

	public List<String> queryAllProjectId();
}
