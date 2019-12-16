#                                                             java二进制

------

# 二进制基础

计算机的能理解的就是二进制编码，编程语言只是在二进制之上进行封装。

## 说明

任何东西都有规范，提到JAVA就会提到2个规范，JAVA语言规范、JVM规范。JAVA语言规范主要定义JAVA的语法、变量、类型、文法等等，JVM规范主要定义Class文件类型、运行时数据、帧栈、虚拟机的启动、虚拟机的指令集等等。

- JAVA语言规范主要定义什么是JAVA语言。
- JVM规范主要定义JVM内部实现，二进制class文件和JVM指令集等。

## 规范中数字的内部表示和存储

> JAVA八种基本数据类型： 整形：byte,short,int,long 浮点型：float,double 布尔型：boolean 字符型：char

| 数据类型 | 所占位数 |
| -------- | -------- |
| int      | 32bit    |
| short    | 16bit    |
| long     | 64bit    |
| byte     | 8bit     |
| char     | 16bit    |
| float    | 32bit    |
| double   | 64bit    |
| boolean  | 1bit     |

> **备注：**1字节=8位(1 byte = 8bit)



------

# 二进制整数的表示

二进制使用最高位表示符号位，用1表示负数，用0表示正数。

正数的二进制最高位是0 ，负数最高位是1 。



## 负数的二进制表示

正数的二进制取反码，然后加1为补码。**补码就是负数在计算机中的二进制表示**

eg: -5

------

1：把5转化为二进制字节形式。得到101，然后补零。

![java_hook.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook.png?raw=true)

2：这个时候，原码就出来了。然后，取反（0的变成1，1的变成0。）

一个整数按照绝对值大小转换成的二进制数，是为原码。原码就上面的：00000101。

![java_hook2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook2.png?raw=true)

3：得到了反码，反码是和原码反着来的,然后，加一。反码加一是补码。

![java_hook3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook3.png?raw=true)

补码就是负数在计算机中的二进制表示方法。那么，11111011表示8位的-5，如果要表示16位的-5 ，在左边添上8个1即可。

![java_hook4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook4.png?raw=true)

------

1：如果，知道一个二进制数，怎么求其十进制数呢（对负数）

随便来个负数的二进制数。

![java_hook5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook5.png?raw=true)

2：上面最后不是加一么，那现在就减一。先减一，反着上面的方法来。

![java_hook6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook6.png?raw=true)

3：上面不是取反了么，这里也取反。

![java_hook7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook7.png?raw=true)

4：接下来就是计算了。:计算结果是13，那么这个二进制数就是：-13。

![java_hook8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook8.png?raw=true)

------

总结： （首位是1表示负数）

十进制变二进制：原码--反码--加一（补码）；

二进制变十进制：减一--反码--原码。

------

-1<<32  -1

左移31位时：

![java_hook9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook9.png?raw=true)

![java_hook10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_hook10.png?raw=true)

因为这里左移32实际上把1移动到了符号位上，其实已经是属于溢出的异常情况。java不允许。

------

## 负整数为什么采用补码呢？

负整数为什么要采用这种奇怪的表示形式呢？

原因是：只有这种形式，计算机才能实现正确的加减法。

计算机其实只能做加法，1-1其实是1+(-1)。如果用原码表示，计算结果是不对的。比如说：

```
1   -> 00000001

-1 -> 10000001

+ ------------------

-2 -> 10000010
```

 用符合直觉的原码表示，1-1的结果是-2。

 如果是补码表示：

```
1   -> 00000001

-1 -> 11111111

+ ------------------

0  ->  00000000
```

 结果是正确的。

再比如，5-3： 

```
5   -> 00000101

-3 ->  11111101

+ ------------------

2  ->  00000010 
超出的位数直接忽略。
```

 结果也是正确的。

 理解了二进制加减法，我们就能理解为什么正数的运算结果可能出现负数了。当计算结果超出表示范围的时候，最高位往往是1，然后就会被看做负数。比如说，127+1：

```
127   -> 01111111

1       -> 00000001

+ ------------------

-128  ->10000000
```

 计算结果超出了byte的表示范围，会被看做-128。



## 补码的好处

以 +1 和 -1 作加法运算为例，如下图所示：

![Java_binary.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_binary.png?raw=true)

1 + (-1) 这样的加法运算只要将二进制数相加，然后-1的末位就会变成2，根据逢2进1机制，从右至左依次所有位都会变成0。最后，最左端的符号位也会进位1变成0，丢弃溢出的1，就得到最后的结果0的二进制表示32个0。

所有的减法都可以转换成二进制位的加法运算：1-2 可以转换成1+(-2)，(-1)-(-2)可以转换成-1+2……

这跟数学中的表示是一样的，而且非常地方便计算（很多计算机科学家都是从数学领域转入计算机工程，所以在很多细微之处的都能见到数学的影子）。因此，现代计算机硬件结构实际上只设计了加法器，大部分的减法其实都是转换成加法后再运算。

> 备注：
>
> 以正数的二进制数表示为基准，负数的表示只改变符号位，这样的表示方式就是原码。因此，正数的表示方式都是**原码。**
>
> 反码就是将原码除符号位以外的值全部取反，原来是1的变为0，原来是0的变为1。
>
> 补码就是在反码的基础上，在二进制数的右端末位加1(逢2进1)。

**小结**

**正数的原码和反码和补码都一致；负数的原码是正数的符号位取反；负数的反码是原码的非符号位取反；负数的补码是反码加1。**

 

# 位运算

##  左移右移

- 左移：操作符为<<，**向左移动，右边的低位补0，高位的就舍弃掉**，将二进制看做整数，**左移1位就相当于乘以2**。
- 无符号右移(正数)：操作符为>>>，向右移动，**右边的舍弃掉，左边补0**。
- 有符号右移(负数)：操作符为>>，向右移动，右边的舍弃掉，**左边补什么取决于原来最高位是什么**，原来是1就补1，原来是0就补0，将二进制看做整数，**右移1位相当于除以2**。

## 按位与（&）

**两位全为1，结果才为1：**

```
0 & 0=0；
0 & 1=0；
1 & 0=0；  
1 & 1=1； 
```

**用法：**

- 清零：如果想要一个单位清零，那么使其全部二进制为0，只要与一个各位都为零的数值想与，结果为零。
- 取一个数中指定位：找一个数，对应X要取的位，该数的对应位为1，其余位为零，此数与X进行“与运算”可以得到X中的指定位。

例如：设X=1010 1110，取X的低4位，用X & 0000 1111 = 0000 1110 就可以得到。

## 按位或（|）

**只要有一个为1，结果就为1：**

```
0|0=0； 
0|1=1； 
1|0=1；
1|1=1；
```

**用法：**常用来对一个数据的某些位 置1；找到一个数，对应X要置1的位，该数的对应位为1，其余位为零。此数与X相或可使X中的某些位置1。

例如：将X=1010 0000 的低四位置1，用X | 0000 1111 =1010 1111 就可以得到。

## 异或运算（^）

*两个相应位为“异”（值不同），则该位结果为1，否则为0: *

```
0^0=0；
0^1=1；
1^0=1；
1^1=0；
```

**用法：**

- 使特定位翻转：找一个数，对应X要翻转的各位，该数的对应位为1，其余位为零，此数与X对应位异或就可以得到； 例如：X=1010 1110,使X低4位翻转，用X ^ 0000 1111 = 1010 0001就可以得到
- 与0相异或，保留原值 例如：X ^ 0000 0000 = 1010 1110
- 两个变量交换值的方法： 1、借助第三个变量来实现： C=A; A=B; B=C; 2、 利用加减法实现两个变量的交换：A=A+B; B=A-B;A=A-B; 3、用位异或运算来实现：利用一个数异或本身等于0和异或运算符合交换律 例如：A = A ^ B; B = A ^ B; A = A ^ B;

## 取反运算（~）

对于一个二进制数按位取反，即将0变1，1变0： ~1=0; ~0=1;



## 小技巧

　　⑴ 乘法除法：n * 2 等价于 n << 1； n * 5 等价于 n << 2 + 1； n / 2 等价于 n >> 1。

备注：JVM执行时会自动转化，大部分其它高级语言的编译器会做类似优化转换，所以除非有特殊的理由，否则别这么写。

　　⑵ 取低位：n & 0x0000FFFF；取高位：n & 0xFFFF0000。

　　⑶ 奇偶判断(正数)：n & 1，等于0为偶，等于1为奇。

```java
	public static boolean isOdd(int i) {
		return (i & 1) != 0;  //-1 & 1 =1
       // 位与运算符（&）运算规则：两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
	}
```

　　⑷ 正负判断：(n >>> 31) & 1，等于0为正，等于1为负。 （判断最高位）

　　⑸ 取余：n % m ，如m为2的幂次方，可用(n & (m - 1))替代。

# java 二进制相关运用、

## 判断一个数是否是2的幂次方的方法

如果该数是**无符号整数**，可以使用：

```java
private static boolean isPowerOfTwo(int val) {
      return (val & (val-1)) == 0;
}
==============
public void test()  {
		System.out.println(isPowerOfTwo(32)); //true
		System.out.println(isPowerOfTwo(31)); //false
}
```

> 如果一个数是2的n次方，那么这个数用二进制表示时其最高位为1，其余位为0，(val-1)和val都错开了0和1，那么&一定是0。

![Java_binary2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_binary2.png?raw=true)

如果该数是**无符号整数**，也可以使用：

```java
private static boolean isPowerOfTwo(int val) {
      return (val & -val) == val;
}
==========
public void test()  {
		System.out.println(isPowerOfTwo(-32)); //false
		System.out.println(isPowerOfTwo(-31)); //false
	}
```

> 如果一个数是2的n次方，那么这个数用二进制表示时其最高位为1，其余位为0，(val & -val)就是获取最右1的位，那么如果和val等于就是了。

![Java_binary3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_binary3.png?raw=true)

**扩展下，如何判断一个无符号数是2的n次方-1**

```java
private static boolean isPowerOfTwoLoseOne(int val) {
      return (val & (val+1)) == 0;
}
```

 ![Java_binary4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_binary4.png?raw=true)

## 操作位代表类型

JDK SelectionKey有四种操作类型，分别为：

```java
OP_READ = 1 << 0
OP_WRITE = 1 << 2
OP_CONNECT = 1 << 3
OP_ACCEPT = 1 << 4
```

由于只有四种网络操作类型，所以用4 bit就可以表示所有的网络操作位，由于JAVA语言没有bit类型，所以使用了整形来表示，每个操作位代表一种网络操作类型，分别为：00001、00100、01000、10000,这样做的好处是可以非常方便的**通过位操作来进行网络操作位的状态判断和状态修改**，提升操作性能。








  