# SpringMVC基础回顾

## 什么是springMVC

```
spring 提供的最主流的基于 MVC 设计理念的优秀的Web 框架
```



## SpringMVC入门

```xml
springMVC构建web项目的基本步骤：
– 加入 jar 包
– 在 web.xml 中配置 DispatcherServlet
(Struts2是配置一个filter，springMvc是配置Servlet)
<!-- 配置 DispatcherServlet -->
	<servlet>
		<servlet-name>dispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<!-- 配置 DispatcherServlet 的一个初始化参数: 配置 SpringMVC 配置文件的位置和名称 -->
		<!-- 
			实际上也可以不通过 contextConfigLocation 来配置 SpringMVC 的配置文件, 而使用默认的.
			默认的配置文件为: /WEB-INF/<servlet-name>-servlet.xml
		-->
		<!--  
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:springmvc.xml</param-value>
		</init-param>
		-->
		<load-on-startup>1</load-on-startup>
        <!--让这个Servlet在一加载的时候就已经开始创建了-->
	</servlet>

	<servlet-mapping>
		<servlet-name>dispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

– 加入 Spring MVC 的配置文件
配置自定扫描的包-->
<context:component-scan base-package="com.atguigu.springmvc"></context>
<！--配置视图解析器：如何把 handler方法返回值解析为实际的物理视图-->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
<property name="prefix"value="WEB-INF/views/"></property>
<property name="suffix" value=".Jsp"></property>
</bean>
                                    
– 编写处理请求的处理器，并标识为处理器
@Controller
public class HelloWorld {
	/**
	 * 1. 使用 @RequestMapping 注解来映射请求的 URL
	 * 2. 返回值会通过视图解析器解析为实际的物理视图, 对于 InternalResourceViewResolver 视图解析器, 会做如下的解析:
	 * 通过 prefix + returnVal + 后缀 这样的方式得到实际的物理视图, 然会做转发操作
	 * 
	 * /WEB-INF/views/success.jsp
	 * 
	 * @return
	 */
	@RequestMapping("/helloworld")
	public String hello(){
		System.out.println("hello world");
		return "success";
	}
	
}

– 编写视图

```



## 使用 @RequestMapping 映射请求

```java
@RequestMapping注解在springMVC中主要功能是为控制器指定可以处理哪些 URL 请求 (这个注解既可以修饰方法，又可以修饰类，修饰类的时候类似于请求根目录,修饰方法是进一步的细分映射信息，如： /order/list )

@RequestMapping 的 value、method、params 及 heads分别表示请求 URL、请求方法、请求参数及请求头的映射条
件，他们之间是与的关系，联合使用多个条件可让请求映射更加精确化。params 和 headers支持简单的表达式(不常用)
    
他还常用的是: 使用 method 属性来指定请求方式
@RequestMapping(value = "/testMethod", method = RequestMethod.POST)

params 和 headers支持简单的表达式
– param1: 表示请求必须包含名为 param1 的请求参数
– !param1: 表示请求不能包含名为 param1 的请求参数
– param1 != value1: 表示请求包含名为 param1 的请求参数，但其值
不能为 value1
– {“param1=value1”, “param2”}: 请求必须包含名为 param1 和param2
的两个请求参数，且 param1 参数的值必须为 value1
	@RequestMapping(value = "testParamsAndHeaders", params = { "username",
			"age!=10" }, headers = { "Accept-Language=en-US,zh;q=0.8" })
 
@RequestMapping 还支持 Ant 风格的 URL： (请求链接支持通配符，但是不常用)
– ?：匹配文件名中的一个字符
– *：匹配文件名中的任意字符
– **：** 匹配多层路径
– /user/*/createUser: 匹配
/user/aaa/createUser、/user/bbb/createUser 等 URL
– /user/**/createUser: 匹配
/user/createUser、/user/aaa/bbb/createUser 等 URL
– /user/createUser??: 匹配
/user/createUseraa、/user/createUserbb 等 URL

```

### @PathVariable 映射 URL 绑定的占位符

```java
通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中：URL 中的 {xxx} 占位符可以通过
@PathVariable("xxx") 绑定到操作方法的入参中。(该功能是SpringMVC 向 REST 目标挺进)
（这里需要注意占位符中的名字需要和@PathVariable 中的一致）
	/**
	 * @PathVariable 可以来映射 URL 中的占位符到目标方法的参数中.
	 * @param id
	 * @return
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable: " + id);
		return SUCCESS;
	}
```

### REST 请求风格

![JAVA_SPRINGMVC1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC1.png?raw=true)

```xml
springMVC如何来支持REST 请求风格 ，get,post可以正常使用，但是put和delete前端并没有定义这样的方式，spring 提供HiddenHttpMethodFilter 将post，通过参数_method转为 DELETE 或 POST 请求 
<!-- 
	配置 org.springframework.web.filter.HiddenHttpMethodFilter: 可以把 POST 请求转为 DELETE 或 POST 请求 
	-->
	<filter>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
前端页面中使用
	<form action="springmvc/testRest/1" method="post">
		<input type="hidden" name="_method" value="PUT"/>
		<input type="submit" value="TestRest PUT"/>
	</form>
后台代码中
    /**
	 * Rest 风格的 URL.
     以 CRUD 为例: 
新增: /order    POST 
修改: /order/1  PUT update?id=1 
获取: /order/1  GET get?id=1 
删除: /order/1  DELETE delete?id=1
	 * 
	 * 如何发送 PUT 请求和 DELETE 请求呢 ? 
1. 需要配置 HiddenHttpMethodFilter 
2. 需要发送 POST 请求
3. 需要在发送 POST 请求时携带一个 name="_method" 的隐藏域, 值为 DELETE 或 PUT
	 * 
	 * 在 SpringMVC 的目标方法中如何得到 id 呢? 使用 @PathVariable 注解
	 * 
	 */
@RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
	public String testRestDelete(@PathVariable Integer id) {
		System.out.println("testRest Delete: " + id);
		return SUCCESS;
	}
```



## 映射请求参数 & 请求头

```java
Spring MVC 对控制器处理方法签名的限制是很宽松的（我们一般不会强加限制）.但是必要时可以对方法及方法入参标注相应的注解,（
@PathVariable
、@RequestParam、@RequestHeader 等）、SpringMVC 框架会将 HTTP 请求的信息绑定到相应的方法入参中，并根据方法的返回值类型做出相应的后续处理。

```

### @RequestParam 绑定请求参数值

```java
在方法入参处使用 @RequestParam 可以把请求参数传递给请求方法
– value：参数名
– required：是否必须。默认为 true, 表示请求参数中必须包含对应的参数，若不存在，将抛出异常
	/**
	 * @RequestParam 来映射请求参数. value 值即请求参数的参数名 required 该参数是否必须. 默认为 true
	 *               defaultValue 请求参数的默认值
	 */
	@RequestMapping(value = "/testRequestParam")
	public String testRequestParam(
			@RequestParam(value = "username") String un,
			@RequestParam(value = "age", required = false, defaultValue = "0") int age) {
		System.out.println("testRequestParam, username: " + un + ", age: "
				+ age);
		return SUCCESS;
	}
但是，使用如果我们使用@RequestParam 注解参数后，要求所有的参数都存在，如果有一个参数没有带，就会找不到处理器，这点不够灵活。使用required = false来解决这个问题。
```

### @RequestHeader 绑定请求报头的属性值

```java
请求头包含了若干个属性，服务器可据此获知客户端的信息，通过 @RequestHeader 即可将请求头中的属性值绑定到处理方法的入参中
	/**
	 * 了解: 映射请求头信息 用法同 @RequestParam
	 */
	@RequestMapping("/testRequestHeader")
	public String testRequestHeader(
			@RequestHeader(value = "Accept-Language") String al) {
		System.out.println("testRequestHeader, Accept-Language: " + al);
		return SUCCESS;
	}

```

### @CookieValue 绑定请求中的 Cookie 值

```java
@CookieValue 可让处理方法入参绑定某个 Cookie 值
	/**
	 * 了解:不常用
	 * 
	 * @CookieValue: 映射一个 Cookie 值. 属性同 @RequestParam
	 */
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
		System.out.println("testCookieValue: sessionId: " + sessionId);
		return SUCCESS;
	}
```

### 使用 POJO 对象绑定请求参数值(最常用)

```java
我们请求的常是表单，这个表单通常对应着一个类的对象。这时候一个一个的去映射属性，就十分的麻烦，springMVC提供了一种机制，按请求参数名和 POJO 属性名进行自动匹配，自动为该对象填充属性值。支持级联属性。
如：dept.deptId、dept.address.tel 等
//前端传值
<form action="springmvc/testPojo" method="post">
		username: <input type="text" name="username"/>
		<br>
		password: <input type="password" name="password"/>
		<br>
		email: <input type="text" name="email"/>
		<br>
		age: <input type="text" name="age"/>
		<br>
		city: <input type="text" name="address.city"/>
		<br>
		<!--级联属性-->
		province: <input type="text" name="address.province"/> 
		<br>
		<input type="submit" value="Submit"/>
	</form>
//对应的pojo	
   public class User {
	private Integer id;
	private String username;
	private String password;
	private String email;
	private int age;
	private Address address;
     }
     

	/**
	 * Spring MVC 会按请求参数名和 POJO 属性名进行自动匹配， 自动为该对象填充属性值。支持级联属性。
	 * 如：dept.deptId、dept.address.tel 等
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user) {
		System.out.println("testPojo: " + user);
		return SUCCESS;
	}

```

### 使用 **Servlet**  原生API 作为入参 (也常用)

```java
在某一些请求中我们仍然需要使用Servlet的原生API，如request等。 springMVC的controller从功能本质上来说也还是Servlet(所以在对controller进行参数赋值的时候，会将Servlet中的请求参数封装进controller)，所以在这里我们仍然可以使用原生的Servlet API
	/**
	 * 可以使用 Serlvet 原生的 API 作为目标方法的参数 具体支持以下类型
	 * 
	 * HttpServletRequest 
	 * HttpServletResponse 
	 * HttpSession
	 * java.security.Principal 
	 * Locale 
	 *InputStream 
	 * OutputStream 
	 * Reader 
	 * Writer
	 * @throws IOException 
	 */
	@RequestMapping("/testServletAPI")
	public void testServletAPI(HttpServletRequest request,
			HttpServletResponse response, Writer out) throws IOException {
		System.out.println("testServletAPI, " + request + ", " + response);
		out.write("hello springmvc");
//		return SUCCESS;
	}
```



## 处理模型数据

```java
mvc设计模式是我们发送一个请求到控制器，控制器去调用方法，获得一个返回值，然后转发到页面，我们需要将返回值封装到页面并显示出来。

问题，我们如何将模型数据(获取的返回值)放入到一个对象(通常是请求域)，封装到页面中？
Spring MVC 提供了以下几种途径输出模型数据：
– ModelAndView(常用): 处理方法返回值类型为 ModelAndView时, 方法体即可通过该对象添加模型数据，他既包含视图信息，又包含模型数据
/**
	 * 目标方法的返回值可以是 ModelAndView 类型。 
	 * 其中可以包含视图和模型信息
	 * SpringMVC 会把 ModelAndView 的 model 中数据放入到 request 域对象中. (从springMVC源码中看出来的)
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView(){
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		
		//添加模型数据到 ModelAndView 中.
		modelAndView.addObject("time", new Date());
		
		return modelAndView;
	}

// 页面：	time: ${requestScope.time } 他是真的将模型数据放在请求域中
从springMVC的源码可知，无论你返回的是什么类型，最后都会被封装成ModelAndView，然后进行统一的处理

– Map 及 Model(常用): 入参为org.springframework.ui.Model、org.springframework.ui.ModelMap 或 java.uti.Map 时，处理方法返回时，Map中的数据会自动添加到模型中。
/**
	 * 目标方法可以添加 Map 类型(从类图可以看出实际上也可以是 Model 类型或 ModelMap 类型(了解))的参数. 
	 * @param map springMVC为这里传入的是实际个modeMap对象（多态思想）
	 * @return
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map){
		System.out.println(map.getClass().getName()); //BindingAwareModelMap
		map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
		return SUCCESS;
	}
这种形式主要使用的还是放在map中， 这里SUCCESS会放在modelandVeiw的veiw中，map中的值会放在model中

– @SessionAttributes: 不同于之前将模型数据放在请求域request中，这种机制将模型中的某个属性放到HttpSession 中,且 @SessionAttributes是注解在控制器类上，以便多个请求之间可以共享这个属性。

@SessionAttributes 通过属性名指定需要放到会话中的属性（指定属性value，都是数组），还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中(types)

@SessionAttributes(value={"user"}, types={String.class})
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	/**
	 * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外(实际上使用的是 value 属性值),
	 * 还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中(实际上使用的是 types 属性值)
	 * 
	 * 注意: 该注解只能放在类的上面. 而不能修饰放方法. 
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map){
		User user = new User("Tom", "123456", "tom@atguigu.com", 15);
		map.put("user", user); //符合value
		map.put("school", "atguigu");  //符合type
        //这里面的值即在request中，又在session中，
		return SUCCESS;
	}

 //页面 session user: ${sessionScope.user }

– @ModelAttribute: 方法入参标注该注解后, 入参的对象就会放到数据模型中
他的使用场景： 我们需要完成一个修改操作，但是类其中有个字段不能被修改。
我们常做的就是表单传入的是只有其他的，不包含这个不能被修改的字段，但是我们拿着这样的值去更新，则这条记录中不能被修改的字段就是null,如果数据不是敏感字段，数量少，我们可以使用hidden隐藏字段。还有一种做法就是在更新前冲数据库中查询出这个字段的值。

 springMVC为我们提供更优雅的解决方案：@ModelAttribute
 (表单对对应属性赋值前，这个对象不是new的，而是从数据库中获取的) 这时候我们赋值，赋了一个值，其余值不是null，而是从数据库中查询的值。
    
//页面
    <!--  
		模拟修改操作
		1. 原始数据为: 1, Tom, 123456,tom@atguigu.com,12
		2. 密码不能被修改.
		3. 表单回显, 模拟操作直接在表单填写对应的属性值
	-->
	<form action="springmvc/testModelAttribute" method="Post">
		<input type="hidden" name="id" value="1"/>
		username: <input type="text" name="username" value="Tom"/>
		<br>
		email: <input type="text" name="email" value="tom@atguigu.com"/>
		<br>
		age: <input type="text" name="age" value="12"/>
		<br>
		<input type="submit" value="Submit"/>
	</form>
	<br><br>
 
// 后台：

	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(User user){
		System.out.println("修改: " + user);
		return SUCCESS;
	}
	/**
	 * 1. 有 @ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用! 
	 * 2. @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参(这样注解的话，是将注解放在目标方法的入参上，告诉他用这个key去取), 其 value 属性值有如下的作用:
	 * 1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
	 * 2). SpringMVC 会以 value 为 key, POJO 类型的对象为 value, 存入到 request 中. 
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id, 
			Map<String, Object> map){
		System.out.println("modelAttribute method");
		if(id != null){
			//模拟从数据库中获取对象
			User user = new User(1, "Tom", "123456", "tom@atguigu.com", 12);
			System.out.println("从数据库中获取一个对象: " + user);
			
			map.put("user", user);
 //这里方法将user放入到请求域中，一定有一个时候将其取出，并将表单中修改的数据放入到这个bean中，然后将这个bean赋值到对应的请求方法上           
		}
	}   
@ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用!  
/**
	 * 运行流程:
	 * 1. 执行 @ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放入到了 Map 中. 键为: user
	 * 2. SpringMVC 从 Map 中取出 User 对象, 并把表单的请求参数赋给该 User 对象的对应属性.
	 * 3. SpringMVC 把上述对象传入目标方法的参数. 
	 * 
	 * 注意: 在 @ModelAttribute 修饰的方法中, 放入到 Map 时的键需要和目标方法入参类型的第一个字母小写的字符串一致!
	 * 

	 * 源代码分析的流程
	 * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中.
	 * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
	 * 1). 创建 WebDataBinder 对象:
	 * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写. 
	 * *注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute 
	 * 的 value 属性值 
	 * 
	 * ②. 确定 target 属性:
	 * 	> 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok
	 * 	> *若不存在: 则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
	 * 获取 attrName 所对应的属性值. 若 session 中没有对应的属性值, 则抛出了异常. 
	 * 	> 若 Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key
	 * 和 attrName 相匹配, 则通过反射创建了 POJO 对象
	 * 
	 * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性. 
	 * 3). *SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel. 
	 * 近而传到 request 域对象中. (可以在页面获取)
	 * 4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参. 
	 */    
```

![JAVA_SPRINGMVC2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC2.png?raw=true)

总结：

```java
	
* SpringMVC 确定目标方法 POJO 类型入参的过程
	 * 1. 确定一个 key:
	 * 1). 若目标方法的 POJO 类型的参数木有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
	 * 2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值. 
	 * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
	 * 1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到. 
	 * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰, 
	 * 若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
	 * 对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常. 
	 * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
	 * 会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
	 * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中. 
```



## 视图和视图解析器

![JAVA_SPRINGMVC3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC3.png?raw=true)

```java
视图的作用是渲染模型数据，将模型里的数据以某种形式呈现给客户。为了实现视图模型和具体实现技术的解耦，Spring 在
org.springframework.web.servlet 包中定义了一个高度抽象的 View接口：

视图对象由视图解析器负责实例化。

请求处理方法执行完成后，最终返回一个 ModelAndView对象，它包含了逻辑名和模型对象的视图。（返回 String，View 或 ModeMap 等类型的处理方法，Spring MVC 也会在内部将它们装配成一个ModelAndView 对象）Spring MVC 借助视图解析器（ViewResolver）得到最终的视图对象（View）。每一个请求都会创建一个新的view对象

最终的视图可以是 JSP ，也可能是Excel、JFreeChart 等各种表现形式的视图。对于最终究竟采取何种视图对象对模型数据进行渲染，处理器并不关心，处理器工作重点聚焦在生产模型数据的工作上，从而实现 MVC 的充分解耦

视图解析器的最终作用就是将逻辑视图转为物理视图。

InternalResourceViewResolver：JSP 是最常见的视图技术，可以使用InternalResourceViewResolver 作为视图解析器
(他是默认的也是最常使用的视图解析器)
<!-- 配置视图解析器: 如何把 handler 方法返回值解析为实际的物理视图 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>

```

![JAVA_SPRINGMVC4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC4.png?raw=true)

### 具体的视图解析器

```java
若项目中存在JSTL，则 SpringMVC 会自动把视图由InternalResourceView 转为 JstlView
若使用 JSTL 的 fmt 标签则需要在 SpringMVC 的配置文件中配置国际化资源文件(不太常用)
若希望直接响应通过 SpringMVC 渲染的页面，可以使用 mvc:view-controller 标签实现
<!-- 配置直接转发的页面，不通过controller， 常用于静态资源 -->
	<!-- 可以直接相应转发的页面, 而无需再经过 Handler 的方法.  -->
	<mvc:view-controller path="/success" view-name="success"/>
但是配置了这个标签，改变了请求映射方式，发现以前的请求链接不好用了。

解决方法
<!-- 在实际开发中通常都需配置 mvc:annotation-driven 标签 -->
	<mvc:annotation-driven></mvc:annotation-driven>
```

### 自定义视图

```java
实际上springMVC去整合其他的视图，比如整合Excel，JFreeChart。都是通过自定义视图来完成。整合Excel为我们提供了一个抽象类。
@Component
public class HelloView implements View{

	@Override
	//返回内容类型
	public String getContentType() {
		return "text/html";
	}

	@Override
	//渲染视图
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print("hello view, time: " + new Date());
	}

}

这个自定义视图如何显示到页面上？ InternalResourceViewResolver是不能满足我们的需求，我们需要额外的配置一些视图解析器
	<!-- 配置视图  BeanNameViewResolver 解析器: 使用视图的名字来解析视图 -->
	<!-- 通过 order 属性来定义视图解析器的优先级, order 值越小优先级越高 -->
	<bean class="org.springframework.web.servlet.view.BeanNameViewResolver">
		<property name="order" value="100"></property>
	<!--常用的InternalResourceViewResolver应该放在后面，BeanNameViewResolver 不行了，再去使用常用的
	InternalResourceViewResolver的优先级是Integer的最大值，所以我们只要配置了一个值就比他的优先级高
	这个道理类比：去菜馆，想尝尝鲜，如果其他地方的味道不行，再换回老地方。
	-->
	</bean>

```

![JAVA_SPRINGMVC5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC5.png?raw=true)

### 重定向视图

```java
前面所讲的视图都是通过转发去的， 那么如何重定向视图了？
一般情况下，控制器方法返回字符串类型的值会被当成逻辑视图名处理
如果返回的字符串中带 forward: 或 redirect: 前缀时，SpringMVC 会对他们进行特殊处理：将 forward: 和
redirect: 当成指示符，其后的字符串作为 URL 来处理
– redirect:success.jsp：会完成一个到 success.jsp 的重定向的操作
– forward:success.jsp：会完成一个到 success.jsp 的转发操作

```

源码解析实现机制：

![JAVA_SPRINGMVC6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC6.png?raw=true)

## SpringMVC 表单标签 &处理静态资源

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--  
	SpringMVC 处理静态资源:
	1. 为什么会有这样的问题:
	优雅的 REST 风格的资源URL 不希望带 .html 或 .do 等后缀
	若将 DispatcherServlet 请求映射配置为 /, 
	则 Spring MVC 将捕获 WEB 容器的所有请求, 包括静态资源的请求, SpringMVC 会将他们当成一个普通请求处理, 
	因找不到对应处理器将导致错误。
	2. 解决: 在 SpringMVC 的配置文件中配置 <mvc:default-servlet-handler/>解决静态资源问题，且
还需要加上<mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>	
让我们能正常访问非静态资源
-->
<!-- 配置视图解析器 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>
	
	<!--  
		default-servlet-handler 将在 SpringMVC 上下文中定义一个 DefaultServletHttpRequestHandler,
		它会对进入 DispatcherServlet 的请求进行筛查, 如果发现是没有经过映射的请求, 就将该请求交由 WEB 应用服务器默认的 Servlet 处理. 如果不是静态资源的请求，才由 DispatcherServlet 继续处理

   总结： 就是对springmvc中的静态资源提供一个默认的Servlet进行响应
		一般 WEB 应用服务器默认的 Servlet 的名称都是 default.
		若所使用的 WEB 服务器的默认 Servlet 名称不是 default，则需要通过 default-servlet-name 属性显式指定
		
	-->
	<mvc:default-servlet-handler/>

	<mvc:annotation-driven></mvc:annotation-driven>	
	

<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".delete").click(function(){
			var href = $(this).attr("href");
			$("form").attr("action", href).submit();			
			return false;
		});
	})
</script>

    <!--专用于发送post请求的form-->
	<form action="" method="POST">
		<input type="hidden" name="_method" value="DELETE"/>
	</form>

<c:forEach items="${requestScope.employees }" var="emp">
				<tr>
					<td>${emp.id }</td>
					<td>${emp.lastName }</td>
					<td>${emp.email }</td>
					<td>${emp.gender == 0 ? 'Female' : 'Male' }</td>
					<td>${emp.department.departmentName }</td>
					<td><a href="emp/${emp.id}">Edit</a></td>
					<td><a class="delete" href="emp/${emp.id}">Delete</a></td>
 <!--通过js将上面的两个get请求转换为post请求，好应用HiddenHttpMethodFilter，将post请求转换为REST风格的delete，put-->
				</tr>
</c:forEach>

注意开发的时候都建议使用绝对路径${pageContext.request.contextPath }
```



## 数据转换 & 数据格式化 & 数据校验

![JAVA_SPRINGMVC7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC7.png?raw=true)

![JAVA_SPRINGMVC8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC8.png?raw=true)

```java
		<!--  
			1. 数据类型转换 （当我们需要传时间的时候，页面中传入的是字符串类型，后面需以日期格式接收）
			2. 数据类型格式化 (字符串需要以一定格式传入)
			3. 数据校验. 
			1). 如何校验 ? 注解 ?
			①. 使用 JSR 303 验证标准
			②. 加入 hibernate validator 验证框架的 jar 包
			③. 在 SpringMVC 配置文件中添加 <mvc:annotation-driven />
			④. 需要在 bean 的属性上添加对应的注解
			⑤. 在目标方法 bean 类型的前面添加 @Valid 注解
			2). 验证出错转向到哪一个页面 ?
			注意: 需校验的 Bean 对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其他的入参
			3). 错误消息 ? 如何显示, 如何把错误消息进行国际化
		-->
		Birth: <form:input path="birth"/>
		<form:errors path="birth"></form:errors>
Spring MVC 主框架将 ServletRequest 对象及目标方法的入参实例传递给 WebDataBinderFactory 实例，以创建 DataBinder 实例对象.
            
DataBinder 调用装配在 Spring MVC 上下文中的ConversionService 组件进行数据类型转换、数据格式化工作。将 Servlet 中的请求信息填充到入参对象中

调用 Validator 组件对已经绑定了请求消息的入参对象进行数据合法性校验，并最终生成数据绑定结果BindingData 对象

Spring MVC 抽取 BindingResult 中的入参对象和校验错误对象，将它们赋给处理方法的响应入参

```

如何写自定义的类型转换器，进行自定义的类型转换

```java
Spring MVC 上下文中内建了很多转换器，可完成大多数 Java 类型的转换工作。一般情况下我们不需要来定义这个类型转换器。	
ConversionService 是 Spring 类型转换体系的核心接口
可以利用 ConversionServiceFactoryBean 在 Spring 的 IOC容器中定义一个 ConversionService. Spring 将自动识别出IOC 容器中的 ConversionService，并在 Bean 属性配置及Spring MVC 处理方法入参绑定等场合使用它进行数据的转换

可通过 ConversionServiceFactoryBean 的 converters 属性注册自定义的类型转换器

<mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>	
	
	<!-- 配置 ConversionService -->
	<bean id="conversionService"
		class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
		<property name="converters">
			<set>
				<ref bean="employeeConverter"/>
			</set>
		</property>	
	</bean>
	
  页面需求：
  <form action="testConversionServiceConverer" method="POST">
		<!-- lastname-email-gender-department.id 例如: GG-gg@atguigu.com-0-105 -->
		Employee: <input type="text" name="employee"/>
		<input type="submit" value="Submit"/>
	</form>
	
 后台：
 	@RequestMapping("/testConversionServiceConverer")
	public String testConverter(@RequestParam("employee") Employee employee){
		System.out.println("save: " + employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}
自定义的转换器
@Component
public class EmployeeConverter implements Converter<String, Employee> {

	@Override
	public Employee convert(String source) {
		if(source != null){
			String [] vals = source.split("-");
			//GG-gg@atguigu.com-0-105
			if(vals != null && vals.length == 4){
				String lastName = vals[0];
				String email = vals[1];
				Integer gender = Integer.parseInt(vals[2]);
				Department department = new Department();
				department.setId(Integer.parseInt(vals[3]));
				
				Employee employee = new Employee(null, lastName, email, gender, department);
				System.out.println(source + "--convert--" + employee);
				return employee;
			}
		}
		return null;
	}

}

```

### mvc:annotation-driven

```java
我们会在什么情况下使用 mvc:annotation-driven 配置。
当我们使用了配置
<mvc:view-controller path="/success" view-name="success"/>
或者
<mvc:default-servlet-handler/>
之后，原有的映射无法生效了， 这时候我们使用mvc:annotation-driven 配置 能让恢复正常的配置

 还有就是使用自定义转换器的时候使用到他
 <mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>	
 
 我们实际开发中一般都需要配置上他，那他到底都做了些什么？
 
 会自动注册RequestMappingHandlerMapping、RequestMappingHandlerAdapter 与
ExceptionHandlerExceptionResolver 三个bean。
• 还将提供以下支持：
– 支持使用 ConversionService 实例对表单参数进行类型转换
– 支持使用 @NumberFormat annotation、@DateTimeFormat
注解完成数据类型的格式化
– 支持使用 @Valid 注解对 JavaBean 实例进行 JSR 303 验证
– 支持使用 @RequestBody 和 @ResponseBody 注解

```

![JAVA_SPRINGMVC9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC9.png?raw=true)

### 数据格式化

```java
在实体类上加入注解	
    @Past
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birth;
	
	@NumberFormat(pattern="#,###,###.#")
	private Float salary;

要让这些注解生效，需要在配置文件中加上 <mvc:annotation-driven />

数据格式化步骤:(字符串转日期为例)
1：在springMVC配置文件中加上<mvc:annotation-driven /> 注解
2：在目标类的目标属性上加上注解
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birth;
3： 我们我们配置了自定义类型转换器还想使用date转换功能，则需要使用
	<mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>	
	<!-- 配置 ConversionService的时候需要使用FormattingConversionServiceFactoryBean -->
	<bean id="conversionService"
		class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
		<property name="converters">
			<set>
				<ref bean="employeeConverter"/>
			</set>
		</property>	
	</bean>
4： 如果类型转换出错，错误信息就在
@RequestMapping(value="/emp", method=RequestMethod.POST)
	public String save( Employee employee, BindingResult result){
		System.out.println("save: " + employee);
		
		if(result.getErrorCount() > 0){
			System.out.println("出错了!");
			
			for(FieldError error:result.getFieldErrors()){
				System.out.println(error.getField() + ":" + error.getDefaultMessage());
			}
			
			//若验证出错, 则转向定制的页面
			map.put("departments", departmentDao.getDepartments());
			return "input";
		}
		
		employeeDao.save(employee);
		return "redirect:/emps";
	}

```

### 数据校验

![JAVA_SPRINGMVC10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC10.png?raw=true)

```java
3. 数据校验. （字符串不为null ,email必须是一个合法的email地址， 电话号码，身份证号码都必须合法）
			1). 如何校验 ? 注解 ?
			①. 使用 JSR 303 验证标准
			②. 加入 hibernate validator 验证框架的 jar 包
			③. 在 SpringMVC 配置文件中添加 <mvc:annotation-driven />
<mvc:annotation-driven/> 会默认装配好一个LocalValidatorFactoryBean，通过在处理方法的入参上标注 @valid 注解即可让 Spring MVC 在完成数据绑定后执行数据校验的工作

			④. 需要在 bean 的属性上添加对应的注解
			⑤. 在目标方法 bean 类型的前面添加 @Valid 注解
@RequestMapping(value="/emp", method=RequestMethod.POST)
public String save(@Valid Employee employee, Errors result, Map<String, Object> map){....}
BindingResult其实也是Errors

			2). 验证出错转向到哪一个页面 ?
注意: 需校验的 Bean 对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其他的入参

			3). 错误消息 ? 如何显示, 如何把错误消息进行国际化
			<form:errors path=“userName”>
			
JSR 303 是 Java 为 Bean 数据合法性校验提供的标准框架，它已经包含在 JavaEE 6.0 中 .JSR 303 通过在 《Bean 属性上》标注类似于 @NotNull、@Max等标准的注解指定校验规则，并通过标准的验证接口对 Bean进行验证

Hibernate Validator 是 JSR 303 的一个参考实现，除支持所有标准的校验注解外，它还支持一些扩展注解

```



## 处理 JSON：使用 HttpMessageConverter

十分常用

```java
如何处理json
	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson(){
		return employeeDao.getAll();
	}
@ResponseBody 注解将我们要返回的值转换为json.前端对获取的json进行处理

```

json处理原理HttpMessageConverter

![JAVA_SPRINGMVC11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC11.png?raw=true)

```
springMVC提供一个HttpMessageConverter<T> 负责将请求信息转换为一个对象（类型为 T），将对象（类型为 T）输出为响应信息，在dispatchservlet调用目标方法的时候会自动根据我们的环境自动装配对应的HttpMessageConverter集合
```

![JAVA_SPRINGMVC12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC12.png?raw=true)



## 国际化

```java
关于国际化:
		1. 在页面上能够根据浏览器语言设置的情况对文本(不是内容), 时间, 数值进行本地化处理
		2. 可以在 bean 中获取国际化资源文件 Locale 对应的消息
		3. 可以通过超链接切换 Locale, 而不再依赖于浏览器的语言设置情况
		前端：		
	<a href="i18n?locale=zh_CH">中文</a>
	<br><br>
	<a href="i18n?locale=en_US">英文</a>
		
		解决: 在进行任何国际化之前都需要先配置国际化资源文件
		i18n.properties：
		i18n.user=User
         i18n.password=Password
         
         i18n_en_US.properties：
         i18n.user=User
         i18n.password=Password
         
         i18n_zh_CN.properties
         i18n.user=\u7528\u6237\u540D
         i18n.password=\u5BC6\u7801
		1. 使用 JSTL 的 fmt 标签
			<fmt:message key="i18n.user"></fmt:message>
		2. 在 bean 中注入 ResourceBundleMessageSource 的示例, 使用其对应的 getMessage 方法即可
	<!-- 配置国际化资源文件 -->
	<bean id="messageSource"
		class="org.springframework.context.support.ResourceBundleMessageSource">
		<property name="basename" value="i18n"></property>
	</bean>
		3. 配置 LocalResolver 和 LocaleChangeInterceptor
    <!-- 配置 SessionLocalResolver -->
	<bean id="localeResolver"
		class="org.springframework.web.servlet.i18n.SessionLocaleResolver"></bean>
 
<!-- 配置 LocaleChanceInterceptor -->
 <mvc:interceptors>		
		<bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor"></bean>
	</mvc:interceptors>
后台代码控制：
    @Autowired
	private ResourceBundleMessageSource messageSource; 
    @RequestMapping("/i18n")
	public String testI18n(Locale locale){
		String val = messageSource.getMessage("i18n.user", null, locale);
		System.out.println(val); 
		return "i18n";
	}

```

![JAVA_SPRINGMVC13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC13.png?raw=true)

## 文件的上传

```java
Spring MVC使用MultipartResolver 实现的文件上传。
Spring 用Jakarta Commons FileUpload 技术实现了一个MultipartResolver 实现类：CommonsMultipartResovler
(为了让 CommonsMultipartResovler 正确工作，必须先将 Jakarta Commons FileUpload 及 Jakarta Commons io
的类包添加到类路径下)

Spring MVC 默认没有装配 MultipartResovler，如果想使用 Spring的文件上传功能，需现在上下文中配置MultipartResolver
<!-- 配置 MultipartResolver -->
	<bean id="multipartResolver"
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<property name="defaultEncoding" value="UTF-8"></property>
		<property name="maxUploadSize" value="1024000"></property>	（以字节为单位）
	</bean>	
前端的使用	
 	<form action="testFileUpload" method="POST" enctype="multipart/form-data">
		File: <input type="file" name="file"/>
		Desc: <input type="text" name="desc"/>
		<input type="submit" value="Submit"/>
	</form>
后台代码中使用
	@RequestMapping("/testFileUpload")
	public String testFileUpload(@RequestParam("desc") String desc, 
			@RequestParam("file") MultipartFile file) throws IOException{
		System.out.println("desc: " + desc);
		System.out.println("OriginalFilename: " + file.getOriginalFilename());
		System.out.println("InputStream: " + file.getInputStream());
		return "success";
	}

同时我们可以使用之前的 HttpMessageConverter 做文件下载
@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException{
		byte [] body = null;
		ServletContext servletContext = session.getServletContext();
		InputStream in = servletContext.getResourceAsStream("/files/abc.txt");
		body = new byte[in.available()];
		in.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=abc.txt");
		
		HttpStatus statusCode = HttpStatus.OK;
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
		return response;
	}
```



## 使用拦截器

什么是拦截器

```java
 拦截器能够在一个Action执行前后拦截它。
 拦截器链（Interceptor Chain）。拦截器链就是将拦截器按一定的顺序联结成一条链。在访问被拦截的方法或字段时，拦截器链中的拦截器就会按其之前定义的顺序被调用。
```

拦截器与过滤器的区别

```java
（1）过滤器（Filter）：当你有一堆东西的时候，你只希望选择符合你要求的某一些东西。定义这些要求的工具，就是过滤器。（理解：就是一堆字母中取一个B）

（2）拦截器（Interceptor）：在一个流程正在进行的时候，你希望干预它的进展，甚至终止它进行，这是拦截器做的事情。（理解：就是一堆字母中，干预他，通过验证的少点，顺便干点别的东西）。

过滤器和拦截器触发时机不一样:
过滤器是在请求进入容器后，但请求进入servlet之前进行预处理的。请求结束返回也是，是在servlet处理完后，返回给前端之前。
```


![JAVA_SPRINGMVC14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC14.png?raw=true)

springMVC对拦截器的支持

```java
Spring MVC使用拦截器对请求进行拦截处理.
1：实现HandlerInterceptor接口，自定义拦截器
2：配置文件中配置对应拦截器
<mvc:interceptors>
	<!-- 配置自定义的拦截器 -->
		<bean class="com.atguigu.springmvc.interceptors.FirstInterceptor"></bean>
</mvc:interceptors>		
    
用户可以自定义拦截器来实现特定的功能，自定义的拦截器必须实现HandlerInterceptor接口
preHandle()：这个方法在业务处理器处理请求之前被调用，在该方法中对用户请求 request 进行处理。如果程序员决定该拦截器对请求进行拦截处理后还要调用其他的拦截器，或者是业务处理器去进行处理，则返回true；如果程序员决定不需要再调用其他的组件去处理请求，则返回false。

– postHandle()：这个方法在业务处理器处理完请求后，但是DispatcherServlet 向客户端返回响应前被调用，在该方法中对用户请求request进行处理。

– afterCompletion()：这个方法在 DispatcherServlet 完全处理完请求后被调用，可以在该方法中进行一些资源清理的操作。

配置文件中配置
默认的拦截器拦截所有的请求
	<mvc:interceptors>
		<!-- 配置自定义的拦截器 -->
		<bean class="com.atguigu.springmvc.interceptors.FirstInterceptor"></bean>
		
		<!-- 配置拦截器作用的路径 -->
		<mvc:interceptor>
			<mvc:mapping path="/emps"/>
			<bean class="com.atguigu.springmvc.interceptors.SecondInterceptor"></bean>
		</mvc:interceptor>
		
		<!-- 配置 LocaleChanceInterceptor -->
		<bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor"></bean>
	</mvc:interceptors>
	

public class FirstInterceptor implements HandlerInterceptor{

	/**
	 * 该方法在目标方法之前被调用.
	 * 若返回值为 true, 则继续调用后续的拦截器和目标方法. 
	 * 若返回值为 false, 则不会再调用后续的拦截器和目标方法. 
	 * 
	 * 可以考虑做权限. 日志, 事务等. 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("[FirstInterceptor] preHandle");
		return true;
	}

	/**
	 * 调用目标方法之后, 但渲染视图之前. 
	 * 可以对请求域中的属性或视图做出修改. 
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("[FirstInterceptor] postHandle");
	}

	/**
	 * 渲染视图之后被调用. 释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("[FirstInterceptor] afterCompletion");
	}

}

```

![JAVA_SPRINGMVC15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC15.png?raw=true)

多个拦截器的执行顺序

```java
按照配置的顺序： 是一个嵌套的俄罗斯套娃
	<mvc:interceptors>
		<!-- 配置自定义的拦截器 -->
		<bean class="com.atguigu.springmvc.interceptors.FirstInterceptor"></bean>
		<bean class="com.atguigu.springmvc.interceptors.SecondInterceptor"></bean>
	</mvc:interceptors>
```

![JAVA_SPRINGMVC16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC16.png?raw=true)

![JAVA_SPRINGMVC17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC17.png?raw=true)

## 异常处理

![JAVA_SPRINGMVC18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC18.png?raw=true)

```java
Spring MVC 通过 HandlerExceptionResolver 处理程序的异常，包括 Handler 映射、数据绑定以及目标方法执行
时发生的异常。

ExceptionHandlerExceptionResolver：
主要处理 Handler 中用 @ExceptionHandler 注解定义的方法。

	
	/**
	 * 1. 在 @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
	 * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
	 * 3. @ExceptionHandler 方法标记的异常有优先级的问题.  他会找到离我们抛的异常最近的异常来处理
	 * 4. @ControllerAdvice: 如果在当前 Handler 中找不到 @ExceptionHandler 方法来出来当前方法出现的异常, 
	 * 则将去 @ControllerAdvice 标记的类中查找 @ExceptionHandler 标记的方法来处理异常. 
	 */
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleArithmeticException(Exception ex){
		System.out.println("出异常了: " + ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		return mv;
	}
	
	@ExceptionHandler({RuntimeException.class})
	public ModelAndView handleArithmeticException2(Exception ex){
		System.out.println("[出异常了]: " + ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		return mv;
	}

	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("i") int i){
		System.out.println("result: " + (10 / i));
		return "success";
	}

ResponseStatusExceptionResolver：

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="用户名和密码不匹配!")
public class UserNameNotMatchPasswordException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}

DefaultHandlerExceptionResolver：
对一些特殊的异常进行处理，比如NoSuchRequestHandlingMethodException、HttpRequestMethodNotSupportedException、HttpMediaTypeNotSupportedException、HttpMediaTypeNotAcceptableException等。

SimpleMappingExceptionResolver：在xml文件中配置，出现什么异常，转向什么页面
如果希望对所有异常进行统一处理，可以使用SimpleMappingExceptionResolver，它将异常类名映射为视图名，即发生异常时使用对应的视图报告异常
<!-- 配置使用 SimpleMappingExceptionResolver 来映射异常 -->
	<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
		<property name="exceptionAttribute" value="ex"></property>
		<property name="exceptionMappings">
			<props>
				<prop key="java.lang.ArrayIndexOutOfBoundsException">error</prop>
			</props>
		</property>
	</bean>	
```



配置全局的异常处理

```java
我们在controller中使用的@ExceptionHandler 异常只能处理当前controller中的异常，如果我们想处理全局的异常处理
需要使用 @ControllerAdvice注解(他是注解在类上的)

@ControllerAdvice
public class SpringMVCTestExceptionHandler {

	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleArithmeticException(Exception ex){
		System.out.println("----> 出异常了: " + ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		return mv;
	}
	
}

```



## SpringMVC 运行流程

![JAVA_SPRINGMVC19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC19.png?raw=true)

流程分解

![JAVA_SPRINGMVC20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC20.png?raw=true)

![JAVA_SPRINGMVC21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC21.png?raw=true)

![JAVA_SPRINGMVC22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC22.png?raw=true)

```java
请求经过HandlerMapping  找到对应的HandlerExecutionChain，然后HandlerExecutionChain 通过HandlerAdapter(他需要做的就是格式转化，数据校验等)适配到实际controller了。
```



## 在 Spring 的环境中整合springMVC

```java
需要进行 Spring 整合 SpringMVC 吗 ?
还是否需要再加入 Spring 的 IOC 容器 ?
是否需要再 web.xml 文件中配置启动 Spring IOC 容器的 ContextLoaderListener ?
		
1. 需要: 通常情况下, 类似于数据源, 事务, 整合其他框架都是放在 Spring 的配置文件中(而不是放在 SpringMVC 的配置文件中).实际上（通常写法）放入 Spring 配置文件对应的 IOC 容器中的还有 Service 和 Dao.
    
2. 不需要: 都放在 SpringMVC 的配置文件中. 也可以分多个 Spring 的配置文件, 然后使用 import 节点导入其他的配置文件

3： 仍然建议在spring中配置自己的数据源, 事务, 整合其他框架等，springMVC配置文件中只配置自己专门负责的事情。

```

在spring中整合springMVC

web.xml中配置加载两个IOC容器

```xml
<!-- 配置启动 Spring IOC 容器的 Listener -->
	<!-- needed for ContextLoaderListener -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:beans.xml</param-value>
	</context-param>

	<!-- Bootstraps the root web application context before servlet initialization -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<servlet>
		<servlet-name>springDispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:springmvc.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>springDispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```



```java
问题: 若 Spring 的 IOC 容器和 SpringMVC 的 IOC 容器扫描的包有重合的部分, 就会导致有的 bean 会被创建 2 次.
Spring 的 IOC 容器不应该扫描 SpringMVC 中的 bean, 对应的SpringMVC 的 IOC 容器不应该扫描 Spring 中的 bean
解决:
		1. 使 Spring 的 IOC 容器扫描的包和 SpringMVC 的 IOC 容器扫描的包没有重合的部分. 
		2. 使用 exclude-filter 和 include-filter 子节点来规定只能扫描的注解
springMVC.xml：
<context:component-scan base-package="com.atguigu.springmvc" use-default-filters="false">
		<context:include-filter type="annotation" 
			expression="org.springframework.stereotype.Controller"/>
		<context:include-filter type="annotation" 
			expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
	</context:component-scan>
spring.xml：
	<context:component-scan base-package="com.atguigu.springmvc">
		<context:exclude-filter type="annotation" 
			expression="org.springframework.stereotype.Controller"/>
		<context:exclude-filter type="annotation" 
			expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
	</context:component-scan>

结论：现状
SpringMVC 的 IOC 容器中的 bean 可以来引用 Spring IOC 容器中的 bean. 返回来呢 ? 反之则不行. Spring IOC 容器中的 bean 却不能来引用 SpringMVC IOC 容器中的 bean! (这个现象是框架帮我们指定好了的)

在 Spring MVC 配置文件中引用业务层的 Bean
多个 Spring IOC 容器之间可以设置为父子关系，以实现良好的解耦。
Spring MVC WEB 层容器可作为 “业务层” Spring容器的子容器：即 WEB 层容器可以引用业务层容器的 Bean，而业务层容器却访问不到 WEB 层容器的 Bean
```

![JAVA_SPRINGMVC23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGMVC23.png?raw=true)

