#                                                       GIT 笔记

# 搭建git 仓库

## 环境安装 

------

介绍window系统如何安装
下载路径：[Git客户端](http://git-scm.com/download)
安装后再任意地方鼠标右键就会出现如下，`Git Bash Here`就是我们的客户端
[![img](http://upload-images.jianshu.io/upload_images/1637925-3982651ccae9e7db.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-3982651ccae9e7db.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
[![img](http://upload-images.jianshu.io/upload_images/1637925-1a5cde2c6ea71d56.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-1a5cde2c6ea71d56.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 初次运行 Git 前的配置

------

安装完`Git`应该做的第一件事就是设置你的用户名称与邮件地址。 这样做很重要，因为每一个`Git`的提交都会使用这些信息，并且它会写入到你的每一次提交中，不可更改：

```
git config --global user.name "zhaodahan"
git config --global user.email 1791154630@qq.com
```

再次强调，使用了 `--global` 选项，那么该命令只需要运行一次，因为之后无论你在该系统上做任何事情， Git 都会使用那些信息。 当你想针对特定项目使用不同的用户名称与邮件地址时，可以在那个项目目录下运行没有 `--global` 选项的命令来配置。

## 创建版本库

------

指定一个目录作为你的仓库地址，如使用`E:\gitRepository`作为后期所有项目的总空间。
创建我的第一个项目名~起名叫做`gitDemo`，这个直接在window新建一个文件夹就行。
然后在`gitDemo`目录里面，右键鼠标打开`git`客户端，输入创建版本库命令：

```
git init
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-6aeb26e51005605f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-6aeb26e51005605f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这样仓库建好了，而且告诉你是一个空的仓库（empty Git repository），可以发现当前目录下多了一个`.git`的目录(建议隐藏的)，这个目录是`Git`来跟踪管理版本库的，不要手动修改这个目录里面的文件，否则就把Git仓库给破坏了。

## 添加文件到版本库

------

`git`仓库搭建好了，现在就来添加文件到版本库里面。
创建一个`txt`文档起名叫做`readme.txt`，往里面添加一点内容`hello git`.
注意，最好不要用系统自带的`txt`来编写，因为这里需要文件是`UTF-8`格式的，所以我使用`EditPlus`这种可以把文件另存为`UTF-8`格式的编辑器的。
创建好文件后，添加到仓库只需要2个操作：
第一步，用命令`git add`告诉`Git`，把本地代码托送到暂存区

```
git add readme.txt
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-d9bcfde50a5da466.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-d9bcfde50a5da466.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
第二步，用命令`git commit`告诉`Git`，把文件提交到仓库：

```
git commit -m "first commit"
```



[![img](http://upload-images.jianshu.io/upload_images/1637925-112ae4b062a87c61.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-112ae4b062a87c61.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

`-m` 参数是用来注释你提交的信息的，这样以后才知道这次提交时用来干嘛

或者你嫌弃文件太多，一次次add感觉很麻烦，那么可以试试使用`git add .`提交，`.`表示提交当前目录所有文件

```
git add . 
或者
git add *
```

## 查看当前新增或者修改的文件

------

实际使用中你不止只有一个文件，或新增或修改多个文件，可能时间一久就忘了有多少文件需要提交
这里模拟一下，首先新建一个`hello.txt`的文件，然后修改`readme.txt`内容，添加一句`come on baby`.
这样就有2个文件需要提交了。
我们使用`git status`命令来查看当前状态,是否有未提交的文件

```
git status
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-685665a710253672.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-685665a710253672.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如图，可以看到红色的字体显示的一个`readme.txt`被修改过了，但还没有准备提交的修改，另外一个是`Untracked files: hello.txt`，表示新增的文件。

这时候准备把上面2个文件都提交，使用命令：

```
git add readme.txt
git add hello.txt
```

提交后再使用`git status`命令查看下
[![img](http://upload-images.jianshu.io/upload_images/1637925-0d80be964d7135c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-0d80be964d7135c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
你会看到这2个文件都变成绿色了，表示添加到暂存区成功了
然后使用`git commit`提交后，在使用`git status`查看

```
git commit -m "second commit"
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-5accf85711b7b292.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-5accf85711b7b292.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如图，可以看到文件提交到仓库了，并且`git status`后提示`nothing to commit, working directory clean`，说明当前没有需要提交的修改，工作目录是干净。

## 比较当前文件跟版本文件内容

------

假如你已经记不清上次怎么修改的`readme.txt`，所以，需要用`git diff`这个命令看看：

```
git diff readme.txt
```

发现什么反应都没有。

原来是`readme.txt`已经`commit`提交到版本库了。内容一样当然没有啥好比对的。这里我们往`readme.txt`添加一条内容`day day up`。
[![img](http://upload-images.jianshu.io/upload_images/1637925-524a65016268ddad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-524a65016268ddad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如图，最后一行绿色的`+day day up.`表示是新增的。

## 查看历史提交记录

------

有时候你想看看之前提交的历史纪录~那么就需要使用到`git log`命令：

```
git log
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-c1d5042fa613864f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-c1d5042fa613864f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如图，可以看到我们提交的2次历史，`first commit`和`second commit`。历史纪录是根据时间倒叙排列的。
如果觉得代码太多了，加点过滤就行

```
git log --pretty=oneline
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-5bfe6947e56d4a2d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-5bfe6947e56d4a2d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其中，里面那一串常常字符串作用很重要，是用于版本回退的

# 理解git暂存区

暂存区，在`git`中是个很重要的概念，弄懂了暂存区才算真正懂了`git`

## 什么是暂存区

------

工作区： 我们正在编辑的区域

暂存区： 执行了add 操作后的区域

版本库 ：执行了commit之后的 (这里还要有区别，这个版本库，包含了本地版本库的和远程版本库的内容，push 后就是同步本地与远程的版本库)

------

下图展示了工作区、版本库中的暂存区和版本库之间的关系。
[![img](http://upload-images.jianshu.io/upload_images/1637925-7e0ed5643b7aa8d1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-7e0ed5643b7aa8d1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
图中可以看到 `Git`命令是如何影响工作区和暂存区`（stage, index）`的。

- 图中左侧为工作区，右侧为版本库。在**版本库中标记为 `index` 的区域是暂存区**`（stage, index）`，标记为 `master` 的是 `master` 分支所代表的目录树。
- 图中我们可以看出此时`HEAD`实际是指向`master`分支的一个“游标”。所以图示的命令中出现`HEAD`的地方可以用`master`来替换。
- 图中的`objects`标识的区域为`Git`的对象库，实际位于`.git/objects`目录下。
- 当对工作区修改（或新增）的文件执行`git add`命令时，暂存区index的目录树被更新，同时工作区修改（或新增）的文件内容被写入到对象库中的一个新的对象中，而该对象的`ID` 被记录在暂存区的文件索引中。
- 当执行提交操作`（git commit）`时，暂存区的目录树写到版本库（对象库）中，`master`分支会做相应的更新。即`master`指向的目录树就是提交时暂存区的目录树。
- 当执行 `git reset HEAD` 命令时，暂存区的目录树会被重写，被 master 分支指向的目录树所替换，但是工作区不受影响。
- 当执行 `git checkout .` 或者 `git checkout -- [file]` 命令时，会用暂存区全部或指定的文件替换工作区的文件。这个操作很危险，会清除工作区中未添加到暂存区的改动。
- 当执行 `git checkout HEAD .` 或者 `git checkout HEAD [file]` 命令时，会用 `HEAD` 指向的 `master` 分支中的全部或者部分文件替换暂存区和以及工作区中的文件。这个命令也是极具危险性的，因为不但会清除工作区中未提交的改动，也会清除暂存区中未提交的改动。
- 当执行 `git rm --cached [file]`命令时，会直接从暂存区删除文件，工作区则不做出改变。
- 当执行 `git rm file`命令时，会同时删除暂存区和工作区的文件。
- 当执行 `rm file`命令时，只会删除工作区的文件。

------

## 举例子来证明以上观点

------

假设：
工作区：a
暂存区（index）:b
HEAD:C

git diff命令结论

```git
git diff           比较a跟b
git diff --cached  比较b跟c
git diff HEAD      比较a跟c
```

git reset跟 git checkout结论

```
git reset HEAD              c覆盖b
git checkout -- <file>      b覆盖a
git checkout HEAD <file>    c覆盖a,b
```

git rm命令结论

```
git rm          删除a跟b
git rm --cached 只删除b
rm file         只删除a
```

## 证明git diff结论

------

例子，默认新建一个`readme.txt`，里面输入内容`one`然后add并且`commit`一次。

1：修改`readme.txt`，新增内容`two`，这时候a内容改变了,多了`two`，而b跟c内容不变，都只有`one`。
执行`git diff readme.txt`查看效果

[![img](http://upload-images.jianshu.io/upload_images/1637925-f2c5b58dd9f84984.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-f2c5b58dd9f84984.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结论：如图看出，内容有修改， a跟b比较了

------

执行`git diff --cached readme.txt`查看效果

[![img](http://upload-images.jianshu.io/upload_images/1637925-e80c37e983c6d767.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-e80c37e983c6d767.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结论：如图看出，没有变化，因为b跟c内容一样。

------

执行`git diff HEAD readme.txt`查看效果

[![img](http://upload-images.jianshu.io/upload_images/1637925-f7a205ed6544928d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-f7a205ed6544928d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如图所示：内容有修改，a跟c比较了

------

2.这时候执行`git add readme.txt`,这时候a，b内容都多了two，而c内容不变，只有`one`
执行`git diff readme.txt`查看效果

[![img](http://upload-images.jianshu.io/upload_images/1637925-c145bf0d470028f1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-c145bf0d470028f1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结论：如图看出，没有变化， 因为a跟b内容一样。

------

执行git diff –cached readme.txt查看效果

[![img](http://upload-images.jianshu.io/upload_images/1637925-868697247dc73377.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-868697247dc73377.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结论：如图看出，内容有修改，b跟c比较了

------

执行`git diff HEAD readme.txt`查看效果

[![img](http://upload-images.jianshu.io/upload_images/1637925-3ef4b4c68ac59fc3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-3ef4b4c68ac59fc3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结论：如图看出，内容有修改，a跟c比较了

3.最后使用`git commit`提交一次，这时候a,b,c内容都一样，都包含`two`。

[![img](http://upload-images.jianshu.io/upload_images/1637925-cb227be3056bb420.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-cb227be3056bb420.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结论，如图看出，没有变化，说明a,b,c内容一样

------

根据上面的实例再一次证明了如下观点：

```
git diff           比较工作区跟暂存区
git diff --cached  比较暂存区跟HEAD
git diff HEAD      比较工作区跟HEAD
```



## 证明git reset跟 git checkout结论

------

例子，默认新建一个`readme.txt`，里面输入内容`one`然后add并且`commit`一次，这时候a,b,c内容都是`one`

------

1.修改`readme.txt`，新增内容`two`，执行`git add readme.txt`操作，这时候a ,b内容都多了`two`,c还是只有`one`.
执行`git reset HEAD -- readme.txt`命令后，c覆盖b,这时候b内容也变成只有`one`了，使用`git diff readme.txt`命令可以看到，有内容修改，a跟b内容不一样。

[![img](http://upload-images.jianshu.io/upload_images/1637925-ef35a27e51d1aa79.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-ef35a27e51d1aa79.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

2.此时a内容有two,b和c都只有one，执行`git checkout -- readme.txt`后，b覆盖a,此时a,b,c都是one。执行`git diff readme.txt`命令可以看到，没有改变。

[![img](http://upload-images.jianshu.io/upload_images/1637925-81b2cf09cb0864cd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-81b2cf09cb0864cd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

3.此时a,b,c都只有`one`，修改一下，添加内容`two`，执行`git add readme.txt`和`git commit -m "two"`.再修改一次`readme.txt`,添加内容`three`,然后会执行`git add readme.txt`，此时a跟b都包含three，而c只包含one跟two。执行`git checkout HEAD readme.txt`后，c覆盖a和b,a,b里面内容都只有one跟two。分别使用命令`git diff --cached`和`git diff HEAD`来查看b跟c，a跟c的比对，发现都一样。

[![img](http://upload-images.jianshu.io/upload_images/1637925-ff3132a249f75a4d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-ff3132a249f75a4d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

根据上面的实例再一次证明了如下观点：

```
git reset HEAD              HEAD覆盖暂存区
git checkout -- <file>      暂存区覆盖工作区
git checkout HEAD <file>    HEAD覆盖暂存区和工作区
```



------

## 证明git rm 结论

------

默认新建一个`readme.txt`，里面输入内容`one`，然后使用`git add readme.txt`命令。
1.执行git rm readme.txt命令，发现文件被删除了。

2.再新建一个一个`readme.txt`，里面输入内容 one，然后使用`git add readme.txt`命令。执行`git rm --cached readme.txt`命令，发现文件内`readme.txt`还在，然后执行`git status`命令，发现是`Untracked`状态，也就是未`add`，这就说明暂存区被删除了。

[![img](http://upload-images.jianshu.io/upload_images/1637925-c90584783b25ac0e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-c90584783b25ac0e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

根据上面的实例再一次证明了如下观点：

```
git rm file      会将文件从缓存区和你的硬盘中（工作区）删除
git rm --cached  只删除暂存区，不删除工作区
rm file          只删除工作区
```

# 提交错了我想撤销或者回退版本

这几个功能正式体现版本控制工具的强大之处。(十分好用)

## 撤销操作

如果你文件**只是在工作区修改**了，但是还没提交到暂存区的时候,回滚就是从暂存区覆盖到工作区

```
git checkout -- [file]  b覆盖a
```

你可以用`git checkout -- [file]`来撤销。简单的说就是暂存区覆盖工作区。这里模拟一下，比如现在`readme.txt`里面内容是`first day`，并且已经提交到暂存区了，此时修改`readme.txt`，内容改成`second day.`，然后执行`git checkout -- readme.txt`命令,你会发现`readme.txt`内容又变成`first day`了
[![ img](http://upload-images.jianshu.io/upload_images/1637925-9719d401f79eaaaf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-9719d401f79eaaaf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



如果你文件**在工作区修改了  ,并且也执行`git add`命令提交给暂存区**了，那么执行上面的`git checkout -- [file]`已经无效了，因为两者内容一致。 这时候就需要版本库的内存覆盖缓存区

```
git reset HEAD  c覆盖b
```

这时候就应该使用`git reset HEAD`命令来撤销，简单的说就是让`HEAD`覆盖暂存区，因为此时的`HEAD`这边的文件内容还是上次提交时的内容。现在模拟一下，现在有`readme.txt`跟`hello.txt`两个文件，都经过修改
[![img](http://upload-images.jianshu.io/upload_images/1637925-9b660579cc6e278e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-9b660579cc6e278e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如图可以看到，使用`git status -s`来查看的时候，红色的M表示这2个文件都经过修改，使用`git add .`提交后在查看，发现都是绿色的M，表示都提交到暂存区了，这时候执行`git reset HEAD hello.txt`后再查看，发现`hello.txt`变成红色M了，说明hello.txt从暂存区撤销了。



```
git checkout HEAD [file]
```

`git checkout HEAD [file]`命令是`git checkout -- [file]`和`git reset HEAD`的合成体，直接用HEAD覆盖工作区,暂存区。如下图中所示，一开始 工作区暂存区以及HEAD中文件内容都是`first day.`,此时修改`readme.txt`内容为`second day.`，然后执行`git add .`提交到暂存区，接着执行`git checkout HEAD readme.txt`命令，再查看`readme.txt`内容的时候你会发现变成了`first day.`
[![img](http://upload-images.jianshu.io/upload_images/1637925-df44d8ab88e2d689.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-df44d8ab88e2d689.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



## 版本回退以及切换

```
git reset --hard HEAD^  （这个命令用于C覆盖b比较好用） 这个命令使用来移动 HEAD 指针的
```

首先，`Git`必须知道当前版本是哪个版本，在`Git`中，用`HEAD`表示当前版本，也就是最新的提交`3628164...882e1e0`（注意我的提交ID和你的肯定不一样），上一个版本就是`HEAD^`，上上一个版本就是`HEAD^^`，当然往上100个版本写100个`^`比较容易数不过来，所以写成`HEAD~100`。
先查看当前版本记录  `git log --oneline`，发现最近的两个版本为`b520a36 第一次提交`和`479c6fd 第二次提交`
[![img](http://upload-images.jianshu.io/upload_images/1637925-c6024a4ed9be32d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-c6024a4ed9be32d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

当前版本是`b520a36 第一次提交`,现在我们来执行`git reset --hard HEAD^`来回退到`479c6fd 第二次提交`版本,如图：
[![img](http://upload-images.jianshu.io/upload_images/1637925-a41980f19a5678ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-a41980f19a5678ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
git reset --hard commit_id
```

如果你回退版本后又后悔了，想恢复最后那个版本怎么办，通过`git reset --hard commit_id`命令可以搞定，注意这里的`commit_id`是版本号，只要记得版本号，你想切换到哪个版本都行，如果你忘记了刚才最后一个的版本号，可以通过`git reflog`来查看，这里我们记得最后那次版本号为`b520a36`，执行`git reset --hard b520a36`
[![img](http://upload-images.jianshu.io/upload_images/1637925-ecfd58b1f7f210ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-ecfd58b1f7f210ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 删除操作

------

这里介绍一下`git`中的删除操作命令，以及意外删除了该如何还原。

```
git rm
```

执行`git rm`命令会同时删除工作区跟暂存区中的指定文件，要慎重处理。

但是如果你意外删除了也是可以恢复的。不过要分成2种情况处理：

1. 还未执行`git commit`提交到`HEAD`的时候删除文件，这时候直接使用`git checkout HEAD [file]`就能还原。
   [![img](http://upload-images.jianshu.io/upload_images/1637925-04948eecdf6b242a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-04948eecdf6b242a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2. 执行`git commit`提交到`HAED`后时候才删除文件，这时候就只能执行`git reset --hard HEAD^`回退上一个版本。
   [![img](http://upload-images.jianshu.io/upload_images/1637925-38954d1eb68b8290.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-38954d1eb68b8290.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

### 删除错误提交的commit

​    有时，不仅添加到了暂存区，而且commit到了版本库，这个时候就不能使用git rm了，需要使用git reset命令。

​    **错误提交到了版本库，此时无论工作区、暂存区，还是版本库，这三者的内容都是一样的**，所以在这种情况下，只是删除了工作区和暂存区的文件，下一次用该版本库回滚那个误添加的文件还会重新生成。

​    这个时候，我们必须撤销版本库的修改才能解决问题！

​    git reset有三个选项，--hard、--mixed、--soft。

```
//仅仅只是撤销已提交的版本库，不会修改暂存区和工作区
git reset --soft 版本库ID
```

 

```
//仅仅只是撤销已提交的版本库和暂存区，不会修改工作区
git reset --mixed 版本库ID
```

 

```
//彻底将工作区、暂存区和版本库记录恢复到指定的版本库
git reset --hard 版本库ID
```

​    那我们到底应该用哪个选项好呢？

​    （1）如果你是在提交了后，对工作区的代码做了修改，并且想保留这些修改，那么可以使用git reset --mixed 版本库ID，注意这个版本库ID应该不是你刚刚提交的版本库ID，而是**刚刚提交版本库的上一个版本库**。如下图：

​    （2）如果不想保留这些修改，可以直接使用彻底的恢复命令，git reset --hard 版本库ID。

​    （3）为什么不使用--soft呢，因为它只是恢复了版本库，**暂存区仍然存在你错误提交的文件索引**，还需要进一步使用上一节的删除错误添加到暂存区的文件，详细见上文。

![img](https://images2015.cnblogs.com/blog/695731/201510/695731-20151017164205272-1808101337.png)

------



## 结束

------

总结一下上面所学的：

场景1：当你改乱了工作区某个文件的内容，想直接丢弃工作区的修改时，用命令`git checkout -- file`。
场景2：当你不但改乱了工作区某个文件的内容，还添加到了暂存区时，想丢弃修改，分两步，第一步用命令`git reset HEAD file`，就回到了场景1，第二步按场景1操作。
场景3：已经提交了不合适的修改到版本库时，想要撤销本次提交，使用`git reset --hard HEAD^`。

# 连接远程仓库（github，coding）

Git尝试连接远程仓库了，目前国内外比较出名的提供`Git`仓库远程托管的有国外的`github`，国内的`开源中国`以及`coding`。这里以`coding`为例子介绍如何操作远程仓库。

## 正文

------

假如你现在本地有一个`gitLearn`的项目，想托管到`coding`上面，那么你先进入`coding`创建一个仓库，如图。

[![img](http://upload-images.jianshu.io/upload_images/1637925-001ac894906d8774.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-001ac894906d8774.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
[![img](http://upload-images.jianshu.io/upload_images/1637925-6ff4670ad502150b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-6ff4670ad502150b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

创建完后复制远程仓库的SSH方式的地址。

### 连接远仓库命令

------

现进入本地`gitLearn`的目录，执行

```
git init
```

初始化你的项目，然后提交你的代码到本地库，执行：

```
git add readme.txt
git commit -m "first submit"
```

此时你的代码已经在本地库了，执行命令

```
git remote add origin git@git.coding.net:tengj/gitLearn.git
```

就连上了远程的`gitLearn`项目。远程库的名字就是`origin`，这是Git默认的叫法，也可以改成别的，但是`origin`这个名字一看就知道是远程库。

### 列出远端别名

------

我们可以使用`git remote`来列出远端别名，如果没有任何参数，`Git`会列出它存储的远端仓库别名了事。默认情况下，如果你的项目是克隆的（与本地创建一个新的相反）， `Git`会自动将你的项目克隆自的仓库添加到列表中，并取名“`origin`”。 如果你执行时加上 `-v` 参数，你还可以看到每个别名的实际链接地址。

[![img](http://upload-images.jianshu.io/upload_images/1637925-a629d477d591a4a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-a629d477d591a4a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 推送内容到远程库

------

连上之后就可以推送代码到远程仓库了，执行

```
git push -u origin master
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-0d7ad0d8544da200.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-0d7ad0d8544da200.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

由于**远程库是空的，我们第一次推送`master`分支时，加上了`-u`参数**，`Git`不但会把本地的`master`分支内容推送的远程新的`master`分支，还会把本地的`master`分支和远程的`master`分支关联起来，在以后的推送或者拉取时就可以简化命令，直接用`git push origin master`。 （还有一种就是直接clone 远程仓库，做操作）
推送完后，可以查看远程仓库上多了`readme.txt`文件。

[![img](http://upload-images.jianshu.io/upload_images/1637925-fa678152e9802a60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-fa678152e9802a60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果你使用上面命令出现如下错误提示：

[![img](http://upload-images.jianshu.io/upload_images/1637925-97c77a6276683ce9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-97c77a6276683ce9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

那是因为你`HEAD`区域还是空的，记得先用`git add`和`git commit`命令提交你想提交的文件到`HEAD`。然后在用`git push -u origin master`就可以了。

### 从远程仓库下载项目到本地

------

用`git clone`
比如我想在我电脑`E:\gitRepository`目录下面下载这个远程仓库项目
直接使用命令：

```
git clone git@git.coding.net:tengj/gitLearn.git
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-aa84d207701f2512.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-aa84d207701f2512.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

就会下载项目到`E:\gitRepository`下面。

### 从远端仓库提取数据并尝试合并到当前分支

------

如果你跟别人一起连着一个远程仓库项目，别人提交了代码，你想更新成最新的代码怎么办。
使用`git pull`命令即可

```
git pull origin master
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-e4c6f8c207329c4c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-e4c6f8c207329c4c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 删除跟远程仓库的连接

------

如果你需要删除一个远端 —— 不再需要它了、项目已经没了，等等 —— 你可以使用 `git remote rm [alias]` 把它删掉。

```
git remote rm origin
// 这里只是和远程仓库断开连接，并没有在远程将库删除。
```

注意，断开跟远程的连接后，如果还想连上，则需要再来一次

```
git remote add origin git@git.coding.net:tengj/gitLearn.git
```

## 总结

------

总结如下：

```
git remote               查看远程库的信息  
git remote -v            显示更详细的信息  
git remote add origin git@git.coding.net:tengj/gitLearn.git  连接远程仓库  
git clone git@git.coding.net:tengj/gitLearn.git            复制一份远程仓库项目到本地  
git remote rm origin     关闭连接远程仓库  
git push origin master   推送分支  
git pull origin master   抓取更新
```

------

# 多人协同工作之分支管理

### 分支简介

------

分支就像是电影里面的平行宇宙。

当你正在电脑前努力学习`Git`的时候，另一个你正在另一个平行宇宙里努力学习`SVN`。如果两个平行宇宙互不干扰，那对现在的你也没啥影响。不过，在某个时间点，两个平行宇宙合并了，结果，你既学会了`Git`又学会了`SVN`！
[![img](http://upload-images.jianshu.io/upload_images/1637925-c94e858c56ce2210.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-c94e858c56ce2210.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

为了真正理解 `Git` 处理分支的方式，需要清楚`Git`是如何保存数据的。

```
Git 保存的不是文件的变化或者差异，而是一系列不同时刻的文件快照。
```

在进行提交操作时，`Git`会保存一个提交对象（`commit object`）。根据`Git`保存数据的方式，我们可以很自然的想到——该提交对象会包含一个指向暂存内容快照的指针。 但不仅仅是这样，该提交对象还包含了作者的姓名和邮箱、提交时输入的信息以及指向它的父对象的指针。首次提交产生的提交对象没有父对象，普通提交操作产生的提交对象有一个父对象，而由多个分支合并产生的提交对象有多个父对象。

`Git`的分支，其实本质上仅仅是指向提交对象的可变指针。 `Git`的默认分支名字是 `master`。 在多次提交操作之后，你其实已经有一个指向最后那个提交对象的 `master` 分支。 它会在每次的提交操作中自动向前移动。

> `Git` 的 “master” 分支并不是一个特殊分支。它就跟其它分支完全没有区别。 之所以几乎每一个仓库> 都有 master 分支，是因为 `git init` 命令默认创建它，并且大多数人都懒得去改动它。

分支在实际中有什么用呢？假设你准备开发一个新功能，但是需要两周才能完成，第一周你写了50%的代码，如果立刻提交，由于代码还没写完，不完整的代码库会导致别人不能干活了。如果等代码全部写完再一次提交，又存在丢失每天进度的巨大风险。
现在有了分支，就不用怕了。你创建了一个属于你自己的分支，别人看不到，还继续在原来的分支上正常工作，而你在自己的分支上干活，想提交就提交，直到开发完毕后，再一次性合并到原来的分支上，这样，既安全，又不影响别人工作。
其他版本控制系统如SVN等都有分支管理，但是用过之后你会发现，这些版本控制系统创建和切换分支比蜗牛还慢，简直让人无法忍受，结果分支功能成了摆设，大家都不去用。
但`Git`的分支是与众不同的，无论创建、切换和删除分支，`Git`在1秒钟之内就能完成！无论你的版本库是1个文件还是1万个文件。

### 分支创建

------

`Git`是怎么创建新分支的呢？ 很简单，它只是为你创建了一个可以移动的新的**指针**。 比如，创建一个 `testing`分支， 你需要使用 `git branch` 命令：

```
$ git branch testing
```

这会在当前所在的提交对象上创建一个指针。

[![两个指向相同提交历史的分支。](http://upload-images.jianshu.io/upload_images/1637925-810ca5d1d4a7053b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-810ca5d1d4a7053b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

那么，`Git`又是怎么知道当前在哪一个分支上呢？ 也很简单，它有一个名为 `HEAD` 的特殊指针。在 `Git`中，它是一个指针，指向当前所在的本地分支（译注：将 `HEAD` 想象为当前分支的别名）。 在本例中，你仍然在`master` 分支上。 因为 `git branch` 命令仅仅 *创建* 一个新分支，并不会自动切换到新分支中去。

[![HEAD 指向当前所在的分支.](http://upload-images.jianshu.io/upload_images/1637925-cd090e0cf304f589.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-cd090e0cf304f589.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

你可以简单地使用 `git log` 命令查看各个分支当前所指的对象。 提供这一功能的参数是 `--decorate`。

```
$ git log --oneline --decorate
f30ab (HEAD, master, testing) add feature #32 - ability to add new
34ac2 fixed bug #1328 - stack overflow under certain conditions
98ca9 initial commit of my project
```

正如你所见，当前 “master” 和 “testing” 分支均指向校验和以 `f30ab` 开头的提交对象。

### 分支切换

------

要切换到一个已存在的分支，你需要使用`git checkout`命令。 我们现在切换到新创建的 `testing` 分支去：

```
$ git checkout testing
```

这样 `HEAD` 就指向 `testing` 分支了。
[![HEAD 指向当前所在的分支.](http://upload-images.jianshu.io/upload_images/1637925-193cae7fdc059f35.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-193cae7fdc059f35.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

上面的创建分支和切换分支命令可以合起来用下面这个命令来替代。

```
$ git checkout -b testing
```

那么，这样的实现方式会给我们带来什么好处呢？ 现在不妨再提交一次：

```
$ vim test.rb
$ git commit -a -m 'made a change'
```

[![HEAD 分支随着提交操作自动向前移动.](http://upload-images.jianshu.io/upload_images/1637925-97fedcc803fd5c85.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-97fedcc803fd5c85.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如图所示，你的 `testing` 分支向前移动了，但是 `master` 分支却没有，它仍然指向运行 `git checkout`时所指的对象。 这就有意思了，现在我们切换回 `master` 分支看看：

```
$ git checkout master
```

[![检出时 HEAD 随之移动.](http://upload-images.jianshu.io/upload_images/1637925-ab5eed0ea0e7ed7c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-ab5eed0ea0e7ed7c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这条命令做了两件事。 一是使 HEAD 指回 `master` 分支，二是将工作目录恢复成 `master` 分支所指向的快照内容。 也就是说，你现在做修改的话，项目将始于一个较旧的版本。 本质上来讲，这就是忽略`testing`分支所做的修改，以便于向另一个方向进行开发。
可以使用 `git branch`命令查看当前分支，注意前面带`*`的表示当前分支
[![img](http://upload-images.jianshu.io/upload_images/1637925-817d9661d25c84fb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-817d9661d25c84fb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

> **Note**
> **分支切换会改变你工作目录中的文件**
> 在切换分支时，一定要注意你工作目录里的文件会被改变。 如果是切换到一个较旧的分支，你的工作目> 录会恢复到该分支最后一次提交时的样子。 如果`Git`不能干净利落地完成这个任务，它将禁止切换分支。

### 合并分支（快速合并）

------

假如我们在`testing`上的工作完成了，就可以把`testing`合并到`master`上。`Git`怎么合并呢？最简单的方法，就是直接把`master`指向`testing`的当前提交，就完成了合并，这里你需要使用`git merge`命令

```
$ git merge testing
Updating 64ba18a..760118b
Fast-forward
 hello.txt | 1 +
 1 file changed, 1 insertion(+)
 create mode 100644 hello.txt
```

`git merge`命令用于合并指定分支到当前分支。合并后，再查看内容，就可以看到，和`testing`分支的最新提交是完全一样的。
注意到上面的`Fast-forward`信息，`Git`告诉我们，这次合并是“快进模式”，也就是直接把`master`指向`testing`的当前提交，所以合并速度非常快。当然，也不是每次合并都能`Fast-forward`，我们后面会讲其他方式的合并。
[![img](http://upload-images.jianshu.io/upload_images/1637925-f90f9d9c8d628f31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-f90f9d9c8d628f31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 删除分支

------

合并完分支后，甚至可以删除`dev`分支。删除`dev`分支就是把`dev`指针给删掉，删掉后，我们就剩下了一条`master`分支,这里需要使用`git branch -d`命令来删除分支

```
$ git branch -d testing
Deleted branch testing (was 760118b).
```

[![img](http://upload-images.jianshu.io/upload_images/1637925-3e73d2a880a707b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-3e73d2a880a707b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 分支合并冲突

------

人生不如意之事十之八九，合并分支往往也不是一帆风顺的。
准备新的`dev`分支，继续我们的新分支开发：

```
$ git checkout -b dev
Switched to a new branch 'dev'
```

修改`README.md`内容，添加一样内容”day day up~”，在`dev`分支上提交：

```
$ git commit -am "one commit"
[dev 6a6a08e] one commit
 1 file changed, 1 insertion(+)
```

切换到`master`分支：

```
$ git checkout master
Switched to branch 'master'
Your branch is up-to-date with 'origin/master'.
```

`Git`还会自动提示我们当前`master`分支比远程的`master`分支要超前1个提交。
在`master`分支上把`README.md`文件的最后改为 `good good study`,然后提价

```
$ git commit -am "two commit"
[master 75d6f25] two commit
 1 file changed, 1 insertion(+)
```

现在，`master`分支和`dev`分支各自都分别有新的提交，变成了这样：
[![img](http://upload-images.jianshu.io/upload_images/1637925-dbf7ee2243ed5ee7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-dbf7ee2243ed5ee7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这种情况下，Git无法执行“快速合并”，只能试图把各自的修改合并起来，但这种合并就可能会有冲突，我们试试看：

```
$ git merge dev
Auto-merging README.md
CONFLICT (content): Merge conflict in README.md
Automatic merge failed; fix conflicts and then commit the result.
```

果然冲突了！Git告诉我们， README.md文件存在冲突，必须手动解决冲突后再提交。`git status`也可以告诉我们冲突的文件：

```
$ git status
On branch master
Your branch is ahead of 'origin/master' by 1 commit.
  (use "git push" to publish your local commits)
You have unmerged paths.
  (fix conflicts and run "git commit")

Unmerged paths:
  (use "git add <file>..." to mark resolution)

        both modified:   README.md

no changes added to commit (use "git add" and/or "git commit -a")
​```  
我们可以直接查看`README.md`的内容：  
​``` bash
$ cat README.md
#gitLearn
<<<<<<< HEAD
good good study
=======
day day up
>>>>>>> dev
```



Git用`<<<<<<<`，`=======`，`>>>>>>>`标记出不同分支的内容，我们修改如下后保存：

```
#gitLearn
good good study
day day up
```

再提交：

```
$ git commit -am 'merge commit'
[master 9a4d00b] merge commit
```

现在，`master`分支和`dev`分支变成了下图所示：

[![img](http://upload-images.jianshu.io/upload_images/1637925-02ceb2dc99ad3291.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-02ceb2dc99ad3291.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

用带参数的`git log`也可以看到分支的合并情况：

```
$ git log --graph --pretty=oneline --abbrev-commit
*   9a4d00b merge commit
|\
| * 6a6a08e one commit
* | 75d6f25 two commit
|/
* ae06dcf 123
* 760118b test
*   64ba18a test
|\
| *   4392848 Accept Merge Request #1 test : (dev -> master)
| |\
| | * a430c4b update README.md
| |/
| * 88ec6d7 Initial commit
* 32d11c8 update README.md
* 8d5acc1 new file README
* e02f115 Initial commit

​```  
最后，删除`feature1`分支：  
​``` bash
$ git branch -d dev
Deleted branch dev (was 6a6a08e).
```



### 合并分支（普通合并）

------

通常，合并分支时，如果可能，Git会用`Fast forward`模式，但这种模式下，删除分支后，会丢掉分支信息。
如果要强制禁用`Fast forward`模式，`Git`就会在`merge`时生成一个新的`commit`，这样，从分支历史上就可以看出分支信息。
下面我们实战一下`--no-ff`方式的`git merge`：
首先，仍然创建并切换`dev`分支：

```
$ git checkout -b dev
Switched to a new branch 'dev'
```

修改README.md文件，并提交一个新的commit：

```
$ git commit -am 'submit'
[dev fee6025] submit
 1 file changed, 1 insertion(+)
```

现在，我们切换回`master`：

```
$ git checkout master
Switched to branch 'master'
```

目前来说流程图是这样：

[![img](http://upload-images.jianshu.io/upload_images/1637925-0d55d26667bbd33a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-0d55d26667bbd33a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

准备合并`dev`分支，请注意`--no-ff`参数，表示禁用`Fast forward`：

```
$ git merge --no-ff -m "merge with no-ff" dev
Merge made by the 'recursive' strategy.
 README.md | 1 +
 1 file changed, 1 insertion(+)
```

因为本次合并要创建一个新的commit，所以加上`-m`参数，把commit描述写进去。

合并后，我们用`git log`看看分支历史：

```
$ git log --graph --pretty=oneline --abbrev-commit
*   b98f802 merge with no-ff
|\
| * fee6025 submit
|/
*   9a4d00b merge commit
...
```



可以看到，不使用`Fast forward`模式，merge后就像这样：
[![img](http://upload-images.jianshu.io/upload_images/1637925-51a07e8355c4bd03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/1637925-51a07e8355c4bd03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 分支管理策略

------

开发的时候一般3个分支就可以了：

1. mster 主分支用来发布
2. dev 日常开发用的分支
3. bug 修改bug用的分支

首先，`master`分支应该是非常稳定的，也就是仅用来发布新版本，平时不能在上面干活；
干活都在`dev`分支上，也就是说，`dev`分支是不稳定的，到某个时候，比如1.0版本发布时，再把`dev`分支合并到`master`上，在`master`分支发布1.0版本,你和你的小伙伴们每个人都在`dev`分支上干活，每个人都有自己的分支，时不时地往`dev`分支上合并就可以了；
`bug`分支用来处理日常bug,搞定后合到dev分支即可；

假设远程公共仓库，有一个`master`和一个`dev`分支，进行多人协作开发时候（每个人的公钥必须加入到远程账号下，否则无法`push`）， 每个人都应该`clone`一份到本地。 但是`clone`的只是`master`，如果远程的`master`和`dev`一样，没关系；如果不一致，则需要`clone`出`dev`分支 `git checkout -b dev origin/dev` 之后每个人在本地的`dev`分支上独自开发（最好不要在`mast`上开发）， 开发完成之后`push`到远程`dev`, `git push origin dev`。 之后审核人再确定是否合并`dev`到`master`。

### 团队多人开发协作

------

当你从远程仓库克隆时，实际上Git自动把本地的`master`分支和远程的`master`分支对应起来了，并且，远程仓库的默认名称是`origin`。
要查看远程库的信息，用`git remote`：

```
$ git remote
origin
```

或者，用`git remote -v`显示更详细的信息：

```
$ git remote -v
origin  git@git.coding.net:tengj/gitLearn.git (fetch)
origin  git@git.coding.net:tengj/gitLearn.git (push)
```

上面显示了可以抓取和推送的`origin`的地址。如果没有推送权限，就看不到push的地址。

#### 推送分支

------

推送分支，就是把该分支上的所有本地提交推送到远程库。推送时，要指定本地分支，这样，`Git`就会把该分支推送到远程库对应的远程分支上：

```
$ git push origin master
```

果要推送其他分支，比如`dev`，就改成：

```
$ git push origin dev
```

#### 抓取分支

------

多人协作时，大家都会往`master`和`dev`分支上推送各自的修改。
现在，模拟一个你的小伙伴，可以在另一台电脑（注意要把`SSH Key`添加到`GitHub`）或者同一台电脑的另一个目录下克隆：

```
$ git clone git@git.coding.net:tengj/gitStudy.git
Cloning into 'gitStudy'...
remote: Counting objects: 3, done.
remote: Total 3 (delta 0), reused 0 (delta 0)
Receiving objects: 100% (3/3), done.
Checking connectivity... done.
​```  
当你的小伙伴从远程库clone时，默认情况下，你的小伙伴只能看到本地的`master`分支。不信可以用`git branch`命令看看：
​``` bash
$ git branch
* master 
```

现在，你的小伙伴要在`dev`分支上开发，就必须创建远程`origin`的`dev`分支到本地，于是他用这个命令创建本地`dev`分支（程分支dev要先创建）。

```
$ git checkout -b dev
git
```

创建dev分之后，先同步远程服务器上的数据到本地

```
$ git fetch origin
From git.coding.net:tengj/gitStudy
 * [new branch]      dev        -> origin/dev
```

现在，他就可以在`dev`上继续修改，然后，时不时地把`dev`分支`push`到远程：

```
$ git commit -am 'test'
[dev c120ad6] test
 1 file changed, 1 insertion(+)
$ git push origin dev
Counting objects: 3, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (2/2), done.
Writing objects: 100% (3/3), 262 bytes | 0 bytes/s, done.
Total 3 (delta 0), reused 0 (delta 0)
To git@git.coding.net:tengj/gitStudy.git
   65c05aa..c120ad6  dev -> dev
```

你的小伙伴已经向`origin/dev`分支推送了他的提交，而碰巧你也对同样的文件作了修改，并试图推送：

```
$ git push origin dev
To git@git.coding.net:tengj/gitStudy.git
 ! [rejected]        dev -> dev (fetch first)
error: failed to push some refs to 'git@git.coding.net:tengj/gitStudy.git'
hint: Updates were rejected because the remote contains work that you do
hint: not have locally. This is usually caused by another repository pushing
hint: to the same ref. You may want to first integrate the remote changes
hint: (e.g., 'git pull ...') before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
```

推送失败，因为你的小伙伴的最新提交和你试图推送的提交有冲突，解决办法也很简单，Git已经提示我们，**先用`git pull`把最新的提交从`origin/dev`抓下来，然后，在本地合并，解决冲突，再推送**：

```
$ git pull origin dev
remote: Counting objects: 3, done.
remote: Compressing objects: 100% (2/2), done.
remote: Total 3 (delta 0), reused 0 (delta 0)
Unpacking objects: 100% (3/3), done.
From git.coding.net:tengj/gitStudy
 * branch            dev        -> FETCH_HEAD
   b7b87f4..f636337  dev        -> origin/dev
Auto-merging a.txt
CONFLICT (content): Merge conflict in a.txt
Automatic merge failed; fix conflicts and then commit the result.
```

因此，多人协作的工作模式通常是这样：

1. 先pull
2. 再提交
3. 解决冲突
4. push

## 总结

------

与分支相关的命令，大体如下：

```
git branch           查看当前分支
git branch -v        查看每一个分支的最后一次提交
git branch -a        查看本地和远程分支的情况
git branch --merged  查看已经与当前分支合并的分支
git branch --no-merged 查看已经与当前分支未合并的分支
git branch -r        查看远程分支
git branch dev       创建分支 dev
git checkout dev     切换到分支dev
git checkout -b dev  创建并切换分支dev
git merge dev        名称为dev的分支与当前分支合并
git branch -d dev    删除分支dev
```

------

# git  提交大文件

gitHub有大文件限制，超过100M拒绝提交。

官方提示使用[Git LFS](https://git-lfs.github.com/)。(但是我在使用的过程中出现问题，使用无法push 到远程，可能是公司网络权限的问题。)

```
突破github限制，支持单个文件超出100M (使用 Git LFS)  Git LFS 全名为：Git Large File Storage
Git LFS的官方网址在这里： https://git-lfs.github.com/
现在来简单说下使用方式：先安装 Git LFS 的客户端，然后在将要push的仓库里重新打开一个bash命令行： 
只需设置1次 LFS : git lfs install
然后 跟踪一下你要push的大文件的文件或指定文件类型 git lfs track "*.pdf" ， 当然还可以直接编辑.gitattributes文件
以上已经设置完毕， 其余的工作就是按照正常的 add , commit , push 流程就可以了 : 
git add yourLargeFile.pdf
git commit -m "Add Large file"
git push -u origin master
备注： 我在push那本c++书的时候一些小细节：
=============================================
① 仓库从github上克隆时用的时HTTPS的方式，不是SSH(也可尝试使用这个，看下是否会成功)。 
② 在最终push的时候会打印出如下信息： 
Git LFS: (0 of 0 files, 1 skipped) 0 B / 0 B, 147.30 MB skipped 
乍一看，表面上是跳过了该文件，但是回到github上查看，真的push上去了 
③ 目前 Git LFS的总存储量为1G左右，超过需要付费。 
====================================================
使用中发现，必须先将.gitattributes文件进行更新、提交（Commit）和推送（Push），然后再对大文件进行Add，Commit，Push，即要分两次Push才能成功上传大文件。

如果将.gitattributes的更新和大文件的Add，Commit，Push合并为一次Commit和Push，则Push依然会失败，提示不能上传大文件！
```

在公司尝试失败，回家在自己电脑上再次尝试。

实验材料    <https://u14797164.ctfile.com/fs/14797164-237162196>

# git 只想clone git仓库中的某一个文件而不是将整个仓库都克隆下来

## 一： 使用工具网站

[DownGit](http://downgit.zhoudaxiaa.com/)： 只需要找到仓库中对应文件夹的url,输入之后,点击 download 自动打包下载 (实验测试过可用且好用，但是这始终是别人的做的工具没有保障)

## 二 ： 仿照工具使用postman 或者下载工具发送请求

需要下载地址： https://github.com/zhaodahan/zhao_Note/blob/master/%E5%BC%80%E5%8F%91.txt
参考地址：https://raw.githubusercontent.com/zhaodahan/English-level-up-tips-for-Chinese/master/word-list/Java.md
生成下载地址：https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/%E5%BC%80%E5%8F%91.txt

(但是这样做存在问题，浏览器会默认的查看显示这个文件，而不是打包下载，还有一个致命的问题就是文件内容的中文编码，即使设置了请求头编码仍然存在显示问题)

## 三： 克隆自己的项目

注意：本方法会下载整个项目，但是，最后出现在本地项目文件下里只有需要的那个文件夹存在。类似先下载，再过滤。有时候因为需要我们只想git clone 下仓库的单个或多个文件夹，而不是全部的仓库内容，这样就很省事。

在Git1.7.0以前，这无法实现，git认为如果这样做的话，仓库的数据一致性无法保证，但是幸运的是在Git1.7.0以后加入了Sparse Checkout模式，这使得Check Out指定文件或者文件夹成为可能。

举个例子：

现在有一个test仓库https://github.com/mygithub/test
你要gitclone里面的tt子目录：
在本地的硬盘位置打开Git Bash

```
git init test && cd test     //新建仓库并进入文件夹
git config core.sparsecheckout true //设置允许克隆子目录

echo 'tt*' >> .git/info/sparse-checkout //设置要克隆的仓库的子目录路径   //空格别漏

git remote add origin git@github.com:mygithub/test.git  //这里换成你要克隆的项目和库

git pull origin master    //下载
```



## 四 ：解决的方法是用SVN

这个方法不用下载整个仓库，但是需要暗转SVN

下载TortoiseSVN。安装的时候记得把命令行勾上，默认不装。 
假设我需要获取https://github.com/dynamsoft-dbr/cmake下的images目录。只需要用命令

`svn export https://github.com/dynamsoft-dbr/cmake.git/trunk/images`

就可以了。把trunk添加到仓库的链接后，然后加上子目录的路径。

# gitHub上某个仓库不想要了

------

![github_delete.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/github_delete.png?raw=true)

在做这步之前需要复制一下仓库名称，在删除确认的时候需要我们提供仓库名称(根据提示删除操作是无法撤销的，删除之前多思量)。

![github_delete2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/github_delete2.png?raw=true)

------

# git 分支对比

```
有两个分支 dev 和主分支
# 查看 dev 有，而 master 中没有的：
$ git log dev ^master
# 查看 dev 中比 master 中多提交了哪些内容：
$ git log master..dev
# 不知道谁提交的多谁提交的少，单纯想知道有什么不一样：
git log dev...master
# 在上述情况下，再显示出每个提交是在哪个分支上
git log --left-right dev...master
```

   

# git 提交忽略某些文件

在日常使用中，并非项目文件夹下的所有文件及文件夹变更都需要纳入版本控制。

### 1、创建 .gitignore文件

在git管理的项目文件夹中（严格的讲，应该叫做git的本地repository），创建一个文件名为“.gitignore”的纯文本文件![img](https://images2015.cnblogs.com/blog/114186/201706/114186-20170623062535695-2077512996.png)

上图中的.gitignore文件就是我用notepad++刚刚创建出来用于告诉git忽略哪些文件变更的配置文件。

Git在扫描文件变更时，会先查看.gitignore的配置，根据.gitignore中的配置再决定哪些文件需要被标注上untracked。当然，那些被ignore的文件肯定不会被标上untracked，因为那些文件对于git而言是透明的。

### 2、编辑.gitignore文件

编辑.gitignore文件，将target、nbactions.xml、nb-configuration.xml三个条目添加到.gitignore中，如**列表1**所示。

```
##############################
## Folders                  ##
##############################
target
 
##############################
## Netbeans conf            ##
##############################
nb-configuration.xml
nbactions.xml
```

上述我们不希望提交的文件变更全部消失了，git不会再追踪这些文件的变更情况，也不会将它们提交到服务器端。

### 3、git 忽略失败

在项目中有些配置文件不需要提交，但是有同学在后面开发中发现在.igonore文件中无论如何都无法忽略某些文件的提交。原因在这里：

 已经维护起来的文件，即使加上了gitignore，也无济于事。----

那么如何解决呢？方式如下

`git update-index --assume-unchanged   要忽略的文件夹/文件夹下文件名`

比如我要忽略项目下.idea文件夹下所有xml文件,idea下都是xml文件（我用的webstorm）：

`git update-index --assume-unchanged   .idea/*.xml`

如果要重新恢复提交，使用如下命令：

`git update-index --assume-unchanged   .idea/*.xml`

这样每次提交就不会提交idea下的文件了。

# git 看某次commit改了哪些文件

```
git log 查看commit的历史
git show <commit-hash-id>查看某次commit的修改内容
git log -p <filename>查看某个文件的修改历史
git log -p -2查看最近2次的更新内容
```

