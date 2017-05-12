package com.luculent.data.constant;

import com.luculent.data.base.IntegerEnum;

public enum JobStatus implements IntegerEnum{
    ENABLED(1),DISABLED(0);
    
    private Integer val;
    
    private JobStatus(Integer val) {
	// TODO Auto-generated constructor stub
	this.val = val;
    }

    @Override
    public Integer getVal() {
	// TODO Auto-generated method stub
	return this.val;
    }

    
}
