package other.builder;

import org.testng.annotations.Test;
/**
 * 
 *@Description:builder模式
 *@Author:zhangyang
 *@Since:2017年6月2日上午10:28:09
 */
public class UserBeanBuilder {
    private String id;

    private String name;

    private String address;

    private String phone;

    private String qq;

    public UserBeanBuilder() {
	// TODO Auto-generated constructor stub
    }

    public static class Builder {
	private String id;

	private String name;

	private String address;

	private String phone;

	private String qq;

	public Builder(String id, String name) {
	    this.id = id;
	    this.name = name;

	}

	public Builder address(String address) {
	    this.address = address;
	    return this;
	}

	public Builder phone(String phone) {
	    this.phone = phone;
	    return this;
	}

	public Builder qq(String qq) {
	    this.qq = qq;
	    return this;
	}
	
	public UserBeanBuilder build(){
	    return new UserBeanBuilder(this);
	}
    }
    
    private UserBeanBuilder(Builder builder){
	this.id = builder.id;
	this.name = builder.name;
	this.phone = builder.phone;
	this.address = builder.address;
	this.qq = builder.qq;
	
    }
    
    public String toOutString() {
        return "id: "+this.id+"\n name:"+this.name+"\n address:"+this.address+"\n phone:"+this.phone+"\n qq:"+this.qq;
    }
    
    @Test(description="builder 方式运行")
    public void test(){
	UserBeanBuilder user = new UserBeanBuilder.Builder("1111", "java").phone("1111").build();
	System.out.println(user.toOutString());
    }

}
