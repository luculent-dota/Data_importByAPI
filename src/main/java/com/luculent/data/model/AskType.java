package com.luculent.data.model;

import com.luculent.data.base.IntegerEnum;

/**
 * 
 *@Description:请求类型
 *@Author:zhangy
 *@Since:2017年4月13日上午11:18:36
 */
public enum AskType implements IntegerEnum{
    GET(1),POST(2);
    private Integer val;

    private AskType(Integer val){
	this.val = val;
    }

    @Override
    public Integer getVal() {
	// TODO Auto-generated method stub
	return this.val;
    }

    
    
    
}
