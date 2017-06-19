package com.luculent.data.shiro;

import org.apache.log4j.chainsaw.Main;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
 * shiro密码加密配置
 *
 */
public class PasswordHash implements InitializingBean {
	
	private String salt="luculent";
	private String algorithmName;
	private int hashIterations;

	public String getAlgorithmName() {
		return algorithmName;
	}
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	public int getHashIterations() {
		return hashIterations;
	}
	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(algorithmName, "algorithmName mast be MD5、SHA-1、SHA-256、SHA-384、SHA-512");
	}
	
	public String toHex(Object source) {
		return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
	}
	
	public static void main(String[] args) {
		PasswordHash ss = new PasswordHash();
		ss.setAlgorithmName("MD5");
		ss.setHashIterations(1);
		System.err.println(ss.toHex("admin"));
		String api ="09a389c672634d51a0554f3abeec75b8-8b15573c83a14dc699b39f49e8571b69";
		System.err.println(api.substring(0, 32));
	}
}

