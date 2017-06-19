package com.luculent.data.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.struts.chain.commands.servlet.CreateAction;
import org.springframework.beans.factory.annotation.Autowired;

import com.luculent.data.constant.DataConstant;
import com.luculent.data.service.SysApiService;
import com.luculent.data.service.SysProjectService;

public class ShiroDbRealm extends AuthorizingRealm{
	
	@Autowired
	private SysApiService sysApiService;
	@Autowired
	private SysProjectService sysProjectService;
	
	private String USERNAME="admin";
	private String PASSWORD="1e0798d96c84567e086fe2c3d3802d57";
	
	public ShiroDbRealm(CacheManager cacheManager,CredentialsMatcher matcher) {
		super(cacheManager,matcher);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(shiroUser.getProjectIds());
		info.setStringPermissions(shiroUser.getApiProjectIds());
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userName = token.getUsername();
		if(!USERNAME.equals(userName)){
			return null;
		}
		//其实user是可以存储更多信息的
		ShiroUser shiroUser = new ShiroUser(USERNAME,sysProjectService.queryAllProjectId(),sysApiService.queryAllApiIdWithProjectId());
		//添加认证缓存
		return new SimpleAuthenticationInfo(shiroUser,PASSWORD.toCharArray(),ShiroByteSource.of(DataConstant.SHIRO_SALT),getName());
	}
	
	@Override
	public void onLogout(PrincipalCollection principals){
		super.clearCache(principals);
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		removeUserCache(shiroUser);
	}
	
	private void removeUserCache(ShiroUser shiroUser){
		SimplePrincipalCollection principals = new SimplePrincipalCollection();
		principals.add(shiroUser.getUserName(), super.getName());
		super.clearCachedAuthenticationInfo(principals);
	}

}
