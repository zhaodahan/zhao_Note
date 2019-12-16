#                                             深入 JVM

# 什么是JVM

jvm--即使java的虚拟机。

什么是虚拟机？ **通过软件模拟的一套完整的硬件系统，运行在一个隔离的环境中**。（如 常用的VMWare）

常用的虚拟机与JVM的区别？

```
 VMWare 用软件模拟的都是现实中能找到对应存在的如：cpu，硬盘，内存等，运行的指令集是CPU指令集。	

JVM模拟的是现实中找不到的，并没有一台真实的计算机是能运行java字节码的。JVM只是单纯的做一个软件设计来**模拟硬件行为**。 比如jvm运行的指令集是java的字节码指令集和我们平常使用的CPU指令集是不同的。

此外在正常的CPU中，一般会与若干个寄存器。在jvm中为了设计的精简，除了pc寄存器外，其余的寄存器都做了裁剪。因为寄存器的主要功能是加快数据的访问速度。在jvm中纯粹是用软件模拟，使用寄存器后并不会有本质的提升。并且会让jvm的设计和实现变得繁琐。

总结： jvm是被定制的，现实中不存在的这样一台计算机。
```



## 规范

![JAVA_JVM.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM.png?raw=true)

![JAVA_JVM2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM2.png?raw=true)

java规范定义了什么是java语言，jvm规范就定义了什么是jvm虚拟机。

java语言与java虚拟机是相对独立，只要满足java 虚拟机规范，任何语言(包括非java 编程语言，且不符合java语言规范 ) 都可以在JVM中编译和运行。

JVM 规范主要定义了JVM的内部实现。比如class文件的文件结构和JVM的指令集。

![JAVA_JVM3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM3.png?raw=true)

![JAVA_JVM4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM4.png?raw=true)

![JAVA_JVM5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM5.png?raw=true)



![JAVA_JVM6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM6.png?raw=true)

## 练习

1. 有关补码，简要阐述补码的好处。并计算给出 -99, -105, 205 整数的补码
    答：简述补码的好处：
    在人们的计算概念中零是没有正负之分的，统一0的处理
    统一处理加减法，无需增加减法器操作
    正数二进制的补码等于它本身，负数的二进制补码等于取反+1 (符号位不处理)
    -99：
    原码：  1100011
    反码  ：10011100
    补码  ：10011101
    其它的我直接给出补码了：
    -105：10010111
    -205：00000000 11001101

2. 有关浮点数，根据IEEE745，计算11000001000100000000000000000000的单精度浮点的值，并给出计算过程。
    1 符号位      10000010 值是3       00100000000000000000000  值是1.001 
    = -1 * (2^3)*(2^0 + 2^-3)
    = -8*(1+8/1)
    = -8 -1
    = -9

3.写一个Java程序，将100.2转成IEEE745 二进制表示 ，给出程序和结果。

结果：01000010110010000110011001100110

```java
public static void main(String[] args) {
		String value=convert(100.2f);
		System.out.println(value);
	}

public static String convert(float num) {
	int intVal = Float.floatToIntBits(num);
	return intVal > 0 ? "0" + Integer.toBinaryString(intVal) : Integer
			.toBinaryString(intVal);
}
```


## 总结

什么是JVM , hotspot 只是一个JVM的实现。 真正的JVM只是一个规范，规定了字节码要怎么执行。 任何实现了JVM规范的实现都是JVM。 “没有规矩不成方圆， 有了规矩都是方圆”

# JVM的运行机制



## JVM的启动流程

![JAVA_JVM7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM7.png?raw=true)

## JVM的基本结构

![JAVA_JVM8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM8.png?raw=true)

方法区一般保存了和类相关的一些源信息。

![JAVA_JVM9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM9.png?raw=true)

堆栈：   堆是全局共享，而栈是线程私有的。

![JAVA_JVM10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM10.png?raw=true)

![JAVA_JVM11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM11.png?raw=true)

![JAVA_JVM12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM12.png?raw=true)

![JAVA_JVM13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM13.png?raw=true)

![JAVA_JVM14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM14.png?raw=true)



## 内存模型

java 中每一个线程都有一个工作内存(私有)和主内存(共享)。 工作内存和主存之间存在一个同步关系。 

使用工作内存是为了效率。

![JAVA_JVM15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM15.png?raw=true)

线程总是在字节的本地内存即工作内存中存储共享变量的副本，	而共享变量的原本是存储在主内存中，这两者之间存在着同步，那一定存在着延时问题。	 (如果想在一个线程中修改了一个**变量**后再主内存和其他线程中立即可见，就需要使用Volatile 关键字修饰)。	

![JAVA_JVM16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM16.png?raw=true)



## 编译和解释运行

在编译过程中为了性能的优化可能会导致指令重排。

![JAVA_JVM17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM17.png?raw=true)

解释执行是解释一句，执行一句。 编译执行是线编译完所有后再一次执行完，性能更高是解释执行的十倍以上。



## 练习

1. 写一个程序，让程序在运行之后，最终抛出由于Perm区溢出引起的OOM，给出运行的jdk版本，程序源码，运行参数，以及系统溢出后的截图、程序所依赖的jar包说明，并说明你的基本思路

思路：

要求perm区溢出。可以设置一个较小的MaxPermSize，但是必须要让jvm起来。
然后 载入大量类 即可。不一定要动态生成类。找一个大点的jar包，把类加载一下就可以了

答：

```java
/**
 * jdk基于版本6 
 * 想要perm抛出Oom，首先要知道oom存放什么数据： 类型的常量池, 字段、方法信息 ,方法字节码
 * 由于Java想要动态创建字段、class信息需要引用到第三方Jar包。所以这个地方我利用无限创建常量池来使得抛出    perm gen oom jvm
 * 运行参数：-XX:MaxPerSize=8M 程序只依懒jvm基本的jar包
 * 
 */
public class PermOOM {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		while (true) {
			list.add(UUID.randomUUID().toString().intern());
		}
	}
}
系统溢出后打印的异常栈：
Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
	at java.lang.String.intern(Native Method)
	at test.classloader.PermOOM.main(PermOOM.java:20)
```

基本思路：
首先要知道oom存放什么数据： 类型的常量池, 字段、方法信息 ,方法字节码，
所以这个地方我利用无限创建常量池来使得抛出perm 空间填满，从而抛出perm区的Oom

2.你能想到有什么办法，可以让一个程序的函数调用层次变的更深。比如，你在一个递归调用中，发生了stack的溢出，你可以做哪些方面的尝试，使系统尽量不溢出？阐述你的观点和原因。

思路：

需要让函数调用层次更深，可以从2个方面回答，第一增大栈空间，也就是设置xss。
第二，可以减小局部变量表，比如 少用double，long，减少参数个数，局部变量在使用的时候，注意作用域。
在作用域开外的，局部变量，是可以被重用的，以此减少局部变量表的大小。

答：首先了解到线程在调用每个方法的时候，都会创建相应的栈，在退出方法的时候移出栈桢，并且栈是私用的，也需要占用空间，所以让一个程序的函数调用层次变的更深
​    减少栈贞的空间很必要。或者增大线程的线的大小。
 通过volatile增加调用层次深度。线程会对一个**没有volatile的变量进行临时存储**，这就导致线程栈的空间增大，如果对一个变量增加volatile修饰，可以适当增加深度，详情看实验：

```java
 /**
 * jdk6 启动参数是默认参数
 */
public class OverflowTest {
	private volatile int i=0;
	private volatile int b=0;
	private volatile int c=0;
	
//	private  int i=0;
//	private  int b=0;
//	private  int c=0;
	
	public static void main(String[] args) {
		OverflowTest o=new OverflowTest();
		try {
			o.deepTest();
		} catch (Throwable e) {
			System.out.println("over flow deep:"+o.i);
			e.printStackTrace();
		}
	}
	private void deepTest() {
		++i;
		++b;
		++c;
		deepTest();
	}
}
在上面代码运行两次：9800（函数调用层次）上下一百范围内浮动，如果将i,b,c用volatile修饰，函数调用层次在11344左右浮动。
所以想到的方法是：减少方法栈占用空间，或者增加线程栈的空间。
```
# JVM 常用的配置参数

## Trace 跟踪参数

主要是对GC的状态进行跟踪。GC回收的是堆的空间。

![JAVA_JVM18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM18.png?raw=true)

![JAVA_JVM19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM19.png?raw=true)

![JAVA_JVM20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM20.png?raw=true)

![JAVA_JVM21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM21.png?raw=true)

![JAVA_JVM22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM22.png?raw=true)

![JAVA_JVM23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM23.png?raw=true)

![JAVA_JVM24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM24.png?raw=true)

## 堆的分配参数


![JAVA_JVM25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM25.png?raw=true)

![JAVA_JVM26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM26.png?raw=true)

![JAVA_JVM27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM27.png?raw=true)

![JAVA_JVM28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM28.png?raw=true)

-------



![JAVA_JVM31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM31.png?raw=true)

![JAVA_JVM32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM32.png?raw=true)

![JAVA_JVM33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM33.png?raw=true)

![JAVA_JVM34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM34.png?raw=true)

![JAVA_JVM35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM35.png?raw=true)

![JAVA_JVM36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM36.png?raw=true)

![JAVA_JVM37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM37.png?raw=true)

![JAVA_JVM38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM38.png?raw=true)

![JAVA_JVM39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM39.png?raw=true)

![JAVA_JVM40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM40.png?raw=true)

永久区大小的设置：

![JAVA_JVM41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM41.png?raw=true)

![JAVA_JVM42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM42.png?raw=true)

![JAVA_JVM43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM43.png?raw=true)

**问题思考**:

```
-Xmx(最大堆空间)和–Xms(最小堆空间)应该保持一个什么关系,可以让系统的性能尽可能的好呢?
```

Java会尽量的维持在最小堆运行，即使设置的最大值很大，只有当GC之后也无法满足最小堆，才会去扩容。

首先并不是虚拟机内存越大就越好，大概原因是因为：内存越大，JVM 进行 Full GC 所需的时间越久

把xmx和xms设置一致可以让JVM在启动时就直接向OS申请xmx的commited内存，好处是：

1. 避免JVM在运行过程中向OS申请内存

2. 延后启动后首次GC的发生时机

3. 减少启动初期的GC次数

4. 尽可能避免使用swap space



```
如果要做一个java桌面产品，需要绑定JRE，但是JRE很大，如何做JRE瘦身
```

JRE安装目录.目录包括bin,lib二个文件夹，所以就是将这两个文件进行瘦身了，

1. bin: 可以认为这是Java虚拟机.

2. lib: 执行class文件时,Java虚拟机需要用到的类库及资源文件.

 

一、bin瘦身主要从两方面考虑

① exe文件，最主要的工具是java.exe,它用来执行class文件，如果只是为了单纯运行Java程序的话,其他可执行文件一般都是用不到的(可剔除). 

② DLL文件，是java.exe执行class文件过程中调用的，执行class文件,java.exe需要哪个库文件就加载哪个dll,不需用的可以剔除.



## 栈的分配参数

**每个线程**建立的时候都会分配栈空间，栈中主要内容就是栈帧。帧里面主要内容就是局部变量表，操作数栈，引用等信息。（一般都很小，一般不会加大，栈越小，能开的线程越多）

![JAVA_JVM44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM44.png?raw=true)

![JAVA_JVM45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM45.png?raw=true)

## IDE运行时 加上JVM配置参数

![JAVA_JVM29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM29.png?raw=true)

![JAVA_JVM30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM30.png?raw=true)

# GC的算法

## GC的概念

GC：Garbage Collection 垃圾收集

这里所谓的垃圾就是系统运行过程中产生的已经无用的对象(占用着一定的内存空间，如果长期没有回收会导致JVM内存用完而溢出，因此这些无用的对象就是需要在一定的时间内被回收，确保整个系统他会有足够的内存可以使用，在C语言和c++中需要程序员自己去申请和释放内存。而java中将自己申请和回收内存的方式做了调整，jvm自己来回收内存——JVM根据一个算法开启了一个监听线程来不断的扫描并回收内存，解放了程序员)。 

主要目的是为了防止由程序员引起的人为内存泄漏。

java中，GC的对象就是堆空间和永久区。在java中堆和永久区的内存空间是受到GC管理。

## 算法

### 引用计数算法

（老牌垃圾回收算法。无法处理循环引用，没有被Java采纳）

给对象中添加一个引用计数器，每当有一个地方引用它时，计数器值就加1；当引用失效时，计数器值就减1；任何时刻计数器为0的对象就是不可能再被使用的。

![JAVA_JVM46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM46.png?raw=true)

主流的java虚拟机并没有选用引用计数算法来管理内存，其中最主要的原因是：**它很难解决对象之间相互循环引用的问题**。

**引用计数算法的问题：**

- 引用和去引用伴随加法和减法，影响性能
- 致命的缺陷：对于**循环引用的对象**无法进行回收

![JAVA_JVM47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM47.png?raw=true)

对于最右边的那张图而言：循环引用的计数器都不为0，但是他们对于根对象都已经不可达了，但是无法释放。

### 标记-清除算法

标记-清除算法是现代垃圾回收算法的思想基础。标记-清除算法将垃圾回收分为两个阶段：标记阶段和清除阶段。一种可行的实现是，在标记阶段，**首先通过根节点，标记所有从根节点开始的可达对象**。因此，未被标记的对象就是未被引用的垃圾对象；然后，在清除阶段，清除所有未被标记的对象。

![JAVA_JVM48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM48.png?raw=true)

它的做法是当堆中的有效内存空间（available memory）被耗尽的时候，就会停止整个程序（也被成为stop the world），然后进行两项工作，第一项则是标记，第二项则是清除。

- 标记：标记的过程其实就是，遍历所有的GC Roots，然后将所有GC Roots可达的对象标记为存活的对象。
- 清除：清除的过程将遍历堆中所有的对象，将没有标记的对象全部清除掉。

也就是说，**就是当程序运行期间，若可以使用的内存被耗尽的时候，GC线程就会被触发并将程序暂停，随后将依旧存活的对象标记一遍，最终再将堆中所有没被标记的对象全部清除掉，接下来便让程序恢复运行**。

**为什么非要停止程序的运行呢？**

假设我们的程序与GC线程是一起运行的，各位试想这样一种场景。

假设我们刚标记完图中最右边的那个对象，暂且记为A，结果此时在程序当中又new了一个新对象B，且A对象可以到达B对象。但是由于此时A对象已经标记结束，B对象此时的标记位依然是0，因为它错过了标记阶段。因此当接下来轮到清除阶段的时候，新对象B将会被苦逼的清除掉。如此一来，不难想象结果，GC线程将会导致程序无法正常工作。

上面的结果当然令人无法接受，我们刚new了一个对象，结果经过一次GC，忽然变成null了，这还怎么玩？

**标记-清除算法的缺点：**

（1）首先，**它的缺点就是效率比较低（递归与全堆对象遍历）**，导致stop the world的时间比较长，尤其对于交互式的应用程序来说简直是无法接受。试想一下，如果你玩一个网站，这个网站一个小时就挂五分钟，你还玩吗？

（2）第二点主要的缺点，则是**这种方式清理出来的空闲内存是不连续的**，这点不难理解，我们的死亡对象都是随即的出现在内存的各个角落的，现在把它们清除之后，内存的布局自然会乱七八糟。而为了应付这一点，JVM就不得不维持一个内存的空闲列表，这又是一种开销。而且在分配数组对象的时候，寻找连续的内存空间会不太好找。



### 标记-压缩算法（老年代的GC）

标记-清除算法有个缺点就是造成内存碎片，存在不连续的空间，这样会导致申请较大空间的时候，又需要进行垃圾回收。

![JAVA_JVM49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM49.png?raw=true)

标记压缩算法在标记-清除算法的基础上做了一些优化，可以避免内存碎片。

标记-压缩算法适应于存活对象较多的场合如老年代。和清除算法一样第一步都要先对所有可达对象做一次标记，但是后面清理并不是简单的清理未标记的对象，而是**将所有存活对象压缩到内存的一端**，清理边界外所有的空间。

![JAVA_JVM50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM50.png?raw=true)

**缺点** ： 压缩阶段，由于移动了可用对象，需要去更新引用。



### 复制算法：（新生代的GC）

复制算法与标记清除算法相比是一种相对高效的回收算法。不适应于存活对象比较多的场合(老年代)

**复制算法的概念：**

将原有的内存空间分为两块，每次只使用其中一块，在垃圾回收时，将正在使用的内存中的存活对象复制到未使用的内存块中，之后，清除正在使用的内存块中的所有对象，交换两个内存的角色，完成垃圾回收。

- 与标记-清除算法相比，复制算法是一种相对高效的回收方法
- 不适用于存活对象较多的场合，如老年代（复制算法**适合做新生代的GC**）

![JAVA_JVM51.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM51.png?raw=true)

**复制算法的最大的问题是：空间的浪费**

复制算法使得每次都只对整个半区进行内存回收，内存分配时也就不用考虑内存碎片等复杂情况，只要移动堆顶指针，按顺序分配内存即可，实现简单，运行高效。只是这种算法的代价是将内存缩小为原来的一半，这个太要命了。复制算法要想使用，最起码对象的存活率要非常低才行，而且最重要的是，我们必须要克服50%内存的浪费。

现在的商业虚拟机都采用这种收集算法来回收新生代，新生代中的对象98%都是“朝生夕死”的，所以并不需要按照1:1的比例来划分内存空间，而是**将内存分为一块比较大的Eden空间和两块较小的Survivor空间**，每次使用Eden和其中一块Survivor。当回收时，将Eden和Survivor中还存活着的对象一次性地复制到另外一块Survivor空间上，最后清理掉Eden和刚才用过的Survivor空间。HotSpot虚拟机默认Eden和Survivor的大小比例是8:1，也就是说，每次新生代中可用内存空间为整个新生代容量的90%（80%+10%），只有10%的空间会被浪费。

当然，98%的对象可回收只是一般场景下的数据，我们没有办法保证每次回收都只有不多于10%的对象存活**，当Survivor空间不够用时，需要依赖于老年代进行分配担保，所以大对象直接进入老年代**。

![JAVA_JVM52.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM52.png?raw=true)

这里回答新生代的可用容量并没有占完。

![JAVA_JVM53.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM53.png?raw=true)

## 分代思想

基本原则是： 根据对象的存活周期进行分类。短命的对象归为新生代，长命的对象归为老年代。

![JAVA_JVM54.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM54.png?raw=true)

为什么老年代会有大量对象存活了？

绝大部分对象只有经过若干次gc都没有被回收才会进入老年代。

**总结**：

![JAVA_JVM55.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM55.png?raw=true)

## 可触及性



所有的算法都需要能够识一个垃圾对象(识别了才能做标记，才能做清除，压缩，复制)，因此需要给出一个**可触及性**的定义。

---



**可触及的：**

　　从根节点可以触及到这个对象。

​    　其实就是从根节点扫描，只要这个对象在**引用链中**，那就是可触及的。

**不可触及的：**

　　在finalize()后，可真正进入不可触及状态

　　不可触及的对象不可能复活

​       要被回收。

**可复活的：**

​     这个对象是可不触及的，现阶段不可触及，当阶段不可触及，但是也许过一会儿这个对象就可触及了，他处于一个

​     有可能被再次触及的状态。 

　　一旦所有引用被释放，就是可复活状态

　　因为在finalize()中可能复活该对象，不能被回收

-----

例子：

![JAVA_JVM56.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM56.png?raw=true)

```
//当执行GC时，会执行finalize方法，并且只会执行一次
//当执行GC时，会执行finalize方法，然后这一行代码的作用是将null的object复活一下，然后变成了可触及性
```

**finalize方法的使用总结：**

- 经验：**避免使用finalize()**，操作不慎可能导致错误。
- 优先级低，何时被调用，不确定

何时发生GC不确定，自然也就不知道finalize方法什么时候执行

- 如果要使用finalize去释放资源，我们可以使用try-catch-finally来替代它

### 可触及性中什么是根

![JAVA_JVM57.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM57.png?raw=true)

1.一般来说在栈中引用的对象我们可以认为是根。线程栈当前锁调用函数中的局部变量，是当前存在的，如果被其引用证明这个对象是真实有效的可以作为根。

2.全局对象是可以作为根的。因为它在任何时候可以被任何人使用。



## Stop-The-World

它是Java中一种全局暂停的现象。类似于我们常用的VMWare 中的挂起状态。

**全局停顿，所有Java代码停止**，native代码可以执行，但不能和JVM交互

**多半情况下是由于GC引起**

​    少数情况下由其他情况下引起，如：Dump线程、死锁检查、堆Dump。

**GC时为什么会有全局停顿？**

 （1）避免无法彻底清理干净

打个比方：类比在聚会，突然GC要过来打扫房间，聚会时很乱，又有新的垃圾产生，房间永远打扫不干净，只有让大家停止活动了，才能将房间打扫干净。

​    况且，如果没有全局停顿，会给GC线程造成很大的负担，GC算法的难度也会增加，GC很难去判断哪些是垃圾。

（2）GC的工作必须在一个能确保**一致性**的快照中进行。

这里的一致性的意思是：在整个分析期间整个执行系统看起来就像被冻结在某个时间点上，不可以出现**分析过程中对象引用关系还在不断变化**的情况，该点不满足的话分析结果的准确性无法得到保证。

这点是导致GC进行时必须停顿所有Java执行线程的其中一个重要原因。

**Stop-The-World的危害：**

长时间服务停止，没有响应（将用户正常工作的线程全部暂停掉）

遇到HA系统，可能引起主备切换导致主备机都启用，严重危害生产环境。备注：HA：High Available, 高可用性集群。

例子：

![JAVA_JVM58.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM58.png?raw=true)


![JAVA_JVM59.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM59.png?raw=true)

![JAVA_JVM60.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM60.png?raw=true)

## 练习

```java
/**
 * 写一个程序，尽量产生STW现象。给出代码和启动JVM参数。并附上GC的log日志，标出停顿的时间。
 * 答：
 * 	  尽量产生stw现象，最好就是年老代一直被填满，而且尽量保证捕获error，保证程序可以长时间运行，又能满足课题要求。
 *   产生stw其它几个因素：
 *   	dump线程
 *      死锁检查
 *      堆dupm
 *   垃圾回收算法：为让stw时间较长，增大年老代空间和选用serial old垃圾算法进行回收老年代
 *  jvm垃圾回收参数：-Xms512m -Xmx512m -Xmn4m -XX:+PrintGCDetails -XX:+UseSerialGC 
 * 
 */
public class GenerateSTW {
	/**
	 * 通过集合引用对象，保证对象不被gc回收
	 */
	private List<byte[]> content=new ArrayList<byte[]>();
	public static void main(String[] args) {
		GenerateSTW stw=new GenerateSTW();
		stw.start();
	}

	private void start() {
		while(true){
			try {
				content.add(new byte[1024]);
			} catch (OutOfMemoryError e) {
				//在不可以分配的时候，进行清理部分空间,继续运行，这样会很快产生下一次垃圾回收
				for(int i=0;i<1024;i++){
					content.remove(i);
				}
				
			}
			
		}
	}

}

gc:log
[GC [DefNew: 3711K->383K(3712K), 0.0065474 secs] 511956K->511923K(523904K), 0.0065829 secs] [Times: user=0.00 sys=0.02, real=0.01 secs] 
[GC [DefNew: 3711K->383K(3712K), 0.0070612 secs] 515251K->515217K(523904K), 0.0070912 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[GC [DefNew: 3711K->383K(3712K), 0.0071249 secs] 518545K->518512K(523904K), 0.0071581 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[GC [DefNew: 3711K->3711K(3712K), 0.0000197 secs][Tenured: 518128K->520191K(520192K), 0.2000829 secs] 521840K->521807K(523904K), [Perm : 2106K->2106K(12288K)], 0.2001707 secs] [Times: user=0.19 sys=0.00, real=0.20 secs] 
[Full GC [Tenured: 520191K->520191K(520192K), 0.1989891 secs] 523903K->523886K(523904K), [Perm : 2106K->2106K(12288K)], 0.1990396 secs] [Times: user=0.19 sys=0.00, real=0.20 secs] 
[Full GC [Tenured: 520191K->520191K(520192K), 0.1843441 secs] 523902K->523902K(523904K), [Perm : 2106K->2106K(12288K)], 0.1843975 secs] [Times: user=0.19 sys=0.00, real=0.18 secs] 
[Full GC [Tenured: 520191K->518799K(520192K), 0.5034074 secs] 523902K->518799K(523904K), [Perm : 2106K->2104K(12288K)], 0.5034560 secs] [Times: user=0.50 sys=0.00, real=0.50 secs] 
[GC [DefNew: 3328K->3328K(3712K), 0.0000172 secs][Tenured: 518799K->520191K(520192K), 0.1844447 secs] 522127K->522119K(523904K), [Perm : 2104K->2104K(12288K)], 0.1845251 secs] [Times: user=0.17 sys=0.00, real=0.19 secs] 
[Full GC [Tenured: 520191K->520191K(520192K), 0.1819704 secs] 523903K->523901K(523904K), [Perm : 2104K->2104K(12288K)], 0.1820234 secs] [Times: user=0.17 sys=0.00, real=0.18 secs] 
[Full GC [Tenured: 520191K->520191K(520192K), 0.1820428 secs] 523902K->523902K(523904K), [Perm : 2104K->2104K(12288K)], 0.1820878 secs] [Times: user=0.19 sys=0.00, real=0.18 secs] 
[Full GC [Tenured: 520191K->520191K(520192K), 0.1829175 secs] 523902K->523902K(523904K), [Perm : 2104K->2104K(12288K)], 0.1829629 secs] [Times: user=0.17 sys=0.00, real=0.18 secs] 

```

2、是否有方法尽可能减少一次STW停顿时间？由此带来的弊端是什么？

答：减少一次STW停顿时间，我这里从三个方面回答，一个是垃圾算法选择，一个是程序使用堆设置，无用对象尽早释放
垃圾算法选择：现在都是多核cpu，可以采用并行和并发收集器，如果是响应时间优化的系统应用 ，则jdk6版本一般

选择的垃圾回收算法是：XX:+UseConcMarkSweepGC,即cms收集器，这个收集器垃圾回收时间短，但是垃圾回收总时间变长，使的降低吞吐量，算法使用的是标记-清除，并发收集器不对内存空间进行压缩,整理,所以运行一段时间以后会产生"碎片",使得运行效率降低.CMSFullGCsBeforeCompaction此值设置运行多少次GC以后对内存空间进行压缩,整理

2、程序使用堆设置：应该根据程序运行情况，通过Jvm垃圾回收分析，设置一个比较合适的堆大小，不能一意味的将堆设置过大，导致程序回收很大一块空间，所以会导致stw时间较长，

3、无用对象尽早释放：使用的对象，如果没有用，尽早设置null,尽量在年轻代将对象进行回收掉，可以减少full gc停顿时长



# GC参数

算法是偏理论的， 算法在JVM中具体的进行使用是应用到垃圾回收器中。

![JAVA_JVM61.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM61.png?raw=true)



## 串行收集器

**串行收集器：Serial(连续的)收集器**

- 最古老，最稳定
- 简单而高效 (效率比价高)
- 可能会产生较长的停顿
- `-XX:+UseSerialGC`  启用参数

　　　　**新生代、老年代都会使用串行回收**

　　　   新生代复制算法

　　　　老年代标记-整理(压缩)

总结：**Serial收集器**对于**运行在Client模式下**的虚拟机来说是一个很好的选择。

这个收集器是一个单线程的收集器，但它的单线程的意义并不仅仅说明它只会使用一个CPU或一条收集线程去完成垃圾收集工作，更重要的是在它进行垃圾收集时，必须暂停其他所有的工作线程，直到它收集结束。

![JAVA_JVM62.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM62.png?raw=true)



## 并行收集器

**ParNew收集器：**

- ParNew收集器其实就是Serial收集器新生代的并行版本。
- 多线程，需要多核支持。
- `-XX:+UseParNewGC`

　　　　**新生代并行 ** 依然使用的是复制算法

　　　　**老年代串行**

- -XX:ParallelGCThreads 限制线程数量

这个垃圾收集器只会影响新生代的收集，不会影响老年代的垃圾收集

![JAVA_JVM63.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM63.png?raw=true)

![JAVA_JVM64.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM64.png?raw=true)

**各种参数设置：**

- -XX:MaxGCPauseMills

　　　　最大停顿时间，单位毫秒

　　　　GC**尽力保证**回收时间不超过设定值

- -XX:GCTimeRatio

　　　　0-100的取值范围

　　　　垃圾收集时间占总时间的比

　　　　默认99，即最大允许1%时间做GC

注：这两个参数是矛盾的。因为**停顿时间和吞吐量不可能同时调优**。我们一方买希望停顿时间少，另外一方面希望吞吐量高，其实这是矛盾的。因为：在GC的时候，垃圾回收的工作总量是不变的，如果将停顿时间减少，那频率就会提高；既然频率提高了，说明就会频繁的进行GC，那吞吐量就会减少，性能就会降低。

**吞吐量：CPU用于用户代码的时间/CPU总消耗时间的比值**，即=运行用户代码的时间/(运行用户代码时间+垃圾收集时间)。比如，虚拟机总共运行了100分钟，其中垃圾收集花掉1分钟，那吞吐量就是99%。

注2：以上所有的收集器当中，当执行GC时，都会stop the world，但是下面的CMS收集器却不会这样。

## CMS收集器

CMS收集器（Concurrent Mark Sweep：**并发标记清除**）是一种**以获取最短回收停顿时间为目标**的收集器。适合应用在互联网站或者B/S系统的服务器上，这类应用尤其重视服务器的响应速度，希望系统停顿时间最短。

- Concurrent Mark Sweep 并发标记清除，并发低停顿
- 标记-清除算法
- 并发阶段会降低吞吐量（因为停顿时间减少了，于是GC的频率会变高）
- **老年代收集器**（新生代使用ParNew 并发收集器）
- -XX:+UseConcMarkSweepGC   打开这收集器

注：这里的并发指的是与用户线程一起执行。

**CMS收集器运行过程：（着重实现了标记的过程）**

（1）**初始标记**

根可以直接关联到的对象

速度快

（2）**并发标记**（和用户线程一起）

主要标记过程，标记全部对象

（3）**重新标记**

由于并发标记时，用户线程依然运行，因此在正式清理前，再做修正

（4）**并发清除**（和用户线程一起）

基于标记结果，直接清理对象

整个过程如下图所示：

![JAVA_JVM65.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM65.png?raw=true)

其中，初始标记和重新标记时，需要stop the world。整个过程中耗时最长的是并发标记和并发清除，这两个过程都可以和用户线程一起工作。

![JAVA_JVM66.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM66.png?raw=true)

**CMS收集器特点：**

（1）尽可能降低停顿

（2）会影响系统整体吞吐量和性能

比如，在用户线程运行过程中，分一半CPU去做GC，系统性能在GC阶段，反应速度就下降一半

（3）清理不彻底

因为在清理阶段，用户线程还在运行，会产生新的垃圾，无法清理

（4）因为和用户线程一起运行，不能在空间快满时再清理

-XX:CMSInitiatingOccupancyFraction设置触发GC的阈值

如果不幸内存预留空间不够，就会引起concurrent mode failure

![JAVA_JVM67.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM67.png?raw=true)

**既然标记清除算法会造成内存空间的碎片化，CMS收集器为什么使用标记清除算法而不是使用标记整理算法：**

 CMS收集器更加关注停顿，**它在做GC的时候是和用户线程一起工作的（并发执行）**，如果使用标记整理算法的话，那么在清理的时候就会去移动可用对象的内存空间，那么应用程序的线程就很有可能找不到应用对象在哪里。

**整理时的各种参数：**

- -XX:+ UseCMSCompactAtFullCollection     

Full GC后，进行一次整理。整理过程是独占的，会引起停顿时间变长

- -XX:+CMSFullGCsBeforeCompaction

设置进行几次Full GC后，进行一次碎片整理

- -XX:ParallelCMSThreads

设定CMS的线程数量

**GC参数的整理：**

-XX:+UseSerialGC：在新生代和老年代使用串行收集器

-XX:SurvivorRatio：设置eden区大小和survivior区大小的比例

-XX:NewRatio:新生代和老年代的比

-XX:+UseParNewGC：在新生代使用并行收集器

-XX:+UseParallelGC ：新生代使用并行回收收集器  (更注重吞吐量的)

-XX:+UseParallelOldGC：老年代使用并行回收收集器

-XX:ParallelGCThreads：设置用于垃圾回收的线程数

-XX:+UseConcMarkSweepGC：新生代使用并行收集器，老年代使用CMS+串行收集器

-XX:ParallelCMSThreads：设定CMS的线程数量

-XX:CMSInitiatingOccupancyFraction：设置CMS收集器在老年代空间被使用多少后触发

-XX:+UseCMSCompactAtFullCollection：设置CMS收集器在完成垃圾收集后是否要进行一次内存碎片的整理

-XX:CMSFullGCsBeforeCompaction：设定进行多少次CMS垃圾回收后，进行一次内存压缩

-XX:+CMSClassUnloadingEnabled：允许对类元数据进行回收

-XX:CMSInitiatingPermOccupancyFraction：当永久区占用率达到这一百分比时，启动CMS回收

-XX:UseCMSInitiatingOccupancyOnly：表示只在到达阀值的时候，才进行CMS回收

 

**最后的总结：**

**为了减轻GC压力，我们需要注意些什么？**

- 软件如何设计架构（性能的根本在应用）
- GC参数属于微调（设置不合理会影响性能，产生大的延时）
- 堆空间如何管理和分配
- 代码如何写

**Tomcat实例**

![JAVA_JVM68.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM68.png?raw=true)

![JAVA_JVM69.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM69.png?raw=true)

![JAVA_JVM70.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM70.png?raw=true)

![JAVA_JVM71.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM71.png?raw=true)



![JAVA_JVM72.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM72.png?raw=true)

![JAVA_JVM73.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM73.png?raw=true)

![JAVA_JVM74.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM74.png?raw=true)

![JAVA_JVM75.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM75.png?raw=true)

![JAVA_JVM76.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM76.png?raw=true)

![JAVA_JVM77.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM77.png?raw=true)

# 类加载器

## class 装载验证流程

![JAVA_JVM78.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM78.png?raw=true)

![JAVA_JVM79.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM79.png?raw=true)

![JAVA_JVM80.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM80.png?raw=true)

![JAVA_JVM81.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM81.png?raw=true)

![JAVA_JVM82.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM82.png?raw=true)



## 什么是类装载器classLoard

![JAVA_JVM83.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM83.png?raw=true)

ClassLoader是一个抽象类，他的实例的功能是将java字节码以流的方式读入到JVM中去执行。它的实例是定制的，满足不同的字节码获取方式(从网络中加载，从文件中加载....以不同的方式加载)。 最后classLoader负责的是类装载过程中的**加载阶段** （就是类装载的第一步，链接和初始化都与其无关，只负责将类读进JVM）。



## JDK中classLoader默认设计模式

![JAVA_JVM84.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM84.png?raw=true)

![JAVA_JVM85.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM85.png?raw=true)

![JAVA_JVM86.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM86.png?raw=true)

![JAVA_JVM87.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM87.png?raw=true)

例子：

![JAVA_JVM88.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM88.png?raw=true)

![JAVA_JVM89.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM89.png?raw=true)

![JAVA_JVM90.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM90.png?raw=true)

![JAVA_JVM91.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM91.png?raw=true)

![JAVA_JVM92.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM92.png?raw=true)

![JAVA_JVM93.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM93.png?raw=true)


## 打破常规模式

![JAVA_JVM94.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM94.png?raw=true)

## 热替换

![JAVA_JVM95.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM95.png?raw=true)

JDK没有强制我们使用双亲模式，我们可以通过重写ClassLoader来实现我们的一些功能，如热替换。



# 性能监控工具



## 系统性能监控

确定系统运行的整体状态，基本定位问题所在

### Linux 系统中常用的系统监听命令

#### uptime

![JAVA_JVM96.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM96.png?raw=true)

值内容代表的含义：

系统时间   

运行时间
例子中为7分钟

连接数
每一个终端算一个连接

1,5,15分钟内的系统**平均负载**
运行队列中的平均进程数

#### top

![JAVA_JVM97.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM97.png?raw=true)

这里需要注意的是： 如果发现swap交换分区在被大量的使用的话，这时就需要引起注意， 说明系统的实际内存可能有所欠缺，使用到了交换空间，就会引起大量的IO读写，会对系统的性能造成影响。

#### vmstat

![JAVA_JVM98.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM98.png?raw=true)

### 非Linux自带的监听命令

(需要安装)

#### pidstat

功能： 细致观察进程 ，监控CPU， 监控IO， 监控内存

需要安装
`sudo apt-get install sysstat`

![JAVA_JVM99.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM99.png?raw=true)

这个命令比较特殊的是这个命令可以显示线程的使用情况：

![JAVA_JVM100.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM100.png?raw=true)

-d 选项来显示磁盘IO的使用情况：

![JAVA_JVM101.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM101.png?raw=true)



### windows系统性能监控 

#### 任务管理器

![JAVA_JVM102.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM102.png?raw=true)

#### Perfmon

Windows自带多功能性能监控工具 

命令行中输入perfmon命令就可以打开这个工具

![JAVA_JVM103.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM103.png?raw=true)

![JAVA_JVM104.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM104.png?raw=true)

#### Process Explorer

非windows 自带需要安装

![JAVA_JVM105.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM105.png?raw=true)

**windows下命令行的工具**

`pslist`  命令行工具，可用于自动化数据收集， 显示java程序的运行情况

虽然图形化工具比较直观，但是无法做脚本批量化工作。

![JAVA_JVM106.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM106.png?raw=true)

-d 查看线程的使用情况：

![JAVA_JVM107.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM107.png?raw=true)

## Java自带的工具

查看Java程序运行细节，进一步定位问题

![JAVA_JVM108.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM108.png?raw=true)

#### jps

ps命令是Linux系统中用于查看进程信息的命令。 jps 只列出java进程，类似于ps命令

```
参数-q可以指定jps只输出进程ID ，不输出类的短名称
参数-m可以用于输出传递给Java进程（主函数 main）的参数
参数-l可以用于输出主函数的完整路径
参数-v可以显示传递给JVM的参数

```



![JAVA_JVM109.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM109.png?raw=true)

![JAVA_JVM110.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM110.png?raw=true)

#### jinfo

可以用来查看**正在运行的Java应用程序的扩展参数**，甚至支持在运行时，修改部分参数

```
-flag <name>：打印指定JVM的参数值
-flag [+|-]<name>：设置指定JVM参数的布尔值
-flag <name>=<value>：设置指定JVM参数的值
```



![JAVA_JVM111.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM111.png?raw=true)

#### jmap	

生成Java应用程序的堆快照和对象的统计信息

![JAVA_JVM112.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM112.png?raw=true)

可以做一个简单的判断，什么对象占用了多少的空间。

**Dump堆**

![JAVA_JVM113.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM113.png?raw=true)

(这里b表示二进制)

#### jstack

打印线程dump

```
-l 打印锁信息
-m 打印java和native的帧信息
-F 强制dump，当jstack没有响应时使用
```

![JAVA_JVM114.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM114.png?raw=true)

**图形化的工具**

#### JConsole

图形化监控工具可以查看Java应用程序的运行概况，监控堆信息、永久区使用情况、类加载情况等

![JAVA_JVM115.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM115.png?raw=true)

#### Visual VM

Visual VM是一个功能强大的多合一故障诊断和性能监控的可视化工具

![JAVA_JVM116.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM116.png?raw=true)



# java 堆分析

## 内存溢出(OOM)的原因

JVM中，内存区间有

![JAVA_JVM117.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM117.png?raw=true)

**堆溢出**：

![JAVA_JVM118.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM118.png?raw=true)

**永久区的溢出**： 

永久区的溢出就是系统当中的类太多。

![JAVA_JVM119.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM119.png?raw=true)

**Java栈溢出**：

栈溢出指，在创建线程的时候，需要为线程分配栈空间，这个栈空间是向操作系统请求的，如果操作系统无法给出足够的空间，就会抛出OOM。

![JAVA_JVM120.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM120.png?raw=true)

![JAVA_JVM121.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM121.png?raw=true)



**直接内存溢出**：

这种情况和栈空间溢出的情况是类似的：

![JAVA_JVM122.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM122.png?raw=true)

ByteBuffer.allocateDirect()无法从操作系统获得足够的空间

ByteBuffer.allocateDirect() 这个方法分配的直接内存空间是在堆外的。是操作系统直接分配给JVM内存的。

![JAVA_JVM123.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM123.png?raw=true)



## MAT使用基础

Memory Analyzer（MAT），它是一款功能强大的Java堆内存分析器。可以用于查找内存泄露以及查看内存消耗情况。MAT是基于Eclipse开发的，是一款免费的性能分析工具。 ----》http://www.eclipse.org/mat/

在分析堆快照前，首先需要导出应用程序的堆快照。jmap、JConsole和Visual VM等工具都可用于获得Java应用程序的堆快照文件。此外，MAT本身也具有这个功能。

![JAVA_JVM124.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM124.png?raw=true)

除了直接在MAT中导出正在运行的应用程序堆快照外，也可以通过“Open Heap Dump”来打开一个既存的堆快照文件。

![JAVA_JVM125.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM125.png?raw=true)

柱状图，可以显示系统中所有类的内存使用情况。图为系统内所有类的统计信息，包含类的实例数量和占用的空间。

**他就是一个对堆内存使用情况的统计和分析工具**。

另外一个实用的功能是，可以**通过MAT查看系统中的Java线程**：

当然，这里查看Java层面的应用线程，对于虚拟机的系统线程是无法显示的。通过线程的堆栈，还可以查看局部变量的信息。

![JAVA_JVM126.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM126.png?raw=true)

(带有“<local>”标记的，就为当前帧栈的局部变量)



**MAT的另外一个常用功能，是在各个对象的引用列表中穿梭查看**。对于给定一个对象，通过MAT可以找到引用当前对象的对象，即入引用（Incomming References），以及当前对象引用的对象，即出引用（Outgoing References）

![JAVA_JVM127.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM127.png?raw=true)

## Eclipse MAT 安装及使用实例

Eclipse MAT官方网页：https://www.eclipse.org/mat/downloads.php

STS安装路径 ：Help -> Eclipse MarketPlace…
搜索 ‘mat’ 然后安装Memory Analzer 1.6.1
重启STS

安装过程中可能会报错：No repository found containing: osgi.bundle,org.apache.commons.io,2.4.0，忽略继续安装即可 

浅堆(Shallow Heap)与深堆(Retained Heap)
显示入引用（incoming）和出引用(outgoing)
支配树



# 锁

## 什么是线程安全？

(使用锁的原因)

代码所在的进程中有多个线程在同时运行，而这些线程可能会同时运行这段代码。如果每次运行结果和单线程运行的结果是一样的，而且其他的变量的值也和预期的是一样的，就是线程安全的。

(就是多个线程在对同一变量进行运算的时候是由**隔离**的)



## 对象头Mark

HotSpot虚拟机中，对象在内存中存储的布局可以分为三块区域：对象头（Header）、实例数据（Instance Data）和对齐填充（Padding）。  



Mark Word，对象头的标记，32位。描述对象的hash、锁信息，垃圾回收标记，年龄。

```
指向锁记录的指针
指向monitor的指针
GC标记
偏向锁线程ID
(这是一个有很多功能的头，在很多地方都会被使用)
```



HotSpot虚拟机的对象头(Object Header)包括两部分信息，第一部分用于存储对象自身的运行时数据， 如哈希码（HashCode）、GC分代年龄、锁状态标志、线程持有的锁、偏向线程ID、偏向时间戳等等，这部分数据的长度在32位和64位的虚拟机（暂 不考虑开启压缩指针的场景）中分别为32个和64个Bits，官方称它为“Mark Word”。



## 偏向锁






![JAVA_JVM128.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM128.png?raw=true)
![JAVA_JVM129.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM129.png?raw=true)
![JAVA_JVM130.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM130.png?raw=true)
![JAVA_JVM131.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM131.png?raw=true)
![JAVA_JVM132.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM132.png?raw=true)
![JAVA_JVM133.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM133.png?raw=true)
![JAVA_JVM134.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM134.png?raw=true)
![JAVA_JVM135.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM135.png?raw=true)
![JAVA_JVM136.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM136.png?raw=true)
![JAVA_JVM137.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM137.png?raw=true)
![JAVA_JVM138.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM138.png?raw=true)
![JAVA_JVM139.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM139.png?raw=true)
![JAVA_JVM140.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM140.png?raw=true)
![JAVA_JVM141.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM141.png?raw=true)
![JAVA_JVM142.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM142.png?raw=true)
![JAVA_JVM143.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM143.png?raw=true)
![JAVA_JVM144.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM144.png?raw=true)
![JAVA_JVM145.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM145.png?raw=true)
![JAVA_JVM146.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM146.png?raw=true)
![JAVA_JVM147.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM147.png?raw=true)
![JAVA_JVM148.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM148.png?raw=true)
![JAVA_JVM149.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM149.png?raw=true)
![JAVA_JVM150.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM150.png?raw=true)
![JAVA_JVM151.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM151.png?raw=true)
![JAVA_JVM152.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM152.png?raw=true)
![JAVA_JVM153.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM153.png?raw=true)
![JAVA_JVM154.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM154.png?raw=true)
![JAVA_JVM155.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM155.png?raw=true)
![JAVA_JVM156.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM156.png?raw=true)
![JAVA_JVM157.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM157.png?raw=true)
![JAVA_JVM158.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM158.png?raw=true)
![JAVA_JVM159.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM159.png?raw=true)
![JAVA_JVM160.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM160.png?raw=true)
![JAVA_JVM161.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM161.png?raw=true)
![JAVA_JVM162.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM162.png?raw=true)
![JAVA_JVM163.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM163.png?raw=true)
![JAVA_JVM164.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM164.png?raw=true)
![JAVA_JVM165.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM165.png?raw=true)
![JAVA_JVM166.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM166.png?raw=true)
![JAVA_JVM167.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM167.png?raw=true)
![JAVA_JVM168.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM168.png?raw=true)
![JAVA_JVM169.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM169.png?raw=true)
![JAVA_JVM170.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM170.png?raw=true)
![JAVA_JVM171.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM171.png?raw=true)
![JAVA_JVM172.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM172.png?raw=true)
![JAVA_JVM173.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM173.png?raw=true)
![JAVA_JVM174.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM174.png?raw=true)
![JAVA_JVM175.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM175.png?raw=true)
![JAVA_JVM176.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM176.png?raw=true)
![JAVA_JVM177.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM177.png?raw=true)
![JAVA_JVM178.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM178.png?raw=true)
![JAVA_JVM179.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM179.png?raw=true)
![JAVA_JVM180.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM180.png?raw=true)
![JAVA_JVM181.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM181.png?raw=true)
![JAVA_JVM182.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM182.png?raw=true)
![JAVA_JVM183.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM183.png?raw=true)
![JAVA_JVM184.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM184.png?raw=true)
![JAVA_JVM185.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM185.png?raw=true)
![JAVA_JVM186.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM186.png?raw=true)
![JAVA_JVM187.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM187.png?raw=true)
![JAVA_JVM188.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM188.png?raw=true)
![JAVA_JVM189.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM189.png?raw=true)
![JAVA_JVM190.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM190.png?raw=true)
![JAVA_JVM191.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM191.png?raw=true)
![JAVA_JVM192.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM192.png?raw=true)
![JAVA_JVM193.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM193.png?raw=true)
![JAVA_JVM194.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM194.png?raw=true)
![JAVA_JVM195.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM195.png?raw=true)
![JAVA_JVM196.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM196.png?raw=true)
![JAVA_JVM197.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM197.png?raw=true)
![JAVA_JVM198.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM198.png?raw=true)
![JAVA_JVM199.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM199.png?raw=true)
![JAVA_JVM200.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JVM200.png?raw=true)









　
