package com.luculent.data.utils.util;

import java.util.UUID;

public class UUIDBuilder {

	/**
	 * 防止类被实例化
	 */
	private UUIDBuilder() {
		// TODO Auto-generated constructor stub
		throw new AssertionError();
	}
	
	public static String getUUID(){
	     return UUID.randomUUID().toString().replace("-", "");
	}
}
