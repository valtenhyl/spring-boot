package com.valten.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String login() {
        return "login";
    }

    @GetMapping("/user/common")
    public String common() {
        return "user/common";
    }

    @GetMapping("/user/admin")
    public String admin() {
        return "user/admin";
    }
}

