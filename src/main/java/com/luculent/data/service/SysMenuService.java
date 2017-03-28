package com.luculent.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luculent.data.mapper.SysApiMapper;
import com.luculent.data.mapper.SysProjectMapper;
import com.luculent.data.model.SysApi;
import com.luculent.data.model.SysProject;
import com.luculent.data.utils.tree.BaseStaticTreeNode;
import com.luculent.data.utils.tree.StaticTree;
import com.luculent.data.utils.tree.TreeNode;

@Service
@Transactional
public class SysMenuService {
	@Autowired
	private SysProjectMapper sysProjectMapper;
	@Autowired
	private SysApiMapper sysApiMapper;
	
	private final String VirtualBaseTree ="VirtualBaseTree";
	
	
	/**
	 * 构建菜单树
	 * @return
	 */
	public TreeNode getMenuTree(){
		List<BaseStaticTreeNode> nodeList = new ArrayList<BaseStaticTreeNode>();
		
		nodeList.add(new BaseStaticTreeNode(VirtualBaseTree, "菜单树", null, "base"));
		List<SysProject> projectList = sysProjectMapper.selectList(new EntityWrapper<SysProject>().orderBy("sort,scrq", true));
		List<SysApi> apiList =  sysApiMapper.selectList(new EntityWrapper<SysApi>().orderBy("sort,scrq", true)); 
		for(SysProject projectBean:projectList){
			BaseStaticTreeNode treeNode = new BaseStaticTreeNode(projectBean.getId(), projectBean.getName(),VirtualBaseTree , "project");
			nodeList.add(treeNode);
		}
		for(SysApi apiBean:apiList){
			BaseStaticTreeNode treeNode = new BaseStaticTreeNode(apiBean.getId(), apiBean.getName(),apiBean.getProjectId() , "api");
			nodeList.add(treeNode);
		}
		StaticTree tree = new StaticTree(nodeList.toArray(new BaseStaticTreeNode[0]),VirtualBaseTree);
		tree.render();
		return tree.getRootNode();
		
	}
	
	
}
