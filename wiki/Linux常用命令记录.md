

# Linux常用命令小记录

------

查看防火墙状态：

```
 service iptables status
```



vim 中复制一行，粘贴到下一行 (需要命令模式下执行)

```
yy           # 复制当前行
p            # 粘贴到粘贴到当前光标所在行下。
```



执行历史命令

```
!!                # 重复执行上一条命令
! 字符串           # 重复执行以字符串开始的历史命令
```



文件重命名

```
例子：将目录A重命名为B

mv A B

例子：将/a目录移动到/b下，并重命名为c

mv /a /b/c
```



【vim】撤销和恢复撤销快捷键

```
u是撤销你刚才做的动作

ctrl+r 是恢复你刚才撤销的动作
```



linux下怎么把一个文件复制到另一个文件夹

```
cp ./zhaoTestCalculate.sh  /home/priceCalculate/zhaoTestCalculate.sh
    源文件                   目标目录，需要加上文件名
```



Linux 下查看进程的命令：

```
netstat -nlp |grep 8089
```



Linux 常用的文件内容搜索命令

vi命令下的查找和替换

```
/pattern<Enter> ：向下查找pattern匹配字符串

?pattern<Enter>：向上查找pattern匹配字符串
使用了查找命令之后，使用如下两个键快速查找：
n：按照同一方向继续查找
N：按照反方向查找
pattern是需要匹配的字符串，例如：
/name<Enter>      #查找name
/name<Enter>    #查找name单词（注意前后的空格）
除此之外，pattern还可以使用一些特殊字符，包括（/、^、$、*、.），其中前三个这两个是vi与vim通用的，“/”为转义字符。
/^name<Enter>    #查找以name开始的行
/name$<Enter>    #查找以name结束的行
//^name<Enter>    #查找^name字符串
```

