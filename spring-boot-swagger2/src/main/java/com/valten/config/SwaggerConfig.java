package com.valten.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
// 开启Swagger2的自动配置
@EnableSwagger2
// 也可以在配置文件中配置是否开启swagger
// @ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class SwaggerConfig {

    /**
     * RequestHandlerSelectors，配置要扫描接口的方式
     * basePackage:指定要扫描的包
     * any():扫描全部
     * none():不扫描
     * withClassAnnotation：扫描类上的注解，参数是一个注解的反射对象
     * withMethodAnnotation：扫描方法上的注解
     * paths() 过滤什么路径
     * .paths(PathSelectors.ant("/valten/**"))
     */
    // 配置docket以配置Swagger具体参数
    @Bean
    public Docket docket(ApiInfo apiInfo, Environment environment) {

        // 设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev", "test");
        // 通过environment.acceptsProfiles判断是否在自己设定的环境中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .enable(flag) // enable 是否启动swagger，如果为false，则不能在浏览器中访问 Could not render e, see the console.
                .select() // 通过.select()方法，去配置扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.valten.controller"))
                .build();
    }

    // 配置Swagger信息 ApiInfo
    @Bean
    public ApiInfo apiInfo() {
        Contact contact = new Contact("青衫不改", "http://valten.gitee.io/blog", "valtenhyl@163.com");
        // public ApiInfo(String title, String description, String version, String termsOfServiceUrl, Contact contact, String ", String licenseUrl, Collection<VendorExtension> vendorExtensions)
        return new ApiInfo( "青衫不改的SwaggerAPI文档", // 标题
                "即使再小的帆也能远航", // 描述
                "v1.0", // 版本
                "http://valten.gitee.io/blog", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()); // 扩展
    }
}
