package com.luculent.data.utils.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luculent.data.constant.JsonKey;
import com.luculent.data.model.BackBean;

public class ConventionUtils {

	/**
	 * 防止类被实例化
	 */
	private ConventionUtils() {
		// TODO Auto-generated constructor stub
		throw new AssertionError();
	}
	
	public static BackBean jsonToBackBean(String json){
	    if(StringUtils.isEmpty(json)){
		return null;
	    }
	    JSONObject obj = JSON.parseObject(json);
	    JSONObject head = obj.getJSONObject(JsonKey.head.name());
	    JSONObject body = obj.getJSONObject(JsonKey.body.name());
	    return new BackBean.Builder(head.getString(JsonKey.rtnCode.name()),head.getString(JsonKey.rtnMsg.name()))
		.sql(body.getString(JsonKey.sql.name()))
		.total(body.getString(JsonKey.total.name()))
		.page(body.getString(JsonKey.page.name()))
		.list(body.getString(JsonKey.list.name()))
		.build();
	}
	
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
