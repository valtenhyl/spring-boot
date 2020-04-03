package com.valten.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    // 在线生成cron表达式 http://cron.qqe2.com/
    @Scheduled(cron = "0/2 * * * * *")
    public void hello() {
        System.out.println("你被执行了~~");
    }
}
