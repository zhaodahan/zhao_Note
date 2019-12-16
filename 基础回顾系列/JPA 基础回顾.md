# JPA 基础回顾

## 什么是JPA

![JAVA_JPA1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA1.png?raw=true)

JDBC 本身是一套规范，统一了java程序访问数据库的接口

```java
JPA：Java Persistence API：用于对象持久化的 API。 是Java EE 5.0 平台标准的 ORM 规范(他和JDBC一样，是一套规范，而不是具体的实现，具体的实现指的就是hibernate这些ORM框架)，使得应用程序以统一的方式访问持久层

JPA的另一个特点就是，他全部使用的是注解(是java jdk自带的)。 也可以说我们是使用JPA  基于注解的方式来使用Hibernate框架
```

![JAVA_JPA2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA2.png?raw=true)

## 初识JPA

```java
1:创建 persistence.xml,这个文件是JPA最基本的配置文件，用来配置使用什么数据库，使用什么持久化框架 
2:创建实体类, 使用 annotation 来描述实体类跟数据库表之间的映射关系.
3：使用 JPA API 完成数据增加、删除、修改和查询操作
创建 EntityManagerFactory (对应 Hibernate 中的 SessionFactory);
创建 EntityManager (对应 Hibernate 中的Session);

 
```

基础配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0"
	xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
	<persistence-unit name="jpa-1" transaction-type="RESOURCE_LOCAL">
		<!-- 
		配置使用什么 ORM 产品来作为 JPA 的实现 
		1. 实际上配置的是  javax.persistence.spi.PersistenceProvider 接口的实现类
		2. 若 JPA 项目中只有一个 JPA 的实现产品, 则也可以不配置该节点. 
        HibernatePersistence 就是hibernate实现
		-->
		<provider>org.hibernate.ejb.HibernatePersistence</provider>
	
		<!-- 添加持久化类 -->
		<class>com.atguigu.jpa.helloworld.Customer</class>
		<class>com.atguigu.jpa.helloworld.Order</class>
	
		<class>com.atguigu.jpa.helloworld.Department</class>
		<class>com.atguigu.jpa.helloworld.Manager</class>
	
		<class>com.atguigu.jpa.helloworld.Item</class>
		<class>com.atguigu.jpa.helloworld.Category</class>
		
		<!-- 
		配置二级缓存的策略 
		ALL：所有的实体类都被缓存
		NONE：所有的实体类都不被缓存. 
		ENABLE_SELECTIVE：标识 @Cacheable(true) 注解的实体类将被缓存
		DISABLE_SELECTIVE：缓存除标识 @Cacheable(false) 以外的所有实体类
		UNSPECIFIED：默认值，JPA 产品默认值将被使用
		-->
		<shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
	
		<properties>
			<!-- 连接数据库的基本信息 -->
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql:///jpa"/>
			<property name="javax.persistence.jdbc.user" value="root"/>
			<property name="javax.persistence.jdbc.password" value="1230"/>
			
			<!-- 配置 JPA 实现产品的基本属性. 配置 hibernate 的基本属性 -->
			<property name="hibernate.format_sql" value="true"/>
			<property name="hibernate.show_sql" value="true"/>
			<property name="hibernate.hbm2ddl.auto" value="update"/>
			
			<!-- 二级缓存相关 -->
			<property name="hibernate.cache.use_second_level_cache" value="true"/>
			<property name="hibernate.cache.region.factory_class" value="org.hibernate.cache.ehcache.EhCacheRegionFactory"/>
			<property name="hibernate.cache.use_query_cache" value="true"/>
		</properties>
	</persistence-unit>
</persistence>

```

创建实体化类

```java
@Table(name="JPA_CUTOMERS")
@Entity
public class Customer {
	private Integer id;
	private String lastName;

	private String email;
	private int age;
	
	private Date createdTime;
	private Date birth;
   // 映射主键和对应的列，这个主键是加在对应的get方法上
    public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	public Customer(String lastName, int age) {
		super();
		this.lastName = lastName;
		this.age = age;
	}



	private Set<Order> orders = new HashSet<>();

//使用table来生产主键策略
//	@TableGenerator(name="ID_GENERATOR",
//			table="jpa_id_generators",
//			pkColumnName="PK_NAME",
//			pkColumnValue="CUSTOMER_ID",
//			valueColumnName="PK_VALUE",
//			allocationSize=100)
//	@GeneratedValue(strategy=GenerationType.TABLE,generator="ID_GENERATOR")
    
    
	@GeneratedValue(strategy=GenerationType.AUTO)
    // GeneratedValue 生成主键策略，AUTO 选用底层数据库进行自增
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="LAST_NAME",length=50,nullable=false)
    //@Column 指定对应字段的列名
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Temporal(TemporalType.TIMESTAMP) //精确到秒，对应dateTime
    //Date类型的字段默认的都会被映射为dateTime类型。
    //JPA为我们提供@Temporal 注解，精确的为我们指定Date类型映射到表的类型。
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Temporal(TemporalType.DATE) //精确到日,对应数据库date类型
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	//映射单向 1-n 的关联关系
	//使用 @OneToMany 来映射 1-n 的关联关系
	//使用 @JoinColumn 来映射外键列的名称
	//可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略
	//可以通过 @OneToMany 的 cascade 属性来修改默认的删除策略. 
	//注意: 若在 1 的一端的 @OneToMany 中使用 mappedBy 属性, 则 @OneToMany 端就不能再使用 @JoinColumn 属性了. 
//	@JoinColumn(name="CUSTOMER_ID")
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE},mappedBy="customer")
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	//工具方法. 不需要映射为数据表的一列. 
	@Transient
    //持久类中get方法，即使我们不写任何注解，JPA还是会将其映射为字段，但是这时候我们我可以使用@Transient告诉JPA框架这个方法我们不需要映射
	public String getInfo(){
		return "lastName: " + lastName + ", email: " + email;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", age=" + age + ", createdTime=" + createdTime
				+ ", birth=" + birth + "]";
	}
}
```

使用JPA API 进行操作

```java
public class Main {
	
	public static void main(String[] args) {
		
		//1. 创建 EntitymanagerFactory
		String persistenceUnitName = "jpa-1";
		
		Map<String, Object> properites = new HashMap<String, Object>();
		properites.put("hibernate.show_sql", true);
		
		EntityManagerFactory entityManagerFactory = 
				//Persistence.createEntityManagerFactory(persistenceUnitName);
				Persistence.createEntityManagerFactory(persistenceUnitName, properites);
				
		//2. 创建 EntityManager. 类似于 Hibernate 的 SessionFactory
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//3. 开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//4. 进行持久化操作
		Customer customer = new Customer();
		customer.setAge(12);
		customer.setEmail("tom@atguigu.com");
		customer.setLastName("Tom");
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		
		entityManager.persist(customer);
		
		//5. 提交事务
		transaction.commit();
		
		//6. 关闭 EntityManager
		entityManager.close();
		
		//7. 关闭 EntityManagerFactory
		entityManagerFactory.close();
	}
	
}

```

注意：

```
persistence.xml： JPA 规范要求在类路径的 META-INF 目录下放置persistence.xml，文件的名称是固定的。
(但是，如果我们使用spring整合jpa的时候，这个文件就可以不需要了)
```

![JAVA_JPA3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA3.png?raw=true)

### 用 table 来生成主键详解

（这个机制不常使用，但是在特定的场景也会使用到）

![JAVA_JPA4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA4.png?raw=true)

![JAVA_JPA5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA5.png?raw=true)

```java
将当前主键的值单独保存到一个数据库的表中，主键的值每次都是从指定的表中查询来获得
这种方法生成主键的策略可以适用于任何数据库，不必担心不同数据库不兼容造成的问题。

name属性表示该表主键生成策略的名称，它被引用在@GeneratedValue中设置的“generator”值中。
table属性表示表生成策略所持久化的表名，例如，这里表使用的是数据库中的“tb_generator”。
catalog属性和schema具体指定表所在的目录名或是数据库名。
pkColumnName属性的值表示在持久化表中，该主键生成策略所对应键值的名称。例如在“tb_generator”中将“gen_name”作为主键的键值
valueColumnName属性的值表示在持久化表中，该主键当前所生成的值，它的值将会随着每次创建累加。例如，在“tb_generator”中将“gen_value”作为主键的值
pkColumnValue属性的值表示在持久化表中，该生成策略所对应的主键。例如在“tb_generator”表中，将“gen_name”的值为“CUSTOMER_PK”。
initialValue表示主键初识值，默认为0。
allocationSize表示每次主键值增加的大小，例如设置成1，则表示每次创建新记录后自动加1，默认为50。
```



## JPA 的 API

### Persistence和EntityManagerFactory

```java
Persistence 类： 最主要的作用就是用于获取 EntityManagerFactory 实例。
最常用的方法：
// 值需要传入持久化单元的名称就可以了
EntityManagerFactory entityManagerFactory =Persistence.createEntityManagerFactory(persistenceUnitName); 

//他还有一个重载的方法(不常用，仅做了解)
带有两个参数的方法：第一个参数含义是持久化单元的名称，后一个参数 Map类型，用于设置 JPA 的相关属性
//1. 创建 EntitymanagerFactory
		String persistenceUnitName = "jpa-1";
		
		Map<String, Object> properites = new HashMap<String, Object>();
		properites.put("hibernate.show_sql", true);
		
		EntityManagerFactory entityManagerFactory = 
				Persistence.createEntityManagerFactory(persistenceUnitName, properites);
(但是在和spring进行集成是的时候，这个创建EntityManagerFactory的方法，我们都不需要使用，spring已经帮我们生成好了)
```

EntityManagerFactory

```java
EntityManagerFactory (对应 Hibernate 中的 SessionFactory);接口主要用来创建 EntityManager 实例。

createEntityManager()：用于创建实体管理器对象实例。
createEntityManager(Map map)：用于创建实体管理器对象实例的重载方法，Map 参数用于提供 EntityManager 的属性。
isOpen()：检查 EntityManagerFactory 是否处于打开状态。实体管理器工厂创建后一直处于打开状态，除非调用close()方法将其关闭。
close()：关闭 EntityManagerFactory 。 EntityManagerFactory 关闭后将释放所有资源，isOpen()方法测试将返回 false，其它方法将不能调用，否则将导致IllegalStateException异常。

```



### EntityManager的四个基本方法

```java
EntityManager#find
EntityManager#getReference
EntityManager#persistence
EntityManager#remove
从JPA的角度对象也可以看成四个状态
实体的状态:
新建状态:   新创建的对象，尚未拥有持久性主键。
持久化状态：已经拥有持久性主键并和持久化建立了上下文环境
游离状态：拥有持久化主键，但是没有与持久化建立上下文环境
删除状态:  拥有持久化主键，已经和持久化建立上下文环境，但是从数据库中删除
```

JPA单元测试模板

```java
public class JPATest {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction transaction;
	
	@Before
	public void init(){
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa-1");
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destroy(){
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test
	public void test(){

	}

}
```

四个方法简单测试

```java
EntityManager#find
	//类似于 hibernate 中 Session 的 get 方法. 
	// 在调用find的时候就已经发送了sql语句立即查询，并没有做懒加载
	@Test
	public void testFind() {
		Customer customer = entityManager.find(Customer.class, 1);
		System.out.println("-------------------------------------");
		
		System.out.println(customer);
	}
	
EntityManager#getReference
	//类似于 hibernate 中 Session 的 load 方法
	//在要使用的时候才发送sql语句去查询，使用了懒加载。一开始返回的只是个代理对象
	@Test
	public void testGetReference(){
		Customer customer = entityManager.getReference(Customer.class, 1);
		System.out.println(customer.getClass().getName());
		
		System.out.println("-------------------------------------");
//		transaction.commit();
//		entityManager.close();
		
		System.out.println(customer);
	}

EntityManager#persistence
	//类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态. 
	//和 hibernate 的 save 方法的不同之处: 若对象有 id, 则不能执行 insert 操作, 而会抛出异常. 
	@Test
	public void testPersistence(){
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");
		customer.setId(100);
		
		entityManager.persist(customer);
		System.out.println(customer.getId());
	}

EntityManager#remove
	//类似于 hibernate 中 Session 的 delete 方法. 把对象对应的记录从数据库中移除
	//但注意: 该方法只能移除 持久化 对象. 而 hibernate 的 delete 方法实际上还可以移除 游离对象.
	@Test
	public void testRemove(){
//    不能移除游离对象
//		Customer customer = new Customer();
//		customer.setId(2);
		
// 只能移除持久化对象		
		Customer customer = entityManager.find(Customer.class, 2);
		entityManager.remove(customer);
	}
```



### EntityManager#merge

![JAVA_JPA6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA6.png?raw=true)

```java
/**
	 * 总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
	 */
	//1. 若传入的是一个临时对象 
	//会创建一个新的对象, 把临时对象的属性复制到新的对象中, 然后对新的对象执行持久化insert操作. 所以
	//新的对象中有 id, 但以前的临时对象中没有 id. 
// 数据库中没有，缓存中没有，id没有，直接插入
	@Test
	public void testMerge1(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("cc@163.com");
		customer.setLastName("CC");
		
		Customer customer2 = entityManager.merge(customer);
		
		System.out.println("customer#id:" + customer.getId());// 游离对象，id是null 
		System.out.println("customer2#id:" + customer2.getId()); //持久对象，id不为null
	}

	//若传入的是一个游离对象, 即传入的对象有 OID. 
	//1. 若在 EntityManager 缓存中没有该对象
	//2. 若在数据库中也没有对应的记录
	//3. JPA 会创建一个新的对象, 然后把当前游离对象的属性复制到新创建的对象中
	//4. 对新创建的对象执行 insert 操作. 
//数据库中没有，缓存中也没有，id有，同样insert
	@Test
	public void testMerge2(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("DD");
		
		customer.setId(100);
		
		Customer customer2 = entityManager.merge(customer);
		
		System.out.println("customer#id:" + customer.getId());
		System.out.println("customer2#id:" + customer2.getId());
	}


//若传入的是一个游离对象, 即传入的对象有 OID. 
	//1. 若在 EntityManager 缓存中没有该对象
	//2. 若在数据库中 <<有>> 对应的记录
	//3. JPA 会查询对应的记录, 然后返回该记录对一个的对象, 再然后会把游离对象的属性复制到查询到的对象中.
	//4. 对查询到的对象执行 update 操作.
// 数据库有，缓存没有， update
	@Test
	public void testMerge3(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("ee@163.com");
		customer.setLastName("EE");
		
		customer.setId(4);
		
		Customer customer2 = entityManager.merge(customer);
		System.out.println(customer == customer2); //false
	}

	//若传入的是一个游离对象, 即传入的对象有 OID. 
	//1. 若在 EntityManager 缓存<<中>>有对应的对象
	//2. JPA 会把游离对象的属性复制到查询到EntityManager 缓存中的对象中.
	//3. EntityManager 缓存中的对象执行 UPDATE. 
// 缓存有，数据库也有。将游离对象复制到缓存对象中。 update
	@Test
	public void testMerge4(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("DD");
		
		customer.setId(4);
		Customer customer2 = entityManager.find(Customer.class, 4);
		
		entityManager.merge(customer);
		
		System.out.println(customer == customer2); //false
	}

总结： 对象数据库不存在则insert，存在则update
```



### EntityManager 其他方法

```java
flush ()：同步持久上下文环境，即将持久上下文环境的所有未保存实体的状态信息保存到数据库中。
setFlushMode (FlushModeType flushMode)：设置持久上下文环境的Flush模式。参数可以取2个枚举
FlushModeType.AUTO 为自动更新数据库实体，
FlushModeType.COMMIT 为直到提交事务时才更新数据库记录。
getFlushMode ()：获取持久上下文环境的Flush模式。返回FlushModeType类的枚举值。
refresh (Object entity)：用数据库实体记录的值更新实体对象的状态，即更新实例的属性值。
clear ()：清除持久上下文环境，断开所有关联的实体。如果这时还有未提交的更新则会被撤消。
contains (Object entity)：判断一个实例是否属于当前持久上下文环境管理的实体。
isOpen ()：判断当前的实体管理器是否是打开状态。
getTransaction ()：返回资源层的事务对象。EntityTransaction实例可以用于开始和提交多个事务。
close ()：关闭实体管理器。之后若调用实体管理器实例的方法或其派生的查询对象的方法都将抛出 IllegalstateException 异常，除了getTransaction 和 isOpen方法(返回 false)。不过，当与实体管理器关联的事务处于活动状态时，调用 close 方法后持久上下文将仍处于被管理状态，直到事务完成。
createQuery (String qlString)：创建一个查询对象。
createNamedQuery (String name)：根据命名的查询语句块创建查询对象。参数为命名的查询语句。
createNativeQuery (String sqlString)：使用标准 SQL语句创建查询对象。参数为标准SQL语句字符串。
createNativeQuery (String sqls, String resultSetMapping)：使用标准SQL语句创建查询对象，并指定返回结果集 Map的 名称。
```



### EntityTransaction

```java
EntityTransaction 接口用来管理资源层实体管理器的事务操作。
通过调用实体管理器的getTransaction方法 获得其实例

```



## ORM  映射元数据

ORM框架持有我们的实体类还有实体之间的关联关系，当我们需要使用关联查询的时候，框架会根据我们配置的关联关系来生成对应的sql语句。

### 映射单向多对一的关联关系

使用的最多: 一个用户有多个订单，一个订单只能属于一个用户

```java
@Table(name="JPA_ORDERS")
@Entity
public class Order {

	private Integer id;
	private String orderName;

	private Customer customer;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ORDER_NAME")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	//映射单向 n-1 的关联关系
	//使用 @ManyToOne 来映射多对一的关联关系
	//使用 @JoinColumn 来映射外键.  name="CUSTOMER_ID" 外键的列名是CUSTOMER_ID，他会自动的根据Customer去映射customer对应表的主键
	//可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
	@JoinColumn(name="CUSTOMER_ID")
	@ManyToOne(fetch=FetchType.LAZY)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
```

使用测试:

```java
@Test
	public void testManyToOneUpdate(){
		Order order = entityManager.find(Order.class, 2);
		order.getCustomer().setLastName("FFF");
	}
	
	//不能直接删除 1 的一端, 因为有外键约束. 需要先将n的一段删除干净了，才能去删除1的这一段的
	@Test
	public void testManyToOneRemove(){
//		Order order = entityManager.find(Order.class, 1);
//		entityManager.remove(order);
		
		Customer customer = entityManager.find(Customer.class, 7);
		entityManager.remove(customer);
	}
	
	//默认情况下, 使用左外连接的方式来获取 n 的一端的对象和其关联的 1 的一端的对象. 
    // 主表是n的这一段的表，外连接的是1的一段的表
    //如果n的这一段映射使用了懒加载策略，那么会使用懒加载，会发送两条sql语句
	//可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
	@Test
	public void testManyToOneFind(){
		Order order = entityManager.find(Order.class, 1);
		System.out.println(order.getOrderName());
		
		System.out.println(order.getCustomer().getLastName());
	}
	*/
	
	/**
	 * 保存多对一时, 建议先保存 1 的一端, 后保存 n 的一端, 这样不会多出额外的 UPDATE 语句.
	 */

	@Test
	public void testManyToOnePersist(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("gg@163.com");
		customer.setLastName("GG");
		
		Order order1 = new Order();
		order1.setOrderName("G-GG-1");
		
		Order order2 = new Order();
		order2.setOrderName("G-GG-2");
		
		//设置关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//执行保存操作
		entityManager.persist(order1);
		entityManager.persist(order2);
		
		entityManager.persist(customer);
	}
```



### 映射单向一对多的关联关系

一个用户有多个订单

```java
@Table(name="JPA_ORDERS")
@Entity
public class Order {

	private Integer id;
	private String orderName;
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ORDER_NAME")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
}

@Table(name="JPA_CUTOMERS")
@Entity
public class Customer {

	private Integer id;
	private String lastName;
    //.........
	private Set<Order> orders = new HashSet<>();

//映射单向 1-n 的关联关系
	//使用 @OneToMany 来映射 1-n 的关联关系
	//使用 @JoinColumn 来映射外键列的名称
	//可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略 fetch=FetchType.EAGER  不使用懒加载  
	//可以通过 @OneToMany 的 cascade 属性来修改默认的删除策略. CascadeType.REMOVE 在删除1的一端的同时将多的一端也删除。
	@JoinColumn(name="CUSTOMER_ID")
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE})
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}

```

![JAVA_JPA7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA7.png?raw=true)

实际应用

```java
	@Test
	public void testUpdate(){
		Customer customer = entityManager.find(Customer.class, 10);
		
		customer.getOrders().iterator().next().setOrderName("O-XXX-10");
	}
	
	//默认情况下, 若删除 1 的一端, 则会先把关联的 n 的一端的外键置空, 然后进行删除. 
	//可以通过 @OneToMany 的 cascade 属性来修改默认的删除策略. 
	@Test
	public void testOneToManyRemove(){
		Customer customer = entityManager.find(Customer.class, 8);
		entityManager.remove(customer);
	}
	
	//默认对关联的多的一方使用懒加载的加载策略. 
	//可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略
	@Test
	public void testOneToManyFind(){
		Customer customer = entityManager.find(Customer.class, 9);
		System.out.println(customer.getLastName());
		
		System.out.println(customer.getOrders().size());
	}
	

    // 由1的一段去保存关联关系与，保存的先后关系不大，都会发送n条update。因为多的一端在插入时，不会额外的插入外键列	
	//单向 1-n 关联关系执行保存时, 一定会多出 UPDATE 语句.
	//因为 n 的一端在插入时不会同时插入外键列. 
	@Test
	public void testOneToManyPersist(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("mm@163.com");
		customer.setLastName("MM");
		
		Order order1 = new Order();
		order1.setOrderName("O-MM-1");
		
		Order order2 = new Order();
		order2.setOrderName("O-MM-2");
		
		//建立关联关系
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);

		//执行保存操作
		entityManager.persist(customer);

		entityManager.persist(order1);
		entityManager.persist(order2);
	}
```

### 映射双向多对一的关联关系

(双向和单向不同，双向多对一和双向一对多其实就是一回事)

```java
需要注意的是： 两边映射的外键名要一致，都要是CUSTOMER_ID
@Table(name="JPA_ORDERS")
@Entity
public class Order {
	private Integer id;
	private String orderName;
    private Customer customer;
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ORDER_NAME")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
    @JoinColumn(name="CUSTOMER_ID")
	@ManyToOne(fetch=FetchType.LAZY)
    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

@Table(name="JPA_CUTOMERS")
@Entity
public class Customer {

	private Integer id;
	private String lastName;
    //.........
	private Set<Order> orders = new HashSet<>();

	@JoinColumn(name="CUSTOMER_ID")
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE})
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}

```

测试 双向多对一

```java
// 这样就行保存，会发送3条insert，4条update
	//若是双向 1-n 的关联关系, 执行保存时
	//若先保存 n 的一端, 再保存 1 的一端, 默认情况下, 会多出 2n 条 UPDATE 语句.对应这个例子就是4条
	//若先保存 1 的一端, 则也会多出 n 条 UPDATE 语句。 对应这个例子就是2条
// 建议：在进行双向 1-n 关联关系时, 建议使用 n 的一方来维护关联关系, 而 1 的一方不维护关联系, 这样会有效的减少 SQL 语句. 

// 如何让1的一段放弃维护：
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE},mappedBy="customer")
	public Set<Order> getOrders() {
		return orders;
	}
使用mappedBy告诉他，这是由多的一方Order的customer属性去维护。

	//注意: 若在 1 的一端的 @OneToMany 中使用 mappedBy 属性, 则 @OneToMany 端就不能再使用 @JoinColumn 属性了. 
@Test
	public void testOneToManyPersist(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("mm@163.com");
		customer.setLastName("MM");
		
		Order order1 = new Order();
		order1.setOrderName("O-MM-1");
		
		Order order2 = new Order();
		order2.setOrderName("O-MM-2");
		
		//建立关联关系
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//执行保存操作
		entityManager.persist(customer);

		entityManager.persist(order1);
		entityManager.persist(order2);
	}	
```



### 映射双向一对一的关联关系

双向@OneToOne： 一个部门只能有一个经理，一个经理也只能管一个部门

```java
// 这里是基于外键的双向一对一映射。 
//问题：这个外键放在哪个表里面，基于外键的双向一对一，这个外键放在哪个表里面都可以
@Table(name="JPA_MANAGERS")
@Entity
public class Manager {

	private Integer id;
	private String mgrName;
	
	private Department dept;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="MGR_NAME")
	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	//对于不维护关联关系, 没有外键的一方, 使用 @OneToOne 来进行映射, 建议设置 mappedBy
	@OneToOne(mappedBy="mgr")
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}
}


@Table(name="JPA_DEPARTMENTS")
@Entity
public class Department {

	private Integer id;
	private String deptName;
	
	private Manager mgr;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    // 外键选择放在Department 表中
	//使用 @OneToOne 来映射 1-1 关联关系。
	//若需要在当前数据表中添加主键则需要使用 @JoinColumn 来进行映射. 注意, 1-1 关联关系, 所以需要添加 unique=true
	@JoinColumn(name="MGR_ID", unique=true)
	@OneToOne(fetch=FetchType.LAZY)
	public Manager getMgr() {
		return mgr;
	}

	public void setMgr(Manager mgr) {
		this.mgr = mgr;
	}
}

```

测试

```java
	//1. 默认情况下, 若获取不维护关联关系的一方, 则也会通过左外连接获取其关联的对象. 
	//可以通过 @OneToOne 的 fetch 属性来修改加载策略. 但依然会再发送 SQL 语句来初始化其关联的对象
	//这说明在不维护关联关系的一方, 不建议修改 fetch 属性. 
// 为什么了？ 在不维护关联关系的一方没有外键，不清楚是否存在关联对象，需要发送语句去查询。因为外键字段是定义在 Department 表中的,Hibernate 在不读取 Department 表的情况是无法判断是否有关联有 Deparmtment, 因此无法判断设置 null 还是代理对象, 而统一设置为代理对象,也无法满足不关联的情况, 所以无法使用延迟加载,只 有显式读取 Department.
	@Test
	public void testOneToOneFind2(){
		Manager mgr = entityManager.find(Manager.class, 1);
		System.out.println(mgr.getMgrName());
		
		System.out.println(mgr.getDept().getClass().getName());
	}
	
	//1.默认情况下, 若获取维护关联关系的一方, 则会通过左外连接获取其关联的对象. 
	//但可以通过 @OntToOne 的 fetch 属性来修改加载策略.
	@Test
	public void testOneToOneFind(){
		Department dept = entityManager.find(Department.class, 1);
		System.out.println(dept.getDeptName());
		System.out.println(dept.getMgr().getClass().getName());
	}
	
	//双向 1-1 的关联关系, 建议先保存不维护关联关系的一方, 即没有外键的一方, 这样不会多出 UPDATE 语句.
	@Test
	public void testOneToOnePersistence(){
		Manager mgr = new Manager();
		mgr.setMgrName("M-BB");
		
		Department dept = new Department();
		dept.setDeptName("D-BB");
		
		//设置关联关系
		mgr.setDept(dept);
		dept.setMgr(mgr);
		
		//执行保存操作
		entityManager.persist(mgr);
		entityManager.persist(dept);
	}
```



### 映射双向多对多的关联关系

在双向多对多关系中，我们必须指定一个关系维护端(owner side),可以通过 @ManyToMany 注释中指定 mappedBy 属性来标识其为关系维护端。

![JAVA_JPA8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA8.png?raw=true)

一个类别可以有多个商品，一个商品也可以属于多个类别

```java
@Table(name="JPA_CATEGORIES")
@Entity
public class Category {

	private Integer id;
	private String categoryName;
	
	private Set<Item> items = new HashSet<>();

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="CATEGORY_NAME")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@ManyToMany(mappedBy="categories")
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
}

@Table(name="JPA_ITEMS")
@Entity
public class Item {

	private Integer id;
	private String itemName;
	
	private Set<Category> categories = new HashSet<>();

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ITEM_NAME")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	//使用 @ManyToMany 注解来映射多对多关联关系
	//使用 @JoinTable 来映射中间表
	//1. name 指向中间表的名字
	//2. joinColumns 映射当前类所在的表在中间表中的外键
	//2.1 name 指定外键列的列名
	//2.2 referencedColumnName 指定外键列关联当前表的哪一列
	//3. inverseJoinColumns 映射关联的类所在中间表的外键
	@JoinTable(name="ITEM_CATEGORY",
			joinColumns={@JoinColumn(name="ITEM_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="CATEGORY_ID", referencedColumnName="ID")})
	@ManyToMany
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}

```

![JAVA_JPA9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA9.png?raw=true)

测试

```java
	//对于关联的集合对象, 默认使用懒加载的策略.
	//使用维护关联关系的一方获取, 还是使用不维护关联关系的一方获取, SQL 语句相同. (他们使用的中间表关联)
	@Test
	public void testManyToManyFind(){
//		Item item = entityManager.find(Item.class, 5);
//		System.out.println(item.getItemName());
//		
//		System.out.println(item.getCategories().size());
		
		Category category = entityManager.find(Category.class, 3);
		System.out.println(category.getCategoryName());
		System.out.println(category.getItems().size());
	}
	
	//多对所的保存
	@Test
	public void testManyToManyPersist(){
		Item i1 = new Item();
		i1.setItemName("i-1");
	
		Item i2 = new Item();
		i2.setItemName("i-2");
		
		Category c1 = new Category();
		c1.setCategoryName("C-1");
		
		Category c2 = new Category();
		c2.setCategoryName("C-2");
		
		//设置关联关系
		i1.getCategories().add(c1);
		i1.getCategories().add(c2);
		
		i2.getCategories().add(c1);
		i2.getCategories().add(c2);
		
		c1.getItems().add(i1);
		c1.getItems().add(i2);
		
		c2.getItems().add(i1);
		c2.getItems().add(i2);
		
		//执行保存
		entityManager.persist(i1);
		entityManager.persist(i2);
		entityManager.persist(c1);
		entityManager.persist(c2);
	}
```



## 使用二级缓存

```java
JPA 如何开球并使用二级缓存？ (二级缓存可以跨entityManager)

1： 在persistence.xml 配置文件中加入配置
(这个配置是hibernate中开启二级缓存的配置) 和配置二级缓存的策略
 <?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0"
	xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
	<persistence-unit name="jpa-1" transaction-type="RESOURCE_LOCAL">
		<!-- 
		配置使用什么 ORM 产品来作为 JPA 的实现 
		1. 实际上配置的是  javax.persistence.spi.PersistenceProvider 接口的实现类
		2. 若 JPA 项目中只有一个 JPA 的实现产品, 则也可以不配置该节点. 
		-->
		<provider>org.hibernate.ejb.HibernatePersistence</provider>

		<!-- 
		配置二级缓存的策略 
		ALL：所有的实体类都被缓存
		NONE：所有的实体类都不被缓存. 
		ENABLE_SELECTIVE：标识 @Cacheable(true) 注解的实体类将被缓存
		DISABLE_SELECTIVE：缓存除标识 @Cacheable(false) 以外的所有实体类
		UNSPECIFIED：默认值，JPA 产品默认值将被使用
		-->
		<shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
	
		<properties>
			<!-- 连接数据库的基本信息 -->
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql:///jpa"/>
			<property name="javax.persistence.jdbc.user" value="root"/>
			<property name="javax.persistence.jdbc.password" value="1230"/>
			
			<!-- 配置 JPA 实现产品的基本属性. 配置 hibernate 的基本属性 -->
			<property name="hibernate.format_sql" value="true"/>
			<property name="hibernate.show_sql" value="true"/>
			<property name="hibernate.hbm2ddl.auto" value="update"/>
			
			<!-- 二级缓存相关 -->
			<property name="hibernate.cache.use_second_level_cache" value="true"/>
			<property name="hibernate.cache.region.factory_class" value="org.hibernate.cache.ehcache.EhCacheRegionFactory"/>
			<property name="hibernate.cache.use_query_cache" value="true"/>
		</properties>
	</persistence-unit>
</persistence>

2： 加入类路径(src)下ehcache.xml配置文件
<ehcache>
    <diskStore path="java.io.tmpdir"/>
    <defaultCache
        maxElementsInMemory="10000"
        eternal="false"
        timeToIdleSeconds="120"
        timeToLiveSeconds="120"
        overflowToDisk="true"
        />

    <cache name="sampleCache1"
        maxElementsInMemory="10000"
        eternal="false"
        timeToIdleSeconds="300"
        timeToLiveSeconds="600"
        overflowToDisk="true"
        />
</ehcache>

3:类上注解 开启二级缓存
@Cacheable(true)
@Table(name="JPA_CUTOMERS")
@Entity
public class Customer {...}
```



## 查询语言（JPQL）

### 什么是JPQL

```java
JPQL 即JPA查询语言。
JPQL语言的语句可以是 select 语句、update 语句或delete语句，它们都通过 Query 接口封装执行 （他和hibernatele的HQL类似都是对entityManager 常用方法的补充）

使用JPQL 这类语句写sql语句需要注意： 他并不是将我们书写的sql语句直接就发送给数据库，而是对我们写的伪sql进行封装解析，所以在写这个伪sql的时候，我们需要带有对象的思想去书写。虽然直接写以前的sql也能够正常使用，但是写伪sql能更加方便
例如：
String jpql = "SELECT o FROM Order o "
				+ "WHERE o.customer = (SELECT c FROM Customer c WHERE c.lastName = ?)";
这里的o.customer 代表的就是一个对象。

Query接口的主要方法
int executeUpdate()： 用于执行update或delete语句。
List getResultList()： 用于执行select语句并返回结果集实体列表。
Object getSingleResult()： 用于执行只返回单个结果实体的select语句。
Query setFirstResult(int startPosition)： 用于设置从哪个实体记录开始返回查询结果。
Query setMaxResults(int maxResult) ：用于设置返回结果实体的最大数。与setFirstResult结合使用可实现分页查询。
Query setFlushMode(FlushModeType flushMode) 
设置查询对象的Flush模式。参数可以取2个枚举值：FlushModeType.AUTO 为自动更新数据库记录，FlushMode Type.COMMIT 为直到提交事务时才更新数据库记录。

setParameter(int position, Object value) ：为查询语句的指定位置参数赋值。Position 指定参数序号，value 为赋给参数的值。
```

初步实例

```java
	@Test
	public void testHelloJPQL(){
		String jpql = "FROM Customer c WHERE c.age > ?";
		Query query = entityManager.createQuery(jpql);
		
		//占位符的索引是从 1 开始
		query.setParameter(1, 1);
		List<Customer> customers = query.getResultList();
		System.out.println(customers.size());
	}

	//默认情况下, 若只查询部分属性, 则将返回 Object[] 类型的结果. 或者 Object[] 类型的 List.
	//也可以在实体类中创建对应的构造器, 然后再 JPQL 语句中利用对应的构造器返回实体类的对象.
	@Test
	public void testPartlyProperties(){
		String jpql = "SELECT new Customer(c.lastName, c.age) FROM Customer c WHERE c.id > ?";
		List result = entityManager.createQuery(jpql).setParameter(1, 1).getResultList();
		
		System.out.println(result);
	}

我们还可以将查询语句放在类上
@NamedQuery(name="testNamedQuery", query="FROM Customer c WHERE c.id = ?")
@Cacheable(true)
@Table(name="JPA_CUTOMERS")
@Entity
public class Customer {.......}

	//createNamedQuery 适用于在实体类前使用 @NamedQuery 标记的查询语句
	@Test
	public void testNamedQuery(){
		Query query = entityManager.createNamedQuery("testNamedQuery").setParameter(1, 3);
		Customer customer = (Customer) query.getSingleResult();
		
		System.out.println(customer);
	}
	

//createNativeQuery 适用于本地 SQL
	@Test
	public void testNativeQuery(){
		String sql = "SELECT age FROM jpa_cutomers WHERE id = ?";
		Query query = entityManager.createNativeQuery(sql).setParameter(1, 3);
		
		Object result = query.getSingleResult();
		System.out.println(result);
	}
```



### 使用 Hibernate 的查询缓存

```java
	//使用 hibernate 的查询缓存.当然配置文件需要配置启用查询缓存 
	@Test
	public void testQueryCache(){
		String jpql = "FROM Customer c WHERE c.age > ?";
		Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);
		
		//占位符的索引是从 1 开始
		query.setParameter(1, 1);
		List<Customer> customers = query.getResultList();
		System.out.println(customers.size());
		
		query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);
		
		//占位符的索引是从 1 开始
		query.setParameter(1, 1);
		customers = query.getResultList();
		System.out.println(customers.size());
	}
```



### ORDER BY 和 GROUP BY

```java
	//查询 order 数量大于 2 的那些 Customer
	@Test
	public void testGroupBy(){
		String jpql = "SELECT o.customer FROM Order o "
				+ "GROUP BY o.customer "
				+ "HAVING count(o.id) >= 2";
		List<Customer> customers = entityManager.createQuery(jpql).getResultList();
		
		System.out.println(customers);
	}
	
// JPA 发送的sql语句中会自动驾驶order By
	@Test
	public void testOrderBy(){
		String jpql = "FROM Customer c WHERE c.age > ? ORDER BY c.age DESC";
		Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);
		
		//占位符的索引是从 1 开始
		query.setParameter(1, 1);
		List<Customer> customers = query.getResultList();
		System.out.println(customers.size());
	}
```



### 关联查询

```java
JPQL 也支持和 SQL 中类似的关联语法。如：
left out join / left join 
inner join 
left join / inner join fetch 
其中，left join和left out join等义，都是允许符合条件的右边表达式中的实体为空。
	/**
	 * JPQL 的关联查询同 HQL 的关联查询. 
	 * 建议加上FETCH ，得到的是一个对象，其关联属性已经初始化好了
	 */
	@Test
	public void testLeftOuterJoinFetch(){
		String jpql = "FROM Customer c LEFT OUTER JOIN FETCH c.orders WHERE c.id = ?";
		
		Customer customer = 
				(Customer) entityManager.createQuery(jpql).setParameter(1, 12).getSingleResult();
		System.out.println(customer.getLastName());
		System.out.println(customer.getOrders().size());
		
//		List<Object[]> result = entityManager.createQuery(jpql).setParameter(1, 12).getResultList();
//		System.out.println(result);
	}
```



### 子查询 和 JPQL 函数

```java
@Test
	public void testSubQuery(){
		//查询所有 Customer 的 lastName 为 YY 的 Order
		String jpql = "SELECT o FROM Order o "
				+ "WHERE o.customer = (SELECT c FROM Customer c WHERE c.lastName = ?)";
		
		Query query = entityManager.createQuery(jpql).setParameter(1, "YY");
		List<Order> orders = query.getResultList();
		System.out.println(orders.size());
	}

JPQL提供了以下一些内建函数，包括字符串处理函数、算术函数和日期函数。
字符串处理函数主要有：
concat(String s1, String s2)：字符串合并/连接函数。
substring(String s, int start, int length)：取字串函数。
trim([leading|trailing|both,] [char c,] String s)：从字符串中去掉首/尾指定的字符或空格。
lower(String s)：将字符串转换成小写形式。
upper(String s)：将字符串转换成大写形式。
length(String s)：求字符串的长度。
locate(String s1, String s2[, int start])：从第一个字符串中查找第二个字符串(子串)出现的位置。若未找到则返回0。
算术函数主要有 abs、mod、sqrt、size 等。Size 用于求集合的元素个数。
日期函数主要为三个，即 current_date、current_time、current_timestamp，它们不需要参数，返回服务器上的当前日期、时间和时戳
	//使用 jpql 内建的函数
	@Test
	public void testJpqlFunction(){
		String jpql = "SELECT lower(c.email) FROM Customer c";
		
		List<String> emails = entityManager.createQuery(jpql).getResultList();
		System.out.println(emails);
	}
	
```



### UPDATE 和 DELETE

```java
这里使用update和delete主要是为了进行批量操作
update Customers c set c.status = '未偿付' where c.balance < 10000
或者
delete from Customers c where c.status = 'inactive' and c.orders is empty

//可以使用 JPQL 完成 UPDATE 和 DELETE 操作. 
	@Test
	public void testExecuteUpdate(){
		String jpql = "UPDATE Customer c SET c.lastName = ? WHERE c.id = ?";
		Query query = entityManager.createQuery(jpql).setParameter(1, "YYY").setParameter(2, 12);
		
		query.executeUpdate();
	}
```

注意： JPQL 不支持insert语句。

```java
JPQL 不支持insert语句。jpa 使用insert。
(1)jpa 原生insert的sql语句
@Modifying
    @Query(value = "insert into t_sys_org_user(org_id,user_id) values(?1,?2)",nativeQuery = true)
    int addUserToOrg(Long orgId,Long userId);
(2) 调用简单的持久化方法
```



## spring整合JPA

![JAVA_JPA10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JPA10.png?raw=true)

```java
问题:  spring整合JPA 整合什么？
1： 让spring去管理EntityManagerFactory
2： 让JPA用上spring的声明式事务

怎么整合？
三种整合方式：
LocalEntityManagerFactoryBean：适用于那些仅使用 JPA 进行数据访问的项目，该 FactoryBean 将根据JPA PersistenceProvider 自动检测配置文件进行工作，一般从“META-INF/persistence.xml”读取配置信息，这种方式最简单，但不能设置 Spring 中定义的DataSource，且不支持 Spring 管理的全局事务 (所以pass 弃用)

从JNDI中获取：用于从 Java EE 服务器获取指定的EntityManagerFactory，这种方式在进行 Spring 事务管理时一般要使用 JTA 事务管理 (所以如果不用Java EE 服务器，这种方式就使用不了，所以也pass)

LocalContainerEntityManagerFactoryBean(推荐使用)：适用于所有环境的 FactoryBean，能全面控制 EntityManagerFactory 配置,如指定 Spring 定义的 DataSource 等等。

spring 配置文件
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">

	<!-- 配置自动扫描的包 -->
	<context:component-scan base-package="com.atguigu.jpa"></context:component-scan>

	<!-- 配置 C3P0 数据源 -->
	<context:property-placeholder location="classpath:db.properties"/>

	<bean id="dataSource"
		class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>	
		
		<!-- 配置其他属性 -->
	</bean>
	
	<!-- 配置 EntityManagerFactory -->
	<bean id="entityManagerFactory"
		class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<!-- 配置 JPA 提供商的适配器. 可以通过内部 bean 的方式来配置 -->
		<property name="jpaVendorAdapter">
			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"></bean>
		</property>	
		<!-- 配置实体类所在的包 -->
		<property name="packagesToScan" value="com.atguigu.jpa.spring.entities"></property>
		<!-- 配置 JPA 的基本属性. 例如 JPA 实现产品的属性 -->
		<property name="jpaProperties">
			<props>
				<prop key="hibernate.show_sql">true</prop>
				<prop key="hibernate.format_sql">true</prop>
				<prop key="hibernate.hbm2ddl.auto">update</prop>
			</props>
		</property>
	</bean>
	
	<!-- 配置 JPA 使用的事务管理器 -->
	<bean id="transactionManager"
		class="org.springframework.orm.jpa.JpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactory"></property>	
	</bean>
	
	<!-- 配置支持基于注解是事务配置 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>

</beans>


DAO层使用JPA： (这里需要注意 @PersistenceContext 注解)
@Repository
public class PersonDao {
	//如何获取到和当前事务关联的 EntityManager 对象呢 ?
	//通过 @PersistenceContext 注解来标记成员变量!
	@PersistenceContext
	private EntityManager entityManager;
	public void save(Person person){
		entityManager.persist(person);
	}	
}

```




