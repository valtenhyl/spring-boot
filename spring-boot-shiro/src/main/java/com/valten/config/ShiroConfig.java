package com.valten.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.valten.model.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro配置类
 **/
@Configuration
public class ShiroConfig {

    @Autowired
    private PermsMap permsMap;

    /**
     * 配置Shiro的Web过滤器，拦截浏览器请求并交给SecurityManager处理
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean webFilter(@Qualifier("securityManager") SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>(16);
        // 配置退出过滤器logout，由shiro实现
        filterChainMap.put("/logout", "logout");
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
        filterChainMap.put("/login", "anon");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 权限注入
        // filterChainMap.put("/cat","roles[cat]");

        // 动态权限注入
        List<Map<String, String>> perms = permsMap.getPerms();
        perms.forEach(perm -> filterChainMap.put(perm.get("url"), perm.get("permission")));

        filterChainMap.put("/**", "authc");

        // 设置默认登录的URL.
        shiroFilterFactoryBean.setLoginUrl("/login");
        System.out.println(filterChainMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    /**
     * org.apache.shiro.mgt.SecurityManager
     * 配置Shiro核心 安全管理器 SecurityManager
     * SecurityManager安全管理器：所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；负责与后边介绍的其他组件进行交互。（类似于SpringMVC中的DispatcherServlet控制器）
     * 将session管理器和cache管理器注入到SecurityManager中
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("customRealm") CustomRealm customRealm,
                                           @Qualifier("redisCacheManager") RedisCacheManager redisCacheManager,
                                           @Qualifier("sessionManager") DefaultWebSessionManager sessionManager,
                                           @Qualifier("rememberMeManager") CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 将自定义的realm交给SecurityManager管理
        securityManager.setRealm(customRealm);
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(redisCacheManager);
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager);
        // 使用记住我
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    /**
     * 注入自定义的Ream
     *
     * @return
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        //注入密码加密
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    /**
     * 密码加密算法设置
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 总结：一般对未授权用户需要统一返回指定403页面的，
     * 使用注解更加方便；需要做业务逻辑（比如对未授权的请求记录进行预警等），使用编程式更加方便。
     */

    /**
     * 注解式：
     * 首先需要开启Aop注解,在ShiroConfig类中新增如下方法：
     * 开启aop注解支持
     * 即在controller中使用 @RequiresPermissions("user/userList")
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        //设置安全管理器
        attributeSourceAdvisor.setSecurityManager(securityManager);
        return attributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 用于thymeleaf模板使用shiro标签
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
