package com.valten.controller;

import com.valten.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("hello")
    public void test() throws InterruptedException {
        orderService.orderTask();
    }
}
