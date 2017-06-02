package other.builder;

import org.testng.annotations.Test;
/**
 * 
 *@Description:多构造器
 *@Author:zhangyang
 *@Since:2017年6月2日上午10:27:54
 */
public class UserBeanTeleSoping {
    private String id;

    private String name;

    private String address;

    private String phone;

    private String qq;

    public UserBeanTeleSoping() {
	// TODO Auto-generated constructor stub
    }

    public UserBeanTeleSoping(String id, String name) {
	this.id = id;
	this.name = name;
    }

    public UserBeanTeleSoping(String id, String name, String phone) {
	this.id = id;
	this.name = name;
	this.phone = phone;
    }

//     public UserBeanTeleSoping(String id,String name,String qq) {
//     this.id = id;
//     this.name =name;
//     this.qq = qq;
//    
//     }

    public UserBeanTeleSoping(String id, String name, String address, String phone, String qq) {
	this.id = id;
	this.name = name;
	this.address = address;
	this.phone = phone;
	this.qq = qq;
    }

    public String toOutString() {
	return "id: " + this.id + "\n name:" + this.name + "\n address:" + this.address + "\n phone:" + this.phone
		+ "\n qq:" + this.qq;
    }

    @Test
    public void test() {
	UserBeanTeleSoping user = new UserBeanTeleSoping("1111", "java", "185648");
	System.out.println(user.toOutString());

    }
}
