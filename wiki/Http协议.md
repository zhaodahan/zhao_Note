#                                                      Http协议

# Http协议基础

## 一、技术基石

### (1)什么是HTTP?

答：HTTP是一个客户端和服务器端**请求**和**响应**的**标准TCP**。http本质就是tcp,其实是建立在TCP之上的。

当我们打开百度网页时，是这样的：

> https://www.baidu.com

多了个S，其实S表示TLS、SSL。

SSL(Secure Sockets Layer 安全套接层),及其继任者传输层安全（Transport Layer Security，TLS）是为网络通信提供安全及数据完整性的一种安全协议。**TLS与SSL在传输层对网络连接进行加密**。

HTTP的技术基石如图所示：

![Http_protocol.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol.png?raw=true)



### (2)什么是HTTP协议

HTTP协议（HyperText Transfer Protocol）,即超文本传输协议是用于服务器传输到客户端浏览器的传输协议。Web上，服务器和客户端利用HTTP协议进行通信会话。其会话的结构是一个简单的请求/响应序列，即浏览器发出请求和服务器做出响应。

![Http_protocol2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol2.png?raw=true)



## 二、深入理解技术基石和工作流程

既然HTTP是基于传输层的TCP协议，而TCP协议是**面向连接**的**端到端**的协议。因此，使用HTTP协议传输前，首先建立TCP连接，就是因此在谈的TCP链接过程的“三次握手”。如图

![Http_protocol3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol3.png?raw=true)

在Web上，HTTP协议使用TCP协议而不是UDP协议的原因在于一个网页必须传送很多数据，而且保证其完整性。TCP协议提供传输控制，按顺序组织数据和错误纠正的一系列功能。

一次HTTP操作称为一个事务，其工作过程可分为四步：

> 1、客户端与服务器需要建立连接。（比如某个超级链接，HTTP就开始了。）
>
>  
>
> 2、建立连接后，发送请求。
>
>  
>
> 3、服务器接到请求后，响应其响应信息。
>
>  
>
> 4、客户端接收服务器所返回的信息通过浏览器显示在用户的显示屏上，然后客户机与服务器断开连接。

建立连接，其实建立在TCP连接基础之上。图解核心工作过程（即省去连接过程）如下：

![Http_protocol4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol4.png?raw=true)

## 三、工作过程的HTTP报文

HTTP报文由从客户机到服务器的**请求**和从服务器到客户机的**响应**构成。

**一、请求报文**格式如下：

> 请求行
>
> 通用信息头
>
> 请求头
>
> 实体头
>
> **（空行）**
>
> 报文主体

如图，请求服务器时发送的报文内容：

![Http_protocol5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol5.png?raw=true)

对于其中请求报文详解:

> 1、请求行
>
> 方法字段 + URL + Http协议版本
>
> 2、通用信息头
>
> Cache-Control：指定请求和响应遵循的缓存机制。
>
> keep-alive 是其连接持续有效
>
> 3、请求头
>
> Host：主机名
>
> Referer：允许客户端指定请求URL的资源地址。
>
> User-Agent：请求用户信息。【可以看出一些客户端浏览器的内核信息】
>
> 4、报文主体
>
> 如图中的 “ p=278 ”一般来说，请求主体少不了请求参数。

**二、应答报文**格式如下：

> 状态行
>
> 通用信息头
>
> 响应头
>
> 实体头
>
> **（空行）**
>
> 报文主体

响应的内容:

![Http_protocol6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol6.png?raw=true)

对其中响应报文详解：

> 1、状态行
>
> HTTP协议版本 + 状态码 + 状态代码的文本描述
>
> 【比如这里，200 代表请求成功】
>
> 2、通用信息头
>
> keep-alive 是其连接持续有效
>
> Date头域：时间描述
>
> 3、响应头
>
> Server：处理请求的原始服务器的软件信息。
>
> 4、实体头
>
> Content-Type头：便是接收方实体的介质类型。（这也表示了你的报文主体是什么。）
>
> **（空行）**
>
> 5、报文主体
>
> 这里就是HTML响应页面了，在tab页中的response中可查看。

一次简单的请求/响应就完成了。

## 四、HTTP协议知识补充

请求报文相关：

请求行-请求方法

> GET            请求获取Request-URI所标识的资源
> POST          在Request-URI所标识的资源后附加新的数据
> HEAD         请求获取由Request-URI所标识的资源的响应消息报头
> PUT            请求服务器存储一个资源，并用Request-URI作为其标识
> DELETE       请求服务器删除Request-URI所标识的资源
> TRACE        请求服务器回送收到的请求信息，主要用于测试或诊断
> CONNECT  保留将来使用
> OPTIONS   请求查询服务器的性能，或者查询与资源相关的选项和需求

响应报文相关：

响应行-状态码

> 1xx：指示信息--表示请求已接收，继续处理
> 2xx：成功--表示请求已被成功接收、理解、接受
> 3xx：重定向--要完成请求必须进行更进一步的操作
> 4xx：客户端错误--请求有语法错误或请求无法实现
> 5xx：服务器端错误--服务器未能实现合法的请求

常见的状态码

> 200 OK    请求成功（其后是对GET和POST请求的应答文档。）
>
> 304 Not Modified
>
> 未按预期修改文档。客户端有缓冲的文档并发出了一个条件性的请求（一般是提供If-Modified-Since头表示客户只想比指定日期更新的文档）。服务器告诉客户，原来缓冲的文档还可以继续使用。
>
> 404 Not Found   服务器无法找到被请求的页面。
>
> 500 Internal Server Error   请求未完成。服务器遇到不可预知的情况。s

比如304，在浏览器第一次打开百度时，如图所示:

![Http_protocol7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol7.png?raw=true)

刷新一下：

![Http_protocol8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol8.png?raw=true)

这上面的304就证明了

> 1、304状态码:有些图片和js文件在本地客户端缓存，再次请求后，缓存的文件可以使用。
>
> 2、以上所以HTTP请求，只靠一个TCP连接，这就是所谓的**持久连接**。

## 五、关于HTTP协议的Web应用框架或者规范

Servlet规范中Web应用容器都实现了HTTP协议中的对象，即请求和响应对象。比如 `javax.servlet.http.HttpServletResponse` 对象中肯定有对状态码描述，如图

![Http_protocol9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_protocol9.png?raw=true)

## 五、总结

HTTP协议其实就是我们对话一样，语言就是其中的协议。所以掌握HTTP协议明白以下几点就好：

> 1、用什么通过HTTP协议通信
>
>  url请求，和响应
>
> 2、怎么通过HTTP协议通信
>
> 三次握手建立连接。

# Cookie（小甜饼）

## 一、Cookie是什么

首先从HTTP说起，Cookie是Http协议中那部分呢？

答：Cookie是**请求头域和响应头域的字段**。简单地说，就是伴随**请求和响应**的一组**键值对**的文本，小文本。所以称之为”Cookie“饼干。Cookie的生命来源于服务器。首先是客户端请求服务端，此时请求为第一次，无Cookie参数。这时候，服务端setCookie发送给客户端。记住，**Cookie来源自服务端**。

Cookie有什么用呢？

答：Cookie来源自服务端，当然服务于客户。就像你我的会话，文字是在我们之间传递的。所以**Cookie用于服务端和客户端的会话**。因为Http协议是**无状态**的，Cookie就是维持会话，说白了就是传递数据的额外媒介。

下面我们访问百度地址。

① 产生于**服务端**的**Response**，在**响应头域**：

![Http_cookie.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie.png?raw=true)

**② 请求头域**是这样的：（可以在Cookie Tab页发现，和响应有一样的）

![Http_cookie2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie2.png?raw=true)

## 二、Cookie 传输过程

![Http_cookie3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie3.png?raw=true)

CookieServlet，模拟一下Cookie的一生。代码如下：

```java
@WebServlet(urlPatterns="/cookie")
public class CookieServletT extends HttpServlet
{
    private static final long serialVersionUID = 1L;
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        // 获取Cookie
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies)
            System.out.println(cookie.getName() + " " + cookie.getValue());
 
        // 创建Cookie
        Cookie cookie = new Cookie("CookieName", "CookieValue");
        cookie.setMaxAge(10);
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);
         
        // 响应
        PrintWriter pw = resp.getWriter();
        pw.print("<html><body><h1>Hello,Cookie!</h1></body></html>");
    }
     

```

### ① 客户端访问，无服务端写入的Cookie。

代码 `new Cookie("CookieName", "CookieValue");` 可以看出服务端产生一个新的键值对Cookie，并且设置，说明**第一次请求**时，请求的请求头域**Cookie是没有**的。下面没有CookieName=CookieValue 的Cookie值。如图：

![Http_cookie4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie4.png?raw=true)

### ② 服务端的Cookie传至浏览器。

代码中 `HttpServletResponse.addCookie(cookie);` 这样响应就加入了刚刚那个键值对Cookie。怎么传到浏览器（客户端）

![Http_cookie5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie5.png?raw=true)

从图中可得到，Cookie是通过HTTP的响应头域发送至浏览器。每个Cookie的set，都有一个对应Set-Cookie的头。还有其中的时间代表Cookie的存活时间，HttpOnly可是此Cookie只读模式。

 

### ③ 浏览器解析Cookie，保存至浏览器文件。

直接可以打开IE的Internet选项：

![Http_cookie6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie6.png?raw=true)

如图，那个位置文件就是我们Cookie存的地方。打开查看其内容就是:存放着Cookie信息和URL信息及一些关于时间的。

```
CookieName
CookieValue
localhost/servletBYSocket/
9728
 3416923392
 30449698
 3325104969
 30449698
 *
```

这样就完全搞懂了Cookie如何写入浏览器。

### ④ 客户端访问，有服务端写入的Cookie。

这样，同样的URL再次访问时

![Http_cookie7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie7.png?raw=true)

### ⑤ 服务器获取

服务端这时呢？只要简单的 getCookies() 就可以获取Cookie列表了。如图，服务端控制台打印如下：

![Http_cookie8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie8.png?raw=true)

**Cookie传输小结**

① 客户端访问，无服务端写入的Cookie

② 服务端的Cookie写入浏览器

③ 浏览器解析Cookie，保存至浏览器文件

④ 客户端访问，有服务端写入的Cookie

⑤ 服务器获取

## 三、谈Cookie的作用到XSS（跨站点脚本攻击）

Cookie没有病毒那么危险，但包含敏感信息。比如最常见的记住密码，或者一些用户经常浏览的网页数据。如图：

![Http_cookie9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie9.png?raw=true)

用户不希望这些泄露，甚至被攻击。但事实上存在这个攻击。

全名：Cross Site Script，中文名：跨站脚本攻击。顾名思义，是指“HTML注入”纂改了网页，插入恶意的脚本，从而在用户用浏览网页的时候，控制用户浏览器的一种攻击。一般攻击的套路如图所示：

![Http_cookie10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie10.png?raw=true)

## 四、总结

Cookie是HTTP协议中的一种会话机制。

> 1、What 什么是Cookie   ;     2、How Cookie怎么用.
>
>   存在于请求响应头中的key-value键值对。 在服务端启用，用于服务于回话,服务器创建cookie通过响应头发送给客户端，客户端将其解析并存储在本地，然后在下次请求的时候也会将cookie带在请求头上。服务器获取cookie解析其信息。
>



# Session（会话）

## 一、Session由来

HTTP是**无状态**，也就是说**每次请求都是独立的线程**。举个例：购物中，你选择了A商品，加入购物车，这就是A线程。然后在选择B商品就是B线程。可是每次线程独立（对容器而言，A、B成了不同的用户），线程A不知道有B，B也不知道A。如何一起付款呢？

简答来说：**怎么保存同个用户多个请求会话状态呢**？自然**HTTPS**保证连接是安全的，可以使它与一个会话关联。问题就在于如何跟踪同一个用户，选择自然很多：

> 1、**EJB**(有状态会话bean保存会话状态) 环境苛刻需要带EJB的J2EE服务器，而不是Tomcat这种Web容器。
>
> 2、**数据库**（这貌似万能的。针对数据）
>
> 3、**HttpSession****，**保存跨一个特定用户多个请求的会话状态**。
>
> 4、上面说的**HTTPS**,条件太苛刻了。

 ![Http_session.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session.png?raw=true)

## 二、Session机制

机制:What？How？

**What is Session?**

Session代表着**服务器**和**客户端**一次**会话**的过程。直到**session失效**（服务端关闭），或者**客户端关闭**时结束。

**How does session works？**

Session 是**存储**在**服务端**的，针对每个客户端（客户），通过SessionID来区别不同用户的。Session是以Cookie技术或URL重写实现。默认以Cookie技术实现，服务端会给这次会话创造一个JSESSIONID的Cookie值。

**补充**：

> 其实还有一种技术：**表单隐藏字段**。它也可以实现session机制。服务器响应前，会修改form表单，添加一个sessionID类似的隐藏域，以便传回服务端的时候可以标示出此会话。
>
> 这技术，也可以使用在Web安全上，可以有效地控制**CSRF跨站请求伪造**。

## 三、Session机制过程

图中这是session**第一次请求**的详细图。以Cookie技术实现，

![Http_session2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session2.png?raw=true)

 `HttpSessionByCookieServlet.java` 的Servlet小demo，模拟Session的一生。

代码如下：

```java

/**
 * @author Jeff Lee
 * @since 2015-7-12 10:58:28
 *  HttpSession的默认Cookie实现案例
 */
@WebServlet(urlPatterns = "/sessionByCookie")
public class HttpSessionByCookieServletT extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession(); 
        //返回与当前request相关联的session，如果没有则在服务器端创建一个;如果已存在session，则会获取session。
        if (session.isNew()) {
            // 设置session属性值 
            session.setAttribute("name", "Jeff");
        }
        // 获取SessionId
        String sessionId = session.getId();
         
        PrintWriter out = resp.getWriter();
        // 如果HttpSession是新建的话
        if (session.isNew()) {
            out.println("Hello,HttpSession! <br>The first response - SessionId="
                    + sessionId + " <br>");
        } else {
            out.println("Hello,HttpSession! <br>The second response - SessionId="
                    + sessionId + " <br>");
            // 从Session获取属性值
            out.println("The second-response - name: "
                    + session.getAttribute("name"));
        }
         
    }
     
}

```

**① 客户端向服务端发送第一次请求**

此时，客户端想让服务端把自己的名字设置到会话中。

**② 服务端的容器产生该用户唯一sessionID的session对象，并设置值**

通过从请求中`req.getSession()`，新生成了一个session对象。并设置了setAttribute("name", "Jeff")，key为string，value是对象皆可。这时候，我们不用再把session通过cookie技术处理，**容器**帮我们**处理**了。

**③ 容器响应 Set-Cookie：JSESSIONID= ...**

![Http_session3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session3.png?raw=true)

每个Cookie的set，都有一个对应Set-Cookie的头。HttpOnly可是此Cookie只读模式。只不过**session唯一标识**是：**JSESSIONID**

**④ 浏览器解析Cookie，保存至浏览器文件。**

![Http_session4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session4.png?raw=true)

**第二次请求会发什么变化呢？**

![Http_session5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session5.png?raw=true)

重新访问了这个地址：

① 再次请求

![Http_session6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session6.png?raw=true)

此时，请求会有Cookie值：**JSESSIONID=… 该值传给服务端**

**② 容器获取SessionId，关联HttpSession**

**③ 此时响应无SetCookie**

如图：

![Http_session7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session7.png?raw=true)

但是这次请求，我们响应出上一次请求set的值。Jeff 就打印出来了！

关于服务端获取session，也就是从请求中获取session对象，容器会帮你根据Cookie找到唯一的session对象。

## 四、补充

![Http_session8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_session8.png?raw=true)

上图Bad guy，就是攻击者。跨站请求伪造，伪造用户请求来对服务器数据或者是用户等造成威胁。web安全也就是从这些基础中慢慢提升。

 

