#                                  java8 读书笔记

------

# Lambda 表达式

（增加函数处理能力）

Lambda表达式是在Java8中引入的，是Java8的最大的特点。 Lambda表达式有利于函数式编程，简化了开发。

## 语法

lambda表达式的特点，语法如下。

```
parameter -> expression body
```

下面是一个lambda表达式的重要特征。

- **可选类型声明** - **<u>无需声明参数的类型</u>**。编译器可以从该参数的值推断。
- **可选圆括号参数** - 无需在括号中声明参数。对于多个参数，括号是必需的。
- **可选大括号** - 表达式主体没有必要使用大括号，如果主体中含有一个单独的语句。
- **可选return关键字** - <u>编译器会自动返回值，如果主体有一个表达式返回的值。花括号是必需的，以表明表达式返回一个值</u>。（如果lambda表达式中含有return语句就需要大括号，如果没有return 编译器也会自己返回）



## 示例

```java
public class Java8Tester {
   interface MathOperation {
      int operation(int a, int b);
   }  

   interface GreetingService {
      void sayMessage(String message);
   }

   private int operate(int a, int b, MathOperation mathOperation){
      return mathOperation.operation(a, b);
   } 
    
   public static void main(String args[]){
      Java8Tester tester = new Java8Tester();

      //with type declaration
      MathOperation addition = (int a, int b) -> a + b; 
       //这里Lanmbda是给了MathOperation接口的一个实现

      //with out type declaration
      MathOperation subtraction = (a, b) -> a - b;

      //with return statement along with curly braces
      MathOperation multiplication = (int a, int b) -> { return a * b; };
      //without return statement and without curly braces（curly braces 大括号） 
      MathOperation division = (int a, int b) -> a / b;
      //这里没有return语句就可以不需要大括号

      System.out.println("10 + 5 = " + tester.operate(10, 5, addition));	   
      System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
      System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
      System.out.println("10 / 5 = " + tester.operate(10, 5, division));
     //这里使用lanmbda表达式简化了开发，对接口的不同简单实现我们不需要再单独来写一个类实现。

      //without parenthesis(圆括号)
      GreetingService greetService1 = message -> System.out.println("Hello " + message);

      //with parenthesis
      GreetingService greetService2 = (message) -> System.out.println("Hello " + message);

      greetService1.sayMessage("Mahesh");
      greetService2.sayMessage("Suresh");	   
   }   

}

=======================================输出结果============================================
10 + 5 = 15
10 - 5 = 5
10 x 5 = 50
10 / 5 = 2
-----
 
```

由上面的例子我们可以得出：

lambda表达式主要用于为只有一个单一的方法接口(内联执行的功能的接口)提供实现。

Lambda表达式消除匿名类的需求，并给出了一个非常简单但功能强大的函数式编程能力。

## 变量的作用域

在lambda表达式，可以指任何**最终的变量**（final）或有效的最后一个变量（被分配一次）。如果变量被二次赋值，lambda表达式将抛出编译错误。

```java
public class Java8Tester {
   final static String salutation = "Hello! ";
   public static void main(String args[]){
      GreetingService greetService1 = message -> System.out.println(salutation + message);
      greetService1.sayMessage("Mahesh");   
   }   
   interface GreetingService {
      void sayMessage(String message);
   }
}
```



------

# 方法引用

引用函数由他们名称，而<u>不是直接调用它们</u>。使用函数的参数。

方法引用参考描述使用“::”符号。

一种方法参考可以用来指向下列类型的方法。

- 静态方法。
- 实例方法。
- 使用new运算符构造函数(TreeSet::new)

## 方法参考实例

```java
public class Java8Tester {
	public static void main(String args[]) {
		List names = new ArrayList();
		names.add("Mahesh");
		names.add("Suresh");
		names.add("Ramesh");
		names.add("Naresh");
		names.add("Kalpesh");

		names.forEach(System.out::println);
        //通过使用System.out:: println方法为静态方法引用
	}
}
```



------

# 函数式接口

函数式接口: 接口具有单一功能，用以说明接口的作用。

例如，一个可比接口使用单个方法compareTo，并且被用于比较目的。

Java8 很多函数形式的接口定义被广泛应用于lambda表达式。

以下是在java.util.Function包中定义的功能接口列表。

| S.N. | 接口和说明                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | **BiConsumer<T,U>** 表示接收两个输入参数和不返回结果的操作。 |
| 2    | **BiFunction<T,U,R>** 表示接受两个参数，并产生一个结果的函数。 |
| 3    | **BinaryOperator<T>** 表示在相同类型的两个操作数的操作，生产相同类型的操作数的结果。 |
| 4    | **BiPredicate<T,U>** 代表两个参数谓词（布尔值函数）。        |
| 5    | **BooleanSupplier** 代表布尔值结果的提供者。                 |
| 6    | **Consumer<T>** 表示接受一个输入参数和不返回结果的操作。     |
| 7    | **DoubleBinaryOperator** 代表在两个double值操作数的运算，并产生一个double值结果。 |
| 8    | **DoubleConsumer** 表示接受一个double值参数，不返回结果的操作。 |
| 9    | **DoubleFunction<R>** 表示接受double值参数，并产生一个结果的函数。 |
| 10   | **DoublePredicate** 代表一个double值参数谓词（布尔值函数）。 |
| 11   | **DoubleSupplier** 表示double值结果的提供者。                |
| 12   | **DoubleToIntFunction** 表示接受double值参数，并产生一个int值结果的函数。 |
| 13   | **DoubleToLongFunction** 代表接受一个double值参数，并产生一个long值结果的函数。 |
| 14   | **DoubleUnaryOperator** 表示上产生一个double值结果的单个double值操作数的操作。 |
| 15   | **Function<T,R>** 表示接受一个参数，并产生一个结果的函数。   |
| 16   | **IntBinaryOperator** 表示对两个int值操作数的运算，并产生一个int值结果。 |
| 17   | **IntConsumer** 表示接受单个int值的参数并没有返回结果的操作。 |
| 18   | **IntFunction<R>** 表示接受一个int值参数，并产生一个结果的函数。 |
| 19   | **IntPredicate** 表示一个整数值参数谓词（布尔值函数）。      |
| 20   | **IntSupplier** 代表整型值的结果的提供者。                   |
| 21   | **IntToDoubleFunction** 表示接受一个int值参数，并产生一个double值结果的功能。 |
| 22   | **IntToLongFunction** 表示接受一个int值参数，并产生一个long值结果的函数。 |
| 23   | **IntUnaryOperator** 表示产生一个int值结果的单个int值操作数的运算。 |
| 24   | **LongBinaryOperator** 表示在两个long值操作数的操作，并产生一个long值结果。 |
| 25   | **LongConsumer** 表示接受一个long值参数和不返回结果的操作。  |
| 26   | **LongFunction<R>**  表示接受long值参数，并产生一个结果的函数。 |
| 27   | **LongPredicate** 代表一个long值参数谓词（布尔值函数）。     |
| 28   | **LongSupplier** 表示long值结果的提供者。                    |
| 29   | **LongToDoubleFunction** 表示接受double参数，并产生一个double值结果的函数。 |
| 30   | **LongToIntFunction** 表示接受long值参数，并产生一个int值结果的函数。 |
| 31   | **LongUnaryOperator** 表示上产生一个long值结果单一的long值操作数的操作。 |
| 32   | **ObjDoubleConsumer<T>** 表示接受对象值和double值参数，并且没有返回结果的操作。 |
| 33   | **ObjIntConsumer<T>** 表示接受对象值和整型值参数，并返回没有结果的操作。 |
| 34   | **ObjLongConsumer<T>** 表示接受对象的值和long值的说法，并没有返回结果的操作。 |
| 35   | **Predicate<T>** 代表一个参数谓词（布尔值函数）。            |
| 36   | **Supplier<T>** 表示一个提供者的结果。                       |
| 37   | **ToDoubleBiFunction<T,U>** 表示接受两个参数，并产生一个double值结果的功能。 |
| 38   | **ToDoubleFunction<T>** 代表一个产生一个double值结果的功能。 |
| 39   | **ToIntBiFunction<T,U>** 表示接受两个参数，并产生一个int值结果的函数。 |
| 40   | **ToIntFunction<T>** 代表产生一个int值结果的功能。           |
| 41   | **ToLongBiFunction<T,U>** 表示接受两个参数，并产生long值结果的功能。 |
| 42   | **ToLongFunction<T>** 代表一个产生long值结果的功能。         |
| 43   | **UnaryOperator<T>** 表示上产生相同类型的操作数的结果的单个操作数的操作。 |

## 函数接口例子

谓词 Predicate<T> 接口与方法试验（对象）返回一个布尔值功能接口。此接口意味着一个对象被检测为 true 或 false。

```java
public class Java8Tester {
	public static void eval(List<Integer> list, Predicate<Integer> predicate) {
		for (Integer n : list) {
			if (predicate.test(n)) {
				System.out.println(n + " ");
			}
		}
	}

	public static void main(String args[]) {

		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		// Predicate<Integer> predicate = n -> true
		// n is passed as parameter to test method of Predicate interface
		// test method will always return true no matter what value n has.
		System.out.println("Print all numbers:");
		// pass n as parameter
		eval(list, n -> true);

		// Predicate<Integer> predicate1 = n -> n%2 == 0
		// n is passed as parameter to test method of Predicate interface
		// test method will return true if n%2 comes to be zero
		System.out.println("Print even numbers:");
		eval(list, n -> n % 2 == 0);

		// Predicate<Integer> predicate2 = n -> n > 3
		// n is passed as parameter to test method of Predicate interface
		// test method will return true if n is greater than 3.
		System.out.println("Print numbers greater than 3:");
		eval(list, n -> n > 3);
	}

    //这里 Predicate 的实现相当于一个判断表达式
}
==============================================================
  Print all numbers:
1 
2 
3 
4 
5 
6 
7 
8 
9 
Print even numbers:
2 
4 
6 
8 
Print numbers greater than 3:
4 
5 
6 
7 
8 
9 
```



------

# 接口默认方法

 （接口有默认的方法实现）

Java8引入了接口默认方法实现 这样一个新的概念。此功能是为了向后兼容性增加，使旧接口可用于利用JAVA8 ambda表达式的能力。

例如，列表或集合接口不具备forEach方法声明。从而增加了这样的方法只会打破收集框架实现。 Java8引入了默认的方法使列表/Collection接口可以拥有forEach默认方法，并实行类实现这些接口不需要实现相同功能。

## 语法 --default 关键字

```java
public interface vehicle {
	default void print(){
		System.out.println("I am a vehicle!");
	}
}
```

## 多重默认

在接口默认方法，它们是一类实现两个接口使用相同的默认方法，那么如何解决这种模糊性。考虑下面的情况。

```java
public interface vehicle {
	default void print(){
		System.out.println("I am a vehicle!");
	}
}
public interface fourWheeler {
	default void print(){
		System.out.println("I am a four wheeler!");
	}
}
```

第一个解决方案是创建一个自己的方法，并覆盖默认实现。

```java
public class car implements vehicle, fourWheeler {
	default void print(){
		System.out.println("I am a four wheeler car vehicle!");
	}
}
```

第二个解决方法是调用使用超指定接口的默认方法。

```java
public class car implements vehicle, fourWheeler {
	default void print(){
		vehicle.super.print();
	}
}
```

## 静态默认方法

现在，从Java8起接口也可以有静态辅助方法。

```java
public interface vehicle {
	default void print(){
		System.out.println("I am a vehicle!");
	}
	static void blowHorn(){
		System.out.println("Blowing horn!!!");
	}
}
```

## 默认方法示例

```java
public class Java8Tester {
   public static void main(String args[]){
      Vehicle vehicle = new Car();
      vehicle.print();
   }   
}

interface Vehicle {
   default void print(){
      System.out.println("I am a vehicle!");
   }
   static void blowHorn(){
      System.out.println("Blowing horn!!!");
   }
}

interface FourWheeler {
   default void print(){
      System.out.println("I am a four wheeler!");
   }
}

class Car implements Vehicle, FourWheeler {
   public void print(){
      Vehicle.super.print();
      FourWheeler.super.print();
	  Vehicle.blowHorn();
      System.out.println("I am a car!");
   }
}
```



------

# 数据流API

（新数据流的API，以方便数据流处理）

流/Stream是在JAVA8中引入的一个抽象。

在Java中使用集合框架，开发人员必须使用循环，使检查反复。另一个值得关注的是效率，Java开发人员必须编写的并行代码处理，但是非常容易出错。

为了解决这样的问题，JAVA8引入了流的概念，它允许开发者通过声明处理数据(类似sql语句对数据进行操作)，并可以leverate多核架构，而不需要编写任何特定的代码。

## 什么是数据流？

流代表支持聚合操作源的序列的对象。

数据流的特点。

- 元素序列 - 流提供了一组特定类型的以顺序方式元素。流获取/计算需求的元素。它不存储元素。
- 源- 流使用集合，数组或I/O资源为输入源。
- 聚合操作 - 数据流支持如filter, map, limit, reduced, find, match等聚合操作。
- 管道传输 - 大多数流操作的返回流本身使他们的结果可以被管道传输。这些操作被称为中间操作以及它们的功能是利用输入，处理输入和输出返回到目标。collect()方法是终端操作，这是通常出现在管道传输操作结束标记流的结束。
- 自动迭代 - 流操作内部做了反复对比，其中明确迭代需要集合提供源元素。

## 生成数据流

使用Java8，Collection 接口有两个方法来生成流。

- stream() -返回顺序流考虑集合作为其源。
- parallelStream() - 返回并行数据流考虑集合作为其源。

```java
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
```

## ForEach

数据流提供了新的forEach方法遍历该流中的每个元素。考虑下面的例子打印10个随机数字。

```java
Random random = new Random();
random.ints().limit(10).forEach(System.out::println);
```

## map

**map**方法用于映射每个元素对应的结果。考虑下面的例子打印唯一的方形数字。

```java
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
//get list of unique squares
List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
```

## filter

**filter**方法用于消除基于标准元素。考虑下面的例子打印空字符串计数(总数)。

```java
List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
//get count of empty string
int count = strings.stream().filter(string -> string.isEmpty()).count();
```

## limit

**limit** 方法用于减少流的大小。考虑下面的例子打印10个随机数字。

```java
Random random = new Random();
random.ints().limit(10).forEach(System.out::println);
```

## sorted

sorted方法用来流排序。考虑下面的例子打印10个随机数字的排序顺序。

```java
Random random = new Random();
random.ints().limit(10).sorted().forEach(System.out::println);
```

## 并行处理

parallelStream是流进行并行处理的替代方案。考虑下面的例子打印空字符串计数。

```java
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
//get count of empty string
int count = strings.parallelStream().filter(string -> string.isEmpty()).count();
```

这是很容易在顺序和并行的流之间进行切换。

## 收集器

收集器是用来处理组合在一个数据流的元素的结果。**收集器可用于返回一个列表或一个字符串**。

```java
	List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd",
				"", "jkl");
		List<String> filtered = strings.stream()
				.filter(string -> !string.isEmpty())
				.collect(Collectors.toList());
		System.out.println("Filtered List: " + filtered);
		String mergedString = strings.stream()
				.filter(string -> !string.isEmpty())
				.collect(Collectors.joining(", "));
		System.out.println("Merged String: " + mergedString);
=============================输出结果=============================================
Filtered List: [abc, bc, efg, abcd, jkl]
Merged String: abc, bc, efg, abcd, jkl
```



## 统计

使用Java8，统计收集器引入计算所有统计数据时，流处理可以做这些。

```java
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

		IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x)
				.summaryStatistics();

		System.out.println("Highest number in List : " + stats.getMax());
		System.out.println("Lowest  number in List : " + stats.getMin());
		System.out.println("Sum of all numbers : " + stats.getSum());
		System.out.println("Average of all  numbers : " + stats.getAverage());
========================================结果==============================================
Highest number in List : 7
Lowest  number in List : 2
Sum of all numbers : 25
Average of all  numbers : 3.5714285714285716
```

## Stream 例子

```java
public class Java8Tester {
	public static void main(String args[]) {

		System.out.println("Using Java 7: ");
		// Count empty strings
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd",
				"", "jkl");
		System.out.println("List: " + strings);
		long count = getCountEmptyStringUsingJava7(strings);
		System.out.println("Empty Strings: " + count);

		count = getCountLength3UsingJava7(strings);
		System.out.println("Strings of length 3: " + count);

		// Eliminate empty string
		List<String> filtered = deleteEmptyStringsUsingJava7(strings);
		System.out.println("Filtered List: " + filtered);

		// Eliminate empty string and join using comma.
		String mergedString = getMergedStringUsingJava7(strings, ", ");
		System.out.println("Merged String: " + mergedString);

		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

		// get list of square of distinct numbers
		List<Integer> squaresList = getSquares(numbers);
		System.out.println("Squares List: " + squaresList);

		List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);
		System.out.println("List: " + integers);
		System.out.println("Highest number in List : " + getMax(integers));
		System.out.println("Lowest number in List : " + getMin(integers));
		System.out.println("Sum of all numbers : " + getSum(integers));
		System.out.println("Average of all numbers : " + getAverage(integers));
		System.out.println("Random Numbers: ");
		// print ten random numbers
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println(random.nextInt());
		}
		System.out.println("Using Java 8: ");
		System.out.println("List: " + strings);
		count = strings.stream().filter(string -> string.isEmpty()).count();
		System.out.println("Empty Strings: " + count);

		count = strings.stream().filter(string -> string.length() == 3).count();
		System.out.println("Strings of length 3: " + count);

		filtered = strings.stream().filter(string -> !string.isEmpty())
				.collect(Collectors.toList());
		System.out.println("Filtered List: " + filtered);

		mergedString = strings.stream().filter(string -> !string.isEmpty())
				.collect(Collectors.joining(", "));
		System.out.println("Merged String: " + mergedString);

		squaresList = numbers.stream().map(i -> i * i).distinct()
				.collect(Collectors.toList());
		System.out.println("Squares List: " + squaresList);

		System.out.println("List: " + integers);
		IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x)
				.summaryStatistics();

		System.out.println("Highest number in List : " + stats.getMax());
		System.out.println("Lowest  number in List : " + stats.getMin());
		System.out.println("Sum of all numbers : " + stats.getSum());
		System.out.println("Average of all  numbers : " + stats.getAverage());
		System.out.println("Random Numbers: ");
		random.ints().limit(10).sorted().forEach(System.out::println);

		// parallel processing
		count = strings.parallelStream().filter(string -> string.isEmpty())
				.count();
		System.out.println("Empty Strings: " + count);
	}

	private static int getCountEmptyStringUsingJava7(List<String> strings) {
		int count = 0;
		for (String string : strings) {
			if (string.isEmpty()) {
				count++;
			}
		}
		return count;
	}

	private static int getCountLength3UsingJava7(List<String> strings) {
		int count = 0;
		for (String string : strings) {
			if (string.length() == 3) {
				count++;
			}
		}
		return count;
	}

	private static List<String> deleteEmptyStringsUsingJava7(
			List<String> strings) {
		List<String> filteredList = new ArrayList<String>();
		for (String string : strings) {
			if (!string.isEmpty()) {
				filteredList.add(string);
			}
		}
		return filteredList;
	}

	private static String getMergedStringUsingJava7(List<String> strings,
			String seperator) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : strings) {
			if (!string.isEmpty()) {
				stringBuilder.append(string);
				stringBuilder.append(seperator);
			}
		}
		String mergedString = stringBuilder.toString();
		return mergedString.substring(0, mergedString.length() - 2);
	}

	private static List<Integer> getSquares(List<Integer> numbers) {
		List<Integer> squaresList = new ArrayList<Integer>();
		for (Integer number : numbers) {
			Integer square = new Integer(number.intValue() * number.intValue());
			if (!squaresList.contains(square)) {
				squaresList.add(square);
			}
		}
		return squaresList;
	}

	private static int getMax(List<Integer> numbers) {
		int max = numbers.get(0);
		for (int i = 1; i < numbers.size(); i++) {
			Integer number = numbers.get(i);
			if (number.intValue() > max) {
				max = number.intValue();
			}
		}
		return max;
	}

	private static int getMin(List<Integer> numbers) {
		int min = numbers.get(0);
		for (int i = 1; i < numbers.size(); i++) {
			Integer number = numbers.get(i);
			if (number.intValue() < min) {
				min = number.intValue();
			}
		}
		return min;
	}

	private static int getSum(List<Integer> numbers) {
		int sum = numbers.get(0);
		for (int i = 1; i < numbers.size(); i++) {
			sum += numbers.get(i).intValue();
		}
		return sum;
	}

	private static int getAverage(List<Integer> numbers) {
		return getSum(numbers) / numbers.size();
	}
}
```

------

# 判空 Optional 类 

( 强调最佳实践，妥善处理空(null)值)

Optional用于包含非空对象的**容器对象**。

Optional对象，用于表示使用不存在null值。这个类有各种实用的方法，以方便代码来处理为可用或不可用，而不是检查null值。

## 类声明

以下是java.util.Optional<T>类的声明：

```java
public final class Optional<T>
   extends Object
```

## 类方法

| S.N. | 方法及说明                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | **static <T> Optional<T> empty()** 返回一个空的 Optional 实例。 |
| 2    | **boolean equals(Object obj)** 表示某个其他对象是否“等于”此Optional。 |
| 3    | **Optional<T> filter(Predicate<? super T> predicate)**  如果值存在，并且该值给定的谓词匹配，返回一个可选描述值，否则返回一个空Optional。 |
| 4    | **<U> Optional<U> flatMap(Function<? super T,Optional<U>> mapper)** 如果值存在，应用提供的可选承载映射功能到它，返回结果，否则返回一个空Optional。 |
| 5    | **T get()** 如果值是出现在这个 Optional 中，返回这个值，否则抛出NoSuchElementException异常。 |
| 6    | **int hashCode()** 返回当前值，哈希码值（如有）或0（零），如果值不存在。 |
| 7    | **void ifPresent(Consumer<? super T> consumer)** 如果值存在，调用指定的使用方提供值，否则什么都不做。 |
| 8    | **boolean isPresent()** 返回true，如果有一个值存在，否则为false。 |
| 9    | **<U> Optional<U> map(Function<? super T,? extends U> mapper)** 如果值存在，应用提供的映射函数，如果结果非空，返回一个Optional描述结果。 |
| 10   | **static <T> Optional<T> of(T value)** 返回一个Optional具有指定当前非空值。 |
| 11   | **static <T> Optional<T> ofNullable(T value)** 返回一个Optional描述指定的值，如果非空，否则返回一个空的Optional。 |
| 12   | **T orElse(T other)** 返回值（如果存在），否则返回other。    |
| 13   | **T orElseGet(Supplier<? extends T> other)** 如果存在，返回值，否则调用其他并返回调用的结果。 |
| 14   | **<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)** 返回所含值，如果存在的话，否则抛出将由提供者创建的一个例外。 |
| 15   | **String toString()** 返回此Optional适合调试一个非空字符串表示。 |

## 方法继承

这个类从以下类继承的方法：

- java.lang.Object

## Optional 例子

```java
public class Java8Tester {
	public static void main(String args[]) {
		Java8Tester java8Tester = new Java8Tester();

		Integer value1 = null;
		Integer value2 = new Integer(10);
		// Optional.ofNullable - allows passed parameter to be null.
		Optional<Integer> a = Optional.ofNullable(value1);
		// Optional.of - throws NullPointerException if passed parameter is null
		Optional<Integer> b = Optional.of(value2);

		System.out.println(java8Tester.sum(a, b));
	}

	public Integer sum(Optional<Integer> a, Optional<Integer> b) {
		// Optional.isPresent - checks the value is present or not （是否存在）
		System.out.println("First parameter is present: " + a.isPresent());

		System.out.println("Second parameter is present: " + b.isPresent());

		// Optional.orElse - returns the value if present otherwise returns the default value passed.
		Integer value1 = a.orElse(new Integer(0));
		// Optional.get - gets the value, value should be present
		Integer value2 = b.get();

		return value1 + value2;
	}
}
============================显示=================================================
First parameter is present: false
Second parameter is present: true
10

```



------

# 日期时间API

(改进日期时间API)使用Java8，新的日期时间API引入覆盖旧的日期时间API的以下缺点。

- **非线程安全** - java.util.Date不是线程安全的，因此开发者必须在使用日期处理并发性问题。新的日期时间API是不可变的，并且没有setter方法。
- **设计不佳** - 默认的开始日期从1900年，开始每月从1天从0开始，所以没有统一。不直接使用方法操作日期。新的API提供了这样操作实用方法。
- **困难的时区处理** - 开发人员必须编写大量的代码来处理时区的问题。新的API设计开发保持特定领域设计。

JAVA8引入了java.time包 - 下一个新的日期时间API。以下是一些在java.time程序包引入重要的类。

- **本地** - 简化日期时间API，没有时间处理区的复杂性。
- **时区** - 专业的日期时间API来处理各种时区。

| SN   | 使用描述方法                                                 |
| ---- | ------------------------------------------------------------ |
| 1    | [本地日期时间API](https://www.ctolib.com/docs-java8-c--java8_datetime_api.html#)  LocalDate/本地时间和LocalDateTime类简化时区不需要开发。 |
| 2    | [时区日期时间API](https://www.ctolib.com/docs-java8-c--java8_datetime_api.html#) 时区日期时间API使用的时区是需要考虑的。 |
| 3    | [计时单位枚举](https://www.ctolib.com/docs-java8-c--java8_datetime_api.html#) java.time.temporal.ChronoUnit枚举在Java8添加，以取代旧的API用来代表日，月等整数值 |
| 4    | [周期和持续时间](https://www.ctolib.com/docs-java8-c--java8_datetime_api.html#) 这些类引入到处理时间的差异。 |
| 5    | [时间调节器](https://www.ctolib.com/docs-java8-c--java8_datetime_api.html#) TemporalAdjuster是做数学日期。例如，要获得“本月第二个星期六”或“下周二”。 |
| 6    | [向后兼容性](https://www.ctolib.com/docs-java8-c--java8_datetime_api.html#) toInstant()方法被添加到可用于将它们转换到新的日期时间的API原始日期和日历对象。 |



------

# Base64

Java8现在有内置编码器和解码器的Base64编码。在Java8中，我们可以使用三种类型的Base64编码。

- **简单** - 输出映射设置字符在A-ZA-Z0-9+/。编码器不添加任何换行输出和解码器拒绝在A-Za-z0-9+/以外的任何字符。
- **URL** - 输出映射设置字符在A-Za-z0-9+_。输出URL和文件名安全。
- **MIME** - 输出映射到MIME友好的格式。输出表示在每次不超过76个字符行和使用'\r'后跟一个换行符'\n'回车作为行分隔符。无行隔板的存在是为了使编码的结束输出。

## Nested 类

| S.N. | Nested 类 & 描述                                             |
| ---- | ------------------------------------------------------------ |
| 1    | **static class Base64.Decoder** 这个类实现了一个解码器使用的Base64编码方案解码字节的数据，在RFC4648和RFC2045规定。 |
| 2    | **static class Base64.Encoder** 这个类实现一个编码器使用的Base64编码方案编码字节数据在RFC4648和RFC2045规定。 |

## 方法

| S.N. | 方法名称 & 描述                                              |
| ---- | ------------------------------------------------------------ |
| 1    | **static Base64.Decoder getDecoder()** 返回Base64.Decoder解码使用基本型base64编码方案。 |
| 2    | **static Base64.Encoder getEncoder()** 返回Base64.Encoder编码使用的基本型base64编码方案。 |
| 3    | **static Base64.Decoder getMimeDecoder()** 返回Base64.Decoder解码使用MIME类型的base64解码方案。 |
| 4    | **static Base64.Encoder getMimeEncoder()** 返回Base64.Encoder编码使用MIME类型base64编码方案。 |
| 5    | **static Base64.Encoder getMimeEncoder(int lineLength, byte[] lineSeparator)** 返回Base64.Encoder编码使用指定的行长度和线分隔的MIME类型base64编码方案。 |
| 6    | **static Base64.Decoder getUrlDecoder()** 返回Base64.Decoder解码使用URL和文件名安全型base64编码方案。 |
| 7    | **static Base64.Encoder getUrlEncoder()** 返回Base64.Decoder解码使用URL和文件名安全型base64编码方案。... |

## 方法继承

这个类从以下类继承的方法：

- java.lang.Object

## Base64 例子

```java
public class HelloWorld {

	public static void main(String args[]) {
		try {
			// Encode using basic encoder
			String base64encodedString = Base64.getEncoder().encodeToString(
					"YiiBai?java8".getBytes("utf-8"));
			System.out.println("Base64 Encoded String (Basic) :"
					+ base64encodedString);

			// Decode
			byte[] base64decodedBytes = Base64.getDecoder().decode(
					base64encodedString);
			System.out.println("Original String: "
					+ new String(base64decodedBytes, "utf-8"));

			base64encodedString = Base64.getUrlEncoder().encodeToString(
					"YiiBai?java8".getBytes("utf-8"));
			System.out.println("Base64 Encoded String (URL) :"
					+ base64encodedString);

			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < 10; ++i) {
				stringBuilder.append(UUID.randomUUID().toString());
			}

			byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
			String mimeEncodedString = Base64.getMimeEncoder().encodeToString(
					mimeBytes);
			System.out.println("Base64 Encoded String (MIME) :"
					+ mimeEncodedString);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error :" + e.getMessage());
		}
	}
}
```



# 　