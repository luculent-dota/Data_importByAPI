package com.luculent.data.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.Assert;

/**
 * 
 * @ClassName. BaseStaticTreeNode
 * @Description. 静态树节点
 * @author zhangyang
 * @date 2015年8月8日 上午8:45:05
 * @version V1.0
 */
public class BaseStaticTreeNode implements TreeNode{

	private String id;
	
	private String text;
	
	private boolean disabled = false;
	
	private String fatherId;
	
	private String type;
	
	private String icon;
	
	private String state;
	
	private int orderNum;
	
	private String url;
	
	private Map<String,String> attributs;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	public BaseStaticTreeNode() {
		// TODO Auto-generated constructor stub
	}
	
	public BaseStaticTreeNode(String id, String text) {
		setId(id);
		setText(text);
	}
	
	public BaseStaticTreeNode(String id, String text, String fatherId) {
		setId(id);
		setText(text);
		setFatherId(fatherId);
	}
	
	public BaseStaticTreeNode(String id, String text, String fatherId, String type) {
		setId(id);
		setText(text);
		setFatherId(fatherId);
		setType(type);
	}
	
	public BaseStaticTreeNode(String id, String text, String fatherId, String type, String icon) {
		setId(id);
		setText(text);
		setFatherId(fatherId);
		setType(type);
		setIcon(icon);
	}
	
	
	public BaseStaticTreeNode(String id, String text, String fatherId, String type, String icon, String url) {
		setId(id);
		setText(text);
		setFatherId(fatherId);
		setType(type);
		setIcon(icon);
		setUrl(url);
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getAttributs() {
		return attributs;
	}

	public void setAttributs(Map<String, String> attributs) {
		this.attributs = attributs;
	}
	
	public void attr(String key,String value){
		if(this.attributs == null){
			this.attributs = new HashMap<String,String>();
		}
		this.attributs.put(key, value);
	}

	public void addChild(BaseStaticTreeNode currentNode) {
		children.add(currentNode);
	}

	public void addChilds(List<TreeNode> currentNodes) {
		children.addAll(currentNodes);
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	/**
	* <p>Description:TODO </p> 
	* @return
	*/
	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return children.size() == 0;
	}
	
	public boolean getLeaf(){
		return isLeaf();
	}
	
	public boolean equals(Object obj){
		if(obj instanceof BaseStaticTreeNode){
			BaseStaticTreeNode node  = (BaseStaticTreeNode) obj;
			Assert.notNull(this.getId(),"id为空不能做为一个有效的节点");
			if(this.getId().equals(node.getId())){
				return true;
			}
		}
		return super.equals(obj);
	}
	
	protected Object  clone() {
		Object target = null;
		try{
			target = BeanUtils.cloneBean(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		return target;
	}

	
	
	
}
