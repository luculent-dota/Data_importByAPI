package com.luculent.data.exception;

public class SchedulerJobAboutException extends BaseRuntimeException{

    /** 
     *@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */ 
    private static final long serialVersionUID = 4949259803500852881L;

    public SchedulerJobAboutException() {
	// TODO Auto-generated constructor stub
	super("定时任务配置出现异常");
    }
    
    public SchedulerJobAboutException(String msg) {
	// TODO Auto-generated constructor stub
	super(msg);
    }
    
}
