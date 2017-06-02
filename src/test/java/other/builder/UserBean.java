package other.builder;

import org.testng.annotations.Test;

/**
 * 
 *@Description:传统模式
 *@Author:zhangyang
 *@Since:2017年6月2日上午10:27:33
 */
public class UserBean {

    private String id;
    
    private String name;
    
    private String address;
    
    private String phone;
    
    private String qq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
    
    public String toOutString() {
        return "id: "+this.id+"\n name:"+this.name+"\n address:"+this.address+"\n phone:"+this.phone+"\n qq:"+this.qq;
    }
    
    
    @Test
    public void test(){
	UserBean user = new UserBean();
	user.setId("1111");
	user.setName("java");
	System.out.println(user.toOutString());
    }
    
}
