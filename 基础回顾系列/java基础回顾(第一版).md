# java 基础回顾 (第一版)



# 一： 概述

1: 软件是什么？

 软件， 代码 是操作硬件(或者说是操作系统)去做一些动作的**指令**。

2 硬件

![JAVA_REVIEW1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW1.png?raw=true)

(总线搭建在主板上，主板是一个连接计算机各个部分的电路板)

![JAVA_REVIEW2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW2.png?raw=true)

计算机的最核心的部分就是cpu 。 

他分为两部分：

运算器：完成数值运算(+-*/)和逻辑运算。 他对应着编程语言中的各种运算规则。 计算机无时不刻都在进行运算。

控制器：用于控制和协调其他组件完成动作 (命令中心)

![JAVA_REVIEW3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW3.png?raw=true)

(这里的GHz 和内存大小的换算单位是一样的， 1G=1024M 1M=1024K  1K=1024)



3 内存

数据在被cpu执行前必须先被加载在内存中。  (与cpu 交互的只能是内存 ----原因： 读写速度)

我们写的代码也是需要先被加载在内存中。然后被cpu解析执行。



4 万维网

![JAVA_REVIEW4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW4.png?raw=true)

5: 发展与提升

![JAVA_REVIEW5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW5.png?raw=true)

![JAVA_REVIEW6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW6.png?raw=true)

知识 变成 技能 (需要长时间的磨练)

# 二：基础

1： java的跨平台性

我们在widows 上写的java程序可以拿到Linux系统中执行。 

正常的应该程序都是运行在操作系统之上。 不同操作系统的指令集不同需要重新编译。 但是java可以一次编译，不同系统都可以运行， 这是因为java 在操作系统之上封装了一层，就是jvm。 java程序不是直接运行在操作系统之上，而是运行在jvm之上

![JAVA_REVIEW7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW7.png?raw=true)

jvm运行在不同的操作系统之上。(不同的系统有不同的jvm)

![JAVA_REVIEW8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW8.png?raw=true)

2: jdk ,jre,jvm

![JAVA_REVIEW9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW9.png?raw=true)



## 3: 变量的数据类型

![JAVA_REVIEW10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW10.png?raw=true)

![JAVA_REVIEW11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW11.png?raw=true)

声明Long类型的数值变量必须以"L" 结尾。

![JAVA_REVIEW12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW12.png?raw=true)

float 单精度，只能精确到7 位

float 4个字节， int 也是4个字节，但是float表示的数的范围却比int 大：

原因： 这两个的表示数的方法不一样，float 自己表示数的方式是一些位来表示具体的数， 另外的一些位则来表示

10 的多少次幂 。一下将表示的数的范围扩大了。 但是这样也导致这样表示的数精确度不高。

float类型的定义要加上“f” ,因为一般的小数默认的被定义为double . 如果不加上f标识，相当于将double强转为float这样会导致精度丢失。



4： char

![JAVA_REVIEW13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW13.png?raw=true)

## 5： 基础类型之间的运算的规则

自动类型的提升 (小转大----补位)

强制类型的转换 (大转小----截断)

(不同类型之间存储的位数不一样， 为了方便运算变将类型变成一样的来进行计算--》补位 或截断)

![JAVA_REVIEW14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW14.png?raw=true)

![JAVA_REVIEW15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW15.png?raw=true)

![JAVA_REVIEW16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW16.png?raw=true)

![JAVA_REVIEW17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW17.png?raw=true)

(int 能表示绝大部分数， (如果可以使用short接收)避免接收过小而编译正常，运行时异常，所以强制规范)

6: java中对数字常量，字符常量

![JAVA_REVIEW18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW18.png?raw=true)

![JAVA_REVIEW19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW19.png?raw=true)

char 型变量+ 是做运算， 字符串+ 是做连接，字符串与任何基本类型运算其结果都是字符串。

7： 进制间的转换

![JAVA_REVIEW20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW20.png?raw=true)

(一般二进制给出的都是补码)

![JAVA_REVIEW21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW21.png?raw=true)

![JAVA_REVIEW22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW22.png?raw=true)

![JAVA_REVIEW23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW23.png?raw=true)

![JAVA_REVIEW24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW24.png?raw=true)

![JAVA_REVIEW25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW25.png?raw=true)

(实际开发中调用api)

![JAVA_REVIEW26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW26.png?raw=true)



8: 运算

取余：

![JAVA_REVIEW27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW27.png?raw=true)

++

![JAVA_REVIEW28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW28.png?raw=true)

![JAVA_REVIEW29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW29.png?raw=true)

注意： ++ ，+= ，+% 等不会改变变量的基础类型，但是

```java
short n=10 

n=n+2 
//这样会改变，因为 n是short ,2默认是int
```



9: 逻辑操作与短路操作的区别

![JAVA_REVIEW30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW30.png?raw=true)

10： 位移运算

![JAVA_REVIEW31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW31.png?raw=true)

位移是存在限制的： 

![JAVA_REVIEW32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW32.png?raw=true)

(如果左右过多位数导致最高位发生改变，那么改数整体的值也会发生改变)

实用例子：

![JAVA_REVIEW33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW33.png?raw=true)

![JAVA_REVIEW34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW34.png?raw=true)

## 11：三元运算符：

![JAVA_REVIEW35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW35.png?raw=true)

 这里需要注意： **表达式1和2 需要统一成一个类型**

![JAVA_REVIEW46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW46.png?raw=true)



12: 随机数

![JAVA_REVIEW36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW36.png?raw=true)

13： switch case

![JAVA_REVIEW37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW37.png?raw=true)



14: 数组的初始化

![JAVA_REVIEW38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW38.png?raw=true)


数组一旦初始化，长度就确定了。

二维数组可以看成： 一个竖向的一维数组他的没个元素同样是一个一维数组 (极有可能在内存中也是这么表示的) 



15： 引用变量的赋值

引用变量的赋值不仅仅是地址的赋值， 还会判断类型是否一致

```
int[] ar1=XXX
int[][] ar2=xxx;
ar1=ar2 X 
因为ar2 和ar1 类型不同，ar2的地址中会带着[[ ,来表示这是一个二维数组
```

变量在内存中的默认赋值


![JAVA_REVIEW39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW39.png?raw=true)

16: 可变个数形参

定义方式： 

```
 test1(String...str){}
```

但是  test1(String...str) 在编译器中被理解为等价于test1(String[ ] str) 

所以

```
test1(String...str){}
test1(String[] str){}
编译器会认为两者等价，不能构成重载。 且两者的使用方法一致。  
```

![JAVA_REVIEW40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW40.png?raw=true)

适用场景： sql查询时，条件个数是可变的。



## 17: 变量的赋值

   （下面两条是基础铁则，其他的都可以通过这个类推）

​      基础数据类型的变量赋值，赋值的变量所保存的基础数据值

​     引用类型的变量赋值，赋的值是引用变量的地址。  

​     方法形参的值传递： **值传递** 就是实参给形参赋值  (通用上面的规则)  

就相当于基础数据类型的赋值只是将实参复制了一份，并没有真正的操作实际的对象，操作的只是他的副本。

但是需要注意： 形参也会在变量中入栈。



![JAVA_REVIEW50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW50.png?raw=true)

赋值的顺序：

![JAVA_REVIEW54.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW54.png?raw=true)



18: 构造器

无论何时都会存在构造器。没有显示写构造器的时候，会有一个默认的空参构造器，但是如果显示的声明了一个构造器，默认的空参构造器就没有了。



19: UML

![JAVA_REVIEW41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW41.png?raw=true)



20：继承性

一旦子类继承父类后就获取了父类声明的所有的结构，属性，方法

即使是私有的属性和方法也是被继承到了的，只是由于封装性的影响，不可以让子类直接的调用private。(因为不能是因为有了继承性而打破封装性)

类似没有显示声明构造器，就会有一个默认的无参构造器。 在继承的时候，没有显示的声明父类，就会默认的继承Object类，所有的java类，除Object以为都间接继承Object类

在**继承性中属性是不会被覆盖**的。



## 21 : 方法重写

方法重写(覆盖)中，子类重写的方法的权限修饰符要不小于父类的权限修饰符

理解： 方法的重写实质上就是子类的方法覆盖父类的方法。 在重写发生的继承中，子类中会存在两个同名，同形参的方法。 子类重写方法 要优于父类的方法被发现。 如果这时候父类的权限大于子类，则会导致子类的方法无法覆盖掉父类的。所以子类的重写方法的权限修饰符需要大于等于父类方法的权限修饰符。

特殊情况： 

​               **子类不能重写父类中声明为private的方法** 私有的方法对于子类来说不可见(因为如果可以覆盖，则破坏了封装性)
​               重写的方法一般是**都申明为非static**， static的方法不存在重写， 是因为静态的方法不能被覆盖，他是随着类的    加载而加载

重写的提现： 如果是重写，则方法的调用是子类的实现 

子类重写方法的**返回值**(或抛出的异常)可以是父类方法的返回值(或抛出的异常)的一个子类(遵从多态性)，这里针对是类(引用数据类型)，如果是基础数据类型，则必须一致



22：权限修饰符

![JAVA_REVIEW42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW42.png?raw=true)



23： 多态性 (对象的多态性)

**方法**---多态编译看父类 ，执行只看子类 (方法的重写)。  因为编译的时候无法确认具体是传的哪个子类，所以这里调用的方法只能是父类声明的方法。

**属性----不存在多态性， 属性编译和运行都看父类。**

从内存上来理解：堆中父类，子类的属性都存在。 但是子类的方法覆盖了父类的方法（重写）。 但是在访问属性值的时候p.XX 。这时候持有这父类的引用，所以找到的是父类的属性值。

多态性是运行时行为。

24: equals

![JAVA_REVIEW43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW43.png?raw=true)

![JAVA_REVIEW44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW44.png?raw=true)

25： 包装类



![JAVA_REVIEW45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW45.png?raw=true)

面试题：

![JAVA_REVIEW47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW47.png?raw=true)

![JAVA_REVIEW48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW48.png?raw=true)

128 false 的原因。 在自动装箱的过程汇总，-128 到127 自动装箱的机率要高一些，所以java做了优化，在Integer中定义了一个IntegerCache结构静态的生成了这些数的缓存。 所以128 是新new的一个对象，地址是不同的。 1 则是取的缓存



26：static

类中某些特定的数据我们希望他在**内存空间中只有一份**。 被类的**所有对象所共享**，单独存放。  随着类的加载(早于对象的创建)而加载。

如果类中存在所有对象都具备的相同值的属性，可以考虑申明为static。

27 代码块

代码块(**初始化**块 )

![JAVA_REVIEW49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW49.png?raw=true)

应用：

![JAVA_REVIEW53.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW53.png?raw=true)

**代码块的初始化，先与构造器的初始化**。

代码块的执行先于构造器。



28: final (常量)

![JAVA_REVIEW55.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW55.png?raw=true)

29：抽象abstract

abstract 与重写息息相关。

 abstract 不能用来修饰私有方法。因为private修饰的方法不能被重写。如果可以修饰private方法，那么就子类都成为了抽象类。没有意义。

abstract 不能修饰静态，于私有类似。 父子类的同名静态方法，不认为是重写。

abstract 不能修饰已经被final 修饰的方法。  修饰为final的不能被继承，重写。 但是申明为abstract就是希望被继承。两者有冲突。

抽象类中可以定义构造器，用于子类创建对象。



30: 接口

![JAVA_REVIEW56.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW56.png?raw=true)

![JAVA_REVIEW57.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW57.png?raw=true)

![JAVA_REVIEW58.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW58.png?raw=true)

java8 ： 接口改进

![JAVA_REVIEW59.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW59.png?raw=true)

![JAVA_REVIEW60.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW60.png?raw=true)

31： 内部类

![JAVA_REVIEW61.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW61.png?raw=true)

![JAVA_REVIEW62.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW62.png?raw=true)

# 三：高级

## 1：多线程

内存结构上：

每个线程各自有一份独立的栈和程序计数器。 他们共享进程的方法区和堆。

所以线程间的通信在进程中进行比较方便，因为在进程中是他们都可以看见的。

(这个整体可以类比租房，各自有各自的卧室，但是你们共享客厅)

![JAVA_REVIEW63.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW63.png?raw=true)

![JAVA_REVIEW64.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW64.png?raw=true)

![JAVA_REVIEW65.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW65.png?raw=true)

注意： 

什么是共享数据(需要加锁)

同步线程安全的必要条件就是： 所有人都共用一个锁。

![JAVA_REVIEW66.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW66.png?raw=true)

Lock：

![JAVA_REVIEW67.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW67.png?raw=true)

线程通信， 线程1,2 交替执行。

![JAVA_REVIEW68.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW68.png?raw=true)

![JAVA_REVIEW69.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW69.png?raw=true)

![JAVA_REVIEW70.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW70.png?raw=true)



2: string 

![JAVA_REVIEW71.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW71.png?raw=true)

![JAVA_REVIEW72.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW72.png?raw=true)

![JAVA_REVIEW73.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW73.png?raw=true)

![JAVA_REVIEW74.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW74.png?raw=true)

![JAVA_REVIEW78.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW78.png?raw=true)

![JAVA_REVIEW75.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW75.png?raw=true)

![JAVA_REVIEW76.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW76.png?raw=true)

![JAVA_REVIEW77.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW77.png?raw=true)

3：日期

![JAVA_REVIEW79.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW79.png?raw=true)

![JAVA_REVIEW80.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW80.png?raw=true)

4：枚举 (定义一组变量)

使用enum关键了枚举类默认继承于java.lang.ENUM类

![JAVA_REVIEW81.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW81.png?raw=true)

## 5: 注解(代码里的特殊标记)

![JAVA_REVIEW82.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW82.png?raw=true)

**注解最重要的功能：跟踪代码依赖性，实现替代配置文件功能**

![JAVA_REVIEW83.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW83.png?raw=true)

(注解需要与反射结合才能起作用。 是通过放射读取注解，和注解的值，才知道用户使用注解的目的是什么(信息处理流程))

![JAVA_REVIEW84.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW84.png?raw=true)

![JAVA_REVIEW85.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW85.png?raw=true)

6：集合

![JAVA_REVIEW86.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW86.png?raw=true)

![JAVA_REVIEW87.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW87.png?raw=true)

![JAVA_REVIEW88.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW88.png?raw=true)

![JAVA_REVIEW89.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW89.png?raw=true)

List 的有序和set的无序的理解：

list和set的底层都是基于数组，这里的有序和无序指的是集合中的元素在数组中存放的位置是否是“有序”。 list中元素的添加在数组中是按照下标依次存储。 set中是根据hash值随机存储。


![JAVA_REVIEW90.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW90.png?raw=true)



![JAVA_REVIEW91.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW91.png?raw=true)

![JAVA_REVIEW92.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW92.png?raw=true)

collections:

![JAVA_REVIEW96.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW96.png?raw=true)



7: 泛型

静态(泛型方法除外) 与异常中不能使用泛型 

![JAVA_REVIEW93.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW93.png?raw=true)

![JAVA_REVIEW94.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW94.png?raw=true)

## 8:IO

在整个IO中我们都是站在程序的角度上说输入和输出的

![JAVA_REVIEW95.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW95.png?raw=true)



![JAVA_REVIEW97.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW97.png?raw=true)

![JAVA_REVIEW98.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW98.png?raw=true)

![JAVA_REVIEW99.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW99.png?raw=true)

![JAVA_REVIEW100.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW100.png?raw=true)

9: 序列化

![JAVA_REVIEWAD1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD1.png?raw=true)

![JAVA_REVIEWAD2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD2.png?raw=true)

## 10 ： 反射

在**运行时**获取任何类的内部信息。

![JAVA_REVIEWAD3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD3.png?raw=true)

![JAVA_REVIEWAD4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD4.png?raw=true)

![JAVA_REVIEWAD5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD5.png?raw=true)

![JAVA_REVIEWAD6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD6.png?raw=true)

![JAVA_REVIEWAD7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD7.png?raw=true)

![JAVA_REVIEWAD8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD8.png?raw=true)

![JAVA_REVIEWAD9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD9.png?raw=true)

![JAVA_REVIEWAD10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD10.png?raw=true)

![JAVA_REVIEWAD11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD11.png?raw=true)

![JAVA_REVIEWAD12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD12.png?raw=true)

![JAVA_REVIEWAD13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD13.png?raw=true)

![JAVA_REVIEWAD14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD14.png?raw=true)

![JAVA_REVIEWAD15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD15.png?raw=true)

![JAVA_REVIEWAD16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD16.png?raw=true)



# 四： java8 新特性

1： lamda

lamda 的本质就是接口（函数式接口）的实例。 (**接口的具体实现**) ，对原来的匿名接口实现类的一系列**简化**。

![JAVA_REVIEWAD18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD18.png?raw=true)

![JAVA_REVIEWAD19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD19.png?raw=true)

2： 方法引用

对lamda表达式的进一步简化，他的本质就是lamda表达式

![JAVA_REVIEWAD20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD20.png?raw=true)

3： StreamAPI

集合存储数据，Stream计算数据。


![JAVA_REVIEWAD21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD21.png?raw=true)

stream是延迟操作： 中间操作类似于书写sql语句(并不会去执行)，执行了终止操作才是真正的执行了sql语句。一旦执行了终止操作，stream就不可用了。(类比数据库操作中的关闭连接)

![JAVA_REVIEWAD22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD22.png?raw=true)

![JAVA_REVIEWAD23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD23.png?raw=true)

中间操作

![JAVA_REVIEWAD24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD24.png?raw=true)

映射： 类似于数学函数，将集合中的元素通过操作转换成另外的表现形式。 x->f(x) ，可以理解生成一个新的流

![JAVA_REVIEWAD25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD25.png?raw=true)

终止操作：

![JAVA_REVIEWAD26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD26.png?raw=true)

![JAVA_REVIEWAD27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD27.png?raw=true)

![JAVA_REVIEWAD28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD28.png?raw=true)

**归约**

![JAVA_REVIEWAD29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD29.png?raw=true)

![JAVA_REVIEWAD30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD30.png?raw=true)

4: Optional 

他本身就是一个**容器**。我们将对象放入这个容器中，然后调用容器的方法来尽可能的避免空指针异常。

Optional  如何避免空指针异常：

```
 Optional类：为了在程序中避免出现空指针异常而创建的。
 常用的方法：ofNullable(T t)
            orElse(T t)
```

![JAVA_REVIEWAD31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD31.png?raw=true)






# END:  编程思考

1： 在我们编码过程中。解决问题的思路： 满足的条件， 解决的范围。

2： 在我们解决问题的过程中，如果前路不同，需要换个思路

eg:

![JAVA_REVIEW51.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW51.png?raw=true)

![JAVA_REVIEW52.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEW52.png?raw=true)

3： 测试习惯

我们编程的过程中，尽量写好一小部分就开始测试。这样避免因为后面代码写多了不好查找。

4: 源码阅读方法

第一步: 看构造器 。 确定其数据结构和其构造算法的思想

第二步： 假设情景代入方法。

4： 学习本质

在学习的过程中，我们需要反复的掌握本质。

![JAVA_REVIEWAD17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD17.png?raw=true)

5 ： 在我们总结公式的时候

在不知道规律的时候，使用举例法。 一般举3条例子， 从中总结出规律。





![JAVA_REVIEWAD32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD32.png?raw=true)
![JAVA_REVIEWAD33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD33.png?raw=true)
![JAVA_REVIEWAD34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD34.png?raw=true)
![JAVA_REVIEWAD35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD35.png?raw=true)


![JAVA_REVIEWAD36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD36.png?raw=true)
![JAVA_REVIEWAD37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD37.png?raw=true)
![JAVA_REVIEWAD38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD38.png?raw=true)
![JAVA_REVIEWAD39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD39.png?raw=true)
![JAVA_REVIEWAD40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD40.png?raw=true)
![JAVA_REVIEWAD41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD41.png?raw=true)
![JAVA_REVIEWAD42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD42.png?raw=true)
![JAVA_REVIEWAD43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD43.png?raw=true)
![JAVA_REVIEWAD44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD44.png?raw=true)
![JAVA_REVIEWAD45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD45.png?raw=true)
![JAVA_REVIEWAD46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD46.png?raw=true)
![JAVA_REVIEWAD47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD47.png?raw=true)
![JAVA_REVIEWAD48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD48.png?raw=true)
![JAVA_REVIEWAD49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD49.png?raw=true)
![JAVA_REVIEWAD50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REVIEWAD50.png?raw=true)




