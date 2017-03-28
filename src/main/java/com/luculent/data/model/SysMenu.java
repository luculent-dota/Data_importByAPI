package com.luculent.data.model;

import java.util.List;

/**
 * 
* @ClassName: SysMenu 
* @Description: 系统用菜单 非数据库表 
* @author zhangy
* @date 2017年3月28日 下午3:13:15
 */
public class SysMenu {
	
	private String title;
	private String icon="fa-cubes";
	private boolean spread = true;
	
	private List<SysMenuChild> children;
	
	public SysMenu() {
		// TODO Auto-generated constructor stub
	}
	
	public SysMenu(String title,List<SysMenuChild> children) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.children = children;
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public List<SysMenuChild> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuChild> children) {
		this.children = children;
	}
	
	
}
