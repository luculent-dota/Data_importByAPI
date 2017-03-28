package com.luculent.data.utils.util;

import java.util.UUID;

public class UUIDBuilder {

	public static String getUUID(){
	     return UUID.randomUUID().toString().replace("-", "");
	}
}
