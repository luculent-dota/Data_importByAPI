package com.luculent.data;

/**
 * 系统常量
 */
public class DataConstant {
	
	/**
	 * 防止类被实例化
	 */
	private DataConstant() {
		// TODO Auto-generated constructor stub
		throw new AssertionError();
	}

	/** 菜单树根节点. */
	public static final String VirtualBaseTree ="VirtualBaseTree";
	
	/** 菜单路径. */
	public static final String MENU_HREF ="/api/index.htm?apiId=";
	
	
	/** 验证码. */
	public static final String CODE_NAME="验证码";
	
	
	public static final String RES_CODE_SUCCESS="000000";
	
	public static final String URL_AND ="&";
	
	public static final String URL_EQUAL ="=";

}
