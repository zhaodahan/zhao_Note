---
 title: git  消息中间件之ActiveMQ
    date: 2019-11-05 16:17:53
    tags: 
      - note
      - mq
---



消息中间件之ActiveMQ

消息中间件已经成为应用系统内部通信的核心手段。它具有解耦、异步、削峰、签收、事务、流量控制、最终一致性等一系列高性能架构所需功能。

当前使用较多的消息中间件有RabbitMQ、RocketMQ、ActiveMQ、Kafka、ZeroMQ、MetaMQ等，

(重点：

 1：结合Spring/SpringBoot进行实际开发配置

 2：MQ多节点集群的部署

)

# 什么是mq



mq是分布式系统消息通信的一种方式(消息中间件)。 mq是一种具体的规范，ActiveMQ是其具体的实现。

![JAVA_ACTIVEMQ1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ1.png?raw=true)

(消息中间件的原理类似于我们发送微信消息，他也是一个三角形，消息的发送者，消息的接收者，和中间接收和发送消息的腾讯服务器)

![JAVA_ACTIVEMQ13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ13.png?raw=true)

**在什么场景下应该使用消息中间件？**

1：异步处理

用户注册后，需要发注册邮件和注册短信
串行方式：

![JAVA_ACTIVEMQ2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ2.png?raw=true)

并行方式：

![JAVA_ACTIVEMQ3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ3.png?raw=true)

引入消息队列：

![JAVA_ACTIVEMQ4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ4.png?raw=true)

```
假设三个业务节点每个使用50毫秒钟，不考虑网络等其他开销，则串行方式的时间是150毫秒，并行的时间可能是100毫秒。

因为CPU在单位时间内处理的请求数是一定的，假设CPU1秒内吞吐量是100次。则串行方式1秒内CPU可处理的请求量是7次（1000/150）。并行方式处理的请求量是10次（1000/100）

小结：如以上案例描述，传统的方式系统的性能（并发量，吞吐量，响应时间）会有瓶颈

按照以上约定，用户的响应时间相当于是注册信息写入数据库的时间，也就是50毫秒。注册邮件，发送短信写入消息队列后，直接返回，因此写入消息队列的速度 很快，基本可以忽略，因此用户的响应时间可能是50毫秒。因此架构改变后，系统的吞吐量提高到每秒20 QPS。比串行提高了3倍，比并行提高了两倍
```

2：应用解耦

用户下单后，订单系统需要通知库存系统

![JAVA_ACTIVEMQ5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ5.png?raw=true)

```
传统模式的缺点：

假如库存系统无法访问，则订单减库存将失败，从而导致订单失败
订单系统与库存系统耦合

解决：引入应用消息队列
```

![JAVA_ACTIVEMQ6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ6.png?raw=true)

```
订单系统：用户下单后，订单系统完成持久化处理，将消息写入消息队列，返回用户订单下单成功
库存系统：订阅下单的消息，采用拉/推的方式，获取下单信息，库存系统根据下单信息，进行库存操作
假如：在下单时库存系统不能正常使用。也不影响正常下单，因为下单后，订单系统写入消息队列就不再关心其他的后续操作了。实现订单系统与库存系统的应用解耦
```

3： 流量削锋

秒杀或团抢活动中流量削锋

![JAVA_ACTIVEMQ7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ7.png?raw=true)

```
应用场景：秒杀活动，一般会因为流量过大，导致流量暴增，应用挂掉。为解决这个问题，一般需要在应用前端加入消息队列。
可以控制活动的人数
可以缓解短时间内高流量压垮应用

用户的请求，服务器接收后，首先写入消息队列。假如消息队列长度超过最大数量，则直接抛弃用户请求或跳转到错误页面
秒杀业务根据消息队列中的请求信息，再做后续处理
```

4: 日志处理

指将消息队列用在日志处理中，比如Kafka的应用，解决大量日志传输的问题

![JAVA_ACTIVEMQ8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ8.png?raw=true)

5:消息通讯

纯的消息通讯。比如实现点对点消息队列，或者聊天室等

![JAVA_ACTIVEMQ9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ9.png?raw=true)



**为什么要在系统中使用消息中间件？**消息中间件能干嘛？

```
解耦、异步、削峰
```

![JAVA_ACTIVEMQ10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ10.png?raw=true)

![JAVA_ACTIVEMQ11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ11.png?raw=true)

![JAVA_ACTIVEMQ12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ12.png?raw=true)

**使用了消息队列会有什么缺点**

```
系统可用性降低:
本来其他系统只要运行好好的，那你的系统就是正常.
加个消息队列进去，那消息队列挂了，你的系统就不可用了。因此，系统可用性降低

系统复杂性增加:
要多考虑很多方面的问题，比如一致性问题、如何保证消息不被重复消费，如何保证保证消息可靠传输。
```



activemq官网：http://activemq.apache.org/

## JMS规范

![JAVA_ACTIVEMQ18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ18.png?raw=true)

![JAVA_ACTIVEMQ19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ19.png?raw=true)

jms 是指两个应用程序之间进行异步通信的API.

### JMS message

在消息中间件调用的时候怎么解决消息的幂等性(消息怎么防止不被重复消费)，这里就需要使用到消息头

**消息头**

```java
JMSDestination： 消息的目的地，Topic或者是Queue。 
textMessage.setJMSDestination()；(可以指定某条具体的消息需要发送到某个具体的目的地)

JMSDeliveryMode ： 持久和非持久模式 (是否需要持久化，服务器故障后消息是否会消失，保证消息可靠性)
设置：Producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

JMSExpiration：消息的过期时间(默认永不过期)
producer.setTimeToLive(3600000); //有效期1小时 （1000毫秒 * 60秒 * 60分）

JMSPriority:消息的优先级
producer.setPriority(9);//0-4为正常的优先级，默认4，5-9为高优先级 ,类似于普通快递和加急快递.  加急消息必须先于普通消息到达

JMSMessageID(最重要)：一个字符串用来唯一标示一个消息。
textMessage.setJMSMessageID()
解决消息的幂等性就需要用到这个特性，在分布式系统中，由系统分配一个唯一的id,消费端通过对一这个id来判断这个消息是否已经被消费(去库中查我们已经消费了消息id)    
```

**消息体**

```java
封装具体的消息数据

TextMessage(常用) ：Java字符串的消息，string类型

MapMessage(常用)：键--值对的消息s

BytesMessage ： 二进制数组 byte[]

StreamMessage :java流

ObjectMessage ：个可序列化的Java对象

（他们都是通过session创建的，重点的是发送和接收的消息体的类型必须一致，发送的是TextMessage，接收的一定是TextMessage接收）
```

**消息属性**

```java
消息的属性就像可以分配给一条消息的附加消息头一样 （两个消息都有头和体，类似一对双胞胎，这时候我们可以通过他们身上佩戴的首饰去区分他，这里的首饰就是我们的消息属性）

TextMessage message = pubSession.createTextMessage();
message.setText(text);
message.setStringProperty("username",username);    //自定义属性
如上，他们是以属性名和属性值“对”的形式制定的。
```

# mq 怎么使用

![JAVA_ACTIVEMQ14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ14.png?raw=true)



## 异步消息的消费和处理

![JAVA_ACTIVEMQ15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ15.png?raw=true)

### 将消息推送到mq 队列

1: 引入必须jar包

```xml
<!-- https://mvnrepository.com/artifact/org.apache.activemq/activemq-all -->
<!--activemq所需的基础包-->
<dependency>
    <groupId>org.apache.activemq</groupId>
    <artifactId>activemq-all</artifactId>
    <version>5.15.9</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.apache.xbean/xbean-spring -->
<!--和spring整合的基础包-->
<dependency>
    <groupId>org.apache.xbean</groupId>
    <artifactId>xbean-spring</artifactId>
    <version>3.16</version>
</dependency>

```

2：消息生产者编码

```java
public void testMQProducerQueue() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号. 按照给定的url地址，采用默认的用户名和密码
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.156.44:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象： 两个参数。一：事务，二：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）。这里的Queue使用jms包下的
        Queue queue = session.createQueue("test-queue");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        //7、使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello!test-queue");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }
```

3：消费者编码

```java
public void TestMQConsumerQueue() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.156.44:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Queue queue = session.createQueue("test-queue");
        //6、使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
 
            @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                if(null!=message && message instanceof TextMessage){
                    //生产者传输过来的是TextMessage， 消费者接收的也必须是TextMessage
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息，防止关闭的太快而，没有接收到消息,保证控制台不关，一直监听
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
```

4：消费的3种情况

![JAVA_ACTIVEMQ16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ16.png?raw=true)

### 将消息推送到Topic

一对多，类似微信公众号订阅，主体发布了消息每个订阅的人都会受到相同的消息。 生产者和消费者之间存在`时间相关性，消费者只能接收自他订阅之后发布的消息`  需要先订阅后发布 (如果主题发布的时候没有任何订阅者，那么该消息就是费消息，没有任何消费者可以接收到该条消息)。topic不保存消息。需要先启动消费者再启动生产者。



1：主题发布的生产者编码

```java
public void TestTopicProducer() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.156.44:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(topic);
        //7、使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello!test-topic");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }
```

2：消费者接收订阅消息编码

```java
public void TestTopicConsumer() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.156.44:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
        //6、使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(topic);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
 
            @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
 
```

### topic 和queue的对比

![JAVA_ACTIVEMQ17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ17.png?raw=true)



## spring/springBoot整合简化编码

### spring整合

1：Maven修改，添加spring支持jms依赖

```xml
<!--activemq必须的核心jiar包-->
<!--activemq需要的jav包-->
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-all</artifactId>
            <version>5.15.9</version>
        </dependency>
        <dependency>
            <groupId>org.apache.xbean</groupId>
            <artifactId>xbean-spring</artifactId>
            <version>3.16</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.5</version>
        </dependency>

    <!-- spring整合mq所需的jar包 -->

     <!-- spring-jms 依赖,spring整合mq必要的适配包 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jms</artifactId>
            <version>4.3.10.RELEASE</version>
        </dependency>
    
        <!-- activemq所需的池技术依赖-->
         <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-pool</artifactId>
            <version>5.15.9</version>
        </dependency>
 
   <!--spring 依赖jar包-->
 <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.3.23.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.3.23.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>4.3.23.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>4.3.23.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.6.1</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.6.8</version>
        </dependency>
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>2.1_2</version>
        </dependency>
```

2：spring整合mq配置applicationContext.xml

```xml
<!--
以前我们需要在代码中手动
 //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.156.44:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
现在 ConnectionFactory，Topic等我们都不用显示编写，而是通过spring自动注入 
-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:jms="http://www.springframework.org/schema/jms"
    xmlns:c="http://www.springframework.org/schema/c"
    xsi:schemaLocation="http://www.springframework.org/schema/jms http://www.springframework.org/schema/jms/spring-jms-4.3.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">
   
  <context:component-scan base-package="com.atguigu.activemq"></context:component-scan>

       <!--配置生产者，PooledConnectionFactory对应activemq-pool jar包-->
        <bean id="jmsFactory" class="org.apache.activemq.pool.PooledConnectionFactory" destroy-method="stop">
                <property name="connectionFactory">
                        <!--真正产生connection的ConnectionFactory-->
                        <bean class="org.apache.activemq.ActiveMQConnectionFactory">
                                <property name="brokerURL" value="tcp://10.112.70.211:61616"/>
                        </bean>
                </property>
                <property name="maxConnections" value="100"></property>
        </bean>
        
       <!--spring构造注入spring-active-queue目的地-->
        <bean id="destinationQueue" class="org.apache.activemq.command.ActiveMQQueue">
                <constructor-arg index="0" value="spring-active-queue"/>
        </bean>

        <!-- Spring的JMS模版工具 -->
        <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
                <property name="connectionFactory" ref="jmsFactory" />
                <property name="defaultDestination" ref="destinationQueue"/>
                <property name="messageConverter">
                        <bean class="org.springframework.jms.support.converter.SimpleMessageConverter" />
                </property>
        </bean>


</beans>
```

3：队列

```java
/**
 * 通用的ActiveMQ queue消息生产者
 * @author ZENG.XIAO.YAN 
 * @version  v1. 
 */
@Component 
public class CommonMqQueueProducer {
    @Autowired
    private JmsTemplate jmsTemplate;  
  
    /**
     * 发送点对点的文本类型的Mq消息
     * @param queue 队列的名字
     * @param message   文本消息（一般直接传输json字符串，所以可以认为文本消息是最通用的）
     */
  
    public void send(String queue, String message) {
        jmsTemplate.send(queue, new MessageCreator() { 
            @Override  
            public Message createMessage(Session session) throws JMSException { 
                return session.createTextMessage(message);
            }
        });
        
        //Lambda
        jmsTemplate.send(queue, session->{
             return session.createTextMessage(message);
        }
        });
    }
  
}

// 消费者，消费者类需要实现MessageListener接口，然后重写onMessage方法，且消费者需要交给Spring容器来实例化
package com.zxy.mq.consumer;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener; 
import javax.jms.TextMessage; 
import org.springframework.stereotype.Component; 
@Component 
public class TestAConsumer implements MessageListener {  
    @Override 
    public void onMessage(Message message) {  
        // myQueueA的消费者  
        try {  
             String text = ((TextMessage) message).getText();  
             System.out.println(this.getClass().getSimpleName() + "接受到消息---->" + text);  
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
//或者；
//String receive =(String) consumer.jmsTemplate.receiveAndConvert();
//  System.out.println("消费者："+receive);

/**
 * MQ消息测试类
 */
public class MqTestDemo {
    private static ApplicationContext applicationContext;
    // 静态代码块加载Spring容器
    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println("spring 容器已启动。。。");
    }   
  
    public static void main(String[] args) {
        CommonMqQueueProducer mqQueueProducer = applicationContext.getBean(CommonMqQueueProducer.class);
        for (int i =  ; i <   ; i++) {
            // 奇数给myQueueA发，偶数给myQueueB发  
            if (i %   ==  ) {
                mqQueueProducer.send("myQueueA", "Mq消息A" + i);
            } else { 
                mqQueueProducer.send("myQueueB", "Mq消息B" + i);
            }
        }
    }
  
}
```

4：主题

```xml
spring配置文件中注入Topic
   <bean id="destinationTopic" class="org.apache.activemq.command.ActiveMQTopic">
                <constructor-arg index="0" value="spring-active-topic"/>
        </bean>

        <!-- Spring的JMS模版工具 -->
        <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
                <property name="connectionFactory" ref="jmsFactory" />
                <!-- 这里目的地是topic，则发送到topic,注入的是队列则到队列-->
                <property name="defaultDestination" ref="destinationTopic"/>
                <property name="messageConverter">
                        <bean class="org.springframework.jms.support.converter.SimpleMessageConverter" />
                </property>
        </bean>

生产者消费者代码不需要修改
```



5：spring中实现消费者不启动，直接通过配置监听完成

```xml
1：修改一下配置文件，注入监听器

        <bean id="destinationQueue" class="org.apache.activemq.command.ActiveMQQueue">
                <constructor-arg index="0" value="spring-active-queue"/>
        </bean>
        <bean id="destinationTopic" class="org.apache.activemq.command.ActiveMQTopic">
                <constructor-arg index="0" value="spring-active-topic"/>
        </bean>

        <!-- Spring的JMS模版工具 -->
        <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
                <property name="connectionFactory" ref="jmsFactory" />
                <property name="defaultDestination" ref="destinationTopic"/>
                <property name="messageConverter">
                        <bean class="org.springframework.jms.support.converter.SimpleMessageConverter" />
                </property>
        </bean>

         <!--配置监听，等价于我们以前在代码中引入监听器 -->
        <bean id="queueListenerContainer"
              class="org.springframework.jms.listener.DefaultMessageListenerContainer">
                <property name="connectionFactory" ref="jmsFactory" />
                <property name="destination" ref="destinationTopic" />
                <property name="messageListener" ref="myMessageListener" />
        </bean>
       
<!--他也可以使用 component-scan + @Service 注解来代替-->
      <bean id="myMessageListener" class="..."></bean>
2：需要编写监听类
@Service
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if (null != message && message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("spring消费者接收到消息："+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
只需要启动一个生产者，不用启动消费者。会自动监听记录
```



### springBoot整合

引入maven配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.rocket</groupId>
    <artifactId>springboot_activemq</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot_activemq</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.targer>1.8</maven.compiler.targer>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
         
        <!--spring整合activemq-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
            <version>2.1.5.RELEASE</version>
        </dependency>
         <!--消息队列连接池-->
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-pool</artifactId>
            <version>5.15.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```



#### 队列

1：项目配置文件applocation.yml

```yaml
server:
  port: 7777
spring:
  activemq:
    broker-url: tcp://10.112.70.211:61616 #自己的mq服务器地址
    user: admin
    password: admin
  jms:
    pub-sub-domain: false   #false=queue  ，true=Topic 。不写默认false 

#自己定义的队列名称
myqueue: boot-activemq-queue
```

2：  配置bean, 类似于在spring中配置的applocationContext.xml

```java
@Component
@EnableJms #开启jms注解
public class ConfigBean {
  @Value("${myqueue}")
    private String myQueue;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }
}
```

3： 在应用中生产者生产消息

```java
@Component
public class Queue_Produce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"******:"+ UUID.randomUUID().toString().substring(0,6));
    }
}
```

4： 对列消费者

依赖jar包和服务配置步骤与消费者类似

```java
@Service
public class QueueConsumer {

    //@JmsListener 监听我们要消费的队列，spring还需要我们显示编写监听器类，springBoot中此注解已经帮我们搞定
    @JmsListener(destination = "${myqueue}")
    public void receive(TextMessage textMessage) throws JMSException {
        System.out.println("消费者接受消息："+textMessage.getText());
    }
}
```



#### 主题

jar包依赖于队列一直

1：服务配置文件 `pub-sub-domain: true`

```yaml
server:
  port: 7777
spring:
  activemq:
    broker-url: tcp://10.112.70.211:61616
    user: admin
    password: admin
  jms:
    pub-sub-domain: true # true表示topic

mytopic: boot-activemq-topic
```

2：配置bean,与队列不同

```java
@Component
@EnableJms
public class ConfigBean {
    @Value("${mytopic}")
    private String myTopic;

    @Bean
    public Topic queue(){
        return new ActiveMQTopic(myTopic);
    }
}
```

3: 消息生产者和消息消费者代码与队列类似，只是目的地取的是Topic



## 消息延时，和定时投递

ActiveMQ提供了一种broker端消息定时调度机制。

我们只需要把几个描述消息定时调度方式的参数作为message消息属性添加到消息，broker端的调度器就会按照我们想要的行为去处理消息。

**使用mqschedule策略的前提**

1：开启ActiveMQ的延迟发送功能要先修改ActiveMQ安装文件下面的config文件夹下的activemq.xml文件。 在broker节点上加上`schedulerSupport="true"`这个属性，然后重启才会生效

![JAVA_ACTIVEMQ36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ36.png?raw=true)

一共有四个属性:

```java
Property name       type description 

AMQ_SCHEDULED_DELAY long 延迟投递的时间 

AMQ_SCHEDULED_PERIOD long 重复投递的时间间隔 

AMQ_SCHEDULED_REPEAT  int 重复投递次数   // 注意这里是int类型

AMQ_SCHEDULED_CRON String Cron表达式
```



使用示例：延迟60秒

```java
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("test msg");
        long time = 60 * 1000;
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
        producer.send(message);
```

延迟30秒，投递10次，间隔10秒:

```java
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("test msg");
        long delay = 30 * 1000;
        long period = 10 * 1000;
        int repeat = 9;
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
        message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
        producer.send(message);
```

使用 CRON 表达式的例子：

```java
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("test msg");
        message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0 * * * *");
        producer.send(message);

    //  CRON表达式的优先级高于另外三个参数，如果在设置了CRON的同时，也有repeat和period参数，则会在每次CRON执行的时候，重复投递repeat次，每次间隔为period。就是说设置是叠加的效果。例如每小时都会发生消息被投递10次，延迟1秒开始，每次间隔1秒:
      MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("test msg");
        message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0 * * * *");
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1000);
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
        message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 9);
        producer.send(message);

注意：此处Cron是Unix系统中任务调度器,它使用一个字符串来表示一个任务何时需要被执行。而不是quartz里边的那个Cron表达式
```



springBoot 整合是在上面springboot整合mq队列的例子上进行修改，他们需要使用到定时调度功能，原理是使用springBoot提供的`@Scheduled` 定时调度功能，所以都需要在主启动类上加上`@EnableScheduling` 注解

### springBoot 整合activeMQ队列生产者定时投递

```java
这里定时投递，需要使用到定时任务
1： 主启动类上加上@EnableScheduling 注解

2：服务上加上@Scheduled(cron = "0 0 1 * * ?") 注解
public void produceMsgScheduled(){
    jmsMessagingTemplate.convertAndSend(queue,"******Scheduled:"+ UUID.randomUUID().toString().substring(0,6));
    System.out.println("发送消息。。。");
}
```



### springBoot 整合activeMQ队列生产者延时时投递

```java
//在第一次执行a之前要延迟的毫秒数
@Scheduled(initialDelay = 3000)
public void produceMsgScheduled(){
    jmsMessagingTemplate.convertAndSend(queue,"******Scheduled:"+ UUID.randomUUID().toString().substring(0,6));
    System.out.println("发送消息。。。");
}
```



### springBoot 整合activeMQ队列生产者间隔投递

```java
//每隔3秒定投
@Scheduled(fixedDelay = 3000)
public void produceMsgScheduled(){
    jmsMessagingTemplate.convertAndSend(queue,"******Scheduled:"+ UUID.randomUUID().toString().substring(0,6));
    System.out.println("发送消息。。。");
}
```





## 消息的可靠性(非持久化和持久化)

消息的可靠性从三个角度来考虑：持久， 事务， 签收

### 持久

在消息发送方发送消息时设置`消息头JMSDeliveryMode`发送模式。在消息服务器宕机时消息的处理方式。

![JAVA_ACTIVEMQ20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ20.png?raw=true)

queue默认是持久化的；topic，因为要发送给多个人，默认是非持久化的

**持久化Topic**

持久化的Topic比较特殊。 

生产者代码做相应的改造

```java
public void TestTopicProducer() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.156.44:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        //connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(topic);
        Producer.setDeliveryMode(DeliveryMode.PERSISTENT);
       
        //开启连接的代码需要改变位置，因为这里需要我们启动的是一个持久化的主题，所以启动一定要在设置模式后
        connection.start();
        //7、使用会话对象创建一个消息对象
        //8、发送消息
        //9、关闭资源

    }
```

消费者做相应的改造

```java
public void TestTopicConsumer() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.156.44:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、设置客户端id，用于注册，当他注册到服务器后，服务器就会往他推送消息，如果他宕机了没有收到，这时候由于生产者设置了持久化，他订阅了没有收到，消息就会持久，当他恢复了正常，又可以继续推送
        connection.setClientID("client-1"); // 表明有一个叫client1的用户订阅，

        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
    
        //6、使用会话对象创建生产者对象。客户端持久化订阅
        TopicSubscriber consumer = session.createDurableSubscriber(topic, "client1-sub");

         connection.start();
    
        //7 用来接收消息
         Message message = consumer.receive();
                while(message != null){
                    TextMessage textMsg = (TextMessage)message;
                    System.err.println("消费消息："+textMsg.getText());
                    //接收下一个消息
                    message = consumer.receive(1000L);
                }
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源

    }

一定先运行一次持久化的消费者，等于向mq注册，类似于订阅公众号。然后生产者发送消息，这时候无论消费者是否在线都会接收到，如果消费者现在没有在线，下次在线的时候回将之前的一并接收(这里类似微信公众号文章的推送机制，一定要先注册(订阅)，这也是高可用的保证)
```



### 事务

事务偏生产者： 在处理批量生产消息的时候。要么一起成功，要么一起失败(正常情况手动提交，失败后回滚)

添加事务主要注意两点：

1.修改Session配置，启用事务

```java
/**
 * 3.通过Connection对象创建Session会话（上下文环境对象），用于接收消息。
 *   参数1：是否启用事务 ，true开启事务。需要手动提交，类比数据库操作。 如果这是true，第二个参数就不重要了
 *   参数2：签收模式，一般设置为自动签收
 */
Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
```

![JAVA_ACTIVEMQ21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ21.png?raw=true)

2.代码最后提交事务（必须要提交事务，不然在MQ上无法得到发送的消息）

```java
//提交事务
session.commit();

注意：
如果消息消费者设置了事务，但没有session.commit(); 会导致消息被重复消费，所以不要在消费者中使用事务。
```

 session设置启用事务，但不加session.commit()，执行代码之后，mq服务器收不到消息



### 签收

签收偏消费者： 类比我们收快递，收到后签收，给送快递的一个应答

```java
非事务：三种签收方式
Session.AUTO_ACKNOWLEDGE：自动签收

Session.CLIENT_ACKNOWLEDGE：手动签收，需要对收到的每条消息做反馈,显示的调用。
//消费者手工去签收消息，另起一个线程（TCP）去通知MQ服务确认消息签收
  msg.acknowledge();

Session.DUPS_OK_ACKNOWLEDGE(不常用) ：可重复签收 (可以被多个线程查看)

事务：签收不重要，默认就自动签收了，但是要注意session.commit() ，如果没有commit就会被重复消费
(事务优先级比签收大)
```





## 配置集群容错的mq集群

### 配置集群基础broker

(生产环境中不能使用单间mq, mq要求高可用，一旦宕机了。生产者消费者都取不到消息了)

1: 嵌入（java） 式broker

```xml
1：配置文件引入
<!--activemq所需的基础包-->
<dependency>
    <groupId>org.apache.activemq</groupId>
    <artifactId>activemq-all</artifactId>
    <version>5.15.9</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.apache.xbean/xbean-spring -->
<!--和spring整合的基础包-->
<dependency>
    <groupId>org.apache.xbean</groupId>
    <artifactId>xbean-spring</artifactId>
    <version>3.16</version>
</dependency>

<!--以免报错 java.lang.classNotFoundException com.fasterxml.jackson.databind.ObjectMapper-->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.5</version>
</dependency>

```

将mini版的mq嵌入到java中，他就可以作为一个mq的微服务器（不需要再在Linux服务器上启动mq服务）

```java
import org.apache.activemq.broker.BrokerService;
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        //ActiveMQ也支持在vm中通信基于嵌入式的broker
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}


```

(实操在高级特性处查看)

# mq高级特性

## ActiveMQ 传输协议

1：默认的61616端口如何改

```
修改TCP 61616端口：
修改conf/activemq.xml配置文件中的transportConnectors，找到name="openwire" uri="tcp://....61616", 修改这里的端口即可

修改管理页面的8161端口：
修改conf/jetty.xml,修改内容容器jetty的默认启动端口，找到默认的8161后修改
```

2：mq链接协议如何配置，只能使用TCP吗？

![JAVA_ACTIVEMQ22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ22.png?raw=true)

```xml
TCP（默认，常用的）:TCP的Client监听端口61616
网络传输数据前，必须要序列化数据成字节流。
tcp://hostname:port?key=value (后面的参数可选)

NIO(常用)：底层和TCP类似，且是基于TCP协议。但是NIO更侧重底层访问，对同一资源可以有更多的client调用和服务端有更多的负载。NIO 比TCP提供更好的性能

配置实例：
<broker>
  ...
  <transportConnectors>
    <transportConnector name="nio" uri="nio://0.0.0.0:61616"/>  
  </<transportConnectors>
  ...
</broker>    
```

![JAVA_ACTIVEMQ23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ23.png?raw=true)

3.换了NIO协议后，对应的编码修改

![JAVA_ACTIVEMQ24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ24.png?raw=true)

```java
1：修改activemq.xml配置文件，这里注意修改端口号。
  <transportConnectors>
    <transportConnector name="nio" uri="nio://0.0.0.0:61618?trace=true"/>  
  </<transportConnectors>
  
 2:NIO 增强案例
 url格式以“nio”开头，表示这个端口使用以tcp协议为基础的nio网络IO模型，但这样设置，只能使用这个端口支持openwrite协议，我们怎么让这个端口支持nio网络IO模型,又让他支持多个协议
 
 解决方法： 使用auto关键字结合“+”符号来为端口设置多个特性
 
```



## ActiveMQ 消息存储和持久化

http://activemq.apache.org/persistence

什么是持久化？就是将我们的数据以文件的形式存储到数据库或者硬盘。 非持久化数据会被保存在内存中

持久化的目的是让我们mq宕机，为了高可用，消息存储一份就不会丢失(通常两者不会在同一台物理机上)。

![JAVA_ACTIVEMQ25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ25.png?raw=true)

### ActiveMQ 的持久化方式

默认的是KahaDB，但是工作中常用的是JDBC

#### KahaDB

```xml
类似redis持久化AOF的原理，日志文件，记录所有操作记录的日志记录文件。恢复数据就重复一遍记录的操作。
activeMQ 5.4后默认的持久化插件，是一个带有持久化机制的文件数据库，该数据库对于使用它的消息代理是本地的。

将KahaDB用作代理的持久性适配器，ActiveMQ的conf/activemq.xml做配置：
 <broker brokerName="broker">
    <persistenceAdapter>
      <kahaDB directory="activemq-data" journalMaxFileLength="32mb"/>
    </persistenceAdapter>
 </broker>

```

![JAVA_ACTIVEMQ26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ26.png?raw=true)

![JAVA_ACTIVEMQ27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ27.png?raw=true)

#### JDBC

既然要存储到数据库中就需要建立对应的数据库和表。

且要持久化数据库，那么mq设置消息持久化模式要设置为持久化模式，如果设置为非持久模式就不会存储到表中

mq要操作我们数据库，就需要添加我们的数据库驱动包到mq的lib文件夹下，如果使用了数据库连接池，还需要加上对应的数据库连接池jar包

```xml
在不进行日志记录的情况下启用JMS消息的JDBC持久性，我们需要从AMQ 4.x更改消息代理的默认持久性配置。
<persistenceAdapter> 
  <jdbcPersistenceAdapter dataSource="#my-ds" createTablesOnStartup="false" /> 
</persistenceAdapter>

这里的“#my-ds” #表示引用。
dataSource指定持久化数据库的bean，createTablesOnStartup是否在启动的时候创建数据表，默认值是true(没写这个也默认配置是true)，这样每次启动都会去创建数据表了，一般是第一次启动的时候设置为true，之后改成false。

MySQL配置JDBC持久化的bean配置
<beans>
    <broker brokerName="test-broker" persistent="true" xmlns="http://activemq.apache.org/schema/core">
        <persistenceAdapter>
            <jdbcPersistenceAdapter dataSource="#mysql-ds" createTablesOnStartup="false"/>
        </persistenceAdapter>
    </broker>
    <!--这里注意数据库bean的配置要放在broker标签外,import标签之上才有效-->
    <bean id="mysql-ds" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost/activemq?relaxAutoCommit=true"/>
        <property name="username" value="activemq"/>
        <property name="password" value="activemq"/>
        <property name="maxActive" value="200"/>
        <property name="poolPreparedStatements" value="true"/>
    </bean>
</beans>

```

`createTablesOnStartup` mq启动的时候会自动化建立的三个表(前提是数据库能够连通)

```sql
activemq_msgs用于存储消息，Queue和Topic都存储在这个表中： 
(队列消息成功消费后就会删除数据库表中的记录，Topic持久化的订阅和消息消费成功不会删除，但是消费者离线后才会删除)
ID：自增的数据库主键
CONTAINER：消息的Destination
MSGID_PROD：消息发送者客户端的主键(标识消息发送者)
MSG_SEQ：是发送消息的顺序，MSGID_PROD+MSG_SEQ可以组成JMS的MessageID
EXPIRATION：消息的过期时间，存储的是从1970-01-01到现在的毫秒数
MSG：消息本体的Java序列化对象的二进制数据(流)
PRIORITY：优先级，从0-9，数值越大优先级越高

activemq_acks用于存储订阅关系。如果是持久化Topic，订阅者和服务器的订阅关系在这个表保存：
主要的数据库字段如下：
CONTAINER：消息的Destination，订阅的Topic
SUB_DEST：如果是使用Static集群，这个字段会有集群其他系统的信息
CLIENT_ID：每个订阅者都必须有一个唯一的客户端ID用以区分
SUB_NAME：订阅的Topic下的子主题名称
SELECTOR：选择器，可以选择只消费满足条件的消息。条件可以用自定义属性实现，可支持多属性AND和OR操作
LAST_ACKED_ID：记录消费过的消息的ID。

表activemq_lock只有在集群环境中才有用，只有一个Broker可以获得消息，称为Master Broker，
其他的只能作为备份等待Master Broker不可用，才可能成为下一个Master Broker。
这个表用于记录哪个Broker是当前的Master Broker。

劲量自动生成；应急sql
/*
Navicat MySQL Data Transfe
Date: 2019-07-25 13:44:04
*/
 
SET FOREIGN_KEY_CHECKS=0;
 
-- ----------------------------
-- Table structure for ACTIVEMQ_ACKS
-- ----------------------------
DROP TABLE IF EXISTS `ACTIVEMQ_ACKS`;
CREATE TABLE `ACTIVEMQ_ACKS` (
  `CONTAINER` varchar(250) NOT NULL,
  `SUB_DEST` varchar(250) DEFAULT NULL,
  `CLIENT_ID` varchar(250) NOT NULL,
  `SUB_NAME` varchar(250) NOT NULL,
  `SELECTOR` varchar(250) DEFAULT NULL,
  `LAST_ACKED_ID` bigint(20) DEFAULT NULL,
  `PRIORITY` bigint(20) NOT NULL DEFAULT '5',
  `XID` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`CONTAINER`,`CLIENT_ID`,`SUB_NAME`,`PRIORITY`),
  KEY `ACTIVEMQ_ACKS_XIDX` (`XID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
-- ----------------------------
-- Table structure for ACTIVEMQ_LOCK
-- ----------------------------
DROP TABLE IF EXISTS `ACTIVEMQ_LOCK`;
CREATE TABLE `ACTIVEMQ_LOCK` (
  `ID` bigint(20) NOT NULL,
  `TIME` bigint(20) DEFAULT NULL,
  `BROKER_NAME` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
-- ----------------------------
-- Table structure for ACTIVEMQ_MSGS
-- ----------------------------
DROP TABLE IF EXISTS `ACTIVEMQ_MSGS`;
CREATE TABLE `ACTIVEMQ_MSGS` (
  `ID` bigint(20) NOT NULL,
  `CONTAINER` varchar(250) DEFAULT NULL,
  `MSGID_PROD` varchar(250) DEFAULT NULL,
  `MSGID_SEQ` bigint(20) DEFAULT NULL,
  `EXPIRATION` bigint(20) DEFAULT NULL,
  `MSG` longblob,
  `PRIORITY` bigint(20) DEFAULT NULL,
  `XID` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ACTIVEMQ_MSGS_MIDX` (`MSGID_PROD`,`MSGID_SEQ`),
  KEY `ACTIVEMQ_MSGS_CIDX` (`CONTAINER`),
  KEY `ACTIVEMQ_MSGS_EIDX` (`EXPIRATION`),
  KEY `ACTIVEMQ_MSGS_PIDX` (`PRIORITY`),
  KEY `ACTIVEMQ_MSGS_XIDX` (`XID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
```

实际操作中的坑

![JAVA_ACTIVEMQ28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ28.png?raw=true)

#### JDBC持久增强Jdbc with journal

Jdbc with journal：就是高速缓存日志，在ActiveMQ 和mysql之间挡一层journal高速缓存(JDBC 建立连接和读写会消耗一定的性能， 生产者生产的消息先存放在journal中，如果我们的消费者速度能够赶上生产的速度，在journal还没同步到数据库的时候，消费者就已经消费了大半，如果消费者速度赶不上，则journal批处理同步)

```xml
1：Jdbc with journal的性能优于jdbc
2：Jdbc用于master/slave模式的数据库分享
3：Jdbc with journal不能用于master/slave模式
4：一般情况下，推荐使用jdbc with journal

activemq.xml配置文件配置：原来使用的kahaDB消息持久化机制注释掉
journalPersistenceAdapterFactory: journal持久化适配器
        <persistenceFactory>
            <journalPersistenceAdapterFactory
                    journalLogFiles="4"
                    journalLogFileSize="32768"
                    useJournal="true"
                    useQuickJournal="true"
                    dataSource="#mysql-ds"
                    dataDirectory="activemq-data"/>
        </persistenceFactory>

```



## ActiveMQ 多节点集群ZooKeeper+LevelDB

引入消息中间件后，如何保证其高可用性？

```
集群。
避免单点故障。主从复制。master——slaver的互相选举策略。

```

### 如何搭建mq集群

方案：ZooKeeper+ Replicated LevelDB主从复制集群

LevelDB：LevelDB也是日志记录数据库，不过他使用了自定义索引代替了Btree索引。(性能更强)

![JAVA_ACTIVEMQ29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ29.png?raw=true)

```
ZooKeeper协调集群中的哪个节点成为主节点。(集群的监控者和选举负责人是zookeeper)
主节点接受客户端连接。其他节点进入从模式并连接主节点并同步其持久状态。从节点不接受客户端连接。(对外相当于一个整体，可以提供服务的就是master)所有持久性操作都复制到连接的从站。
如果主服务器去世，则具有最新更新的从服务器将升级为主服务器。然后可以将发生故障的节点重新联机，并将进入从属模式。

当选出一个新的主节点时，至少需要有一定数量的联机节点才能找到更新最新的节点。更新最新的节点将成为新的主节点。建议至少使用3个副本节点运行，以便可以在不造成服务中断的情况下关闭一个副本节点。 (只有一个节点的时候无法选取master)

部署技巧
1:客户端应使用故障转移传输连接到复制群集中的代理节点。例如，使用类似以下内容的URL：
failover:(tcp://broker1:61616,tcp://broker2:61616,tcp://broker3:61616)

2:需要提前配置zookeeper集群，至少运行3个ZooKeeper服务器节点，以使ZooKeeper服务具有高可用性

```

集群配置

<https://blog.csdn.net/zyjavaWeb/article/details/79209572>

![JAVA_ACTIVEMQ30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ30.png?raw=true)

![JAVA_ACTIVEMQ32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ32.png?raw=true)



```xml
1:修改jetty.xml 改管理控制台的8161端口
2：修改conf/activemq.xm 修改TCP mq提供服务的消息端口 如：61616端口
(1,2 中的端口，在集群中每台机器都有唯一的端口)
3：hostName名字映射，映射主机ip，hostName 和brokeName
4：修改持久化LeverDB节点持久化配置: ActiveMQ配置为将LevelDB用于其持久性适配器：
<broker brokerName="broker" ... >
  ...
  <persistenceAdapter>
    <replicatedLevelDB
      directory="activemq-data"
      replicas="3"   
      bind="tcp://0.0.0.0:0"
      zkAddress="zoo1.example.org:2181,zoo2.example.org:2181,zoo3.example.org:2181"
      zkPassword="password"
      zkPath="/activemq/leveldb-stores"
      hostname="broker1.example.org"
      />
  </persistenceAdapter>
  ...
</broker>

replicas:集群中将存在的节点数
zkAddress:ZooKeeper集群的服务器列表
zkPassword：连接到ZooKeeper服务器时使用的密码。
zkPath：ZooKeeper目录的路径，在该目录中将交换主/从选举信息。
bind：当该节点成为主节点时，它将绑定配置的地址和端口以服务复制协议。支持使用动态端口：只需配置tcp://0.0.0.0:0
hostname：当该节点成为主节点时，用于播发复制服务的主机名(集群中的mq设置为一致)
directory：LevelDB用来保存其数据文件的目录
```

3：hostName名字映射。他要求3个节点BrokeName一致

![JAVA_ACTIVEMQ33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ33.png?raw=true)

![JAVA_ACTIVEMQ31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ31.png?raw=true)

4：节点持久化配置

![JAVA_ACTIVEMQ34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ34.png?raw=true)

**集群查看master**

![JAVA_ACTIVEMQ35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ35.png?raw=true)



## 高级特性和面试题

### 异步投递

mq的三个特性中就有：异步，削峰，解耦

mq默认的就是异步的发送消息。 但是在某些特殊情况下，他会同步的发送消息。

| **没有使用事务**并且 **生产者以PERSISTENT传送模式发送消息** 则会以同步的模式发送消息

同步发送明显会阻塞发送端，性能降低。 这时候我们可以强制性开启异步,但是这样不能确保消息发送成功，可能导致消息部分丢失

```java
1:在url中设置属性
tcp://locahost:61616?jms.useAsyncSend=true
2：代码处设置
((ActiveMQConnectionFactory)connectionFactory).setUseAsyncSend(true);
or
((ActiveMQConnection)connection).setUseAsyncSend(true);
```

在强制性设置异步发送后，我们又没使用事务且发送持久化消息，我们应该如何确保消息发送成功

```java
解决方案：正确的异步方法是需要接收回调
((ActiveMQConnection)connection).setUseAsyncSend(true);
//.......
message = session.createTextMessage(”message--’+i); 
message.setJMSMessageTD(UUID.rando.WiUID.toString+---orderAtguigu); 
String msgID = message.getJMSMessageID();
activeMQMessageProducer.send(message, new AsyncCallback()
{

//消息发送成功
public void onsuccess()
{
 System.out.println(msglD+iias been ok send’);
}

 //消息发送失败
public void onException(JMSException exception)
{ 
   System.out.println(msglD+”fail to send to mq”)
)

}
```



### ActiveMQ的消息的消费重试机制

https://blog.csdn.net/qq_36850813/article/details/102703317

我们在使用activemq时难免有一些消费失败的消息，为了保证消息的高可用。mq就提供了一个消息消费重试机制。

#### 消费重试机制

在消息的消费过程中，如果消息未被签收或者签收失败，是会导致消息重复消费的，但如果消息一直签收失败，那是不是就会被无限次的消费呢？答案是否定的。

一条消息签收不成功，消息服务器就会认为该消费者没有消费过这条消息，就会再次将这条消息传送给该消费者供它消费。至于会传送几次取决于我们定义的消费重试机制。很显然消费重试机制是针对消费者端的。

``哪些情况下会发生消息的重复消费呢？其实就是客户端消息签收失败的情况下，这包括但不限于以下情况：`
 `1.消费者端开启事务，但最终事务回滚而未提交，或者在提交之前关闭了连接而提交失败`
 `2.需要手动签收(CLENT_ACKNOWLEDGE)的消息，消费者端在签收之后又调用了 session.recover();`
 在默认情况下，当消息签收失败时ActiveMQ消息服务器会继续每隔1秒钟向消费者端发送一次这个签收失败的消息，默认会尝试6次(加上正常的1次共7次)，如果这7次消费者端全部签收失败，则会给ActiveMQ服务器发送一个“poison ack”，表示这个消息不正常(“有毒”)，这时消息服务器不会继续传送这个消息给这个消费者，而是将这个消息放入死信队列(DLQ，即Dead Letter Queue)。

http://activemq.apache.org/redelivery-policy  在官网的配置可以设置重试机制的属性

![JAVA_ACTIVEMQ37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ37.png?raw=true)

也可以在代码中设置重试次数，重试间隔等来自定义重试机制

```java
ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
//自定义消费重试机制
RedeliveryPolicy policy = new RedeliveryPolicy();
policy.setMaximumRedeliveries(3);
factory.setRedeliveryPolicy(policy);
Connection connection = factory.createConnection();
connection.start();
```

spring 配置重试机制

配置文件中配置 （这里配置的属性就是官网中提示的属性）

```xml
	
      <!-- 定义ReDelivery(重发机制)机制 ，重发时间间隔是100毫秒，最大重发次数是3次 http://www.kuqin.com/shuoit/20140419/339344.html -->
	<bean id="activeMQRedeliveryPolicy" class="org.apache.activemq.RedeliveryPolicy">
		<!--是否在每次尝试重新发送失败后,增长这个等待时间 -->
		<property name="useExponentialBackOff" value="true"></property>
		<!--重发次数,默认为6次   这里设置为1次 -->
		<property name="maximumRedeliveries" value="1"></property>
		<!--重发时间间隔,默认为1秒 -->
		<property name="initialRedeliveryDelay" value="1000"></property>
		<!--第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value -->
		<property name="backOffMultiplier" value="2"></property>
		<!--最大传送延迟，只在useExponentialBackOff为true时有效（V5.5），假设首次重连间隔为10ms，倍数为2，那么第 
			二次重连时间间隔为 20ms，第三次重连时间间隔为40ms，当重连时间间隔大的最大重连时间间隔时，以后每次重连时间间隔都为最大重连时间间隔。 -->
		<property name="maximumRedeliveryDelay" value="1000"></property>
	</bean>

        <!--创建连接工厂 -->
	<bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
		<property name="brokerURL" value="tcp://localhost:61616"></property>
		<property name="redeliveryPolicy" ref="activeMQRedeliveryPolicy" />  <!-- 引用重发机制 -->
	</bean>
```



#### 死信队列

ActiveMQ中引入了“死信队列”(Dead Letter Queue)的概念，在一条消息被重复发送给消息消费者端多次(默认为6次)后，若一直签收不成功，则ActiveMQ会将这条消息移入到“死信队列”。开发时可以开启一个后台线程监听这个队列(默认死信队列的名称为ActiveMQ.DLQ)中的消息，进行人工干预，也就是说死信队列的作用主要是处理签收失败的消息。

![JAVA_ACTIVEMQ38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ38.png?raw=true)

死信队列的配置

```java
死信队列的配置主要有两种：SharedDeadLetterStrategy和IndividualDeadLetterStrategy

1.SharedDeadLetterStrategy(默认策略)：共享的死信队列配置策略，将所有的DeadLetter保存在一个共享的队列中
共享队列的名称默认为“ActiveMQ.DLQ”，可以通过“deadLetterQueue”属性来设定：在activemq.xml中的<policyEntries>节点中配置
<deadLetterStrategy>
	<sharedDeadLetterStrategy deadLetterQueue="DLQ-QUEUE"/>
</deadLetterStrategy>

2.IndividualDeadLetterStrategy：单独的死信队列配置策略，把DeadLetter放入各自的死信通道中
对于Queue而言，死信通道的前缀默认为“ActiveMQ.DLQ.Queue”；
对于Topic而言，死信通道的前缀默认为“ActiveMQ.DLQ.Topic”。
比如队列Order，那么它对应的死信通道为“ActiveMQ.DLQ.Queue.Order”。我们可以使用queuePrefix和topicPrefix来指定上述前缀：
<!-- 仅对与order队列起作用 -->
<policyEntry queue="order">
	<deadLetterStrategy>
<!-- useQueueForQueueMessage属性的作用：是否将名为order的Topic中的DeadLetter也保存在该队列中，默认为true -->
		<individualDeadLetterStrategy queuePrefix="DLQ." useQueueForQueueMessage="false"/>
	</deadLetterStrategy>
</policyEntry>

注意：默认情况下，无论是Topic还是Queue，Broker都使用Queue来保存DeadLetter，即死信通道通常为Queue，不过开发时也可以指定为Topic

配置案例1：自动删除过期消息，此时对于过期的消息将不会被放入到死信队列，而是自动删除，>表示对所有队列起作用，processExpired表示是否将过期消息放入死信队列，默认为true

<!-- >表示对所有队列起作用 -->
<policyEntry queue=">">
	<deadLetterStrategy>
		<sharedDeadLetterStrategy processExpired="false"/>
	</deadLetterStrategy>
</policyEntry>

配置案例2：将签收失败的非持久消息也放入到死信队列，默认情况下，ActiveMQ不会把非持久化的死消息放入死信队列，processNonPersistent表示是否将非持久化消息放入死信队列，默认为false

<!-- >表示对所有队列起作用 -->
<policyEntry queue=">">
	<deadLetterStrategy>
		<sharedDeadLetterStrategy processNonPersistent="true"/>
	</deadLetterStrategy>
</policyEntry>
```

### 防止重复消费问题，幂等性调用

![JAVA_ACTIVEMQ39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ACTIVEMQ39.png?raw=true)

```
ActiveMQ中的消息有时是会被重复消费的(如网络延迟)，而我们消费消息时大都会在拿到消息后去调用其他的方法，比如说将消息的内容解析为一个对象保存到数据库中。一旦发生消息的重复消费时就会重复保存，这是有问题的，因此我们需要考虑如何防止重复调用。其实我们是没有办法防止重复调用的，只能在重复调用时进行消息是否重复消费的校验，当然对于幂等性接口也可以不进行校验。

 那如何进行校验呢？
 有很多种方式，
 1：比如说我们将消费过的消息的messageId保存到数据库(作为唯一主键)，每次消费消息前先到数据库中查一下该消息是否已被消费。或者消息入库是插入操作，消息id作为主键，就会出现主键重复无法插入
 
 2：在分布式系统中，将消费过的消息 放入redis中，以messageId作为key，message对象作为value(其实value不重要，当然也要看需求本身)，在消费消息时先从redis中查找该消息是否已被消费。

```



### 消息的分发策略

#### queue的分发策略

queue的分发策略比较固定：`轮询`（默认）或`按照严格顺序` 这两种分发策略

#### topic的分发策略

topic适用于可插拔的分发策略

所有实现了`org.apache.activemq.broker.region.policy.DispatchPolicy`的都可以。

默认实现是`org.apache.activemq.broker.region.policy.SimpleDispatchPolicy`，它将消息传递给所有的订阅者。一个更高级的实现示例是`org.apache.activemq.broker.region.policy.PriorityNetworkDispatchPolicy`，它只会分发给拥有最高优先级的网络消费者。这在循环网络拓扑结构中非常有用，因为在这种拓扑结构中，到消费者的路由不止一条。



