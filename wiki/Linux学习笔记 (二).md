#                            Linux学习笔记 (二)

------

# 服务管理



## 服务的简介与分类

![Linux_service.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service.png?raw=true)

类似于windows中安装软件，有一些只是安装成了系统常用软件(如 qq )， 而有一些则是系统的应用服务 （如驱动）。

 所谓的服务管理就是干两件事，服务的**启动** (手动启动)和服务的**自启动 **（开机启动）

![Linux_service2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service2.png?raw=true)

 其实不管是RPM包，还是源码包，只不过是初始安装方式不一样而已，如果已经在Linux系统上安装完成之后，那么启动服务都是可以通过如下方式启动：

```
`/绝对路径/启动脚本名 start`
```

　　之所以RPM包还有诸如 service 服务名 start 等启动方式是由于其安装位置差异造成的。

![Linux_service3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service3.png?raw=true)



## RPM 包安装的服务的管理

### 独立服务的启动与自启动

![Linux_service4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service4.png?raw=true)

![Linux_service5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service5.png?raw=true)

**Linux中所有的可执行文件如果想运行，就需要找到启动脚本(无论是绝对路径还是相对路径)， 运行启动脚本**。

![Linux_service6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service6.png?raw=true)

RPM 包安装的httpd服务启动方式有两种：

```
`①、/etc/rc.d/init.d/httpd start``②、service httpd start`
```

第二种命令是第一种命令的简化形式。这是红帽专有命令。service 会去 rpm 包默认的安装位置去找可执行文件，所以service类似的启动服务管理命令只能管理rpm安装包。如果更改了rpm包的默认安装位置，可能造成通过service启动服务失败。

**自启动**:

![Linux_service7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service7.png?raw=true)

![Linux_service8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service8.png?raw=true)

![Linux_service9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service9.png?raw=true)

![Linux_service10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service10.png?raw=true)

![Linux_service11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service11.png?raw=true)

这几种方法中，修改/etc/rc.local或者/etc/rc.d/rc.local 配置文件是最推荐使用的，因为他不仅可以自启动RPM包还可以启动源码包安装，checkconfig 就无法查看到非RPM包安装的，netsysv 不是红帽系列的就无法使用。

### 基于xinetd的服务的启动与自启动 (了解)

这个服务也是属于 RPM包，现在Linux系统中基于 xinetd 服务越来越少了，启动服务我们只需要修改 /etc/xinetd.d/服务名   这个文件的 disable = no ，然后重启xinetd服务。 (因为我们想启动的服务是基于xinetd)

![Linux_service13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service13.png?raw=true)

![Linux_service12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service12.png?raw=true)

自启动也可以通过 chkconfig 服务名 on 来设置。还可以通过 ntsysv 配置。

## 源码包安装的服务的管理

![Linux_service14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service14.png?raw=true)

![Linux_service15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service15.png?raw=true)

源码包的启动是使用**绝对路径**的启动。

自启动

![Linux_service16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service16.png?raw=true)

**如果想让service命令来管理源码包安装**

service的原理就是搜索 /etc/init.d 下面的目录。

![Linux_service17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service17.png?raw=true)

其他：

![Linux_service18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_service18.png?raw=true)

# 系统管理

## 进程管理

进程： 正在运行中的程序。一个程序如果要运行，肯定需要产生一个或多个进程。

进程管理的最主要任务不是杀掉进程，而是判断进程的健康状态。

### 进程查看

查看系统中所有的进程

![Linux_process.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process.png?raw=true)

![Linux_process2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process2.png?raw=true)

查看系统健康状态

![Linux_process3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process3.png?raw=true)

![Linux_process4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process4.png?raw=true)

![Linux_process5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process5.png?raw=true)

(僵尸进程，正在结束，但是没有结束完全)

![Linux_process6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process6.png?raw=true)

![Linux_process7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process7.png?raw=true)

![Linux_process8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process8.png?raw=true)

![Linux_process9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process9.png?raw=true)

top 命令比较消耗资源，没事不常用。

![Linux_process10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process10.png?raw=true)

### 进程管理 

终止进程

![Linux_process11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process11.png?raw=true)

![Linux_process12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process12.png?raw=true)

将某个终端踢出

![Linux_process13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process13.png?raw=true)

## 工作管理

工作管理是就是将某个进程放入后台。(类似windows中的将某个窗口最小化)

![Linux_process14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process14.png?raw=true)

恢复

![Linux_process15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process15.png?raw=true)

组合使用

```
Ctrl +z  将程序放入到后台
fg       默认将最后一个恢复到前台
```



## 系统资源查看

(就是查看系统属性)

![Linux_process16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process16.png?raw=true)

![Linux_process17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process17.png?raw=true)

![Linux_process18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process18.png?raw=true)

![Linux_process19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process19.png?raw=true)

![Linux_process20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process20.png?raw=true)

![Linux_process21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_process21.png?raw=true)

## 系统定时任务

让计算机在指定的时间执行指定的任务。（十分常用）

如果想让系统中的定时任务生效，必须启动crond  (d 代表守护进程)服务。

![Linux_systemTimeWork.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_systemTimeWork.png?raw=true)

![Linux_systemTimeWork2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_systemTimeWork2.png?raw=true)

![Linux_systemTimeWork3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_systemTimeWork3.png?raw=true)



# 日志管理



## 日志简介

如同我们做开发一样，日志记录了我们做的那些事情， 如果出现了问题，我们可以通过日志来定位问题，发现原因， 最终解决问题。这就相当一个监控，当我们出现了问题，我们的第一操作就是查看我们的监控(日志)。

![Linux_log.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log.png?raw=true)

![Linux_log2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log2.png?raw=true)

![Linux_log3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log3.png?raw=true)

除了系统默认日志以外，采用RPM包安装的系统服务也会将日志记录在/var/log/包名 目录中。源码包的日志在源码包手动的指定目录中。这些日志不由rsyslogd日志服务管理。

常见的有：

![Linux_log4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log4.png?raw=true)

## rsyslogd 日志服务



![Linux_log5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log5.png?raw=true)

![Linux_log6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log6.png?raw=true)

![Linux_log7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log7.png?raw=true)

## 日志轮替

两件事： 切割(把大的日志按照规则--一般按天 切割成小日志)   ,   轮换(把旧的日志---超过30天后的日志就不保留了删除)

Linux 自带日志轮替工具

![Linux_log8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log8.png?raw=true)

![Linux_log9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log9.png?raw=true)

![Linux_log10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log10.png?raw=true)

![Linux_log11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log11.png?raw=true)

![Linux_log12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_log12.png?raw=true)

# 启动管理

 

## CentOS 6.3 启动管理

系统运行级别：

![Linux_StartUp.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp.png?raw=true)

![Linux_StartUp2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp2.png?raw=true)

不推荐使用`init 0` 来关机，因为他类似Windows中的强制退出，并没有保存并正确退出一些正在运行的程序。

![Linux_StartUp3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp3.png?raw=true)



### Linux 的启动过程

![Linux_StartUp4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp4.png?raw=true)

## 启动引导程序grub

grub配置	

![Linux_StartUp5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp5.png?raw=true)

![Linux_StartUp6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp6.png?raw=true)

![Linux_StartUp7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp7.png?raw=true)

注意配置文件这里的“/” 表示的是/boot ,而不是真正的根目录。

如果在服务器中同时安装了windows和Linux，应该先安装widows，因为windows的引导程序不会识别Linux，但是grub会识别widows。 如果先安装了Linux，windows引导程序就会覆盖Linux，导致无法发现Linux。

## 系统修复模式-备份与恢复

## 什么是备份

备份的原则是不把鸡蛋 放在同一个篮子里。

![Linux_StartUp8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp8.png?raw=true)

![Linux_StartUp9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp9.png?raw=true)

差异备份是折中策略，备份量比增量多，恢复比增量少。	

## dump 与 rstore命令	

![Linux_StartUp10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp10.png?raw=true)

![Linux_StartUp11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp11.png?raw=true)

![Linux_StartUp12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp12.png?raw=true)

![Linux_StartUp13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_StartUp13.png?raw=true)

