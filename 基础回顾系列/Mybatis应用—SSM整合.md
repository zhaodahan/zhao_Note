# Maven+SpringMVC+Spring+MyBatis+Bootstrap整合

![JAVA_MYBATIS30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS30.png?raw=true)

## 整合

一： 创建一个maven工程。加入相关依赖jar包

pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.atguigu</groupId>
	<artifactId>ssm-crud</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>


	<!--引入项目依赖的jar包 -->
	<!-- SpringMVC、Spring -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
	<dependencies>

		<!--引入pageHelper分页插件 -->
		<dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper</artifactId>
			<version>5.0.0</version>
		</dependency>

		<!-- MBG -->
		<!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
		<dependency>
			<groupId>org.mybatis.generator</groupId>
			<artifactId>mybatis-generator-core</artifactId>
			<version>1.3.5</version>
		</dependency>


		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>4.3.7.RELEASE</version>
		</dependency>

		<!-- 返回json字符串的支持 -->
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.8.8</version>
		</dependency>

		<!--JSR303数据校验支持；tomcat7及以上的服务器， 
		tomcat7以下的服务器：el表达式。额外给服务器的lib包中替换新的标准的el
		-->
		<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-validator -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>5.4.1.Final</version>
		</dependency>


		<!-- Spring-Jdbc -->
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>4.3.7.RELEASE</version>
		</dependency>

		<!--Spring-test -->
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<version>4.3.7.RELEASE</version>
		</dependency>


		<!-- Spring面向切面编程 -->
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aspects</artifactId>
			<version>4.3.7.RELEASE</version>
		</dependency>

		<!--MyBatis -->
		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.4.2</version>
		</dependency>
		<!-- MyBatis整合Spring的适配包 -->
		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.3.1</version>
		</dependency>

		<!-- 数据库连接池、驱动 -->
		<!-- https://mvnrepository.com/artifact/c3p0/c3p0 -->
		<dependency>
			<groupId>c3p0</groupId>
			<artifactId>c3p0</artifactId>
			<version>0.9.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.41</version>
		</dependency>
		<!-- （jstl，servlet-api，junit） -->
		<!-- https://mvnrepository.com/artifact/jstl/jstl -->
		<dependency>
			<groupId>jstl</groupId>
			<artifactId>jstl</artifactId>
			<version>1.2</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>3.0.1</version>
			<scope>provided</scope>
		</dependency>


		<!-- junit -->
		<!-- https://mvnrepository.com/artifact/junit/junit -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
		</dependency>
	</dependencies>

</project>
```

二：配置web环境

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">
	
	<!--1、启动Spring的容器  -->
	<!-- needed for ContextLoaderListener -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>

	<!-- Bootstraps the root web application context before servlet initialization -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<!--2、springmvc的前端控制器，拦截所有请求  -->
	<!-- The front controller of this Spring Web application, responsible for handling all application requests -->
	<servlet>
		<servlet-name>dispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<!-- Map all requests to the DispatcherServlet for handling -->
	<servlet-mapping>
		<servlet-name>dispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
	
	<!-- 3、字符编码过滤器，一定要放在所有过滤器之前 -->
	<filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
		<init-param>
			<param-name>forceRequestEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
		<init-param>
			<param-name>forceResponseEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	<!-- 4、使用Rest风格的URI，将页面普通的post请求转为指定的delete或者put请求 -->
	<filter>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	<filter>
		<filter-name>HttpPutFormContentFilter</filter-name>
		<filter-class>org.springframework.web.filter.HttpPutFormContentFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>HttpPutFormContentFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	
</web-app>
```

配置对应的spring和springmvc的配置文件

spring的 applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	<context:component-scan base-package="com.atguigu">
		<context:exclude-filter type="annotation"
			expression="org.springframework.stereotype.Controller" />
	</context:component-scan>

	<!-- Spring的配置文件，这里主要配置和业务逻辑有关的 -->
	<!--=================== 数据源，事务控制，xxx ================-->
	<context:property-placeholder location="classpath:dbconfig.properties" />
	<bean id="pooledDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
	</bean>

	<!--================== 配置和MyBatis的整合=============== -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 指定mybatis全局配置文件的位置 -->
		<property name="configLocation" value="classpath:mybatis-config.xml"></property>
		<property name="dataSource" ref="pooledDataSource"></property>
		<!-- 指定mybatis，mapper文件的位置 -->
		<property name="mapperLocations" value="classpath:mapper/*.xml"></property>
	</bean>

	<!-- 配置扫描器，将mybatis接口的实现加入到ioc容器中 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!--扫描所有dao接口的实现，加入到ioc容器中 -->
		<property name="basePackage" value="com.atguigu.crud.dao"></property>
	</bean>
	
	<!-- 配置一个可以执行批量的sqlSession -->
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
		<constructor-arg name="executorType" value="BATCH"></constructor-arg>
	</bean>
	<!--=============================================  -->

	<!-- ===============事务控制的配置 ================-->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<!--控制住数据源  -->
		<property name="dataSource" ref="pooledDataSource"></property>
	</bean>
	<!--开启基于注解的事务，使用xml配置形式的事务（必要主要的都是使用配置式）  -->
	<aop:config>
		<!-- 切入点表达式 -->
		<aop:pointcut expression="execution(* com.atguigu.crud.service..*(..))" id="txPoint"/>
		<!-- 配置事务增强 -->
		<aop:advisor advice-ref="txAdvice" pointcut-ref="txPoint"/>
	</aop:config>
	
	<!--配置事务增强，事务如何切入  -->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<!-- 所有方法都是事务方法 -->
			<tx:method name="*"/>
			<!--以get开始的所有方法  -->
			<tx:method name="get*" read-only="true"/>
		</tx:attributes>
	</tx:advice>
	
	<!-- Spring配置文件的核心点（数据源、与mybatis的整合，事务控制） -->
	
</beans>

```

springmvc 使用的默认的配置文件dispatcherServlet-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	<!--SpringMVC的配置文件，包含网站跳转逻辑的控制，配置  -->
	<context:component-scan base-package="com.atguigu" use-default-filters="false">
		<!--只扫描控制器。  -->
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>
	
	<!--配置视图解析器，方便页面返回  -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>
	
	<!--两个标准配置  -->
	<!-- 将springmvc不能处理的请求交给tomcat -->
	<mvc:default-servlet-handler/>
	<!-- 能支持springmvc更高级的一些功能，JSR303校验，快捷的ajax...映射动态请求 -->
	<mvc:annotation-driven/>

</beans>

```

