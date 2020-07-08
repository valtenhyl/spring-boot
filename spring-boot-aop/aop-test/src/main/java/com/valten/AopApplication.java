package com.valten;

import com.valten.service.BuyService;
import com.valten.service.ChatService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopApplication.class);
        BuyService buyService = context.getBean(BuyService.class);
        buyService.buyItem(1);

        ChatService chatService = context.getBean(ChatService.class);
        chatService.privateChat(123);
    }

}
