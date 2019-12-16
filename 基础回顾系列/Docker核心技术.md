# Docker核心技术

![JAVA_DOCKER1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER1.png?raw=true)

docker的主要目的也是为了简化(因环境变化的)部署。

解决了运行环境和配置问题**软件容器**，方便做持续集成并有助于整体发布的容器虚拟化技术。

docker就是一个精简版的小型Linux系统。

docker三要素：

```
镜像（image） ： 类比java中的类
容器（container）： 类比java中的类产生的实例 new Person（）
仓库（repository）： 类比gitHub ，存放类(镜像)的远程仓库

Docker 本身是一个容器运行载体或称之为管理引擎(镜像大管家)。我们把应用程序和配置依赖打包好形成一个可交付的运行环境(我们搬家直接搬一栋楼)，这个打包好的运行环境就是 image镜像文件。只有通过这个镜像文件才能生成 Docker 容器。image 文件可以看作是容器的模板。Docker 根据 image 文件生成容器的实例。同一个 image 文件，可以生成多个同时运行的容器实例。
 
 一个容器运行一种服务，当我们需要的时候，就可以通过docker客户端创建一个对应的运行实例，也就是我们的容器(运行的)
```



## 阿里云镜像加速

详见Docker.mmap

![JAVA_DOCKER2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER2.png?raw=true)

![JAVA_DOCKER3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER3.png?raw=true)

```
 vim /etc/sysconfig/docker
   将获得的自己账户下的阿里云加速地址配置进
other_args="--registry-mirror=https://你自己的账号加速信息.mirror.aliyuncs.com"
```

## docker 的运行原理

![JAVA_DOCKER4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER4.png?raw=true)

docker的工作机制类似于git ,他的Client和server都在本地。有一个本地仓库和远程仓库。

每个docker容器都是一个精简版的Linux系统



![JAVA_DOCKER5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER5.png?raw=true)
![JAVA_DOCKER6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER6.png?raw=true)
![JAVA_DOCKER7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER7.png?raw=true)
![JAVA_DOCKER8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER8.png?raw=true)
![JAVA_DOCKER9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER9.png?raw=true)
![JAVA_DOCKER10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER10.png?raw=true)
![JAVA_DOCKER11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER11.png?raw=true)
![JAVA_DOCKER12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER12.png?raw=true)
![JAVA_DOCKER13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER13.png?raw=true)
![JAVA_DOCKER14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER14.png?raw=true)
![JAVA_DOCKER15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER15.png?raw=true)
![JAVA_DOCKER16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER16.png?raw=true)
![JAVA_DOCKER17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER17.png?raw=true)
![JAVA_DOCKER18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER18.png?raw=true)
![JAVA_DOCKER19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER19.png?raw=true)
![JAVA_DOCKER20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER20.png?raw=true)
![JAVA_DOCKER21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER21.png?raw=true)
![JAVA_DOCKER22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER22.png?raw=true)
![JAVA_DOCKER23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER23.png?raw=true)
![JAVA_DOCKER24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER24.png?raw=true)
![JAVA_DOCKER25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER25.png?raw=true)
![JAVA_DOCKER26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER26.png?raw=true)
![JAVA_DOCKER27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER27.png?raw=true)
![JAVA_DOCKER28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER28.png?raw=true)
![JAVA_DOCKER29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER29.png?raw=true)
![JAVA_DOCKER30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER30.png?raw=true)
![JAVA_DOCKER31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER31.png?raw=true)
![JAVA_DOCKER32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER32.png?raw=true)
![JAVA_DOCKER33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER33.png?raw=true)
![JAVA_DOCKER34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER34.png?raw=true)
![JAVA_DOCKER35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER35.png?raw=true)
![JAVA_DOCKER36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER36.png?raw=true)
![JAVA_DOCKER37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER37.png?raw=true)
![JAVA_DOCKER38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER38.png?raw=true)
![JAVA_DOCKER39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER39.png?raw=true)
![JAVA_DOCKER40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER40.png?raw=true)
![JAVA_DOCKER41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER41.png?raw=true)
![JAVA_DOCKER42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER42.png?raw=true)
![JAVA_DOCKER43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER43.png?raw=true)
![JAVA_DOCKER44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER44.png?raw=true)
![JAVA_DOCKER45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER45.png?raw=true)
![JAVA_DOCKER46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER46.png?raw=true)
![JAVA_DOCKER47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER47.png?raw=true)
![JAVA_DOCKER48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER48.png?raw=true)
![JAVA_DOCKER49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER49.png?raw=true)
![JAVA_DOCKER50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_DOCKER50.png?raw=true)