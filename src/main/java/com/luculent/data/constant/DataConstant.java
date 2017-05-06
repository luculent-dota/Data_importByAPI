package com.luculent.data.constant;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.core.Conventions;

import com.luculent.data.utils.util.ConventionUtils;

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
	
	public static final DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	
	
	/** 验证码. */
	public static final String CODE_NAME="验证码";
	
	public static final String RES_CODE_SUCCESS="000000";
	
	public static final String RES_CODE_NOTLOGIN="900003";
	
	public static final String URL_AND ="&";
	
	public static final String URL_EQUAL ="=";
	
	public static final int CONNECTION_TIMEOUT = 60;
	
	public static final String PATH = System.getProperty("user.dir") +"\\src\\main";
	
	public static final String SCHEDULER_PATH = DataConstant.PATH+"\\java\\com\\luculent\\data\\scheduler\\";
	
	public static final String SCHEDULER_PACKAGE_PATH="com.luculent.data";
	/**ocr地址*/
	public static final String OCR_PATH =PATH+"\\resources\\";
	/**图片输出地址*/
	public static final String TEMP_PATH =PATH+"\\webapp\\temp\\";
	/**图片扩展名 */
	public static final String IMG_TYPE =".gif";
	
	public static final ThreadLocal<Integer> PAGENUM = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 1;
		}
	};
	
	/**自动登陆重试次数(从1开始 值减1).*/
	public static final int AUTO_LOGIN_NUM =4;
	
	/**
	 * 参数失败原因组
	 */
	
	public static final String REA010SONKEY="REA010SONKEY";
	
	public static final String REA010SON="REA010SON";
	
	@SuppressWarnings("unchecked")
	public static final Map<String,String> FAILPARAMS_NETWORK = ConventionUtils.toMap(REA010SONKEY,FailParamType.NETWORK.name(),REA010SON,FailParamType.NETWORK.getReason());
	@SuppressWarnings("unchecked")
	public static final Map<String,String> FAILPARAMS_LOGIN = ConventionUtils.toMap(REA010SONKEY,FailParamType.LOGIN.name(),REA010SON,FailParamType.LOGIN.getReason());
	@SuppressWarnings("unchecked")
	public static final Map<String,String> FAILPARAMS_NOEXISTS = ConventionUtils.toMap(REA010SONKEY,FailParamType.NOEXISTS.name(),REA010SON,FailParamType.NOEXISTS.getReason());

}
