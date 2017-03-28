/**   
* @Package com.app.open.common.tree 
*/
package com.luculent.data.utils.tree;

/** 
 * <p>Description: TODO</p>
 * @author zhangyang
 * <p>日期:2015年7月1日 下午5:20:17</p> 
 * @version V1.0 
 */
public class ZTreeDTO {
	
	private String id;
	
	private String pId;
	
	private String name;
	
	/** 
	 * <p>Description: </p>  
	 */
	public ZTreeDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ZTreeDTO(String id,String pId,String name) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.pId = pId;
		this.name = name;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
