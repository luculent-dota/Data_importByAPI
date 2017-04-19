package com.luculent.data.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * Created by luculent on 2017/4/19.
 */
public class DemoJob implements StatefulJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
