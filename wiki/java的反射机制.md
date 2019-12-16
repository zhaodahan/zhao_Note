#                                               java 的反射机制

## 什么是Java 反射机制

通俗的讲就是反射可以**在运行时根据指定的类名获得类的信息**。

动态：运行期间动态绑定执行规则。

静态：编译以后就已经确定的执行过程。

动态加载类到方法区

```java
public void test1(){
    String str = new String("Hello");
    Class cls = String.class;
    //动态获取 String类型的方法信息：
    Method[] all = cls.getDeclaredMethods();
    //Method 代表方法的信息
    // method.getName 可以获取方法的名称
    for (Method method : all) {
        System.out.println(method.getName());
    }
}
```



**创建对象过程**

1.Java 将类加载到方法区（自动完成）

2.**利用类创建对象**。内存中有一个String对象，**方法区中还有类的信息！通过Class对象可以获取类的相关信息**。

![java_reflect.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/java_reflect.png?raw=true)

## 动态获取类的信息

### 动态获取类的方法信息

Foo类（package：demo）：

```java
//编译以后是 demo.Foo.class
//类的全名是 demo.Foo
class Foo{
    public int test(){
        return 5;
    }
    public double test1(){
        return 5d;
    }
}
```

动态获取Foo类的方法：

```java
@Test
public void testClassForName(){
  /*
     * 动态的加载类信息到方法区s并且返回对应的Class对象！
     * Class 对象可以访问类的全部信息！
     * 
     * 将className对应的类文件，从磁盘中加载到内存方法区，返回这个类的信息
     */
    String className = "demo.Foo";
    try {
        Class cls = Class.forName(className);
        Method[] all = cls.getDeclaredMethods();
        for (Method method : all) {
            System.out.println(method.getName());
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
}
```

输出：

```
test
test1﻿
```



### 动态获取类的属性信息

Eoo类：

```java
class Eoo{
    int id;
    String name;
    double salary;
    String meno;
    public Eoo(){
    	
    }
    public Eoo(int id, String name, double salary, String meno) {
        super();
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.meno = meno;
    }
    // get，set略
}
```

动态获取Eoo类的属性：

```java
@Test
public void testField()throws Exception {
  /*
     * 动态获取一个类的全部属性信息
     * 1 动态加载一个类到方法区
     * 2 动态获取类的属性信息
     */
    String className = "demo.Eoo";
    //动态加载类
    Class cls = Class.forName(className);
    //动态获取类声明的属性信息
    Field[] all = cls.getDeclaredFields();
    for (Field field : all) {
        //getName 获取属性的名字
        System.out.print(field.getName()+" ");
    }
}
```

输出：

```
id name salary meno
```



### 动态获取类的构造器信息

```java
@Test
public void testCon() throws Exception{ 
     //  动态加载类
    String className = "demo.Eoo";
    Class cls = Class.forName(className);
    Constructor[] all = cls.getDeclaredConstructors();
    for (Constructor c : all) {
        System.out.print(c.getName());
        //获取构造器的参数类型列表
        // Parameter 参数  Type类型
        // Class[] 代表所有参数的类型列表
        Class[] types = c.getParameterTypes();
        System.out.println(Arrays.toString(types));
	}
}
```

输出：

```
demo.Eoo[]
demo.Eoo[int, class java.lang.String, double, class java.lang.String]﻿
```



## 动态创建对象

### 调用无参构造器创建对象

如果没有无参数构造器，将发生异常！Class 提供了方法 newInstance()。

```java
@Test
public void testNewInstance() throws Exception{
  /*
     * 动态调用无参数构造器创建对象
     * 1 动态加载类
     * 2 利用class 的方法 newInstance 执行
     * 无参数构造器常见对象
     * 注意：类必须有无参数，否则出异常 
     */
    String className = "java.util.Date";
    Class cls = Class.forName(className);
    // cls.newInstance()调用无参数构造器创建对象
    Object obj = cls.newInstance();
    System.out.println(obj);
    
    //静态的创建对象！编译已经就固定了！
    Date date = new Date();
}
```

输出：

```
Fri Sep 16 20:04:55 CST 2016
```



### 调用有参构造器创建对象

如果没有对应有参数构造器！将发生异常！参数传递错误、将发生异常！

```java
/**
 * 调用 className 类名对应的类的有参数构造器，paramTypes 代表对应构造器的参数列表
 * className + paramTypes 共同决定调用哪个构造器！执行构造器还需要具体的参数params
 */
public Object create(String className,Class[] paramTypes,Object[] params)
    throws Exception{
    // 动态加载类
    // 动态获取指定参数类型的构造器
    // 执行这个构造器，传递 params 参数。
    Class cls = Class.forName(className);
    //getDeclaredConstructor 在类信息中查找
    //给定参数类型的构造器信息
    Constructor c = cls.getDeclaredConstructor(paramTypes);
    //执行构造器 c.newInstance() 方法，创建对象
    //返回值就是这个构造器创建的对象
    Object obj = c.newInstance(params);
    return obj;
}
@Test
public void testCreate() throws Exception {
    String className = "java.util.Date";
    //类型列表==Class类型的数组
    Class[] paramTypes = {long.class};
    //实际参数列表
    Object[] params={-1000L*60*60*24*365};
    Object obj = create(className, paramTypes, params);
    System.out.println(obj);
    //思考：如何动态调用 new String("Hello");
    className = "java.lang.String";
    /*
     * {} 只能拥有声明变量时候直接初始化,不能用于赋值语句！
     * 赋值语句可以使用 new Object[]{"Hello"} 
     */
    paramTypes = new Class[]{String.class};
    params = new Object[]{"Hello"};
    obj = create(className, paramTypes, params);
    System.out.println(obj);//Hello
	
    //思考：如何动态调用 new String(byte[],"utf-8");
    obj = create("java.lang.String", 
        new Class[]{byte[].class, String.class}, 
        new Object[]{new byte[]{65,66,67,68}, "UTF-8"});
    System.out.println(obj);
}
```

输出：

```
Wed Jan 01 08:00:00 CST 1969
Hello
ABCD﻿
```



## 动态获取类的属性值

实现过程（如何利用反射API实现动态属性访问）：

1.找到对象的类型信息（方法区）

2.在信息中找属性信息（Field）

3.在对象上获取属性的值！

类Goo：

```java
public class Goo {
    public int id;
    public String name;
    public Goo() {
        
    }
    public Goo(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
```

动态获取属性的值：

```java
 /**
 * 获取obj对象的 fieldName 对应属性的值
 * @param obj
 * @param fieldName
 * @return 属性值
 */
public Object get(Object obj, String fieldName)throws Exception{
    //1 获取类信息
    /* 
     * Java 中对象的getClass()方法可以获取 对象的类型信息！
     * Java 中 有3种方法可以获取Class信息
     *  1. 类名.class 获取类信息(静态)
     *  2. Class.forName("类名") 获取类信息 动态 
     *  3. obj.getClass() 获取类信息。运行期间，通过当前对象获取类信息
     */
    Class cls = obj.getClass();
    //找到属性:
    /* 
     * getDeclaredField 按照属性名在cls中查找
     * 类信息。 当属性没有找到时候，抛出异常！
     */
    Field field = cls.getDeclaredField(fieldName);
    //在对象上获取属性的值！
    /*
     * get方法：在一个对象上获取属性的值，对象上没有对应的属性，抛出异常
     */
    Object value = field.get(obj);
    return value;
}  
 
@Test
public void testGetField()throws Exception {
    /*
     * 动态获取对象的属性
     */
    Goo goo = new Goo(5, "Tom");
    Object v1 = get(goo, "id");
    Object v2 = get(goo, "name");
    System.out.println(v1+" "+v2);
}
```

输出：

```java
5 Tom
```



## 动态调用类的方法

```java
@Test
public void testinvoke()throws Exception{
    List<String> list=new ArrayList<String>();
    list.add("tom");
    list.add("jack");
    // 动态获取类信息
    Class cls = list.getClass();
    // 通过方法名和参数类型找到对应的方法
    Method method = cls.getDeclaredMethod("remove", new Class[]{int.class});
    // 调用方法，传递对象和具体参数
    Object value=method.invoke(list,new Object[]{0});
    System.out.println(value); //tom
}
```

## 反射的意义

**常见的框架的底层都是使用反射实现的**！如：Spring MyBatis Struts2 Hibernate …

现有application.xml文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans>
    <bean id="date" class="java.util.Date"></bean>
    <bean id="list" class="java.util.ArrayList"></bean>
</beans>
```

模拟Spring框架的getBean()方法：

```java
public class ApplicationContext {
    private HashMap<String, Object> map= new HashMap<String, Object>();	
    public ApplicationContext(String xml) {
        SAXReader reader = new SAXReader();
        try{
            //读取xml
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(xml);
            Document doc = reader.read(in);
            //解析XML内容 获取全部的<bean>
            List<Element> beans = doc.getRootElement().elements();
            for (Element e : beans) {
                //e 是每个 <bean> 元素
                String id = e.attributeValue("id");
                String className = e.attributeValue("class");
                //利用反射创建对象
                Class cls = Class.forName(className);
                Object obj = cls.newInstance();
                //对象缓存到 map中
                map.put(id, obj);
            }
			
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public Object getBean(String id){
        return map.get(id);
    }
    public static void main(String[] args) {
        String conf = "application.xml";
        ApplicationContext ac = new ApplicationContext(conf);
        Object o = ac.getBean("date");
        System.out.println(o); //Fri Sep 16 21:30:07 CST 2016
    }
}
```





　
