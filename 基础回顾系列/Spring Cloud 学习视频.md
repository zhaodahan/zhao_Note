# Spring Cloud学习视频

解决分布式微服务的各种情况的整套架构,Spring Cloud 是一整套技术集合。

```
SpringCloud分布式开发五大常用组件
服务发现——Netflix Eureka
客服端负载均衡——Netflix Ribbon
断路器——Netflix Hystrix
服务网关——Netflix Zuul
分布式配置——Spring Cloud Config
```

参考文档：https://springcloud.cc/spring-cloud-netflix.html

https://springcloud.cc/spring-cloud-dalston.html

## 需要思考的问题

1) 什么是微服务

![JAVA_SPRINGCLOUD1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD1.png?raw=true)

```
微服务， 微服务架构，spring Cloud三者是完全不同的东西

微服务：
=============================================================
微服务是一种架构模式,风格，提倡将单一的应用划分成一组小的服务。每个服务独立运行在自己的进程中。相当于一个个独立的应用。每个服务处理具体单一的业务，可以使用不同的语言， 独立的部署，独立发布

从技术维度上：微服务化的核心就是将传统的一站式应用，根据业务拆分成一个一个的服务，彻底地去耦合,每一个微服务提供单个业务功能的服务，一个服务做一件事，从技术角度看就是一种小而独立的处理过程，类似进程概念，能够自行单独启动或销毁，拥有自己独立的数据库。

自己的理解总结：
微服务，他首先不是spring Cloud，Dubbo这样的技术架构，他只是一种架构设计模式和风格，把我们以前传统的将所有对业务操作的服务糅合成一个整体的设计风格，分成不同的服务(小应用)操作不同的业务。这样更加的灵活。 这些服务我们将其当成一个小型的独立应用，与其他服务无关，他们课可以使用不同技术实现，可以独立的部署。 可以类似搭建积木一样，随机组合成一个大应用。(这里的微服务就相当于搭积木用的积木一样)
=============================================================

微服务架构：
=============================================================
微服务强调的是服务的大小，强调的是单一的个体

微服务架构基于宏观，强调的是整体。是积木拼接的一个整体
微服务架构是一种架构模式体系。提倡将应用划分成一组小的服务，服务间以http restful 相互调用配合。
=============================================================

spring Cloud
=============================================================
微服务架构的落地实现技术栈。 使用spring Cloud来搭建我们的微服务架构
提供解决微服务，各种维度问题的技术实现。

springCloud，基于SpringBoot提供了一套微服务解决方案，包括服务注册与发现，配置中心，全链路监控，服务网关，负载均衡，熔断器等组件。
SpringCloud利用SpringBoot的开发巧妙地简化了分布式系统基础设施的开发，SpringCloud为开发人员提供了快速构建分布式系统的一些工具，包括配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等,它们都可以用SpringBoot的开发风格做到一键启动和部署。
 
SpringBoot并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过SpringBoot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包

SpringCloud=分布式微服务架构下的一站式解决方案，是各个微服务架构落地技术的集合体，俗称微服务全家桶
=============================================================
```

spring Cloud和springBoot的关系

```
springBoot关注的是微观，如何快速方便的开发单个个体微服务。

SpringCloud是关注全局的微服务协调整理治理框架，它将SpringBoot开发的一个个单体微服务整合并管理起来，
为各个微服务之间提供，配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等集成服务
 
SpringBoot可以离开SpringCloud独立使用开发项目，但是SpringCloud离不开SpringBoot，属于依赖的关系.
 
SpringBoot专注于快速、方便的开发单个微服务个体，SpringCloud关注全局的服务治理框架。
```

2）微服务间如何进行通信

```
基于HTTP的RESTful API (就是符合restful风格的http请求)
想象成客户端请求服务端的那种模式
Consumer消费者（Client）通过REST调用Provider提供者（Server）提供的服务
```

3) 微服务技术栈有哪些

 ![JAVA_SPRINGCLOUD2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD2.png?raw=true)

4)微服务的优缺点是什么

```
优点：
1：服务足够内聚，专业解决专门业务
2：服务轻耦合，从开发到部署都是独立的。 多数情况下不会牵一发而动全身
3：各个服务可以使用不同的语言开发，在特定的服务能够更好的选择特定技术
4：容易与第三方集成，允许灵活的方式集成自动部署，如Jenkins
5：每个服务可以有自己的数据库，不同的服务可以连接不同的数据库

缺点：
1： 系统更加复杂，随着服务的增加，部署和系统复杂性随之增加
2： 存系统部署依赖，有些服务需要依赖于其他服务
3： 服务间通信成本增加，系统集成测试难度增加，性能检测难度增加
4： 数据一致性更难保证
```

5）什么是服务熔断，什么是服务降级

```
服务熔断是为了避免分布式服务雪崩的一种保护机制，当某个服务出现异常，会直接返回预定响应，而不是长期等待，消耗资源

服务降级是整体资源不够用了，关闭某些重要性不那么强的服务，来保证某些重要的服务可用。
```

|          | 服务熔断                               | 服务降级                     |
| -------- | -------------------------------------- | ---------------------------- |
| 实现方式 | Hystrix                                | Hystrix                      |
| 开发位置 | 在服务提供方，服务端                   | 服务消费方，客户端           |
| 注解     | @HystrixCommand +@EnableCircuitBreaker | @FeignClient+FallbackFactory |

6）spring Cloud 和Dubbo的区别

在微服务中，这两个架构只能二选一

|                      | spring Cloud                                     | Doubbo                                                       |
| -------------------- | ------------------------------------------------ | ------------------------------------------------------------ |
| 通信机制             | 基于HTTP的RESTful API                            | 基于RPC远程过程调用                                          |
| 定位                 | 微服务架构一站式解决方案                         | RPC框架                                                      |
| 微服务各种情况支持度 | 对于各种情况都有属于spring Cloud的一整套支持方案 | 不全                                                         |
| 形象理解             | 品牌机，覆盖范围光，各种情况都有解决方案         | 组装机，各个环节的选择自由度很高，但是出问题的可能性就更大。不利于稳定 |



7）Eureka 和Zookeeper的区别

```
Eureka 在设计的时候遵守的是AP原则
Eureka 可以很好的应对因网路故障而导致的部分节点不可用，而不会像zookeeper那样让整个注册服务瘫软
```

|                      | Eureka               | Zookeeper               |
| -------------------- | -------------------- | ----------------------- |
| 遵守CAP              | AP(高可用，分区容错) | CP (强一致性，分区容错) |
| 高可用(当遇网络故障) | 自我保护机制         | 服务瘫痪，重新选取节点  |
| 集群节点             | 每个节点平等         | 主从节点                |



## Rest微服务构建案例工程模块搭建案例

### Maven的分包分模块

```xml
一个简单的Maven模块结构是这样的：
---- app-parent      一个父项目(app-parent)聚合很多子项目(app-util,app-dao,app-service,app-web)
      |---- pom.xml (pom)
      |
      |-------- app-util
      | |-------- pom.xml (jar)
      |
      |-------- app-dao
      | |-------- pom.xml (jar)
      |
      |-------- app-service
      | |-------- pom.xml (jar)
      |
      |-------- app-web
        |-------- pom.xml (war) 
(分成不同的jar包，模块和模块之间来进行通信和调用，完成了Maven的分包分部署的构建)
正常开发项目中都是：一个大的项目划分成几个不同的模块，模块与模块之间通过Maven互联。
微服务示例：
一个Project带着多个Module子模块，MicroServiceCloud父工程（Project）下初次带着3个子模块（Module）
microservicecloud-api：封装的整体Entity/接口/公共配置等
microservicecloud-provider-dept-8001：微服务落地的服务提供者
microservicecloud-consumer-dept-80：微服务调用的客户端使用

构建步骤：
1：microservicecloud整体父工程Project
新建父工程microservicecloud，切记是Packageing是pom模式
主要是定义POM文件，将后续各个子模块公用的jar包等统一提出来，类似一个抽象父类

2：microservicecloud-api公共子模块Module
在microservicecloud的基础上新建microservicecloud-api，是间的Module工程，不是Maven Project
确保其父工程是microservicecloud
在父工程的pom.xml中

	<modules>
		<module>microservicecloud-api</module>
		<module>microservicecloud-provider-dept-8001</module>
		<module>microservicecloud-consumer-dept-80</module>
		<module>microservicecloud-eureka-7001</module>
		<module>microservicecloud-consumer-dept-feign</module>
		<module>microservicecloud-provider-dept-hystrix-8001</module>
		<module>microservicecloud-consumer-hystrix-dashboard</module>
		<module>microservicecloud-zuul-gateway-9527</module>
		<module>microservicecloud-config-3344</module>
		<module>microservicecloud-config-client-3355</module>
		<module>microservicecloud-config-eureka-client-7001</module>
		<module>microservicecloud-config-dept-client-8001</module>
	</modules>
子模块的pom.xml	
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <!--parent 应用了上面的构建步骤了就自动生成了-->
  <parent><!-- 子类里面显示声明才能有明确的继承表现，无意外就是父类的默认版本否则自己定义 -->
   <groupId>com.atguigu.springcloud</groupId>
   <artifactId>microservicecloud</artifactId>
   <version>0.0.1-SNAPSHOT</version>
  </parent>

  <artifactId>microservicecloud-api</artifactId><!-- 当前Module我自己叫什么名字 -->

  <dependencies><!-- 当前Module需要用到的jar包，按自己需求添加，如果父类已经包含了，可以不用写版本号 -->
   <dependency>
     <groupId>org.projectlombok</groupId>
     <artifactId>lombok</artifactId>
   </dependency>
  </dependencies>

</project>
mvn clean install后给其它模块引用，达到通用目的。也即需要用到部门实体的话，不用每个工程都定义一份，直接引用本模块即可。

3：microservicecloud-provider-dept-8001  部门微服务提供者Module
类似上面的步骤，构建服务提供者模块
pom中引用microservicecloud-api
<parent>
   <groupId>com.atguigu.springcloud</groupId>
   <artifactId>microservicecloud</artifactId>
   <version>0.0.1-SNAPSHOT</version>
  </parent>

  <artifactId>microservicecloud-provider-dept-8001</artifactId>

  <dependencies>
   <dependency><!-- 引入自己定义的api通用包，可以使用Dept部门Entity -->
     <groupId>com.atguigu.springcloud</groupId>
     <artifactId>microservicecloud-api</artifactId>
     <version>${project.version}</version>
   </dependency>
      
4：microservicecloud-consumer-dept-80 部门微服务消费者Module
   类似3构建
    
@Configuration
public class ConfigBean
{
    @Bean
    public RestTemplate getRestTemplate()
    {
         return new RestTemplate();
    }
}
 
 @RestController
public class DeptController_Consumer
{
    private static final String REST_URL_PREFIX = "http://localhost:8001";
    
    @Autowired
    private RestTemplate restTemplate;
    /**
	 * 使用 使用restTemplate访问restful接口非常的简单粗暴无脑。 (url, requestMap,
	 * ResponseBean.class)这三个参数分别代表 REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
	 */
    @RequestMapping(value="/consumer/dept/add")
    public boolean add(Dept dept)
    {
         return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add", dept, Boolean.class);
    }
    
    @RequestMapping(value="/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id)
    {
         return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/"+id, Dept.class);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/consumer/dept/list")
    public List<Dept> list()
    {
         return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list", List.class);
    }   
}     
```

![JAVA_SPRINGCLOUD3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD3.png?raw=true)

### spring Cloud的版本

```xml
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Dalston.SR1</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-dependencies</artifactId>
				<version>1.5.9.RELEASE</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
```



## spring Cloud 五大基石

### 服务发现——Eureka

#### Eureka是什么？

![JAVA_SPRINGCLOUD4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD4.png?raw=true)

```
他的主要功能就是服务的注册与发现，只需要使用服务的标识符(微服务的名称)，就可以访问到服务，而不需要修改服务调用的配置文件了。功能类似于dubbo的注册中心，比如Zookeeper。

Eureka 采用了 C-S 的设计架构。Eureka Server 作为服务注册功能的服务器，它是服务注册中心。
系统中的其他微服务，使用 Eureka 的客户端连接到 Eureka Server并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。SpringCloud 的一些其他模块（比如Zuul）就可以通过 Eureka Server 来发现系统中的其他微服务，并执行相关的逻辑。

Eureka包含两个组件：Eureka Server和Eureka Client

Eureka Server提供服务注册服务
各个节点启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到
(微服务要先注册进Eureka Server ，其他应用才能找到他，并使用它)
  
EurekaClient是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）

三大角色
Eureka Server 提供服务注册和发现
Service Provider服务提供方将自身服务注册到Eureka，从而使服务消费方能够找到
Service Consumer服务消费方从Eureka获取注册服务列表，从而能够消费服务
```

#### 构建Eureka Server服务

```yaml
1: pom 依赖
   <!--eureka-server服务端 -->
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-eureka-server</artifactId>
   </dependency>
2：yml配置文件
server: 
  port: 7001
 
eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    register-with-eureka: false #false表示不向注册中心注册自己。
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/        
      #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。对外暴露的eureka服务器地址
      
 3: 主启动类上加上@EnableEurekaServer注解
@SpringBootApplication
@EnableEurekaServer//EurekaServer服务器端启动类,接受其它微服务注册进来
public class EurekaServer7001_App
{
  public static void main(String[] args)
  {
   SpringApplication.run(EurekaServer7001_App.class, args);
  }
}  

4：其他微服务注册进Eureka
microservicecloud-provider-dept-8001将已有的部门微服务注册进eureka服务中心
1)pom引入eureka依赖
<!-- 将微服务provider侧注册进eureka -->
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-eureka</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-config</artifactId>
   </dependency>
2）yml配置文件配置eureka clinet
eureka:
  client: #客户端注册进eureka服务列表内
    service-url: 
      defaultZone: http://localhost:7001/eureka
 3) 主启动类中加上注解@EnableEurekaClient
 @SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
public class DeptProvider8001_App
{
  public static void main(String[] args)
  {
   SpringApplication.run(DeptProvider8001_App.class, args);
  }
}

5： 注册的服务间发现及调用 Ribbon ，Feign
对于注册进eureka的微服务，我们可以通过服务发现来获取注册进eureka的微服务的信息，让其他服务了解该服务。(服务发现不是重点，仅了解，不使用)
服务间的调用还是使用的是：Ribbon ，Feign
```

#### Eureka的自我保护机制

![JAVA_SPRINGCLOUD5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD5.png?raw=true)

```
Eureka 在设计的时候遵守的是AP原则 (高可用，分区容错)

Eureka的自我保护机制就是： 好死不如赖活着
一句话：某时刻某一个微服务不可用了，eureka不会立刻清理，依旧会对该微服务的信息进行保存

默认情况下，如果EurekaServer在一定时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例（默认90秒）。但是当网络分区故障发生时，微服务与EurekaServer之间无法正常通信，以上行为可能变得非常危险了——因为微服务本身其实是健康的，此时本不应该注销这个微服务。Eureka通过“自我保护模式”来解决这个问题。当EurekaServer节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，EurekaServer就会保护服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。

自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留），也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定
```

#### Eureka集群配置

![JAVA_SPRINGCLOUD6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD6.png?raw=true)

```yaml
1）拷贝eureka server 构建集群组件服务
2）修改配置以支持集群
a:修改映射配置 (都在本地才需要修改)
做一些域名映射，方便自己使用的时候访问，也可以不修改配置，这不是重点
修改C:\Windows\System32\drivers\etc路径下的hosts文件
127.0.0.1  eureka7001.com
127.0.0.1  eureka7002.com
127.0.0.1  eureka7003.com
b:修改eureka服务器的配置（3台为例， 7001,7002,7003）
server: 
  port: 7001
 
eureka: 
  instance:
    hostname: eureka7001.com #eureka服务端的实例名称
  client: 
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: 
      #单机 defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/      
      #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
      defaultZone: http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
      
=================================================  
server: 
  port: 7002


eureka: 
  instance:
    hostname: eureka7002.com #eureka服务端的实例名称
  client: 
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: 
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/      
      #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7003.com:7003/eureka/

=================================================  
  server: 
  port: 7003


eureka: 
  instance:
    hostname: eureka7003.com #eureka服务端的实例名称
  client: 
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: 
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/    
 =================================================  
 c: 服务注册方微服务需要同时注册进eureka集群
 eureka:
  client: #客户端注册进eureka服务列表内
    service-url: 
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
  instance:
    instance-id: microservicecloud-dept8001   #自定义服务名称信息
    prefer-ip-address: true     #访问路径可以显示IP地址
```

![JAVA_SPRINGCLOUD7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD7.png?raw=true)

### 客服端负载均衡——Ribbon与Feign

#### Ribbon是什么？

```
Ribbon=客户端 + 负载均衡工具

主要提供客户端的负载均衡算法。 举例： 我们在从超市排队结账，我们就是客户端消费者，排队有三个队列(他们就是服务的提供者)，我们肯定会去排队排在等待人少的队列。这就是(服务消费方)客户端的负载均衡。 Ribbon就是做这样的一个事

```



#### Ribbon如何使用

```xml
Ribbon在工作时分成两步
第一步先选择 EurekaServer ,它优先选择在同一个区域内负载较少的server.
第二步再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址。其中Ribbon提供了多种策略：比如轮询、随机和根据响应时间加权。

1：引入Ribbon依赖
 <!-- Ribbon相关 -->
   <dependency>
     <groupId>org.springframework.cloud</groupId>
      <!-- eureka 客户端， Ribbon需要跟 eureka 整合-->
     <artifactId>spring-cloud-starter-eureka</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-ribbon</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
       <!-- eureka 和config 有联系-->
     <artifactId>spring-cloud-starter-config</artifactId>
   </dependency>
   
2： yml配置
修改application.yml   追加eureka的服务注册地址 (Ribbon依赖于eureka )

eureka:
  client:
#   register-with-eureka: false
    service-url: 
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
3：加上注解开启ribbon
在我们做负载均衡的客户端端RestTemplate上加上 @LoadBalanced 注解
对ConfigBean进行新注解@LoadBalanced，获得Rest时加入Ribbon的配置
主启动类添加@EnableEurekaClient 告诉他这是一个eureka客户端

Ribbon和Eureka整合后Consumer可以直接使用微服务名调用服务而不用再关心地址和端口号
  //private static final String REST_URL_PREFIX = "http://localhost:8001";
  private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";
 
  @Autowired
  private RestTemplate restTemplate;
```

#### Ribbon的负载均衡

![JAVA_SPRINGCLOUD8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD8.png?raw=true)

```java
核心组件IRule ，它定义了Ribbon的负载均衡算法， 我们默认什么都不指定使用的是轮询算法

我们使用IRule 来指定使用特定的负载均衡算法

如何使用IRule 来更改我们的复杂均衡策略
在配置类中注册我们想要使用的策略rule
@Configuration
public class ConfigBean //boot -->spring   applicationContext.xml --- @Configuration配置   ConfigBean = applicationContext.xml
{ 
	@Bean
	@LoadBalanced//Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端       负载均衡的工具。
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean
	public IRule myRule()
	{
		//return new RoundRobinRule();
		//return new RandomRule();//达到的目的，用我们重新选择的随机算法替代默认的轮询。
		return new RetryRule();
	}
}
```

![JAVA_SPRINGCLOUD9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD9.png?raw=true)

我们还可以自定义我们的负载均衡算法

```java
如果要使用我们自定义的负载均衡算法
1：在服务消费方客户端启动类上加上@RibbonClient
@SpringBootApplication
@EnableEurekaClient
//在启动该微服务的时候不再使用我们默认的ribbon而是去加载我们的自定义Ribbon配置类，从而使配置生效
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MySelfRule.class)
public class DeptConsumer80_App
{
	public static void main(String[] args)
	{
		SpringApplication.run(DeptConsumer80_App.class, args);
	}
}
2： 我们自定义的配置类MySelfRule 的编写需要注意： 这个配置类不能放在我们启动类所在的主包下(他会让我们所消费所有的目标服务都使用这个负载均衡规则，这样无法达到特殊定制化的目的)，如果我们只需要在特定的目标服务上使用我们自定义的负载均衡规则，则只需要将我们配置的规则配置放在主启动类包外
@Configuration
public class MySelfRule
{
	@Bean
	public IRule myRule()
	{
		//return new RandomRule();// Ribbon默认是轮询，我自定义为随机
		//return new RoundRobinRule();// Ribbon默认是轮询，我自定义为随机
		
		return new RandomRule_ZY();// 我自定义为每台机器5次
	}
}

3：编写我们自定义的负载均衡规则
public class RandomRule_ZY extends AbstractLoadBalancerRule
{

	// total = 0 // 当total==5以后，我们指针才能往下走，
	// index = 0 // 当前对外提供服务的服务器地址，
	// total需要重新置为零，但是已经达到过一个5次，我们的index = 1
	// 分析：我们5次，但是微服务只有8001 8002 8003 三台，OK？
	private int total = 0; 			// 总共被调用的次数，目前要求每台被调用5次
	private int currentIndex = 0;	// 当前提供服务的机器号

	public Server choose(ILoadBalancer lb, Object key)
	{
		if (lb == null) {
			return null;
		}
		Server server = null;

		while (server == null) {
			if (Thread.interrupted()) {
				return null;
			}
			List<Server> upList = lb.getReachableServers();
			List<Server> allList = lb.getAllServers();

			int serverCount = allList.size();
			if (serverCount == 0) {
				/*
				 * No servers. End regardless of pass, because subsequent passes only get more
				 * restrictive.
				 */
				return null;
			}

//			int index = rand.nextInt(serverCount);// java.util.Random().nextInt(3);
//			server = upList.get(index);

			
//			private int total = 0; 			// 总共被调用的次数，目前要求每台被调用5次
//			private int currentIndex = 0;	// 当前提供服务的机器号
            if(total < 5)
            {
	            server = upList.get(currentIndex);
	            total++;
            }else {
	            total = 0;
	            currentIndex++;
	            if(currentIndex >= upList.size())
	            {
	              currentIndex = 0;
	            }
            }			
			
			
			if (server == null) {
				/*
				 * The only time this should happen is if the server list were somehow trimmed.
				 * This is a transient condition. Retry after yielding.
				 */
				Thread.yield();
				continue;
			}

			if (server.isAlive()) {
				return (server);
			}

			// Shouldn't actually happen.. but must be transient or a bug.
			server = null;
			Thread.yield();
		}

		return server;

	}

	@Override
	public Server choose(Object key)
	{
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig)
	{
		// TODO Auto-generated method stub

	}

}
```



#### Feign是什么

```
Feign是一个声明式WebService客户端。
使用Feign能让编写Web Service客户端更加简单, 它的使用方法是定义一个接口，然后在上面添加注解。
Spring Cloud对Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverters。
Feign可以与Eureka和Ribbon组合使用以支持负载均衡；

总结：Feign就是接口+注解的申明式Java Http客户端调用
 

Feign能干什么
Feign旨在使编写Java Http客户端变得更容易。
前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它(以前是Dao接口上面标注Mapper注解,现在是一个微服务接口上面标注一个Feign注解即可)，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。


Feign集成了Ribbon (所以使用Ribbon的默认轮询算法来实现负载均衡)Feign自带负载均衡配置项
利用Ribbon维护了MicroServiceCloud-Dept的服务列表信息，并且通过轮询实现了客户端的负载均衡。而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用
```

![JAVA_SPRINGCLOUD10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD10.png?raw=true)

#### Feign如何使用

```java
Feign是一个声明式的Web服务客户端，使得编写Web服务客户端变得非常容易，
只需要创建一个接口，然后在上面添加注解即可。
参考官网：https://github.com/OpenFeign/feign 

1：引入Feign依赖
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-feign</artifactId>
   </dependency>
2：针对我们的目标服务接口，同样开发一个供调用的Feign注解+接口形式的接口
@FeignClient(value = "MICROSERVICECLOUD-DEPT")
public interface DeptClientService
{
  @RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
  public Dept get(@PathVariable("id") long id);


  @RequestMapping(value = "/dept/list",method = RequestMethod.GET)
  public List<Dept> list();


  @RequestMapping(value = "/dept/add",method = RequestMethod.POST)
  public boolean add(Dept dept);
}

3：消费方主启动类，加上@EnableFeignClients注解
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages= {"com.atguigu.springcloud"})
@ComponentScan("com.atguigu.springcloud")
public class DeptConsumer80_Feign_App
{
  public static void main(String[] args)
  {
   SpringApplication.run(DeptConsumer80_Feign_App.class, args);
  }
}
 

4： 调用方使用这个Feign接口
@RestController
public class DeptController_Feign
{
  @Autowired
  private DeptClientService service;
}

```



#### Ribbon与Feign的异与同

|          | Ribbon                     | Feign                    |
| -------- | -------------------------- | ------------------------ |
| 编程方式 | 面向微服务名称编程         | 面向微服务接口编程       |
| 使用方式 | Ribbon+RestTemplate        | Feign+ 微服务接口        |
| 负载均衡 | IRule 指定各种负载均衡算法 | 集成Ribbon，默认轮询     |
| 使用端   | 基于客户端，服务的消费端   | 基于客户端，服务的消费端 |



#### 思考：feign如何定制负载均衡策略

```properties
feign其实不是做负载均衡的,负载均衡是ribbon的功能,feign只是集成了ribbon而已,但是负载均衡的功能还是feign内置的ribbon再做,而不是feign。
feign的作用的替代RestTemplate,性能比较低，但是可以使代码可读性很强。
因此配置负载均衡策略即为对ribbon进行配置。 
所以我们如果想为feign定制负载均衡策略,只需要对ribbon进行配置即可

如下：

#其中“alh-tms”指明对哪个微服务的调用进行配置，当前配置使用随机策略，修改策略修改最后的类名即可
alh-tms.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule
 
# 设置连接超时时间，单位ms
ribbon.ConnectTimeout=5000
# 设置读取超时时间，单位ms
ribbon.ReadTimeout=5000
# 对所有操作请求都进行重试
ribbon.OkToRetryOnAllOperations=true
# 切换实例的重试次数
ribbon.MaxAutoRetriesNextServer=2
# 对当前实例的重试次数
ribbon.MaxAutoRetries=1

```



### 断路器——Hystrix

#### Hystrix是什么？

```
他主要负责spring Cloud中做服务熔断，服务降级处理。 他的处理思想和spring的前置通知，环绕通知有相似之处。

分布式系统面临的问题
复杂分布式体系结构中的应用程序有数十个依赖关系，每个依赖关系在某些时候将不可避免地失败。

服务雪崩
多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其它的微服务，这就是所谓的“扇出”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应”.
 
对于高流量的应用来说，单一的后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和。比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。
 
Hystrix到底是什么？

分布式微服务架构spring Cloud是一整套系统的落地解决方案。现在只是是分布式就会有服务熔断的问题，为了避免产生雪崩效应，一个服务调不通了，我们弃车保帅，不可不调他了，或者返回一个正常，异常响应。不能因为他而拖累整个系统。


spring Cloud专门产生一个组件Hystrix是一个用于处理分布式系统的 延迟 和 容错
在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，
不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。 (类似于电力系统中的保险丝)
 
“断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝），向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。
```

#### Hystrix实现服务熔断

![JAVA_SPRINGCLOUD11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD11.png?raw=true)

```java
什么是服务熔断？

为了避免分布式服务雪崩，spring Cloud提供的一个服务提供方的服务容错机制。当某些服务长期不可用，服务方返回一个预定的响应。不让服务消费方长期等待，而导致的消费过量资源而服务雪崩

服务熔断
熔断机制是应对雪崩效应的一种微服务链路保护机制。
当扇出链路的某个微服务不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回"错误"的响应信息，类似于spring的aop的异常通知，当出了异常后我们要做些什么 。当检测到该节点微服务调用响应正常后恢复调用链路。在SpringCloud框架里熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况，当失败的调用到一定阈值，缺省是5秒内20次调用失败就会启动熔断机制。熔断机制的注解是@HystrixCommand。

如何实现服务熔断？ (他是在服务提供方实现的)
1：在服务提供方 引入Hystrix依赖
   <!--  hystrix -->
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-hystrix</artifactId>
   </dependency>
2:再服务提供方代码出加上 @HystrixCommand 注解，启用hystrix
@HystrixCommand报异常后如何处理。
一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
@RestController
public class DeptController
{
  @Autowired
  private DeptService service = null;
  
  @RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
  @HystrixCommand(fallbackMethod = "processHystrix_Get")
  //当这个服务出了一场，去执行指定的方法获得解决方案  
  public Dept get(@PathVariable("id") Long id)
  {
   Dept dept =  this.service.get(id);
   if(null == dept)
   {
     throw new RuntimeException("该ID："+id+"没有没有对应的信息");
   }
   return dept;
  }
  
  public Dept processHystrix_Get(@PathVariable("id") Long id)
  {
   return new Dept().setDeptno(id)
           .setDname("该ID："+id+"没有没有对应的信息,null--@HystrixCommand")
           .setDb_source("no this database in MySQL");
  }
}

3:为服务提供方的主启动类添加新注解@EnableCircuitBreaker
 @SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableCircuitBreaker//对hystrixR熔断机制的支持
public class DeptProvider8001_Hystrix_App
{
  public static void main(String[] args)
  {
   SpringApplication.run(DeptProvider8001_Hystrix_App.class, args);
  }
}   
```

![JAVA_SPRINGCLOUD12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD12.png?raw=true)

#### Hystrix实现服务降级

![JAVA_SPRINGCLOUD13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD13.png?raw=true)

```java
什么是服务降级？
整体资源快不够了，忍痛将某些服务先关掉，待渡过难关，再开启回来。
服务降级处理是在客户端实现完成的，与服务端没有关系

服务熔断中，某一个服务方法出了异常都有一个异常解决方案。 这样会有问题，方法膨胀和将AOP中的主干业务逻辑和我们的切面处理逻辑耦合在一块了。

解决方案，将切面处理逻辑通通在接口绑定。

实现方式：
1：在服务调用端的接口层
根据已经有的DeptClientService接口新建一个实现了
FallbackFactory接口的类DeptClientServiceFallbackFactory
千万不要忘记在类上面新增@Component注解，大坑！！！
@Component//不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService>
{
  @Override
  public DeptClientService create(Throwable throwable)
  {
   return new DeptClientService() {
     @Override
     public Dept get(long id)
     {
       return new Dept().setDeptno(id)
               .setDname("该ID："+id+"没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
               .setDb_source("no this database in MySQL");
     }
 
     @Override
     public List<Dept> list()
     {
       return null;
     }
 
     @Override
     public boolean add(Dept dept)
     {
       return false;
     }
   };
  }
}

2： 在在服务调用端的接口上的@FeignClient中添加fallbackFactory属性值，指定fallback
@FeignClient(value = "MICROSERVICECLOUD-DEPT",fallbackFactory=DeptClientServiceFallbackFactory.class)
public interface DeptClientService
{
  @RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
  public Dept get(@PathVariable("id") long id);
 
  @RequestMapping(value = "/dept/list",method = RequestMethod.GET)
  public List<Dept> list();
 
  @RequestMapping(value = "/dept/add",method = RequestMethod.POST)
  public boolean add(Dept dept);
}
 
3： 在服务的消费方，添加运行服务降级配置，开启服务降级
feign: 
  hystrix: 
    enabled: true
4： 最终效果
服务端provider已经down了，但是我们做了服务降级处理，让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器
```

![JAVA_SPRINGCLOUD14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD14.png?raw=true)

#### Hystrix dashboard

Hystrix  除了可以实现服务熔断和服务降级，还能实现服务监控

Hystrix dashboard 是一个单独的微服务工程

```java
除了隔离依赖服务的调用以外，Hystrix还提供了准实时的调用监控（Hystrix Dashboard），Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，并以统计报表和图形的形式展示给用户，包括每秒执行多少请求多少成功，多少失败等。Spring Cloud提供了Hystrix Dashboard的整合，对监控内容转化成可视化界面。
 
如何使用 Hystrix dashboard？
1： 在 Hystrix dashboard微服务工程中引入依赖
   <!-- hystrix和 hystrix-dashboard相关-->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-hystrix</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
   </dependency> 
2：主启动类新注解@EnableHystrixDashboard
@SpringBootApplication
@EnableHystrixDashboard
public class DeptConsumer_DashBoard_App
{
  public static void main(String[] args)
  {
   SpringApplication.run(DeptConsumer_DashBoard_App.class,args);
  }
}

3:所有Provider微服务提供方都需要配置监控依赖配置
   <!-- actuator监控信息完善 -->
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
4： Hystrix dashboard的使用与查看
http://localhost:8001/hystrix.stream
如何看： 7色 1圈 1线
```

![JAVA_SPRINGCLOUD15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD15.png?raw=true)

![JAVA_SPRINGCLOUD16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD16.png?raw=true)

### 服务网关——Zuul

#### zuul是什么

```
Zuul包含了对请求的路由和过滤两个最主要的功能：(类似于大楼的保安)
路由：负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础(对微服务名字做相对而言保护)
过滤：则负责对请求的处理过程进行干预，是实现请求校验、服务聚合等功能的基础.
(他就是要在所有的微服务外面包上一层，提供保护)

Zuul和Eureka进行整合，将Zuul自身注册为Eureka服务治理下的应用，同时从Eureka中获得其他微服务的消息，也即以后的访问微服务都是通过Zuul跳转后获得。
 
注意：Zuul服务最终还是会注册进Eureka
 
提供=代理+路由+过滤三大功能
```

#### zuul 怎么用

```yaml
zuul 本身也是一个微服务项目工程
1：引入zuul依赖
     <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-eureka</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-zuul</artifactId>
   </dependency>
2：zuul yml配置eureka和zuul访问映射规则
(Zuul服务需要注册进Eureka，所以需要配置Eureka， zuul需要实现外部访问统一入口，所以需要映射服务访问规则)
server: 
  port: 9527
 
spring: 
  application:
    name: microservicecloud-zuul-gateway
    
zuul: 
  prefix: /atguigu     # 统一前缀
  ignored-services: "*" #原真实服务名忽略，让原服务名不可访问 单个具体，多个可以用"*"
  routes: 
    mydept.serviceId: microservicecloud-dept
    mydept.path: /mydept/**     #映射路径
 
eureka: 
  client: 
    service-url: 
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka,http://eureka7003.com:7003/eureka  
  instance:
    instance-id: gateway-9527.com
    prefer-ip-address: true 

info:
  app.name: atguigu-microcloud
  company.name: www.atguigu.com
  build.artifactId: $project.artifactId$
  build.version: $project.version$
  
3: 主启动上加上注解@EnableZuulProxy来开启zuul
@SpringBootApplication
@EnableZuulProxy
public class Zuul_9527_StartSpringCloudApp
{
  public static void main(String[] args)
  {
   SpringApplication.run(Zuul_9527_StartSpringCloudApp.class, args);
  }
}
 
4: 访问规则
在没有配置忽略原服务名访问时，访问规则是：
http://myzuul.com:9527/microservicecloud-dept/dept/get/2 # 原服务名范围
http://myzuul.com:9527/mydept/dept/get/1           #映射访问

```

![JAVA_SPRINGCLOUD17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD17.png?raw=true)

### 分布式配置——Spring Cloud Config

#### 什么是Spring Cloud Config 分布式配置中心

![JAVA_SPRINGCLOUD18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD18.png?raw=true)

```
在微服务概述中有定义，可以有一个非常轻量级的集中式管理来协调这些服务
分布式服务系统面临的配置问题：微服务将单体应用拆分成一个个子服务，每个服务粒度相对较小，系统中会出现大量服务。每个服务都需要配置来能运行。大量的配置文件分散在不同的服务中，难以管理，急需一个集中，动态的配置管理机制。spring Cloud提供了config server来解决这个问题

config server 是一个集中化的 外部配置。分布式配置中心，一个独立的微服务应用，所有微服务配置的大管家。最终所有配置信息以rest 接口形式暴露。(可以和gitHub 整合)

```

#### Spring Cloud Config 结合gitHub怎么用

![JAVA_SPRINGCLOUD19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD19.png?raw=true)

```yaml
1： gitHub建立配置仓库clone到本地
在配置仓库中存储各个微服务的配置文件
2：搭建分布式配置中心config server微服务应用
2.1引入Config server 依赖
<dependencies>
		<!-- springCloud Config -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>
		<!-- 避免Config的Git插件报错：org/eclipse/jgit/api/TransportConfigCallback -->
		<dependency>
			<groupId>org.eclipse.jgit</groupId>
			<artifactId>org.eclipse.jgit</artifactId>
			<version>4.10.0.201712302008-r</version>
		</dependency>
		<!-- 图形化监控 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<!-- 熔断 -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
		</dependency>
		<!-- 热部署插件 -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>springloaded</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
	</dependencies>
 2.2 配置config server yml
 server: 
  port: 3344 
  
spring:
  application:
    name:  microservicecloud-config
  cloud:
    config:
      server:
        git:
          uri: git@github.com:zzyybs/microservicecloud-config.git #GitHub上面的git仓库名字
 2.3 config server 启动类上加注解@EnableConfigServer 告诉他是一个config server
 @SpringBootApplication
@EnableConfigServer
public class Config_3344_StartSpringCloudApp
{
	public static void main(String[] args)
	{
		SpringApplication.run(Config_3344_StartSpringCloudApp.class, args);
	}
}
2.4 如此通过config server 就可以拉取GitHub上的配置文件信息
http://localhost:3344/application-dev.yml
3：客户端配置，拉取使用配置中心的配置
3.1 引入依赖
<!-- SpringCloud Config客户端 -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
3.2 创建对应的bootstrap.yml 来决定拉取那个配置
spring:
  cloud:
    config:
      name: microservicecloud-config-client #需要从github上读取的资源名称，注意没有yml后缀名
      profile: test   #本次访问的配置项
      label: master   
      uri: http://config-3344.com:3344  
      #本微服务启动后先去找3344号服务，通过SpringCloudConfig获取GitHub的服务地址
  
```

![JAVA_SPRINGCLOUD20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD20.png?raw=true)

![JAVA_SPRINGCLOUD21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD21.png?raw=true)

#### 配置中心的配置如何动态刷新

```
ConfigServer(配置中心服务端)从远端git拉取配置文件并在本地git一份，ConfigClient（微服务）从ConfigServer端获取自己对应 配置文件；当远端git仓库配置文件发生改变，ConfigServer如何通知到ConfigClient端，即ConfigClient如何感知到配置发生更新？

实现思路：
Spring Cloud Bus会向外提供一个http接口，即图中的/bus/refresh。我们将这个接口配置到远程的git的webhook上，当git上的文件内容发生变动时，就会自动调用/bus-refresh接口。Bus就会通知config-server，config-server会发布更新消息到消息总线的消息队列中，其他服务订阅到该消息就会信息刷新，从而实现整个微服务进行自动刷新。

具体参见https://blog.csdn.net/wtdm_160604/article/details/83720391
```

![JAVA_SPRINGCLOUD22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD22.png?raw=true)

#### 配置中心的配置文件存放在本地如何配置

```yaml
配置文件放在本地

在yml中配置存放本地文件的配置
#配置文件存放在git服务器
#spring:
#  cloud:
#    config:
#      server:
#        git:
#          uri: https://github.com/miaoba2009/NewConfig.git
#          searchPaths: /repoNew
#          username: miaoba2009
#          password: mb_123456789
#      label: master
#配置文件存放在配置服务器本地
spring:
  cloud:
    config:
      server: 
        native:
          search-locations: classpath:/shared
  profiles:
     active: native
  application:
    name: cb-config-server


management:
  security:
    enabled: false
```

![JAVA_SPRINGCLOUD27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD27.png?raw=true)

## 总结

![JAVA_SPRINGCLOUD23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD23.png?raw=true)

![JAVA_SPRINGCLOUD24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD24.png?raw=true)

![JAVA_SPRINGCLOUD25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD25.png?raw=true)

![JAVA_SPRINGCLOUD26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGCLOUD26.png?raw=true)


