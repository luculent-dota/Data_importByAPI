package com.luculent.data.constant;

import com.luculent.data.base.IntegerEnum;

/**
 * 
 *@Description:Api类型
 *@Author:zhangy
 *@Since:2017年4月13日上午11:03:00
 */
public enum ApiType implements IntegerEnum{
    LOGIN(1),CODE(2),OTHER(0),
    ISREQUIRED(1),NOREQUIRED(0);
    
    private Integer val;
    
    private ApiType(Integer val){
	this.val = val;
    }

    @Override
    public Integer getVal() {
	// TODO Auto-generated method stub
	return this.val;
    }

    
}
