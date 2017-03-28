/**   
* @Package com.app.open.common.tree 
*/
package com.luculent.data.utils.tree;

import java.util.List;

/** 
 * <p>Description: 树节点接口</p>
 * 实现了这个接口的对象可以直接转化成json data给前端的js框架处理
 * @author zhangyang
 * <p>日期:2015年6月29日 下午3:36:35</p> 
 * @version V1.0 
 */
public interface TreeNode {
	
	/**
	 * 得到子节点
	 * 
	 * @return list nest {@link TreeNode}
	 */
	public List<TreeNode> getChildren();

	/**
	 * 得到能唯一确定节点的id
	 * 
	 * @return
	 */
	public String getId();
	
	/**
	 * 得到父亲节点的id
	 * @return
	 */
	public String getFatherId();

	/**
	 * 是否叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf();

	/**
	 * 得到节点内容
	 * 
	 * @return
	 */
	public String getText();
	
	/**
	 * 获取路径内容
	 * 
	* @return String
	 */
	public String getUrl();
	
	/**
	 * equals
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj);
	
	/**
	 * 排序号
	 * @return
	 */
	public Integer getOrderNum();
}
