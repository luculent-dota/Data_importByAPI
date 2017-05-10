package com.luculent.data.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class FpbXiangmxqJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
	// TODO Auto-generated method stub
	
    }

}
