package com.luculent.data.entity;

/**
 * Created by luculent on 2017/4/19.
 */
public class Head {
    private  String  rtnMsg;
    private  String rtnCode;

    public Head() {
    }

    public Head(String rtnMsg, String rtnCode) {
        this.rtnMsg = rtnMsg;
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }
}
