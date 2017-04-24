package com.luculent.data.exception;

public class BasisKeyNotFoundException extends BaseRuntimeException {

    /** 
     */
    private static final long serialVersionUID = -7299361885349391670L;

    public BasisKeyNotFoundException() {
	super("此基础常量不存在");
    }

}
