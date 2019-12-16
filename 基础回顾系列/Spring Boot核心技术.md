# Spring Boot核心技术

# springBoot入门

这个项目的目的就是为了简化spring的开发。 以前我们开发一个应用需要进行各式配置。 使用springBoot后，springBoot已经默认帮我配置好了(默认的帮我们配置了很多东西)，我们只需要简单的操作就可以直接使用。

## Spring Boot HelloWorld

一个功能：浏览器发送hello请求，服务器接受请求并处理，响应Hello World字符串；

以前我们想实现这个功能，创建一个web项目，导入spring，springmvc一系列jar包依赖，配置大量的配置信息，然后将项目打包成war包放在Tomcat中运行。

使用springBoot后

1、创建一个maven工程；（jar） 导入spring boot相关的依赖

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```

3、编写一个主程序；启动Spring Boot应用

```java
/** 
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 */
@SpringBootApplication
public class HelloWorldMainApplication {

    public static void main(String[] args) {

        // Spring应用启动起来
        SpringApplication.run(HelloWorldMainApplication.class,args);
    }
}

问题记录：
Your ApplicationContext is unlikely to start due to a @ComponentScan of the default package.
HelloWorldMainApplication : 我们的启动类应该放在一个包中，不能直接放在java src目录下，启动类不能从组件的默认包启动    
```

4、编写相关的Controller、Service

```java
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}

```

5、运行主程序测试

http://localhost:8080/hello

6、简化部署： 将下面的插件导入即可

```xml
 <!-- 这个插件，可以将应用打包成一个可执行的jar包；-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

将这个应用打成jar包，直接使用java -jar的命令进行执行；

### 深入理解HelloWorld

springBoot确实简单，只需要写一个启动程序，就可以启动我们程序。controller，service根本就不需要我们配置。

他是如何实现这样的功能的？

**1: 从POM文件 开始帮分析**

```xml
1: 从POM文件 开始帮我们分析？
他配置了一个父项目：父项目在开发中的作用一般是来做项目依赖管理的
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring‐boot‐starter‐parent</artifactId>
    <version>1.5.9.RELEASE</version>
</parent>

他的父项目是
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring‐boot‐dependencies</artifactId>
  <version>1.5.9.RELEASE</version>
  <relativePath>../../spring‐boot‐dependencies</relativePath>
</parent>
他来真正管理Spring Boot应用里面的所有依赖版本；

	<properties>
		<!-- Dependency versions -->
		<activemq.version>5.14.5</activemq.version>
		<antlr2.version>2.7.7</antlr2.version>
		<appengine-sdk.version>1.9.59</appengine-sdk.version>
		<artemis.version>1.5.5</artemis.version>
		<aspectj.version>1.8.13</aspectj.version>
		。。。。。。。。。。
	<dependencyManagement>
		<dependencies>
			<!-- Spring Boot -->
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot</artifactId>
				<version>1.5.9.RELEASE</version>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot</artifactId>
				<type>test-jar</type>
				<version>1.5.9.RELEASE</version>
			</dependency>	
            ......................
他定义了各种适用这个springBoot版本的各种组件依赖的版本号   和帮我导入了需要的springBoot基础核心依赖
他被称为Spring Boot的版本仲裁中心；
以后我们导入依赖默认是不需要写版本；（没有在dependencies里面管理的依赖自然需要声明版本号）            
```

**2、启动器(真正帮我导入依赖)**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring‐boot‐starter‐web</artifactId>
</dependency>
spring-boot-starter-web：
spring-boot-starter：spring-boot场景启动器；帮我们导入了web模块正常运行所依赖的组件；

Spring Boot将所有的功能场景都抽取出来，做成一个个的starters（启动器），
当我们需要某个场景的依赖的时候，就只需要在项目里面引入这些starter
相关场景的所有需要的一系列依赖都会导入进来。要用什么功能就导入什么场景的启动器
```

**3:主程序类，主入口类**

```java
/**
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 */
@SpringBootApplication
public class HelloWorldMainApplication {
    public static void main(String[] args) {
        // Spring应用启动起来
        SpringApplication.run(HelloWorldMainApplication.class,args);
    }
}
```

@**SpringBootApplication**: Spring Boot应用标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot
就应该运行这个类的main方法来启动SpringBoot应用；

```java
=============@SpringBootApplication =====================
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
      @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
      @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
}
=============@SpringBootApplication =====================
    
@SpringBootConfiguration:Spring Boot的配置类；(springBoot注解)
标注在某个类上，表示这是一个Spring Boot的配置类；
@SpringBootConfiguration:的底层是@Configuration注解

@Configuration:配置类上来标注这个注解；(spring底层注解)
配置类 ----- 配置文件；配置类也是容器中的一个组件；@Component

@EnableAutoConfiguration：开启自动配置功能；
以前我们需要配置的东西，Spring Boot帮我们自动配置；
(以前我们扫描组件(包扫描)：需要进行xml配置 或者注解@CompentScan，现在无需做任何操作，)

```

**@EnableAutoConfiguration**告诉SpringBoot开启自动配置功能；这样自动配置才能生效；

```java
=============@EnableAutoConfiguration =====================
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
}
=============@EnableAutoConfiguration =====================
@AutoConfigurationPackage：自动配置包
@AutoConfigurationPackage注解的核心是：@Import(AutoConfigurationPackages.Registrar.class)：
Spring的底层注解@Import，给容器中导入一个组件；导入的组件由
AutoConfigurationPackages.Registrar.class
    
=================AutoConfigurationPackages.Registrar.class=====================
核心代码是： registerBeanDefinitions 注册bean定义信息
public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
AutoConfigurationPackages.register(registry, 
(new AutoConfigurationPackages.PackageImport(metadata)).getPackageName());
 }
 
//new AutoConfigurationPackages.PackageImport(metadata)).getPackageName()
//metadata： 标注注解的源信息。
//@AutoConfigurationPackage 是@SpringBootApplication的底层注解，所以这里getPackageName()就是拿去标注了@SpringBootApplication启动注解的类所在的包名，将其包下面的所有组件注册在IOC容器中
=================AutoConfigurationPackages.Registrar.class=====================  
所以@AutoConfigurationPackage注解的含义就是：将主配置类（@SpringBootApplication标注的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器；

所以在HelloWorld中我们可以扫描到Controller，因为他们是在启动包下
```

@EnableAutoConfiguration 下还有一个注解

@Import(EnableAutoConfigurationImportSelector.class)

```java
@Import(EnableAutoConfigurationImportSelector.class)
@Import 是spring底层的注解。就是给容器中导入组件
导入EnableAutoConfigurationImportSelector：导入哪些组件的选择器；
将所有需要导入的组件以全类名的方式返回；这些组件就会被添加到容器中；
会给容器中导入非常多的自动配置类（xxxAutoConfiguration）；就是给容器中导入各种场景需要的所有组件，
并配置好这些组件；有了这些自动配置类，免去了我们手动编写配置注入功能组件等的工作；
```

![JAVA_SPRINGBOOT1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT1.png?raw=true)

```java
这些自动配置类是通过  
SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class,classLoader)；获取的
这个方法就是从从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值

总结：
Spring Boot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值，将
这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作(为什么我们的web项目什么都没有配配置就能启动了，就是WebMvcAutoConfiguration 帮我们配置了)；以前我们需要自己配置的东西，自动配置类都帮我们；
J2EE的整体整合解决方案和自动配置都在spring-boot-autoconfigure-1.5.9.RELEASE.jar；
@Configuration
//................
public class WebMvcAutoConfiguration {
//.....................
    @Bean
    @ConditionalOnMissingBean({HiddenHttpMethodFilter.class})
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {}
    
    
        @Bean
        @ConditionalOnMissingBean
        public InternalResourceViewResolver defaultViewResolver() {
            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
            resolver.setPrefix(this.mvcProperties.getView().getPrefix());
            resolver.setSuffix(this.mvcProperties.getView().getSuffix());
            return resolver;
        }

        @Bean
        @ConditionalOnBean({View.class})
        @ConditionalOnMissingBean
        public BeanNameViewResolver beanNameViewResolver() {
            BeanNameViewResolver resolver = new BeanNameViewResolver();
            resolver.setOrder(2147483637);
            return resolver;
        }

        @Bean
        @ConditionalOnBean({ViewResolver.class})
        @ConditionalOnMissingBean(
            name = {"viewResolver"},
            value = {ContentNegotiatingViewResolver.class}
        )
//.....................
}

WebMvcAutoConfiguration 是一个配置类，等同于我们之前写的springMvc.xml。 里面配置了各种@bean 组件，包括Filter和 XX 视图解析器等等我们以前在springMvc.xml 中配置的信息,现在WebMvcAutoConfiguration都已经帮我们配置好了，我们直接拿去使用就可以了。
```

### 快速创建Spring Boot项目

```
使用Spring Initializer快速创建Spring Boot项目
1、IDEA：使用 Spring Initializer快速创建项目
IDE都支持使用Spring的项目创建向导快速创建一个Spring Boot项目；
选择我们需要的模块(web)；向导会联springBoot官网帮我创建Spring Boot项目；

主程序生成好了，我们只需要我们自己的逻辑
默认生成的resources文件夹中目录结构
    static：保存所有的静态资源； js css images；类似以前web中的webContent
    templates：保存所有的模板页面（因为Spring Boot默认jar包使用的是嵌入式的Tomcat，默认不支持JSP页
              面）；可以使用模板引擎（freemarker、thymeleaf）；springBoot中常使用的是thymeleaf         
    application.properties：Spring Boot应用的配置文件；可以修改一些默认设置；
2、STS使用 Spring Starter Project快速创建项目
```

## springBoot的配置

### 配置文件

```xml
springBoot使用一个全局的配置文件，《全局配置文件名》是固定的；非全局的配置文件名可以自己定义
•application.properties
•application.yml

配置文件的作用：修改SpringBoot自动配置的默认值
因为我们知道，在创建springBoot项目的时候，我们没有做任何的配置，全是靠springBoot的默认配置启动，所以我们需要自定义特性化配置来修改默认的配置满足我们的特殊要求。

在springBoot中常使用的是application.yml 这样一种配置文件，类似于配置以前的xml，不过他有自己的特殊语法
YAML：配置例子
server:
  port: 8081
XML：
<server>
    <port>8081</port>    
</server>
YAML语法：
基本语法
k:(空格)v：表示一对键值对（空格必须有）；
以空格的缩进来控制层级关系；只要是左对齐的一列数据，都是同一个层级的  属性和值也是大小写敏感；

k: v：字面直接来写；
字符串默认不用加上单引号或者双引号；
""：双引号；不会转义字符串里面的特殊字符；特殊字符会作为本身想表示的意思
name: "zhangsan \n lisi"：输出；zhangsan 换行 lisi
''：单引号；会转义特殊字符，特殊字符最终只是一个普通的字符串数据
name: ‘zhangsan \n lisi’：输出；zhangsan \n lisi
```

####   yml 配置文件值注入

```java
配置文件：
person:
    lastName: hello
    age: 18
    boss: false
    birth: 2017/12/12
    maps: {k1: v1,k2: 12}
    lists:
      ‐ lisi
      ‐ zhaoliu
    dog:
      name: 小狗
      age: 12
          
javabean：
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
}
方式一：@ConfigurationProperties
/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *      prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
 *
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
}

总结：要读取配置文件中配置的属性值
1：使用一个javaBean来接收配置文件的值
2：要想让javaBean能够接收配置文件中的属性值
  需要使用@ConfigurationProperties注解告诉springBoot本类中的所有属性和配置文件中相关的配置进行绑定
  还要让这个javaBean在Spring IOC容器中被注册。  加上 @Component
3：通过@ConfigurationProperties(prefix = "person")的 prefix 指定这个javaBean绑定配置文件中哪些属性值 
  注意： 这个javaBean中的所有属性都需要能够在配置文件中找到
  
4： 可以在pom.xml中 导入配置文件处理器，配置文件进行绑定就会有提示
   <!‐‐导入配置文件处理器，配置文件进行绑定就会有提示‐‐>
<dependency>        
    <groupId>org.springframework.boot</groupId>            
    <artifactId>spring‐boot‐configuration‐processor</artifactId>            
    <optional>true</optional>            
</dependency>

方式二：@Value
他对应的是以前在xml配置文件配置bean的value
<bean class="Person">
      <property name="lastName" value="字面量/${key}从环境变量、配置文件中获取值/#{SpEL}"></property>
 <bean/>
@Value 能取的值有 字面量/${key}从环境变量、配置文件中获取值/#{SpEL}
他可以自动从配置文件中取值

在java属性值上加注解即可
    @Value("${person.last‐name}")
    private String lastName;
    @Value("#{11*2}")
    private Integer age;

注意： 
这里@ConfigurationProperties ，@Value 都只能够获取的全局配置文件中的属性值
如果我们需要获取其他配置文件中属性值就需要在获取属性的类山注解
@PropertySource(value = {"classpath:XXXX"})
```

@Value获取值和@ConfigurationProperties获取值比较

|                      | @ConfigurationProperties | @Value     |
| -------------------- | ------------------------ | ---------- |
| 功能                 | 批量注入配置文件中的属性 | 一个个指定 |
| 松散绑定（松散语法） | 支持                     | 不支持     |
| SpEL                 | 不支持                   | 支持       |
| JSR303数据校验       | 支持                     | 不支持     |
| 复杂类型封装         | 支持                     | 不支持     |

配置文件yml还是properties他们都能获取到值；

我们应该使用哪种方式获取配置文件中的值？

如果说，我们只是在某个业务逻辑中需要获取一下配置文件中的某项值，使用@Value；

如果说，我们需要使用大量的配置文件中的值，我们就可以使用@ConfigurationProperties。 专门编写了一个javaBean来和配置文件进行映射

#### @PropertySource

@PropertySource：加载指定的配置文件

当我们的配置信息不断变多的时候，我们需要将和启动配置无关的配置抽取出来作为一个单独的配置文件

但是抽取出来，各种类似@ConfigurationProperties ，@Value就无法获取我们配置文件中的值，

这时候就需要@PropertySource：加载指定的配置文件

```java
@PropertySource(value = {"classpath:person.properties"})
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    @Value("true")
    private Boolean boss
}

注意： @PropertySource 的属性值是一个数组，说明我们可以同时导入多个配置文件
```

#### @ImportResource

@ImportResource：导入Spring的配置文件，让配置文件里面的内容生效；
Spring Boot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别；想让Spring的配置文件生效，加载进来就需要将`@ImportResource` 注解标注在一个配置类上 (通常标注在启动类上，他也是一个配置类)

```java

springBoot中不建议我们编写Spring的配置文件，他使用配置类@Configuration 代替Spring配置文件。 
使用@Bean给容器中添加底层组件
但是某些需求需要使用已经写好了的spring配置文件，我们就可以使用@ImportResource
@ImportResource(locations = {"classpath:beans.xml"})
导入Spring的配置文件让其生效
```

#### 配置文件中占位符

我们在配置文件中可以使用占位符${XXX}

```xml
配置文件中的占位符有3个作用
1：使用springBoot默认提供的一些随机值
${random.value}、${random.int}、${random.long}
${random.int(10)}、${random.int[1024,65536]}
举例： 	person.last‐name=张三${random.uuid}

2：使用占位符来给新定义的key使用之前已经定义好的变量的值
person.hello=XXX
person.dog.name=${person.hello}_dog

3:占位符获取之前配置的值，如果没有可以是用:指定默认值
person.dog.name=${person.hello:hello}_dog
这里默认值是hello
```

#### Profile 多环境支持

我们在开发，测试，正式运行的时候往往需要在多套不同的运行环境下运行，这就需要我们有多个配置文件

```java
为了方便我们切换环境：
springBoot主配置文件(全局配置文件)编写的时候，文件名支持 application-{profile}.properties/yml
来实现不同环境的配置文件{profile}就是我们定义的环境标识，默认使用application.properties/yml的配置；

激活指定profile
1、在配置文件中指定 spring.profiles.active=dev
2、命令行：
java -jar spring-boot-02-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev；
可以直接在测试的时候，配置传入命令行参数
3、虚拟机参数；
-Dspring.profiles.active=dev
```



### 加载顺序

#### 配置文件加载位置

```java
springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文
件
–classpath:/ (这里/是src/ 或者resource/)
–classpath:/config/
–file:./ (./ 就是当前项目的根目录，和src同级)
–file:./config/
依次扫描，后扫描的会覆盖之前扫描的，他们都会同时加载，互补配置互补配置；
我们还可以通过spring.config.location来改变默认的配置文件位置 ,他的应用场景就是在项目打包后制定通过控制台制定我们的配置文件 （项目已经打包了，后来在运行的时候需要修改一些配置）
java -jar spring-boot-02-config-02-0.0.1-SNAPSHOT.jar --spring.config.location=G:/application.properties
指定配置文件和默认加载的这些配置文件共同起作用形成互补配置；

同样，我们也可以在外部修改配置， 运行时通过命令指定配置
```



### 自动配置原理

配置文件到底能写什么？怎么写？
<https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/#common-application-properties>

自动配置原理；

```java
1）、SpringBoot启动的时候加载主配置类，开启了自动配置功能 @EnableAutoConfiguration
2） 他会将大量的xxxAutoConfiguration 加入到容器中

以HttpEncodingAutoConfiguration（Http编码自动配置）为例解释自动配置原理；
==============HttpEncodingAutoConfiguration===========
//表示这是一个配置类，以前编写的配置文件一样，也可以给容器中添加组件
@Configuration
//启动指定类的ConfigurationProperties功能；将配置文件中对应的值和HttpEncodingProperties绑定起来；
//HttpEncodingProperties 中 @ConfigurationProperties(prefix = "spring.http.encoding") 会将配置文件中配置的spring.http.encoding下面的值和其属性值进行绑定   
//并把HttpEncodingProperties加入到ioc容器中，
@EnableConfigurationProperties(HttpEncodingProperties.class)
//Spring底层@Conditional注解（Spring注解版），根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效；    判断当前应用是否是web应用，如果是，当前配置类生效    
@ConditionalOnWebApplication
//判断当前项目有没有CharacterEncodingFilter这个类，如果有这个配置类才会生效  
//CharacterEncodingFilter；SpringMVC中进行乱码解决的过滤器；
@ConditionalOnClass(CharacterEncodingFilter.class)  

//判断配置文件中是否存在某个配置  spring.http.encoding.enabled；如果不存在，
//matchIfMissing =true判断也是成立的
//即使我们配置文件中不配置pring.http.encoding.enabled=true，也是默认生效的；    
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing =
true)  
public class HttpEncodingAutoConfiguration {
 
   //他已经和SpringBoot的配置文件映射了  
   private final HttpEncodingProperties properties;
    //只有一个有参构造器的情况下，参数的值就会从容器中拿，  
    //对应@EnableConfigurationProperties(HttpEncodingProperties.class) 
   public HttpEncodingAutoConfiguration(HttpEncodingProperties properties) {  
		this.properties = properties;        
	}    
 
    @Bean   //给容器中添加一个组件，这个组件的某些值需要从properties中获取
    ////判断容器没有这个组件？  注意  这里是  @ConditionalOnMissingBean
    //HttpEncodingAutoConfiguration 上注解的是@EnableConfigurationProperties(HttpEncodingProperties.class)
   //是判断导入了web等Filter相关包后才会让这个注解生效，@ConditionalOnMissingBean 是为了防止自定义Filter 
    @ConditionalOnMissingBean(CharacterEncodingFilter.class)    
    public CharacterEncodingFilter characterEncodingFilter() {    
            CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();        
            filter.setEncoding(this.properties.getCharset().name());        
            filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));        
            filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));        
            return filter;        
    } 
 根据当前不同的条件判断，决定这个配置类是否生效？一但这个配置类生效；这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的；   
==============HttpEncodingAutoConfiguration===========
3：所有在配置文件中能配置的属性都是在xxxxProperties类中封装者；配置文件能配置什么就可以参照某个功
能对应的这个属性类 
////从配置文件中获取指定的值和bean的属性进行绑定     
@ConfigurationProperties(prefix = "spring.http.encoding")  
public class HttpEncodingProperties {
   public static final Charset DEFAULT_CHARSET = Charset.forName("UTF‐8");
  。。。。。。。

 总结：
1）、SpringBoot启动会加载大量的自动配置类
2）、我们看我们需要的功能有没有SpringBoot默认写好的自动配置类；
3）、我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件有，我们就不需要再来配置了）
4）、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们就可以在配置文件中指定这
些属性的值；
 xxxxAutoConfigurartion：自动配置类；给容器中添加组件
 xxxxProperties:封装配置文件中相关属性；     
```

#### @Conditional派生注解

Spring原生的@Conditional:必须是@Conditional指定的条件成立，才给容器中添加组件，配置配里面的所有内容才生效；

| @Conditional扩展注解            | 作用（判断是否满足当前指定条件）                 |
| ------------------------------- | ------------------------------------------------ |
| @ConditionalOnJava              | 系统的java版本是否符合要求                       |
| @ConditionalOnBean              | 容器中存在指定Bean；                             |
| @ConditionalOnMissingBean       | 容器中不存在指定Bean；                           |
| @ConditionalOnExpression        | 满足SpEL表达式指定                               |
| @ConditionalOnClass             | 系统中有指定的类                                 |
| @ConditionalOnMissingClass      | 系统中没有指定的类                               |
| @ConditionalOnSingleCandidate   | 容器中只有一个指定的Bean，或者这个Bean是首选Bean |
| @ConditionalOnProperty          | 系统中指定的属性是否有指定的值                   |
| @ConditionalOnResource          | 类路径下是否存在指定资源文件                     |
| @ConditionalOnWebApplication    | 当前是web环境                                    |
| @ConditionalOnNotWebApplication | 当前不是web环境                                  |
| @ConditionalOnJndi              | JNDI存在指定项                                   |

**自动配置类必须在一定的条件下才能生效；** 所以虽然我们驱动类@SpringBootApplication 注册了大量的XXXAutoConfiguration,但是他们大多数不满足条件，对应的配置不会执行

我们怎么知道哪些自动配置类生效？

我们可以通过启用  debug=true属性；来让控制台打印自动配置报告，这样我们就可以很方便的知道哪些自动配置类生效；Positive matches:（自动配置类启用的）Negative matches:（没有启动，没有匹配成功的自动配置类）

## springBoot 日志



### 日志

什么是日志？ 就是在程序运行的时记录的一些需要被开发人员看到的信息。 

日志框架的演变：

```
 小张；开发一个大型系统；
		1、System.out.println("")；将关键数据打印在控制台；去掉？写在一个文件？

		2、框架来记录系统的一些运行时信息；日志框架 ；  zhanglogging.jar；

		3、高大上的几个功能？异步模式？自动归档？xxxx？  zhanglogging-good.jar？

		4、将以前框架卸下来？换上新的框架，重新修改之前相关的API；zhanglogging-prefect.jar；

		5、JDBC---数据库驱动；
			写了一个统一的接口层；日志门面（日志的一个抽象层）；logging-abstract.jar；
			给项目中导入具体的日志实现就行了；我们之前的日志框架都是实现的抽象层；

总结： 
什么是日志框架？
日志的目的就是为了记录在程序运行时的一些程序信息，最简单的方式就是System.out.println。但是这样存在一个问题，就是太多的如业务逻辑无关的代码出现在我们项目中，且无法根据特定的情况显示特定的信息，扩展性太差。 如果我们不想显示这些日志信息还需要依次去删除业务代码。 更不提一些高大上的功能：异步模式，自动归档 等

所以我们将记录日志抽离出来，让其能够实现一些高级功能。 这就是日志框架
```

市面上的日志框架：`JUL、JCL、Jboss-logging、logback、log4j、log4j2、slf4j....`

左边选一个门面（抽象层）、右边来选一个实现；

| 日志门面  （日志的抽象层）                                   | 日志实现                                             |
| ------------------------------------------------------------ | ---------------------------------------------------- |
| ~~JCL（Jakarta  Commons Logging： common-logging.jar）~~    SLF4j（Simple  Logging Facade for Java）    **~~jboss-logging~~** | Log4j  JUL（java.util.logging）  Log4j2  **Logback** |

选择日志门面：  SLF4J；

Jboss-Loging:使用场景太少了，生来就不是用来给我们java程序员用来记录日志的。

JCL：一直都是一些特定的框架在使用它，且太古老了，很久没有更新了(最近一次更新2014年)。

日志实现：Logback；

Log4j 和Logback 都是同一个作者写的。Log4j存在性能问题，作者觉得对log4j进行升级改动太大，所以重写了一个日志框架Logback。 并且他想到以后可能又要写新的日志框架，就抽象出一个日志门面SLF4J

Log4j2 ： 他并不是Log4j  的升级版，而是Apache新开发的一个日志框架，各方面设计的都很好，但是就是太好了，各种框架都还不能完全适应他。

SpringBoot：底层是Spring框架，Spring框架默认是用JCL

​	**==SpringBoot选用 SLF4j和logback；==**

#### 如何在系统中使用SLF4j   

https://www.slf4j.org

开发的时候，日志记录方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法；

给系统里面导入slf4j的jar和  logback的实现jar

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

![JAVA_SPRINGBOOT2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT2.png?raw=true)

每一个日志的实现框架都有自己的配置文件。使用slf4j以后，**配置文件还是做成日志实现框架自己本身的配置文件**；

**遗留问题**

统一日志记录，即使是别的框架和我一起统一使用slf4j进行输出？

a（slf4j+logback）: Spring（commons-logging）、Hibernate（jboss-logging）、MyBatis、xxxx

![JAVA_SPRINGBOOT3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT3.png?raw=true)

```
如何让系统中所有的日志都统一到slf4j；
1、将系统中其他日志框架先排除出去；
2、用中间包来替换原有的日志框架；
3、我们导入slf4j其他的实现
```

#### springBoot 中使用日志

springBoot每一个场景启动器都会依赖spring-boot-starter

![JAVA_SPRINGBOOT4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT4.png?raw=true)

```java
1）、SpringBoot底层也是使用slf4j+logback的方式进行日志记录
2）、SpringBoot也把其他的日志都替换成了slf4j；
3）、中间替换包
public abstract class LogFactory {

    static String UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J = "http://www.slf4j.org/codes.html#unsupported_operation_in_jcl_over_slf4j";

    static LogFactory logFactory = new SLF4JLogFactory();
 4）、如果我们要引入其他框架？一定要把这个框架的默认日志依赖移除掉？
 比如：Spring框架用的是commons-logging；
 dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<exclusions>
				<exclusion>
					<groupId>commons-logging</groupId>
					<artifactId>commons-logging</artifactId>
				</exclusion>
			</exclusions>
</dependency>
==SpringBoot能自动适配所有的日志，而且底层使用slf4j+logback的方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架排除掉即可；==
```

springBoot中日志的使用

```java
 1、默认配置
SpringBoot默认帮我们配置好了日志；默认给我们使用的是info级别的
//记录器
	Logger logger = LoggerFactory.getLogger(getClass());
	@Test
	public void contextLoads() {
		//日志的级别；
		//由低到高   trace<debug<info<warn<error
		//可以调整输出的日志级别；日志就只会在这个级别以以后的高级别生效
		logger.trace("这是trace日志...");
		logger.debug("这是debug日志...");
		//SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
		logger.info("这是info日志...");
		logger.warn("这是warn日志...");
		logger.error("这是error日志...");
	}
2:日志输出格式：
		%d表示日期时间，
		%thread表示线程名，
		%-5level：级别从左显示5个字符宽度
		%logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
		%msg：日志消息，
		%n是换行符
    -->
    默认的日志格式：
    %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
3:SpringBoot修改日志的默认配置
 logging.level.com.atguigu=trace

#logging.path=
# 不指定路径在当前项目下生成springboot.log日志
# 可以指定完整的路径；
#logging.file=G:/springboot.log

# 在当前磁盘的根路径下创建spring文件夹和里面的log文件夹；使用 spring.log 作为默认文件
logging.path=/spring/log

#  在控制台输出的日志的格式
logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
# 指定文件中日志输出的格式
logging.pattern.file=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} ==== %msg%n
```

| logging.file | logging.path | Example  | Description                        |
| ------------ | ------------ | -------- | ---------------------------------- |
| (none)       | (none)       |          | 只在控制台输出                     |
| 指定文件名   | (none)       | my.log   | 输出日志到my.log文件               |
| (none)       | 指定目录     | /var/log | 输出到指定目录的 spring.log 文件中 |

```java
4：指定配置
给类路径下放上每个日志框架自己的配置文件即可；SpringBoot就不使用他默认配置的了。
我们最好是给日志文件命名上加上XXX-spring.xml ，这样能被springBoot识别，就可以使用springBoot的profile功能
如：
logback.xml：直接就被Logback日志框架底层实现类识别了；
logback-spring.xml：日志框架就不直接加载日志的配置项，由SpringBoot解析日志配置，可以使用SpringBoot的高级Profile功能 ，可以使用springProfile标签
```

| Logging System          | Customization                                                |
| ----------------------- | ------------------------------------------------------------ |
| Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml` or `logback.groovy` |
| Log4j2                  | `log4j2-spring.xml` or `log4j2.xml`                          |
| JDK (Java Util Logging) | `logging.properties`                                         |

如：

```xml
<appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <springProfile name="dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ----> [%thread] ---> %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
            <springProfile name="!dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==== [%thread] ==== %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
        </layout>
    </appender>
如果使用logback.xml作为日志配置文件，还要使用profile功能，会有以下错误
 `no applicable action for [springProfile]`
```

**5：切换日志框架**

通过分析依赖树，可以按照slf4j的日志适配图，进行相关的切换

使用 <exclusion> 标签排除依赖

slf4j+log4j的方式； (几乎不用，og4j就是因为性能问题才开发Logback，切换回去没意义)

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <exclusions>
    <exclusion>
      <artifactId>logback-classic</artifactId>
      <groupId>ch.qos.logback</groupId>
    </exclusion>
    <exclusion>
      <artifactId>log4j-over-slf4j</artifactId>
      <groupId>org.slf4j</groupId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
</dependency>
```

log4j2：

```xml
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>spring-boot-starter-logging</artifactId>
                    <groupId>org.springframework.boot</groupId>
                </exclusion>
            </exclusions>
        </dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```



## springBoot WEB

springBoot 大量的使用了自动配置原理，正因为有自动配置我们才可以只做少量操作就能完成一个web应用

### springBoot对web中静态资源的处理

spring和springMVC打的都是war包，对静态资源的处理都是放在webapp这个问题下

springBoot是打的jar包： 他处理静态资源的方式：

```java
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
            } else {
                Integer cachePeriod = this.resourceProperties.getCachePeriod();
                if (!registry.hasMappingForPattern("/webjars/**")) {
                    this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(cachePeriod));
                }

                String staticPathPattern = this.mvcProperties.getStaticPathPattern();
                if (!registry.hasMappingForPattern(staticPathPattern)) {
                    this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(this.resourceProperties.getStaticLocations()).setCachePeriod(cachePeriod));
                }

            }
        }

springboot对静态资源的映射有两种方式
1： 直接放在项目的文件夹下
registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(this.resourceProperties.getStaticLocations())}
staticPathPattern // ---- "/** "
"/**" 访问当前项目的任何资源，都去（静态资源的文件夹）找映射
"classpath:/META‐INF/resources/",
"classpath:/resources/",
"classpath:/static/", (最常用，resources/static中存放静态资源文件，springBoot构建的项目中默认带着这个目录 )
"classpath:/public/"
"/"：当前项目的根路径  (项目下的java，resources都是/)
    
2： 以Maven的方式引入静态资源
webjars：以jar包的方式引入静态资源； http://www.webjars.org/
<!‐‐引入jquery‐webjar‐‐>在访问的时候只需要写webjars下面资源的名称即可
<dependency>        
    <groupId>org.webjars</groupId>            
    <artifactId>jquery</artifactId>            
    <version>3.3.1</version>            
</dependency>
" /webjars/**"  ===> // 都去类路径下的classpath:/META-INF/resources/webjars/

registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}
```

![JAVA_SPRINGBOOT5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT5.png?raw=true)

### 引入Thymeleaf 模板引擎

![JAVA_SPRINGBOOT6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT6.png?raw=true)

引入thymeleaf

```xml
<dependency>        
    <groupId>org.springframework.boot</groupId>            
    <artifactId>spring‐boot‐starter‐thymeleaf</artifactId> 
     <!--    springboot1.5.9 默认使用 2.1.6  -->
</dependency>        
切换thymeleaf版本，在properties中配置替版本
<properties>
    <thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>        
    <!‐‐ 布局功能的支持程序  thymeleaf3主程序  layout2以上版本 ‐‐>        
    <!‐‐ thymeleaf2   layout1 ， layout2 以后才支持 thymeleaf3 ‐‐>        
    <thymeleaf‐layout‐dialect.version>2.2.2</thymeleaf‐layout‐dialect.version>        
</properties>
```

Thymeleaf使用& 语法

```java
从自动配置中我们可以看出默认规则
@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING = Charset.forName("UTF‐8");    
    private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");    
    public static final String DEFAULT_PREFIX = "classpath:/templates/";    
    public static final String DEFAULT_SUFFIX = ".html";
}
只要我们把HTML页面放在classpath:/templates/，thymeleaf就能自动渲染；
(所以我们将常用的方式是将我们的静态模板放入在classpath:/templates/ 下 ，springBoot构建的项目默认带有这个目录)
 导入thymeleaf的名称空间：<html lang="en" xmlns:th="http://www.thymeleaf.org"> 编写的时候就有语法提示   
```

![JAVA_SPRINGBOOT7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT7.png?raw=true)

```java
使用：
<!--th:属性   设置任意属性-->
<div id="div01" class="myDiv" th:id="${hello}" th:class="${hello}" th:text="${hello}">这是显示欢迎信息</div>

还有就是各种表达式的运用，类似于jsp
```



### springBoot MVC默认配置

```java
SpringBoot对SpringMVC的做了哪些默认配置:
1： Inclusion of `ContentNegotiatingViewResolver` and `BeanNameViewResolver` beans.
自动配置了ViewResolver（视图解析器：根据方法的返回值得到视图对象（View），视图对象决定如何渲染（转发？重定向？））
ContentNegotiatingViewResolver：组合所有的视图解析器的；
如何定制：我们可以自己给容器中添加一个视图解析器；SpringBoot自动的将其组合进来；

2：Support for serving static resources, including support for WebJars (see below).静态资源文件夹路径,webjars
Static `index.html` support. 静态首页访问
 Custom `Favicon` support (see below).  favicon.ico

3： 自动注册了 of `Converter`, `GenericConverter`, `Formatter` beans.
Converter：转换器；  public String hello(User user)：类型转换使用Converter
Formatter  格式化器；  2017.12.17===Date；

@Bean
@ConditionalOnProperty(prefix = "spring.mvc", name = "date-format")//在文件中配置日期格式化的规则
public Formatter<Date> dateFormatter() {
	return new DateFormatter(this.mvcProperties.getDateFormat());//日期格式化组件
}
自己添加的格式化器转换器，我们只需要放在容器中即可

4：Support for `HttpMessageConverters` (see below).
HttpMessageConverter：SpringMVC用来转换Http请求和响应的；User---Json；
`HttpMessageConverters` 是从容器中确定；获取所有的HttpMessageConverter；
自己给容器中添加HttpMessageConverter，只需要将自己的组件注册容器中（@Bean,@Component）

5： Automatic registration of `MessageCodesResolver` (see below).定义错误代码生成规则
 
6：Automatic use of a `ConfigurableWebBindingInitializer` bean (see below).
=我们可以配置一个ConfigurableWebBindingInitializer来替换默认的；（添加到容器）==
  作用:初始化WebDataBinder web数据绑定器；
  请求数据=====JavaBean；


如何修改springBoot的默认配置
1）、SpringBoot在自动配置很多组件的时候，先看容器中有没有用户自己配置的（@Bean、@Component）如果有就用用户配置的，如果没有，才自动配置(直接放组件中就可以了，springBoot会通过BeanFactory自动引用)；如果有些组件可以有多个（ViewResolver）将用户配置的和自己默认的组合起来；一般默认优先使用用户自己默认的

2）、在SpringBoot中会有非常多的xxxConfigurer帮助我们进行扩展配置
实际开发中，仅靠springBoot的默认配置是不够用的。
以前在开发mvc的时候，我们可以通过编写一个配置文件来配置视图映射， 拦截器等等
  <mvc:view‐controller path="/hello" view‐name="success"/>
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/hello"/>
            <bean></bean>
        </mvc:interceptor>
    </mvc:interceptors>
  现在在springBoot中我们通过
编写一个配置类（@Configuration），是WebMvcConfigurerAdapter类型；不能标注@EnableWebMvc; (如果添加启用了这个注解，会导致自动注解上的@ConditionalOnMissingBean(WebMvcConfigurationSupport.class) 这个注解生效，导致不能自动配置，就意味着SpringBoot对SpringMVC的自动配置不需要了，所有都是我们自己配置；所有的SpringMVC的自动配置都失效了)
以前我们在配置文件中配置的都可以通过这个类来进配置
既保留了所有的自动配置，也能用我们扩展的配置 
//使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       // super.addViewControllers(registry);
        //浏览器发送 /atguigu 请求来到 success
        registry.addViewController("/atguigu").setViewName("success");
    }
}
原理：
1、WebMvcAutoConfiguration是SpringMVC的自动配置类（springBoot mvc默认的自动配置类也是继承WebMvcConfigurerAdapter） 
2、在做其他自动配置(默认)时会导入；@Import(EnableWebMvcConfiguration.class)
3、容器中所有的WebMvcConfigurer都会一起起作用；
4、我们的配置类也会被调用；
效果：SpringMVC的自动配置和我们的扩展配置都会起作用；

3）、在SpringBoot中会有很多的xxxCustomizer帮助我们进行定制配置

```

### springBoot 配置默认的Servlet容器

springBoot默认使用Tomcat作为嵌入式的Servlet容器；(以前需要打包成war包放入到外部的容器中，现在不需要了)

![JAVA_SPRINGBOOT8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT8.png?raw=true)

问题：

1： 以前我们使用外置的Tomcat容器，我们可以定制和修改config配置来达到我们的生产要求，现在使用内置的容器，我们如何来修改容器配置

```java
两种方式，本质上都是调用EmbeddedServletContainerCustomizer定制器中设置属性的方法
1：在配置文件中修改和server有关的配置（本质修改的是ServerProperties，他实现了EmbeddedServletContainerCustomizer）
===========================
server.port=8081
server.context‐path=/crud
server.tomcat.uri‐encoding=UTF‐8
//通用的Servlet容器设置
server.xxx
//Tomcat的设置
server.tomcat.xxx
===========================
2、在配置类中注入编写的EmbeddedServletContainerCustomizer：嵌入式的Servlet容器的定制器；来修改Servlet容器的配置
@Bean  //一定要将这个定制器加入到容器中
public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
    return new EmbeddedServletContainerCustomizer() {
        //定制嵌入式的Servlet容器相关的规则
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.setPort(8083);
        }
    };
}

```



2： 以前我们是打的war包。Servlet的三大组件 Filter，Servlet，Listener我们都可以在web.xml中进行配置，现在springBoot去掉了web.xml .我们应该如何配置Servlet的三大组件

(这三大组件属于Servlet容器特有，光是写在项目中，或者是简单的使用组件的方式注入到spring容器中都无法成功的容内置的Servlet容器使用，所以springboot为我们单独提供了特有的注册器)

```java
注册三大组件用以下方式
ServletRegistrationBean
FilterRegistrationBean
ServletListenerRegistrationBean

在我们的配置内中配置相关的bean
//注册三大组件
@Bean
public ServletRegistrationBean myServlet(){
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new
MyServlet(),"/myServlet");
    return registrationBean;
}

@Bean
public FilterRegistrationBean myFilter(){
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new MyFilter());
    registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
    return registrationBean;
}

@Bean
public ServletListenerRegistrationBean myListener(){
    ServletListenerRegistrationBean<MyListener> registrationBean = new
ServletListenerRegistrationBean<>(new MyListener());
    return registrationBean;
}

SpringBoot帮我们自动配置SpringMVC的时候，自动的注册SpringMVC的前端控制器；DIspatcherServlet；
DispatcherServletAutoConfiguration中：
@Bean(name = DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
@ConditionalOnBean(value = DispatcherServlet.class, name =
DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
public ServletRegistrationBean dispatcherServletRegistration(
      DispatcherServlet dispatcherServlet) {
   ServletRegistrationBean registration = new ServletRegistrationBean(
         dispatcherServlet, this.serverProperties.getServletMapping());
    //默认拦截： /  所有请求；包扩静态资源，但是不拦截jsp请求；  以前写的/*会拦截jsp
    //可以通过server.servletPath来修改SpringMVC前端控制器默认拦截的请求路径
   
   registration.setName(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
   registration.setLoadOnStartup(
         this.webMvcProperties.getServlet().getLoadOnStartup());
   if (this.multipartConfig != null) {
      registration.setMultipartConfig(this.multipartConfig);
   }
   return registration;
}
```

3： springBoot默认使用的内置Servlet容器是Tomcat，但是因为某些需求，我想使用其他的Servlet容器，如何使用？

![JAVA_SPRINGBOOT9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT9.png?raw=true)

```xml
springBoot 默认支持切换Jetty（支持长连接） Undertow（高性能，无阻塞，但是不支持jsp）

切换非Tomcat容器，默认是Tomcat：
<!‐‐ 引入web模块 ‐‐>
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring‐boot‐starter‐web</artifactId>
   <exclusions>
      <exclusion>
         <artifactId>spring‐boot‐starter‐tomcat</artifactId>
         <groupId>org.springframework.boot</groupId>
      </exclusion>
   </exclusions>
</dependency>
<!‐‐引入其他的Servlet容器‐‐>
<dependency>
    <!--Undertow :<artifactId>spring‐boot‐starter‐undertow</artifactId>-->
   <artifactId>spring‐boot‐starter‐jetty</artifactId>
   <groupId>org.springframework.boot</groupId>
</dependency>

```

4: 嵌入式的Servlet容器是怎么进行工作的？

```
步骤：
1:SpringBoot应用启动运行run方法,其中refreshContext(context);SpringBoot刷新IOC容器【创建IOC容器对象，并初始化容器，创建容器中的每一个组件】；如果是web应用创建AnnotationConfigEmbeddedWebApplicationContext，否则：AnnotationConfigApplicationContext

2：refresh(context);刷新刚才创建好的ioc容器；onRefresh(); web的ioc容器重写了onRefresh方法。web 的ioc容器会创建嵌入式的Servlet容器；createEmbeddedServletContainer();

3：获取嵌入式的Servlet容器工厂，使用容器工厂获取嵌入式的Servlet容器。
SpringBoot根据导入的依赖情况，给容器中添加相应的EmbeddedServletContainerFactory【TomcatEmbeddedServletContainerFactory】容器中某个组件要创建对象就会惊动后置处理器；
EmbeddedServletContainerCustomizerBeanPostProcessor；只要是嵌入式的Servlet容器工厂，后置处理器就工作；
后置处理器，从容器中获取所有的EmbeddedServletContainerCustomizer，调用定制器的定制方法

创建 getEmbeddedServletContainer 的时候就会创建并启动一个Servlet容器（Tomcat）
```

5： 如何使用外置的Servlet容器？ (主要是为了支持jsp)

外置的Servlet容器：外面安装Tomcat---应用war包的方式打包；

**步骤**

1）、必须创建一个war项目；（利用idea创建好目录结构  webapp  / WEB-INF /web.xml）

2）、将嵌入式的Tomcat指定为provided；

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-tomcat</artifactId>
   <scope>provided</scope>
</dependency>
```

3）、必须编写一个**SpringBootServletInitializer**的子类，并调用configure方法

```java
public class ServletInitializer extends SpringBootServletInitializer {

   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       //传入SpringBoot应用的主程序
      return application.sources(SpringBoot04WebJspApplication.class);
   }

}
```

4）、启动服务器就可以使用；

**原理**

jar包：执行SpringBoot主类的main方法，启动ioc容器，创建嵌入式的Servlet容器；

war包：启动服务器，**服务器启动SpringBoot应用**【SpringBootServletInitializer】，启动ioc容器；

## springBoot 与Docker

### Docker是什么？

docker 类似于我们以前了解的虚拟机，但是性能远高于虚拟机 。主要功能是帮助我们简化服务器软件的安装

![JAVA_SPRINGBOOT10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT10.png?raw=true)

docker的最大功能就是将安装好的软件打包成镜像给其他机器用，或者是直接使用其他机器打包好的镜像

```
docker主机(Host)：安装了Docker程序的机器（Docker直接安装在操作系统之上）； 类似于服务器
docker客户端(Client)：连接docker主机进行操作；
docker仓库(Registry)：用来保存各种打包好的软件镜像； 类似于gitHub上的仓库
docker镜像(Images)：软件打包好的镜像；放在docker仓库中；
docker容器(Container)：镜像启动后的实例称为一个容器；容器是独立运行的一个或一组应用 类似于Tomcat启动后就是
                      一个Servlet容器

使用Docker的步骤：
1）、安装Docker
2）、去Docker仓库找到这个软件对应的镜像；
3）、使用Docker运行这个镜像，这个镜像就会生成一个Docker容器；
4）、对容器的启动停止就是对软件的启动停止；
```

常用命令

![JAVA_SPRINGBOOT11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT11.png?raw=true)

**镜像操作**

| 操作 | 命令                                            | 说明                                                     |
| ---- | ----------------------------------------------- | -------------------------------------------------------- |
| 检索 | docker  search 关键字  eg：docker  search redis | 我们经常去docker  hub上检索镜像的详细信息，如镜像的TAG。 |
| 拉取 | docker pull 镜像名:tag                          | :tag是可选的，tag表示标签，多为软件的版本，默认是latest  |
| 列表 | docker images                                   | 查看所有本地镜像                                         |
| 删除 | docker rmi image-id                             | 删除指定的本地镜像                                       |

默认搜索的仓库是docker hub (https://hub.docker.com/)

### 在linux虚拟机上安装docker

步骤：

```shell
1、检查内核版本，必须是3.10及以上
uname -r
2、安装docker
yum install docker
3、输入y确认安装
4、启动docker
[root@localhost ~]# systemctl start docker
[root@localhost ~]# docker -v
Docker version 1.12.6, build 3e8e77d/1.12.6
5、开机启动docker
[root@localhost ~]# systemctl enable docker
Created symlink from /etc/systemd/system/multi-user.target.wants/docker.service to /usr/lib/systemd/system/docker.service.
6、停止docker
systemctl stop docker
```



### 容器操作

软件镜像（QQ启动程序）----运行镜像----产生一个容器（正在运行的软件，运行的QQ）；

步骤：

```shell
1、搜索镜像
[root@localhost ~]# docker search tomcat
2、拉取镜像
[root@localhost ~]# docker pull tomcat
3、根据镜像启动容器
docker run --name mytomcat -d tomcat:latest
4、docker ps  
查看运行中的容器
5、 停止运行中的容器
docker stop  容器的id
6、查看所有的容器
docker ps -a
7、启动容器
docker start 容器id
8、删除一个容器
 docker rm 容器id
9、启动一个做了端口映射的tomcat
[root@localhost ~]# docker run -d -p 8888:8080 tomcat
-d：后台运行
-p: 将主机的端口映射到容器的一个端口    主机端口:容器内部的端口

为什么要做端口映射？
我们安装的软件是安装在docker中，我们访问虚拟机的8080，是访问不到docker中的。所以需要做一个映射，将虚拟机的端口映射到docker容器内部的端口

10、为了演示简单关闭了linux的防火墙
service firewalld status ；查看防火墙状态
service firewalld stop：关闭防火墙
11、查看容器的日志
docker logs container-name/container-id

更多命令参看
https://docs.docker.com/engine/reference/commandline/docker/
可以参考每一个镜像的文档

```

一个镜像可以启动多个容器。多实例

![JAVA_SPRINGBOOT12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT12.png?raw=true)

注意： docker安装mysql的时候必须先指定密码，否则启动后无法使用

```
[root@localhost ~]# docker run --name mysql01 -e MYSQL_ROOT_PASSWORD=123456 -d mysql
b874c56bec49fb43024b3805ab51e9097da779f2f572c22c695305dedd684c5f
[root@localhost ~]# docker ps
#做端口映射
[root@localhost ~]# docker run -p 3306:3306 --name mysql02 -e MYSQL_ROOT_PASSWORD=123456 -d mysql
几个其他的高级操作
docker run --name mysql03 -v /conf/mysql:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
把主机的/conf/mysql文件夹挂载到 mysqldocker容器的/etc/mysql/conf.d文件夹里面
改mysql的配置文件就只需要把mysql配置文件放在自定义的文件夹下（/conf/mysql）

docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
指定mysql的一些配置参数
```

## springBoot 数据访问

springBoot与关系型数据库交互，与各种持久层框架结合

### JDBC

```java
1： 数据源的配置
JDBC： 
导入依赖
         <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
配置文件配置
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://192.168.15.22:3306/jdbc
    driver-class-name: com.mysql.jdbc.Driver

为什么这样简单的配置，就能让springboot获取到数据源dataSource
数据源的相关配置都修改在DataSourceProperties中
DataSourceConfiguration 中更加配置文件中的配置在spring容器中注入不同的数据源

2：容器启动时执行建表sql和插入数据sql
DataSourceAutoConfiguration 在IOC容器启动时给IOC 容器中加入了DataSourceInitializer
/**
 * Bean to handle {@link DataSource} initialization by running {@literal schema-*.sql} 
 **/
class DataSourceInitializer implements ApplicationListener<DataSourceInitializedEvent> {...}
他是一个ApplicationListener
作用：
1）、runSchemaScripts();运行建表语句；
2）、runDataScripts();运行插入数据的sql语句；
要先达到上面的效果默认只需要在类路径下将文件命名为：
schema-*.sql、data-*.sql
默认规则：schema.sql，schema-all.sql；
也可以在配置文件中指定我们要执行的sql语句   
	schema:
      - classpath:department.sql
      指定位置
```

### 配置Druid数据源

(DataSourceConfiguration 为我们默认注入了几个数据源，默认是Tomcat)

但是使用最多的还是Druid数据源，他提供数据监控(他并不由springBoot默认配置)

```java
1: 引入依赖
	<!--引入druid数据源-->
		<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
			<version>1.1.8</version>
		</dependency>
2： 配置文件中配置
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://192.168.15.22:3306/jdbc
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource

# 数据源高级配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
#   配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500

3:Druid数据源并不由springBoot默认提供，所以在配置文件中配置了并不能将属性赋值给Druid数据源。所以我们需要自己写一个配置类，注入Druid数据源。 并且由于配置的属性，与我Druid数据源属性一致，所以在创建Druid数据源的时候可以使用@ConfigurationProperties 取得配置文件中的值，
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
       return  new DruidDataSource();
    }
}   

4： 如果我们要使用Druid数据源 的监控功能，需要配置上 1、配置一个管理后台的Servlet 2、配置一个web监控的filter

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
       return  new DruidDataSource();
    }

    //配置Druid的监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");//默认就是允许所有访问
        initParams.put("deny","192.168.15.21");

        bean.setInitParameters(initParams);
        return bean;
    }


    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }
}

5： 在使用管理后台的时候，直接访问根据配置管理后台的Servlet时配置的url即可
```

### springBoot整合Mybatis

（他是在配置了上面JDBC和数据源的基础上进行整合的）

![JAVA_SPRINGBOOT13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT13.png?raw=true)

```java
1： 引入依赖
        <dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.1</version>
		</dependency>
2：注解版自定义Mybatis全局配置规则
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){

            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}

3： 配置文件自定义Mybatis全局配置规则
在全局文件中制定我们Mybatis的全局配置文件和sql映射文件的位置
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml 指定全局配置文件的位置
  mapper-locations: classpath:mybatis/mapper/*.xml  指定sql映射文件的位置


```

### springData与JPA

![JAVA_SPRINGBOOT14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT14.png?raw=true)

**整合SpringData JPA**

JPA:ORM（Object Relational Mapping）；

1）、编写一个实体类（bean）和数据表进行映射，并且配置好映射关系；

```java
//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "tbl_user") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class User {

    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

    @Column(name = "last_name",length = 50) //这是和数据表对应的一个列
    private String lastName;
    @Column //省略默认列名就是属性名
    private String email;
```

2）、编写一个Dao接口来操作实体类对应的数据表（Repository）

```java
//继承JpaRepository来完成对数据库的操作
//第二个泛型传入的是主键的类型
public interface UserRepository extends JpaRepository<User,Integer> {
}

```

3）、基本的配置JpaProperties

```yaml
spring:  
 jpa:
    hibernate:
#     更新或者创建数据表结构
      ddl-auto: update
#    控制台显示SQL
    show-sql: true
```



## 启动配置原理

几个重要的事件回调机制

配置在META-INF/spring.factories

**ApplicationContextInitializer**

**SpringApplicationRunListener**

只需要放在ioc容器中

**ApplicationRunner**

**CommandLineRunner**

启动流程：

### 1、创建SpringApplication对象

```java
initialize(sources);
private void initialize(Object[] sources) {
    //保存主配置类
    if (sources != null && sources.length > 0) {
        this.sources.addAll(Arrays.asList(sources));
    }
    //判断当前是否一个web应用
    this.webEnvironment = deduceWebEnvironment();
    //从类路径下找到META-INF/spring.factories配置的所有ApplicationContextInitializer；然后保存起来
    setInitializers((Collection) getSpringFactoriesInstances(
        ApplicationContextInitializer.class));
    //从类路径下找到ETA-INF/spring.factories配置的所有ApplicationListener
    setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
    //从多个配置类中找到有main方法的主配置类
    this.mainApplicationClass = deduceMainApplicationClass();
}
```



### 2、运行run方法

```java
public ConfigurableApplicationContext run(String... args) {
   StopWatch stopWatch = new StopWatch();
   stopWatch.start();
   ConfigurableApplicationContext context = null;
   FailureAnalyzers analyzers = null;
   configureHeadlessProperty();
    
   //获取SpringApplicationRunListeners；从类路径下META-INF/spring.factories
   SpringApplicationRunListeners listeners = getRunListeners(args);
    //回调所有的获取SpringApplicationRunListener.starting()方法
   listeners.starting();
   try {
       //封装命令行参数
      ApplicationArguments applicationArguments = new DefaultApplicationArguments(
            args);
      //准备环境
      ConfigurableEnvironment environment = prepareEnvironment(listeners,
            applicationArguments);
       		//创建环境完成后回调SpringApplicationRunListener.environmentPrepared()；表示环境准备完成
       
      Banner printedBanner = printBanner(environment);
       
       //创建ApplicationContext；决定创建web的ioc还是普通的ioc
      context = createApplicationContext();
       
      analyzers = new FailureAnalyzers(context);
       //准备上下文环境;将environment保存到ioc中；而且applyInitializers()；
       //applyInitializers()：回调之前保存的所有的ApplicationContextInitializer的initialize方法
       //回调所有的SpringApplicationRunListener的contextPrepared()；
       //
      prepareContext(context, environment, listeners, applicationArguments,
            printedBanner);
       //prepareContext运行完成以后回调所有的SpringApplicationRunListener的contextLoaded（）；
       
       //s刷新容器；ioc容器初始化（如果是web应用还会创建嵌入式的Tomcat）；Spring注解版
       //扫描，创建，加载所有组件的地方；（配置类，组件，自动配置）
      refreshContext(context);
       //从ioc容器中获取所有的ApplicationRunner和CommandLineRunner进行回调
       //ApplicationRunner先回调，CommandLineRunner再回调
      afterRefresh(context, applicationArguments);
       //所有的SpringApplicationRunListener回调finished方法
      listeners.finished(context, null);
      stopWatch.stop();
      if (this.logStartupInfo) {
         new StartupInfoLogger(this.mainApplicationClass)
               .logStarted(getApplicationLog(), stopWatch);
      }
       //整个SpringBoot应用启动完成以后返回启动的ioc容器；
      return context;
   }
   catch (Throwable ex) {
      handleRunFailure(context, listeners, analyzers, ex);
      throw new IllegalStateException(ex);
   }
}
```

### 3、事件监听机制

配置在META-INF/spring.factories

**ApplicationContextInitializer**

```java
public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("ApplicationContextInitializer...initialize..."+applicationContext);
    }
}

```

**SpringApplicationRunListener**

```java
public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {

    //必须有的构造器
    public HelloSpringApplicationRunListener(SpringApplication application, String[] args){

    }

    @Override
    public void starting() {
        System.out.println("SpringApplicationRunListener...starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        Object o = environment.getSystemProperties().get("os.name");
        System.out.println("SpringApplicationRunListener...environmentPrepared.."+o);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...contextPrepared...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...contextLoaded...");
    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringApplicationRunListener...finished...");
    }
}

```

ApplicationContextInitializer和SpringApplicationRunListener需要配置（META-INF/spring.factories）才能生效

手动在classpath下创建这个文件

```properties
org.springframework.context.ApplicationContextInitializer=\
com.atguigu.springboot.listener.HelloApplicationContextInitializer

org.springframework.boot.SpringApplicationRunListener=\
com.atguigu.springboot.listener.HelloSpringApplicationRunListener
```



只需要放在ioc容器中

**ApplicationRunner**

```java
@Component
public class HelloApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner...run....");
    }
}
```



**CommandLineRunner**

```java
@Component
public class HelloCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner...run..."+ Arrays.asList(args));
    }
}
```



## 自定义starter 场景启动器

springBoot为我们配置了很多的场景启动器，但是即使如何还是不能囊括所有的场景，所以需要我们自定义场景启动器来简化springBoot的开发

```java
starter：
1、这个场景需要使用到的依赖是什么？
2、如何编写自动配置
=================================================
@Configuration  //指定这个类是一个配置类
@ConditionalOnXXX  //在指定条件成立的情况下自动配置类生效
@AutoConfigureAfter  //指定自动配置类的顺序
@Bean  //给容器中添加组件

@ConfigurationPropertie结合相关xxxProperties类来绑定相关的配置
@EnableConfigurationProperties //让xxxProperties生效加入到容器中

自动配置类要能加载
将需要启动就加载的自动配置类，配置在META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
=================================================
3、模式：
启动器只用来做依赖导入；
专门来写一个自动配置模块；
启动器依赖自动配置；别人只需要引入启动器（starter）

```

![JAVA_SPRINGBOOT15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT15.png?raw=true)

**实例**：

自定义starts命名规则：  mybatis-spring-boot-starter；自定义启动器名-spring-boot-starter

步骤：

1）、启动器模块

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.atguigu.starter</groupId>
    <artifactId>atguigu-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!--启动器-->
    <dependencies>

        <!--引入自动配置模块-->
        <dependency>
            <groupId>com.atguigu.starter</groupId>
            <artifactId>atguigu-spring-boot-starter-autoconfigurer</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>

</project>
```

2）、自动配置模块

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>

   <groupId>com.atguigu.starter</groupId>
   <artifactId>atguigu-spring-boot-starter-autoconfigurer</artifactId>
   <version>0.0.1-SNAPSHOT</version>
   <packaging>jar</packaging>

   <name>atguigu-spring-boot-starter-autoconfigurer</name>
   <description>Demo project for Spring Boot</description>

   <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>1.5.10.RELEASE</version>
      <relativePath/> <!-- lookup parent from repository -->
   </parent>

   <properties>
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
      <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
      <java.version>1.8</java.version>
   </properties>

   <dependencies>

      <!--引入spring-boot-starter；所有starter的基本配置-->
      <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter</artifactId>
      </dependency>

   </dependencies>

</project>
```



```java
package com.atguigu.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "atguigu.hello")
public class HelloProperties {

    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

```

```java
package com.atguigu.starter;

public class HelloService {

    HelloProperties helloProperties;

    public HelloProperties getHelloProperties() {
        return helloProperties;
    }

    public void setHelloProperties(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHellAtguigu(String name){
        return helloProperties.getPrefix()+"-" +name + helloProperties.getSuffix();
    }
}

```

```java
package com.atguigu.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication //web应用才生效
@EnableConfigurationProperties(HelloProperties.class)
public class HelloServiceAutoConfiguration {

    @Autowired
    HelloProperties helloProperties;
    @Bean
    public HelloService helloService(){
        HelloService service = new HelloService();
        service.setHelloProperties(helloProperties);
        return service;
    }
}

```

让自动配置生效META-INF/spring.factories 加上我们自定义的自动配置类

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.atguigu.starter.HelloServiceAutoConfiguration
```

更多SpringBoot整合示例

https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples