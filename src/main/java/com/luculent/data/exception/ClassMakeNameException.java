package com.luculent.data.exception;

public class ClassMakeNameException extends BaseRuntimeException{

    /** 
     *@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */ 
    private static final long serialVersionUID = 2077639073708194102L;

    public ClassMakeNameException() {
	super("动态Class异常");
    }
    
    public ClassMakeNameException(String msg) {
	super(msg);
    }
}
