/**   
* @Package com.app.open.common.tree 
*/
package com.luculent.data.utils.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/** 
 * 静态树，一次呈现全部节点，
 * <p>
 * 区别分动态树，子结点每次获取
 * @author zhangyang
 * <p>日期:2015年6月29日 下午4:02:48</p> 
 * @version V1.0 
 */
public class StaticTree {
	/** 虚拟根节点 */
	public static final String VIRTUAL_ROOT_NODE_ID = "VirtualRootNode";
	
	private List BaseStaticTreeNodes;
	
	private BaseStaticTreeNode rootNode;
	
	private List filterIds = new ArrayList();

	private List filerNodes = new ArrayList();
	
	
	public StaticTree(BaseStaticTreeNode[] BaseStaticTreeNodes,BaseStaticTreeNode rootNode){
		this.BaseStaticTreeNodes  = Arrays.asList(BaseStaticTreeNodes);
		this.rootNode = rootNode;
	}
	
	public StaticTree(BaseStaticTreeNode[] BaseStaticTreeNodes,String rootNodeId){
		this.BaseStaticTreeNodes  = Arrays.asList(BaseStaticTreeNodes);
		this.rootNode = getRootNode(rootNodeId);
	}
	
	private BaseStaticTreeNode getRootNode(String rootNodeId){
		for(Iterator iter = BaseStaticTreeNodes.iterator();iter.hasNext();){
			BaseStaticTreeNode node = (BaseStaticTreeNode) iter.next();
			if(null == rootNodeId && null == node.getFatherId()){
				return node;
			}
			if(null !=rootNodeId && rootNodeId.equals(node.getId())){
				return node;
			}
		}
		throw new IllegalArgumentException("没有对应的根节点");
	}
	/*
	 * 和递归方法共享的一个变量，即 未关联到树上的节点，会持续递减
	 */
	private List remainNodes;
	/**
	 * 构造整个树
	 * 
	 */
	public  void render(){
		remainNodes = BaseStaticTreeNodes;
		construct(rootNode);
	}
	/**
	 * 构造 树结点
	 * 
	 * @param rootNode
	 * @param remainNodes
	 *            未关联到树上的节点
	 */
	private void construct(BaseStaticTreeNode processNode){
		// 未关联到树上的节点
		List tempRemainNodes = new ArrayList();
		// 已经关联树上的最外层的节点
		List treeNodes = new ArrayList();
		// 取出需要过滤的集合
		if(filterIds.contains(processNode.getId())){
			filerNodes.add(processNode);
		}
		// 从未关联的节点中找出processNode的子节点并增加父子关系
		for(Iterator it = remainNodes.iterator();it.hasNext();){
			BaseStaticTreeNode currentNode = (BaseStaticTreeNode) it.next();
			// 取出当前节点id和 processNode父节点id相等的节点
			if(processNode.getId().equals(currentNode.getFatherId())){
				// 增加父节点的子节点
				processNode.addChild(currentNode);
				treeNodes.add(currentNode);
			}else{
				// 加到未处理的节点集合中
				tempRemainNodes.add(currentNode);
			}
		}
		
		remainNodes = tempRemainNodes;
		
		for(int i=0;i<treeNodes.size();i++){
			BaseStaticTreeNode node =(BaseStaticTreeNode) treeNodes.get(i);
			construct(node);
		}
	}
	
	public BaseStaticTreeNode getRootNode(){
		return rootNode;
	}
	
	public void setRootNode(BaseStaticTreeNode rootNode){
		 this.rootNode = rootNode;
	}
	
	public BaseStaticTreeNode getFilerNodes(){
		VitualRootNode vitualRootNode = new VitualRootNode();
		vitualRootNode.addChilds(filerNodes);
		return vitualRootNode;
	}
	
	public void setFilerNodes(List<TreeNode> filerNodes){
		this.filterIds = filerNodes;
	}
	
	public class VitualRootNode extends BaseStaticTreeNode {
		
		public VitualRootNode() {
			// TODO Auto-generated constructor stub
			setId(VIRTUAL_ROOT_NODE_ID);
		}
		
		public String getFatherId(){
			return null;
		}
		
		public String getText(){
			return "";
		}
	}

}
