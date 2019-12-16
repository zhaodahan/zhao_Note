#                                                       Java陷阱

# 数值运算

## 奇数性

```java
public class Odd {  
    public static void main(String[] args) {  
        System.out.println("1是奇数吗？"+isOdd(1));  
        System.out.println("2是奇数吗？"+isOdd(2));  
        System.out.println("-1是奇数吗？"+isOdd(-1)); 
        System.out.println("-2是奇数吗？" + isOdd(-2));
    }  
    public static boolean isOdd(int i){  
        return i % 2 == 1;  
    }  
}
```

输出结果：

```
1是奇数吗？true
2是奇数吗？false
-1是奇数吗？false
-2是奇数吗？false
```

### 分析

这是Java对取余操作符（%）的定义所产生的。isOdd方法对于所有**负奇数**的判断都会失败。在任何负整数上调用该方法都回返回false，不管该整数是偶数还是奇数。

### 建议

所以当使用到了**取余**操作符，都要考虑到操作数和结果的符号。

### 解决

方案一：

```java
public static boolean isOdd(int i) {
		return i % 2 != 0; 
   //既然取余操作对所有的负奇数都失效，那么就说明对所有负偶数任然有效，偶数的反面不就是奇数吗，改求偶数
	}
```

方案二：

```java
	public static boolean isOdd(int i) {
		return (i & 1) != 0;
       // 位与运算符（&）运算规则：两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
	}
```

## 长整除

```java
public class LongDivision {  
  publicstatic void main(String[] args) {  
      final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;  
      final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;  
      System.out.println(MICROS_PER_DAY/MILLIS_PER_DAY);  
  }  
}  
```

输出结构：

```
5
//这明显不对，明眼就可以看出正确结构是1000
```



### 分析

当两个int数值相乘时，将得到另一个int数值。因此最后的结果是int数值，从而导致溢出。

### 建议

当在操作很大的数字时，千万要提防溢出。

### 解决

方案：强制表达式中的所有后续计算都用long运算来完成

```java
public class LongDivision2 {
	public static void main(String[] args) {
		final long MICROS_PER_DAY = 24L * 60 * 60 * 1000 * 1000;
		final long MILLIS_PER_DAY = 24L * 60 * 60 * 1000;
		System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
	}
}
```



## 找零时刻

```java
public class Change {
	public static void main(String[] args) {
		System.out.println(2.0 - 1.1);
	}
}
```

结果输出

```
0.8999999999999999
```

### 分析

**并不是所有的小数都可以用二进制浮点数精确**表示。

### 建议

在需要精确答案的地方，要避免使用float和double。对于货币计算，要使用`int、long、BigDecimal`。**一定要用BigDecimal（String）构造器，而千万不要用BigDecimal（double）**。后一个构造器将用它的参数的精确值来创建一个实例。

### 解决

方案一：JDK5.0或更新版本，可以使用printf方法

```java
public class Change2 {
	public static void main(String[] args) {
		System.out.printf("%.2f", 2.0 - 1.1);
	}
}
```

方案二：使用BigDecimal类

```java
public class Change3 {
	public static void main(String[] args) {
		BigDecimal bigNum1 = new BigDecimal("2.0");
		BigDecimal bigNum2 = new BigDecimal("1.1");
		System.out.println(bigNum1.subtract(bigNum2));
	}
}
```

## 初级问题

```java
 class Elementary {
	public static void main(String[] args) {
		System.out.println(12345 + 5432l);
	}
}
```

输出结果：

```
17777 
//（结构是正确的，但是这里我们本来是想加54321 ，结果1写成了L）
```

### 分析

“5432l”中的“l”是小写的“L”。

### 建议

在long类型常量中，一定要用大写的L，千万不要使用小写的l。类似地，要避免使用单个l字母作为变量名。

### 解决

方案：

```java
public class Elementary {
	public static void main(String[] args) {
		System.out.println(12345 + 5432L);
	}
}
```

## 多重转型

```java
 class Multicast {
	public static void main(String[] args) {
		System.out.println((int) (char) (byte) -1);
	}
}
```

输出结果：

```
65535
```

### 分析

如果最初的数值类型是有符号的，就执行符号扩展。如果是char，那么不管它将要被转换成什么类型，都执行零扩展。

## Dos Equis 

```java
class DosEquis {
	public static void main(String[] args) {
		char x = 'X';
		int i = 0;
		System.out.println(true ? x : 0);
		System.out.println(false ? i : x);
	}
}
```

输出结果：

```
X
88 (这里也应该是X才对)
```

### 分析

如果第2个和第3个操作数具有相同的类型，那么它就是条件表达式的类型。

如果一个操作数的类型是T，T表示byte、short、char，而另一个操作数是一个int类型的常量表达式，它的值可以用类型T表示。将对操作数类型进行二进制数字提升，而条件表达式的类型就是第2个和第3个操作数被提升之后的类型。

### 建议

**最好在表达式中使用类型相同的第2个和第3个操作数**。

## 大问题

```java
public class BigProblem2 {  
  public static void main(String[] args){  
         BigInteger a = new BigInteger("5000");
		BigInteger b = new BigInteger("1000");
		BigInteger c = BigInteger.ZERO;
           c.add(a);  
           c.add(b);  
      System.out.println(c);  //输出0 
  }  
}  
```

### 分析

BigInteger实例是不可变的。String、BigDecimal、Integer、Long、Short、Byte、Character、Boolean、Float、Double也是如此。我们不能修改它们的值，对这些类型的操作将返回新的实例。

### 修改

```java
      BigInteger a = newBigInteger("5000");  
      BigInteger b = newBigInteger("1000");  
      BigInteger c = BigInteger.ZERO;  
      c = c.add(a);
      c = c.add(b);
	System.out.println(c); //输出6000
```



## 差是什么

```java
public class Differences {  
  public static void main(String[] args){  
      int a = 012;  
      int b = 10;  
      System.out.println(a-b);  //输出结果为0
  }  
}

```

### 分析

以0开头的整形常量将被解释成为八进制数值。

### 建议

千万不要在一个整型常量前面加一个0，这会使它变成一个八进制常量。



## Math.abs方法

```java
public class Abs {  
  public static void main(String[] args){  
      int i = Math.abs(Integer.MIN_VALUE);   //Math.abs（）获取一个数的绝对值
      System.out.println(i);   //-2147483648
  }  
}
```

### 分析

如果它的参数是Integer.MIN_VALUE或者Long.MIN_VALUE。Math.abs不能保证一定会返回非负的结果

# 字符类型操作

## 最后的笑声

```java
public class LastLaugh {  
  public static void main(String[] args) {  
      System.out.println("H"+"a");  
      System.out.println('H'+'a');  
  }  
}  
```

输出结果：

```
Ha
169 
```

为什么第二个输出的不是字符，而是ASCII 码相加的值。

### 分析

①将字符类型数值的操作数提升为int数值，从char数值到int的拓宽基本类型转换时将16位的char数值零扩展到32位的int。

②当且仅当+操作符的操作数中至少有一个是String类型时，才会执行字符串连接操作。否则，执行加法。

### 解决

方案一：预设一个空字符串（推荐）

```java
System.out.println(""+'H'+'a');  
```

方案二：将一个数值用String.valueOf显式地转化成一个字符串

```java
 System.out.println(String.valueOf('H')+'a');  
```

方案三：使用字符串缓冲区（推荐）

```java
StringBuffer sb = newStringBuffer();  sb.append('H');  sb.append('a');  System.out.println(sb.toString());
```

方案四:JDK5.0,可以用printf方法

```java
System.out.printf("%s"+'a','H'); 
```



## ABC

```java
public class Abc {
	public static void main(String[] args) {
		String letters = "ABC";
		char[] numbers = { '1', '2', '3' };
		System.out.println(letters + "easy as " + numbers.toString());
	}
}
```

输出结果;

```
ABCeasy as [C@7a46a697
//为什么不是输出的字符数组中的值组成的字符串，而是字符数组的地址
```

### 建议

要将一个char数组转换成一个字符串，就要调用String.valueof(char[]）方法,而不能使用toString()方法。

### 解决

方案一：

```java
public class Abc2 {
	public static void main(String[] args) {
		String letters = "ABC";
		char[] numbers = { '1', '2', '3' };
		System.out.print(letters + "easy as ");
		System.out.println(numbers);
	}
}
```

方案二：

```java
public class Abc3 {
	public static void main(String[] args) {
		String letters = "ABC";
		char[] numbers = { '1', '2', '3' };
		System.out.print(letters + "easy as " + String.valueOf(numbers));
	}
}
```



## 动物庄园

```java
public class AnimalFarm {
	public static void main(String[] args) {
		String pig = "length: 10";
		String dog = "length: " + pig.length();
		System.out.println("Animalare equals:" + pig == dog);
	}
}
```

输出结果：

```
false
```

### 分析

这种用法错误，“+”的优先级比“==”高 。 先执行的是`"Animalare equals:" + pig` 然后才执行`== dog`

### 建议

**在使用字符串连接操作符时，总是将重要的操作数用括号括起来**。**在比较对象引用时，应该优先使用equals方法而不是==操作符**

### 解决

方案一：在使用字符串连接操作符时，总是将重要的操作数用括号括起来。

```java
System.out.println("Animal areequals:"+ (pig == dog));  
```

方案二：在比较对象引用时，应该优先使用equals方法而不是==操作符

```java
System.out.println("Animal areequals:"+ pig.equals(dog));  
```



## 字符串奶酪

```java
public class StringCheese {
	public static void main(String[] args) {
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++)
			bytes[i] = (byte) i;
		String str = new String(bytes);
		for (int i = 0, n = str.length(); i < n; i++)
			System.out.println((int) str.charAt(i) + " ");
	}
}

```

输出结果;

```
65533 
65533 
65533 
65533 
65533 
65533 
  .
  .
  .
```

### 分析

这里的罪魁祸首就是String(byte[])构造器。有关它的规范描述道：“在通过解码使用平台缺省字符集的指定byte数组来构造一个新的String时，该新String的长度是字符集的一个函数，因此，它可能不等于byte数组的长度。当给定的所有字节在缺省字符集中**并非全部有效**时，这个构造器的行为是不确定的”[Java-API]。

### 建议

每当你要**将一个byte序列转换成一个String**时，你都在使用某一个字符集，不管你是否显式地指定了它。如果你想让你的程序的行为是可预知的，那么就请你在**每次使用字符集时都明确地指定**。

### 解决

```java
public class StringCheese {
	public static void main(String[] args) throws Exception {
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++)
			bytes[i] = (byte) i;
		String str = new String(bytes, "ISO-8859-1");
		for (int i = 0, n = str.length(); i < n; i++)
			System.out.println((int) str.charAt(i) + " ");
	}
}
```

# 运算符陷阱



## 无情的增量

```java
public class Increment {
	public static void main(String[] args) {
		int j = 0;
		for (int i = 0; i < 100; i++) {
			j = j++;
		}
		System.out.println(j);
	}
}

```

输出结果：

```
0
```

### 分析

当++操作符被置于一个变量值之后时，其作用就是一个后缀增量操作符,表达式**j++的值等于j在执行增量操作之前的初始值**。因此，前面提到的赋值语句首先保存j的值，然后将j设置为其值加1，最后将j复位到它的初始值。换句话说，这个赋值操作等价于下面的语句序列：

```java
int temp = j;
j = j + 1;
j = temp;
```

对上面的解释进行验证：

```java
public void test()  {
		int j = 0;
		for (int i = 0; i < 100; i++) {
			j = j++; 
			System.out.println(j); 
          //这里无论执行多少次j都是初始的0值。循环的前进是依靠i的递增
		}
		System.out.println(j);
	}
```

输出结果：

```
0
0
0
0
.
.
.
0
0
0
0
```



### 类似的问题

```java
public class Demo {
	public s tatic void main(String[] args) {
		int j = 0;
		j = j++;
		System.out.println(j); //结果输出 0
	}
}
```

```java
	public void test() throws UnsupportedEncodingException {
		int j = 0;
		j = j++;
		System.out.println(j++); //结果输出 0
		System.out.println(j);	 //结果输出 1 
	}
     //这里单纯的j++，是会对j做自增，但是如果我们家将其再次赋值给自己会等于他的初始值
============================ 验证===============================
  	public void test() throws UnsupportedEncodingException {
		int j = 0;
		j = j++;
		System.out.println(j=j++); //结果输出 0
		System.out.println(j);	 //结果输出 0
	}  
```

```java
	public void test() throws UnsupportedEncodingException {
		int j = 0;
		j = j++;
		System.out.println(j);	//结果输出 0
		System.out.println(j++); //结果输出 0	
	}
```

### 建议

不要在单个的表达式中对相同的变量赋值超过一次。



## 变幻莫测的i值

```java
public class Shifty {  
   public static void main(String[] args) {  
       int i = 0;  
       while(-1 << i != 0)  
            i++;  
       System.out.println(i);  
   }  
}  
```

输出结果：

```
一直在无线循环，i不断的递增，且-1<<i 不可能为0
-1
828513 （i）
-2
828514
-4
828515
-8
828516
-16
828517
-32
828518
-64
828519
-128
828520
-256
 .......
```



### 分析

常量-1是所有32位都被置位的int数值（0xffffffff）。左移操作符将0移入到由移位所空出的右边的最低位，因此表达式（-1<< i）将i最右边的位设置为0，并保持其余的32 -i位为1。很明显，这个循环将完成32次迭代，因为-1<< i对任何小于32的i来说都不等于0。你可能期望终止条件测试在i等于32时返回false，从而使程序打印32，但是它打印的并不是32。实际上，它不会打印任何东西，而是进入了一个无限循环。问题在于**（-1<< 32）等于-1而不是0，因为移位操作符之使用其右操作数的低5位作为移位长度。或者是低6位，如果其左操作数是一个long类数值**。

```java
 System.out.println(-1 << 32); //-1
```

对"移位操作符之使用其右操作数的低5位作为移位长度"的理解

```
移位操作符是一个二元操作符，两个操作数分别位于移位操作两边形如：左操作数 移位操作符 右操作数 这样的结构，其含义是，将左操作数按照移位操作符指定的移位方向，进行右操作数指定的次数的移位。

首先，移位操作符能操作的数只有int类型和long类型，这个是指左操作数的类型。对于int类型而言，int在Java中占4字节，一共32位，也就是说，对于一个在Java中的int数据，做32次移位，那么这个int数据就完全变了，以左移为例，左移是补0，那么对于任意一个int类型数据，做32次移位，那么int数据变成32位全0的数据，Java不允许一次性移位左操作数的所有位，也就是右操作数不能大于32。于是回到上述的句子，其指的是右操作数的低5位，5位二进制所代表的最大值为2^5-1，为31，所以取右操作数的低5位，就是只看右操作数的二进制的低5位，其数值不会超过2^5次方，也就是int的32位。因此，移位操作符进行移位的实际次数，其实是右操作数2的次数。

简而言之，移位操作符执行的次数，只取右操作数的低5位（其数值不会大于2^5=32，也就是移位次数不会超过32位，因为32位是int型的位数）作为移位的次数。总之，移位长度是对32取余的

```

负数的二进制表示是：

正数二进制取反码，然后加1为补码。补码就是负数在计算机中的二进制表示方法

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

### 建议

如果可能的话，移位长度应该是小于32的常量。

# 异常处理



## 优柔寡断

```java
public class Indecisive {  
  public static void main(String[] args){  
      System.out.println(decision());  
  }  
  public static boolean decision(){  
      try{  
          return true;  
      }  
      finally{  
          return false;  
      }  
  }  
}
```

输出结果：

```
false
```

### 分析

在一个try-finally语句中，finally语句块总是在控制权离开try语句块时执行的。无论try语句块是正常结束的，还是意外结束的，情况都是如此。

### 建议

每一个finally语句块都应该正常结束，除非抛出的是不受检查的异常。千万不要用一个return、break、continue或throw来退出一个finally语句块，并且千万不要允许将一个受检查的异常传播到一个finally语句块之外去。

### 扩展

`面试题` try {}里有一个return语句，那么紧跟在这个try后的finally {}里的code会不会被执行，什么时候被执行，在return前还是后?

```java
boolean decision(){  
	    try{ 
	    	System.out.println("try");
	        return true;  
	    }  
	    finally{  
	    	System.out.println("finally");
	        return false;  
	    }  
	}  
```

输出结果：

```
try
finally
false
```



## 你好，再见

```java
public class HelloGoodbye {  
  public static void main(String[] args){  
      try{  
          System.out.println("Hello,World!");  
          System.exit(0);  
      }finally{  
          System.out.println("Goodbye,world!");  
      }  
    }  
}
```

输出结果：

```
Hello,World!
```

### 分析

不论try语句块的执行是正常地还是意外地结束，finally语句块确实都会执行。然而在这个程序中，try语句块根本就没有结束其执行过程。System.exit方法将停止当前线程和所有其他当场死亡的线程。finally子句的出现并不能给予线程继续去执行的特殊权限。System.exit将立即停止所有的程序线程，它并不会使finally语句块得到调用，但是它在停止VM之前会执行关闭挂钩操作。当VM被关闭时，请使用关闭挂钩来终止外部资源。通过调用System.halt可以在不执行关闭挂钩的情况下停止VM，但是这个方法很少使用。



# 对象构建



## 令人混淆的构造器

```java
public class Confusing {  
  public Confusing(Object obj){  
      System.out.println("Object");  
  }  
  public Confusing(String str){  
      System.out.println("String");  
  }  
  public static void main(String[] args){  
      new Confusing(null);  
  }  
}
```

输出结果：

```
String
```

### 分析

Java的重载解析过程可以分为两个阶段。第一阶段，选取所有可获得并且可应用的方法或构造器。第二阶段，在第一阶段选择的方法或构造器中**选取最精确**的一个。

### 建议

想要强制要求编译器选择一个精确的重载版本，需要将实参转型为形参所声明的类型。重载版本的解析可能会产生混淆，应该尽可能避免重载。如果确定进行了重载 ，请保证所有的重载版本所接受的参数类型都是互不兼容的。



# 静态陷阱



## 狸猫变犬子

```java
class Counter {
	private static int count;

	public static void increment() {
		count++;
	}

	public static int getCount() {
		return count;
	}
}

class Dog extends Counter {
	public void woof() {
		increment();
	}
}

class Cat extends Counter {
	public void meow() {
		increment();
	}
}

public class Ruckus {
	public static void main(String[] args) {
		Dog dog = new Dog();
		dog.woof();
		Cat cat = new Cat();
		cat.meow();
		System.out.println(dog.getCount() + "woof");
		System.out.println(cat.getCount() + "meow");
	}
}
```

结果

```
2woof
2meow
```

### 分析

**静态字段由声明它的类及其子类所共享**。

### 建议

如果需要让每一个子类都具有某个单独副本，那么必须在每一个子类中声明一个单独的静态字段。



## 我所得到的都是静态的

```java
class Car {
	public static void bark() {
		System.out.println("Dodo");
	}
}

class SubCar extends Car {
	public static void bark() {
        System.out.println("son Dodo");
	}
}

public class Bark {
	public static void main(String[] args) {
		Car car = new Car();
		Car subcar = new SubCar();
		car.bark();
		subcar.bark();
	}
}
```

结果：

```
Dodo
Dodo
//结果表明只调用了父类的bark()方法，子类重新不成功
```

### 分析

**静态方法是不能被覆写的，它们只能被隐藏**。

### 修改

①类名.方法名：SubCar.bark();

②删掉static

 

## 不是你的类型

```java
public class Type {  
  public static void main(String[] args){  
      String s = null;  
      System.out.println(s instanceof String);   // 输出 false
  }  
}
```

### 分析

instanceof操作符被定义为在做操作数为null时返回false。

### 问题：不是你的类型（续）

```java
public class Type2 {  
  public static void main(String[] args){  
      System.out.println(new Type() instanceof String);  
  }  
}
```

### 分析

错误。如果两个操作数的类型都是类，其中一个必须是另一个的子类型。

## 特创论

```java
public class Creator {
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			Creature creature = new Creature();
		}
		System.out.println(Creature.getNum());  //输出结果100
	}
}

class Creature {
	private static long num = 0;

	public Creature() {
		num++;
	}

	public static long getNum() {
		return num;
	}
}
```

### 建议

①在使用一个变量来对实例的创建进行计数时，要使用long类型而不是int类型的变量，以防止溢出。

②要注意，如果多线程可以并行创建对象，那么递增计数器的代码和读取计数器的代码都应该被同步。可以利用synchronized。如果你使用的是5.0或更新的版本，你可以使用一个AtomicLong实例，它可以在面临并发时绕过对同步的需要。允许那些处理基于数字类的工具和实用工具进行统一访问。

# 其他



## 日期游戏

```java
public class DatingGame {  
  public static void main(String[] args){  
      Calendar cal = Calendar.getInstance();  
      cal.set(1999,12, 31);  
      System.out.println(cal.get(Calendar.YEAR));  //输出为2000
  }  
}
```

### 分析

Date将一月表示为0，而Calendar延续了这个错误。

### 建议

在使用Calendar或Date的时候一定要当心，千万要记得查阅API文档。





   