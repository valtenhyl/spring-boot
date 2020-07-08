package com.valten.service;

import com.valten.anno.AuthPermission;
import org.springframework.stereotype.Service;

/**
 * 聊天业务
 *
 * @className ChatService
 * @package com.valten.service
 * @author huangyuanli
 * @date 2020/7/8 16:11
 **/
@Service("chatService")
public class ChatService {

    @AuthPermission
    public void privateChat(int userId) {
        System.out.println("我要聊天！来自：" + userId);
    }
}
