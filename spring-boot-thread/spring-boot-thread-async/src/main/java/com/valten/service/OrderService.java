package com.valten.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private TranTest2Service tranTest2Service;

    // 订单处理任务
    public void orderTask() throws InterruptedException {
        this.cancelOrder(); // 取消订单
        tranTest2Service.sendMessage1(); // 发短信的方法   1
        tranTest2Service.sendMessage2(); // 发短信的方法  2=
    }

    // 取消订单
    public void cancelOrder() {
        System.out.println("取消订单的方法执行------开始");
        System.out.println("取消订单的方法执行------结束 ");
    }
}
