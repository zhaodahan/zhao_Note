#                                     git 问题解决记录

# unable to access 'XXX':Empty reply from server

```
$ git pull origin master
fatal: unable to access 'https://github.com/zhaodahan/zhao_Note.git/': Empty reply from server
```

解决：**重置git配置的http或https代理的：**

```
git config --global --unset http.proxy
```

然后就成功pull



# 本地库已经删除了，但是依旧提交到了远程库

远程库与本地库不一致，使用git pull 无法将多余的文件拉取下来。

解决思路： 用远程库覆盖本地版本库。 然后修改后重新提交。



# git clone 和pull  速度太慢解决方案-----git 使用代理



windows上进入c:\windows\system32\drivers\etc，找到hosts

```
151.101.72.249 http://global-ssl.fastly.Net
192.30.253.112 http://github.com
```

cmd : 刷新DNS来生效

```
ipconfig /flushdns
```

修改使用ssr 来连接

```
git config --global http.proxy http://127.0.0.1:1080
git config --global https.proxy https://127.0.0.1:1080

git clone https://www.github.com/xxxx/xxxx.git
这种

对于SSH协议，也就是

git clone git@github.com:xxxxxx/xxxxxx.git
这种，依旧是无效的


只对github进行代理，对国内的仓库不影响git config --global http.https://github.com.proxy https://127.0.0.1:1080
git config --global https.https://github.com.proxy https://127.0.0.1:1080
```

使用完之后取消代理以便不会影响提交

```
git config --global --unset http.proxy
git config --global --unset https.proxy
```

注意： git 里不要提交大于20M 的文件否则容易卡主

Unpacking objects  卡主

```
需要使用git https代理来处理
```



# git pull 卡死情况

```
先执行 git gc

```

如果实在不行 重新clone

#  使用git提交代码到github,每次都要输入用户名和密码的解决方法

```
找到项目的.git目录下的config文件： 
修改url路径
将其改为如下格式： 
https://username:password@github.com/username/project.git 
在这里，username就是你的用户名，比如我的用户名是CmderQ，那么改成之后的结果是： 
url= https://CmderQ:#####@github.com/CmderQ/hello.git 
这里的”#####”代表的就是我这个账户的密码。改完之后，保存

例如: 我的修改
#	url = https://github.com/zhaodahan/zhao_Note.git
    url = https://zhaodahan:zhao7494@github.com/zhaodahan/zhao_Note.git
```

# Github下载仓库中单个文件的方法

```
使用下述规则下载
https://raw.githubusercontent.com/【user】/【repository】/【branch】/【filename】
如：https://raw.githubusercontent.com/afad-dataset/tarball/master/AFAD-Full.tar.xzae
```

