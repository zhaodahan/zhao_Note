#                                                         java 基础回顾与提高篇 

# 三大特性



## 三大特性之—封装



封装： 顾名思义就是包装， 将一些不希望暴露的**信息影藏** ,隐藏实现细节 。 只提供我们想暴露的.  

将数据与对数据的操作封装成一个不可分割的整体。

数据被保护在抽象数据类型的内，尽可能地隐藏内部的细节，只保留一些对外接口使之与外部发生联系。其他对象只能通过包裹在数据外面的已经授权的操作来与这个封装的对象进行交流和交互。也就是说用户是无需知道对象内部的细节（当然也无从知道），但可以通过该对象对外的提供的接口来访问该对象。

如我们常写的实体类

```java
public class AuthorityUser extends IdEntity{

	private static final long serialVersionUID = 5066019692766416652L;

	private String userId;
	
	private String phone;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}	
}
```

将属性私有， 用`private`修饰 ， 对这些属性的操作提供一个`public`方法。外部对象如果想操作属性，只能通过提供的`public`方法。如果某些属性不想被外面的用户知道 就可以不提供访问这个属性的方法。

对于封装而言，一个对象它所封装的是自己的属性和方法，所以它是不需要依赖其他对象就可以完成自己的操作。

从**程序的角度来分析封装带来的好处**：

如果不使用封装，那么该对象就没有`setter()和getter()`

`Husband`类：

```java
public class Husband {
    public String name ;
    public int age ;
    public Wife wife;
}
```

使用它：

```java
Husband husband = new Husband();
husband.age = 30;
husband.name = "张三";
```

但是那天如果我们需要修改`Husband`，例如将age修改为String类型的呢？你只有一处使用了这个类还好，如果你有几十个甚至上百个这样地方，你是不是要改到崩溃。如果使用了封装，对于调用我们完全可以不需要做任何修改，只需要稍微改变下Husband类的setAge()方法即可。

```java
public class Husband {
    /*
     * 对属性的封装
     * 一个人的姓名、性别、年龄、妻子都是这个人的私有属性
     */
    private String name ;
    private String age ;    /* 改成 String类型的*/
    private Wife wife;

    public String getAge() {
        return age;
    }
    
    public void setAge(int age) {
        //转换即可
        this.age = String.valueOf(age);
    }
    /** 省略其他属性的setter、getter **/

}
```

其他的地方依然那样引用`husband.setAge(22)`保持不变。

从这里我们可以看到的就是： **封装确实可以使我们容易地修改类的内部实现，而无需修改使用了该类的客户代码**。

封装之后还可以对成员变量做更精确的控制：

```java
public class Husband {
    private String name ;
    private String sex ;
    private int age ;    /* 改成 String类型的*/
    private Wife wife;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age > 120){
            System.out.println("ERROR：error age input....");    //提示錯誤信息
        }else{
            this.age = age;
        }

    }
    
    public String getSexName() {
        if("0".equals(sex)){
            sexName = "女";
        }
        else if("1".equals(sex)){
            sexName = "男";
        }
        else{
            sexName = "人妖???";
        }
        return sexName;
    }

    /** 省略其他属性的setter、getter **/

}
```

对属性的`getter， setter`  通过使用封装我们也能够对对象的出口做出很好的控制 。



## 三大特性之继承

继承是代码复用的一种方式。

继承是基于对相似事务的一个顶层抽象。 如 Husband 和 wife 他们的顶层抽象就是他们共有特征，就是他们都是人， 都有 `age name ,sex` 等属性  。他们都拥有人的属性和行为，同时也是从人那里继承来的这些属性和行为的。

继承的概念了，继承是使用已存在的类的定义作为基础建立新类，新类的定义可以增加新的数据或新的功能，也可以用父类的功能，但不能选择性地继承父类(必须全部继承)。通过使用继承我们能够非常方便地复用以前的代码，能够大大的提高开发的效率。

![java_extend.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_extend.png?raw=true)

继承所描述的是“is-a”的关系，如果有两个对象A和B，若可以描述为“A是B”，则可以表示A继承B，其中B是被继承者称之为父类或者超类，A是继承者称之为子类或者派生类。

实际上继承者是被继承者的特殊化 A 是特殊的B，它除了拥有被继承者的特性外，还拥有自己独有得特性。

在继承关系中，继承者完全可以替换被继承者，反之则不可以，例如我们可以说猫是动物，但不能说动物是猫就是这个道理，其实对于这个我们将其称之为“向上转型”

继承定义了类如何相互关联，共享特性。对于若干个相同或者相识的类，我们可以抽象出他们共有的行为或者属相并将其定义成一个父类或者超类，然后用这些类继承该父类，他们不仅可以拥有父类的属性、方法还可以定义自己独有的属性或者方法。

同时在使用继承时需要记住三句话：

- 1、子类拥有父类非private的属性和方法。 
- 2、子类可以拥有自己属性和方法，即子类可以对父类进行扩展。
- 3、子类可以用自己的方式实现父类的方法。

子类只能继承父类的非私有属性和方法

```java
/**
 * 定义父类
 * @author CBS
 */
public class Father {
    private String name;//私有属性
    private char sex;
    
    public void tell(){
        System.out.println(name+sex);
    }
    
    private void speak(){
        System.out.println(name+"is speaking!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

}

/**
 * 定义子类，子类中什么都不写。
 * @author CBS
 */
public class Child extends Father {

}


/**
 * 定义测试类
 * @author CBS
 */
public class Test {

    public static void main(String[] args) {
        
        Child c=new Child();
        c.tell();//tell方法是可以用的
        //c.speak();//报错，父类的私有方法不可见
    } 
//Java官方文档的解释：子类不能继承父类的私有属性，但是如果子类中公有的方法影响到了父类私有属性，那么私有属性是能够被子类使用的。就生活中的例子来说官方文档的解释也很契合, 我们不可能完全继承父母的一切（如性格等），但是父母的一些无法继承的东西却仍会深刻的影响着我们。
    
}

//Java中修饰符的作用是封装类中的属性和方法使其对外不可见，官方文档的解释很好的兼顾了Java的继承和封装。
```

继承是为了更好的提高代码的重用性，封装则保护了代码的不可见部分。

### 构造器

从上面可知， 子类不能继承父类的私有属性。 除此之外还有一样，子类也无法继承，就是构造器。对于构造器而言，它只能够被调用，而不能被继承。 调用父类的构造方法我们使用super()即可。

```java
public class Person {
    protected String name;
    protected int age;
    protected String sex;

    Person(){
        System.out.println("Person Constrctor...");
    }
}

public class Husband extends Person{
    private Wife wife;

    Husband(){
        // super（）这里实际上是隐式的调用了的
        System.out.println("Husband Constructor...");
    }

    public static void main(String[] args) {
        Husband husband  = new Husband();
    }
}

Output:
Person Constrctor...
Husband Constructor...
```

通过这个示例可以看出，构建过程是从父类“向外”扩散的，也就是从父类开始向子类一级一级地完成构建 (就是我们**在构建子类对象的时候会先初始化父类**)。而我们并`没有显示`的引用父类的构造器(实则是隐式的调用了父类的默认构造方法)，这就是java的聪明之处：编译器会默认给子类调用父类的构造器。但是，**这个默认调用父类的构造器是有前提的：父类有默认构造器。如果父类没有默认构造器，我们就要必须显示的使用super()来调用父类构造器**，否则编译器会报错：无法找到符合父类形式的构造器。 

`Implicit super constructor Creator() is undefined. Must explicitly invoke another constructor`

```java
public class Person {
    protected String name;
    protected int age;
    protected String sex;

    Person(String name){
        System.out.println("Person Constrctor-----" + name);
    }
}

public class Husband extends Person{
    private Wife wife;

    Husband(){
        super("chenssy");
        System.out.println("Husband Constructor...");
    }

    public static void main(String[] args) {
        Husband husband  = new Husband();
    }
}

Output:
Person Constrctor-----chenssy
Husband Constructor...
```

所以综上所述：对于继承而已，子类会默认调用父类的构造器，但是如果没有默认的父类构造器，子类必须要显示的指定父类的构造器，而且必须是在子类构造器中做的第一件事(第一行代码)。



### protected关键字

private访问修饰符，对于封装而言，是最好的选择，但这个只是基于理想的世界，有时候我们需要这样的需求：我们需要将某些事物尽可能地对这个世界隐藏，但是仍然**允许子类的成员来访问它们**。这个时候就需要使用到protected。

对于protected而言，它指明就类用户而言，他是private，但是对于任何继承与此类的子类而言或者其他任何位于同一个包的类而言，他却是可以访问的。

```java
public class Person {
    private String name;
    private int age;
    private String sex;

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "this name is " + name;
    }

    /** 省略其他setter、getter方法 **/
}

public class Husband extends Person{
    private Wife wife;

    public  String toString(){
        setName("chenssy");    //调用父类的setName();
        return  super.toString();    //调用父类的toString()方法
    }

    public static void main(String[] args) {
        Husband husband = new Husband();

        System.out.println(husband.toString());
    }
}

Output：
this name is chenssy
```

从上面示例可以看书子类Husband可以明显地调用父类Person的setName()。

### 向上转型

继承是is-a的相互关系，猫继承与动物，所以我们可以说猫是动物，或者说猫是动物的一种。这样将猫看做动物就是向上转型。

```java
public class Person {
    public void display(){
        System.out.println("Play Person...");
    }
    static void display(Person person){
        person.display();
    }
}

public class Husband extends Person{
    public static void main(String[] args) {
        Husband husband = new Husband();
        Person.display(husband);      //向上转型
    }
}
```

在这我们通过`Person.display(husband)`这句话可以看出husband是person类型。

将子类转换成父类，在继承关系上面是向上移动的，所以一般称之为向上转型。由于向上转型是从一个叫专用类型向较通用类型转换，所以它总是安全的，唯一发生变化的可能就是属性和方法的丢失。

### 谨慎继承

继承存在如下缺陷：

- 1、父类变，子类就必须变。
- 2、继承破坏了封装，对于父类而言，它的实现细节对与子类来说都是透明的。
- 3、继承是一种强耦合关系。

使用继承的时候，我们需要确信使用继承确实是有效可行的办法。那么到底要不要使用继承呢？问一问自己是否需要从子类向父类进行向上转型。如果必须向上转型，则继承是必要的，但是如果不需要，则应当好好考虑自己是否需要继承。



## 三大特性之多态

多态是基于继承的，没有继承就不存在多态。

多态就是指程序中定义的**引用变量所指向的具体类型** 和 **通过该引用变量发出的方法调用** 在编程时并不确定，而是在程序运行期间才确定，即一个引用变量倒底会指向哪个类的实例对象，该引用变量发出的方法调用到底是哪个类中实现的方法，必须在由程序运行期间才能决定。

因为在程序运行时才确定具体的类，这样不用修改源程序代码，就可以让引用变量绑定到各种不同的类实现上，从而导致该引用调用的具体方法随之改变，即不修改程序代码就可以改变程序运行时所绑定的具体代码，让程序可以选择多个运行状态，这就是多态性。

比如你是一个酒神，对酒情有独钟。某日回家发现桌上有几个杯子里面都装了白酒，从外面看我们是不可能知道这是些什么酒，只有喝了之后才能够猜出来是何种酒。你一喝，这是剑南春、再喝这是五粮液、再喝这是酒鬼酒….在这里我们可以描述成如下：

```
酒 a = 剑南春
酒 b = 五粮液
酒 c = 酒鬼酒
…
```

这里所表现的的就是多态。剑南春、五粮液、酒鬼酒都是酒的子类，我们只是通过酒这一个父类就能够引用不同的子类，这就是多态——我们只有在运行的时候才会知道引用变量所指向的具体实例对象。 

要理解多态我们就必须要明白什么是“向上转型”。在上面的喝酒例子中，酒（Win）是父类，剑南春（JNC）、五粮液（WLY）、酒鬼酒（JGJ）是子类。我们定义如下代码：

```java
JNC a = new  JNC();
```

对于这个代码我们非常容易理解无非就是实例化了一个剑南春的对象嘛！但是这样呢？

```java
Wine a = new JNC();
```

在这里我们这样理解，这里定义了一个Wine 类型的a，它指向JNC对象实例。由于JNC是继承与Wine，所以JNC可以自动向上转型为Wine，所以a是可以指向JNC实例对象的。这样做存在一个非常大的好处，在继承中我们知道子类是父类的扩展，它可以提供比父类更加强大的功能，如果我们定义了一个指向子类的父类引用类型，那么它除了能够引用父类的共性外，还可以使用子类强大的功能。

但是向上转型存在一些缺憾，那就是它必定会导致一些方法和属性的丢失，而导致我们不能够获取它们。所以父类类型的引用可以调用父类中定义的所有属性和方法，对于只存在与子类中的方法和属性它就望尘莫及了.

```java
public class Wine {
    public void fun1(){
        System.out.println("Wine 的Fun.....");
        fun2();
    }

    public void fun2(){
        System.out.println("Wine 的Fun2...");
    }
}

public class JNC extends Wine{
    /**
     * @desc 子类重载父类方法
     *        父类中不存在该方法，向上转型后，父类是不能引用该方法的
     * @param a
     * @return void
     */
    public void fun1(String a){
        System.out.println("JNC 的 Fun1...");
        fun2();
    }

    /**
     * 子类重写父类方法
     * 指向子类的父类引用调用fun2时，必定是调用该方法
     */
    public void fun2(){
        System.out.println("JNC 的Fun2...");
    }
}

public class Test {
    public static void main(String[] args) {
        Wine a = new JNC();
        a.fun1();
    }
}
-------------------------------------------------
Output:
Wine 的Fun.....
JNC 的Fun2...
//从结果上来看：a.fun1()首先是运行父类Wine中的fun1().然后再运行子类JNC中的fun2()。
    分析：在这个程序中子类JNC重载了父类Wine的方法fun1()，重写fun2()，而且重载后的fun1(String a)与 fun1()不是同一个方法，由于父类中没有该方法，向上转型后会丢失该方法，所以执行JNC的Wine类型引用是不能引用fun1(String a)方法。而子类JNC重写了fun2() ，那么指向JNC的Wine引用会调用JNC中fun2()方法。
```

指向子类的父类引用由于向上转型了，因为引用是父类的引用，调用只能调用父类有的方法，对于子类中存在而父类中不存在的方法，父类引用是不知道的，所以无法调用。但是因为实际的对象是子类实例，所以实际运行的是子类的实现。



### 多态的实现

继承是多态的基础，为多态的实现做了准备。

子类Child继承父类Father，一个指向子类的父类引用即可以处理父类Father对象，也可以处理子类Child对象，当相同的消息发送给子类或者父类对象时，该对象就会根据自己所属的引用而执行不同的行为，这就是多态。即多态性就是相同的消息使得不同的类做出不同的响应。

Java实现多态有三个必要条件：继承、重写、向上转型。

- 继承：在多态中必须存在有继承关系的子类和父类。
- 重写：子类对父类中某些方法进行重新定义，在调用这些方法时就会调用子类的方法。
- 向上转型：在多态中需要将子类的引用赋给父类对象，只有这样该引用才能够具备技能调用父类的方法和子类的方法。

只有满足了上述三个条件，我们才能够在同一个继承结构中使用统一的逻辑实现代码处理不同的对象，从而达到执行不同的行为。

**实现形式**

Java中有两种形式可以实现多态： 继承和接口。

**基于继承实现的多态**

基于继承的实现机制主要表现在父类和继承该父类的一个或多个子类对某些方法的重写，多个子类对同一方法的重写可以表现出不同的行为。

```java
public class Wine {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wine(){
    }

    public String drink(){
        return "喝的是 " + getName();
    }

    /**
     * 重写toString()
     */
    public String toString(){
        return null;
    }
}

public class JNC extends Wine{
    public JNC(){
        setName("JNC");
    }

    /**
     * 重写父类方法，实现多态
     */
    public String drink(){
        return "喝的是 " + getName();
    }

    /**
     * 重写toString()
     */
    public String toString(){
        return "Wine : " + getName();
    }
}

public class JGJ extends Wine{
    public JGJ(){
        setName("JGJ");
    }

    /**
     * 重写父类方法，实现多态
     */
    public String drink(){
        return "喝的是 " + getName();
    }

    /**
     * 重写toString()
     */
    public String toString(){
        return "Wine : " + getName();
    }
}

public class Test {
    public static void main(String[] args) {
        //定义父类数组
        Wine[] wines = new Wine[2];
        //定义两个子类
        JNC jnc = new JNC();
        JGJ jgj = new JGJ();

        //父类引用子类对象
        wines[0] = jnc;
        wines[1] = jgj;

        for(int i = 0 ; i < 2 ; i++){
            System.out.println(wines[i].toString() + "--" + wines[i].drink());
        }
        System.out.println("-------------------------------");

    }
}
OUTPUT:
Wine : JNC--喝的是 JNC
Wine : JGJ--喝的是 JGJ
-------------------------------
在上面的代码中JNC、JGJ继承Wine，并且重写了drink()、toString()方法，程序运行结果是调用子类中方法，输出JNC、JGJ的名称，这就是多态的表现。不同的对象可以执行相同的行为，但是他们都需要通过自己的实现方式来执行，这就要得益于向上转型了。
```

我们都知道所有的类都继承自超类Object，toString()方法也是Object中方法，当我们这样写时：

```java
Object o = new JGJ();
System.out.println(o.toString());

输出的结果是Wine : JGJ。
```

所以基于继承实现的多态可以总结如下：对于引用子类的父类类型，在处理该引用时，它适用于继承该父类的所有子类，子类对象的不同，对方法的实现也就不同，执行相同动作产生的行为也就不同。

如果父类是抽象类，那么子类必须要实现父类中所有的抽象方法，这样该父类所有的子类一定存在统一的对外接口，但其内部的具体实现可以各异。这样我们就可以使用顶层类提供的统一接口来处理该层次的方法。

**基于接口实现的多态**

继承是通过重写父类的同一方法的几个不同子类来体现的，那么就可以是通过实现接口并覆盖接口中同一方法的几不同的类体现的。

在接口的多态中，指向接口的引用必须是指定这实现了该接口的一个类的实例程序，在运行时，根据对象引用的实际类型来执行对应的方法。

继承都是单继承，只能为一组相关的类提供一致的服务接口。但是接口可以是多继承多实现，它能够利用一组相关或者不相关的接口进行组合与扩充，能够对外提供一致的服务接口。所以它相对于继承来说有更好的灵活性。


