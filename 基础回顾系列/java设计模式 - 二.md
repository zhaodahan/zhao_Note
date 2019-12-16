### 行为型模式

站在方法这个角度来思考和设计的。让方法的设计更加的合理。

#### 模版方法模式

##### 需求——豆浆制作问题

```
编写制作豆浆的程序，说明如下:
1) 制作豆浆的流程 选材--->添加配料--->浸泡--->放到豆浆机打碎
2) 通过添加不同的配料，可以制作出不同口味的豆浆
3) 选材、浸泡和放到豆浆机打碎这几个步骤对于制作每种口味的豆浆都是一样的
4) 对于这种，多数条件是固定的，是不变的。只有特定的条件发生改变，我们可以采用模板方法模式来完成 (类似的如测试代码执行时间等都可以使用模板方法模式)
```

**模板方法的基本介绍**

```
模板模式(Template Pattern)，在一个抽象类中公开定义了执行它的方法的模板。它的子类可以按需要重写方法实现，但调用将以抽象类中定义的方式进行。简单说，模板方法模式 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构，就可以重定义该算法的某些特定步骤这种类型的设计模式属于行为型模式
```

![JAVAWEB_DESIGN86.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN86.png?raw=true)

在豆浆制作过程中使用模板方法模式

![JAVAWEB_DESIGN87.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN87.png?raw=true)

代码实现：

抽象类

```java
//抽象类，表示豆浆
public abstract class SoyaMilk {
	//模板方法, make , 模板方法可以做成final , 不让子类去覆盖.
	final void make() {	
		select(); 
		addCondiments();
		soak();
		beat();
	}
	
	//选材料
	void select() {
		System.out.println("第一步：选择好的新鲜黄豆  ");
	}
	//添加不同的配料， 抽象方法, 子类具体实现
	abstract void addCondiments();
    
	//浸泡
	void soak() {
		System.out.println("第三步， 黄豆和配料开始浸泡， 需要3小时 ");
	}
	void beat() {
		System.out.println("第四步：黄豆和配料放到豆浆机去打碎  ");
	}
}
```

实际实现类

```java
public class PeanutSoyaMilk extends SoyaMilk {
	@Override
	void addCondiments() {
		// TODO Auto-generated method stub
		System.out.println(" 加入上好的花生 ");
	}

}

public class RedBeanSoyaMilk extends SoyaMilk {
	@Override
	void addCondiments() {
		// TODO Auto-generated method stub
		System.out.println(" 加入上好的红豆 ");
	}

}
```

客户端

```java
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//制作红豆豆浆
		System.out.println("----制作红豆豆浆----");
		SoyaMilk redBeanSoyaMilk = new RedBeanSoyaMilk();
		redBeanSoyaMilk.make();
		
		System.out.println("----制作花生豆浆----");
		SoyaMilk peanutSoyaMilk = new PeanutSoyaMilk();
		peanutSoyaMilk.make();
	}
```

##### 模板方法模式中的钩子方法

```
1）在模板方法模式的父类中，我们可以定义一个方法，它默认不做任何事(视情况返回一个booean 值，类似下面的实现，他也可以是一个空方法，他的价值就在于要让他的子类去覆盖他)，子类可以视情况要不要覆盖它，该方法称为“钩子”。(他可以比较轻松的去实现哪一个方法在你的模板方法中是否需要被调用)

2) 还是用上面做豆浆的例子来讲解，比如，我们还希望制作纯豆浆，不添加任何的配料，请使用钩子方法对前面
的模板方法进行改造
```

对原来的代码进行改进

```java
//抽象类，表示豆浆
public abstract class SoyaMilk {

	//模板方法, make , 模板方法可以做成final , 不让子类去覆盖.
	final void make() {
		
		select(); 
		if(customerWantCondiments()) {
			addCondiments();
		}
		soak();
		beat();
		
	}
	
	//选材料
	void select() {
		System.out.println("第一步：选择好的新鲜黄豆  ");
	}
	
	//添加不同的配料， 抽象方法, 子类具体实现
	abstract void addCondiments();
	
	//浸泡
	void soak() {
		System.out.println("第三步， 黄豆和配料开始浸泡， 需要3小时 ");
	}
	 
	void beat() {
		System.out.println("第四步：黄豆和配料放到豆浆机去打碎  ");
	}
	
	//钩子方法，决定是否需要添加配料
	boolean customerWantCondiments() {
		return true;
	}
}

这样修改时不会对cline操作产生影响的

当我们需要实现纯豆浆的时候
public class PureSoyaMilk extends SoyaMilk{
	@Override
	void addCondiments() {
		// TODO Auto-generated method stub
		//空实现
	}
	
	@Override // 这里需要去重写这个钩子方法。
	boolean customerWantCondiments() {
		// TODO Auto-generated method stub
		return false;
	}
 
}
```



##### 模板方法模式在Spring IOC 容器 中的使用

Spring IOC 容器初始化时运用到的模板方法模式

![JAVAWEB_DESIGN88.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN88.png?raw=true)

##### 模板方法模式总结

```
1) 基本思想是：算法只存在于一个地方，也就是在父类中，容易修改。需要修改算法时，只要修改父类的模板方
法或者已经实现的某些步骤，子类就会继承这些修改
2) 实现了最大化代码复用。父类的模板方法和已实现的某些步骤会被子类继承而直接使用。
3) 既统一了算法，也提供了很大的灵活性。父类的模板方法确保了算法的结构保持不变，同时由子类提供部分步骤的实现。
4) 该模式的不足之处：每一个不同的实现都需要一个子类实现，导致类的个数增加，使得系统更加庞大
5) 一般模板方法都加上 final 关键字， 防止子类重写模板方法.
6) 模板方法模式使用场景：当要完成在某个过程，该过程要执行一系列步骤 ，这一系列的步骤基本相同，但其
个别步骤在实现时 可能不同，通常考虑用模板方法模式来处理
```



#### 命令模式

##### 需求——智能生活

![JAVAWEB_DESIGN89.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN89.png?raw=true)

从他面临的问题和解决的方式很类似于我们的外观模式。

```
命令模式基本介绍
在软件设计中，我们经常需要向某些对象发送请求，但是并不知道请求的接收者是谁，也不知道被请求的操作是哪个，我们只需在程序运行时指定具体的请求接收者即可

举例说明： 比如将军发生命令让士兵去打仗，命令被士兵接收到，但是将军并不知道具体的是哪个士兵。即命令的发布者并不知道是谁来执行的。中间有一个传令官。他是通过在将军和士兵之间加入一个缓冲层——命令。来讲他们关联起来。他能让命令的发布者和命令的执行者解耦。 

```

![JAVAWEB_DESIGN90.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN90.png?raw=true)

##### 命令模式解决需求

代码实现

![JAVAWEB_DESIGN91.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN91.png?raw=true)

代码实现：

```java
//创建命令接口
public interface Command {
	//执行动作(操作)
	public void execute();
	//撤销动作(操作)
	public void undo();
}

public class LightOffCommand implements Command {
	// 聚合LightReceiver
	LightReceiver light;

	// 构造器
	public LightOffCommand(LightReceiver light) {
			super();
			this.light = light;
		}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		// 调用接收者的方法
		light.off();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		// 调用接收者的方法， 灯开的撤销方法就是关闭
		light.on();
	}
}

public class LightOnCommand implements Command {
	//聚合LightReceiver
	LightReceiver light;
	
	//构造器
	public LightOnCommand(LightReceiver light) {
		super();
		this.light = light;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//调用接收者的方法
		light.on();
	}	

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		//调用接收者的方法
		light.off();
	}

}

public class LightReceiver {
	public void on() {
		System.out.println(" 电灯打开了.. ");
	}
	
	public void off() {
		System.out.println(" 电灯关闭了.. ");
	}
}

/**
 * 没有任何命令，即空执行: 用于初始化每个按钮, 当调用空命令时，对象什么都不做
 * 其实，这样是一种设计模式, 可以省掉对空判断
 * @author Administrator
 *
 */
public class NoCommand implements Command {
	@Override
	public void execute() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub	
	}

}
```

遥控器

```java
public class RemoteController {

	// 开 按钮的命令数组
	Command[] onCommands;
	Command[] offCommands;

	// 执行撤销的命令
	Command undoCommand;

	// 构造器，完成对按钮初始化

	public RemoteController() {

		onCommands = new Command[5];
		offCommands = new Command[5];

		for (int i = 0; i < 5; i++) {
			onCommands[i] = new NoCommand();
			offCommands[i] = new NoCommand();
		}
	}

	// 给我们的按钮设置你需要的命令
	public void setCommand(int no, Command onCommand, Command offCommand) {
		onCommands[no] = onCommand;
		offCommands[no] = offCommand;
	}

	// 按下开按钮
	public void onButtonWasPushed(int no) { // no 0
		// 找到你按下的开的按钮， 并调用对应方法
		onCommands[no].execute();
		// 记录这次的操作，用于撤销
		undoCommand = onCommands[no];

	}

	// 按下开按钮
	public void offButtonWasPushed(int no) { // no 0
		// 找到你按下的关的按钮， 并调用对应方法
		offCommands[no].execute();
		// 记录这次的操作，用于撤销
		undoCommand = offCommands[no];

	}
	
	// 按下撤销按钮
	public void undoButtonWasPushed() {
		undoCommand.undo();
	}

}

```

客户端

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//使用命令设计模式，完成通过遥控器，对电灯的操作
		
		//创建电灯的对象(接受者)
		LightReceiver lightReceiver = new LightReceiver();
		
		//创建电灯相关的开关命令
		LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
		LightOffCommand lightOffCommand = new LightOffCommand(lightReceiver);
		
		//需要一个遥控器
		RemoteController remoteController = new RemoteController();
		
		//给我们的遥控器设置命令, 比如 no = 0 是电灯的开和关的操作
		remoteController.setCommand(0, lightOnCommand, lightOffCommand);
		
		System.out.println("--------按下灯的开按钮-----------");
		remoteController.onButtonWasPushed(0);
		System.out.println("--------按下灯的关按钮-----------");
		remoteController.offButtonWasPushed(0);
		System.out.println("--------按下撤销按钮-----------");
		remoteController.undoButtonWasPushed();
		
		
		System.out.println("=========使用遥控器操作电视机==========");
		
		TVReceiver tvReceiver = new TVReceiver();
		
		TVOffCommand tvOffCommand = new TVOffCommand(tvReceiver);
		TVOnCommand tvOnCommand = new TVOnCommand(tvReceiver);
		
		//给我们的遥控器设置命令, 比如 no = 1 是电视机的开和关的操作
		remoteController.setCommand(1, tvOnCommand, tvOffCommand);
		
		System.out.println("--------按下电视机的开按钮-----------");
		remoteController.onButtonWasPushed(1);
		System.out.println("--------按下电视机的关按钮-----------");
		remoteController.offButtonWasPushed(1);
		System.out.println("--------按下撤销按钮-----------");
		remoteController.undoButtonWasPushed();

	}
```

当遥控器有新增的命令按钮是，客户端的使用方式，他符合了开闭原则。

##### 命令模式在spring框架中的JdbcTemplate 中的使用

![JAVAWEB_DESIGN92.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN92.png?raw=true)

![JAVAWEB_DESIGN93.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN93.png?raw=true)

##### 命令模式的总结

```
命令模式的核心思想是：把发起请求的对象和执行请求的对象解耦。 命令对象是起到一个桥梁，过渡的作用。

使用命令模式我们可有容易设计一个命令队列。只要把命令对象放到列队，就可以多线程的执行命令。 他还可以容易实现对请求的撤销和重做

空命令也是一种设计模式，它为我们省去了判空的操作。在上面的实例中，如果没有用空命令，我们每按下一个按键都要判空，这给我们编码带来一定的麻烦。

命令模式经典的应用场景：界面的一个按钮都是一条命令、模拟 CMD（DOS 命令）订单的撤销/恢复、触发-反馈机制
```



#### 访问者模式

##### 需求——评分

![JAVAWEB_DESIGN94.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN94.png?raw=true)

##### 访问者模式改进

基础介绍

```
1) 访问者模式（Visitor Pattern），封装一些作用于某种数据结构的各元素的操作，它可以在不改变数据结构的前
提下定义作用于这些元素的新的操作。
2) 主要将数据结构与数据操作分离，解决 <数据结构> 和 <操作> 耦合性问题

3) 访问者模式的基本工作原理是：在被访问的类里面加一个对外提供接待访问者的接口
4) 访问者模式主要应用场景是：需要对一个对象结构中的对象进行很多不同操作(这些操作彼此没有关联)，同时需要避免让这些操作"污染"这些对象的类，可以选用访问者模式解决
```

![JAVAWEB_DESIGN95.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN95.png?raw=true)

访问者模式解决评分问题

![JAVAWEB_DESIGN96.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN96.png?raw=true)

代码实现

访问者：

```java
public abstract class Action {
	//得到男性 的测评
	public abstract void getManResult(Man man);
	//得到女的 测评
	public abstract void getWomanResult(Woman woman);
    
    //这里涉及action中会使用到man,man中也会使到action
}

public class Success extends Action {
	@Override
	public void getManResult(Man man) {
		// TODO Auto-generated method stub
		System.out.println(" 男人给的评价该歌手很成功 !");
	}

	@Override
	public void getWomanResult(Woman woman) {
		// TODO Auto-generated method stub
		System.out.println(" 女人给的评价该歌手很成功 !");
	}
}

public class Fail extends Action {
	@Override
	public void getManResult(Man man) {
		// TODO Auto-generated method stub
		System.out.println(" 男人给的评价该歌手失败 !");
	}

	@Override
	public void getWomanResult(Woman woman) {
		// TODO Auto-generated method stub
		System.out.println(" 女人给的评价该歌手失败 !");
	}
}

```

接收访问者对象：

```java
public abstract class Person {
	//提供一个方法，让访问者可以访问
	public abstract void accept(Action action);
}

//说明
//1. 这里我们使用到了双分派, 即首先在客户端程序中，将具体状态作为参数传递Woman中(第一次分派)
//2. 然后Woman 类调用作为参数的 "具体方法" 中方法getWomanResult, 同时将自己(this)作为参数
//   传入，完成第二次的分派
public class Woman extends Person{
	@Override
	public void accept(Action action) {
		// TODO Auto-generated method stub
		action.getWomanResult(this);
	}

}

public class Man extends Person {
	@Override
	public void accept(Action action) {
		// TODO Auto-generated method stub
		action.getManResult(this);
	}
}

所谓双分派是指不管类怎么变化，我们都能找到期望的方法运行。双分派意味着得到执行的操作取决于请求的种类和两个接收者的类型。
以上述实例为例，假设我们要添加一个 Wait 的状态类，考察 Man 类和 Woman 类的反应，由于使用了双分
派，只需增加一个 Action 子类即可在客户端调用即可，不需要改动任何其他类的代码。 

双分派在这个案例中不是很合适，但是仔细理解还是很巧妙。(可以通过类图和debug代码去理解)
```

需要被访问的数据结构——含有接收访问者对象的集合：

```java
//数据结构，管理很多人（Man , Woman）
public class ObjectStructure {
	//维护了一个集合
	private List<Person> persons = new LinkedList<>();
	
	//增加到list
	public void attach(Person p) {
		persons.add(p);
	}
	//移除
	public void detach(Person p) {
		persons.remove(p);
	}
	 
	//显示测评情况 。 这个方法只是简单的显示。不涉及实际功能 
	public void display(Action action) {
		for(Person p: persons) {
			p.accept(action);
		}
	}
}
```

客户端：

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建ObjectStructure
		ObjectStructure objectStructure = new ObjectStructure();
		
		objectStructure.attach(new Man());
		objectStructure.attach(new Woman());
		
		
		//成功
		Success success = new Success();
		objectStructure.display(success);
		
		System.out.println("===============");
		Fail fail = new Fail();
		objectStructure.display(fail);
		
		System.out.println("=======给的是待定的测评========");
		
		Wait wait = new Wait();
		objectStructure.display(wait);
	}
```

上面代码的设计只是双分派的一种(不是很好理解)，另外一种不是双分派的实现思路是： 不在action中针对person的分类定义两个方法，在action 中只定义一个方法 getResult(action ) 根据传过来的action不同，得到不同的结果。(这样的扩展性感觉会更好，也更简单)

##### 访问者模式的总结

```
 优点
1) 访问者模式符合单一职责原则(让数据结构和操作进行了解耦)、让程序具有优秀的扩展性、灵活性非常高
2) 访问者模式可以对功能进行统一，可以做报表、UI、拦截器与过滤器，适用于数据结构相对稳定的系统

缺点
1) 具体元素对访问者公布细节，也就是说访问者关注了其他类的内部细节，这是迪米特法则所不建议的, 这样造
成了具体元素变更比较困难

因此，如果一个系统有比较稳定的数据结构，又有经常变化的功能需求，那么访问者模式就是比较合适的.
```



#### 迭代器模式

![JAVAWEB_DESIGN97.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN97.png?raw=true)

![JAVAWEB_DESIGN98.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN98.png?raw=true)

##### 迭代器模式实现统一的遍历

```
1) 迭代器模式（Iterator Pattern）是常用的设计模式，属于行为型模式
2) 如果我们的集合元素是用不同的方式实现的，有数组，还有 java 的集合类，或者还有其他方式，当客户端要遍
历这些集合元素的时候就要使用多种遍历方式，而且还会暴露元素的内部结构，可以考虑使用迭代器模式解决。
3) 迭代器模式，提供一种遍历集合元素的统一接口，用一致的方法遍历集合元素，不需要知道集合对象的底层表
示，即：不暴露其内部的结构。
```

![JAVAWEB_DESIGN99.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN99.png?raw=true)

迭代器遍历学校问题

![JAVAWEB_DESIGN100.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN100.png?raw=true)

代码实现：

遍历方式：

```java
//系
public class Department {
	private String name;
	private String desc;
    //getter ,setter
}

public class ComputerCollegeIterator implements Iterator {
//这里我们需要Department 是以怎样的方式存放=>数组
	Department[] departments;
	int position = 0; //遍历的位置
	
	public ComputerCollegeIterator(Department[] departments) {
		this.departments = departments;
	}

	//判断是否还有下一个元素
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(position >= departments.length || departments[position] == null) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public Object next() {
		// TODO Auto-generated method stub
		Department department = departments[position];
		position += 1;
		return department;
	}
	
	//删除的方法，默认空实现
	public void remove() {
		
	}
}

public class InfoColleageIterator implements Iterator {	
	List<Department> departmentList; // 信息工程学院是以List方式存放系
	int index = -1;//索引
	
	public InfoColleageIterator(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	//判断list中还有没有下一个元素
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(index >= departmentList.size() - 1) {
			return false;
		} else {
			index += 1;
			return true;
		}
	}

	@Override
	public Object next() {
		// TODO Auto-generated method stub
		return departmentList.get(index);
	}
	
	//空实现remove
	public void remove() {
		
	}

}
```

数据存储层：

```java
public interface College {
	public String getName();
	//增加系的方法
	public void addDepartment(String name, String desc);
	//返回一个迭代器,遍历
	public Iterator  createIterator();
}

public class ComputerCollege implements College {
	Department[] departments;
	int numOfDepartment = 0 ;// 保存当前数组的对象个数
	
	public ComputerCollege() {
		departments = new Department[5];
		addDepartment("Java专业", " Java专业 ");
		addDepartment("PHP专业", " PHP专业 ");
		addDepartment("大数据专业", " 大数据专业 ");	
	}
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "计算机学院";
	}

	@Override
	public void addDepartment(String name, String desc) {
		// TODO Auto-generated method stub
		Department department = new Department(name, desc);
		departments[numOfDepartment] = department;
		numOfDepartment += 1;
	}

	@Override
	public Iterator createIterator() {
		// TODO Auto-generated method stub
		return new ComputerCollegeIterator(departments);
	}

}

public class InfoCollege implements College {
	List<Department> departmentList;
	public InfoCollege() {
		departmentList = new ArrayList<Department>();
		addDepartment("信息安全专业", " 信息安全专业 ");
		addDepartment("网络安全专业", " 网络安全专业 ");
		addDepartment("服务器安全专业", " 服务器安全专业 ");
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "信息工程学院";
	}

	@Override
	public void addDepartment(String name, String desc) {
		// TODO Auto-generated method stub
		Department department = new Department(name, desc);
		departmentList.add(department);
	}

	@Override
	public Iterator createIterator() {
		// TODO Auto-generated method stub
		return new InfoColleageIterator(departmentList);
	}
}

public class OutPutImpl {
	//学院集合
	List<College> collegeList;
	public OutPutImpl(List<College> collegeList) {
		this.collegeList = collegeList;
	}
	//遍历所有学院,然后调用printDepartment 输出各个学院的系
	public void printCollege() {
		//从collegeList 取出所有学院, Java 中的 List 已经实现Iterator
		Iterator<College> iterator = collegeList.iterator();
		while(iterator.hasNext()) {
			//取出一个学院
			College college = iterator.next();
			System.out.println("=== "+college.getName() +"=====" );
			printDepartment(college.createIterator()); //得到对应迭代器
		}
	}
	
	//输出 学院输出 系	
	public void printDepartment(Iterator iterator) {
		while(iterator.hasNext()) {
			Department d = (Department)iterator.next();
			System.out.println(d.getName());
		}
	}
	
}
```

客户端：

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建学院
		List<College> collegeList = new ArrayList<College>();		
		ComputerCollege computerCollege = new ComputerCollege();
		InfoCollege infoCollege = new InfoCollege();
		
		collegeList.add(computerCollege);
		//collegeList.add(infoCollege);
		OutPutImpl outPutImpl = new OutPutImpl(collegeList);
		outPutImpl.printCollege();
	}
```



##### 迭代器模式在JDK中的应用

JDK 的 ArrayList 集合中就使用了迭代器模式

![JAVAWEB_DESIGN101.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN101.png?raw=true)

##### 迭代器模式的总结

```
优点
1) 提供一个统一的方法遍历对象，客户不用再考虑聚合的类型，使用一种方法就可以遍历对象了。
2) 隐藏了聚合的内部结构，客户端要遍历聚合的时候只能取到迭代器，而不会知道聚合的具体组成。
3) 提供了一种设计思想，就是一个类应该只有一个引起变化的原因（叫做单一责任原则）。在聚合类中，我们把迭代器分开，就是要把管理对象集合和遍历对象集合的责任分开，这样一来集合改变的话，只影响到聚合对象。而如果遍历方式改变的话，只影响到了迭代器。
4) 当要展示一组相似对象，或者遍历一组相同对象时使用, 适合使用迭代器模式

缺点
每个聚合对象都要一个迭代器，会生成多个迭代器不好管理类
```



#### 观察者模式

##### 需求

![JAVAWEB_DESIGN102.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN102.png?raw=true)

(难度： 实时的通知第三方)

传统方案：

![JAVAWEB_DESIGN103.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN103.png?raw=true)

普通方式代码实现

```java
/**
 * 类是核心
 * 1. 包含最新的天气情况信息 
 * 2. 含有 CurrentConditions 对象
 * 3. 当数据有更新时，就主动的调用   CurrentConditions对象update方法(含 display), 这样他们（接入方）就看到最新的信息
 * @author Administrator
 *
 */
public class WeatherData {
	private float temperatrue;
	private float pressure;
	private float humidity;
	private CurrentConditions currentConditions;
	//加入新的第三方

	public WeatherData(CurrentConditions currentConditions) {
		this.currentConditions = currentConditions;
	}

	public float getTemperature() {
		return temperatrue;
	}

	public float getPressure() {
		return pressure;
	}

	public float getHumidity() {
		return humidity;
	}

	public void dataChange() {
		//调用 接入方的 update
		currentConditions.update(getTemperature(), getPressure(), getHumidity());
	}

	//当数据有更新时，就调用 setData
	public void setData(float temperature, float pressure, float humidity) {
		this.temperatrue = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		//调用dataChange， 将最新的信息 推送给 接入方 currentConditions
		dataChange();
	}
}

/**
 * 显示当前天气情况（可以理解成是气象站自己的网站）
 * @author Administrator
 *
 */
public class CurrentConditions {
	// 温度，气压，湿度
	private float temperature;
	private float pressure;
	private float humidity;

	//更新 天气情况，是由 WeatherData 来调用，我使用推送模式
	public void update(float temperature, float pressure, float humidity) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		display();
	}

	//显示
	public void display() {
		System.out.println("***Today mTemperature: " + temperature + "***");
		System.out.println("***Today mPressure: " + pressure + "***");
		System.out.println("***Today mHumidity: " + humidity + "***");
	}
}

public static void main(String[] args) {
		//创建接入方 currentConditions
		CurrentConditions currentConditions = new CurrentConditions();
		//创建 WeatherData 并将 接入方 currentConditions 传递到 WeatherData中
		WeatherData weatherData = new WeatherData(currentConditions);
		
		//更新天气情况
		weatherData.setData(30, 150, 40);
		
		//天气情况变化
		System.out.println("============天气情况变化=============");
		weatherData.setData(40, 160, 20);
}
```

这个方案的问题

其他第三方接入气象站获取数据的问题，不能动态的添加第三方。   在 WeatherData 中，当增加一个第三方，都需要创建一个对应的第三方的公告板对象，并加入到 dataChange, 违反了OCP原则，不利于维护。

##### 观察者模式原理

![JAVAWEB_DESIGN104.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN104.png?raw=true)

##### 观察者模式解决天气预报问题

![JAVAWEB_DESIGN105.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN105.png?raw=true)

代码实现

核心类：

```java
//接口, 让WeatherData 来实现 
public interface Subject {
	
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}

/**
 * 类是核心
 * 1. 包含最新的天气情况信息 
 * 2. 含有 观察者集合，使用ArrayList管理
 * 3. 当数据有更新时，就主动的调用   ArrayList, 通知所有的（接入方）就看到最新的信息
 * @author Administrator
 *
 */
public class WeatherData implements Subject {
	private float temperatrue;
	private float pressure;
	private float humidity;
	//观察者集合
	private ArrayList<Observer> observers;
	
	//加入新的第三方

	public WeatherData() {
		observers = new ArrayList<Observer>();
	}

	public float getTemperature() {
		return temperatrue;
	}

	public float getPressure() {
		return pressure;
	}

	public float getHumidity() {
		return humidity;
	}

	public void dataChange() {
		//调用 接入方的 update
		
		notifyObservers();
	}

	//当数据有更新时，就调用 setData
	public void setData(float temperature, float pressure, float humidity) {
		this.temperatrue = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		//调用dataChange， 将最新的信息 推送给 接入方 currentConditions
		dataChange();
	}

	//注册一个观察者
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	//移除一个观察者
	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		if(observers.contains(o)) {
			observers.remove(o);
		}
	}

	//遍历所有的观察者，并通知
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(int i = 0; i < observers.size(); i++) {
			observers.get(i).update(this.temperatrue, this.pressure, this.humidity);
		}
	}
}

```

观察者类：

```java
//观察者接口，有观察者来实现
public interface Observer {
	public void update(float temperature, float pressure, float humidity);
}


public class BaiduSite implements Observer {
	// 温度，气压，湿度
	private float temperature;
	private float pressure;
	private float humidity;
	// 更新 天气情况，是由 WeatherData 来调用，我使用推送模式
	public void update(float temperature, float pressure, float humidity) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		display();
	}

	// 显示
	public void display() {
		System.out.println("===百度网站====");
		System.out.println("***百度网站 气温 : " + temperature + "***");
		System.out.println("***百度网站 气压: " + pressure + "***");
		System.out.println("***百度网站 湿度: " + humidity + "***");
	}
}


public class CurrentConditions implements Observer {

	// 温度，气压，湿度
	private float temperature;
	private float pressure;
	private float humidity;

	// 更新 天气情况，是由 WeatherData 来调用，我使用推送模式
	public void update(float temperature, float pressure, float humidity) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		display();
	}

	// 显示
	public void display() {
		System.out.println("***Today mTemperature: " + temperature + "***");
		System.out.println("***Today mPressure: " + pressure + "***");
		System.out.println("***Today mHumidity: " + humidity + "***");
	}
}

```

客户端

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建一个WeatherData
		WeatherData weatherData = new WeatherData();
		
		//创建观察者
		CurrentConditions currentConditions = new CurrentConditions();
		BaiduSite baiduSite = new BaiduSite();
		
		//注册到weatherData
		weatherData.registerObserver(currentConditions);
		weatherData.registerObserver(baiduSite);
		
		//测试
		System.out.println("通知各个注册的观察者, 看看信息");
		weatherData.setData(10f, 100f, 30.3f);
		
		
		weatherData.removeObserver(currentConditions);
		//测试
		System.out.println();
		System.out.println("通知各个注册的观察者, 看看信息");
		weatherData.setData(10f, 100f, 30.3f);
	}
```

观察者模式的好处

```
1) 观察者模式设计后，会以集合的方式来管理用户(Observer)，包括注册，移除和通知。
2) 这样，我们增加观察者(这里可以理解成一个新的公告板)，就不需要去修改核心类 WeatherData 不会修改代码，
遵守了 ocp 原则
```

##### 观察者模式在JDK中的应用

Jdk 的 Observable 类就使用了观察者模式

![JAVAWEB_DESIGN106.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN106.png?raw=true)



#### 中介者模式

需求：

![JAVAWEB_DESIGN107.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN107.png?raw=true)

##### 中介者模式原理

![JAVAWEB_DESIGN108.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN108.png?raw=true)

(中介者模式的好处是： 减少了子系统之间的交流，存在交流就需要提供接口)

```
1) 中介者模式（Mediator Pattern），用一个中介对象来封装一系列的对象交互。中介者使各个对象不需要显式地
相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互
2) 中介者模式属于行为型模式，使代码易于维护
3) 比如 MVC 模式，C（Controller 控制器）是 M（Model 模型）和 V（View 视图）的中介者，在前后端交互时起
到了中间人的作用
```

![JAVAWEB_DESIGN109.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN109.png?raw=true)

中介者模式原理类图：

![JAVAWEB_DESIGN110.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN110.png?raw=true)

##### 中介者模式实现智能家居案例

![JAVAWEB_DESIGN111.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN111.png?raw=true)

代码实现：

中介者层：

```java
public abstract class Mediator {
	//将一个同事对象，加入到集合中
	public abstract void Register(String colleagueName, Colleague colleague);
	//接收消息, 具体的同事对象发出
	public abstract void GetMessage(int stateChange, String colleagueName);
	public abstract void SendMessage();
}

//具体的中介者类
public class ConcreteMediator extends Mediator {
	//集合，放入所有的同事对象
	private HashMap<String, Colleague> colleagueMap;
	private HashMap<String, String> interMap;

	public ConcreteMediator() {
		colleagueMap = new HashMap<String, Colleague>();
		interMap = new HashMap<String, String>();
	}

	@Override
	public void Register(String colleagueName, Colleague colleague) {
		// TODO Auto-generated method stub
		colleagueMap.put(colleagueName, colleague);
		// TODO Auto-generated method stub
		if (colleague instanceof Alarm) {
			interMap.put("Alarm", colleagueName);
		} else if (colleague instanceof CoffeeMachine) {
			interMap.put("CoffeeMachine", colleagueName);
		} else if (colleague instanceof TV) {
			interMap.put("TV", colleagueName);
		} else if (colleague instanceof Curtains) {
			interMap.put("Curtains", colleagueName);
		}

	}

	//具体中介者的核心方法
	//1. 根据得到消息，完成对应任务
	//2. 中介者在这个方法，协调各个具体的同事对象，完成任务
	@Override
	public void GetMessage(int stateChange, String colleagueName) {
		// TODO Auto-generated method stub

		//处理闹钟发出的消息
		if (colleagueMap.get(colleagueName) instanceof Alarm) {
			if (stateChange == 0) {
				((CoffeeMachine) (colleagueMap.get(interMap
						.get("CoffeeMachine")))).StartCoffee();
				((TV) (colleagueMap.get(interMap.get("TV")))).StartTv();
			} else if (stateChange == 1) {
				((TV) (colleagueMap.get(interMap.get("TV")))).StopTv();
			}

		} else if (colleagueMap.get(colleagueName) instanceof CoffeeMachine) {
			((Curtains) (colleagueMap.get(interMap.get("Curtains"))))
					.UpCurtains();

		} else if (colleagueMap.get(colleagueName) instanceof TV) {//如果TV发现消息

		} else if (colleagueMap.get(colleagueName) instanceof Curtains) {
			//如果是以窗帘发出的消息，这里处理...
		}

	}

	@Override
	public void SendMessage() {
		// TODO Auto-generated method stub
	}

}
```

同事层：

```java
//同事抽象类
public abstract class Colleague {
	private Mediator mediator;
	public String name;

	public Colleague(Mediator mediator, String name) {
		this.mediator = mediator;
		this.name = name;
	}

	public Mediator GetMediator() {
		return this.mediator;
	}

	public abstract void SendMessage(int stateChange);
}

//具体的同事类
public class Alarm extends Colleague {
	//构造器
	public Alarm(Mediator mediator, String name) {
		super(mediator, name);
		// TODO Auto-generated constructor stub
		//在创建Alarm 同事对象时，将自己放入到ConcreteMediator 对象中[集合]
		mediator.Register(name, this);
	}

	public void SendAlarm(int stateChange) {
		SendMessage(stateChange);
	}

	@Override
	public void SendMessage(int stateChange) {
		// TODO Auto-generated method stub
		//调用的中介者对象的getMessage
		this.GetMediator().GetMessage(stateChange, this.name);
	}

}

public class TV extends Colleague {
	public TV(Mediator mediator, String name) {
		super(mediator, name);
		// TODO Auto-generated constructor stub
		mediator.Register(name, this);
	}

	@Override
	public void SendMessage(int stateChange) {
		// TODO Auto-generated method stub
		this.GetMediator().GetMessage(stateChange, this.name);
	}

	public void StartTv() {
		// TODO Auto-generated method stub
		System.out.println("It's time to StartTv!");
	}

	public void StopTv() {
		// TODO Auto-generated method stub
		System.out.println("StopTv!");
	}
}

```

客户端

```java
public static void main(String[] args) {
		//创建一个中介者对象
		Mediator mediator = new ConcreteMediator();
		
		//创建Alarm 并且加入到  ConcreteMediator 对象的HashMap
		Alarm alarm = new Alarm(mediator, "alarm");
		
		//创建了CoffeeMachine 对象，并  且加入到  ConcreteMediator 对象的HashMap
		CoffeeMachine coffeeMachine = new CoffeeMachine(mediator,
				"coffeeMachine");
		
		//创建 Curtains , 并  且加入到  ConcreteMediator 对象的HashMap
		Curtains curtains = new Curtains(mediator, "curtains");
		TV tV = new TV(mediator, "TV");
		
		//让闹钟发出消息
		alarm.SendAlarm(0);
		coffeeMachine.FinishCoffee();
		alarm.SendAlarm(1);
	}
```

##### 中介者模式总结

```
1) 多个类相互耦合，会形成网状结构, 使用中介者模式将网状结构分离为星型结构，进行解耦
2) 减少类间依赖，降低了耦合，符合迪米特原则
3) 中介者承担了较多的责任，一旦中介者出现了问题，整个系统就会受到影响
4) 如果设计不当，中介者对象本身变得过于复杂，这点在实际使用时，要特别注意
```



#### 备忘录模式

![JAVAWEB_DESIGN112.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN112.png?raw=true)

##### 基本介绍和原理分析

```
备忘录模式（Memento Pattern）在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这
个状态。这样以后就可将该对象恢复到原先保存的状态

现实生活中的备忘录是用来记录某些要去做的事情，或者是记录已经达成的共同意见的事情，以防忘记了。而在软件层面，备忘录模式有着相同的含义，备忘录对象主要用来记录一个对象的某种状态，或者某些数据，当要做回退时，可以从备忘录对象里获取原来的数据进行恢复操作
```

![JAVAWEB_DESIGN113.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN113.png?raw=true)

代码实现

```java
//原始对象
public class Originator {
	private String state;//状态信息
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	//编写一个方法，可以保存一个状态对象 Memento
	//因此编写一个方法，返回 Memento
	public Memento saveStateMemento() {
		return new Memento(state);
	}
	
	//通过备忘录对象，恢复状态
	public void getStateFromMemento(Memento memento) {
		state = memento.getState();
	}
}

// 用于保存对象状态的备忘录对象
public class Memento {
	private String state;
	//构造器
	public Memento(String state) {
		super();
		this.state = state;
	}

	public String getState() {
		return state;
	}		
}

//保存对象状态的时候需要一个规则
public class Caretaker {
	//在List 集合中会有很多的备忘录对象。 对象和他的备忘录对象是分开的
    // 一个原始对象可能会存在多个备忘录对象
	private List<Memento> mementoList = new ArrayList<Memento>();
	
	public void add(Memento memento) {
		mementoList.add(memento);
	}
	
	//获取到第index个Originator 的 备忘录对象(即保存状态)
	public Memento get(int index) {
		return mementoList.get(index);
	}
}

public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Originator originator = new Originator();
		Caretaker caretaker = new Caretaker();
		originator.setState(" 状态#1 攻击力 100 ");
		
		//保存了当前的状态
		caretaker.add(originator.saveStateMemento());
		originator.setState(" 状态#2 攻击力 80 ");
		caretaker.add(originator.saveStateMemento());
		originator.setState(" 状态#3 攻击力 50 ");
		caretaker.add(originator.saveStateMemento());
		
		System.out.println("当前的状态是 =" + originator.getState());
		//希望得到状态 1, 将 originator 恢复到状态1
		
		originator.getStateFromMemento(caretaker.get(0));
		System.out.println("恢复到状态1 , 当前的状态是");
		System.out.println("当前的状态是 =" + originator.getState());
	}
```

##### 备份录模式实现游戏角色恢复

![JAVAWEB_DESIGN114.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN114.png?raw=true)

代码实现

```java
public class Memento {
	//攻击力
	private int vit;
	//防御力
	private int def;
	public Memento(int vit, int def) {
		super();
		this.vit = vit;
		this.def = def;
	}
	// getter（） 和setter
}

//守护者对象, 保存游戏角色的状态
public class Caretaker {

	//如果只保存一次状态
	private Memento  memento;
	//对GameRole 保存多次状态
	//private ArrayList<Memento> mementos;
	//对多个游戏角色保存多个状态
	//private HashMap<String, ArrayList<Memento>> rolesMementos;

	public Memento getMemento() {
		return memento;
	}

	public void setMemento(Memento memento) {
		this.memento = memento;
	}		
}

public class GameRole {

	private int vit;
	private int def;
	
	//创建Memento ,即根据当前的状态得到Memento
	public Memento createMemento() {
		return new Memento(vit, def);
	}
	
	//从备忘录对象，恢复GameRole的状态
	public void recoverGameRoleFromMemento(Memento memento) {
		this.vit = memento.getVit();
		this.def = memento.getDef();
	}
	
	//显示当前游戏角色的状态
	public void display() {
		System.out.println("游戏角色当前的攻击力：" + this.vit + " 防御力: " + this.def);
	}

	// getter（） 和setter
	
}

public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建游戏角色
		GameRole gameRole = new GameRole();
		gameRole.setVit(100);
		gameRole.setDef(100);
		System.out.println("和boss大战前的状态");
		gameRole.display();
		
		//把当前状态保存caretaker
		Caretaker caretaker = new Caretaker();
		caretaker.setMemento(gameRole.createMemento());
		
		System.out.println("和boss大战~~~");
		gameRole.setDef(30);
		gameRole.setVit(30);
		gameRole.display();
		System.out.println("大战后，使用备忘录对象恢复到站前");
		
		gameRole.recoverGameRoleFromMemento(caretaker.getMemento());
		System.out.println("恢复后的状态");
		gameRole.display();
	}
```



```
备忘录模式的注意事项和细节
1) 给用户提供了一种可以恢复状态的机制，可以使用户能够比较方便地回到某个历史的状态
2) 实现了信息的封装，使得用户不需要关心状态的保存细节
3) 如果类的成员变量过多，势必会占用比较大的资源，而且每一次保存都会消耗一定的内存, 这个需要注意
4) 适用的应用场景：1、后悔药。 2、打游戏时的存档。 3、Windows 里的 ctri + z。 4、IE 中的后退。 4、数
据库的事务管理
5) 为了节约内存，备忘录模式可以和原型模式配合使用
```



#### 解释器模式（Interpreter 模式）

需求：

![JAVAWEB_DESIGN115.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN115.png?raw=true)

传统方案解决四则运算问题分析：编写一个方法，接收表达式的形式，然后根据用户输入的数值进行解析，得到结果

```
问题分析：如果加入新的运算符，比如 * / ( 等等，不利于扩展，另外让一个方法来解析会造成程序结构混乱，不够清晰.
```

##### 基本介绍和原理分析

```
基本介绍： 给定一个语言，定义解释器，用解释器来解释这个语言。

即： 表达式 -> 解释器(可以有多种) -> 结果
```

![JAVAWEB_DESIGN116.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN116.png?raw=true)

##### 解释器模式实现四则

![JAVAWEB_DESIGN117.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN117.png?raw=true)

代码实现

表达式层

```java
/**
 * 抽象类表达式，通过HashMap 键值对, 可以获取到变量的值
 * 
 * @author Administrator
 *
 */
public abstract class Expression {
	// a + b - c
	// 解释公式和数值, key 就是公式(表达式) 参数[a,b,c], value就是就是具体值
	// HashMap {a=10, b=20}
	public abstract int interpreter(HashMap<String, Integer> var);
}

/**
 * 变量的解释器
 * @author Administrator
 *
 */
public class VarExpression extends Expression {

	private String key; // key=a,key=b,key=c

	public VarExpression(String key) {
		this.key = key;
	}

	// var 就是{a=10, b=20}
	// interpreter 根据 变量名称，返回对应值
	@Override
	public int interpreter(HashMap<String, Integer> var) {
		return var.get(this.key);
	}
}

/**
 * 抽象运算符号解析器 这里，每个运算符号，都只和自己左右两个数字有关系，
 * 但左右两个数字有可能也是一个解析的结果，无论何种类型，都是Expression类的实现类
 * 
 * @author Administrator
 *
 */
public class SymbolExpression extends Expression {
	protected Expression left;
	protected Expression right;

	public SymbolExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	//因为 SymbolExpression 是让其子类来实现，因此 interpreter 是一个默认实现
	@Override
	public int interpreter(HashMap<String, Integer> var) {
		// TODO Auto-generated method stub
		return 0;
	}
}

public class SubExpression extends SymbolExpression {

	public SubExpression(Expression left, Expression right) {
		super(left, right);
	}

	//求出left 和 right 表达式相减后的结果
	public int interpreter(HashMap<String, Integer> var) {
		return super.left.interpreter(var) - super.right.interpreter(var);
	}
}

/**
 * 加法解释器
 * @author Administrator
 *
 */
public class AddExpression extends SymbolExpression  {

	public AddExpression(Expression left, Expression right) {
		super(left, right);
	}

	//处理相加
	//var 仍然是 {a=10,b=20}..
	//一会我们debug 源码,就ok
	public int interpreter(HashMap<String, Integer> var) {
		//super.left.interpreter(var) ： 返回 left 表达式对应的值 a = 10
		//super.right.interpreter(var): 返回right 表达式对应值 b = 20
		return super.left.interpreter(var) + super.right.interpreter(var);
	}
}

```

计算器

```java
public class Calculator {
	// 定义表达式
	private Expression expression;

	// 构造函数传参，并解析
	public Calculator(String expStr) { // expStr = a+b
		// 安排运算先后顺序
		Stack<Expression> stack = new Stack<>();
		// 表达式拆分成字符数组 
		char[] charArray = expStr.toCharArray();// [a, +, b]

		Expression left = null;
		Expression right = null;
		//遍历我们的字符数组， 即遍历  [a, +, b]
		//针对不同的情况，做处理
		for (int i = 0; i < charArray.length; i++) {
			switch (charArray[i]) {
			case '+': //
				left = stack.pop();// 从stack取出left => "a"
				right = new VarExpression(String.valueOf(charArray[++i]));// 取出右表达式 "b"
				stack.push(new AddExpression(left, right));// 然后根据得到left 和 right 构建 AddExpresson加入stack
				break;
			case '-': // 
				left = stack.pop();
				right = new VarExpression(String.valueOf(charArray[++i]));
				stack.push(new SubExpression(left, right));
				break;
			default: 
				//如果是一个 Var 就创建要给 VarExpression 对象，并push到 stack
				stack.push(new VarExpression(String.valueOf(charArray[i])));
				break;
			}
		}
		//当遍历完整个 charArray 数组后，stack 就得到最后Expression
		this.expression = stack.pop();
	}

	public int run(HashMap<String, Integer> var) {
		//最后将表达式a+b和 var = {a=10,b=20}
		//然后传递给expression的interpreter进行解释执行
		return this.expression.interpreter(var);
	}
}

public class ClientTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String expStr = getExpStr(); // a+b
		HashMap<String, Integer> var = getValue(expStr);// var {a=10, b=20}
		Calculator calculator = new Calculator(expStr);
		System.out.println("运算结果：" + expStr + "=" + calculator.run(var));
	}

	// 获得表达式
	public static String getExpStr() throws IOException {
		System.out.print("请输入表达式：");
		return (new BufferedReader(new InputStreamReader(System.in))).readLine();
	}

	// 获得值映射
	public static HashMap<String, Integer> getValue(String expStr) throws IOException {
		HashMap<String, Integer> map = new HashMap<>();

		for (char ch : expStr.toCharArray()) {
			if (ch != '+' && ch != '-') {
				if (!map.containsKey(String.valueOf(ch))) {
					System.out.print("请输入" + String.valueOf(ch) + "的值：");
					String in = (new BufferedReader(new InputStreamReader(System.in))).readLine();
					map.put(String.valueOf(ch), Integer.valueOf(in));
				}
			}
		}

		return map;
	}
}

```

##### 解释器模式在 Spring 框架应用的源码剖析

Spring 框架中 SpelExpressionParser 就使用到解释器模式

![JAVAWEB_DESIGN118.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN118.png?raw=true)

##### 解释器模式总结

```
1) 当有一个语言需要解释执行，可将该语言中的句子表示为一个抽象语法树，就可以考虑使用解释器模式，让程序具有良好的扩展性
2) 应用场景：编译器、运算表达式计算、正则表达式、机器人等
3) 使用解释器可能带来的问题：解释器模式会引起类膨胀、解释器模式采用递归调用方法，将会导致调试非常复杂、效率可能降低.
```



#### 状态模式

![JAVAWEB_DESIGN119.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN119.png?raw=true)

分析： 我们发现他的状态在活动中是不断的变化的

##### 基本介绍和原理分析

```
1) 状态模式（State Pattern）：它主要用来解决对象在多种状态转换时，需要对外输出不同的行为的问题。状态和行为是一一对应的，状态之间可以相互转换(一个状态对应一种行为)
2) 当一个对象的内在状态改变时，允许改变其行为，这个对象看起来像是改变了其类
```

![JAVAWEB_DESIGN120.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN120.png?raw=true)

##### 状态模式解决抽奖问题

![JAVAWEB_DESIGN121.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN121.png?raw=true)

代码实现：

状态层

```java
/**
 * 状态抽象类
 * @author Administrator
 *
 */
public abstract class State {	
	// 扣除积分 - 50
    public abstract void deductMoney();
    // 是否抽中奖品
    public abstract boolean raffle();
    // 发放奖品
    public abstract  void dispensePrize();
}

/**
 * 不能抽奖状态
 * @author Administrator
 *
 */
public class NoRaffleState extends State {

	 // 初始化时传入活动引用，扣除积分后改变其状态
    RaffleActivity activity;

    public NoRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }

    // 当前状态可以扣积分 , 扣除后，将状态设置成可以抽奖状态
    @Override
    public void deductMoney() {
        System.out.println("扣除50积分成功，您可以抽奖了");
        activity.setState(activity.getCanRaffleState());
    }

    // 当前状态不能抽奖
    @Override
    public boolean raffle() {
        System.out.println("扣了积分才能抽奖喔！");
        return false;
    }

    // 当前状态不能发奖品
    @Override
    public void dispensePrize() {
        System.out.println("不能发放奖品");
    }
}

/**
 * 发放奖品的状态
 * @author Administrator
 *
 */
public class DispenseState extends State {

	 // 初始化时传入活动引用，发放奖品后改变其状态
    RaffleActivity activity;

    public DispenseState(RaffleActivity activity) {
        this.activity = activity;
    }
    
    //

    @Override
    public void deductMoney() {
        System.out.println("不能扣除积分");
    }

    @Override
    public boolean raffle() {
        System.out.println("不能抽奖");
        return false;
    }

    //发放奖品
    @Override
    public void dispensePrize() {
        if(activity.getCount() > 0){
            System.out.println("恭喜中奖了");
            // 改变状态为不能抽奖
            activity.setState(activity.getNoRafflleState());
        }else{
            System.out.println("很遗憾，奖品发送完了");
            // 改变状态为奖品发送完毕, 后面我们就不可以抽奖
            activity.setState(activity.getDispensOutState());
            //System.out.println("抽奖活动结束");
            //System.exit(0);
        }

    }
}

/**
 * 奖品发放完毕状态
 * 说明，当我们activity 改变成 DispenseOutState， 抽奖活动结束
 * @author Administrator
 *
 */
public class DispenseOutState extends State {

	// 初始化时传入活动引用
    RaffleActivity activity;

    public DispenseOutState(RaffleActivity activity) {
        this.activity = activity;
    }
    @Override
    public void deductMoney() {
        System.out.println("奖品发送完了，请下次再参加");
    }

    @Override
    public boolean raffle() {
        System.out.println("奖品发送完了，请下次再参加");
        return false;
    }

    @Override
    public void dispensePrize() {
        System.out.println("奖品发送完了，请下次再参加");
    }
}

/**
 * 可以抽奖的状态
 * @author Administrator
 *
 */
public class CanRaffleState extends State {

    RaffleActivity activity;

    public CanRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }

    //已经扣除了积分，不能再扣
    @Override
    public void deductMoney() {
        System.out.println("已经扣取过了积分");
    }

    //可以抽奖, 抽完奖后，根据实际情况，改成新的状态
    @Override
    public boolean raffle() {
        System.out.println("正在抽奖，请稍等！");
        Random r = new Random();
        int num = r.nextInt(10);
        // 10%中奖机会
        if(num == 0){
            // 改变活动状态为发放奖品 context
            activity.setState(activity.getDispenseState());
            return true;
        }else{
            System.out.println("很遗憾没有抽中奖品！");
            // 改变状态为不能抽奖
            activity.setState(activity.getNoRafflleState());
            return false;
        }
    }

    // 不能发放奖品
    @Override
    public void dispensePrize() {
        System.out.println("没中奖，不能发放奖品");
    }
}

```

活动层

```java
/**
 * 抽奖活动 //
 * 
 * @author Administrator
 *
 */
public class RaffleActivity {

	// state 表示活动当前的状态，是变化
    State state = null;
    // 奖品数量
    int count = 0;
    
    // 四个属性，表示四种状态
    State noRafflleState = new NoRaffleState(this);
    State canRaffleState = new CanRaffleState(this);
    
    State dispenseState =   new DispenseState(this);
    State dispensOutState = new DispenseOutState(this);

    //构造器
    //1. 初始化当前的状态为 noRafflleState（即不能抽奖的状态）
    //2. 初始化奖品的数量 
    public RaffleActivity( int count) {
        this.state = getNoRafflleState();
        this.count = count;
    }

    //扣分, 调用当前状态的 deductMoney
    public void debuctMoney(){
        state.deductMoney();
    }

    //抽奖 
    public void raffle(){
    	// 如果当前的状态是抽奖成功
        if(state.raffle()){
        	//领取奖品
            state.dispensePrize();
        }

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    //这里请大家注意，每领取一次奖品，count--
    public int getCount() {
    	int curCount = count; 
    	count--;
        return curCount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getNoRafflleState() {
        return noRafflleState;
    }

    public void setNoRafflleState(State noRafflleState) {
        this.noRafflleState = noRafflleState;
    }

    public State getCanRaffleState() {
        return canRaffleState;
    }

    public void setCanRaffleState(State canRaffleState) {
        this.canRaffleState = canRaffleState;
    }

    public State getDispenseState() {
        return dispenseState;
    }

    public void setDispenseState(State dispenseState) {
        this.dispenseState = dispenseState;
    }

    public State getDispensOutState() {
        return dispensOutState;
    }

    public void setDispensOutState(State dispensOutState) {
        this.dispensOutState = dispensOutState;
    }
}

//客户端
public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建活动对象，奖品有1个奖品
        RaffleActivity activity = new RaffleActivity(1);

        // 我们连续抽300次奖
        for (int i = 0; i < 30; i++) {
            System.out.println("--------第" + (i + 1) + "次抽奖----------");
            // 参加抽奖，第一步点击扣除积分
            activity.debuctMoney();

            // 第二步抽奖
            activity.raffle();
        }
	}

```

##### 状态模式在其他实际需求中的应用

![JAVAWEB_DESIGN122.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN122.png?raw=true)

![JAVAWEB_DESIGN123.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN123.png?raw=true)

![JAVAWEB_DESIGN124.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN124.png?raw=true)

实际代码

状态层：

```java
/**
 * 状态接口
 * 这些方法都是状态图中存在的那些“直线” 即可能操作
 *
 */
public interface State {

	/**
     * 电审
     */
    void checkEvent(Context context);

    /**
     * 电审失败
     */
    void checkFailEvent(Context context);

    /**
     * 定价发布
     */
    void makePriceEvent(Context context);

    /**
     * 接单
     */
    void acceptOrderEvent(Context context);

    /**
     * 无人接单失效
     */
    void notPeopleAcceptEvent(Context context);

    /**
     * 付款
     */
    void payOrderEvent(Context context);

    /**
     * 接单有人支付失效
     */
    void orderFailureEvent(Context context);

    /**
     * 反馈
     */
    void feedBackEvent(Context context);

    String getCurrentState();
}


/**
 * 状态枚举类
 *状态图中的方块，实际可能的操作
 *
 */
public enum StateEnum {
	 //订单生成
    GENERATE(1, "GENERATE"),

    //已审核
    REVIEWED(2, "REVIEWED"),

    //已发布
    PUBLISHED(3, "PUBLISHED"),

    //待付款
    NOT_PAY(4, "NOT_PAY"),

    //已付款
    PAID(5, "PAID"),

    //已完结
    FEED_BACKED(6, "FEED_BACKED");

    private int key;
    private String value;

    StateEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }
    public int getKey() {return key;}
    public String getValue() {return value;}
}


public abstract class AbstractState implements State {

	protected static final RuntimeException EXCEPTION = new RuntimeException("操作流程不允许");

	//抽象类，默认实现了 State 接口的所有方法
	//该类的所有方法，其子类(具体的状态类)，可以有选择的进行重写
	
    @Override
    public void checkEvent(Context context) {
        throw EXCEPTION;
    }

    @Override
    public void checkFailEvent(Context context) {
        throw EXCEPTION;
    }

    @Override
    public void makePriceEvent(Context context) {
        throw EXCEPTION;
    }

    @Override
    public void acceptOrderEvent(Context context) {
        throw EXCEPTION;
    }

    @Override
    public void notPeopleAcceptEvent(Context context) {
        throw EXCEPTION;
    }

    @Override
    public void payOrderEvent(Context context) {
        throw EXCEPTION;
    }

    @Override
    public void orderFailureEvent(Context context) {
        throw EXCEPTION;
    }

    @Override
    public void feedBackEvent(Context context) {
        throw EXCEPTION;
    }
}


//各种具体状态类
class FeedBackState extends AbstractState {

	@Override
	public String getCurrentState() {
		return StateEnum.FEED_BACKED.getValue();
	}
}

class GenerateState extends AbstractState {

	@Override
	public void checkEvent(Context context) {
		context.setState(new ReviewState());
	}

	@Override
	public void checkFailEvent(Context context) {
		context.setState(new FeedBackState());
	}

	@Override
	public String getCurrentState() {
		return StateEnum.GENERATE.getValue();
	}
}

class NotPayState extends AbstractState {

	@Override
	public void payOrderEvent(Context context) {
		context.setState(new PaidState());
	}

	@Override
	public void feedBackEvent(Context context) {
		context.setState(new FeedBackState());
	}

	@Override
	public String getCurrentState() {
		return StateEnum.NOT_PAY.getValue();
	}
}

class PaidState extends AbstractState {

	@Override
	public void feedBackEvent(Context context) {
		context.setState(new FeedBackState());
	}

	@Override
	public String getCurrentState() {
		return StateEnum.PAID.getValue();
	}
}

class PublishState extends AbstractState {

	@Override
	public void acceptOrderEvent(Context context) {
		//把当前状态设置为  NotPayState。。。
		//至于应该变成哪个状态，有流程图来决定
		context.setState(new NotPayState());
	}

	@Override
	public void notPeopleAcceptEvent(Context context) {
		context.setState(new FeedBackState());
	}

	@Override
	public String getCurrentState() {
		return StateEnum.PUBLISHED.getValue();
	}
}

class ReviewState extends AbstractState {

	@Override
	public void makePriceEvent(Context context) {
		context.setState(new PublishState());
	}

	@Override
	public String getCurrentState() {
		return StateEnum.REVIEWED.getValue();
	}

}
```

应用层：

```java
//环境上下文
public class Context extends AbstractState{
	//当前的状态 state, 根据我们的业务流程处理，不停的变化
	private State state;

    @Override
    public void checkEvent(Context context) {
        state.checkEvent(this);
        getCurrentState();
    }

    @Override
    public void checkFailEvent(Context context) {
        state.checkFailEvent(this);
        getCurrentState();
    }

    @Override
    public void makePriceEvent(Context context) {
        state.makePriceEvent(this);
        getCurrentState();
    }

    @Override
    public void acceptOrderEvent(Context context) {
        state.acceptOrderEvent(this);
        getCurrentState();
    }

    @Override
    public void notPeopleAcceptEvent(Context context) {
        state.notPeopleAcceptEvent(this);
        getCurrentState();
    }

    @Override
    public void payOrderEvent(Context context) {
        state.payOrderEvent(this);
        getCurrentState();
    }

    @Override
    public void orderFailureEvent(Context context) {
        state.orderFailureEvent(this);
        getCurrentState();
    }

    @Override
    public void feedBackEvent(Context context) {
        state.feedBackEvent(this);
        getCurrentState();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String getCurrentState() {
        System.out.println("当前状态 : " + state.getCurrentState());
        return state.getCurrentState();
    }
}

客户端
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建context 对象
		Context context = new Context();
        //将当前状态设置为 PublishState
		context.setState(new PublishState());
        System.out.println(context.getCurrentState());
        
//        //publish --> not pay
        context.acceptOrderEvent(context);
//        //not pay --> paid
        context.payOrderEvent(context);
//        // 失败, 检测失败时，会抛出异常
//        try {
//        	context.checkFailEvent(context);
//        	System.out.println("流程正常..");
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.getMessage());
//		}
        
	}
```

##### 状态模式的总结

```
1) 代码有很强的可读性，他处理的是一个流程图的问题。状态模式将每个状态的行为封装到对应的一个类中。 
2) 方便维护。将容易产生问题的 if-else 语句删除了，如果把每个状态的行为都放到一个类中，每次调用方法时都
要判断当前是什么状态，不但会产出很多 if-else 语句，而且容易出错
3) 符合“开闭原则”。容易增删状态
4) 会产生很多类。每个状态都要一个对应的类，当状态过多时会产生很多类，加大维护难度
5) 应用场景：当一个事件或者对象有很多种状态，状态之间会相互转换，对不同的状态要求有不同的行为的时候，
可以考虑使用状态模式
```



#### 策略模式

需求：鸭子问题

![JAVAWEB_DESIGN125.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN125.png?raw=true)

使用传统的继承方式实现不同鸭子的管理问题

```java
public abstract class Duck {
	public Duck() {
	}

	public abstract void display();//显示鸭子信息
	public void quack() {
		System.out.println("鸭子嘎嘎叫~~");
	}
	
	public void swim() {
		System.out.println("鸭子会游泳~~");
	}
	
	public void fly() {
		System.out.println("鸭子会飞翔~~~");
	}	
}

public class PekingDuck extends Duck {
	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println("~~北京鸭~~~");
	}
	
	//因为北京鸭不能飞翔，因此需要重写fly
	@Override
	public void fly() {
		// TODO Auto-generated method stub
		System.out.println("北京鸭不能飞翔");
	}
}

```

##### 基本介绍和原理分析

```
策略模式是将策略和实际问题分装起来。定义算法族（策略组），分别封装起来，让他们之间可以互相替换，此模式让算法的变化独立于使用算法的客户______他的核心是将解决问题的算法封装起来。 让他和问题之间进行解耦

这算法体现了几个设计原则，第一、把变化的代码从不变的代码中分离出来；第二、针对接口编程而不是具体类（定义了策略接口）；第三、多用组合/聚合，少用继承（客户通过组合方式使用策略）。
```

![JAVAWEB_DESIGN126.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN126.png?raw=true)

##### 策略模式改进

```
思路分析(类图)
策略模式：分别封装行为接口，实现算法族，超类里放行为接口对象，在子类里具体设定行为对象。
原则就是：分离变化部分，封装接口，基于接口编程各种功能。此模式让行为的变化独立于算法的使用者
```

![JAVAWEB_DESIGN127.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN127.png?raw=true)

代码实现

抽象策略层

```java
public interface FlyBehavior {
	void fly(); // 子类具体实现
}

public class BadFlyBehavior implements FlyBehavior {

	@Override
	public void fly() {
		// TODO Auto-generated method stub
		System.out.println(" 飞翔技术一般 ");
	}

}
public class GoodFlyBehavior implements FlyBehavior {

	@Override
	public void fly() {
		// TODO Auto-generated method stub
		System.out.println(" 飞翔技术高超 ~~~");
	}

}
public class NoFlyBehavior implements FlyBehavior{

	@Override
	public void fly() {
		// TODO Auto-generated method stub
		System.out.println(" 不会飞翔  ");
	}

}

//========================================================================================
public interface QuackBehavior {
	void quack();//子类实现
}

```

使用者层

```java
public abstract class Duck {
	//属性, 策略接口
	FlyBehavior flyBehavior;
	//其它属性<->策略接口
	QuackBehavior quackBehavior;
	
	public Duck() {
	
	}
    
    public void setFlyBehavior(FlyBehavior flyBehavior) {
		this.flyBehavior = flyBehavior;
	}
	
	public void setQuackBehavior(QuackBehavior quackBehavior) {
		this.quackBehavior = quackBehavior;
	}
    
    public void fly() {
		//改进
		if(flyBehavior != null) {
			flyBehavior.fly();
		}
	}
    
	public abstract void display();//显示鸭子信息
	public void quack() {
		System.out.println("鸭子嘎嘎叫~~");
	}
	public void swim() {
		System.out.println("鸭子会游泳~~");
	}
	
}

public class PekingDuck extends Duck {
	//假如北京鸭可以飞翔，但是飞翔技术一般
	public PekingDuck() {
		// TODO Auto-generated constructor stub
		flyBehavior = new BadFlyBehavior();
		
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println("~~北京鸭~~~");
	}
}

public class WildDuck extends Duck {

	
	//构造器，传入FlyBehavor 的对象
	public  WildDuck() {
		// TODO Auto-generated constructor stub
		flyBehavior = new GoodFlyBehavior();
	}
	
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(" 这是野鸭 ");
	}

}

```

客户端

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		WildDuck wildDuck = new WildDuck();
		wildDuck.fly();//
		
		ToyDuck toyDuck = new ToyDuck();
		toyDuck.fly();
		
		PekingDuck pekingDuck = new PekingDuck();
		pekingDuck.fly();
		
		//动态改变某个对象的行为, 北京鸭 不能飞
		pekingDuck.setFlyBehavior(new NoFlyBehavior());
		System.out.println("北京鸭的实际飞翔能力");
		pekingDuck.fly();
	}
```

分析： 策略模式将我们原来的继承行为转换为聚合。

##### 策略模式在 JDK-Arrays 应用的源码分析

JDK 的 Arrays 的 Comparator 就使用了策略模式

![JAVAWEB_DESIGN128.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN128.png?raw=true)

##### 策略模式总结

```
1) 策略模式的关键是：分析项目中变化部分与不变部分
2) 策略模式的核心思想是：多用组合/聚合 少用继承；用行为类组合，而不是行为的继承。更有弹性
3) 体现了“对修改关闭，对扩展开放”原则，客户端增加行为不用修改原有代码，只要添加一种策略（或者行为）
即可，避免了使用多重转移语句（if..else if..else）
4) 提供了可以替换继承关系的办法： 策略模式将算法封装在独立的 Strategy 类中使得你可以独立于其 Context 改
变它，使它易于切换、易于理解、易于扩展
5) 需要注意的是：每添加一个策略就要增加一个类，当策略过多是会导致类数目庞大
```



#### 职责链模式(责任链模式)

##### 需求——采购审批

![JAVAWEB_DESIGN129.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN129.png?raw=true)

传统的解决方式

![JAVAWEB_DESIGN130.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN130.png?raw=true)

```
1) 传统方式是：接收到一个采购请求后，根据采购金额来调用对应的 Approver (审批人)完成审批。
2) 传统方式的问题分析 : 客户端这里会使用到 分支判断(比如 switch) 来对不同的采购请求处理， 这样就存在如下问题 (1) 如果各个级别的人员审批金额发生变化，在客户端的也需要变化 (2) 客户端必须明确的知道 有多少个审批级别和访问
3) 这样 对一个采购请求进行处理 和 Approver (审批人) 就存在强耦合关系，不利于代码的扩展和维护
```



##### 基本介绍和原理分析

```
基本介绍
1) 职责链模式（Chain of Responsibility Pattern）, 又叫 责任链模式，为请求创建了一个《接收者对象的链》。这种模式对请求的发送者和接收者进行解耦。
2) 职责链模式通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。
3) 这种类型的设计模式属于行为型模式
```

![JAVAWEB_DESIGN131.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN131.png?raw=true)

##### 使用职责链模式来解决采购审批需求

![JAVAWEB_DESIGN132.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVAWEB_DESIGN132.png?raw=true)

代码实现

请求

```java
//请求类
public class PurchaseRequest {
	private int type = 0; //请求类型
	private float price = 0.0f; //请求金额
	private int id = 0;
	//构造器
	public PurchaseRequest(int type, float price, int id) {
		this.type = type;
		this.price = price;
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public float getPrice() {
		return price;
	}
	public int getId() {
		return id;
	}	
}
```

抽象的责任接口

```java
public abstract class Approver {
	Approver approver;  //下一个处理者
	String name; // 名字	
	public Approver(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	//下一个处理者
	public void setApprover(Approver approver) {
		this.approver = approver;
	}
	
	//处理审批请求的方法，得到一个请求, 处理是子类完成，因此该方法做成抽象
	public abstract void processRequest(PurchaseRequest purchaseRequest);
	
}

```

实际的请求处理者

```java
public class DepartmentApprover extends Approver {
	public DepartmentApprover(String name) {
		// TODO Auto-generated constructor stub
		super(name);
	}
	
	@Override
	public void processRequest(PurchaseRequest purchaseRequest) {
		// TODO Auto-generated method stub
		if(purchaseRequest.getPrice() <= 5000) {
			System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理");
		}else {
			approver.processRequest(purchaseRequest);
		}
	}

}


public class CollegeApprover extends Approver {
	public CollegeApprover(String name) {
		// TODO Auto-generated constructor stub
		super(name);
	}
	
	@Override
	public void processRequest(PurchaseRequest purchaseRequest) {
		// TODO Auto-generated method stub
		if(purchaseRequest.getPrice() < 5000 && purchaseRequest.getPrice() <= 10000) {
			System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理");
		}else {
			approver.processRequest(purchaseRequest);
		}
	}
}

public class ViceSchoolMasterApprover extends Approver {
	public ViceSchoolMasterApprover(String name) {
		// TODO Auto-generated constructor stub
		super(name);
	}
	
	@Override
	public void processRequest(PurchaseRequest purchaseRequest) {
		// TODO Auto-generated method stub
		if(purchaseRequest.getPrice() < 10000 && purchaseRequest.getPrice() <= 30000) {
			System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理");
		}else {
			approver.processRequest(purchaseRequest);
		}
	}
}

public class SchoolMasterApprover extends Approver {
	public SchoolMasterApprover(String name) {
		// TODO Auto-generated constructor stub
		super(name);
	}
	
	@Override
	public void processRequest(PurchaseRequest purchaseRequest) {
		// TODO Auto-generated method stub
		if(purchaseRequest.getPrice() > 30000) {
			System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理");
		}else {
			approver.processRequest(purchaseRequest);
		}
	}
}

```

客户端

```java
public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建一个请求
		PurchaseRequest purchaseRequest = new PurchaseRequest(1, 31000, 1);
		
		//创建相关的审批人
		DepartmentApprover departmentApprover = new DepartmentApprover("张主任");
		CollegeApprover collegeApprover = new CollegeApprover("李院长");
	ViceSchoolMasterApprover viceSchoolMasterApprover = new ViceSchoolMasterApprover("王副校");
		SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("佟校长");
	
		//需要将各个审批级别的下一个设置好 (处理人构成环形: )
		departmentApprover.setApprover(collegeApprover);
		collegeApprover.setApprover(viceSchoolMasterApprover);
		viceSchoolMasterApprover.setApprover(schoolMasterApprover);
		schoolMasterApprover.setApprover(departmentApprover);
		
		departmentApprover.processRequest(purchaseRequest);
		viceSchoolMasterApprover.processRequest(purchaseRequest);
	}

```

##### 职责链模式在 SpringMVC 框架应用的源码分析

SpringMVC-HandlerExecutionChain 类就使用到职责链模式

```
springmvc 请求的流程图中，执行了 拦截器相关方法 interceptor.preHandler 等等在处理 SpringMvc 请求时，使用到职责链模式还使用到适配器模式

HandlerExecutionChain 主要负责的是请求拦截器的执行和请求处理,但是他本身不处理请求，只是将请求分配给链上注册处理器执行，这是职责链实现方式,减少职责链本身与处理逻辑之间的耦合,规范了处理流程HandlerExecutionChain 维护了 HandlerInterceptor 的集合， 可以向其中注册相应的拦截器.
```

##### 总结

```
1) 将请求和处理分开，实现解耦，提高系统的灵活性
2) 简化了对象，使对象不需要知道链的结构
3) 性能会受到影响，特别是在链比较长的时候，因此需控制链中最大节点数量，一般通过在 Handler 中设置一个最大节点数量，在 setNext()方法中判断是否已经超过阀值，超过则不允许该链建立，避免出现超长链无意识地破坏系统性能
4) 调试不方便。采用了类似递归的方式，调试时逻辑可能比较复杂
5) 最佳应用场景：有多个对象可以处理同一个请求时，比如：多级请求、请假/加薪等审批流程、Java Web 中 Tomcat
对 Encoding 的处理、拦截器
```

