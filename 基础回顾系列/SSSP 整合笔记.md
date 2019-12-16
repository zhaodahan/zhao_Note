# SSSP整合笔记

 Spring、SpringMVC、JPA、SpringData 进行 CRUD,分页

## 搭建环境

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">

	<!-- 配置启动spring IOC 容器的 Listener -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<!-- 配置字符编码过滤器 -->
	<!-- 字符编码过滤器必须配置在所有过滤器的最前面! -->
	<filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
	</filter>
	
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	<!-- 配置可以把 POST 请求转为 PUT、DELETE 请求的 Filter -->
	<filter>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	<!-- 配置 OpenEntityManagerInViewFilter. 可以解决懒加载异常的问题 -->
	<filter>
		<filter-name>OpenEntityManagerInViewFilter</filter-name>
		<filter-class>org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>OpenEntityManagerInViewFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	<!-- 配置 SpringMVC 的 DispatcherServlet -->
	<servlet>
		<servlet-name>springDispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>springDispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

</web-app>
```

spring的配置文件 ,类路径下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:jpa="http://www.springframework.org/schema/data/jpa"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

	<!-- 配置自动扫描的包 -->
	<context:component-scan base-package="com.atguigu.sssp">
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		<context:exclude-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
	</context:component-scan>
	
	<!-- 配置数据源 -->
	<context:property-placeholder location="classpath:db.properties"/>

	<bean id="dataSource"
		class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"></property>	
		<property name="password" value="${jdbc.password}"></property>	
		<property name="driverClass" value="${jdbc.driverClass}"></property>	
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>	
		
		<!-- 配置其他属性 -->
	</bean>
	
	<!-- 配置 JPA 的 EntityManagerFactory -->
	<bean id="entityManagerFactory"
		class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<property name="jpaVendorAdapter">
			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"></bean>
		</property>	
		<property name="packagesToScan" value="com.atguigu.sssp"></property>
		<property name="jpaProperties">
			<props>
				<prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>
				<prop key="hibernate.hbm2ddl.auto">update</prop>
				<prop key="hibernate.show_sql">true</prop>
				<prop key="hibernate.format_sql">true</prop>
				<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
				
				<prop key="hibernate.cache.use_second_level_cache">true</prop>
				<prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</prop>
				<prop key="hibernate.cache.use_query_cache">true</prop>
			</props>
		</property>
		<property name="sharedCacheMode" value="ENABLE_SELECTIVE"></property>
	</bean>
	
	<!-- 配置事务 -->
	<bean id="transactionManager"
		class="org.springframework.orm.jpa.JpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactory"></property>	
	</bean>
	
	<!-- 配置支持基于注解的事务 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>
	
	<!-- 配置 SpringData -->
	<jpa:repositories base-package="com.atguigu.sssp"
		entity-manager-factory-ref="entityManagerFactory"></jpa:repositories>
	
</beans>

```

数据源 db.properties"

```java
jdbc.user=root
jdbc.password=1230
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.jdbcUrl=jdbc:mysql:///spring
```

二级缓存 ehcache.xml

```xml
<ehcache>

    <diskStore path="java.io.tmpdir"/>

    <defaultCache
        maxElementsInMemory="10000"
        eternal="false"
        timeToIdleSeconds="120"
        timeToLiveSeconds="120"
        overflowToDisk="true"
        />

    <cache name="sampleCache1"
        maxElementsInMemory="10000"
        eternal="false"
        timeToIdleSeconds="300"
        timeToLiveSeconds="600"
        overflowToDisk="true"
        />


    <cache name="sampleCache2"
        maxElementsInMemory="1000"
        eternal="true"
        timeToIdleSeconds="0"
        timeToLiveSeconds="0"
        overflowToDisk="false"
        /> -->

</ehcache>

```

SpringMVC的配置文件 springDispatcherServlet-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

	<!-- 配置自动扫描的包 -->
	<context:component-scan base-package="com.atguigu.sssp" use-default-filters="false">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		<context:include-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
	</context:component-scan>
	
	<!-- 配置视图解析器 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>

	<mvc:default-servlet-handler/>
	<mvc:annotation-driven></mvc:annotation-driven>

</beans>

```



## CRUD,分页操作

所操作的实体之间的关系：

一个部门里面有多个员工

### 分页

```java
完成分页操作
Dao 层：
不带查询查询条件的分页，所以可以直接调用 PagingAndSortingRepository# findAll(Pageable pageable) 返回 Page 对象。
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Employee getByLastName(String lastName);	
}
Service 层：
没有业务逻辑
把 Controller 传入的 pageNo 和 pageSize 封装为 Pageable 对象。注意：Pageable 的 pageNo 是从 0 开始的
调用 Dao 层的方法即可。
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Transactional(readOnly=true)
	public Page<Employee> getPage(int pageNo, int pageSize){
		PageRequest pageable = new PageRequest(pageNo - 1, pageSize);
		return employeeRepository.findAll(pageable);
	}
}

Controller 层：
获取 pageNo，并对 pageNo 进行校验
调用 Service 方法返回 Page 对象
把 Page 对象放入到 request 中
转发页面
@Controller
public class EmployeeHandler {
	@Autowired
	private EmployeeService employeeService;
    
	@RequestMapping("/emps")
	public String list(@RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr, 
			Map<String, Object> map){
		int pageNo = 1;
		
		try {
			//对 pageNo 的校验
			pageNo = Integer.parseInt(pageNoStr);
			if(pageNo < 1){
				pageNo = 1;
			}
		} catch (Exception e) {}
		
		Page<Employee> page = employeeService.getPage(pageNo, 5);
		map.put("page", page);
		
		return "emp/list";
	}
	
}
JSP 页面：使用 JSTL 来显示页面


```

