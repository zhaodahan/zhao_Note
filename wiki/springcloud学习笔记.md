#                               springcloud学习笔记

# 初解Spring Cloud

## Spring Cloud是什么？

Spring Cloud是**一 系列**框架的有序集合。 

它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发**工具包**。

(可以理解为spring Cloud 就是spring封装的一个工具包)

微服务是可以独立部署、水平扩展、独立访问（或者有独立的数据库）的服务单元，springcloud就是这些微服务的大管家，采用了微服务这种架构之后，项目的数量会非常多，springcloud需要管理好这些微服务，需要很多小弟(许多组件)来帮忙。

主要的小弟有：Spring Cloud Config、Spring Cloud Netflix（Eureka、Hystrix、Zuul、Archaius…）、Spring Cloud Bus、Spring Cloud for Cloud Foundry、Spring Cloud Cluster、Spring Cloud Consul、Spring Cloud Security、Spring Cloud Sleuth、Spring Cloud Data Flow、Spring Cloud Stream、Spring Cloud Task、Spring Cloud Zookeeper、Spring Cloud Connectors、Spring Cloud Starters、Spring Cloud CLI。

## 核心成员

### Spring Cloud Netflix

这是springCloud的核心组件。 (重中之重)， 有许多子组件。

**Netflix Eureka**

**服务中心**，云端服务发现，一个基于 REST 的服务，用于定位服务，以实现云端中间层服务发现和故障转移。这个可是springcloud最牛逼的小弟，服务中心，任何小弟需要其它小弟支持什么都需要从这里来拿，同样的你有什么独门武功的都赶紧过报道，方便以后其它小弟来调用；它的好处是你不需要直接找各种什么小弟支持，只需要到服务中心来领取，也不需要知道提供支持的其它小弟在哪里，还是几个小弟来支持的，反正**拿来用就行，服务中心来保证稳定性和质量**。

(服务**注册调用**中心)

**Netflix Hystrix**

**熔断器，容错管理工具**，旨在通过熔断机制控制服务和第三方库的节点,从而对延迟和故障提供更强大的容错能力。比如突然某个小弟生病了，但是你还需要它的支持，然后调用之后它半天没有响应，你却不知道，一直在等等这个响应；有可能别的小弟也正在调用你的武功绝技，那么当请求多之后，就会发生严重的阻塞影响老大的整体计划。这个时候Hystrix就派上用场了，当Hystrix发现某个小弟不在状态不稳定立马马上让它下线，让其它小弟来顶上来，或者给你说不用等了这个小弟今天肯定不行，该干嘛赶紧干嘛去别在这排队了。

**Netflix Zuul**

Zuul 是在云平台上提供动态路由,监控,弹性,安全等边缘服务的框架。Zuul 相当于是设备和 Netflix 流应用的 Web 网站后端所有**请求的前门** （类似于nginx的功能）。当其它门派来找大哥办事的时候一定要先经过zuul,看下有没有带刀子什么的给拦截回去，或者是需要找那个小弟的直接给带过去。

**Netflix Archaius**

配置**管理API**，包含一系列配置管理API，提供动态类型化属性、线程安全配置操作、轮询框架、回调机制等功能。可以实现动态获取配置， 原理是每隔60s（默认，可配置）从配置源读取一次内容，这样修改了配置文件后不需要重启服务就可以使修改后的内容生效，前提使用archaius的API来读取。

### Spring Cloud Config

**配置中心**，配置管理工具包，让你可以把配置放到远程服务器，集中化管理集群配置，目前支持本地存储、Git以及Subversion。就是以后大家武器、枪火什么的东西都集中放到一起，别随便自己带，方便以后统一管理、升级装备。

### Spring Cloud Bus

事件、消息总线，用于在集群（例如，配置变化事件）中传播状态变化，可与Spring Cloud Config联合实现热部署。相当于水浒传中日行八百里的神行太保戴宗，确保各个小弟之间消息保持畅通。 (他是springCloud中的**消息传递管理者**，消息中转站 )

### Spring Cloud for Cloud Foundry( foundry--铸造厂)

Cloud Foundry是VMware推出的业界第一个开源PaaS**云平台**，它支持多种框架、语言、运行时环境、云平台及应用服务，使开发人员能够在几秒钟内**进行应用程序的部署和扩展**，无需担心任何基础架构的问题

就是与CloudFoundry进行集成的一套解决方案，抱Cloud Foundry的大腿。

### Spring Cloud Cluster

Spring Cloud Cluster(集群)将取代Spring Integration(整合)。提供在分布式系统中的集群所需要的基础功能支持，如：选举、集群的状态一致性、全局锁、tokens等常见状态模式的抽象和实现。

Spring Cloud Cluster提供了很多方便组织成统一的工具。

### Spring Cloud Consul

Consul 是一个支持**多数据中心**分布式**高可用的服务发现**和**配置共享**的服务软件. 支持健康检查,并允许 HTTP 和 DNS 协议调用 API 存储键值对.

Spring Cloud Consul 封装了Consul，consul是一个**服务发现与配置工具**，与Docker容器可以无缝集成。

![img](http://www.ityouknow.com/assets/images/2017/springcloud/spring-cloud-architecture.png)

- 1、外部或者内部的非Spring Cloud项目都统一通过API网关（Zuul）来访问内部服务.
- 2、网关接收到请求后，从注册中心（Eureka）获取可用服务
- 3、由Ribbon进行均衡负载后，分发到后端的具体实例
- 4、微服务之间通过Feign进行通信处理业务
- 5、Hystrix负责处理服务超时熔断
- 6、Turbine监控服务间的调用和熔断相关指标

图中没有画出配置中心，配置中心管理各微服务不同环境下的配置文件。

## 其它小弟

**Spring Cloud Security**

基于spring security的安全工具包，为你的应用程序添加**安全控制**。这个小弟很牛逼专门**负责**整个帮派的**安全问题**，设置不同的门派访问特定的资源，不能把秘籍葵花宝典泄漏了。

**Spring Cloud Sleuth**

**日志收集**工具包，为SpringCloud应用实现了一种**分布式追踪解决方案**。

**Spring Cloud Data Flow**(数据流动--------专门做处理数据的工具组件)

- Data flow 是一个用于开发和执行**大范围数据处理**，批量运算和持续**运算**的**统一编程模型和托管服务**。
- 对于微服务程序来说，Spring Cloud data flow是一个原生云可编配的服务。使用Spring Cloud data flow可以为像数**据抽取，实时分析，和数据导入/导出这种常见用例创建和编配数据通道** 。
- Spring Cloud data flow 为**基于微服务的分布式流处理和批处理数据通道提供了一系列模型和最佳实践**。

**Spring Cloud Stream**

Spring Cloud Stream是**创建消息驱动**微服务**应用**的框架。Spring Cloud Stream是基于Spring Boot创建，用来建立单独的／工业级spring应用，使用spring integration（整合）**提供与消息代理之间的连接**。数据流操作开发包，封装了与Redis,Rabbit、Kafka等发送接收消息。

一个业务会牵扯到多个任务，任务之间是通过事件触发的，这就是Spring Cloud stream要干的。

**Spring Cloud Task**

Spring Cloud Task 主要解决短命微服务的**任务管理**，**任务调度**的工作，比如说某些定时任务晚上就跑一次，或者某项数据分析临时就跑几次。

**Spring Cloud Zookeeper**

ZooKeeper是一个分布式的，开放源码的**分布式应用程序协调服务**，是Hadoop和Hbase的重要组件。它是一个**为分布式应用提供一致性服务**的**软件**，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。ZooKeeper的目标就是封装好复杂易出错的关键服务，将简单易用的接口和性能高效、功能稳定的系统提供给用户。

Spring Cloud Zookeeper用zookeeper方式的服务发现和配置管理，抱Zookeeper的大腿。

**Spring Cloud Connectors**

Spring Cloud Connectors **简化了连接到服务的过程和从云平台获取操作**的过程，有很强的扩展性，可以利用Spring Cloud Connectors来构建你自己的云平台。

便于云端应用程序在各种PaaS平台连接到后端，如：数据库和消息代理服务。

**Spring Cloud Starters**

Spring Boot式的启动项目，为Spring Cloud提供开箱即用的依赖管理。

**Spring Cloud CLI**

基于 Spring Boot CLI，可以让你以命令行方式快速建立云组件。

## 和Spring Boot 是什么关系

Spring Boot 是 Spring 的一套快速配置脚手架，可以基于Spring Boot 快速开发单个微服务，Spring Cloud是一个**基于**Spring Boot实现的**云应用开发工具**；

Spring Boot专注于快速、方便集成的单个个体。(搭建单个微服务)

Spring Cloud是**关注全局的服务治理**框架；

Spring Boot使用了**默认大于配置**的理念，很多集成方案已经帮你选择好了，能不配置就不配置，Spring Cloud很大的一部分是基于Spring Boot来实现,可以不基于Spring Boot吗？不可以。Spring Boot可以离开Spring Cloud独立使用开发项目，但是Spring Cloud离不开Spring Boot，属于依赖的关系。

> spring -> spring booot > Spring Cloud 这样的关系。

## Spring Cloud的优势

- 产出于spring大家族，可以保证后续的更新、完善。比如dubbo现在就差不多死了
- 基于Spring Boot ，springBoot已经做了大部分事情，十分方便。
- 作为微服务治理的大框架，考虑的很全面，几乎服务治理的方方面面都考虑到了，方便开发开箱即用。
- 轻轻松松几行代码就完成了熔断、均衡负载、服务中心的各种平台功能
- 使用Spring Cloud一站式解决方案能在从容应对业务发展的同时大大减少开发成本

# 注册中心Eureka

Eureka是Netflix开源的一款产品，它提供了完整的Service Registry和Service Discovery实现。也是springcloud体系中最重要最核心的组件之一。

## 背景介绍

### 服务中心

**服务中心**又称**注册中心**，管理各种服务功能包括服务的注册、发现、熔断、负载、降级等功能。

有了服务中心调用关系会有什么变化：

项目A调用项目B

正常调用项目A请求项目B

![img](http://www.itmind.net/assets/images/2017/springcloud/ab.jpg)

有了服务中心之后，任何一个服务都不能直接去调用，都需要通过服务中心来调用

![img](http://www.itmind.net/assets/images/2017/springcloud/a2b.jpg)

项目A调用项目B，项目B在调用项目C

![img](http://www.itmind.net/assets/images/2017/springcloud/abc.jpg)

这时候调用的步骤就会为两步：第一步，项目A首先从服务中心请求项目B服务器，然后项目B在从服务中心请求项目C服务。

![img](http://www.itmind.net/assets/images/2017/springcloud/a2b2c.jpg)

通过服务中心来获取服务你不需要关注你调用的项目IP地址，由几台服务器组成，他们之间的调用关系是怎么样的，你只需要每次直接去服务中心获取可以使用的服务去调用既可。

由于各种服务都注册到了服务中心，就有了去做很多高级功能条件。比如几台服务提供相同服务来做均衡负载；监控服务器调用成功率来做熔断，移除服务列表中的故障点；监控服务调用时间来对不同的服务器设置不同的权重等等。

### Netflix

Netflix公司和拍很多美剧的是同一家公司。其实springcloud的微服务就基于Netflix公司的开源产品来做的。

Netflix的开源框架组件已经在Netflix的大规模分布式微服务环境中经过多年的生产实战验证，正逐步被社区接受为构造微服务框架的标准组件。Spring Cloud开源产品，主要是基于对Netflix开源组件的进一步封装，方便Spring开发人员构建微服务基础框架。是通向微服务架构的捷径。

### Eureka

按照官方介绍：

> Eureka is a REST (Representational State Transfer) based service that is primarily used in the AWS cloud for locating services for the purpose of load balancing and failover of middle-tier servers.
>
> Eureka 是一个基于 REST 的服务，主要在 AWS 云中使用, **定位服务**来进行中间层服务器的负载均衡和故障转移。

Spring Cloud 封装了 Netflix 公司开发的 Eureka 模块来**实现服务注册和发现**。Eureka 采用了 **C-S 的设计架构**。Eureka Server 作为服务注册功能的服务器，它是**服务注册中心**。而系统中的其他微服务，使用 Eureka 的客户端连接到 Eureka Server，并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。Spring Cloud 的一些其他模块（比如Zuul）就可以通过 Eureka Server 来发现系统中的其他微服务，并执行相关的逻辑。

Eureka由两个组件组成：Eureka服务器和Eureka客户端。Eureka服务器用作服务注册服务器。Eureka客户端是一个java客户端，用来简化与服务器的交互、作为轮询负载均衡器，并提供服务的故障切换支持。

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka-architecture-overview.png)

上图简要描述了Eureka的基本架构，由3个角色组成：

1、Eureka Server

- 提供服务注册和发现

2、Service Provider

- 服务提供方
- 将**自身服务注册到Eureka**，从而使服务消费方能够找到

3、Service Consumer

- 服务消费方
- **从Eureka获取注册服务列表**，从而能够消费服务

Eureka的客户端通过在配置中配置

```properties
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/ （服务中心地址）
```

通过这个配置注册到服务中。 如果客户端突然中断等，服务端短时间依然会还是会拿到客户端的引用的。

## 案例实践

### Eureka Server

spring cloud已经帮我们实现了服务注册中心，只需要很简单的几个步骤就可以调用spring cloud的实现。

1、pom中添加依赖

```xml
#springcloud之eureka服务注册中心POM配置
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.zhao</groupId>
	<artifactId>testurake</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>testurake</name>
	<description>Demo project for Spring Boot testurake</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.3.RELEASE</version>
		<!-- <version>2.1.0.RELEASE</version> -->
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<!--eureka server -->   
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
		</dependency>

		<!-- spring boot test -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Dalston.RC1</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>
</project>

```

2、添加**启动代码**中添加`@EnableEurekaServer`注解

```java
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudEurekaApplication.class, args);
	}
}
```

3、配置文件

在`application.properties`添加以下配置：

```properties
#这里是将自己配置成一个注册中心，默认服务注册中心也会将自己作为客户端来尝试注册它自己，我们需要禁用它的客户端注册行为
spring.application.name=spring-cloud-eureka

server.port=8000
eureka.client.register-with-eureka=false 
#表示是否将自己注册到Eureka Server，默认为true。
eureka.client.fetch-registry=false 
#fetch 取来，表示是否从Eureka Server获取注册信息，默认为true

eureka.client.serviceUrl.defaultZone=http://localhost:${server.port}/eureka/
#设置与Eureka Server交互的地址，查询服务和注册服务都需要依赖这个地址。默认是http://localhost:8761/eureka ；(配置文件中已经将端口改成了8000) 多个地址可使用 , 分隔。
```

启动工程后，访问：http://localhost:8000/，可以看到下面的页面，其中还没有发现任何服务

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka_start.jpg)



eureka是一个高可用的组件，它没有后端缓存，每一个实例注册之后需要向注册中心发送心跳（因此可以在内存中完成），在默认情况下erureka server也是一个eureka client ,必须要指定一个 server。

eureka server的配置文件appication.yml：

```properties
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```



## 集群

注册中心这么关键的服务，如果是单点话，遇到故障就是毁灭性的。

在一个分布式系统中，服务注册中心是最重要的基础部分，理应随时处于可以提供服务的状态。为了维持其可用性，使用**集群**是很好的解决方案。Eureka通过**互相注册**的方式来实现高可用的部署，所以我们只需要将Eureke Server配置其他可用的serviceUrl就能实现高可用部署。

### 双节点注册中心

双节点的注册中心的搭建。

1、创建application-peer1.properties，作为peer1服务中心的配置，并将serviceUrl指向peer2

```properties
spring.application.name=spring-cloud-eureka
server.port=8000
eureka.instance.hostname=peer1

eureka.client.serviceUrl.defaultZone=http://peer2:8001/eureka/
```

2、创建application-peer2.properties，作为peer2服务中心的配置，并将serviceUrl指向peer1

```properties
spring.application.name=spring-cloud-eureka
server.port=8001
eureka.instance.hostname=peer2

eureka.client.serviceUrl.defaultZone=http://peer1:8000/eureka/
```

3、host转换

在hosts文件中加入如下配置

```properties
127.0.0.1 peer1  
127.0.0.1 peer2  
```

4、打包启动

依次执行下面命令

```properties
#打包
mvn clean package
# 分别以peer1和peeer2 配置信息启动eureka
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
```

依次启动完成后，浏览器输入：`http://localhost:8000/` 效果图如下：

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka-two.jpg)

根据图可以看出peer1的注册中心DS Replicas已经有了peer2的相关配置信息，并且出现在available-replicas中。我们手动停止peer2来观察，发现peer2就会移动到unavailable-replicas一栏中，表示peer2不可用。

### eureka集群使用

在生产中我们可能需要三台或者大于三台的注册中心来保证服务的稳定性，配置的原理其实都一样，将注册中心分别指向其它的注册中心。其实和双节点的注册中心类似，每台注册中心分别又指向其它两个节点即可，使用application.yml来配置。

application.yml配置详情如下：

```properties
---
spring:
  application:
    name: spring-cloud-eureka
  profiles: peer1
server:
  port: 8000
eureka:
  instance:
    hostname: peer1
  client:
    serviceUrl:
      defaultZone: http://peer2:8001/eureka/,http://peer3:8002/eureka/
---
spring:
  application:
    name: spring-cloud-eureka
  profiles: peer2
server:
  port: 8001
eureka:
  instance:
    hostname: peer2
  client:
    serviceUrl:
      defaultZone: http://peer1:8000/eureka/,http://peer3:8002/eureka/
---
spring:
  application:
    name: spring-cloud-eureka
  profiles: peer3
server:
  port: 8002
eureka:
  instance:
    hostname: peer3
  client:
    serviceUrl:
      defaultZone: http://peer1:8000/eureka/,http://peer2:8001/eureka/
```

分别以peer1、peer2、peer3的配置参数启动eureka注册中心。

```
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer3
```

依次启动完成后，浏览器输入：`http://localhost:8000/` 效果图如下：

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka-cluster.jpg)

可以在peer1中看到了peer2、peer3的相关信息。至此eureka集群也已经完成了

# 服务提供与调用

使用eureka服务注册中心，搭建一个简单的服务端注册服务，客户端去调用服务使用的案例。

三个角色：

服务注册中心

服务提供者

服务消费者

其中服务注册中心就是我们上一篇的eureka单机版启动既可。流程是首先启动注册中心，服务提供者生产服务并注册到服务中心中，消费者从服务中心中获取服务并执行。

## 服务提供

我们假设服务提供者有一个hello方法，可以根据传入的参数，提供输出“hello xxx，this is first messge”的服务

### 1、pom包配置

创建一个springboot项目，pom.xml中添加如下配置：

```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.17.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Edgware.SR5</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

### 2、配置文件

application.properties配置如下：

```properties
spring.application.name=spring-cloud-producer
server.port=9000
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```

### 3、启动类

启动类中添加`@EnableDiscoveryClient`注解

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ProducerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}
}
```

### 4、controller

```java
//提供hello服务
@RestController
public class HelloController {
	
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
}
```

添加`@EnableDiscoveryClient`注解后，项目就具有了服务注册的功能。启动工程后，就可以在**注册中心**的页面看到SPRING-CLOUD-PRODUCER服务。

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka_server.png)

到此服务提供者配置就完成了。

## 服务调用

### 1、pom包配置

和服务提供者基本一致，但是要导入feign依赖

```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

2、配置文件

application.properties配置如下：

```properties
spring.application.name=spring-cloud-consumer
server.port=9001
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```

### 3、启动类

启动类添加`@EnableDiscoveryClient`和`@EnableFeignClients`注解。

```java
@SpringBootApplication
@EnableDiscoveryClient //启用服务注册与发现
@EnableFeignClients //启用feign进行远程调用，Feign是一个声明式Web Service客户端。它的使用方法是定义一个接口，然后在上面添加注解。
public class ConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
```

> Spring Cloud对Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverters。Feign可以与Eureka和Ribbon组合使用以支持负载均衡。

### 4、feign调用实现

```java
@FeignClient(name= "spring-cloud-producer") //name:远程服务名，及spring.application.name配置的名称
public interface HelloRemote {
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);
}
```

此类中的方法和远程服务中contoller中的方法名和参数需保持一致。

### 5、web层调用远程服务

将HelloRemote注入到controller层，像普通方法一样去调用即可。

```java
@RestController
public class ConsumerController {
    @Autowired
    HelloRemote HelloRemote;
    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return HelloRemote.hello(name);
    }
}
```

## 测试

### 简单调用

依次启动spring-cloud-eureka、spring-cloud-producer、spring-cloud-consumer三个项目

先输入：`http://localhost:9000/hello?name=neo` 检查spring-cloud-producer服务是否正常

返回：`hello neo，this is first messge`

说明spring-cloud-producer正常启动，提供的服务也正常。

浏览器中输入：`http://localhost:9001/hello/neo`

返回：`hello neo，this is first messge`

说明客户端已经成功的通过feign调用了远程服务hello，并且将结果返回到了浏览器。

### 负载均衡

以上面spring-cloud-producer为副本进行修改，将其中的controller改动如下：

```java
@RestController
public class HelloController {
	
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is producer 2  send first messge";
    }
}
```

在配置文件中改动端口：

```properties
spring.application.name=spring-cloud-producer
server.port=9003
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```

打包启动后，在eureka就会发现两个服务提供者，如下图：

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka_server2.png)

然后在浏览器再次输入：`http://localhost:9001/hello/neo` 进行测试：

第一次返回结果：`hello neo，this is first messge`

第二次返回结果：`hello neo，this is producer 2 send first messge`

不断的进行测试下去会发现两种结果交替出现，说明两个服务中心自动提供了服务均衡负载的功能。如果我们将服务提供者的数量在提高为N个，测试结果一样，请求会自动轮询到每个服务端来处理。

# 熔断器Hystrix 

## 熔断器

### 雪崩效应

在微服务架构中通常会有多个服务层调用，**基础服务的故障**可能会导致级联故障，进而造成整个系统不可用的情况，这种现象被称为服务雪崩效应。服务雪崩效应是一种因“**服务提供者”的不可用**导致“**服务消费者”的不可用**,**并**将不可用逐渐**放大**的过程。

如果下图所示：A作为服务提供者，B为A的服务消费者，C和D是B的服务消费者。A不可用引起了B的不可用，并将不可用像滚雪球一样放大到C和D时，雪崩效应就形成了。

![img](http://www.itmind.net/assets/images/2017/springcloud/hystrix-1.png)

### 熔断器（CircuitBreaker）

熔断器的原理很简单，如电力过载保护器，它可以实现**快速失败**。

 如果它在一段时间内监测到许多类似的错误，会强迫其以后的多个调用快速失败，不再访问远程服务器，从而防止应用程序不断地尝试执行可能会失败的操作。

使得应用程序继续执行而不用等待修正错误，或者浪费CPU时间去等到长时间的超时产生。

熔断器也可以使应用程序能够诊断错误是否已经修正，如果已经修正，应用程序会再次尝试调用操作。

**熔断器模式**就像是那些容易导致错误的**操作**的**一种代理**。这种代理能够记录最近调用发生错误的次数，然后决定使用允许操作继续，或者立即返回错误。

熔断器就是保护服务高可用的最后一道防线。就是不让服务一直调用哪些可能始终都调不通的服务

### Hystrix特性

**1.断路器机制**

断路器很好理解, 当Hystrix Command**请求后端服务失败数量超过一定比例**(默认50%), 这时所有请求会直接失败而不会发送到后端服务. 断路器保持在开路状态一段时间后(默认5秒), 自动切换到半开路状态(HALF-OPEN). 这时会判断下一次请求的返回情况, 如果请求成功(服务已经修复了), 恢复正常的调用, **Hystrix的断路器就像我们家庭电路中的保险丝, 一旦后端服务不可用, 断路器会直接切断请求链, 避免发送大量无效请求影响系统吞吐量, 并且断路器有自我检测并恢复的能力**.

**2.Fallback**

Fallback相当于是**降级操作**. 对于查询操作, 我们可以实现一个fallback方法, 当请求后端服务出现异常的时候, 可以使用fallback方法返回的值. <u>fallback方法的返回值一般是设置的默认值或者来自缓存</u>.

**3.资源隔离**

在Hystrix中, 主要通过**线程池**来实现资源隔离。

通常在使用的时候我们会**根据调用的远程服务**划分出多个线程池。 例如调用产品服务的Command放入A线程池, 调用账户服务的Command放入B线程池。

这样做的主要优点是运行环境被隔离开了. 这样就算调用服务的代码存在bug或者由于其他原因导致自己所在线程池被耗尽时, 不会对系统的其他服务造成影响. 但是带来的代价就是维护多个线程池会对系统带来额外的性能开销. 如果是对性能有严格要求而且确信自己调用服务的客户端代码不会出问题的话, 可以使用Hystrix的信号模式(Semaphores)来隔离资源.

## Feign Hystrix

因为**熔断只是作用在服务调用这一端** 。所以我们如果要使用熔断器。就只需要在服务消费端(spring-cloud-consumer)上做调整就可以了。而且如果已经使用了Feign，Feign中已经依赖了Hystrix，所以就不加maven配置。

### 1、配置文件

application.properties添加这一条：

```properties
feign.hystrix.enabled=true
```

### 2、创建回调类

创建HelloRemoteHystrix类继承与HelloRemote实现回调的方法

```java
@Component
public class HelloRemoteHystrix implements HelloRemote{
    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello" +name+", this messge send failed ";
    }
}
```

### 3、添加fallback属性

在`HelloRemote`类添加指定fallback类，在服务熔断的时候返回fallback类中的内容。

```java
@FeignClient(name= "spring-cloud-producer",fallback = HelloRemoteHystrix.class)
public interface HelloRemote {

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);

}
```

### 4、测试

那我们就来测试一下看看效果吧。

依次启动spring-cloud-eureka、spring-cloud-producer、spring-cloud-consumer三个项目。

浏览器中输入：`http://localhost:9001/hello/neo`

返回：`hello neo，this is first messge`

说明加入熔断相关信息后，不影响正常的访问。接下来我们手动停止spring-cloud-producer项目再次测试：

浏览器中输入：`http://localhost:9001/hello/neo`

返回：`hello neo, this messge send failed`

根据返回结果说明熔断成功。

# 熔断监控Hystrix Dashboard和Turbine

Hystrix-dashboard是针对Hystrix进行实时监控的工具。

通过Hystrix Dashboard我们可以在直观地看到各Hystrix Command的请求响应时间, 请求成功率等数据。但是只使用Hystrix Dashboard的话, 你只能看到单个应用内的服务信息, 这明显不够. 我们需要一个工具能让我们汇总系统内多个服务的数据并显示到Hystrix Dashboard上, 这个工具就是Turbine.

## Hystrix Dashboard

在熔断示例项目spring-cloud-consumer-hystrix的基础上更改，重新命名为：spring-cloud-consumer-hystrix-dashboard。

### 1、添加依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

三个包必须添加

### 2、启动类

启动类添加启用Hystrix Dashboard和熔断器

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
public class ConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
```

### 3、测试

启动工程后访问 http://localhost:9001/hystrix，将会看到如下界面：

![img](http://www.itmind.net/assets/images/2017/springcloud/hystrix-dashboard-1.jpg)

图中会有一些提示：

> Cluster via Turbine (default cluster): http://turbine-hostname:port/turbine.stream 
> Cluster via Turbine (custom cluster): http://turbine-hostname:port/turbine.stream?cluster=[clusterName]
> Single Hystrix App: http://hystrix-app:port/hystrix.stream

大概意思就是如果查看默认集群使用第一个url,查看指定集群使用第二个url,单个应用的监控使用最后一个，我们暂时只演示单个应用的所以在输入框中输入： http://localhost:9001/hystrix.stream ，输入之后点击 monitor，进入页面。

如果没有请求会先显示`Loading ...`，访问http://localhost:9001/hystrix.stream 也会不断的显示ping。

请求服务http://localhost:9001/hello/neo，就可以看到监控的效果了，首先访问http://localhost:9001/hystrix.stream，显示

到监控页面就会显示如下图：

![img](http://www.itmind.net/assets/images/2017/springcloud/hystrix-dashboard-2.jpg)

其实就是http://localhost:9001/hystrix.stream返回结果的图形化显示，Hystrix Dashboard Wiki上详细说明了图上每个指标的含义，如下图：

![img](http://www.itmind.net/assets/images/2017/springcloud/hystrix-dashboard-3.png)

到此单个应用的熔断监控已经完成。

## Turbine

在复杂的分布式系统中，相同服务的节点经常需要部署上百甚至上千个，很多时候，运维人员希望能够把相同服务的节点状态以一个整体集群的形式展现出来，这样可以更好的把握整个系统的状态。 为此，Netflix提供了一个开源项目（Turbine）来提供把多个hystrix.stream的内容聚合为一个数据源供Dashboard展示。

### 1、添加依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-turbine</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-netflix-turbine</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
	</dependency>
</dependencies>
```

### 2、配置文件

```properties
spring.application.name=hystrix-dashboard-turbine
server.port=8001
turbine.appConfig=node01,node02
# turbine.appConfig ：配置Eureka中的serviceId列表，表明监控哪些服务
turbine.aggregator.clusterConfig= default
#turbine.aggregator.clusterConfig：指定聚合哪些集群，多个使用”,”分割，默认为default。可使用http://turbine-hostname:port/turbine.stream?cluster=[clusterName]访问
turbine.clusterNameExpression= new String("default")
#turbine.clusterNameExpression： 
#1. clusterNameExpression指定集群名称，默认表达式appName；此时：turbine.aggregator.clusterConfig需要配置想要监控的应用名称；
#2. 当clusterNameExpression: default时，turbine.aggregator.clusterConfig可以不写，因为默认就是default；
#3. 当clusterNameExpression: metadata[‘cluster’]时，假设想要监控的应用配置了eureka.instance.metadata-map.cluster: ABC，则需要配置，同时turbine.aggregator.clusterConfig: ABC

eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/ 
```

### 3、启动类

启动类添加`@EnableTurbine`，激活对Turbine的支持

```java
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine 
//这里不需要加@EnableDiscoveryClient注解
public class DashboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}
}
```

### 4、测试

在示例项目spring-cloud-consumer-hystrix基础上修改为两个服务的调用者spring-cloud-consumer-node1和spring-cloud-consumer-node2

spring-cloud-consumer-node1项目改动如下： application.properties文件内容

```properties
spring.application.name=node01
server.port=9001
feign.hystrix.enabled=true

eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```

spring-cloud-consumer-node2项目改动如下： application.properties文件内容

```properties
spring.application.name=node02
server.port=9002
feign.hystrix.enabled=true

eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```

修改完毕后，依次启动spring-cloud-eureka、spring-cloud-consumer-node1、spring-cloud-consumer-node1、hystrix-dashboard-turbine（Turbine）

打开eureka后台可以看到注册了三个服务：

![img](http://www.itmind.net/assets/images/2017/springcloud/turbine-01.jpg)

访问 http://localhost:8001/turbine.stream

返回：

```
: ping
data: {"reportingHostsLast10Seconds":1,"name":"meta","type":"meta","timestamp":1494921985839}
```

并且会不断刷新以获取实时的监控数据，说明和单个的监控类似，返回监控项目的信息。进行图形化监控查看，输入：http://localhost:8001/hystrix，返回酷酷的小熊界面，输入： http://localhost:8001/turbine.stream，然后点击 Monitor Stream ，之后访问http://localhost:9005/hello/neo,http://localhost:9006/hello/neo，可以看到出现了俩个监控列表

![img](http://www.itmind.net/assets/images/2017/springcloud/turbine-02.jpg)

# 配置中心git示例

随着线上项目变的日益庞大，每个项目都散落着各种配置文件，如果采用分布式的开发模式，需要的配置文件随着服务增加而不断增多。某一个基础服务信息变更，都会引起一系列的更新和重启。**配置中心**便是解决此类问题的。

Spring Cloud Config配置中心，功能全面强大，可以无缝的和spring体系相结合，够方便够简单颜值高我喜欢。

## Spring Cloud Config

一个配置中心提供的核心功能应该有：

- 提供服务端和客户端支持
- **集中管理**各环境的配置文件
- 配置文件修改之后，可以快速的生效
- 可以进行版本管理
- 支持大的并发查询
- 支持各种语言

Spring Cloud Config可以完美的支持以上所有的需求。

Spring Cloud Config项目是一个解决**分布式系统的配置<u>管理</u>**方案。它包含了**Client和Server**两个部分，server提供配置文件的存储、以**接口的形式将配置文件的内容提供出去**，**client通过接口获取数据**、并依据此数据初始化自己的应用。Spring cloud使用git或svn存放配置文件，默认情况下使用git。

**以git为例示例**：

首先在github上面创建了一个文件夹config-repo用来存放配置文件，为了模拟生产环境，我们创建以下三个配置文件：

```properties
// 开发环境
neo-config-dev.properties
// 测试环境
neo-config-test.properties
// 生产环境
neo-config-pro.properties
```

每个配置文件中都写一个属性neo.hello,属性值分别是 hello im dev/test/pro 。下面我们开始配置server端

## server 端

### 1、添加依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
</dependencies>
```

只需要加入spring-cloud-config-server包引用既可。

### 2、配置文件

```properties
server:
  port: 8001
spring:
  application:
    name: spring-cloud-config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/ityouknow/spring-cloud-starter/     # 配置git仓库的地址
          search-paths: config-repo                             # git仓库地址下的相对地址，可以配置多个，用,分割。
          username:                                             # git仓库的账号
          password:                                             # git仓库的密码
```

Spring Cloud Config也提供本地存储配置的方式。

我们只需要设置属性`spring.profiles.active=native`，Config Server会默认从应用的`src/main/resource`目录下检索配置文件。也可以通过`spring.cloud.config.server.native.searchLocations=file:E:/properties/`属性来指定配置文件的位置。虽然Spring Cloud Config提供了这样的功能，但是为了支持更好的管理内容和版本控制的功能，还是推荐使用git的方式。

### 3、启动类

启动类添加`@EnableConfigServer`，激活对配置中心的支持

```java
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
```

### 4、测试

首先我们先要测试server端是否可以读取到github上面的配置信息，直接访问：`http://localhost:8001/neo-config/dev`

返回信息如下：

```json
{
    "name": "neo-config", 
    "profiles": [
        "dev"
    ], 
    "label": null, 
    "version": null, 
    "state": null, 
    "propertySources": [
        {
            "name": "https://github.com/ityouknow/spring-cloud-starter/config-repo/neo-config-dev.properties", 
            "source": {
                "neo.hello": "hello im dev"
            }
        }
    ]
}
上述的返回的信息包含了配置文件的位置、版本、配置文件的名称以及配置文件中的具体内容，说明server端已经成功获取了git仓库的配置信息。
```

如果直接查看配置文件中的配置信息可访问：`http://localhost:8001/neo-config-dev.properties`，返回：`neo.hello: hello im dev`

修改配置文件`neo-config-dev.properties`中配置信息为：`neo.hello=hello im dev update`,再次在浏览器访问`http://localhost:8001/neo-config-dev.properties`，返回：`neo.hello: hello im dev update`。说明server端会自动读取最新提交的内容

**仓库中的配置文件会被转换成web接口**，访问可以参照以下的规则：

- /{application}/{profile}[/{label}]
- /{application}-{profile}.yml
- /{label}/{application}-{profile}.yml
- /{application}-{profile}.properties
- /{label}/{application}-{profile}.properties

以neo-config-dev.properties为例子，它的application是neo-config，profile是dev。client会根据填写的参数来选择读取对应的配置。

## client 端

展示如何在业务项目中去获取server端的配置信息

### 1、添加依赖

```xml
<dependencies>
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
		<scope>test</scope>
	</dependency>
</dependencies>
引入spring-boot-starter-web包方便web测试
```

### 2、配置文件

需要配置两个配置文件，application.properties和bootstrap.properties

application.properties如下：

```properties
spring.application.name=spring-cloud-config-client
server.port=8002
```

bootstrap.properties如下：

```properties
spring.cloud.config.name=neo-config
#spring.application.name：对应{application}部分
spring.cloud.config.profile=dev 
#spring.cloud.config.profile：对应{profile}部分
spring.cloud.config.uri=http://localhost:8001/
#spring.cloud.config.uri：配置中心的具体地址
spring.cloud.config.label=master
#spring.cloud.config.label：对应git的分支。如果配置中心使用的是本地存储，则该参数无用

#spring.cloud.config.discovery.service-id：指定配置中心的service-id，便于扩展为高可用配置集群。
```

> 特别注意：上面**这些与spring-cloud相关的属性必须配置在bootstrap.properties中**，config部分内容才能被正确加载。因为config的相关配置会先于application.properties，而bootstrap.properties的加载也是先于application.properties。

### 3、启动类

启动类添加`@EnableConfigServer`，激活对配置中心的支持 （如果需要注册到配置中心别其他的服务调用）

```java
@SpringBootApplication
public class ConfigClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
}
//启动类正常`@SpringBootApplication`注解就可以 
```

### 4、web测试

使用`@Value`注解来获取server端参数的值

```java
@RestController
class HelloController {
    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }
}
```

启动项目后访问：`http://localhost:9007/hello`，返回：`hello im dev update`说明已经正确从server端获取到了参数。

手动修改`neo-config-dev.properties`中配置信息为：`neo.hello=hello im dev update1`提交到github,再次在浏览器访问`http://localhost:9007/hello`，返回：`neo.hello: hello im dev update`，说明获取的信息还是旧的参数，这是为什么呢？因为**springboot项目只有在启动的时候才会获取配置文件的值**，修改github信息后，client端并没有再次去获取。

下面解决这个问题。

# 配置中心svn示例和refresh

很多公司都使用的svn来做代码的版本控制，介绍使用svn+Spring Cloud Config来做配置中心。

## svn版本

基本步骤一样

### 1、添加依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
	<dependency>
		<groupId>org.tmatesoft.svnkit</groupId>
		<artifactId>svnkit</artifactId>
	</dependency>
</dependencies>
需要多引入svnkitr包
```

### 2、配置文件

```properties
server:
  port: 8001

spring:
  cloud:
    config:
      server:
        svn:
          uri: http://192.168.0.6/svn/repo/config-repo
          username: username
          password: password
        default-label: trunk
  profiles:
    active: subversion
  application:
    name: spring-cloud-config-server
#和git版本稍有区别，需要显示声明subversion.
```

### 3、启动类

启动类没有变化，添加`@EnableConfigServer`激活对配置中心的支持

```java
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
```

### 4、测试

**服务端测试**

访问：`http://localhost:8001/neo-config-dev.properties`，返回：`neo.hello: hello im dev`，说明服务端可以正常读取到svn代码库中的配置信息。修改配置文件`neo-config-dev.properties`中配置信息为：`neo.hello=hello im dev update`,再次在浏览器访问`http://localhost:8001/neo-config-dev.properties`，返回：`neo.hello: hello im dev update`。说明server端会自动读取最新提交的内容

**客户端测试**

客户端直接使用上面示例项目`spring-cloud-config-client`来测试，配置基本不用变动。启动项目后访问：`http://localhost:8002/hello，返回：`hello im dev update`说明已经正确的从server端获取到了参数。同样修改svn配置并提交，再次访问``http://localhost:8002/hello```依然获取的是旧的信息，和git版本的问题一样。

## refresh解决无法及时更新

解决上面的遗留的不能及时更新问题。Spring Cloud Config分服务端和客户端，服务端负责将git（svn）中存储的配置文件发布成REST接口，客户端可以从服务端REST接口获取配置。但客户端并不能主动感知到配置的变化，从而主动去获取新的配置。客户端如何去主动获取新的配置信息呢，springcloud已经给我们提供了解决方案，每个客户端通过POST方法触发各自的`/refresh`。

修改`spring-cloud-config-client`项目已到达可以refresh的功能。

### 1、添加依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

增加了`spring-boot-starter-actuator`包,是一套监控的功能，可以监控程序在运行时状态，其中就包括`/refresh`的功能。
```

### 2、 开启更新机制

给加载变量的类上面加载`@RefreshScope`，在客户端执行`/refresh`的时候就会更新此类下面的变量值。

```java
@RestController
@RefreshScope 
// 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
class HelloController {

    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }
}
```

### 3、测试

*springboot 1.5.X 以上默认开通了安全认证，所以需要在配置文件application.properties添加以下配置*

```properties
management.security.enabled=false
```

以**post请求**的方式来访问`http://localhost:9007/refresh` 就会更新修改后的配置文件。

我们再次来测试，首先访问`http://localhost:9007/hello`，返回：`hello im dev`，我将库中的值修改为`hello im dev update`。在win上面打开cmd执行`curl -X POST http://localhost:8002/refresh`，返回`["neo.hello"]`说明已经更新了`neo.hello`的值。我们再次访问`http://localhost:8002/hello`，返回：`hello im dev update`,客户端已经得到了最新的值。

每次手动刷新客户端也很麻烦，有没有什么办法只要提交代码就自动调用客户端来更新呢，github的webhook是一个好的办法。

### 4、webhook

WebHook是当某个事件发生时，通过发送http post请求的方式来通知信息接收方。

Webhook来监测你在Github.com上的各种事件，最常见的莫过于push事件。如果你设置了一个监测push事件的Webhook，那么每当你的这个项目有了任何提交，这个Webhook都会被触发，这时Github就会发送一个HTTP POST请求到你配置好的地址。

如此一来，你就可以通过这种方式去自动完成一些重复性工作。下图就是github上面的webhook配置。

![img](http://www.itmind.net/assets/images/2017/springcloud/webhook.jpg)

- `Payload URL` ：触发后回调的URL （使用的是IP或域名来访问，是从github发起调用  ）
- `Content type` ：数据格式，两种一般使用json
- `Secret` ：用作给POST的body加密的字符串。采用HMAC算法
- `events` ：触发的事件列表。

| events事件类型 | 描述                       |
| -------------- | -------------------------- |
| push           | 仓库有push时触发。默认事件 |
| create         | 当有分支或标签被创建时触发 |
| delete         | 当有分支或标签被删除时触发 |

> svn也有类似的hook机制，每次提交后会触发post-commit脚本，我们可以在这里写一些post请求

这样我们就可以利用hook的机制去触发客户端的更新，但是当客户端越来越多的时候hook支持的已经不够优雅，另外每次增加客户端都需要改动hook也是不现实的。其实Spring Cloud给了我们更好解决方案。

# 配置中心服务化和高可用

客户端都是直接调用配置中心的server端来获取配置文件信息。

这样就存在了一个问题，客户端和服务端的耦合性太高，如果server端要做**集群**，客户端只能通过原始的方式来路由，server端改变IP地址的时候，客户端也需要修改配置，不符合springcloud服务治理的理念。springcloud提供了这样的解决方案，**我们只需要将server端当做一个服务注册到eureka中，client端去eureka中去获取配置中心server端的服务既可**。

基于配置中心git版本的内容来改造

## server端改造

### 1、添加依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency> 
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
</dependencies>
需要多引入`spring-cloud-starter-eureka`包，来添加对eureka的支持。
```

### 2、配置文件

```properties
server:
server:
  port: 8001
spring:
  application:
    name: spring-cloud-config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/ityouknow/spring-cloud-starter/     # 配置git仓库的地址
          search-paths: config-repo                 # git仓库地址下的相对地址，可以配置多个，用,分割。
          username: username                                        # git仓库的账号
          password: password                                    # git仓库的密码
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8000/eureka/   ## 注册中心eurka地址
```

增加了eureka注册中心的配置

### 3、启动类

启动类添加`@EnableDiscoveryClient`激活对配置中心的支持

```java
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
```

这样server端的改造就完成了。先启动eureka注册中心，在启动server端，在浏览器中访问：`http://localhost:8000/`就会看到server端已经注册了到注册中心了。

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka-config01.jpg)

## 客户端改造

### 1、添加依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>
需要多引入`spring-cloud-starter-eureka`包，来添加对eureka的支持。
```

### 2、配置文件

```properties
spring.application.name=spring-cloud-config-client
server.port=8002

# 下面的这些配置需要放在bootstrap.properties的配置中，因为config service配置优于application.properties加载
#去掉了`spring.cloud.config.uri`直接指向server端地址的配置,增加了最后的三个配置
spring.cloud.config.name=neo-config
spring.cloud.config.profile=dev
spring.cloud.config.label=master
spring.cloud.config.discovery.enabled=true
#开启Config服务发现支持
spring.cloud.config.discovery.serviceId=spring-cloud-config-server
#指定server端的name,也就是server端`spring.application.name`的值
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
#指向配置中心的地址
```

### 3、启动类

启动类添加`@EnableDiscoveryClient`激活对配置中心的支持

```java
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
}
```

启动client端，在浏览器中访问：`http://localhost:8000/` 就会看到server端和client端都已经注册了到注册中心了。

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka-config02.jpg)

## 高可用

为了模拟生产集群环境，我们改动server端的端口为8003，再启动一个server端来做服务的负载，提供高可用的server端支持。

![img](http://www.itmind.net/assets/images/2017/springcloud/eureka-config03.jpg)

如上图就可发现会有两个server端同时提供配置中心的服务，防止某一台down掉之后影响整个系统的使用。

我们先单独测试服务端，分别访问：`http://localhost:8001/neo-config/dev`、`http://localhost:8003/neo-config/dev`返回信息：

```json
{
    "name": "neo-config", 
    "profiles": [
        "dev"
    ], 
    "label": null, 
    "version": null, 
    "state": null, 
    "propertySources": [
        {
            "name": "https://github.com/ityouknow/spring-cloud-starter/config-repo/neo-config-dev.properties", 
            "source": {
                "neo.hello": "hello im dev"
            }
        }
    ]
}
```

说明两个server端都正常读取到了配置信息。

再次访问：`http://localhost:8002/hello`，返回：`hello im dev update`。说明客户端已经读取到了server端的内容，我们随机停掉一台server端的服务，再次访问`http://localhost:8002/hello`，返回：`hello im dev update`，说明达到了高可用的目的。

# 配置中心和消息总线

如果需要客户端获取到最新的配置信息需要执行`refresh`，我们可以利用webhook的机制每次提交代码发送请求来刷新客户端，当客户端越来越多的时候，需要每个客户端都执行一遍，这种方案就不太适合了。使用Spring Cloud Bus可以完美解决这一问题。

## Spring Cloud Bus

Spring cloud bus通过轻量消息代理连接各个分布的节点。这会用在广播状态的变化（例如配置变化）或者其他的消息指令。<u>Spring bus的一个核心思想是通过分布式的启动器对spring boot应用进行扩展，也可以用来建立一个多个应用之间的**通信频道**。</u>目前唯一实现的方式是用AMQP消息代理作为通道。

Spring cloud bus被国内翻译为消息总线。将它理解为**管理和传播所有分布式项目中的消息**，其实本质是利用了MQ的广播机制在分布式的系统中传播消息，目前常用的有Kafka和RabbitMQ。利用bus的机制可以做很多的事情，其中配置中心客户端刷新就是典型的应用场景之一，我们用一张图来描述bus在配置中心使用的机制。

![img](http://www.itmind.net/assets/images/2017/springcloud/configbus1.jpg)

利用Spring Cloud Bus做配置更新的步骤:

- 1、提交代码触发post给客户端A发送bus/refresh
- 2、客户端A接收到请求从Server端更新配置并且发送给Spring Cloud Bus
- 3、Spring Cloud bus接到消息并通知给其它客户端
- 4、其它客户端接收到通知，请求Server端获取最新配置
- 5、全部客户端均获取到最新的配置

## 项目示例

MQ我们使用RabbitMQ来做示例。

**客户端spring-cloud-config-client改造**  （主体是客户端，改造的就是客户端）

### 1、添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
需要多引入`spring-cloud-starter-bus-amqp`包，增加对消息总线的支持
```

### 2、配置文件

```properties
## 刷新时，关闭安全验证
management.security.enabled=false
## 开启消息跟踪
spring.cloud.bus.trace.enabled=true

#配置文件需要增加RebbitMq的相关配置
spring.rabbitmq.host=192.168.9.89
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=123456
```

### 3、测试

依次启动spring-cloud-eureka、spring-cloud-config-server、spring-cloud-config-client项目，在启动spring-cloud-config-client项目的时候我们会发现启动日志会输出这样的一条记录。

```
2017-05-26 17:05:38.568  INFO 21924 --- [           main] o.s.b.a.e.mvc.EndpointHandlerMapping     : Mapped "{[/bus/refresh],methods=[POST]}" onto public void org.springframework.cloud.bus.endpoint.RefreshBusEndpoint.refresh(java.lang.String)
```

说明客户端已经具备了消息总线通知的能力了，为了更好的模拟消息总线的效果，我们更改客户端spring-cloud-config-client项目的端口为8003、8004依次启动，这样测试环境就准备好了。启动后eureka后台效果图如下：

![img](http://www.itmind.net/assets/images/2017/springcloud/configbus3.jpg)

我们先分别测试一下服务端和客户端是否正确运行，访问：`http://localhost:8001/neo-config/dev`，返回信息：

```
{
    "name": "neo-config", 
    "profiles": [
        "dev"
    ], 
    "label": null, 
    "version": null, 
    "state": null, 
    "propertySources": [
        {
            "name": "https://github.com/ityouknow/spring-cloud-starter/config-repo/neo-config-dev.properties", 
            "source": {
                "neo.hello": "hello im dev"
            }
        }
    ]
}
```

说明server端都正常读取到了配置信息。

依次访问：`http://localhost:8002/hello`、`http://localhost:8003/hello`、`http://localhost:8004/hello`，返回：`hello im dev`。说明客户端都已经读取到了server端的内容。

现在我们更新`neo-config-dev.properties` 中`neo.hello`的值为`hello im dev update`并提交到代码库中，访问：`http://localhost:8002/hello` 依然返回`hello im dev`。我们对端口为8002的客户端发送一个`/bus/refresh`的post请求。在win下使用下面命令来模拟webhook.

```
curl -X POST http://localhost:8002/bus/refresh
```

执行完成后，依次访问：`http://localhost:8002/hello`、`http://localhost:8003/hello`、`http://localhost:8004/hello`，返回：`hello im dev update`。说明三个客户端均已经拿到了最新配置文件的信息，这样我们就实现了图一中的示例。

## 改进版本

在上面的流程中，我们已经到达了利用消息总线触发一个客户端`bus/refresh`,而刷新所有客户端的配置的目的。但这种方式并不优雅。原因如下：

- 打破了微服务的职责单一性。微服务本身是业务模块，它本不应该承担配置刷新的职责。
- 破坏了微服务各节点的对等性。
- 有一定的局限性。例如，微服务在迁移时，它的网络地址常常会发生变化，此时如果想要做到自动刷新，那就不得不修改WebHook的配置。

因此我们将上面的架构模式稍微改变一下

![img](http://www.itmind.net/assets/images/2017/springcloud/configbus2.jpg)

这时Spring Cloud Bus做配置更新步骤如下:

- 1、提交代码触发post请求给bus/refresh
- 2、server端接收到请求并发送给Spring Cloud Bus
- 3、Spring Cloud bus接到消息并通知给其它客户端
- 4、其它客户端接收到通知，请求Server端获取最新配置
- 5、全部客户端均获取到最新的配置

这样的话我们在**server端**的代码做一些改动，来支持`bus/refresh`

### 1、添加依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-bus-amqp</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
</dependencies>
引入`spring-cloud-starter-bus-amqp`包，增加对消息总线的支持
```

### 2、配置文件

```properties
server:
  port: 8001
spring:
  application:
    name: spring-cloud-config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/ityouknow/spring-cloud-starter/     # 配置git仓库的地址
          search-paths: config-repo                # git仓库地址下的相对地址，可以配置多个，用,分割。
          username: username                                        # git仓库的账号
          password: password                                    # git仓库的密码
  rabbitmq:
    host: 192.168.0.6
    port: 5672
    username: admin
    password: 123456

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8000/eureka/   ## 注册中心eurka地址


management:
  security:
     enabled: false
```

### 3、测试

依次启动spring-cloud-eureka、spring-cloud-config-server、spring-cloud-config-client项目，改动spring-cloud-config-client项目端口为8003、8004依次启动。测试环境准备完成。

按照上面的测试方式，访问server端和三个客户端测试均可以正确返回信息。同样修改`neo-config-dev.properties`中`neo.hello`的值为`hello im dev update`并提交到代码库中。在win下使用下面命令来模拟webhook触发server端`bus/refresh`.

```
curl -X POST http://localhost:8001/bus/refresh
```

执行完成后，依次访问：`http://localhost:8002/hello`、`http://localhost:8003/hello`、`http://localhost:8004/hello`，返回：`hello im dev update`。说明三个客户端均已经拿到了最新配置文件的信息，这样我们就实现了上图中的示例。

## 其它

### 局部刷新

某些场景下（例如灰度发布），我们可能只想刷新部分微服务的配置，此时可通过`/bus/refresh`端点的destination参数来定位要刷新的应用程序。

例如：`/bus/refresh?destination=customers:8000`，这样消息总线上的微服务实例就会根据destination参数的值来判断是否需要要刷新。其中，`customers:8000`指的是各个微服务的ApplicationContext ID。

destination参数也可以用来定位特定的微服务。例如：`/bus/refresh?destination=customers:**`，这样就可以触发customers微服务所有实例的配置刷新。

### 跟踪总线事件

一些场景下，我们可能希望知道**Spring Cloud Bus事件传播的细节**。此时，我们可以跟踪总线事件（RemoteApplicationEvent的子类都是总线事件）。

跟踪总线事件非常简单，只需设置`spring.cloud.bus.trace.enabled=true`，这样在`/bus/refresh`端点被请求后，访问`/trace`端点就可获得类似如下的结果：

```json
{
  "timestamp": 1495851419032,
  "info": {
    "signal": "spring.cloud.bus.ack",
    "type": "RefreshRemoteApplicationEvent",
    "id": "c4d374b7-58ea-4928-a312-31984def293b",
    "origin": "stores:8002",
    "destination": "*:**"
  }
  },
  {
  "timestamp": 1495851419033,
  "info": {
    "signal": "spring.cloud.bus.sent",
    "type": "RefreshRemoteApplicationEvent",
    "id": "c4d374b7-58ea-4928-a312-31984def293b",
    "origin": "spring-cloud-config-client:8001",
    "destination": "*:**"
  }
  },
  {
  "timestamp": 1495851422175,
  "info": {
    "signal": "spring.cloud.bus.ack",
    "type": "RefreshRemoteApplicationEvent",
    "id": "c4d374b7-58ea-4928-a312-31984def293b",
    "origin": "customers:8001",
    "destination": "*:**"
  }
}
```

这个日志显示了`customers:8001`发出了RefreshRemoteApplicationEvent事件，广播给所有的服务，被`customers:9000`和`stores:8081`接受到了。想要对接受到的消息自定义自己的处理方式的话，可以添加`@EventListener`注解的AckRemoteApplicationEvent和SentApplicationEvent类型到你自己的应用中。或者到TraceRepository类中，直接处理数据。

## `/bus/refresh` BUG

`/bus/refresh` 有一个很严重的BUG，一直没有解决：对客户端执行`/bus/refresh`之后，挂到总线的上的客户端都会从Eureka注册中心撤销登记；如果对server端执行`/bus/refresh`,server端也会从Eureka注册中心撤销登记。再用白话解释一下，就是本来人家在Eureka注册中心注册的好好的，只要你对着它执行一次`/bus/refresh`，立刻就会从Euraka中挂掉。

使用Spring Cloud最新版本的包就可以解决这个问题。

在pom中使用Spring Cloud的版本，解决这个bug.

```
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
	<java.version>1.8</java.version>
	<spring-cloud.version>Dalston.SR1</spring-cloud.version>
</properties>
```

主要是这句：`<spring-cloud.version>Dalston.SR1</spring-cloud.version>` ，详情可以参考本文示例中的代码

# 服务网关zuul

Eureka用于服务的注册于发现，Feign支持服务的调用以及均衡负载，Hystrix处理服务的熔断防止故障扩散，Spring Cloud Config服务集群配置中心

还是少考虑了一个问题，外部的应用如何来访问内部各种各样的微服务呢？

在微服务架构中，后端服务往往不直接开放给调用端，而是通过一个**API网关根据请求的url，路由到相应的服务**。当添加API网关后，在**第三方调用端和服务提供方之间就创建了一面墙**，**这面墙直接与调用方通信进行权限控制**，后**将请求均衡****分发**给后台服务端。

![img](http://www.itmind.net/assets/images/2017/springcloud/api_gateway.png)

## 为什么需要API Gateway

1、简化客户端调用复杂度

在**微服务架构模式下后端服务的实例数一般是动态的**，对于客户端而言很难发现动态改变的服务实例的访问地址信息。因此在基于微服务的项目中为了简化前端的调用逻辑，通常会引入API Gateway作为轻量级网关，同时API Gateway中也会**实现相关的认证逻辑**从而简化内部服务之间相互调用的复杂度。

2、数据裁剪以及聚合

通常而言不同的客户端对于显示时对于数据的需求是不一致的，比如手机端或者Web端又或者在低延迟的网络环境或者高延迟的网络环境。

因此为了优化客户端的使用体验，API Gateway可以对通用性的响应数据进行裁剪以适应不同客户端的使用需求。同时还可以将多个API调用逻辑进行聚合，从而减少客户端的请求数，优化客户端用户体验

3、多渠道支持

当然我们还可以针对不同的渠道和客户端提供不同的API Gateway,对于该模式的使用由另外一个大家熟知的方式叫Backend for front-end, 在Backend for front-end模式当中，我们可以针对不同的客户端分别创建其BFF

![img](http://www.itmind.net/assets/images/2017/springcloud/bff.png)

4、遗留系统的微服务化改造

对于系统而言进行微服务改造通常是由于原有的系统存在或多或少的问题，比如技术债务，代码质量，可维护性，可扩展性等等。API Gateway的模式同样适用于这一类遗留系统的改造，通过微服务化的改造逐步实现对原有系统中的问题的修复，从而提升对于原有业务响应力的提升。通过引入抽象层，逐步使用新的实现替换旧的实现。

![img](http://www.itmind.net/assets/images/2017/springcloud/bff-process.png)

> 在Spring Cloud体系中， Spring Cloud Zuul就是提供负载均衡、反向代理、权限认证的一个API gateway。

## Spring Cloud Zuul

Spring Cloud Zuul路由是微服务架构的不可或缺的一部分，提供**动态路由**，**监控**，**弹性，安全**等的边缘服务。Zuul是Netflix出品的一个**基于JVM路由**和**服务端的负载均衡器**。

Zuul是如何工作的：

### 简单使用

1、添加依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-zuul</artifactId>
</dependency>
引入spring-cloud-starter-zuul包
```

2、配置文件

```properties
spring.application.name=gateway-service-zuul
server.port=8888

#这里的配置表示，访问/it/** 直接重定向到http://www.ityouknow.com/**
zuul.routes.baidu.path=/it/**
zuul.routes.baidu.url=http://www.ityouknow.com/
```

3、启动类

```java
@SpringBootApplication
@EnableZuulProxy
//启动类添加`@EnableZuulProxy`，支持网关路由。
public class GatewayServiceZuulApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceZuulApplication.class, args);
	}
}
```

4、问题

在启动gateway-service-zuul-simple项目时，控制台报错

```java
Caused by: java.lang.ClassNotFoundException: com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect
```

检查后发现是没有加zuul依赖包。

加上依赖包后启动项目依旧报错

```java
com.netflix.discovery.shared.transport.TransportException: Cannot execute request on any known server
```

从控制台输出的错误信息和网上搜索猜想是没有引入启动注册中心。

因为在前面将的springcloud中所有的服务（小弟）都需要注册到服务中心(到大哥那里去报到)里，供大家调用。

所有这里做些修改：

1: 启动服务注册中心

2： 将zuul注册到服务中心

配置修改：

```properties
spring.application.name=gateway-service-zuul
server.port=8888

#这里的配置表示，访问/it/** 直接重定向到http://www.ityouknow.com/**
zuul.routes.baidu.path=/it/**
zuul.routes.baidu.url=http://www.ityouknow.com/

# 是否需要将自己注册到注册中心中,默认值true
eureka.client.registerWithEureka=true

# 是否从注册中心中获取注册信息,默认值true
eureka.client.fetchRegistry=true
#将自己的ip地址注册到Eureka服务中
eureka.instance.prefer-ip-address=true

# 客户端和服务端进行交互的地址
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```

完善启动类，为启动类加上`@EnableDiscoveryClient`注解，让服务能被注册中心发现

```java
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class TestzuulApplication
```

5、测试

启动`gateway-service-zuul-simple`项目，在浏览器中访问：`http://localhost:8888/it/spring-cloud`，看到页面返回了：`http://www.ityouknow.com/spring-cloud` 页面的信息，如下：

![img](http://www.itmind.net/assets/images/2017/springcloud/zuul-01.jpg)

以前示例代码`spring-cloud-producer`为例来测试请求的重定向，在配置文件中添加：

```
zuul.routes.hello.path=/hello/**
zuul.routes.hello.url=http://localhost:9000/
```

启动`spring-cloud-producer`，重新启动`gateway-service-zuul-simple`，访问：`http://localhost:8888/hello/hello?name=%E5%B0%8F%E6%98%8E`，返回：`hello 小明，this is first messge`

说明访问`gateway-service-zuul-simple`的请求自动转发到了`spring-cloud-producer`，并且将结果返回。

### 服务化

**通过url映射的方式来实现zull的转发有局限**性，比如每增加一个服务就需要配置一条内容，另外后端的服务如果是动态来提供，就不能采用这种方案来配置了。实际上**在实现微服务架构时**，**服务名与服务实例地址的关系在eureka server中已经存在**了，所以**只需要将Zuul注册到eureka server上去发现其他服务，就可以实现对serviceId的映射**。

在上面示例项目`gateway-service-zuul-simple`的基础上来改造。

1、添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
在原来的配置的基础上增加`spring-cloud-starter-eureka`包，添加对eureka的支持。
```

2、配置文件

配置修改为：

```properties
spring.application.name=gateway-service-zuul
server.port=8888

zuul.routes.api-a.path=/producer/**
zuul.routes.api-a.serviceId=spring-cloud-producer

eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```

3、测试

依次启动 `spring-cloud-eureka`、 `spring-cloud-producer`、`gateway-service-zuul-eureka`，访问：`http://localhost:8888/producer/hello?name=%E5%B0%8F%E6%98%8E`，返回：`hello 小明，this is first messge`

说明访问`gateway-service-zuul-eureka`的请求自动转发到了`spring-cloud-producer`，并且将结果返回。

为了更好的模拟服务集群，我们复制`spring-cloud-producer`项目改为`spring-cloud-producer-2`，修改`spring-cloud-producer-2`项目端口为9001，controller代码修改如下：

```java
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is two messge";
    }
}
```

修改完成后启动`spring-cloud-producer-2`，重启`gateway-service-zuul-eureka`。测试多次访问`http://localhost:8888/producer/hello?name=%E5%B0%8F%E6%98%8E`，依次返回：

```
hello 小明，this is first messge
hello 小明，this is two messge
hello 小明，this is first messge
hello 小明，this is two messge
...
```

说明通过zuul成功调用了producer服务并且做了**均衡负载**。

**网关的默认路由规则**

如果后端服务多达十几个的时候，每一个都这样配置也挺麻烦的，spring cloud zuul已经帮我们做了默认配置。**默认情况下，Zuul会代理所有注册到Eureka Server的微服务**，并且Zuul的路由规则如下：`http://ZUUL_HOST:ZUUL_PORT/微服务在Eureka上的serviceId/**`会被转发到serviceId对应的微服务。

我们注销掉`gateway-service-zuul-eureka`项目中关于路由的配置：

```
#zuul.routes.api-a.path=/producer/**
#zuul.routes.api-a.serviceId=spring-cloud-producer
```

重新启动后，访问`http://localhost:8888/spring-cloud-producer/hello?name=%E5%B0%8F%E6%98%8E`，测试返回结果和上述示例相同，说明Spring cloud zuul默认已经提供了转发功能。

**超时问题**：

参照配置：

```properties
spring:
  application:
    name: spring-cloud-zuul
  zipkin:
    base-url: http://127.0.0.1:9411
  sleuth:
    sampler:
      probability: 1.0
server:
  port: 8888

zuul:
  routes:
    producer:
      path: /producer/**
      serviceId: spring-cloud-producer
    consumer:
      path: /consumer/**
      serviceId: spring-cloud-consumer
  host:
    connect-timeout-millis: 6000
  retryable: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8000/eureka/

ribbon:
  ReadTimeout: 60000       # 设置超时时间，如果使用默认配置，请求很容易超时com.netflix.zuul.exception.ZuulException：Hystrix Readed time out
  ConnectTimeout: 60000
  MaxAutoRetries: 2
  MaxAutoRetriesNextServer: 2


# Spring Boot 2.0之后，使用EnableZipkinServer创建自定义的zipkin服务器已经被废弃
# 获取最新发布的zipkin服务器，一个可执行的jar
#
# curl -sSL https://zipkin.io/quickstart.sh | bash -s
```

# 服务网关Zuul高级篇

Zuul还有更多的应用场景，比如：**鉴权**、**流量转发**、**请求统计**等等，这些功能都可以使用Zuul来实现。

## Zuul的核心

Filter是Zuul的核心，用来实现对外服务的控制。Filter的生命周期有4个，分别是“PRE”、“ROUTING”、“POST”、“ERROR”，整个生命周期可以用下图来表示。

![img](http://www.itmind.net/assets/images/2018/springcloud/zuul-core.png)

Zuul大部分功能都是通过过滤器来实现的，这些过滤器类型对应于请求的典型生命周期。

- **PRE：** 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
- **ROUTING：**  routing（路由：按照某条线路发送）这种过滤器将请求路由到微服务。这种过滤器用于**构建发送给微服务的请求**，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
- **POST：**这种过滤器**在路由到微服务以后**执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
- **ERROR：**在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。

### Zuul中默认实现的Filter

| 类型  | 顺序 | 过滤器                  | 功能                       |
| ----- | ---- | ----------------------- | -------------------------- |
| pre   | -3   | ServletDetectionFilter  | 标记处理Servlet的类型      |
| pre   | -2   | Servlet30WrapperFilter  | 包装HttpServletRequest请求 |
| pre   | -1   | FormBodyWrapperFilter   | 包装请求体                 |
| route | 1    | DebugFilter             | 标记调试标志               |
| route | 5    | PreDecorationFilter     | 处理请求上下文供后续使用   |
| route | 10   | RibbonRoutingFilter     | serviceId请求转发          |
| route | 100  | SimpleHostRoutingFilter | url请求转发                |
| route | 500  | SendForwardFilter       | forward请求转发            |
| post  | 0    | SendErrorFilter         | 处理有错误的请求响应       |
| post  | 1000 | SendResponseFilter      | 处理正常的请求响应         |

**禁用指定的Filter**

可以在application.yml中配置需要禁用的filter，格式：

```properties
zuul:
	FormBodyWrapperFilter:
		pre:
			disable: true
```

## 自定义Filter

实现自定义Filter，需要继承ZuulFilter的类，并覆盖其中的4个方法。

```java
public class MyFilter extends ZuulFilter {
    @Override
    String filterType() {
        return "pre"; //定义filter的类型，有pre、route、post、error四种
    }

    @Override
    int filterOrder() {
        return 10; //定义filter的顺序，数字越小表示顺序越高，越先执行
    }

    @Override
    boolean shouldFilter() {
        return true; //表示是否需要执行该filter，true表示执行，false表示不执行
    }

    @Override
    Object run() {
        return null; //filter需要执行的具体操作
    }
}
```

## 自定义Filter示例

我们假设有这样一个场景，因为服务网关应对的是外部的所有请求，为了避免产生安全隐患，我们需要对请求做一定的限制，比如请求中含有Token便让请求继续往下走，如果请求不带Token就直接返回并给出提示。

首先自定义一个Filter，在run()方法中验证参数是否含有Token。

```java
public class TokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public String filterType() {
        return "pre"; // 可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());

        String token = request.getParameter("token");// 获取请求的参数

        if (StringUtils.isNotBlank(token)) {
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }
    }

}
```

将**TokenFilter加入到请求拦截队列，在启动类中**添加以下代码：

```java
@Bean
public TokenFilter tokenFilter() {
	return new TokenFilter();
}
```

这样就将我们自定义好的Filter加入到了请求拦截中。

**测试**

我们依次启动示例项目：`spring-cloud-eureka`、`spring-cloud-producer`、`spring-cloud-zuul`。`spring-cloud-zuul`稍微进行改造。

访问地址：`http://localhost:8888/spring-cloud-producer/hello?name=neo`，返回：token is empty ，请求被拦截返回。
访问地址：`http://localhost:8888/spring-cloud-producer/hello?name=neo&token=xx`，返回：hello neo，this is first messge，说明请求正常响应。

通过上面这例子我们可以看出，我们可以使用“PRE”类型的Filter做很多的验证工作，在实际使用中我们可以结合shiro、oauth2.0等技术去做鉴权、验证。

## 路由熔断

当我们的后端服务出现异常的时候，我们不希望将异常抛出给最外层，期望服务可以自动进行一降级。Zuul给我们提供了这样的支持。当某个服务出现异常时，直接返回我们预设的信息。

我们通过自定义的fallback方法，并且将其指定给某个route来实现该route访问出问题的熔断处理。主要继承ZuulFallbackProvider接口来实现，ZuulFallbackProvider默认有两个方法，一个用来指明熔断拦截哪个服务，一个定制返回内容。

```java
public interface ZuulFallbackProvider {
   /**
	 * The route this fallback will be used for.
	 * @return The route the fallback will be used for.
	 *实现类通过实现getRoute方法，告诉Zuul它是负责哪个route定义的熔断
	 */
	public String getRoute();

	/**
	 * Provides a fallback response.
	 * @return The fallback response.
	 *fallbackResponse方法则是告诉 Zuul 断路出现时，它会提供一个什么返回值来处理请求
	 */
	public ClientHttpResponse fallbackResponse();
}
```

后来Spring又扩展了此类，丰富了返回方式，在返回的内容中添加了异常信息，因此最新版本建议直接继承类`FallbackProvider` 。

```java
@Component
public class ProducerFallback implements FallbackProvider {
    private final Logger logger = LoggerFactory.getLogger(FallbackProvider.class);

    //指定要处理的 service。
    @Override
    public String getRoute() {
        return "spring-cloud-producer";
    }

    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("The service is unavailable.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        if (cause != null && cause.getCause() != null) {
            String reason = cause.getCause().getMessage();
            logger.info("Excption {}",reason);
        }
        return fallbackResponse();
    }
}
```

当服务出现异常时，打印相关异常信息，并返回”The service is unavailable.”。

启动项目spring-cloud-producer-2，这时候服务中心会有两个spring-cloud-producer项目，我们重启Zuul项目。再手动关闭spring-cloud-producer-2项目，多次访问地址：`http://localhost:8888/spring-cloud-producer/hello?name=neo&token=xx`，会交替返回：

```
hello neo，this is first messge
The service is unavailable.
...
```

根据返回结果可以看出：spring-cloud-producer-2项目已经启用了熔断，返回:`The service is unavailable.`

> Zuul 目前只支持服务级别的熔断，不支持具体到某个URL进行熔断。

## 路由重试

有时候因为网络或者其它原因，服务可能会暂时的不可用，这个时候我们希望可以再次对服务进行重试，Zuul也帮我们实现了此功能，需要结合Spring Retry 一起来实现。

**添加Spring Retry依赖**

首先在spring-cloud-zuul项目中添加Spring Retry依赖。

```xml
<dependency>
	<groupId>org.springframework.retry</groupId>
	<artifactId>spring-retry</artifactId>
</dependency>
```

**开启Zuul Retry**

再配置文件中配置启用Zuul Retry

```properties
#是否开启重试功能
zuul.retryable=true
#对当前服务的重试次数
ribbon.MaxAutoRetries=2
#切换相同Server的次数
ribbon.MaxAutoRetriesNextServer=0
```

这样就开启了Zuul的重试功能。

**测试**

我们对spring-cloud-producer-2进行改造，在hello方法中添加定时，并且在请求的一开始打印参数。

```java
@RequestMapping("/hello")
public String index(@RequestParam String name) {
    logger.info("request two name is "+name);
    try{
        Thread.sleep(1000000);
    }catch ( Exception e){
        logger.error(" hello two error",e);
    }
    return "hello "+name+"，this is two messge";
}
```

重启 spring-cloud-producer-2和spring-cloud-zuul项目。

访问地址：`http://localhost:8888/spring-cloud-producer/hello?name=neo&token=xx`，当页面返回：`The service is unavailable.`时查看项目spring-cloud-producer-2后台日志如下：

```
2018-01-22 19:50:32.401  INFO 19488 --- [io-9001-exec-14] o.s.c.n.z.f.route.FallbackProvider       : request two name is neo
2018-01-22 19:50:33.402  INFO 19488 --- [io-9001-exec-15] o.s.c.n.z.f.route.FallbackProvider       : request two name is neo
2018-01-22 19:50:34.404  INFO 19488 --- [io-9001-exec-16] o.s.c.n.z.f.route.FallbackProvider       : request two name is neo
```

说明进行了三次的请求，也就是进行了两次的重试。这样也就验证了我们的配置信息，完成了Zuul的重试功能。

**注意**

开启重试在某些情况下是有问题的，比如当压力过大，一个实例停止响应时，路由将流量转到另一个实例，很有可能导致最终所有的实例全被压垮。说到底，断路器的其中一个作用就是防止故障或者压力扩散。用了retry，断路器就只有在该服务的所有实例都无法运作的情况下才能起作用。这种时候，断路器的形式更像是提供一种友好的错误信息，或者假装服务正常运行的假象给使用者。

不用retry，仅使用负载均衡和熔断，就必须考虑到是否能够接受单个服务实例关闭和eureka刷新服务列表之间带来的短时间的熔断。如果可以接受，就无需使用retry。

## Zuul高可用

![img](http://www.itmind.net/assets/images/2018/springcloud/zuul-case.png)

我们实际使用Zuul的方式如上图，不同的客户端使用不同的负载将请求分发到后端的Zuul，Zuul在通过Eureka调用后端服务，最后对外输出。因此为了保证Zuul的高可用性，前端可以同时启动多个Zuul实例进行负载，在Zuul的前端使用Nginx或者F5进行负载转发以达到高可用性。

# 使用Spring Cloud Sleuth和Zipkin进行分布式链路跟踪

随着业务发展，系统拆分导致系统调用链路愈发复杂一个前端请求可能最终需要调用很多次后端服务才能完成，当整个请求变慢或不可用时，我们是无法得知该请求是由某个或某些后端服务引起的，这时就需要解决如何快读定位服务故障点，以对症下药。于是就有了分布式系统调用跟踪的诞生。

## Spring Cloud Sleuth

一个分布式服务跟踪系统主要有三部分：

数据收集

数据存储

数据展示

根据系统大小不同，每一部分的结构又有一定变化。譬如，对于大规模分布式系统，数据存储可分为实时数据和全量数据两部分，实时数据用于故障排查（troubleshooting），全量数据用于系统优化；数据收集除了支持平台无关和开发语言无关系统的数据收集，还包括异步数据收集（需要跟踪队列中的消息，保证调用的连贯性），以及确保更小的侵入性；数据展示又涉及到数据挖掘和分析。虽然每一部分都可能变得很复杂，但基本原理都类似。

![img](http://www.itmind.net/assets/images/2018/springcloud/tracing1.png)

服务追踪的追踪单元是从客户发起请求（request）抵达被追踪系统的边界开始，到被追踪系统向客户返回响应（response）为止的过程，称为一个“trace”。每个 trace 中会调用若干个服务，为了记录调用了哪些服务，以及每次调用的消耗时间等信息，在每次调用服务时，埋入一个调用记录，称为一个“span”。这样，若干个有序的 span 就组成了一个 trace。在系统向外界提供服务的过程中，会不断地有请求和响应发生，也就会不断生成 trace，把这些带有span 的 trace 记录下来，就可以描绘出一幅系统的服务拓扑图。附带上 span 中的响应时间，以及请求成功与否等信息，就可以在发生问题的时候，找到异常的服务；根据历史数据，还可以从系统整体层面分析出哪里性能差，定位性能优化的目标。

Spring Cloud Sleuth为服务之间调用提供**链路追踪**。通过Sleuth可以很清楚的了解到一个服务请求经过了哪些服务，每个服务处理花费了多长。从而让我们可以很方便的理清各微服务间的调用关系。

此外Sleuth可以帮助我们：

- 耗时分析: 通过Sleuth可以很方便的了解到每个采样请求的耗时，从而分析出哪些服务调用比较耗时;
- 可视化错误: 对于程序未捕捉的异常，可以通过集成Zipkin服务界面上看到;
- 链路优化: 对于调用比较频繁的服务，可以针对这些服务实施一些优化措施。

spring cloud sleuth可以结合zipkin，将信息发送到zipkin，利用zipkin的存储来存储信息，利用zipkin ui来展示数据。

这是Spring Cloud Sleuth的概念图：

![img](http://www.itmind.net/assets/images/2018/springcloud/tracing2.png)

## ZipKin

Zipkin 是一个开放源代码分布式的跟踪系统，由Twitter公司开源，它**致力于收集服务的定时数据**，以解决微服务架构中的延迟问题，包括数据的收集、存储、查找和展现。

每个服务向zipkin报告计时数据，zipkin会根据调用关系通过Zipkin UI生成依赖关系图，显示了多少跟踪请求通过每个服务，该系统让开发者可通过一个 Web 前端轻松的收集和分析数据，例如用户每次请求服务的处理时间等，可方便的监测系统中存在的瓶颈。

Zipkin提供了可插拔数据存储方式：In-Memory、MySql、Cassandra以及Elasticsearch。接下来的测试为方便直接采用In-Memory方式进行存储，生产推荐Elasticsearch。

## 快速上手

### 创建zipkin-server项目

**项目依赖**

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <dependency>
        <groupId>io.zipkin.java</groupId>
        <artifactId>zipkin-server</artifactId>
    </dependency>
    <dependency>
        <groupId>io.zipkin.java</groupId>
        <artifactId>zipkin-autoconfigure-ui</artifactId>
    </dependency>
</dependencies>
```

**启动类**

```java
@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class ZipkinApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }

}
```

**配置文件**

```properties
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 9000
spring:
  application:
    name: zipkin-server
```

配置完成后依次启动示例项目：`spring-cloud-eureka`、`zipkin-server`项目。刚问地址:`http://localhost:9000/zipkin/`可以看到Zipkin后台页面

![img](http://www.itmind.net/assets/images/2018/springcloud/tracing3.png)

### 其他项目添加zipkin支持

在项目`spring-cloud-producer`和`spring-cloud-zuul`中添加zipkin的支持。

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

Spring应用在监测到Java依赖包中有sleuth和zipkin后，会自动在RestTemplate的调用过程中向HTTP请求注入追踪信息，并向Zipkin Server发送这些信息。

同时配置文件中添加如下代码：

```properties
spring:
  zipkin:
    base-url: http://localhost:9000
  sleuth:
    sampler:
      percentage: 1.0
 #spring.zipkin.base-url指定了Zipkin服务器的地址
 #spring.sleuth.sampler.percentage将采样比例设置为1.0，也就是全部都需要
 #Spring Cloud Sleuth有一个Sampler策略，可以通过这个实现类来控制采样算法
```

项目添加zipkin之后，依次进行启动。

### 进行验证

这样我们就模拟了这样一个场景，通过外部请求访问Zuul网关，Zuul网关去调用`spring-cloud-producer`对外提供的服务。

四个项目均启动后，在浏览器中访问地址：`http://localhost:8888/producer/hello?name=neo` 两次，然后再打开地址：`http://localhost:9000/zipkin/`点击对应按钮进行查看。

点击查找看到有两条记录

![img](http://www.itmind.net/assets/images/2018/springcloud/zipkin1.png)

点击记录进去页面，可以看到每一个服务所耗费的时间和顺序

![img](http://www.itmind.net/assets/images/2018/springcloud/zipkin2.png)

点击依赖分析，可以看到项目之间的调用关系

![img](http://www.itmind.net/assets/images/2018/springcloud/zipkin3.png)

# 注册中心 Consul 使用详解

Eureka 2.X 遇到困难停止开发了。

但其实对国内的用户影响甚小，一方面国内大都使用的是 Eureka 1.X 系列，另一方面 Spring Cloud 支持很多服务发现的软件，Eureka 只是其中之一，下面是 Spring Cloud 支持的服务发现软件以及特性对比：

| Feature              | euerka                       | Consul                 | zookeeper             | etcd              |
| -------------------- | ---------------------------- | ---------------------- | --------------------- | ----------------- |
| 服务健康检查         | 可配支持                     | 服务状态，内存，硬盘等 | (弱)长连接，keepalive | 连接心跳          |
| 多数据中心           | —                            | 支持                   | —                     | —                 |
| kv 存储服务          | —                            | 支持                   | 支持                  | 支持              |
| 一致性               | —                            | raft                   | paxos                 | raft              |
| cap                  | ap                           | ca                     | cp                    | cp                |
| 使用接口(多语言能力) | http（sidecar）              | 支持 http 和 dns       | 客户端                | http/grpc         |
| watch 支持           | 支持 long polling/大部分增量 | 全量/支持long polling  | 支持                  | 支持 long polling |
| 自身监控             | metrics                      | metrics                | —                     | metrics           |
| 安全                 | —                            | acl /https             | acl                   | https 支持（弱）  |
| spring cloud 集成    | 已支持                       | 已支持                 | 已支持                | 已支持            |

在以上服务发现的软件中，Euerka 和 Consul 使用最为广泛。如果我们需要一个Euerka的替代者，可以使用Consul。

## Consul 介绍

Consul 是开源工具，用于**实现分布式系统的<u>服务发现</u>与<u>配置</u>**。

与其它分布式服务注册与发现的方案相比，Consul 的方案更“一站式”，内置了服务注册与发现框架、分布一致性协议实现、健康检查、Key/Value 存储、多数据中心方案，不再需要依赖其它工具（比如 ZooKeeper 等）。使用起来也较 为简单。

**Consul 的优势：**

- 使用 Raft 算法来保证一致性, 比复杂的 Paxos 算法更直接. 相比较而言, zookeeper 采用的是 Paxos, 而 etcd 使用的则是 Raft。
- 支持多数据中心，内外网的服务采用不同的端口进行监听。 多数据中心集群可以避免单数据中心的单点故障,而其部署则需要考虑网络延迟, 分片等情况等。 zookeeper 和 etcd 均不提供多数据中心功能的支持。
- 支持健康检查。 etcd 不提供此功能。
- 支持 http 和 dns 协议接口。 zookeeper 的集成较为复杂, etcd 只支持 http 协议。
- 官方提供 web 管理界面, etcd 无此功能。
- 综合比较, Consul 作为服务注册和配置管理的新星, 比较值得关注和研究。

**特性：**

- 服务发现
- 健康检查
- Key/Value 存储
- 多数据中心

**Consul 角色**

- client: 客户端, **无状态**, 将 HTTP 和 DNS 接口请求转发给局域网内的服务端集群。
- server: 服务端, 保存配置信息, **高可用集群**, 在局域网内与本地客户端通讯, 通过广域网与其它数据中心通讯。 每个数据中心的 server 数量推荐为 3 个或是 5 个。

Consul 客户端、服务端还支持跨中心的使用，更加提高了它的高可用性。

![img](http://www.itmind.net/assets/images/2018/springcloud/consul-server-client.png)

**Consul 工作原理：**

![img](http://www.itmind.net/assets/images/2018/springcloud/consol_service.png)

- 1、当 Producer 启动的时候，会向 Consul 发送一个 post 请求，告诉 Consul 自己的 IP 和 Port
- 2、Consul 接收到 Producer 的注册后，每隔10s（默认）会向 Producer 发送一个健康检查的请求，检验Producer是否健康
- 3、当 Consumer 发送 GET 方式请求 /api/address 到 Producer 时，会先从 Consul 中拿到一个存储服务 IP 和 Port 的临时表，从表中拿到 Producer 的 IP 和 Port 后再发送 GET 方式请求 /api/address
- 4、该临时表每隔10s会更新，只包含有通过了健康检查的 Producer

Spring Cloud Consul 项目是针对 Consul 的服务治理实现。Consul 是一个分布式高可用的系统，它包含多个组件，但是作为一个整体，在微服务架构中为我们的基础设施提供服务发现和服务配置的工具  。

## Consul 

Consul 提供了一些列特性，包括更丰富的**健康检查**，**键值对存储**以及**多数据中心**。Consul 需要每个数据中心都有一套服务，以及每个客户端的 agent，类似于使用像 Ribbon 这样的服务。Consul agent 允许大多数应用程序成为 Consul 不知情者，通过配置文件执行服务注册并通过 DNS 或负载平衡器 sidecars 发现。

**Consul 提供强大的一致性保证**，因为服务器使用 Raft 协议复制状态 。Consul **支持丰富的健康检查**，包括 TCP，HTTP，Nagios / Sensu 兼容脚本或基于 Eureka 的 TTL。客户端节点参与基于 Gossip 协议的健康检查，该检查分发健康检查工作，而不像集中式心跳检测那样成为可扩展性挑战。发现请求被路由到选举出来的 leader，这使他们默认情况下强一致性。允许客户端过时读取取使任何服务器处理他们的请求，从而实现像 Eureka 这样的线性可伸缩性。

Consul 强烈的一致性意味着它可以作为领导选举和集群协调的锁定服务。Eureka 不提供类似的保证，并且通常需要为需要执行协调或具有更强一致性需求的服务运行 ZooKeeper。

Consul 提供了支持面向服务的体系结构所需的一系列功能。这包括服务发现，还包括丰富的运行状况检查，锁定，密钥/值，多数据中心联合，事件系统和 ACL。Consul 和 consul-template 和 envconsul 等工具生态系统都试图尽量减少集成所需的应用程序更改，以避免需要通过 SDK 进行本地集成。Eureka 是一个更大的 Netflix OSS 套件的一部分，该套件预计应用程序相对均匀且紧密集成。因此 Eureka 只解决了一小部分问题，可以和 ZooKeeper 等其它工具可以一起使用。

**Consul 强一致性**(C)带来的是：

<u>服务注册相比 Eureka 会稍慢一些</u>。因为 Consul 的 raft 协议要求必须过半数的节点都写入成功才认为注册成功 Leader 挂掉时，重新选举期间整个 Consul 不可用。保证了强一致性但牺牲了可用性。

Eureka 保证高可用(A)和最终一致性：

服务注册相对要快，因为不需要等注册信息 replicate 到其它节点，也不保证注册信息是否 replicate 成功 当数据出现不一致时，虽然 A, B 上的注册信息不完全相同，但每个 Eureka 节点依然能够正常对外提供服务，这会出现查询服务信息时如果请求 A 查不到，但请求 B 就能查到。如此保证了可用性但牺牲了一致性。

## Consul 安装

Consul 不同于 Eureka ，需要单独安装，访问[Consul 官网](https://www.consul.io/downloads.html)下载 Consul 的最新版本，我这里是 consul_1.2.1。

根据不同的系统类型选择不同的安装包，从下图也可以看出 Consul 支持所有主流系统。

![img](http://www.itmind.net/assets/images/2018/springcloud/consul_insall.png)

我这里以 Windows 为例，下载下来是一个 consul_1.2.1_windows_amd64.zip 的压缩包，解压是是一个 consul.exe 的执行文件。

![img](http://www.itmind.net/assets/images/2018/springcloud/consul_win.png)

cd 到对应的目录下，使用 cmd 启动 Consul

```
cd D:\Common Files\consul
#cmd启动：
consul agent -dev        # -dev表示开发模式运行，另外还有-server表示服务模式运行
```

为了方便期间，可以在同级目录下创建一个 run.bat 脚本来启动，脚本内容如下：

```
consul agent -dev
pause
```

启动结果如下：

![img](http://www.itmind.net/assets/images/2018/springcloud/consol_cmd.png)

启动成功之后访问：`http://localhost:8500`，可以看到 Consul 的管理界面

![img](http://www.itmind.net/assets/images/2018/springcloud/consol_manage.png)

这样就意味着我们的 Consul 服务启动成功了。

## Consul 服务端

我们开发 Consul 的服务端，我们创建一个 spring-cloud-consul-producer 项目

### 添加依赖包

依赖包如下：

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-consul-discovery</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>
spring-boot-starter-actuator 健康检查依赖于此包。
spring-cloud-starter-consul-discovery. Spring Cloud Consul 的支持
```

Spring Boot 版本使用的是 2.0.3.RELEASE，Spring Cloud 最新版本是 Finchley.RELEASE 依赖于 Spring Boot 2.x.

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.0.3.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->
</parent>

<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>${spring-cloud.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

### 配置文件

配置文件内容如下

```properties
spring.application.name=spring-cloud-consul-producer
server.port=8501
spring.cloud.consul.host=localhost
spring.cloud.consul.port=8500
#spring-cloud-starter-consul-discovery` Spring Cloud Consul 的支持
spring.cloud.consul.discovery.serviceName=service-producer
#注册到consul的服务名称
#spring.cloud.consul.discovery.serviceName 是指注册到 Consul 的服务名称，后期客户端会根据这个名称来进行服务调用。
```

### 启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
//@EnableDiscoveryClient` 注解表示支持服务发现
public class ConsulProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulProducerApplication.class, args);
	}
}
```

我们在创建一个 Controller，推文提供 hello 的服务。

```java
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "helle consul";
    }
}
```

为了模拟注册均衡负载复制一份上面的项目重命名为 spring-cloud-consul-producer-2 ,修改对应的端口为 8502，修改 hello 方法的返回值为：”helle consul two”，修改完成后依次启动两个项目。

这时候我们再次在浏览器访问地址：http://localhost:8500，显示如下：

![img](http://www.itmind.net/assets/images/2018/springcloud/consol_producer.png)

我们发现页面多了 service-producer 服务，点击进去后页面显示有两个服务提供者：

![img](http://www.itmind.net/assets/images/2018/springcloud/consol_producer-2.png)

这样服务提供者就准备好了。

## Consul 消费端

我们创建一个 spring-cloud-consul-consumer 项目，pom 文件和上面示例保持一致。

### 配置文件

配置文件内容如下

```properties
spring.application.name=spring-cloud-consul-consumer
server.port=8503
spring.cloud.consul.host=127.0.0.1
spring.cloud.consul.port=8500
#设置不需要注册到 consul 中
spring.cloud.consul.discovery.register=false
#客户端可以设置注册到 Consul 中，也可以不注册到 Consul 注册中心中，根据我们的业务来选择，只需要在使用服务时通过 Consul 对外提供的接口获取服务信息即可。
```

### 启动类

```java
@SpringBootApplication
public class ConsulConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsulConsumerApplication.class, args);
	}
}
```

### 进行测试

我们先来创建一个 ServiceController ,试试如果去获取 Consul 中的服务。

```java
@RestController
public class ServiceController {

    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;

   /**
     * 获取所有服务
     */
    @RequestMapping("/services")
    public Object services() {
        return discoveryClient.getInstances("service-producer");
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancer.choose("service-producer").getUri().toString();
    }
}
```

Controller 中有俩个方法，一个是获取所有服务名为`service-producer`的服务信息并返回到页面，一个是随机从服务名为`service-producer`的服务中获取一个并返回到页面。

添加完 ServiceController 之后我们启动项目，访问地址：`http://localhost:8503/services`，返回：

```
[{"serviceId":"service-producer","host":"windows10.microdone.cn","port":8501,"secure":false,"metadata":{"secure":"false"},"uri":"http://windows10.microdone.cn:8501","scheme":null},{"serviceId":"service-producer","host":"windows10.microdone.cn","port":8502,"secure":false,"metadata":{"secure":"false"},"uri":"http://windows10.microdone.cn:8502","scheme":null}]
```

发现我们刚才创建的端口为 8501 和 8502 的两个服务端都存在。

多次访问地址：`http://localhost:8503/discover`，页面会交替返回下面信息：

```
http://windows10.microdone.cn:8501
http://windows10.microdone.cn:8502
...
```

说明 8501 和 8501 的两个服务会交替出现，从而实现了获取服务端地址的均衡负载。

大多数情况下我们希望使用均衡负载的形式去获取服务端提供的服务，因此使用第二种方法来模拟调用服务端提供的 hello 方法。

创建 CallHelloController ：

```java
@RestController
public class CallHelloController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @RequestMapping("/call")
    public String call() {
        ServiceInstance serviceInstance = loadBalancer.choose("service-producer");
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());
        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }

}
```

**使用 RestTemplate 进行远程调用**。添加完之后重启 spring-cloud-consul-consumer 项目。在浏览器中访问地址：`http://localhost:8503/call`，依次返回结果如下：

```
helle consul
helle consul two
...
```

说明我们已经成功的调用了 Consul 服务端提供的服务，并且实现了服务端的均衡负载功能。通过今天的实践我们发现 Consul 提供的服务发现易用、强大。