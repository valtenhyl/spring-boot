package com.valten.controller;

import com.valten.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    /**
     * 跳转到登陆页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(String username, String password, boolean rememberMe, Model model) {

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();

        try {
            //主体提交登录请求到SecurityManager
            token.setRememberMe(rememberMe);
            currentUser.login(token);
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "密码不正确");
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "账号不存在");
        } catch (AuthenticationException ae) {
            model.addAttribute("msg", "状态不正常");
        }
        if (currentUser.isAuthenticated()) {
            System.out.println("认证成功");
            return "redirect:/success";
        } else {
            token.clear();
            return "login";
        }
    }

    @GetMapping({"/", "/success"})
    public String success(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        model.addAttribute("username", user.getUsername());
        return "success";
    }
}
