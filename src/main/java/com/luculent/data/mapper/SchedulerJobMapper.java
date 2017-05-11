package com.luculent.data.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.luculent.data.model.SchedulerJob;

public interface SchedulerJobMapper extends BaseMapper<SchedulerJob>{

    public List<SchedulerJob> queryListByClassName(String className);
}
