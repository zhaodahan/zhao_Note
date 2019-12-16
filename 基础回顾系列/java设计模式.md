# 设计模式

设计模式是对软件设计过程中普遍存在（反复存在）的各种问题提出的解决方案。

开发大型项目如同我们建立大型建筑，需要提前考虑各种情况，需要“设计图”。而且在软件开发过程中，需求在后期会新增和修改。如果改动代价很大，这就说明这个软件的设计是不合理的。

```
设计模式在软件中哪里？
面向对象(oo)=>功能模块[设计模式+算法(数据结构)]=>框架[使用到多种设计模式]=>架构 [服务器集群]
```

我们学了面向对象后，他的特性是要提现在设计模式中，设计模式是面向对象的精髓。

设计模式可以让我们的代码结构更加清晰，其在效率和优化中有更好的帮助。

设计模式类似于搭建积木的过程中让积木搭建的更加稳定和坚固。

设计模式专注的不是功能的实现，而是站在更高的角度上，要让代码有更好的复用性和扩展性。他是站在**结构**这个层面。

## 设计模式七大原则

### 设计模式的目的

编写程序过程中，我们尝试解决耦合性，内聚性以及可维护性，可扩展性，重用性，灵活性等多种问题。  

```
设计模式是为了让程序(软件)，具有更好
1) 代码重用性 (即：相同功能的代码，不用多次编写)
2) 可读性 (即：编程规范性, 便于其他程序员的阅读和理解)
3) 可扩展性 (即：当需要增加新的功能时，非常的方便，称为可维护)
4) 可靠性 (即：当我们增加新的功能后，对原来的功能没有影响，耦合性低)
5) 使程序呈现(对外)高内聚，(对内)低耦合的特性
```

 要想让程序具有这些特性，那我们在设计程序的时候需要遵循七个原则

```
设计模式常用的七大原则有:
1) 单一职责原则
2) 接口隔离原则
3) 依赖倒转(倒置)原则
4) 里氏替换原则
5) 开闭原则
6) 迪米特法则
7) 合成复用原则
```

### 单一职责原则

对类来说的，即一个类应该只负责一项职责(如userDAO 只负责对user表的操作)。如类 A 负责两个不同职责：职责 1，职责 2。当职责 1 需求变更而改变 A 时，可能造成职责 2 执行错误，所以需要将类 A 的粒度分解为 A1，A2

#### 单一职责原则的应用实例——交通工具

```java
public class SingleResponsibility1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vehicle vehicle = new Vehicle();
		vehicle.run("摩托车");
		vehicle.run("汽车");
		vehicle.run("飞机");
	}

}

// 交通工具类
// 方式1 问题： 这里的Vehicle 即负责了路上跑的，又负责了天上飞的，导致飞机都在公路上运行
// 1. 在方式1 的run方法中，违反了单一职责原则
// 2. 解决的方案非常的简单，根据交通工具运行方法不同，分解成不同类即可
class Vehicle {
	public void run(String vehicle) {
		System.out.println(vehicle + " 在公路上运行....");
	}
}

```

遵守单一职责进行改进

```java
public class SingleResponsibility2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RoadVehicle roadVehicle = new RoadVehicle();
		roadVehicle.run("摩托车");
		roadVehicle.run("汽车");
		
		AirVehicle airVehicle = new AirVehicle();
		airVehicle.run("飞机");
	}

}

//方案2的分析
//1. 遵守单一职责原则
//2. 但是这样做的改动很大，即将类分解，同时修改客户端(上面的main方法)
//3. 改进：直接修改Vehicle 类，改动的代码会比较少=>方案3

class RoadVehicle {
	public void run(String vehicle) {
		System.out.println(vehicle + "公路运行");
	}
}

class AirVehicle {
	public void run(String vehicle) {
		System.out.println(vehicle + "天空运行");
	}
}

class WaterVehicle {
	public void run(String vehicle) {
		System.out.println(vehicle + "水中运行");
	}
}
```

接着改进：

```java
public class SingleResponsibility3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vehicle2 vehicle2  = new Vehicle2();
		vehicle2.run("汽车");
		vehicle2.runWater("轮船");
		vehicle2.runAir("飞机");
	}

}


//方式3的分析
//1. 这种修改方法没有对原来的类做大的修改，只是增加方法
//2. 这里虽然没有在类这个级别上遵守单一职责原则，但是在方法级别上，仍然是遵守单一职责
class Vehicle2 {
	public void run(String vehicle) {
		//处理
		System.out.println(vehicle + " 在公路上运行....");	
	}
	
	public void runAir(String vehicle) {
		System.out.println(vehicle + " 在天空上运行....");
	}
	
	public void runWater(String vehicle) {
		System.out.println(vehicle + " 在水中行....");
	}
	
	//方法2.
	//..
	//..
	
	//...
}
```

对着三种改进方案进行对比：

方案二在方法上遵守了单一职责，方案三在类上遵守了单一职责。 当我们各个情况发生了改变，我们只需要针对对应方式进行修改，提高了类的可扩展性，和稳定性。在修改后不会对其他方式有影响，降低变更引起的风险。

```
单一职责原则注意事项和细节
1) 主要是为了降低类的复杂度，一个类只负责一项职责。
2) 提高类的可读性，可维护性
3) 降低变更引起的风险
4) 通常情况下，我们应当遵守单一职责原则，只有逻辑足够简单，才可以在代码级违反单一职责原则；只有类中
方法数量足够少，可以在方法级别保持单一职责原则
```



### 接口隔离原则

客户端不应该依赖它不需要的接口，即一个类对另一个类的依赖应该建立在最小的接口上

![JAVAWEB_DESIGN1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN1.png?raw=true)

```java

//接口
interface Interface1 {
	void operation1();
	void operation2();
	void operation3();
	void operation4();
	void operation5();
}

class B implements Interface1 {
	public void operation1() {
		System.out.println("B 实现了 operation1");
	}
	
	public void operation2() {
		System.out.println("B 实现了 operation2");
	}
	public void operation3() {
		System.out.println("B 实现了 operation3");
	}
	public void operation4() {
		System.out.println("B 实现了 operation4");
	}
	public void operation5() {
		System.out.println("B 实现了 operation5");
	}
}

class D implements Interface1 {
	public void operation1() {
		System.out.println("D 实现了 operation1");
	}
	
	public void operation2() {
		System.out.println("D 实现了 operation2");
	}
	public void operation3() {
		System.out.println("D 实现了 operation3");
	}
	public void operation4() {
		System.out.println("D 实现了 operation4");
	}
	public void operation5() {
		System.out.println("D 实现了 operation5");
	}
}

class A { //A 类通过接口Interface1 依赖(使用) B类，但是只会用到1,2,3方法
	public void depend1(Interface1 i) {
		i.operation1();
	}
	public void depend2(Interface1 i) {
		i.operation2();
	}
	public void depend3(Interface1 i) {
		i.operation3();
	}
}
  
class C { //C 类通过接口Interface1 依赖(使用) D类，但是只会用到1,4,5方法
	public void depend1(Interface1 i) {
		i.operation1();
	}
	public void depend4(Interface1 i) {
		i.operation4();
	}
	public void depend5(Interface1 i) {
		i.operation5();
	}
}
```

(这个设计就违反了接口隔离原则：类 A 通过接口 Interface1 依赖类 B，类 C 通过接口 Interface1 依赖类 D，如果接口 Interface1 对于类 A 和类 C来说不是最小接口，那么**类 B 和类 D 必须去实现他们不需要的方法**)

```
按隔离原则应当这样处理：
将接口 Interface1 拆分为独立的几个接口(这里我们拆分（建立在最小接口原则的基础上）成 3 个接口)，类 A 和类 C 分别与他们需要的接口建立
依赖关系。也就是采用接口隔离原则
```

![JAVAWEB_DESIGN2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN2.png?raw=true)

```java
public class Segregation1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 使用一把
		A a = new A();
		a.depend1(new B()); // A类通过接口去依赖B类
		a.depend2(new B());
		a.depend3(new B());

		C c = new C();

		c.depend1(new D()); // C类通过接口去依赖(使用)D类
		c.depend4(new D());
		c.depend5(new D());

	}

}

// 接口1
interface Interface1 {
	void operation1();

}

// 接口2
interface Interface2 {
	void operation2();

	void operation3();
}

// 接口3
interface Interface3 {
	void operation4();

	void operation5();
}

class B implements Interface1, Interface2 {
	public void operation1() {
		System.out.println("B 实现了 operation1");
	}

	public void operation2() {
		System.out.println("B 实现了 operation2");
	}

	public void operation3() {
		System.out.println("B 实现了 operation3");
	}

}

class D implements Interface1, Interface3 {
	public void operation1() {
		System.out.println("D 实现了 operation1");
	}

	public void operation4() {
		System.out.println("D 实现了 operation4");
	}

	public void operation5() {
		System.out.println("D 实现了 operation5");
	}
}

class A { // A 类通过接口Interface1,Interface2 依赖(使用) B类，但是只会用到1,2,3方法
	public void depend1(Interface1 i) {
		i.operation1();
	}

	public void depend2(Interface2 i) {
		i.operation2();
	}

	public void depend3(Interface2 i) {
		i.operation3();
	}
}

class C { // C 类通过接口Interface1,Interface3 依赖(使用) D类，但是只会用到1,4,5方法
	public void depend1(Interface1 i) {
		i.operation1();
	}

	public void depend4(Interface3 i) {
		i.operation4();
	}

	public void depend5(Interface3 i) {
		i.operation5();
	}
}
```



### 依赖倒转(倒置)原则

```
依赖倒转原则(Dependence Inversion Principle)是指：
1) 高层模块不应该依赖低层模块，二者都应该依赖其抽象 (抽象类，或者接口)，就是我们可以依赖抽象类或者接口，但是不要依赖于具体的子类(面向对象的多态性)，这样不好扩展。
2) 抽象不应该依赖细节，细节应该依赖抽象
3) 依赖倒转(倒置)的中心思想是面向接口编程
4) 依赖倒转原则是基于这样的设计理念：相对于细节的多变性，抽象的东西要稳定的多。以抽象为基础搭建的架
构比以细节为基础的架构要稳定的多。在 java 中，抽象指的是接口或抽象类，细节就是具体的实现类
5) 使用接口或抽象类的目的是制定好规范，而不涉及任何具体的操作，把展现细节的任务交给他们的实现类去完
成
```

依赖倒置原则是我们设计模式接口编程的基石。 (接口和抽象类的目的和意义在于设计，他在设计模式中的有更多的提现)

依赖倒置的实例：

不使用依赖倒转的传统方案：

```java
public class DependecyInversion {

	public static void main(String[] args) {
		Person person = new Person();
		person.receive(new Email());
	}

}


class Email {
	public String getInfo() {
		return "电子邮件信息: hello,world";
	}
}

//完成Person接收消息的功能
//方式1分析
//1. 简单，比较容易想到
//2. 如果我们获取的对象是 微信，短信等等，则新增类，同时Perons也要增加相应的接收方法 (这里显然的需要应用多态)
//3. 解决思路：引入一个抽象的接口IReceiver, 表示接收者, 这样Person类与接口IReceiver发生依赖
//   因为Email, WeiXin 等等属于接收的范围，他们各自实现IReceiver 接口就ok, 这样我们就符号依赖倒转原则
class Person {
	public void receive(Email email ) {
		System.out.println(email.getInfo());
	}
}
```

使用依赖倒置的改进方案：

```java
public class DependecyInversion {

	public static void main(String[] args) {
		//客户端无需改变
		Person person = new Person();
		person.receive(new Email());
		
		person.receive(new WeiXin());
	}

}

//定义接口
interface IReceiver {
	public String getInfo();
}

class Email implements IReceiver {
	public String getInfo() {
		return "电子邮件信息: hello,world";
	}
}

//增加微信
class WeiXin implements IReceiver {
	public String getInfo() {
		return "微信信息: hello,ok";
	}
}

//方式2
class Person {
	//这里我们是对接口的依赖
	public void receive(IReceiver receiver ) {
		System.out.println(receiver.getInfo());
	}
}

```

依赖关系传递的三种方式

```
1. 接口传递 (如上面改进的方式2)
2. 构造方法传递
3. setter方法的传递
```

方式一：接口传递依赖

```java
	// 方式1： 通过接口传递实现依赖
	// 开关的接口
	interface IOpenAndClose {
		public void open(ITV tv); // 抽象方法,接收接口
	}

	interface ITV { // ITV接口
		public void play();
	}

	class ChangHong implements ITV {

		@Override
		public void play() {
			// TODO Auto-generated method stub
			System.out.println("长虹电视机，打开");
		}
	}
```

方式二：构造方法传递依赖

```java
// 方式2: 通过构造方法依赖传递
	interface IOpenAndClose {
		public void open(); // 抽象方法
	}

	interface ITV { // ITV接口
		public void play();
	}

	class OpenAndClose implements IOpenAndClose {
		public ITV tv; // 成员

		public OpenAndClose(ITV tv) { // 构造器
			this.tv = tv;
		}

		public void open() {
			this.tv.play();
		}
	}
```



方式三：setter方法的传递依赖

```java
// 方式3 , 通过setter方法传递
interface IOpenAndClose {
	public void open(); // 抽象方法

	public void setTv(ITV tv);
}

interface ITV { // ITV接口
	public void play();
}

class OpenAndClose implements IOpenAndClose {
	private ITV tv;

	public void setTv(ITV tv) {
		this.tv = tv;
	}

	public void open() {
		this.tv.play();
	}
}

class ChangHong implements ITV {

	@Override
	public void play() {
		// TODO Auto-generated method stub
		System.out.println("长虹电视机，打开");
	}
	 
}
```

依赖倒置原则注意事项

1： 底层模块尽量都要其有其抽象类和接口

2： 变量的声明类型劲量是抽象类和接口。这样我们的变量的引用和实际对象之间就存在一个缓冲层，利于程序的扩展和优化



### 里氏替换原则

里氏替换原则主要就是告诉我们继承需要注意什么： 子类**尽量**不要重写父类方法。

所有能够使用基类的地方，都必须能够透明的使用其子类对象。

我们在进行面向对象编程的时候，不可避免的会使用到继承，但是我们的继承其实也存在一定的弊端。继承中隐含着这样的一层含义，父类中凡是已经实现好的方法，实际上是在设定规范和契约。 继承在带来便利的同时，也增加了对象间的耦合性。如果一个类被其他的子类所继承，则当这个类需要进行修改时，必须考虑到所有的子类。父类修改了，子类可能会出现故障。

```java
public class Liskov {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		A a = new A();
		System.out.println("11-3=" + a.func1(11, 3));
		System.out.println("1-8=" + a.func1(1, 8));

		System.out.println("-----------------------");
		B b = new B();
		System.out.println("11-3=" + b.func1(11, 3));//这里本意是求出11-3
		System.out.println("1-8=" + b.func1(1, 8));// 1-8
		System.out.println("11+3+9=" + b.func2(11, 3));
	}

}

// A类
class A {
	// 返回两个数的差
	public int func1(int num1, int num2) {
		return num1 - num2;
	}
}

// B类继承了A
// 增加了一个新功能：完成两个数相加,然后和9求和
class B extends A {
	//这里，重写了A类的方法, 可能是无意识
	public int func1(int a, int b) {
		return a + b;
	}

	public int func2(int a, int b) {
		return func1(a, b) + 9;
	}
}
```

在实际的编程过程中，我们通常会通过重写父类的方法来完成新的功能，这样写虽然比较简单，但是整个继承体系的复用性会比较差，特别是在多态运行比较频繁的时候更加容易出错。

根据里氏替换原则，通常的改进方法是：原来的父类和子类都继承一个更通俗的基类，去掉原来的继承关系，采用依赖，聚合，组合的关系替代。

```java
public class Liskov {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		A a = new A();
		System.out.println("11-3=" + a.func1(11, 3));
		System.out.println("1-8=" + a.func1(1, 8));

		System.out.println("-----------------------");
		B b = new B();
		//因为B类不再继承A类，因此调用者，不会再func1是求减法
		//调用完成的功能就会很明确
		System.out.println("11+3=" + b.func1(11, 3));//这里本意是求出11+3
		System.out.println("1+8=" + b.func1(1, 8));// 1+8
		System.out.println("11+3+9=" + b.func2(11, 3));
		
		
		//使用组合仍然可以使用到A类相关方法
		System.out.println("11-3=" + b.func3(11, 3));// 这里本意是求出11-3
		

	}

}

//创建一个更加基础的基类
class Base {
	//把更加基础的方法和成员写到Base类
}

// A类
class A extends Base {
	// 返回两个数的差
	public int func1(int num1, int num2) {
		return num1 - num2;
	}
}

// B类继承了A
// 增加了一个新功能：完成两个数相加,然后和9求和
class B extends Base {
	//如果B需要使用A类的方法,使用组合关系
	private A a = new A();
	
	//这里，重写了A类的方法, 可能是无意识
	public int func1(int a, int b) {
		return a + b;
	}

	public int func2(int a, int b) {
		return func1(a, b) + 9;
	}
	
	//我们仍然想使用A的方法
	public int func3(int a, int b) {
		return this.a.func1(a, b);
	}
}

```

![JAVAWEB_DESIGN3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN3.png?raw=true)


### 开闭原则

开闭原则是编程中最基础，和最重要的一个设计原则。(前面的各种原则，最终都是为了实现这个原则)

```
开闭原则： 一个类，模块，函数 应该对扩展开放(对提供功能方来说，他是可以增加功能的)，对修改关闭（对使用方来说，他的修改时不可见的，是关闭的）.
当我们有一个功能扩展的时候，肯定是需要修改代码的，但是我们增加了一个类，增加了一个方法，我们使用方的代码并没有做修改。

总结： 用抽象构建框架，用实现扩展细节

当功能需要改变的时候，应该尽量 增加扩展，而不是修改原有的代码吗
```

代码理解：

```java
public class Ocp {
	public static void main(String[] args) {
		//使用看看存在的问题
		GraphicEditor graphicEditor = new GraphicEditor();
		graphicEditor.drawShape(new Rectangle());
		graphicEditor.drawShape(new Circle());
	}
}

//这是一个用于绘图的类 [使用方]
class GraphicEditor {
	//接收Shape对象，然后根据type，来绘制不同的图形
	public void drawShape(Shape s) {
		if (s.m_type == 1)
			drawRectangle(s);
		else if (s.m_type == 2)
			drawCircle(s);
	}

	//绘制矩形
	public void drawRectangle(Shape r) {
		System.out.println(" 绘制矩形 ");
	}

	//绘制圆形
	public void drawCircle(Shape r) {
		System.out.println(" 绘制圆形 ");
	}

}

//Shape类，基类
class Shape {
	int m_type;
}

class Rectangle extends Shape {
	Rectangle() {
		super.m_type = 1;
	}
}

class Circle extends Shape {
	Circle() {
		super.m_type = 2;
	}
}

```

上面代码的问题：他违反了**对扩展开放，对修改关闭**。当我们需要给类增加新的功能时，如需要画其他图形(三角形)的时候，就必须要修改我们使用方的代码。

```java
public class Ocp {
	public static void main(String[] args) {
		//使用看看存在的问题
		GraphicEditor graphicEditor = new GraphicEditor();
		graphicEditor.drawShape(new Rectangle());
		graphicEditor.drawShape(new Circle());
		graphicEditor.drawShape(new Triangle());
	}
}

//这是一个用于绘图的类 [使用方] 因为这里需要使用Shape 去绘图
class GraphicEditor {
	//接收Shape对象，然后根据type，来绘制不同的图形
	public void drawShape(Shape s) {
		if (s.m_type == 1)
			drawRectangle(s);
		else if (s.m_type == 2)
			drawCircle(s);
		else if (s.m_type == 3)
			drawTriangle(s);
	}

	//绘制矩形
	public void drawRectangle(Shape r) {
		System.out.println(" 绘制矩形 ");
	}

	//绘制圆形
	public void drawCircle(Shape r) {
		System.out.println(" 绘制圆形 ");
	}
	
	//绘制三角形
	public void drawTriangle(Shape r) {
		System.out.println(" 绘制三角形 ");
	}
}

//Shape类，基类
class Shape {
	int m_type;
}

class Rectangle extends Shape {
	Rectangle() {
		super.m_type = 1;
	}
}

class Circle extends Shape {
	Circle() {
		super.m_type = 2;
	}
}

//新增画三角形
class Triangle extends Shape {
	Triangle() {
		super.m_type = 3;
	}
}
```

根据开闭原则进行改进：

思路： 运用多态，将shap做成一个抽象类，提供一个draw()让具体的子类去实现

```java
public class Ocp {

	public static void main(String[] args) {
		//使用看看存在的问题
		GraphicEditor graphicEditor = new GraphicEditor();
		graphicEditor.drawShape(new Rectangle());
		graphicEditor.drawShape(new Circle());
		graphicEditor.drawShape(new Triangle());
		graphicEditor.drawShape(new OtherGraphic());
	}

}

//这是一个用于绘图的类 [使用方]
class GraphicEditor {
	//接收Shape对象，调用draw方法
	public void drawShape(Shape s) {
		s.draw();
	}
	
}

//Shape类，基类
abstract class Shape {
	public abstract void draw();//抽象方法
}

class Rectangle extends Shape {
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		System.out.println(" 绘制矩形 ");
	}
}

class Circle extends Shape {
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		System.out.println(" 绘制圆形 ");
	}
}

//新增画三角形
class Triangle extends Shape {
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		System.out.println(" 绘制三角形 ");
	}
}

//新增一个图形
class OtherGraphic extends Shape {
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		System.out.println(" 绘制其它图形 ");
	}
}
```



### 迪米特法则

迪米特法则(Demeter Principle)又叫**最少知道原则**，即一个类对自己依赖的类知道的越少越好。也就是说，对于
被依赖的类不管多么复杂，都尽量将逻辑封装在类的内部。对外除了提供的 public 方法，不对外泄露任何信息。(降低耦合)。

应用实例：有一个学校，下属有各个学院和总部，现要求打印出学校总部员工 ID 和学院员工的 id

```java
//客户端
public class Demeter1 {
	public static void main(String[] args) {
		//创建了一个 SchoolManager 对象
		SchoolManager schoolManager = new SchoolManager();
		//输出学院的员工id 和  学校总部的员工信息
		schoolManager.printAllEmployee(new CollegeManager());
	}

}


//学校总部员工类
class Employee {
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
}

//管理学院员工的管理类
class CollegeManager {
	//返回学院的所有员工
	public List<CollegeEmployee> getAllEmployee() {
		List<CollegeEmployee> list = new ArrayList<CollegeEmployee>();
		for (int i = 0; i < 10; i++) { //这里我们增加了10个员工到 list
			CollegeEmployee emp = new CollegeEmployee();
			emp.setId("学院员工id= " + i);
			list.add(emp);
		}
		return list;
	}
}



//学院的员工类
class CollegeEmployee {
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
}
//学校管理类

//分析 SchoolManager 类的直接朋友类有哪些 Employee、CollegeManager
//CollegeEmployee 不是 直接朋友 而是一个陌生类，这样违背了 迪米特法则 
class SchoolManager {
	//返回学校总部的员工
	public List<Employee> getAllEmployee() {
		List<Employee> list = new ArrayList<Employee>();
		
		for (int i = 0; i < 5; i++) { //这里我们增加了5个员工到 list
			Employee emp = new Employee();
			emp.setId("学校总部员工id= " + i);
			list.add(emp);
		}
		return list;
	}

	//该方法完成输出学校总部和学院员工信息(id)
	void printAllEmployee(CollegeManager sub) {
		//分析问题
		//1. 这里的 CollegeEmployee 不是  SchoolManager的直接朋友
		//2. CollegeEmployee 是以局部变量方式出现在 SchoolManager
		//3. 违反了 迪米特法则  带来的问题是：类SchoolManager对自己的依赖CollegeManager ，知道的多，耦合性太强，如果CollegeManager的相关逻辑变量，很大程度上需要修改SchoolManager的逻辑。违反了开闭原则
		
		//获取到学院员工
		List<CollegeEmployee> list1 = sub.getAllEmployee();
		System.out.println("------------学院员工------------");
		for (CollegeEmployee e : list1) {
			System.out.println(e.getId());
		}
		//获取到学校总部员工
		List<Employee> list2 = this.getAllEmployee();
		System.out.println("------------学校总部员工------------");
		for (Employee e : list2) {
			System.out.println(e.getId());
		}
	}
}

```

改进：

前面设计的问题在于 SchoolManager 中，CollegeEmployee 类并不是 SchoolManager 类的直接朋友 (分析)
按照迪米特法则，应该避免类中出现这样非直接朋友关系的耦合

```java
//客户端
public class Demeter1 {

	public static void main(String[] args) {
		System.out.println("~~~使用迪米特法则的改进~~~");
		//创建了一个 SchoolManager 对象
		SchoolManager schoolManager = new SchoolManager();
		//输出学院的员工id 和  学校总部的员工信息
		schoolManager.printAllEmployee(new CollegeManager());

	}

}


//学校总部员工类
class Employee {
	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}


//学院的员工类
class CollegeEmployee {
	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}


//管理学院员工的管理类
class CollegeManager {
	//返回学院的所有员工
	public List<CollegeEmployee> getAllEmployee() {
		List<CollegeEmployee> list = new ArrayList<CollegeEmployee>();
		for (int i = 0; i < 10; i++) { //这里我们增加了10个员工到 list
			CollegeEmployee emp = new CollegeEmployee();
			emp.setId("学院员工id= " + i);
			list.add(emp);
		}
		return list;
	}
	
	//输出学院员工的信息
	public void printEmployee() {
		//获取到学院员工
		List<CollegeEmployee> list1 = getAllEmployee();
		System.out.println("------------学院员工------------");
		for (CollegeEmployee e : list1) {
			System.out.println(e.getId());
		}
	}
}

//学校管理类

//分析 SchoolManager 类的直接朋友类有哪些 Employee、CollegeManager
//CollegeEmployee 不是 直接朋友 而是一个陌生类，这样违背了 迪米特法则 
class SchoolManager {
	//返回学校总部的员工
	public List<Employee> getAllEmployee() {
		List<Employee> list = new ArrayList<Employee>();
		
		for (int i = 0; i < 5; i++) { //这里我们增加了5个员工到 list
			Employee emp = new Employee();
			emp.setId("学校总部员工id= " + i);
			list.add(emp);
		}
		return list;
	}

	//该方法完成输出学校总部和学院员工信息(id)
	void printAllEmployee(CollegeManager sub) {
		
		//分析问题
		//1. 将输出学院的员工方法，封装到CollegeManager (就是你怎么做的不要告诉我，我直接进行调用就可以了)
		sub.printEmployee();
	
		//获取到学校总部员工
		List<Employee> list2 = this.getAllEmployee();
		System.out.println("------------学校总部员工------------");
		for (Employee e : list2) {
			System.out.println(e.getId());
		}
	}
}
```

```
迪米特法则注意事项和细节
1) 迪米特法则的核心是降低类之间的耦合
2) 但是注意：由于每个类都减少了不必要的依赖，因此迪米特法则只是要求降低类间(对象间)耦合关系， 并不是
要求完全没有依赖关系
```



### 合成复用原则

原则是**尽量**使用合成/聚合的方式，而不是使用继承

什么是合成/聚合？ 

![JAVAWEB_DESIGN4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN4.png?raw=true)

### 设计原则的核心思想总结：

```
1) 找出应用中可能需要变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起。
2) 针对接口编程，而不是针对实现编程。
3) 为了交互对象之间的松耦合设计而努力
```



## UML

UML 就是用来描述的类与类之间关系的一个画图语言工具。

![JAVAWEB_DESIGN5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN5.png?raw=true)

使用 UML 来建模，常用的工具有 Rational Rose , 也可以使用一些插件来建模

（Eclipse安装AmaterasUML插件，Idea 中安装 PlantUML插件） 

（https://www.jianshu.com/p/a6bd7e3048ef） 和

（https://blog.csdn.net/qq_21383435/article/details/80559284）

画UML图就是将自己的思想描述给别人看，关键在于机构，思路清晰。

### 类图

描述类与类之间的关系

```
类之间的关系：依赖、泛化（继承）、实现、关联、聚合与组合。
```

类图举例：

代码

```java
public class Person{ //代码形式->类图
	private Integer id;
	private String name;
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return  name;
	}
}
```

类图：

![JAVAWEB_DESIGN6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN6.png?raw=true)



#### 依赖

类A如果 在类中用到了类B,则我们认为他们之间有依赖关系，我们就说类A依赖于类B 。 (没有B,类A连编译都无法通过)

类图举例：

代码

```java
public class PersonServiceBean {
	private PersonDao personDao;// 类
	public void save(Person person) {
	}
	public IDCard getIDCard(Integer personid) {
	}
	public void modify() {
		Department department = new Department();
	}
}

public class PersonDao {
}
public class IDCard {
}

public class Person {
}

public class Department {
}
```

类图：

![JAVAWEB_DESIGN7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN7.png?raw=true)

```
依赖小结
1) 类中用到了对方 (局部变量)
2) 如果是类的成员属性
3) 如果是方法的返回类型
4) 是方法接收的参数类型
5) 方法中使用到
```



#### 泛化（继承）

泛化也就是继承，是依赖关系的特例。	

类图举例：

代码

```java
public abstract class DaoSupport {
	public void save(Object entity) {
	}

	public void delete(Object id) {
	}
}

public class PersonServiceBean extends Daosupport {
}
```

类图：

![JAVAWEB_DESIGN8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN8.png?raw=true)

```
泛化小结:
1) 泛化关系实际上就是继承关系
2) 如果 A 类继承了 B 类，我们就说 A 和 B 存在泛化关系
```



#### 实现

实现关系实际上就是 A 类实现 B 接口，他是依赖关系的特例

类图举例：

代码

```java
public interface PersonService {
	public void delete(Interger id);
}

public class PersonServiceBean implements PersonService {
	public void delete(Interger id) {
	}
}
```

类图：

![JAVAWEB_DESIGN9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN9.png?raw=true)


#### 关联

关联关系仍然是依赖关系的特例。他描述的是类之间关系的导航性，单向，双向，一对一，一对多，多对多

![JAVAWEB_DESIGN10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN10.png?raw=true)

#### 聚合

聚合也是依赖关系的特例，表示的是整体和部分的关系。**整体和部分可以分开**就是聚合，不能分开就是组合关系。

类图举例：

如：一台电脑由键盘(keyboard)、显示器(monitor)，鼠标等组成；组成电脑的各个配件是可以从电脑上分离出来
的，使用带空心菱形的实线来表示

代码

```java
public class Computer {
	private Mouse mouse ; //鼠标可以和computer可分离
	private Moniter moniter;//显示器可以和Computer可分离
	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}
	public void setMoniter(Moniter moniter) {
		this.moniter = moniter;
	}	
}
```

类图：

![JAVAWEB_DESIGN11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN11.png?raw=true)

上面电脑和鼠标之间的关系，如果我们认为电脑和鼠标之间的关系不可分离，那么他们之间的关系就升级成组合。 

组合关系在代码中的具体体现一般为

```java
public class Computer {
	private Mouse mouse = new Mouse(); //鼠标可以和computer不能分离
	private Moniter moniter = new Moniter();//显示器可以和Computer不能分离
```

![JAVAWEB_DESIGN12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN12.png?raw=true)

#### 组合

组合关系：也是整体与部分的关系，但是**整体与部分不可以分开**。

类图举例：

再看一个案例：在程序中我们定义实体：Person 与 IDCard、Head, 那么 Head 和 Person 就是 组合，IDCard 和
Person 就是聚合。

代码

```java
public class Person {
	private IDCard card;
	private Head head = new Head(); //不可分割
}

public class IDCard {
}

public class Head {
}
```

类图：

![JAVAWEB_DESIGN13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN13.png?raw=true)

(他们不可分割，同生共死)

## 二十三中设计模式

设计模式学习步骤;

```
应用场景->设计模式->剖析原理->分析实现步骤(图解)->代码实现-> 框架或项目源码分析(找到使用的地
方) 
```



### 创建型模式

他注重的是对**对象的创建**应该怎么样去设计。

#### 单例模式

**解决怎么样让这个系统中的某个类的实例对象只会存在一个。** 从设计层面保证这个类的实例对象只能产生一个。并且该类只提供一个取得其对象实例的方法(静态方法)。

```
单例模式有八种方式：
1) 饿汉式(静态常量)
2) 饿汉式（静态代码块）
3) 懒汉式(线程不安全)
4) 懒汉式(线程安全，同步方法)
5) 懒汉式(线程安全，同步代码块)
6) 双重检查
7) 静态内部类
8) 枚举
```

##### 饿汉式(静态常量)

```
饿汉式（静态常量）应用实例  ————步骤如下：
1) 构造器私有化 (防止 new )
2) 类的内部创建对象
3) 向外暴露一个静态的公共方法。getInstance
```

代码实现：

```java
public class SingletonTest01 {
	public static void main(String[] args) {
		//测试
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2); // true
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());
        // 两者hashcode是相同的，说明两者获取的对象是相同的
	}
}

//饿汉式(静态变量)
class Singleton {
	//1. 构造器私有化, 外部能new
	private Singleton() {
		
	}
	
	//2.本类内部创建对象实例
	private final static Singleton instance = new Singleton();
	
	//3. 提供一个公有的静态方法，返回实例对象
	public static Singleton getInstance() {
		return instance;
	}
	
}
```

总结分析：

```

优缺点说明：
1) 优点：这种写法比较简单，就是在类装载的时候就完成实例化。避免了线程同步问题。
2) 缺点：在类装载的时候就完成实例化，没有达到 Lazy Loading 的效果。如果从始至终从未使用过这个实例，则
会造成内存的浪费
3) 这种方式基于 classloder 机制避免了多线程的同步问题，不过，instance 在类装载时就实例化，在单例模式中大
多数都是调用 getInstance 方法，但是导致类装载的原因有很多种，因此不能确定有其他的方式（或者其他的静
态方法）导致类装载，这时候初始化 instance 就没有达到 lazy loading 的效果
4) 结论：这种单例模式可用，可能造成内存浪费
```



#####  饿汉式（静态代码块）

```java
public class SingletonTest02 {

	public static void main(String[] args) {
		//测试
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2); // true
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());
	}

}

//饿汉式(静态变量)

class Singleton {
	
	//1. 构造器私有化, 外部不能new
	private Singleton() {
		
	}
	
	//2.本类内部创建对象实例
	private  static Singleton instance;
	
	static { // 在静态代码块中，创建单例对象
		instance = new Singleton();
	}
	
	//3. 提供一个公有的静态方法，返回实例对象
	public static Singleton getInstance() {
		return instance;
	}
	
}
```

这种方式的优缺点和上面的方式其实类似，只不过将类实例化的过程放在了静态代码块中，也是在类装载的时候，就执
行静态代码块中的代码，初始化类的实例。可能会导致内存的浪费

#####  懒汉式(线程不安全)

```java
public class SingletonTest03 {

	public static void main(String[] args) {
		System.out.println("懒汉式1 ， 线程不安全~");
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2); // true
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());
	}

}

class Singleton {
	private static Singleton instance;
	
	private Singleton() {}
	
	//提供一个静态的公有方法，当使用到该方法时，才去创建 instance
	//即懒汉式
	public static Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
}
```

总结分析：

```
总结优缺点说明：
1) 起到了 Lazy Loading 的效果，但是只能在单线程下使用。
2) 如果在多线程下，一个线程进入了 if (singleton == null)判断语句块，还未来得及往下执行，另一个线程或得了执行权也通过了这个判断语句，这时候几个线程同时都进入生产对象的语句，这时便会产生多个实例。所以在多线程环境下不可使用这种方式
3) 结论：在实际开发中，不要使用这种方式
```



##### 懒汉式(线程安全，同步方法)

对上面懒汉式(线程不安全的改进)

```java
public class SingletonTest04 {
	public static void main(String[] args) {
		System.out.println("懒汉式2 ， 线程安全~");
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2); // true
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());
	}

}

// 懒汉式(线程安全，同步方法)
class Singleton {
	private static Singleton instance;
	
	private Singleton() {}
	
	//提供一个静态的公有方法，加入同步处理的代码，解决线程安全问题
	//当有一个线程在进行getInstance()的时候，我们将这个方法锁住，不让其他线程进去。
    //相当于进入方法后就将们锁住了，只有当他执行完之后，开门，其他线程才能进去执行。
	public static synchronized Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
}
```

总结分析：

```
 分析优缺点说明：
1) 解决了线程安全问题
2) 效率太低了，每个线程在想获得类的实例时候，执行 getInstance()方法都要进行同步。而其实这个方法只执行
一次实例化代码就够了，后面的想获得该类实例，直接 return 就行了。现在的现状是每一次都要去进行同步，方法进行同步效率太低
3) 结论：在实际开发中，不推荐使用这种方式
```



##### 懒汉式(线程不安全，同步代码块)

对上面方法的进一步改进

```java
// 懒汉式(线程不安全，同步方法)
class Singleton {
	private static Singleton instance;
	private Singleton() {}
	public static  Singleton getInstance() {
		if(instance == null) {
            synchronized(Singleton.class){ //他只对创建实例的代码进行同步，本意是对上面同步方法效率低进行改进，但是这样是没办法解决线程安全的，如果两个线程都进入了判空代码块中，仍然还是会生成多个实例。但仍然是单例的一中实现方式
			instance = new Singleton();
            }
		}
		return instance;
	}
}
```

在实际开发中不使用这种方式。

##### 双重检查（doublecheck 懒汉式线程安全）

这种方式推荐使用，他能解决两个问题，一个是能解决线程安全问题，二是能够解决效率问题

```java
// 懒汉式(线程安全，同步方法)
class Singleton {
	private static volatile Singleton instance; 
    // volatile 能让修改立即保存，相当于一个轻量级的synchronized关键字
	private Singleton() {}
	//提供一个静态的公有方法，加入双重检查代码，解决线程安全问题, 同时解决懒加载问题
	//同时保证了效率, 推荐使用
	public static  Singleton getInstance() {
		if(instance == null) {
			synchronized (Singleton.class) {
				if(instance == null) {
					instance = new Singleton();
				}
			}
			
		}
		return instance;
	}
}
// 在上一个方法改进方案的基础上synchronized关键字中再加入个if判空是因为，如果在synchronized关键字之上同时有两个线程进入，然后其中一个线程进入synchronized关键字中，生成了一个实例，因为instance加了volatile修饰，他的修改会立即上传。第二个线程仍旧会进入synchronized关键字中，但是现在是因为加入了一个if判空，这个线程就不会再生成实例了
// 在synchronized关键字之外再包一层if,是为了解决同步效率问题，当instance不为空之后，剩余的线程就不会进入到同步代码块中。
```

分析总结

```
优缺点说明：
1) Double-Check 概念是多线程开发中常使用到的，如代码中所示，我们进行了两次 if (singleton == null)检查，这
样就可以保证线程安全了。
2) 这样，实例化代码只用执行一次，后面再次访问时，判断 if (singleton == null)，直接 return 实例化对象，也避
免的反复进行方法同步.
3) 线程安全；延迟加载；效率较高
4) 结论：在实际开发中，推荐使用这种单例设计模式
```



##### 静态内部类

```java
// 静态内部类完成， 推荐使用
静态内部类的特点： 当我们Singleton进行装载的时候，我们SingletonInstance不会进行装载。只有当我们Singleton调用我们SingletonInstance的时候，SingletonInstance 才会进行装载，只会装载一次，当jvm类装载的时候线程是安全的。侧面完成了懒加载
class Singleton {
	//构造器私有化
	private Singleton() {}
	
	//写一个静态内部类,该类中有一个静态属性 Singleton
	private static class SingletonInstance {
		private static final Singleton INSTANCE = new Singleton(); 
	}
	
	//提供一个静态的公有方法，直接返回SingletonInstance.INSTANCE
	public static  Singleton getInstance() {	
		return SingletonInstance.INSTANCE;
	}
}
//总结： 推荐使用
```

分析总结：

```
优缺点说明：
1) 这种方式采用了类装载的机制来保证初始化实例时只有一个线程。
2) 静态内部类方式在 Singleton 类被装载时并不会立即实例化，而是在需要实例化时，调用 getInstance 方法，才
会装载 SingletonInstance 类，从而完成 Singleton 的实例化。
3) 类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM 帮助我们保证了线程的安全性，在类进行
初始化时，别的线程是无法进入的。
4) 优点：避免了线程不安全，利用静态内部类特点实现延迟加载，效率高
5) 结论：推荐使用.
```



##### 枚举

在java中枚举常被当作声明常量来处理'public static fianl..'，但其实他可以做到更多事情。

枚举可以实现单例模式的一个实际原理就是，枚举类对象是静态单实例的。 (他是语法级别的单例模式。java从语法就定义了枚举类对象是单例的)

代码论证

```java
enum DD{
	instance
}

public class DDD{
	public static void main(String[] args) {
		new DD(); //这里编译报错，枚举对象无法被new
	}
}
```



```java
//什么一个枚举类
enum SingletonC implements Serializable {
    INSTANCE; //属性
    private String field;
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
}

//枚举类的使用
SingletonC singleton = SingletonC.INSTANCE;
```

```java
public class SingletonTest08 {
	public static void main(String[] args) {
		Singleton instance = Singleton.INSTANCE;
		Singleton instance2 = Singleton.INSTANCE;
		System.out.println(instance == instance2);
		System.out.println(instance.hashCode());
		System.out.println(instance2.hashCode()); //两种hashcode是相等的
		instance.sayOK(); //这里instance 就可以当一个单例对象来使用
	}
}

//使用枚举，可以实现单例, 推荐
enum Singleton {
	INSTANCE; //属性
	public void sayOK() {
		System.out.println("ok~");
	}
}
```

分析总结：

```
优缺点说明：
1) 这借助 JDK1.5 中添加的枚举来实现单例模式。不仅能避免多线程同步问题，而且还能防止反序列化重新创建
新的对象。
2) 这种方式是 Effective Java 作者 Josh Bloch 提倡的方式
3) 结论：推荐使用，单例最优实现
```



##### 单例模式在 JDK 应用的源码分析

jdk java.lang 中的Runtime，就是经典的单例饿汉式的应用

```java
public class Runtime {
    private static Runtime currentRuntime = new Runtime();
    /**
     * @return  the <code>Runtime</code> object associated with the current
     *          Java application.
     */
    public static Runtime getRuntime() {
        return currentRuntime;
    }

    /** Don't let anyone else instantiate this class */
    private Runtime() {}
```

![JAVAWEB_DESIGN14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN14.png?raw=true)

单例模式中的注意事项：

```
单例模式注意事项和细节说明
1) 单例模式保证了 系统内存中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使
用单例模式可以提高系统性能
2) 当想实例化一个单例类的时候，必须要记住使用相应的获取对象的方法，而不是使用 new
3) 单例模式使用的场景：需要频繁的进行创建和销毁的对象、创建对象时耗时过多或耗费资源过多(即：重量级
对象)，但又经常用到的对象、工具类对象、频繁访问数据库或文件的对象(比如数据源、session 工厂等)
```



#### 工厂模式

##### 简单工厂模式

###### 需求

```
看一个披萨订购的项目：要便于披萨种类的扩展，要便于维护
1) 披萨的种类很多(比如 GreekPizz、CheesePizz 等)
2) 披萨的制作有 prepare，bake, cut, box
3) 完成披萨店订购功能。
```

###### 传统方式

1) 思路分析(类图)

![JAVAWEB_DESIGN15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN15.png?raw=true)

代码实现：

```java
//将Pizza 类做成抽象
public abstract class Pizza {
	protected String name; //名字

	//准备原材料, 不同的披萨不一样，因此，我们做成抽象方法
	public abstract void prepare();

	
	public void bake() {
		System.out.println(name + " baking;");
	}

	public void cut() {
		System.out.println(name + " cutting;");
	}

	//打包
	public void box() {
		System.out.println(name + " boxing;");
	}

	public void setName(String name) {
		this.name = name;
	}
}

========
public class CheesePizza extends Pizza {
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		System.out.println(" 给制作奶酪披萨 准备原材料 ");
	}

}

public class GreekPizza extends Pizza {

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		System.out.println(" 给希腊披萨 准备原材料 ");
	}

}
```

订购披萨：

```java
public class OrderPizza {
	// 构造器
	public OrderPizza() {
		Pizza pizza = null;
		String orderType; // 订购披萨的类型
		do {
			orderType = getType();
			if (orderType.equals("greek")) {
				pizza = new GreekPizza();
				pizza.setName(" 希腊披萨 ");
			} else if (orderType.equals("cheese")) {
				pizza = new CheesePizza();
				pizza.setName(" 奶酪披萨 ");
			} else {
				break;
			}
			//输出pizza 制作过程
			pizza.prepare();
			pizza.bake();
			pizza.cut();
			pizza.box();

		} while (true);
	}
    
    // 写一个方法，可以获取客户希望订购的披萨种类
	private String getType() {
		try {
			BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("input pizza 种类:");
			String str = strin.readLine();
			return str;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
```

整合类图（思路）： 还需要一个披萨店端来使用这个披萨订购器

![JAVAWEB_DESIGN16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN16.png?raw=true)

披萨店：

```java
// 相当于一个客户端，发出订购
public class PizzaStore {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new OrderPizza();
	}
}
```

总结分析：

```
传统的方式的优缺点
1) 优点是比较好理解，简单易操作。
2) 缺点是违反了设计模式的 ocp 原则，即对扩展开放，对修改关闭。即当我们给类增加新功能的时候，尽量不修改代码，或者尽可能少修改代码.
3) 比如我们这时要新增加一个 Pizza 的种类(Pepper 披萨)，我们需要做如下修改.
```

如果我们增加一个 Pizza 类，只要是订购 Pizza 的代码都需要修改.

![JAVAWEB_DESIGN17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN17.png?raw=true)

新增pizza类

```java
public class PepperPizza extends Pizza {

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		System.out.println(" 给胡椒披萨准备原材料 ");
	}

}
```

pizza订购器需要修改

```java
		public OrderPizza() {
			Pizza pizza = null;
			String orderType; // 订购披萨的类型
			do {
				orderType = getType();
					if (orderType.equals("greek")) {
				pizza = new GreekPizza();
				pizza.setName(" 希腊披萨 ");
			} else if (orderType.equals("cheese")) {
				pizza = new CheesePizza();
				pizza.setName(" 奶酪披萨 ");
			} else if (orderType.equals("pepper")) {
				pizza = new PepperPizza();
				pizza.setName("胡椒披萨");
			} else {
				break;
			}

				//输出pizza 制作过程
				pizza.prepare();
				pizza.bake();
				pizza.cut();
				pizza.box();

			} while (true);
		}
```

当有多个披萨订购器使用到pizza的子类的时候，那需要修改地方就比较多。

![JAVAWEB_DESIGN18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN18.png?raw=true)

###### 简单工厂模式改进

改进思路：

```
改进的思路分析
分析：修改代码可以接受，但是如果我们在其它的地方也有创建 Pizza 的代码，就意味着，也需要修改，而创建 Pizza
的代码，往往有多处。

思路：把创建 Pizza 对象封装到一个类中，这样我们有新的 Pizza 种类时，只需要修改该类就可，其它有创建到 Pizza
对象的代码就不需要修改了.-> 简单工厂模式 
```

![JAVAWEB_DESIGN19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN19.png?raw=true)

简单工厂模式的基本介绍：

```
基本介绍
1) 简单工厂模式是属于创建型模式，是工厂模式的一种。简单工厂模式是由一个工厂对象决定创建出哪一种产品
类的实例。简单工厂模式是工厂模式家族中最简单实用的模式 (使用的最多的)
2) 简单工厂模式：定义了一个创建对象的类，由这个类来封装实例化对象的行为(代码)
3) 在软件开发中，当我们会用到大量的创建某种、某类或者某批对象时，就会使用到工厂模式
```

简单工厂的代码：

```java
//简单工厂类
public class SimpleFactory {
	//简单工厂模式 也叫 静态工厂模式 
	//根据orderType 返回对应的Pizza 对象
	public static Pizza createPizza2(String orderType) {
		Pizza pizza = null;
		System.out.println("使用简单工厂模式2");
		if (orderType.equals("greek")) {
			pizza = new GreekPizza();
			pizza.setName(" 希腊披萨 ");
		} else if (orderType.equals("cheese")) {
			pizza = new CheesePizza();
			pizza.setName(" 奶酪披萨 ");
		} else if (orderType.equals("pepper")) {
			pizza = new PepperPizza();
			pizza.setName("胡椒披萨");
		}
		return pizza;
	}
}
```

orderPizza使用我们的简单工厂

```java
public class OrderPizza {
	//定义一个简单工厂对象
	SimpleFactory simpleFactory;
	Pizza pizza = null;
	
	//构造器
	public OrderPizza(SimpleFactory simpleFactory) {
		setFactory(simpleFactory);
	}
	
	public void setFactory(SimpleFactory simpleFactory) {
		String orderType = ""; //用户输入的
		this.simpleFactory = simpleFactory; //设置简单工厂对象
		do {
			orderType = getType(); 
			pizza = this.simpleFactory.createPizza(orderType);
			
			//输出pizza
			if(pizza != null) { //订购成功
				pizza.prepare();
				pizza.bake();
				pizza.cut();
				pizza.box();
			} else {
				System.out.println(" 订购披萨失败 ");
				break;
			}
		}while(true);
	}
}
```

店端调用：

```java
//相当于一个客户端，发出订购
public class PizzaStore {
	public static void main(String[] args) {
		//使用简单工厂模式
		new OrderPizza(new SimpleFactory());
		System.out.println("~~退出程序~~");
	}
}
```

这样改进之后，我们以后如果要拓展，增加一些pizza种类，我们就只需要修改简单工厂就可以。

![JAVAWEB_DESIGN20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN20.png?raw=true)

##### 工厂方法模式

###### 需求

```
披萨项目新的需求：客户在点披萨时，可以点不同口味的披萨，比如 北京的奶酪 pizza、北京的胡椒 pizza 或
者是伦敦的奶酪 pizza、伦敦的胡椒 pizza。

思路 1
使用简单工厂模式，创建不同的简单工厂类，比如 BJPizzaSimpleFactory、LDPizzaSimpleFactory 等等.从当前
这个案例来说，也是可以的，但是考虑到项目的规模，以及软件的可维护性、可扩展性并不是特别好

思路 2
使用工厂方法模式
```

**工厂方法模式简单介绍**

![JAVAWEB_DESIGN21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN21.png?raw=true)

```
1) 工厂方法模式设计方案：，针对前面的案例，将披萨项目的实例化功能抽象成抽象方法，在不同的口味点餐子类中具体实现。
2) 工厂方法模式：定义了一个创建对象(pizza)的抽象方法(createPizza抽象)，由子类决定要实例化的类。工厂方法模式将对象的实例化推迟到子类
```

代码实现：

```java
/将Pizza 类做成抽象
public abstract class Pizza {
   //...... 复用原先的pizza类
}
public class LDPepperPizza extends Pizza{
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		setName("伦敦的胡椒pizza");
		System.out.println(" 伦敦的胡椒pizza 准备原材料");
	}
}
public class BJCheesePizza extends Pizza {

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		setName("北京的奶酪pizza");
		System.out.println(" 北京的奶酪pizza 准备原材料");
	}

}
```

pizza的生产者

```java
public abstract class OrderPizza { // 在这里他是扮演者工厂类的角色

	//定义一个抽象方法，createPizza , 让各个工厂子类自己实现
	abstract Pizza createPizza(String orderType);
	
	// 构造器
	public OrderPizza() {
		Pizza pizza = null;
		String orderType; // 订购披萨的类型
		do {
			orderType = getType();
			pizza = createPizza(orderType); //抽象方法，由工厂子类完成
			//输出pizza 制作过程
			pizza.prepare();
			pizza.bake();
			pizza.cut();
			pizza.box();
			
		} while (true);
	}	

	// 写一个方法，可以获取客户希望订购的披萨种类
	private String getType() {
	//.....复用之前的
	}

}
```

子类：

```java
public class BJOrderPizza extends OrderPizza {
	@Override
	Pizza createPizza(String orderType) {
	
		Pizza pizza = null;
		if(orderType.equals("cheese")) {
			pizza = new BJCheesePizza();
		} else if (orderType.equals("pepper")) {
			pizza = new BJPepperPizza();
		}
		// TODO Auto-generated method stub
		return pizza;
	}

}

=====================================
public class LDOrderPizza extends OrderPizza {
	@Override
	Pizza createPizza(String orderType) {
	
		Pizza pizza = null;
		if(orderType.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (orderType.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		// TODO Auto-generated method stub
		return pizza;
	}

}
```

使用：

```java
public class PizzaStore {
	public static void main(String[] args) {
		String loc = "bj";
		if (loc.equals("bj")) {
			//创建北京口味的各种Pizza
			new BJOrderPizza();
		} else {
			//创建伦敦口味的各种Pizza
			new LDOrderPizza();
		}
		// TODO Auto-generated method stub
	}
}
```



##### 抽象工厂模式

**基本介绍**

```
1) 抽象工厂模式：定义了一个 interface 用于创建相关或有依赖关系的对象簇，而无需指明具体的类
2) 抽象工厂模式可以将简单工厂模式和工厂方法模式进行整合。
3) 从设计层面看，抽象工厂模式就是对简单工厂模式的改进(或者称为进一步的抽象)。
4) 将工厂抽象成两层，AbsFactory(抽象工厂) 和 具体实现的工厂子类。程序员可以根据创建对象类型使用对应
的工厂子类。这样将单个的简单工厂类变成了工厂簇，更利于代码的维护和扩展。
```

上一个需求，使用抽象工厂模式如何改进：

![JAVAWEB_DESIGN22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN22.png?raw=true)

代码实现：

pizza和其种类，沿用上面工厂方法模式的

抽象层：

```java
//一个抽象工厂模式的抽象层(接口)
public interface AbsFactory {
	//让下面的工厂子类来 具体实现
	public Pizza createPizza(String orderType);
}

//这是工厂子类
public class BJFactory implements AbsFactory {

	@Override
	public Pizza createPizza(String orderType) {
		System.out.println("~使用的是抽象工厂模式~");
		// TODO Auto-generated method stub
		Pizza pizza = null;
		if(orderType.equals("cheese")) {
			pizza = new BJCheesePizza();
		} else if (orderType.equals("pepper")){
			pizza = new BJPepperPizza();
		}
		return pizza;
	}

}

public class LDFactory implements AbsFactory {

	@Override
	public Pizza createPizza(String orderType) {
		System.out.println("~使用的是抽象工厂模式~");
		Pizza pizza = null;
		if (orderType.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (orderType.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		return pizza;
	}

}
```

pizza订购器

```java
public class OrderPizza {

	AbsFactory factory;

	// 构造器
	public OrderPizza(AbsFactory factory) {
		setFactory(factory);
	}

	private void setFactory(AbsFactory factory) {
		Pizza pizza = null;
		String orderType = ""; // 用户输入
		this.factory = factory;
		do {
			orderType = getType();
			// factory 可能是北京的工厂子类，也可能是伦敦的工厂子类
			pizza = factory.createPizza(orderType);
			if (pizza != null) { // 订购ok
				pizza.prepare();
				pizza.bake();
				pizza.cut();
				pizza.box();
			} else {
				System.out.println("订购失败");
				break;
			}
		} while (true);
	}

}
```

店铺端：

```java
public class PizzaStore {
	public static void main(String[] args) {
		//new OrderPizza(new BJFactory());
		new OrderPizza(new LDFactory());
	}
}
```

##### 工厂模式在 JDK-Calendar 应用的源码分析

他使用了简单工厂方法模式：

![JAVAWEB_DESIGN23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN23.png?raw=true)



##### 工厂模式总结

![JAVAWEB_DESIGN24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN24.png?raw=true)

```
1) 工厂模式的意义
将实例化对象的代码提取出来，放到一个类中统一管理和维护，达到和主项目的依赖关系的解耦。从而提高项
目的扩展和维护性。(就是在使用类和产生类之间加一个缓存层)
2) 三种工厂模式 (简单工厂模式、工厂方法模式、抽象工厂模式)
3) 设计模式的依赖抽象原则 (尽量依赖抽象，不要依赖细节-----依赖倒置原则)
 创建对象实例时，不要直接 new 类, 而是把这个 new 类的动作放在一个工厂的方法中，并返回。
这样的好处就是有了一个缓冲层，不要依赖具体的类，而是依赖其抽象
 不要让类继承具体类，而是继承抽象类或者是实现 interface(接口)
 不要覆盖基类中已经实现的方法。
```



#### 原型模式

解决的是我们怎么样去克隆一个对象，深拷贝还是浅拷贝。

##### 克隆羊问题

需求：现在有一只羊 tom，姓名为: tom, 年龄为：1，颜色为：白色，请编写程序创建和 tom 羊 属性完全相同的 10只羊。

##### 传统的解决方法

![JAVAWEB_DESIGN25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN25.png?raw=true)

```java
public class Sheep {
	private String name;
	private int age;
	private String color;
	public Sheep(String name, int age, String color) {
		super();
		this.name = name;
		this.age = age;
		this.color = color;
	}
```

使用方：

```java
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//传统的方法
		Sheep sheep = new Sheep("tom", 1, "白色");
		
		Sheep sheep2 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
		Sheep sheep3 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
		Sheep sheep4 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
		Sheep sheep5 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
		//....
		
		System.out.println(sheep);
		System.out.println(sheep2);
		System.out.println(sheep3);
		System.out.println(sheep4);
		System.out.println(sheep5);
		//...
	}
```

总结分析优缺点：

```
1) 优点是比较好理解，简单易操作。
2) 在创建新的对象时，总是需要重新获取原始对象的属性，如果创建的对象比较复杂时，效率较低
3) 总是需要重新初始化对象(对象会边框)，而不是动态地获得对象运行时的状态, 不够灵活
```

改进思路：

```
思路：Java 中 Object 类是所有类的根类，Object 类提供了一个 clone()方法，该方法可以将一个 Java 对象复制
一份，但是需要实现 clone 的 Java 类必须要实现一个接口 Cloneable，该接口表示该类能够复制且具有复制的能力 =>
原型模式
```



##### 原型模式改进

基本介绍：

```
1) 原型模式(Prototype 模式)是指：用原型实例指定创建对象的种类，并且通过拷贝这些原型，创建新的对象
2) 原型模式是一种创建型设计模式，允许一个对象再创建另外一个可定制的对象，无需知道如何创建的细节
3) 工作原理是:通过将一个原型对象传给那个要发动创建的对象，这个要发动创建的对象通过请求原型对象拷贝它
们自己来实施创建，即 对象.clone()
4) 形象的理解：孙大圣拔出猴毛， 变出其它孙大圣
```

![JAVAWEB_DESIGN26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN26.png?raw=true)

用原型模式来解决克隆羊的问题

```java
public class Sheep implements Cloneable {
	private String name;
	private int age;
	private String color;
	private String address = "蒙古羊";
	public Sheep friend; //是对象, 克隆是会如何处理
	public Sheep(String name, int age, String color) {
		super();
		this.name = name;
		this.age = age;
		this.color = color;
	}
	//----属性对应的set，get方法
	
	//克隆该实例，使用默认的clone方法来完成
	@Override
	protected Object clone()  {	
		Sheep sheep = null;
		try {
			sheep = (Sheep)super.clone();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		// TODO Auto-generated method stub
		return sheep;
	}
	
	
}
```

cline使用：

```java
public class Client {
	public static void main(String[] args) {
		System.out.println("原型模式完成对象的创建");
		// TODO Auto-generated method stub
		Sheep sheep = new Sheep("tom", 1, "白色");
		
		sheep.friend = new Sheep("jack", 2, "黑色");
		
		Sheep sheep2 = (Sheep)sheep.clone(); //克隆
		Sheep sheep3 = (Sheep)sheep.clone(); //克隆
		Sheep sheep4 = (Sheep)sheep.clone(); //克隆
		Sheep sheep5 = (Sheep)sheep.clone(); //克隆
		
		System.out.println("sheep2 =" + sheep2 + "sheep2.friend=" + sheep2.friend.hashCode());
		System.out.println("sheep3 =" + sheep3 + "sheep3.friend=" + sheep3.friend.hashCode());
		System.out.println("sheep4 =" + sheep4 + "sheep4.friend=" + sheep4.friend.hashCode());
		System.out.println("sheep5 =" + sheep5 + "sheep5.friend=" + sheep5.friend.hashCode());
	}
}
```



##### 原型模式在 Spring 框架中源码分析

Spring 中原型 bean 的创建，就是原型模式的应用

![JAVAWEB_DESIGN31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN31.png?raw=true)

![JAVAWEB_DESIGN32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN32.png?raw=true)

##### 原型模式中的浅拷贝和深拷贝

![JAVAWEB_DESIGN27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN27.png?raw=true)

深拷贝就是连他引用的对象也为其申请存储空间进行clone操作(类似于递归深入)

省考班实现方式

```
1) 深拷贝实现方式 1：重写 clone 方法来实现深拷贝
2) 深拷贝实现方式 2：通过对象序列化实现深拷贝(推荐)
```

方式一：

```java
具体需要被拷贝的引用类：
public class DeepCloneableTarget implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	private String cloneName;
	private String cloneClass;

	//构造器
	public DeepCloneableTarget(String cloneName, String cloneClass) {
		this.cloneName = cloneName;
		this.cloneClass = cloneClass;
	}

	//因为该类的属性，都是String , 因此我们这里使用默认的clone完成即可
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

//需要被clone的类
public class DeepProtoType implements Serializable, Cloneable{
	public String name; //String 属性
	public DeepCloneableTarget deepCloneableTarget;// 引用类型
	public DeepProtoType() {
		super();
	}
	
	
	//深拷贝 - 方式 1 使用clone 方法，先处理基本类型，再处理引用类型。但是存在一个问题，如果这里的基本类型很多，这种方式就会存在一个问题
	@Override
	protected Object clone() throws CloneNotSupportedException {	
		Object deep = null;
		//这里完成对基本数据类型(属性)和String的克隆
		deep = super.clone(); 
		//对引用类型的属性，进行单独处理
		DeepProtoType deepProtoType = (DeepProtoType)deep;
		deepProtoType.deepCloneableTarget  = (DeepCloneableTarget)deepCloneableTarget.clone();
		// TODO Auto-generated method stub
		return deepProtoType;
	}
	
	//深拷贝 - 方式2 通过对象的序列化实现 (推荐)
	public Object deepClone() {
		//创建流对象
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;	
		try {
			//序列化
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			//字节数组输出流换成对象输出流
			oos.writeObject(this); //当前这个对象以对象流的方式输出，这里是将this当成一个整体去处理
			
			//反序列化
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			DeepProtoType copyObj = (DeepProtoType)ois.readObject();
			
			return copyObj;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			//关闭流
			try {
				bos.close();
				oos.close();
				bis.close();
				ois.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2.getMessage());
			}
		}
		
	}
	
}

//client 基本同上。
```



##### 原型模式的总结

```
1) 创建新的对象比较复杂时，可以利用原型模式"简化对象的创建过程"，同时也能够提高效率（spring中重复利用了这点）
2) 不用重新初始化对象，而是“动态”地获得对象运行时的状态。 如果原始对象发生变化(增加或者减少属性)，其它克隆对象的也会发生相应的变化，无需修改代码
3) 在实现深克隆的时候可能需要比较复杂的代码
4) 缺点：需要为每一个类配备一个克隆方法，这对全新的类来说不是很难，但对已有的类进行改造时，需要修改
其源代码，违背了 ocp 原则
```



#### 建造者模式

##### 需求——盖房项目

```
1) 需要建房子：盖房子存在一个固定的流程，这一过程为打桩、砌墙、封顶
2) 房子有各种各样的，比如普通房，高楼，别墅，各种房子的过程虽然一样，但是要求不要相同的.
```



##### 传统方式解决需求

![JAVAWEB_DESIGN28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN28.png?raw=true)

代码实现：

```java
public abstract class AbstractHouse {
	//抽象方法，具体的实现方法由子类来实现
	//打地基
	public abstract void buildBasic();
	//砌墙
	public abstract void buildWalls();
	//封顶
	public abstract void roofed();
	
	public void build() {
		buildBasic();
		buildWalls();
		roofed();
	}	
}

具体的子类实现类：

public class CommonHouse extends AbstractHouse {

	@Override
	public void buildBasic() {
		// TODO Auto-generated method stub
		System.out.println(" 普通房子打地基 ");
	}

	@Override
	public void buildWalls() {
		// TODO Auto-generated method stub
		System.out.println(" 普通房子砌墙 ");
	}

	@Override
	public void roofed() {
		// TODO Auto-generated method stub
		System.out.println(" 普通房子封顶 ");
	}

}

实际的使用这
public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommonHouse commonHouse = new CommonHouse();
		commonHouse.build();
}
```



传统方式的分析

```
1) 优点是比较好理解，简单易操作。
2) 设计的程序结构，过于简单，没有设计缓存层对象，程序的扩展和维护不好. 也就是说，这种设计方案，把产
品(即：房子) 和 创建产品的过程(即：建房子流程) 封装在一起，耦合性增强了。
3) 解决方案：将产品和 产品建造过程 解耦 => 建造者模式.
如同生成的产品和产品的生产线其实是可以分开的
```



##### 建造者模式改进

基本介绍

```
1) 建造者模式（Builder Pattern） 又叫生成器模式，是一种对象构建模式。它可以“将复杂对象的建造过程抽象出
来（抽象类别）”，使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
2) 建造者模式 是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建它们，
用户不需要知道内部的具体构建细节。
```

![JAVAWEB_DESIGN29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN29.png?raw=true)

抽象建造者只需要指定具体的建造流程就可以了。具体的实现由具体的建造者来完成(其实际思想和传统实现方式是类似，但是他是将实际建造的产品和建造的过程分离开了的)。


![JAVAWEB_DESIGN30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN30.png?raw=true)

**使用建造者模式来进行改进**

![JAVAWEB_DESIGN33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN33.png?raw=true)

代码实现：

```java
//产品->Product
public class House {
	private String baise; //地基
	private String wall; //墙
	private String roofed;//房顶
	//----属性对应的set,get
}

// 抽象的建造者
public abstract class HouseBuilder {
	protected House house = new House();
	//将建造的流程写好, 抽象的方法
	public abstract void buildBasic();
	public abstract void buildWalls();
	public abstract void roofed();
	
	//建造房子好， 将产品(房子) 返回
	public House buildHouse() {
		return house;
	}	
}

//这里我们将产品的构造过程是写在子类中的，但是产品本身的属性是封装于父类中， 所以我们把制造过程和产品本身分开了。
public class HighBuilding extends HouseBuilder {
	@Override
	public void buildBasic() {
		// TODO Auto-generated method stub
		System.out.println(" 对哦属性house进行操作，，，高楼的打地基100米 ");
	}

	@Override
	public void buildWalls() {
		// TODO Auto-generated method stub
		System.out.println(" 高楼的砌墙20cm ");
	}

	@Override
	public void roofed() {
		// TODO Auto-generated method stub
		System.out.println(" 高楼的透明屋顶 ");
	}
}

//指挥者，这里去指定制作流程，返回产品
//这个类的角色是为了接收不同的子类。来生产不同的产品
public class HouseDirector {
	HouseBuilder houseBuilder = null; 
	//构造器传入 houseBuilder
	public HouseDirector(HouseBuilder houseBuilder) {
		this.houseBuilder = houseBuilder;
	}

	//通过setter 传入 houseBuilder
	public void setHouseBuilder(HouseBuilder houseBuilder) {
		this.houseBuilder = houseBuilder;
	}
	
	//如何处理建造房子的流程，交给指挥者 ，这是为了更加的灵活。可以动态的修改我们的流程
	public House constructHouse() {
		houseBuilder.buildBasic();
		houseBuilder.buildWalls();
		houseBuilder.roofed();
		return houseBuilder.buildHouse();
	}
	
}

cline:
	public static void main(String[] args) {
		//盖普通房子
		CommonHouse commonHouse = new CommonHouse();
		//准备创建房子的指挥者
		HouseDirector houseDirector = new HouseDirector(commonHouse);
		//完成盖房子，返回产品(普通房子)
		House house = houseDirector.constructHouse();
		System.out.println("输出流程");
		System.out.println("--------------------------");
		//盖高楼
		HighBuilding highBuilding = new HighBuilding();
		//重置建造者
		houseDirector.setHouseBuilder(highBuilding);
		//完成盖房子，返回产品(高楼)
		houseDirector.constructHouse();	
		
	}
```



##### 建造者模式在 JDK 的应用和源码分析

java.lang.StringBuilder 中的建造者模式

![JAVAWEB_DESIGN36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN36.png?raw=true)

![JAVAWEB_DESIGN34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN34.png?raw=true)

![JAVAWEB_DESIGN35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN35.png?raw=true)

##### 建造者模式总结和注意事项

```
1) 客户端(使用程序)不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可
以创建不同的产品对象
2) 每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具
体建造者， 用户使用不同的具体建造者即可得到不同的产品对象

3) 可以更加精细地控制产品的创建过程 。将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，
也更方便使用程序来控制创建过程

4) 增加新的具体建造者无须修改原有类库的代码，指挥者类针对抽象建造者类编程，系统扩展方便，符合“开闭
原则”

5) 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，则不适合使
用建造者模式，因此其使用范围受到一定的限制。如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大


抽象工厂模式 VS 建造者模式
抽象工厂模式实现对产品家族的创建，一个产品家族是这样的一系列产品：具有不同分类维度的产品组合，采
用抽象工厂模式不需要关心构建过程，只关心什么产品由什么工厂生产即可。而建造者模式则是要求按照指定
的蓝图建造产品，它的主要目的是通过组装零配件而产生一个新产品

构建者模式需要提供一个具体的流程。 抽象工程模式是创建一个产品，构建者模式是根据蓝图组装一个产品
```



### 结构型模式

他是站在软件结构的角度上思考的。怎么让我们软件的结构更加具有伸缩性，更加具有弹性。更加具有扩展性。

#### 适配器模式

适配器基本介绍：

![JAVAWEB_DESIGN37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN37.png?raw=true)

```
1) 适配器模式(Adapter Pattern)将某个类的接口转换成客户端期望的另一个接口表示，主要目的是兼容性，让原本
因接口不匹配不能一起工作的两个类可以协同工作。其别名为包装器(Wrapper)
2) 适配器模式属于结构型模式
3) 主要分为三类：类适配器模式、对象适配器模式、接口适配器模式

工作原理
1) 适配器模式：将一个类的接口转换成另一种接口.让原本接口不兼容的类可以兼容
2) 从用户的角度看不到被适配者，是解耦的
3) 用户调用适配器转化出来的目标接口方法，适配器再调用被适配者的相关接口方法
```

![JAVAWEB_DESIGN38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN38.png?raw=true)

##### 需求——充电器

```
以生活中充电器的例子来讲解适配器，充电器本身相当于 Adapter，220V 交流电相当于 src (即被适配者)，我 们
的目 dst(即 目标)是 5V 直流电
```

![JAVAWEB_DESIGN39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN39.png?raw=true)

##### 类适配器

```
基本介绍：Adapter 类，通过继承 src 类，实现 dst 类接口，完成 src->dst 的适配。
```

类图分析：

![JAVAWEB_DESIGN40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN40.png?raw=true)



 代码实现：

被适配的类(实际的功能提供者)

```java
//被适配的类
public class Voltage220V {
	//输出220V的电压
	public int output220V() {
		int src = 220;
		System.out.println("电压=" + src + "伏");
		return src;
	}
}
```

适配的类(我们实际需要使用的功能)

```java
//适配接口
public interface IVoltage5V {
	public int output5V();
}
```

适配器类 (将实际的功能提供者提供的功能转换成我们需要的功能)

类适配器，继承功能提供者，实现我们需要提供的功能

```java
//适配器类
public class VoltageAdapter extends Voltage220V implements IVoltage5V {

	@Override
	public int output5V() {
		// TODO Auto-generated method stub
		//获取到220V电压
		int srcV = output220V();
		int dstV = srcV / 44 ; //转成 5v (这里就相当于在做适配)
		return dstV;
	}
}
```

 手机类：

```java
public class Phone {
	//充电
	public void charging(IVoltage5V iVoltage5V) {
		if(iVoltage5V.output5V() == 5) {
			System.out.println("电压为5V, 可以充电~~");
		} else if (iVoltage5V.output5V() > 5) {
			System.out.println("电压大于5V, 不能充电~~");
		}
	}
}
```

客户端clinet

```java
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(" === 类适配器模式 ====");
		Phone phone = new Phone();
		phone.charging(new VoltageAdapter()); //这里实际传入的是适配器类
	}
```

总结分析：

```
类适配器模式注意事项和细节
1) Java 是单继承机制，所以类适配器需要继承 src 类这一点算是一个缺点, 因为这要求 dst 必须是接口，有一定局
限性;
2) src 类的方法在 Adapter 中都会暴露出来，也增加了使用的成本。
3) 由于其继承了 src 类，所以它可以根据需求重写 src 类的方法，使得 Adapter 的灵活性增强了
```

##### 对象适配器(常用)

```
1) 基本思路和类的适配器模式相同，类适配器需要使用继承，但设计模式的原则就是少用继承，继承存在一定的问题
根据“合成复用原则”，在系统中尽量使用关联关系（聚合）来替代继承关系。

只是将 Adapter 类作修改，不是继承 src 类，而是持有 src 类的实例，以解决
兼容性的问题。 即：持有 src 类，实现 dst 类接口，完成 src->dst 的适配 

2) 对象适配器模式是适配器模式常用的一种
```

使用对象适配器来改进类适配器这个案例：

![JAVAWEB_DESIGN41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN41.png?raw=true)

代码实现：

(主要修改适配器类)

```java
//适配器类
public class VoltageAdapter  implements IVoltage5V {
	private Voltage220V voltage220V; // 关联关系-聚合
	//通过构造器，传入一个 Voltage220V 实例
	public VoltageAdapter(Voltage220V voltage220v) {
		this.voltage220V = voltage220v;
	}

	@Override
	public int output5V() {	
		int dst = 0;
		if(null != voltage220V) {
			int src = voltage220V.output220V();//获取220V 电压
			System.out.println("使用对象适配器，进行适配~~");
			dst = src / 44;
			System.out.println("适配完成，输出的电压为=" + dst);
		}
		return dst;
	}
}
```

客户端：

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(" === 对象适配器模式 ====");
		Phone phone = new Phone();
		phone.charging(new VoltageAdapter(new Voltage220V()));
	}
```

(其余无需改变)

总结分析

```
1) 对象适配器和类适配器其实算是同一种思想，只不过实现方式不同。
根据合成复用原则，使用组合替代继承， 所以它解决了类适配器必须继承 src 的局限性问题，也不再要求 dst
必须是接口。

2) 使用成本更低，更灵活。
```



##### 接口适配器

基本介绍： 他与前面两个适配器不同

```
适用于一个接口不想使用其所有的方法的情况

核心思路：当不需要全部实现接口提供的方法时(接口有很多方法，但并不想全部实现)，可先设计一个抽象类实现接口，并为该接口中每个方法提供一个默认实现（空方法），那么该抽象类的子类(一般是匿名内部类)可有选择地覆盖父类的某些方法来实现需求
```

![JAVAWEB_DESIGN42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN42.png?raw=true)

代码实现：

接口：

```java
public interface Interface4 {
	public void m1();
	public void m2();
	public void m3();
	public void m4();
}
```

抽象接口实现类 (接口适配)

```java
//在AbsAdapter 我们将 Interface4 的方法进行默认实现
public abstract class AbsAdapter implements Interface4 {

	//默认实现
	public void m1() {

	}

	public void m2() {

	}

	public void m3() {

	}

	public void m4() {

	}
}
```

客户端子类(真正方法的实现者)

```java
public class Client {
	public static void main(String[] args) {	
		AbsAdapter absAdapter = new AbsAdapter() {
			//只需要去覆盖我们 需要使用 接口方法
			@Override
			public void m1() {
				// TODO Auto-generated method stub
				System.out.println("使用了m1的方法");
			}
		};	
		absAdapter.m1();
	}
}

```

##### 适配器模式在 SpringMVC 框架应用的源码剖析

SpringMvc 中的 HandlerAdapter, 就使用了适配器模式

![JAVAWEB_DESIGN43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN43.png?raw=true)

![JAVAWEB_DESIGN45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN45.png?raw=true)

```java
public class DispatchServlet {

	public static List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();

	public DispatchServlet() {
		handlerAdapters.add(new AnnotationHandlerAdapter());
		handlerAdapters.add(new HttpHandlerAdapter());
		handlerAdapters.add(new SimpleHandlerAdapter());
	}

	public void doDispatch() {
		// 此处模拟SpringMVC从request取handler的对象，
		// 适配器可以获取到希望的Controller
		 HttpController controller = new HttpController();
		// AnnotationController controller = new AnnotationController();
		//SimpleController controller = new SimpleController();
		// 得到对应适配器
		HandlerAdapter adapter = getHandler(controller);
		// 通过适配器执行对应的controller对应方法
		adapter.handle(controller);
	}

	public HandlerAdapter getHandler(Controller controller) {
		//遍历：根据得到的controller(handler), 返回对应适配器
		for (HandlerAdapter adapter : this.handlerAdapters) {
			if (adapter.supports(controller)) {
				return adapter;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		new DispatchServlet().doDispatch(); // http...
	}

}

//多种Controller实现  
public interface Controller {

}

class HttpController implements Controller {
	public void doHttpHandler() {
		System.out.println("http...");
	}
}

class SimpleController implements Controller {
	public void doSimplerHandler() {
		System.out.println("simple...");
	}
}

class AnnotationController implements Controller {
	public void doAnnotationHandler() {
		System.out.println("annotation...");
	}
}

///定义一个Adapter接口 
public interface HandlerAdapter {
	public boolean supports(Object handler);

	public void handle(Object handler);
}

// 多种适配器类

class SimpleHandlerAdapter implements HandlerAdapter {

	public void handle(Object handler) {
		((SimpleController) handler).doSimplerHandler();
	}

	public boolean supports(Object handler) {
		return (handler instanceof SimpleController);
	}

}

class HttpHandlerAdapter implements HandlerAdapter {

	public void handle(Object handler) {
		((HttpController) handler).doHttpHandler();
	}

	public boolean supports(Object handler) {
		return (handler instanceof HttpController);
	}

}

class AnnotationHandlerAdapter implements HandlerAdapter {

	public void handle(Object handler) {
		((AnnotationController) handler).doAnnotationHandler();
	}

	public boolean supports(Object handler) {

		return (handler instanceof AnnotationController);
	}
```

![JAVAWEB_DESIGN44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN44.png?raw=true)

总结：

```
总结：
doDispatch中，我们可以看到，它的作用是相当于在Servlet的 doService调用的。
他的最终目的都要去调用controller中的方法。但是controller有好几种，
我们最常用的就是在调用放做判断if,else ... 判断不同种类的controller，执行对应的方法，
但是这样存在一个问题，如果controller的种类有了增加，我们就需要修改调用放的代码
我们需要做到“开闭原则，对使用关闭，对扩展开放” 

在这里增加一个适配层。不同种类的controller传进来，通过适配层，转换成对应的适配器，通过
适配器去调用对应的controller中的方法。这样增加controller的种类，我们只需要增加对应的适配类
就可以了，调用方doDispatch的代码就不需要做改动了。
```



##### 适配器模式总结

```
1) 三种命名方式，是根据 src 是以怎样的形式给到 Adapter（在 Adapter 里的形式）来命名的。
2) 类适配器：以类给到，在 Adapter 里，就是将 src 当做类，继承
   对象适配器：以对象给到，在 Adapter 里，将 src 作为一个对象，持有
   接口适配器：以接口给到，在 Adapter 里，将 src 作为一个接口，实现
3) Adapter 模式最大的作用还是将原本不兼容的接口融合在一起工作。
4) 实际开发中，实现起来不拘泥于我们讲解的三种经典形式
```



#### 桥接模式

##### 需求——手机操作问题

![JAVAWEB_DESIGN46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN46.png?raw=true)


##### 传统的解决方案

![JAVAWEB_DESIGN47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN47.png?raw=true)

问题分析：

```
1) 扩展性问题(类爆炸)，如果我们再增加手机的样式(旋转式)，就需要增加各个品牌手机的类，同样如果我们增加
一个手机品牌，也要在各个手机样式类下增加。
2) 违反了单一职责原则，当我们增加手机样式时，要同时增加所有品牌的手机，这样增加了代码维护成本.
```

##### 桥接模式改进

基本介绍

```
1) 桥接模式(Bridge 模式)是指：将实现与抽象放在两个不同的类层次中，
使两个层次可以独立改变的一种结构型设计模式
2) Bridge 模式基于类的最小设计原则(类尽可能的少)，通过使用封装、聚合及继承等行为让不同的类承担不同的职责。它的主要特点是把抽象(Abstraction)与行为实现(Implementation)分离开来，从而可以保持各部分的独立性以及应对他们的
功能扩展
```

![JAVAWEB_DESIGN48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN48.png?raw=true)

**改进**：使用桥接模式改进传统方式，让程序具有搞好的扩展性，利用程序维护

![JAVAWEB_DESIGN49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN49.png?raw=true)

代码实现：

实现层：

```java
//接口
public interface Brand {
	void open();
	void close();
	void call();
}

public class Vivo implements Brand {

	@Override
	public void open() {
		// TODO Auto-generated method stub
		System.out.println(" Vivo手机开机 ");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		System.out.println(" Vivo手机关机 ");
	}

	@Override
	public void call() {
		// TODO Auto-generated method stub
		System.out.println(" Vivo手机打电话 ");
	}

}

public class XiaoMi implements Brand {

	@Override
	public void open() {
		// TODO Auto-generated method stub
		System.out.println(" 小米手机开机 ");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		System.out.println(" 小米手机关机 ");
	}

	@Override
	public void call() {
		// TODO Auto-generated method stub
		System.out.println(" 小米手机打电话 ");
	}

}

```

抽象层

```java
public abstract class Phone {
	//组合品牌
	private Brand brand;
	//构造器
	public Phone(Brand brand) {
		super();
		this.brand = brand;
	}
	protected void open() {
		this.brand.open();
	}
	protected void close() {
		brand.close();
	}
	protected void call() {
		brand.call();
	}	
}

//折叠式手机类，继承 抽象类 Phone
public class FoldedPhone extends Phone {

	//构造器
	public FoldedPhone(Brand brand) {
		super(brand);
	}
	
	public void open() {
		super.open();
		System.out.println(" 折叠样式手机 ");
	}
	
	public void close() {
		super.close();
		System.out.println(" 折叠样式手机 ");
	}
	
	public void call() {
		super.call();
		System.out.println(" 折叠样式手机 ");
	}
}

public class UpRightPhone extends Phone {
	
		//构造器
		public UpRightPhone(Brand brand) {
			super(brand);
		}
		
		public void open() {
			super.open();
			System.out.println(" 直立样式手机 ");
		}
		
		public void close() {
			super.close();
			System.out.println(" 直立样式手机 ");
		}
		
		public void call() {
			super.call();
			System.out.println(" 直立样式手机 ");
		}
}
```

客户端

```java
public static void main(String[] args) {
		//获取折叠式手机 (样式 + 品牌 )
		Phone phone1 = new FoldedPhone(new XiaoMi());
		phone1.open();
		phone1.call();
		phone1.close();
		System.out.println("=======================")；
	}
```

##### 桥接模式在 JDBC 的源码剖析

![JAVAWEB_DESIGN50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN50.png?raw=true)

##### 总结分析

```
优点：
1) 实现了抽象和实现部分的分离，从而极大的提供了系统的灵活性，让抽象部分和实现部分独立开来，这有助于
系统进行分层设计，从而产生更好的结构化系统。
2) 对于系统的高层部分，只需要知道抽象部分和实现部分的接口就可以了，其它的部分由具体业务来完成。
3) 桥接模式替代多层继承方案，可以减少子类的个数，降低系统的管理和维护成本。

对于那些不希望使用继承或因为多层次继承导致系统类的个数急剧增加的系统，桥接模式尤为适用

缺点：
4) 桥接模式的引入增加了系统的理解和设计难度，他要求我们首先能够明确的分析哪些是抽象层，哪些是实现层。
(整个体系中的上层是抽象，下层是实现，实现层比较好分析)
5) 桥接模式要求正确识别出系统中两个独立变化的维度(抽象、和实现)，因此其使用范围有一定的局限性。
```

![JAVAWEB_DESIGN51.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN51.png?raw=true)

#### 装饰模式

解决类爆炸问题。

##### 需求——星巴克咖啡订单项目（咖啡馆）

![JAVAWEB_DESIGN52.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN52.png?raw=true)

##### 传统方案

最常想到的方案一： 将所有组合都组合出来(量太大，且不好扩展)

![JAVAWEB_DESIGN53.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN53.png?raw=true)

方案二：将调料内置到咖啡类中(不好扩展，扩展维护量比较大)

![JAVAWEB_DESIGN54.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN54.png?raw=true)

##### 装饰者模式改进

上面的两种方案都不好维护，思考能不能通过设计，将单品和调料分离开来，让他们自由的组合。

**装饰者模式基本介绍**

装饰者模式：**动态的将新功能附加到对象上**。在对象功能扩展方面，它比继承更有弹性，装饰者模式也体现了**开闭原则**

装饰者模式原理：

![JAVAWEB_DESIGN55.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN55.png?raw=true)

![JAVAWEB_DESIGN56.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN56.png?raw=true)

![JAVAWEB_DESIGN57.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN57.png?raw=true)

![JAVAWEB_DESIGN58.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN58.png?raw=true)

代码实现

顶层类：

```java
public abstract class Drink {

	public String des; // 描述
	private float price = 0.0f;
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	//计算费用的抽象方法
	//子类来实现
	public abstract float cost();
	
}
```

主体类

```java
public class Coffee  extends Drink {
	@Override
	public float cost() {
		// TODO Auto-generated method stub
		return super.getPrice();
	}	
}
```

装饰者类

```java
public class Decorator extends Drink {
	private Drink obj;
	public Decorator(Drink obj) { //用于组合
		// TODO Auto-generated constructor stub
		this.obj = obj;
	}
	
	@Override
	public float cost() {
		// TODO Auto-generated method stub
		// getPrice 自己价格+ obj.cost() 单品的价格
		return super.getPrice() + obj.cost();
	}
	
	@Override
	public String getDes() {
		// TODO Auto-generated method stub
		// obj.getDes() 输出被装饰者的信息
		return des + " " + getPrice() + " && " + obj.getDes();
	}

}
```

单品类：

```java
public class LongBlack extends Coffee {
	public LongBlack() {
		setDes(" longblack ");
		setPrice(5.0f);
	}
}
```

调料类

```java
//具体的Decorator， 这里就是调味品
public class Chocolate extends Decorator {

	public Chocolate(Drink obj) {
		super(obj);
		setDes(" 巧克力 ");
		setPrice(3.0f); // 调味品 的价格
	}

}

public class Milk extends Decorator {
	public Milk(Drink obj) {
		super(obj);
		// TODO Auto-generated constructor stub
		setDes(" 牛奶 ");
		setPrice(2.0f); 
	}
}
```

客户端类

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 装饰者模式下的订单：2份巧克力+一份牛奶的LongBlack

		// 1. 点一份 LongBlack
		Drink order = new LongBlack();
		System.out.println("费用1=" + order.cost());
		System.out.println("描述=" + order.getDes());

		// 2. order 加入一份牛奶
		order = new Milk(order);
		System.out.println("order 加入一份牛奶 费用 =" + order.cost());
		System.out.println("order 加入一份牛奶 描述 = " + order.getDes());

		// 3. order 加入一份巧克力

		order = new Chocolate(order);
		System.out.println("order 加入一份牛奶 加入一份巧克力  费用 =" + order.cost());
		System.out.println("order 加入一份牛奶 加入一份巧克力 描述 = " + order.getDes());
	
	}
//类似于俄罗斯套娃，使用时一层套一层
```

总结分析：这样设计有利于扩展， 当我们在新增一个单品或者再新增一种调味，都只需要新增一个单独的类了，原有的体系无需改动，扩展性十分优秀。

##### 装饰者模式在 JDK 应用的源码分析

Java 的 IO 结构，FilterInputStream 就是一个装饰者

![JAVAWEB_DESIGN59.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN59.png?raw=true)

![JAVAWEB_DESIGN60.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN60.png?raw=true)



#### 组合模式

##### 需求

![JAVAWEB_DESIGN61.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN61.png?raw=true)

##### 传统方案

![JAVAWEB_DESIGN62.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN62.png?raw=true)

传统方式问题：耦合性太高。 难以扩展

##### 组合模式改进

```
基本介绍
1) 组合模式（Composite Pattern），又叫部分整体模式，它创建了对象组的树形结构，将对象组合成树状结构以
表示“整体-部分”的层次关系。
2) 组合模式依据树形结构来组合对象，用来表示部分以及整体层次。属于结构型模式。
3) 组合模式使得用户对单个对象和组合对象的访问具有一致性，即：组合能让客户以一致的方式处理个别对象以
及组合对象
```

重点： 将对象组合成树状结构。使得用户对单个对象和组合对象的访问具有一致性

![JAVAWEB_DESIGN63.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN63.png?raw=true)

```
组合模式能解决的问题， 当我们要处理的对象可以生成一颗树形结构的时候，且我们要对树上的节点和叶子进行操作
时，他能提供一致的访问方式，让我们不用考虑它是节点还是叶子
```

改进:

![JAVAWEB_DESIGN64.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN64.png?raw=true)

代码实现

抽象类

```java
public abstract class OrganizationComponent {
	private String name; // 名字
	private String des; // 说明
	protected  void add(OrganizationComponent organizationComponent) {
		//默认实现 ,这里不定义成abstract是因为叶子节点无需重写该方法
		throw new UnsupportedOperationException();
	}
	
	protected  void remove(OrganizationComponent organizationComponent) {
		//默认实现
		throw new UnsupportedOperationException();
	}

	//构造器
	public OrganizationComponent(String name, String des) {
		super();
		this.name = name;
		this.des = des;
	}

    //-----属性的setter，和getter
	
	//方法print, 做成抽象的, 子类都需要实现
	protected abstract void print();	
	
}

```

Composite 

```java
//University 就是 Composite , 可以管理College
public class University extends OrganizationComponent {

	List<OrganizationComponent> organizationComponents = new ArrayList<OrganizationComponent>();

	// 构造器
	public University(String name, String des) {
		super(name, des);
		// TODO Auto-generated constructor stub
	}

	// 重写add
	@Override
	protected void add(OrganizationComponent organizationComponent) {
		// TODO Auto-generated method stub
		organizationComponents.add(organizationComponent);
	}

	// 重写remove
	@Override
	protected void remove(OrganizationComponent organizationComponent) {
		// TODO Auto-generated method stub
		organizationComponents.remove(organizationComponent);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public String getDes() {
		// TODO Auto-generated method stub
		return super.getDes();
	}

	// print方法，就是输出University 包含的学院
	@Override
	protected void print() {
		// TODO Auto-generated method stub
		System.out.println("--------------" + getName() + "--------------");
		//遍历 organizationComponents 
		for (OrganizationComponent organizationComponent : organizationComponents) {
			organizationComponent.print();
		}
	}

}

public class College extends OrganizationComponent {

	//List 中 存放的Department
	List<OrganizationComponent> organizationComponents = new ArrayList<OrganizationComponent>();

	// 构造器
	public College(String name, String des) {
		super(name, des);
		// TODO Auto-generated constructor stub
	}

	// 重写add
	@Override
	protected void add(OrganizationComponent organizationComponent) {
		// TODO Auto-generated method stub
		//  将来实际业务中，Colleage 的 add 和  University add 不一定完全一样
		organizationComponents.add(organizationComponent);
	}

	// 重写remove
	@Override
	protected void remove(OrganizationComponent organizationComponent) {
		// TODO Auto-generated method stub
		organizationComponents.remove(organizationComponent);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public String getDes() {
		// TODO Auto-generated method stub
		return super.getDes();
	}

	// print方法，就是输出University 包含的学院
	@Override
	protected void print() {
		// TODO Auto-generated method stub
		System.out.println("--------------" + getName() + "--------------");
		//遍历 organizationComponents 
		for (OrganizationComponent organizationComponent : organizationComponents) {
			organizationComponent.print();
		}
	}

}
```

叶子节点

```java
public class Department extends OrganizationComponent {

	//没有集合
	
	public Department(String name, String des) {
		super(name, des);
		// TODO Auto-generated constructor stub
	}

	
	//add , remove 就不用写了，因为他是叶子节点
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}
	
	@Override
	public String getDes() {
		// TODO Auto-generated method stub
		return super.getDes();
	}
	
	@Override
	protected void print() {
		// TODO Auto-generated method stub
		System.out.println(getName());
	}

}
```

客户端

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//从大到小创建对象 学校
		OrganizationComponent university = new University("清华大学", " 中国顶级大学 ");
		
		//创建 学院
		OrganizationComponent computerCollege = new College("计算机学院", " 计算机学院 ");
		OrganizationComponent infoEngineercollege = new College("信息工程学院", " 信息工程学院 ");
		
		
		//创建各个学院下面的系(专业)
		computerCollege.add(new Department("软件工程", " 软件工程不错 "));
		computerCollege.add(new Department("网络工程", " 网络工程不错 "));
		computerCollege.add(new Department("计算机科学与技术", " 计算机科学与技术是老牌的专业 "));
		
		//
		infoEngineercollege.add(new Department("通信工程", " 通信工程不好学 "));
		infoEngineercollege.add(new Department("信息工程", " 信息工程好学 "));
		
		//将学院加入到 学校
		university.add(computerCollege);
		university.add(infoEngineercollege);
		
		//university.print();
		infoEngineercollege.print();
	}
```

总结分析：这样设计扩展性更强，如果这个树形结构中新增了一个节点，直接聚合聚合进来就行了，其他节点的代码无需改变。

##### 组合模式在 JDK 集合的源码分析

Java 的集合类-HashMap 就使用了组合模式

![JAVAWEB_DESIGN65.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN65.png?raw=true)

![JAVAWEB_DESIGN66.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN66.png?raw=true)

##### 组合模式总结

```
1) 简化客户端操作。客户端只需要面对一致的对象而不用考虑整体部分或者叶子节点的问题。
2) 具有较强的扩展性。当我们要更改组合对象时，我们只需要调整内部的层次关系，客户端不用做出任何改动.
3) 方便创建出复杂的层次结构。客户端不用理会组合里面的组成细节，容易添加节点或者叶子从而创建出复杂的
树形结构
4) 需要遍历组织机构，或者处理的对象具有树形结构时, 非常适合使用组合模式.
5) 要求较高的抽象性，如果节点和叶子有很多差异性的话，比如很多方法和属性都不一样，不适合使用组合模式
```



#### 外观模式

##### 需求——影院管理项目

```
组建一个家庭影院：
DVD 播放器、投影仪、自动屏幕、环绕立体声、爆米花机,要求完成使用家庭影院的功能，其过程为：
直接用遥控器：统筹各设备开关
开爆米花机
放下屏幕
开投影仪
开音响
开 DVD，选 dvd
去拿爆米花
调暗灯光
播放
观影结束后，关闭各种设备
```

![JAVAWEB_DESIGN67.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN67.png?raw=true)

传统方案的问题： 客户端中流程会很复杂，而且一旦其中某项发生改变，客户端的流程也会随之改变

```
解决思路：定义一个高层接口，给子系统中的一组接口提供一个一致的界面(比如在高层接口提供四个方法ready, play, pause, end )，用来访问子系统中的一群接口
也就是说通过定义一个一致的接口(界面类)，用以屏蔽内部子系统的细节，使得调用端只需跟这个接口发生调用，而无需关心这个子系统的内部细节 => 外观模式  (其核心思想也是在客户端与具体的类之间添加一层缓存层)
```

##### 外观模式改进

![JAVAWEB_DESIGN68.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN68.png?raw=true)

![JAVAWEB_DESIGN70.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN70.png?raw=true)

代码实现

```java
public class HomeTheaterFacade {
	
	//定义各个子系统对象
	private TheaterLight theaterLight;
	private Popcorn popcorn;
	private Stereo stereo;
	private Projector projector;
	private Screen screen;
	private DVDPlayer dVDPlayer;
	
	
	//构造器
	public HomeTheaterFacade() {
		super();
		this.theaterLight = TheaterLight.getInstance();
		this.popcorn = Popcorn.getInstance();
		this.stereo = Stereo.getInstance();
		this.projector = Projector.getInstance();
		this.screen = Screen.getInstance();
		this.dVDPlayer = DVDPlayer.getInstanc();
	}

	//操作分成 4 步
	
	public void ready() {
		popcorn.on();
		popcorn.pop();
		screen.down();
		projector.on();
		stereo.on();
		dVDPlayer.on();
		theaterLight.dim();
	}
	
	public void play() {
		dVDPlayer.play();
	}
	
	public void pause() {
		dVDPlayer.pause();
	}
	
	public void end() {
		popcorn.off();
		theaterLight.bright();
		screen.up();
		projector.off();
		stereo.off();
		dVDPlayer.off();
	}	
	
}

```

具体的子系统类

```java
public class DVDPlayer {
	
	//使用单例模式, 使用饿汉式
	private static DVDPlayer instance = new DVDPlayer();
	
	public static DVDPlayer getInstanc() {
		return instance;
	}
	
	public void on() {
		System.out.println(" dvd on ");
	}
	public void off() {
		System.out.println(" dvd off ");
	}
	
	public void play() {
		System.out.println(" dvd is playing ");
	}
	
	//....
	public void pause() {
		System.out.println(" dvd pause ..");
	}
}

//--其余子系统类似
```

客户端

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//这里直接调用。。 很麻烦
		HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade();
		homeTheaterFacade.ready();
		homeTheaterFacade.play();
		homeTheaterFacade.end();
	}
```

##### 外观模式在 MyBatis 框架应用的源码分析

MyBatis 中的 Configuration 去创建 MetaObject 对象使用到外观模式

![JAVAWEB_DESIGN69.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN69.png?raw=true)

![JAVAWEB_DESIGN71.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN71.png?raw=true)

##### 外观模式总结

```
1) 外观模式对客户端与子系统的耦合关系 - 解耦，让子系统内部的模块更易维护和扩展
2) 通过合理的使用外观模式，可以帮我们更好的划分访问的层次
3) 当系统需要进行分层设计时，可以考虑使用 Facade 模式
4) 在维护一个遗留的大型系统时，可能这个系统已经变得非常难以维护和扩展，此时可以考虑为新系统开发一个
Facade 类，来提供遗留系统的比较清晰简单的接口，让新系统与 Facade 类交互，提高复用性

外观模式是当我们使用接口使用起来已经很复杂了，这时我们抽取一个更高层，来简化调用
```



#### 享元模式

##### 需求——网站显示

```
展示网站项目需求
小型的外包项目，给客户 A 做一个产品展示网站，客户 A 的朋友感觉效果不错，也希望做这样的产品展示网
站，但是要求都有些不同：
1) 有客户要求以新闻的形式发布
2) 有客户人要求以博客的形式发布
3) 有客户希望以微信公众号的形式发布
```

传统方案解决网站展现项目

![JAVAWEB_DESIGN72.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN72.png?raw=true)

同样的问题相似度，复制相似的几份浪费空间。

```
解决思路：整合到一个网站中，共享其相关的代码和数据，对于硬盘、内存、CPU、数据库空间等服务器资源
都可以达成共享，减少服务器资源。对于代码来说，由于是一份实例，维护和扩展都更加容易
```

**享元模式基本介绍**

```
1）享元模式（Flyweight Pattern）: 是运用共享技术有效地支持大量细粒度的对象。
享： 共享  ，元： 对象 ， 享元模式就是共享对象模式。

2) 常用于系统底层开发，解决系统的性能问题。像数据库连接池，里面都是创建好的连接对象，在这些连接对象
中有我们需要的则直接拿来用，避免重新创建，如果没有我们需要的，则创建一个
3) 享元模式能够解决《重复对象的内存浪费》的问题，当系统中有大量相似对象，需要缓冲池时。不需总是创建新对
象，可以从缓冲池里拿。这样可以降低系统内存，同时提高效率
4) 享元模式经典的应用场景就是池技术了，String 常量池、数据库连接池、缓冲池等等都是享元模式的应用，享
元模式是池技术的重要实现方式
```

![JAVAWEB_DESIGN73.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN73.png?raw=true)

![JAVAWEB_DESIGN74.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN74.png?raw=true)

```
内部状态和外部状态
比如围棋、五子棋、跳棋，它们都有大量的棋子对象，围棋和五子棋只有黑白两色，跳棋颜色多一点，所以棋子颜
色就是棋子的内部状态；而各个棋子之间的差别就是位置的不同，当我们落子后，落子颜色是定的，但位置是变化
的，所以棋子坐标就是棋子的外部状态
1) 享元模式提出了两个要求：细粒度和共享对象。这里就涉及到内部状态和外部状态了，即将对象的信息分为两
个部分：内部状态和外部状态
2) 内部状态指对象共享出来的信息，存储在享元对象内部且不会随环境的改变而改变
3) 外部状态指对象得以依赖的一个标记，是随环境改变而改变的、不可共享的状态。
4) 举个例子：围棋理论上有 361 个空位可以放棋子，每盘棋都有可能有两三百个棋子对象产生，因为内存空间有
限，一台服务器很难支持更多的玩家玩围棋游戏，如果用享元模式来处理棋子，那么棋子对象就可以减少到只
有两个实例，这样就很好的解决了对象的开销问题
```

##### 享元模式改进

![JAVAWEB_DESIGN75.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN75.png?raw=true)

代码实现

```java
public abstract class WebSite {
	public abstract void use();//抽象方法
}


//具体网站
public class ConcreteWebSite extends WebSite {
	//共享的部分，内部状态
	private String type = ""; //网站发布的形式(类型)
	//构造器
	public ConcreteWebSite(String type) {
		this.type = type;
	}
	@Override
	public void use() {
		// TODO Auto-generated method stub
		System.out.println("网站的发布形式为:" + type + " 在使用中 .. );
	}	
}
```

享元工厂

```java
// 网站工厂类，根据需要返回压一个网站
public class WebSiteFactory {
	//集合， 充当池的作用
	private HashMap<String, ConcreteWebSite> pool = new HashMap<>();
	//根据网站的类型，返回一个网站, 如果没有就创建一个网站，并放入到池中,并返回
	public WebSite getWebSiteCategory(String type) {
		if(!pool.containsKey(type)) {
			//就创建一个网站，并放入到池中
			pool.put(type, new ConcreteWebSite(type));
		}
		return (WebSite)pool.get(type);
	}
	
	//获取网站分类的总数 (池中有多少个网站类型)
	public int getWebSiteCount() {
		return pool.size();
	}
}
```

客户端

```java
public static void main(String[] args) {
		// 创建一个工厂类
		WebSiteFactory factory = new WebSiteFactory();
		// 客户要一个以新闻形式发布的网站
		WebSite webSite1 = factory.getWebSiteCategory("新闻");
		webSite1.use（);

		// 客户要一个以博客形式发布的网站
		WebSite webSite2 = factory.getWebSiteCategory("博客");
		webSite2.use();

		// 客户要一个以博客形式发布的网站
		WebSite webSite3 = factory.getWebSiteCategory("博客");
		webSite3.use();

	}
```

上面的代码中，type是网站的内部状态，但是我们没有找打外部状态。

改进

![JAVAWEB_DESIGN76.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN76.png?raw=true)

```java
public abstract class WebSite {
	public abstract void use(User user);//抽象方法
}


//具体网站
public class ConcreteWebSite extends WebSite {
	//共享的部分，内部状态
	private String type = ""; //网站发布的形式(类型)
	//构造器
	public ConcreteWebSite(String type) {
		this.type = type;
	}
	@Override
	public void use(User user) {
		// TODO Auto-generated method stub
		System.out.println("网站的发布形式为:" + type + " 在使用中 .. 使用者是" + user.getName());
	}	
}

public static void main(String[] args) {
		// 创建一个工厂类
		WebSiteFactory factory = new WebSiteFactory();
		// 客户要一个以新闻形式发布的网站
		WebSite webSite1 = factory.getWebSiteCategory("新闻");
		webSite1.use(new User("tom"));

		// 客户要一个以博客形式发布的网站
		WebSite webSite2 = factory.getWebSiteCategory("博客");
		webSite2.use(new User("jack"));

		// 客户要一个以博客形式发布的网站
		WebSite webSite3 = factory.getWebSiteCategory("博客");
		webSite3.use(new User("smith"));

		// 客户要一个以博客形式发布的网站
		WebSite webSite4 = factory.getWebSiteCategory("博客");
		webSite4.use(new User("king"));
		System.out.println("网站的分类共=" + factory.getWebSiteCount());
	}
```

总结： 将我们共用的写到ConcreteWebSite 的属性中去。外部需要使用的地方，单独拿出来，如这个user。

##### 享元模式在 JDK-Interger 的应用源码分析

Integer 中的享元模式

![JAVAWEB_DESIGN77.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN77.png?raw=true)



##### 享元模式总结

```
享元模式的总结： 享元模式的本质类似于池技术，有一个集合，判断有没有(这里是以内部状态进行判断的)，如果没有则加入集合，如果有则直接从集合中取。 但是这里需要技巧将内部状态抽取和外部状态抽取出来。 

系统中有大量对象，这些对象消耗大量内存，并且对象的状态大部分可以外部化时，我们就可以考虑选用享元模式
享元模式提高了系统的复杂度。需要分离出内部状态和外部状态，而外部状态具有固化特性，不应该随着内部状态的改变而改变，这是我们使用享元模式需要注意的地方.
```



#### 代理模式

```
基本介绍
代理模式：为一个对象提供一个替身，以控制对这个对象的访问。即通过代理对象访问目标对象.这样做的好处是:可以在目标对象实现的基础上,增强额外的功能操作,即扩展目标对象的功能。

通过代理模式我们可以实现类似： 日志记录，权限拦截等功能。

主要有三种 静态代理、动态代理 (JDK 代理、接口代理)和 Cglib 代理 (静态代理和动态代理都是基于接口实现的。可以在内存动态的创建对象，而不需要实现接口) 。
```

![JAVAWEB_DESIGN78.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN78.png?raw=true)

##### 静态代理

```
静态代理在使用时,需要定义接口或者父类,被代理对象(即目标对象)与代理对象一起实现相同的接口或者是继承相同父类
```

![JAVAWEB_DESIGN79.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN79.png?raw=true)

![JAVAWEB_DESIGN80.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN80.png?raw=true)

代码实现

```java
//接口
public interface ITeacherDao {
	void teach(); // 授课的方法
}


public class TeacherDao implements ITeacherDao {
	@Override
	public void teach() {
		// TODO Auto-generated method stub
		System.out.println(" 老师授课中  。。。。。");
	}
}

代理类
//代理对象,静态代理
public class TeacherDaoProxy implements ITeacherDao{
	private ITeacherDao target; // 目标对象，通过接口来聚合
	//构造器
	public TeacherDaoProxy(ITeacherDao target) {
		this.target = target;
	}
	
	@Override
	public void teach() {
		// 在代理类中可以对被代理类方法进行扩展： 日志记录，权限控制
		System.out.println("开始代理  完成某些操作。。。。。 ");//方法
		target.teach();
		System.out.println("提交。。。。。");//方法
	}

}

public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建目标对象(被代理对象)
		TeacherDao teacherDao = new TeacherDao();
		//创建代理对象, 同时将被代理对象传递给代理对象
		TeacherDaoProxy teacherDaoProxy = new TeacherDaoProxy(teacherDao);
		
		//通过代理对象，调用到被代理对象的方法
		//即：执行的是代理对象的方法，代理对象再去调用目标对象的方法 
		teacherDaoProxy.teach();
	}
```

总结分析

```
静态代理优缺点
1) 优点：在不修改目标对象的功能前提下, 能通过代理对象对目标功能扩展
2) 缺点：因为代理对象需要与目标对象实现一样的接口,所以会有很多代理类
        一旦接口增加方法,目标对象与代理对象都要维护
```



##### 动态代理

基本介绍

```
1) 代理对象,不需要实现接口，但是目标对象要实现接口，否则不能用动态代理
2) 代理对象的生成，是利用 JDK 的 API（这里会用到jdk的反射机制），动态的在内存中构建代理对象，然后通过代理对象去调用被代理对象的方法。
3) 动态代理也叫做：JDK 代理、接口代理

JDK 中生成代理对象的 API
1) 代理类所在包:java.lang.reflect.Proxy
2) JDK 实现代理只需要使用 newProxyInstance 方法,但是该方法需要接收三个参数,完整的写法是:
static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h )

```

实例： 将前面的静态代理改成动态代理

![JAVAWEB_DESIGN81.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN81.png?raw=true)

 代码实现：

```java
//接口
public interface ITeacherDao {
	void teach(); // 授课方法
	void sayHello(String name);
}

public class TeacherDao implements ITeacherDao {
	@Override
	public void teach() {
		// TODO Auto-generated method stub
		System.out.println(" 老师授课中.... ");
	}

	@Override
	public void sayHello(String name) {
		// TODO Auto-generated method stub
		System.out.println("hello " + name);
	}	
}
```

生成代理对象的工厂

```java
//
public class ProxyFactory {
	//维护一个目标对象 , Object 。 这个对象通过构造器传进来
	private Object target;
	//构造器 ， 对target 进行初始化
	public ProxyFactory(Object target) {		
		this.target = target;
	} 
	
	//给目标对象 生成一个代理对象
	public Object getProxyInstance() {
		
		//说明
		/*
		 *  public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
                                          
            //1. ClassLoader loader ： 指定当前目标对象使用的类加载器, 获取加载器的方法固定
            //2. Class<?>[] interfaces: 目标对象实现的接口类型，使用泛型方法确认类型
            //3. InvocationHandler h : 事情处理，执行目标对象的方法时，会触发事情处理器方法, 会把当前执行的目标对象方法作为参数传入
		 */
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// TODO Auto-generated method stub
						System.out.println("JDK代理开始~~");
						//反射机制调用目标对象的方法
						Object returnVal = method.invoke(target, args);
						System.out.println("JDK代理提交");
						return returnVal;
					}
				}); 
	}
	
	
}

```

客户端

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建目标对象
		ITeacherDao target = new TeacherDao();
		//给目标对象，创建代理对象, 可以转成 ITeacherDao
		ITeacherDao proxyInstance = (ITeacherDao)new ProxyFactory(target).getProxyInstance();
		// proxyInstance=class com.sun.proxy.$Proxy0 内存中动态生成了代理对象
		System.out.println("proxyInstance=" + proxyInstance.getClass());
		//通过代理对象，调用目标对象的方法
		//proxyInstance.teach();
		proxyInstance.sayHello(" tom ");
	}
```



##### Cglib 代理

无论是静态代理还是动态代理，都需要我们的被代理类实现接口。但是在我们的实际开发过程中存在大部分没有实现接口的类需要进行动态代理。这时候就需要Cglib代理。

```
Cglib代理也叫作子类代理,它是在内存中构建一个子类对象从而实现对目标对象功能扩展。
Cglib 是一个强大的高性能的代码生成包,它可以在运行期扩展 java 类与实现 java 接口.它广泛的被许多 AOP 的
框架使用,例如 Spring AOP，实现方法拦截
Cglib 包的底层是通过使用字节码处理框架 ASM 来转换字节码并生成新的类
在 AOP 编程中如何选择代理模式：
1. 目标对象需要实现接口，用 JDK 代理
2. 目标对象不需要实现接口，用 Cglib 代理
```

![JAVAWEB_DESIGN82.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN82.png?raw=true)

Cglib改进上面的案例

![JAVAWEB_DESIGN83.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN83.png?raw=true)

代码实现：

```java
被代理对象
public class TeacherDao {

	public String teach() {
		System.out.println(" 老师授课中  ， 我是cglib代理，不需要实现接口 ");
		return "hello";
	}
}

代理对象生成工厂
public class ProxyFactory implements MethodInterceptor {
	//维护一个目标对象
	private Object target;
	//构造器，传入一个被代理的对象
	public ProxyFactory(Object target) {
		this.target = target;
	}

	//返回一个代理对象:  是 target 对象的代理对象
	public Object getProxyInstance() {
		//1. 创建一个工具类
		Enhancer enhancer = new Enhancer();
		//2. 设置父类
		enhancer.setSuperclass(target.getClass());
		//3. 设置回调函数
		enhancer.setCallback(this);
		//4. 创建子类对象，即代理对象
		return enhancer.create();
		
	}
	
	//重写  intercept 方法，会调用目标对象的方法
	@Override
	public Object intercept(Object arg0, Method method, Object[] args, MethodProxy arg3) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("Cglib代理模式 ~~ 开始");
		Object returnVal = method.invoke(target, args);
		System.out.println("Cglib代理模式 ~~ 提交");
		return returnVal;
	}

}

客户端
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建目标对象
		TeacherDao target = new TeacherDao();
		//获取到代理对象，并且将目标对象传递给代理对象
		TeacherDao proxyInstance = (TeacherDao)new ProxyFactory(target).getProxyInstance();

		//执行代理对象的方法，触发intecept 方法，从而实现 对目标对象的调用
		String res = proxyInstance.teach();
		System.out.println("res=" + res);
	}
```

##### 代理模式的变体

```
1) 防火墙代理
内网通过代理穿透防火墙，实现对公网的访问。
2) 缓存代理
比如：当请求图片文件等资源时，先到缓存代理取，如果取到资源则 ok,如果取不到资源，再到公网或者数据
库取，然后缓存。
3) 远程代理
远程对象的本地代表，通过它可以把远程对象当本地对象来调用。远程代理通过网络和真正的远程对象沟通信
息。
4) 同步代理：主要使用在多线程编程中，完成多线程间同步工作
同步代理：主要使用在多线程编程中，完成多线程间同步工作

```

![JAVAWEB_DESIGN84.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN84.png?raw=true)

![JAVAWEB_DESIGN85.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN85.png?raw=true)





　
