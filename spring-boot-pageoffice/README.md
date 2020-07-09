### 前后端分离

#### index.html
> 谷歌浏览器版本42以上必须采用`POBrowser.openWindowModeless`的方式打开
```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="jquery.min.js"></script>
    <script type="text/javascript" src="pageoffice.js" id="po_js_main"></script>
</head>
<body>
<h1 th:inline="text">顺E通 测试 PageOffice 集成效果演示</h1>

<a href="javascript:POBrowser.openWindowModeless('http://192.168.40.104:8081/syt/word','width=1200px;height=800px;');">打开文件</a>
</html>
```

#### nginx.json
> syt开头的所有请求都转到后端
```
    location ^~ /syt/ {
        proxy_pass http://192.168.40.26:9090;
    }
```

因为项目设置有上下文,即`server.servlet.context-path: /syt`,所以后台pageoffice服务页面也得做相应的修改,加上山下文
```java
        // 设置服务页面
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
```

#### 如果跨域,配置跨域
```java
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");
        // 2允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 3允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
```