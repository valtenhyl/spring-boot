package com.valten.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 首页所有人可以访问，功能页有对应权限的人可以访问
        // 请求授权的规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/common").hasRole("vip1")
                .antMatchers("/user/admin").hasRole("vip2");

        // 没有权限默认会到登录页面，需要开启登录页面
          // /login
          // 定制登录页 loginPage("/toLogin") 默认用户名密码 username/password
        http.formLogin().loginPage("/toLogin").usernameParameter("username").passwordParameter("password").loginProcessingUrl("/login");

        // 防止网站攻击
        http.csrf().disable(); // 关闭crsf功能

        // 开启注销功能,跳到首页
        http.logout().logoutSuccessUrl("/");

        // 开启记住我功能, cookie 默认包存两周
        http.rememberMe().rememberMeParameter("remember");
    }

    // 认证， 从springboot 2.1.x可以直接使用
    // 密码编码： PasswordEncoder
    // 在Spring Security 5.0+新增了很多加密方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 这些数据正常应该从数据库获取
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("test").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2");
    }
}

