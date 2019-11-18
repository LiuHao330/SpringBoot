package com.springboot.mybatis.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.springboot.mybatis.entity.User;
import com.springboot.mybatis.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权逻辑");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //添加授权字符串info
//        info.addStringPermission("user:add");
        Subject subject= SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();
        User dbUser=userService.findById(user.getId());
        info.addStringPermission(user.getSalt());
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证逻辑");
        //编写Shiro判断逻辑，判断用户名和密码
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        User user=userService.findByName(token.getUsername());
        if(user==null){
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
    /**
     * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
