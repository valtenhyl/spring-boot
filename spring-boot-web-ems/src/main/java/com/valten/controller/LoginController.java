package com.valten.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        if (!StringUtils.isEmpty(username) && "111111".equals(password)) {
            session.setAttribute("current_user", username);
            return "redirect:/main.html";
        } else {
            model.addAttribute("msg", "用户名或密码错误!");
            return "index";
        }
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        // 清除缓存
        session.invalidate();
        return "redirect:/index.html";
    }
}
