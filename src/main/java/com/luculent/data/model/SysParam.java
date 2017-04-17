package com.luculent.data.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.luculent.data.base.BaseModel;

/**
 * 
* @ClassName: SysParam 
* @Description: api参数实体
* @author zhangy
* @date 2017年3月28日 下午3:14:11
 */
@TableName("api_param")
public class SysParam extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3829525456651579276L;
	
	/** 主键. */
	private String id;
	/** 参数名称. */
	private String name;
	/** 参数 默认值. */
	private String defaultValue;
	/** 是否必填 1必填 0选填. */
	private Integer required;
	/** 说明. */
	private String detail;
	/** 数据来源. */
	private String dataSource;
	/** 验证码类型 定值CONSTANT，区间INTERVAL，基础值 BASIS，页码PAGE. */
	private String paramType;
	/** 备注. */
	private String remarks;
	/** 所属接口. */
	private String apiId;
	/** 生成日期. */
	private String scrq;

	/**
	 * Gets the 主键.
	 *
	 * @return the 主键
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the 主键.
	 *
	 * @param id
	 *            the new 主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the 参数名称.
	 *
	 * @return the 参数名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 参数名称.
	 *
	 * @param name
	 *            the new 参数名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 参数 默认值.
	 *
	 * @return the 参数 默认值
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the 参数 默认值.
	 *
	 * @param defaultValue
	 *            the new 参数 默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the 是否必填 1必填 0选填.
	 *
	 * @return the 是否必填 1必填 0选填
	 */
	public Integer getRequired() {
		return required;
	}

	/**
	 * Sets the 是否必填 1必填 0选填.
	 *
	 * @param required
	 *            the new 是否必填 1必填 0选填
	 */
	public void setRequired(Integer required) {
		this.required = required;
	}

	/**
	 * Gets the 说明.
	 *
	 * @return the 说明
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Sets the 说明.
	 *
	 * @param detail
	 *            the new 说明
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	

	/**
	 * Gets the 数据来源.
	 *
	 * @return the 数据来源
	 */
	public String getDataSource() {
	    return dataSource;
	}

	/**
	 * Sets the 数据来源.
	 *
	 * @param dataSource
	 *            the new 数据来源
	 */
	public void setDataSource(String dataSource) {
	    this.dataSource = dataSource;
	}
	
	

	/**
	 * Gets the 验证码类型 定值CONSTANT，区间INTERVAL，基础值 BASIS，页码PAGE.
	 *
	 * @return the 验证码类型 定值CONSTANT，区间INTERVAL，基础值 BASIS，页码PAGE
	 */
	public String getParamType() {
	    return paramType;
	}

	/**
	 * Sets the 验证码类型 定值CONSTANT，区间INTERVAL，基础值 BASIS，页码PAGE.
	 *
	 * @param paramType
	 *            the new 验证码类型 定值CONSTANT，区间INTERVAL，基础值 BASIS，页码PAGE
	 */
	public void setParamType(String paramType) {
	    this.paramType = paramType;
	}

	/**
	 * Gets the 备注.
	 *
	 * @return the 备注
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the 备注.
	 *
	 * @param remarks
	 *            the new 备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Gets the 所属接口.
	 *
	 * @return the 所属接口
	 */
	public String getApiId() {
		return apiId;
	}

	/**
	 * Sets the 所属接口.
	 *
	 * @param apiId
	 *            the new 所属接口
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	/**
	 * Gets the 生成日期.
	 *
	 * @return the 生成日期
	 */
	public String getScrq() {
		return scrq;
	}

	/**
	 * Sets the 生成日期.
	 *
	 * @param scrq
	 *            the new 生成日期
	 */
	public void setScrq(String scrq) {
		this.scrq = scrq;
	}
	
	
	
	

}
