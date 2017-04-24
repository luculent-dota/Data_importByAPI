package com.luculent.data.exception;

/**
 * 
 *@Description:基础异常类 继承 运行时异常
 *@Author:zhangy
 *@Since:2017年4月24日下午3:35:50
 */
public class BaseRuntimeException extends RuntimeException {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1521993636589378485L;

    public BaseRuntimeException() {
	super();
    }

    public BaseRuntimeException(String msg) {
	super(msg);
    }

    public BaseRuntimeException(String msg, Throwable t) {
	super(msg, t);
    }
}
