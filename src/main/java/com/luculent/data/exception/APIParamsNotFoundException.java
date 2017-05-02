package com.luculent.data.exception;

/**
 * 
 *@Description:API无参数异常
 *@Author:zhangy
 *@Since:2017年5月2日上午9:52:03
 */
public class APIParamsNotFoundException extends BaseRuntimeException{

    /** 
     *@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */ 
    private static final long serialVersionUID = -447509920668773759L;
    
    public APIParamsNotFoundException() {
	// TODO Auto-generated constructor stub
	super("API找不到参数异常");
    }
    
    public APIParamsNotFoundException(String msg){
	super(msg);
    }
}
