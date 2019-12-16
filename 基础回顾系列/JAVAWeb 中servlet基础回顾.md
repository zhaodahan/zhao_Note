# JAVAWeb 中servlet基础回顾

框架的基础

## servlet和servlet容器

```
servlet就是运行在服务器上的java类。 servlet的本质就是java类。
servlet容器: 运行servlet的容器，servlet运行时环境，就是我么常说的服务器，如Tomcat。
```

![JAVAWEB_REVIEW1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW1.png?raw=true)

### Tomcat

![JAVAWEB_REVIEW2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW2.png?raw=true)

jsp的本质也是servlet， 翻译后应该是servlet。也是一个java文件。

Tomcat的启动需要java环境（需要配置jdk根目录javahome）。说明Tomcat依赖于java。

![JAVAWEB_REVIEW3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW3.png?raw=true)

### web应用

![JAVAWEB_REVIEW4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW4.png?raw=true)

web应用从上面的图可以看出是由运行在服务器上的一些servlet类和jsp等静态资源组成的应用。

![JAVAWEB_REVIEW5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW5.png?raw=true)

扩展: 为tomcat映射任意路径下的web应用

![JAVAWEB_REVIEW6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW6.png?raw=true)

(eclispe javaEE 模式中配置service的原理就是上面Tomcat提供的机制)

### Servlet与Servlet容器间的关系

Servlet只是一个简单的java类，他运行在Servlet容器中，他与平台无关。

Servlet容器现在就是指我们的Tomcat服务器。**Servlet容器负责与客户间的通信以及< 调用 >Servlet的方法**。Servlet容器与客户间的通信采用"请求-响应"模式；

![JAVAWEB_REVIEW8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW8.png?raw=true)

Servlet不仅能在其中运行Servlet， 他还能对运行在其中的Servlet进行管理(创建和销毁)。

![JAVAWEB_REVIEW11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW11.png?raw=true)

构造器只被调用了一次，说明Servlet是单实例的。

Servlet能做的事：

![JAVAWEB_REVIEW7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW7.png?raw=true)

![JAVAWEB_REVIEW9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW9.png?raw=true)



![JAVAWEB_REVIEW10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW10.png?raw=true)

![JAVAWEB_REVIEW12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW12.png?raw=true)

![JAVAWEB_REVIEW13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW13.png?raw=true)

![JAVAWEB_REVIEW14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW14.png?raw=true)



### ServletConfig

封装了Servlet的配置信息，并可以使用其获取Servletcontext对象。

![JAVAWEB_REVIEW15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW15.png?raw=true)

![JAVAWEB_REVIEW16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW16.png?raw=true)

Servlet容器在管理Servlet时，会在构造方法调用后调用init（ServletConfig config），我们从这里可以看出。ServletConfig对象是**Servlet容器创建并初始化后赋值给我们Servlet**的。

### ServletContext

**Servlet引擎为每个web应用都创建了一个ServletContext对象**。他代表的是当前的web应用，是当前web应用的一个java描述，里面记录着当前web应用的各种信息，我们可以通过ServletConfig对象得到ServletContext对象的引用。

![JAVAWEB_REVIEW17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW17.png?raw=true)

Servlet中配置的初始化参数只是一个局部的参数，只有当前Servlet能获取，但是context中的初始化参数是全局的，他是整个web应用都能使用的参数。

**ServletContex 还有一个常用的功能**：获取当前web应用某一个文件的绝对路径。

![JAVAWEB_REVIEW18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW18.png?raw=true)

![JAVAWEB_REVIEW19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW19.png?raw=true)

![JAVAWEB_REVIEW20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW20.png?raw=true)

### get& post请求

WEB浏览器与WEB服务器之间的一问一答的交互过程必须遵循http超文本协议。

![JAVAWEB_REVIEW21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW21.png?raw=true)



![JAVAWEB_REVIEW22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW22.png?raw=true)



### httpservletrequest和httpservletresponse

service的请求接收信息：

![JAVAWEB_REVIEW23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW23.png?raw=true)

![JAVAWEB_REVIEW24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW24.png?raw=true)

![JAVAWEB_REVIEW25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW25.png?raw=true)

![JAVAWEB_REVIEW26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW26.png?raw=true)

## JSP 技术

为什么需要jsp? 

动态网页中绝大部分的内容是不变，我们想要在页面中写java代码 (比在java代码中写html代码好)。

jsp会被动他的翻译成一个Servlet。 他利用了动态代理。

![JAVAWEB_REVIEW27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW27.png?raw=true)

![JAVAWEB_REVIEW28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW28.png?raw=true)

![JAVAWEB_REVIEW29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW29.png?raw=true)

### 域对象的属性操作

![JAVAWEB_REVIEW30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW30.png?raw=true)

```
四个域对象：  这几个对象中都有设置属性和获取属性的方法，但是同样设置的属性，在不用的作用域，生命周期不一样
pageContext      page域        只能在当前jsp页面中使用（当前页面-》pageContext代表的只是当前jsp页面的上下文）                        
request          request域     只能在同一个请求中使用（转发， 不同的jsp页面绝大部分情况下是两个完全不一样的请求，除非是转发的请求）
session          session域     只能在同一个会话（session对象）中使用（私有的） 
                               会话： 浏览器从打开到关闭被称为一次会话 (前提是在此期间会话未失效)
application      context域     他是servletContext对象，只能在同一个web应用中使用。（全局的）
```

![JAVAWEB_REVIEW31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW31.png?raw=true)

![JAVAWEB_REVIEW32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW32.png?raw=true)

![JAVAWEB_REVIEW33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW33.png?raw=true)

(session 会话最经典的应用案例就是  购物车)

![JAVAWEB_REVIEW34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW34.png?raw=true)

### 请求转发和重定向

```
请求转发：
request.getRequestDispatcher().forward();
首先需要获取到RequestDispatcher接口，然后调用他的forward方法。

重定向：
response.sendRedirect();
```

![JAVAWEB_REVIEW35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW35.png?raw=true)

![JAVAWEB_REVIEW36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW36.png?raw=true)

重定向和转发的区别：

![JAVAWEB_REVIEW37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW37.png?raw=true)

![JAVAWEB_REVIEW38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW38.png?raw=true)

转发只会发送一个http请求。

### jsp指令

他是jsp引擎专门设计的，并不会产生任何实际的输出，而只是告诉jsp引擎应该如何处理(翻译)jsp页面中的部分。

![JAVAWEB_REVIEW39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW39.png?raw=true)

![JAVAWEB_REVIEW40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW40.png?raw=true)

![JAVAWEB_REVIEW41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW41.png?raw=true)

![JAVAWEB_REVIEW42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW42.png?raw=true)

### 中文乱码

比较实用

![JAVAWEB_REVIEW43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW43.png?raw=true)

```java
接收表单的参数：
// 只针对post请求
				request.setCharacterEncoding("UTF-8"); //这个方法要放在获取参数方法之前
				String keyword = request.getParameter("storeName");
//只针对get请求有效
	String keyword = new String(request.getParameter("storeName").getBytes("iso-8859-1"), "utf-8"); 
```



##  MVC 设计模式

![JAVAWEB_REVIEW46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW46.png?raw=true)


![JAVAWEB_REVIEW44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW44.png?raw=true)

![JAVAWEB_REVIEW45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW45.png?raw=true)

注意： javaweb开发中尽量使用绝对路径

![JAVAWEB_REVIEW60.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW60.png?raw=true)

## Cookie 和 HttpSession

 首先我们知道http协议是一个无状态的“一问一答”协议。服务器不能识别这些请求是否是从同一个浏览器发出的。但是我们有时候存在这样的需求如购物车，需要我们的请求具有“伪状态”， 我们需要知道是同一个用户在将商品加入到购物车。需要对用户进行跟踪。

```
作为web服务器，必须有一个机制能够唯一的标识一个用户，且能够同时的记录改用户的状态。
```

![JAVAWEB_REVIEW47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW47.png?raw=true)

常用的两种会话最终机制： cookie 和session

web浏览器要从大量的http请求中区分这些请求是否属于同一个会话。这就需要**浏览器对其发出的请求信息进行标识**。这样同一个会话的请求就都带有相同的标识号(会话ID)。

### cookie

cookie是在客户端保存了http状态信息方案。他是第一请求服务器生成发送给浏览器，存储在客户端浏览器中。然后浏览器下面的请求就会带上这个标识id。

![JAVAWEB_REVIEW48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW48.png?raw=true)

cookie核心机制：

![JAVAWEB_REVIEW49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW49.png?raw=true)

![JAVAWEB_REVIEW50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW50.png?raw=true)

![JAVAWEB_REVIEW51.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW51.png?raw=true)

![JAVAWEB_REVIEW52.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW52.png?raw=true)

**持久化cookie**

cookie默认情况下是一个会话级别的cookie。如果我们希望cookie信息存储在磁盘上(C:\Users\Administrator\AppData\Roaming\Microsoft\Windows\Cookies)，我们可以调用其setMaxage(秒)方法。存储多少秒。超过时间就删除。

![JAVAWEB_REVIEW53.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW53.png?raw=true)

这个常应用在登录网址时记录用户名和密码。这样下次访问网址的时候就不用再重复登录了。

cookie 还可以用于记录用户的浏览记录。

**设置cookie的作用路径**

![JAVAWEB_REVIEW54.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW54.png?raw=true)

### session

与cookie机制不同的是，session机制是在**服务器**端以一种散列表的形式保存http状态信息方案。

![JAVAWEB_REVIEW55.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW55.png?raw=true)

![JAVAWEB_REVIEW56.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW56.png?raw=true)

```
session的生命周期
Session存储在服务器端，一般是放置在服务器的内存中（为了高速存取），Sessinon在用户访问第一次访问服务器时创建，需要注意只有访问JSP、Servlet等java服务程序时才会创建Session，只访问HTML、IMAGE等静态资源并不会创建Session，可调用request.getSession(true)强制生成Session。

Session什么时候失效？
1. 服务器会把长时间没有活动的Session从服务器内存中清除，此时Session便失效。Tomcat中Session的默认失效时间为30分钟。
2. 调用Session的invalidate方法来主动失效。
3. 并不是关闭了浏览器session就失效了，我们还可以通过持久化cookie来获取session对象
```

![JAVAWEB_REVIEW57.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW57.png?raw=true)

![JAVAWEB_REVIEW58.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW58.png?raw=true)

session虽然是存储在服务器端的，但是他的正常运行还是需要客户端浏览器的支持。他需要使用请求头中的Cookie作为识别标志。服务器向客户端浏览器发送一个名为JSESSIONID的Cookie，它的值为该Session的id（也就是HttpSession.getId()的返回值）。Session依据该Cookie来识别是否为同一用户。该Cookie为服务器自动生成的，它的maxAge属性一般为-1，表示仅当前浏览器内有效，并且各浏览器窗口间不共享，关闭浏览器就会失效。因此同一机器的两个浏览器窗口访问服务器时，会生成两个不同的Session。但是由浏览器窗口内的链接、脚本等打开的新窗口（也就是说不是双击桌面浏览器图标等打开的窗口）除外。这类子窗口会共享父窗口的Cookie，因此会共享一个Session。

注意：新开的浏览器窗口会生成新的Session，但子窗口除外。子窗口会共用父窗口的Session。

```
如果客户端浏览器将Cookie功能禁用，或者不支持Cookie怎么办？例如，绝大多数的手机浏览器都不支持Cookie。Java Web提供了另一种解决方案：URL地址重写。

URL地址重写是对客户端不支持Cookie的解决方案。URL地址重写的原理是将该用户Session的id信息重写到URL地址中。

服务器能够解析重写后的URL获取Session的id。这样即使客户端不支持Cookie，也可以使用Session来记录用户状态。HttpServletResponse类提供了encodeURL(String url)实现URL地址重写，这个方法为自动将sessionid加在传入的URL中，该方法会自动判断客户端是否支持Cookie。如果客户端支持Cookie，会将URL原封不动地输出来。如果客户端不支持Cookie，则会将用户Session的id重写到URL中。
```

![JAVAWEB_REVIEW59.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW59.png?raw=true)

session的经典案例应用： 购物车。

#### 使用session来避免表单的重复提交

在提交表单的过程中，可能由于网路问题导致页面没有响应 或者是错误操作 而多次点击提交按钮导致同样一个请求提交了多次。从而导致应用出现问题。

![JAVAWEB_REVIEW61.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW61.png?raw=true)

如何避免表单提交：

在提交表单之前给表单加一个标记，然后再服务器端再验证这个标记。当消耗了这个标记后，将这个标记撕毁。

![JAVAWEB_REVIEW62.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW62.png?raw=true)

#### 使用session生成一次性验证码

![JAVAWEB_REVIEW63.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW63.png?raw=true)

生成验证码的servlet代码：

```java
package com.atguigu.javaweb;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateColorServlet extends HttpServlet {
	//设置验证图片的宽度, 高度, 验证码的个数
	private int width = 152;
	private int height = 40;
	private int codeCount = 4;
	
	//验证码字体的高度
	private int fontHeight = 4;
	
	//验证码中的单个字符基线. 即：验证码中的单个字符位于验证码图形左上角的 (codeX, codeY) 位置处
	private int codeX = 0;
	private int codeY = 0;
	
	//验证码由哪些字符组成
	char [] codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789".toCharArray();
	
	//初始化验证码图形属性
	public void init(){
		fontHeight = height - 2;
		codeX = width / (codeCount + 2);
		codeY = height - 4;
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//定义一个类型为 BufferedImage.TYPE_INT_BGR 类型的图像缓存
		BufferedImage buffImg = null;
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	
		//在 buffImg 中创建一个 Graphics2D 图像
		Graphics2D graphics = null;
		graphics = buffImg.createGraphics();
		
		//设置一个颜色, 使 Graphics2D 对象的后续图形使用这个颜色
		graphics.setColor(Color.WHITE);
		
		//填充一个指定的矩形: x - 要填充矩形的 x 坐标; y - 要填充矩形的 y 坐标; width - 要填充矩形的宽度; height - 要填充矩形的高度
		graphics.fillRect(0, 0, width, height);
		
		//创建一个 Font 对象: name - 字体名称; style - Font 的样式常量; size - Font 的点大小
		Font font = null;
		font = new Font("", Font.BOLD, fontHeight);
		//使 Graphics2D 对象的后续图形使用此字体
		graphics.setFont(font);
		
		graphics.setColor(Color.BLACK);
		
		//绘制指定矩形的边框, 绘制出的矩形将比构件宽一个也高一个像素
		graphics.drawRect(0, 0, width - 1, height - 1);
		
		//随机产生 15 条干扰线, 使图像中的认证码不易被其它程序探测到
		Random random = null;
		random = new Random();
		graphics.setColor(Color.GREEN);
		for(int i = 0; i < 15; i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(20);
			int y1 = random.nextInt(20);
			graphics.drawLine(x, y, x + x1, y + y1);
		}
		
		//创建 randomCode 对象, 用于保存随机产生的验证码, 以便用户登录后进行验证
		StringBuffer randomCode;
		randomCode = new StringBuffer();
		
		for(int i = 0; i < codeCount; i++){
			//得到随机产生的验证码数字
			String strRand = null;
			strRand = String.valueOf(codeSequence[random.nextInt(36)]);
			
			//用随机产生的颜色将验证码绘制到图像中
			graphics.setColor(Color.BLUE);
			graphics.drawString(strRand, (i + 1)* codeX, codeY);
		}
		
		//禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		//将图像输出到输出流中
		ServletOutputStream sos = null;
		sos = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos); 
		sos.close();
	}
}

```



session注意事项： 

1： 当我们服务器关闭的时候，并不会销毁我们的session及其中存储的对象。而是会将写在磁盘中，当重新启动服务器的时候，服务器会重新从服务器磁盘中读取(下面的session属性监听器可以验证这点)

##  Servlet 过滤器

Filter ： 基本功能就是对**servlet容器调用servlet**的过程进行拦截。从而在servlet响应的前后进行一些操作。

(在请求进入servlet之前做一个拦截，在响应回到客户端端之前也会进行一次拦截)

![JAVAWEB_REVIEW64.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW64.png?raw=true)

Filter程序是一个实现了Filter接口的**java类**。他和servlet相似，都是由servlet容器进行管理(其创建销毁，调用都是由servlet容器来执行)，同样的如果想让Feilter程序被servlet容器管理，他需要先在web.xml 中注册

![JAVAWEB_REVIEW65.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW65.png?raw=true)

![JAVAWEB_REVIEW66.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW66.png?raw=true)

![JAVAWEB_REVIEW67.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW67.png?raw=true)

![JAVAWEB_REVIEW68.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW68.png?raw=true)

![JAVAWEB_REVIEW69.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW69.png?raw=true)

![JAVAWEB_REVIEW70.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW70.png?raw=true)

![JAVAWEB_REVIEW71.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW71.png?raw=true)

![JAVAWEB_REVIEW72.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW72.png?raw=true)

#### 禁止浏览器缓存的Filter

![JAVAWEB_REVIEW73.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW73.png?raw=true)

#### 字符编码过滤器

![JAVAWEB_REVIEW74.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW74.png?raw=true)

#### 检查用户是否登录过滤器

![JAVAWEB_REVIEW75.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW75.png?raw=true)

#### 权限管理过滤器



## Servlet 监听器

监听器就是监听某个对象身上**发生的事件** 或 **状态变化**并近新相应处理的**组件对象**。当被监听的对象发生改变的时候立即发生行动。

![JAVAWEB_REVIEW76.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW76.png?raw=true)

```
监听器的相关概念：

事件源：被监听的对象  ----- 三个域对象 request  session  servletContext
监听器：监听事件源对象  事件源对象的状态的变化都会触发监听器 ----
注册监听器：将监听器与事件源进行绑定
响应行为：监听器监听到事件源的状态变化时 所涉及的功能代码 ---- 程序员编写代码
```

![JAVAWEB_REVIEW77.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW77.png?raw=true)

![JAVAWEB_REVIEW78.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW78.png?raw=true)

![JAVAWEB_REVIEW79.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW79.png?raw=true)

![JAVAWEB_REVIEW80.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW80.png?raw=true)

![JAVAWEB_REVIEW81.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW81.png?raw=true)

如何创建一个监听器：

1： 实现对应的监听器接口
2： 将创建的监听器注册到web.xml中

![JAVAWEB_REVIEW82.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW82.png?raw=true)

![JAVAWEB_REVIEW83.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW83.png?raw=true)

### 域对象属性变更监视器

(这几个监听器比较少用)

![JAVAWEB_REVIEW84.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW84.png?raw=true)

![JAVAWEB_REVIEW85.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW85.png?raw=true)

### 感知session绑定的事件监听器

用来绑定javaBean对象在session中的状态

![JAVAWEB_REVIEW86.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW86.png?raw=true)

![JAVAWEB_REVIEW87.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW87.png?raw=true)

![JAVAWEB_REVIEW88.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW88.png?raw=true)

![JAVAWEB_REVIEW89.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW89.png?raw=true)

### 监听器的应用

####  统计在线访客，并可以踢出访客

在我们学习了之前的知识，我们可以考虑到的方案是： 创建一个session创建监听器， 每个用户访问的时候为其生成session。 将其记录到访问集合中，但我们要踢出该访客的时候将其session失效，并记录其信息到禁用列表，符合其信息的不为其生产session。在过滤器链中添加一个过滤器，判断用户是否拥有session，如果没有session，且不是新用户无法生成session，就拒绝访问。 

![JAVAWEB_REVIEW90.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW90.png?raw=true)

## 文件的上传下载

### 文件上传

```java
分析： 如何进行文件上传
1: 我们需要进行表单提交，且文件是以二进制形式上传的
所以请求方式不能是get，url后面装不下，只能是post，但是仅仅post，他实际提交的时候他只提交了一个字符串(文件名)
所以我们还需要来设置一个请求的编码方式enctype="multipart/form-data"，这样的话就是以一个二进制的方式来发送的请求，需要设置在form里面，否则无法提交文件
<form action="upload" method="post" enctype="multipart/form-data">
     <table>
         <tr>
             <td>上传文件:</td>
             <td><input type="file" name="file"/></td>
         </tr>
         <tr>
             <td></td>
             <td><input type="submit" value="上传文件"/></td>
        </tr>
     </table>
 </form>
2：同样的我们在服务器端也需要获取一个二进制流来写入文件到实际的文件存储地址。
服务端无法再使用request.getParameter等方式获取请求信息，因为提交的编码方式变成了以二进制的方式提交。可以使用输入流的方式来获取，但是解析内容起来比较麻烦。Apache为我们提供了一个文件上传组件fileupload.
public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
                String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
                File file = new File(savePath);
                //判断上传文件的保存目录是否存在
                if (!file.exists() && !file.isDirectory()) {
                    System.out.println(savePath+"目录不存在，需要创建");
                    //创建目录
                    file.mkdir();
                }
                //消息提示
                String message = "";
                try{
                    //使用Apache文件上传组件处理文件上传步骤：
                    //1、创建一个DiskFileItemFactory工厂
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    //2、创建一个文件上传解析器
                    ServletFileUpload upload = new ServletFileUpload(factory);
                     //解决上传文件名的中文乱码
                    upload.setHeaderEncoding("UTF-8"); 
                    //3、判断提交上来的数据是否是上传表单的数据
                    if(!ServletFileUpload.isMultipartContent(request)){
                        //按照传统方式获取数据
                        return;
                    }
                    //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
                    List<FileItem> list = upload.parseRequest(request);
                    for(FileItem item : list){
                        //如果fileitem中封装的是普通输入项的数据
                        if(item.isFormField()){
                            String name = item.getFieldName();
                            //解决普通输入项的数据的中文乱码问题
                            String value = item.getString("UTF-8");
                            //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                            System.out.println(name + "=" + value);
                        }else{//如果fileitem中封装的是上传文件
                            //得到上传的文件名称，
                            String filename = item.getName();
                            System.out.println(filename);
                            if(filename==null || filename.trim().equals("")){
                                continue;
                            }
                            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                            filename = filename.substring(filename.lastIndexOf("\\")+1);
                            //获取item中的上传文件的输入流
                            InputStream in = item.getInputStream();
                            //创建一个文件输出流
                            FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                            //创建一个缓冲区
                            byte buffer[] = new byte[1024];
                            //判断输入流中的数据是否已经读完的标识
                            int len = 0;
                            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                            while((len=in.read(buffer))>0){
                                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                                out.write(buffer, 0, len);
                            }
                            //关闭输入流
                            in.close();
                            //关闭输出流
                            out.close();
                            //删除处理文件上传时生成的临时文件
                            item.delete();
                            message = "文件上传成功！";
                        }
                    }
                }catch (Exception e) {
                    message= "文件上传失败！";
                    e.printStackTrace();
                    
                }
                request.setAttribute("message",message);
                request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

```

![JAVAWEB_REVIEW91.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW91.png?raw=true)

![JAVAWEB_REVIEW92.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW92.png?raw=true)

通用代码：

```java
public class FileUploadAppProperties {
	
	private Map<String, String> properties = new HashMap<>();
	
	private FileUploadAppProperties(){}
	
	private static FileUploadAppProperties instance = new FileUploadAppProperties();
	
	public static FileUploadAppProperties getInstance() {
		return instance;
	}
	
	public void addProperty(String propertyName, String propertyValue){
		properties.put(propertyName, propertyValue);
	}
	
	public String getProperty(String propertyName){
		return properties.get(propertyName);
	}
	
}
```

```java
public class FileUploadAppListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public FileUploadAppListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	InputStream in = getClass().getClassLoader().getResourceAsStream("/upload.properties");
    	
    	Properties properties = new Properties();
    	try {
			properties.load(in);
			
			for(Map.Entry<Object, Object> prop: properties.entrySet()){
				String propertyName = (String) prop.getKey();
				String propertyValue = (String) prop.getValue();
				
				FileUploadAppProperties.getInstance().addProperty(propertyName, propertyValue);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
```

```java
public class FileUploadBean {

	public static void main(String[] args) {
		FileUploadBean bean = new FileUploadBean("ABC", "e:\\abc.txt", "aabbcc");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(bean);
		System.out.println(jsonStr);
		
		List<FileUploadBean> beans = new ArrayList<>();
		beans.add(bean);
		beans.add(new FileUploadBean("def", "e:\\def.txt", "ddeeff"));
		jsonStr = gson.toJson(beans);
		System.out.println(jsonStr);
	}
	
	private Integer id;
	// 文件名
	private String fileName;
	// 文件的路径
	private String filePath;
	// 文件的描述
	private String fileDesc;
    
public FileUploadBean(String fileName, String filePath, String fileDesc) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileDesc = fileDesc;
	}
```



```java
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String FILE_PATH = "/WEB-INF/files/";
	
	private static final String TEMP_DIR = "d:\\tempDirectory";
	
	private UploadFileDao dao = new UploadFileDao();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		String path = null;
		
		//获取 ServletFileUpload 对象. 
		ServletFileUpload upload = getServletFileUpload();
		
		try {
			
			//把需要上传的 FileItem 都放入到该 Map 中
			//键: 文件的待存放的路径, 值: 对应的 FileItem 对象
			Map<String, FileItem> uploadFiles = new HashMap<String, FileItem>();
			
			//解析请求, 得到 FileItem 的集合.
			List<FileItem> items = upload.parseRequest(request);
			
			//1. 构建 FileUploadBean 的集合, 同时填充 uploadFiles
			List<FileUploadBean> beans = buildFileUploadBeans(items, uploadFiles);
			
			//2. 校验扩展名:
			vaidateExtName(beans);

			//3. 校验文件的大小: 在解析时, 已经校验了, 我们只需要通过异常得到结果. 
			
			//4. 进行文件的上传操作.
			upload(uploadFiles);
			
			//5. 把上传的信息保存到数据库中
			saveBeans(beans);
			
			//6. 删除临时文件夹的临时文件
			FileUtils.delAllFile(TEMP_DIR);
			
			path = "/app/success.jsp";
			
		} catch (Exception e) {
			e.printStackTrace();
			path = "/app/upload.jsp";
			request.setAttribute("message", e.getMessage());
		}
		
		request.getRequestDispatcher(path).forward(request, response);
	}

	private void saveBeans(List<FileUploadBean> beans) {
		dao.save(beans); 
	}

	/**
	 * 文件上传前的准备工作. 得到 filePath 和 InputStream
	 * @param uploadFiles
	 * @throws IOException
	 */
	private void upload(Map<String, FileItem> uploadFiles) throws IOException {
		for(Map.Entry<String, FileItem> uploadFile: uploadFiles.entrySet()){
			String filePath = uploadFile.getKey();
			FileItem item = uploadFile.getValue();
			
			upload(filePath, item.getInputStream());
		}
	}

	/**
	 * 文件上传的 IO 方法.
	 * 
	 * @param filePath
	 * @param inputStream
	 * @throws IOException
	 */
	private void upload(String filePath, InputStream inputStream) throws IOException {
		OutputStream out = new FileOutputStream(filePath);
		
		byte [] buffer = new byte[1024];
		int len = 0;
		
		while((len = inputStream.read(buffer)) != -1){
			out.write(buffer, 0, len);
		}
		
		inputStream.close();
		out.close();
		
		System.out.println(filePath); 
	}

	/**
	 * 校验扩展名是否合法
	 * @param beans: 
	 */
	private void vaidateExtName(List<FileUploadBean> beans) {
		String exts = FileUploadAppProperties.getInstance().getProperty("exts");
		List<String> extList = Arrays.asList(exts.split(","));
		System.out.println(extList);
		
		for(FileUploadBean bean: beans){
			String fileName = bean.getFileName();
			System.out.println(fileName.indexOf(".")); 
			
			String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
			System.out.println(extName); 
			
			if(!extList.contains(extName)){
				throw new InvalidExtNameException(fileName + "文件的扩展名不合法");
			}
		}
	}

	/**
	 * 利用传入的 FileItem 的集合, 构建 FileUploadBean 的集合, 同时填充 uploadFiles
	 * 
	 * FileUploadBean 对象封装了: id, fileName, filePath, fileDesc
	 * uploadFiles: Map<String, FileItem> 类型, 存放文件域类型的  FileItem. 键: 待保存的文件的名字 ,值: FileItem 对象
	 * 
	 * 构建过程:
	 * 1. 对传入 FileItem 的集合进行遍历. 得到 desc 的那个 Map. 键: desc 的 fieldName(desc1, desc2 ...). 
	 * 值: desc 的那个输入的文本值
	 * 
	 * 2. 对传入 FileItem 的集合进行遍历. 得到文件域的那些 FileItem 对象, 构建对应的 key (desc1 ....) 来获取其 desc.
	 * 构建的 FileUploadBean 对象, 并填充 beans 和 uploadFiles
	 * 
	 * @param items
	 * @param uploadFiles
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private List<FileUploadBean> buildFileUploadBeans(List<FileItem> items, Map<String, FileItem> uploadFiles) throws UnsupportedEncodingException {
		List<FileUploadBean> beans = new ArrayList<>();
		
		Map<String, String> descs = new HashMap<>();
		
		for(int i = 0; i < items.size(); i++){
			FileItem item = items.get(i);
			
			if(item.isFormField()){
				//desc1 或 desc2 ...
				String fieldName = item.getFieldName();
				String desc = item.getString("UTF-8");
				
				descs.put(fieldName, desc);
			}
		}
		
		for(int i = 0; i < items.size(); i++){
			FileItem item = items.get(i);
			FileUploadBean bean = null;
			if(!item.isFormField()){
				String fieldName = item.getFieldName();
				String descName = "desc" + fieldName.substring(fieldName.length() - 1);
				String desc = descs.get(descName); 
				
				//对应文件名
				String fileName = item.getName();
				String filePath = getFilePath(fileName);
				
				bean = new FileUploadBean(fileName, filePath, desc);
				beans.add(bean);
				
				uploadFiles.put(bean.getFilePath(), item);
			}			
		}
		
		return beans;
	}

	/**
	 * 根据跟定的文件名构建一个随机的文件名
	 * 1. 构建的文件的文件名的扩展名和给定的文件的扩展名一致
	 * 2. 利用 ServletContext 的 getRealPath 方法获取的绝对路径
	 * 3. 利用了 Random 和 当前的系统时间构建随机的文件的名字
	 * 
	 * @param fileName
	 * @return
	 */
	private String getFilePath(String fileName) {
		String extName = fileName.substring(fileName.lastIndexOf("."));
		Random random = new Random();
		
		String filePath = getServletContext().getRealPath(FILE_PATH) + "\\" + System.currentTimeMillis() + random.nextInt(100000) + extName;
		return filePath;
	}

	/**
	 * 构建 ServletFileUpload 对象
	 * 从配置文件中读取了部分属性, 用户设置约束. 
	 * 该方法代码来源于文档. 
	 * @return
	 */
	private ServletFileUpload getServletFileUpload() {
		String fileMaxSize = FileUploadAppProperties.getInstance().getProperty("file.max.size");
		String totalFileMaxSize = FileUploadAppProperties.getInstance().getProperty("total.file.max.size");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setSizeThreshold(1024 * 500);
		File tempDirectory = new File(TEMP_DIR);
		factory.setRepository(tempDirectory);

		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setSizeMax(Integer.parseInt(totalFileMaxSize));
		upload.setFileSizeMax(Integer.parseInt(fileMaxSize));
		
		return upload;
	}

}

```



### 文件下载

![JAVAWEB_REVIEW93.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW93.png?raw=true)

![JAVAWEB_REVIEW94.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_REVIEW94.png?raw=true)

```java
public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        //处理请求  
        //读取要下载的文件  
        File f = new File("E:/好久不见.mp3");  
        if(f.exists()){  
            FileInputStream  fis = new FileInputStream(f);  
            String filename=URLEncoder.encode(f.getName(),"utf-8"); //解决中文文件名下载后乱码的问题  
            byte[] b = new byte[fis.available()];  
            fis.read(b);  
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");  
            response.setHeader("Content-Disposition","attachment; filename="+filename+"");  
            //获取响应报文输出流对象  
            ServletOutputStream  out =response.getOutputStream();  
            //输出  
            out.write(b);  
            out.flush();  
            out.close();  
        }     
          
    }  
```

静态下载：

```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/x-msdownload"); 
		
		String fileName = "文件下载.pptx";
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		
		OutputStream out = response.getOutputStream();
		String pptFileName = "C:\\Users\\Think Pad\\Desktop\\__正在上课__\\11.尚硅谷_JavaWEB_监听器.pptx";
		
		InputStream in = new FileInputStream(pptFileName);
		
		byte [] buffer = new byte[1024];
		int len = 0;
		
		while((len = in.read(buffer)) != -1){
			out.write(buffer, 0, len);
		}
		
		in.close();
	}
```



##  国际化

