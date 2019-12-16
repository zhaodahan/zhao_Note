# springBoot实战笔记



# 1：springBoot 中使用过滤器，且需在过滤器中使用环境配置中的参数

第一步： 正常为项目加上过滤器

```java
@Configuration
public class WebConfiguration {
    
    //加这个是过滤掉静态资源的请求
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    
    //这个方法是将自定义的Filter加入到注册的过滤链
    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
    
    public class MyFilter implements Filter {
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
		}

		@Override
		public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			HttpServletRequest request = (HttpServletRequest) srequest;
			System.out.println("this is MyFilter,url :"+request.getRequestURI());
			filterChain.doFilter(srequest, sresponse);
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
			// TODO Auto-generated method stub
		}
    }
}
```

(详情参考spring 学习笔记---->自定义Filter)



第二步：环境配置文件中加上配置，过滤器中引入配置读取类

![SpringBoot_Combat_Experience1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience1.png?raw=true)

```java
@Component
public class TokenFilter implements Filter {

	@Autowired
	private Environment env;

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String uri = request.getRequestURI();

		System.out.println("filter url:" + uri);
		// // 获取token的有效性；
		String token = request.getParameter("token");

		String url = env.getProperty("token.identify.url");
		String appId = env.getProperty("token.identify.appid");
```

![SpringBoot_Combat_Experience2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience2.png?raw=true)



第三步： 修改引入Filter的config

虽然第二步引入了读取配置的Environment 类，但是在Filter中我们无法进行类的自动注入，导致Environment  始终是空，所以我们修改一下config。

```java
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration  
public class Config implements WebApplicationInitializer {  
   
    @Override  
    public void onStartup(ServletContext servletContext) throws ServletException {  
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();  
        delegatingFilterProxy.setTargetBeanName("TokenFilter");  
        delegatingFilterProxy.setTargetFilterLifecycle(true);  
        FilterRegistration filterRegistration = servletContext.addFilter("TokenFilter",delegatingFilterProxy);  
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "*");  
    }  
   
}  
```



具体代码参考：

[gitHub示例代码参考](https://github.com/zhaodahan/zhao_project/tree/master/PriceCalculateManagement)




































![SpringBoot_Combat_Experience3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience3.png?raw=true)
![SpringBoot_Combat_Experience4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience4.png?raw=true)
![SpringBoot_Combat_Experience5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience5.png?raw=true)
![SpringBoot_Combat_Experience6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience6.png?raw=true)
![SpringBoot_Combat_Experience7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience7.png?raw=true)
![SpringBoot_Combat_Experience8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience8.png?raw=true)
![SpringBoot_Combat_Experience9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience9.png?raw=true)
![SpringBoot_Combat_Experience10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience10.png?raw=true)
![SpringBoot_Combat_Experience11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience11.png?raw=true)
![SpringBoot_Combat_Experience12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/SpringBoot_Combat_Experience12.png?raw=true) 