# Hibernate笔记

## 什么是Hibernate

hibernate是一个**框架**。

```
什么是框架？
框架是一个半成品，在这基础上开发可以提高我们的效率，减少代码量，提高程序的健壮性。框架的底层帮我们写好了一些借口和类，我们直接调用就好了。 
```

hibernate是java领域的**持久化**框架。

```
什么是持久化？

简单的理解，持久化就是将数据永久的保存在数据库中————即写库。 
广义的理解：所有对数据库的操作，都是持久化
```

Hibernate是一个**ORM框架**。

![JAVA_HIBERNATE1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE1.png?raw=true)

```
什么是ORM?
（Object/Relation Mapping）对象关系映射
ORM的思想： 将数据库中表的记录映射为对象。将对数据库数据的操作转换为对对象的操作。
ORM是对JDBC的封装
```

![JAVA_HIBERNATE2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE2.png?raw=true)

从ORM原理图中可以看出，在使用ORM框架的过程中，

```
主要关注两点： 
1：对象关系映射文件  (怎么描述)
2：ORM API  (怎么调用)
```

总结： Hibernate就是采用面向对象的处理方式来操作数据库。

**如何进行Hibernate开发**

![JAVA_HIBERNATE3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE3.png?raw=true)

第一步：创建hibernate的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
		"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
		"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
	<session-factory>
    
		<!-- 配置连接数据库的基本信息 -->
		<property name="connection.username">root</property>
		<property name="connection.password">1230</property>
		<property name="connection.driver_class">com.mysql.jdbc.Driver</property>
		<property name="connection.url">jdbc:mysql:///hibernate5</property>
		
		<!-- 配置 hibernate 的基本信息 -->
		<!-- hibernate 所使用的数据库方言 -->
		<property name="dialect">org.hibernate.dialect.MySQLInnoDBDialect</property>		
		
		<!-- 执行操作时是否在控制台打印 SQL -->
		<property name="show_sql">true</property>
	
		<!-- 是否对 SQL 进行格式化 -->
		<property name="format_sql">true</property>
	
		<!-- 指定自动生成数据表的策略 -->
		<property name="hbm2ddl.auto">update</property>
		
		<!-- 指定关联的 .hbm.xml 文件,这里配置的不是全类名，而是目录结构 -->
		<mapping resource="com/atguigu/hibernate/helloworld/News.hbm.xml"/>
	
	</session-factory>
</hibernate-configuration>
```

第二步： 创建持久化类 和关系映射文件 。。。

创建持久化构造类需要注意

```
1： 必须要提供一个无参的构造方法
2： 要带有标识id
3:  属性需要具有getter和setter
4： 不能声明为final，懒加载时因为需要hibernate运行时需要生成Cglib代理
5： 需要重写equals 和hashcode ，javaBean可能被放在集合中
```

关系映射文件描述的是持久类与关系表之间的关系

![JAVA_HIBERNATE4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE4.png?raw=true)

```xml
<hibernate-mapping package="com.atguigu.hibernate.helloworld">

    <class name="News" table="NEWS" dynamic-insert="true">
    	
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <!-- 指定主键的生成方式, native: 使用数据库本地方式 -->
            <generator class="native" />
        </id>
    
```



第三步： 基本使用

```java
public void test() {	
		System.out.println("test...");
		//1. 创建一个 SessionFactory 对象
		SessionFactory sessionFactory = null;
		//1). 创建 Configuration 对象: 对应 hibernate 的基本配置信息和 对象关系映射信息
		Configuration configuration = new Configuration().configure();
		//4.0 之前这样创建
//		sessionFactory = configuration.buildSessionFactory();
    
		//2). 创建一个 ServiceRegistry 对象: hibernate 4.x 新添加的对象
		//hibernate 的任何配置和服务都需要在该对象中注册后才能有效.
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				                            .buildServiceRegistry();
		
		//3).
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//2. 创建一个 Session 对象
		Session session = sessionFactory.openSession();
		//3. 开启事务
		Transaction transaction = session.beginTransaction();
		//4. 执行保存操作
		News news = new News("Java12345", "ATGUIGU", new Date(new java.util.Date().getTime()));
		session.save(news);
	
		//5. 提交事务 
		transaction.commit();
		//6. 关闭 Session
		session.close();
		//7. 关闭 SessionFactory 对象
		sessionFactory.close();
	}
```

### 




### Configuration 类

```
Configuration 类负责管理 Hibernate 的配置信息

Hibernate 运行的底层信息：数据库的URL、用户名、密码、JDBC驱动类，数据库Dialect,数据库连接池等（对应 hibernate.cfg.xml 文件）。
持久化类与数据表的映射关系（*.hbm.xml 文件）

创建 Configuration 的两种方式 都是去读取类似hibernate.cfg.xml 的hibernate配置文件信息构建
————属性文件（hibernate.properties）:
Configuration cfg = new Configuration();
Xml文件（hibernate.cfg.xml） 推荐这种方式
Configuration cfg = new Configuration().configure();
——Configuration 的 configure 方法还支持带参数的访问：
File file = new File(“simpleit.xml”);
Configuration cfg = new Configuration().configure(file);
```

### SessionFactory

```
如同数据库中的表对应着java持久类， 我们的单个数据库在Hibernate中就对应着这个SessionFactory对象。

sessionFactory这个对象是线程安全的，他一旦创建完毕，即被赋予特定的配置信息后就不会再被修改了

SessionFactory是生成Session的工厂。构造 SessionFactory 很消耗资源，一般情况下一个应用中只初始化一个 SessionFactory 对象。Hibernate4 新增了一个 ServiceRegistry 接口，所有基于 Hibernate 的配置或者服务都必须统一向这个 ServiceRegistry  注册后才能生效

创建sessionFactory的步骤

```

![JAVA_HIBERNATE5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE5.png?raw=true)

### Session 接口

![JAVA_HIBERNATE6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE6.png?raw=true)

```
Session 是应用程序与数据库之间交互操作的一个单线程对象，是 Hibernate 运作的中心，所有持久化对象必须在 session 的管理下才可以进行持久化操作。此对象的生命周期很短。Session 对象有一个一级缓存，显式执行 flush 之前，所有的持久层操作的数据都缓存在 session 对象处。《相当于 JDBC 中的 Connection》。

session相当于java程序与数据库的一次连接。实际上是对connection的一个封装。

持久化类与 Session 关联起来后，持久化对象就具有了持久化的能力。
Session 类的方法：
取得持久化对象的方法： get() load()
持久化对象都得保存，更新和删除：save(),update(),saveOrUpdate(),delete()
开启事务: beginTransaction().
管理 Session 的方法：isOpen(),flush(), clear(), evict(), close()等

```

### Transaction(事务)

```
是数据库事务对应的java对象。

所有持久层都应该在事务管理下进行，即使是只读操作。 hibernate中没有打开事务，不会进行实际的持久化操作
  Transaction tx = session.beginTransaction();
常用方法:
commit():提交相关联的session实例
rollback():撤销事务操作
wasCommitted():检查事务是否提交
```

### Hibernate 配置文件的配置项

```
hbm2ddl.auto：即由 java 代码生成数据库脚本, 进而生成具体的表结构。

取值 ：
create : 会根据 .hbm.xml  文件来生成数据表, 但是每次运行都会删除上一次的表 ,重新生成表, 哪怕二次没有任何改变 

create-drop : 会根据 .hbm.xml 文件生成表,但是SessionFactory一关闭, 表就自动删除 

update : 最常用的属性值，也会根据 .hbm.xml 文件生成表, 但若 .hbm.xml  文件和数据库中对应的数据表的表结构不同, Hiberante  将更新数据表结构，但不会删除已有的《行 ，原有的数据依旧会存在》和《列， 维护原来的数据合理性》 

validate : 会和数据库中的表进行比较, 若 .hbm.xml 文件中的列在数据表中不存在，则抛出异常。但是他不会去修改表

format_sql：是否将 SQL 转化为格式良好的 SQL . 取值 true | false

```



## 通过 Session操纵对象

```
Session 接口是 Hibernate 向应用程序提供的操纵数据库的最主要的接口, 它提供了基本的保存, 更新, 删除和《加载》(这里是加载，而不是查询，查询需要单独的query接口) Java 对象的方法.

Session 具有一个缓存, 位于缓存中的对象称为持久化对象, 它和数据库中的相关记录对应。(这个缓存被称为hibernate的一级缓存)。Session 能够在某些时间点, 按照缓存中对象的变化来执行相关的 SQL 语句, 来同步更新数据库, 这一过程被称为刷新缓存(flush)

```

测试代码模板

```java
public class HibernateTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				                            .buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy(){
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
    //Junit模板
	@Test
	public void testComponent(){
		Worker worker = new Worker();
        //...............
		session.save(worker);
	}
}
```

### session缓存

```
在 Session 接口的实现中包含一系列的 Java 集合, 这些 Java 集合构成了 Session 缓存. 只要 Session 实例没有结束生命周期, 且没有清理缓存，则存放在它缓存中的对象也不会结束生命周期
Session 缓存可减少 Hibernate 应用程序访问数据库的频率。
```

![JAVA_HIBERNATE7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE7.png?raw=true)

#### 操作session缓存

![JAVA_HIBERNATE8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE8.png?raw=true)

```java
	/**
 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
 * 1. 在 Transaction 的 commit() 方法中: 先调用 session 的 flush 方法, 再提交事务， 即在session的生命周期里，在事务中操作session缓存会在事务的commit()时自动的flsh(),一保证缓存和数据库数据一致
 * 2. flush() 方法会可能会发送 SQL 语句, 但不会提交事务. 
 * 3. 注意: 在未提交事务或显式的调用 session.flush() 方法之前, 也有可能会进行 flush() 操作.
 * 1). 执行 HQL 或 QBC 查询, 会先进行 flush() 操作, 以得到数据表的最新的记录 (在事务的范围内，保证查询的结果必须是最新的)
 * 2). 若记录的 ID 是由底层数据库使用自增的方式生成的(native), 则在调用 save() 方法时, 就会立即发送 INSERT 语句（数据会插入到数据库，这是个例外的情况）. 
 * 因为 save 方法后, 必须保证对象的 ID 是存在的(在缓存中)!  如果是使用hilo的方式生成id，就不需要发送insert来生成id.
	 */
    @Test
	public void testSessionFlush(){
		News news = (News) session.get(News.class, 1);
		news.setAuthor("Oracle"); //1
		
//		session.flush();
//		System.out.println("flush");
		
		News news2 = (News) session.createCriteria(News.class).uniqueResult();
		System.out.println(news2); //3.1
	}

	@Test
	public void testSessionFlush2(){
		News news = new News("Java", "SUN", new Date());
		session.save(news); //3.2
	}
```

flush 缓存

```
默认情况下 Session 在以下时间点刷新缓存：
显式调用 Session 的 flush() 方法
当应用程序调用 Transaction 的 commit（）方法的时, 该方法先 flush ，然后在向数据库提交事务
当应用程序执行一些查询(HQL, Criteria)操作时，如果缓存中持久化对象的属性已经发生了变化，会先 flush 缓存，以保证查询结果能够反映持久化对象的最新状态

flush 缓存的例外情况: 如果对象使用 native 生成器生成 OID, 那么当调用 Session 的 save() 方法保存对象时, 会立即执行向数据库插入该实体的 insert 语句.

commit() 和 flush() 方法的区别：flush 执行一系列 sql 语句，但不提交事务；commit 方法先调用flush() 方法，然后提交事务. 意味着提交事务意味着对数据库操作永久保存下来。

```

reflesh（）

```java
/**
	 * refresh(): 会强制发送 SELECT 语句, 以使 Session 缓存中对象的状态和数据表中对应的记录保持一致!
	 */
	@Test
	public void testRefresh(){
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		//手动修改数据库中记录的信息，再来读取
		session.refresh(news); 
		System.out.println(news); 
        // 这里可能读取还是不是最新的数据，这是因为事务的隔离级别的原因。mysql默认的隔离级别是可重复读REPEATABLE READ。 这里需要通过hibernate修改事物隔离级别为READ COMMITED读已提交。
	}
```

hibernate设置事务的隔离级别

```xml
 Hibernate 通过为 Hibernate 映射文件指定 hibernate.connection.isolation 属性来设置事务的隔离级别
在 Hibernate 的配置文件中可以显式的设置隔离级别. 每一个隔离级别都对应一个整数:
1. READ UNCOMMITED
2. READ COMMITED 
4. REPEATABLE READ
8. SERIALIZEABLE
<!-- 设置 Hibernate 的事务隔离级别 -->
<property name="connection.isolation">2</property>
```



### 持久化对象的状态

![JAVA_HIBERNATE9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE9.png?raw=true)

```
(方便理解： 可以将场景类比，入职公司，OID是员工号，session是在公司管理体制下)
临时对象（Transient）:  (对应着未入职)
在使用代理主键的情况下, OID 通常为 null
<不处于 Session 的缓存中>,不在整个机制监控之下
在数据库中没有对应的记录

持久化对象(也叫”托管”)（Persist）： (入职了)
OID 不为 null
《位于 Session 缓存中》
若在数据库中已经有和其对应的记录, 持久化对象和数据库中的相关记录对应
Session 在 flush 缓存时, 会根据持久化对象的属性变化, 来同步更新数据库
在同一个 Session 实例的缓存中, 数据库表中的每条记录只对应唯一的持久化对象

删除对象(Removed) ： (离职了)
曾经拥有，现在发生改变，在数据库中没有和其 OID 对应的记录
不再处于 Session 缓存中了。
一般情况下, 应用程序不该再使用被删除的对象

游离对象(也叫”脱管”) （Detached）： (请假了，暂时脱离公司体制管理)
《OID 不为 null，不再处于 Session 缓存中》
一般情况需下, 游离对象是由持久化对象转变过来的, 因此在数据库中可能还存在与它对应的记录

```

#### session中各个方法及对状态的影响

save（）方法

```java
/**
	 * 1. save() 方法
	 * 1). 使一个临时对象变为持久化对象
	 * 2). 为对象分配 ID.
	 * 3). 在 flush 缓存时会发送一条 INSERT 语句.
	 * 4). 在 save 方法之前的 id 是无效的
	 * 5). 持久化对象的 ID 是不能被修改的!
	 */
	@Test
	public void testSave(){
		News news = new News();
		news.setTitle("CC")；。。。。
         //news.setId(100)  //对应4 ,save前设置id是无效的，实际插入后会变
		System.out.println(news);	
		session.save(news);
		System.out.println(news); //save() 打印前后区别在于save后持久化对象有了id.
//		news.setId(101); //报错 对应5
	}
```

persist()：

```java
/**
	 * persist(): 也会执行 INSERT 操作
	 * 
	 * 和 save() 的区别 : 
	 * 在调用 persist 方法之前, 若对象已经有 id 了, 则不会执行 INSERT, 而抛出异常
	 */
	@Test
	public void testPersist(){
		News news = new News();
		news.setTitle("EE");
		news.setAuthor("ee");
		news.setDate(new Date());
		news.setId(200); 
		
		session.persist(news); 
	}
```

get() 和 load() 方法

```java
/**
	 * get VS load: 根据class和id，从session中取对应的持久化对象。如果session缓存中有就从session缓存中
	 * 取，如果没有就发送一条select by id ，sql语句进行查询
	 * 
	 * 1. 执行 get 方法: 会立即加载对象. 
	 * 执行 load 方法, 若不适用该对象, 则不会立即执行查询操作, 而返回一个《代理对象》，这里是使用cglib代理
	 *    
	 *    get 是 立即检索, load 是延迟检索. 
	 * 
	 * 2.在需要初始化代理对象之前已经关闭了Session，load方法可能会抛出 LazyInitializationException异常: 
	 * 
	 * 3. 若数据表中没有对应的记录, Session 也没有被关闭.  
	 *    get 返回 null
	 *    load 若不使用该对象的任何属性, 没问题; 若需要初始化了, 抛出异常.  
	 */
	@Test
	public void testLoad(){
		
		News news = (News) session.load(News.class, 10);
		System.out.println(news.getClass().getName()); 
		
//		session.close();
//		System.out.println(news); 
	}
	
	@Test
	public void testGet(){
		News news = (News) session.get(News.class, 1);
//		session.close();
		System.out.println(news); 
	}
	
```

update方法

```java
/**
	 * update:
	 * 在什么时候用
	 * 1. 若更新一个持久化对象, 不需要显示的调用 update 方法. 因为在调用 Transaction
	 * 的 commit() 方法时, 会先执行 session 的 flush 方法.
	 * 2. 更新一个游离对象（即，对象不在当前session缓存中）, 需要显式的调用 session 的 update 方法. 可以
	 *    把一个游离对象
	 * 变为持久化对象
	 * 
	 * 需要注意的:
	 * 1. 无论要更新的游离对象和数据表的记录是否一致, 都会发送 UPDATE 语句. 
	 *    如何能让 updat 方法不再盲目的出发 update 语句呢 ? 在 .hbm.xml 文件的 class 节点设置
	 *    select-before-update=true (默认为 false). 但通常不需要设置该属性. 
	 * 
	 * 2. 若数据表中没有对应的记录, 但还调用了 update 方法, 会抛出异常
	 * 
	 * 3. 当 update() 方法关联一个游离对象时, 
	 * 如果在 Session 的缓存中已经存在相同 OID 的持久化对象, 会抛出异常. 因为在 Session 缓存中
	 * 不能有两个 OID 相同的对象!
	 *    
	 */
	@Test
	public void testUpdate(){
		News news = (News) session.get(News.class, 1);
		
		transaction.commit();
		session.close();
		
//		news.setId(100);

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
//		news.setAuthor("SUN"); 
		
		News news2 = (News) session.get(News.class, 1);
		session.update(news);
	}
```

saveOrUpdate() 方法。同时包含了 save() 与 update() 方法的功能

![JAVA_HIBERNATE10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE10.png?raw=true)

```java
/**
	 * 注意:
	 * 1. 若 OID 不为 null, 但数据表中还没有和其对应的记录. 会抛出一个异常. 
	 * 2. 了解: OID 值等于 id 的 unsaved-value 属性值的对象, 也被认为是一个游离对象
	 */
	@Test
	public void testSaveOrUpdate(){
		News news = new News("FFF", "fff", new Date());
		news.setId(11);
		
		session.saveOrUpdate(news); 
	}
```

merge() 方法

![JAVA_HIBERNATE11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE11.png?raw=true)

无论任何状态，最终生成一个符合我们预期的对象。

delete()方法

```java
/**   Session 的 delete() 方法既可以删除一个游离对象, 也可以删除一个持久化对象
	 * delete: 执行删除操作. 只要 OID 和数据表中一条记录对应, 就会准备执行 delete 操作
	 * 若 OID 在数据表中没有对应的记录, 则抛出异常
	 * 
	 * 可以通过设置 hibernate 配置文件 hibernate.use_identifier_rollback 为 true,
	 * 使删除对象后, 把其 OID 置为  null
	 */
	@Test
	public void testDelete(){
//		News news = new News();
//		news.setId(11);
		
		News news = (News) session.get(News.class, 163840);
		session.delete(news);  
        System.out.println(news);
        //这里删除了只是先删除的session缓存中，只有在flush时才会删除数据库中的记录。但是这里会存在一个问题，我们通过news对象的引用仍然可以获取到一个对象且id不是空，这时候，仍然可以调用非update方法。，但是这样就会报错。
        //所以这这时候我们可以在hibernate 配置文件 中配置。
           	<!-- 删除对象后, 使其 OID 置为 null -->
    	<property name="use_identifier_rollback">true</property>
		
	}
```

evict方法

```java
/**
	 * evict: 从 session 缓存中把指定的持久化对象移除
	 */
	@Test
	public void testEvict(){
		News news1 = (News) session.get(News.class, 1);
		News news2 = (News) session.get(News.class, 2);
		
		news1.setTitle("AA");
		news2.setTitle("BB");
		
		session.evict(news1);  // 将1 从缓存中移除了，这样我们在提交事务的时候就只会执行一个update语句。
	}
```

### 通过 Hibernate 调用存储过程

hibernate并没有提供原生的API来调用存储过程，只能够通过hibernate找到JDBC, 然后像JDBC那样去调用存储过程

Work 接口: 直接通过 JDBC API 来访问数据库的操作

![JAVA_HIBERNATE12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE12.png?raw=true)

```
Session 的 doWork(Work) 方法用于执行 Work 对象指定的操作, 即调用 Work 对象的 execute() 方法. Session 会把当前使用的数据库连接传递给 execute() 方法.
```

![JAVA_HIBERNATE13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE13.png?raw=true)

不经如此

```java
@Test
	public void testDoWork(){
		session.doWork(new Work() {	
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println(connection); 
				//调用存储过程. 
                // 这里不仅仅能用来调用存储过程，还能够通过原生的jdbc connenction 来进行批量操作等
			}
		});
	}
```

### Hibernate 与触发器协同工作

```
Hibernate 与数据库中的触发器协同工作时, 会造成两类问题
1）触发器使 Session 的缓存中的持久化对象与数据库中对应的数据不一致(触发器修改了数据的数据):触发器运行在数据库中, 它执行的操作对 Session 是透明的
2）Session 的 update() 方法盲目地激发触发器: 无论游离对象的属性是否发生变化, 都会执行 update 语句, 而 update 语句会激发数据库中相应的触发器

解决方案: 
1）在执行完 Session 的相关操作后, 立即调用 Session 的 flush() 和 refresh() 方法, 迫使 Session 的缓存与数据库同步(refresh() 方法重新从数据库中加载对象)

2）在映射文件的的 <class> 元素中设置 select-before-update 属性: 当 Session 的 update 或 saveOrUpdate() 方法更新一个游离对象时, 会先执行 Select 语句, 获得当前游离对象在数据库中的最新数据, 只有在不一致的情况下才会执行 update 语句。 这样就能解决盲目的update的问题

```



## Hibernate 的配置文件hibernate.cfg.xml

```
Hibernate 配置文件主要用于配置数据库连接和 Hibernate 运行时所需的各种属性
每个 Hibernate 配置文件对应一个 Configuration 对象
Hibernate配置文件可以有两种格式:
hibernate.properties 
hibernate.cfg.xml （不推荐）
```

在 hibernate 中使用 C3P0 数据源:

```xml
1). 导入 jar 包:

hibernate-release-4.2.4.Final\lib\optional\c3p0\*.jar

2). 加入配置:

hibernate.c3p0.max_size: 数据库连接池的最大连接数
hibernate.c3p0.min_size: 数据库连接池的最小连接数
hibernate.c3p0.acquire_increment: 当数据库连接池中的连接耗尽时, 同一时刻获取多少个数据库连接

hibernate.c3p0.timeout:   数据库连接池中连接对象在多长时间没有使用过后，就应该被销毁
hibernate.c3p0.idle_test_period:  表示连接池检测线程多长时间检测一次池内的所有链接对象是否超时. 
连接池本身不会把自己从连接池中移除，而是专门有一个线程按照一定的时间间隔来做这件事，
这个线程通过比较连接对象最后一次被使用时间和当前时间的时间差来和 timeout 做对比，进而决定是否销毁这个连接对象。 

hibernate.c3p0.max_statements:  缓存 Statement 对象的数量

在hibernate.cfg.xml 中配置c3p0数据源
<!-- 配置 C3P0 数据源 -->
    	<property name="hibernate.c3p0.max_size">10</property>
    	<property name="hibernate.c3p0.min_size">5</property>
    	<property name="c3p0.acquire_increment">2</property>
    	
    	<property name="c3p0.idle_test_period">2000</property>
    	<property name="c3p0.timeout">2000</property>
    	
    	<property name="c3p0.max_statements">10</property>
    	
    	<!-- 设定 JDBC 的 Statement 读取数据的时候每次从数据库中取出的记录条数 -->
    	<property name="hibernate.jdbc.fetch_size">100</property>
    	
    	<!-- 设定对数据库进行批量删除，批量更新和批量插入的时候的批次大小 -->
    	<property name="jdbc.batch_size">30</property>
 设置上面两个属性对mysql数据库是无效的，但是对oracle是有效的。

hibernate.jdbc.fetch_size：实质是调用 Statement.setFetchSize() 方法设定 JDBC 的 Statement 读取数据的时候每次从数据库中取出的记录条数。
例如一次查询1万条记录，对于Oracle的JDBC驱动来说，是不会 1 次性把1万条取出来的，而只会取出 fetchSize 条数，当结果集遍历完了这些记录以后，再去数据库取 fetchSize 条数据。因此大大节省了无谓的内存消耗。Fetch Size设的越大，读数据库的次数越少，速度越快；Fetch Size越小，读数据库的次数越多，速度越慢。Oracle数据库的JDBC驱动默认的Fetch Size = 10，是一个保守的设定，根据测试，当Fetch Size=50时，性能会提升1倍之多，当fetchSize=100，性能还能继续提升20%，Fetch Size继续增大，性能提升的就不显著了。并不是所有的数据库都支持Fetch Size特性，例如MySQL就不支持

hibernate.jdbc.batch_size：设定对数据库进行批量删除，批量更新和批量插入的时候的批次大小，类似于设置缓冲区大小的意思。batchSize 越大，批量操作时向数据库发送sql的次数越少，速度就越快。测试结果是当Batch Size=0的时候，使用Hibernate对Oracle数据库删除1万条记录需要25秒，Batch Size = 50的时候，删除仅仅需要5秒！Oracle数据库 batchSize=30 的时候比较合适。
```



## 对象关系映射文件 *.hbm.xml

hbm==》hibernate mapping

![JAVA_HIBERNATE14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE14.png?raw=true)

```
通过这个文件，Hibernate可以理解持久化类和数据表之间的对应关系，也可以理解持久化类属性与数据库表列之间的对应关系。在运行时 Hibernate 将根据这个映射文件来生成各种 SQL 语句
(所以这个文件是十分的重要)

hibernate-mapping
类层次：class
主键：id
基本类型:property
实体引用类: many-to-one  |  one-to-one
集合:set | list | map | array
one-to-many
many-to-many
子类:subclass | joined-subclass
其它:component | any 等
查询语句:query（用来放置查询语句，便于对数据库查询的统一管理和优化）
```

### hibernate-mapping

```xml
hibernate-mapping 是 hibernate 映射文件的根元素
<hibernate-mapping package="com.atguigu.hibernate.entities">
    <class name="News" table="NEWS" dynamic-update="true">
       </class>   
</hibernate-mapping>
虽然在一个hibernate-mapping 中我们可以写多个class。但是建议还是只写一个class，这样容易区分。

hibernate-mapping 中最主要的属性就是package。 package (可选): 指定一个包前缀，如果在映射文档中没有指定全限定的类名， 就使用这个作为包名。 (在配置关联关系的时候能够起到简化配置的作用) 

其他属性：

default-cascade(默认为 none): 设置hibernate默认的级联风格. 若配置 Java 属性, 集合映射时没有指定 cascade 属性, 则 Hibernate 将采用此处指定的级联风格. 

default-access (默认为 property): 指定 Hibernate 的默认的属性访问策略。默认值为 property, 即使用 getter, setter 方法来访问属性. 若指定 access, 则 Hibernate 会忽略 getter/setter 方法, 而通过反射访问成员变量.

default-lazy(默认为 true): 设置 Hibernat morning的延迟加载策略. 该属性的默认值为 true, 即启用延迟加载策略. 若配置 Java 属性映射, 集合映射时没有指定 lazy 属性, 则 Hibernate 将采用此处指定的延迟加载策略 

```

### class

```xml
class 元素用于指定类和表的映射

name:指定该持久化类映射的持久化类的类名

table:指定该持久化类映射的表名, Hibernate 默认以持久化类的类名作为表名

  <class name="News" table="NEWS" dynamic-update="true">

dynamic-insert: 若设置为 true, 表示当保存一个对象时, 会动态生成 insert 语句, insert 语句中仅包含所有取值不为 null 的字段. 默认值为 false

dynamic-update: 若设置为 true, 表示当更新一个对象时, 会动态生成 update 语句, update 语句中仅包含所有取值需要更新的字段. 默认值为 false
      
dynamic-insert和dynamic-update 就是在我们对session中元素进行操作的时候，能够动态的生成sql语句，即生成的sql语句只包含改变了的值。

select-before-update:设置 Hibernate 在更新某个持久化对象之前是否需要先执行一次查询. 默认值为 false。这个是指为true的时候，总会发送一条select，会导致我们的性能降低。无需要则不设置这个属性。

batch-size:指定根据 OID 来抓取实例时每批抓取的实例数.

lazy: 指定是否使用延迟加载.  

mutable: 若设置为 true, 等价于所有的 <property> 元素的 update 属性为 false, 表示整个实例不能被更新. 默认为 true. 

discriminator-value: 指定区分不同子类的值. 当使用 <subclass/> 元素来定义持久化类的继承关系时需要使用该属性

```

### id

```xml
Hibernate 使用对象标识符(OID) 来建立内存中的对象和数据库表中记录的对应关系. 对象的 OID 和数据表的主键对应.
在对象-关系映射文件中, <id> 元素用来设置对象标识符. <generator> 子元素用来设定标识符生成器.
Hibernate 提供了标识符生成器接口: IdentifierGenerator, 并提供了各种内置实现。我们也可以自己实现。s

<id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
id：设定持久化类的 OID 和表的主键的映射
name: 标识持久化类 OID 的属性名  
column: 设置标识属性所映射的数据表的列名(主键字段的名字). 
unsaved-value:若设定了该属性, Hibernate 会通过比较持久化类的 OID 值和该属性值来区分当前持久化类的对象是否为临时对象
type:指定 Hibernate 映射类型. Hibernate 映射类型是 Java 类型与 SQL 类型的桥梁. 如果没有为某个属性显式设定映射类型, Hibernate 会运用反射机制先识别出持久化类的特定属性的 Java 类型, 然后自动使用与之对应的默认的 Hibernate 映射类型
Java 的基本数据类型和包装类型对应相同的 Hibernate 映射类型. 基本数据类型无法表达 null, 所以对于持久化类的 OID 推荐使用包装类型
    
generator：设定持久化类设定标识符生成器
class: 指定使用的标识符生成器全限定类名或其缩写名
```

主键生成策略

![JAVA_HIBERNATE15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE15.png?raw=true)

```
increment 标识符生成器由 Hibernate 以递增的方式为代理主键赋值，他是先读取主键，然后再在本地加一。这个加一会存在多线程并发问题。所以这种方式只适合测试并不适合开发

identity 标识符生成器由底层数据库来负责生成标识符, 它要求底层数据库把主键定义为自动增长字段类型，依赖于底层数据库系统。OID 必须为 long, int 或 short 类型, 如果把 OID 定义为 byte 类型, 在运行时会抛出异常

sequence标识符生成器利用底层数据库提供的序列来生成标识符。他要求底层数据库必须支持序列(mysql 不支持，oracle支持)

hilo 标识符生成器由 Hibernate 按照一种 high/low 算法*生成标识符, 它从数据库的特定表的字段中获取 high 值。(他会发送查询语句去查询)hilo 生存标识符机制不依赖于底层数据库系统, 因此它适合所有的数据库系统

native （最常用） 标识符生成器依据底层数据库对自动生成标识符的支持能力, 来选择使用 identity, sequence 或 hilo 标识符生成器. 由于 native 能根据底层数据库系统的类型, 自动选择合适的标识符生成器, 因此很适合于跨数据库平台开发


```

### Property

```xml
property 元素用于指定类的属性和表的字段的映射
name:指定该持久化类的属性的名字
column:指定与类的属性映射的表的字段名. 如果没有设置该属性, Hibernate 将直接使用类的属性名作为字段名. 
type:指定 Hibernate 映射类型. Hibernate 映射类型是 Java 类型与 SQL 类型的桥梁. 如果没有为某个属性显式设定映射类型, Hibernate 会运用反射机制先识别出持久化类的特定属性的 Java 类型, 然后自动使用与之对应的默认的 Hibernate 映射类型.
not-null:若该属性值为 true, 表明不允许为 null, 默认为 false
access:指定 Hibernate 的默认的属性访问策略。默认值为 property, 即使用 getter, setter 方法来访问属性. 若指定 field, 则 Hibernate 会忽略 getter/setter 方法, 而通过反射访问成员变量
unique: 设置是否为该属性所映射的数据列添加唯一约束. 

index: 指定一个字符串的索引名称. 当系统需要 Hibernate 自动建表时, 用于为该属性所映射的数据列创建索引, 从而加快该数据列的查询.
length: 指定该属性所映射数据列的字段的长度
scale: 指定该属性所映射数据列的小数位数, 对 double, float, decimal 等类型的数据列有效.
formula：设置一个 SQL 表达式, Hibernate 将根据它来计算出派生属性的值. 
派生属性: 并不是持久化类的所有属性都直接和表的字段匹配, 持久化类的有些属性的值必须在运行时通过计算才能得出来, 这种属性称为派生属性

  <!-- 映射派生属性 -->
<property name="desc" formula="(SELECT concat(author, ': ', title) FROM NEWS n WHERE n.id = id)"></property>
(他的实现原理是： 内置一个子查询)
formula=“(sql)” 的英文括号不能少
Sql 表达式中的列名和表名都应该和数据库对应, 而不是和持久化对象的属性对应
如果需要在 formula 属性中使用参数, 这直接使用 where cur.id=id 形式, 其中 id 就是参数(外表的id), 和当前持久化对象的 id 属性对应的列的 id 值将作为参数传入. 
```

## Java 时间和日期类型的Hibernate映射

![JAVA_HIBERNATE16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE16.png?raw=true)

```xml
Java 中, 代表时间和日期的类型包括: java.util.Date 和 java.util.Calendar. 
JDBC API 中还提供了 3 个扩展了 java.util.Date 类的子类: java.sql.Date, java.sql.Time 和 java.sql.Timestamp, 这三个类分别和标准 SQL 类型中的 DATE, TIME 和 TIMESTAMP 类型对应

在标准 SQL 中, DATE 类型表示日期, TIME 类型表示时间, TIMESTAMP 类型表示时间戳, 同时包含日期和时间信息. 

III. 如何把 java.util.Date 映射为 DATE, TIME 和 TIMESTAMP ?

可以通过 property 的 type 属性来进行映射: 

例如:

<property name="date" type="timestamp">
    <column name="DATE" />
</property>

<property name="date" type="data">
    <column name="DATE" />
</property>

<property name="date" type="time"> (建议写这个)
    <column name="DATE" />
</property>

其中 timestamp, date, time 既不是 Java 类型, 也不是标准 SQL 类型, 而是 hibernate 映射类型(前面对应java类型，后面对应sql类型). 
使用时间类型，一定要指定property的type属性。
```

## Java 大对象类型的Hiberante映射

```xml
在 Java 中, java.lang.String 可用于表示长字符串(长度超过 255),
字节数组 byte[] 可用于存放图片或文件的二进制数据. 此外, 在 JDBC API 中还提供了 java.sql.Clob 和 java.sql.Blob 类型, 它们分别和标准 SQL 中的 CLOB 和 BLOB 类型对应. CLOB 表示字符串大对象(Character Large Object), BLOB表示二进制对象(Binary Large Object)

Mysql 不支持标准 SQL 的 CLOB 类型, 在 Mysql 中, 用 TEXT, MEDIUMTEXT 及 LONGTEXT 类型来表示长度操作 255 的长文本数据

如果需要精确映射需要使用
 <!-- 映射大对象 -->
        <!-- 若希望精确映射 SQL 类型, 可以使用 sql-type 属性. -->
        <property name="content">
        	<column name="CONTENT" sql-type="mediumtext"></column>
        </property>
```

Blob 的使用

```java
@Test
	public void testBlob() throws Exception{
 //      保存       
//		News news = new News();
//		news.setAuthor("cc");
//		news.setContent("CONTENT");
//		news.setDate(new Date());
//		news.setDesc("DESC");
//		news.setTitle("CC");
//		
//		InputStream stream = new FileInputStream("Hydrangeas.jpg");
//		Blob image = Hibernate.getLobCreator(session)
//				              .createBlob(stream, stream.available());
//		news.setImage(image);
//		
//		session.save(news);
		
		News news = (News) session.get(News.class, 1);
		Blob image = news.getImage();
		
		InputStream in = image.getBinaryStream();
		System.out.println(in.available()); 
	}
```



## 映射组成关系

![JAVA_HIBERNATE17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE17.png?raw=true)

```java
public class Worker {	
	private Integer id;
	private String name;
	private Pay pay;
}

public class Pay {	
	private int monthlyPay;
	private int yearPay;
	private int vocationWithPay;
	
	private Worker worker;
}

映射文件：
<hibernate-mapping package="com.atguigu.hibernate.entities">
    <class name="Worker" table="WORKER">
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <!-- 映射组成关系 -->
        <component name="pay" class="Pay">
        	<parent name="worker"/>
        	<!-- 指定组成关系的组件的属性 -->
        	<property name="monthlyPay" column="MONTHLY_PAY"></property>
        	<property name="yearPay" column="YEAR_PAY"></property>
        	<property name="vocationWithPay" column="VOCATION_WITH_PAY"></property>
        </component>
        
    </class>
</hibernate-mapping>

Hibernate 把持久化类的属性分为两种: 
值(value)类型: 没有 OID, 不能被单独持久化, 生命周期依赖于所属的持久化类的对象的生命周期
实体(entity)类型: 有 OID, 可以被单独持久化, 有独立的生命周期
显然无法直接用 property 映射 pay 属性
Hibernate 使用 <component> 元素来映射组成关系, 该元素表名 pay 属性是 Worker 类一个组成部分, 在 Hibernate 中称之为组件


<component> 元素来映射组成关系
class:设定组成关系属性的类型, 此处表明 pay 属性为 Pay 类型
<parent> 元素指定组件属性所属的整体类
name: 整体类在组件类中的属性名
```



## 映射关联关系

### 一对多关联关系

![JAVA_HIBERNATE18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE18.png?raw=true)

#### 单向 n-1

![JAVA_HIBERNATE19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE19.png?raw=true)

```java
public class Customer {
	private Integer customerId;
	private String customerName;
}

<hibernate-mapping>  
    <class name="com.atguigu.hibernate.entities.n21.Customer" table="CUSTOMERS">   
        <id name="customerId" type="java.lang.Integer">
            <column name="CUSTOMER_ID" />
            <generator class="native" />
        </id>
    
        <property name="customerName" type="java.lang.String">
            <column name="CUSTOMER_NAME" />
        </property>      
    </class>   
</hibernate-mapping>


public class Order {
	private Integer orderId;
	private String orderName;
	
	private Customer customer;
}	

<hibernate-mapping package="com.atguigu.hibernate.entities.n21">

    <class name="Order" table="ORDERS">

        <id name="orderId" type="java.lang.Integer">
            <column name="ORDER_ID" />
            <generator class="native" />
        </id>
        
        <property name="orderName" type="java.lang.String">
            <column name="ORDER_NAME" />
        </property>
        
		<!-- 
			映射多对一的关联关系。 使用 many-to-one 来映射多对一的关联关系 
			name: 多这一端关联的一那一端的属性的名字
			class: 一那一端的属性对应的类名
			column: 一那一端在多的一端对应的数据表中的外键的名字
			多对一，在多的这边会存在对应的外键
		-->
		<many-to-one name="customer" class="Customer" column="CUSTOMER_ID"></many-to-one>

    </class>
</hibernate-mapping>

```

多对一关联测试：

```java
 @Test
	public void testMany2OneSave(){
		Customer customer = new Customer();
		customer.setCustomerName("BB");
		
		Order order1 = new Order();
		order1.setOrderName("ORDER-3");
		
		Order order2 = new Order();
		order2.setOrderName("ORDER-4");
		
		//设定关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//执行  save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT
		//先插入 1 的一端, 再插入 n 的一端, 只有 INSERT 语句.
//		session.save(customer);
//		
//		session.save(order1);
//		session.save(order2);
		
		//先插入 Order, 再插入 Customer. 3 条 INSERT, 2 条 UPDATE
		//先插入 n 的一端, 再插入 1 的一端, 会多出 UPDATE 语句!
		//因为在插入多的一端时, 无法确定 1 的一端的外键值. 所以只能等 1 的一端插入后, 再额外发送 UPDATE 语句. (这种方式会导致多2条sql，效率变低)
		//推荐先插入 1 的一端, 后插入 n 的一端
		session.save(order1); 
        // 在插入order的时候，会插入customer，但是这时不知道customer是什么，所以先插入空，后面再update
		session.save(order2);
		
		session.save(customer);
	}


@Test
	public void testMany2OneGet(){
		//1. 若查询多的一端的一个对象, 则默认情况下, 只查询了多的一端的对象. 而没有查询关联的
		//1 的那一端的对象! (延迟加载)
		Order order = (Order) session.get(Order.class, 1);
		System.out.println(order.getOrderName()); 
        //问题： 在查询order的时候，被将关联的custom查询出来吗？ 
		
		System.out.println(order.getCustomer().getClass().getName());
		
		session.close();
		
		//2. 在需要使用到关联的对象时, 才发送对应的 SQL 语句. 
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomerName()); 
		
		//3. 在查询 Customer 对象时, 由多的一端导航到 1 的一端时, 
		//若此时 session 已被关闭, 则默认情况下
		//会发生 LazyInitializationException 异常
		
		//4. 获取 Order 对象时, 默认情况下, 其关联的 Customer 对象是一个代理对象!
		
	}

	@Test
//级联删除
	public void testDelete(){
		// 1 这一端的对象有 n 的对象在引用, 不能直接删除 1 这一端的对象
		Customer customer = (Customer) session.get(Customer.class, 1);
		session.delete(customer); 
	}
```

#### 双向 1-n

![JAVA_HIBERNATE20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE20.png?raw=true)

```
双向 1-n 与 双向 n-1 是完全相同的两种情形。 双向 1-n 需要在 1 的一端可以访问 n 的一端
```

代码实现：



```java
 public class Customer {
	private Integer customerId;
	private String customerName;
	
	/*
	 * 1. 声明集合类型时, 需使用接口类型, 因为 hibernate 在获取
	 * 集合类型时, 返回的是 Hibernate 内置的集合类型, 而不是 JavaSE 一个标准的
	 * 集合实现. 
	 * 2. 需要把集合进行初始化, 可以防止发生空指针异常
	 */
	private Set<Order> orders = new HashSet<>();
     
     当 Session 从数据库中加载 Java 集合时, 创建的是 Hibernate 内置集合类的实例, 因此在持久化类中定义集合属性时必须把属性声明为 Java 接口类型
Hibernate 的内置集合类具有集合代理功能, 支持延迟检索策略
事实上, Hibernate 的内置集合类封装了 JDK 中的集合类, 这使得 Hibernate 能够对缓存中的集合对象进行脏检查, 按照集合对象的状态来同步更新数据库。

}

<hibernate-mapping package="com.atguigu.hibernate.entities.n21.both">   
    <class name="Customer" table="CUSTOMERS">
    
        <id name="customerId" type="java.lang.Integer">
            <column name="CUSTOMER_ID" />
            <generator class="native" />
        </id>
    
        <property name="customerName" type="java.lang.String">
            <column name="CUSTOMER_NAME" />
        </property>
        
        <!-- 映射 1 对多的那个集合属性 -->
        <!-- set: 映射 set 类型的属性, table: set 中的元素对应的记录放在哪一个数据表中. 该值需要和多对一的多的那个表的名字一致 -->
        <!-- inverse: 指定由哪一方来维护关联关系. 通常设置为 true, 以指定由多的一端来维护关联关系 -->
        <!-- cascade 设定级联操作. 开发时不建议设定该属性. 建议使用手工的方式来处理 -->
        <!-- order-by 在查询时对集合中的元素进行排序, order-by 中使用的是表的字段名, 而不是持久化类的属性名  -->
        <set name="orders" table="ORDERS" inverse="true" order-by="ORDER_NAME DESC">
        	<!-- 执行多的表中的外键列的名字 -->
        	<key column="CUSTOMER_ID"></key>
        	<!-- 指定映射类型 -->
        	<one-to-many class="Order"/>
        </set>
        
    </class>
    
</hibernate-mapping>



public class Order {
	private Integer orderId;
	private String orderName;
	
	private Customer customer;
}	

<hibernate-mapping package="com.atguigu.hibernate.entities.n21.both">

    <class name="Order" table="ORDERS">

        <id name="orderId" type="java.lang.Integer">
            <column name="ORDER_ID" />
            <generator class="native" />
        </id>
        
        <property name="orderName" type="java.lang.String">
            <column name="ORDER_NAME" />
        </property>
        
		<!-- 
			映射多对一的关联关系。 使用 many-to-one 来映射多对一的关联关系 
			name: 多这一端关联的一那一端的属性的名字
			class: 一那一端的属性对应的类名
			column: 一那一端在多的一端对应的数据表中的外键的名字
		-->
		<many-to-one name="customer" class="Customer" column="CUSTOMER_ID"></many-to-one>

    </class>
</hibernate-mapping>

```

![JAVA_HIBERNATE21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE21.png?raw=true)

双向测试

```java
@Test
	public void testMany2OneSave(){
		Customer customer = new Customer();
		customer.setCustomerName("AA");
		
		Order order1 = new Order();
		order1.setOrderName("ORDER-1");
		
		Order order2 = new Order();
		order2.setOrderName("ORDER-2");
		
		//设定关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
        
         session.save(customer);
		session.save(order1);
		session.save(order2);
		
		//执行  save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT, 2 条 UPDATE
		//因为 1 的一端和 n 的一端都维护关联关系. 所以会多出 UPDATE
		//可以在 1 的一端的 set 节点指定 inverse=true, 来使 1 的一端放弃维护关联关系!
		//建议设定 set 的 inverse=true, 建议先插入 1 的一端, 后插入多的一端
		//好处是不会多出 UPDATE 语句

		
		//先插入 Order, 再插入 Cusomer, 3 条 INSERT, 4 条 UPDATE
//		session.save(order1);
//		session.save(order2);
//		
//		session.save(customer);
	}
在hibernate中通过对 inverse 属性的来决定是由双向关联的哪一方来维护表和表之间的关系. inverse = false 的为主动方，inverse = true 的为被动方(放弃维护关联关系), 由主动方负责维护关联关系.在没有设置 inverse=true 的情况下，父子两边都维护父子关系 

在双向 1-n 关系中，将 n 方设为主控方将有助于性能改善(如果要国家元首记住全国人民的名字，不是太可能，但要让全国人民知道国家元首，就容易的多)在 1-N 关系中，若将 1 方设为主控方,会额外多出 update 语句。

@Test
	public void testOne2ManyGet(){
		//1. 对 n 的一端的集合使用延迟加载
		Customer customer = (Customer) session.get(Customer.class, 7);
		System.out.println(customer.getCustomerName()); 
		//2. 返回的多的一端的集合时 Hibernate 内置的集合类型. 
		//该类型具有延迟加载和存放代理对象的功能. 
		System.out.println(customer.getOrders().getClass()); 
		
		//session.close();
		//3. 可能会抛出 LazyInitializationException 异常 
		
		System.out.println(customer.getOrders().size()); 
		
		//4. 再需要使用集合中元素的时候进行初始化. 
	}
	
	
```



### 一对一关联关系

![JAVA_HIBERNATE22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE22.png?raw=true)

#### 外键映射

```java
public class Manager {
	private Integer mgrId;
	private String mgrName;
	
	private Department dept;
}

<hibernate-mapping>

    <class name="com.atguigu.hibernate.one2one.foreign.Manager" table="MANAGERS">
        
        <id name="mgrId" type="java.lang.Integer">
            <column name="MGR_ID" />
            <generator class="native" />
        </id>
        
        <property name="mgrName" type="java.lang.String">
            <column name="MGR_NAME" />
        </property>
        
        <!-- 映射 1-1 的关联关系: 在对应的数据表中已经有外键了, 当前持久化类使用 one-to-one 进行映射 -->
        <!-- 
        	没有外键的一端需要使用one-to-one元素，该元素使用 property-ref 属性指定使用被关联实体主键以外的字段作为关联字段
         -->
        <one-to-one name="dept" 
        	class="com.atguigu.hibernate.one2one.foreign.Department"
        	property-ref="mgr"></one-to-one>
        
    </class>
    
</hibernate-mapping>


public class Department {

	private Integer deptId;
	private String deptName;
	
	private Manager mgr;
}

<hibernate-mapping>

    <class name="com.atguigu.hibernate.one2one.foreign.Department" table="DEPARTMENTS">

        <id name="deptId" type="java.lang.Integer">
            <column name="DEPT_ID" />
            <generator class="native" />
        </id>
        
        <property name="deptName" type="java.lang.String">
            <column name="DEPT_NAME" />
        </property>
		
		<!-- 使用 many-to-one 的方式来映射 1-1 关联关系 , 外键--> 
		<many-to-one name="mgr" class="com.atguigu.hibernate.one2one.foreign.Manager" 
			column="MGR_ID" unique="true"></many-to-one>	        
			        
    </class>
</hibernate-mapping>


```

测试

```java
	@Test
	public void testSave(){
		
		Department department = new Department();
		department.setDeptName("DEPT-BB");
		
		Manager manager = new Manager();
		manager.setMgrName("MGR-BB");
		
		//设定关联关系
		department.setMgr(manager);
		manager.setDept(department);
		
		//保存操作
		//建议先保存没有外键列的那个对象. 这样会减少 UPDATE 语句 ，外键，多对以，如果先保存department，需要去更新其manager_id ，所以会多一条sql
		session.save(department);
		session.save(manager);
		
	}

	
	@Test
	public void testGet(){
		//1. 默认情况下对关联属性使用懒加载
		Department dept = (Department) session.get(Department.class, 1);
		System.out.println(dept.getDeptName()); 
		
		//2. 所以会出现懒加载异常的问题. 
//		session.close();
//		Manager mgr = dept.getMgr();
//		System.out.println(mgr.getClass()); 
//		System.out.println(mgr.getMgrName()); 
		
		//3. 查询 Manager 对象的(左外连接)连接条件应该是 dept.manager_id = mgr.manager_id
		//而不应该是 dept.dept_id = mgr.manager_id ，这是因为在one-to-one 少设置了一个属性property-ref="mgr"
		Manager mgr = dept.getMgr();
		System.out.println(mgr.getMgrName()); 
		
	}


@Test
	public void testGet2(){
		//在查询没有外键的实体对象时, 使用的左外连接查询, 一并查询出其关联的对象
		//并已经进行初始化. 
		Manager mgr = (Manager) session.get(Manager.class, 1);
		System.out.println(mgr.getMgrName()); 
		System.out.println(mgr.getDept().getDeptName()); 
	}

```

#### 主键映射

基于主键的映射策略:指一端的主键生成器使用 foreign 策略,表明根据”对方”的主键来生成自己的主键，自己并不能独立生成主键. <param> 子元素指定使用当前持久化类的哪个属性作为 “对方”

```java
public class Manager {
	private Integer mgrId;
	private String mgrName;
	
	private Department dept;
}

<hibernate-mapping>
    <class name="com.atguigu.hibernate.one2one.primary.Manager" table="MANAGERS">  
        <id name="mgrId" type="java.lang.Integer">
            <column name="MGR_ID" />
            <generator class="native" />
        </id>
        
        <property name="mgrName" type="java.lang.String">
            <column name="MGR_NAME" />
        </property>
        
        <one-to-one name="dept" 
        	class="com.atguigu.hibernate.one2one.primary.Department"></one-to-one>  
    </class>
</hibernate-mapping>


public class Department {

	private Integer deptId;
	private String deptName;
	
	private Manager mgr;
}

<hibernate-mapping package="com.atguigu.hibernate.one2one.primary">

    <class name="Department" table="DEPARTMENTS">

        <id name="deptId" type="java.lang.Integer">
            <column name="DEPT_ID" />
            <!-- 使用外键的方式来生成当前的主键 -->
            <generator class="foreign">
            	<!-- property 属性指定使用当前持久化类的哪一个属性的主键作为外键 -->
            	<param name="property">mgr</param>
            </generator>
        </id>
        
        <property name="deptName" type="java.lang.String">
            <column name="DEPT_NAME" />
        </property>
		
		<!--  
		采用 foreign 主键生成器策略的一端增加 one-to-one 元素映射关联属性,
		其 one-to-one 节点还应增加 constrained=true 属性, 以使当前的主键上添加外键约束
		-->
		<one-to-one name="mgr" class="Manager" constrained="true"></one-to-one>
					        
    </class>
</hibernate-mapping>


```

测试

```java
@Test
	public void testSave(){
		
		Department department = new Department();
		department.setDeptName("DEPT-DD");
		
		Manager manager = new Manager();
		manager.setMgrName("MGR-DD");
		
		//设定关联关系
		manager.setDept(department);
		department.setMgr(manager);
		
		//保存操作
		//先插入哪一个都不会有多余的 UPDATE
		session.save(department);
		session.save(manager);
		
	}

@Test
	public void testGet(){
		//1. 默认情况下对关联属性使用懒加载
		Department dept = (Department) session.get(Department.class, 1);
		System.out.println(dept.getDeptName()); 
		
		//2. 所以会出现懒加载异常的问题. 
		Manager mgr = dept.getMgr();
		System.out.println(mgr.getMgrName()); 
	}

@Test
	public void testGet2(){
		//在查询没有外键的实体对象时, 使用的左外连接查询, 一并查询出其关联的对象
		//并已经进行初始化. 
		Manager mgr = (Manager) session.get(Manager.class, 1);
		System.out.println(mgr.getMgrName()); 
		System.out.println(mgr.getDept().getDeptName()); 
	}
```

## 多对多关联关系

![JAVA_HIBERNATE23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE23.png?raw=true)

![JAVA_HIBERNATE24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE24.png?raw=true)

```
n-n 的关联必须使用连接表
与 1-n 映射类似，必须为 set 集合元素添加 key 子元素，指定 CATEGORIES_ITEMS 表中参照 CATEGORIES 表的外键为 CATEGORIY_ID
```

代码实现

```java
public class Category {

	private Integer id;
	private String name;
	
	private Set<Item> items = new HashSet<>();
}
<hibernate-mapping package="com.atguigu.hibernate.n2n">

    <class name="Category" table="CATEGORIES">
    
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
    
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <!-- table: 指定中间表 -->
        <set name="items" table="CATEGORIES_ITEMS">
            <key>
                <column name="C_ID" />
            </key>
            <!-- 使用 many-to-many 指定多对多的关联关系. column 执行 Set 集合中的持久化类在中间表的外键列的名称  -->
            <many-to-many class="Item" column="I_ID"></many-to-many>
        </set>
        
    </class>
</hibernate-mapping>

public class Item {
	private Integer id;
	private String name;
	private Set<Category> categories = new HashSet<>();
}
<hibernate-mapping>

    <class name="com.atguigu.hibernate.n2n.Item" table="ITEMS">
        
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
        
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <!--双向多对多才需要都写-->
        <set name="categories" table="CATEGORIES_ITEMS" inverse="true">
        	<key column="I_ID"></key>
        	<many-to-many class="com.atguigu.hibernate.n2n.Category" column="C_ID"></many-to-many>
        </set>
        
    </class>
</hibernate-mapping>

```

测试

```java
@Test
	public void testSave(){
		Category category1 = new Category();
		category1.setName("C-AA");

		Category category2 = new Category();
		category2.setName("C-BB");
		
		Item item1 = new Item();
		item1.setName("I-AA");
		
		Item item2 = new Item();
		item2.setName("I-BB");
		
		//设定关联关系
		category1.getItems().add(item1);
		category1.getItems().add(item2);
		
		category2.getItems().add(item1);
		category2.getItems().add(item2);
		
		item1.getCategories().add(category1);
		item1.getCategories().add(category2);
		
		item2.getCategories().add(category1);
		item2.getCategories().add(category2);
		
		//执行保存操作
		session.save(category1);
		session.save(category2);
		
		session.save(item1);
		session.save(item2);
	}

@Test
	public void testGet(){
		Category category = (Category) session.get(Category.class, 1);
		System.out.println(category.getName()); 
		
		//需要内连接中间表
		Set<Item> items = category.getItems();
		System.out.println(items.size()); 
	}
```

双向n-n

```xml
双向n-n和单向类似，不同的是，两个hbm.xml中的都需要配置set
双向 n-n 关联需要两端都使用集合属性
双向n-n关联必须使用连接表

在双向 n-n 关联的两边都需指定连接表的表名及外键列的列名. 两个集合元素 set 的 table 元素的值必须指定，而且必须相同。set元素的两个子元素：key 和 many-to-many 都必须指定 column 属性，其中，key 和 many-to-many 分别指定本持久化类和关联类在连接表中的外键列名，因此两边的 key 与 many-to-many 的column属性交叉相同。也就是说，一边的set元素的key的 cloumn值为a,many-to-many 的 column 为b；则另一边的 set 元素的 key 的 column 值 b,many-to-many的 column 值为 a.  
 <!-- catolog -->
        <set name="items" table="CATEGORIES_ITEMS">
            <key>
                <column name="C_ID" />
            </key>
            <many-to-many class="Item" column="I_ID"></many-to-many>
        </set>

 <!--Item-->
        <set name="categories" table="CATEGORIES_ITEMS" inverse="true">
        	<key column="I_ID"></key>
        	<many-to-many class="com.atguigu.hibernate.n2n.Category" column="C_ID"></many-to-many>
        </set>


对于双向 n-n 关联, 必须把其中一端的 inverse 设置为 true, 否则两端都维护关联关系可能会造成主键冲突.
```

## 映射继承关系

![JAVA_HIBERNATE25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE25.png?raw=true)

如果要使用多态查询，前提是必须先使用继承映射

### 使用 subclass 进行映射

```
采用 subclass 的继承映射可以实现对于继承关系中父类和子类使用同一张表
```

 ![JAVA_HIBERNATE26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE26.png?raw=true)

代码实现

```java
public class Person {	
	private Integer id;
	private String name;
	private int age;
	}

public class Student extends Person{
	private String school;
}
<hibernate-mapping package="com.atguigu.hibernate.subclass">

    <class name="Person" table="PERSONS" discriminator-value="PERSON">

        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
        
        <!-- 配置辨别者列 -->
		<discriminator column="TYPE" type="string"></discriminator>

        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <property name="age" type="int">
            <column name="AGE" />
        </property>
        
        <!-- 映射子类 Student, 使用 subclass 进行映射 -->
        <subclass name="Student" discriminator-value="STUDENT">
        	<property name="school" type="string" column="SCHOOL"></property>
        </subclass>
        
    </class>
</hibernate-mapping>
```

测试

```java
/**
	 * 插入操作: 
	 * 1. 对于子类对象只需把记录插入到一张数据表中.
	 * 2. 辨别者列有 Hibernate 自动维护. 
	 */
	@Test
	public void testSave(){
		
		Person person = new Person();
		person.setAge(11);
		person.setName("AA");
		
		session.save(person);
		
		Student stu = new Student();
		stu.setAge(22);
		stu.setName("BB");
		stu.setSchool("ATGUIGU");
		
		session.save(stu);	
	}

/**
	 * 缺点:
	 * 1. 使用了辨别者列.
	 * 2. 子类独有的字段不能添加非空约束.
	 * 3. 若继承层次较深, 则数据表的字段也会较多. 
	 */
	
	/**
	 * 查询:
	 * 1. 查询父类记录, 只需要查询一张数据表
	 * 2. 对于子类记录, 也只需要查询一张数据表
	 */
	@Test
	public void testQuery(){
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size()); 
		
		List<Student> stus = session.createQuery("FROM Student").list();
		System.out.println(stus.size()); 
	}
```



### 使用 joined-subclass 进行映射

```
采用 joined-subclass 元素的继承映射可以实现每个子类一张表，子类实例由父类表和子类表共同存储。
但需要为每个子类使用 key 元素映射共有主键。但是相比subclass,子类增加的属性可以添加非空约束。
```

![JAVA_HIBERNATE27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE27.png?raw=true)

代码实现

```java
public class Person {	
	private Integer id;
	private String name;
	private int age;
	}

public class Student extends Person{
	private String school;
}
<hibernate-mapping package="com.atguigu.hibernate.joined.subclass">

    <class name="Person" table="PERSONS">

        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
        
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <property name="age" type="int">
            <column name="AGE" />
        </property>
        
        <joined-subclass name="Student" table="STUDENTS">
        	<key column="STUDENT_id"></key> //映射主键
        	<property name="school" type="string" column="SCHOOL"></property>
        </joined-subclass>
        
    </class>
</hibernate-mapping>
```

 测试

```java
/**
	 * 插入操作: 
	 * 1. 对于子类对象至少需要插入到两张数据表中. 
	 */
	@Test
	public void testSave(){
		
		Person person = new Person();
		person.setAge(11);
		person.setName("AA");
		
		session.save(person);
		
		Student stu = new Student();
		stu.setAge(22);
		stu.setName("BB");
		stu.setSchool("ATGUIGU");
		
		session.save(stu);
		
	}

/**
	 * 优点:
	 * 1. 不需要使用了辨别者列.
	 * 2. 子类独有的字段能添加非空约束.
	 * 3. 没有冗余的字段. 
	 */
	
	/**
	 * 查询:
	 * 1. 查询父类记录, 做一个左外连接查询
	 * 2. 对于子类记录, 做一个内连接查询. 
	 */
	@Test
	public void testQuery(){
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size()); 
		
		List<Student> stus = session.createQuery("FROM Student").list();
		System.out.println(stus.size()); 
	}
// 查询和插入的效率较低
```



### 使用  union-subclass 进行映射

```
将每一个实体对象映射到一个独立的表中。子类增加的属性可以有非空约束
既不需要使用鉴别者列，也无须使用 key 元素来映射共有主键.
使用 union-subclass 映射策略是不可使用 identity 的主键生成策略
```

代码实现

```java
public class Person {	
	private Integer id;
	private String name;
	private int age;
	}

public class Student extends Person{
	private String school;
}

<hibernate-mapping package="com.atguigu.hibernate.union.subclass">

    <class name="Person" table="PERSONS">

        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="hilo" /> 
            // 使用 union-subclass 映射策略是不可使用 identity 的主键生成策略
        </id>
        
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <property name="age" type="int">
            <column name="AGE" />
        </property>
	
		<union-subclass name="Student" table="STUDENTS">
			<property name="school" column="SCHOOL" type="string"></property>
		</union-subclass>        
        
    </class>
</hibernate-mapping>
```

 测试

```java
/**
	 * 优点:
	 * 1. 无需使用辨别者列.
	 * 2. 子类独有的字段能添加非空约束.
	 * 
	 * 缺点:
	 * 1. 存在冗余的字段
	 * 2. 若更新父表的字段, 则更新的效率较低
	 */
	
	/**
	 * 查询:
	 * 1. 查询父类记录, 需把父表和子表记录汇总到一起再做查询. 性能稍差. 
	 * 2. 对于子类记录, 也只需要查询一张数据表
	 */
	@Test
	public void testQuery(){
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size()); 
		
		List<Student> stus = session.createQuery("FROM Student").list();
		System.out.println(stus.size()); 
	}
	
	/**
	 * 插入操作: 
	 * 1. 对于子类对象只需把记录插入到一张数据表中.
	 */
	@Test
	public void testSave(){
		
		Person person = new Person();
		person.setAge(11);
		person.setName("AA");
		
		session.save(person);
		
		Student stu = new Student();
		stu.setAge(22);
		stu.setName("BB");
		stu.setSchool("ATGUIGU");
		
		session.save(stu);	
	}

```



## Hibernate 检索策略

![JAVA_HIBERNATE28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE28.png?raw=true)

```
我们在检索数据时需要注意两个问题：
1）不浪费内存：当 Hibernate 从数据库中加载 Customer 对象时, 如果同时加载所有关联的 Order 对象, 而程序实际上仅仅需要访问 Customer 对象, 那么这些关联的 Order 对象就白白浪费了许多内存.
2）更高的查询效率：发送尽可能少的 SQL 语句
```

### 类级别的检索策略

```
类级别可选的检索策略
立即检索: 立即加载检索方法指定的对象
延迟检索: 延迟加载检索方法指定的对象。在使用具体的属性时，再进行加载
类级别的检索策略可以通过 <class> 元素的 lazy 属性进行设置 (无论 <class> 元素的 lazy 属性是 true 还是 false, Session 的 get() 方法及 Query 的 list() 方法在类级别总是使用立即检索策略, 他仅仅对load（）有效
)
如果程序加载一个对象的目的是为了访问它的属性, 可以采取立即检索. 
如果程序加载一个持久化对象的目的是仅仅为了获得它的引用, 可以采用延迟检索。注意出现懒加载异常！
```



### 一对一，一对多的检索策略

```
一对一，一对多 主要针对<set>
<set> 元素有 lazy 和 fetch 属性

lazy: 主要决定 orders 集合被初始化的时机. 即到底是在加载 Customer 对象时就被初始化, 还是在程序访问 orders 集合时被初始化

fetch: 取值为 “select” 或 “subselect” 时, 决定初始化 orders 的查询语句的形式;  若取值为”join”, 则决定 orders 集合被初始化的时机若把 fetch 设置为 “join”, lazy 属性将被忽略

<set> 元素的 batch-size 属性：用来为延迟检索策略或立即检索策略设定批量检索的数量. 批量检索能减少 SELECT 语句的数目, 提高延迟检索或立即检索的运行性能. 
```

测试

```java
public void testOne2ManyLevelStrategy(){
		Customer customer = (Customer) session.get(Customer.class, 1);
		System.out.println(customer.getCustomerName()); 
		
		System.out.println(customer.getOrders().size());
		Order order = new Order();
		order.setOrderId(1);
		System.out.println(customer.getOrders().contains(order));
		
		Hibernate.initialize(customer.getOrders()); //通过 Hibernate.initialize() 静态方法显式初始化

		
		//---------------set 的 lazy 属性------------------
		//1. 1-n 或 n-n 的集合属性默认使用懒加载检索策略.
		//2. 可以通过设置 set 的 lazy 属性来修改默认的检索策略. 默认为 true，并不建议设置为  false. 
		//3. lazy 还可以设置为 extra. 增强的延迟检索. 该取值会尽可能的延迟集合初始化的时机!
	}


@Test
	public void testSetFetch(){
        //fetch: 取值为 “select” 或 “subselect” 时, 决定初始化 orders 的查询语句的形式,join决定初始化orders的时机
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size()); 
		
		for(Customer customer: customers){
			if(customer.getOrders() != null)
				System.out.println(customer.getOrders().size());
		}
		
		//set 集合的 fetch 属性: 确定初始化 orders 集合的方式. 
		//1. 默认值为 select（抓取）. 通过正常的方式来初始化 set 元素
		//2. 可以取值为 subselect. 通过子查询的方式来初始化所有的 set 集合. 子查询
	//作为 where 子句的 in 的条件出现, 子查询查询所有 1 的一端的 ID. 此时 lazy 有效.但 batch-size 失效. 
		
        //3. 若取值为 join. 则
	//3.1 在加载 1 的一端的对象时, 使用迫切左外连接(使用左外链接进行查询, 且把集合属性进行初始化)的方式检索 n 的一端的集合属性
		//3.2 忽略 lazy 属性.
		//3.3 HQL 查询忽略 fetch=join 的取值
	}


@Test
	public void testSetBatchSize(){
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size()); 
		
		for(Customer customer: customers){
			if(customer.getOrders() != null)
				System.out.println(customer.getOrders().size());
		}
		
		//set 元素的 batch-size 属性: 设定一次初始化 set 集合的数量. 
        //他的原理是in sql 语句
	}
```



### 多对一和一对一的检索策略

```java
@Test
	public void testMany2OneStrategy(){
//		Order order = (Order) session.get(Order.class, 1);
//		System.out.println(order.getCustomer().getCustomerName()); 
		
		List<Order> orders = session.createQuery("FROM Order o").list();
		for(Order order: orders){
			if(order.getCustomer() != null){
				System.out.println(order.getCustomer().getCustomerName()); 
			}
		}
		
		//1. lazy 取值为 proxy 和 false 分别代表对应对应的属性采用延迟检索和立即检索
		//2. fetch 取值为 join, 表示使用迫切左外连接的方式初始化 n 关联的 1 的一端的属性
		//忽略 lazy 属性. 
		//3. batch-size, 该属性需要设置在 1 那一端的 class 元素中: 
		//<class name="Customer" table="CUSTOMERS" lazy="true" batch-size="5">
		//作用: 一次初始化 1 的这一段代理对象的个数. 
	}
```



## HQL查询

```java
HQL（Hibernate Query Language） 检索方式: 使用面向对象的 HQL 查询语言
它有如下功能:
在查询语句中设定各种查询条件
支持投影查询, 即仅检索出对象的部分属性
支持分页查询
支持连接查询
支持分组查询, 允许使用 HAVING 和 GROUP BY 关键字
提供内置聚集函数, 如 sum(), min() 和 max()
支持子查询
支持动态绑定参数
能够调用 用户定义的 SQL 函数或标准的 SQL 函数

HQL 检索方式包括以下步骤:
1.通过 Session 的 createQuery() 方法创建一个 Query 对象, 它包括一个 HQL 查询语句. HQL 查询语句中可以包含命名参数
2.动态绑定参数
3.调用 Query 相关方法执行查询语句. 

	@Test
	public void testHQL(){	
		//1. 创建 Query 对象
		//基于位置的参数. 
		String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? "
				+ "ORDER BY e.salary";
		Query query = session.createQuery(hql);
		
		//2. 绑定参数
		//Query 对象调用 setXxx 方法支持方法链的编程风格.
		Department dept = new Department();
		dept.setId(80); 
		query.setFloat(0, 6000)
		     .setString(1, "%A%")
		     .setEntity(2, dept);
		
		//3. 执行查询
		List<Employee> emps = query.list();
		System.out.println(emps.size());  
	}

HQL vs SQL:
HQL 查询语句是面向对象的, Hibernate 负责解析 HQL 查询语句, 然后根据对象-关系映射文件中的映射信息, 把 HQL 查询语句翻译成相应的 SQL 语句. HQL 查询语句中的主体是域模型中的类及类的属性
SQL 查询语句是与关系数据库绑定在一起的. SQL 查询语句中的主体是数据库表及表的字段. 
```

实例基础

Department （1） ——————》 Employee（n）

```java
public class Employee {
	private Integer id;
	private String name;
	private float salary;
	private String email;
	
	private Department dept;
}
<hibernate-mapping>
    <class name="com.atguigu.hibernate.entities.Employee" table="GG_EMPLOYEE">  	
   	<!--  
    	<cache usage="read-write"/>
    	-->	
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
    
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <property name="salary" type="float">
            <column name="SALARY" />
        </property>
        
        <property name="email" type="java.lang.String">
            <column name="EMAIL" />
        </property>
        
        <many-to-one name="dept" class="com.atguigu.hibernate.entities.Department">
            <column name="DEPT_ID" />
        </many-to-one>
        
    </class>
    
    <query name="salaryEmps"><![CDATA[FROM Employee e WHERE e.salary > :minSal AND e.salary < :maxSal]]></query>
    
</hibernate-mapping>

public class Department {
	private Integer id;
	private String name;
	private Set<Employee> emps = new HashSet<>();
}

<hibernate-mapping>

    <class name="com.atguigu.hibernate.entities.Department" table="GG_DEPARTMENT">
        
        <id name="id" type="java.lang.Integer">
            <column name="ID" />
            <generator class="native" />
        </id>
    
        <property name="name" type="java.lang.String">
            <column name="NAME" />
        </property>
        
        <set name="emps" table="GG_EMPLOYEE" inverse="true" lazy="true">
            <key>
                <column name="DEPT_ID" />
            </key>
            <one-to-many class="com.atguigu.hibernate.entities.Employee" />
        </set>
        
    </class>
</hibernate-mapping>

```

绑定参数

```java
如何绑定参数
Hibernate 的参数绑定机制依赖于 JDBC API 中的 PreparedStatement 的预定义 SQL 语句功能.
HQL 的参数绑定由两种形式:
按参数名字绑定: 在 HQL 查询语句中定义命名参数, 命名参数以 “:” 开头.
    String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
按参数位置绑定: 在 HQL 查询语句中用 “?” 来定义参数位置
	String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? "
				+ "ORDER BY e.salary";
相关方法:
setEntity(): 把参数与一个持久化类绑定
	//基于位置的参数. 
		String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? "
				+ "ORDER BY e.salary";
		Query query = session.createQuery(hql);
		
		//2. 绑定参数
		//Query 对象调用 setXxx 方法支持方法链的编程风格.
		Department dept = new Department();
		dept.setId(80); 
		query.setFloat(0, 6000)
		     .setString(1, "%A%")
		     .setEntity(2, dept);
		
		//3. 执行查询
		List<Employee> emps = query.list();
setParameter(): 绑定任意类型的参数. 该方法的第三个参数显式指定 Hibernate 映射类型
		//1. 创建 Query 对象
		//基于命名参数. 
		String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
		Query query = session.createQuery(hql);
		
		//2. 绑定参数
		query.setFloat("sal", 7000)
		     .setString("email", "%A%");
		
		//3. 执行查询
		List<Employee> emps = query.list();
Qurey 接口支持方法链编程风格, 它的 setXxx() 方法返回自身实例, 而不是 void 类型
HQL 采用 ORDER BY 关键字对查询结果排序
```

### 分页查询

```java
分页查询: （因数据库的不同，分页不能完全统一DAO,但是HQL解决了这个问题）
setFirstResult(int firstResult): 设定从哪一个对象开始检索, 参数 firstResult 表示这个对象在查询结果中的索引位置, 索引位置的起始值为 0. 默认情况下, Query 从查询结果中的第一个对象开始检索

setMaxResults(int maxResults): 设定一次最多检索出的对象的数目. 在默认情况下, Query 和 Criteria 接口检索出查询结果中所有的对象

@Test
	public void testPageQuery(){
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);
		
		int pageNo = 3;
		int pageSize = 5;
		// 查询出第三页的分页数据
		List<Employee> emps = 
								query.setFirstResult((pageNo - 1) * pageSize)
								     .setMaxResults(pageSize)
								     .list();
		System.out.println(emps);
	}
```

### 在映射文件中定义命名查询语句

```java
Hibernate 允许在映射文件中定义字符串形式的查询语句.
<query> 元素用于定义一个 HQL 查询语句, 它和 <class> 元素并列
   <query name="salaryEmps"><![CDATA[FROM Employee e WHERE e.salary > :minSal AND e.salary < :maxSal]]></query>
  
  调用：
    在程序中通过 Session 的 getNamedQuery() 方法获取查询语句对应的 Query 对象. 
	@Test
	public void testNamedQuery(){
		Query query = session.getNamedQuery("salaryEmps");
		
		List<Employee> emps = query.setFloat("minSal", 5000)
				                   .setFloat("maxSal", 10000)
				                   .list();
		
		System.out.println(emps.size()); 
	}
好处是可以将我们的sql语句写在配置文件中，当我们需要去修改的时候，不用去动代码
```



### 投影查询

```java
查询结果仅包含实体的部分属性. 通过 SELECT 关键字实现.
Query 的 list() 方法返回的集合中包含的是数组类型的元素, 每个对象数组代表查询结果的一条记录
可以在持久化类中定义一个对象的构造器来包装投影查询返回的记录, 使程序代码能完全运用面向对象的语义来访问查询结果集. 
可以通过 DISTINCT 关键字来保证查询结果不会返回重复元素
	
	@Test
	public void testFieldQuery(){
		String hql = "SELECT e.email, e.salary, e.dept FROM Employee e WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		List<Object[]> result = query.setEntity("dept", dept)
				                     .list();
		
		for(Object [] objs: result){
			System.out.println(Arrays.asList(objs));
		}
	}
	

@Test
// 他需要一个带参的构造器 对应 new Employee(e.email, e.salary, e.dept)
	public void testFieldQuery2(){
		String hql = "SELECT new Employee(e.email, e.salary, e.dept) "
				+ "FROM Employee e "
				+ "WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		List<Employee> result = query.setEntity("dept", dept)
				                     .list();
		
		for(Employee emp: result){
			System.out.println(emp.getId() + ", " + emp.getEmail() 
					+ ", " + emp.getSalary() + ", " + emp.getDept());
		}
	}
```



### 报表查询

```java
报表查询用于对数据分组和统计, 与 SQL 一样, HQL 利用 GROUP BY 关键字对数据分组, 用 HAVING 关键字对分组数据设定约束条件.
在 HQL 查询语句中可以调用以下聚集函数
count()
min()
max()
sum()
avg()
总结： 就是可以在HQL中可以使用这些查询函数
@Test
	public void testGroupBy(){
		String hql = "SELECT min(e.salary), max(e.salary) "
				+ "FROM Employee e "
				+ "GROUP BY e.dept "
				+ "HAVING min(salary) > :minSal";
		
		Query query = session.createQuery(hql)
				             .setFloat("minSal", 8000);
		
		List<Object []> result = query.list();
		for(Object [] objs: result){
			System.out.println(Arrays.asList(objs));
		}
	}
```

### HQL (迫切)左外连接

```java
迫切左外连接:
LEFT JOIN FETCH 关键字表示迫切左外连接检索策略.
list() 方法返回的集合中存放实体对象的引用, 每个 Department 对象关联的 Employee  集合都被初始化, 存放所有关联的 Employee 的实体对象. 
查询结果中可能会包含重复元素, 可以通过一个 HashSet 来过滤重复元素
@Test
	public void testLeftJoinFetch(){
//		String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.emps";
		String hql = "FROM Department d INNER JOIN FETCH d.emps";
		Query query = session.createQuery(hql);
		
		List<Department> depts = query.list();
		depts = new ArrayList<>(new LinkedHashSet(depts));
		System.out.println(depts.size()); 
		
		for(Department dept: depts){
			System.out.println(dept.getName() + "-" + dept.getEmps().size());
		}
	}
	
左外连接:
LEFT JOIN 关键字表示左外连接查询. 
list() 方法返回的集合中存放的是对象数组类型
根据配置文件来决定 Employee 集合的检索策略. 
如果希望 list() 方法返回的集合中仅包含 Department 对象, 可以在HQL 查询语句中使用 SELECT 关键字
	@Test
	public void testLeftJoin(){
		String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
		Query query = session.createQuery(hql);
		
		List<Department> depts = query.list();
		System.out.println(depts.size());
		
		for(Department dept: depts){
			System.out.println(dept.getName() + ", " + dept.getEmps().size()); 
		}
		
//   	String hql = "FROM Department d LEFT JOIN d.emps";		
//		List<Object []> result = query.list(); 
//		result = new ArrayList<>(new LinkedHashSet<>(result));
//		System.out.println(result); 
//		
//		for(Object [] objs: result){
//			System.out.println(Arrays.asList(objs));
//		}
	}

总结，当我们使用左连接查询的时候，一般都是用的的是迫切左外连接
```

### HQL (迫切)内连接

```java
迫切内连接:
INNER JOIN FETCH 关键字表示迫切内连接, 也可以省略 INNER 关键字
list() 方法返回的集合中存放 Department 对象的引用, 每个 Department 对象的 Employee 集合都被初始化, 存放所有关联的 Employee 对象
内连接:
INNER JOIN 关键字表示内连接, 也可以省略 INNER 关键字
list() 方法的集合中存放的每个元素对应查询结果的一条记录, 每个元素都是对象数组类型
如果希望 list() 方法的返回的集合仅包含 Department  对象, 可以在 HQL 查询语句中使用 SELECT 关键字

@Test //(不返回不符合条件的记录)
	@Test
	public void testLeftJoinFetch(){
		String hql = "FROM Department d INNER JOIN FETCH d.emps";
		Query query = session.createQuery(hql);
		
		List<Department> depts = query.list();
		depts = new ArrayList<>(new LinkedHashSet(depts));
		System.out.println(depts.size()); 
		
		for(Department dept: depts){
			System.out.println(dept.getName() + "-" + dept.getEmps().size());
		}
	}
```

### 关联级别运行时的检索策略

```
如果在 HQL 中没有显式指定检索策略, 将使用映射文件配置的检索策略. 
HQL 会忽略映射文件中设置的迫切左外连接检索策略, 如果希望 HQL 采用迫切左外连接策略, 就必须在 HQL 查询语句中显式的指定它 (不常用)
若在 HQL 代码中显式指定了检索策略, 就会覆盖映射文件中配置的检索策略
```



## QBC 查询

```java
使用 QBC(Query By Criteria) API 来检索对象. 这种 API 封装了基于字符串形式的查询语句, 提供了更加面向对象的查询接口. 
	
	@Test
	public void testQBC(){
		//1. 创建一个 Criteria 对象
		Criteria criteria = session.createCriteria(Employee.class);
		
		//2. 添加查询条件: 在 QBC 中查询条件使用 Criterion 来表示
		//Criterion 可以通过 Restrictions 的静态方法得到
		criteria.add(Restrictions.eq("email", "SKUMAR"));
		criteria.add(Restrictions.gt("salary", 5000F));
		
		//3. 执行查询
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee); 
	}
	
@Test
	public void testQBC2(){
		Criteria criteria = session.createCriteria(Employee.class);
		
		//1. AND: 使用 Conjunction 表示
		//Conjunction 本身就是一个 Criterion 对象
		//且其中还可以添加 Criterion 对象
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("name", "a", MatchMode.ANYWHERE));
		Department dept = new Department();
		dept.setId(80);
		conjunction.add(Restrictions.eq("dept", dept));
		System.out.println(conjunction); 
		
		//2. OR 使用Disjunction 对象表示
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.ge("salary", 6000F));
		disjunction.add(Restrictions.isNull("email"));
		
		criteria.add(disjunction);
		criteria.add(conjunction);
		
		criteria.list();
	}


@Test
	public void testQBC3(){
		Criteria criteria = session.createCriteria(Employee.class);
		//统计查询: 使用 Projection 来表示: 可以由 Projections 的静态方法得到
		criteria.setProjection(Projections.max("salary"));
		
		System.out.println(criteria.uniqueResult()); 
	}

	@Test
	public void testQBC4(){
		Criteria criteria = session.createCriteria(Employee.class);
		
		//1. 添加排序
		criteria.addOrder(Order.asc("salary"));
		criteria.addOrder(Order.desc("email"));
		
		//2. 添加翻页方法
		int pageSize = 5;
		int pageNo = 3;
		criteria.setFirstResult((pageNo - 1) * pageSize)
		        .setMaxResults(pageSize)
		        .list();
	}
```



## 本地 SQL

```java
本地 SQL 检索方式: 使用本地数据库的 SQL 查询语句
本地SQL查询来完善HQL不能涵盖所有的查询特性(HQL 本身不支持INSERT into VALUES )
	@Test
	public void testNativeSQL(){
		String sql = "INSERT INTO gg_department VALUES(?, ?)";
		Query query = session.createSQLQuery(sql);
		
		query.setInteger(0, 280)
		     .setString(1, "ATGUIGU")
		     .executeUpdate();
	}

	@Test
	public void testHQLUpdate(){
		String hql = "DELETE FROM Department d WHERE d.id = :id";
		
		session.createQuery(hql).setInteger("id", 280)
		                        .executeUpdate();
	}
```



## 二级缓存

```java
一级缓存叫session缓存。
二级缓存也叫SessionFactory 级别的缓存。
	@Test
	public void testHibernateSecondLevelCache(){
		Employee employee = (Employee) session.get(Employee.class, 100);
		System.out.println(employee.getName()); 
		
		transaction.commit();
		session.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Employee employee2 = (Employee) session.get(Employee.class, 100);
		System.out.println(employee2.getName()); 
	}
session关闭了，所以这个时候这里会发送两条sql 语句。
如何只发送一条sql语句。 这时候就要使用我们的二级缓存。
二级别的缓存是 SessionFactory 级别的缓存，它是属于进程范围的缓存


SessionFactory 的缓存可以分为两类:
内置缓存: Hibernate 自带的, 不可卸载. 通常在 Hibernate 的初始化阶段, Hibernate 会把映射元数据和预定义的 SQL 语句放到 SessionFactory 的缓存中, 映射元数据是映射文件中数据（.hbm.xml 文件中的数据）的复制. 该内置缓存是只读的.  (存放hibernate配置文件配置的基础信息的缓存)
    
外置缓存(二级缓存): 一个可配置的缓存插件. 在默认情况下, SessionFactory 不会启用这个缓存插件. 外置缓存中的数据是数据库数据的复制, 外置缓存的物理介质可以是内存或硬盘

适合放入二级缓存中的数据:
很少被修改
不是很重要的数据, 允许出现偶尔的并发问题
不适合放入二级缓存中的数据:
经常被修改
财务数据, 绝对不允许出现并发问题
与其他应用程序共享的数据

```

![JAVA_HIBERNATE29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE29.png?raw=true)

从二级缓存架构图中我们可以看出，使用二级缓存需要第三方插件来提供。

```java
二级缓存可以设定以下 4 种类型的并发访问策略, 每一种访问策略对应一种事务隔离级别
非严格读写(Nonstrict-read-write): 不保证缓存与数据库中数据的一致性. 提供 Read Uncommited 事务隔离级别, 对于极少被修改, 而且允许脏读的数据, 可以采用这种策略

读写型(Read-write): 提供 Read Commited 数据隔离级别.对于经常读但是很少被修改的数据, 可以采用这种隔离类型, 因为它可以防止脏读

事务型(Transactional): 仅在受管理环境下适用. 它提供了 Repeatable Read 事务隔离级别. 对于经常读但是很少被修改的数据, 可以采用这种隔离类型, 因为它可以防止脏读和不可重复读

只读型(Read-Only):提供 Serializable 数据隔离级别, 对于从来不会被修改的数据, 可以采用这种访问策略
```



### 二级缓存的提供者

```java
Hibernate 的二级缓存是进程或集群范围内的缓存
二级缓存是可配置的的插件, Hibernate 允许选用以下类型的缓存插件:
EHCache(最常用，支持的缓存策略更多): 可作为进程范围内的缓存, 存放数据的物理介质可以使内存或硬盘, 对 Hibernate 的查询缓存提供了支持

OpenSymphony OSCache:可作为进程范围内的缓存, 存放数据的物理介质可以使内存或硬盘, 提供了丰富的缓存数据过期策略, 对 Hibernate 的查询缓存提供了支持

SwarmCache: 可作为集群范围内的缓存, 但不支持 Hibernate 的查询缓存

JBossCache:可作为集群范围内的缓存, 支持 Hibernate 的查询缓存
4 种缓存插件支持
```

![JAVA_HIBERNATE30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE30.png?raw=true)

### 使用二级缓存

```java
1. 使用 Hibernate 二级缓存的步骤:

1). 加入二级缓存插件的 jar 包及配置文件:

I. 复制 \hibernate-release-4.2.4.Final\lib\optional\ehcache\*.jar 到当前 Hibrenate 应用的类路径下.
II. 复制 hibernate-release-4.2.4.Final\project\etc\ehcachexml 到当前 WEB 应用的类路径下，根目录下

2). 配置 hibernate.cfg.xml 

I.   配置启用 hibernate 的二级缓存
<property name="cache.use_second_level_cache">true</property>

II.  配置hibernate二级缓存使用的产品 (参考文档配置properties文件)
<property name="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</property>

III. 配置对哪些类使用 hibernate 的二级缓存
<class-cache usage="read-write" class="com.atguigu.hibernate.entities.Employee"/>
		
实际上也可以在 .hbm.xml 文件中配置对哪些类使用二级缓存, 及二级缓存的策略是什么.
    	<cache usage="read-write"/>


2). 集合级别的二级缓存的配置

I. 配置对集合使用二级缓存 ，使用集合缓存，我们不但要让集合使用二级缓存，集合中的元素也要使用二级缓存

<collection-cache usage="read-write" collection="com.atguigu.hibernate.entities.Department.emps"/>

也可以在 .hbm.xml 文件中进行配置

<set name="emps" table="GG_EMPLOYEE" inverse="true" lazy="true">
	<cache usage="read-write"/>
    <key>
        <column name="DEPT_ID" />
    </key>
    <one-to-many class="com.atguigu.hibernate.entities.Employee" />
</set>

II. 注意: 还需要配置集合中的元素对应的持久化类也使用二级缓存! 否则将会多出 n 条 SQL 语句. 

3). ehcache 的 配置文件: ehcache.xml
<diskStore>: 指定一个目录：当 EHCache 把数据写到硬盘上时, 将把数据写到这个目录下.
<defaultCache>: 设置缓存的默认数据过期策略 
<cache> 设定具体的命名缓存的数据过期策略。每个命名缓存代表一个缓存区域
缓存区域(region)：一个具有名称的缓存块，可以给每一个缓存块设置不同的缓存策略。如果没有设置任何的缓存区域，则所有被缓存的对象，都将使用默认的缓存策略。即：<defaultCache.../>
Hibernate在不同的缓存区域保存不同的类/集合。
对于类而言，区域的名称是类名。如:com.atguigu.domain.Customer
对于集合而言，区域的名称是类名加属性名。如com.atguigu.domain.Customer.orders


4).  查询缓存: 默认情况下, 设置的缓存对 HQL 及 QBC 查询时无效的, 但可以通过以下方式使其是有效的

I.  在 hibernate 配置文件中声明开启查询缓存

<property name="cache.use_query_cache">true</property>

II. 调用 Query 或 Criteria 的 setCacheable(true) 方法

III. 查询缓存依赖于二级缓存
```

ehcache.xml文件

```xml
<ehcache>

    <!--  
    	指定一个目录：当 EHCache 把数据写到硬盘上时, 将把数据写到这个目录下.
    -->     
    <diskStore path="d:\\tempDirectory"/>


    <!--  
    	设置缓存的默认数据过期策略 
    -->    
    <defaultCache
        maxElementsInMemory="10000"
        eternal="false"
        timeToIdleSeconds="120"
        timeToLiveSeconds="120"
        overflowToDisk="true"
        />

   	<!--  
   		设定具体的命名缓存的数据过期策略。每个命名缓存代表一个缓存区域
   		缓存区域(region)：一个具有名称的缓存块，可以给每一个缓存块设置不同的缓存策略。
   		如果没有设置任何的缓存区域，则所有被缓存的对象，都将使用默认的缓存策略。即：<defaultCache.../>
   		Hibernate 在不同的缓存区域保存不同的类/集合。
			对于类而言，区域的名称是类名。如:com.atguigu.domain.Customer
			对于集合而言，区域的名称是类名加属性名。如com.atguigu.domain.Customer.orders
   	-->
   	<!--  
   		name: 设置缓存的名字,它的取值为类的全限定名或类的集合的名字 
		maxElementsInMemory: 设置基于内存的缓存中可存放的对象最大数目 
		
		eternal: 设置对象是否为永久的, true表示永不过期,
		此时将忽略timeToIdleSeconds 和 timeToLiveSeconds属性; 默认值是false 
		timeToIdleSeconds:设置对象空闲最长时间,以秒为单位, 超过这个时间,对象过期。
		当对象过期时,EHCache会把它从缓存中清除。如果此值为0,表示对象可以无限期地处于空闲状态。 
		timeToLiveSeconds:设置对象生存最长时间,超过这个时间,对象过期。
		如果此值为0,表示对象可以无限期地存在于缓存中. 该属性值必须大于或等于 timeToIdleSeconds 属性值 
		
		overflowToDisk:设置基于内存的缓存中的对象数目达到上限后,是否把溢出的对象写到基于硬盘的缓存中 
   	-->
    <cache name="com.atguigu.hibernate.entities.Employee"
        maxElementsInMemory="1"
        eternal="false"
        timeToIdleSeconds="300"
        timeToLiveSeconds="600"
        overflowToDisk="true"
        />

    <cache name="com.atguigu.hibernate.entities.Department.emps"
        maxElementsInMemory="1000"
        eternal="true"
        timeToIdleSeconds="0"
        timeToLiveSeconds="0"
        overflowToDisk="false"
        />

</ehcache>

```

## 管理session

HibernateUtils.java

```java
Session 对象的生命周期与本地线程绑定
需要在hibernate.cfg.xml 中配置了
<!-- 配置管理 Session 的方式 -->
<property name="current_session_context_class">thread</property>
thread: Session 对象的生命周期与本地线程绑定，这样我们就能在项目中使用HibernateUtils ，获取固定session

    
public class HibernateUtils {	
	private HibernateUtils(){} 
	private static HibernateUtils instance = new HibernateUtils();
	public static HibernateUtils getInstance() {
		return instance;
	}

	private SessionFactory sessionFactory;

	public SessionFactory getS essionFactory() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}
	
	public Session getSession(){
		return getSessionFactory().getCurrentSession();
	}

}


public class DepartmentDao {

	public void save(Department dept){
		//内部获取 Session 对象
		//获取和当前线程绑定的 Session 对象
		//1. 不需要从外部传入 Session 对象
		//2. 多个 DAO 方法也可以使用一个事务!
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println(session.hashCode());
		
		session.save(dept);
	}
	
	/**
	 * 若需要传入一个 Session 对象, 则意味着上一层(Service)需要获取到 Session 对象.
	 * 这说明上一层需要和 Hibernate 的 API 紧密耦合. 所以不推荐使用此种方式. 
	 */
	public void save(Session session, Department dept){
		session.save(dept);
	}
	
}


	@Test
	public void testManageSession(){
		
		//获取 Session
		//开启事务
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println("-->" + session.hashCode());
		Transaction transaction = session.beginTransaction();
		
		DepartmentDao departmentDao = new DepartmentDao();
		
		Department dept = new Department();
		dept.setName("ATGUIGU");
		
		departmentDao.save(dept);
		departmentDao.save(dept);
		departmentDao.save(dept);
		
		//若 Session 是由 thread 来管理的, 则在提交或回滚事务时, 已经关闭 Session 了.  线程结束了
		transaction.commit();
		System.out.println(session.isOpen()); 
	}
	
Hibernate 按一下规则把 Session 与本地线程绑定
当一个线程(threadA)第一次调用 SessionFactory 对象的 getCurrentSession() 方法时, 该方法会创建一个新的 Session(sessionA) 对象, 把该对象与 threadA 绑定, 并将 sessionA 返回 
当 threadA 再次调用 SessionFactory 对象的 getCurrentSession() 方法时, 该方法将返回 sessionA 对象
当 threadA 提交 sessionA 对象关联的事务时, Hibernate 会自动flush sessionA 对象的缓存, 然后提交事务, 关闭 sessionA 对象. 当 threadA 撤销 sessionA 对象关联的事务时, 也会自动关闭 sessionA 对象
若 threadA 再次调用 SessionFactory 对象的 getCurrentSession() 方法时, 该方法会又创建一个新的 Session(sessionB) 对象, 把该对象与 threadA 绑定, 并将 sessionB 返回 

(这里注意，在我们单独的使用hibernate的时候需要配置这个属性，但是当我们和spring集成的时候不需要配置这些)

```



## 批量处理数据

```java
批量处理数据是指在一个事务中处理大量数据.
在应用层进行批量操作, 主要有以下方式:
通过 Session 
通过 HQL 
通过 StatelessSession 
通过 JDBC API (推荐使用这种方式，凡是批量操作，使用原生JDBC 速度是最快的)

	@Test
	public void testBatch(){
		session.doWork(new Work() {			
			@Override
			public void execute(Connection connection) throws SQLException {
				//通过 JDBC 原生的 API 进行操作, 效率最高, 速度最快!
			}
		});
	}
```

![JAVA_HIBERNATE31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIBERNATE31.png?raw=true)

使用session来批量不好的地方在于他会将对象放在缓存中，缓存中的数量是有限的。且若对象采用 “identity” 标识符生成器, 则 Hibernate 无法在 JDBC 层进行批量插入操作。 他每次插入都要在数据库底层调用自动生成。

