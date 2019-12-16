# Spring注解驱动开发

spring注解： 在spring基础框架上解放配置(以前需要配置在配置文件中的内容，现在一个注解就搞定)

注解的核心本质是java类，是动态代理，是spring的后置处理器

```java
如：@controller注解源码
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	String value() default "";

}
```



```
深入了解Spring原理机制
SpringBoot、SpringCloud作为Spring之上的框架，他们大量使用到了Spring的一些底层注解、原理
比如@Conditional、@Import、@EnableXXX等。掌握这些底层原理、注解，那么我们对这些高层框架就能做到高度定制，使用的游刃有余；
1）、Spring的常用注解（@ComponentScan、@Bean、@Configuration、@Conditional、@Import、@PropertySource、@Profile等）
2）、Spring的原理；
	1）、后置处理器（BeanPostProcessor、BeanFactoryPostProcessor等）
	2）、监听器（ApplicationListener）
	3）、Spring容器启动过程；
3）、web原理
	1）、Servlet3.0标准新增特性
	2）、异步请求相关
```

# spring容器相关注解

```java
spring的底层，他的重要特性就是IOC(DI)，他认为所有的组件都应该放在IOC容器中管理。 组件之间的装配(依赖关系)都应该通过容器进行依赖注入，自动装配。

/**
	 * 给容器中注册组件的几种方式；
	 * 1）、包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）[自己写的类]
	 * 2）、@Bean[导入的第三方包里面的组件]
	 * 3）、@Import[快速给容器中导入一个组件]
	 * 		1）、@Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
	 * 		2）、ImportSelector(接口):返回需要导入的组件的全类名数组； (springBoot中使用的较多)
	 * 		3）、ImportBeanDefinitionRegistrar:手动注册bean到容器中
	 * 4）、使用Spring提供的 FactoryBean（工厂Bean）;
	 * 		1）、默认获取到的是工厂bean调用getObject创建的对象
	 * 		2）、要获取工厂Bean本身，我们需要给id前面加一个&
	 * 			&colorFactoryBean
	 */
```

## 注解方式实现bean的注入

### 单个bean的注入

使用纯注解的方式来完成容器的组件的注册，管理及注入。

以前需要使用在xml中进行手动配置的bean，现在全部使用注解来进行注册。(配置文件能干的事，注解都能干 )

```java
以前需要手动注解bean
<bean id="person" class="com.atguigu.bean.Person"></bean>
代码获取
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		Person bean = (Person) applicationContext.getBean("person");
		System.out.println(bean);

使用注解式开发
使用配置类替代配置文件 （配置类多久等同以前的配置文件）
//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
public class MainConfig {
    
	//在配置类中注册一个组件
	//给容器中注册一个Bean;类型为返回值的类型对应以前配置文件中的class，id默认是用方法名作为id
	//这里的@Bean 对应的配置文件中的一个bean标签
	@Bean("person")
	public Person person01(){
		return new Person("lisi", 20);
	}

}

java代码中获取
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		Person bean = applicationContext.getBean(Person.class);
		System.out.println(bean);

```

#### @Scope设置组件作用域

```java
spring IOC容器中的实例，默认是单实例的，始终都是注入的同一个对象实例。但是有时候我们希望每次获取的都是不同的，就需要设置注入的作用域
以前需要手动注解bean的作用域，在bean标签中scope="prototype"
<bean id="person" class="com.atguigu.bean.Person"  scope="prototype" ></bean>

使用注解式开发
@Configuration
public class MainConfig2 {
	
	//默认是单实例的
	/**
	 * ConfigurableBeanFactory#SCOPE_PROTOTYPE    
	 * @see ConfigurableBeanFactory#SCOPE_SINGLETON  
	 * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
	 * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION	 sesssion
	 * @return\
	 * @Scope:调整作用域
	 * prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中。
	 * 					  每次获取的时候才会调用方法创建对象；每获取一次，创建一次
	 * singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
	 * 			以后每次获取就是直接从容器（map.get()）中拿，
	 * request：同一次请求创建一个实例  (需要web环境)
	 * session：同一个session创建一个实例 (需要web环境)
	 * 
	 */
	@Scope("prototype") // 等同于在上面bean标签中配置的  scope="prototype"，如果是单实例，默认不用写
	@Bean("person")
	public Person person(){
		System.out.println("给容器中添加Person....");
		return new Person("张三", 25);
	}
}
```

#### bean懒加载

```java
懒加载是针对bean单实例来说
单实例bean：默认在容器启动的时候创建对象；
懒加载：容器启动不创建对象。第一次使用(获取)Bean创建对象，并初始化；
//	@Scope("prototype")
	@Lazy //@Lazy不需要@Scope注解，因为懒加载只是专门针对单实例来说的
	@Bean("person")
	public Person person(){
		System.out.println("给容器中添加Person....");
		return new Person("张三", 25);
	}
```

### 自动扫描组件

```java
以前在配置文件中的包扫描
<!-- 包扫描、只要标注了@Controller、@Service、@Repository，@Component 都会被自动扫描注册
use-default-filters="false" 禁用掉默认的扫描规则，使用这个之后才能使用includeFilters
-->
<context:component-scan base-package="com.atguigu" ></context:component-scan>

注解式开发
以前是在配置文件中写component-scan，现在配置文件变成了配置类，现在需要在配置类上写component-scan。对应着一个@ComponentScans注解
//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类

@ComponentScans(
		value = {
				@ComponentScan(value="com.atguigu",includeFilters = {
						@Filter(type=FilterType.ANNOTATION,classes={Controller.class}),
						@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={BookService.class}),
						@Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
				},useDefaultFilters = false)	
		}
		)
//@ComponentScan  value:指定要扫描的包
//excludeFilters = Filter[] ：指定扫描的时候按照什么规则排除那些组件
//includeFilters = Filter[] ：指定扫描的时候只需要包含哪些组件
//FilterType.ANNOTATION：按照注解
//FilterType.ASSIGNABLE_TYPE：按照给定的类型；
//FilterType.ASPECTJ：使用ASPECTJ表达式 ( 不常用)
//FilterType.REGEX：使用正则指定 ( 不常用)
//FilterType.CUSTOM：使用自定义规则
public class MainConfig {
}
```

FilterType.CUSTOM：使用自定义规则

```java
/** Filter candidates using a given custom
	 * {@link org.springframework.core.type.filter.TypeFilter} implementation.
	 * 给定的自定义一规则必须是TypeFilter 的一个实现类
	 */
	CUSTOM

//自定义的过滤规则
public class MyTypeFilter implements TypeFilter {

	/**
	 * metadataReader：读取到的当前正在扫描的类的信息
	 * metadataReaderFactory:可以获取到其他任何类信息的
	 */
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		// TODO Auto-generated method stub
		//获取当前类注解的信息
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		//获取当前正在扫描的类的类信息
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		//获取当前类资源（类的路径）
		Resource resource = metadataReader.getResource();
		
		String className = classMetadata.getClassName();
		System.out.println("--->"+className);
		if(className.contains("er")){
		//自定义规则，满足规则则返回true，将其注册到IOC容器中
			return true;
		}
		return false;
	}

}

```

#### 当注解代码中属性为数组时

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScans {
	ComponentScan[] value();
}

ComponentScan[]  这是个数组
@ComponentScans(
		value = {
				@ComponentScan(value="com.atguigu")
		       }
		)

在注解到代码中的时候需要使用{ } 来包含其中的内容，多个元素值用逗号分隔
Filter[] includeFilters() default {};

includeFilters = {
						@Filter(type=FilterType.ANNOTATION,classes={Controller.class}),
						@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={BookService.class}),
						@Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
				}
```



### 按照条件注册bean

@Conditional 注解

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {

	/**
	 * All {@link Condition}s that must {@linkplain Condition#matches match}
	 * in order for the component to be registered.
	 */
	Class<? extends Condition>[] value();
    // value 是个数组，且是实现了Condition接口的类的class的数组
}

public interface Condition {
	boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
}

==============================================================================================
@Configuration
public class MainConfig2 {
/**
	 * @Conditional({Condition}) ： 按照一定的条件进行判断，满足条件给容器中注册bean
	 * 
	 * 如果系统是windows，给容器中注册("bill")
	 * 如果是linux系统，给容器中注册("linus")
	 */
	
	@Bean("bill")
	public Person person01(){
		return new Person("Bill Gates",62);
	}
	
	@Conditional(LinuxCondition.class)
	@Bean("linus")
	public Person person02(){
		return new Person("linus", 48);
	}
    
}

//实现Condition接口的实现类
//判断是否linux系统
public class LinuxCondition implements Condition {

	/**
	 * ConditionContext：判断条件能使用的上下文（环境）
	 * AnnotatedTypeMetadata：注释信息
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// TODO是否linux系统
		//1、能获取到ioc使用的beanfactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//2、获取类加载器
		ClassLoader classLoader = context.getClassLoader();
		//3、获取当前环境信息
		Environment environment = context.getEnvironment();
		//4、获取到bean定义的注册类 (可以获取,移除 到注册的bean)
		BeanDefinitionRegistry registry = context.getRegistry();
		
		String property = environment.getProperty("os.name");
		
		//可以判断容器中的bean注册情况，也可以给容器中注册bean
        //判断是否存在注册的person bean
		boolean definition = registry.containsBeanDefinition("person");
		if(property.contains("linux")){
			return true;
		}
		
		return false;
	}

}

//判断是否windows系统
public class WindowsCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		String property = environment.getProperty("os.name");
		if(property.contains("Windows")){
			return true;
		}
		return false;
	}

}
```

@Conditional可以注解在方法上，也可以注解现在类上(对类进行统一配置)

```java
//类中组件统一设置。满足当前条件，这个类中配置的所有bean注册才能生效；
@Conditional({WindowsCondition.class})
@Configuration
public class MainConfig2 {
}
```

@Conditional({XX.class}) 会在创建当前bean之前拦截判断，如果true则注册在IOC容器中，否则不注册在IOC容器中

### @Import快速导入组件

```java
	 * 3）、@Import[快速给容器中导入一个组件]
	 * 		1）、@Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
	 * 		2）、ImportSelector(接口):返回需要导入的组件的全类名数组； (springBoot中使用的较多)
	 * 		3）、ImportBeanDefinitionRegistrar:手动注册bean到容器中
@Configuration
@Import({Color.class,Red.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
//@Import导入组件，id默认是组件的全类名
//@Import 直接导入
public class MainConfig2 {
}

ImportSelector： 接口：
public interface ImportSelector {
	//返回需要导入的组件的全类名数组
	String[] selectImports(AnnotationMetadata importingClassMetadata);

}

//自定义逻辑返回需要导入的组件,他也需要被@Import 导入
public class MyImportSelector implements ImportSelector {

	//返回值，就是到导入到容器中的组件全类名
	//AnnotationMetadata:当前标注@Import注解的类的所有注解信息
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		//方法不要返回null值
		return new String[]{"com.atguigu.bean.Blue","com.atguigu.bean.Yellow"};
	}

}

ImportBeanDefinitionRegistrar (接口)：手动注册bean到容器中
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * AnnotationMetadata：当前类的注解信息
	 * BeanDefinitionRegistry:BeanDefinition注册类；
	 * 		把所有需要添加到容器中的bean；调用
	 * 		BeanDefinitionRegistry.registerBeanDefinition手工注册进来
	 */
	@Override
public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		boolean definition = registry.containsBeanDefinition("com.atguigu.bean.Red");
		boolean definition2 = registry.containsBeanDefinition("com.atguigu.bean.Blue");
		if(definition && definition2){
			//指定Bean定义信息；（Bean的类型，Bean。。。）
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
			//注册一个Bean，指定bean名
			registry.registerBeanDefinition("rainBow", beanDefinition);
		}
	}

}
```

### FactoryBean注册组件

```java
 * 4）、使用Spring提供的 FactoryBean（工厂Bean）;
	 * 		1）、默认获取到的是工厂bean调用getObject创建的对象
	 * 		2）、要获取工厂Bean本身，我们需要给id前面加一个&+工厂id名
	 * 		====>	&colorFactoryBean
spring与其他框架的整合这种注册组件的方式使用的特别多	 

1://创建一个Spring定义的FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {

	//返回一个Color对象，这个对象会添加到容器中
	@Override
	public Color getObject() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ColorFactoryBean...getObject...");
		return new Color();
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Color.class;
	}

	//是单例？
	//true：这个bean是单实例，在容器中保存一份
	//false：多实例，每次获取都会创建一个新的bean；
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}

2:注册FactoryBean 到配置中
@Configuration
public class MainConfig2 {
  	@Bean
	public ColorFactoryBean colorFactoryBean(){
		return new ColorFactoryBean();
	}  
}

//虽然在IOC容器中获取到的bean id 是colorFactoryBean ，但是实际上根据这个id 获取到的对象是ColorFactoryBean getObject()生产的对象。
```

## Bean的生命周期

```java
/**
 * bean的生命周期：
 * 		bean创建---初始化----销毁的过程
 * 容器管理bean的生命周期；
 * 我们可以自定义初始化和销毁方法；容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
 * 
 * 构造（对象创建）
 * 		单实例：在容器启动的时候创建对象
 * 		多实例：在每次获取的时候创建对象
 * 
 * BeanPostProcessor.postProcessBeforeInitialization
 * 初始化：
 * 		对象创建完成，并赋值好，调用初始化方法。。。
 * BeanPostProcessor.postProcessAfterInitialization
 * 销毁：
 * 		单实例：容器关闭的时候
 * 		多实例：容器不会管理这个bean；容器不会调用销毁方法；
 * 
 * 
 * 1）、指定初始化和销毁方法；
 *  <bean id="person" class="com.atguigu.bean.Person" init-method="" destroy-method="" ></bean>    
 * 		通过@Bean指定init-method和destroy-method；
 * 2）、通过让Bean实现InitializingBean（定义初始化逻辑），
 * 				  DisposableBean（定义销毁逻辑）;
 * 3）、可以使用JSR250(java规范的注解)；
 * 		@PostConstruct：在bean创建完成并且属性赋值完成；来执行初始化方法
 * 		@PreDestroy：在容器销毁bean之前通知我们进行清理工作
 * 4）、BeanPostProcessor【interface】：bean的后置处理器；
 * 		在bean初始化前后进行一些处理工作；
 * 		postProcessBeforeInitialization:在初始化之前工作
 * 		postProcessAfterInitialization:在初始化之后工作
 *
 */
1:
@ComponentScan("com.atguigu.bean")
@Configuration
public class MainConfigOfLifeCycle {
    
	@Bean(initMethod="init",destroyMethod="detory")
	public Car car(){
		return new Car();
	}

}

2:
@Component
public class Cat implements InitializingBean,DisposableBean {
	@Override
	public void destroy() throws Exception {
		// 销毁方法
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化方法， 在BeanFactory 创建完bean后 完成调用
	}
}

3:
@Component
public class Dog implements ApplicationContextAware {	
	public Dog(){
		System.out.println("dog constructor...");
	}
	
	//对象创建并赋值之后调用
	@PostConstruct
	public void init(){
		System.out.println("Dog....@PostConstruct...");
	}
	
	//容器移除对象之前
	@PreDestroy
	public void detory(){
		System.out.println("Dog....@PreDestroy...");
	}	

}

4：
/**
 * 后置处理器：初始化前后进行处理工作
 * 将后置处理器加入到容器中
 * @author lfy
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("postProcessBeforeInitialization..."+beanName+"=>"+bean);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("postProcessAfterInitialization..."+beanName+"=>"+bean);
		return bean;
	}

}
```

BeanPostProcessor原理

```java
  BeanPostProcessor原理
  ==========原理代码===============================
  populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
  initializeBean
 {
      applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
      invokeInitMethods(beanName, wrappedBean, mbd);执行自定义初始化
      applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 }
  ==========原理代码===============================
 applyBeanPostProcessorsBeforeInitialization遍历得到容器中所有的BeanPostProcessor；挨个执行beforeInitialization，一但返回null，跳出for循环，不会执行后面的BeanPostProcessor.postProcessorsBeforeInitialization

Spring底层对 BeanPostProcessor 的使用；
bean赋值，注入其他组件，@Autowired，生命周期注解功能，@Async,xxx BeanPostProcessor;
```



## 属性赋值

```java
1：使用@Value赋值；
public class Person {
	
	//使用@Value赋值；
	//1、基本数值
	//2、可以写SpEL； #{}
	//3、可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）
	
	@Value("张三")
	private String name;
	@Value("#{20-2}")
	private Integer age;
	
	@Value("${person.nickName}")
	private String nickName;
	// 要使配置文件为这个赋值，xml配置的步骤需要保留，需要先导入配置===》@PropertySource
	//这里出来使用@Value取出配置文件中的值，还可以
	   ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String property = environment.getProperty("person.nickName");
	//在代码中取
	
}
=====================================
person.properties：
person.nickName=XXXX
====================================

配置类：
//使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
@PropertySource(value={"classpath:/person.properties"})
@Configuration
public class MainConfigOfPropertyValues {
	
	@Bean
	public Person person(){
		return new Person();
	}

}
```



## 自动装配

```java
/**
 * 自动装配;
 * 		Spring利用依赖注入（DI），完成对IOC容器中中各个组件的依赖关系赋值；
 * 
 * 1）、@Autowired：自动注入：[spring规范的注解]
 * 		1）、默认优先按照类型去容器中找对应的组件:applicationContext.getBean(BookDao.class);找到就赋值
 * 		2）、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
 *           自动扫描包会将扫描到的类的首字母小写作为id
 * 							applicationContext.getBean("bookDao")
 * 		3）、@Qualifier("bookDao")：使用@Qualifier指定需要装配的组件的id，而不是使用属性名
 * 		4）、自动装配默认一定要将属性赋值好，没有就会报错；
 * 			可以使用@Autowired(required=false);
 * 		5）、@Primary：让Spring进行自动装配的时候，默认使用首选的bean；
 * 		也可以继续使用@Qualifier指定需要装配的bean的名字,不过已经制定了，就不需要首选了，
 *       所以优先级@Qualifier>@Primary
 * 		BookService{
 * 			@Autowired
 * 			BookDao  bookDao;
 * 		}
 * 
 * 2）、Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]
 * 		@Resource:
 * 			可以和@Autowired一样实现自动装配功能；默认是按照组件名称进行装配的；
 * 			没有能支持@Primary功能没有支持@Autowired（reqiured=false）;
 * 		@Inject:
 * 			需要导入javax.inject的包，和Autowired的功能一样。没有required=false的功能；
 *  @Autowired:Spring定义的； @Resource、@Inject都是java规范. 推荐使用@Autowired
 * 	
 * AutowiredAnnotationBeanPostProcessor:解析完成自动装配功能；		
 * 
 * 3）、 @Autowired:构造器，参数，方法，属性；都是从容器中获取参数组件的值
 * 		1）、[标注在方法位置]：@Bean+方法参数；参数从容器中获取;默认不写@Autowired效果是一样的；都能自动装配
 * 		2）、[标在构造器上]：如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
 * 		3）、放在参数位置：
 * 
 * 4）、如果想要自定义组件想要使用Spring容器底层的一些组件（ApplicationContext，BeanFactory，xxx）；
 * 		值需要让自定义组件实现xxxAware；在创建对象的时候，会调用接口规定的方法注入相关组件；Aware；
 * 		把Spring底层一些组件注入到自定义的Bean中；
 * 		xxxAware：功能使用xxxProcessor；
 * 			ApplicationContextAware==》ApplicationContextAwareProcessor；
 * 		
 * @author lfy
 *
 */
@Configuration
@ComponentScan({"com.atguigu.service","com.atguigu.dao",
	"com.atguigu.controller","com.atguigu.bean"})
public class MainConifgOfAutowired {
	
	/**
	 * @Bean标注的方法创建对象的时候，方法参数的值从容器中获取
	 * @param car
	 * @return
	 */
	@Bean
	public Color color(Car car){
		Color color = new Color();
		color.setCar(car);
		return color;
	}	
}

//默认加在ioc容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
@Component
public class Boss {	
	private Car car;
	//构造器要用的组件，都是从容器中获取
	public Boss(Car car){
		this.car = car;
		System.out.println("Boss...有参构造器");
	}	

	public Car getCar() {
		return car;
	}

	//@Autowired 
	//标注在方法，Spring容器创建当前对象，就会调用方法，完成赋值；
	//方法使用的参数，自定义类型的值从ioc容器中获取
	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "Boss [car=" + car + "]";
	}	
	
}

```

### @Profile环境搭建

```java
/**
 * Profile：
 * 		Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能；
 * 
 * 开发环境、测试环境、生产环境；
 * 数据源：(/A)(/B)(/C)；
 * 
 * 
 * @Profile：指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件
 * 
 * 1）、加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中。默认是default环境
 * 2）、@Profile还可以写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效
 * 3）、没有标注环境标识的bean在，任何环境下都是加载的；
 */

@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware{
	
	@Value("${db.user}")
	private String user;
	
	private StringValueResolver valueResolver;
	
	private String  driverClass;
	
	
	@Profile("test")
	@Bean("testDataSource")
	public DataSource dataSourceTest(@Value("${db.password}")String pwd) throws Exception{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		dataSource.setDriverClass(driverClass);
		return dataSource;
	}
	
	
	@Profile("dev")
	@Bean("devDataSource")
	public DataSource dataSourceDev(@Value("${db.password}")String pwd) throws Exception{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ssm_crud");
		dataSource.setDriverClass(driverClass);
		return dataSource;
	}
	
	@Profile("prod")
	@Bean("prodDataSource")
	public DataSource dataSourceProd(@Value("${db.password}")String pwd) throws Exception{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/scw_0515");
		
		dataSource.setDriverClass(driverClass);
		return dataSource;
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		// TODO Auto-generated method stub
		this.valueResolver = resolver;
		driverClass = valueResolver.resolveStringValue("${db.driverClass}");
	}

}

```

根据环境注册bean

```java
@Profile("prod") 制定特定的环境注册对应的配置
切换配置环境
	//1、使用命令行动态参数: 在虚拟机参数位置加载 -Dspring.profiles.active=test
	//2、代码的方式激活某种环境； (不能使用AnnotationConfigApplicationContext的有参构造器，会自动注册，激活默认环境)
	@Test
	public void test01(){
		AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext();
		//1、创建一个applicationContext
		//2、设置需要激活的环境
		applicationContext.getEnvironment().setActiveProfiles("dev");
		//3、注册主配置类
		applicationContext.register(MainConfigOfProfile.class);
		//4、启动刷新容器
		applicationContext.refresh();
		
		
		String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
		for (String string : namesForType) {
			System.out.println(string);
		}
		
		Yellow bean = applicationContext.getBean(Yellow.class);
		System.out.println(bean);
		applicationContext.close();
	}
```



## AOP原理

AOP：【本质动态代理，最后会生成一个动态代理类】

指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式；

实现一个AOP:

```java
1:定义一个业务逻辑类（MathCalculator）；在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常，xxx）
public class MathCalculator {	
	public int div(int i,int j){
		System.out.println("MathCalculator...div...");
		return i/j;	
	}

}
2:定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行；
 * 		通知方法：
 * 			前置通知(@Before)：logStart：在目标方法(div)运行之前运行
 * 			后置通知(@After)：logEnd：在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
 * 			返回通知(@AfterReturning)：logReturn：在目标方法(div)正常返回之后运行
 * 			异常通知(@AfterThrowing)：logException：在目标方法(div)出现异常以后运行
 * 			环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.procced()）
 /**
 * 切面类
 * @author lfy
 * 
 * @Aspect： 告诉Spring当前类是一个切面类
 *
 */
@Aspect
public class LogAspects {
	
	//抽取公共的切入点表达式
	//1、本类引用
	//2、其他的切面引用
	@Pointcut("execution(public int com.atguigu.aop.MathCalculator.*(..))")
	public void pointCut(){};
	
	//@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
	@Before("pointCut()")
	public void logStart(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		System.out.println(""+joinPoint.getSignature().getName()+"运行。。。@Before:参数列表是：{"+Arrays.asList(args)+"}");
	}
	
	@After("com.atguigu.aop.LogAspects.pointCut()")
	public void logEnd(JoinPoint joinPoint){
		System.out.println(""+joinPoint.getSignature().getName()+"结束。。。@After");
	}
	
	//JoinPoint一定要出现在参数表的第一位
	@AfterReturning(value="pointCut()",returning="result")
	public void logReturn(JoinPoint joinPoint,Object result){
		System.out.println(""+joinPoint.getSignature().getName()+"正常返回。。。@AfterReturning:运行结果：{"+result+"}");
	}
	
	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(JoinPoint joinPoint,Exception exception){
		System.out.println(""+joinPoint.getSignature().getName()+"异常。。。异常信息：{"+exception+"}");
	}

}
3:给切面类的目标方法标注何时何地运行（通知注解）；@Before @After...
4:将切面类和业务逻辑类（目标方法所在类）都加入到容器中;
5:必须告诉Spring哪个类是切面类(给切面类上加一个注解：@Aspect)
6:给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
等同于在xml中配置
	<!-- 开启基于注解版的切面功能 -->
	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
在Spring中很多的 @EnableXXX;  注解
==================================================

@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {
//业务逻辑类加入容器中
	@Bean
	public MathCalculator calculator(){
		return new MathCalculator();
	}

	//切面类加入到容器中
	@Bean
	public LogAspects logAspects(){
		return new LogAspects();
	}
}

注意： 要想让AOP 生效，我们不能自己new 业务逻辑类，需要让其被IOC容器管理。从IOC容器中获取

总结 三步：
 * 	1）、将业务逻辑组件和切面类都加入到容器中；告诉Spring哪个是切面类（@Aspect）
 * 	2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
 *  3）、开启基于注解的aop模式；@EnableAspectJAutoProxy
```

AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】

```java
AOP中主要就是@EnableAspectJAutoProxy；
分析：
 * 1、@EnableAspectJAutoProxy是什么？
 * 		@Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar
 * 			利用AspectJAutoProxyRegistrar自定义给容器中注册bean；BeanDefinetion
 * 			internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 * 
 * 		给容器中注册一个AnnotationAwareAspectJAutoProxyCreator；
 * 
 * 2、 AnnotationAwareAspectJAutoProxyCreator：
 * 		AnnotationAwareAspectJAutoProxyCreator
 * 			->AspectJAwareAdvisorAutoProxyCreator
 * 				->AbstractAdvisorAutoProxyCreator
 * 					->AbstractAutoProxyCreator
 * 						implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 						关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 * 
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator.有后置处理器的逻辑；
 * 
 * AbstractAdvisorAutoProxyCreator.setBeanFactory()重写了setBeanFactory()-》调用initBeanFactory()
 * 
 * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory() 又重写了父类的调用initBeanFactory()
 * 
 ==============以下是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程==================
 流程：
1）、传入配置类，创建ioc容器
2）、注册配置类，调用refresh（）刷新容器；
3）、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建；
4）、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作；创建剩下的单实例bean

后置处理器的注册逻辑：
3.1）、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor (创建IOC容器的时候需要传入配置类，在配置类中会配置需要注册的后置处理器，@EnableAspectJAutoProxy 中会为容器注入AnnotationAwareAspectJAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor
还有一些默认的后置处理器
)
    
3.2）、给容器中加别的BeanPostProcessor
3.3）、优先注册实现了PriorityOrdered接口的BeanPostProcessor；
3.4）、再给容器中注册实现了Ordered接口的BeanPostProcessor；
3.5）、注册没实现优先级接口的BeanPostProcessor；
3.6）、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中；


3.6.0）创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
3.6.1）、创建Bean的实例
3.6.2）、populateBean；给bean的各种属性赋值
3.6.3）、initializeBean：初始化bean；

initializeBean中调用invokeAwareMethods
3.6.3.1）、invokeAwareMethods()：处理Aware接口的方法回调
调用invokeAwareMethods()
实现了 Aware ==》setBeanFactory()   
3.6.3.1.1）、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功；--》aspectJAdvisorsBuilder 

3.6.3.2）、applyBeanPostProcessorsBeforeInitialization()：
应用后置处理器的postProcessBeforeInitialization（）
3.6.3.3）、invokeInitMethods()；执行自定义的初始化方法
3.6.3.4）、applyBeanPostProcessorsAfterInitialization()；
执行后置处理器的postProcessAfterInitialization（）；


3.7）、把BeanPostProcessor注册到BeanFactory中；
beanFactory.addBeanPostProcessor(postProcessor);
以后其他组件在创建实例的时候，就可以使用后缀处理器拦截到bean的创建过程

=======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程========
    
AnnotationAwareAspectJAutoProxyCreator 作为后置处理器接下来都做了些什么？
AnnotationAwareAspectJAutoProxyCreator的执行时机？

AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor

====================================
创建bean会被 拦截
AnnotationAwareAspectJAutoProxyCreator是InstantiationAwareBeanPostProcessor类型的处理器
【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，会调用InstantiationAwareBeanPostProcessor的postProcessBeforeInstantiation()】
和其他后置处理器不一样，
AnnotationAwareAspectJAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor
SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;
}


public interface BeanPostProcessor {
	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}

一般的后置处理器，初始化前后执行的是postProcessBeforeInitialization，postProcessAfterInitialization
AnnotationAwareAspectJAutoProxyCreator执行的是postProcessBeforeInstantiation
=====================================

4）、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作；创建剩下的单实例bean

==================================完成BeanFactory初始化工作流程===================================
完成BeanFactory初始化工作流程：
4.1）、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
getBean->doGetBean()->getSingleton()->createBean()
4.2）、getSingleton()先从单实例缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建；只要创建好的Bean都会被缓存起来
4.3）、createBean();真正的创建bean；
4.3.1）、resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续 4.3.2
    
后置处理器先尝试返回对象；
//拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor;就执行postProcessBeforeInstantiation
//AnnotationAwareAspectJAutoProxyCreator 就是InstantiationAwareBeanPostProcessor类型的处理器
//InstantiationAwareBeanPostProcessor是在 创建Bean实例之前先尝试用后置处理器返回对象的
// AnnotationAwareAspectJAutoProxyCreator 会在任何 bean创建之前 先尝试返回bean的实例
bean = applyBeanPostProcessorsBeforeInstantiation（）：
if (bean != null) {
	bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
}

4.3.2）、doCreateBean(beanName, mbdToUse, args);真正的去创建一个bean实例；和3.6流程一样；

总结：
 AnnotationAwareAspectJAutoProxyCreator 会在任何 bean创建之前 先尝试返回bean的实例
【BeanPostProcessor是在Bean对象  创建完成初始化前后  调用的】
【InstantiationAwareBeanPostProcessor是在 创建Bean实例之前先尝试用后置处理器返回对象的】
【AnnotationAwareAspectJAutoProxyCreator会在所有bean创建之前会有一个拦截，因为他是InstantiationAwareBeanPostProcessor的后置处理器，会在拦截钱调用postProcessBeforeInstantiation()】
		
======================================完成BeanFactory初始化工作流程================================

AnnotationAwareAspectJAutoProxyCreator在调用 applyBeanPostProcessorsBeforeInstantiation时做了什么？  
======applyBeanPostProcessorsBeforeInstantiation返回代理对象==========>
public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)  {
	Object cacheKey = getCacheKey(beanClass, beanName);
	if (beanName == null || !this.targetSourcedBeans.contains(beanName)) {
			if (this.advisedBeans.containsKey(cacheKey)) {
				return null;
			}
			if (isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)) {
				this.advisedBeans.put(cacheKey, Boolean.FALSE);
				return null;
			}
	}

// Create proxy here if we have a custom TargetSource.
// Suppresses unnecessary default instantiation of the target bean:
// The TargetSource will handle target instances in a custom fashion.
if (beanName != null) {
 	TargetSource targetSource = getCustomTargetSource(beanClass, beanName);
	if (targetSource != null) {
		this.targetSourcedBeans.add(beanName);
Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
Object proxy = createProxy(beanClass, beanName, specificInterceptors, targetSource);
         this.proxyTypes.put(cacheKey, proxy.getClass());
         return proxy;
	 	}
	}

	return null;
	}

AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】	的作用：
1: 每一个bean创建之前，调用postProcessBeforeInstantiation()；

1）、判断当前bean是否在advisedBeans中（保存了所有需要增强bean: 如AOP代理中的MathCalculator，需要加入切面的bean）
2）、判断当前bean是否是基础类型
是否实现接口的Advice、Pointcut、Advisor、AopInfrastructureBean的bean，或者是否是切面（@Aspect）

3）、判断是否需要跳过 (不要创建代理类bean)
3.1）、获取候选的增强器（切面里面的通知方法）【List<Advisor> candidateAdvisors】
每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor；
判断每一个增强器是否是 AspectJPointcutAdvisor 类型的；返回true
3.2）、永远返回false

2、创建对象(需要增强bean: 如AOP代理中的MathCalculator )
    
3. 创建完对象后调用 postProcessAfterInitialization；

return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
1）、获取当前bean的所有增强器（通知方法）  Object[]  specificInterceptors
	1、找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）
	2、获取到能在bean使用的增强器。
	3、给增强器排序
2）、保存当前bean在advisedBeans中；
3）、如果当前bean需要增强，创建当前bean的代理对象；
	1）、获取所有增强器（通知方法）
	2）、保存到proxyFactory
3）、创建代理对象：Spring自动决定
				JdkDynamicAopProxy(config);jdk动态代理；
			ObjenesisCglibAopProxy(config);cglib的动态代理；
4）、给容器中返回当前组件使用cglib增强了的代理对象；
5）、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程；
		
======applyBeanPostProcessorsBeforeInstantiation返回代理对象======>


应用了AOP切面的业务增强目标方法的执行流程：

目标方法执行	；
容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象，xxx）；
1）、CglibAopProxy.intercept();拦截目标方法的执行
2）、根据ProxyFactory对象获取将要执行的目标方法拦截器链；
List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
拦截器链怎么获取
   1）、List<Object> interceptorList保存所有拦截器 5
	一个默认的ExposeInvocationInterceptor 和 4个我们字节定义的增强器；
    2）、遍历所有的增强器，判断是切入点的增强其，将其转为Interceptor；
 	registry.getInterceptors(advisor);
    3）、将增强器转为我们需要使用的List<MethodInterceptor>；
  		如果是MethodInterceptor，直接加入到集合中
         如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor； 
		转换完成返回MethodInterceptor数组；
3）、如果没有拦截器链，直接执行目标方法;
	拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
4）、如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等信息传入创建一个 CglibMethodInvocation 对象，并调用 Object retVal =  mi.proceed();

5）、拦截器链的触发过程;
     1)、如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）执行目标方法；
     2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行；
		拦截器链的机制，保证通知方法与目标方法的执行顺序；
(见下图)
=================================总结AOP=======================================
总结：
1）、 @EnableAspectJAutoProxy 开启AOP功能
2）、 @EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
3）、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器；
4）、容器的创建流程：
   4.1）、registerBeanPostProcessors（）注册后置处理器；创建AnnotationAwareAspectJAutoProxyCreator对象
   4.2）、finishBeanFactoryInitialization（）初始化剩下的单实例bean
      4.2.1）、创建业务逻辑组件和切面组件
      4.2.2）、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 	  4.2.3）、组件创建完之后，判断组件是否需要增强
             是：切面的通知方法，包装成增强器（Advisor）;给业务逻辑组件创建一个代理对象（cglib）；
5）、执行目标方法：
  5.1）、代理对象执行目标方法
			5.2）、CglibAopProxy.intercept()；
			5.2.1）、得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
			5.2.2）、利用拦截器的链式机制，依次进入每一个拦截器进行执行；
			5.2.3）、效果：
 					正常执行：前置通知-》目标方法-》后置通知-》返回通知
      				出现异常：前置通知-》目标方法-》后置通知-》异常通知
```

![JAVA_ANNOTATION1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_ANNOTATION1.png?raw=true)

## 声明式事务

就是spring用注解简化我们的事务开发

```java
/**
 * 声明式事务：
 * 
 * 环境搭建：
 * 1、导入相关依赖
 * 		数据源、数据库驱动、Spring-jdbc模块
 * 2、配置数据源、JdbcTemplate（Spring提供的简化数据库操作的工具）操作数据
 * 3、给方法上标注 @Transactional 表示当前方法是一个事务方法；
 * 4、 @EnableTransactionManagement 开启基于注解的事务管理功能；
 * 		@EnableXXX
 * 5、配置事务管理器来控制事务;
 * 		@Bean
 * 		public PlatformTransactionManager transactionManager()
 * 
 * 
 * 原理：
 * 1）、@EnableTransactionManagement
 * 			利用TransactionManagementConfigurationSelector给容器中会导两个入组件
 * 			AutoProxyRegistrar
 * 			ProxyTransactionManagementConfiguration
 * 2）、AutoProxyRegistrar：
 * 			给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；
 * 			InfrastructureAdvisorAutoProxyCreator：？
 利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；
 * 
 * 3）、ProxyTransactionManagementConfiguration 是个配置类 做了什么？
 * 			1、给容器中注册事务增强器；
 * 				1）、事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
 * 				2）、事务拦截器：
 * 					TransactionInterceptor；保存了事务属性信息，事务管理器；
 * 					他是一个 MethodInterceptor；
 * 					在目标方法执行的时候；
 * 						执行拦截器链；
 * 						事务拦截器：
 * 							1）、先获取事务相关的属性
 * 							2）、再获取PlatformTransactionManager，如果事先没有添加指定任何
                               transactionmanger最终会从容器中按照类型获取一个
                               PlatformTransactionManager；
 * 								
 * 							3）、执行目标方法
 * 								如果异常，获取到事务管理器，利用事务管理器回滚操作；
 * 								如果正常，利用事务管理器，提交事务
 * 			
 */
@EnableTransactionManagement
@ComponentScan("com.atguigu.tx")
@Configuration
public class TxConfig {
	
	//数据源
	@Bean
	public DataSource dataSource() throws Exception{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser("root");
		dataSource.setPassword("123456");
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		return dataSource;
	}
	
	// 也可以直接从容器中拿组件public JdbcTemplate jdbcTemplate(DataSource dataSource)
	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception{
		//Spring对@Configuration类会特殊处理；给容器中加组件的方法，多次调用都只是从容器中找组件
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}
	
	//注册事务管理器在容器中
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception{
		return new DataSourceTransactionManager(dataSource());
	}
	

}

使用申明式事务 ： 只需要在service方法上加入@Transactional 注解和在配置文件中开启事务，
<tx:annotation-driven/>  现在只需要在配置类上加上@EnableTransactionManagement 注解即可

	@Transactional
	public void insertUser(){
		userDao.insert();
		//otherDao.other();xxx
		System.out.println("插入完成...");
		int i = 10/0;
	}

```

# spring扩展原理

```java
  扩展原理：
  BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作的
  
  1、BeanFactoryPostProcessor：beanFactory的后置处理器；
  		在BeanFactory标准初始化之后调用，来定制和修改BeanFactory的内容；
  什么是标准初始化： 所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建
  
  
  BeanFactoryPostProcessor原理:
  1)、ioc容器创建对象
  2)、invokeBeanFactoryPostProcessors(beanFactory);
  		如何找到所有的BeanFactoryPostProcessor并执行他们的方法；
  			1）、直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
  			2）、在finishBeanFactoryInitialization(beanFactory)初始化创建其他组件前面执行
  
  2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
  		postProcessBeanDefinitionRegistry();
  		在所有bean定义信息《将要被加载》，bean实例还未创建的；
  		说明他的执行是优先于BeanFactoryPostProcessor执行；
  
        BeanDefinitionRegistry Bean定义信息的保存中心，
        以后BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例
        (我们可以在其中添加bean的定义信息)
         RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
		AbstractBeanDefinition beanDefinition = 
            BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();
         //上面两种方式任选其一来定义bean的定义信息
		registry.registerBeanDefinition("hello", beanDefinition);
  		利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件；
  
  	原理：
  		1）、ioc创建对象
  		2）、refresh()-》invokeBeanFactoryPostProcessors(beanFactory);
  		3）、从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件。
  			1、依次触发所有的postProcessBeanDefinitionRegistry()方法
  			2、再来触发postProcessBeanFactory()方法BeanFactoryPostProcessor；
  
  		4）、再来从容器中找到BeanFactoryPostProcessor组件；然后依次触发postProcessBeanFactory()方法
  	
```



## 监听器

```java
 3、ApplicationListener：监听容器中发布的事件。事件驱动模型开发；
  	  public interface ApplicationListener<E extends ApplicationEvent>
  		监听 ApplicationEvent 及其下面的子事件；
  
  	 事件步骤：
  		1）、写一个监听器（ApplicationListener实现类）来监听某个事件（ApplicationEvent及其子类）
  			@EventListener;
  			原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener；
  
  		2）、把监听器加入到容器；
  		3）、只要容器中有相关事件的发布，我们就能监听到这个事件；
  				ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件；
  				ContextClosedEvent：关闭容器会发布这个事件；
  		4）、自己发布一个事件：
  				applicationContext.publishEvent()；
  				
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

	//当容器中发布此事件以后，方法触发
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		System.out.println("收到事件："+event);
	}

}  				

使用@EventListener; 注解我们可以在任何地方都监听事件
@Service
public class UserService {
     //class指定监听哪些事件
	@EventListener(classes={ApplicationEvent.class})
	public void listen(ApplicationEvent event){
		System.out.println("UserService。。监听到的事件："+event);
	}

}


   监听器原理：
   	ContextRefreshedEvent、IOCTest_Ext$1[source=我发布的时间]、ContextClosedEvent；
   1）、ContextRefreshedEvent事件：
   	1）、容器创建对象：refresh()；
   	2）、finishRefresh();容器刷新完成会发布ContextRefreshedEvent事件
   2）、自己发布事件；
   3）、容器关闭会发布ContextClosedEvent；
   
 【事件发布流程】：
   	3）、publishEvent(new ContextRefreshedEvent(this));
   			1）、获取事件的多播器（派发器）：getApplicationEventMulticaster()
   			2）、multicastEvent派发事件：
   			3）、获取到所有的ApplicationListener；
   				for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) 
   				1）、如果有Executor，可以支持使用Executor进行异步派发；
   					Executor executor = getTaskExecutor();
   				2）、否则，同步的方式直接执行listener方法；invokeListener(listener, event);
   				 拿到listener回调onApplicationEvent方法；
   
【事件多播器（派发器）】
   	1）、容器创建对象：refresh();
   	2）、initApplicationEventMulticaster();初始化ApplicationEventMulticaster；
   		1）、先去容器中找有没有id=“applicationEventMulticaster”的组件；
   		2）、如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
   			并且加入到容器中，我们就可以在其他组件要派发事件，自动注入这个applicationEventMulticaster；
   
【容器中有哪些监听器】
   	1）、容器创建对象：refresh();
   	2）、registerListeners();
   		从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中；
   		String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
   		//将listener注册到ApplicationEventMulticaster中
   		getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
   	
@EventListener注解注入了EventListenerMethodProcessor，实现了SmartInitializingSingleton
    SmartInitializingSingleton 原理：->afterSingletonsInstantiated();会在单实例bean全部初始化之后才执行
    生成对应的后置代理，将监听的时间注入给目标方法
    		1）、ioc容器创建对象并refresh()；
    		2）、finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean；
    			1）、先创建所有的单实例bean；getBean();
    			2）、获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型的；
    				如果是就调用afterSingletonsInstantiated();
```

# Spring容器创建过程



```java
Spring容器的refresh()【创建刷新】;
1、prepareRefresh()刷新前的预处理;
	1）、initPropertySources()初始化一些属性设置;子类自定义个性化的属性设置方法；
	2）、getEnvironment().validateRequiredProperties();检验属性的合法等
	3）、earlyApplicationEvents= new LinkedHashSet<ApplicationEvent>();保存容器中的一些早期的事件；
2、obtainFreshBeanFactory();获取BeanFactory；
	1）、refreshBeanFactory();刷新【创建】BeanFactory；
			创建了一个this.beanFactory = new DefaultListableBeanFactory();
			设置id；
	2）、getBeanFactory();返回刚才GenericApplicationContext创建的BeanFactory对象；
	3）、将创建的BeanFactory【DefaultListableBeanFactory】返回；
3、prepareBeanFactory(beanFactory);BeanFactory的预准备工作（BeanFactory进行一些设置）；
	1）、设置BeanFactory的类加载器、支持表达式解析器...
	2）、添加部分BeanPostProcessor【ApplicationContextAwareProcessor】
	3）、设置忽略的自动装配的接口EnvironmentAware、EmbeddedValueResolverAware、xxx；
	4）、注册可以解析的自动装配；我们能直接在任何组件中自动注入：
			BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext
	5）、添加BeanPostProcessor【ApplicationListenerDetector】
	6）、添加编译时的AspectJ；
	7）、给BeanFactory中注册一些能用的组件；
		environment【ConfigurableEnvironment】、
		systemProperties【Map<String, Object>】、
		systemEnvironment【Map<String, Object>】
	要用的时候直接@Autowired注入	
4、postProcessBeanFactory(beanFactory);BeanFactory准备工作完成后进行的后置处理工作；
	1）、子类通过重写这个方法来在BeanFactory创建并预准备完成以后做进一步的设置
======================以上是BeanFactory的创建及预准备工作==================================
  后面就是使用  BeanFactory 来创建各个组件
========================================================================================  
5、invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor的方法；
	BeanFactoryPostProcessor：BeanFactory的后置处理器。在BeanFactory标准初始化之后执行的；
	两个接口：BeanFactoryPostProcessor、BeanDefinitionRegistryPostProcessor
	1）、执行BeanFactoryPostProcessor的方法；
		先执行BeanDefinitionRegistryPostProcessor
		1）、获取所有的BeanDefinitionRegistryPostProcessor；
		2）、看先执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor、
			postProcessor.postProcessBeanDefinitionRegistry(registry)
		3）、在执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessor；
			postProcessor.postProcessBeanDefinitionRegistry(registry)
		4）、最后执行没有实现任何优先级或者是顺序接口的BeanDefinitionRegistryPostProcessors；
			postProcessor.postProcessBeanDefinitionRegistry(registry)
			
		
		再执行BeanFactoryPostProcessor的方法
		1）、获取所有的BeanFactoryPostProcessor
		2）、看先执行实现了PriorityOrdered优先级接口的BeanFactoryPostProcessor、
			postProcessor.postProcessBeanFactory()
		3）、在执行实现了Ordered顺序接口的BeanFactoryPostProcessor；
			postProcessor.postProcessBeanFactory()
		4）、最后执行没有实现任何优先级或者是顺序接口的BeanFactoryPostProcessor；
			postProcessor.postProcessBeanFactory()
                
6、registerBeanPostProcessors(beanFactory);注册BeanPostProcessor（Bean的后置处理器）【 intercept bean creation拦截bean的创建过程】
		不同接口类型的BeanPostProcessor；在Bean创建前后的执行时机是不一样的
		BeanPostProcessor、
		DestructionAwareBeanPostProcessor、
		InstantiationAwareBeanPostProcessor、
		SmartInstantiationAwareBeanPostProcessor、
		MergedBeanDefinitionPostProcessor【internalPostProcessors】、
		
	1）、获取所有的BeanPostProcessor;后置处理器都默认可以通过PriorityOrdered、Ordered接口来执行优先级
	2）、先注册PriorityOrdered优先级接口的BeanPostProcessor；
			把每一个BeanPostProcessor；添加到BeanFactory中
			beanFactory.addBeanPostProcessor(postProcessor);
	3）、再注册Ordered接口的
	4）、最后注册没有实现任何优先级接口的
	5）、最终注册MergedBeanDefinitionPostProcessor； (这些默认的后置处理器)
	6）、注册一个ApplicationListenerDetector；来在Bean创建完成后检查是否是ApplicationListener，如果是
			applicationContext.addApplicationListener((ApplicationListener<?>) bean);

7、initMessageSource();初始化MessageSource组件（做国际化功能；消息绑定，消息解析）；
	1）、获取BeanFactory
	2）、看容器中是否有id为messageSource的，类型是MessageSource的组件
		如果有赋值给messageSource，如果没有自己创建一个DelegatingMessageSource；
		MessageSource：取出国际化配置文件中的某个key的值；能按照区域信息获取；
    3）、把创建好的MessageSource注册在容器中，以后获取国际化配置文件的值的时候，可以自动注入MessageSource；
		beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);	
		MessageSource.getMessage(String code, Object[] args, String defaultMessage, Locale locale);

8、initApplicationEventMulticaster();初始化事件派发器；
		1）、获取BeanFactory
		2）、从BeanFactory中获取applicationEventMulticaster的ApplicationEventMulticaster；
		3）、如果上一步没有配置；创建一个SimpleApplicationEventMulticaster
		4）、将创建的ApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入
9、onRefresh();留给子容器（子类）
		1、子类重写这个方法，在容器刷新的时候可以自定义逻辑；
10、registerListeners();给容器中将所有项目里面的ApplicationListener注册进来；
		1、从容器中拿到所有的ApplicationListener
		2、将每个监听器添加到事件派发器中；
			getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
		3、派发之前步骤产生的事件；
		
11、finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例bean；
	1、beanFactory.preInstantiateSingletons();初始化后剩下的单实例bean
		1）、获取容器中的所有Bean，依次进行初始化和创建对象
		2）、获取Bean的定义信息；RootBeanDefinition
		3）、Bean不是抽象的，是单实例的，是懒加载；
			1）、判断是否是FactoryBean；是否是实现FactoryBean接口的Bean；
			2）、不是工厂Bean。利用getBean(beanName);创建对象
				0、getBean(beanName)； ioc.getBean();
				1、doGetBean(name, null, null, false);
				2、先获取缓存中保存的单实例Bean。如果能获取到说明这个Bean之前被创建过
				（所有创建过的单实例Bean都会被缓存起来）
				从private final Map<String, Object> singletonObjects =
                    new ConcurrentHashMap<String, Object>(256)获取的
				3、缓存中获取不到，开始Bean的创建对象流程；
				4、标记当前bean已经被创建
				5、获取Bean的定义信息；
				6、【获取当前Bean依赖的其他Bean;如果有按照getBean()把依赖的Bean先创建出来；】
				==========================7=========================================	
				7、启动单实例Bean的创建流程；
					1）、createBean(beanName, mbd, args);
					2）、Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
                          让BeanPostProcessor先拦截返回代理对象；
					    【InstantiationAwareBeanPostProcessor】这种类型：在bean实例化之前提前执行；
						先触发：postProcessBeforeInstantiation()；
						如果有返回值：触发postProcessAfterInitialization()；
						返回的是包装后的代理对象
					3）、如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象；调用4）
					4）、Object beanInstance = doCreateBean(beanName, mbdToUse, args);创建Bean
						 1）、【创建Bean实例】；createBeanInstance(beanName, mbd, args);
						 	利用工厂方法或者对象的构造器创建出Bean实例；
						 2）、applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
						 	调用MergedBeanDefinitionPostProcessor的
						 	postProcessMergedBeanDefinition(mbd, beanType, beanName);
                               允许这个后置处理器去修改bean定义
						 3）、【Bean属性赋值】populateBean(beanName, mbd, instanceWrapper);
						 	========赋值之前：
						 	1）、拿到InstantiationAwareBeanPostProcessor后置处理器；
						 		postProcessAfterInstantiation()；
						 	2）、拿到InstantiationAwareBeanPostProcessor后置处理器；
						 		postProcessPropertyValues()；
						 	========赋值之前
						 	3）、应用Bean属性的值；为属性利用setter方法等进行赋值；
						 		applyPropertyValues(beanName, mbd, bw, pvs);
						 4）、【Bean初始化】initializeBean(beanName, exposedObject, mbd);
						 	1）、【执行Aware接口方法】invokeAwareMethods(beanName, bean);
                                 执行xxxAware接口的方法
						 		BeanNameAware\BeanClassLoaderAware\BeanFactoryAware
						 	2）、【执行后置处理器初始化之前】
						 	applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
						 		BeanPostProcessor.postProcessBeforeInitialization（）;
						 	3）、【执行初始化方法】invokeInitMethods(beanName, wrappedBean, mbd);
						 		1）、是否是InitializingBean接口的实现；执行接口规定的初始化；
						 		2）、是否自定义初始化方法；
						 	4）、【执行后置处理器初始化之后】
						 	applyBeanPostProcessorsAfterInitialization
						 		BeanPostProcessor.postProcessAfterInitialization()；
						5）、注册Bean的销毁方法；
					5）、将创建的Bean添加到缓存中singletonObjects；
	     ==========================7=========================================				
	    ioc容器就是这些Map；很多的Map里面保存了单实例Bean，环境信息。。。。；
		所有Bean都利用getBean创建完成以后；
		检查所有的Bean是否是SmartInitializingSingleton接口的；如果是；就执行
		afterSingletonsInstantiated()；触发post_initialation的回调方法
			
12、finishRefresh();完成BeanFactory的初始化创建工作；IOC容器就创建完成；
		1）、initLifecycleProcessor();初始化和生命周期有关的后置处理器；LifecycleProcessor
			默认从容器中找是否有lifecycleProcessor的组件【LifecycleProcessor】；如果没有
			new DefaultLifecycleProcessor();加入到容器；	
			写一个LifecycleProcessor的实现类，可以在BeanFactory
				void onRefresh();
				void onClose();	
		2）、	getLifecycleProcessor().onRefresh();
			拿到前面定义的生命周期处理器（BeanFactory）；回调onRefresh()；
		3）、publishEvent(new ContextRefreshedEvent(this));发布容器刷新完成事件；
		4）、liveBeansView.registerApplicationContext(this);
	
======总结===========
	1）、Spring容器在启动的时候，先会保存所有注册进来的Bean的定义信息；
		1）、xml注册bean；<bean>
		2）、注解注册Bean；@Service、@Component、@Bean、xxx
	2）、Spring容器会合适的时机创建这些Bean
		1）、用到这个bean的时候；利用getBean创建bean；创建好以后保存在容器中；
		2）、统一创建剩下所有的bean的时候；finishBeanFactoryInitialization()；
	3）、后置处理器；BeanPostProcessor
		1）、每一个bean创建完成，都会使用各种后置处理器进行处理；来增强bean的功能；
			AutowiredAnnotationBeanPostProcessor:处理自动注入
			AnnotationAwareAspectJAutoProxyCreator:来做AOP功能；
			xxx....
			增强的功能注解：
			AsyncAnnotationBeanPostProcessor
			....
	4）、事件驱动模型；
		ApplicationListener；事件监听；
		ApplicationEventMulticaster；事件派发：
```

# 注解的web

servlet 3.0 新增的注解

```java
以前来开发web工程
无论是sevlet  filter listener 还是dispatchServlet都需要在web.xml中进行配置

servlet3.0之后提供注解来方便我们进行组件注册 (他需要Tomcat7.0 以上才支持)
 
Shared libraries（共享库） / runtimes pluggability（运行时插件能力）

1、Servlet容器启动会扫描，当前应用里面每一个jar包的
	ServletContainerInitializer的实现
2、提供ServletContainerInitializer的实现类；
	必须绑定在，META-INF/services/javax.servlet.ServletContainerInitializer
	文件的内容就是ServletContainerInitializer实现类的全类名；

总结：容器在启动应用的时候，会扫描当前应用每一个jar包里面
META-INF/services/javax.servlet.ServletContainerInitializer
指定的实现类，启动并运行这个实现类的方法；传入感兴趣的类型；


ServletContainerInitializer；
@HandlesTypes；

//容器启动的时候会将@HandlesTypes指定的这个类型下面的子类（实现类，子接口等）传递过来；
//传入感兴趣的类型；
@HandlesTypes(value={HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

	/**
	 * 应用启动的时候，会运行onStartup方法；
	 * 
	 * Set<Class<?>> arg0：感兴趣的类型的所有子类型；
	 * ServletContext arg1:代表当前Web应用的ServletContext；一个Web应用一个ServletContext；
	 * 
	 * 1）、使用ServletContext注册Web组件（Servlet、Filter、Listener）
	 * 2）、使用编码的方式，在项目启动的时候给ServletContext里面添加组件；
	 * 		必须在项目启动的时候来添加；
	 * 		1）、ServletContainerInitializer得到的ServletContext；
	 * 		2）、ServletContextListener得到的ServletContext；
	 */
	@Override
	public void onStartup(Set<Class<?>> arg0, ServletContext sc) throws ServletException {
    }
}
```

### 整合springMVC

```java
 <build>
  	<plugins>
  		<plugin>
  			<groupId>org.apache.maven.plugins</groupId>
  			<artifactId>maven-war-plugin</artifactId>
  			<version>2.4</version>
  			<configuration>
  				<failOnMissingWebXml>false</failOnMissingWebXml>
  			</configuration>
  		</plugin>
  	</plugins>
  </build>

1、web容器在启动的时候，会扫描每个jar包下的META-INF/services/javax.servlet.ServletContainerInitializer
2、加载这个文件指定的类SpringServletContainerInitializer
3、spring的应用一启动会加载感兴趣的WebApplicationInitializer接口的下的所有组件；
4、并且为WebApplicationInitializer组件创建对象（组件不是接口，不是抽象类）
	1）、AbstractContextLoaderInitializer：创建根容器；createRootApplicationContext()；
	2）、AbstractDispatcherServletInitializer：
			创建一个web的ioc容器；createServletApplicationContext();
			创建了DispatcherServlet；createDispatcherServlet()；
			将创建的DispatcherServlet添加到ServletContext中；
				getServletMappings();
	3）、AbstractAnnotationConfigDispatcherServletInitializer：注解方式配置的DispatcherServlet初始化器
			创建根容器：createRootApplicationContext()
					getRootConfigClasses();传入一个配置类
			创建web的ioc容器： createServletApplicationContext();
					获取配置类；getServletConfigClasses();
	
总结：
	以注解方式来启动SpringMVC；继承AbstractAnnotationConfigDispatcherServletInitializer；
实现抽象方法指定DispatcherServlet的配置信息；

//web容器启动的时候创建对象；调用方法来初始化容器以前前端控制器
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	//获取根容器的配置类；（Spring的配置文件）   父容器；
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[]{RootConfig.class};
	}

	//获取web容器的配置类（SpringMVC配置文件）  子容器；
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[]{AppConfig.class};
	}

	//获取DispatcherServlet的映射信息
	//  /：拦截所有请求（包括静态资源（xx.js,xx.png）），但是不包括*.jsp；
	//  /*：拦截所有请求；连*.jsp页面都拦截；jsp页面是tomcat的jsp引擎解析的；
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[]{"/"};
	}

}

//SpringMVC只扫描Controller；子容器
//useDefaultFilters=false 禁用默认的过滤规则；
@ComponentScan(value="com.atguigu",includeFilters={
		@Filter(type=FilterType.ANNOTATION,classes={Controller.class})
},useDefaultFilters=false)
@EnableWebMvc
public class AppConfig  extends WebMvcConfigurerAdapter  {

	//定制
	
	//视图解析器
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		//默认所有的页面都从 /WEB-INF/ xxx .jsp
		//registry.jsp();
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	//静态资源访问
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.enable();
	}
	
	//拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		//super.addInterceptors(registry);
		registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
	}

}

//Spring的容器不扫描controller;父容器
@ComponentScan(value="com.atguigu",excludeFilters={
		@Filter(type=FilterType.ANNOTATION,classes={Controller.class})
})
public class RootConfig {

}
===========================
定制SpringMVC；
1）、@EnableWebMvc:开启SpringMVC定制配置功能；
	<mvc:annotation-driven/>；

2）、配置组件（视图解析器、视图映射、静态资源映射、拦截器。。。）
	extends WebMvcConfigurerAdapter
```


