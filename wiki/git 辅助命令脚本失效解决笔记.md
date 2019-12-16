## git 辅助脚本失效的解决笔记

## 起因

改变了原理的仓库的存储结构。 导致

![git_bash_pic_invalid.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/git_bash_pic_invalid.png?raw=true)

问题提示是：

```
pull origin master:
fatal: not a git repository(or any of the e parenIt dit rectories): .git
```

按照错误日志是没有识别出这事一个git仓库，但是脚本所在的目录确实是git仓库的地址。 可能是我改变仓库的目录时候破坏了器原有的目录结构。

## 尝试解决

网上查阅资料：

解决办法：

1： 在目录执行 `git init`

```
.init Create an empty git repository or reinitialize an existing one
可以重新初始化仓库文件。
```

![git_bash_pic_invalid2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/git_bash_pic_invalid2.png?raw=true)

重新执行脚本：

```
pull origin master:
fatal: not a git repository(or any of the e parenIt dit rectories): .git
```

仍然失败。 

2 ： 网上没有找到具体的解决办法， 自己来，直接在脚本中让他跳转到我们的仓库目录。，

```
# Git装好后有个cmd目录，把这个路径加到Windows的path环境变量下，然后像平时写bat批处理文件一样，把git命令写到里边去就行了。不用再做bash的login操作
#echo .....Jump to folder E:\komi\komiTest（跳转至该路径，是一个git项目，应该会有一个.git文件夹。如果这个bat文件已经在git项目路径下了，可以不用跳转，直接写git命令，会对该git项目直接进行操作）

cd F:\gitRepository\zhao_Note
echo .....!!!!git checkout master 
git pull origin master
git  add .
git commit  -m 'pic_synchronized'
git push origin master
echo .....enter any key to exit
pause>nul
```

然后尝试 执行脚本。 成功解决。

3 。 但是这样仍然有一个问题，那就是在公司，我使用的git 仓库的目录与自己笔记本上的是不同。 这样提交到git 仓库上就会覆盖公司那里的脚本。

解决办法。 使用 gitignore 忽略这个文件。

实施之后进行测试。

重新改回脚本再次看一下能不能发现脚本

发现会被提交。 这是使用git 笔记中git 忽略失败 的操作方法尝试成功，但是不知道

```
git update-index --assume-unchanged 文件名
```

 这个命令是否效果永久



