package com.luculent.data.model;

public class SysMenuChild {

	private String title;
	
	private String icon="&#xe641;";
	
	private String href;
	
	public SysMenuChild() {
		// TODO Auto-generated constructor stub
	}
	
	public SysMenuChild(String title,String href) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.href = href;
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	
}
