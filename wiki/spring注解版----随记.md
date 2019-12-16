# Spring 注解版随记

1： spring 底层的底层重要的特性就是IOC（**控制反转**）和DI (**依赖注入**).

**spring 认为所有的组件**( 一般就是我们需要使用的类 )**都应该放在IOC容器中**。 **组件之间的关系**(对象之间的调用)**通过容器来进行自动装配**(也就是依赖注入)。



2 : FactoryBean 

普通的对象，导入到容器中。 容器会调用他的无参构造器来构建一个对象注册到容器中。 

FactoryBean : 顾名思义，这是一个创建bean工厂的接口。我们会将这个接口中定义的getObject()的返回值作为实际在IOC容器中注册的bean。



3 ： 容器bean 的生命周期

单实例： 在IOC容器启动的时候创建对象，在容器关闭的时候销毁对象。

多实例： 在对象使用的时候创建对象。 每次使用创建一个对象。多实例容器关闭的时候不会管理这个对象。



4： IOC 容器创建对象步骤：

  1） 执行构造方法： 构造

  2） 执行初始化方法。



5：自动装配 DI

spring中利用依赖注入，完成对IOC容器中各个组件依赖关系的赋值。

![spring_annotation.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/spring_annotation.png?raw=true)

![spring_annotation2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/spring_annotation2.png?raw=true)

![spring_annotation3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/spring_annotation3.png?raw=true)



　6: AOP

在程序运行期间动态将某段代码切入到指定方法的指定位置运行的机制。 (他的底层运用的就是动态代理)



7： BeanPostProcessor ： bean的后置处理器。

 在bean 初始化前后做一些事情。



