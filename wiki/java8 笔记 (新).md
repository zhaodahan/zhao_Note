#                                  java8  (尚硅谷教学视屏笔记)

------

**java8 有两大核心：Lambda 表达式 和Stream API**



# java8 的优点



## 速度更快

### 改变底层数据结构

java8 改进了底层的**数据结构**，如 hashMap

![JAVA8_NEW1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW1.png?raw=true)

即便hash算法写的再好也无法避免hash碰撞。即便存在负载因子进行扩容。也会导致效率较低。

![JAVA8_NEW2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW2.png?raw=true)

如 ConcurrentHashMap：

![JAVA8_NEW3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW3.png?raw=true)

### 改变底层内存结构

之前：

![JAVA8_NEW4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW4.png?raw=true)

现在：

![JAVA8_NEW5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW5.png?raw=true)

## 其他

代码更少（增加了新的语法 **Lambda** 表达式）

强大的 **Stream** **API**

便于并行

最大化减少空指针异常 Optional

其中最为核心的为 Lambda 表达式与Stream API



# Lambda 表达式

Lambda 是一个**匿名函数**，我们可以把 Lambda表达式理解为是**一段可以传递的代码**（将代码像数据一样进行传递）。

```java
public class TestLambda1 {
	
	//原来的匿名内部类
	@Test
	public void test1(){
		Comparator<String> com = new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
		};
		
		TreeSet<String> ts = new TreeSet<>(com);
		
		TreeSet<String> ts2 = new TreeSet<>(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
			
		});
	}
	
	//现在的 Lambda 表达式
	@Test
	public void test2(){
		Comparator<String> com = (x, y) -> Integer.compare(x.length(), y.length());
		TreeSet<String> ts = new TreeSet<>(com);
	}
	
	List<Employee> emps = Arrays.asList(
			new Employee(101, "张三", 18, 9999.99),
			new Employee(102, "李四", 59, 6666.66),
			new Employee(103, "王五", 28, 3333.33),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(105, "田七", 38, 5555.55)
	);

	//需求：获取公司中年龄小于 35 的员工信息
	public List<Employee> filterEmployeeAge(List<Employee> emps){
		List<Employee> list = new ArrayList<>();
		
		for (Employee emp : emps) {
			if(emp.getAge() <= 35){
				list.add(emp);
			}
		}
		
		return list;
	}
	
	@Test
	public void test3(){
		List<Employee> list = filterEmployeeAge(emps);
		
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
	
	//需求：获取公司中工资大于 5000 的员工信息
	public List<Employee> filterEmployeeSalary(List<Employee> emps){
		List<Employee> list = new ArrayList<>();
		
		for (Employee emp : emps) {
			if(emp.getSalary() >= 5000){
				list.add(emp);
			}
		}
		
		return list;
	}
	
	//优化方式一：策略设计模式
	public List<Employee> filterEmployee(List<Employee> emps, MyPredicate<Employee> mp){
		List<Employee> list = new ArrayList<>();
		
		for (Employee employee : emps) {
			if(mp.test(employee)){
				list.add(employee);
			}
		}
		
		return list;
	}
	
	@Test
	public void test4(){
		List<Employee> list = filterEmployee(emps, new FilterEmployeeForAge());
		for (Employee employee : list) {
			System.out.println(employee);
		}
		
		System.out.println("------------------------------------------");
		
		List<Employee> list2 = filterEmployee(emps, new FilterEmployeeForSalary());
		for (Employee employee : list2) {
			System.out.println(employee);
		}
	}
	
	//优化方式二：匿名内部类
	@Test
	public void test5(){
		List<Employee> list = filterEmployee(emps, new MyPredicate<Employee>() {
			@Override
			public boolean test(Employee t) {
				return t.getId() <= 103;
			}
		});
		
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
	
	//优化方式三：Lambda 表达式
	@Test
	public void test6(){
		List<Employee> list = filterEmployee(emps, (e) -> e.getAge() <= 35);
		list.forEach(System.out::println);
		
		System.out.println("------------------------------------------");
		
		List<Employee> list2 = filterEmployee(emps, (e) -> e.getSalary() >= 5000);
		list2.forEach(System.out::println);
	}
	
	//优化方式四：Stream API
	@Test
	public void test7(){
		emps.stream()
			.filter((e) -> e.getAge() <= 35)
			.forEach(System.out::println);
		
		System.out.println("----------------------------------------------");
		
		emps.stream()
			.map(Employee::getName)
			.limit(3)
			.sorted()
			.forEach(System.out::println);
	}
}

```



## Lamdba 基础语法

Lamdba 的关键在于提取出功能的核心代码实现。

```
一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
箭头操作符将 Lambda 表达式拆分成两部分：
     
左侧：Lambda 表达式的参数列表
右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
      
       语法格式一：无参数，无返回值
       		() -> System.out.println("Hello Lambda!");
      
       语法格式二：有一个参数，并且无返回值
       		(x) -> System.out.println(x)
      
       语法格式三：若只有一个参数，小括号可以省略不写
       		x -> System.out.println(x)
      
       语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
      		Comparator<Integer> com = (x, y) -> {
      			System.out.println("函数式接口");
      			return Integer.compare(x, y);
      		};
      
       语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
       		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
      
       语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
       		(Integer x, Integer y) -> Integer.compare(x, y);
      
       上联：左右遇一括号省
       下联：左侧推断类型省
       横批：能省则省
      
       二、Lambda 表达式需要“函数式接口”的支持
       函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
       	
```

语法格式一：无参数，无返回值

`() -> System.out.println("Hello Lambda!");`

```java
	@Test
	public void test1(){
		int num = 0;//jdk 1.7 前，必须是 final
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello World!" + num);
			}
		};
		
		r.run();
		
		System.out.println("-------------------------------");
		
		Runnable r1 = () -> System.out.println("Hello Lambda!");
		r1.run();
	}
```

语法格式二：有一个参数，并且无返回值
​       		`(x) -> System.out.println(x)`

![JAVA8_NEW6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW6.png?raw=true)

 语法格式三：若只有一个参数，小括号可以省略不写
​       		`x -> System.out.println(x)`

```java
	@Test
	public void test2(){
		Consumer<String> con = x -> System.out.println(x);
		con.accept("我大尚硅谷威武！");
	}
```



## Java8 内置的四大核心函数式接口

什么是函数式接口？只包含一个抽象方法的接口，称为函数式接口

四大核心函数式接口：

```
Consumer<T> : 消费型接口
   		void accept(T t);
   
 Supplier<T> : 供给型接口
   		T get(); 
   
 Function<T, R> : 函数型接口
   		R apply(T t);
   
 Predicate<T> : 断言型接口
   		boolean test(T t);
```

使用示例：

```java
//Consumer<T> 消费型接口 :
	@Test
	public void test1(){
		happy(10000, (m) -> System.out.println("你们刚哥喜欢大宝剑，每次消费：" + m + "元"));
	} 
	
	public void happy(double money, Consumer<Double> con){
		con.accept(money);
	}
```



```java
//Supplier<T> 供给型接口 :
	@Test
	public void test2(){
		List<Integer> numList = getNumList(10, () -> (int)(Math.random() * 100));
		
		for (Integer num : numList) {
			System.out.println(num);
		}
	}
	
	//需求：产生指定个数的整数，并放入集合中
	public List<Integer> getNumList(int num, Supplier<Integer> sup){
		List<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < num; i++) {
			Integer n = sup.get();
			list.add(n);
		}
		
		return list;
	}
```



```java
	//Function<T, R> 函数型接口：
	@Test
	public void test3(){
		String newStr = strHandler("\t\t\t 我大尚硅谷威武   ", (str) -> str.trim());
		System.out.println(newStr);
		
		String subStr = strHandler("我大尚硅谷威武", (str) -> str.substring(2, 5));
		System.out.println(subStr);
	}
	
	//需求：用于处理字符串
	public String strHandler(String str, Function<String, String> fun){
		return fun.apply(str);
	}
```

```java
	//Predicate<T> 断言型接口：
	@Test
	public void test4(){
		List<String> list = Arrays.asList("Hello", "atguigu", "Lambda", "www", "ok");
		List<String> strList = filterStr(list, (s) -> s.length() > 3);
		
		for (String str : strList) {
			System.out.println(str);
		}
	}
	
	//需求：将满足条件的字符串，放入集合中
	public List<String> filterStr(List<String> list, Predicate<String> pre){
		List<String> strList = new ArrayList<>();
		
		for (String str : list) {
			if(pre.test(str)){
				strList.add(str);
			}
		}
		
		return strList;
	}
	
```



## 方法引用与构造器引用

使用他的前提是， 引用的方法与要实现的接口的方法的参数与返回类型一致。

```
/*
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * 
 * 1. 对象的引用 :: 实例方法名
 * 
 * 2. 类名 :: 静态方法名
 * 
 * 3. 类名 :: 实例方法名
 * 
 * 注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * 
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * 
 * 1. 类名 :: new
 * 
 * 三、数组引用
 * 
 * 	类型[] :: new;
 * 
 * 
 */
```



# Stream API

使用这个操作数据是会变的十分的方便。

这个流虽然也是对数据进行操作的，但是和IO流还是有区别的。

流(Stream) 到底是什么：是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。

 “集合讲的是数据，流讲的是计算！”   经过一系列的中间操作将源数据转换成一个新流

注意：

①Stream 自己不会存储元素。

②**Stream 不会改变源对象**。相反，他们会返回一个持有结果的新Stream。

 ③Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行

![JAVA8_NEW7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW7.png?raw=true)

![JAVA8_NEW8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW8.png?raw=true)

```java
/*
 * 一、Stream API 的操作步骤：
 * 
 * 1. 创建 Stream
 * 
 * 2. 中间操作
 * 
 * 3. 终止操作(终端操作)
 */
public class TestStreamaAPI {
	
	//1. 创建 Stream
	@Test
	public void test1(){
		//1. Collection 提供了两个方法  stream() 与 parallelStream()
		List<String> list = new ArrayList<>();
		Stream<String> stream = list.stream(); //获取一个顺序流
		Stream<String> parallelStream = list.parallelStream(); //获取一个并行流
		
		//2. 通过 Arrays 中的 stream() 获取一个数组流
		Integer[] nums = new Integer[10];
		Stream<Integer> stream1 = Arrays.stream(nums);
		
		//3. 通过 Stream 类中静态方法 of()
		Stream<Integer> stream2 = Stream.of(1,2,3,4,5,6);
		
		//4. 创建无限流
		//迭代
		Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
		stream3.forEach(System.out::println);
		
		//生成
		Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
		stream4.forEach(System.out::println);
		
	}
	
	//2. 中间操作
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 59, 6666.66),
			new Employee(101, "张三", 18, 9999.99),
			new Employee(103, "王五", 28, 3333.33),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(105, "田七", 38, 5555.55)
	);
	
	/*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */
	
	//内部迭代：迭代操作 Stream API 内部完成
	@Test
	public void test2(){
		//所有的中间操作不会做任何的处理, 中间操作是不会有任何的结果
		Stream<Employee> stream = emps.stream()
			.filter((e) -> {
				System.out.println("测试中间操作");
				return e.getAge() <= 35;
			});
		
		//只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
		stream.forEach(System.out::println);
	}
	
	//外部迭代
	@Test
	public void test3(){
		Iterator<Employee> it = emps.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	@Test
	public void test4(){
		emps.stream()
			.filter((e) -> {
				System.out.println("短路！"); // &&  ||
				return e.getSalary() >= 5000;
			}).limit(3)
			.forEach(System.out::println);
	}
	
	@Test
	public void test5(){
		emps.parallelStream()
			.filter((e) -> e.getSalary() >= 5000)
			.skip(2)
			.forEach(System.out::println);
	}
	
	@Test
	public void test6(){
		emps.stream()
			.distinct()
			.forEach(System.out::println);
	}
}

```

注意： 所有的中间操作是不会有任何的结果(不会被执行)， 只有在我们执行了终止操作后才会执行中间操作。



## 映射

```java
public class TestStreamAPI1 {
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 59, 6666.66),
			new Employee(101, "张三", 18, 9999.99),
			new Employee(103, "王五", 28, 3333.33),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(105, "田七", 38, 5555.55)
	);
	
	//2. 中间操作
	/*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
	@Test
	public void test1(){
		Stream<String> str = emps.stream()
			.map((e) -> e.getName());
		
		System.out.println("-------------------------------------------");
		
		List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
		
		Stream<String> stream = strList.stream()
			   .map(String::toUpperCase);
		
		stream.forEach(System.out::println);
		
		Stream<Stream<Character>> stream2 = strList.stream()
			   .map(TestStreamAPI1::filterCharacter);
		
		stream2.forEach((sm) -> {
			sm.forEach(System.out::println);
		});
		
		System.out.println("---------------------------------------------");
		
		Stream<Character> stream3 = strList.stream()
			   .flatMap(TestStreamAPI1::filterCharacter);
		
		stream3.forEach(System.out::println);
	}

	public static Stream<Character> filterCharacter(String str){
		List<Character> list = new ArrayList<>();
		
		for (Character ch : str.toCharArray()) {
			list.add(ch);
		}
		
		return list.stream();
	}
	
	/*
		sorted()——自然排序
		sorted(Comparator com)——定制排序
	 */
	@Test
	public void test2(){
		emps.stream()
			.map(Employee::getName)
			.sorted()
			.forEach(System.out::println);
		
		System.out.println("------------------------------------");
		
		emps.stream()
			.sorted((x, y) -> {
				if(x.getAge() == y.getAge()){
					return x.getName().compareTo(y.getName());
				}else{
					return Integer.compare(x.getAge(), y.getAge());
				}
			}).forEach(System.out::println);
	}
}

```

map 与flatMap 之间的区别类似于 （`List.add()  和 List.addAll()`）

![JAVA8_NEW9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW9.png?raw=true)

![JAVA8_NEW10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW10.png?raw=true)

## 排序

小技巧：

![JAVA8_NEW11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW11.png?raw=true)

![JAVA8_NEW12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW12.png?raw=true)

## 查找与匹配

(终止操作)

```java
public class TestStreamAPI2 {
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 59, 6666.66, Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Status.FREE),
			new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(105, "田七", 38, 5555.55, Status.BUSY)
	);
	
	//3. 终止操作
	/*
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
	@Test
	public void test1(){
			boolean bl = emps.stream()
				.allMatch((e) -> e.getStatus().equals(Status.BUSY));
			
			System.out.println(bl);
			
			boolean bl1 = emps.stream()
				.anyMatch((e) -> e.getStatus().equals(Status.BUSY));
			
			System.out.println(bl1);
			
			boolean bl2 = emps.stream()
				.noneMatch((e) -> e.getStatus().equals(Status.BUSY));
			
			System.out.println(bl2);
	}
	
	@Test
	public void test2(){
		Optional<Employee> op = emps.stream()
			.sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
			.findFirst();
		
		System.out.println(op.get());
		
		System.out.println("--------------------------------");
		
		Optional<Employee> op2 = emps.parallelStream()
			.filter((e) -> e.getStatus().equals(Status.FREE))
			.findAny();
		
		System.out.println(op2.get());
	}
	
	@Test
	public void test3(){
		long count = emps.stream()
						 .filter((e) -> e.getStatus().equals(Status.FREE))
						 .count();
		
		System.out.println(count);
		
		Optional<Double> op = emps.stream()
			.map(Employee::getSalary)
			.max(Double::compare);
		
		System.out.println(op.get());
		
		Optional<Employee> op2 = emps.stream()
			.min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
		
		System.out.println(op2.get());
	}
	
	//注意：流进行了终止操作后，不能再次使用
	@Test
	public void test4(){
		Stream<Employee> stream = emps.stream()
		 .filter((e) -> e.getStatus().equals(Status.FREE));
		
		long count = stream.count();
		
		stream.map(Employee::getSalary)
			.max(Double::compare);
	}
}

```



## 规约与收集

collect——将流转换为其他形式

```java
public class TestStreamAPI3 {
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 79, 6666.66, Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Status.FREE),
			new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(105, "田七", 38, 5555.55, Status.BUSY)
	);
	
	//3. 终止操作
	/*
		归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
	public void test1(){
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		Integer sum = list.stream()
			.reduce(0, (x, y) -> x + y);
		
		System.out.println(sum);
		
		System.out.println("----------------------------------------");
		
		Optional<Double> op = emps.stream()
			.map(Employee::getSalary)
			.reduce(Double::sum);
		// map 进行提取，reduce进行操作。 map 和 reduce 的连接通常称为 map-reduce 模式
		System.out.println(op.get());
	}
	
	//需求：搜索名字中 “六” 出现的次数
	@Test
	public void test2(){
		Optional<Integer> sum = emps.stream()
			.map(Employee::getName)
			.flatMap(TestStreamAPI1::filterCharacter)
			.map((ch) -> {
				if(ch.equals('六'))
					return 1;
				else 
					return 0;
			}).reduce(Integer::sum);
		
		System.out.println(sum.get());
	}
	
	//collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
	@Test
	public void test3(){
		List<String> list = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toList());
		
		list.forEach(System.out::println);
		
		System.out.println("----------------------------------");
		
		Set<String> set = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toSet());
		
		set.forEach(System.out::println);

		System.out.println("----------------------------------");
		
		HashSet<String> hs = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toCollection(HashSet::new));
		
		hs.forEach(System.out::println);
	}
	
	@Test
	public void test4(){
		Optional<Double> max = emps.stream()
			.map(Employee::getSalary)
			.collect(Collectors.maxBy(Double::compare));
		
		System.out.println(max.get());
		
		Optional<Employee> op = emps.stream()
			.collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
		
		System.out.println(op.get());
		
		Double sum = emps.stream()
			.collect(Collectors.summingDouble(Employee::getSalary));
		
		System.out.println(sum);
		
		Double avg = emps.stream()
			.collect(Collectors.averagingDouble(Employee::getSalary));
		
		System.out.println(avg);
		
		Long count = emps.stream()
			.collect(Collectors.counting());
		
		System.out.println(count);
		
		System.out.println("--------------------------------------------");
		
		DoubleSummaryStatistics dss = emps.stream()
			.collect(Collectors.summarizingDouble(Employee::getSalary));
		
		System.out.println(dss.getMax());
	}
	
	//分组
	@Test
	public void test5(){
		Map<Status, List<Employee>> map = emps.stream()
			.collect(Collectors.groupingBy(Employee::getStatus));
		
		System.out.println(map);
	}
	
	//多级分组
	@Test
	public void test6(){
		Map<Status, Map<String, List<Employee>>> map = emps.stream()
			.collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
				if(e.getAge() >= 60)
					return "老年";
				else if(e.getAge() >= 35)
					return "中年";
				else
					return "成年";
			})));
		
		System.out.println(map);
	}
	
	//分区
	@Test
	public void test7(){
		Map<Boolean, List<Employee>> map = emps.stream()
			.collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
		
		System.out.println(map);
	}
	
	//
	@Test
	public void test8(){
		String str = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.joining("," , "----", "----"));
		
		System.out.println(str);
	}
	
	@Test
	public void test9(){
		Optional<Double> sum = emps.stream()
			.map(Employee::getSalary)
			.collect(Collectors.reducing(Double::sum));
		
		System.out.println(sum.get());
	}
}

```

![JAVA8_NEW13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW13.png?raw=true)

# 并行流顺序流

并行流使线程更容易的切换到多线程， 他的底层是Fork/Join 框架

![JAVA8_NEW14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW14.png?raw=true)

![JAVA8_NEW15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW15.png?raw=true)

![JAVA8_NEW16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW16.png?raw=true)

![JAVA8_NEW17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW17.png?raw=true)

java8 以前使用fork join

```java
public class ForkJoinCalculate extends RecursiveTask<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 13475679780L;
	
	private long start;
	private long end;
	
	private static final long THRESHOLD = 10000L; //临界值
	
	public ForkJoinCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		long length = end - start;
		
		if(length <= THRESHOLD){
			long sum = 0;
			
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			
			return sum;
		}else{
			long middle = (start + end) / 2;
			
			ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
			left.fork(); //拆分，并将该子任务压入线程队列
			
			ForkJoinCalculate right = new ForkJoinCalculate(middle+1, end);
			right.fork();
			
			return left.join() + right.join();
		}
		
	}

}

```



java8 

```java
public class TestForkJoin {
	
	@Test
	public void test1(){
		long start = System.currentTimeMillis();
		
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 10000000000L);
		
		long sum = pool.invoke(task);
		System.out.println(sum);
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费的时间为: " + (end - start)); //112-1953-1988-2654-2647-20663-113808
	}
	
	@Test
	public void test2(){
		long start = System.currentTimeMillis();
		
		long sum = 0L;
		
		for (long i = 0L; i <= 10000000000L; i++) {
			sum += i;
		}
		
		System.out.println(sum);
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费的时间为: " + (end - start)); //34-3174-3132-4227-4223-31583
	}
	
    // java8
	@Test
	public void test3(){
		long start = System.currentTimeMillis();
		
		Long sum = LongStream.rangeClosed(0L, 10000000000L)
							 .parallel()
							 .sum();
		
		System.out.println(sum);
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费的时间为: " + (end - start)); //2061-2053-2086-18926
	}

```



# Optional 容器类

作用： 尽量避免空指针异常。

```
Optional<T> 类(java.util.Optional) 是一个容器类，代表一个值存在或不存在，

原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且

可以避免空指针异常。

常用方法：

Optional.of(T t) : 创建一个 Optional 实例

Optional.empty() : 创建一个空的 Optional 实例

Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例

isPresent() : 判断是否包含值

orElse(T t) :	如果调用对象包含值，返回该值，否则返回t

orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值

map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()

flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
```

```java
/*
 * 一、Optional 容器类：用于尽量避免空指针异常
 * 	Optional.of(T t) : 创建一个 Optional 实例
 * 	Optional.empty() : 创建一个空的 Optional 实例
 * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * 	isPresent() : 判断是否包含值
 * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 */
public class TestOptional {
	
	@Test
	public void test4(){
		Optional<Employee> op = Optional.of(new Employee(101, "张三", 18, 9999.99));
		
		Optional<String> op2 = op.map(Employee::getName);
		System.out.println(op2.get());
		
		Optional<String> op3 = op.flatMap((e) -> Optional.of(e.getName()));
		System.out.println(op3.get());
	}
	
	@Test
	public void test3(){
		Optional<Employee> op = Optional.ofNullable(new Employee());
		
		if(op.isPresent()){
			System.out.println(op.get());
		}
		
		Employee emp = op.orElse(new Employee("张三"));
		System.out.println(emp);
		
		Employee emp2 = op.orElseGet(() -> new Employee());
		System.out.println(emp2);
	}
	
	@Test
	public void test2(){
		/*Optional<Employee> op = Optional.ofNullable(null);
		System.out.println(op.get());*/
		
//		Optional<Employee> op = Optional.empty();
//		System.out.println(op.get());
	}

	@Test
	public void test1(){
		Optional<Employee> op = Optional.of(new Employee());
		Employee emp = op.get();
		System.out.println(emp);
	}
	
	@Test
	public void test5(){
		Man man = new Man();
		
		String name = getGodnessName(man);
		System.out.println(name);
	}
	
	//需求：获取一个男人心中女神的名字
	public String getGodnessName(Man man){
		if(man != null){
			Godness g = man.getGod();
			
			if(g != null){
				return g.getName();
			}
		}
		
		return "苍老师";
	}
	
	//运用 Optional 的实体类
	@Test
	public void test6(){
		Optional<Godness> godness = Optional.ofNullable(new Godness("林志玲"));
		
		Optional<NewMan> op = Optional.ofNullable(new NewMan(godness));
		String name = getGodnessName2(op);
		System.out.println(name);
	}
	
	public String getGodnessName2(Optional<NewMan> man){
		return man.orElse(new NewMan())
				  .getGodness()
				  .orElse(new Godness("苍老师"))
				  .getName();
	}
}

```

他不是消除空指针异常，而是快速的定位异常。

![JAVA8_NEW18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW18.png?raw=true)

# 接口中默认方法

![JAVA8_NEW19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW19.png?raw=true)

# 其他

新的时间API优化。

# 实战中常用代码

## java8stream操作：从集合中获取符合条件的元素

```java
List<Student> students = new ArrayList<>();

        students.add(new Student(1,"张三",90));
        students.add(new Student(2,"李四",60));
        students.add(new Student(3,"王五",30));
        students.add(new Student(4,"赵六",85));

        int studentId = 3;
        Student student = students.stream()
            .filter(o -> o.getId() == studentId)
            .findAny().orElse(null);

```



## java8 如何处理一个对象如果不为空返回这个对象的某个属性的值，否者则做另外的处理

![JAVA8_NEW20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW20.png?raw=true)

```java
// 需求： 遍历list将取出省份对应的省份id. 如果对应的省份不存在就返回我们生成的   
private String findProvinceId(List<Insurance> provinceInsurances, String provinceName) {

        Insurance insurance = provinceInsurances.stream()
                .filter((e) -> e.getProvinceName().equals(provinceName))
                .findAny().orElse(null);


        String s = Optional.ofNullable(insurance)
                .map(Insurance::getProvinceId).orElse(UUID.randomUUID().toString().replaceAll("-", ""));

        return s;

    }
```



## java 8 中遍历并处理集合中的元素

```java
//            insuranceList.stream()
//                    .forEach((e)->e.setProvinceId(findProvinceId(provinceInsurances,e.getProvinceName())));

            insuranceList.forEach((e)->
                    e.setProvinceId(findProvinceId(provinceInsurances,e.getProvinceName())));
```

这里不需要使用StreamAPI 。 

原因： 

1： stream API 操作数据并不会改变源数据

2： stream API 中的forEach 是终止操作。



## java 8 中连接两个数组

```java
   @Test
    public void test() {
        String[] aa ={"aa","bb"};
        String[] bb ={"cc","dd"};
        String[] cc=concat(aa,bb);
        Arrays.stream(cc).forEach(System.out::println);


    }

    public static <T> T[] concat(T[] first, T[] second) {
        return Stream.concat(
                Stream.of(first),
                Stream.of(second)
        ).toArray(i -> (T[]) Arrays.copyOf(new Object[0], i, first.getClass()));
    }

  @Test
    public void test() {
        ArrayList<String> aa = new ArrayList<>();
        ArrayList<String> bb = new ArrayList<>();
        aa.add("aa");
        bb.add("bb");
        Arrays.stream(concat(aa.toArray(), bb.toArray())).forEach(System.out::println);


    }
```



## 计算数组中某个值出现的次数

使用 `Arrays.stream().filter().count()` 计算等于指定值的值的总数。

```java
  @Test
    public void test() {
        String[] aa ={"aa","bb","aa"};
        System.out.println(countOccurrences(aa,"aa"));

    }

    public static long countOccurrences(T[] numbers, T value) {
        return Arrays.stream(numbers)
                .filter(number -> number.equals(value))
                .count();
    }
```



## 返回两个数组之间的差异

从 b 中创建一个集合，然后在 a 上使用 `Arrays.stream().filter()` 只保留 b 中不包含的值。

```

```







![JAVA8_NEW21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW21.png?raw=true)
![JAVA8_NEW22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW22.png?raw=true)
![JAVA8_NEW23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW23.png?raw=true)
![JAVA8_NEW24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW24.png?raw=true)
![JAVA8_NEW25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW25.png?raw=true)
![JAVA8_NEW26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW26.png?raw=true)
![JAVA8_NEW27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW27.png?raw=true)
![JAVA8_NEW28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW28.png?raw=true)
![JAVA8_NEW29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW29.png?raw=true)
![JAVA8_NEW30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW30.png?raw=true)
![JAVA8_NEW31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW31.png?raw=true)
![JAVA8_NEW32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW32.png?raw=true)
![JAVA8_NEW33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW33.png?raw=true)
![JAVA8_NEW34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW34.png?raw=true)
![JAVA8_NEW35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW35.png?raw=true)
![JAVA8_NEW36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW36.png?raw=true)
![JAVA8_NEW37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW37.png?raw=true)
![JAVA8_NEW38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW38.png?raw=true)
![JAVA8_NEW39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW39.png?raw=true)
![JAVA8_NEW40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW40.png?raw=true)
![JAVA8_NEW41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW41.png?raw=true)
![JAVA8_NEW42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW42.png?raw=true)
![JAVA8_NEW43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW43.png?raw=true)
![JAVA8_NEW44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW44.png?raw=true)
![JAVA8_NEW45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW45.png?raw=true)
![JAVA8_NEW46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW46.png?raw=true)
![JAVA8_NEW47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW47.png?raw=true)
![JAVA8_NEW48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW48.png?raw=true)
![JAVA8_NEW49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW49.png?raw=true)
![JAVA8_NEW50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA8_NEW50.png?raw=true)





## 



