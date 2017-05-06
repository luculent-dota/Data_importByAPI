package com.luculent.data.constant;

public enum FailParamType {
    NETWORK("网络连接不稳定"),LOGIN("自动登陆失败"),NOEXISTS("参数不正确");
    
    private String reason;
    
    private FailParamType(String reason) {
	// TODO Auto-generated constructor stub
	this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

}
