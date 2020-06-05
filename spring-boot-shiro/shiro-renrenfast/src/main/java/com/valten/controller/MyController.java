package com.valten.controller;

import com.valten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MyController {
    @Autowired
    private UserService userService;
    @RequestMapping({"/","/index","/index.html"})
    public String toIndex(Model model){
        return "index";
    }
    @RequestMapping("/user/{handle}")
    public String add(@PathVariable("handle") String handle){
        return "user/" + handle;
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);//执行登录的方法，如果没有异常，就登录ok
            return "index";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("msg", "密码不正确");
            return "login";
        }catch (UnknownAccountException e){
            model.addAttribute("msg", "用户名不正确");
            return "login";
        }
    }
    @RequestMapping("/unAuthor")
    @ResponseBody
    public String unauthorized(){
        return "未经授权，无法访问此页面";
    }
    @RequestMapping("/logout")
    public String logout(){
        //退出操作，获取当前用户，执行logout即可
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index";
    }
}
