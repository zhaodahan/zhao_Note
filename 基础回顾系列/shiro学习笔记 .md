# Shiro学习笔记

Apache Shiro 是目前使用率较高的一个 Java 安全框架。内容涵盖 Shiro 认证、加密、授权、安全标签、安全注解、会话管理、缓存、Rememberme 等 JavaEE 企业级开发的核心技术

## 什么是shiro

![JAVA_SHIRO1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO1.png?raw=true)

```
Shiro 是 Apache 下的一个Java的安全（权限）框架， 他不仅仅局限于web，而是所有的java应用环境
简而言之它就是一个用来管理操作权限的框架
官网网址：http://shiro.apache.org/

Shiro 可以完成：
认证(登录，密码匹配由shiro来帮我们完成)、
授权(点击链接，判断是否有权限查看)、
加密(保护数据的安全性，使用shiro可以很容易的堆密码进行加密)
会话管理、(在非web环境下，也可以使用shiro为我们提供的session)、
与Web 集成、
缓存(shiro提供缓存模块)
Concurrency：Shiro 支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去；
Run As：允许一个用户假装为另一个用户（如果他们允许）的身份进行访问；
Remember Me：记住我，这个是非常常见的功能，即一次登录后，下次再来的话不用登录了
```

Shiro 架构

![JAVA_SHIRO2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO2.png?raw=true)

![JAVA_SHIRO3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO3.png?raw=true)

```
Subject：应用代码直接交互的对象是 Subject，也就是说 Shiro 的对外API 核心就是 Subject。Subject 代表了当前“用户”.

SecurityManager:所有与安全有关的操作都会与SecurityManager 交互；且其管理着所有 Subject；可以看出它是 Shiro的核心，它负责与 Shiro 的其他组件进行交互，它相当于 SpringMVC 中DispatcherServlet 的角色

Realm:：Shiro 从 Realm 获取安全数据（如用户、角色、权限），就是说SecurityManager 要验证用户身份，那么它需要从 Realm 获取相应的用户进行比较以确定用户身份是否合法；也需要从 Realm 得到用户相应的角色/权限进行验证用户是否能进行操作；可以把 Realm 看成 DataSource
```



## 初识shiro

常用的API: 如何使用shiro

```java
//读取配置文件 shiro.ini 并构建SecurityManager ，只是了解，开发中不这样使用
Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
 SecurityManager securityManager = factory.getInstance();

下面的是常用的操作shiro的API
// 获取当前的 Subject. 调用 SecurityUtils.getSubject();
//我们在程序中和shiro打交道的就是Subject。他是整个shiro架构的门面
        Subject currentUser = SecurityUtils.getSubject();

// 测试使用 Session 
// 获取 Session: Subject#getSession()
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("---> Retrieved the correct value! [" + value + "]");
        }

// 测试当前的用户是否已经被认证. 即是否已经登录. 
// 调动 Subject 的 isAuthenticated() 
        if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // rememberme
            token.setRememberMe(true);
            try {
            	// 执行登录. 
                currentUser.login(token);
            } 
            // 若没有指定的账户, 则 shiro 将会抛出 UnknownAccountException 异常. 
            catch (UnknownAccountException uae) {
                log.info("----> There is no user with username of " + token.getPrincipal());
                return; 
            } 
            // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。 
            catch (IncorrectCredentialsException ice) {
                log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
                return; 
            } 
            // 用户被锁定的异常 LockedAccountException
            catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类. 
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }


        //say who they are:
        //print their identifying principal主要认证 (in this case, a username):
        log.info("----> User [" + currentUser.getPrincipal() + "] logged in successfully.");


 // 测试是否有某一个角色. 调用 Subject 的 hasRole 方法. 
//角色是权限的组合，一个角色可以拥有多个权限
        if (currentUser.hasRole("schwartz")) {
            log.info("----> May the Schwartz be with you!");
        } else {
            log.info("----> Hello, mere mortal.");
            return; 
        }

//test a typed permission (not instance-level)
// 测试用户是否具备某一个行为(用户是否具有权限干某事). . 调用 Subject 的 isPermitted() 方法。 
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("----> You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

//a (very powerful) Instance Level permission:
// 测试用户是否具备某一个行为，比上面的更加具体： user:delete:zhangsan，我们可以删除user zhangsan 的信息
        if (currentUser.isPermitted("user:delete:zhangsan")) {
            log.info("----> can do");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

// 执行登出. 调用 Subject 的 Logout() 方法. 
        System.out.println("---->" + currentUser.isAuthenticated());
        currentUser.logout(); 
        System.out.println("---->" + currentUser.isAuthenticated());

        System.exit(0);
    }

// 在真正编码使用shiro的时候，测试角色，测试权限，我们不会使用方法进行硬编码，而是使用注解的方式
```



### 集成spring

在web环境下使用shiro。(在WEB环境下，大部分情况下都是和spring进行集成)

spring集成shiro步骤

```xml
1：加入shiro的jar包
2:配置 Spring 及 SpringMVC
配置web.xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">
	
	<!-- spring使用classpath:applicationContext.xml -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>

	<!-- Bootstraps the root web application context before servlet initialization -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<!-- springMVC 使用默认的配置文件 spring-servlet.xml-->
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<!-- Map all requests to the DispatcherServlet for handling -->
	<servlet-mapping>
		<servlet-name>spring</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
	
	
</web-app>


springMVC配置文件 spring-servlet.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
	
	<context:component-scan base-package="com.atguigu.shiro"></context:component-scan>
	
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>
	
	<mvc:annotation-driven></mvc:annotation-driven>
	<mvc:default-servlet-handler/>

</beans>

spring 的配置文件classpath:applicationContext.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- =========================================================
         Shiro Core Components - Not Spring Specific
         ========================================================= -->
    <!-- Shiro's main business-tier object for web-enabled applications
         (use DefaultSecurityManager instead when there is no web environment)-->
    <!--  
    1. 配置 SecurityManager!
    -->     
    <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="cacheManager" ref="cacheManager"/>
        <property name="authenticator" ref="authenticator"></property>
        
        <property name="realms">
        	<list>
    			<ref bean="jdbcRealm"/>
    			<ref bean="secondRealm"/>
    		</list>
        </property>
        
        <property name="rememberMeManager.cookie.maxAge" value="10"></property>
    </bean>

    <!-- Let's use some enterprise caching support for better performance.  You can replace this with any enterprise
         caching framework implementation that you like (Terracotta+Ehcache, Coherence, GigaSpaces, etc -->
    <!--  
    2. 配置 CacheManager. 
    2.1 需要加入 ehcache 的 jar 包及配置文件. 
    -->     
    <bean id="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
        <!-- Set a net.sf.ehcache.CacheManager instance here if you already have one.  If not, a new one
             will be creaed with a default config:
             <property name="cacheManager" ref="ehCacheManager"/> -->
        <!-- If you don't have a pre-built net.sf.ehcache.CacheManager instance to inject, but you want
             a specific Ehcache configuration to be used, specify that here.  If you don't, a default
             will be used.: -->
        <property name="cacheManagerConfigFile" value="classpath:ehcache.xml"/> 
    </bean>
    
    <bean id="authenticator" 
    	class="org.apache.shiro.authc.pam.ModularRealmAuthenticator">
    	<property name="authenticationStrategy">
    		<bean class="org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy"></bean>
    	</property>
    </bean>

    <!-- Used by the SecurityManager to access security data (users, roles, etc).
         Many other realm implementations can be used too (PropertiesRealm,
         LdapRealm, etc. -->
    <!-- 
    	3. 配置 Realm 
    	3.1 直接配置实现了 org.apache.shiro.realm.Realm 接口的 bean
    -->     
    <bean id="jdbcRealm" class="com.atguigu.shiro.realms.ShiroRealm">
    	<property name="credentialsMatcher">
    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
    			<property name="hashAlgorithmName" value="MD5"></property>
    			<property name="hashIterations" value="1024"></property>
    		</bean>
    	</property>
    </bean>
    
    <bean id="secondRealm" class="com.atguigu.shiro.realms.SecondRealm">
    	<property name="credentialsMatcher">
    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
    			<property name="hashAlgorithmName" value="SHA1"></property>
    			<property name="hashIterations" value="1024"></property>
    		</bean>
    	</property>
    </bean>

    <!-- =========================================================
         Shiro Spring-specific integration
         ========================================================= -->
    <!-- Post processor that automatically invokes init() and destroy() methods
         for Spring-configured Shiro objects so you don't have to
         1) specify an init-method and destroy-method attributes for every bean
            definition and
         2) even know which Shiro objects require these methods to be
            called. -->
    <!--  
    4. 配置 LifecycleBeanPostProcessor. 可以自定的来调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法. 
    -->       
    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

    <!-- Enable Shiro Annotations for Spring-configured beans.  Only run after
         the lifecycleBeanProcessor has run: -->
    <!--  
    5. 启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor 之后才可以使用. 
    -->     
    <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
          depends-on="lifecycleBeanPostProcessor"/>
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>

    <!-- Define the Shiro Filter here (as a FactoryBean) instead of directly in web.xml -
         web.xml uses the DelegatingFilterProxy to access this bean.  This allows us
         to wire things with more control as well utilize nice Spring things such as
         PropertiesPlaceholderConfigurer and abstract beans or anything else we might need: -->
    <!--  
    6. 配置 ShiroFilter. 
    6.1 id 必须和 web.xml 文件中配置的 DelegatingFilterProxy 的 <filter-name> 一致.
                      若不一致, 则会抛出: NoSuchBeanDefinitionException. 因为 Shiro 会来 IOC 容器中查找和 <filter-name> 名字对应的 filter bean.
    -->     
    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <property name="loginUrl" value="/login.jsp"/>
        <property name="successUrl" value="/list.jsp"/>
        <property name="unauthorizedUrl" value="/unauthorized.jsp"/>
        
        <property name="filterChainDefinitionMap" ref="filterChainDefinitionMap"></property>
        
        <!--  
        	配置哪些页面需要受保护. 
        	以及访问这些页面需要的权限. 
        	1). anon 可以被匿名访问
        	2). authc 必须认证(即登录)后才可能访问的页面. 
        	3). logout 登出.
        	4). roles 角色过滤器
        -->
        <!--  
        <property name="filterChainDefinitions">
            <value>
                /login.jsp = anon
                /shiro/login = anon
                /shiro/logout = logout
                
                /user.jsp = roles[user]
                /admin.jsp = roles[admin]
                
                # everything else requires authentication:
                /** = authc
            </value>
        </property>
        -->
    </bean>
    
    <!-- 配置一个 bean, 该 bean 实际上是一个 Map. 通过实例工厂方法的方式 -->
    <bean id="filterChainDefinitionMap" 
    	factory-bean="filterChainDefinitionMapBuilder" factory-method="buildFilterChainDefinitionMap"></bean>
    
    <bean id="filterChainDefinitionMapBuilder"
    	class="com.atguigu.shiro.factory.FilterChainDefinitionMapBuilder"></bean>
    
    <bean id="shiroService"
    	class="com.atguigu.shiro.services.ShiroService"></bean>

</beans>


3:配置shiro 
1： 确认加入了shiro相关的jar包
2： 在web下配置shiro
在web.xml中0配置shiro filter
<!-- Shiro Filter is defined in the spring application context: -->
	<!-- 
	1. 配置  Shiro 的 shiroFilter.  
	2. DelegatingFilterProxy 实际上是 Filter 的一个代理对象. 默认情况下, Spring 会到 IOC 容器中查找和 
	<filter-name> 对应的 filter bean. 也可以通过 targetBeanName 的初始化参数来配置 filter bean 的 id. 
	-->
    <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <param-name>targetFilterLifecycle</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>

    <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

在spring的配置文件中来配置shiro
 <!-- =========================================================
         Shiro 的核心注解
         ========================================================= -->

    <!--  
    1. 配置 SecurityManager!十分关键。
    他需要3个属性
    1：cacheManager
    2：配置realm
    -->     
    <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="cacheManager" ref="cacheManager"/>
        <property name="authenticator" ref="authenticator"></property>
        
        <property name="realms">
        	<list>
    			<ref bean="jdbcRealm"/>
    			<ref bean="secondRealm"/>
    		</list>
        </property>
        
        <property name="rememberMeManager.cookie.maxAge" value="10"></property>
    </bean>

    <!--  
    2. 配置 CacheManager.  两种方式,直接指定ref="ehCacheManager" 或者直接传入配置文件
    2.1 需要加入 ehcache 的 jar 包及配置文件. 
    -->     
    <bean id="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
        <!-- Set a net.sf.ehcache.CacheManager instance here if you already have one.  If not, a new one will be creaed with a default config:
             <property name="cacheManager" ref="ehCacheManager"/> -->
        <!-- If you don't have a pre-built net.sf.ehcache.CacheManager instance to inject, but you want a specific Ehcache configuration to be used, specify that here.  If you don't, a default will be used.: -->
        <property name="cacheManagerConfigFile" value="classpath:ehcache.xml"/> 
    </bean>
    
    <bean id="authenticator" 
    	class="org.apache.shiro.authc.pam.ModularRealmAuthenticator">
    	<property name="authenticationStrategy">
    		<bean class="org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy"></bean>
    	</property>
    </bean>

    <!--被SecurityManager用于访问安全数据(用户、角色等)。
         Many other realm implementations can be used too (PropertiesRealm,LdapRealm, etc. -->
    <!-- 
    	3. 配置 Realm 
    	3.1 直接配置实现了 org.apache.shiro.realm.Realm 接口的 bean
        这个com.atguigu.shiro.realms.ShiroRealm使我们自己实现的实现了Realm接口的一个实现类
    -->     
    <bean id="jdbcRealm" class="com.atguigu.shiro.realms.ShiroRealm">
    	<property name="credentialsMatcher">
    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
    			<property name="hashAlgorithmName" value="MD5"></property>
    			<property name="hashIterations" value="1024"></property>
    		</bean>
    	</property>
    </bean>
    
    <bean id="secondRealm" class="com.atguigu.shiro.realms.SecondRealm">
    	<property name="credentialsMatcher">
    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
    			<property name="hashAlgorithmName" value="SHA1"></property>
    			<property name="hashIterations" value="1024"></property>
    		</bean>
    	</property>
    </bean>

    <!-- =========================================================
         Shiro Spring-specific integration
         与shiro相关的特定的集成
         ========================================================= -->
    <!-- Post processor that automatically invokes init() and destroy() methods
         for Spring-configured Shiro objects so you don't have to
         1) specify an init-method and destroy-method attributes for every bean
            definition and
         2) even know which Shiro objects require these methods to be
            called. -->
    <!--  
    4. 配置 LifecycleBeanPostProcessor. 可以自定的来调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法. 
    -->       
    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

    <!-- Enable Shiro Annotations for Spring-configured beans.  Only run after
         the lifecycleBeanProcessor has run: -->
    <!--  
    5. 启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor 之后才可以使用. 
    -->     
    <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
          depends-on="lifecycleBeanPostProcessor"/>
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>


    <!--  
    6. 配置 ShiroFilter. 
    6.1 id 必须和 web.xml 文件中配置的 DelegatingFilterProxy 的 <filter-name> 一致.
    6.2 若不一致, 则会抛出: NoSuchBeanDefinitionException. 因为 Shiro 会来 IOC 容器中查找和 <filter-name> 名字对应的 filter bean.
    -->     
    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <property name="loginUrl" value="/login.jsp"/>
        <property name="successUrl" value="/list.jsp"/>
        <property name="unauthorizedUrl" value="/unauthorized.jsp"/>
        
        <property name="filterChainDefinitionMap" ref="filterChainDefinitionMap"></property>
        
        <!--  
        	配置哪些页面需要受保护. 
        	以及访问这些页面需要的权限. 
        	1). anon 可以被匿名访问 
        	2). authc 必须认证(即登录)后才可能访问的页面. 
        	3). logout 登出.
        	4). roles 角色过滤器
        -->
        <!--  
        <property name="filterChainDefinitions">
            <value>
                /login.jsp = anon
                /shiro/login = anon
                /shiro/logout = logout
                
                /user.jsp = roles[user]
                /admin.jsp = roles[admin]
                
                # everything else requires authentication:
                /** = authc
            </value>
        </property>
        -->
    </bean>
```

### shiro的工作流程

![JAVA_SHIRO4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO4.png?raw=true)

```
与Web 集成:
在web.xml中配置的ShiroFilter是一个入口。他可以拦截所有的url
ShiroFilter 类似于如 Strut2/SpringMVC 这种web 框架的前端控制器，是安全控制的入口点，其负责读取配置（如ini 配置文件），然后判断URL是否需要登录/权限等工作

ShiroFilter 判断当前url是否需要被认证或者匿名(是否配置被拦截)，如果不需要被拦截直接放行。如果需要，则判断两个规则。 1： 是否可以匿名访问   2 是否需要认证   如果都不满足则重定向到初始界面
```

###  DelegatingFilterProxy

```xml
<filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <param-name>targetFilterLifecycle</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
在配置shiroFilter的时候，他实际使用的是DelegatingFilterProxy (Delegating 委托)
DelegatingFilterProxy 作用是自动到 Spring 容器查找名字为 shiroFilter（filter-name）的 bean 并把所有 Filter的操作委托给它。
```

### filterChainDefinitionMap 

shiro权限url配置细节

![JAVA_SHIRO5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO5.png?raw=true)

```xml
<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <property name="loginUrl" value="/login.jsp"/>
        <property name="successUrl" value="/list.jsp"/>
        <property name="unauthorizedUrl" value="/unauthorized.jsp"/>
        
        <property name="filterChainDefinitionMap" ref="filterChainDefinitionMap"></property>
        
        <!--  
        	配置哪些页面需要受保护. 
        	以及访问这些页面需要的权限. 
        	1). anon 可以被匿名访问 
        	2). authc 必须认证(即登录)后才可能访问的页面. 
        	3). logout 登出.
        	4). roles 角色过滤器
        -->
        <!--  
        <property name="filterChainDefinitions">
            <value>
                /login.jsp = anon
                /shiro/login = anon
                /shiro/logout = logout
                
                /user.jsp = roles[user]
                /admin.jsp = roles[admin]
                
                # everything else requires authentication:
                /** = authc
            </value>
        </property>
        -->
    </bean>
shiro权限url配置细节：
其格式是： “url=拦截器[参数]，拦截器[参数]”；
url 中可以使用通配符， 如： /** 
 url 模式使用 Ant 风格模式
 – ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/；
 – *：匹配零个或多个字符串，如 /admin 将匹配 /admin、/admin123，但不匹配 /admin/1；
 – **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
URL 匹配顺序
URL 权限采取第一次匹配优先的 方式，即从头开始使用第一个匹配的 url 模式对应的拦截器链
如：
– /bb/**=filter1
– /bb/aa=filter2
– /**=filter3
– 如果请求的url是“/bb/aa”，因为按照声明顺序进行匹配，那么将使用 filter1 进行拦截
所以： 在filterChainDefinitions 中规则的配置顺序十分重要

拦截器[参数：
 anon（anonymous） 拦截器表示匿名访问（即不需要登录即可访问）
 authc （authentication）拦截器表示需要身份认证通过后才能访问

```

## shiro的几大特色功能点

#### 认证(登录)

![JAVA_SHIRO6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO6.png?raw=true)

```
AuthorizationInfo，AuthenticationInfo:
在解释它们前首先必须要描述一下Shiro对于安全用户的界定：和大多数操作系统一样。用户具有角色和权限两种最基本的属性。例如，我的Windows登陆名称是learnhow，它的角色是administrator，而administrator具有所有系统权限。这样learnhow自然就拥有了所有系统权限。那么其他人需要登录我的电脑怎么办，我可以开放一个guest角色，任何无法提供正确用户名与密码的未知用户都可以通过guest来登录，而系统对于guest角色开放的权限极其有限。

同理，Shiro对用户的约束也采用了这样的方式。AuthenticationInfo代表了用户的角色信息集合，AuthorizationInfo代表了角色的权限信息集合。如此一来，当设计人员对项目中的某一个url路径设置了只允许某个角色或具有某种权限才可以访问的控制约束的时候，Shiro就可以通过以上两个对象来判断。

总结： AuthorizationInfo 类似于系统中角色于权限的集合
      AuthenticationInfo 类似于我的Windows登陆名称是learnhow
```



##### 认证的思路分析

```java
//我们在程序中和shiro打交道的就是Subject。他是整个shiro架构的门面
        Subject currentUser = SecurityUtils.getSubject();
// 测试当前的用户是否已经被认证. 即是否已经登录. 
// 调动 Subject 的 isAuthenticated() 
        if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // rememberme
            token.setRememberMe(true);
            try {
            	// 执行登录. 
                currentUser.login(token);
            } 
            // 若没有指定的账户, 则 shiro 将会抛出 UnknownAccountException 异常. 
            catch (UnknownAccountException uae) {
                log.info("----> There is no user with username of " + token.getPrincipal());
                return; 
            } 
            // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。 
            catch (IncorrectCredentialsException ice) {
                log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
                return; 
            } 
            // 用户被锁定的异常 LockedAccountException
            catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // 所有认证时异常的父类. 
            catch (AuthenticationException ae) {

            }
        }

认证的步骤：
1. 获取当前的 Subject. 调用 SecurityUtils.getSubject();
2. 测试当前的用户是否已经被认证. 即是否已经登录. 调用 Subject 的 isAuthenticated() 
3. 若没有被认证, 则把用户名和密码封装为 UsernamePasswordToken 对象
1). 创建一个表单页面
2). 把请求提交到 SpringMVC 的 Handler
3). 获取用户名和密码. 
4. 执行登录: 调用 Subject 的 login(AuthenticationToken) 方法. 
5. 自定义 Realm 的方法, 从数据库中获取对应的记录, 返回给 Shiro.
1). 实际上需要继承 org.apache.shiro.realm.AuthenticatingRealm 类
2). 实现 doGetAuthenticationInfo(AuthenticationToken) 方法. 
6. 由 shiro 完成对密码的比对. 

    
实现的实际示例：
表单页面
<form action="shiro/login" method="POST">
		username: <input type="text" name="username"/>
		<br><br>
		
		password: <input type="password" name="password"/>
		<br><br>
		
		<input type="submit" value="Submit"/>
	</form>
handler：
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {

	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, 
			@RequestParam("password") String password){
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(true);
            try {
            	System.out.println("1. " + token.hashCode());
            	// 执行登录. 
                currentUser.login(token);
            } 
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类. 
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            	System.out.println("登录失败: " + ae.getMessage());
            }
        }
		
		return "redirect:/list.jsp";
	}
	
}

准备realm
Subject.login(token) 方法会自动的去调用realm中的doGetAuthenticationInfo获取用户的角色和权限信息
这才是我们的重点Realm，真正理解Realm的实现还得回到上文提及的两个非常类似的对象AuthorizationInfo和AuthenticationInfo。因为Realm就是提供这两个对象的地方。
(这个ShiroRealm 需要提前在spring中配置好才能被使用)

public class ShiroRealm extends AuthorizingRealm {

    // handler中传递的token就是被传递到这里的
	@Override
    // 这里是认证用户
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");
		
		//1. 把 AuthenticationToken 转换为 UsernamePasswordToken 
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		//2. 从 UsernamePasswordToken 中来获取 username
		String username = upToken.getUsername();
		
		//3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
		System.out.println("从数据库中获取 username: " + username + " 所对应的用户信息.");
		
		//4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
		if("unknown".equals(username)){
			throw new UnknownAccountException("用户不存在!");
		}
		
		//5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常. 
		if("monster".equals(username)){
			throw new LockedAccountException("用户被锁定");
		}
		
		//6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
		//以下信息是从数据库中获取的.
		//1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象. 
		Object principal = username;
		//2). credentials: 密码.  密码的比对是由shiro帮我们完成的，这里暂时使用的是静态密码
        // 读取封装的SimpleAuthenticationInfo 对象shiro进行验证
		Object credentials = null; //"fc1709d0a95a6be30bc5926fdb7f22f4";
		if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if("user".equals(username)){
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		
		//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		//4). 盐值. 
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}


	//授权会被 shiro 回调的方法
    // 这里是判断用户权限
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//1. 从 PrincipalCollection 中来获取登录用户的信息
		Object principal = principals.getPrimaryPrincipal();
		
		//2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");
		}
		
		//3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		
		//4. 返回 SimpleAuthorizationInfo 对象. 
		return info;
	}
}



    

```

##### 密码的比对

```
shiro密码的比对:
通过 AuthenticatingRealm 的 credentialsMatcher 属性来进行的密码的比对!
在上面UsernamePasswordToken 保存了前台输入的密码
SimpleAuthorizationInfo保存了从数据库中读取的密码
他们是如何进行比对的？ 怎么比对的？ 是如何找到的？
```

![JAVA_SHIRO7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO7.png?raw=true)

##### shiro的密码MD5加密

![JAVA_SHIRO8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO8.png?raw=true)

![JAVA_SHIRO9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO9.png?raw=true)

```xml
1. 如何把一个字符串加密为 MD5 ？
替换当前 Realm 的 credentialsMatcher 属性. 直接使用 HashedCredentialsMatcher 对象, 并设置加密算法即可. 
   <!-- 
    	3. 配置 Realm 
    	3.1 直接配置实现了 org.apache.shiro.realm.Realm 接口的 bean
    -->     
    <bean id="jdbcRealm" class="com.atguigu.shiro.realms.ShiroRealm">
    	<property name="credentialsMatcher">
    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
    			<property name="hashAlgorithmName" value="MD5"></property>
                  <!--配置加密的次数 --> 
    			<property name="hashIterations" value="1024"></property>
    		</bean>
    	</property>
    </bean>

配置credentialsMatcher凭证匹配器后，会自动的将前台传递过来的明文在匹配的时候转换成MD5加密后的字符串
md5加密的核心代码是：
	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("user");;
		int hashIterations = 1024;
		
		Object result = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);
		System.out.println(result);
	}
```

##### MD5盐值加密

```java
解决的问题，即使加密的次数多次后，仍然存在，如果两个人的密码一样，那么加密后的密码也一样
我们希望即便两个人的密码一样，加密后的结果也不一样
就像做饭，材料都一样，怎么做出来菜不一样，就是加点盐，加点作料
	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("user");;
		int hashIterations = 1024;
		
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}
需要注意两个问题
1：我们加密后的结果需要把盐加进去
Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
2: 我们这个返回值中应该将盐带上
	//4). 盐值. 
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;

1. 为什么使用 MD5 盐值加密: 
2. 如何做到:
1). 在 doGetAuthenticationInfo 方法返回值创建 SimpleAuthenticationInfo 对象的时候, 需要使用
SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName) 构造器
2). 使用 ByteSource.Util.bytes() 来计算盐值. 
3). 盐值需要唯一: 一般使用随机字符串或 user id
4). 使用 new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations); 来计算盐值加密后的密码的值. 
    
 这样即使两个人密码一样，他们保存在数据中的密码也是不一样的，更加安全
 示例：
 controller中：
 /**
     * 登陆控制
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request,String userName,String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
        try{
            //调用subject.login(token)进行登录，会自动委托给securityManager,调用之前  
            subject.login(token);//会跳到我们自定义的realm中 
            return "/jsp/index/index";
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("用户名或密码错误");
            //request.setAttribute("error", "用户名或密码错误");
            return "/jsp/login/loginError";
        }
    }

realm:
/**
     * 用于登录认证，匹配数据库的信息.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {

        String userName = (String) token.getPrincipal();// 获取用户名

        // 根据用户输入的用户名在数据库进行匹配
        Sys_user user = userService.getUserByName(userName);
        //用户名存在则匹配密码
        if (user != null) {
             //1)principal：认证的实体信息，可以是userName，也可以是数据库表对应的用户的实体对象  
            Object principal = user.getUserName();

            //2)credentials：数据库中的密码  
            Object credentials = user.getPassword(); 

            //3)realmName：当前realm对象的name，调用父类的getName()方法即可  
            String realmName = getName();  

            //4)credentialsSalt盐值  
            ByteSource credentialsSalt = ByteSource.Util.bytes(userName);//使用用户名作为盐值  

            //根据用户的情况，来构建AuthenticationInfo对象,通常使用的实现类为SimpleAuthenticationInfo
            //5)与数据库中用户名和密码进行比对，密码盐值加密，第4个参数传入realName。
            SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt,realmName);
            return authcInfo;
        } else {
            return null;
        }
    }

```



##### shiro中的多realm验证

![JAVA_SHIRO10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO10.png?raw=true)

```xml
某些特定情况下，我们可能会将安全数据放在不同的数据库中，不通数据库使用不同的加密算法
我们需要将authenticator 配置为一个单独的bean，并配置给securityManager
<bean id="authenticator" 
    	class="org.apache.shiro.authc.pam.ModularRealmAuthenticator">
    	<property name="authenticationStrategy">
    		<bean class="org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy"></bean>
    	</property>
    </bean>

<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="cacheManager" ref="cacheManager"/>
        <property name="authenticator" ref="authenticator"></property>
        
        <property name="realms">
        	<list>
    			<ref bean="jdbcRealm"/>
    			<ref bean="secondRealm"/>
    		</list>
        </property>
        
        <property name="rememberMeManager.cookie.maxAge" value="10"></property>
    </bean>


<bean id="jdbcRealm" class="com.atguigu.shiro.realms.ShiroRealm">
    	<property name="credentialsMatcher">
    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
    			<property name="hashAlgorithmName" value="MD5"></property>
    			<property name="hashIterations" value="1024"></property>
    		</bean>
    	</property>
    </bean>
    
    <bean id="secondRealm" class="com.atguigu.shiro.realms.SecondRealm">
    	<property name="credentialsMatcher">
    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
    			<property name="hashAlgorithmName" value="SHA1"></property>
    			<property name="hashIterations" value="1024"></property>
    		</bean>
    	</property>
    </bean>

```

##### shiro 认证策略AuthenticationStrategy

```xml
上面说过shiro支持多realm 验证，那么多realm如何让验证通过了？
AuthenticationStrategy 接口的默认实现：
• FirstSuccessfulStrategy：只要有一个 Realm 验证成功即可，只返回第一个 Realm 身份验证成功的认证信息，其他的忽略；
• AtLeastOneSuccessfulStrategy：只要有一个Realm验证成功即可，和FirstSuccessfulStrategy 不同，将返回所有Realm身份验证成功的认证信
息；
• AllSuccessfulStrategy：所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息，如果有一个失败就失败了。
• ModularRealmAuthenticator 默认是 AtLeastOneSuccessfulStrategy策略

策略的配置：
 <bean id="authenticator" 
    	class="org.apache.shiro.authc.pam.ModularRealmAuthenticator">
        <property name="realms">
        	<list>
    			<ref bean="jdbcRealm"/>
    			<ref bean="secondRealm"/>
    		</list>
        </property>
    	<property name="authenticationStrategy">
    		<bean class="org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy"></bean>
    	</property>
    </bean>
```

#### 授权

##### 将realms 配置给SecurityManager

```
为什么需要将realms 配置给SecurityManager？
如果要实现授权需要在SecurityManager中读取realms ,所以在我们实现授权的时候需要将realms 配置给SecurityManager

如果将realms 配置给SecurityManager，那么authenticator 多realms中 如何获取realm 进行验证？
在shiro初始化的时候回将SecurityManager传入，并判断如果是authenticator 就将realms传给authenticator 
```

![JAVA_SHIRO11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO11.png?raw=true)

总结： 在我们配置realms 的时候，建议直接将realms 配置给SecurityManager

##### 什么是授权

```java
授权，也叫做访问控制，即在应用中控制谁能访问哪些资源（如访问页面、编辑数据、页面操作等）。在授权中需要了解几个关键对象：
主体（Subject 在 Shiro 中使用 Subject 代表该用户。用户只有授权后才允许访问相应的资源）、
资源（Resource 在应用中用户可以访问的 URL）、
权限（Permission 权限表示在应用中用户能不能访问某个资源）、
角色（Role 权限的集合，一般情况下会赋予用户角色而不是权限，即这样用户可以拥有一组权限，赋予权限时比较方便。典型的如：项目经理、技术总监、CTO、开发工程师等都是角色，不同的角色拥有一组不同的权限）。

Shiro 支持三种方式的授权：
– 编程式：通过写if/else 授权代码块完成 （这种方式需要在调用方写代码，所以不常用）
 // 测试是否有某一个角色. 调用 Subject 的 hasRole 方法. 
//角色是权限的组合，一个角色可以拥有多个权限
        if (currentUser.hasRole("schwartz")) {
            log.info("----> May the Schwartz be with you!");
        } else {
            log.info("----> Hello, mere mortal.");
            return; 
        }

//test a typed permission (not instance-level)
// 测试用户是否具备某一个行为(用户是否具有权限干某事). . 调用 Subject 的 isPermitted() 方法。 
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("----> You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

– 注解式：通过在执行的Java方法上放置相应的注解完成，没有权限将抛出相应的异常 (常用)

    
– JSP/GSP 标签：在JSP/GSP 页面通过相应的标签完成
需要导入
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>   
 实际使用：
 Welcome: <shiro:principal></shiro:principal>
	
	<shiro:hasRole name="admin">
	<br><br>
	<a href="admin.jsp">Admin Page</a>
	</shiro:hasRole>
	
	<shiro:hasRole name="user">
	<br><br>
	<a href="user.jsp">User Page</a>
	</shiro:hasRole>

默认拦截器
 Shiro 内置了很多默认的拦截器，比如身份验证、授权等相关的。默认拦截器可以参考org.apache.shiro.web.filter.mgt.DefaultFilter中的枚举的拦截器
```

![JAVA_SHIRO12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO12.png?raw=true)

![JAVA_SHIRO13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO13.png?raw=true)

![JAVA_SHIRO14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO14.png?raw=true)

##### 授权流程

```java
授权的realm 如何实现：

1. 授权需要继承 AuthorizingRealm 类, 并实现其 doGetAuthorizationInfo 方法
2. AuthorizingRealm 类继承自 AuthenticatingRealm, 但没有实现 AuthenticatingRealm 中的 
, 所以认证和授权只需要继承 AuthorizingRealm 就可以了. 同时实现他的两个抽象方法.
public class TestRealm extends AuthorizingRealm {

	//用于授权的方法. 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	//用于认证的方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

}

为什么实现了AuthorizingRealm ，shiro框架就能帮我们完成授权了？
subject的上层方法逐渐追下去，实际调用的realm的
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) 
```

![JAVA_SHIRO15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO15.png?raw=true)

多realm授权 同 多realm认证一样，ModularRealmAuthenticator 

![JAVA_SHIRO16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO16.png?raw=true)

realm授权 ：

```java

具体的实现：realm授权需要继承 AuthorizingRealm 类，实现doGetAuthorizationInfo 方法
//授权会被 shiro 回调的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//1. 从 PrincipalCollection 中来获取登录用户的信息
		Object principal = principals.getPrimaryPrincipal();
		
		//2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");
		}
		
		//3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		
		//4. 返回 SimpleAuthorizationInfo 对象. 
		return info;
	}
```

##### shiro 权限注解(重点)

```java
shiro注解，可以注解在controller层上，也可以注解在service层上的

@RequiresAuthentication：表示当前Subject已经通过login进行了身份验证；
即 Subject. isAuthenticated() 返回 true

@RequiresUser：表示当前 Subject 已经身份验证或者通过记住我登录的。

@RequiresGuest：表示当前Subject没有身份验证或通过记住我登录过，即是游客身份。(匿名访问)

@RequiresRoles(value={“admin”, “user”}, logical=Logical.AND)：
表示当前 Subject 需要角色 admin 和user

@RequiresPermissions (value={“user:a”, “user:b”},logical= Logical.OR)：
表示当前 Subject 需要权限 user:a 或user:b

public class ShiroService {
	
	@RequiresRoles({"admin"})
	public void testMethod(){
		System.out.println("testMethod, time: " + new Date());
		
		Session session = SecurityUtils.getSubject().getSession();
		Object val = session.getAttribute("key");
		
		System.out.println("Service SessionVal: " + val);
	}
没有权限则抛异常， 可以使用spring的声明式异常搞一个错误页面

这里需要注意的：
我们在开发的时候往往会在service层添加一个@Transactional注解。即让service方法开始的时候获取事务，这个时候这个service就已经是一个代理对象，这时候将@RequiresRoles({"admin"}) 等注解加在service层上就是空的，这时候就需要将注解加在controller上，不能让我们的service是代理的代理，否则在我们注入的时候会发生类型转换异常

```

#####  从数据表中初始化受保护的资源和权限

```xml
在上面，我们发现，受保护的资源和对应我们是配置在spring的配置文件中的
<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>       
        <!--  
        	配置哪些页面需要受保护. 
        	以及访问这些页面需要的权限. 
        	1). anon 可以被匿名访问
        	2). authc 必须认证(即登录)后才可能访问的页面. 
        	3). logout 登出.
        	4). roles 角色过滤器
        -->
        <property name="filterChainDefinitions">
            <value>
                /login.jsp = anon
                /shiro/login = anon
                /shiro/logout = logout
                
                /user.jsp = roles[user]
                /admin.jsp = roles[admin]
                
                # everything else requires authentication:
                /** = authc
            </value>
        </property>
    </bean>

这样配置如果受保护的资源和对应的权限过多，这样配置就显得过于繁琐了
我们还是希望这些配置数据库中的，然后通过sql取出来
如何实现:
<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>     
        <property name="filterChainDefinitionMap" ref="filterChainDefinitionMap"></property>
    </bean>

<!-- 配置一个 bean, 该 bean 实际上是一个 Map. 通过实例工厂方法的方式 -->
    <bean id="filterChainDefinitionMap" 
    	factory-bean="filterChainDefinitionMapBuilder" factory-method="buildFilterChainDefinitionMap"></bean>
    
    <bean id="filterChainDefinitionMapBuilder"
    	class="com.atguigu.shiro.factory.FilterChainDefinitionMapBuilder"></bean>

public class FilterChainDefinitionMapBuilder {

	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
//实际开发中只需要访问数据表，将map初始化就可以了
        
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put("/shiro/logout", "logout");
		map.put("/user.jsp", "authc,roles[user]");
		map.put("/admin.jsp", "authc,roles[admin]");
		map.put("/list.jsp", "user");
		
		map.put("/**", "authc");
		
		return map;
	}
	
}
```

![JAVA_SHIRO17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO17.png?raw=true)

#### 会话管理

```java
客户端和服务器的一次会话
最大特点就是：不依赖于底层容器（如web容器tomcat），不管 JavaSE 还是 JavaEE 环境都可以使用

会话相关的 API，与java中session操作方法类似，具体查看ppt

我们在javaweb还是建议httpsession。那么这个shiro中的session有什么意义？
在传统的web应用中，我们没办法在service层访问httpsession的，（不建议这么做，这么做的话就是一个嵌入式的）。

这时候我们有了shiro session 就可以在service访问httpsession了。
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "value12345");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}
}

这里两个session是互通的，这里service层是可以访问到HttpSession 中的内容的
public class ShiroService {	
	@RequiresRoles({"admin"})
	public void testMethod(){
		System.out.println("testMethod, time: " + new Date());
		
		Session session = SecurityUtils.getSubject().getSession();
		Object val = session.getAttribute("key");
		
		System.out.println("Service SessionVal: " + val);
	}
	
}
```

##### SessionDao

可以把session写入到数据库中，对session进行增删改查

![JAVA_SHIRO18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO18.png?raw=true)

```
 AbstractSessionDAO 提供了 SessionDAO 的基础实现，如生成会话ID等
• CachingSessionDAO 提供了对开发者透明的会话缓存的功能，需要设置相应的 CacheManager
• MemorySessionDAO 直接在内存中进行会话维护
• EnterpriseCacheSessionDAO 提供了缓存功能的会话维护，默认情况下使用 MapCache 实现，内部使用ConcurrentHashMap 保存缓存的会话。简单实现，开发建议实现这个

这个应用不多，具体实现使用查看ppt
```



#### 缓存

CacheManagerAware 接口

```xml
Shiro 内部相应的组件（DefaultSecurityManager）会自动检测相应的对象（如Realm）是否实现了CacheManagerAware 并自动注入相应的CacheManager,Realm就能自动缓存

（AuthorizingRealm 默认具有cache功能，他的类图体系中实现了CacheManagerAware）
在配置文件中我们也注入了caheManager
    <bean id="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
        <property name="cacheManagerConfigFile" value="classpath:ehcache.xml"/> 
    </bean>
	
	<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="cacheManager" ref="cacheManager"/>

        <property name="realms">
        	<list>
    			<ref bean="jdbcRealm"/>
    			<ref bean="secondRealm"/>
    		</list>
        </property>
        
    </bean>
Realm 缓存

实际开发的时候，我们可能会使用Redis来做缓存
```



#### RememberMe

记住我。

```
什么是RememberMe？
一些常用的网站是具有rememberme功能的，当我们选了记住我，下次进入网站就不需要再进行登录了。
说白了就是将cookie写到客户端。但是当我们访问一些敏感资源的时候，如支付，仍然需要身份认证

认证和记住我的区别？
认证：调用 subject.isAuthenticated()
记住我：调用 subject.isRemembered()

但是两者只能 两者二选一，即 subject.isAuthenticated()==true，则subject.isRemembered()==false；反之一样

建议：
访问一般网页：如个人在主页之类的，我们使用user 拦截器即可，user 拦截器只要用户登录(isRemembered() || isAuthenticated())过即可访问成功；
访问特殊网页：如我的订单，提交订单页面，我们使用authc 拦截器即可，authc 拦截器会判断用户是否是通过
Subject.login（isAuthenticated()==true）登录的，如果是才放行，否则会跳转到登录页面叫你重新登录。
```

##### 如何实现RememberMe

需要使用默认拦截器user

```java
@RequestMapping("/login")
	public String login(@RequestParam("username") String username, 
			@RequestParam("password") String password){
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(true);
            try {
            	System.out.println("1. " + token.hashCode());
            	// 执行登录. 
                currentUser.login(token);
            } 
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类. 
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            	System.out.println("登录失败: " + ae.getMessage());
            }
        }
		
		return "redirect:/list.jsp";
	}

这句代码token.setRememberMe(true); 就是实现的重点

```

##### 如何设置RememberMe时间

![JAVA_SHIRO19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SHIRO19.png?raw=true)

```xml
<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="cacheManager" ref="cacheManager"/>   
        <property name="realms">
        	<list>
    			<ref bean="jdbcRealm"/>
    			<ref bean="secondRealm"/>
    		</list>
        </property>
        
        <property name="rememberMeManager.cookie.maxAge" value="10"></property>
  </bean>

```

