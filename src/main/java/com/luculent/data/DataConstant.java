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
	
	/** get请求 . */
	public static final Integer ASK_TYPE_GET = 1;
	
	/** post请求. */
	public static final Integer ASK_TYPE_POST = 2;
	
	/** 验证码. */
	public static final String CODE_NAME="验证码";
	
	/** api类型为登陆. */
	public static final Integer API_TYPE_LOGIN =1;
	
	/** api类型为验证码. */
	public static final Integer API_TYPE_CODE =2;
	
	/** api类型为其他. */
	public static final Integer API_TYPE_OTHER =0;
	
	public static final String RES_CODE_SUCCESS="000000";
	
	public static final String URL_AND ="&";
	
	public static final String URL_EQUAL ="=";

}
