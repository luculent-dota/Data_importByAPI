package com.luculent.data.utils.tree;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.Assert;

/** 
 * <p>Description: 静态树节点</p>
 * @author zhangyang
 * <p>日期:2015年6月29日 下午3:56:45</p> 
 * @version V1.0 
 */
public abstract class BaseTreeNodeDTO implements TreeNode,Serializable{

	
	public BaseTreeNodeDTO(){};
	
	public String url;
	
	public abstract List<TreeNode> getChildren();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public void addChild(BaseTreeNodeDTO currentNode) {
		getChildren().add(currentNode);
	}

	public void addChilds(List<TreeNode> currentNodes) {
		getChildren().addAll(currentNodes);
	}
	
	
	public boolean isLeaf() {
		return getChildren().size() == 0;
	}

	public boolean getLeaf(){
		return isLeaf();
	}
	
	/**
	 * 重写
	 */
	public boolean equals(Object obj) {
		if (obj instanceof BaseTreeNodeDTO) {
			BaseTreeNodeDTO node = (BaseTreeNodeDTO) obj;
			Assert.notNull(this.getId(), "id为空不能做为一个有效的节点!");
			if(this.getId().equals(node.getId())){
				return true;
			}
		}
		return super.equals(obj);
	}
	
	protected Object clone() throws CloneNotSupportedException {
		Object target  = null;
		try {
			target = BeanUtils.cloneBean(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target; 
	}
	
	
	
	
}
