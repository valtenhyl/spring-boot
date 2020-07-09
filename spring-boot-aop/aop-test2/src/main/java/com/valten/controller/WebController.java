package com.valten.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class WebController {

    @RequestMapping("/hello")
    public String index(@PathParam("name") String name, @PathParam("age") int age) {
        return "Hello " + name + ", 今年" + age + "岁";
    }
}
