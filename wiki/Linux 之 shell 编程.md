Shell编程

# shell 简介

------

Shell是用户与Linux或Unix内核通信的工具。

shell编程指的并不是编写这个工具,而是指利用现有的shell工具进行编程,写出来的程序是轻量级的脚本,我们叫做shell脚本。

Shell的语法是从C语言继承过来的,因此我们在写shell脚本的时候往往能看到c语言的影子。
Shell脚本实在是太灵活了,相比标准的Java、C、C++ 等,它不过是一些现有命令的堆叠,这是他的优势也是他的劣势,太灵活导致不容易书写规范。

参考的shell脚本： https://github.com/zhaodahan/BaseShell



## 什么是shell

shell **命令解析器**，是包裹在linux内核外面的一层壳，用于用户与linux内核通信的。*它为用户提供了一个向 Linux 内核发送请求以便运行程序的界面系统级程*序，用户可以用 shell 来启动、挂起、停止甚至是编写一些程序。

机器底层能识别的是0,1 二进制数据，我们操作机器是通过编写命令，但是系统底层不识别，所以我们需要shell来讲命令解析成底层能够识别的0,1 二进制。

![Linux_shell.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell.png?raw=true)

shell 就是我们与计算机内核的一个交互工具，让我们有地方可以输命令(就是Linux的终端， widows的cmd 等)

不仅如此，shell还有一个强大的功能就是：

shell 是一个功能很强大的编程语言，它易编写、易调试，而且灵活性强。shell 是**解释执行的脚本语言**(不用单独编译，在运行过程中解释)，在 shell 中可以直接调用 Linux 系统命令。

Linux中默认的shell是B shell ——Bash （主流，常用）， 还有一类是C shell, 这两种的区别是语法结构不同， 主要使用bash  shell 。

------

## 初识 shell

shell 版hello world .

echo 输出命令:

这个命令如果学过Java的类似于 System.out.println()，C语言的类似于 printf()，在shell 当中语法形式如下：

```
echo 【选项】【输出内容】
```

　　-e  表示支持反斜杠控制的字符转换，也就是转义字符 (加上这个选项支持更加复杂的选项)。

![Linux_shell2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell2.png?raw=true)

![Linux_shell3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell3.png?raw=true)

echo命令的注意事项：

1： 输出的字符串内容不能包含“ ! ” ，因为！ 在shell中有特殊作用，如果要执意输出 ！， 那么这个字符串只能用单引号引起来，做特殊标识。

  

首先我们编写一个shell 脚本。通过 vi hello.sh，打开 hello.sh 文件，文件内容：

```
#! /bin/bash
echo "helllo "
```

①、Linux系统是不区分文件后缀名的，但是创建脚本文件 hello.sh，后缀名最好加上.sh（虽然不加也没问题， 真正起作用的是是在运行的时候，解释执行 文件开头的#! /bin/bash），便于我们识别。

②、脚本的第一行 #!,这是一个约定的标记，它告诉系统这个脚本需要用什么解释器去执行，即使用哪一种 shell，所以学习 bash，第一行固定都是 #!/bin/bash。这是不能省略的。

执行该脚本，有如下两种方式：

①、作为可执行程序

```
cd /tmp                     #进入到脚本所在的目录
chmod +x ./hello.sh   #使得脚本具有可执行权限
./hello.sh                  #执行脚本
```

注意：一定要写成 ./hello.sh，而不是 hello.sh，运行其它二进制的程序也一样，直接写 hello.sh，linux 系统会去 PATH 里寻找有没有叫 hello.sh 的（这是后面会讲的环境变量的配置），而只有 /bin, /sbin, /usr/bin，/usr/sbin 等在 PATH 里，你的当前目录通常不在 PATH 里，所以写成 hello.sh 是会找不到命令的，要用 ./hello.sh 告诉系统说，就在当前目录找。

②、作为解释器参数

也就是直接运行解释器，其参数就是 shell 脚本的文件名，如下：

```
/bin/sh hello.sh
或者
bash hello.sh
#注意：这种方式执行脚本，并不需要第一行写上 #!/bin/bash。
```



**shell 编程的最大好处就是所有的Linux命令都可以在脚本中执行(应用)**。 



## bash基本功能

### 历史命令

```
history # 改命令可以查看我们输入的命令的历史
table键 可以补充命令输入
```

![Linux_shell_history.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_history.png?raw=true)

使用呢“！” 来调用历史文件中的命令， 十分方便，特别是

```
! 字符串
例如： 
！ ser
调用历史文件（~/.bash_history）中以ser开头的字符串的最后一条命令。 如果命令十分难输入和难找，这种方法十分的便利。
```

![Linux_shell_history2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_history2.png?raw=true)

### 命令的别名：alias

![Linux_shell_alias.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_alias.png?raw=true)

### 常用快捷键

![Linux_shell4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell4.png?raw=true)

### 输入输出重定向 

**①、标准输入输出**

就是我们的输入设备和输出设备

![Linux_shell_redirect.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_redirect.png?raw=true)

**②、输出重定向：将命令执行结果本该显示在屏幕上的存储到别的地方**

改变了正常的输出方向。（他的作用可类比日志）

![Linux_shell_redirect2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_redirect2.png?raw=true)

将输出信息写入到`/dev/null` 就是将输出写入到垃圾箱。 

错误输出2>> ,这两者之间没有空格

**③、输入重定向：本该由键盘输入的信息改为由文件进行输入**

　　输入重定向用的很少。在讲解输入重定向之前，我们先介绍一个命令：

![Linux_shell_redirect3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_redirect3.png?raw=true)

### 多命令顺序执行

![Linux_shell_commandOrder.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_commandOrder.png?raw=true)

第一个符号`;`连接两个命令，两个命令是没有任何逻辑关系的，即使第一个命令出错了，第二个命令还是会执行。

`&&`  常用于安装rpm 包 ， `./config && make && make install` 

当我们需要在程序中自动判断一条命令是否正确执行， 就可以使用&& 与|| 结合的用法

`命令 && echo yes || echo no` 

当命令正确执行的时候过&& ，执行yes  ，不过||

当命令不正确执行的时候执行过不了&& ，yes不能执行，过|| 执行no

### 管道符 命令1 | 命令2

![Linux_shell_channel.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_channel.png?raw=true)

注意： 管道符要求的是命令1的正确输出，才能作用命令2 的操作对象。

### 通配符



![Linux_shell_wildcard.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_wildcard.png?raw=true)

![Linux_shell_wildcard2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_wildcard2.png?raw=true)

注意： 

1：[ ] 中括号只匹配一个字符。



### bash 中的其他特殊符号

![Linux_shell_symbol.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_symbol.png?raw=true)



## Linux的bash变量

### 什么是变量

变量是计算机内存的单元，其中存放的值可以改变。

当 shell 脚本需要保存一些信息时，如一个文件名或者一个数字，就把它放在一个变量里。每个变量都有一个名字，可以根据名字来引用变量。使用变量可以保存有用信息，使系统获知用户的相关设置，变量也可以保存暂时信息。

**变量的命名规则**(命名注意事项)

1： 和绝大多数编程1语言一样不能以数字开头。

![Linux_shell_variable.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable.png?raw=true)

**变量的分类**

1： 自定义变量

2： 环境变量 (和widows中的环境变量类似)

3：位置参数变量： 用来向脚本中传入参数和数据，变量名不能自定义，作用固定

4： 预定义变量，bash已经将名称，功能定义好了的。

### 用户自定义变量的用法

用户自定义变量也就是本地变量。只在当前 shell 中生效。

![Linux_shell_variable2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable2.png?raw=true)

![Linux_shell_variable3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable3.png?raw=true)

变量追加

![Linux_shell_variable4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable4.png?raw=true)

### 环境变量

环境变量和windows类似，一部分是系统定义的，一部分是允许用户自定义添加的。

环境变量主要保存的是和系统操作环境相关的变量。前面讲的用户自定义变量（本地变量）只在当前shell 中生效，而环境变量会在**当前shell和这个shell的所有子shell当中生效**，如果把环境变量写入相应的配置文件中，那么这个环境变量就会在所有的shell中生效。

**①、通过 pstree 命令区分当前shell 的级别是父还是子**

![Linux_shell_variable5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable5.png?raw=true)

**②、声明环境变量**

```
`export 变量名=变量值`
```

**③、查询所有环境变量**

```
`env`
```

前面讲的 set 命令是查看所有变量，而 env 是查看环境变量。

**④、查看、删除指定环境变量**

```
`$变量名          #查看环境变量``unset 变量名   #删除环境变量`
```

这两个和本地变量一样。

![Linux_shell_variable6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable6.png?raw=true)

**⑤、系统查找命令的路径环境变量 $PATH**

这两个PATH和widows中的环境变量PATH功能是一样吧的

我们知道调用命令必须要是在当前目录，或者是用绝对路径进行。但是实际上我们调用某个命令直接使用命令名就可以了，比如cd,ls等等这些常用的，这是为什么呢？

原因就是在 $PATH 里面我们已经定义好了，我们执行某个名称的命令，系统会首先去$PATH里面查找，如果找不到才会报找不到命令错误。

![Linux_shell_variable7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable7.png?raw=true)

可以看到，$PATH 里面都是保存的一些路径，然后通过:分隔。

 hello.sh 的脚本，我们知道要想执行它，必须首先赋予可执行权限，然后要么在当前目录通过./hello.sh来执行，要么通过绝对路径/tmp/hello.sh来执行。如果我们想直接通过 hello.sh 来执行呢？

解决办法就将hello.sh的所在路径添加到 $PATH 变量中，如下：

![Linux_shell_variable8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable8.png?raw=true)

Tab 键自动补全其实也是搜索的PATH变量。

**⑥、定义系统提示符的变量 $PS1**

改变提示符 如[root @ localhost   /], 显示成我们自定义的格式

![Linux_shell_variable9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable9.png?raw=true)

### 位置参数变量

顾名思义： 这些变量时用来描述位置参数的，且变量名和其作用是固定的不可改变的。我们能做的就是往里面传值取值。

![Linux_shell_variable10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable10.png?raw=true)

![Linux_shell_Location_variable.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_Location_variable.png?raw=true)

### 预定义变量的用法

预定义变量也是位置参数变量的一种，有如下几种用法：

![Linux_shell_variable11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable11.png?raw=true)

`$？` 是最常用的， 用来在程序中判断上一个命令是否正确执行。

### 接受键盘输入参数

![Linux_shell_read.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_read.png?raw=true)



### 声明变量类型 declare

![Linux_shell_variable12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable12.png?raw=true)

```
-i声明为整形
[root@localhost ~]# aa=11
[root@localhost ~]# bb=22
[root@localhost ~]# cc=$aa+$bb
[root@localhost ~]# echo $cc
11+22
[root@localhost ~]# declare -i cc=$aa+$bb
[root@localhost ~]# echo $cc
33
```

![Linux_shell_variable13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable13.png?raw=true)

### 变量测试

主要用于在脚本中判断上一个命令是否正确执行，我们也可以写if else 替换他们

![Linux_shell_variable14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable14.png?raw=true)

![Linux_shell_variable15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variable15.png?raw=true)



### 环境变量配置文件

我们将脚本的路径加入到 $PATH 路径中 ，然后就可以直接通过脚本的名称来执行脚本。没有写入文件，如果系统重启之后，发现又必须要重新加入到 $PATH 变量中才可以。

如果**想让这个环境变量一直生效就要将其加入到环境变量配置文件**中。

不用退出再登录，让修改后的环境变量配置文件直接生效：

![Linux_shell_source.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_source.png?raw=true)

环境变量配置文件中主要定义对系统的操作环境生效的系统默认环境变量，比如 PATH,HISTSIZE,PS1，HOSTNAME等默认环境变量。分别有以下配置文件：

![Linux_shell_variablefile.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variablefile.png?raw=true)

注意： 

1 ：只要保存在/etc 目录下的环境变量配置文件是对所有用户都生效。

2： 如果是放在当前家目录的中的环境变量配置文件进行修改就只对当前用户生效。



我们知道 $PATH 变量，用户每次登录，就会从上面的文件读取顺序读取所有配置文件，最后得到 $PATH 变量的值。

注意：越往后面的配置文件，里面配置的变量内容优先级越高。

![Linux_shell_variablefile2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_variablefile2.png?raw=true)

### 其他的环境变量

`~/ .bash_logout`  注销时生效的环境变量配置文件。 、

(如果我们想每次登陆的时候执行 清空历史命令等 就可以写着这个文件)

`~/.bash_history` 保存历史命令的文件。

系统登陆时显示的信息：

针对本地生效

![Linux_shell_issue.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_issue.png?raw=true)

针对远程登录生效

![Linux_shell_issue2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_issue2.png?raw=true)

注意， 本地登录的变量在这里就不再有效了， 只能写纯文本信息

且 默认在文件中配置的是没有生效的，需要修改ssh 的配置文件

两者都适用的配置文件

![Linux_shell_issue3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_issue3.png?raw=true)

(但是他是登录后才显示的)

## bash 的基础操作

### 正则表达式

#### 通配符与正则的区别

通配符 `* ? [ ]` 是用来**在系统中匹配符合条件的文件名的**。 `ls ， find，cp 这些命令不支持正则表达式，所以只能使用shell自己的通配符来进行匹配`  通配符是**完全匹配**(精确匹配一个)

![Linux_shell_regular.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular.png?raw=true)

正则表示是用来在**文件**中配置符合条件的**字符串**。grep等命令可以支持正则，  正则是**包含匹配**。



#### 正则表达式的概念及特点：

![Linux_shell_regular2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular2.png?raw=true)

![Linux_shell_regular11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular11.png?raw=true)

注意： “ * ”在正则中的作用与在通配符中的作用不同。 

![Linux_shell_regular12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular12.png?raw=true)

![Linux_shell_regular13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular13.png?raw=true)

```
正则表达式是对字符串操作的一种逻辑公式，就是用事先定义好的一些特定字符、及这些特定字符的组合，组成一个“规则字符串”，
这个“规则字符串”用来表达对字符串的一种过滤逻辑。规定一些特殊语法表示字符类、数量限定符和位置关系,然后用这些特殊语法和普通字符一起表示一个模式,这就是正则表达式(Regular Expression)。
给定一个正则表达式和另一个字符串，我们可以达到如下的目的：
　　1. 给定的字符串是否符合正则表达式的过滤逻辑（称作“匹配”）；
　　2. 可以通过正则表达式，从字符串中获取我们想要的特定部分。
```

#### 正则表达式有三部分组成：

![Linux_shell_regular3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular3.png?raw=true)

**1、字符类**

字符类，他们在模式中表示一个字符,但是取值范围是一类字符中的任意一个。

![Linux_shell_regular4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular4.png?raw=true)

练习示例：

![Linux_shell_regular6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular6.png?raw=true)

**2、数量限定符**

数量限定符，邮件地址的每一部分可以有一个或多个x字符,IP地址的每一部 分可以有1-3个y字符

![Linux_shell_regular5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular5.png?raw=true)



```shell
[root@ds_app1 zhaotest]# echo "abcd1234e4" | grep --color '4?'
[root@ds_app1 zhaotest]# echo "abcd1234e4" | grep --color 'a?'
[root@ds_app1 zhaotest]# echo "abcd1234e4" | grep --color 'ab?'
[root@ds_app1 zhaotest]# echo "abcd1234e4" | grep -E  --color '4?'
abcd1234e4
# 从上面可以知道 grep 命令支持数量限定符 正则表达式是需要-E选项的。 
```

![Linux_shell_regular7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular7.png?raw=true)

符合这些正则的字符串都会被过滤出来。

**3  位置限定符**

位置限定符：描述各种字符类以及普通字符之间的位置关系，例如邮件地址分三部分,用普通字符@和.隔 开,IP地址分四部分,用.隔开,每一部分都可以用字符类和数量限定符 描述。为了表示位置关系,需要位置限定符的概念

![Linux_shell_regular8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular8.png?raw=true)

![Linux_shell_regular9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular9.png?raw=true)

**4、特殊字符**

![Linux_shell_regular10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_regular10.png?raw=true)



### 字符 操作命令

01、字段提取命令 cut  

grep命令是提取出我们需要的行, cut 提起处于我们想要的列 。 他们常结合起来使用，提取出我们的目标值。

```
 cut [选项] 文件名
-b：仅显示行中指定直接范围的内容；

-c：仅显示行中指定范围的字符；

-d：指定字段的分隔符，默认的字段分隔符为“TAB”；

-f：显示指定字段的内容；

-n：与“-b”选项连用，不分割多字节字符；

--complement：补足被选择的字节、字符或字段；

--out-delimiter=<字段分隔符>：指定输出内容是的字段分割符；

--help：显示指令的帮助信息；

--version：显示指令的版本信息。

从文件中提取我们需要的几列字段
```

![Linux_shell_String2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_String2.png?raw=true)

![Linux_shell_String3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_String3.png?raw=true)

![Linux_shell_String4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_String4.png?raw=true)

grep 与cut结合提取出我们的目的值 (cut 一般不单独使用)

![Linux_shell_String5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_String5.png?raw=true)

02、格式化输出命令 printf 命令   

```
printf  ‘输出类型输出格式’  输出内容

　　　　输出类型：
　　　　　　%ns：输出字符串。n 是数字指代输出几个字符
　　　　　　%ni：输出整数。n 是数字指代输出几个数字
　　　　　　%m.nf：输出浮点数。m是整数，n是小数
　　　　　　
　　　　输出格式：
　　　　　　\a：输出警告声音
　　　　　　\b：输出退格键，删除键
　　　　　　\f：清楚屏幕
　　　　　　\n：换行
　　　　　　\r：回车
　　　　　　\t：水平输出退格键
　　　　　　\v：垂直输出退格键
```

printf 命令这个命令并不友好，也不常用，但是他是awk 命令的基本输出

03、awk命令  =>  

awk命令和cut命令的作用是一样的，就是获取文件中对应的列，但是cut 有局限(不能截取分割符不同的文件信息)且比较简单。 如果用cut命令无法解决了，就可以考虑awk命令。

![Linux_shell_awk.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk.png?raw=true)

![Linux_shell_awk2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk2.png?raw=true)

```
awk ‘条件1{动作1}条件2{动作2}....’  文件名  条件 （Pattern）

　　　　动作（Action）：
　　　　　　格式化输出
　　　　　　流程控制语句
　　　　例如：awk  ‘ { printf  $2  "\t"  $6"\n" } ’   student.txt

　　　　　　　　$2、$6：代表第几行

　　BEGIN  => awk  ‘BEGIN{print "test !!"} { printf  $2  "\t"  $6"\n" } ’   student.txt 

　　　　　　BEGIN 会在所有的数据处理完之前执行

　　　　　　例如：FS内置变量

　　　　　　　　cat /etc/passwd | grep "/bin/bash" | \ awk 'BEGIN {FS=":"} {printf $1 "\t" $3 "\n"}'

　　END  =>   相反 ：所有的数据处理完之后执行
```

![Linux_shell_awk3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk3.png?raw=true)

![Linux_shell_awk4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk4.png?raw=true)

![Linux_shell_awk5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk5.png?raw=true)

![Linux_shell_awk6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk6.png?raw=true)

![Linux_shell_awk7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk7.png?raw=true)

![Linux_shell_awk8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_awk8.png?raw=true)



04、sed 命令 ：是一种几乎包括在所有UNIX平台（包括Linux）的轻量级**流编辑器**。 

sed意为流编辑器(Stream Editor),在Shell脚本和Makefile中作为**过滤器**使用非常普遍,也就是把前一个程序的输出 作为sed

的输入,经过一系列编辑命令转换为另一种格式输出。

**sed 主要是用来将数据进行选取、替换、删除、新增的命令** （将这个命令**过滤器**）。　　这样看来就是一个修改内容的编辑器和vim类似。 但是与vim不同的是，sed 支持管道符。vim 只能操作文件，但是sed 能操作命令的输出结果。

```
sed [选项] -‘[动作]’- 文件名

　　　　-n：一般sed命令会把所有数据都输出到屏幕，如果加入此选择，则只会把经过sed命令处理的行输出到屏幕
　　　　-e：允许对输入数据应用多条sed命令编辑
　　　　-i：用sed的修改结果直接修改读取数据的文件，而不是由屏幕输出 (不仅修改显示还修改源文件的内容)
　　　　
sed命令行的基本格式为：
　　sed option 'script' file1 file2 ... 
　　sed option -f scriptfile file1 file2 ...
```

sed处理的文件既可以由标准输入重定向得到,也可以当命令行参数传入,命令行参数可以多次传入多个文件,sed会依次处理。 sed的编辑命令可以直接当命令行参数传入,也可以写成一个脚本文件然后用-f参数指定,编辑命令的格式为：

　　`/pattern/action`

其中pattern是正则表达式,action是编辑操作。 sed程序一行一行读出待处理文件,如果某一行与pattern匹配,则执行相应的action,如果一条命令没有pattern而只有action,这个action将作用于待处理文件的每一行。　

![Linux_shell_sed.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_sed.png?raw=true)

![Linux_shell_sed2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_sed2.png?raw=true)

![Linux_shell_sed3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_sed3.png?raw=true)

![Linux_shell_sed4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_sed4.png?raw=true)



![Linux_shell_String3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_String3.png?raw=true)

　　　　

```
打印，输出指定行：sed  -n  ‘2p’ student.txt

　　　　删除：sed “2,4d” student.txt   =>  删除2 -- 4行的数据，但是不修改文件本身 

 　　　追加：sed ‘2a  Hello’   student.txt　在第二行后追加 hello

　　　　插入：sed ‘2i  wrold Hello ’  student.txt  在第二行前插入两行数据

　　　　字符串替换：sed -i  ‘2s/99/55/g’ student.txt    把第2行的99替换成55

 　　　　　　　　加上 -i：连同文件一起修改，不加只是对打印修改

 　　　　　　　　加-e：sed -e ‘s/Liming/ads/g;s/Gao/ads/g’ student.txt

　　　　　　　　 同时把“Liming” 和 “Gao” 替换为ads
```

 

05、排序命令  sort=>  

```
sort [选项] 文件名

　　　　选项：
　　　　　　-f：忽略大小写
　　　　　　-n：以数值型进行排序，默认使用字符串型排序
　　　　　　-r：反向排序
 　　　　　 -t：指定分隔符，默认是制表符
　　　　　　-k n[,m]：按照指定的字段范围排序。从第n字段开始，m字段结束（默认到行尾）

 　　　　例如：sort -t ":" -k 3,3 /etc/passwd （一般不需要）
　　　　　　指定分隔符是 “：”，用第三字段开头，第三字段结尾排序，就是只用第三字段排序（用户UID排序）
　　　　　　这里他是只会将第三个字段识别为字符型，如果第三个字段是数值型，他需要加上-n 选项。 
```

 

 06、统计命令wc  =>  

```
wc [选项] 文件名
　　　　选项：
　　　　　　-l：只统计行数
　　　　　　-w：只统计单词数
　　　　　　-m：只统计字符数
```

![Linux_shell_wc.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_wc.png?raw=true)

### 条件判断

就是测试一个条件是否成立。 (这里主要是给shell 脚本程序做判断使用的)

**判断文件是否存在**

![Linux_shell_test.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_test.png?raw=true)

使用方式：

![Linux_shell_test2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_test2.png?raw=true)

![Linux_shell_test3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_test3.png?raw=true)

![Linux_shell_test4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_test4.png?raw=true)

**判断文件权限**

![Linux_shell_testAuthority.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testAuthority.png?raw=true)

![Linux_shell_testAuthority2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testAuthority2.png?raw=true)

（判断文件权限要注意， 他不会区分所有者和所属组等，只要包含有读写等权限都会返回true）

**两个文件之间进行比较** （常用于判断一个文件是否修改，常用于操作同一个文件的不同备份）

![Linux_shell_testCompare.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testCompare.png?raw=true)

注意:

 -ef 选项常用于程序脚本判断是否是硬链接。(人为则是使用 `ll -i` ) 

![Linux_shell_testCompare2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testCompare2.png?raw=true)

**两个整数之间的比较**

![Linux_shell_testIntegerCompare.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testIntegerCompare.png?raw=true)

![Linux_shell_testIntegerCompare2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testIntegerCompare2.png?raw=true)

**两个字符串间比较**

![Linux_shell_testStringCompare.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testStringCompare.png?raw=true)

![Linux_shell_testStringCompare2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testStringCompare2.png?raw=true)

**多重条件判断**

![Linux_shell_testMoreCompare.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_testMoreCompare.png?raw=true)





### 流程控制

#### if 语句

if 语句语法格式：

```shell
if condition -------( [ 条件表达式(注意两边的空格不能少) ] )  # if与condition之间的空格也不能少   
then  # 可以直接跟在 test表达式之后，这时候就需要“；” 隔开，也可以另起一行，这时不需要；分割
    command1 
    command2
    ...
    commandN 
fi
```

例子：

![Linux_shell_if_example.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_example.png?raw=true)	

![Linux_shell_if_example2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_example2.png?raw=true)	

if else 语法格式：

```shell
if condition
then
    command1 
    command2
    ...
    commandN
else
    command
fi
```

![Linux_shell_if_example3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_example3.png?raw=true)	

![Linux_shell_if_example4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_example4.png?raw=true)	

**if else-if else**

if else-if else 语法格式：

```shell
if condition1
then
    command1
elif condition2 
then 
    command2
  ...省略更多的命令..
else
    commandN
fi
```

以下实例判断两个变量是否相等：

```shell
a=10
b=20
if [ $a == $b ]
then
   echo "a 等于 b"
elif [ $a -gt $b ]
then
   echo "a 大于 b"
elif [ $a -lt $b ]
then
   echo "a 小于 b"
else
   echo "没有符合的条件"
fi
```

输出结果：

```
a 小于 b
```

if else语句经常与test命令结合使用，如下所示：

```shell
num1=$[2*3]
num2=$[1+5]
if test $[num1] -eq $[num2]
then
    echo '两个数字相等!'
else
    echo '两个数字不相等!'
fi
```

输出结果：

```
两个数字相等!
```

![Linux_shell_if_example5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_example5.png?raw=true)



#### case语句

多分支判断语句(类似java 中的switch语句)

case语句为多选择语句。可以用case语句匹配一个值与一个模式，如果匹配成功，执行相匹配的命令。case语句格式如下：

```shell
case 值 in
模式1)
    command1
    command2
    ...
    commandN
    ;;     # 这里；；代表程序段结束。
模式2）
    command1
    command2
    ...
    commandN
    ;;
esac
```

![Linux_shell_if_case.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_case.png?raw=true)

```shell
#! /bin/bash
read -p "input yes/no  " cho
case $cho in
"Y")
  echo "yes"
  ;;
"N")
  echo "NO"
  ;;
*)                      # 注意这里其他任何用* 表示不需要加引号
  echo "erro"
  ;;
esac
```



#### for 循环

for循环一般格式为：

```shell 
for var in item1 item2 ... itemN   #每次循环将item1/2.. 赋值给变量var
do                      # for in 后面的内容只要是隔开的，不管以什么分割，都会被当成一个单独的元素处理    
    command1
    command2
    ...
    commandN
done
```

写成一行：

```
for var in item1 item2 ... itemN; do command1; command2… done;
```

例子“

```shell
for loop in 1 2 3 4 5
do
    echo "The value is: $loop"
done
```

输出结果：

```
The value is: 1
The value is: 2
The value is: 3
The value is: 4
The value is: 5
```

![Linux_shell_if_for.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_for.png?raw=true)



![Linux_shell_if_for2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_for2.png?raw=true)

![Linux_shell_if_for3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_if_for3.png?raw=true)



#### while循环和until循环

```shell
while condition   # 循环过程中会对condition 进行改变，否则会进入死循环
do
    command
done
```

while 循环条件成立执行循环

```
#!/bin/bash
int=1
while(( $int<=5 ))
do
    echo $int
    let "int++"
done
```

![Linux_shell_while.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_while.png?raw=true)

until 与while相反循环条件不成立执行循环

![Linux_shell_until.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_until.png?raw=true)

#### 跳出循环

在循环过程中，有时候需要在未达到循环结束条件时强制跳出循环，Shell同java语言一样使用两个命令来实现该功能：break和continue。

