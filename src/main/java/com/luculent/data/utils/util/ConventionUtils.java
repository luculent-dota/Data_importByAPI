package com.luculent.data.utils.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConventionUtils {

	
	/**
	 * 
	* <p>Description: map转换工具</p> 
	* @param objs
	* @return Map
	* @throws
	 */
	public static Map toMap(Object... objs) {
		HashMap map = new HashMap();
		Object key = null;
		for (int i = 0; i < objs.length; i++) {
			if(i%2 == 0){
				key = objs[i];
			}else{
				map.put(key, objs[i]);
			}
		}
		return map;
	}
	
	
	/**
	 * 
	* <p>Description: list转换工具</p> 
	* @param objs
	* @return Map
	* @throws
	 */
	public static List toList(Object... objs) {
		List result = new ArrayList();
		for (int i = 0; i < objs.length; i++) {
			result.add(objs[i]);
		}
		return result;
	}
}
