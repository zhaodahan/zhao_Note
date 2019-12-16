#                                      深入理解java虚拟机

# 类加载机制

类是在运行期间动态加载的。

## 类的生命周期



![Java_jvm_lifecycle.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_jvm_lifecycle.png?raw=true)

包括以下 7 个阶段：

- **加载（Loading）**
- **验证（Verification）**
- **准备（Preparation）**
- **解析（Resolution）**
- **初始化（Initialization）**
- 使用（Using）
- 卸载（Unloading）

其中解析过程在某些情况下可以在初始 化阶段之后再开始，这是为了支持 Java 的动态绑定。

## 类加载器

顾名思义是用来加载类的。

### 什么是类加载器

负责读取 Java 字节代码，并转换成`java.lang.Class`类的一个实例；

### 类加载器与类的”相同“判断

类加载器除了用于加载类外，还可用于确定**类**在Java虚拟机中的唯一性。即便是同样的字节代码，被不同的类加载器加载之后所得到的类，也是不同的。

通俗一点来讲，要判断两个类是否“相同”，前提是这两个类必须被同一个类加载器加载，否则这个两个类不“相同”。
这里指的“相同”，包括类的Class对象的`equals()`方法、`isAssignableFrom()`方法、`isInstance()`方法、`instanceof`关键字等判断出来的结果。



###  类加载器种类

启动类加载器，`Bootstrap ClassLoader`，加载`JACA_HOME\lib`，或者被`-Xbootclasspath`参数限定的类(启动时加载)
扩展类加载器，`Extension ClassLoader`，加载`\lib\ext`，或者被`java.ext.dirs`系统变量指定的类
应用程序类加载器，`Application ClassLoader`，加载`ClassPath`中的类库
自定义类加载器，通过继承`ClassLoader`实现，一般是加载我们的自定义类



###  双亲委派模型

类加载器的java类如同其它的 Java 类一样，也是要由类加载器来加载的；除了启动类加载器，每个类都有其父类加载器（父子关系由组合（不是继承）来实现）；

所谓双亲委派是指每次收到类加载请求时，先将请求委派给父类加载器完成（所有加载请求最终会委派到顶层的Bootstrap ClassLoader加载器中），如果父类加载器无法完成这个加载（该加载器的搜索范围中没有找到对应的类），子类尝试自己加载。

![Java_jvm_classLoarder.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_jvm_classLoarder.png?raw=true)


**双亲委派好处**

- 避免同一个类被多次加载；
- 每个加载器只能加载自己范围内的类；

### 类加载过程

类加载分为三个步骤：**加载**，**连接**，**初始化**；

![Java_jvm_classLoarder2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_jvm_classLoarder2.png?raw=true)

#### 加载

根据一个类的全限定名(如`cn.edu.hdu.test.HelloWorld.class`)来读取此类的二进制字节流到JVM内部;

将字节流所代表的静态存储结构转换为方法区的运行时数据结构（hotspot选择将Class对象存储在方法区中，Java虚拟机规范并没有明确要求一定要存储在方法区或堆区中）

转换为一个与目标类型对应的`java.lang.Class`对象；

#### 连接

**验证**

验证阶段主要包括四个检验过程：文件格式验证、元数据验证、字节码验证和符号引用验证;

**准备**

为类中的所有静态变量分配内存空间，并为其设置一个初始值（由于还没有产生对象，实例变量将不再此操作范围内）；

**解析**

将常量池中所有的符号引用转为直接引用（得到类或者字段、方法在内存中的指针或者偏移量，以便直接调用该方法）。这个阶段可以在初始化之后再执行。

#### 初始化

在连接的准备阶段，类变量已赋过一次系统要求的初始值，而在初始化阶段，则是根据程序员自己写的逻辑去初始化类变量和其他资源，举例如下：

```java
    public static int value1  = 5;
    public static int value2  = 6;
    static{
        value2 = 66;
    }
//在准备阶段value1和value2都等于0；
//在初始化阶段value1和value2分别等于5和66；
```

- 所有类变量初始化语句和静态代码块都会在编译时被前端编译器放在收集器里头，存放到一个特殊的方法中，这个方法就是<clinit>方法，即类/接口初始化方法，该方法只能在类加载的过程中由JVM调用；
- 编译器收集的顺序是由语句在源文件中出现的顺序所决定的，静态语句块中只能访问到定义在静态语句块之前的变量；
- 如果超类还没有被初始化，那么优先对超类初始化，但在<clinit>方法内部不会显示调用超类的<clinit>方法，由JVM负责保证一个类的<clinit>方法执行之前，它的超类<clinit>方法已经被执行。
- JVM必须确保一个类在初始化的过程中，如果是多线程需要同时初始化它，仅仅只能允许其中一个线程对其执行初始化操作，其余线程必须等待，只有在活动线程执行完对类的初始化操作之后，才会通知正在等待的其他线程。(所以可以利用静态内部类实现线程安全的单例模式)
- 如果一个类没有声明任何的类变量，也没有静态代码块，那么可以没有类<clinit>方法；

**何时触发初始化**

1. **为一个类型创建一个新的对象实例时（比如new、反射、序列化）**
2. **调用一个类型的静态方法时（即在字节码中执行invokestatic指令）**
3. **调用一个类型或接口的静态字段，或者对这些静态字段执行赋值操作时（即在字节码中，执行getstatic或者putstatic指令），不过用final修饰的静态字段除外，它被初始化为一个编译时常量表达式**
4. **调用JavaAPI中的反射方法时（比如调用java.lang.Class中的方法，或者java.lang.reflect包中其他类的方法）**
5. **初始化一个类的派生类时（Java虚拟机规范明确要求初始化一个类时，它的超类必须提前完成初始化操作，接口例外）**
6. **JVM启动包含main方法的启动类时。**

 

##  自定义类加载器

 要创建用户自己的类加载器，只需要继承`java.lang.ClassLoader`类，然后覆盖它的findClass(String name)方法即可，即指明如何获取类的字节码流。

**如果要符合双亲委派规范，则重写findClass方法（用户自定义类加载逻辑）；要破坏的话，重写loadClass方法(双亲委派的具体逻辑实现)**。

例子：

```java
package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class TestClassLoad {
    @Override
    public String toString() {
        return "类加载成功。";
    }
}
public class PathClassLoader extends ClassLoader {
    private String classPath;

    public PathClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getData(String className) {
        String path = classPath + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0, num);
            }
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String args[]) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        ClassLoader pcl = new PathClassLoader("D:\\ProgramFiles\\eclipseNew\\workspace\\cp-lib\\bin");
        Class c = pcl.loadClass("classloader.TestClassLoad");//注意要包括包名
        System.out.println(c.newInstance());//打印类加载成功.
    }
}
```

 

 

## **JAVA热部署实现**

首先谈一下何为热部署（hotswap），热部署是在不重启 Java 虚拟机的前提下，能自动侦测到 class 文件的变化，更新运行时 class 的行为。Java 类是通过 Java 虚拟机加载的，某个类的 class 文件在被 classloader 加载后，会生成对应的 Class 对象，之后就可以创建该类的实例。默认的虚拟机行为只会在启动时加载类，如果后期有一个类需要更新的话，单纯替换编译的 class 文件，Java 虚拟机是不会更新正在运行的 class。如果要实现热部署，最根本的方式是修改虚拟机的源代码，改变 classloader 的加载行为，使虚拟机能监听 class 文件的更新，重新加载 class 文件，这样的行为破坏性很大，为后续的 JVM 升级埋下了一个大坑。

另一种友好的方法是创建自己的 classloader 来加载需要监听的 class，这样就能控制类加载的时机，从而实现热部署。 

 热部署步骤：

1、销毁自定义classloader(被该加载器加载的class也会自动卸载)；

2、更新class

3、使用新的ClassLoader去加载class 

JVM中的Class只有满足以下三个条件，才能被GC回收，也就是该Class被卸载（unload）：

   \- 该类所有的实例都已经被GC，也就是JVM中不存在该Class的任何实例。
   \- 加载该类的ClassLoader已经被GC。
   \- 该类的`java.lang.Class` 对象没有在任何地方被引用，如不能在任何地方通过反射访问该类的方法

 

延伸出来问题进行分析：JVM是怎么加载的

```java
public class SSClass
{
    static
    {
        System.out.println("SSClass");
    }
}   
public class SuperClass extends SSClass
{
    static
    {
        System.out.println("SuperClass init!");
    }
 
    public static int value = 123;
 
    public SuperClass()
    {
        System.out.println("init SuperClass");
    }
}
public class SubClass extends SuperClass
{
    static
    {
        System.out.println("SubClass init");
    }
 
    static int a;
 
    public SubClass()
    {
        System.out.println("init SubClass");
    }
}
public class NotInitialization
{
    public static void main(String[] args)
    {
        System.out.println(SubClass.value);
    }
}
```

运行结果：

```
SSClass
SuperClass init!
123
```

疑问：为什么没有输出SubClass init。解释一下：**对于静态字段，只有直接定义这个字段的类才会被初始化**，因此通过其子类来引用父类中定义的静态字段，只会触发父类的初始化而不会触发子类的初始化。

这就牵涉到了虚拟机类加载机制。

 

## 类加载过程

类从被加载到虚拟机内存中开始，到卸载出内存为止，它的整个生命周期包括：加载（Loading）、验证（Verification）、准备(Preparation)、解析(Resolution)、初始化(Initialization)、使用(Using)和卸载(Unloading)7个阶段。其中准备、验证、解析3个部分统称为连接（Linking）。如图所示。

![JVM 001](https://images.cnblogs.com/cnblogs_com/andy-zhou/806130/o_JVM001.png)

加载、验证、准备、初始化和卸载这5个阶段的顺序是确定的，类的加载过程必须按照这种顺序按部就班地开始，而解析阶段则不一定：它在某些情况下可以在初始化阶段之后再开始，这是为了支持Java语言的运行时绑定（也称为动态绑定或晚期绑定）。（以下陈述的内容都以HotSpot为基准）。

#### 加载

在加载阶段（可以参考`java.lang.ClassLoader`的loadClass()方法），虚拟机需要完成以下3件事情：

1. 通过一个类的全限定名来获取定义此类的二进制字节流（并没有指明要从一个Class文件中获取，可以从其他渠道，譬如：网络、动态生成、数据库等）；
2. 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构；
3. 在内存中生成一个代表这个类的`java.lang.Class`对象，作为方法区这个类的各种数据的访问入口；

加载阶段和连接阶段（Linking）的部分内容（如一部分字节码文件格式验证动作）是交叉进行的，加载阶段尚未完成，连接阶段可能已经开始，但这些夹在加载阶段之中进行的动作，仍然属于连接阶段的内容，这两个阶段的开始时间仍然保持着固定的先后顺序。

#### 验证

验证是连接阶段的第一步，这一阶段的目的是为了确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。
验证阶段大致会完成4个阶段的检验动作：

1. 文件格式验证：验证字节流是否符合Class文件格式的规范；例如：是否以0xCAFEBABE开头、主次版本号是否在当前虚拟机的处理范围之内、常量池中的常量是否有不被支持的类型。
2. 元数据验证：对字节码描述的信息进行语义分析（注意：对比javac编译阶段的语义分析），以保证其描述的信息符合Java语言规范的要求；例如：这个类是否有父类，除了java.lang.Object之外。
3. 字节码验证：通过数据流和控制流分析，确定程序语义是合法的、符合逻辑的。
4. 符号引用验证：确保解析动作能正确执行。

验证阶段是非常重要的，但不是必须的，它对程序运行期没有影响，如果所引用的类经过反复验证，那么可以考虑采用`-Xverifynone`参数来关闭大部分的类验证措施，以缩短虚拟机类加载的时间。

#### 准备

准备阶段是**正式为类变量分配内存并设置<u>类</u>变量初始值的阶段**，这些变量所使用的内存都将在**方法区**中进行分配。这时候进行内存分配的仅包括类变量（被static修饰的变量），而<u>不包括实例变量</u>，实例变量将会在对象实例化时随着对象一起分配在堆中。其次，这里所说的初始值“通常情况”下是数据类型的零值，假设一个类变量的定义为：

```
public static int value=123;
```

**那变量value在准备阶段过后的初始值为0而不是123**.因为这时候尚未开始执行任何java方法，而把value赋值为123的putstatic指令是程序被编译后，存放于类构造器()方法之中，所以把value赋值为123的动作将在初始化阶段才会执行。

**至于“特殊情况”是指：public static final int value=123，即当类字段的字段属性是ConstantValue时，会在准备阶段初始化为指定的值，所以标注为final之后，value的值在准备阶段初始化为123而非0.**

#### 解析

解析阶段是虚拟机**将常量池内的符号引用替换为直接引用**的过程。解析动作主要针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符7类符号引用进行。

#### 初始化

类初始化阶段是类加载过程的最后一步，<u>到了初始化阶段，才真正开始执行类中定义的java程序代码</u>。在准备极端，变量已经做过一次系统要求的初始值，而在初始化阶段，则根据代码程序制定的主管计划去初始化类变量和其他资源，或者说：初始化阶段是执行类构造器`<clinit>()`方法的过程.

`<clinit>()`方法是由编译器自动收集类中的所有类变量的**赋值动作和静态语句块static{}**中的语句合并产生的，编译器收集的顺序是由语句在源文件中出现的顺序所决定的，**静态语句块只能访问到定义在静态语句块之前的变量**，**在前面的<u>静态语句块</u>可以赋值，但是不能访问**。如下：

```java
public class Test
{
    static
    {
        i=0;
        System.out.println(i);
        //这句编译器会报错：Cannot reference a field before it is defined（非法向前应用）
    }
    static int i=1;
}
```

那么去掉报错的那句，改成下面：

```java
public class Test
{
    static
    {
        i=0;
//      System.out.println(i);
    }
    static int i=1;
 
    public static void main(String args[])
    {
        System.out.println(i);
    }
}
```

输出结果是什么？当然是1啦~在准备阶段我们知道i=0，然后**类初始化阶段按照顺序执行**，首先执行static块中的i=0,接着执行static赋值操作i=1,最后在main方法中获取i的值为1。

虚拟机会保证在子类`<init>()`方法执行之前，父类的`<clinit>()`方法方法已经执行完毕，回到本文开篇的举例代码中，结果会打印输出：SSClass就是这个道理。由于父类的`<clinit>()`方法先执行，也就意味着父类中定义的静态语句块要优先于子类的变量赋值操作。

`<clinit>()`方法对于类或者接口来说并不是必需的，如果一个类中没有静态语句块，也没有对变量的赋值操作，那么编译器可以不为这个类生产`<clinit>()`方法。

接口中不能使用静态语句块，但仍然有变量初始化的赋值操作，因此接口与类一样都会生成`<clinit>()`方法。但接口与类不同的是，执行接口的`<clinit>()`方法不需要先执行父接口的`<clinit>()`方法。只有当父接口中定义的变量使用时，父接口才会初始化。另外，接口的实现类在初始化时也一样不会执行接口的`<clinit>()`方法。

虚拟机会保证一个类的`<clinit>()`方法在多线程环境中被正确的加锁、同步，如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的`<clinit>()`方法，其他线程都需要阻塞等待，直到活动线程执行`<clinit>()`方法完毕。如果在一个类的`<clinit>()`方法中有耗时很长的操作，就可能造成多个线程阻塞，在实际应用中这种阻塞往往是隐藏的。

```java
package jvm.classload;
 
public class DealLoopTest
{
    static class DeadLoopClass
    {
        static
        {
            if(true)
            {
                System.out.println(Thread.currentThread()+"init DeadLoopClass");
                while(true)
                {
                }
            }
        }
    }
 
    public static void main(String[] args)
    {
        Runnable script = new Runnable(){
            public void run()
            {
                System.out.println(Thread.currentThread()+" start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread()+" run over");
            }
        };
 
        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
```

运行结果：（即一条线程在死循环以模拟长时间操作，另一条线程在阻塞等待）

```
Thread[Thread-0,5,main] start
Thread[Thread-1,5,main] start
Thread[Thread-0,5,main]init DeadLoopClass
```

需要注意的是，其他线程虽然会被阻塞，但如果执行()方法的那条线程退出()方法后，其他线程唤醒之后不会再次进入()方法。**同一个类加载器下，一个类型只会初始化一次**。
将上面代码中的静态块替换如下：

```java
static
{
    System.out.println(Thread.currentThread() + "init DeadLoopClass");
    try
    {
        TimeUnit.SECONDS.sleep(10);
    }
    catch (InterruptedException e)
    {
        e.printStackTrace();
    }
}
```

运行结果：

```
Thread[Thread-0,5,main] start
Thread[Thread-1,5,main] start
Thread[Thread-1,5,main]init DeadLoopClass (之后sleep 10s)
Thread[Thread-1,5,main] run over
Thread[Thread-0,5,main] run over
```

 虚拟机规范严格规定了有且只有5中情况（jdk1.7）必须对类进行“初始化”（而加载、验证、准备自然需要在此之前开始）：

1. 遇到`new,getstatic,putstatic,invokestatic`这失调字节码指令时，如果类没有进行过初始化，则需要先触发其初始化。生成这4条指令的最常见的Java代码场景是：使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译器把结果放入常量池的静态字段除外）的时候，以及调用一个类的静态方法的时候。
2. 使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没有进行过初始化，则需要先触发其初始化。
3. 当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化。
4. 当虚拟机启动时，用户需要指定一个要执行的主类（包含main()方法的那个类），虚拟机会先初始化这个主类。
5. 当使用jdk1.7动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getstatic,REF_putstatic,REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有进行初始化，则需要先出触发其初始化。

上面举了一个范例：通过子类引用父类的静态字段，不会导致子类初始化。

这里再举两个例子。

1. 通过数组定义来引用类，不会触发此类的初始化：（SuperClass类已在本文开篇定义）

   ```java
   public class NotInitialization
   {
       public static void main(String[] args)
       {
           SuperClass[] sca = new SuperClass[10];
       }
   }
   ```


2.常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化：

```java
public class ConstClass
{
    static
    {
        System.out.println("ConstClass init!");
    }
    public static  final String HELLOWORLD = "hello world";
}
public class NotInitialization
{
    public static void main(String[] args)
    {
        System.out.println(ConstClass.HELLOWORLD);
    }
}
```

运行结果：

```
hello world
```





# 垃圾回收

## GC简介

使用GC的目的是为了实现来简化内存管理。

