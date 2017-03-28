/**   
* @Package com.app.open.common.tree 
*/
package com.luculent.data.utils.tree;

/** 
 * <p>Description: 树节点状态</p>
 * @author zhangyang
 * <p>日期:2015年6月29日 下午3:39:10</p> 
 * @version V1.0 
 */
public enum TreeNodeState {
	OPEN,CLOSED,LEAF;
	
	/**
	 * 字符串格式的树节点状态
	 * @return
	 */
	public String getName(){
		return this.name().toLowerCase();
	}
}
