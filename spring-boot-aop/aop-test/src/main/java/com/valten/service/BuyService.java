package com.valten.service;

import com.valten.anno.AuthPermission;
import org.springframework.stereotype.Service;

/**
 * 购买业务
 *
 * @className BuyService
 * @package com.valten.service
 * @author huangyuanli
 * @date 2020/7/8 16:11
 **/
@Service("buyService")
public class BuyService {

    @AuthPermission
    public void buyItem(int userId) {
        System.out.println("我买东西！来自：" + userId);
    }
}
