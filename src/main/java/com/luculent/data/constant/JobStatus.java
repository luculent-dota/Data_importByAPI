package com.luculent.data.constant;

public enum JobStatus {
    ENABLED(1),DISABLED(0);
    
    private int val;
    
    private JobStatus(int val) {
	// TODO Auto-generated constructor stub
	this.val = val;
    }

    public int getVal() {
        return val;
    }
    
}
