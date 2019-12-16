#                                                         Web安全

# 跨站脚本攻击XSS

## 一、认识XSS先

其实什么叫攻击，很简单。获取攻击者想要的信息，就黑成功了。

 Q: 什么是XSS? 为啥有这个呢？

  A: 全名：Cross Site Script，中文名：跨站脚本攻击。顾名思义，是指“HTML注入”纂改了网页，插入恶意的脚本，从而在用户用浏览网页的时候，控制用户浏览器的一种攻击。

![Http_cookie10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_cookie10.png?raw=true)

## 二、XSS攻击

  再来了解下XSS，是如何攻击？

 XSS Playload,所谓用以完成各种具体的功能的恶意脚本。常见的一个XSS Playload，就是通过读取浏览器的Cookie对象，从而发起了‘Cookie劫持’攻击。其中Cookie的‘HttpOnly’标识可以防止哦。

 强大的XSS Playload可以做以下的事情哈：1、构造 GET 与 POST 请求 2、各种钓鱼 3、识别用户浏览器 等等

Q：什么叫做钓鱼呢？
A：顾名思义，愿者上钩，这里做贬义用法。比如，人家用一个假的弹出框，或者假的页面让你输入QQ信息，或者啥账号信息。其实你一输入人家服务器获取到你的账户密码了。这就是鱼儿上钩了。 如图比喻：

![Http_XSS.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_XSS.png?raw=true)

## 三、XSS防御(重点)

  其实在看不到的地方很多已经对抗XSS做了些措施。比如各种浏览器等。

  一、按着上面的思路，先聊下Cookie，一个Cookie，我们是这样使用的：
1、浏览器下服务器发送请求，准备获取Cookie

2、服务器返回发送Cookie头，向客户端浏览器写入Cookie。（注意哦，这里是浏览器，不要当成什么浏览器内核）

3、在Cookie到期前，浏览器所有页面，都会发送Cookie。

这就意味着，我们Cookie不能乱用。就像Session一样，所以在使用的时候，要注意下。有时候Cooike在用于记住密码的时候，千万要注意要将Cookie设置HttpOnly属性为Ture。这里我以SpringMVC为例子。如果用到Cookie的时候，应该这样：



```java
`         ``// create cookie and set it in response``Cookie cookie1 = new Cookie("cookie1", "cookieValueHttpOnly");``Cookie cookie2 = new Cookie("cookie2", "cookieValue");``cookie1.setHttpOnly(true);` `response.addCookie(cookie1);``response.addCookie(cookie2);`
```





  二、输入校验

  输入校验的逻辑必须放在服务端中实现。如果用JS进行的话，容易被攻击者绕过去。所以普遍的做法是，类似很多代码一样进行Double Check:”客户端JS校验和服务端校验一起，这样客户端JS校验会阻挡大部分甚至说99%的用户的误操作。”

  在XSS防御上，我们需要对用户输入的一些特殊字符校验，过滤或者是编码。这种输入校验的方式成为“XSS Filter”。首先我们在配置文件中，

![Http_XSS2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Http_XSS2.png?raw=true)