#                                                                                SpringBoot学习笔记

------

# SpringBoot 入门

## 什么是SpringBoot

Spring Boot设计目的是用来**简化新Spring应用的初始搭建以及开发过程**。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。----就是spring boot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，spring boot整合了所有的框架。

## 使用spring boot有什么好处

简单、快速、方便 !

平时如果我们需要搭建一个spring web项目的时候需要怎么做呢？

- 1）配置web.xml，加载spring和spring mvc
- 2）配置数据库连接、配置spring事务
- 3）配置加载配置文件的读取，开启注解
- 4）配置日志文件
- …
- 配置完成之后部署tomcat 调试
- …

现在非常流行微服务，如果我这个项目仅仅只是需要发送一个邮件，如果我的项目仅仅是生产一个积分；我都需要这样折腾一遍!

但是如果使用spring boot呢？
很简单，我仅仅只需要非常少的几个配置就可以迅速方便的搭建起来一套web项目或者是构建一个微服务！

## 快速入门

### maven构建项目

- 1、访问http://start.spring.io/
- 2、选择构建工具Maven Project、Spring Boot版本以及一些工程基本信息，点击“Switch to the full version.”java版本选择1.7，可参考下图所示：

![img](http://www.itmind.net/assets/images/2016/springboot1.png)

![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/springBootInit.png)



- 3、点击Generate Project下载项目压缩包
- 4、解压后，使用eclipse，Import -> Existing Maven Projects -> Next ->选择解压后的文件夹-> Finsh，OK done!

**项目结构介绍**

![img](http://www.itmind.net/assets/images/2016/springboot2.png)

如上图所示，Spring Boot的基础结构共三个文件:

- src/main/java 程序开发以及主程序入口
- src/main/resources 配置文件
- src/test/java 测试程序

另外，spingboot建议的目录结果如下：
root package结构：`com.example.myproject`

```
com
  +- example
    +- myproject
      +- Application.java
      |
      +- domain
      |  +- Customer.java
      |  +- CustomerRepository.java
      |
      +- service
      |  +- CustomerService.java
      |
      +- controller
      |  +- CustomerController.java
      |
```

- 1、**Application.java 建议放到根目录下面,主要用于做一些框架配置**
- 2、domain目录主要用于实体（Entity）与数据访问层（Repository）
- 3、service 层主要是业务类代码
- 4、controller 负责页面访问控制

采用默认配置可以省去很多配置，当然也可以根据自己的喜欢来进行更改
最后，启动Application main方法，至此一个java项目搭建好了！

### 引入web模块

1、pom.xml中添加支持web的模块：

```xml
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
```

在这里引入依赖需要注意：

```xml

	<modelVersion>4.0.0</modelVersion>

	<groupId>com.zhao</groupId>
	<artifactId>springboot</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>springboot</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.0.RELEASE</version>
		<relativePath /> 
	</parent>
   <!-- 
   在pom.xml中引入spring-boot-start-parent,spring官方的解释叫stater poms,它可以提供dependency management,也就是说依赖管理，引入以后在申明其它dependency的时候就不需要version了
   -->

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>


		<!-- 添加支持web的模块 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

	</dependencies>
    
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
        <!-- 
           如果我们要直接Main启动spring，那么以下plugin尽量要添加
         -->
	</build>


// pom.xml中分为 parent，properties，dependencies，build。 我们引入依赖需要放在dependencies中

```

pom.xml文件中默认有两个模块：

```xml
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

`spring-boot-starter` ：核心模块，包括自动配置支持、日志和YAML；

`spring-boot-starter-test` ：测试模块，包括JUnit、Hamcrest、Mockito。

2、编写controller内容：

```java
@RestController
// @RestController返回json字符串的数据，直接可以编写RESTFul的接口；意思就是controller里面的方法都以json格式输出
//其实Spring Boot也是引用了JSON解析包Jackson，自然我们就可以在Demo对象上使用Jackson提供的json属性的注解，对时间进行格式化，对一些字段进行忽略等等
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}  
```

如果不想使用JackJson框架，而想使用其他的Json框架

spring boot默认的json使用起来很陌生，想用自己熟悉的fastjson

​    **引入fastjson依赖库：**

```xml
 <dependency>  
          <groupId>com.alibaba</groupId>  
          <artifactId>fastjson</artifactId>  
          <version>1.2.15</version>  
  </dependency>  
```

官方文档说的1.2.10以后，会有两个方法支持HttpMessageconvert，一个是FastJsonHttpMessageConverter，支持4.2以下的版本，一个是FastJsonHttpMessageConverter4支持4.2以上的版本。这里也就是说：低版本的就不支持了，所以这里最低要求就是1.2.10+。

**配置fastjon**

支持两种方法：

第一种方法就是：
（1）启动类继承extends WebMvcConfigurerAdapter
（2）覆盖方法configureMessageConverters 

```java
** 
 * 
 * @author Angel --守护天使 
 * @version v.0.1 
 * @date 2016年7月29日下午7:06:11 
 */  
@SpringBootApplication  
public class ApiCoreApp  extends WebMvcConfigurerAdapter {    
    @Override  
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {  
        super.configureMessageConverters(converters);  
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();  
        FastJsonConfig fastJsonConfig = new FastJsonConfig();  
        fastJsonConfig.setSerializerFeatures(  
                SerializerFeature.PrettyFormat  
        );  
        fastConverter.setFastJsonConfig(fastJsonConfig); 
        converters.add(fastConverter);  
    }  
} 
```

第二种方法:

（1）在App.java启动类中，注入Bean : HttpMessageConverters

```java
/** 
 * 
 * @author Angel --守护天使 
 * @version v.0.1 
 * @date 2016年7月29日下午7:06:11 
 */  
@SpringBootApplication  
public class ApiCoreApp {   
    @Bean  
    public HttpMessageConverters fastJsonHttpMessageConverters() {  
       FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();  
       FastJsonConfig fastJsonConfig = new FastJsonConfig();  
       fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);  
       fastConverter.setFastJsonConfig(fastJsonConfig);  
       HttpMessageConverter<?> converter = fastConverter;  
       return new HttpMessageConverters(converter);  
    }  
    public static void main(String[] args) {  
       SpringApplication.run(ApiCoreApp.class, args);  
    }  
}
```

 那么这时候在实体类中使用@JSONField(serialize=false)，是不是此字段就不返回了，就说明应用成功，其中JSONField的包路径是：com.alibaba.fastjson.annotation.JSONField。

3、启动主程序，打开浏览器访问http://localhost:8080/hello，就可以看到效果了！

（还有一种启动方式：

右键project – Run as – Maven build – 在Goals里输入spring-boot:run ,然后Apply,最后点击Run。）

无需做什么额外的配置，而且还不需要启动tomcate. 

但是这样有一个问题： 

如果我想同时启动多个项目怎么办？  又不能如在Tomcat service中配置多个端口号？

### 如何同时访问两个SpringBoot项目

如果eclipse里面有两个SpringBoot项目并且controller层的访问路径一样，并且由于没有路径名，不能同时跑两个main方法，若同时跑两个main方法肯定端口号被占用。

#### **解决方法一**

(打包成war包),放入本地的Tomcat中而不是SpringBoot自带的Tomcat中访问

1.去掉Springboot中的tomcat的jar即可

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <artifactId>log4j-over-slf4j</artifactId>
            <groupId>org.slf4j</groupId>
        </exclusion>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

2.加入本地的Tomcat

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```

3.让启动SpringBoot的类继承SpringBootServletInitializer并实现它的configure方法

```java
@SpringBootApplication
// @SpringBootApplication申明让spring boot自动给程序进行必要的配置，等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
public class StartProject extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(StartProject.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return super.configure(builder);
	}
}

```

4.把项目打成war包（还可自定义项目名）

```xml
<build>
    <finalName>Maven_SpringBoot_Demo1</finalName>
     <plugins>
        <plugin>
              <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-war-plugin</artifactId>
               <configuration>
                  //这里叫什么名之后包就叫什么名
                  <warName>bootTest</warName>
              </configuration>
          </plugin> 
       </plugins>
</build>
```



### **如何做单元测试**

src/test/下的测试入口，编写简单的http请求来测试；使用mockmvc进行，利用MockMvcResultHandlers.print()打印出执行结果。

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTests {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
    }

 @Test
    public void getHello() throws Exception {
    	 ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World")));
    	 MvcResult mvcResult = resultActions.andReturn();
         MockHttpServletResponse response = mvcResult.getResponse();
         String contentAsString = response.getContentAsString();

         System.out.println(contentAsString);
    }

}
```

这里可能会有springboot测试的时候status(),content()方法报错

是因为静态导入的原因，只要导入：

```java
import static org.hamcrest.Matchers.equalTo;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
```

### 开发环境的调试

热启动在正常开发项目中常见，虽然平时开发web项目过程中，改动项目启重启总是报错；但springBoot对调试支持很好，修改之后可以实时生效，需要添加以下的配置：

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

该模块在完整的打包环境下运行的时候会被禁用。如果你使用java -jar启动应用或者用一个特定的classloader启动，它会认为这是一个“生产环境”。

我这个例子的pom.xml文件；

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.zhao</groupId>
	<artifactId>springboot</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>springboot</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.0.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>


		<!-- 添加支持web的模块 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!-- 开发调试热启动 -->
		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-devtools</artifactId>
				<optional>true</optional>
			</dependency>
		</dependencies>

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


</project>
```



### Spring Boot热部署

配置springBoot 支持热部署springloaded

```xml
<plugin>  
                     <groupId>org.springframework.boot</groupId>  
                     <artifactId>spring-boot-maven-plugin </artifactId>  
                     <dependencies>   
                       <!--springloaded  hot deploy -->   
                       <dependency>   
                           <groupId>org.springframework</groupId>   
                           <artifactId>springloaded</artifactId>   
                           <version>1.2.4.RELEASE</version>  
                       </dependency>   
                    </dependencies>   
                    <executions>   
                       <execution>   
                           <goals>   
                               <goal>repackage</goal>   
                           </goals>   
                           <configuration>   
                               <classifier>exec</classifier>   
                           </configuration>   
                       </execution>   
                     </executions>  
</plugin>  
```

如果是使用spring-boot:run的话，那么到此配置结束(要想在eclipse中使用spring-boot:run，右键project –> Run as –> Maven build –> 在Goals里输入spring-boot:run ,然后Apply,最后点击Run。 (可以安装spring tools插件方便构建项目))

 如果使用的`run as – java application`的话，那么还需要做一些处理：

把spring-loader-1.2.4.RELEASE.jar下载下来，放到项目的lib目录中，然后把IDEA的run参数里VM参数设置为：

-javaagent:.\lib\springloaded-1.2.4.RELEASE.jar -noverify

然后启动就可以了，这样在run as的时候，也能进行热部署了。并不是所有的代码都支持热部署了



### 全局异常捕捉

在一个项目中的异常我们我们都会统一进行处理的，那么如何进行统一进行处理呢？

新建一个类GlobalDefaultExceptionHandler，

```java
@ControllerAdvice
//在class注解上@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    //方法上注解上@ExceptionHandler(value = Exception.class
    public void defaultErrorHandler(HttpServletRequest req, Exception e)  {
      //打印异常信息：
       e.printStackTrace();
       System.out.println("GlobalDefaultExceptionHandler.defaultErrorHandler()");
// 返回json数据或者String数据：那么需要在方法上加上注解：@ResponseBody添加return即可。
//返回视图：定义一个ModelAndView即可，然后return;定义视图文件(比如：error.html,error.ftl,error.jsp)

  }
}
```

controller方法中抛出了异常,那么在控制台就可以看到我们全局捕捉的异常信息了

### SpringBoot的基本配置

**SpringBoot修改端口号**

Spring boot 默认端口是8080，如果想要进行更改的话，只需要修改applicatoin.properties文件，在配置文件中加入：

```properties
server.port=9090
```

**Spring Boot配置ContextPath**

Spring boot默认是/ ，这样直接通过[http://ip:port/](http://ipport/)就可以访问到index页面，如果要修改为[http://ip:port/path/](http://ipport/)访问的话，那么需要在Application.properties文件中加入server.context-path = /你的path,比如：spring-boot,那么访问地址就是[http://ip:port/spring-boot](http://ipport/) 路径。

```properties
server.context-path=/spring-boot
```

**改变JDK编译版本**

Spring Boot在编译的时候，是有默认JDK版本的，如果我们期望使用我们要的JDK版本的话，那么要怎么配置呢？

这个只需要修改pom.xml文件的`<build> -- <plugins>`加入一个plugin即可。

```xml
<plugin>
   <artifactId>maven-compiler-plugin</artifactId>
   <configuration>
      <source>1.8</source>
      <target>1.8</target>
   </configuration>
</plugin> 
```

添加了plugin之后，需要右键Maven à Update Projects,这时候你可以看到工程根目录下的JRE System Library 版本更改。

**处理静态资源(默认资源映射)**

Spring Boot 默认为我们提供了静态资源处理，使用 `WebMvcAutoConfiguration` 中的配置各种属性。建议使用Spring Boot的默认配置方式，如果需要特殊处理的再通过配置进行修改。

如果想要自己完全控制的  WebMVC，就需要在`@Configuration`注解的配置类上增加`@EnableWebMvc`,

（`@SpringBootApplication` 注解的程序入口类已经包含`@Configuration`）

增加该注解以后WebMvcAutoConfiguration中配置就不会生效，你需要自己来配置需要的每一项。

**默认资源映射**

我们在启动应用的时候，可以在控制台中看到如下信息：

```
2016-01-08 09:29:30.362  INFO 24932 ---[           main]o.s.w.s.handler.SimpleUrlHandlerMapping  : MappedURLpath[/webjars/**]ontohandleroftype[class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2016-01-08 09:29:30.362  INFO 24932 ---[           main]o.s.w.s.handler.SimpleUrlHandlerMapping  : MappedURLpath[/**]ontohandleroftype[class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2016-01-08 09:29:30.437  INFO 24932 ---[           main]o.s.w.s.handler.SimpleUrlHandlerMapping  : MappedURLpath[/**/favicon.ico]ont
```

其中默认配置的 /** 映射到 /static （或/public、/resources、/META-INF/resources） 
其中默认配置的 /webjars/** 映射到 classpath:/META-INF/resources/webjars/ 
<u>上面的 static、public、resources 等目录都在 classpath: 下面（如 src/main/resources/static）</u>。

如果我按如下结构存放相同名称的图片，那么Spring Boot 读取图片的优先级是怎样的呢？ 

如下图： 

![img](http://dl2.iteye.com/upload/attachment/0116/7545/78bfa678-a101-35e4-8e26-9e3cda6a61fc.png)

当我们访问地址 <http://localhost:8080/test.jpg> 的时候，显示哪张图片？

优先级顺序为：META/resources > resources > static > public  

如果我们想访问test2.jpg，请求地址 <http://localhost:8080/img/test2.jpg>

# SpringBoot web综合开发

## web开发

spring boot web开发非常的简单，其中包括常用的json输出、filters、property、log等

### SpringBoot中的注解

#### @Configuration

@Configuration底层是含有@Component ，所以@Configuration 具有和 @Component 的作用。

@Configuration可理解为用spring的时候xml里面的<beans>标签。@Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context" xmlns:jdbc="http://www.springframework.org/schema/jdbc"  
    xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:util="http://www.springframework.org/schema/util" xmlns:task="http://www.springframework.org/schema/task" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-4.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-4.0.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd
        http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task-4.0.xsd" default-lazy-init="false">


</beans>
```

#### @Bean

@Bean可理解为用spring的时候xml里面的<bean>标签。

```java
@Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>，作用为：注册bean对象
package com.dsx.demo;

public class TestBean {
    private String username;
    private String url;
    private String password;
    public void sayHello() {
        System.out.println("TestBean sayHello...");
    }
    public String toString() {
        return "username:" + this.username + ",url:" + this.url + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBean 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestBean 销毁。。。");
    }
}
```

配置类

```java
package com.dsx.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfiguration {
    public TestConfiguration() {
        System.out.println("TestConfiguration容器启动初始化。。。");
    }

    // @Bean注解注册bean,同时可以指定初始化和销毁方法
    @Bean
    @Scope("prototype")
    public TestBean testBean() {
        return new TestBean();
    }
}
```

上述操作相当于实例化TestBean ，并交给spring管理。

注： 
(1)、@Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同； 
(2)、@Bean注解默认作用域为单例singleton作用域(Spring IOC容器中只会存在一个共享的bean实例)，可通过@Scope(“prototype”)设置为原型作用域； 
(3)、既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Repository等注解注册bean（在需要注册的类上加注解），当然需要配置@ComponentScan注解进行自动扫描。

```java
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    @AliasFor("name")
    String[] value() default {};

    @AliasFor("value")
    String[] name() default {};

    Autowire autowire() default Autowire.NO;

    String initMethod() default "";

    String destroyMethod() default "(inferred)";
}
```

使用@Bean注解时，可以配置initMethod()和destoryMethod方法，分别在实例化和销毁的时候执行。

或者使用通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作 。

### json 接口开发

在spring 开发的时候需要我们提供json接口的时候需要做那些配置呢

> 1. 添加 jackjson 等相关jar包
> 2. 配置spring controller扫描
> 3. 对接的方法添加@ResponseBody

spring boot如何做呢，只需要类添加 ` @RestController ` 即可，默认类中的方法都会以json的格式返回

```java
@RestController
public class HelloWorldController {
    @RequestMapping("/getUser")
    public User getUser() {
    	User user=new User();
    	user.setUserName("小明");
    	user.setPassWord("xxxx");
        return user;
    }
}
```

如果我们需要使用页面开发只要使用` @Controller`  结合模板

...............


### 自定义Filter

我们常常在项目中会使用filters用于记录调用日志、排除有XSS威胁的字符、执行权限验证等等。Spring Boot自动添加了OrderedCharacterEncodingFilter和HiddenHttpMethodFilter，并且我们可以自定义Filter。

两个步骤：

> 1. 实现Filter接口，实现Filter方法
> 2. 添加`@Configuration` 注解，将自定义Filter加入过滤链

代码：

```java
@Configuration
public class WebConfiguration {
    
    //加这个是过滤掉静态资源的请求
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    
    //这个方法是将自定义的Filter加入到注册的过滤链
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

### 自定义Property

在web开发的过程中，我经常需要自定义一些配置文件，如何使用呢

SpringBoot中虽然免除了大部分手动配置，但是对于一些特定的情况，还是需要我们进行手动配置的，SpringBoot为我们提供了application.properties配置文件(**公共配置文件**)，让我们可以进行**自定义配置，来对默认的配置进行修改**，以适应具体的生产情况，当然还包括一些第三方的配置。几乎所有配置都可以写到**application.peroperties文件中，这个文件会被SpringBoot自动加载，**免去了我们手动加载的烦恼。实际上，很多时候我们却会自定义配置文件，这些文件就需要我们进行手动加载，SpringBoot是不会自动识别这些文件的

#### 配置文件的格式

SpringBoot可以识别两种格式的配置文件，分别是yml文件与properties文件，我们可以将application.properties文件换成application.yml，这两个文件都可以被SpringBoot自动识别并加载，但是如果是自定义的配置文件，就最好还是使用properties格式的文件，因为SpringBoot中暂时还并未提供手动加载yml格式文件的功能（这里指注解方式）。

pplication.properties配置文件只有放在指定的位置：src/main/resource目录下 才会被springBoot自动的加载。（一般自定义的配置文件也位于此目录之下）

#### 配置文件的加载

加载的意思就是将文件读取到Spring容器之中，更确切的说就是将各个配置项装载到Spring上下文容器之中供随时取用。

#### 配置在application.properties中

```properties
com.neo.title=纯洁的微笑
com.neo.description=分享生活和技术
```



application.properties配置文件是在SpringBoot项目启动的时候被自动加载的，其内部的相关设置会自动覆盖SpringBoot默认的对应设置项，所以的配置项均会保存到Spring容器之中。

自定义的xxx.properties配置文件是不会被SpringBoot自动加载的，需要手动去进行加载，这里的手动加载一般指的是注解的方式加载

##### 加载自定义属性文件的注解

`@PropertySource("classpath:xxx.properties")`，这个注解专门用来加载指定位置的properties文件，Spring暂未提供加载指定位置yml文件的注解

无论对于哪里的properties文件，当我们需要使用其中配置内容的时候，就在当前类的顶部加注该注解，将该配置文件加载到内存，这些配置文件一次加载即可多次使用。

![img](https://img-blog.csdn.net/20180711213426557?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMzUxMDcx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

更通用的情况是新建一个配置类

##### 引用配置文件中内容的方式

**公共配置文件**

**第一种：直接使用@Value注解方式**

公共配置文件application.properties： 直接引用

```java
@RestController
@SpringBootApplication
public class SampleController {
	@Value(value = "${file.upload.stor-path}")
	private String storPath;
 
	@RequestMapping("/")
	String home() {
		return "Hello World! file.upload.stor-path为：" + storPath;
	}

}
```

**第二种：使用@ConfigurationProperties将配置属性注入到Bean中**

```java
@Component //为了这个JavaBean可以被SpringBoot项目启动时候被扫描到并加载到Spring容器之中
@ConfigurationProperties(prefix="com.neo") //这里也是必要的，前缀顾名思义是配置属性的前缀
public class NeoProperties {
	private String title;
	private String description;
	// 省略getter settet方法
   。。。。。。。。。。。。
}

//该类被调用
public class SpringbootApplicationTests {
	@Autowired
	NeoProperties np;
	@Test
	public void contextLoads() {
		System.out.println(np.getTitle()+"======"+np.getDescription());
	}
}

//这里需要注意： 如果我们没有为NeoProperties这个javaBean加上@Component注解，那我们就要在使用这个javaBean的入口类处加上@EnableConfigurationProperties(NeoProperties.class)，这里NeoProperties.class 必须要指定。

Spring Boot 在properties文件中支持使用SpEL表达式，可以进行校验（校验注解使用的是javax.validation）等操作。
例如以下：
（1）随机数：
          test.int.random=${random.int}
（2）数组注入
          test.int.random[0]=${random.int}
          test.int.random[1]=${random.int}
（3）校验
         @NotNull
         private String storPath;

--------------------- 
```

#### 外部化配置

自己定义了外部配置文件zhao.properties

```properties
com.zhao.name=\u8D75\u500D\u52C7(赵XX)
com.zhao.age=24
```

**第一种：直接使用@Value注解方式**

```java
@RunWith(SpringRunner.class) 
@SpringBootTest
@PropertySource("classpath:zhao.properties")
public class SpringbootApplicationTests {
	@Value("${com.zhao.name}")
	private String name;
	@Value("${com.zhao.age}")
	private String age;
	@Test
	public void contextProperty() {
		System.out.println(name+"======"+age);
		
	}

}
//报错：Could not resolve placeholder 'com.zhao.name' in value "${com.zhao.name}" 不能解析占位符
//原因：这里这个错误经过验证，是在junit中运行的原因，可能是没有真正加载完配置文件，导致不能解析

//在controller使用这种方式就完全没有问题
@RestController
@PropertySource("classpath:zhao.properties")
public class HelloWorldController {
	
	@Value("${com.zhao.name}")
    private String name;
    
    @Value("${com.zhao.age}")
    private String age;
	
    @RequestMapping("/hello2")
	public String hello() {
		return name+age;
	}	
}

//这里能正常的输出
```

**第二种：使用自定义配置类**

```java

@Component
@ConfigurationProperties(prefix = "com.zhao")
public class UserProperty {

	private String name;
	
	private String age;
    
   // 省略getter settet方法
  
//===//该类被调用======
@RestController
@PropertySource("classpath:zhao.properties") //在这里加载配置文件
public class HelloWorldController {
	@Autowired
	UserProperty np;
    @RequestMapping("/hello2")
	public String hello() {
		return np.getName();
	}
}   

```

#### SpringBoot yml 配置

在 spring boot 中，有两种配置文件，一种是application.properties,另一种是application.yml,两种都可以配置spring boot 项目中的一些变量的定义，参数的设置等。

**两者的区别:**

application.properties 配置文件在写的时候要写完整，如：

```properties
spring.profiles.active=dev
spring.datasource.data-username=root
spring.datasource.data-password=root
```

在yml 文件中配置的话，写法如下:

```properties
spring:
  profiles:
    active: prod
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/test
    username: root
    password: root

#yml 文件在写的时候层次感强
```

2. 在项目中配置多套环境的配置方法:
    因为现在一个项目有好多环境，开发环境，测试环境，准生产环境，生产环境，每个环境的参数不同，所以我们就**可以把每个环境的参数配置到yml文件中**，这样在想用哪个环境的时候只需要在主配置文件中将用的配置文件写上就行如下：

  ```properties
  spring:
    profiles:
       active: prod 
  #这行配置在application.yml 文件中，意思是当前起作用的配置文件是application_prod.yml,其他的配置文件命名为 application_dev.yml,application_bat.yml等。
  ```

3. 项目启动的时候也可以设置 Java -jar xxxxxx.jar spring.profiles.active=prod 也可以这样启动设置配置文件，但是这只是用于开发和测试。

4. 配置文件数据的读取：

  ```properties
  比如我在文件中配置了一个 
  massage:
    data:
       name: qibaoyi
  我在类中想要获取他 需要这样去写：
  @Value("${message.data.name}")
  private String name;
  后面你取到变量name 的值就是配置文件中配置的值。
  ```

5. **需要注意一点，配置文件中参数的写法：name: qibaoyi中间是有一个空格的**

### log配置

默认情况下，Spring Boot会用Logback来记录日志，并用INFO级别输出到控制台。

**默认日志 Logback ：**

默认情况下，Spring Boot会用Logback来记录日志，并用INFO级别输出到控制台。

![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/springBoot_log.png)

日志输出内容元素具体如下：

- 时间日期：精确到毫秒
- 日志级别：ERROR, WARN, INFO, DEBUG or TRACE
- 进程ID
- 分隔符：— 标识实际日志的开始
- 线程名：方括号括起来（可能会截断控制台输出）
- Logger名：通常使用源代码的类名
- 日志内容

**添加日志依赖**

假如maven依赖中添加了 spring-boot-starter-logging ：

```xml
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

实际开发中不需要直接添加该依赖。 spring-boot-starter 其中包含了 spring-boot-starter-logging ，该依赖内容就是 Spring Boot 默认的日志框架 logback 。

工程中有用到了 Thymeleaf ，而 Thymeleaf 依赖包含了 spring-boot-starter ，最终我只要引入 Thymeleaf 即可。

```xml
`<dependency>`` ``<groupId>org.springframework.boot</groupId>`` ``<artifactId>spring-boot-starter-thymeleaf</artifactId>``</dependency>`
```

**控制台输出**

日志级别从低到高分为：

```properties
TRACE < DEBUG < INFO < WARN < ERROR < FATAL 
```

如果设置为 WARN ，则低于 WARN 的信息都不会输出。

Spring Boot 中默认配置 ERROR 、 WARN 和 INFO 级别的日志输出到控制台。

**文件输出**

默认情况下，Spring Boot将日志输出到控制台，不会写到日志文件。

如果要编写除控制台输出之外的日志文件，则需在 application.properties 中设置 logging.file 或 logging.path 属性。

- logging.file ，设置文件，可以是绝对路径，也可以是相对路径。如： logging.file=my.log
- logging.path ，设置目录，会在该目录下创建 spring.log 文件，并写入日志内容，如： logging.path=/var/log

如果只配置 logging.file ，会在项目的当前路径下生成一个 xxx.log 日志文件。

如果只配置 logging.path ，在 /var/log 文件夹生成一个日志文件为 spring.log

注：二者不能同时使用，如若同时使用，则只有 logging.file 生效

默认情况下，日志文件的大小达到 10MB 时会切分一次，产生新的日志文件，默认级别为： ERROR、WARN、INFO

配置输出的地址和输出级别

```properties
logging.path=/user/local/log
logging.level.com.favorites=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=ERROR
```

path为本机的log地址，`logging.level ` 后面可以根据包路径配置不同资源的log级别

## 数据库操作 

任何平台都离不了数据库的操作，在spring boot中如何接入数据库

大体步骤：

```
(1) 在application.properties中加入datasouce的配置
spring.datasource.url = jdbc:mysql://localhost:3306/test
spring.datasource.username = root
spring.datasource.password = root
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.max-active=20
spring.datasource.max-idle=8
spring.datasource.min-idle=8
spring.datasource.initial-size=10
(2) 在pom.xml加入mysql的依赖。
<dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
</dependency>
(3) 获取DataSouce的Connection进行测试。

```

这里重点讲mysql、spring data jpa的使用，jpa是利用Hibernate生成各种自动化的sql，如果只是简单的增删改查，基本上不用手写了，spring内部已经封装实现了。

什么是JPA:

JPA全称Java Persistence API.JPA通过JDK 5.0**注解或XML描述对象－关系表的映射关系**，并将运行期的实体[对象持久化](http://baike.baidu.com/view/402359.htm)到数据库中

简单介绍一下如何在spring boot中使用

### 1、添加相jar包

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
     <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
```

### 2、添加配置文件

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.show-sql= true
```

其实这个hibernate.hbm2ddl.auto参数的作用主要用于：自动创建|更新|验证数据库表结构,有四个值：

> 1. create： 每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
> 2. create-drop ：每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
> 3. update：**最常用**的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），以后加载hibernate时根据 model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
> 4. validate ：每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。

`dialect` 主要是指定生成表名的存储引擎为InneoDB
`show-sql` 是否打印出自动生产的SQL，方便调试的时候查看

------



```
总体步骤：
(1)   创建实体类Demo。
(2)   创建jpa repository类操作持久化。
(3)   创建service类。
(4)   创建restful请求类。
(5)   测试
```

------



### 3、添加实体类和Dao

```java
@Entity //加入这个注解，Demo就会进行持久化了， 建立与数据库表的映射关系 ，还可以使用@Table注解配置持久化的表名
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
	private String regTime；
	//省略getter settet方法、构造方法
}
```



DAO只要继承JpaRepository类就可以，几乎可以不用写方法。

有一个特别功能非常赞，就是可以根据方法名来自动的生产SQL，比如`findByUserName` 会自动生产一个以 `userName` 为参数的查询方法，比如 `findAlll` 自动会查询表里面的所有数据，比如自动分页等等。(这里的Dao层类似于mvc中的serviceImpl 层)

**Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列**

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByUserNameOrEmail(String username, String email);
```

### 4、测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() throws Exception {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG); 
		String formattedDate = dateFormat.format(date);	
		userRepository.save(new User("aa1", "aa@126.com", "aa", "aa123456",formattedDate));
		userRepository.save(new User("bb2", "bb@126.com", "bb", "bb123456",formattedDate));
		userRepository.save(new User("cc3", "cc@126.com", "cc", "cc123456",formattedDate));

		Assert.assertEquals(9, userRepository.findAll().size());
		Assert.assertEquals("bb", userRepository.findByUserNameOrEmail("bb", "cc@126.com").getNickName());
		userRepository.delete(userRepository.findByUserName("aa1"));
	}

}
```

 spring data jpa 还有很多功能，比如封装好的分页，可以自己定义SQL，主从分离等等

## thymeleaf模板

**Spring boot 推荐使用thymeleaf模板来代替jsp**。

### Thymeleaf 介绍

Thymeleaf是类似于FreeMarker的模板引擎，是springBoot提供的可以替代jsp的模板。

与其它模板引擎相比与其它模板引擎相比：

- 1.**Thymeleaf 在有网络和无网络的环境下皆可运行**，可以不启动项目静态显示。这是由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。
- 2.Thymeleaf 可以直接套用模板实现JSTL、 OGNL表达式效果，同时开发人员也可以扩展和创建自定义的方言。
- 3.Thymeleaf 提供spring标准方言和一个与 SpringMVC 完美集成的可选模块，可以快速的实现表单绑定、属性编辑器、国际化等功能。

下面的代码示例分别使用Velocity、FreeMarker与Thymeleaf打印出一条消息：

```html
Velocity: <p>$message</p>
FreeMarker: <p>${message}</p>
Thymeleaf: <p th:text="${message}">Hello World!</p>
```

** 注意，由于Thymeleaf使用了XML DOM解析器，因此它并不适合于处理大规模的XML文件。**

### 标准表达式语法

它们分为四类：

- 1.变量表达式
- 2.选择或星号表达式
- 3.文字国际化表达式
- 4.URL表达式

#### 变量表达式

**变量表达式即OGNL表达式或Spring EL表达式**
`${session.user.name}`

它们将以HTML标签的一个属性来表示：

```html
<span th:text="${book.author.name}">  
<li th:each="book : ${books}">  
```

#### 选择(星号)表达式

选择表达式很像变量表达式，不过**它们用一个预先选择的对象来代替上下文变量容器(map)**来执行，如下：
` *{customer.name} `

被指定的object由th:object属性定义：

```html
    <div th:object="${book}">  
      ...  
      <span th:text="*{title}">...</span>  
      ...  
    </div>  
```

#### 文字国际化表达式

文字国际化表达式允许我们从一个外部文件获取区域文字信息(.properties)，用Key索引Value，还可以提供一组参数(可选).

```properties
    #{main.title}  
    #{message.entrycreated(${entryId})}  
```

可以在模板文件中找到这样的表达式代码：

```html
    <table>  
      ...  
      <th th:text="#{header.address.city}">...</th>  
      <th th:text="#{header.address.country}">...</th>  
      ...  
    </table>  
```

#### URL表达式

URL表达式指的是把一个有用的上下文或回话信息添加到URL，这个过程经常被叫做URL重写。
` @{/order/list} `
URL还可以设置参数：
` @{/order/details(id=${orderId})} ` 
相对路径：
` @{../documents/report} `

让我们看这些表达式：

```html
    <form th:action="@{/createOrder}">  
    <a href="main.html" th:href="@{/main}">
```

#### 变量表达式和星号表达有什么区别吗？

如果不考虑上下文的情况下，两者没有区别；星号语法 评估在选定对象上表达，而不是整个上下文 
什么是选定对象？就是**父标签的值**，如下：

```html
  <div th:object="${session.user}">
    <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
    <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
    <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
  </div>
```

等价于：

```html
  <div th:object="${session.user}">
	  <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p>
	  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
	  <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p>
  </div>
```

美元符号和星号语法可以混合使用：

```html
  <div th:object="${session.user}">
	  <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
  	  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
      <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
  </div>
```

### 表达式支持的语法

字面（Literals） 

- 文本文字（Text literals）: `'one text', 'Another one!',…`
- 数字文本（Number literals）: `0, 34, 3.0, 12.3,…`
- 布尔文本（Boolean literals）: `true, false`
- 空（Null literal）: `null`
- 文字标记（Literal tokens）: `one, sometext, main,…`

文本操作（Text operations）

- 字符串连接(String concatenation): `+`
- 文本替换（Literal substitutions）: `|The name is ${name}|`

算术运算（Arithmetic operations）

- 二元运算符（Binary operators）: `+, -, *, /, %`
- 减号（单目运算符）Minus sign (unary operator): `-`

布尔操作（Boolean operations）

- 二元运算符（Binary operators）:`and, or`
- 布尔否定（一元运算符）Boolean negation (unary operator):`!, not`

比较和等价(Comparisons and equality)

- 比较（Comparators）: `>, <, >=, <= (gt, lt, ge, le)`
- 等值运算符（Equality operators）:`==, != (eq, ne)`

条件运算符（Conditional operators）

- If-then: `(if) ? (then)`
- If-then-else: `(if) ? (then) : (else)`
- Default: (value) ?: `(defaultvalue)`

所有这些特征可以被组合并嵌套：

```
'User is of type ' + (${user.isAdmin()} ? 'Administrator' : (${user.type} ?: 'Unknown'))
```



### 常用th标签都有那些？

| 关键字      | 功能介绍                                     | 案例                                                         |
| ----------- | -------------------------------------------- | ------------------------------------------------------------ |
| th:id       | 替换id                                       | ` <input th:id="'xxx' + ${collect.id}"/> `                   |
| th:text     | 文本替换                                     | `<p th:text="${collect.description}">description</p>`        |
| th:utext    | 支持html的文本替换                           | `<p th:utext="${htmlcontent}">conten</p>`                    |
| th:object   | 替换对象                                     | `<div th:object="${session.user}"> `                         |
| th:value    | 属性赋值                                     | `<input th:value="${user.name}" /> `                         |
| th:with     | 变量赋值运算                                 | `<div th:with="isEven=${prodStat.count}%2==0"></div> `       |
| th:style    | 设置样式                                     | `th:style="'display:' + @{(${sitrue} ? 'none' : 'inline-block')} + ''" ` |
| th:onclick  | 点击事件                                     | `th:onclick="'getCollect()'" `                               |
| th:each     | 属性赋值                                     | `tr th:each="user,userStat:${users}"> `                      |
| th:if       | 判断条件                                     | ` <a th:if="${userId == collect.userId}" > `                 |
| th:unless   | 和th:if判断相反                              | `<a th:href="@{/login}" th:unless=${session.user != null}>Login</a>` |
| th:href     | 链接地址                                     | `<a th:href="@{/login}" th:unless=${session.user != null}>Login</a> /> ` |
| th:switch   | 多路选择 配合th:case 使用                    | `<div th:switch="${user.role}"> `                            |
| th:case     | th:switch的一个分支                          | ` <p th:case="'admin'">User is an administrator</p>`         |
| th:fragment | 布局标签，定义一个代码片段，方便其它地方引用 | `<div th:fragment="alert">`                                  |
| th:include  | 布局标签，替换内容到引入的文件               | `<head th:include="layout :: htmlhead" th:with="title='xx'"></head> /> ` |
| th:replace  | 布局标签，替换整个标签到引入的文件           | `<div th:replace="fragments/header :: title"></div> `        |
| th:selected | selected选择框 选中                          | `th:selected="(${xxx.id} == ${configObj.dd})"`               |
| th:src      | 图片类地址引入                               | `<img class="img-responsive" alt="App Logo" th:src="@{/img/logo.png}" /> ` |
| th:inline   | 定义js脚本可以使用变量                       | `<script type="text/javascript" th:inline="javascript">`     |
| th:action   | 表单提交的地址                               | `<form action="subscribe.html" th:action="@{/subscribe}">`   |
| th:remove   | 删除某个属性                                 | `<tr th:remove="all"> 1.all:删除包含标签和所有的孩子。2.body:不包含标记删除,但删除其所有的孩子。3.tag:包含标记的删除,但不删除它的孩子。4.all-but-first:删除所有包含标签的孩子,除了第一个。5.none:什么也不做。这个值是有用的动态评估。` |
| th:attr     | 设置标签属性，多个属性可以用逗号分隔         | 比如 `th:attr="src=@{/image/aa.jpg},title=#{logo}"`，此标签不太优雅，一般用的比较少。 |

还有非常多的标签，这里只列出最常用的几个,由于一个标签内可以包含多个th:x属性，其生效的优先级顺序为:`include,each,if/unless/switch/case,with,attr/attrprepend/attrappend,value/href,src ,etc,text/utext,fragment,remove。 `

### 几种常用的使用方法

##### 1、赋值、字符串拼接

```html
 <p  th:text="${collect.description}">description</p>
 <span th:text="'Welcome to our application, ' + ${user.name} + '!'">
```

字符串拼接另外一种简洁的写法

```html
<span th:text="|Welcome to our application, ${user.name}!|">
```

##### 2、条件判断 If/Unless

Thymeleaf中使用th:if和th:unless属性进行条件判断，下面的例子中，`<a>`标签只有在`th:if`中条件成立时才显示：

```html
<a th:if="${myself=='yes'}" > </i> </a>
<a th:unless=${session.user != null} th:href="@{/login}" >Login</a>
```

**th:unless**于th:if恰好相反，只有**表达式中的条件不成立**，才会显示其内容。

也可以使用 `(if) ? (then) : (else)` 这种语法来判断显示的内容

##### 3、for 循环  each

```html
  <tr  th:each="collect,iterStat : ${collects}"> 
     <th scope="row" th:text="${collect.id}">1</th>
     <td >
        <img th:src="${collect.webLogo}"/>
     </td>
     <td th:text="${collect.url}">Mark</td>
     <td th:text="${collect.title}">Otto</td>
     <td th:text="${collect.description}">@mdo</td>
     <td th:text="${terStat.index}">index</td>
 </tr>
```

iterStat称作 状态变量，属性有：

- index:当前迭代对象的index（从0开始计算）
- count: 当前迭代对象的index(从1开始计算)
- size:被迭代对象的大小
- current:当前迭代变量
- even/odd:布尔值，当前循环是否是偶数/奇数（从0开始计算）
- first:布尔值，当前循环是否是第一个
- last:布尔值，当前循环是否是最后一个

##### 4、URL

URL在Web应用模板中占据着十分重要的地位，需要特别注意的是Thymeleaf对于URL的处理是通过语法`@{…}`来处理的。 如果需要Thymeleaf对URL进行渲染，那么务必使用th:href，th:src等属性

```html
<!-- Will produce 'http://localhost:8080/standard/unread' (plus rewriting) -->
 <a  th:href="@{/standard/{type}(type=${type})}">view</a>

<!-- Will produce '/gtvg/order/3/details' (plus rewriting) -->
<a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>

//最后的(orderId=${o.id})表示将括号内的内容作为URL参数处理
//@{...}表达式中可以通过{orderId}访问Context中的orderId变量
//@{/order}是Context相关的相对路径，在渲染时会自动添加上当前Web应用的Context名字，假设context名字为app，那么结果应该是/app/order
```

设置背景

```html
<div th:style="'background:url(' + @{/<path-to-image>} + ');'"></div>
```

根据属性值改变背景

```html
 <div class="media-object resource-card-image"  th:style="'background:url(' + @{(${collect.webLogo}=='' ? 'img/favicon.png' : ${collect.webLogo})} + ')'" ></div>
```

##### 5、内联js

内联文本：[[…]]内联文本的表示方式，使用时，必须先用`th:inline=”text/javascript/none`”激活，th:inline可以在父级标签内使用，甚至作为body的标签。。

```js
<script th:inline="javascript">
/*<![CDATA[*/
...
var username = /*[[${sesion.user.name}]]*/ 'Sebastian';
var size = /*[[${size}]]*/ 0;
...
/*]]>*/
</script>
```

js附加代码：

```js
/*[+
var msg = 'This is a working application';
+]*/
```

js移除代码：

```js
/*[- */
var msg = 'This is a non-working template';
/* -]*/
```

##### 6、内嵌变量

为了模板更加易用，Thymeleaf还提供了一系列Utility对象（内置于Context中），可以通过#直接访问：

- dates ： *java.util.Date的功能方法类。*
- calendars : *类似#dates，面向java.util.Calendar*
- numbers : *格式化数字的功能方法类*
- strings : *字符串对象的功能类，contains,startWiths,prepending/appending等等。*
- objects: *对objects的功能类操作。*
- bools: *对布尔值求值的功能方法。*
- arrays：*对数组的功能类方法。*
- lists: *对lists功能类方法*
- sets
- maps
  …

下面用一段代码来举例一些常用的方法：

dates

```java
/*
 * Format date with the specified pattern
 * Also works with arrays, lists or sets
 */
${#dates.format(date, 'dd/MMM/yyyy HH:mm')}
${#dates.arrayFormat(datesArray, 'dd/MMM/yyyy HH:mm')}
${#dates.listFormat(datesList, 'dd/MMM/yyyy HH:mm')}
${#dates.setFormat(datesSet, 'dd/MMM/yyyy HH:mm')}

/*
 * Create a date (java.util.Date) object for the current date and time
 */
${#dates.createNow()}

/*
 * Create a date (java.util.Date) object for the current date (time set to 00:00)
 */
${#dates.createToday()}
```

##### strings

```java
/*
 * Check whether a String is empty (or null). Performs a trim() operation before check
 * Also works with arrays, lists or sets
 */
${#strings.isEmpty(name)}
${#strings.arrayIsEmpty(nameArr)}
${#strings.listIsEmpty(nameList)}
${#strings.setIsEmpty(nameSet)}

/*
 * Check whether a String starts or ends with a fragment
 * Also works with arrays, lists or sets
 */
${#strings.startsWith(name,'Don')}                  // also array*, list* and set*
${#strings.endsWith(name,endingFragment)}           // also array*, list* and set*

/*
 * Compute length
 * Also works with arrays, lists or sets
 */
${#strings.length(str)}

/*
 * Null-safe comparison and concatenation
 */
${#strings.equals(str)}
${#strings.equalsIgnoreCase(str)}
${#strings.concat(str)}
${#strings.concatReplaceNulls(str)}

/*
 * Random
 */
${#strings.randomAlphanumeric(count)}
```

## 使用thymeleaf布局

使用thymeleaf布局非常的方便

定义代码片段

```html
<footer th:fragment="copy"> 
&copy; 2016
</footer>
```

在页面任何地方引入：

```html
<body> 
  <div th:include="footer :: copy"></div>
  <div th:replace="footer :: copy"></div>
 </body>
```

th:include 和 th:replace区别，include只是加载，replace是替换

返回的HTML如下：

```html
<body> 
   <div> &copy; 2016 </div> 
  <footer>&copy; 2016 </footer> 
</body>
```

常用的后台页面布局，将整个页面分为头部，尾部、菜单栏、隐藏栏，点击菜单只改变content区域的页面

```html
<body class="layout-fixed">
  <div th:fragment="navbar"  class="wrapper"  role="navigation">
	<div th:replace="fragments/header :: header">Header</div>
	<div th:replace="fragments/left :: left">left</div>
	<div th:replace="fragments/sidebar :: sidebar">sidebar</div>
	<div layout:fragment="content" id="content" ></div>
	<div th:replace="fragments/footer :: footer">footer</div>
  </div>
</body>
```

任何页面想使用这样的布局值只需要替换中见的 content模块即可

```html
 <html xmlns:th="http://www.thymeleaf.org" layout:decorator="layout">
   <body>
      <section layout:fragment="content">
    ...
```

也可以在引用模版的时候传参

```html
<head th:include="layout :: htmlhead" th:with="title='Hello'"></head>
```

layout 是文件地址，如果有文件夹可以这样写 fileName/layout:htmlhead
htmlhead 是指定义的代码片段 如 `th:fragment="copy"`

## 关闭thymeleaf缓存

Thymeleaf缓存在开发过程中，肯定是不行的，那么就要在开发的时候把缓存关闭，需要在application.properties进行配置

```properties
spring.thymeleaf.cache=false
```

## 使用freemarker

和使用thymeleaf很类似。除最基础的模板编写风格以外，不同的是：

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

引入配置：

```properties
##FREEMARKER (FreeMarkerAutoConfiguration)

########################################################

spring.freemarker.allow-request-override=false

spring.freemarker.cache=true

spring.freemarker.check-template-location=true

spring.freemarker.charset=UTF-8

spring.freemarker.content-type=text/html

spring.freemarker.expose-request-attributes=false

spring.freemarker.expose-session-attributes=false

spring.freemarker.expose-spring-macro-helpers=false

#spring.freemarker.prefix=

#spring.freemarker.request-context-attribute=

#spring.freemarker.settings.*=

#spring.freemarker.suffix=.ftl

#spring.freemarker.template-loader-path=classpath:/templates/#comma-separatedlist

#spring.freemarker.view-names= #whitelistofviewnamesthatcanberesolved
```

## 使用jsp

springBoot并不推荐使用jsp,如果硬是要使用

依赖： 额外引入

```xml
 <!-- servlet 依赖. -->  
       <dependency>  
           <groupId>javax.servlet</groupId>  
           <artifactId>javax.servlet-api</artifactId>  
           <scope>provided</scope>  
       </dependency>  
        
       <!--  
           JSTL（JSP Standard Tag Library，JSP标准标签库)是一个不断完善的开放源代码的JSP标签库，是由apache的jakarta小组来维护的。JSTL只能运行在支持JSP1.2和Servlet2.3规范的容器上，如tomcat 4.x。在JSP 2.0中也是作为标准支持的。  
            
           不然报异常信息：  
           javax.servlet.ServletException: Circular view path [/helloJsp]: would dispatch back to the current handler URL [/helloJsp] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)  
              
        -->  
       <dependency>  
           <groupId>javax.servlet</groupId>  
           <artifactId>jstl</artifactId>  
       </dependency>  
        
       <!-- tomcat 的支持.-->  
       <dependency>  
           <groupId>org.springframework.boot</groupId>  
           <artifactId>spring-boot-starter-tomcat</artifactId>  
           <scope>provided</scope>  
       </dependency>  
       <dependency>  
           <groupId>org.apache.tomcat.embed</groupId>  
           <artifactId>tomcat-embed-jasper</artifactId>  
           <scope>provided</scope>  
       </dependency>  
```

jdk编译版本：

```xml
<build>
       <finalName>spring-boot-jsp</finalName>
       <plugins>
           <plugin>
              <artifactId>maven-compiler-plugin</artifactId>
              <configuration>
                  <source>1.8</source>
                  <target>1.8</target>
              </configuration>
           </plugin>
       </plugins>
    </build>
```

properties：想使用JSP需要配置application.properties

```properties
# 页面默认前缀目录
spring.mvc.view.prefix=/WEB-INF/jsp/
# 响应页面默认后缀
spring.mvc.view.suffix=.jsp
```

## Gradle 构建工具

spring 项目建议使用Gradle进行构建项目，相比maven来讲 Gradle更简洁，而且gradle更适合大型复杂项目的构建。gradle吸收了maven和ant的特点而来，不过目前maven仍然是Java界的主流。

一个使用gradle配置的项目

```properties
buildscript {
    repositories {
        maven { url "http://repo.spring.io/libs-snapshot" }
        mavenLocal()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.3.6.RELEASE")
    }
}

apply plugin: 'java'  //添加 Java 插件, 表明这是一个 Java 项目
apply plugin: 'spring-boot' //添加 Spring-boot支持
apply plugin: 'war'  //添加 War 插件, 可以导出 War 包
apply plugin: 'eclipse' //添加 Eclipse 插件, 添加 Eclipse IDE 支持, Intellij Idea 为 "idea"

war {
    baseName = 'favorites'
    version =  '0.1.0'
}

sourceCompatibility = 1.7  //最低兼容版本 JDK1.7
targetCompatibility = 1.7  //目标兼容版本 JDK1.7

repositories {     //  Maven 仓库
    mavenLocal()        //使用本地仓库
    mavenCentral()      //使用中央仓库
    maven { url "http://repo.spring.io/libs-snapshot" } //使用远程仓库
}
 
dependencies {   // 各种 依赖的jar包
    compile("org.springframework.boot:spring-boot-starter-web:1.3.6.RELEASE")
    compile("org.springframework.boot:spring-boot-starter-thymeleaf:1.3.6.RELEASE")
    compile("org.springframework.boot:spring-boot-starter-data-jpa:1.3.6.RELEASE")
    compile group: 'mysql', name: 'mysql-connector-java', version: '5.1.6'
    compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.4'
    compile("org.springframework.boot:spring-boot-devtools:1.3.6.RELEASE")
    compile("org.springframework.boot:spring-boot-starter-test:1.3.6.RELEASE")
    compile 'org.webjars.bower:bootstrap:3.3.6'
	compile 'org.webjars.bower:jquery:2.2.4'
    compile("org.webjars:vue:1.0.24")
	compile 'org.webjars.bower:vue-resource:0.7.0'

}

bootRun {
    addResources = true
}
```

## WebJars

WebJars是一个很神奇的东西，可以让大家以jar包的形式来使用前端的各种框架、组件。

### 什么是WebJars

什么是WebJars？WebJars是将客户端（浏览器）资源（JavaScript，Css等）打成jar包文件，以对资源进行统一依赖管理。WebJars的jar包部署在Maven中央仓库上。

### 为什么使用

我们在开发Java web项目的时候会使用像Maven，Gradle等构建工具以实现对jar包版本依赖管理，以及项目的自动化管理，但是对于JavaScript，Css等前端资源包，我们只能采用拷贝到webapp下的方式，这样做就无法对这些资源进行依赖管理。那么WebJars就提供给我们这些前端资源的jar包形势，我们就可以进行依赖管理。

### 如何使用

1、 [WebJars主官网](http://www.webjars.org/bower) 查找对于的组件，比如Vuejs

```xml
<dependency>
    <groupId>org.webjars.bower</groupId>
    <artifactId>vue</artifactId>
    <version>1.0.21</version>
</dependency>
```

2、页面引入

```html
<link th:href="@{/webjars/bootstrap/3.3.6/dist/css/bootstrap.css}" rel="stylesheet"></link>
```

就可以正常使用了！

## SpringBoot中的定时任务

Spring Boot 中定时任务使用非常简单:

```java
//定时任务
@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        System.out.println(">>>>>>>>> SchedulingConfig.scheduler()");
    }
}

```

## Spring Boot使用Druid和监控配置 （配置文件）

什么是Druid？

Druid是Java语言中最好的数据库连接池，并且能够提供强大的监控和扩展功能(选择使用它的原因)。

 Spring Boot 中配置使用Druid:

(1)添加Maven依赖 (或jar包)

```xml
<dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.18</version>
</dependency>
```

(2)、配置数据源相关信息

```properties
# 数据库访问配置
# 主数据源，默认的
# spring.datasource.type=com.alibaba.druid.pool.DruidDataSource 已经废弃了
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=123456
# 下面为连接池的补充设置，应用到上面所有数据源中
# 初始化大小，最小，最大
spring.datasource.initialSize=5
spring.datasource.minIdle=5
spring.datasource.maxActive=20
# 配置获取连接等待超时的时间
spring.datasource.maxWait=60000
spring.datasource.timeBetweenEvictionRunsMillis=60000
# 配置一个连接在池中最小生存的时间，单位是毫秒
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.validationQuery=SELECT 1 FROM DUAL
spring.datasource.testWhileIdle=true
spring.datasource.testOnBorrow=false
spring.datasource.testOnReturn=false
# 打开PSCache，并且指定每个连接上PSCache的大小
spring.datasource.poolPreparedStatements=true
spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
# 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
spring.datasource.filters=stat,wall,log4j
# 通过connectProperties属性来打开mergeSql功能；慢SQL记录
spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
# 合并多个DruidDataSource的监控数据
#spring.datasource.useGlobalDataSourceStat=true
```

(3) 配置监控统计功能

#### 配置Servlet---DruidStatViewServlet

如下是在SpringBoot项目中基于注解的配置：

```java
//druid数据源状态监控.
@WebServlet(urlPatterns="/druid/*",
initParams={
    @WebInitParam(name="allow",value="192.168.1.72,127.0.0.1"),
    // IP白名单 (没有配置或者为空，则允许所有访问)
    @WebInitParam(name="deny",value="192.168.1.73"),// IP黑名单 (存在共同时，deny优先于allow)
    @WebInitParam(name="loginUsername",value="admin"),// 用户名
    @WebInitParam(name="loginPassword",value="123456"),// 密码
    @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
       }
)
public class DruidStatViewServlet extends StatViewServlet{
    privatestaticfinallongserialVersionUID = 1L;  
}
```

#### 配置Filter----DruidStatFilter 

```java
//druid过滤器
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
    initParams={            @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        }
)

public class DruidStatFilter extends WebStatFilter{
}
```

最后在controller类上加上注解：`@ServletComponentScan`是的spring能够扫描到我们自己编写的servlet和filter。

注意不要忘记在 启动类上添加 @ServletComponentScan 注解，不然就是404了。

然后启动项目后访问 <http://127.0.0.1:8080/druid/index.html> 即可查看数据源及SQL统计等。  

(4)配置监控系统方式二：

在这里我们将使用另外一种方式进行处理：使用代码注册Servlet：

编写类：com.kfit.base.servlet.DruidConfiguration ：

```java
//druid 配置.这样的方式不需要添加注解：@ServletComponentScan
@Configuration
public class DruidConfiguration {
    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle2(){
       //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
       ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid2/*");   
       //添加初始化参数：initParams
       //白名单：
       servletRegistrationBean.addInitParameter("allow","127.0.0.1");
       //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
       servletRegistrationBean.addInitParameter("deny","192.168.1.73");
       //登录查看信息的账号密码.
       servletRegistrationBean.addInitParameter("loginUsername","admin2");
       servletRegistrationBean.addInitParameter("loginPassword","123456");
       //是否能够重置数据.
       servletRegistrationBean.addInitParameter("resetEnable","false");
       return servletRegistrationBean;
    }

 

    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter2(){    
       FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new                       WebStatFilter());     
       //添加过滤规则.
       filterRegistrationBean.addUrlPatterns("/*");     
       //添加不需要忽略的格式信息.
  filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
      return filterRegistrationBean;

    }
}
```

启动应用就可以访问：<http://127.0.0.1:8080/druid2/index.html>输入账号和密码：admin2/123456 就可以访问了。

## Spring Boot使用Druid（编程注入）

```java
//这样的方式不需要添加注解：@ServletComponentScan
@Configuration
public class DruidConfiguration {

	// 注册一个StatViewServlet
	@Bean
	public ServletRegistrationBean DruidStatViewServle2() {
		// org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new StatViewServlet(), "/druid2/*");

		// 添加初始化参数：initParams
		// 白名单：
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not
		// permitted to view this page.
		servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", "admin2");
		servletRegistrationBean.addInitParameter("loginPassword", "123456");
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;

	}

//注册一个：filterRegistrationBean
	@Bean
	public FilterRegistrationBean druidStatFilter2() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
				new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions",
				"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
		return filterRegistrationBean;

	}

	/**
	 * 注册dataSouce，这里作为一个例子，只注入了部分参数信息，其它的参数自行扩散思维。
     */
	@Bean
	public DataSource druidDataSource(
			@Value("${spring.datasource.driverClassName}") String driver,
			@Value("${spring.datasource.url}") String url,
			@Value("${spring.datasource.username}") String username,
			@Value("${spring.datasource.password}") String password,
			@Value("${spring.datasource.maxActive}") int maxActive

	) {

		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driver);
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setMaxActive(maxActive);
		try {
			druidDataSource.setFilters("stat, wall");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return druidDataSource;

	}

}
//这里的区别在于加入一个方法：druidDataSource进行数据源的注入.
//如果同时进行了编程式的注入和配置的注入，配置的就无效了。实际中推荐使用配置文件的方式
```



## Spring Boot  中使用Servlet、Filter、Listener、Interceptor 

Web开发使用 Controller 基本上可以完成大部分需求，但是我们还可能会用到 Servlet、Filter、Listener、Interceptor 等等。

当使用Spring-Boot时，嵌入式Servlet容器通过**扫描注解**的方式注册Servlet、Filter和Servlet规范的所有监听器（如HttpSessionListener监听器）。 

spring boot中添加自己的Servlet有两种方法，代码注册和注解自动注册

一、代码注册通过ServletRegistrationBean、 FilterRegistrationBean 和ServletListenerRegistrationBean 获得控制。 
也可以通过实现 ServletContextInitializer 接口直接注册。

二、在 SpringBootApplication 上使用`@ServletComponentScan`注解后，Servlet、Filter、Listener 可以直接通过 `@WebServlet、@WebFilter、@WebListener` 注解自动注册，无需其他代码。  ( 常用)

## Spring Boot 拦截器HandlerInterceptor

Web开发中，我们除了使用 Filter 来过滤请web求外，还可以使用Spring提供的HandlerInterceptor（拦截器）。

HandlerInterceptor 的功能跟过滤器类似，但是提供更精细的的控制能力：在request被响应之前、request被响应之后、视图渲染之前以及request全部结束之后。我们不能通过拦截器修改request内容，但是可以通过抛出异常（或者返回false）来暂停request的执行。

spring Boot使用的拦截器就是spring的拦截器。 

配置拦截器也很简单，Spring 为什么提供了基础类WebMvcConfigurerAdapter ，我们只需要重写addInterceptors 方法添加注册拦截器。

实现自定义拦截器只需要3步： 
1、创建我们自己的拦截器类并实现 HandlerInterceptor 接口。 
2、创建一个Java类继承WebMvcConfigurerAdapter，并重写 addInterceptors 方法。 
2、实例化我们自定义的拦截器，然后将对像手动添加到拦截器链中（在addInterceptors方法中添加）。 

```java
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    publicvoid addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
        super.addInterceptors(registry);

    }

}
```

**只有经过DispatcherServlet 的请求，才会走拦截器链**，我们自定义的Servlet 请求是不会被拦截的，比如我们自定义的Servlet地址<http://localhost:8080/myServlet>1 是不会被拦截器拦截的。并且不管是属于哪个Servlet 只要复合**过滤器**的过滤规则，过滤器都会拦截。

最后说明下，我们上面用到的 WebMvcConfigurerAdapter 并非只是注册添加拦截器使用，其顾名思义是做Web配置用的，它还可以有很多其他作用.

##  Spring Boot启动加载数据CommandLineRunner

实际应用中，我们会有**在项目服务启动的时候就去加载一些数据或做一些事情这样的需求**。 
为了解决这样的问题，Spring Boot 为我们提供了一个方法，通过实现接口 CommandLineRunner 来实现。

```java
/**
 * 服务启动执行
 */

@Component
@Order(value=2)
//以利用@Order注解（或者实现Order接口）来规定所有CommandLineRunner实例的运行顺序。
public class MyStartupRunner1 implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
       // 这个方法会在项目启动的时候执行。并不一定用来加载数据。我们也可以用来做一些其他的事
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
	}

}
```

Spring Boot应用程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法。

```java
public void run(String... args) throws Exception {
            System.out.println(Arrays.asList(args));
    System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作11111111<<<<<<<<<<<<<");
}

/*这里的args就是程序启动的时候进行设置的:
SpringApplication.run(App.class, new String[]{"hello,","林峰"});
这里为了做演示，配置为固定值了，其实直接接收main中的args即可，那么在运行的时候，进行配置即可*/
```

```
eclipse中给java应用传args参数的方法如下：
1、先写好Java代码，比如文件名为IntArrqy.java；
2、在工具栏或菜单上点run as下边有个Run Configuration；
3、在弹出窗口点选第二个标签arguments；
4、把你想输入的参数写在program argumenst就可以了，多个参数使用空格隔开。
完成后点run即可通过运行结 果看到参数使用情况了
```

## Spring Boot环境变量读取

凡是被Spring管理的类，实现接口 EnvironmentAware 重写方法 setEnvironment 可以在工程启动时，获取到系统环境变量

```java
/**
 * 主要是@Configuration，实现接口：EnvironmentAware就能获取到系统环境信息;
 */

@Configuration
public class MyEnvironmentAware implements EnvironmentAware {
	// 注入application.properties的属性到指定变量中.
	@Value("${spring.datasource.url}")
	private String myUrl;

	/**
	 * 注意重写的方法 setEnvironment 是在系统启动的时候被执行。
	 */
	@Override
	public void setEnvironment(Environment environment) {
		// 打印注入的属性信息.
		System.out.println("myUrl=" + myUrl);
		// 通过 environment 获取到系统属性.
		System.out.println(environment.getProperty("JAVA_HOME"));
		// 通过 environment 同样能获取到application.properties配置的属性.
		System.out.println(environment.getProperty("spring.datasource.url"));
		// 获取到前缀是"spring.datasource." 的属性列表值.
		RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(
				environment, "spring.datasource.");
		System.out.println("spring.datasource.url="
				+ relaxedPropertyResolver.getProperty("url"));
		System.out.println("spring.datasource.driverClassName="
				+ relaxedPropertyResolver.getProperty("driverClassName"));
	}

}
```

## Spring Boot改变自动扫描的包

在开发中我们知道Spring Boot默认会扫描启动类同包以及子包下的注解

那么如何进行改变这种扫描包的方式:

原理很简单就是：@ComponentScan注解进行指定要扫描的包以及要扫描的类。

```java
@ComponentScan(basePackages={"cn.kfit","org.kfit"})
//将这个注解加在启动类上，扫描指定的包
//这里需要注意的是，加上这个注解之后，springBoot就只会扫面注解中指定的表。这样启动类所在的包及子包就不会被自动扫描，如果想扫描这个包，就加在注解后面
```

##  Spring Boot Junit 单元测试

spring Boot 中使用Junit单元测试需要注意：

Spring Boot如何使用Junit呢？

​      1). 加入Maven的依赖；

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
</dependency>
```

​      2). 编写测试service;

​      3). 编写测试类;

```java
// / SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
// //指定我们SpringBoot工程的Application启动类
@SpringApplicationConfiguration(classes = App.class) // 这里APP是启动类
// /由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class HelloServiceTest {
	@Resource
	private HelloService helloService;
	@Test
	publicvoid testGetName() {
		Assert.assertEquals("hello", helloService.getName());
	}
}
//这时候就可以进行测试了，右键—Run As – Junit Test。
```

# Spring boot中Redis的使用

spring boot对常用的数据库支持外，对nosql 数据库也进行了封装自动化。

## redis介绍

Redis是目前业界使用最广泛的内存数据存储。相比memcached，Redis支持更丰富的数据结构，例如hashes, lists, sets等，同时**支持数据持久化**。除此之外，Redis还提供一些类数据库的特性，比如事务，HA[双机集群(*HA*)系统简称]主从库。可以说**Redis兼具了缓存系统和数据库的一些特性**，因此有着丰富的应用场景。

## 如何使用

1、引入 spring-boot-starter-redis

```xml
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-redis</artifactId>  
</dependency>  
```

2、添加配置文件

```properties
# REDIS (RedisProperties)
# Redis数据库索引（默认为0）
spring.redis.database=0  
# Redis服务器地址
spring.redis.host=192.168.0.58
# Redis服务器连接端口
spring.redis.port=6379  
# Redis服务器连接密码（默认为空）
spring.redis.password=  
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8  
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1  
# 连接池中的最大空闲连接
spring.redis.pool.max-idle=8  
# 连接池中的最小空闲连接
spring.redis.pool.min-idle=0  
# 连接超时时间（毫秒）
spring.redis.timeout=0  
```

3、添加cache的配置类

```java
@Configuration  //@Configuration 注解本质上还是 @Component,是一个组件
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	@Bean
	public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60);//秒
        return rcm;
    }
    
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
```

3、好了，接下来就可以直接使用了

```java
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
	@Autowired
	private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }
    
    @Test
    public void testObj() throws Exception {
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set("com.neox", user);
        operations.set("com.neo.f", user,1,TimeUnit.SECONDS);
        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");
        boolean exists=redisTemplate.hasKey("com.neo.f");
        if(exists){
        	System.out.println("exists is true");
        }else{
        	System.out.println("exists is false");
        }
       // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}
```

**如何在查找数据库的时候自动使用缓存**

4、自动根据方法生成缓存

```java
@RequestMapping("/getUser")
@Cacheable(value="user-key")
public User getUser() {
    User user=userRepository.findByUserName("aa");
    System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");  
    return user;
}
```

其中value的值就是缓存到redis中的key



## 共享Session-spring-session-data-redis

**在集群环境中做session共享是必不可少的**一步，试想当用户的请求经过nginx转发到A机器进行登录，然后下一步的操作转发到了B机器，这个时候用户信息是存储在A机器上的web容器中，B机器就识别不了这个用户，这个时候就需要做session共享了。

传统session共享解决方案有：

1）使用**tomcat自身的集群**：session信息在不同tomcat的jvm中复制，**无法做到大规模集群**；

2）nginx根据ip hash方式：这种方式比较容易出现热点，和雪崩；

3）tomcat session manager 方法存储：这种方法服务器只能使用 tomcat ，网上有针对 memcached 和 redis 实现，直接配置就行了。

4）使用 filter 方法存储(**推荐**)：，因为它的服务器使用范围比较多，不仅限于 tomcat ，而且实现的原理比较简单容易控制。可以使用 memcached-session-filter，官方网址：http://code.google.com/p/memcached-session-filter/

介绍的**spring-session也是基于filter**的。

1、在springboot中配置spring-session：

分布式系统中，**session共享**有很多的解决方案，其中**托管到缓存中**应该是最常用的方案之一，

### Spring Session官方说明

Spring Session provides an API and implementations for managing a user’s session information.

### 如何使用

1、引入依赖

```xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>

 <dependency>
         <groupId>org.springframework.session</groupId>
         <artifactId>spring-session</artifactId>
     </dependency>
```

2）在application.properties中添加：

```properties
spring.session.store-type=redis
spring.redis.host=abc.redis
spring.redis.password=123
spring.redis.port=19822
#指定redis的地址和端口
```

**spring session就可以接管默认的session管理了，将session信息存储在redis中了**。（spring-session除了redis还支持其他存储）

注：用户用到的实体类（例如：User）需要实现序列化接口，否则spring-session会报错。

3）测试：

启动项目后，在控制台上可以看到springboot已经注册了一个springSessionRepositoryFilter的过滤器，这个filter就是用来做session的。

![img](https://img-blog.csdn.net/20180619143347263?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpdXhpYW83MjM4NDY=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

用户登录后，在redis中可以发现session相关信息。

4）使用nginx的upstream搭建负载均衡：

### 深入spring-session

**1、session超时：**

在tomcat中，如果要设置session的超时，我们可以在web.xml或者springboot的application.properties中直接配置即可，例如在springboot中设置：

```properties
server.session.timeout=1800
```

但引入了spring-session后，这个配置将不再起作用， 我们需要写一个如下的配置类：

```java
@Configuration
//maxInactiveIntervalInSeconds 默认是1800秒过期，这里测试修改为60秒
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
public class RedisSessionConfig{

}
```

> maxInactiveIntervalInSeconds: 设置Session失效时间，使用Redis Session之后，原Boot的server.session.timeout属性不再生效

注：**如果不修改session超时，可以不用该配置类**。

**2、在springboot中使用spring-session完成登录、登出等功能：**

1）定义User实体类：

```java
public class User implements Serializable {
private static final long serialVersionUID = 1629629499205758251L;
//注：该类需要序列化，因为spring-session会将该对象序列化后保存到redis中。
private Long id;
private String name;
private String pwd;
private String note;
private Integer dateAuth;
private Integer tableAuth;
//set/get 方法
```
2）UserController：

```java
@RequestMapping("/user")
@Controller
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class); 
	
@Autowired
private UserService userService;

 /**
 * 登录验证
 * @param request
 * @return
 */
@RequestMapping("/login")
public ModelAndView login(HttpServletRequest request,Model model) {
	String name = request.getParameter("username");
	String password = request.getParameter("password");
	//TODO校验
	Map<String,String> map = new HashMap<>();
	map.put("name",name);
	map.put("pwd",password);
	User user = null;
	try {
		user = userService.login(map);
	} catch (Exception e) {
		logger.error("user login is error...",e);
	}
	if (user != null) {
		HttpSession session = request.getSession();
		session.setAttribute(session.getId(),user);
		model.addAttribute("user", user);
		return new ModelAndView("redirect:/index");
	} else {
		request.setAttribute("errorInfo", "验证失败");
		return new ModelAndView("login/login");
	}
}
/**
 * 退出
 * @param request
 * @return
 */
@RequestMapping("/loginOut")
@ResponseBody
public ResponseMessage loginOut(HttpServletRequest request, HttpServletResponse response) {
	HttpSession session = request.getSession();
	if (session != null) {
		session.setAttribute(session.getId(), null);
	}
	return ResponseMessage.ok(Constants.CODE_SUCCESS,null);
}
}

```
注：spring-session会通过拦截器的方式往session对象中存放、移除sessionId（session.getId()），所以我们在登录、登出、拦截器中会调用session.setAttribute(session.getId(),user);来判断。

3）session拦截器：	

```java
public class SessionInterceptor extends HandlerInterceptorAdapter {
private static String[] IGNORE_URI = {"/login.jsp", "/login/","/login","/loginIndex", "/error"};
private static Logger log = LoggerFactory.getLogger(SessionInterceptor.class);  
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	boolean flag = false;
    String url = request.getRequestURL().toString();
    //String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径:
	for (String s : IGNORE_URI) {
        if (url.contains(s)) {
            flag = true;
            break;
        }
    }
    
    if (!flag) {
    	HttpSession session = request.getSession();
    	Object obj = session.getAttribute(session.getId());//Constants.SESSION_USER
        if (null == obj) {//未登录
        	String servletPath = request.getServletPath();
        	log.error("session失效，当前url：" + url+";module Paht:"+servletPath);
        	if (request.getHeader("x-requested-with") != null && 
        		request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
            {
        		response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
        		response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print("error");
	        } else {
	        	response.sendRedirect(request.getContextPath()+"/user/loginIndex");
	        }
        	return false;
        } else
           {
               //已登录了
            }
        }
    }
    return super.preHandle(request, response, handler);
}
 
@Override
public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    super.postHandle(request, response, handler, modelAndView);
}
}
```


说明：

我们知道**spring-session会自动注入springSessionRepositoryFilter过滤器**，每一次的请求都由他来过滤，其本质是：对每一个请求的request进行了一次封装。那么，在Controller里面拿出的request实际上是封装后的request，

调用request.getSession()的时候，实际上拿到是Spring封装后的session。这个session则存储在redis数据库中。应用通过 `getSession(boolean create)` 方法来获取 session 数据，参数 create 表示 session 不存在时是否创建新的 session 。

1.getSession 方法首先从请求的 “`.CURRENT_SESSION`” 属性来获取 currentSession 

2.没有 currentSession ，则从 request 取出 sessionId ，然后读取 `spring:session:sessions:[sessionId]` 的值，同时根据 lastAccessedTime 和 MaxInactiveIntervalInSeconds 来判断这个 session 是否过期。如果 request 中没有 sessionId ，说明该用户是第一次访问，会根据不同的实现来创建一个新的 session 。 另外， 从 request 取 sessionId 依赖具体的 HttpSessionStrategy 的实现，spring session 给了两个默认的实现 CookieHttpSessionStrategy 和 HeaderHttpSessionStrategy ，即从 cookie 和 header 中取出 sessionId 。

3、spring-session在redis中的存储结构：

spring:session是默认的Redis HttpSession**前缀**（redis中，我们常用’:’作为分割符）。

![img](https://img-blog.csdn.net/20180619150418978?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpdXhpYW83MjM4NDY=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

登录redis 输入 `keys '*sessions*'`

```
t<spring:session:sessions:db031986-8ecc-48d6-b471-b137a3ed6bc4
t(spring:session:expirations:1472976480000
```

其中 1472976480000为失效时间，意思是这个时间后session失效，`db031986-8ecc-48d6-b471-b137a3ed6bc4` 为sessionId

### 如何在两台或者多台中共享session

按照上面的步骤在另一个项目中再次配置一次，启动后自动就进行了session共享。因为他是将session存储在redis缓存中

# Spring data jpa的使用

## spring data jpa介绍

### JPA是什么

JPA**是**Java持久化**规范**。提供一种**对象/关联映射工具**来管理Java应用中的关系数据。

> 注意:JPA是一套规范，不是一套产品，那么像Hibernate,TopLink,JDO他们是一套产品，如果说这些产品实现了这个JPA规范，他们为JPA的实现产品。

### spring data jpa

Spring Data JPA 是JPA的实现产品。

Spring Data JPA 是 Spring 基于 ORM 框架、JPA 规范的基础上封装的一套JPA应用框架，简化对数据的访问和操作。它提供了包括增删改查等在内的常用功能，且易于扩展

> spring data jpa让我们解脱了DAO层的操作，基本上所有CRUD都可以依赖于它来实现



## 基本查询

基本查询也分为两种，一种是spring data默认已经实现，一种是**根据查询的方法来自动解析成SQL**。

### 预先生成方法

spring data jpa 默认预先生成了一些基本的CURD的方法，如：增、删、改、查 等等

1 继承JpaRepository

```java
public interface UserRepository extends JpaRepository<User, Long> {
}
```

2 使用默认方法

```java
@Test
public void testBaseQuery() throws Exception {
	User user=new User();
	userRepository.findAll();
	userRepository.findOne(1l);
	userRepository.save(user);
	userRepository.delete(user);
	userRepository.count();
	userRepository.exists(1l);
	// ...
}
```

### 自定义简单查询

**自定义的简单查询就是根据方法名来自动生成SQL**。

主要的语法是`findXXBy`,`readAXXBy`,`queryXXBy`,`countXXBy`, `getXXBy`后面跟属性名称：

```java
User findByUserName(String userName);
```

也可以使用加一些关键字`And`、 `Or`

```java
User findByUserNameOrEmail(String username, String email);
```

修改、删除、统计也是类似语法

```java
Long deleteById(Long id);

Long countByUserName(String userName)
```

基本上SQL体系中的关键词都可以使用，例如：`LIKE`、 `IgnoreCase`、 `OrderBy`。

```java
List<User> findByEmailLike(String email);

User findByUserNameIgnoreCase(String userName);
    
List<User> findByUserNameOrderByEmailDesc(String email);
```

**具体的关键字，使用方法和生产成SQL如下表所示**

| Keyword           | Sample                                  | JPQL snippet                                                 |
| ----------------- | --------------------------------------- | ------------------------------------------------------------ |
| And               | findByLastnameAndFirstname              | … where x.lastname = ?1 and x.firstname = ?2                 |
| Or                | findByLastnameOrFirstname               | … where x.lastname = ?1 or x.firstname = ?2                  |
| Is,Equals         | findByFirstnameIs,findByFirstnameEquals | … where x.firstname = ?1                                     |
| Between           | findByStartDateBetween                  | … where x.startDate between ?1 and ?2                        |
| LessThan          | findByAgeLessThan                       | … where x.age < ?1                                           |
| LessThanEqual     | findByAgeLessThanEqual                  | … where x.age ⇐ ?1                                           |
| GreaterThan       | findByAgeGreaterThan                    | … where x.age > ?1                                           |
| GreaterThanEqual  | findByAgeGreaterThanEqual               | … where x.age >= ?1                                          |
| After             | findByStartDateAfter                    | … where x.startDate > ?1                                     |
| Before            | findByStartDateBefore                   | … where x.startDate < ?1                                     |
| IsNull            | findByAgeIsNull                         | … where x.age is null                                        |
| IsNotNull,NotNull | findByAge(Is)NotNull                    | … where x.age not null                                       |
| Like              | findByFirstnameLike                     | … where x.firstname like ?1                                  |
| NotLike           | findByFirstnameNotLike                  | … where x.firstname not like ?1                              |
| StartingWith      | findByFirstnameStartingWith             | … where x.firstname like ?1 (parameter bound with appended %) |
| EndingWith        | findByFirstnameEndingWith               | … where x.firstname like ?1 (parameter bound with prepended %) |
| Containing        | findByFirstnameContaining               | … where x.firstname like ?1 (parameter bound wrapped in %)   |
| OrderBy           | findByAgeOrderByLastnameDesc            | … where x.age = ?1 order by x.lastname desc                  |
| Not               | findByLastnameNot                       | … where x.lastname <> ?1                                     |
| In                | findByAgeIn(Collection ages)            | … where x.age in ?1                                          |
| NotIn             | findByAgeNotIn(Collection age)          | … where x.age not in ?1                                      |
| TRUE              | findByActiveTrue()                      | … where x.active = true                                      |
| FALSE             | findByActiveFalse()                     | … where x.active = false                                     |
| IgnoreCase        | findByFirstnameIgnoreCase               | … where UPPER(x.firstame) = UPPER(?1)                        |



## 复杂查询

在实际的开发中我们需要用到分页、删选、连表等查询的时候就需要特殊的方法或者自定义SQL

### 分页查询

spring data jpa已经帮我们实现了分页的功能，在查询的方法中，需要传入参数`Pageable` ,当查询中有多个参数的时候`Pageable`建议做为最后一个参数传入

```java
Page<User> findALL(Pageable pageable);
    
Page<User> findByUserName(String userName,Pageable pageable);
```

`Pageable` 是spring封装的分页实现类，使用的时候需要传入页数、每页条数和排序规则

```java
@Test
public void testPageQuery() throws Exception {
	int page=1,size=10;
	Sort sort = new Sort(Direction.DESC, "id");
    Pageable pageable = new PageRequest(page, size, sort);
    userRepository.findALL(pageable);
    userRepository.findByUserName("testName", pageable);
}
```

### **限制查询**

有时候我们只需要查询前N个元素，或者支取前一个实体。

```java
ser findFirstByOrderByLastnameAsc();

User findTopByOrderByAgeDesc();

Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);

List<User> findFirst10ByLastname(String lastname, Sort sort);

List<User> findTop10ByLastname(String lastname, Pageable pageable);
```

### 自定义SQL查询

其实Spring data 中大部分的SQL都可以根据方法名定义的方式来实现，但是由于某些特殊要求我们需要使用自定义的SQL来查询。(使用方法名定义实现不了的时候)

spring data支持自定义sql查询；

```java
@Modifying
@Query("update User u set u.userName = ?1 where u.id = ?2")
int modifyByIdAndUserId(String  userName, Long id);
	
@Transactional
@Modifying
@Query("delete from User where id = ?1")
void deleteByUserId(Long id);
  
@Transactional(timeout = 10)
@Query("select u from User u where u.emailAddress = ?1")
    User findByEmailAddress(String emailAddress);
//在SQL的查询方法上面使用`@Query`注解，如涉及到删除和修改在需要加上`@Modifying`.也可以根据需要添加 `@Transactional` 对事物的支持，查询超时的设置等
```

### 多表查询

多表查询在spring data jpa中有两种实现方式:

第一种 : 利用hibernate的级联查询来实现

第二种 : 创**建一个结果集的接口**来**接收连表查询后的结果**，这里主要第二种方式。

首先需要定义一个**结果集**的**接口**。

```java
public interface HotelSummary {
	City getCity();
	String getName();
	Double getAverageRating();
	default Integer getAverageRatingRounded() {
		return getAverageRating() == null ? null : (int) Math.round(getAverageRating());
	}
}
```

**查询的方法返回类型**设置为新创建的**接口** 

```java
@Query("select h.city as city, h.name as name, avg(r.rating) as averageRating "
		- "from Hotel h left outer join h.reviews r where h.city = ?1 group by h")
Page<HotelSummary> findByCity(City city, Pageable pageable);

@Query("select h.name as name, avg(r.rating) as averageRating "
		- "from Hotel h left outer join h.reviews r  group by h")
Page<HotelSummary> findByCity(Pageable pageable);
```

使用

```java
Page<HotelSummary> hotels = this.hotelRepository.findByCity(new PageRequest(0, 10, Direction.ASC, "name"));
for(HotelSummary summay:hotels){
		System.out.println("Name" +summay.getName());
	} 
```

> 在运行中Spring会给接口（HotelSummary）自动生产一个代理类来接收返回的结果，代码汇总使用`getXX`的形式来获取



## 多数据源的支持

### 同源数据库的多源支持

日常项目中因为使用的分布式开发模式，不同的服务有不同的数据源，常常需要在一个项目中使用多个数据源。

配置sping data jpa对多数据源的使用，一般分一下为三步：

- 1 配置多数据源
- 2 **不同源的实体类放入不同包路径**
- 3 声明**不同的包路径**下使用不同的数据源、事务支持

**示例实体**
首先，创建两个简单实体，每个独立存储在不同数据库中。 
第一个是User：

```java
@Entity
@Table(schema = "spring_jpa_user")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String name;
@Column(unique = true, nullable = false)
private String email;
private int age;
}
```

第二个是Product:

```java
@Entity
@Table(schema = "spring_jpa_product")
public class Product {
@Id
private int id;
private String name;
private double price;
}
```

这连个实体被放在不同的包中，这对后续实现多数据源配置很重要。

**JPA Repositories**
下面看各自的 JPA Repository —— UserRepository：

```java
public interface UserRepository extends JpaRepository<User, Integer> { }
```

ProductRepository:

```java
public interface ProductRepository extends JpaRepository<Product, Integer> { }
```

同样，两个Repository在不同的独立包中。

同样，两个Repository在不同的独立包中。

**使用javaConfig方式配置JPA**
接下来进行实际Spring 配置

我们设置两个配置类，一个为User，另一个为Product： 
每个配置类都需要定义下面内容：

```java
User DataSource
User EntityManagerFactory (userEntityManager)
User TransactionManager (userTransactionManager)
```


首先开始User配置：

```java
@Configuration
@PropertySource({ "classpath:persistence-multiple-db.properties" })
@EnableJpaRepositories(
    basePackages = "org.dataz.persistence.multiple.dao.user", 
    entityManagerFactoryRef = "userEntityManager", 
    transactionManagerRef = "userTransactionManager"
)
public class UserConfig {
@Autowired
private Environment env;
@Bean
@Primary
public LocalContainerEntityManagerFactoryBean userEntityManager() {
    LocalContainerEntityManagerFactoryBean em
      = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(userDataSource());
    em.setPackagesToScan(
      new String[] { "org.dataz.persistence.multiple.model.user" });

    HibernateJpaVendorAdapter vendorAdapter
      = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto",
      env.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect",
      env.getProperty("hibernate.dialect"));
    em.setJpaPropertyMap(properties);
    return em;
}

@Primary
@Bean
public DataSource userDataSource() {
    DriverManagerDataSource dataSource
      = new DriverManagerDataSource();
    dataSource.setDriverClassName(
      env.getProperty("jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("user.jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.pass"));
    return dataSource;
}

@Primary
//我们通过@Primary注解标记userTransactionManager为首选TransactionManager ，后续无论要隐式或显式地注入事务管理器时无需指定事务名称。
@Bean
public PlatformTransactionManager userTransactionManager() {
    JpaTransactionManager transactionManager
      = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
      userEntityManager().getObject());
    return transactionManager;
}
}
```



下面我们讨论ProductConfig，定义内容同上：

```java
@Configuration
@PropertySource({ "classpath:persistence-multiple-db.properties" })
@EnableJpaRepositories(
    basePackages = "org.dataz.persistence.multiple.dao.product", 
    entityManagerFactoryRef = "productEntityManager", 
    transactionManagerRef = "productTransactionManager"
)
public class ProductConfig {
@Autowired
private Environment env;
@Bean
public LocalContainerEntityManagerFactoryBean productEntityManager() {
    LocalContainerEntityManagerFactoryBean em
      = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(productDataSource());
    em.setPackagesToScan(
      new String[] { "org.dataz.persistence.multiple.model.product" });
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto",
      env.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect",
      env.getProperty("hibernate.dialect"));
    em.setJpaPropertyMap(properties);
    return em;
}

@Bean
public DataSource productDataSource() {
    DriverManagerDataSource dataSource
      = new DriverManagerDataSource();
    dataSource.setDriverClassName(
      env.getProperty("jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("product.jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.pass"));
    return dataSource;
}

@Bean
public PlatformTransactionManager productTransactionManager() {
    JpaTransactionManager transactionManager
      = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
      productEntityManager().getObject());
    return transactionManager;
}
}
```
测试配置
通过创建每个实体的实例进行测试，确保每个实例可以在不同的库中进行保存。测试示例如下：

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserConfig.class, ProductConfig.class })
@TransactionConfiguration
public class JPAMultipleDBTest {
@Autowired
private UserRepository userRepository;
@Autowired
private ProductRepository productRepository;
@Test
@Transactional("userTransactionManager")
public void whenCreatingUser_thenCreated() {
    User user = new User();
    user.setName("John");
    user.setEmail("john@test.com");
    user.setAge(20);
    user = userRepository.save(user);
    assertNotNull(userRepository.findOne(user.getId()));
}

@Test
@Transactional("productTransactionManager")
public void whenCreatingProduct_thenCreated() {
    Product product = new Product();
    product.setName("Book");
    product.setId(2);
    product.setPrice(20);
    product = productRepository.save(product);
    assertNotNull(productRepository.findOne(product.getId()));
}
}
```
**总结**
需要提醒注意两点：

无论通过xml或javaConfig方式配置，配置信息中@EnableJpaRepositories需要一致，至少都需要指明下面三项：

```java
@EnableJpaRepositories(
    basePackages = "org.dataz.persistence.multiple.dao.user", 
    entityManagerFactoryRef = "userEntityManager", 
    transactionManagerRef = "userTransactionManager"
)

```

### 异构数据库多源支持

比如我们的项目中，即需要对mysql的支持，也需要对mongodb的查询等。

实体类声明`@Entity` 关系型数据库支持类型、声明`@Document` 为mongodb支持类型，不同的数据源使用不同的实体就可以了

```java
interface PersonRepository extends Repository<Person, Long> {
 …
}

@Entity
public class Person {
  …
}

interface UserRepository extends Repository<User, Long> {
 …
}

@Document
public class User {
  …
}
```

但是，如果User用户既使用mysql也使用mongodb呢，也可以做混合使用

```java
interface JpaPersonRepository extends Repository<Person, Long> {
 …
}

interface MongoDBPersonRepository extends Repository<Person, Long> {
 …
}

@Entity
@Document
public class Person {
  …
}
```

也可以通过对不同的包路径进行声明，比如A包路径下使用mysql,B包路径下使用mongoDB

```java
@EnableJpaRepositories(basePackages = "com.neo.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.neo.repositories.mongo")
interface Configuration { }
```



## 其它

在@Entity 注解的实体中有下面的两个问题需要注意。

**使用枚举**

使用枚举的时候，我们希望数据库中存储的是枚举对应的String类型，而不是枚举的索引值，需要在属性上面添加`@Enumerated(EnumType.STRING)` 注解

```java
@Enumerated(EnumType.STRING) 
@Column(nullable = true)
private UserType type;
```

**不需要和数据库映射的属性**

正常情况下我们在实体类上加入注解`@Entity`，就会让实体类和表相关连。 如果其中某个属性我们不需要和数据库来关联只是在展示的时候做计算，只需要加上`@Transient`属性既可。

```java
@Transient
private String  userName;
```

## 参考

[Spring Data JPA - Reference Documentation](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

[Spring Data JPA——参考文档 中文版](https://www.gitbook.com/book/ityouknow/spring-data-jpa-reference-documentation/details)

# SpringBoot使用mybatis

mybatis-spring-boot-starter就是springboot+mybatis可以完全注解不用配置文件

orm框架的本质是简化编程中操作数据库的编码。

## mybatis-spring-boot-starter

官方说明：`MyBatis Spring-Boot-Starter will help you use MyBatis with Spring Boot`

mybatis-spring-boot-starter主要有两种解决方案：

一种是使用注解解决一切问题，

一种是简化后的老传统。

都需要首先引入mybatis-spring-boot-starter的pom文件

```xml
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.1.1</version>
</dependency>
```

## 无配置文件注解版

就是一切使用注解搞定。

### 1 添加相关maven文件

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
	<dependency>
		<groupId>org.mybatis.spring.boot</groupId>
		<artifactId>mybatis-spring-boot-starter</artifactId>
		<version>1.1.1</version>
	</dependency>
     <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
     <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
	</dependency>
</dependencies>
```

### 2`application.properties` 添加相关配置

```properties
mybatis.type-aliases-package=com.neo.entity

spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=utf-8
spring.datasource.username = root
spring.datasource.password = root
#springboot会自动加载spring.datasource.*相关配置，数据源就会自动注入到sqlSessionFactory中，sqlSessionFactory会自动注入到Mapper中，直接拿来使用就行了。
```

在**启动类中**添加对**mapper包扫描**`@MapperScan`

```java
@SpringBootApplication
@MapperScan("com.neo.mapper")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

或者直接在Mapper类上面添加注解`@Mapper`,建议使用上面那种，不然每个mapper加个注解挺麻烦.

### 3、开发Mapper

第三步是**最关键**的一块，sql生产都在这里 （这里的mapper都是接口类）

这里就是将原来写在mapper.xml配置文件中的sql以注解的方式写在接口类中

```java
public interface UserMapper {
	
	@Select("SELECT * FROM users")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
		@Result(property = "nickName", column = "nick_name")
	})
	List<UserEntity> getAll();
	 
	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
		@Result(property = "nickName", column = "nick_name")
	})
	UserEntity getOne(Long id);

	@Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(UserEntity user);

	@Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(UserEntity user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);
}
//user_sex使用了枚举,所以会加上javaType = UserSexEnum.class
//- @Select 是查询类的注解，所有的查询均使用这个
//- @Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
//- @Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
//- @Update 负责修改，也可以直接传入对象
//- @delete 负责删除
```

[了解更多属性参考这里](http://www.mybatis.org/mybatis-3/zh/java-api.html)

> **注意，使用#符号和$符号的不同：**

```java
// This example creates a prepared statement, something like select * from teacher where name = ?;
@Select("Select * from teacher where name = #{name}")
Teacher selectTeachForGivenName(@Param("name") String name);

// This example creates n inlined statement, something like select * from teacher where name = 'someName';
@Select("Select * from teacher where name = '${name}'")
Teacher selectTeachForGivenName(@Param("name") String name);
```

### 4、使用

使用的时候当作普通的类注入就可以了

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
	@Autowired
	private UserMapper UserMapper;
	@Test
	public void testInsert() throws Exception {
		UserMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		UserMapper.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		UserMapper.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

		Assert.assertEquals(3, UserMapper.getAll().size());
	}

	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = UserMapper.getAll();
		System.out.println(users.toString());
	}
	
	@Test
	public void testUpdate() throws Exception {
		UserEntity user = UserMapper.getOne(3l);
		System.out.println(user.toString());
		user.setNickName("neo");
		UserMapper.update(user);
		Assert.assertTrue(("neo".equals(UserMapper.getOne(3l).getNickName())));
	}
}
```

## 极简xml版本

和在spring中使用mybatise没有太大的区别，系统会自动根据方法名在映射文件中找对应的sql.

### 1、配置

pom文件的配置和一注释开发的配置是一样，只是`application.properties`新增以下配置

```properties
mybatis.config-location=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
```

指定了mybatis基础配置文件和实体类映射文件的地址

mybatis-config.xml 配置

```xml
<configuration>
	<typeAliases>
		<typeAlias alias="Integer" type="java.lang.Integer" />
		<typeAlias alias="Long" type="java.lang.Long" />
		<typeAlias alias="HashMap" type="java.util.HashMap" />
		<typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
		<typeAlias alias="ArrayList" type="java.util.ArrayList" />
		<typeAlias alias="LinkedList" type="java.util.LinkedList" />
	</typeAliases>
</configuration>
这里也可以添加一些mybatis基础的配置
```

### 2、添加User的映射文件

```xml
<mapper namespace="com.neo.mapper.UserMapper" >
    <resultMap id="BaseResultMap" type="com.neo.entity.UserEntity" >
        <id column="id" property="id" jdbcType="BIGINT" />
        <result column="userName" property="userName" jdbcType="VARCHAR" />
        <result column="passWord" property="passWord" jdbcType="VARCHAR" />
        <result column="user_sex" property="userSex" javaType="com.neo.enums.UserSexEnum"/>
        <result column="nick_name" property="nickName" jdbcType="VARCHAR" />
    </resultMap>
    
    <sql id="Base_Column_List" >
        id, userName, passWord, user_sex, nick_name
    </sql>

    <select id="getAll" resultMap="BaseResultMap"  >
       SELECT 
       <include refid="Base_Column_List" />
	   FROM users
    </select>

    <select id="getOne" parameterType="java.lang.Long" resultMap="BaseResultMap" >
        SELECT 
       <include refid="Base_Column_List" />
	   FROM users
	   WHERE id = #{id}
    </select>

    <insert id="insert" parameterType="com.neo.entity.UserEntity" >
       INSERT INTO 
       		users
       		(userName,passWord,user_sex) 
       	VALUES
       		(#{userName}, #{passWord}, #{userSex})
    </insert>
    
    <update id="update" parameterType="com.neo.entity.UserEntity" >
       UPDATE 
       		users 
       SET 
       	<if test="userName != null">userName = #{userName},</if>
       	<if test="passWord != null">passWord = #{passWord},</if>
       	nick_name = #{nickName}
       WHERE 
       		id = #{id}
    </update>
    
    <delete id="delete" parameterType="java.lang.Long" >
       DELETE FROM
       		 users 
       WHERE 
       		 id =#{id}
    </delete>
</mapper>
```

### 3、编写Dao层的代码

```java
public interface UserMapper {
	List<UserEntity> getAll();
	UserEntity getOne(Long id);
	void insert(UserEntity user);
	void update(UserEntity user);
	void delete(Long id);
}
```

### 4、使用

也是直接注入dao层Mapper就可以了

# springboot+mybatis多数据源最简解决方案



说起多数据源，一般都用来解决：

主从模式或者业务比较复杂需要连接不同的分库来支持业务。

## 配置文件

数据库这边的配置：

```properties
mybatis.config-locations=classpath:mybatis/mybatis-config.xml

spring.datasource.test1.driverClassName = com.mysql.jdbc.Driver
spring.datasource.test1.url = jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=utf-8
spring.datasource.test1.username = root
spring.datasource.test1.password = root

spring.datasource.test2.driverClassName = com.mysql.jdbc.Driver
spring.datasource.test2.url = jdbc:mysql://localhost:3306/test2?useUnicode=true&characterEncoding=utf-8
spring.datasource.test2.username = root
spring.datasource.test2.password = root
```

一个test1库和一个test2库，其中test1位主库，在**使用的过程中必须指定主库**，不然会报错。

## 数据源配置

```java
@Configuration
@MapperScan(basePackages = "com.neo.mapper.test1", sqlSessionTemplateRef  = "test1SqlSessionTemplate")
public class DataSource1Config {

    @Bean(name = "test1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test1SqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "test1TransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
```

最关键的地方就是这块了，一层一层注入,首先创建DataSource，然后创建SqlSessionFactory再创建事务，最后包装到SqlSessionTemplate中。其中需要指定分库的mapper文件地址，以及分库dao层代码

```java
@MapperScan(basePackages = "com.neo.mapper.test1", sqlSessionTemplateRef  = "test1SqlSessionTemplate")
```

这块的注解就是指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate。所有`@Bean`都需要按照命名指定正确。

## dao层和xml层

dao层和xml需要按照库来分在不同的目录，比如：test1库dao层在com.neo.mapper.test1包下，test2库在com.neo.mapper.test1

```java
public interface User1Mapper {
	List<UserEntity> getAll();
	UserEntity getOne(Long id);
	void insert(UserEntity user);
	void update(UserEntity user);
	void delete(Long id);
}
```

xml层

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.neo.mapper.test1.User1Mapper" >
    <resultMap id="BaseResultMap" type="com.neo.entity.UserEntity" >
        <id column="id" property="id" jdbcType="BIGINT" />
        <result column="userName" property="userName" jdbcType="VARCHAR" />
        <result column="passWord" property="passWord" jdbcType="VARCHAR" />
        <result column="user_sex" property="userSex" javaType="com.neo.enums.UserSexEnum"/>
        <result column="nick_name" property="nickName" jdbcType="VARCHAR" />
    </resultMap>
    
    <sql id="Base_Column_List" >
        id, userName, passWord, user_sex, nick_name
    </sql>

    <select id="getAll" resultMap="BaseResultMap"  >
       SELECT 
       <include refid="Base_Column_List" />
	   FROM users
    </select>

    <select id="getOne" parameterType="java.lang.Long" resultMap="BaseResultMap" >
        SELECT 
       <include refid="Base_Column_List" />
	   FROM users
	   WHERE id = #{id}
    </select>

    <insert id="insert" parameterType="com.neo.entity.UserEntity" >
       INSERT INTO 
       		users
       		(userName,passWord,user_sex) 
       	VALUES
       		(#{userName}, #{passWord}, #{userSex})
    </insert>
    
    <update id="update" parameterType="com.neo.entity.UserEntity" >
       UPDATE 
       		users 
       SET 
       	<if test="userName != null">userName = #{userName},</if>
       	<if test="passWord != null">passWord = #{passWord},</if>
       	nick_name = #{nickName}
       WHERE 
       		id = #{id}
    </update>
    
    <delete id="delete" parameterType="java.lang.Long" >
       DELETE FROM
       		 users 
       WHERE 
       		 id =#{id}
    </delete>

</mapper>
```

## 测试

测试可以使用SpringBootTest,也可以放到Controller中，这里只贴Controller层的使用

```java
@RestController
public class UserController {

    @Autowired
    private User1Mapper user1Mapper;

	@Autowired
	private User2Mapper user2Mapper;
	
	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=user1Mapper.getAll();
		return users;
	}
	
    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=user2Mapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(UserEntity user) {
        user2Mapper.insert(user);
    }
    
    @RequestMapping(value="update")
    public void update(UserEntity user) {
        user2Mapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        user1Mapper.delete(id);
    }
    
}
```

