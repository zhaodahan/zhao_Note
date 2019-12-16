# Git&GitHub学习视频

## 什么是git

![JAVA_GIT1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_GIT1.png?raw=true)

```
git 是分布式版本控制工具，他保存的是文件系统的快照

Git 的优势 的优势
 大部分操作在本地完成，不需要联网 (本地库)
 完整性保证 （hash 算法操作）
 尽可能添加数据而不是删除或修改数据 (就能减少不可逆的操作，每次提交都是保存的一个新的版本，没有删除原有数据)
 分支操作非常快捷流畅
 与 Linux 命令全面兼容
```

**git 结构**

![JAVA_GIT2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_GIT2.png?raw=true)

## GitHub

```
GitHub是git的代码托管中心，就是我们普遍的远程仓库
```

![JAVA_GIT3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_GIT3.png?raw=true)

## 本地库初始化

```
1：git init 生成 .git目录
2：设置签名区分不同开发人员的身份，与远程库的用户名和密码无关系
(git 要求我们设置用户名 区分不同开发人员的身份)
用户名：tom
Email 地址：goodMorning@atguigu.com
====命令：
git config user.name tom_pro
git config user.email goodMorning_pro@atguigu.com

```



## 如何实现git版本的前进和后退

![JAVA_GIT4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_GIT4.png?raw=true)

```
Git 是分布式版本控制系统，所以我们需要对各种版本进行操作
git 版本控制的本质是移动head 指针

命令： git reset --hard 目标版本
```

## 删除文件找回

```
前提：删除前，文件存在时的状态要已经提交到了本地库。 (所有找回删除文件操作的前提)
(git添加数据而不是删除,每次的提交记录都会保存不会删除掉，且每次保存的都是一个文件快照)
操作：git reset --hard [指针位置]

删除操作已经提交到本地库：指针位置指向历史记录

已将提交到本地库后，在删除且添加到暂存区的了，如何进行找回：
删除操作尚未提交到本地库：指针位置使用 HEAD
git reset --hard  HEAD （缓存区和工作区都会被重置，都会被刷新为HEAD指针指向的位置）
```

## git 文件版本管理机制

```
Git 把数据看作是文件系统的一组快照。每次提交更新时 Git 都会对当前的全部文件制作一个快照并保存这个快照的索引。为了高效，如果某些文件没有修改，Git 不再重新存储该文件，而是只保留一个链接指向之前存储的文件。所以 Git 的工作方式可以称之为快照流。
```

![JAVA_GIT5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_GIT5.png?raw=true)



![JAVA_GIT6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_GIT6.png?raw=true)

## 分支管理机制

分支操作

```
创建分支
git branch [分支名]
查看分支
git branch -v
切换分支
git checkout [分支名]
合并分支
第一步：站在需要合并其他内容的分支上
git checkout [被合并分支名]
第二步：执行 merge 命令
git merge [有新内容分支名]
```

原理：

分支的本质是指针

![JAVA_GIT7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_GIT7.png?raw=true)

## SSH 免密登录

```
进入当前用户的家目录
$ cd ~
 删除.ssh 目录
$ rm -rvf .ssh
运行命令生成.ssh 密钥目录
$ ssh-keygen -t rsa -C atguigu2018ybuq@aliyun.com
 ssh-keygen -t rsa -C 1791154630@qq.com
 (生成秘钥的时候全部默认)
[ 注意：这里-C 这个参数是大写的 C]
进入.ssh 目录查看文件列表
$ cd .ssh
$ ls -lF
查看 id_rsa.pub 文件内容
$ cat id_rsa.pub
复制 id_rsa.pub 文件内容，登录 GitHub，点击用户头像→Settings→SSH and GPG
keys
New SSH Key
输入复制的密钥信息
回到 Git bash 创建ssh远程地址别名
git remote add origin_ssh git@github.com:atguigu2018ybuq/huashan.git
推送文件进行测试，推送到origin_ssh ssh别名处
```

## git中忽略文件

```
1： 创建我们的.gitignore
.classpath
.project
.settings
target
2:在~/.gitconfig 文件中引入上述文件
[core]
excludesfile = C:/Users/Lenovo/Java.gitignore
[ 注意：这里路径中一定要使用“ “/” ” ，不能使用“ “\

```


