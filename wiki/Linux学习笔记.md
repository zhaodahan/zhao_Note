Linux学习笔记

# Linux的学习方法及注意事项

Linux的设计理念与widows不同，widows是以应用/个人优先，所以设计的比较切合人的使用习惯，比较华丽。Linux以服务器优先，以计算机为先，明确告诉计算机应该做什么事情。考虑的是性能和安全（稳定）。面向的人群是专业的工程师。

而且Linux是以服务器稳定性优先为原则的，装了图形界面客户端，就会占用大量资源，可能会启很多服务，这样服务犯错的机会也会增加，不符合服务器稳定优先原则。

**解决问题的智慧**

根据问题信息查找文档。

先自己思考

善用工具 (百度，Linux自带的帮助文档)

忘掉固有的思维方式(widows)

## 注意事项

1： Linux**严格的区分**大小写 (Linux中的命令是小写的)。

2 ：Linux中所有的内容都是以文件的形式保存的，尤其是设备，硬件。 (Linux中如果想让一个文件永久的生效，他归根结底的都要写入文件) 。我们是通过管理和操作文件来操作硬件的。 

3 ： Linux文件是没有扩展名这个概念的(和widows以扩展名来区分文件不同的)。 Linux不是靠扩展名来区分文件的类型，而是靠文件权限。Linux现在文件有扩展名，只是为了方便用户区分识别，用户墨守成规的一些自定义习惯，没有也无所谓，系统本身是不需要这些的。

4 : Linux 中的所有存储设备都必须挂载之后才能使用，包括硬盘，光盘，U盘。(可以理解为手动分配盘符)

5： windows下的程序不能直接在Linux下安装运行。(可以用服务器)	 

6 ： 对待Linux要改变思维，我们操作的不再是windows那样的pc,而是服务器，服务器的特点：一群人一起协作。

7 ： 重启之前应该正确的关闭服务 

8   : 防火墙不是查毒软件，只是一个过滤网。类似渔网捞鱼，网格就是过滤规则。配置防火墙的时候需要注意。

9   :  定期备份(不要把鸡蛋放在同一个篮子中)

## Linux操作的小技巧

1： 清除操作痕迹（清屏） ， `Ctrl +L` 等同于clear命令。

2 : 任何命令的终止操作 ， `ctrl +c` 

# Linux 入门之简介

## UNIX 与Linux的发展史与关心

UNIX与Linux的两者的关系是"父子关系， UNIX是父亲，Linux是儿子"。LInux是UNIX发行的一个分支，所以Linux是类UNIX系统。

UNIX是很古老的系统，发行于60年代，Windows 是80年代，Linux最年轻时90年代的 。

现在的Linux和UNIX底层都是用C语言写的。（**Linux捆绑了一个联网的协议TCP/IP ，捆绑了一个开发环境C语言**）

## Linux内核

只是个操作系统内核(最核心的部分，不大)，不包含任何的应用软件(如：桌面，应用程序)。 内核的官网（www.kernel.org）

与之对应的就是Linux发行版，就是厂商在内核的基础上(使用了内核)加入了自己的桌面和应用程序的一个操作系统。

## 开源软件

软件运行方式分商业运营和开源。 商业运营最主要的特征就是收费，不开放源代码。

就个人来说Windows平台等商业软件比较多，但是在**服务器**端Linux等开源软件占比较多。

开源---"开放源代码" ，开源并不等于免费。

## Linux的应用领域

**一： 基于Linux的企业服务器**

Linux是UNIX的“儿子”，它从一开始的时候就是作为服务器开发的。作为服务器是Linux的最重要的应用。

证明很多知名的网站使用Linux作为服务器：

使用网站(https://www.netcraft.com/) 来进行踩点和扫描

![netcraft.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/netcraft.png?raw=true)

什么是踩点：

举例： 小偷踩点。

在小区发发传单，没个们上塞几张，连续发几天后再去看，如果传单始终和之前一样说明没人，可以动手。 如果门上传单很干净，传单被清理，说明里面有人。这就是踩点。

比如网上攻击就是从踩点(从安全来讲就是扫描)开始， netcraft使用的就是踩点技术，就是往目标网站发送一个数据包，目标网站会回应一个数据包，这时候分析响应数据包从而判断目标网站的一些信息。

![netcraft_taoba.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/netcraft_taoba.png?raw=true)

我们开发的很多软件都是运行在Linux系统下的。 虽然我们是在Windows下开发的。

**二： 嵌入式应用**

Linux最典型的嵌入式应用就是手机系统。 andriod 底层内核是Linux，IOS的底层是UNIX。

还有就是智能电视。为什么叫智能电视，所谓的智能就是他里面集成了操作系统。智能电视就集成了安卓Android操作系统 (Linux 的内核很小，很方便，可以以裁剪成很多适应智能家电端的系统)。

**三： 电影娱乐业**

电影的特效的图形渲染在Linux上处理。

# Linux 入门之准备

## VMware 虚拟机的安装与使用

VMware是目前最主流的虚拟软件，最主要的好处就是在一台计算机上可以模拟多个操作系统。还有一个好处就是虚拟机安装的系统还可以和你本身的windows系统进行网络通信。(为一台电脑如何网络服务搭建进行测试)。 虚拟机软件安装王完成之后完全可以当做独立的计算机对待，且可以随机更改虚拟操作系统的硬件环境。

官网（https://www.vmware.com/）提供下载；

![WMware_config.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/WMware_config.png?raw=true)

(虚拟机运行速度主要看内存，且个人用户安装VMware不要高于8 ，高版本主要用于企业级集群)

虚拟机安装需要注意：

1： 给虚拟机分配的内存不能超过真实内存的一半(centos 6及以上如果要看到图形界面内存至少要为628)

2 ： 快照使用。 开机保存状态，下次直接从快照恢复。

3 ： 克隆的使用。 

## Linux系统分区介绍

系统分区也叫磁盘分区，从字面意义上就是把大的磁盘分割成几个小的磁盘。

![Linux_partition3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_partition3.png?raw=true)

**分区分类**

主分区：最多只能有4个  (这个限制不是Linux限制的，而是由硬盘的结构限制的，只要结构不发生改变，这个限制就存在)

扩展分区： 当我们硬盘容量增加了，4个分区已经不能满足我们的需要了。 这时候我们就拿出一个分区来作为扩展分区

​                   需要注意的是这里的**扩展分区有且只有一个**。 主分区+扩展分区	最多只能有4个。

​                   扩展分区不能写入数据，不能格式化，只能包含逻辑分区

逻辑分区： 包含在扩展分区中，能正常的写数据，格式化

![Linux_partition.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_partition.png?raw=true)

常见的情况是：

![Linux_partition.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_partition2.png?raw=true)

**格式化**：

硬盘正确的分区后依然不能写入数据，必须要格式化(高级格式化，逻辑格式化。 对应的低级是硬盘上的格式化，物理格式化)后才能写入数据。 这里的格式化是为了写入我们的文件系统（如： FAT32，NTFS, **EXT4**（centos使用的，Linux默认使用的文件系统，将柜子磁盘每4kb打上一个隔断做成一个4kb大小的小格子））。这里的格式化就像我们个柜子打入相应的隔断。

格式化的目的是写入文件系统，而且附带的将所有文件清空

**硬件设备文件名：**

在windows中我们个硬盘进行分区然后格式化后，我们只需要给分区分配盘符就可以直接使用。但是Linux不行。

Linux中是先大硬盘分成小硬盘(分区)，分完区后格式化，在分配盘符之前要给每一个分区起一个硬件别名。（这牵扯到Linux中的一个重要概念：Linux中所有的硬件设备都是文件）。

![Linux_hardwareName.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_hardwareName.png?raw=true)

对图中信息解释：

```
/ : 根目录， Linux下的最高目录，类比widows中的文件。所有数据都保存在根目录下。	
/dev : 根目录下的一级子目录，这个目录下放入的所有文件都是硬件文件。如果是IDE硬盘，我们称之为hd+硬盘号，如果只有一个硬盘就是hda,后面以此类推。
硬盘，光驱，鼠标等在dev都有其相应的硬件文件名。我们把这个文件名称之为硬件的设备文件名。这样做的原因是在Linux出现的初期我们并没有图形界面，我们并不知道该给哪个设备分配盘符，所以我们被迫的给每个设备取个设备名，方便我们识别。如sda 代表SATA接口的第一个硬盘， sdb代表SATA接口的第二个硬盘
```

所以Linux的分区步骤：

分区——>格式化——>必须给分区建立设备文件名——>分配盘符

**分区设备文件名**：

硬盘有了文件名，给分区也要定义一个文件名。

`分区文件名=硬盘文件名+ 分区号`

如： /dev/sda1 : sda代表着SATA接口的一个硬盘。 sda1代表的就是这个硬盘当中的第一个分区，依次类推。

**分配盘符**(**挂载**)：

我们把给分区分配盘符的过程叫做挂载；我们把盘符叫做挂载点。

我们的分区有了，但是在正常使用之前，我们还必须制定挂载点。 widows 是把C ,D字母作为盘符，而Linux理论上拿空的目录名称作为盘符。

![Partiton.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Partiton.png?raw=true)

Linux中必须作为分区的有两个:

1: 根分区（/）

2 : swap 分区： 就是虚拟内存。当我们真正的内存空间不够用的时候，就可以拿swap分区的内存空间来当内存。理论上将交换分区应该分配为内存的两倍，但是最大不能超过2GB(虚拟内存到底不是真正的内存，不能真正的当做内存，大于2GB只会占用我们的内存空间)。  

只要有上面的这两个分区是必须的Linux就可以正常的使用。

3： /boot : 推荐将boot单独分区（因为任何操作系统想正常的使用都必须有一定的空余空间，将所有的数据都放在根分区下，万一根分区写满了，Linux就有可能启动不起来）。boot是作为启动时候保留空余内存的分区，启动后我们不往这个分区写入数据。

![file_partition.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/file_partition.png?raw=true)

## Linux系统的安装

1: 打开虚拟机进入BIOS设置。 计算机默认是通过硬盘启动，但是现在虚拟机硬盘现在是空的。	无法通过硬盘启动。所以我们需要做的就是通过光盘启动(Boot启动----->CD-ROM光盘)。 我们安装完之后还要把这个顺序改回通过硬盘启动。

​	![CD-ROM_install.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/CD-ROM_install.png?raw=true)	

软件安装包

![Linux_soft_package.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_soft_package.png?raw=true)	

个人用户建议安装Desktop ，如果是服务器就安装Minimal最小化安装，安装最少，节约更多资源，更少服务更安全。

root用户登录进去会进入到根目录下面一级子目录家目录下面存储着我们的安装日志：

![Insall_log.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Insall_log.png?raw=true)	

## Linux各目录的作用

![Linux_catalog.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_catalog.png?raw=true)

```
/bin , /sbin , /usr/bin , /usr/sbin 都是跟命令相关的命令。命令对Linux来讲是很重要的，就是一个可执行的二进制文件。
/bin 下面的命令所有用户都可以执行，但是 /sbin ，super root‘s bin 是只有超级用户才能执行。而/usr/bin 这线面的命令在单用户模式(类似于widows下的安全模式---主要是用来做修复的，单用户也是用来做这件事的)下不能执行。
/etc 配置文件存储的目录

```

![Linux_catalog2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_catalog2.png?raw=true)

```
/media （媒体设备，软盘，光盘） , /mnt （额外设备 U 盘） , /misc (NFC服务共享目录)
/opt 外来软件的安装位置，不过更习惯的是放在 /usr/local中
```

![Linux_catalog3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_catalog3.png?raw=true)

```
/proc ,/sys 他们最大的不同点在与他们存在于内存里面，注意不要往里面写数据，因为一重启就丢失了。而且如果写入文件过大超过内存直接就系统给写死机了。
/sry 存放的是服务在启动过程中的一些数据。
/usr 并不是user 用户目录，而是UNIX software Resourse系统软件资源目录。就像上面数的我们更习惯将第三方软件安装在/usr/local中。 是个比较重要的目录。
/var 动态数据保存的位置，如日志， mysql的默认库	
```

# Linux 入门之基础

## 常用命令

命令的格式

```
	命令  [-选项] [参数]
	一个命令比如包含命令的本身， 
	-选项 ： 这个“-”是标志，告诉我们这是一个选项，用来调整命令的功能的，用以输出不同的样式等。且多个选项是
	         可合并在一起写的
    [] : 代表着可选。 可以加上也可以不加。
    参数之间用空格分割区分
    eg： ls -la /etc
```

个人感悟： 命令操作最终对象就是文件。

### 目录处理命令 	ls

![Linux_ls.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ls.png?raw=true)

这个命令是我们最常用。

注意事项：

1：ls 命令， 不加任何参数代表操作的是当前所在位置的当前目录。

2  : -a 查看的是所有文件(包括隐藏文件)

3 ： Linux中以“.”开始的文件代表的是隐藏文件， 它的初衷是告诉用户这是一个系统文件，你最好不要去动他。

4 ： -l  （long） 选项 ，以长格式显示。 可以用于查看权限。

后面部分：

![Linux_ls_l.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ls_l.png?raw=true)

前面10位部分：

![Linux_ls_l_authority.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ls_l_authority.png?raw=true)

5  ： -d directory 。 这表示查看当前目录本身，而不查看目录下的具体文件

6  ： -i 查看文件的i节点。

### 目录处理命令 	mkdir

这里的目录也就是windows中的文件夹

![Linux_mkdir.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_mkdir.png?raw=true)

注意：

1 ： 目录的创建需要有规划。

2： 不要轻易的在/root 下面创建目录和文件

3 ： 如果我们要在一个不存在的目录下创建一个子目录，要想成功需要使用-p选项，递归创建。

4 ： mkdir 可以同时创建多个目录(参数区填写多个目录名即可)

### 目录处理命令  cd

![Linux_cd.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_cd.png?raw=true)

### 目录处理命令  pwd

![Linux_pwd.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_pwd.png?raw=true)

### 目录处理命令  rmdir

![Linux_rmdir.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_rmdir.png?raw=true)

注意：

1： rmdir 的功能是删除**空目录**。 如果想删除一个非空的需要使用rm 命令来删除，这个命令不常用

### 目录处理命令  cp

![Linux_cp.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_cp.png?raw=true)

![Linux_cp_example.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_cp_example.png?raw=true)

注意： 

1： cp命令在复制文件的时候是不需要加选项的，只需要给出**源文件或目录**和**目标目录**，但是如果要复制目录的话需要加上-r选项。

2 ： cp命令可以复制多个文件。 源文件参数以空格分割， 最后一个参数是目标目录。

3 ： cp如果要保留复制文件的文件属性，这时候就需要使用-p选项。

4 ： cp命令在做复制操作的时候可以在复制过去的同时改名。只需要在目标目录后面跟上要修改成的名称即可

### 目录处理命令  mv

剪切与改名是同一个文件

![Linux_mv.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_mv.png?raw=true)

注意：

1： mv命令操作目录与cp命令不同可以直接操作目录不需加上-r选项。

2 ： mv命令对当前目录下文件进行改名 `mv aa bb`



### 目录处理命令  rm

![Linux_rm.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_rm.png?raw=true)

注意：

1 ： 删除需谨慎，最好先备份(如果误删，有相应的软件找回，但是误删后不要在做过多的写读操作)

2 ： rm 的本意是删除文件，如果你想删除目录就需要加上-r选项。`rm -rf 目录名` 加上-f 选项是为了避免询问删除目录的字目录是否删除

### 文件处理命令 touch

创建空文件：

![Linux_touch.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_touch.png?raw=true)

注意： 

1： 创建文件时不建议文件名中有空格(和widows不同)。因为Linux命令中是以空格作为分割符号的，如果带了空格，它会被认为要批量创建两个文件。 如果硬是要创建含有空格的文件名，可以将文件名用双引号引起来告诉系统这是一个整体. eg ： `touch "program files"`。不建议的原因是以后再使用的时候需要一直用双引号引起来。

### 文件处理命令 cat

浏览文件， 查看文件内容， 直接将文件内容显示在控制台上

![Linux_cat.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_cat.png?raw=true)

注意： 

1： cat 命令不太适合浏览比较长的文件。

**文件处理命令  tac**

cat 命令的反向命令，可以反向显示 (文件内容的反向显示)

![Linux_tac.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_tac.png?raw=true)

注意： 反向显示命令不支持 -n选项

### 文件处理命令 more

如果是一个短的文件我们可以用cat 命令查看，如果是一个长文件的话，我们可以使用more命令查看。 more 是我们常用的分页显示文件的命令。

![Linux_more.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_more.png?raw=true)

注意： 

1： more命令存在缺陷就是它的子命令无法往回翻页。只能向前。

### 文件处理命令 less

与more命令不同的是less 命令是more 的升级版弥补了more的缺陷是可以往上翻的。

![Linux_less.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_less.png?raw=true)

注意： 

1 ： less命令的往上翻页 

​            按键是pageUp 是一页一页的往上

​            上箭头按键是一行一行的往上翻

2 ： less 命令比more命令更高级的不止可以往上翻页，它还可以搜索。

​       在less 的浏览状态按 `/+ 要搜索的内容`。按n (next)可以接着往下找

### 文件处理命令 head 

如果一个文件很长，而你只想查看这个文件的前几行，就可以使用head 命令

![Linux_head.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_head.png?raw=true)

注意：

1： 如果使用head 命令不制定查看前多少行吗，它会默认的查看前十行。

### 文件处理命令 tail 

与head 命令相反，查看文件的末尾几行

![Linux_tail.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_tail.png?raw=true)

注意：

1 ： tail 命令常用-f 选项,动态显示文件末尾内容。 常用于监控服务器软件运行日志。



### 链接命令  ln

生成链接文件，类似于widows中的快捷方式

![Linux_ln.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ln.png?raw=true)

![Linux_ln2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ln2.png?raw=true)

注意：

1 ： 如果要生成软链接，就需要使用-s 选项， 这也是我们使用最多的。如果要生成硬链接就不用加上任何的选项

**软链接和硬链接有什么特点和他们之间有什么区别**

![Linux_ln_soft.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ln_soft.png?raw=true)

硬链接的文件信息与原文件相比除了文件所在位置不一样，其余的一模一样。类似于cp 拷贝一样，但是与cp 复制的文件不同，硬链接会同步更新源文件。 等同于操作源文件与硬链接相同(除了删除)。

![Linux_ln_hard.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ln_hard.png?raw=true)

源文件删除丢失之后，软链接就不可以访问了，但是硬链接仍然是可以访问的。

**怎么判断一个文件是硬链接**？

通过i节点来识别。一个文件一定有一个i节点， 一个i 节点不一定对应一个文件。

![Linux_ln_hard_i.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ln_hard_i.png?raw=true)

这也解释了为什么硬链接可以同步更新，我们队文件的操作实际是对i节点的操作， 两个文件都是同一个i节点。 （硬链接的使用情况并不多） 相当于是一个实时备份。

硬链接不能对目录使用，但是软链接可以。



### 权限管理命令 chmod

(权限管理命令中最常用的)

权限更改命令 chmod (change mode)： 我们创建的一个文件他默认的有一个文件，但是他不一定合理，我们可以通过chmod来修改。

我们可以通过 `ls -l` 查询到一个文件的权限 。

权限分成三类： 读 写 执行

![Linux_chmod.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod.png?raw=true)

一个文件的权限谁可以更改： 一个是文件的所有的者， 一个是超级管理员root.

![Linux_chmod2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod2.png?raw=true)

![Linux_chmod3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod3.png?raw=true)

![Linux_chmod4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod4.png?raw=true)

但是我们最常用的是使用数字来赋予权限。 

![Linux_chmod5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod5.png?raw=true)

![Linux_chmod6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod6.png?raw=true)

注意

1： chmod 的选项- R ,递归修改。修改权限通过这个选项也可以递归修改。

r w x 权限的理解：

![Linux_chmod7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod7.png?raw=true)

![Linux_chmod8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod8.png?raw=true)

我们通常有个错误的理解，如果我们想要删除一个文件，我们必须要对这个文件有写权限。 其实不然，写权限是修改这个文件的内容。 **删除一个文件需要的权限是我们需要对这个文件所在的目录具有写权限**。

![Linux_chmod9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chmod9.png?raw=true)



### 权限管理命令 chown 

既然一个文件的权限是可以更改的，那么他的所有者也是可以更改的。 chown (change owner)

![Linux_chown.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chown.png?raw=true)

![Linux_chown2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chown2.png?raw=true)

注意： 

1 ： 在Linux系统中即便你是这个文件的所有者，你也不能修改这个文件的所有者，只有root用户才能修改。



### 权限管理命令 chgrp

一个文件的所有者都可以改变，当然文件的所有组也可以改变 chgrp (change group)

![Linux_chgrp.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chgrp.png?raw=true)

注意：

1： 无论是该权限的所有者还是所有组，他们的前提是必须都存在



### 权限管理命令 umask

在文件创建的时候他有一个默认(缺省)权限。这个默认权限就是通过umask 命令显示和设置于的。

文件创建的时候默认有个缺省组，这个组就是文件所有者的缺省组(主要职位)。一个文件可以属于多个组，但是他的缺省组只有一个，就像一个人可以多个名称，但是他只有一个主名称。

![Linux_umask.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_umask.png?raw=true)

注意：

1： 缺省创建的文件是不可以有可执行权限的。目录的权限就是缺省权限，去掉所有的执行权限就是文件的缺省权限。

![Linux_umask2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_umask2.png?raw=true)

2：直接使用umask命令看到的是权限掩码

![Linux_umask3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_umask3.png?raw=true)

3 : 设置缺省权限

![Linux_umask4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_umask4.png?raw=true)

4 ： 不建议更改系统的默认权限(022--掩码， 真正权限是755)。



### 文件搜索命令 find

find命令的功能呢类似于widows中文件夹的搜索功能。但是在服务器中尽量的少用搜索，对文件做好规划，因为搜索操作会占用大量的资源。

![Linux_find.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find.png?raw=true)

常见用法：

![Linux_find2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find2.png?raw=true)

注意：

1： 根据名字搜索(最常用)，与widows的模糊查找不同的是,Linux中的查找是精确搜索。 如果我们想使用模糊搜索。我们可以使用统配符，如 。

```
* 匹配任意字符 `*name*` 
？匹配单个字符 'name???' 表示name后面有3个字符
```

2 ： Linux中是严格区分大小写的。 

3 ： 按照大小来搜索，我们一般搜索大于多少和小于多少。 但是需要注意的是我们这里使用的单温是数据块。

​         `1 数据块  512 字节 0.5k`  数据块是Linux中存储文件的最小单位512字节

![Linux_find3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find3.png?raw=true)

4:根据文件的时间属性查找

![Linux_find4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find4.png?raw=true)

5 ： 

![Linux_find5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find5.png?raw=true)

![Linux_find6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find6.png?raw=true)

![Linux_find7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find7.png?raw=true)

![Linux_find8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find8.png?raw=true)

-ok 选项只是用来表示确认。

-inum ： i 节点 常用于操作就是删除一些文件名比较特殊的文件，如包含空格的文件，但是他们都有个i节点。

![Linux_find9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_find9.png?raw=true)

注意： `-exec 命令 {} \;`  是固定格式。



### 文件搜索命令 locate

类似于widows中的everything工具进行快速查找。其原理也和everything类似，他并不是去遍历全盘文件，而是建立并维护了一个类似索引的资料库区查找(Linux中的所有文件的位置会被定期更新到这个资料库中)。 但是这样就会有一个问题，一个新建立的文件并没有被收录到这个资料库中，使用locate命令是搜索不到的。而find命名是实时查找就不会存在这样问题。解决这个问题的方法就是手动的去更新资料库(使用命令 `updatedb`)。但是即使我们更新资料库，还有一个隐藏的问题就是存在于 `/tmp` 等目录中的临时文件是不会被locate命令所搜索到的

![Linux_locate.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_locate.png?raw=true)

![Linux_locate2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_locate2.png?raw=true)



### 文件搜索命令  which

更精确的查找命令which。 查找一个命令课可以直接找到这个命令所在目录。

![Linux_which.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_which.png?raw=true)

注意： 

1： which除了可以查找得到这个命令的路径， 还可以查找到这个命令是否具有别名。



### 文件搜索命令  whereis

whereis 命令可which命令有很大的相同点。 但不同点在于它不仅会列出命令存在的目录，还会把命令的帮助文档的目录(绝大部分命令的帮助文档存放在/usr/share 目录下)列出来。

![Linux_whereis.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_whereis.png?raw=true)

### 文件搜索命令  grep

（这个命令比较常用: 类似于sql语句中的where过滤语句，将我们的信息过滤出符合条件的信息）

他与上面的几个搜索不同的是他是在文件内部进行搜索

![Linux_grep.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_grep.png?raw=true)

![Linux_grep2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_grep2.png?raw=true)

![Linux_grep3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_grep3.png?raw=true)



### 帮助命令  man

man命令不止可以查看命令的帮助 `man 命令名称`，还可以查看到系统配置文件的相关帮助信息  

![Linux_man.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_man.png?raw=true)

注意：

1： man命令会打开帮助信息界面，他的浏览方式与more, less命令相同。

2 ：如何查看帮助文档

```
我们查看帮助文档主要是因为我们不知道这个命令的功能是什么。 这时候我们就只需要看这个帮助文档的最开始一行(NAME 所在的那一行描述了这个命令的功能)

如果是查看配置文件的帮助信息，除了配置文件的描述信息，还要看配置文件中内容的格式。

```

3 ： 使用man 查看配置文件的帮助文档的时候需要注意：格式是`man 配置文件名`，注意这里的配置文件名不能写绝对路径名，那样他会显示这个文件内容，这里只需要写上文件名即可。

4 ：因为man 可以查看配置文件帮助文档，但是他不能指定路径，但是如：passwd 既有一个命令叫这个名字，又有一个配置文件叫这个名字。 这时候就需要执行我们要查看的帮助文档的类型(1命令， 5 配置文件  `man 5 passwd` )

![Linux_man2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_man2.png?raw=true)

### 帮助命令  whatis

如果我们不需要像man命令一样查看到十分明确的帮助信息，我们只需要简单的看到这个命令是干什么用的。这时候我们就可以使用whatis命令，只查看命令的描述信息。

![Linux_whatis.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_whatis.png?raw=true)

### 帮助命令   apropos

和whatis命令类似的就是apropos命令，与之不同的是他只是用来查看配置文件的描述信息

![Linux_apropos.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_apropos.png?raw=true)

![Linux_apropos2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_apropos2.png?raw=true)

### 帮助选项  --help

如果我们只是想查看命令的选项，不需要查看具体描述性信息。 这时候可以使用 `--help` 选项

![Linux_help.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_help.png?raw=true)

### 帮助命令 help 

他可以用于查看shell内置命令.。 

怎么判断一个命令是内置命令，内置命令是找不到路径的(使用which，whereis命令)。

![Linux_shell_help.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_help.png?raw=true)

注意： 

1 ： shell内置命令的帮助信息无法使用man命令来查看

![Linux_shell_help2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shell_help2.png?raw=true)

### 用户管理命令  useradd

![Linux_useradd.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_useradd.png?raw=true)

注意： 

1 : useradd命令添加用户，只是添加了一些基本的信息，比如家目录。 并没有为其设置登录密码。如果我们想使用这个用户来登录系统，我们还需要passwd为这个用户加上密码。

### 用户管理命令 passwd

passwd命令可以用来设置（更改）用户的密码。 普通用户更改密码的时候必须符合密码复杂度要求。

![Linux_passwd.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_passwd.png?raw=true)

注意：

1： 每个普通用户只能更改自己的密码，管理员root能更改任何人的密码。



### 用户管理命令  who

Linux是一个多用户登录系统，使用who命令查看着一时间有多少用户登录了这个系统。

![Linux_who.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_who.png?raw=true)

![Linux_who2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_who2.png?raw=true)

### 用户管理命令   w 

w命令是who命令的升级。 

![Linux_w.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_w.png?raw=true)

![Linux_w2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_w2.png?raw=true)

### 压缩和解压命令  gzip

常见的病毒都无法感染压缩文件，所以在Linux中我们常用压缩。

命令`gzip` 对应的压缩包格式是 `.gz`

![Linux_gzip.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_gzip.png?raw=true)

![Linux_gzip2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_gzip2.png?raw=true)

注意： 

1 ： Linux上的压缩命令的压缩比例远高于widows中。

2 ：Linux的压缩命令gzip 也存在不足，第一是他只能压缩文件，不能压缩目录。 第二是gzip的压缩是不保留原文件的， 压缩之后原文件丢失了。 （与widows不同）

### 压缩和解压令  gunzip

对`.gz` 格式的压缩文件进行解压缩的命令是`gunzip`, 或者使用`gzip -d 文件名` 。

![Linux_gunzip.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_gunzip.png?raw=true)

### 压缩和解压命令  tar 

gzip 命令无法压缩目录，如果要压缩目录就需要使用tar命令，tar的原意是打包，在打包目录的同时将目录中的文件压缩

![Linux_tar.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_tar.png?raw=true)

![Linux_tar2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_tar2.png?raw=true)

注意:

1 : tar 命令 -c 选项用于打包， -x 选项用于解包 ， -z 表示打包的时候同时进行压缩 (这个选项十分常用，在压缩成 `.bz2` 格式时 ，这个选项变成 -j )



### 压缩和解压命令  zip 

`zip 命令对应的格式是 .zip` 。  .zip 是widows和Linux都具有的古老压缩格式。

Linux中的压缩包格式一般而言对于windows都支持，但是widows中的压缩格式在Linux中就不支持了。如果想将widows中的压缩包拷贝到Linux中而不用安装第三方应用而直接应用，这时候我们可以直接使用zip格式。 

![Linux_zip.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_zip.png?raw=true)

注意:

1 : zip命令与gzip命令不同， 他压缩后仍然保留原文件(与widows一样)。 而且zip 命令具有 -r 选项可用于压缩目录。 但是zip命令有个缺陷就是他的压缩比例不如gzip 导致他的应用不多。



### 压缩和解压命令   unzip

与zip对应的解压缩命令 unzip

![Linux_unzip.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_unzip.png?raw=true)

### 压缩和解压命令  bzip2

`压缩格式 .bz2 是 .gz格式的升级格式` ，他是用命令bzip2命令来压缩

![Linux_bzip2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_bzip2.png?raw=true)

注意： 

1 ： `.bz2` 既然是 `.gz` 的升级格式，他升级的是增加 -k选项，能产生压缩并保留原文件。

2  :  `.bz2` 的压缩比例进一步提升。常用于大文件的压缩。

3  ： 他还能和tar 命令结合起来使用 , 将`tar命令的 -czf  选项中的 -z选项变成 -j 选项 -----》 tar -cjf`  

​       这种结合也很常用。

![Linux_bzip2_tar.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_bzip2_tar.png?raw=true)

### 压缩和解压命令   bunzip2 

bzip2对应的解压缩命令是bunzip2

![Linux_bunzip2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_bunzip2.png?raw=true)

这种 `xxx.tar.bz2` 格式压缩文件十分常见

### 压缩和解压命令   小结

![Linux_compress_sum.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_compress_sum.png?raw=true)

------



### 网络命令  write

write 命令，往指定用户写信息 (类似于widows中的qq)

![Linux_write.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_write.png?raw=true)

注意

1 ：这里是使用 ctrl +D 来保存结束并发送。

2 ： 接收的用户必须在线。



### 网络命令  wall 

wall ==》write all , 写给所有，发广播信息。

![Linux_wall.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_wall.png?raw=true)

### 网络命令  ping 

ping 命令是用的最多的远程探索命令（通过发送数据包）

![Linux_ping.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ping.png?raw=true)

注意：

1 ： Linux下的ping命令与widows下的不同，除非Ctrl +C ，他是不会停的。(widows发送几条后就自动的停止了)

2 ： 如果要制定发送的数据包条数，使用-c 选项 即可

3 ： 在使用这个命令的时候需要关注 packet loss （丢包的信息）



### 网络命令   ifconfig



![Linux_ifconfig.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_ifconfig.png?raw=true)

### 网络命令  mail 

mail 邮件命令。可以看成write命令的升级版，不管用户在不在线都可以接收到。

![Linux_mail.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_mail.png?raw=true)

![Linux_mail2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_mail2.png?raw=true)

系统常常会给你发送一些系统邮件，这时候就可以使用mail命令来收到并查看系统邮件(这些系统邮件十分重要，可能是系统报错日志等)。

### 网络命令  last

![Linux_last.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_last.png?raw=true)

### 网络命令  lastlog

lastlog 是last的升级扩展命令。

![Linux_lastlog.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_lastlog.png?raw=true)



### 网络命令  taceroute

这条命令在实际的应用中是否有用， 我们在互联网上的每一步操作都留有足迹（记录）。

![Linux_taceroute.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_taceroute.png?raw=true)

### 网络命令  netstat

netstat（net 网络 status 状态） 查询网络状态信息

![Linux_netstat.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_netstat.png?raw=true)

netstat 命令是我们实际中十分常用的命令

### 网络命令  setup

使用setup 来配置网络IP 与ifconfig命令不同，他是永久生效的。

![Linux_setup.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_setup.png?raw=true)

配置步骤：

![Linux_setup2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_setup2.png?raw=true)

注意： 配置完ip ，service一定要重启。

### 挂载命令   mount  和卸载命令 umount

挂载就和windows 插入U盘一样，即插即用。 Linux与widows不同，他是为服务器设计的。需要我们手动指定一下挂载点(指定使用盘符)。

![Linux_mount.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_mount.png?raw=true)

![Linux_mount2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_mount2.png?raw=true)

注意： 在redhat 中，设备文件名是默认的，就是 `/dev/sr0`

### 关机重启命令   shutdown

这个命令关机重启更安全。推荐使用

![Linux_shutdown.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shutdown.png?raw=true)

![Linux_shutdown2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shutdown2.png?raw=true)

![Linux_shutdown3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_shutdown3.png?raw=true)



### 退出登录命令   logout

![Linux_logout.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_logout.png?raw=true)

## 文本编辑器 Vim



### 基本使用

![Linux_Vim.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim.png?raw=true)

注意： vim没有窗口，只有命令，所以你输入的每个字符都会被当成命令来对待

![Linux_Vim2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim2.png?raw=true)

esc 就会进入我们最常见的命令模式，在这里每个字符输入都会被当成命令来对待。 “ ： ” 是编辑模式的标志，是对显示进行编辑， 可以通过“ ： set nu”  ;来设置显示行号。

![Linux_Vim3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim3.png?raw=true)

![Linux_Vim4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim4.png?raw=true)

定位命令要在命令模式下使用， 一进去就是命令模式

![Linux_Vim5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim5.png?raw=true)

![Linux_Vim6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim6.png?raw=true)

![Linux_Vim7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim7.png?raw=true)

![Linux_Vim8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim8.png?raw=true)

![Linux_Vim9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim9.png?raw=true)

保存和退出

![Linux_Vim10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim10.png?raw=true)

快捷键 “shift + zz” 也可以保存退出。

总结：

![Linux_Vim11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim11.png?raw=true)

### 使用技巧

![Linux_Vim_skill.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim_skill.png?raw=true)

**我的具体实验**：

在家目录中创建zhao.test文件进行测试。 先在里面准备实验数据。

![Linux_Vim_test.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim_test.png?raw=true)



### 一： 导入命令执行结果

(1) 把一个文件的内容导入到当前的文件中。`：r` 可以导入文件内容

![Linux_Vim_test2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim_test2.png?raw=true)

(2) 在编写脚本的时候，查找一个命令锁在的位置。 `： ！ 命令`  可以在不退出vi 的情况下执行命令。

![Linux_Vim_test3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim_test3.png?raw=true)

(3) 将上面两个结合起来使用---把一个命令的执行结果导入到当前文件中

![Linux_Vim_test4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim_test4.png?raw=true)

在写脚本的时候我们常常需要在文件中插入注释写脚本的时间 ，这时候就可以使用 `：r !date`

![Linux_Vim_test5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_Vim_test5.png?raw=true)

### 二： 定义快捷键

(1) : 定义添加注释的快捷键

这里我使用 eclipse 常用别的注释快捷键 `ctrl + /` ;

在命令模式下输入：

```
：map ^— I#<ESC>  
注意 这里的^_ 不是在键盘上手动这么输入的，而是通过按键Ctrl +v ,ctrl+/;
这里的I表示将光标移动到行首
```

同时定义删除注释的快捷键

```
:map ^\ 0x   这0(数字0)表示将光标移动行首 x 表示将行首第一个字符删除
```

### 三： 替换

```
：ab 想要被替换的字符  替换的字符
eg   :ab zhao zhaobeiyong
我们输入zhao后回车之后就变成了zhaobeiyong 
```



### 四： 写入配置文件

上面设置的命令，在Linux重启之后就消失了，我们需要将习惯的配置写到配置文件(每个用户的家目录下的  .vimrc,没有就创建)。 如果是root就在/root下。 这里面只能放置一些编辑模式的命令。

```
我的配置文件内容：
1 set nu
 2 map ^_  I#<ESC>
 3 map ^\  0x
```



# 软件包的安装



## 软件包的基础知识

windows中的软件安装包在Linux中是不能识别的。好处就是widows中的大量病毒在Linux中是无效的。缺点是所有的软件都需要针对Linux重新开发。

**软件包的分类**：

![Linux_package.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_package.png?raw=true)

### 源码包

这种软件包就是由源码组成(C语言些德，Linux系统都是C语言写的，两者契合)。 因为他只是一堆源码，所以在安装的时候需要我们自己编译成二进制文件。

优点;

1： 源码开源

2：因为是源代码，所以选择度很高。 (操作性很强)

3： 在自己机器上编译，执行效率高

4：卸载方便，直接删除软件所在目录即可。不会像widows有残余的垃圾文件。

缺点： 不好安装，时间久，出错难解决

### RPM包 (常用)

他就是源码编译成的二进制文件包。

优点：

1： 实现编译好了，安装速度快

2： 包管理系统简单，通过简单的命令就可以实现包的安装，卸载，升级，查询

缺点：

1： 不能查看源码

2： 功能选择灵活性降低。

3： 很烦的依赖性。

就是我们在安装软件包 a的时候他依赖于b，所以我们就要去先安装b 。 我们在卸载的时候也是，需要先卸载b，再卸载a。



## RPM包管理(RPM包安装软件)

### RPM命令手工安装

#### RPM包的命名规则

![Linux_RPM.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM.png?raw=true)

所有的rpm包都在光盘的Package目录下。

注意： 

1：适应的硬件平台无论是i386还是其他，普通的机器上都可以安装，但是如果是x64表示软件是运行在64位的系统上的。noarch认定和的硬件平台都可以安装。

2 ： 短包名和包全名的使用。 如果我们已经安装了rmp包在需要使用到包名的时候我们就直接使用短包名，因为我们在安装了rpm包后，Linux会在数据库中为其存储对应的包名信息(类似于widows中的注册表)，下次使用的时候就直接从数据库中查询，就不需要书写包全名了(**rpm查询和卸载**)。 所以对应的如果没有安装rpm包，就需要使用包全名(**rpm 安装和升级的时候**)。



#### RPM包的依赖性

![Linux_RPM_dependent.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_dependent.png?raw=true)

依赖的解决方案：

1： 树形依赖先装依赖的。

2： 环形依赖，一条命令将所有依赖的都装上

3 ： 模块依赖在（www.rpmfind.net）上查找

 模块依赖详情：

![Linux_RPM_dependent2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_dependent2.png?raw=true)

#### RPM命令管理--安装升级与卸载

##### 安装

![Linux_RPM_install.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_install.png?raw=true)

(常用的习惯是 rpm -ivh  包全名)

注意：

1： rpm 手动命令安装软件，一般是先装主包，再装附加包。

![Linux_RPM_install2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_install2.png?raw=true)

##### 升级

![Linux_RPM_update.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_update.png?raw=true)

就是将-i(install)选项变成了-U(update)选项 。 注意这里是大写的U . Linux是严格区分大小写的。

##### 卸载

![Linux_RPM_erase.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_erase.png?raw=true)

因为这里的rpm包已经装过，所以参数只需要短包名即可。而且使用短包名无需在意目录。因为他是搜索的数据库

注意： 卸载需要注意依赖性。 a->b->c 。所以卸载的时候先卸载a，再卸载b.....



##### 查询

rpm查询十分常用，因为yum没有查询。yum安装也可以用这个命令来查询

![Linux_RPM_query.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_query.png?raw=true)

他常与grep命令结合使用 `rpm -qa | grep 包名` 。

查询包详细信息： (这个信息是组建这个包的时候就已经准备好了的，所以可以使用选项查看未安装的包，这时就需要使用包全名和注意目录)

![Linux_RPM_query2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_query2.png?raw=true)

查询安装位置(这个安装位置就是在组建的时候就已经指定了，所以可以查询未安装的包。虽然我们可以指定安装位置，但是一般不这样使用，因为会导致服务找不到)

![Linux_RPM_query3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_query3.png?raw=true)

根据文件名查属于哪个包

![Linux_RPM_query4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_query4.png?raw=true)

注意： 参数要求是系统文件名。这个文件名是安装rpm 包生成的，而不是你自己生成的。

(猜想： 因为rpm包的安装路径在组建的时候就已经确定好了，他会将这个路径存储在一个文件中，按照这个文件去安装，我们提供文件路径，然后去这个文件中查询)

查询包的依赖性：

![Linux_RPM_query5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_query5.png?raw=true)

##### 检验

rpm包安装之后我们将其特征记录下来，在一段时间后我们将现在的和刚安装时候记录下来的特征进行比较我们就知道了系统文件进行了哪些修改。

![Linux_RPM_verify.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_verify.png?raw=true)

注意： 大写的V，区分大小写。

常见的修改信息：

![ ](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_verify4.png?raw=true)

=====================对这些信息具体代表的内容的解释=========================================

![Linux_RPM_verify2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_verify2.png?raw=true)

(有这些字母表示有修改，没有就表示没有修改)

![Linux_RPM_verify3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_verify3.png?raw=true)

##### 文件提取(修复)

我们在使用过程中做了误操作，将系统的重要文件，命令给删除了，这时候我们不需要重新安装，只需要将rpm包中的文件提取出来进行覆盖即可。

![Linux_RPM_repair.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_repair.png?raw=true)

举例：

![Linux_RPM_repair2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_RPM_repair2.png?raw=true)

### yum命令在线自动安装

为了解决rpm依赖的麻烦，yum在线里面准备好了所有的rpm包，自动帮我们解决依赖的问题。

**yum的本质也是使用rpm包进行管理安装**，只不过它是将所有的rpm包存储在服务器上，并且相关依赖已经帮我们配置好了。我们只需要提供我们需要安装什么包，他就会自动帮我们做。(yum 命令管理的同样是rpm包)

##### 配置网络yum源

centOs 的默认网络yum源是配置好了的。只要机器能联网就能使用centOS的官方yum源服务器进行软件的安装。

![Linux_yum.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_yum.png?raw=true)

但是centOS的yum服务器在国外比较慢，这时候我们就可以将其换成我们国内的服务器。

![Linux_yum2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_yum2.png?raw=true)

**配置本地yum源**

![Linux_yum5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_yum5.png?raw=true)

![Linux_yum4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_yum4.png?raw=true)

![Linux_yum6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_yum6.png?raw=true)



yum目录存放在/etc/yum.repos.d下 
首先我们新建repo文件

```
vi /etc/yum.repos.d/myyum.repo
```


这里的yum文件名可以自拟 例：centos7.repo 
都可以但要与配置文件内的名称一致

这里的yum文件名可以自拟 例：centos7.repo 
都可以但要与配置文件内的名称一致

然后myyum.repo编辑文件内容 
这里我们将镜像文件放置在opt下的myyum目录中 
这个目录不会自动生成，下面在复制镜像内容前我们将新建

```
[myyum]
name=myyum
baseurl=file:///opt/myyum # file:// 这是协议名。 /这代表的是根目录。
enabled=1   (这两个配置是最重要的)
gpgcheck=0
```


*然后挂载本地的cdrom镜像 
mnt为系统为用户提供的挂载目录本身是没有文件的

*然后挂载本地的cdrom镜像 
mnt为系统为用户提供的挂载目录本身是没有文件的

```
mount /dev/cdrom /mnt
```


上面的方法可能会出现找不到接口的情况 
此时要利用Xftp将镜像上传至虚拟机中 （或者这里是挂载光盘）。我一般放在根目录下

上面的方法可能会出现找不到接口的情况 
此时要利用Xftp将镜像上传至虚拟机中 
我一般放在根目录下

#下一步挂载镜像

```
mount -o loop CentOS-7-x86_64-DVD-1611.iso
```


创建yum放置目录并复制镜像挂载后内容


创建yum放置目录并复制镜像挂载后内容

```
mkdir /opt/myyum
cp -rfv /mnt/* /opt/myyum
```


复制完成后生成yum清单 
yum repolist 
此时看见我们创建的myyum出现说明本地yum源配置完成 
一般配置过yum源都会清空yum缓存 
然后装软件测试 
这里我们装vim编辑器来测试

复制完成后生成yum清单 
yum repolist 
此时看见我们创建的myyum出现说明本地yum源配置完成 
一般配置过yum源都会清空yum缓存 
然后装软件测试 
这里我们装vim编辑器来测试

```
yum clean all
yum install -y vim
```


至此本地yum源配置完成

![Linux_yum7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_yum7.png?raw=true)

至此本地yum源配置完成

---------------------


**配置国内网络yum源**
国内yum镜像站点有很多，这里我们用网易的站点 
先将Centos-Base.repo备份

```
cd /etc/yum.repos.d
mv CentOS-Base.repo CentOS.repo.bk
```


下载网络yum包

下载网络yum包

```
wget http://mirrors.163.com/.help/CentOS7-Base-163.repo
```


生成yum缓存

------

Yum软件包管理器基于RPM包管理，能够从指定的服务器自动下载RPM包并且安装，可以自动处理依赖性关系，并且一次安装所有依赖的软体包，使用简单便捷。

##### Yum常用命令：

##### 1 安装

```shell
    yum -y install 全部安装  -y自动回答安装
    yum install package1 安装指定的安装包package1
    yum groupinsall group1 安装程序组group1
```

![Linux_yum3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_yum3.png?raw=true)

**Linux进行yum 程序安装时遇到的公钥没有安装的问题解决方法**

```
yum -y install gcc  --nogpgcheck
```

解决方法：在yum install xxxx 命令之后添加 --nogpgcheck 进行跳过公钥检查安装。

或者：

只需将/etc/yum.conf中"gpgcheck=1"改为"gpgcheck=0"即可



##### 2 更新和升级

```shell
    yum update 全部更新 (这里需要注意：这个命令不仅会将所有的软件包安装，而且还会将Linux内核升级， 但是Linux内核升级完后是需要在本地进行配置的。 如果是在远程会导致服务器直接崩溃)
    yum update package1 更新指定程序包package1
    yum check-update 检查可更新的程序
    yum upgrade package1 升级指定程序包package1
    yum groupupdate group1 升级程序组group1
```

##### 3 查找和显示

```shell
    yum info package1 显示安装包信息package1
    yum list 显示所有已经安装和可以安装的程序包 (他是要到远程服务器上去查询)
    yum search	关键字(包名) ：在远程服务上搜索相关的包
    yum list package1 显示指定程序包安装情况package1
    yum groupinfo group1 显示程序组group1信息yum search string 根据关键字string查找安装包
```

##### 4 删除程序

```shell
    yum remove &#124; erase package1 删除程序包package1
    yum groupremove group1 删除程序组group1
    yum deplist package1 查看程序package1依赖情况
```

yum 卸载一个问题，我们在卸载某个包的时候，他会将其依赖的包都卸载掉。 这就有一个问题。 可能一个包被多个软件依赖。 那么这样卸载就可能会导致一些无法启动的问题。 (所以尽量不要使用yum卸载)



##### 5 清除缓存

```shell
    yum clean packages 清除缓存目录下的软件包
    yum clean headers 清除缓存目录下的 headers
    yum clean oldheaders 清除缓存目录下旧的 headers
    yum clean, yum clean all (= yum clean packages; yum clean oldheaders) 清除缓存目录下的软件包及旧的headers
```

 

### 源码安装

#### 安装位置

rpm包默认的安装位置

![Linux_sourse_code_Install.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install.png?raw=true)

源码安装位置

![Linux_sourse_code_Install2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install2.png?raw=true)

`/usr/local/`系统专门准备的安装外来软件的位置。 /usr unix系统资源目录。

**安装位置不同带来的影响是启动方法的不同**：

![Linux_sourse_code_Install3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install3.png?raw=true)

rpm启动应用程序一般是存储在绝对路径 `cd /etc/rc.d/init.d/`  下，加上对应的应用名就可以启动了。

![Linux_sourse_code_Install4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install4.png?raw=true)

我们可以使用service命令(redhat系列独有)来简化启动，service的原理就是去搜索`/etc/rc.d/init.d/`目录。但是service只能启动rpm安装的，因为只有他们会将应用安装在默认目录。 源码安装的就找不到。

源码包安装的启动方式：

![Linux_sourse_code_Install5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install5.png?raw=true)

#### 安装过程

安装准备：

![Linux_sourse_code_Install7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install7.png?raw=true)

安装注意：

![Linux_sourse_code_Install8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install8.png?raw=true)

安装过程：

![Linux_sourse_code_Install9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install9.png?raw=true)

![Linux_sourse_code_Install10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install10.png?raw=true)

```
$ ./configure
$ make  
$ make install
```

**编译过程流程图：**

![Linux_sourse_code_Install6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sourse_code_Install6.png?raw=true)



源码包卸载：

直接删除安装位置的文件夹就可以了。

```
rm -rf /usr/local/应用名
```



### 脚本安装

![Linux_script_Install.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_script_Install.png?raw=true)

这种包常见的是硬件驱动。 他安装还是源码包

举例：Webmin安装过程
◆下载软件

```
http://sourceforge.net/projects/webadmin/files/webmin/
```

◆解压缩，并进入加压缩目录
◆执行安装脚本 setup.sh

------

# 用户管理

在Linux中主要是通过用户的配置文件来查看和修改用户的信息。

## 用户配置文件

### 用户信息文件/etc/passwd

![Linux_user_manage.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage.png?raw=true)

![Linux_user_manage2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage2.png?raw=true)

```
man 5 passwd ( 5  配置文件  这里需要使用文件名，不能使用绝对路径查看配置文件的帮助文档）
  There is one entry per line, and each line has the format:

              account:password:UID:GID:GECOS:directory:shell
  这里的password只是存放的密码标识，真正的密码存放在文件/etc/shadow中，且存放的是密文。
  shell: Linux的吗，命令解释器
```

![Linux_user_manage_file.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_file.png?raw=true)

### 影子文件 (密码文件) /etc/shadow

passwd的shadow。 存储的是真正的密码。且这个文件的权限是000， 只有root用户有权限操作。

![Linux_user_manage_shadow.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_shadow.png?raw=true)

时间戳换算

![Linux_user_manage_date.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_date.png?raw=true)

### 组信息文件 /etc/group 和组密码文件/etc/gshadow

![Linux_user_manage_group.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_group.png?raw=true)

用户默认的组名就是用户名。

gshadow与影子文件类似，存放的是组密码。

![Linux_user_manage_gshadow.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_gshadow.png?raw=true)

这个组密码不推荐使用，且不 常用。(只有root用户能将用户加入组。这里使用组密码就相当于将为组分配了一个管理员) 他的功能就类与我们群的管理员。



## 用户管理相关文件

家目录： 添加一个用户，他会自动的生成一个家目录。

![Linux_user_manage_other.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_other.png?raw=true)

如果想将一个用户变成超级用户。需要在/etc/passwd 中修改用户的UID 。Linux中识别用户就是通过UID。如果只是修改GID，只是将这个用户加入root用户组，拥有和root相同的权限。

用户邮箱：

![Linux_user_manage_mail.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_mail.png?raw=true)

虽然每个用户都有邮箱，但是并么有搭服务器，只是用内存，用到的只是邮箱的客户端。邮箱也是添加用户的时候就有了。

用户模板目录： 

```
/etc/skel/
```

在添加用户的时候，没有手动创建文件，但是家目录会生成一些默认的隐藏文件，他们就是从模板目录拷贝出来的。如果我们想每个用户在生成的时候有个默认文件，我们就可以将文件放入到这个目录下。



## 用户管理命令 

### useradd

![Linux_user_manage_command.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_command.png?raw=true)

![Linux_user_manage_command2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_command2.png?raw=true)

这个命令最简单的使用方式就是`useradd 用户名`，同时使用`passwd 用户名` 给用户添加修改密码，没有添加密码的用户是添加不完整的。这些命令最终去修改的就是上面说的配置文件。

![Linux_user_manage_command3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_command3.png?raw=true)

添加用户的缺省值来自两个文件 `/etc/default/useradd` ，`/etc/login.defs` 

### passwd

给用户设置密码。

![Linux_user_manage_passwd.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_passwd.png?raw=true)

虽然passwd有这些选项但是同useradd一样，追常用的还是直接`passwd 用户名` 。 并且只有超级用户能使用这些选项

超级用户能改任何用户的密码(且只有超级用户能在passwd 命令后跟上用户名)，普通用户只能改自己的(只需要passwd名命令 即可 passwd表示修改当前用户)。

### usermod

这个命令和useradd的选项和功能是类似的。与之不同的是useradd 命令是在添加用户的时候进行操作的。 usermod是添加之后对其进行操作，修改。 这里mod是modify的缩写。 

### change

这个命令是用来修改用户的密码状态的。

![Linux_user_manage_change.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_change.png?raw=true)

手工vim 直接修改shadow文件也能达到同样的效果，且更加的直观。

用的最多的是：我们只需要记住追常用的这个

![Linux_user_manage_change2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_change2.png?raw=true)

### 删除用户userdel

![Linux_user_manage_userdel.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_userdel.png?raw=true)

### 查看用户  ID

![Linux_user_manage_id.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_id.png?raw=true)

![Linux_user_manage_id2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_id2.png?raw=true)

### 切换用户 su

![Linux_user_manage_su.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_su.png?raw=true)

这里需要注意： 在使用su 切换用户的时候一定需要加上“-” 选项，可以避免对大多数的错误。普通用户的切换成其他的用户是需要密码的，但是超级用户切换成其他用户是不需要输入密码的。

su命令最基本的用法：

![Linux_user_manage_su2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_su2.png?raw=true)



## 用户组管理命令

![Linux_user_manage_groupadd.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_groupadd.png?raw=true)

![Linux_user_manage_groupmod.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_groupmod.png?raw=true)

修改组的操作不建议常做， 银行修改了用户组，会导致一系列的需要改变。还不如直接删除后，重新添加。

![Linux_user_manage_groupdel.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_groupdel.png?raw=true)

要想删除组，这个组中不允许有初始用户存在。但是可以存在附加用户。

**把用户加入组或者从组中删除**

![Linux_user_manage_gpasswd.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_user_manage_gpasswd.png?raw=true)

直接修改group文件也可以达到同样的效果。这里操作是附加用户。 (且建议不要修改初始用户，初始组)

# 权限管理

## ACL 权限

![Linux_acl.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl.png?raw=true)

比如有如下场景：

　　某大牛在QQ群内直播讲解Linux系统的权限管理，讲解完之后，他在一个公有的Linux系统中创建了一个 /project 目录，里面存放的是课后参考资料。那么 /project 目录对于大牛而言是所有者，拥有读写可执行（rwx）权限，对于QQ群内的所有用户他们都分配的一个所属组里面，也都拥有读写可执行（rwx）权限，而对于 QQ 群外的其他人，那么我们不给他访问/project 目录的任何权限，那么 /project 目录的所有者和所属组权限都是（rwx），其他人权限无。

　　问题来了，这时候直播有旁听的人参与（不属于QQ群内），听完之后，我们允许他访问/project目录查看参考资料，但是不能进行修改，也就是拥有（r-x）的权限，这时候我们该怎么办呢？我们知道一个文件只能有一个所属组，我们将他分配到QQ群所在的所属组内，那么他拥有了写的权限，这是不被允许的；如果将这个旁听的人视为目录/project 的其他人，并且将/project目录的其他人权限改为（r-x），那么不是旁听的人也能访问我们/project目录了，这显然也是不被允许的。怎么解决呢？

　　我们想想windows系统里面给某个文件分配权限的办法：

![Linux_acl2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl2.png?raw=true)

　我们想要让某个用户不具备某个权限，直接不给他分配这个目录的相应权限就行了。那么对应到Linux系统也是这样，我们给**指定的用户指定目录分配指定的权限**，也就是 ACL 权限分配。

当所有者，所属组，其他人等角色不能满足用户权限需要了，就需要使用ACL权限分配。

### **查看分区 ACL 权限是否开启：dump2fs**

要想使用acl权限，**分区**就必须支持ACL权限(acl 权限本身虽然是用户操作文件权限，但是是否可以支持ACL权限，是要看文件所在分区是否支持)。

现在的机器acl一般默认是开启的。

　**①、查看当前系统有哪些分区及分区占有状况的：df -h**

![Linux_acl3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl3.png?raw=true)

　　**②、查看指定分区详细文件信息：dumpe2fs -h 分区路径**

　　下面是查看 根分区/ 的详细文件信息

　　![Linux_acl4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl4.png?raw=true)

### 开启分区 ACL 权限

虽然现在的Linux一般都是默认的开启ACL权限，但是如果没有开启

**①、临时开启分区 ACL 权限**

```
mount -o remount,acl /
```

重新挂载根分区，并挂载加入 acl 权限。注意这种命令开启方式，如果系统重启了，那么根分区权限会恢复到初始状态。

**②、永久开启分区 ACL 权限**

　　一、修改配置文件 `/etc/fstab`

　　![Linux_acl5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl5.png?raw=true)

上面是修改根分区拥有 acl 权限

```
UUID=490ed737-f8cf-46a6-ac4b-b7735b79fc63 /                       ext4    defaults,acl        1 1
```

　注意： 这个文件是系统开启检测的文件，如果写错了会导致系统无法启动，所以在修改的时候需要十分小心

　二、重新挂载文件系统或重启系统，使得修改生效

```
mount -o remount /
```

###  查看 ACL 权限：getfacl 文件名

如果文件有ACL权限，使用`getfacl 文件名` 就可以查看这个文件的ACL权限

![Linux_acl6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl6.png?raw=true)

### 设定 ACL 权限：setfacl 选项 文件名

![Linux_acl7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl7.png?raw=true)

示例：

![Linux_acl8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl8.png?raw=true)

![Linux_acl9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl9.png?raw=true)

### 删除ACL权限

①、删除指定用户的 ACL 权限

```
`setfacl -x u:用户名 文件名`
```

　　②、删除指定用户组的 ACL 权限

```
`setfacl -x g:组名 文件名`
```

　　③、删除文件的**所有** ACL 权限

```
`setfacl -b 文件名`
```



### 最大有效权限 mask

我们给用户或用户组设定 ACL 权限其实并不是真正的ACL权限，其与 mask 的权限“相与”之后的权限才是用户的真正权限，一般默认mask权限都是rwx，与我们所设定的权限相与就是我们设定的权限。我们可以通过调整mask权限来达到控制用户最大ACL权限的特征。 (相与 ，都有才有)

调整用户最大权限

```
setfacl -m m:rx   m选项便是调整最大权限 mask
```

mask不仅影响ACL权限还影响所属组的权限

![Linux_acl10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_acl10.png?raw=true)

### 递归的ACL权限

通过加上选项 -R 递归设定文件的 ACL 权限，所有的子目录和子文件也会拥有相同的 ACL 权限。

```
setfacl -m u:用户名:权限 -R 文件名
```

递归设置权限有个缺点就是在我们递给设置完之后，如果文件夹里又加入了新文件，新文件并没有ACL权限,递归只能针对现有的。而解决这个问题就是使用默认的ACL权限。

### 默认的ACL权限

如果给父目录设定了默认的 ACL 权限，那么父目录中所有新建的子文件会继承父目录的 ACL 权限。

```
setfacl -m d:u:用户名:权限 文件名  (defalut)
```

递归是针对的现有的文件，默认是支持未来添加的。

这两个都只能针对父目录设置。



## 文件特殊权限

### SetUID  和 SetGID 和

（1）进程运行时能够访问哪些资源或文件，不取决于进程文件的属主属组，而是取决于运行该命令的用户身份的uid/gid，以该身份获取各种系统资源。

(2)对一个属主为root的可执行文件，如果设置了SUID位，则其他所有普通用户都将可以以root身份运行该文件，获取相应的系统资源。

对文件设置setUID相当于给来操作这个文件的 用户穿了个马甲，在操作这个文件的时候可以改变身份，执行原来身份无法执行的命令。(这类似于一个变身命令,这个变身是临时的只在执行这个文件的时候有效)

(3) 只有可执行的二进制文件 且 命令执行者必须要对给文件有执行权限（即二进制文件的其他所有者拥有X权限）------ 设置了SetUID来有作用。 

setGID 既可以针对二进制文件，也可以针对目录(针对目录的时候必须对目录拥有r和x 权限，且在这个目录下创建的文件，他的文件的所属组不是用户的所属组而是目录的所属组）这个操作用处不大。

`setuid`的作用是让执行该命令的用户以该命令拥有者的权限去执行，比如普通用户执行passwd时会拥有root的权限，这样就可以修改/etc/passwd这个文件了。 (系统中默认的passwd是拥有setUID权限的)

它的标志为：s，会出现在x的地方，例：-rwsr-xr-x  。而`setgid`和它是一样的，即让执行文件的用户以该文件所属组的权限去执行。

![Linux_setuid.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_setuid.png?raw=true)

### Sticky BIT

sticky 只针对目录。 普通用户应该对该目录具有w和x权限。 一般来说一个普通用户对一个目录拥有写权限，那么他可以删除这个文件夹下所有文件，但是如果这个目录拥有了sticky权限，除了root，即使是文件的所有者也无法删除这个目录下的文件。 这个权限的作用是为了防止给一个目录赋予了w权限，任何用户进入了该目录都可以删除这个目录下的文件 (实际应用系统中的 /tmp 目录 ) 

/tmp是系统的临时文件目录，所有的用户在该目录下拥有所有的权限，也就是说在该目录下可以任意创建、修改、删除文件，那如果用户A在该目录下创建了一个文件，用户B将该文件删除了，这种情况我们是不能允许的。为了达到该目的，就出现了stick  bit(粘滞位)的概念。它是针对目录来说的，如果该目录设置了stick  bit(粘滞位)，则该目录下的文件除了该文件的创建者和root用户可以删除和修改/tmp目录下的stuff，别的用户均不能动别人的，这就是粘滞位的作用。

### 设置UID、GID、STICK_BIT

```
SUID：置于 u 的 x 位，原位置有执行权限，就置为 s，没有了为 S .
chmod u+s  xxx # 设置setuid权限
chmod 4551 file // 权限： r-sr-x—x
取消权限
chmod 551 file  (去掉特殊权限位即可)
chmod u-s  xxx  (下面的类同)

SGID：置于 g 的 x 位，原位置有执行权限，就置为 s，没有了为 S .
chmod g+s  xxx # 设置setgid权限
chmod 2551 file // 权限： r-xr-s--x

 
STICKY：粘滞位，置于 o 的 x 位，原位置有执行权限，就置为 t ，否则为T 
chmod o+t  xxx # 设置stick bit权限，针对目录
chmod 1551 file // 权限： r-xr-x--t

注意： 如果这个二进制文件的其他所有者没有X权限设置了setUID等特殊权限会显示为大写的S报错
```

 ![Linux_setuid2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_setuid2.png?raw=true)

其实文件权限用3数字位表示并没有表示完， 还可以在前面加上1位表示特殊权限

![Linux_setuid3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_setuid3.png?raw=true)

注意：这些权限对于系统安全十分的危险， 系统会默认的设置一些，我们一般不对其进行修改和擅自添加(尤其是vim--否则任何用户都可以通过vim修改任何配置文件)。

## 文件 系统属性chattr 权限

### 设定文件系统属性：chattr  

change file attribute

#### 为什么设定文件属性： chattr

在通常情况下，linux下文件具有的属性都是读写和执行，但是这些属性属于高层次的文件属性，它和具体的文件文件系统无关。在文件系统这一层，文件同样也 具有很多属性，chattr和lsattr指令就是设置和查看基于ext2/ext3文件系统的底 层属性。

这些权限对于一些具有特殊要求的文件很有帮助，比如服务器日志或者某个比较 重要的文件。通过chattr命令设置的文件或目录，即使在root权限下也不能直接 删除，只有去除其隐藏权限才能进行操作。chattr命令的作用很大，其中一些功能是由Linux内核版本来支持的，不过现在生产绝大部分跑的linux系统都是2.6以上内核了。通过chattr命令修改属性能够提高系统的安全性，但是它并不适合所有的目录。chattr命令不能保护`/、/dev、/tmp、/var`目录。lsattr命令是显示chattr命令设置的文件属性。



#### chattr:设置文件的底层属性

```
chattr [+-=][选项] 文件或目录名  (注意： 这个权限对root也生效)

option: 

+：增加权限
-：删除权限
=：等于某权限
-a：设定只能想文件中添加数据，而不能删除。
    ---针对文件：只能在文件中增加数据，不能修改和删除 (相当于将文件中的现有数据锁起来)
    ---针对目录：对目录中的文件只能增加文件和修改，不能删除
-i：设定后
    ---针对文件：不能对文件做任何操作，等同于对文件加锁
    ---针对目录：对目录中的文件只能改，但是不能添加和删除
    (i属性是非常好的防止误操作的属性)
-R：递归处理 
-V：显示执行过程
```

注意：设定这些参数，必须在root权限下。

![Linux_chattr.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_chattr.png?raw=true)

实例1：设定i参数，使得在root权限下无法直接删除

```
~$ touch test
~$ sudo chattr +i test
~$ lsattr test 
----i--------e- test
~$ sudo rm test
[sudo] password for hic: 
```

rm: 无法删除"test": 不允许的操作

实例2：设定a参数，使得只能向文件中添加内容

```
~# chattr +a test
~# man lsattr > test 
bash: test: 不允许的操作
```

**lsattr:显示文件的底层属性**

```
语法格式：lsattr [option] [file]
option: 

-a:显示所有文件和目录属性 
-d:仅显示目录属性  若是目录，仅列出本身的属性，而不是子文件的
-R：递归显示

~$ lsattr -a test 
----i--------e- test
```



## 系统命令sudo权限

sudo简介：sudo是linux系统管理指令，是允许系统管理员让普通用户执行一些或者全部的root命令的一个工具，如halt，reboot，su等等。这样不仅减少了root用户的登录 和管理时间，同样也提高了安全性。sudo不是对shell的一个代替，它是面向每个命令的。

①、sudo 的**操作对象只能是系统命令**。

②、把本来由超级用户执行的命令赋予给普通用户执行。

简单来讲就是比如很多只能由超级用户来执行的命令，比如重启，关机等等，有时候不能使用超级用户，那我们该怎么办呢？

　　第一步：那就进行适当的配置，让超级用户赋予普通用户也能执行这些命令的权限

　　第二步：加上 sudo 去执行这些命令。

#### 一、超级用户赋予普通用户执行命令权限，配置 /etc/sudoers 文件

　　我们可以配置 vim /etc/sudoers 文件，或者使用 visudo 命令

![Linux_sudo.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sudo.png?raw=true)

#### 　　二、示例——授权用户可以重启服务器

```
`用户名    ALL=/sbin/shutdown -h now`
```

![Linux_sudo2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sudo2.png?raw=true)

 

#### 　　三、查看可用的sudo 命令

```
`sudo -l`
```

![Linux_sudo3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_sudo3.png?raw=true)

# 文件系统

## 分区

### 为什么要给硬盘分区？

Linux 系统的安装时，我们手动给硬盘划分了4个分区，分为了根目录/，/home 分区,/boot分区，还有/swap交换分区，那么为什么要给硬盘分区呢

**①、易于管理和使用**

　　一个没有分区的硬盘就像一个大柜子，如果我们在柜子里放些衣物和化妆品就会显得很杂乱没有规则更不容易管理和拿取，这时如果我们找来木匠把柜子分割成不同的空间，用来分别储存衣物和化妆品就会让我们很容易管理和拿取衣服和化妆品。同样的一个硬盘如果不分割空间而直接储存各种文件会让我们难以管理和使用。



**②、有利于数据安全**

　　如果漏雨，一个没有分割的大柜子里面的东西肯定全部无法避免被雨水侵蚀的命运，而把柜子分割开来则会因每个空间相对独立，先侵蚀的只会是正好漏雨的空间如果及时采取措施那其它空间里的东西将得以保存。如果中病毒，一个没有分区的硬盘，里面保存的数据肯定全部都会被感染或者损坏，而如果把硬盘分区，然后把文件分开存放，在中毒后我们有充分的时间来采取措施防止病毒和清除病毒，即使需要重做系统也只会丢失系统所在的数据而其它数据将得以保存。



**③、节约寻找文件的时间**

　　在没有分区的硬盘里面找文件就想在一个大柜子里面找衣服一样，总会翻动很多东西才能找到自己想要的。如果我们把硬盘分区，在需要某个文件时可以直接到特定的分区去寻找，这样避免了我们翻找过多的文件。



### Linux系统分区类型

　①、主分区：总共最多只能分 4 个。

　②、扩展分区：**只能有一个**，也算作主分区中的一个，也就是说主分区加上扩展分区最多有 4 个。但是扩展分区不能存储数据和格式化，必须再将扩展分区划分为逻辑分区才能被使用。

　③、逻辑分区：逻辑分区是扩展分区中划分的，如果是 IDE 硬盘，Linux 最多支持59个逻辑分区，如果是 SCSI 硬盘Linux最多支持 11 个逻辑分区。

![Linux_file_partition.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition.png?raw=true)

**注意：如果只有一个主分区，一个扩展分区，扩展分区下有三个逻辑分区。那么主分区的设备文件名为/dev/sda1,扩展分区的设备文件名为 /dev/sda2。而逻辑分区直接是 /dev/sda5，也就是说系统默认的设备文件名从/dev/sda1——/dev/sda4是给主分区和扩展分区命名的，而逻辑分区的设备文件名是从/dev/sda5开始的。**



### Linux 文件系统的格式

  ![Linux_file_partition2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition2.png?raw=true)

 我们只需要知道的是我们现在主要使用的是ext4,更优更强。	   

### 文件系统的常用命令

#### ①、文件系统查看命令：df

```
df 【选项】【挂载点】
查看分区已经用了多大空间还剩多大空间 
```

![Linux_file_partition3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition3.png?raw=true)

#### ②、统计目录或文件大小：du

```
du 【选项】【目录或文件名】
我们一般并不使用这个命令查看文件的大小，因为直接使用ll就可以查看文件的命令了。 但是使用ls 命令查看目录的大小的时候并不是统计的是目录的及子文件的大小。而是子目录一级子文件的文件名所占用的大小 

du命令常用的是统计目录的总大小
du -sh 目录名
```

![Linux_file_partition4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition4.png?raw=true)

　df 统计的/home分区使用大小为 760M，而du 命令统计的/home分区使用大小为 751M，这是为什么呢？

　①、df 命令是从文件系统考虑的，不管要考虑文件占用的空间，还要统计被命令或程序占用的空间（最常见的就是文件已经删除，但是程序并没有释放空间）。

  ②、du 命令是面向文件的，只会计算文件或目录占用的空间。

也就是说，**实际系统的剩余空间大小是以 df 命令统计为准**的。这也告诉我们 Linux 虽然系统很稳定，但是对于经常高负载的服务器，还是应该定期重启，维护服务器的高效运转。



#### ③、文件系统修复命令：fsck

```
fsck 【选项】分区设备文件名
```

![Linux_file_partition5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition5.png?raw=true)

系统在启动时会自动进行文件系统修复，这里我们最好不要手动执行文件系统修复命令，很容易造成意外的错误。

#### ④、显示磁盘状态命令：dumpe2fs

```
dumpe2fs 分区设备文件名 (不是挂载目录)
```

![Linux_file_partition6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition6.png?raw=true)

### 挂载命令

挂载： Linux中每一个设备都一个设备文件名，和自己在文件系统中有一个挂载点(类比widow中的盘符),我们需要将设备文件名和挂载点联系起来，才能通过访问挂载点来访问硬件设备。把设备名与挂载点连接起来的过程我们叫做挂载。

#### ①、查询系统中已经挂载的设备：mount 

```
mount 【-l】
```

选项：-l 会显示卷标名称，也就是设备文件名的别名

![Linux_file_partition7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition7.png?raw=true)

#### ②、依据配置文件 /etc/fstab 的内容自动挂载：mount -a

 /etc/fstab 的文件内容：

![Linux_file_partition8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition8.png?raw=true)

注意：不要将光盘或者U盘的挂载配置写在 /etc/fstab 文件中，因为系统启动的时候会自动挂载这个文件中配置好的内容，你不可能光盘或者U盘一直都和系统连着，如果没有，则挂载出错，系统有可能奔溃。

#### ③、挂载命令格式

![Linux_file_partition9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition9.png?raw=true)

注意：

 1 ：iso9660 是光盘默认的文件系统， 如果是widows默认是vfat（fast32） /fat（fast16） 。 

 2 ： 常用的特殊选项是remount,如果对文件系统的参数做了修改，但是不想重新启动，就可以使用remount重新挂载文件系统。 还有就是exec ，例子如下

例：我们重新挂载 /home 分区，加上-o noexec，也就是说使得 /home 分区的可执行文件不能执行。然后创建一个脚本，然后看其是否能执行。

第一步：在/home目录下创建脚本hello.sh，简单的输出 hello world，我们给其赋予可执行权限，然后执行此脚本：

![Linux_file_partition10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition10.png?raw=true)

第二步：重新挂载 /home分区，加上 -o noexec,在执行此脚本发现权限不够了，使用的超级用户root

![Linux_file_partition11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition11.png?raw=true)

第三步：将/home分区还原，然后在执行此脚本，发现又可以了

　　![img](https://images2017.cnblogs.com/blog/1120165/201711/1120165-20171111100621184-846281062.png)

### 挂载光盘与U盘

#### ①、挂载光盘

一、建立挂载点

```
`mkdir /mnt/cdrom`
```

二、将光盘放入光驱，对于虚拟机我们执行以下操作即可：

![Linux_file_partition12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition12.png?raw=true)

三、挂载光盘

```
`mount -t iso9660 /dev/cdrom /mnt/cdrom`
```

　或执行下面命令

```
`mount /dev/sr0 /mnt/cdrom`
```

为什么有两个设备文件名/dev/cdrom 和/dev/sr0 呢？

![Linux_file_partition13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition13.png?raw=true)

可以看到这是一个软链接，光盘的设备文件名是系统自动检测的，以及固定了，只需要记住就好了。



#### ②、挂载 U 盘

第一步：让虚拟机识别到 U 盘

注意：我们是在真实物理机上安装的虚拟Linux系统，为了让虚拟机能检测到U盘，需要要鼠标点进到虚拟机中，不能用远程连接工具，来能是虚拟机中的Linux检测到u盘。

第二步：执行 fdisk -l 查看U 盘的设备文件名

第三步：挂载 U 盘

```
mount -t vfat /dev/sdb1 /mnt/usb
```

#### ③、卸载命令

```
umount 设备文件名或者挂载点
注意： umount命令的使用不能再光盘中
```

![Linux_file_partition14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition14.png?raw=true)　

### 支持 NTFS 文件系统

(常用于挂载移动硬盘)

 Linux 默认是不支持 NTFS 文件系统的，所以早期的苹果笔记本如果插上移动硬盘，是不能对硬盘的内容进行修改，只能读取的。那么如何解决 Linux 系统不支持 NTFS 文件系统呢？

第一种方法是重新编译内核，这种方法要求较高，这里我们就不推荐。

第二种方法是安装 NTFS-3G 插件 

(下载地址：http://www.tuxera.com/community/ntfs-3g-download/)

![Linux_file_partition15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_file_partition15.png?raw=true)　



## 手工分区

### 添加新硬盘

在虚拟机上进行添加，注意要先关闭虚拟机。在进行虚拟机安装的时候，我们给 Linux 系统分配了一块20GB的硬盘，现在添加一块 10GB的

![Linux_manual_partition.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition.png?raw=true)　

![Linux_manual_partition2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition2.png?raw=true)　

### 使用 fdisk 命令分区

已经添加了硬盘，并且硬盘已经被系统识别了，就可以对其进行分区

```
fdish /dev/sdb
```

![Linux_manual_partition3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition3.png?raw=true)

**第一步：按 n 新建分区**

![Linux_manual_partition4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition5.png?raw=true)

**第二步： 按 p 创建主分区**

![Linux_manual_partition5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition5.png?raw=true)

**第三步：主分区创建完成，这里接着创建扩展分区，按e创建扩展分区**

![Linux_manual_partition6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition6.png?raw=true)

这时候，如果在按n，则会出现创建逻辑分区的选项：

![Linux_manual_partition7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition7.png?raw=true)

分区完成之后，我们要输入 w，保存退出

**第四步：要想让分区有效，可以重启，或者执行下面命令**

```
partprobe
```

下面的警告信息不管用。

![Linux_manual_partition8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition8.png?raw=true)　

**第五步：查看分区信息 fdisk -l**

![Linux_manual_partition9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition9.png?raw=true)　

**第六步：格式化分区（注意不能格式化扩展分区）**

```
mkfs -t ext4 /dev/sdb1
格式化分区
```

　![Linux_manual_partition10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition10.png?raw=true)　

**第七步：建立挂载点，并挂载分区**

```
mkdir /disk1
mount /dev/sdb1 /disk1
```

　![Linux_manual_partition11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition11.png?raw=true)　　

### 分区自动挂载

将硬盘分区，并进行了挂载，但是我们是命令挂载的，重启之后就会失效，又需要我们重新挂载，那么怎么能实现系统重启自动挂载呢？ 这时候就要使用到`/etc/fstab文件`

①、/etc/fstab 文件

![Linux_manual_partition12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Linux_manual_partition12.png?raw=true)

注意： 

1 ： 第一个字段可以使用设备文件名，也可以使用UUID(建议使用这个,唯一识别，设备文件名可能会有变化)。 

2： 可以使用`dumpe2fs  -h  设备文件名` 查看对应的UUID

3：我们自己添加的 第六个字段 不能是1 ，不能比root 根目录权限高，只能比他小，我们要最先检测根目录。

