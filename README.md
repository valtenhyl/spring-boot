## Spring Boot 入门


> lambda、calendat等相关的测试放在HelloWorld中


#### 1、pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

pom.xml 文件中默认有两个模块：

- `spring-boot-starter` ：核心模块，包括自动配置支持、日志和 YAML，如果引入了 `spring-boot-starter-web` web 模块可以去掉此配置，因为 `spring-boot-starter-web` 自动依赖了 `spring-boot-starter`。
- `spring-boot-starter-test` ：测试模块，包括 JUnit、Hamcrest、Mockito。

#### 2、编写 Controller 内容

```java
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
```

`@RestController` 的意思就是 Controller 里面的方法都以 json 格式输出，不用再写什么 jackjson 配置的了！

#### 3、启动主程序

打开浏览器访问 `http://localhost:8080/hello`，就可以看到效果了，有木有很简单！

#### 4、开发环境的调试（热部署）

热启动在正常开发项目中已经很常见了吧，虽然平时开发web项目过程中，改动项目启重启总是报错；但springBoot对调试支持很好，修改之后可以实时生效，需要添加以下的配置：

```xml
 <dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <fork>true</fork>
            </configuration>
        </plugin>
    </plugins>
</build>
```

该模块在完整的打包环境下运行的时候会被禁用。如果你使用 `java -jar`启动应用或者用一个特定的 classloader 启动，它会认为这是一个“生产环境”。



## Spring Boot Web 综合开发



#### 1、pom.xml

```xml
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--jpa-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
```

#### 2、配置文件

```properties
# 数据库
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
# jpa
spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.show-sql=true
```

>  **其实这个 hibernate.hbm2ddl.auto 参数的作用主要用于：自动创建 、更新、验证数据表结构，有以下四个参数：**
>
> 1. create： 每次加载 hibernate 时都会删除上一次的生成的表，然后根据你的 model 类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
> 2. create-drop ：每次加载 hibernate 时根据 model 类生成表，但是 sessionFactory 一关闭,表就自动删除。
> 3. update：最常用的属性，第一次加载 hibernate 时根据 model 类会自动建立起表的结构（前提是先建立好数据库），以后加载 hibernate 时根据 model 类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
> 4. validate ：每次加载 hibernate 时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。

`dialect` 主要是指定生成表名的存储引擎为 InnoDBD
`show-sql` 是否打印出自动生成的 SQL，方便调试的时候查看

#### 3、实体类

```java
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true, unique = true)
    private String nickName;
    @Column(nullable = false)
    private String regTime;

    public User() {
    }

    public User(String userName, String passWord, String email, String nickName, String regTime) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.nickName = nickName;
        this.regTime = regTime;
    }

    // 省略getter settet方法
}
```

dao 只要继承 JpaRepository 类就可以，几乎可以不用写方法，还有一个特别有尿性的功能非常赞，就是可以根据方法名来自动的生成 SQL，比如`findByUserName` 会自动生成一个以 `userName` 为参数的查询方法，比如 `findAlll` 自动会查询表里面的所有数据，比如自动分页等等。。

**Entity 中不映射成列的字段得加 @Transient 注解，不加注解也会映射成列**

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByUserNameOrEmail(String username, String email);
}
```



如果有移下报错信息，

```log	
Loading class com.mysql.jdbc.Driver'. This is deprecated. The new driver class iscom.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
```

 提示信息表明数据库驱动com.mysql.jdbc.Driver已经被弃用了，应当使用新的驱动com.mysql.cj.jdbc.Driver，也可以修改驱动的版本

> 前言：前段时间发现在家使用和公司一样的mysql jdbc驱动版本发生了异常，原因：家里mysql数据库版本与公司不一致导致。查询了相关资料，发现mysql jdbc驱动版本与mysql数据库版本有一定的对应关系，用错了版本就会出现连接不上数据库的异常。
>
> 这里给出mysql jdbc驱动版本与mysql数据库版本的对应关系，照着以下关系，选择相应的jdbc驱动和mysql数据版本即可。
>
> Connector/J 5.1 支持Mysql 4.1、Mysql 5.0、Mysql 5.1、Mysql 6.0 alpha这些版本。
> Connector/J 5.0 支持MySQL 4.1、MySQL 5.0 servers、distributed transaction (XA)。
> Connector/J 3.1 支持MySQL 4.1、MySQL 5.0 servers、MySQL 5.0 except distributed transaction (XA) support。
> Connector/J 3.0 支持MySQL 3.x or MySQL 4.1。

#### 4、自定义 Filter

我们常常在项目中会使用 filters 用于录调用日志、排除有 XSS 威胁的字符、执行权限验证等等。Spring Boot 自动添加了 OrderedCharacterEncodingFilter 和 HiddenHttpMethodFilter，并且我们可以自定义 Filter。

两个步骤：

> 1. 实现 Filter 接口，实现 Filter 方法
> 2. 添加`@Configuration` 注解，将自定义Filter加入过滤链

好吧，直接上代码

```java
@Configuration
public class WebConfiguration {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    
    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
    
    public class MyFilter implements Filter {
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
		}

		@Override
		public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			HttpServletRequest request = (HttpServletRequest) srequest;
			System.out.println("this is MyFilter,url :"+request.getRequestURI());
			filterChain.doFilter(srequest, sresponse);
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
			// TODO Auto-generated method stub
		}
    }
}
```

## Spring Boot 实现图片上传/加水印

---

> 可 **长按** 或 **扫描** 下面的 **小心心** 来订阅作者公众号 **CodeSheep**，获取更多 **务实、能看懂、可复现的** 原创文 ↓↓↓

![CodeSheep · 程序羊](https://user-gold-cdn.xitu.io/2018/8/9/1651c0ef66e4923f?w=270&h=270&f=png&s=102007)

---

---

### 概述

很多网站的图片为了版权考虑都加有水印，尤其是那些图片类网站。自己正好最近和图片打交道比较多，因此就探索了一番基于 Spring Boot这把利器来实现从 **图片上传 → 图片加水印** 的一把梭操作！

>**注：** 本文首发于  [**My Personal Blog：程序羊**](http://www.codesheep.cn)，欢迎光临 [**小站**](http://www.codesheep.cn)

本文内容脑图如下：

![本文内容脑图](https://upload-images.jianshu.io/upload_images/9824247-0c7821414a4de19f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

---

### 搭建 Spring Boot基础工程

过程不再赘述了，这里给出 pom中的关键依赖：

```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.5</version>
        </dependency>
    </dependencies>
```

---

### 编写文件上传服务

- 主要就是编写 **ImageUploadService** 服务

里面仅一个上传图片的方法：`uploadImage ` 方法

```java
    /**
     * 功能：上传图片
     * @param file 文件
     * @param uploadPath 服务器上上传文件的路径
     * @param physicalUploadPath  服务器上上传文件的物理路径
     * @return 上传文件的 URL相对地址
     */
    public String uploadImage( MultipartFile file, String uploadPath, String physicalUploadPath ) {

        String filePath = physicalUploadPath + file.getOriginalFilename();

        try {
            File targetFile=new File(filePath);
            FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadPath + "/" + file.getOriginalFilename();
    }
}
```

---

### 编写图片加水印服务

- 编写 **ImageWatermarkService** 服务

里面就一个主要的 `watermarkAdd`方法，代码后面写有详细解释

```java
@Service
public class ImageWatermarkService {

    /**
     * imgFile 图像文件
     * imageFileName 图像文件名
     * uploadPath 服务器上上传文件的相对路径
     * realUploadPath 服务器上上传文件的物理路径
     */
    public String watermarkAdd( File imgFile, String imageFileName, String uploadPath, String realUploadPath ) {

        String imgWithWatermarkFileName = "watermark_" + imageFileName;
        OutputStream os = null;

        try {
            Image image = ImageIO.read(imgFile);

            int width = image.getWidth(null);
            int height = image.getHeight(null);

            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  // ①
            Graphics2D g = bufferedImage.createGraphics();  // ②
            g.drawImage(image, 0, 0, width,height,null);  // ③

            String logoPath = realUploadPath + "/" + Const.LOGO_FILE_NAME;  // 水印图片地址
            File logo = new File(logoPath);        // 读取水印图片
            Image imageLogo = ImageIO.read(logo);

            int markWidth = imageLogo.getWidth(null);    // 水印图片的宽度和高度
            int markHeight = imageLogo.getHeight(null);

            g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Const.ALPHA) );  // 设置水印透明度
            g.rotate(Math.toRadians(-10), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);  // 设置水印图片的旋转度

            int x = Const.X;
            int y = Const.Y;

            int xInterval = Const.X_INTERVAL;
            int yInterval = Const.Y_INTERVAL;

            double count = 1.5;
            while ( x < width*count ) {  // 循环添加多个水印logo
                y = -height / 2;
                while( y < height*count ) {
                    g.drawImage(imageLogo, x, y, null);  // ④
                    y += markHeight + yInterval;
                }
                x += markWidth + xInterval;
            }

            g.dispose();

            os = new FileOutputStream(realUploadPath + "/" + imgWithWatermarkFileName);
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os); // ⑤
            en.encode(bufferedImage); // ⑥

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return uploadPath + "/" + imgWithWatermarkFileName;
    }

}
```

代码思路解释如下：

>可以对照代码中的标示数字和下面的解释进行理解：

① 创建缓存图片
② 创建绘图工具
③ 将原图绘制到缓存图片
④ 将水印logo绘制到缓存图片
⑤ 创建图像编码工具类
⑥ 编码缓存图像生成目标图片

可见思路清晰易懂！

---

### 编写 图片上传/处理 控制器

我们在该控制器代码中将上述的 **图片上传服务** 和 **图片加水印服务** 给用起来：

```
@RestController
public class WatermarkController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private ImageWatermarkService watermarkService;

    @RequestMapping(value = "/watermarktest", method = RequestMethod.POST)
    public ImageInfo watermarkTest( @RequestParam("file") MultipartFile image ) {

        ImageInfo imgInfo = new ImageInfo();

        String uploadPath = "static/images/";  // 服务器上上传文件的相对路径
        String physicalUploadPath = getClass().getClassLoader().getResource(uploadPath).getPath();  // 服务器上上传文件的物理路径

        String imageURL = imageUploadService.uploadImage( image, uploadPath, physicalUploadPath );
        File imageFile = new File(physicalUploadPath + image.getOriginalFilename() );

        String watermarkAddImageURL = watermarkService.watermarkAdd(imageFile, image.getOriginalFilename(), uploadPath, physicalUploadPath);

        imgInfo.setImageUrl(imageURL);
        imgInfo.setLogoImageUrl(watermarkAddImageURL);
        return imgInfo;
    }
}
```

---

### 实际实验与效果展示

我们用 Postman工具来辅助我们发出 `localhost:9999/watermarktest` 请求，进行图片上传的操作：

![Postman发请求进行图片上传](https://upload-images.jianshu.io/upload_images/9824247-2760c53c1360341e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

之后我们再去项目的资源目录下查看上传的**原图** 和 **加完水印后**图片的效果如下：

![原图](https://upload-images.jianshu.io/upload_images/9824247-4bdebb0778a49977.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![加完水印后的图片](https://upload-images.jianshu.io/upload_images/9824247-bd1be3c8e0435fc8.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

喔唷，这水印 Logo是不是打的有点多... 

不过这下终于不用害怕别人对您的图片侵权啦 ！

---

### 后记

> 由于能力有限，若有错误或者不当之处，还请大家批评指正，一起学习交流！

- My Personal Blog：[CodeSheep  程序羊](http://www.codesheep.cn/)

---

## Spring Boot 使用Nacos作为注册中心



### 1、服务提供者

#### 1、pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.9.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.valten</groupId>
    <artifactId>spring-boot-nacos-provider</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-boot-nacos-provider</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.SR3</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>0.9.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 注册中心 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <!-- 打包之后要放在什么位置 -->
                <targetPath>${project.build.directory}/classes</targetPath>
                <!-- 指定resources插件处理哪个目录下的资源文件 -->
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>**/*.*</include>
                </includes>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

#### 2、bootstrap.yml

```yml
spring:
  application:
    name: @project.artifactId@
  cloud:
    # nacos注册中心地址
    nacos:
      discovery:
        server-addr: localhost:8848
server:
  port: 10010
```

#### 3、启动类

``` java
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @RestController
    static class TestController {
        @GetMapping("/test")
        public String hello(@RequestParam String msg) {
            String res = "这是啥?" + msg;
            System.out.println(res);
            return res;
        }
    }
}
```



### 2、服务消费者

#### 1、pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.9.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.valten</groupId>
    <artifactId>spring-boot-nacos-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-boot-nacos-client</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.SR3</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>0.9.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 注册中心 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <!-- 打包之后要放在什么位置 -->
                <targetPath>${project.build.directory}/classes</targetPath>
                <!-- 指定resources插件处理哪个目录下的资源文件 -->
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>**/*.*</include>
                </includes>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

#### 2、bootstrap.yml

```yml
spring:
  application:
    name: @project.artifactId@
  cloud:
    # nacos注册中心地址
    nacos:
      discovery:
        server-addr: localhost:8848
server:
  port: 9000
```

#### 3、启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class NacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class, args);
    }

    @RestController
    static class TestController {

        @Autowired
        LoadBalancerClient loadBalancerClient;

        @GetMapping("/test")
        public String test() {

            /*springcloud common的负载均衡接口,提供服务名实现服务调用*/
            ServiceInstance serviceInstance = loadBalancerClient.choose("spring-boot-nacos-provider");
            String url = serviceInstance.getUri() + "/test?msg=nacos";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            String msg = url + "---- return : " + result;
            System.out.println(msg);
            return msg;
        }
    }
}
```



## Spring Boot 使用Nacos作为配置中心

### 1、pom.xml



```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.9.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.valten</groupId>
    <artifactId>spring-boot-nacos-config</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-boot-nacos-config</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.SR3</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>0.9.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--nacos配置中心-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <!-- 打包之后要放在什么位置 -->
                <targetPath>${project.build.directory}/classes</targetPath>
                <!-- 指定resources插件处理哪个目录下的资源文件 -->
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>**/*.*</include>
                </includes>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```



### 2、bootstrap.yml



```yml
server:
  port: 8003
spring:
  application:
    # 服务名（pom文件<artifactId>xxx</>中的值）
    name: @project.artifactId@
  cloud:
    nacos:
      config:
        # nacos配置中心地址
        server-addr: localhost:8848
        # nacos配置中心命名空间
        namespace: eff92e7b-3625-4211-88b6-8720f60f645c
        # nacos配置中心Group
        group: TEST_GROUP
```



### 3、启动类



``` java
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

    @RestController
    @RefreshScope
    static class Test {

        @Value("${article.title}")
        private String title;

        @GetMapping("/test")
        public String test() {
            return title;
        }
    }
}
```



### 4、Nacos控制台新建配置

**Data ID**就是 **bootstrap.yml**里的`spring.application.name `加上后缀`properties`，当然后缀不只是

`properties`，也可以是其他的



**spring-boot-nacos-config.properties**

```properties
article.title=完美世界 辰东
```



### 5、NacosConfig区分不同环境下的配置:



#### 1、使用Data ID和profiles进行区分

> Nacos配置列表中新建配置，设置**Data ID**
>
> >spring-boot-nacos-config-DEV.properties
> >
> >spring-boot-nacos-config-TESTproperties
>
> 在bootstrap.properties配置文件中加入配置: spring.profiles.active=环境名



#### 2、使用Group区分

> Nacos配置列表中新建配置，设置对应的组名**Group**
>
> > DEV_GROUP
> >
> > TEST_GROUP
>
> 在bootstrap.properties配置文件中加入配置: spring.cloud.nacos.config.group=组名



#### 3、使用Namespace进行环境隔离

> 在Nacos命名空间中新建命名空间
>
> >命名空间名称：TEST
> >
> >命名空间ID：617e78e0-b7f1-4aed-8825-17af6be3f0af 
>
> 在bootstrap.properties配置文件中加入配置: spring.cloud.nacos.config.namespace=命名空间ID



### 6、NacosConfig持久化(MySQL)



#### 1、在Nacos的conf目录中找到数据库初始化sql文件nacos-mysql.sql

#### 2、新建数据库nacosconfig

#### 3、执行初始化sql脚本

#### 4、修改conf文件夹下的application.properties配置文件,添加mysql相关配置

``` properties
spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacosconfig?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=123456
```



