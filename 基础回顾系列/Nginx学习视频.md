---
title: Nginx笔记
 date: 2019-11-05 16:17:53
 tags: 
	 - note
     - Nginx
---

# Nginx学习视频

## 什么是Nginx？

Nginx同Apache一样都是一种WEB服务器。基于REST架构风格，通过HTTP协议提供各种网络服务。

Apache由于它被设计为一个重量级的。它不支持高并发的服务器。轻量级高并发服务器Nginx就应运而生了。

Nginx的主要作用：作为`高性能的HTTP服务器`和`反向代理服务器` 和做`负载均衡`

Nginx的特点：占用内存少，并发能力强。 （nginx 是专门为了性能优化而开发）

### 代理

生活中的专卖店~客人到adidas专卖店买了一双鞋，这个专卖店就是代理，被代理角色就是adidas厂家，目标角色就是用户。

#### 正向代理

正向代理 类似我们翻墙。客户端非常明确要访问的服务器地址；服务器只清楚请求来自哪个代理服务器，而不清楚来自哪个具体的客户端；正向代理模式屏蔽或者隐藏了真实客户端信息。

![JAVA_NGINX1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX1.png?raw=true)

正向代理，"它代理的是客户端，代客户端发出请求"。

#### 反向代理

客户端是无感知代理的存在的，反向代理对外都是透明的，访问者并不知道自己访问的是一个代理。因为客户端不需要任何配置就可以访问。

反向代理，"它代理的是服务端，代服务端接收请求"，主要用于服务器集群分布式部署的情况下，反向代理隐藏了服务器的信息。

![JAVA_NGINX2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX2.png?raw=true)

#### 实际项目中的场景

![JAVA_NGINX3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX3.png?raw=true)

### 负载均衡

当一台服务器的单位时间内的访问量越大时，服务器压力就越大，大到超过自身承受能力时，服务器就会崩溃。为了避免服务器崩溃，让用户有更好的体验，我们通过负载均衡的方式来分担服务器压力。
我们可以建立很多很多服务器，组成一个服务器集群，当用户访问网站时，先访问一个中间服务器，在让这个中间服务器在服务器集群中选择一个压力较小的服务器，然后将该访问请求引入该服务器。如此以来，用户的每次访问，都会保证服务器集群中的每个服务器压力趋于平衡，分担了服务器压力，避免了服务器崩溃的情况。

增加服务器的数量，然后将请求分发到各个服务器上，将原先请求集中到单个服务器上的情况改为将请求分发到多个服务器上，将负载分发到不同的服务器，也就是我们所说的负载均衡

![JAVA_NGINX4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX4.png?raw=true)

### 动静分离

为了加快网站的解析速度，可以把动态页面和静态页面由不同的服务器来解析，加快解析速度。降低原来单个服务器的压力。

![JAVA_NGINX5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX5.png?raw=true)

## Nginx 的安装，常用命令，后缀文件

### 安装

<https://blog.csdn.net/t8116189520/article/details/81909574>

<https://blog.csdn.net/xukongjing1/article/details/83824219>

和参照课件笔记

注意：Linux防火墙默认并不开发所有端口

```
可以通过命令开发
查看开放的端口号
firewall-cmd --list-all
设置开放的端口号
firewall-cmd --add-service=http –permanent
firewall-cmd --add-port=80/tcp --permanent
重启防火墙
firewall-cmd –reload
```



### 常用命令

```
这些命令的使用前提：进入 nginx 目录中
cd /usr/local/nginx/sbin
1 、查看 nginx 版本号
./nginx -v
2 、启动 nginx
./nginx
3 、停止 nginx
./nginx -s stop
4 、重新加载 nginx
./nginx -s reload
```

### Nginx的配置文件

![JAVA_NGINX6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX6.png?raw=true)

```
nginx 配置文件位置：/usr/local/nginx/conf/nginx.conf

配置文件中的内容
包含三部分内容
（1 ）全局块：
从配置文件开始到 events 块之间的内容，配置服务器整体运行的配置指令。
比如 worker_processes 1; 处理并发数的配置，worker_processes 值越大，可以支持的并发处理量也越多

（2 ）events 块 ：主要影响 Nginx 服务器与用户的网络连接
比如 worker_connections 1024; 支持的最大连接数为 1024
（3 ）http 块：Nginx 服务器配置中最频繁的部分，代理、缓存和日志定义等绝大多数功能和第三方模块的配置
它又包含两部分：
http 全局块：包括文件引入、MIME-TYPE 定义、日志自定义、连接超时时间、单链接请求数上限等。
server 块：server 块就相当于一个虚拟主机
```



## Nginx配置实例

### 反向代理

#### **案例一：**通过Nginx转发到服务器中

实现效果：打开浏览器，在浏览器地址栏输入地址 www.123.com ，访问到对外暴露的Nginx服务器，Tomcat不对外暴露，通过Nginx代理，跳转到 liunx 系统 tomcat 主页

![JAVA_NGINX7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX7.png?raw=true)

![JAVA_NGINX8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX8.png?raw=true)

```
在 nginx 进行请求转发的配置（反向代理配置）
    # erp项目
    upstream erp {
		server 192.168.183.130:8080; 
    }
    server {
        listen       80;
        server_name  xushuai.erp.com;
 
        location / {
            proxy_pass   http://192.168.183.130:8080; //代理的地址，要转发到的地址
			proxy_read_timeout 600s;
			proxy_set_header  X-Real-IP  $remote_addr;
			proxy_set_header Host $host:$server_port;
			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
         
            index  index.html index.htm;
        }
 
 
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
```



#### **案例二：**通过路径转发到不同的服务

实现效果：使用 nginx 反向代理，根据访问的路径跳转到不同端口的服务中

nginx 监听端口为 9001，

访问 http://192.168.17.129:9001/edu/ 直接跳转到 127.0.0.1:8080
访问 http:// 192.168.17.129:9001/vod/ 直接跳转到 127.0.0.1:8081

需要两个 tomcat 服务器，一个 8080 端口，一个 8081 端口

![JAVA_NGINX9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX9.png?raw=true)

```
具体配置
server可以开多个
server {
    listen 9001；#监听的端口
    server_name _;
 #配置映射规则  ~ 表示后面是一种正则表达式的形式   
 location ~/edu/ {
      proxy_pass http://192.168.169.103:8080;
                  }
 
 location ~/vod/ {
      proxy_pass http://192.168.169.113:8081;   
           }
 
 location ~/node3 {
      proxy_pass http://192.168.169.154:80;
           }
}
```

#### location 指令说明

该指令用于匹配 URL

![JAVA_NGINX10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX10.png?raw=true)

### 负载均衡

实现效果：浏览器地址栏输入地址 http://192.168.17.129/edu/a.html ，达到负载均衡效果，平均 8080和 和 8081 端口中
准备工作：准备两台 tomcat 服务器，一台 8080 ，一台 8081

![JAVA_NGINX11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX11.png?raw=true)

```
upstream tomcatserver1 { 
    #ip_hash (根据情况可选)
    server 192.168.72.49:8080 weight=3;  
    server 192.168.72.49:8081;  
    }   
  
 server {  
        listen       80;  
        server_name  8080.max.com;  
        #charset koi8-r;  
        #access_log  logs/host.access.log  main;  
        location ~/edu/ {  
            proxy_pass   http://tomcatserver1;  
            index  index.html index.htm;  
        }  
     }
     
nginx 分配服务器负载均衡策略
第一种 轮询（默认）
每个请求按时间顺序逐一分配到不同的后端服务器，如果后端服务器 down 。 掉，能自动剔除。
第二种 weight (常用)
weight 代表权重默认为 1, 权重越高被分配的客户端越多
第三种 ip_hash (常用)
每个请求按访问 ip 的 的 hash 结果分配，这样每个访客固定ip会固定访问一个后端服务器。一定程度上解决session共享问题
第四种 fair （第三方）
按后端服务器的响应时间来分配请求，响应时间短的优先分配。 (需要额外安装upstream_fair模块)    
     
```



### 动静分离

![JAVA_NGINX12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX12.png?raw=true)

```
通过 location 指定不同的后缀名实现不同的请求转发。通过 expires 参数设置，可以使浏览器缓存过期时间，减少与服务器之前的请求和流量。具体 Expires 定义：是给一个资源设定一个过期时间，也就是说无需去 服务端验证，直接通过浏览器自身确认是否过期即可，所以不会产生额外的流量。此种方法非常适合不经常变动的资源。（如果经常更新的文件，
不建议使用 Expires 来缓存），我这里设置 3d ，表示在这 3 天之内访问这个 URL ，发送一个请求，比对服务器该文件最后更新时间没有变化，则不会从服务器抓取，返回状态码 304 ，如果有修改，则直接从服务器重新下载，返回状态码 200 
```

![JAVA_NGINX13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX13.png?raw=true)

```
erver {
　　listen 80;
　　server_name 192.168.25.35; #  当接收到http请求时，首先host和这里的server_name进行匹配，如果匹配上，则走这个虚拟主机的location路由

　　location /static/~(.*)(\.jpg|\.png|\.gif|\.jepg|\.css|\.js|\.css){  #  静态资源则路由到这里
　　   # root  /data;
　　　　alias html;
　　}

　　location / {  #  其他的url则转发到 http://192.168.25.35:8080
　　　　proxy_pass http://192.168.25.35:8080;

　　}

}
```



### 高可用集群

高可用，就要避免单点故障，就要配置集群

![JAVA_NGINX14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX14.png?raw=true)

![JAVA_NGINX15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX15.png?raw=true)

```shell
准备工作: 总纲
（1 ）需要两台 nginx 服务器
（2 ）需要 keepalived
（3 ）需要虚拟 ip

2 、配置高可用的准备工作
（1 ）需要两台服务器 192.168.17.129 和 192.168.17.131
（2 ）在两台服务器安装 nginx
（3 ）在两台服务器安装 keepalived

3 、在两台服务器安装 keepalived
（1 ） 使用 yum 命令进行安装
yum install keepalived –y
（2 ）安装之后，在 etc 里面生成目录 keepalived ，有文件 keepalived.conf

4 、完成高可用配置（主从配置）
（1 ）修改/etc/keepalived/keepalivec.conf 配置文件
    global_defs {
        notification_email {
        acassen@firewall.loc
        failover@firewall.loc
        sysadmin@firewall.loc
        }
        notification_email_from Alexandre.Cassen@firewall.loc
        smtp_server 192.168.17.129
        smtp_connect_timeout 30
        router_id LVS_DEVEL #通过LVS_DEVEL可以访问到主机，在/etc/host中配置的
    }
    vrrp_script chk_http_port {
    script "/usr/local/src/nginx_check.sh"  #脚本存放位置
    interval 2 #（检测脚本执行的间隔）
    weight 2  #权重
    }
    vrrp_instance VI_1 {
    state BACKUP # 备份服务器上将 MASTER(主) 改为 BACKUP(从)
    interface ens33 #网卡，ifcofig查看的网卡名称
    virtual_router_id 51 # 主、备机的 virtual_router_id 必须相同，唯一标识
    priority 90 # 主、备机取不同的优先级，主机值较大，备份机值较小
    advert_int 1 # 检查心跳时间间隔
    authentication { # 权限检验方式
        auth_type PASS
        auth_pass 1111
       }
    virtual_ipaddress {
    192.168.17.50 # VRRP H 虚拟地址 ，可以绑定多个ip
    }
   }
（2 ）在/usr/local/src 添加检测脚本
        #!/bin/bash
        A=`ps -C nginx –no-header |wc -l`
        if [ $A -eq 0 ];then
        /usr/local/nginx/sbin/nginx  #ngix脚本位置
        sleep 2
            if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then
            killall keepalived
            fi
        fi
（3 ）把两台服务器上 nginx 和 和 keepalived 启动
启动 nginx ：./nginx
启动 keepalived ：systemctl start keepalived.service
5 、最终测试
（1 ）在浏览器地址栏输入 虚拟 ip 地址 192.168.17.50
```



## Nginx原理

1：mater(管理员) 和  worker（实际工作者） (Nginx工作过程中的两个进程)

![JAVA_NGINX16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX16.png?raw=true)

2:worker 如何进行工作的

![JAVA_NGINX17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX17.png?raw=true)

3:一个 master 和多个 woker 有好处
（1 ）可以使用 nginx –s reload 热部署，利用 nginx 进行热部署操作
（2 ）每个 woker 是独立的进程，如果有其中的一个 woker 出现问题，其他 woker 独立的，继续进行争抢，实现请求过程，不会造成服务中断

4 、设置多少个 woker 合适

worker 数和服务器的 cpu 数相等是最为适宜的

5 、连接数 worker_connection
第一个：发送请求，占用了 woker 的几个连接数？
答案：2 或者 4 个

![JAVA_NGINX18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_NGINX18.png?raw=true)

第二个：nginx 有一个 个 master ，有四个 woker ，每个 woker 支持最大的连接数 1024 ，支持的
最大并发数是多少？
 普通的静态访问最大并发数是： `worker_connections * worker_processes /2` ，
 而如果是 HTTP 作 为反向代理来说，最大并发数量应该是 `worker_connections *worker_processes/4` 
