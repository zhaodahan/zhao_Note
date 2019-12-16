#                                 SpringBoot学习笔记（二）

# RabbitMQ详解

RabbitMQ 即一个消息队列，主要是用来实现应用程序的**异步**和**解耦**，同时也能起到**消息缓冲**，**消息分发**的作用。

消息中间件最主要的作用是**解耦**，中间件最标准的用法是生产者生产消息传送到队列，消费者从队列中拿取消息并处理，生产者不用关心是谁来消费，消费者不用关心谁在生产消息，从而达到解耦的目的。在分布式的系统中，消息队列也会被用在很多其它的方面，比如：分布式事务的支持，RPC的调用等等。

## RabbitMQ介绍

RabbitMQ是实现AMQP（高级消息队列协议）的消息中间件的一种，**RabbitMQ主要是为了实现系统之间的双向解耦**而实现的。当生产者大量产生数据时，消费者无法快速消费，那么需要一个中间层。**保存这个数据**。

RabbitMQ是一个开源的AMQP实现，服务器端用Erlang语言编写，支持多种客户端，如：Python、Ruby、.NET、Java、JMS、C、PHP、ActionScript、XMPP、STOMP等，支持AJAX。用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性等方面表现不俗。

### 相关概念

队列服务, 有三个概念： 发消息者、队列、收消息者

RabbitMQ 在这个基本概念之上, 多做了一层抽象, 在发消息者和 队列之间, 加入了交换器 (Exchange). 这样发消息者和队列就没有直接联系, 转而变成发消息者把消息给交换器, 交换器根据调度策略再把消息再给队列。

![img](http://www.itmind.net/assets/images/2016/RabbitMQ01.png)

那么，*其中比较重要的概念有 4 个，分别为：虚拟主机，交换机，队列，和绑定。*

- 虚拟主机：一个虚拟主机持有一组交换机、队列和绑定。为什么需要多个虚拟主机呢  ？很简单，RabbitMQ当中，*用户只能在虚拟主机的粒度进行权限控制。* 因此，如果需要禁止A组访问B组的交换机/队列/绑定，必须为A和B分别创建一个虚拟主机。每一个RabbitMQ服务器都有一个默认的虚拟主机“/”。
- 交换机：*Exchange 用于**转发消息**，但是它不会做存储* ，如果没有 Queue bind 到 Exchange 的话，它会直接丢弃掉 Producer 发送过来的消息。 这里有一个比较重要的概念：**路由键** 。消息到交换机的时候，交互机会转发到对应的队列中，那么究竟转发到哪个队列，就要根据该路由键。
- 绑定：也就是交换机需要和队列相绑定，这其中如上图所示，是多对多的关系。

### 交换机(Exchange)

交换机的功能主要是**接收消息并且转发到绑定的队列**，交换机**不存储消息**，在启用ack模式后，交换机找不到队列会返回错误。交换机有四种类型：Direct, topic, Headers and Fanout

- Direct：direct 类型的行为是”先匹配, 再投送”. 即在绑定时设定一个 **routing_key**, 消息的**routing_key** 匹配时, 才会被交换器投送到绑定的队列中去.
- Topic：按规则转发消息（最灵活）
- Headers：设置header attribute参数类型的交换机
- Fanout：转发消息到所有绑定队列

**Direct Exchange**
Direct Exchange是RabbitMQ默认的交换机模式，也是最简单的模式，根据key全文匹配去寻找队列。
![img](http://www.itmind.net/assets/images/2016/rabbitMq_direct.png)

第一个 X - Q1 就有一个 binding key，名字为 orange； X - Q2 就有 2 个 binding key，名字为 black 和 green。*当消息中的 路由键 和 这个 binding key 对应上的时候，那么就知道了该消息去到哪一个队列中。*

Ps：为什么 X 到 Q2 要有 black，green，2个 binding key呢，一个不就行了吗？ - 这个主要是因为可能又有 Q3，而Q3只接受 black 的信息，而Q2不仅接受black 的信息，还接受 green 的信息。

**Topic Exchange**

*Topic Exchange 转发消息主要是根据**通配符**。* 在这种交换机下，队列和交换机的绑定会定义一种路由模式，那么，通配符就要在这种路由模式和路由键之间匹配后交换机才能转发消息。

在这种交换机模式下：

- 路由键必须是一串字符，用句号（`.`） 隔开，比如说 agreements.us，或者 agreements.eu.stockholm 等。
- 路由模式必须包含一个 星号（`*`），主要用于匹配路由键指定位置的一个单词，比如说，一个路由模式是这样子：agreements..b.*，那么就只能匹配路由键是这样子的：第一个单词是 agreements，第四个单词是 b。 井号（#）就表示相当于一个或者多个单词，例如一个匹配模式是agreements.eu.berlin.#，那么，以agreements.eu.berlin开头的路由键都是可以的。

具体代码发送的时候还是如下：

```java
rabbitTemplate.convertAndSend("testTopicExchange","key1.a.c.key2", " this is  RabbitMQ!");
//第一个参数表示交换机，第二个参数表示routing key，第三个参数即消息
```

topic 和 direct 类似, 只是匹配上支持了”模式”, 在”点分”的 routing_key 形式中, 可以使用两个通配符:

- `*`表示一个词.
- `#`表示零个或多个词.

**Headers Exchange**

headers 也是根据规则匹配, 相较于 direct 和 topic 固定地使用 routing_key , headers 则是一个自定义匹配规则的类型. 在队列与交换器绑定时, 会设定一组键值对规则, 消息中也包括一组键值对( headers 属性), 当这些键值对有一对, 或全部匹配时, 消息被投送到对应队列.

**Fanout Exchange**

Fanout Exchange 消息广播的模式，不管路由键或者是路由模式，*会把消息发给绑定给它的全部队列*，如果配置了routing_key会被忽略。

## springboot集成RabbitMQ

springboot集成RabbitMQ非常简单，如果只是简单的使用配置非常少，springboot提供了spring-boot-starter-amqp项目对消息各种支持。

### 简单使用

1、配置pom包，主要是添加spring-boot-starter-amqp的支持

```xml
<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

2、配置文件

配置rabbitmq的安装地址、端口以及账户信息

```properties
spring.application.name=Spring-boot-rabbitmq

spring.rabbitmq.host=192.168.0.86
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=123456
```

3、队列配置

```java
@Configuration
public class RabbitConfig {
    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }
}
```

3、发送者

rabbitTemplate是springboot 提供的默认实现

```java
public class HelloSender {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	public void send() {
		String context = "hello " + new Date();
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("hello", context);
	}

}
```

4、接收者

```java
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

}
```

5、测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {
	@Autowired
	private HelloSender helloSender;
	@Test
	public void hello() throws Exception {
		helloSender.send();
	}

}
```

> 注意，发送者和接收者的queue name必须一致，不然不能接收

### 多对多使用

一个发送者，N个接收者或者N个发送者和N个接收者会出现什么情况呢？

**一对多发送**
接收端注册了两个Receiver,Receiver1和Receiver2，发送端加入参数计数，接收端打印接收到的参数，下面是测试代码，发送一百条消息，来观察两个接收端的执行效果

```java
@Test
public void oneToMany() throws Exception {
	for (int i=0;i<100;i++){
		neoSender.send(i);
	}
}
```

结果如下：

```
Receiver 1: Spring boot neo queue ****** 11
Receiver 2: Spring boot neo queue ****** 12
Receiver 2: Spring boot neo queue ****** 14
Receiver 1: Spring boot neo queue ****** 13
Receiver 2: Spring boot neo queue ****** 15
Receiver 1: Spring boot neo queue ****** 16
Receiver 1: Spring boot neo queue ****** 18
Receiver 2: Spring boot neo queue ****** 17
Receiver 2: Spring boot neo queue ****** 19
Receiver 1: Spring boot neo queue ****** 20
```

根据返回结果得到以下结论

> 一个发送者，N个接受者,经过测试会均匀的将消息发送到N个接收者中

**多对多发送**

复制了一份发送者，加入标记，在一百个循环中相互交替发送

```
@Test
	public void manyToMany() throws Exception {
		for (int i=0;i<100;i++){
			neoSender.send(i);
			neoSender2.send(i);
		}
}
```

结果如下：

```
Receiver 1: Spring boot neo queue ****** 20
Receiver 2: Spring boot neo queue ****** 20
Receiver 1: Spring boot neo queue ****** 21
Receiver 2: Spring boot neo queue ****** 21
Receiver 1: Spring boot neo queue ****** 22
Receiver 2: Spring boot neo queue ****** 22
Receiver 1: Spring boot neo queue ****** 23
Receiver 2: Spring boot neo queue ****** 23
Receiver 1: Spring boot neo queue ****** 24
Receiver 2: Spring boot neo queue ****** 24
Receiver 1: Spring boot neo queue ****** 25
Receiver 2: Spring boot neo queue ****** 25
```

> 结论：和一对多一样，接收端仍然会均匀接收到消息

### 高级使用

**对象的支持**

springboot完美的支持对象的发送和接收，不需要格外的配置。

```java
//发送者
public void send(User user) {
	System.out.println("Sender object: " + user.toString());
	this.rabbitTemplate.convertAndSend("object", user);
}

...

//接收者
@RabbitHandler
public void process(User user) {
    System.out.println("Receiver object : " + user);
}
```

结果如下：

```
Sender object: User{name='neo', pass='123456'}
Receiver object : User{name='neo', pass='123456'}
```

**Topic Exchange**

topic 是RabbitMQ中最灵活的一种方式，可以根据routing_key自由的绑定不同的队列

首先对topic规则配置，这里使用两个队列来测试

```java
@Configuration
public class TopicRabbitConfig {
    final static String message = "topic.message";
    final static String messages = "topic.messages";
    @Bean
    public Queue queueMessage() {
        return new Queue(TopicRabbitConfig.message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(TopicRabbitConfig.messages);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
```

使用queueMessages同时匹配两个队列，queueMessage只匹配”topic.message”队列

```java
public void send1() {
	String context = "hi, i am message 1";
	System.out.println("Sender : " + context);
	this.rabbitTemplate.convertAndSend("exchange", "topic.message", context);
}

public void send2() {
	String context = "hi, i am messages 2";
	System.out.println("Sender : " + context);
	this.rabbitTemplate.convertAndSend("exchange", "topic.messages", context);
}
```

发送send1会匹配到topic.#和topic.message 两个Receiver都可以收到消息，发送send2只有topic.#可以匹配所有只有Receiver2监听到消息

**Fanout Exchange**

Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。

Fanout 相关配置

```java
@Configuration
public class FanoutRabbitConfig {
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }
    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }
    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }
    @Bean
    Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

}
```

这里使用了A、B、C三个队列绑定到Fanout交换机上面，发送端的routing_key写任何字符都会被忽略：

```java
public void send() {
		String context = "hi, fanout msg ";
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
}
```

结果如下：

```
Sender : hi, fanout msg 
...
fanout Receiver B: hi, fanout msg 
fanout Receiver A  : hi, fanout msg 
fanout Receiver C: hi, fanout msg 
```

结果说明，绑定到fanout交换机上面的队列都收到了消息

# 定时任务

在项目开发过程中，经常需要定时任务来帮助我们来做一些内容，springboot默认已经帮我们实现了，只需要添加相应的注解就可以实现

## 1、pom包配置

pom包里面只需要引入springboot starter包即可

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
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
	</dependency>
</dependencies>
```

## 2、启动类启用定时

在启动类上面加上`@EnableScheduling`即可开启定时

```java
@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

## 3、创建定时任务实现类

定时任务1：

```java
@Component
public class SchedulerTask {
    private int count=0;
    @Scheduled(cron="*/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }

}
```

定时任务2：

```java
@Component
public class Scheduler2Task {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }
}
```

结果如下：

```
this is scheduler task runing  0
现在时间：09:44:17
this is scheduler task runing  1
现在时间：09:44:23
this is scheduler task runing  2
现在时间：09:44:29
this is scheduler task runing  3
现在时间：09:44:35
```

## 参数说明

`@Scheduled` 参数可以接受两种定时的设置，一种是我们常用的`cron="*/6 * * * * ?"`,一种是 `fixedRate = 6000`，两种都表示每隔六秒打印一下内容。

**fixedRate 说明**

- `@Scheduled(fixedRate = 6000)` ：上一次开始执行时间点之后6秒再执行
- `@Scheduled(fixedDelay = 6000)` ：上一次执行完毕时间点之后6秒再执行
- `@Scheduled(initialDelay=1000, fixedRate=6000)` ：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次

# 邮件服务

发送邮件应该是网站的必备功能之一。(什么注册验证，忘记密码或者是给用户发送营销信息)

邮件发送的实现方式：

1：JavaMail相关api

2：spring推出了JavaMailSender

3：springboot封装的spring-boot-starter-mail。

## 简单使用spring-boot-starter-mail

### 1、pom包配置

pom包里面添加spring-boot-starter-mail包引用

```xml
<dependencies>
	<dependency> 
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-mail</artifactId>
	</dependency> 
</dependencies>
```

### 2、在application.properties中添加邮箱配置

```properties
spring.mail.host=smtp.qiye.163.com //邮箱服务器地址
spring.mail.username=xxx@oo.com //用户名
spring.mail.password=xxyyooo    //密码
spring.mail.default-encoding=UTF-8

mail.fromMail.addr=xxx@oo.com  //以谁来发送邮件
```

邮件服务器是一种用来负责电子邮件收发管理的设备。它比网络上的免费邮箱更安全和高效，因此一直是[企业](https://baike.baidu.com/item/%E4%BC%81%E4%B8%9A/707680)公司的必备设备。

### 3、编写mailService,这里只提出实现类。

```java
@Component
public class MailServiceImpl implements MailService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JavaMailSender mailSender; //JavaMailSender是spring封装的邮件发送api

    @Value("${mail.fromMail.addr}")
    private String from;
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);  //subject标题
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
    }
}
```

### 4、编写test类进行测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService MailService;
    @Test
    public void testSimpleMail() throws Exception {
        MailService.sendSimpleMail("ityouknow@126.com","test simple mail"," hello this is simple mail");
    }
}
```

## 加点料

在使用的过程中，我们通常在邮件中加入图片或者附件来丰富邮件的内容。so 如何使用springboot来发送丰富的邮件。

### 发送html格式邮件

其它都不变在MailService添加sendHtmlMail方法.

```java
public void sendHtmlMail(String to, String subject, String content) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);    //true表示需要创建一个multipart message

        mailSender.send(message);
        logger.info("html邮件发送成功");
    } catch (MessagingException e) {
        logger.error("发送html邮件时发生异常！", e);
    }
}
```

在测试类中构建html内容，测试发送

```java
@Test
public void testHtmlMail() throws Exception {
    String content="<html>\n" +
            "<body>\n" +
            "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
            "</body>\n" +
            "</html>";
    MailService.sendHtmlMail("ityouknow@126.com","test simple mail",content);
}
```

### 发送带附件的邮件

在MailService添加sendAttachmentsMail方法.

```java
public void sendAttachmentsMail(String to, String subject, String content, String filePath){
    MimeMessage message = mailSender.createMimeMessage();
    try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
        //添加多个附件可以使用多条 `helper.addAttachment(fileName, file) 
        mailSender.send(message);
        logger.info("带附件的邮件已经发送。");
    } catch (MessagingException e) {
        logger.error("发送带附件的邮件时发生异常！", e);
    }
}
```

在测试类中添加测试方法

```java
@Test
public void sendAttachmentsMail() {
    String filePath="e:\\tmp\\application.log";
    mailService.sendAttachmentsMail("ityouknow@126.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
}
```

### 发送带静态资源的邮件

邮件中的静态资源一般就是指图片，在MailService添加sendAttachmentsMail方法.

```java
public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
    MimeMessage message = mailSender.createMimeMessage();

    try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        mailSender.send(message);
        logger.info("嵌入静态资源的邮件已经发送。");
    } catch (MessagingException e) {
        logger.error("发送嵌入静态资源的邮件时发生异常！", e);
    }
}
```

在测试类中添加测试方法

```java
@Test
public void sendInlineResourceMail() {
    String rscId = "neo006";
    String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
    String imgPath = "C:\\Users\\summer\\Pictures\\favicon.png";

    mailService.sendInlineResourceMail("ityouknow@126.com", "主题：这是有图片的邮件", content, imgPath, rscId);
}
```

> 添加多个图片可以使用多条 `<img src='cid:" + rscId + "' >` 和 `helper.addInline(rscId, res)` 来实现

## 邮件系统

发送邮件的基础服务就这些了，如果要做成一个邮件系统的话还需要考虑以下几个问题：

### 邮件模板

```
尊敬的neo用户：
                  
              恭喜您注册成为xxx网的用户,，同时感谢您对xxx的关注与支持并欢迎您使用xx的产品与服务。
              ...
```

其中只有neo这个用户名在变化，其它邮件内容均不变，如果每次发送邮件都需要手动拼接的话会不够优雅，并且每次模板的修改都需要改动代码的话也很不方便，因此对于这类邮件需求，都建议做成邮件模板来处理。模板的本质很简单，就是在模板中替换变化的参数，转换为html字符串即可，这里以`thymeleaf`为例来演示。

**1、pom中导入thymeleaf的包**

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

**2、在resorces/templates下创建emailTemplate.html**

```html
<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8"/>
        <title>Title</title>
    </head>
    <body>
        您好,这是验证邮件,请点击下面的链接完成验证,<br/>
        <a href="#" th:href="@{ http://www.ityouknow.com/neo/{id}(id=${id}) }">激活账号</a>
    </body>
</html>
```

**3、解析模板并发送**

```java
@Test
public void sendTemplateMail() {
    //创建邮件正文
    Context context = new Context();
    context.setVariable("id", "006");
    String emailContent = templateEngine.process("emailTemplate", context);

    mailService.sendHtmlMail("ityouknow@126.com","主题：这是模板邮件",emailContent);
}
```

### 发送失败

因为各种原因，总会有邮件发送失败的情况，比如：邮件发送过于频繁、网络异常等。在出现这种情况的时候，我们一般会考虑重新重试发送邮件，会分为以下几个步骤来实现：

- 1、接收到发送邮件请求，首先记录请求并且入库。
- 2、调用邮件发送接口发送邮件，并且将发送结果记录入库。
- 3、启动定时系统扫描时间段内，未发送成功并且重试次数小于3次的邮件，进行再次发送

### 异步发送

很多时候邮件发送并不是我们主业务必须关注的结果，比如通知类、提醒类的业务可以允许延时或者失败。这个时候可以采用异步的方式来发送邮件，加快主交易执行速度，在实际项目中可以采用MQ发送邮件相关参数，监听到消息队列之后启动发送邮件。

在spring boot中发送邮件：

1、引入依赖：

```xml
	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
```

2、application.yml中配置邮件相关的参数：

```properties
spring:
  mail:
    host: smtp.exmail.qq.com
     username: 用户名
     password: 密码
     properties:
       mail:
         smtp:
           auth: true
           starttls:
             enable: true
             required: true
```

3、邮件service代码：	

```java
@Service
public class MailService {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private JavaMailSender sender;
@Value("${spring.mail.username}")
private String from;

/**
 * 发送纯文本的简单邮件
 * @param to
 * @param subject
 * @param content
 */
public void sendSimpleMail(String to, String subject, String content){
	SimpleMailMessage message = new SimpleMailMessage();
	message.setFrom(from);
	message.setTo(to);
	message.setSubject(subject);
	message.setText(content);
	try {
		sender.send(message);
		logger.info("简单邮件已经发送。");
	} catch (Exception e) {
		logger.error("发送简单邮件时发生异常！", e);
	}
}

/**
 * 发送html格式的邮件
 * @param to
 * @param subject
 * @param content
 */
public void sendHtmlMail(String to, String subject, String content){
	MimeMessage message = sender.createMimeMessage();
 
	try {
		//true表示需要创建一个multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
 
		sender.send(message);
		logger.info("html邮件已经发送。");
	} catch (MessagingException e) {
		logger.error("发送html邮件时发生异常！", e);
	}
}

/**
 * 发送带附件的邮件
 * @param to
 * @param subject
 * @param content
 * @param filePath
 */
public void sendAttachmentsMail(String to, String subject, String content, String filePath){
	MimeMessage message = sender.createMimeMessage();
 
	try {
		//true表示需要创建一个multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
 
		FileSystemResource file = new FileSystemResource(new File(filePath));
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
        
		sender.send(message);
		logger.info("带附件的邮件已经发送。");
	} catch (MessagingException e) {
		logger.error("发送带附件的邮件时发生异常！", e);
	}
}

/**
 * 发送嵌入静态资源（一般是图片）的邮件
 * @param to
 * @param subject
 * @param content 邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:rscId01\" >
 * @param rscPath 静态资源路径和文件名
 * @param rscId 静态资源id
 */
public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
	MimeMessage message = sender.createMimeMessage();
 
	try {
		//true表示需要创建一个multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
 
		FileSystemResource res = new FileSystemResource(new File(rscPath));
		helper.addInline(rscId, res);
        
		sender.send(message);
		logger.info("嵌入静态资源的邮件已经发送。");
	} catch (MessagingException e) {
		logger.error("发送嵌入静态资源的邮件时发生异常！", e);
	}
}
}
```
4、单元测试代码：

```java
public class MailTests extends BasicUtClass{
@Autowired
private MailService mailService;
private String to = "xujijun@mail.cn";
@Test
public void sendSimpleMail() {
	mailService.sendSimpleMail(to, "主题：简单邮件", "测试邮件内容");
}

@Autowired
VelocityEngine velocityEngine;
@Test
public void sendHtmlMail() {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("time", XDateUtils.nowToString());
	model.put("message", "这是测试的内容。。。");
	model.put("toUserName", "张三");
	model.put("fromUserName", "老许");
	String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "welcome.vm", "UTF-8", model);
	mailService.sendHtmlMail(to, "主题：html邮件", content);
}
 
@Test
public void sendAttachmentsMail() {
	mailService.sendAttachmentsMail(to, "主题：带附件的邮件", "有附件，请查收！", "C:\\Users\\Xu\\Desktop\\csdn\\1.png");
}

@Test
public void sendInlineResourceMail() {
	String rscId = "rscId001";
	mailService.sendInlineResourceMail(to,
			"主题：嵌入静态资源的邮件",
			"<html><body>这是有嵌入静态资源：<img src=\'cid:" + rscId + "\' ></body></html>",
			"C:\\Users\\Xu\\Desktop\\csdn\\1.png",
			rscId);
}
}
```


---------------------


