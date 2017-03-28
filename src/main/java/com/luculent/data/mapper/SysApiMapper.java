package com.luculent.data.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.luculent.data.model.SysApi;

public interface SysApiMapper extends BaseMapper<SysApi>{

	public List<SysApi> queryProjectWithApiName();
}
