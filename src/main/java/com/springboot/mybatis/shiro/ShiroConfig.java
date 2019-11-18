package com.springboot.mybatis.shiro;

import com.springboot.mybatis.entity.User;
import com.springboot.mybatis.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 */
@Configuration
public class ShiroConfig {
    private UserService userService;
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
     public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * 添加Shiro内置过滤器
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *      常用过滤器：anon:无需认证
         *                  authc:必须认证才可以访问
         *                  user:如果使用rememberMe的功能可以直接访问
         *                  perms:必须得到资源权限才可以访问
         *                  role:必须得到角色权限才可以访问
         */
        Map<String,String> filterMap=new LinkedHashMap<String,String>();
        filterMap.put("/login","anon");
        //授权过滤器：当授权拦截后，shiro会自动跳转到未授权的页面
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");
        filterMap.put("/*","authc");
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //未授权页面跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuto");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DeafultWebSecurityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     *创建Realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}
