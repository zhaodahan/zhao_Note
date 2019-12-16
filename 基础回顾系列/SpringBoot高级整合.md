#  尚硅谷_SpringBoot高级(整合篇)



着重介绍SpringBoot的与各大场景的整合使用，内容包括：缓存（整合Redis）、消息中间件（整合RabbitMQ）、检索（整合ElasticSearch）、任务（异步任务，定时任务，邮件任务）、安全（整合SpringSecurity）、分布式（整合Zookeeper/dubbo，整合SpringCloud）、SpringBoot应用监管

# springBoot整合缓存



## springBoot缓存抽象

![JAVA_SPRINGBOOT16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT16.png?raw=true)

```
Spring从3.1开始定义了org.springframework.cache.Cache和org.springframework.cache.CacheManager接口来统一不同的缓存技术；并支持使用JCache（JSR-107）注解简化我们开发；

spring 基于JSR1.7规范定义了自己的缓存抽象来简化缓存开发，并支持使用JSR-107注解简化我们开发
```

![JAVA_SPRINGBOOT17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT17.png?raw=true)

**重要概念&缓存注解**

| Cache          | 缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等 |
| -------------- | ------------------------------------------------------------ |
| CacheManager   | 缓存管理器，管理各种缓存（Cache）组件                        |
| @Cacheable     | 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存 (常用于查询方法) |
| @CacheEvict    | 清空缓存(常用于删除方法)                                     |
| @CachePut      | 保证方法被调用，又希望结果被缓存。(常用于更新方法)           |
| @EnableCaching | 开启基于注解的缓存 (加在springBoot启动类上， 开启缓存自动配置) |
| keyGenerator   | 缓存数据时key生成策略                                        |
| serialize      | 缓存数据时value序列化策略                                    |

## springBoot使用注解缓存示例

一： 引入依赖

```xml
	  <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
```

二： 开启基于注解的缓存

```java
在启动类上加上@EnableCaching 注解
@MapperScan("com.atguigu.cache.mapper")
@SpringBootApplication
@EnableCaching
public class Springboot01CacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot01CacheApplication.class, args);
	}
}
```

三： 在需要使用缓存的方法加上注解

```java
@Cacheable主要针对方法配置，能够根据方法的请求参数对其结果进行缓存 (常用于查询方法)
@CacheEvict清空缓存(常用于删除方法)
@CachePut保证方法被调用，又希望结果被缓存。(常用于更新方法)

 将方法的运行结果进行缓存；以后再要相同的数据，直接从缓存中获取，不用调用方法；
 CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字
 @Cacheable(value = {"emp"}
              /*,keyGenerator = "myKeyGenerator",condition = "#a0>1",unless = "#a0==2"*/)
  public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
 }

@CachePut：既调用方法，又更新缓存数据；同步更新缓存。修改了数据库的某个数据，同时更新缓存；
运行时机：
1、先调用目标方法
2、再将目标方法的结果缓存起来
注意：
查询1号员工；查到的结果会放在缓存中； getEmp(Integer id)
    key：1  value：lastName：张三
更新1号员工；【lastName:zhangsan；gender:0】 @CachePut将方法的返回值也放进缓存了；
但是，这里默认的key：传入的employee对象  值：返回的employee对象；
这时调用getEmp(Integer id)就还是之前的缓存，1号员工没有在缓存中更新
解决方案，是指定更新的缓存key 
key = "#employee.id":使用传入的参数的员工id；
key = "#result.id"：使用返回后的id
总结： 注意@CachePut 是方法运行后往缓存中放数据的
 @CachePut(/*value = "emp",*/key = "#result.id")
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp:"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

@CacheEvict：缓存清除
key：指定要清除的数据
boolean allEntries() default false;
boolean beforeInvocation() default false;
allEntries = true：指定清除这个name下缓存中所有的数据， 默认是false
beforeInvocation = false：缓存的清除是否在方法之前执行。默认代表缓存清除操作是在方法执行之后执行;如果出现异常缓存就不会清除
beforeInvocation = true：
代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除
   @CacheEvict(value="emp",beforeInvocation = true/*key = "#id",*/)
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
        //employeeMapper.deleteEmpById(id);
        int i = 10/0;
    }
===========================================================
 几个属性：
 public @interface Cacheable {
   //cacheNames/value：指定缓存组件的名字;将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；
  // CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字
    @AliasFor("cacheNames")
    String[] value() default {};
    @AliasFor("value")
    String[] cacheNames() default {};
   // key：缓存数据时使用的key；可以用它来指定。如果不制定默认是使用方法参数的值id  值-方法的返回值
   //编写SpEL指定key； #id;参数id的值   #a0  #p0  #root.args[0]getEmp[2] 
    String key() default "";
    //keyGenerator：key的自动生成器；可以自己指定key的生成器的组件id
    // key/keyGenerator：二选一使用;
    String keyGenerator() default "";
    //cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器
    String cacheManager() default "";
    String cacheResolver() default "";
    // condition：指定符合条件的情况下才缓存；
    //condition = "#id>0" condition = "#a0>1"：第一个参数的值》1的时候才进行缓存
    String condition() default "";
    // unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果#result进行判断
    //unless = "#result == null"
    //unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
    String unless() default "";
    //  sync：是否使用异步模式  ，异步模式unless就不支持了  
    boolean sync() default false;
}   

 @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
 如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
 
 @Cacheable的运行流程：
 1、方法运行之前，先去cacheMap中查询Cache（缓存组件），按照cacheNames指定的名字获取；
 （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建
 2、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数
 key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
 SimpleKeyGenerator生成key的默认策略；
 如果没有参数；key=new SimpleKey()
 如果有一个参数：key=参数的值
 如果有多个参数：key=new SimpleKey(params)；
 3、没有查到缓存就调用目标方法；
 4、将目标方法返回的结果，放进缓存中
 
核心：
  1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
  2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
 
注意：
 @Cacheable的key是不能用key = "#result.id" 作为指定的key的，因为在查询前就会使用key去缓存中查询，但是
 #result只有查询完成之后才能获取到值
     
@Cacheable 作用原理
 1、自动配置类；CacheAutoConfiguration
 2、CacheAutoConfiguration给容器中导入了 缓存的配置类组件
     *   org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
     *   org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
 3、哪个配置类默认生效：SimpleCacheConfiguration；
 4、给容器中注册了一个CacheManager：ConcurrentMapCacheManager
 5、可以获取和创建ConcurrentMapCache类型的缓存组件；他的作用将数据保存在ConcurrentMap中；

```

@Caching 与@CacheConfig

```java
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Caching {
    Cacheable[] cacheable() default {};
    CachePut[] put() default {};
    CacheEvict[] evict() default {};
}

@Caching是@Cacheable，@CacheEvict，@CachePut 的合集
在方法十分复杂的时候就可以使用这个注解
===================
 // @Caching 定义复杂的缓存规则
    @Caching(
         cacheable = {
             @Cacheable(/*value="emp",*/key = "#lastName")
         },
         put = {
             @CachePut(/*value="emp",*/key = "#result.id"),
             @CachePut(/*value="emp",*/key = "#result.email")
         }
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }
====================
    @Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheConfig {
    String[] cacheNames() default {};
    String keyGenerator() default "";
    String cacheManager() default "";
    String cacheResolver() default "";
}
==============
//抽取类中缓存的公共配置
@CacheConfig(cacheNames="emp"/*,cacheManager = "employeeCacheManager"*/) 
@Service
public class EmployeeService {。。。。。。。}
```



## springBoot整合Redis

我们常使用Redis来做缓存

```java
springBoot默认使用的是ConcurrentMapCacheManager==ConcurrentMapCache；将数据保存在ConcurrentMap<Object, Object>中

但是实际开发中我们使用缓存中间件；redis、memcached、ehcache；

缓存注解的配置原理中我们知道
     *   org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
     *   org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
springBoot默认支持很多种缓存中间件的自动配置，如：  RedisCacheConfiguration，EhCacheCacheConfiguration
而且我们知道，只要导入了响应的包，这些配置就能起作用

以整合Redis为例：
一： 引入Redis starts 依赖(Redis与数据访问有关，所以是data下)
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
二： 在主配置文件中制定Redis的主机地址
spring.redis.host=118.24.44.169

三： 在代码中使用
@Configuration
protected static class RedisConfiguration {
//...........

 @Bean
 @ConditionalOnMissingBean(name = {"redisTemplate"})
public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
     //...........
}

@Bean
 @ConditionalOnMissingBean({StringRedisTemplate.class})
public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
           //...........
        }
 }
他帮我们引入了操作Redis的两个常用template，我们实际在代码中操作就是使用这两个Template
StringRedisTemplate 这个是操作字符串的，更加的常用

	@Autowired
	StringRedisTemplate stringRedisTemplate;  //操作k-v都是字符串的
	@Autowired
	RedisTemplate redisTemplate;  //k-v都是对象的
	@Autowired
	RedisTemplate<Object, Employee> empRedisTemplate;

RedisTemplate默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中。 但是我们实际上更加常用的是以json字符串的形式保存这个值
//redisTemplate.opsForValue().set("emp-01",empById);
1、将数据以json的方式保存
(1)自己将对象转为json
(2)redisTemplate有其默认的序列化规则；改变默认的序列化规则；

@Configuration
public class MyRedisConfig {
//这里的泛型可以配置成Object，作为通用的
@Bean
public RedisTemplate<Object, Employee> empRedisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException {
RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
template.setConnectionFactory(redisConnectionFactory);
Jackson2JsonRedisSerializer<Employee> ser = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
//重点，重新设置默认的序列化器
template.setDefaultSerializer(ser);
return template;
}

四： 将我们的CacheManager 变成我们操作Redis缓存的CacheManager。 从而使用cache注解操作Redis缓存
原理：CacheManager===Cache 缓存组件来实际给缓存中存取数据
1）、引入redis的starter，容器中保存的是 RedisCacheManager；
   这时候RedisCacheConfiguration 就能生效
2）、RedisCacheManager 帮我们创建 RedisCache 来作为缓存组件；RedisCache通过操作redis缓存数据的
3）、默认保存数据 k-v 都是Object；利用序列化保存；如何保存为json
 1、引入了redis的starter，cacheManager变为 RedisCacheManager；
 2、默认创建的 RedisCacheManager 操作redis的时候使用的是 RedisTemplate<Object, Object>
 3、RedisTemplate<Object, Object> 是 默认使用jdk的序列化机制
4）、自定义CacheManager；
@Configuration
public class MyRedisConfig {

  @Bean
public RedisTemplate<Object, Employee> empRedisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException {
RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
template.setConnectionFactory(redisConnectionFactory);
Jackson2JsonRedisSerializer<Employee> ser = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
//重点，重新设置默认的序列化器
template.setDefaultSerializer(ser);
return template;
}
  
    //CacheManagerCustomizers可以来定制缓存的一些规则
    @Primary  //将某个缓存管理器作为默认的
    @Bean
 public RedisCacheManager employeeCacheManager(RedisTemplate<Object, Employee> empRedisTemplate)  {
        RedisCacheManager cacheManager = new RedisCacheManager(empRedisTemplate);
        //key多了一个前缀
        //使用前缀，默认会将CacheName作为key的前缀
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
}
(默认的CacheManager 注解的生效条件是容器中不存在CacheManager，当我们自定义注入了RedisCacheManager 后，默认自动配置中配置的 CacheManager 就失效了)

容器中自定义RedisCacheManager要么只有一个通用的，都是string类型的。如果配置的是针对个别对象的RedisTemplate<Object, Employee> empRedisTemplate， 那么所有缓存的对象都会被存成Employee的json,在读取缓存的时候就会出现json转换异常，解决方法就是针对不同的对象配置各自的RedisCacheManager ，然后在各自指定的统一@CacheConfig 中制定各自的RedisCacheManager
@CacheConfig(cacheNames="emp",cacheManager = "employeeCacheManager") 

```

# springBoot 整合消息中间件

什么是消息中间件？

![JAVA_SPRINGBOOT18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT18.png?raw=true)

![JAVA_SPRINGBOOT19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT19.png?raw=true)

```
1：应用中，可通过消息服务中间件来提升系统 <异步通信>、扩展解耦能力
2:消息服务中两个重要概念
	消息代理（message broker就是消息中间件的服务器）和目的地（destination）
	(消息中间件服务器，我们要给消息中间中放入内容就要先连接消息中间件服务器)
当消息发送者发送消息以后，将由消息代理接管，消息代理保证消息传递到指定目的地
3:消息队列主要有两种形式的目的地
    1. 队列（queue）：点对点消息通信（point-to-point）
    2. 主题（topic）：发布（publish）/订阅（subscribe）消息通信
4： 消息服务规范
JMS（Java Message Service）JAVA消息服务：基于JVM消息代理的规范。
ActiveMQ、HornetMQ是JMS实现

AMQP（Advanced Message Queuing Protocol）高级消息队列协议，也是一个消息代理的规范，兼容JMS
RabbitMQ是AMQP的实现

```

![JAVA_SPRINGBOOT20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT20.png?raw=true)

spring支持

```
Spring支持
spring-jms提供了对JMS的支持
spring-rabbit提供了对AMQP的支持
需要ConnectionFactory的实现来连接消息代理。提供JmsTemplate、RabbitTemplate来发送消息
@JmsListener（JMS）、@RabbitListener（AMQP）注解在方法上监听消息代理发布的消息
@EnableJms、@EnableRabbit开启支持

Spring Boot自动配置
JmsAutoConfiguration
RabbitAutoConfiguration、
(最简单的情况下，只需要配置一个消息代理的服务器就可以)

```

## RabbitMQ

![JAVA_SPRINGBOOT21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT21.png?raw=true)

```
RabbitMQ是一个由erlang开发的AMQP(Advanved Message Queue Protocol)的开源实现。

核心概念：
Message
消息，它由消息头和消息体组成。消息体是不透明的，而消息头则由一系列的可选属性组成， (类似于http消息)
这些属性包括routing-key（路由键： 发送到的目的地地址IP）、
priority（相对于其他消息的优先权）、
delivery-mode（指出该消息可能需要持久性存储）等。

Publisher
消息的生产者，也是一个向交换器发布消息的客户端应用程序。

Exchange 交换器，用来接收生产者发送的消息并将这些消息路由给服务器中的队列。（类似于网络中的交换器）
他绑定了很多队列(消息到底要到那个队列，是通过消息的路由键指定的)
Exchange有4种类型：
direct(默认，实现点对点模型)，
下面三个实现发布订阅模型
fanout, 
topic, 
headers，
不同类型的Exchange转发消息的策略有所区别

Queue
消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走。

Binding
绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。Exchange 和Queue的绑定可以是多对多的关系。

Connection
网络连接，比如一个TCP连接。

Channel
信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内的虚拟连接，AMQP 命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，这些动作都是通过信道完成。因为对于操作系统来说建立和销毁 TCP 都是非常昂贵的开销，所以引入了信道的概念，以复用一条 TCP 连接。

Consumer
消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。

Virtual Host ： 就是可以将一个消息服务器划分成多个小的虚拟主机，每个虚拟主机就是一个小的独立的消息服务器
虚拟主机，表示一批交换器、消息队列和相关对象。虚拟主机是共享 相同的身份认证和加密环境的独立服务器域。每个 vhost 本质上就是一个 mini 版的 RabbitMQ 服务器，拥有自己的队列、交换器、绑定和权限机制。vhost 是 AMQP 概念的基础，必须在连接时指定，RabbitMQ 默认的 vhost 是 / 。

Broker
表示消息队列服务器实体
```

RabbitMQ运行机制

![JAVA_SPRINGBOOT22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT22.png?raw=true)

## springBoot  整合RabbitMQ

```java
1： 导入依赖
       	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
2：配置文件中配置mq 主机地址，和访问用户名，密码
spring.rabbitmq.host=118.24.44.169
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

3： 简单试用
    @Autowired
	RabbitTemplate rabbitTemplate;
	/**
	 * 1、单播（点对点）
	 */
	@Test
	public void contextLoads() {
		//Message需要自己构造一个;定义消息体内容和消息头
		//rabbitTemplate.send(exchage,routeKey,message);

		//object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq；
		//rabbitTemplate.convertAndSend(exchage,routeKey,object);
		Map<String,Object> map = new HashMap<>();
		map.put("msg","这是第一个消息");
		map.put("data", Arrays.asList("helloworld",123,true));
		//对象被默认序列化以后发送出去
		rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new Book("西游记","吴承恩"));

	}

	//接受数据,
	@Test
	public void receive(){
		Object o = rabbitTemplate.receiveAndConvert("atguigu.news");
		System.out.println(o.getClass());
		System.out.println(o);
	}

	/**
	 * 广播
	 */
	@Test
	public void sendMsg(){
		rabbitTemplate.convertAndSend("exchange.fanout","",new Book("红楼梦","曹雪芹"));
	}

4： 自动配置原理
 *  1、RabbitAutoConfiguration
 *  2、有自动配置了连接工厂ConnectionFactory；
 *  3、RabbitProperties 封装了 RabbitMQ的配置
 *  4、 RabbitTemplate ：给RabbitMQ发送和接受消息；
 *  5、 AmqpAdmin ： RabbitMQ系统管理功能组件;
 *  	AmqpAdmin：创建和删除 Queue，Exchange，Binding
 *  6、@EnableRabbit +  @RabbitListener 监听消息队列的内容

5：如何将数据自动的转为json发送出去
我们默认是以java序列化的方式转发，如果需要更改方式，只需要更改注入的MessageConverter 即可
@Configuration
public class MyAMQPConfig {
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}

6：@EnableRabbit +  @RabbitListener 监听消息队列的内容
实际开发中，需要一些监听功能，springBoot为了简化我们的开发提供@EnableRabbit +  @RabbitListener 注解
@EnableRabbit  //开启基于注解的RabbitMQ模式
@SpringBootApplication
public class Springboot02AmqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot02AmqpApplication.class, args);
	}
}

@Service
public class BookService {

    @RabbitListener(queues = "atguigu.news")
    public void receive(Book book){
        System.out.println("收到消息："+book);
    }

    @RabbitListener(queues = "atguigu")
    public void receive02(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties()); //可以获取到消息头信息
    }
}

7：管理组件实用
	@Autowired
	AmqpAdmin amqpAdmin;
	@Test
	public void createExchange(){
        //创建交换器
		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
		System.out.println("创建完成");
        
        //创建队列
	    amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));
        
		//创建绑定规则        
amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.haha",null));
	}
```



# springBoot 整合检索

应用经常需要添加检索功能，开源的 ElasticSearch 是目前全文搜索引擎的首选。他可以快速的存储、搜索和分析海量数据。Spring Boot通过整合Spring Data ElasticSearch为我们提供了非常便捷的检索功能支持；

## ElasticSearch

Elasticsearch是一个分布式搜索服务.github,维基百科 等大型的站点也是采用了ElasticSearch作为其搜索服务

```json
Elasticsearch  是面向文档的，他存储的是整个对象或者整个文档。(不像mysql存储的是数据的行列)
存成文档后，可以对数据进行索引(作为动词就是存储数据的行为，作为名称-类似mysql的数据库)、搜索、排序、过滤。
ELasticsearch使用JSON对文档进行序列化。(json 天然的轻量级和跨平台)
Elasticsearch是用java编写的 他提供Restful API  让我们操作Elasticsearch


添加数据：
PUT /megacorp/employee/1
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}
megacorp	索引名
employee	类型名
1	这个员工的ID

搜索：
GET /megacorp/employee/1
HEAD 。。。 (是检查是否存在)
DELETE .... （是删除）
我们通过HTTP方法GET来检索文档，DELETE方法删除文档，HEAD方法检查某文档是否存在。如果想更新已存在的文档，我们只需再PUT一次。 (ID 和数据库类似是唯一标识)

简单搜索：
搜索全部员工的请求：GET /megacorp/employee/_search
带上条件的：GET /megacorp/employee/_search?q=last_name:Smith
不写查询字符串，还可以写查询json表达式 （post）
GET /megacorp/employee/_search
{
    "query" : {
        "match" : {
            "last_name" : "Smith"
        }
    }
}
根据复杂的查询
要找到姓氏为“Smith”的员工，但是我们只想得到年龄大于30岁的员工
GET /megacorp/employee/_search
{
    "query" : {
        "filtered" : {
            "filter" : {
                "range" : {
                    "age" : { "gt" : 30 } 
                }
            },
            "query" : {
                "match" : {
                    "last_name" : "smith" 
                }
            }
        }
    }
}
全文搜索：模糊查询
搜索所有喜欢“rock climbing”的员工
GET /megacorp/employee/_search
{
    "query" : {
        "match" : {
            "about" : "rock climbing"
        }
    }
}
主要about字段包含了rock climbing 部分的都会被查出来(类似于百度搜索关键字)

短语搜索:精确查找
查询同时包含"rock"和"climbing"（并且是相邻的）的员工记录，
要做到这个，我们只要将match查询变更为match_phrase查询即可:
GET /megacorp/employee/_search
{
    "query" : {
        "match_phrase" : {
            "about" : "rock climbing"
        }
    }
}

高亮搜索 (类似于百度搜索关键词，将搜索结果高亮显示)
在之前的语句上增加highlight参数
GET /megacorp/employee/_search
{
    "query" : {
        "match_phrase" : {
            "about" : "rock climbing"
        }
    },
    "highlight": {
        "fields" : {
            "about" : {}
        }
    }
}
返回结果中会有一个新的部分叫做highlight，包含了来自about字段中的文本，并且用<em></em>来标识匹配到的单词
```

![JAVA_SPRINGBOOT23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT23.png?raw=true)

## springBoot整合ElasticSearch

```java
1：引入spring-boot-starter-data-elasticsearch
安装Spring Data 对应版本的ElasticSearch
<!--SpringBoot默认使用SpringData ElasticSearch模块进行操作-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
		</dependency>
		<!-- https://mvnrepository.com/artifact/io.searchbox/jest -->
		<dependency>
			<groupId>io.searchbox</groupId>
			<artifactId>jest</artifactId>
			<version>5.3.3</version>
		</dependency>

2:application.yml配置

3:Spring Boot自动配置做了哪些配置
	ElasticsearchRepository、ElasticsearchTemplate、Jest
	
SpringBoot默认支持两种技术来和ES交互；
* 1、Jest（默认不生效）需要导入jest的工具包（io.searchbox.client.JestClient）来让其生效
* 2、SpringData ElasticSearch
        默认配置了下面的信息
 * 		1）、Client客户端  需要指定节点信息clusterNodes；clusterName
 * 		2）、ElasticsearchTemplate 操作es
 *		3）、编写一个 ElasticsearchRepository 的子接口来操作ES；

4: 使用Jest 
1）先将spring-boot-starter-data-elasticsearch 依赖去掉，引入jest让器自动配置生效
2）根据JestProperties 来配置application.yml配置
配置 ElasticSearch服务器地址
spring.elasticsearch.jest.uris=http://118.24.44.169:9200
(默认进行web通信使用端口9200)
3）Jest 的基本使用
@Autowired
	JestClient jestClient;
@Test
	public void contextLoads() {
		//1、给Es中索引（保存）一个文档；
		Article article = new Article();
		article.setId(1);
		article.setTitle("好消息");
		article.setAuthor("zhangsan");
		article.setContent("Hello World");

		//构建一个索引功能
		Index index = new Index.Builder(article).index("atguigu").type("news").build();

		try {
			//执行
			jestClient.execute(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//测试搜索
	@Test
	public void search(){

		//查询表达式
		String json ="{\n" +
				"    \"query\" : {\n" +
				"        \"match\" : {\n" +
				"            \"content\" : \"hello\"\n" +
				"        }\n" +
				"    }\n" +
				"}";

		//更多操作：https://github.com/searchbox-io/Jest/tree/master/jest
		//构建搜索功能
		Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();

		//执行
		try {
			SearchResult result = jestClient.execute(search);
			System.out.println(result.getJsonString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

5： SpringData ElasticSearch  
1）依赖使用spring-boot-starter-data-elasticsearch
application.yml配置：
spring.data.elasticsearch.cluster-name=elasticsearch
spring.data.elasticsearch.cluster-nodes=118.24.44.169:9300
 (默认进行web通信使用端口9200，分布式情况下，各个节点间进行通信使用端口9300)
2）【ES版本有可能不合适】
如果版本不适配：2.4.6
 *			1）、升级SpringBoot版本
 *			2）、安装对应版本的ES
3）两种用法：https://github.com/spring-projects/spring-data-elasticsearch
   a）、编写一个 ElasticsearchRepository
public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
    //参照
    // https://docs.spring.io/spring-data/elasticsearch/docs/3.0.6.RELEASE/reference/html/
//支持自定义方法
   public List<Book> findByBookNameLike(String bookName);

}
测试方法：
//@Document 指定存储索引，类型地址
@Document(indexName = "atguigu",type = "book")
public class Book {
    private Integer id;
    private String bookName;
    private String author;
}


@Autowired
	BookRepository bookRepository;
	@Test
	public void test02(){
//		Book book = new Book();
//		book.setId(1);
//		book.setBookName("西游记");
//		book.setAuthor("吴承恩");
//		bookRepository.index(book);
		for (Book book : bookRepository.findByBookNameLike("游")) {
			System.out.println(book);
		};
	}
```

![JAVA_SPRINGBOOT24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT24.png?raw=true)

# springBoot 整合任务

## 异步任务

```java
Java应用中，绝大多数情况下都是通过同步的方式来实现交互处理的；但是在处理与第三方系统交互的时候，容易造成响应迟缓的情况，之前大部分都是使用多线程来完成此类任务，其实，在Spring 3.x之后，就已经内置了@Async来完美解决这个问题。

在实际开发中，在我们发送一些邮件，或者处理一些数据，我们不想让其阻塞下面的线程，我们就可以使用多线程的方式来进行异步处理。

@EnableAsync  //开启异步注解功能
@SpringBootApplication
public class Springboot04TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot04TaskApplication.class, args);
	}
}

@Service
public class AsyncService {

    //告诉Spring这是一个异步方法. 我们无需手动编写一个多线程方法来进行异步处理，spring会自己开一个线程来执行这个方法。
    @Async
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中...");
    }
}
```



## 定时任务

```java
开发中经常需要执行一些定时任务，比如需要在每天凌晨时候，分析一次前一天的日志信息。Spring为我们提供了异步执行任务调度的方式，提供TaskExecutor 、TaskScheduler 接口。
两个注解：@EnableScheduling、@Scheduled

@EnableScheduling //开启基于注解的定时任务
@SpringBootApplication
public class Springboot04TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot04TaskApplication.class, args);
	}
}

@Service
public class ScheduledService {

    /**
     * second(秒), minute（分）, hour（时）, day of month（日）, month（月）, day of week（周几）.
     * 0 * * * * MON-FRI
     *  【0 0/5 14,18 * * ?】 每天14点整，和18点整，每隔5分钟执行一次
     *  【0 15 10 ? * 1-6】 每个月的周一至周六10:15分执行一次
     *  【0 0 2 ? * 6L】每个月的最后一个周六凌晨2点执行一次
     *  【0 0 2 LW * ?】每个月的最后一个工作日凌晨2点执行一次
     *  【0 0 2-4 ? * 1#1】每个月的第一个周一凌晨2点到4点期间，每个整点都执行一次；
     */
   // @Scheduled(cron = "0 * * * * MON-SAT")
    //@Scheduled(cron = "0,1,2,3,4 * * * * MON-SAT")
   // @Scheduled(cron = "0-4 * * * * MON-SAT")
    @Scheduled(cron = "0/4 * * * * MON-SAT")  //每4秒执行一次
    public void hello(){
        System.out.println("hello ... ");
    }
}

重点是cron  表达式，http://cron.qqe2.com/ 中可以在线生成
```

![JAVA_SPRINGBOOT25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT25.png?raw=true)

## 邮件任务

![JAVA_SPRINGBOOT26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT26.png?raw=true)

```java
1:邮件发送需要引入spring-boot-starter-mail
	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
2:Spring Boot 自动配置MailSenderAutoConfiguration
3:定义MailProperties内容，配置在application.yml中
spring.mail.username=534096094@qq.com
spring.mail.password=gtstkoszjelabijb （这里是qq邮箱提供的第三方登录的授权吗，可以代替密码）
spring.mail.host=smtp.qq.com  (smtp 的服务地址，默认协议是smtp)
spring.mail.properties.mail.smtp.ssl.enable=true (开启安全设置)
4:自动装配JavaMailSender
public class Springboot04TaskApplicationTests {
	@Autowired
	JavaMailSenderImpl mailSender;
	@Test
	public void contextLoads() {
		SimpleMailMessage message = new SimpleMailMessage();
		//邮件设置
		message.setSubject("通知-今晚开会");
		message.setText("今晚7:30开会");
		message.setTo("17512080612@163.com");
		message.setFrom("534096094@qq.com");

		mailSender.send(message);
	}

	@Test
	public void test02() throws  Exception{
		//1、创建一个复杂的消息邮件，带有附件的邮件
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		//邮件设置
		helper.setSubject("通知-今晚开会");
		helper.setText("<b style='color:red'>今天 7:30 开会</b>",true);

		helper.setTo("17512080612@163.com");
		helper.setFrom("534096094@qq.com");

		//上传文件
		helper.addAttachment("1.jpg",new File("C:\\Users\\lfy\\Pictures\\Saved Pictures\\1.jpg"));
		helper.addAttachment("2.jpg",new File("C:\\Users\\lfy\\Pictures\\Saved Pictures\\2.jpg"));

		mailSender.send(mimeMessage);

	}

}
```

![JAVA_SPRINGBOOT27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT27.png?raw=true)

# springBoot整合安全

## 安全框架

```
安全框架最重要的两个框架就是“认证”和“授权”。
认证： 用户名和密码能够登录进系统就是认证

授权：登录进来的用户能查看哪些页面，能做哪些操作，即授权
```



## Spring Security

```java
Spring Security是针对Spring项目的安全框架，也是Spring Boot底层安全模块默认的技术选型。他可以实现强大的web安全控制。对于安全控制，我们仅需引入spring-boot-starter-security模块，进行少量的配置，即可实现强大的安全管理。

几个类：
WebSecurityConfigurerAdapter：自定义Security策略
AuthenticationManagerBuilder：自定义认证策略
@EnableWebSecurity：开启WebSecurity模式


springBoot 使用Spring Security
1) 引入Spring Security
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
相关thymeleaf，使用高版本 
<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
		<thymeleaf-layout-dialect.version>2.3.0</thymeleaf-layout-dialect.version>
		<thymeleaf-extras-springsecurity4.version>3.0.2.RELEASE</thymeleaf-extras-springsecurity4.version>
	</properties>
    
2)编写SpringSecurity的配置类；	@EnableWebSecurity  必须 extends WebSecurityConfigurerAdapter
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置的登陆功能，效果，如果没有登陆，没有权限就会来到登陆页面
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");
        //1、/login来到登陆页
        //2、重定向到/login?error表示登陆失败
        //3、更多详细规定
        //4、默认post形式的 /login代表处理登陆
        //5、一但定制loginPage；那么 loginPage的post请求就是登陆


        //开启自动配置的注销功能。
        http.logout().logoutSuccessUrl("/");//注销成功以后来到首页
        //1、访问 /logout 表示用户注销，清空session
        //2、注销成功会返回 /login?logout 页面；

        //开启记住我功能
        http.rememberMe().rememberMeParameter("remeber");
        //登陆成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登录
        //点击注销会删除cookie

    }

    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3");

    }
}

3）控制请求的访问权限：
	configure(HttpSecurity http) {
  		 	http.authorizeRequests().antMatchers("/").permitAll()
 		 		.antMatchers("/level1/**").hasRole("VIP1")
  		}
4)、定义认证规则：
  		configure(AuthenticationManagerBuilder auth){
  		 	auth.inMemoryAuthentication()
  		 		.withUser("zhangsan").password("123456").roles("VIP1","VIP2")
  		}
5)、开启自动配置的登陆功能：
		configure(HttpSecurity http){
 		 	http.formLogin();
		}
6)、注销：http.logout();
7)、记住我：Remeberme()；
```



## Web&安全

```java
登陆/注销
1）HttpSecurity配置登陆、注销功能
Thymeleaf提供的SpringSecurity标签支持。需要引入thymeleaf-extras-springsecurity4
<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity4</artifactId>
		</dependency>
		
sec:authentication=“name”获得当前用户的用户名
sec:authorize=“hasRole(‘ADMIN’)”当前用户必须拥有ADMIN权限时才会显示标签内容
使用示例：
<h1 align="center">欢迎光临武林秘籍管理系统</h1>
<div sec:authorize="!isAuthenticated()">
	<h2 align="center">游客您好，如果想查看武林秘籍 <a th:href="@{/userlogin}">请登录</a></h2>
</div>
<div sec:authorize="isAuthenticated()">
	<h2><span sec:authentication="name"></span>，您好,您的角色有：
		<span sec:authentication="principal.authorities"></span></h2>
	<form th:action="@{/logout}" method="post">
		<input type="submit" value="注销"/>
	</form>
</div>
<div sec:authorize="hasRole('VIP1')">
	<h3>普通武功秘籍</h3>
	<ul>
		<li><a th:href="@{/level1/1}">罗汉拳</a></li>
		<li><a th:href="@{/level1/2}">武当长拳</a></li>
		<li><a th:href="@{/level1/3}">全真剑法</a></li>
	</ul>

</div>

<div sec:authorize="hasRole('VIP2')">
	<h3>高级武功秘籍</h3>
	<ul>
		<li><a th:href="@{/level2/1}">太极拳</a></li>
		<li><a th:href="@{/level2/2}">七伤拳</a></li>
		<li><a th:href="@{/level2/3}">梯云纵</a></li>
	</ul>

</div>

<div sec:authorize="hasRole('VIP3')">
	<h3>绝世武功秘籍</h3>
	<ul>
		<li><a th:href="@{/level3/1}">葵花宝典</a></li>
		<li><a th:href="@{/level3/2}">龟派气功</a></li>
		<li><a th:href="@{/level3/3}">独孤九剑</a></li>
	</ul>
</div>

2）remember me
表单添加remember-me的checkbox
配置启用remember-me功能
  //开启记住我功能
        http.rememberMe().rememberMeParameter("remeber");
        //登陆成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登录
        //点击注销会删除cookie

3) CSRF（Cross-site request forgery）跨站请求伪造
HttpSecurity启用csrf功能，会为表单添加_csrf的值，提交携带来预防CSRF；

4) 配置我们自己的登录和注销页面
 http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");
一但定制loginPage；那么 loginPage的post请求就是登陆
<form th:action="@{/userlogin}" method="post">
			用户名:<input name="user"/><br>
			密码:<input name="pwd"><br/>
			<input type="checkbox" name="remeber"> 记住我<br/>
			<input type="submit" value="登陆">
		</form>
或者 .loginProcessingUrl() 指定特定的请求。
                
```



# springBoot与分布式

## springBoot 整合dubbo ，zookeeper

![JAVA_SPRINGBOOT28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT28.png?raw=true)

```java
1、将服务提供者注册到注册中心
 * 	    1、引入dubbo和zkclient相关依赖
  		<dependency>
			<groupId>com.alibaba.boot</groupId>
			<artifactId>dubbo-spring-boot-starter</artifactId>
			<version>0.1.0</version>
		</dependency>

		<!--引入zookeeper的客户端工具-->
		<!-- https://mvnrepository.com/artifact/com.github.sgroschupf/zkclient -->
		<dependency>
			<groupId>com.github.sgroschupf</groupId>
			<artifactId>zkclient</artifactId>
			<version>0.1</version>
		</dependency>
 * 	    2、配置dubbo的扫描包和注册中心地址
 dubbo.application.name=provider-ticket
dubbo.registry.address=zookeeper://118.24.44.169:2181
# 扫描服务包，将服务注册到注册中心
dubbo.scan.base-packages=com.atguigu.ticket.service 
 * 	    3、使用@Service发布服务
 package com.atguigu.ticket.service;
public interface TicketService {
    public String getTicket();
}

@Component
@Service //将服务发布出去
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "《厉害了，我的国》";
    }
}

2: 引用注册中心服务
 * 1、引入依赖 (同提供服务引用依赖相同)
 * 2、配置dubbo的注册中心地址
 dubbo.application.name=consumer-user
dubbo.registry.address=zookeeper://118.24.44.169:2181
 * 3、引用服务
 在服务引用方，放置一个需要使用的服务接口(不含实现，包结构名相同，方便我们像调正常接口一样调用远程服务) 
   package com.atguigu.ticket.service;
public interface TicketService {
    public String getTicket();
}  

@Service
public class UserService{
    @Reference // 引用注册中心的服务 
    TicketService ticketService;
    public void hello(){
        String ticket = ticketService.getTicket();
        System.out.println("买到票了："+ticket);
    }
}
```

![JAVA_SPRINGBOOT29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGBOOT29.png?raw=true)

## Spring Boot和Spring Cloud

### springCloud 和Doubbo的区别

```
Doubbo 是分布式服务框架。解决服务之间远程过程调用的问题(就是他们之间的RPC)
springCloud是一个分布式的整体解决方案. 分布式中所有问题，都存在解决方案

SpringCloud分布式开发五大常用组件
服务发现——Netflix Eureka
客服端负载均衡——Netflix Ribbon
断路器——Netflix Hystrix
服务网关——Netflix Zuul
分布式配置——Spring Cloud Config

```

## 热部署

修改代码后不重启应用的情况下，程序可以自动部署

```
如何能实现热部署
1、模板引擎
在Spring Boot中开发情况下禁用模板引擎的cache，页面模板改变ctrl+F9可以重新编译当前页面并生效

2：Spring Loaded
Spring官方提供的热部署程序，实现修改类文件的热部署
下载Spring Loaded（项目地址https://github.com/spring-projects/spring-loaded）
添加运行时参数；
-javaagent:C:/springloaded-1.2.5.RELEASE.jar –noverify


3、Spring Boot Devtools（推荐） + Ctrl F9 （IDEA 中） ，eclipse中直接保存即可
引入依赖
<dependency>  
       <groupId>org.springframework.boot</groupId>  
       <artifactId>spring-boot-devtools</artifactId>   
</dependency> 

```



