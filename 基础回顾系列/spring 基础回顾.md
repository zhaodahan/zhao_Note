spring 基础回顾

## spring 是什么

Spring 是一个 IOC(DI)和AOP容器框架.  spring框架的源码十分优秀。

![JAVAWEB_SPRING1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING1.png?raw=true)

```
Spring 是一个容器, 因为它包含并且管理应用对象的生命周期。

spring是一个一站式框架，在 IOC 和 AOP 的基础上可以整合各种企业应用的开源框架和优秀的第三方类库,有了spring就可以整个javaEE的一系列框架 （实际上 Spring 自身也提供了展现层的 SpringMVC 和 持久层的 Spring JDBC（JPA））。
```



### 初步使用spring

spring能帮助我们做什么？

```java
	public static void main(String[] args) {
//      我们以前使用一个对象：
        // 1： 先new出对象
//		HelloWorld helloWorld = new HelloWorld();
        // 2:  为对象进行赋值 
//		helloWorld.setName("Tom");
        // 3 调用对象的方法
//		helloWorld.hello(); 
		
		//1. 创建 Spring 的 IOC 容器
        // ApplicationContext就是代表我们spring中的IOC容器，
        //ClassPathXmlApplicationContext 表示的是配置文件在类路径下(src下，类的根目录下)
   
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		
        //在构造器和设置属性方法中打印日志，发现IOC容器初始化的时候
         //IOC容器帮助我们做了上面的1，2步。创建了容器并对属性赋了值
		//2. 当我们需要使用时，直接从 IOC 容器中获取 bean 的实例即可
		HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");
        
		//3. 使用 bean
		helloWorld.hello();
    }

<!-- 配置一个 bean -->
	<bean id="helloWorld" class="com.atguigu.spring.helloworld.HelloWorld">
		<!-- 为属性赋值 -->
		<property name="user" value="Jerry"></property>
	</bean>
```



## spring中Bean的配置

### IOC（DI）

```
IOC(Inversion of Control)：其思想是反转资源获取的方向. 

传统的资源查找方式要求组件向容器发起请求查找资源. 作为回应, 容器适时的返回资源. 
而应用了 IOC 之后, 则是容器主动地将资源推送给它所管理的组件, 组件所要做的仅是选择一种合适的方式来接受资源. 
(总结： 原来是主动的去找容器获取，现在是容器主动的推送给你，只需要找中方式接受一下即可)

DI(Dependency Injection) — IOC 的另一种表述方式：即组件以一些预先定义好的方式(例如: setter 方法)接受来自如容器的资源注入. 依赖注入就是依赖容器将我们要用的对象注入。 这样描述更通俗
(DI 和IOC 说的本质上就是一回事)
```

![JAVAWEB_SPRING2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING2.png?raw=true)

#### IOC的形成过程

一个需求：我们有一个报表生成器，生成 HTML 或PDF格式的不同类型的报表.

##### 分离接口与实现

![JAVAWEB_SPRING3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING3.png?raw=true)

这种方式耦合度比较高，在service中，我们需要知道这个接口和他的每个具体的实现类。

##### 工厂设计模式

![JAVAWEB_SPRING4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING4.png?raw=true)

由工厂帮我们生成具体的实现类。

##### 反转控制

![JAVAWEB_SPRING5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING5.png?raw=true)

按需分配，有容器为我们注入我们需要的。

### spring IOC 容器

```
在 Spring IOC 容器读取 Bean 配置创建 Bean 实例之前, 必须对它进行实例化. 只有在容器实例化后, 才可以从 IOC 容器里获取 Bean 实例并使用.

Spring 提供了两种类型的 IOC 容器实现. 
BeanFactory: IOC 容器的基本实现.BeanFactory 是 Spring 框架的基础设施，面向 Spring 本身。
ApplicationContext: 提供了更多的高级特性. 是 BeanFactory 的子接口.ApplicationContext 面向使用 Spring 框架的开发者，几乎所有的应用场合都直接使用 ApplicationContext 而非底层的 BeanFactory

无论使用何种方式, 配置文件时相同的.
ApplicationContext 的主要实现类：
ClassPathXmlApplicationContext：从 类路径下加载配置文件
FileSystemXmlApplicationContext: 从文件系统中加载配置文件

ConfigurableApplicationContext 扩展于 ApplicationContext，新增加两个主要方法：refresh() 和 close()， 让 ApplicationContext 具有启动、刷新和关闭上下文的能力。
ApplicationContext 在初始化上下文时就实例化所有单例的 Bean。

WebApplicationContext 是专门为 WEB 应用而准备的，它允许从相对于 WEB 根目录的路径中完成初始化工作(在集成web的时候需要使用这个容器)

```

![JAVAWEB_SPRING6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING6.png?raw=true)



###  具体的配置Bean

```java
配置 bean
配置形式：
基于 XML 文件的方式；
	<!-- 配置一个 bean -->
    <!--
    class：bean的全类名，这里是通过反射在IOC容器中创建Bean，所以要求Bean中必须有一个无参的构造器
    id: Bean在IOC容器中的唯一标识，id是唯一的。
    -->
	<bean id="helloWorld" class="com.atguigu.spring.helloworld.HelloWorld">
		<!-- 为属性赋值 -->
		<property name="user" value="Jerry"></property>
	</bean>
基于注解的方式

@Component: 基本注解, 标识了一个受 Spring 管理的组件
@Respository: 标识持久层组件
@Service: 标识服务层(业务层)组件
@Controller: 标识表现层组件

（上面两种形式的底层都是全类名反射机制）

创建Bean实例的方式：通过全类名（反射）、通过工厂方法（静态工厂方法 & 实例工厂方法）、FactoryBean

依赖注入的方式：属性注入；构造器注入


```



#### 基于注解的方式

配置xml 量多了不太方便，所以引入基于注解的方式简化配置(他们的原理都是反射)

```java
1)在 classpath 中扫描组件
组件扫描(component scanning):  Spring 能够从 classpath 下自动扫描, 侦测和实例化具有特定注解的组件. 
特定组件包括:
@Component: 基本注解, 标识了一个受 Spring 管理的组件
@Respository: 标识持久层组件
@Service: 标识服务层(业务层)组件
@Controller: 标识表现层组件
对于扫描到的组件, Spring 有默认的命名策略: 使用非限定类名, 第一个字母小写. 也可以在注解中通过 value 属性值标识组件的名称
当在组件类上使用了特定的注解之后, 还需要在 Spring 的配置文件中声明 <context:component-scan> ：
base-package 属性指定一个需要扫描的基类包，Spring 容器将会扫描这个基类包里及其子包中的所有类. 
当需要扫描多个包时, 可以使用逗号分隔.
如果仅希望扫描特定的类而非基包下的所有类，可使用 resource-pattern 属性过滤特定的类.
    <!-- 配置自动扫描的包: 这个包和他的子包会被扫描 -->
	<context:component-scan base-package="com.atguigu.spring.annotation.generic">                         </context:component-scan>
	
<context:include-filter> 子节点表示要包含的目标类
<context:exclude-filter> 子节点表示要排除在外的目标类
<context:component-scan> 下可以拥有若干个 <context:include-filter> 和 <context:exclude-filter> 子节点

注解开发常用于web开发，controller层和service层等各个层有太多的类都需要被IOC容器管理，这时候如果我们手动的一个个的用xml的形式进行配置，太过繁琐，这时候我们只需要对顶层包配置组件扫描就可以一次性解决了

```

##### 基于注解来装配bean的属性

```java
组件装配:
<context:component-scan> 元素还会自动注册 AutowiredAnnotationBeanPostProcessor (bean后置处理器，可以制动装配属性)实例, 该实例可以自动装配具有 @Autowired 和 @Resource 、@Inject注解的属性.

使用 @Autowired 自动装配 Bean
@Autowired 注解自动装配具有兼容类型的单个 Bean属性 (最常用)
构造器, 普通字段(即使是非 public), 一切具有《参数的方法》都可以应用@Autowired 注解
我们一般将其放在类的属性上，为类的属性进行赋值,但其实注解还可以加在入参的前面。(放在参数列表中)

默认情况下, 所有使用 @Authwired 注解的属性都需要被设置(他会去IOC容器中找是否存在符合该属性的bean实例). 当 Spring 找不到匹配的 Bean 装配属性时, 会抛出异常, 若某一属性允许不被设置, 可以设置 @Authwired 注解的 required 属性为 false
从上可知： 能够自动注入的前提是在IOC容器能找到类型兼容的bean

@Controller
public class UserAction {
	
	@Autowired
	private UsreService usreService;
	
	public void execute(){
		System.out.println("接受请求");
		usreService.addNew();
	}
	
}

当根据属性在IOC容器中找到多个符合的bean实例怎么解决？
当 IOC 容器里存在多个类型兼容的 Bean 时, 通过类型的自动装配将无法工作. 此时可以在 @Qualifier 注解里提供 Bean 的名称. Spring 允许对方法的入参标注 @Qualifiter 已指定注入 Bean 的名称
(举例： 一个借口的多个实现
1： 如果有多个兼容的他首先回去找beanName相同的，在我们需要注入的bean上面加上@@Respository（"属性名"），让bean的名称刚好和要注入的属性名一致。
2：明确的在要装配的属性上使用@Qualifier 指定要注入的实现
如：
@Autowired
@Qualifier("usreServiceImpl")
	private UsreService usreService;
)

@Authwired 注解也可以应用在数组类型的属性上, 此时 Spring 将会把所有匹配的 Bean 进行自动装配.
@Authwired 注解也可以应用在集合属性上, 此时 Spring 读取该集合的类型信息, 然后自动装配所有与之兼容的 Bean. 
@Authwired 注解用在 java.util.Map 上时, 若该 Map 的键值为 String, 那么 Spring 将自动装配与之 Map 值类型兼容的 Bean, 此时 Bean 的名称作为键值

@Resource 或 @Inject 注解的功能和@Authwired 一致，我们一般直接使用@Authwired

```



#### spring通过工厂方法配置获取bean

(这种配置方式在整合第三个框架的时候会使用到)

![JAVAWEB_SPRING13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING13.png?raw=true)

```java
 通过调用静态工厂方法创建 Bean：
 
调用静态工厂方法创建 Bean是将对象创建的过程封装到静态方法中. 当客户端需要对象时, 只需要简单地调用静态方法, 而不同关心创建对象的细节.

要声明通过静态方法创建的 Bean, 需要在 Bean 的 class 属性里指定拥有该工厂的方法的类, 同时在 factory-method 属性里指定工厂方法的名称. 最后, 使用 <constrctor-arg> 元素为该方法传递方法参数.
 
   <!-- 通过工厂方法的方式来配置 bean -->
	<!-- 1. 通过静态工厂方法: 一个类中有一个静态方法, 可以返回一个类的实例(了解) -->
	<!-- 在 class 中指定静态工厂方法的全类名, 在 factory-method 中指定静态工厂方法的方法名 -->
	<bean id="dateFormat" class="java.text.DateFormat" factory-method="getDateInstance">
		<!-- 可以通过 constructor-arg 子节点为静态工厂方法指定参数 -->
		<constructor-arg value="2"></constructor-arg>
	</bean>

通过调用实例工厂方法创建 Bean：需要先创建工厂本身，再使用工厂的实例方法来返回bean的实例
实例工厂方法: 将对象的创建过程封装到另外一个对象实例的方法里. 当客户端需要请求对象时, 只需要简单的调用该实例方法而不需要关心对象的创建细节.
要声明通过实例工厂方法创建的 Bean
在 bean 的 factory-bean 属性里指定拥有该工厂方法的 Bean
在 factory-method 属性里指定该工厂方法的名称
使用 construtor-arg 元素为工厂方法传递方法参数
<!-- 2. 实例工厂方法: 先需要创建工厂对象, 再调用工厂的非静态方法返回实例(了解) -->
	<!-- ①. 创建工厂对应的 bean -->
	<bean id="simpleDateFormat" class="java.text.SimpleDateFormat">
		<constructor-arg value="yyyy-MM-dd hh:mm:ss"></constructor-arg>
	</bean>
	
	<!-- ②. 有实例工厂方法来创建 bean 实例 -->
	<!-- factory-bean 指向工厂 bean, factory-method 指定工厂方法(了解) -->
	<bean id="datetime" factory-bean="simpleDateFormat" factory-method="parse">
		<!-- 通过 constructor-arg 执行调用工厂方法需要传入的参数 -->
		<constructor-arg value="1990-12-12 12:12:12"></constructor-arg>
	</bean>
    
```



#### 通过FactoryBean配置获取bean

我们有时候在配置一个bean的时候需要使用到IOC容器中的其他bean（spring集成Quartz来做一个任务调度）。这时候Spring就提供FactoryBean来满足这个需求

```java
实现 FactoryBean(spring底层提供) 接口在 Spring IOC 容器中配置 Bean
Spring 中有两种类型的 Bean, 一种是普通Bean, 另一种是工厂Bean, 即FactoryBean. 
工厂 Bean 跟普通Bean不同, 其返回的对象不是指定类的一个实例, 其返回的是该工厂 Bean 的 getObject 方法所返回的对象 

<!-- 配置通过 FactroyBean 的方式来创建 bean 的实例(了解) --> 
	<bean id="user" class="com.atguigu.spring.ref.UserBean"></bean>
	
 public class UserBean implements FactoryBean<User>{

	/**
	 * 返回的 bean 的实例
	 */
	@Override
	public User getObject() throws Exception {
		User user = new User();
		user.setUserName("abc");
		user.setWifeName("ABC");
		
		List<Car> cars = new ArrayList<>();
		cars.add(new Car("ShangHai", "BuiKe", 180, 300000));
		cars.add(new Car("ShangHai", "CRUZE", 130, 150000));
		
		user.setCars(cars);
		return user;
	}

	/**
	 * 返回的 bean 的类型
	 */
	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	/**
	 * 返回的 bean 是否为单例的
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

}

```

![JAVAWEB_SPRING14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING14.png?raw=true)

### 属性依赖注入的方式

```java
Spring 支持 3 种依赖注入的方式
属性注入
构造器注入
工厂方法注入（很少使用，不推荐）

属性注入：
属性注入即通过 setter 方法注入Bean 的属性值或依赖的对象 (这里就要求bean对象属性必须具有setter方法)
属性注入使用 <property> 元素, 使用 name 属性指定 Bean 的属性名称 (这里的name，对应的是setter方法上的名称，与实际属性名称关系不大)，value 属性或 <value> 子节点指定属性值 
属性注入是实际应用中最常用的注入方式
	<bean id="helloWorld" class="com.atguigu.spring.helloworld.HelloWorld">
		<!-- 为属性赋值 -->
		<property name="user" value="Jerry"></property>
	</bean>

构造方法注入：
通过构造方法注入Bean 的属性值或依赖的对象，它保证了 Bean 实例在实例化后就可以使用。
构造器注入在 <constructor-arg> 元素里声明属性, <constructor-arg> 中没有 name 属性
<!-- 通过构造器注入属性值 -->
	<bean id="helloWorld3" class="com.atguigu.spring.helloworld.HelloWorld">
		<!-- 要求: 在 Bean 中必须有对应的构造器.  -->
		<constructor-arg value="Mike"></constructor-arg>
	</bean>
	
<!-- 若一个 bean 有多个构造器, 如何通过构造器来为 bean 的属性赋值 -->
     <!-- 多个参数，他是按照参数顺序来配的-->
	<!-- 可以根据 index 和 value 进行更加精确的定位. (了解) -->
	<bean id="car" class="com.atguigu.spring.helloworld.Car">
		<constructor-arg value="KUGA" index="1"></constructor-arg>
		<constructor-arg value="ChangAnFord" index="0"></constructor-arg>
		<constructor-arg value="250000" type="float"></constructor-arg>
	</bean>
构造方法注入如何区分方法重载？ 根据参数类型，java中也是根据参数列表来区分重载的
	<bean id="car" class="com.atguigu.spring.helloworld.Car">
		<constructor-arg value="KUGA" type="string"></constructor-arg>
		<constructor-arg value="250000" type="float"></constructor-arg>
	</bean>
	<bean id="car2" class="com.atguigu.spring.helloworld.Car">
		<constructor-arg value="KUGAg" type="string"></constructor-arg>
		<constructor-arg value="180" type="int"></constructor-arg>
	</bean>
（当然顺序和类型可以混合使用）	

```

#### 注入属性的细节

```java
字面值:
字面值：可用字符串表示的值，可以通过 <value> 元素标签或 value 属性进行注入。
基本数据类型及其封装类、String 等类型都可以采取字面值注入的方式
若字面值中包含特殊字符，可以使用 <![CDATA[]]> 把字面值包裹起来。比如带有"<XX>"等的特殊标记现在xml配置文件中解析存在歧义
	<bean id="car2" class="com.atguigu.spring.helloworld.Car">
		<constructor-arg value="ChangAnMazda"></constructor-arg>
		<!-- 若字面值中包含特殊字符, 则可以使用 DCDATA 来进行赋值. (了解) -->
		<constructor-arg>
			<value><![CDATA[<ATARZA>]]></value>
		</constructor-arg>
		<constructor-arg value="180" type="int"></constructor-arg>
	</bean>

引用其它 Bean： 
实际开发过程中，经常需要去引用其他的bean，那么这个bean在配置文件中如何进行配置
组成应用程序的 Bean 经常需要相互协作以完成应用程序的功能. 要使 Bean 能够相互访问, 就必须在 Bean 配置文件中指定对 Bean 的引用
	<!-- 配置 bean -->
	<bean id="dao5" class="com.atguigu.spring.ref.Dao"></bean>

	<bean id="service" class="com.atguigu.spring.ref.Service">
		<!-- 通过property标签的 ref 属性建立bean之间的引用关系  指定当前属性指向哪一个 bean! -->
		<property name="dao" ref="dao5"></property>
	</bean>
	
在 Bean 的配置文件中, 可以通过 <ref> 元素或 ref  属性为 Bean 的属性或构造器参数指定对 Bean 的引用. 
也可以在属性或构造器里包含 Bean 的声明, 这样的 Bean 称为内部 Bean
	<bean id="car" class="com.atguigu.spring.helloworld.Car">
		<constructor-arg value="KUGA" index="1"></constructor-arg>
		<constructor-arg value="ChangAnFord" index="0"></constructor-arg>
		<constructor-arg ref="dao5"></constructor-arg>
	</bean>

<!-- 声明使用内部 bean -->
	<bean id="service2" class="com.atguigu.spring.ref.Service">
		<property name="dao">
			<!-- 内部 bean, 类似于匿名内部类对象. 不能被外部的 bean 来引用，只能在内部使用
			, 也没有必要设置 id 属性 -->
			<bean class="com.atguigu.spring.ref.Dao">
				<property name="dataSource" value="c3p0"></property>
			</bean>
		</property>
	</bean>

注入参数详解：null 值和级联属性
可以使用专用的 <null/> 元素标签为 Bean 的字符串或其它对象类型的属性注入 null 值
和 Struts、Hiberante 等框架一样，Spring 支持级联属性的配置。
	<property name="dataSource" > <null/></property>
级联属性赋值，用“.” 表示级联关系	
   <bean id="action" class="com.atguigu.spring.ref.Action">
		<property name="service" ref="service2"></property>
		<!-- 设置级联属性(了解)，注意需要先初始化之后才能为级联属性赋值，否则会报异常 -->
		<property name="service.dao.dataSource" value="DBCP2"></property>
	</bean>
 
       
集合属性：
在 Spring中可以通过一组内置的 xml 标签(例如: <list>, <set> 或 <map>) 来配置集合属性.

<!-- 装配集合属性 -->
	<bean id="user" class="com.atguigu.spring.helloworld.User">
		<property name="userName" value="Jack"></property>
		<property name="cars">
			<!-- 使用 list 元素来装配集合属性 -->
			<list>
				<ref bean="car"/>
				<ref bean="car2"/>
			</list>
		</property>
	</bean>
	
	<!-- 声明集合类型的 bean，这样就可以让其他的声明共享 -->
	<util:list id="cars">
		<ref bean="car"/>
		<ref bean="car2"/>
	</util:list>
	
	<bean id="user2" class="com.atguigu.spring.helloworld.User">
		<property name="userName" value="Rose"></property>
		<!-- 引用外部声明的 list -->
		<property name="cars" ref="cars"></property>
	</bean>
	
   <bean id="map" class="com.atguigu.spring.helloworld.User">
		<property name="userName" value="Rose"></property>
		<!-- 引用外部声明的 list -->
		<property name="cars">
		   <map>
		    <entry key='AA' value-ref='car'></entry>
			<entry key='BB' value-ref='car2'></entry>
		   </map>
		</property>
	</bean>

使用 <props> 定义 java.util.Properties, 该标签使用多个 <prop> 作为子标签. 每个 <prop> 标签必须定义 key 属性 （这个在加载外部配置文件比较常用）
<--配置 Properties属性值-->
<bean id="datasource" class="com. atguigu spring beans. collections. Datasource">
	<property name="properties">
	<--使用 props和prop子节点来为 properties属性赋值，他对着类中的Properties 类-->
	<props>
		<prop key="user">root</prop>
		<prop key="password">1234</prop>
		<prop key=dbcurl ">jdbc: mysql: ///test</prop>
		<prop key="driverclass">com mysqljdbc Driver</prop>
	</props>
</property>
</bean>

使用 p 命名空间后，基于 XML 的配置方式将进一步简化
<bean id="user3" class="com.atguigu.spring.helloworld.User"
		p:cars-ref="cars" p:userName="Titannic"></bean>

```

### spring的自动装配

```java
Spring IOC 容器可以自动装配 Bean. 需要做的仅仅是在 <bean> 的 autowire 属性里指定自动装配的模式

byType(根据类型自动装配): 若 IOC 容器中有多个与目标 Bean 类型一致的 Bean. 在这种情况下, Spring 将无法判定哪个 Bean 最合适该属性, 所以不能执行自动装配.

byName(根据名称自动装配): 必须将目标 Bean 的名称和属性名设置的完全相同.
<bean id=address " class="com.atguiguspringbeans.autowireAddress"
	p:city="Be1jing" p:street="Huilongguan">
</bean>
<bean id="car" class="com.atguiguspringbeans.autowirecar" p:brand="Aud"
	p:price="300000></bean>
	
<bean id=" person "
	class="com.atguiguspringbeans.autowirePerson" p:name="Tom"
	p:address-ref=address " p:car-ref="car"></bean>
这里等价于	
<!-- 可以使用 autowire属性指定自动装配的方式，
byname根据bean的名字和当前bean的 setter风格的属性名进行自动装配，若有匹配的，则进行自动装配，若没有匹配的，则不装配 -->
<bean id="person" class="com.atguigu.spring.beans.autowire.Person" p:name="Tom"
	autowire="byname></bean>
	
constructor(通过构造器自动装配): 当 Bean 中存在多个构造器时, 此种自动装配方式将会很复杂. 不推荐使用

在实际的项目中很少使用自动装配功能:但是在整合第三方框架的时候有时候使用的是自动装配
在 Bean 配置文件里设置 autowire 属性进行自动装配将会装配 Bean 的所有属性. 然而, 若只希望装配个别属性时, autowire 属性就不够灵活了. 
autowire 属性要么根据类型自动装配, 要么根据名称自动装配, 不能两者兼而有之.

```

### bean 之间的关系：继承；依赖

Bean之间存在着这样，那样的关系，如继承，依赖。 我们需要告诉spring这些关系，并让spring帮我们做一些相关的操作(如：A类依赖于B类，那么B应该先A被创建，这样A才能应用，不至于空指针，因为对象的创建是spring IOC帮我们创建的，所以应该告诉它 ),spring Bean的配置是如何配置继承和依赖关系？

```java
Spring 允许继承 bean 的配置, 被继承的 bean 称为父 bean. 继承这个父 Bean 的 Bean 称为子 Bean

子 Bean 从父 Bean 中继承配置, 包括 Bean 的属性配置，也可以覆盖从父 Bean 继承过来的配置

父 Bean 可以作为配置模板, 也可以作为 Bean 实例. 若只想把父 Bean 作为模板, 可以设置 <bean> 的abstract 属性为 true, 这样 Spring 将不会实例化这个 Bean (他就是用来继承的，等同于抽象类)
并不是 <bean> 元素里的所有属性都会被继承. 比如: autowire, abstract 等.
也可以忽略父 Bean 的 class 属性, 让子 Bean 指定自己的类, 而共享相同的属性配置. 但此时 abstract 必须设为 true
<!-- 装配集合属性 -->
	<bean id="user" class="com.atguigu.spring.helloworld.User">
		<property name="userName" value="Jack"></property>
		<property name="cars">
			<!-- 使用 list 元素来装配集合属性 -->
			<list>
				<ref bean="car"/>
				<ref bean="car2"/>
			</list>
		</property>
	</bean>
	
     <!--这里的配置是继承上的配置-->
    <!-- bean 的配置能够继承吗 ? 使用 parent 来完成继承 -->	
	<bean id="user4" parent="user" p:userName="Bob"></bean>
	
依赖 Bean 配置:
Spring 允许用户通过 depends-on 属性设定 Bean 前置依赖的Bean，前置依赖的 Bean 会在本 Bean 实例化之前创建好
如果前置依赖于多个 Bean，则可以通过逗号，空格或的方式配置 Bean 的名称
<!-- 测试 depents-on -->	
	<bean id="user5" parent="user" p:userName="Backham" depends-on="user6"></bean>
```

### bean 的作用域：singleton；prototype；WEB 环境作用域

作用域 ： 作用范围

在这里理解成spring IOC 容器创建bean的各种方式(也可以理解成bean在IOC 容器中存活的生命周期)。 

bean 的创建是由spring IOC容器帮我们创建，但是我们对创建的bean有各种需求，就需要容易按照不同的方式帮我们创建。

![JAVAWEB_SPRING7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING7.png?raw=true)

```
默认情况下, Spring 只为每个在 IOC 容器里声明的 Bean 创建唯一一个实例, 整个 IOC 容器范围内都能共享该实例
(单例模式： 绝大多数情况下，我们都使用的是同一个对象，不需要重复创建。，浪费资源)

Spring 中, 可以在 <bean> 元素的 scope 属性里设置 Bean 的作用域. 当你希望每次IOC容器返回的Bean是同一个实例时，可以设置scope为singleton(单例模式创建对象，在IOC容器初始化的时候就创建对象)；当你希望每次IOC容器返回的Bean实例是一个新的实例时，可以设置scope为prototype(原型：在创建IOC容器的时候不会去创建对象，只有在bean被调用的时候才基于原型模式创建的一个新的对象)。

(其余的都是web下的环境作用域，对应着对应的web作用域。request该属性仅对HTTP请求产生作用，使用该属性定义Bean时，每次HTTP请求都会创建一个新的Bean，适用于WebApplicationContext环境。session该属性仅用于HTTP Session，同一个Session共享一个Bean实例。不同Session使用不同的实例。)

```



### 使用外部属性文件

在配置文件里配置 Bean 时, 有时需要在 Bean 的配置里混入系统部署的细节信息(例如: 文件路径, 数据源配置信息等). 而这些部署细节实际上需要和 Bean 配置相分离(解耦，方便替换)。 最常见的是jdbc.properties数据库连接池配置文件。

配置方式：

```java
jdbc.propertises 中：
jdbc.user=root
jdbc.password=1230
jdbc.driverClass=jdbc:mysql://10.64.14.115:3306/pro_test_0109?characterEncoding=UTF-8
jdbc.jdbcUrl=jdbc:mysql:///test

jdbc.initPoolSize=5
jdbc.maxPoolSize=10
 
(当我们需要修改数据库的时候，直接修改这个jdbc.propertises配置文件即可)    
    
bean.xml 配置文件中
<!-- 导入外部的资源文件 -->
	<context:property-placeholder location="classpath:db.properties"/>
	
	<!-- 配置数据源 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		
		<property name="initialPoolSize" value="${jdbc.initPoolSize}"></property>
		<property name="maxPoolSize" value="${jdbc.maxPoolSize}"></property>
	</bean>

Spring 提供了一个 PropertyPlaceholderConfigurer 的 BeanFactory 后置处理器, 这个处理器允许用户将 Bean 配置的部分内容外移到属性文件中. 可以在 Bean 配置文件里使用形式为 ${var} 的变量来引用属性配置文件中的值, PropertyPlaceholderConfigurer 从属性文件里加载属性, 并使用这些属性来替换变量.
Spring 还允许在属性文件中使用 ${propName}，以实现属性之间的相互引用。
```



### Spring表达式语言：SpEL

SpEL（Spring Expression Language），即Spring表达式语言，是比JSP的EL更强大的一种表达式语言。

是一个**支持运行时查询和操作对象图的强大的表达式语言**。

语法类似于 EL：SpEL 使用 #{…} 作为定界符，所有在大框号中的字符都将被认为是 SpEL
SpEL 为 bean 的属性进行**动态赋值**提供了便利

```
为什么需要SPEl?
如同jsp的EL, 我们在为bean属性进行赋值的时候，有时候需要进行动态赋值。这是后就需要SPEl 这个表达式语言

SpEL：字面量
字面量的表示：
整数：<property name="count" value="#{5}"/>
小数：<property name="frequency" value="#{89.7}"/>
科学计数法：<property name="capacity" value="#{1e4}"/>
String可以使用单引号或者双引号作为字符串的定界符号：<property name=“name” value="#{'Chuck'}"/> 或 <property name='name' value='#{"Chuck"}'/>
Boolean：<property name="enabled" value="#{false}"/>


```

SpEL：引用 Bean、属性和方法

![JAVAWEB_SPRING8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING8.png?raw=true)

调用静态方法或静态属性：通过 T() 调用一个类的静态方法，它将返回一个 Class Object，然后再调用相应的方法或属性： 

![JAVAWEB_SPRING11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING11.png?raw=true)

SpEL支持的运算符号

![JAVAWEB_SPRING9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING9.png?raw=true)

![JAVAWEB_SPRING10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING10.png?raw=true)

常用的spel

![JAVAWEB_SPRING12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING12.png?raw=true)

### IOC 容器中 Bean 的生命周期方法

Spring IOC 容器可以管理 Bean 的生命周期, Spring 允许在 Bean 生命周期的特定点执行定制的任务. 

在面对各种需求的时候，我们 可能需要在创建bean之前做一些事情，在创建bean之后做一些事情。

spring IOC 管理bean，为我们提供这样的机制

```java
Spring IOC 容器对 Bean 的生命周期进行管理的过程:
通过构造器或   工厂方法创建 Bean 实例
为 Bean 的属性设置值和对其他 Bean 的引用
*调用 Bean 的初始化方法
Bean 可以使用了
*当容器关闭时, 调用 Bean 的销毁方法

（这两个就是IOC容器管理生命周期的方法）
在 Bean 的声明里设置 init-method 和 destroy-method 属性, 为 Bean 指定初始化和销毁方法.

spring 还提供更进一步的定制bean的生命周期方法
Bean 后置处理器：实现接口BeanPostProcessor
Bean 后置处理器允许在调用初始化方法前后对 Bean 进行额外的处理.Bean 后置处理器对 IOC 容器里的所有 Bean 实例逐一处理(他会识别并处理所有的bean,进行统一的处理), 而非单一实例. 其典型应用是: 检查 Bean 属性的正确性或根据特定的标准更改 Bean 的属性.
我们需要在IOC容器中配置上我们的后置处理器，这样它才能被IOC容器管理    
<!-- 配置 bean 后置处理器: 不需要配置 id 属性, IOC 容器会识别到他是一个 bean 后置处理器(因为实现了BeanPostProcessor接口，IOC容器会根据他的反射知道这是一个bean的后置处理器), 并调用其方法 -->
	<bean class="com.atguigu.spring.ref.MyBeanPostProcessor"></bean>
	
public class MyBeanPostProcessor implements BeanPostProcessor {

	//该方法在 init 方法之后被调用
	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		if(arg1.equals("boy")){
			System.out.println("postProcessAfterInitialization..." + arg0 + "," + arg1);
			User user = (User) arg0;
			user.setUserName("李大齐");
		}
		return arg0;
	}

	//该方法在 init 方法之前被调用
	//可以工作返回的对象来决定最终返回给 getBean 方法的对象是哪一个, 属性值是什么
	/**
	 * @param arg0(bean): 实际要返回的对象 ，就是bean
	 * @param arg1(beanName): bean 的 id 值
	 * 返回值: 返回的是实际上要返回给用户的bean，可以在这两个方法中修改要返回的bean，甚至返回一个新的bean
	 */
	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		if(arg1.equals("boy"))
			System.out.println("postProcessBeforeInitialization..." + arg0 + "," + arg1);
		return arg0;
	}

}

	
添加 Bean 后置处理器后 Bean 的生命周期：
通过构造器或工厂方法创建 Bean 实例
为 Bean 的属性设置值和对其他 Bean 的引用
*将 Bean 实例传递给 Bean 后置处理器的 postProcessBeforeInitialization 方法
调用 Bean 的初始化方法
*将 Bean 实例传递给 Bean 后置处理器的 postProcessAfterInitialization方法
Bean 可以使用了
当容器关闭时, 调用 Bean 的销毁方法
```



### 泛型依赖注入

![JAVAWEB_SPRING15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING15.png?raw=true)

```java
//父类： 顶层接口
public class BaseService<T> {
	@Autowired
	private BaseDao<T> dao;	
	public void add(T entity){
		System.out.println("addNew by " + dao);
		dao.save(entity);
	}	
}

public class BaseDao<T> {

	public void save(T entity){
		System.out.println("Save:" + entity);
	}
	
}

//子类： 底层实现
@Service
public class UserService extends BaseService<User>{
}

@Repository
public class UserDao extends BaseDao<User>{

}
public class User {

}

// 配置文件中，配置组件扫描包即可

//应用
public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
		
		UserService userService = (UserService) ctx.getBean("userService");
		userService.addNew(new User());
		
		RoleService roleService = (RoleService) ctx.getBean("roleService");
		roleService.addNew(new Role()); 
}

由此可见，我们的引用关系是在父类建立的，引用的也是父类引用，但是在实际应用中，子类不用特殊声明也会建立这种引用关系，而且真正注入的是 T 对应的子类的类型。
（同样的这个机制也是为了进一步的简化注解，父类建立了这样的引用，在使用同样泛型的子类，我们同样也会建立这样的引用，不过IOC容器通过这个机制帮助我们简化了这一步操作）
```

### 整合多个配置文件

```
Spring 允许通过 <import> 将多个配置文件引入到一个文件中，进行配置文件的集成。这样在启动 Spring 容器时，仅需要指定这个合并好的配置文件就可以。
import 元素的 resource 属性支持 Spring 的标准的路径资源
```

![JAVAWEB_SPRING16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING16.png?raw=true)

## Spring AOP

面向切面编程

### 为什么需要AOP（面向切面编程）？

![JAVAWEB_SPRING17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING17.png?raw=true)

安装我们一般的传统实现方法，我们需要在每个方法的核心代码前后打印日志代码，和在这个前面对参数进行验证。

这样不仅代码繁琐，且耦合性强，而且每个方法所做的事情基本相同，代码重用性差。

这时我们就思考，能不能将要实现的功能抽离出来，让核心功能安心的做自己的事。

解决方案： AOP , 使用动态代理解决上述问题

![JAVAWEB_SPRING18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING18.png?raw=true)

```
代理设计模式的原理: 使用一个代理将对象包装起来, 然后用该代理对象取代原始对象. 任何对原始对象的调用都要通过代理. 代理对象决定是否以及何时将方法调用转到原始对象上.
```

开发中使用动态代理来实现成本比较高，spring 为我们提供了类似的AOP机制。

### 什么是AOP?

![JAVAWEB_SPRING19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING19.png?raw=true)

```java
AOP(Aspect-Oriented Programming, 面向切面编程),是对传统 OOP(Object-Oriented Programming, 面向对象编程) 的补充.AOP 的主要编程对象是切面(aspect).

AspectJ：Java 社区里最完整最流行的 AOP 框架.
两种方式： 
1： 基于xml配置
2： 基于注解的方式
```

#### 基于注解配置

```java
在 Spring 中启用 AspectJ 注解支持:
1:必须在 classpath 下包含 AspectJ 类库: aopalliance.jar、aspectj.weaver.jar 和 spring-aspects.jar
2:将 aop Schema 添加到 <beans> 根元素中，加入aop的命名空间，方便我们使用<aop>标签
3:只要在 Bean 配置文件中定义一个空的 XML 元素 <aop:aspectj-autoproxy>
基于注解的方式：
	<!-- 自动扫描的包 -->
	<context:component-scan base-package="com.atguigu.spring.aop"></context:component-scan>
	<!-- 使 AspectJ 的注解起作用 -->
     <!-- 配置自动为匹配 aspectJ 注解的 Java 类生成代理对象 -->
	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
4： 声明我们的注解类(包含各种前后置通知)	
	
用 AspectJ 注解声明切面，注解我们需要切入的功能：
在 Spring 中声明 AspectJ 切面, 只需要在 IOC 容器中将切面声明为 Bean 实例. 当在 Spring IOC 容器中初始化 AspectJ 切面之后, Spring IOC 容器就会为那些与 AspectJ 切面相匹配的 Bean 创建代理.

在 AspectJ 注解中, 切面只是一个带有 @Aspect 注解的 Java 类. 
通知是标注有某种注解的简单的 Java 方法.
AspectJ 支持 5 种类型的通知注解: 
@Before: 前置通知, 在方法执行之前执行
@After: 后置通知, 在方法执行之后执行 
@AfterRunning: 返回通知, 在方法返回结果之后执行
@AfterThrowing: 异常通知, 在方法抛出异常之后
@Around: 环绕通知, 围绕着方法执行


合并切入点表达式
在 AspectJ 中, 切入点表达式可以通过操作符 &&, ||, ! 结合起来. 
```

![JAVAWEB_SPRING20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING20.png?raw=true)

代理类代码

```java
/**
 * AOP 的 helloWorld
 * 1. 加入 jar 包
 * com.springsource.net.sf.cglib-2.2.0.jar
 * com.springsource.org.aopalliance-1.0.0.jar
 * com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
 * spring-aspects-4.0.0.RELEASE.jar
 * 
 * 2. 在 Spring 的配置文件中加入 aop 的命名空间。 
 * 
 * 3. 基于注解的方式来使用 AOP
 * 3.1 在配置文件中配置自动扫描的包: <context:component-scan base-package="com.atguigu.spring.aop"></context:component-scan>
 * 3.2 加入使 AspjectJ 注解起作用的配置: <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
 * 为匹配的类自动生成动态代理对象. 
 * 
 * 4. 编写切面类: 
 * 4.1 一个一般的 Java 类
 * 4.2 在其中添加要额外实现的功能. 
 *
 * 5. 配置切面
 * 5.1 切面必须是 IOC 中的 bean: 实际添加了 @Component 注解
 * 5.2 声明是一个切面: 添加 @Aspect
 * 5.3 声明通知: 即额外加入功能对应的方法. 
 * 5.3.1 前置通知: @Before("execution(public int com.atguigu.spring.aop.ArithmeticCalculator.*(int, int))")
 * @Before 表示在目标方法执行之前执行 @Before 标记的方法的方法体. 
 * @Before 里面的是切入点表达式: 
 * 
 * 6. 在通知中访问连接细节: 可以在通知方法中添加 JoinPoint 类型的参数, 从中可以访问到方法的签名和方法的参数. 
 * 
 * 7. @After 表示后置通知: 在方法执行之后执行的代码. 
 */

// 把这个类声明为切面：
// 分为两步：1 ：将该类放入到IOC容器中@Component， 2： 在将该类声明为一个切面
//通过添加 @Aspect 注解声明一个 bean 是一个切面!
@Aspect
@Component
public class LoggingAspect {

    // 告诉这个方法是在哪些类，哪些方法之前执行。@Before 声明改方法是一个前置通知，在方法执行之前执行的通知
	@Before("execution(public int com.atguigu.spring.aop.ArithmeticCalculator.*(int, int))")
	public void beforeMethod(JoinPoint joinPoint){
        //joinPoint 连接点， methodName 方法名，args参数
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		
		System.out.println("The method " + methodName + " begins with " + Arrays.asList(args));
	}
	
    //后置通知，目标方法执行后(无论是否发生异常)，都执行的通知
    // 在后置通知中，还不能范围目标方法执行的结果， 这个接口需要@AfterRunning: 返回通知中访问
	@After("execution(* com.atguigu.spring.aop.*.*(..))")
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method " + methodName + " ends");
	}
	
}

```

其他各种通知

```java
/**
 * 可以使用 @Order 注解指定切面的优先级, 值越小优先级越高
 */
@Order(2)
@Aspect
@Component
public class LoggingAspect {
	
	/**
	 * 定义一个方法, 用于声明切入点表达式. 一般地, 该方法中再不需要添入其他的代码. 
	 * 使用 @Pointcut 来声明切入点表达式，来重用表达式
	 * 后面的其他通知直接使用方法名来引用当前的切入点表达式. 
	 * 如果要早这个切面以外的切面使用，需要申明为
	 *同一包下 @Before("LoggingAspect.declareJointPointExpression()")
	 *不同包下	@Before("com.atguigu.spring.aop.LoggingAspect.declareJointPointExpression()")
	 *需带上包名
	 */
	@Pointcut("execution(public int com.atguigu.spring.aop.ArithmeticCalculator.*(..))")
	public void declareJointPointExpression(){}
	
	/**
	 * 在 com.atguigu.spring.aop.ArithmeticCalculator 接口的每一个实现类的每一个方法开始之前执行一段代码
	 */
	@Before("declareJointPointExpression()")
	public void beforeMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		
		System.out.println("The method " + methodName + " begins with " + Arrays.asList(args));
	}
	
	/**
	 * 在方法执行之后执行的代码. 无论该方法是否出现异常
	 */
	@After("declareJointPointExpression()")
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method " + methodName + " ends");
	}
	
	/**
	 * 在方法法正常结束受执行的代码
	 * 返回通知是可以访问到方法的返回值的!
	 */
	@AfterReturning(value="declareJointPointExpression()",
			returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method " + methodName + " ends with " + result);
	}
	
	/**
	 * 在目标方法出现异常时会执行的代码.
	 * 可以访问到异常对象; 且可以指定在出现特定异常时在执行通知代码
	 */
	@AfterThrowing(value="declareJointPointExpression()",
			throwing="e")
	public void afterThrowing(JoinPoint joinPoint, Exception e){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method " + methodName + " occurs excetion:" + e);
	}
	
	/**
	 * 环绕通知需要携带 ProceedingJoinPoint 类型的参数. 
	 * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.
	 * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
	 * 环绕通知，功能最强，但是并不常用
	 */
	/*
	@Around("execution(public int com.atguigu.spring.aop.ArithmeticCalculator.*(..))")
	public Object aroundMethod(ProceedingJoinPoint pjd){
		
		Object result = null;
		String methodName = pjd.getSignature().getName();
		
		try {
			//前置通知
			System.out.println("The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
			//执行目标方法
			result = pjd.proceed();
			//返回通知
			System.out.println("The method " + methodName + " ends with " + result);
		} catch (Throwable e) {
			//异常通知
			System.out.println("The method " + methodName + " occurs exception:" + e);
			throw new RuntimeException(e);
		}
		//后置通知
		System.out.println("The method " + methodName + " ends");
		
		return result;
	}
	*/
}

他们对应着代理类中的各个位置
//返回代理对象
	public ArithmeticCalculator getLoggingProxy(){
		ArithmeticCalculator proxy = null;
		
		ClassLoader loader = target.getClass().getClassLoader();
		Class [] interfaces = new Class[]{ArithmeticCalculator.class};
		InvocationHandler h = new InvocationHandler() {
			/**
			 * proxy: 代理对象。 一般不使用该对象
			 * method: 正在被调用的方法
			 * args: 调用方法传入的参数
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				String methodName = method.getName();
				//打印日志
				System.out.println("[before] The method " + methodName + " begins with " + Arrays.asList(args));
				
				//调用目标方法
				Object result = null;
				
				try {
					//前置通知
					result = method.invoke(target, args);
					//返回通知, 可以访问到方法的返回值
				} catch (NullPointerException e) {
					e.printStackTrace();
					//异常通知, 可以访问到方法出现的异常
				}
				
				//后置通知. 因为方法可以能会出异常, 所以访问不到方法的返回值
				
				//打印日志
				System.out.println("[after] The method ends with " + result);
				
				return result;
			}
		};
		
		/**
		 * loader: 代理对象使用的类加载器。 
		 * interfaces: 指定代理对象的类型. 即代理代理对象中可以有哪些方法. 
		 * h: 当具体调用代理对象的方法时, 应该如何进行响应, 实际上就是调用 InvocationHandler 的 invoke 方法
		 */
		proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader, interfaces, h);
		
		return proxy;
	}
```

#### 基于xml配置

```java
正常情况下, 基于注解的声明要优先于基于 XML 的声明. (基于 XML 的配置则是 Spring 专有的)
    
在 Bean 配置文件中, 所有的 Spring AOP 配置都必须定义在 <aop:config> 元素内部. 对于每个切面而言, 都要创建一个 <aop:aspect> 元素来为具体的切面实现引用后端 Bean 实例


	<!-- 配置需要加切面的实例 bean -->
	<bean id="arithmeticCalculator" 
		class="com.atguigu.spring.aop.xml.ArithmeticCalculatorImpl"></bean>

	<!-- 配置切面的 bean. -->
	<bean id="loggingAspect"
		class="com.atguigu.spring.aop.xml.LoggingAspect"></bean>

	<bean id="vlidationAspect"
		class="com.atguigu.spring.aop.xml.VlidationAspect"></bean>

	<!-- 配置 AOP -->
	<aop:config>
		<!-- 配置切点表达式 -->
		<aop:pointcut expression="execution(* com.atguigu.spring.aop.xml.ArithmeticCalculator.*(int, int))" id="pointcut"/>
		
		<!-- 配置切面及通知 -->
		<aop:aspect ref="loggingAspect" order="2">
			<aop:before method="beforeMethod" pointcut-ref="pointcut"/>
			<aop:after method="afterMethod" pointcut-ref="pointcut"/>
			<aop:after-throwing method="afterThrowing" pointcut-ref="pointcut" throwing="e"/>
			<aop:after-returning method="afterReturning" pointcut-ref="pointcut" returning="result"/>
			<!--  
			<aop:around method="aroundMethod" pointcut-ref="pointcut"/>
			-->
		</aop:aspect>	
		<aop:aspect ref="vlidationAspect" order="1">
			<aop:before method="validateArgs" pointcut-ref="pointcut"/>
		</aop:aspect>
	</aop:config>
```

## Spring 对 JDBC 的支持

为了使 JDBC 更加易于使用, Spring 在 JDBC API 上定义了一个抽象层, 以此建立一个 JDBC 存取框架.

### JdbcTemplate

![JAVAWEB_SPRING21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING21.png?raw=true)

```java
作为 Spring JDBC 框架的核心, JDBC 模板的设计目的是为不同类型的 JDBC 操作提供模板方法. 每个模板方法都能控制整个过程, 并允许覆盖过程中的特定任务. 通过这种方式, 可以在尽可能保留灵活性的情况下, 将数据库存取的工作量降到最低.
```

JdbcTemplate使用

1：配置数据源

```java
<!-- 导入资源文件 -->
	<context:property-placeholder location="classpath:db.properties"/>
	
	<!-- 配置 C3P0 数据源 -->
	<bean id="dataSource"
		class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		<property name="driverClass" value="${jdbc.driverClass}"></property>

		<property name="initialPoolSize" value="${jdbc.initPoolSize}"></property>
		<property name="maxPoolSize" value="${jdbc.maxPoolSize}"></property>
	</bean>
	
	<!-- 配置 Spirng 的 JdbcTemplate -->
	<bean id="jdbcTemplate" 
		class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
```

代码使用

```java
public class JDBCTest {
	
	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate;
	private EmployeeDao employeeDao;
	private DepartmentDao departmentDao;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
		employeeDao = ctx.getBean(EmployeeDao.class);
		departmentDao = ctx.getBean(DepartmentDao.class);
		namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
	}
    
    // 简单试用
    @Test
	public void testDataSource() throws SQLException {
        //判断注入是否成功
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}
    
    
    	/**
	 * 执行 INSERT, UPDATE, DELETE
	 */
	@Test
	public void testUpdate(){
		String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
		jdbcTemplate.update(sql, "Jack", 5);
	}
	
    
    	/**
	 * 执行批量更新: 批量的 INSERT, UPDATE, DELETE
	 * 最后一个参数是 Object[] 的 List 类型: 因为修改一条记录需要一个 Object 的数组, 那么多条不就需要多个 Object 的数组吗
	 */
	@Test
	public void testBatchUpdate(){
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(?,?,?)";
		
		List<Object[]> batchArgs = new ArrayList<>();
		
		batchArgs.add(new Object[]{"AA", "aa@atguigu.com", 1});
		batchArgs.add(new Object[]{"BB", "bb@atguigu.com", 2});
		batchArgs.add(new Object[]{"CC", "cc@atguigu.com", 3});
		batchArgs.add(new Object[]{"DD", "dd@atguigu.com", 3});
		batchArgs.add(new Object[]{"EE", "ee@atguigu.com", 2});
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
    	/**
	 * 从数据库中获取一条记录, 实际得到对应的一个对象
	 * 注意不是调用 queryForObject(String sql, Class<Employee> requiredType, Object... args) 方法!
	 * 这个方法是返回指定requiredType类型的字段
	 * 而需要调用 queryForObject(String sql, RowMapper<Employee> rowMapper, Object... args)
	 * 1. 其中的 RowMapper 指定如何去映射结果集的行, 常用的实现类为 BeanPropertyRowMapper
	 * 2. 使用 SQL 中列的别名完成列名和类的属性名的映射. 例如 last_name lastName
	 * 3. 不支持级联属性. JdbcTemplate 到底是一个 JDBC 的小工具, 而不是 ORM 框架
	 */
	@Test
	public void testQueryForObject(){
		String sql = "SELECT id, last_name lastName, email, dept_id as \"department.id\" FROM employees WHERE id = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		
		System.out.println(employee);
	}

    
    /**
	 * 查到实体类的集合
	 * 注意调用的不是 queryForList 方法
	 */
	@Test
	public void testQueryForList(){
		String sql = "SELECT id, last_name lastName, email FROM employees WHERE id > ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		List<Employee> employees = jdbcTemplate.query(sql, rowMapper,5);
		
		System.out.println(employees);
	}
	
    /**
	 * 获取单个列的值, 或做统计查询
	 * 使用 queryForObject(String sql, Class<Long> requiredType) 
	 */
	@Test
	public void testQueryForObject2(){
		String sql = "SELECT count(id) FROM employees";
		long count = jdbcTemplate.queryForObject(sql, Long.class);
		
		System.out.println(count);
	}

  在 JDBC 模板中使用具名参数：
  在 Spring JDBC 框架中, 绑定 SQL 参数的另一种选择是使用具名参数(named parameter). 
具名参数: SQL 按名称(以冒号开头)而不是按位置进行指定. 具名参数更易于维护, 也提升了可读性. 具名参数由框架类在运行时用占位符取代
具名参数只在 NamedParameterJdbcTemplate 中得到支持 
  他需要在xml配置NamedParameterJdbcTemplate
    
<!-- 配置 NamedParameterJdbcTemplate, 该对象可以使用具名参数, 其没有无参数的构造器, 所以必须为其构造器指定参数 -->
	<bean id="namedParameterJdbcTemplate"
		class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
		<constructor-arg ref="dataSource"></constructor-arg>	
	</bean>
                       
	/**
	 * 可以为参数起名字. 
	 * 1. 好处: 若有多个参数, 则不用再去对应位置, 直接对应参数名, 便于维护
	 * 2. 缺点: 较为麻烦. 
	 */
	@Test
	public void testNamedParameterJdbcTemplate(){
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:ln,:email,:deptid)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ln", "FF");
		paramMap.put("email", "ff@atguigu.com");
		paramMap.put("deptid", 2);
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}
	
    
    	/**
	 * 使用具名参数时, 可以使用 update(String sql, SqlParameterSource paramSource) 方法进行更新操作
	 * 1. SQL 语句中的参数名和类的属性一致! (这个要严格遵守，程序是通过这个对对应的属性进行赋值的)
	 * 2. 使用 SqlParameterSource 的 BeanPropertySqlParameterSource 实现类作为参数. 
	 * 直接传入一个对象进来简化操作
	 */
	@Test
	public void testNamedParameterJdbcTemplate2(){
		String sql = "INSERT INTO employees(last_name, email, dept_id) "
				+ "VALUES(:lastName,:email,:dpetId)";
		
		Employee employee = new Employee();
		employee.setLastName("XYZ");
		employee.setEmail("xyz@sina.com");
		employee.setDpetId(3);
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
		namedParameterJdbcTemplate.update(sql, paramSource);
	}

}


web 常用的形式是注入到service中
每次使用都创建一个 JdbcTemplate 的新实例, 这种做法效率很低下.JdbcTemplate 类被设计成为线程安全的, 所以可以再 IOC 容器中声明它的单个实例, 并将这个实例注入到所有的 DAO 实例中.


@Repository
public class EmployeeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Employee get(Integer id){
		String sql = "SELECT id, last_name lastName, email FROM employees WHERE id = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, id);
		
		return employee;
	}
}
```

### Spring中的事务管理

什么是事务？

```java
事务就是一系列的动作, 它们被当做一个单独的工作单元. 这些动作要么全部完成, 要么全部不起作用
事务管理是用来确保数据的完整性和一致性. 
```

在jdbc中我们操作事务

![JAVAWEB_SPRING22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING22.png?raw=true)

从图中我们可以发现jdbc操作事务和我们AOP的各个通知十分类似。

#### 声明式事务管理

```java
Spring 在不同的事务管理 API 之上定义了一个抽象层TransactionManager (从不同的事务管理 API 中抽象了一整套的事务机制. 开发人员不必了解底层的事务 API（处理hibernate，处理JPA的事务都是同一套机制）, 就可以利用这些事务机制. 有了这些事务机制, 事务管理代码就能独立于特定的事务技术了). 让我们更好的使用事务


声明式事务管理:
将事务管理代码从业务方法中分离出来, 以声明的方式来实现事务管理. 事务管理作为一种横切关注点, 可以通过 AOP 方法模块化. Spring 通过 Spring AOP 框架支持声明式事务管理.
    
    
如何加上事务？(基于注解的方式)
1： 配置事务管理器
<!-- 1. 配置事务管理器 -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	
2： 启用事务注解
	<!-- 启用事务注解 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>

3： 在我们需要添加事务的代码上加上注解@Transactional
    @Transactional
	public void purchase(String username, String isbn) {
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}
		
		//1. 获取书的单价
		int price = bookShopDao.findBookPriceByIsbn(isbn);
		
		//2. 更新数的库存
		bookShopDao.updateBookStock(isbn);
		
		//3. 更新用户余额
		bookShopDao.updateUserAccount(username, price);
	}

除了基本的事务以外，事务还有很多属性： 事务的传播行为 ， 事务的隔离级别， 对那些异常进行回滚，事务的过期时间
这些都可以在    @Transactional 注解中配置

propagation事务的传播行为：一个事务方法被另一个事务方法调度用的时候，被调用的事务方法在事务中如何工作。
(当一个事务方法调用另外一个事务方法的时候，第一个事务方法已经在一个事务中， 那么在这时候有第二个事务方法是用第一个事务的事务了，还是新开一个事务了。 这就可以类比我们去饭馆吃饭碰到熟人了， 是一起吃了，还是新开一桌)
常用的传播行为就是REQUIRED(一起吃)，REQUIRES_NEW(新开一个桌)
默认的传播行为就是 REQUIRED. (用同一个事务)
 
具体的使用：    
@Transactional
	@Override
	// 买多本书
	public void checkout(String username, List<String> isbns) {
		for(String isbn: isbns){
			bookShopService.purchase(username, isbn);
		}
	}
	
    @Transactional
// 买书方法
	public void purchase(String username, String isbn) {
        //。。。。。。
    }
这时候一个事务方法checkout()已经去调用了另外一个事务方法purchase()
这时候我们需要在被调用的事务方法上指定事务传播行为
	//添加事务注解
	//1.使用 propagation 指定事务的传播行为, 即当前的事务方法被另外一个事务方法调用时
	//如何使用事务, 默认取值为 REQUIRED, 即使用调用方法的事务
	@Transactional(propagation=Propagation.REQUIRED)
	public void purchase(String username, String isbn) {.....}
  (因为指定的事务传播行为是两者都属于同一个事务，所以，如果购买其中一本书出现了异常，那么所有购买都会回滚)

如果我们指定传播欣慰是开启一个新的事务。
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void purchase(String username, String isbn) {。。。}
(那么这是如果我们购买的其中一本书出现了异常，也只会有一个回滚)
```

事务具体的传播行为：

![JAVAWEB_SPRING23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING23.png?raw=true)

![JAVAWEB_SPRING24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_SPRING24.png?raw=true)

**事务的隔离级别**

```java
在spring声明式事务中如何指定我们的事务隔离级别来避免脏读，可重复读等

	//使用 isolation 指定事务的隔离级别, 最常用的取值为 READ_COMMITTED
	@Transactional(propagation=Propagation.REQUIRES_NEW,
			isolation=Isolation.READ_COMMITTED)
	@Override
	public void purchase(String username, String isbn) {。。。。}

```

**对那些异常进行回滚**

```java
@Transactional 中有属性可以指定，对那些异常进行回滚，对哪些异常不进行回滚
默认情况下 Spring 的声明式事务对所有的运行时异常进行回滚. 也可以通过对应的属性进行设置. 通常情况下去默认值即可. 
但是我们有时候会存在一些不想回滚的异常noRollbackFor，和只想回滚的异常 rollbackFor
    	//使用 isolation 指定事务的隔离级别, 最常用的取值为 READ_COMMITTED
	@Transactional(noRollbackFor={UserAccountException.class})
	public void purchase(String username, String isbn) {。。。。}

```

 **readOnly**

```java
使用 readOnly 指定事务是否为只读. 表示这个事务只读取数据但不更新数据, 这样可以帮助数据库引擎优化事务. 若真的事一个只读取数据库值的方法, 应设置 readOnly=true
   	//使用 isolation 指定事务的隔离级别, 最常用的取值为 READ_COMMITTED
	@Transactional(noRollbackFor={UserAccountException.class},readOnly=true)
	public void purchase(String username, String isbn) {。。。。}
```

**事务的过期时间**

```java
使用 timeout 指定事务方法在强制回滚之前最多可以占用的时间.  可以防止一个事务对连接占用时间过长，超过时间强制回滚。以提高这个运行=性能，单位是秒。 
    	@Transactional(propagation=Propagation.REQUIRES_NEW,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			timeout=3)
	@Override
	public void purchase(String username, String isbn) {....}
```



#### 使用xml的形式来配置事务

```java
<!-- 1. 配置事务管理器 ,使用数据库层面的操作第一步都是要配置事务管理器-->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	
	<!-- 2. 配置事务属性 -->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<!-- 根据方法名指定事务的属性 -->
			<tx:method name="purchase" propagation="REQUIRES_NEW"/>
			<tx:method name="get*" read-only="true"/>
			<tx:method name="find*" read-only="true"/>
			<tx:method name="*"/>
		</tx:attributes>
	</tx:advice>
	
	<!-- 3. 配置事务切入点(XML配置事务的原理就是AOP，所以配置形式类似), 以及把事务切入点和事务属性关联起来 -->
	<aop:config>
	<!--事务切入点,事务应该在哪个类的那个方法上起作用-->
		<aop:pointcut expression="execution(* com.atguigu.spring.tx.xml.service.*.*(..))" 
			id="txPointCut"/>
		<!--把事务切入点和事务属性关联起来-->
		<aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>	
	</aop:config>
```



## Spring  整合 Hibernate

spring整合hibernate整合什么？

```java
整合两方面
1：由spring IOC 容器生成Hibernate的SessionFactory
2：让hibernate使用上spring的声明式事务
```

整合步骤

```java
2. 整合步骤:

1). 加入 hibernate
①. jar 包
②. 添加 hibernate 的配置文件: hibernate.cfg.xml
③. 编写了持久化类对应的 .hbm.xml 文件。 
<hibernate-configuration>
    <session-factory>
    
    	<!-- 配置 hibernate 的基本属性 -->
    	<!-- 1. 数据源需配置到 IOC 容器中, 所以在此处不再需要配置数据源 -->
    	<!-- 2. 关联的 .hbm.xml 也在 IOC 容器配置 SessionFactory 实例时在进行配置 -->
    	<!-- 3. 配置 hibernate 的基本属性: 方言, SQL 显示及格式化, 生成数据表的策略以及二级缓存等. -->
    	<property name="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</property>

		<property name="hibernate.show_sql">true</property>
		<property name="hibernate.format_sql">true</property>
		
		<property name="hibernate.hbm2ddl.auto">update</property>
		
		<!-- 配置 hibernate 二级缓存相关的属性. -->
		    	
    </session-factory>
</hibernate-configuration>

2). 加入 Spring
①. jar 包
②. 加入 Spring 的配置文件
如果在 Spring IOC 容器中配置数据源. 可以将该数据源注入到 LocalSessionFactoryBean 的 dataSource 属性中. 该属性指定的数据源会覆盖掉 Hibernate 配置文件里的数据库配置.（所以数据源配置在spring中）

可以在 LocalSessionFactoryBean 的 mappingResources 属性中指定 XML 映射文件的位置.该属性为 String[] 类型. 因此可以指定一组映射文件.

<!-- 配置自动扫描的包 -->
	<context:component-scan base-package="com.atguigu.spring.hibernate"></context:component-scan>
	
	<!-- 配置数据源 -->
	<!-- 导入资源文件 -->
	<context:property-placeholder location="classpath:db.properties"/>
	
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>

		<property name="initialPoolSize" value="${jdbc.initPoolSize}"></property>
		<property name="maxPoolSize" value="${jdbc.maxPoolSize}"></property>
	</bean>
	
	<!-- 配置 Hibernate 的 SessionFactory 实例: 通过 Spring 提供的 LocalSessionFactoryBean 进行配置 。 这个配置好后在启动spring IOC容器的时候会帮我们生成 SessionFactory 实例。并帮助我们生成对应的表
	-->
	<bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
		<!-- 配置数据源属性 -->
		<property name="dataSource" ref="dataSource"></property>
		<!-- 配置 hibernate 配置文件的位置及名称 -->
		<!--  
		<property name="configLocation" value="classpath:hibernate.cfg.xml"></property>
		-->
		<!-- 使用 hibernateProperties 属相来配置 Hibernate 原生的属性 -->
		<property name="hibernateProperties">
			<props>
				<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
				<prop key="hibernate.show_sql">true</prop>
				<prop key="hibernate.format_sql">true</prop>
				<prop key="hibernate.hbm2ddl.auto">update</prop>
			</props>
		</property>
		<!-- 配置 hibernate 映射文件的位置及名称, 可以使用通配符 -->
		<property name="mappingLocations" 
			value="classpath:com/atguigu/spring/hibernate/entities/*.hbm.xml"></property>
	</bean>

	<!-- 配置 Spring 的声明式事务 -->
	<!-- 1. 配置事务管理器 -->
	<bean id="transactionManager" class="org.springframework.orm.hibernate4.HibernateTransactionManager">
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>

	<!-- 2. 配置事务属性, 需要事务管理器 -->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="get*" read-only="true"/>
			<tx:method name="purchase" propagation="REQUIRES_NEW"/>
			<tx:method name="*"/>
		</tx:attributes>
	</tx:advice>

	<!-- 3. 配置事务切点, 并把切点和事务属性关联起来 -->
	<aop:config>
		<aop:pointcut expression="execution(* com.atguigu.spring.hibernate.service.*.*(..))" 
			id="txPointcut"/>
		<aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
	</aop:config>

3). 整合.
        
Dao 中使用hibernate
@Repository
public class BookShopDaoImpl implements BookShopDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Spring hibernate 事务的流程
	 * 1. 在方法开始之前
	 * ①. 获取 Session
	 * ②. 把 Session 和当前线程绑定, 这样就可以在 Dao 中使用 SessionFactory 的
	 * getCurrentSession() 方法来获取 Session 了
	 * ③. 开启事务
	 * 
	 * 2. 若方法正常结束, 即没有出现异常, 则
	 * ①. 提交事务
	 * ②. 使和当前线程绑定的 Session 解除绑定
	 * ③. 关闭 Session
	 * 
	 * 3. 若方法出现异常, 则:
	 * ①. 回滚事务
	 * ②. 使和当前线程绑定的 Session 解除绑定
	 * ③. 关闭 Session
	 */
	//获取和当前线程绑定的 Session. 
	private Session getSession(){
	//我们之所以可以在这里使用sessionFactory.getCurrentSession() 获取session。是和spring的事务有关。
这里调方法实际上是在事务方法，在事务开始之前，获取session，将当前session和当前线程绑定起来。开始事务。在这个方法结束，如果没有异常则提交事务关闭session。hibernate的方法都在事务中执行。
		return sessionFactory.getCurrentSession();
	}

	@Override
	public int findBookPriceByIsbn(String isbn) {
		String hql = "SELECT b.price FROM Book b WHERE b.isbn = ?";
		Query query = getSession().createQuery(hql).setString(0, isbn);
		return (Integer)query.uniqueResult();
	}

	@Override
	public void updateBookStock(String isbn) {
		//验证书的库存是否充足. 
		String hql2 = "SELECT b.stock FROM Book b WHERE b.isbn = ?";
		int stock = (int) getSession().createQuery(hql2).setString(0, isbn).uniqueResult();
		if(stock == 0){
			throw new BookStockException("库存不足!");
		}
		
		String hql = "UPDATE Book b SET b.stock = b.stock - 1 WHERE b.isbn = ?";
		getSession().createQuery(hql).setString(0, isbn).executeUpdate();
	}
}	
```

## spring 如何在web中使用

```java
1. Spring 如何在 WEB 应用中使用 ?

1). 需要额外加入的 jar 包:

spring-web-4.0.0.RELEASE.jar
spring-webmvc-4.0.0.RELEASE.jar

2). Spring 的配置文件, 没有什么不同

3). 如何创建 IOC 容器 ? 

①. 非 WEB 应用在 main 方法中直接创建
②. 应该在 WEB 应用被服务器加载时就创建 IOC 容器: 

在 ServletContextListener#contextInitialized(ServletContextEvent sce) 方法中创建 IOC 容器.

③. 在 WEB 应用的其他组件中如何来访问 IOC 容器呢 ?

在 ServletContextListener#contextInitialized(ServletContextEvent sce) 方法中创建 IOC 容器后, 可以把其放在
ServletContext(即 application 域)的一个属性中. 
    public class SpringServletContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SpringServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	//1. 获取 Spring 配置文件的名称. 
    	ServletContext servletContext = arg0.getServletContext();
    	String config = servletContext.getInitParameter("configLocation");
    	
    	//1. 创建 IOC 容器
    	ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
    	
    	//2. 把 IOC 容器放在 ServletContext 的一个属性中. 
    	servletContext.setAttribute("ApplicationContext", ctx);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}

④. 实际上, Spring 配置文件的名字和位置应该也是可配置的! 将其配置到当前 WEB 应用的初始化参数中较为合适. 
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <context-param>
    <param-name>configLocation</param-name>
    <param-value>applicationContext.xml</param-value>
  </context-param>
  <listener>
    <listener-class>com.atguigu.spring.struts2.listeners.SpringServletContextListener</listener-class>
  </listener>
  <servlet>
    <description></description>
    <display-name>TestServlet</display-name>
    <servlet-name>TestServlet</servlet-name>
    <servlet-class>com.atguigu.spring.struts2.servlets.TestServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>TestServlet</servlet-name>
    <url-pattern>/TestServlet</url-pattern>
  </servlet-mapping>
</web-app>

4). 在 WEB 环境下使用 Spring

①. 需要额外加入的 jar 包:

spring-web-4.0.0.RELEASE.jar
spring-webmvc-4.0.0.RELEASE.jar

②. Spring 的配置文件, 和非 WEB 环境没有什么不同

③. 需要在 web.xml 文件中加入如下配置:

<!-- 配置 Spring 配置文件的名称和位置 -->
<context-param>
	<param-name>contextConfigLocation</param-name>
	<param-value>classpath:applicationContext.xml</param-value>
</context-param>

<!-- 启动 IOC 容器的 ServletContextListener -->
<listener>
    <!--这里和我们自己的实现类似-->
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

2. Spring 如何整合 Struts2 ?

1). 整合目标 ? 使 IOC 容器来管理 Struts2 的 Action!

2). 如何进行整合 ? 

①. 正常加入 Struts2

②. 在 Spring 的 IOC 容器中配置 Struts2 的 Action
注意: 在 IOC 容器中配置 Struts2 的 Action 时, 需要配置 scope 属性, 其值必须为 prototype

<bean id="personAction" 
	class="com.atguigu.spring.struts2.actions.PersonAction"
	scope="prototype">
	<property name="personService" ref="personService"></property>	
</bean>

③. 配置 Struts2 的配置文件: action 节点的 class 属性需要指向 IOC 容器中该 bean 的 id

<action name="person-save" class="personAction">
	<result>/success.jsp</result>
</action> 

④. 加入 struts2-spring-plugin-2.3.15.3.jar

3). 整合原理: 通过添加 struts2-spring-plugin-2.3.15.3.jar 以后, Struts2 会先从 IOC 容器中
获取 Action 的实例.

if (appContext.containsBean(beanName)) {
    o = appContext.getBean(beanName);
} else {
    Class beanClazz = getClassInstance(beanName);
    o = buildBean(beanClazz, extraContext);
}
```

