# Jenkins学习视频

Jenkins的主要作用就是帮助我们简化部署项目。

![JAVA_JENKINS1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JENKINS1.png?raw=true)

jenkins自动构建化以后，只要我们一提交到最后的部署都是程序的自动操作，无需我们人为部署

![JAVA_JENKINS2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JENKINS2.png?raw=true)

## Jenkins搭建

### Jenkins相关配置安装

1：Jenkins部署的打包的war包最后要发布到Tomcat上，这时候不是我们直接访问Tomcat，而是程序访问，所以要对Tomcat进行配置

配置文件位置：/opt/tomcat/conf/tomcat-users.xml

```xml
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<role rolename="manager-jmx"/>
<role rolename="manager-status"/>
<user username="tomcat_user" password="123456"
roles="manager-gui,manager-script,manager-jmx,manager-status" />
```

2:jenkins 主程序安装，Jenkins本身只是个war包，所以只需要放在Tomcat容器中即可

把 jenkins.war 放在 Tomcat 解压目录/webapps 目录下

打开 Tomcat 解压目录/server.xml 修改 URL 地址的编码解码字符集

vim /opt/tomcat/conf/server.xml

```
<Connector port="8080" protocol="HTTP/1.1"
connectionTimeout="20000"
 redirectPort="8443" URIEncoding="UTF-8"/>
```

启动 Tomcat 并通过浏览器访问http://192.168.70.131:8080/jenkins

初始化配置，添加Maven，jdk,git的位置，具体参见课件

### 远程构建触发

原理是服务器给 Jenkins 项目特定的 URL 地址发送请求，
但必须以请求参数的形式携带一个特定值，这个特定值就是这里的“身份验证令牌”

```
比如我们这个项目的地址触发地址是：
http://192.168.70.131:8080/jenkins/job/ProOne(项目名)/build

身份验证令牌是：
ATGUIGU_TOKEN
那么最终的访问地址就是：
http://192.168.70.131:8080/jenkins/job/ProOne/build?token=ATGUIGU_TOK
```

 Linux 的 的 curl 命令 命令
Linux 的 curl 命令用来发送 HTTP 请求。

```
-X 参数：指定请求方式
-v 参数：显示响应结果
-u 参数：携带用户名/密码
-H 参数：携带请求消息头信息
curl -X post -v -u [Jenkins 用户名]:[Jenkins 密码] -H "请求消息头信息" http://[服务器 IP 地址]:[服务器端口号]/jenkins/job/[Jenkins 项目名称]/build?token=[身份验证令牌]
curl -X post -v -u admin:2f6bff33bda14baba83ba1c002045f05 -H
"Jenkins-Crumb:88a12946e07d82b3b0d567c7c4610c9a"
http://192.168.70.131:8080/jenkins/job/ProOne/build?token=ATGUIGU_TOKEN
```


