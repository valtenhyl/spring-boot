package com.valten.support;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 *
 * @author huangyuanli
 * @className FtpDownloadListener
 * @package com.valten.listener
 * @date 2020/7/12 10:58
 **/
@Component
@Order(value = 1)
public class FtpDownloadRunner implements ApplicationRunner {

    @Autowired
    private FtpDownloadRoute ftpDownloadRoute;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 这是camel上下文对象，整个路由的驱动全靠它了。
        ModelCamelContext camelContext = new DefaultCamelContext();
        // 启动route
        camelContext.start();
        // 将我们的路由处理加入到上下文中
        camelContext.addRoutes(ftpDownloadRoute);
    }
}
