# SpringData 学习笔记

## 什么是SpringData

```
Spring Data : Spring 的一个子项目。用于简化数据库访问.支持NoSQL 和 关系数据存储
SpringData 项目所支持 NoSQL 存储：
MongoDB （文档数据库）
Neo4j（图形数据库）
Redis（键/值存储）
Hbase（列族数据库）

SpringData 项目所支持的关系数据存储技术：
JDBC
JPA
```

**JPA Spring Data** 

```
JPA Spring Data : 致力于减少数据访问层 (DAO) 的开发量. 开发者唯一要做的，就只是声明持久层的接口，其他都交给 Spring Data JPA 来帮你完成！

```



## 初识SpringData

```java
使用 Spring Data JPA 进行持久层开发需要的步骤：
(1):配置 Spring 整合 JPA 
springData是建立在spring和JPA的基础之上的

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:jpa="http://www.springframework.org/schema/data/jpa"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

	<!-- 配置自动扫描的包 -->
	<context:component-scan base-package="com.atguigu.springdata"></context:component-scan>

	<!-- 1. 配置数据源 -->
	<context:property-placeholder location="classpath:db.properties"/>

	<bean id="dataSource"
		class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>	
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		
		<!-- 配置其他属性 -->
	</bean>

	<!-- 2. 配置 JPA 的 EntityManagerFactory -->
	<bean id="entityManagerFactory" 
		class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<property name="jpaVendorAdapter">
			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"></bean>
		</property>
		<property name="packagesToScan" value="com.atguigu.springdata"></property>
		<property name="jpaProperties">
			<props>
				<!-- 二级缓存相关 -->
				<!--  
				<prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</prop>
				<prop key="net.sf.ehcache.configurationResourceName">ehcache-hibernate.xml</prop>
				-->
				<!-- 生成的数据表的列的映射策略 -->
				<prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>
				<!-- hibernate 基本属性 -->
				<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
				<prop key="hibernate.show_sql">true</prop>
				<prop key="hibernate.format_sql">true</prop>
				<prop key="hibernate.hbm2ddl.auto">update</prop>
			</props>
		</property>
	</bean>

	<!-- 3. 配置事务管理器 -->
	<bean id="transactionManager"
		class="org.springframework.orm.jpa.JpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactory"></property>	
	</bean>

	<!-- 4. 配置支持注解的事务 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>

	<!-- 5. 配置 SpringData -->
	<!-- 加入  jpa 的命名空间 -->
	<!-- base-package: 扫描 Repository Bean 所在的 package -->
	<jpa:repositories base-package="com.atguigu.springdata"
		entity-manager-factory-ref="entityManagerFactory"></jpa:repositories>

</beans>


(2):在 Spring 配置文件中配置 Spring Data，让 Spring 为声明的接口创建代理对象。配置了 <jpa:repositories> 后，Spring 初始化容器时将会扫描 base-package  指定的包目录及其子目录，为继承 Repository 或其子接口的接口创建代理对象，并将代理对象注册为 Spring Bean，业务层便可以通过 Spring 自动封装的特性来直接使用该对象。
            
	<jpa:repositories base-package="com.atguigu.springdata"
		entity-manager-factory-ref="entityManagerFactory"></jpa:repositories>
		
            
(3):声明持久层的接口，该接口继承Repository，Repository 是一个标记型接口，它不包含任何方法，如必要，Spring Data 可实现 Repository 其他子接口，其中定义了一些常用的增删改查，以及分页相关的方法。
//Repository 的两个泛型。 第一个类型是我们需要处理的实体类型， 第二个是主键的类型
public interface PersonRepsotory extends Repository<Person, Integer>{}

(4)在接口中声明需要的方法。Spring Data 将根据给定的策略来为其生成实现代码。
public interface PersonRepsotory extends Repository<Person, Integer>{
//根据 lastName 来获取对应的 Person
	Person getByLastName(String lastName);
}
```

## SpringData原理

```java
在上面我们所做的很简单，只是实现了一个接口，声明了一个方法，并没有写sql语句，框架就为我们执行了与数据库的交互查询操作，他的原理是什么？

springData为继承了Repository接口的接口生成对应的代理。代理对象为接口中符合一定规范的方法结合对应的持久类与对应的映射关系生成对应的sql.类似于hibernate中的save方法。
```

### Repository 接口　

```java
Repository 接口是 Spring Data 的一个核心接口，它不提供任何方法，开发者需要在自己定义的接口中声明需要的方法

 /**
 * 1. Repository 是一个空接口. 即是一个标记接口
 * 2. 若我们定义的接口继承了 Repository, 则该接口会被 IOC 容器识别为一个 Repository Bean.
 * 纳入到 IOC 容器中. 进而可以在该接口中定义满足一定规范的方法. 
 * 
 * 3. 实际上, 也可以通过 @RepositoryDefinition 注解来替代继承 Repository 接口
 */
    public interface Repository<T, ID extends Serializable> { } 

与继承 Repository 等价的一种方式，就是在持久层接口上使用 @RepositoryDefinition 注解，并为其指定 domainClass(对应着操作实体的类)和 idClass(对应着主键的类) 属性
@RepositoryDefinition(domainClass=Person.class,idClass=Integer.class)
public interface PersonRepsotory 
{
    
}
```

#### Repository子接口　

```java
基础的 Repository 提供了最基本的数据访问功能，其几个子接口则扩展了一些功能。它们的继承关系如下：

Repository： 仅仅是一个标识，表明任何继承它的均为仓库接口类
CrudRepository： 继承 Repository，实现了一组 CRUD 相关的方法 
PagingAndSortingRepository： 继承 CrudRepository，实现了一组分页排序相关的方法 
JpaRepository： 继承 PagingAndSortingRepository，实现一组 JPA 规范相关的方法 
自定义的 XxxxRepository 需要继承 JpaRepository，这样的 XxxxRepository 接口就具备了通用的数据访问控制层的能力。
JpaSpecificationExecutor： 不属于Repository体系，实现一组 JPA Criteria 查询相关的方法

public interface PersonRepsotory extends 
	JpaRepository<Person, Integer>,
	JpaSpecificationExecutor<Person>, PersonDao{
    
    }
```

#### CrudRepository接口

```java
CrudRepository 接口是Repository的子接口，提供了最基本的对实体类的添删改查操作 
T save(T entity);//保存单个实体 
Iterable<T> save(Iterable<? extends T> entities);//保存集合        
T findOne(ID id);//根据id查找实体         
boolean exists(ID id);//根据id判断实体是否存在         
Iterable<T> findAll();//查询所有实体,不用或慎用!         
long count();//查询实体数量         
void delete(ID id);//根据Id删除实体         
void delete(T entity);//删除一个实体 
void delete(Iterable<? extends T> entities);//删除一个实体的集合         
void deleteAll();//删除所有实体,不用或慎用! 

====测试=====
 插入等修改需要事务，从service进入   
@Service
public class PersonService {

	@Autowired
	private PersonRepsotory personRepsotory;
	
	@Transactional
	public void savePersons(List<Person> persons){
		personRepsotory.save(persons);
	}
}
    
    
@Test
	public void testCrudReposiory(){
		List<Person> persons = new ArrayList<>();
		
		for(int i = 'a'; i <= 'z'; i++){
			Person person = new Person();
			person.setAddressId(i + 1);
			person.setBirth(new Date());
			person.setEmail((char)i + "" + (char)i + "@atguigu.com");
			person.setLastName((char)i + "" + (char)i);
			
			persons.add(person);
		}
		
		personService.savePersons(persons);
	} 
```

#### PagingAndSortingRepository接口

```java
该接口提供了分页与排序功能 ,他是 CrudRepository接口的子接口
Iterable<T> findAll(Sort sort); //排序 
Page<T> findAll(Pageable pageable); //分页查询（含排序功能） 

Pageable 是一个接口，他有一个实现类是PageRequest，封装了我们分页时需要传入的信息
=====测试========
@Test
	public void testPagingAndSortingRespository(){
		//pageNo 从 0 开始. 
		int pageNo = 6 - 1;
		int pageSize = 5;
		//Pageable 接口通常使用的其 PageRequest 实现类. 其中封装了需要分页的信息
		//排序相关的. Sort 封装了排序的信息
		//Order 是具体针对于某一个属性进行升序还是降序. 这里的Order是springData里定义的专用于排序的order
		Order order1 = new Order(Direction.DESC, "id");
		Order order2 = new Order(Direction.ASC, "email");
		Sort sort = new Sort(order1, order2);
		
		PageRequest pageable = new PageRequest(pageNo, pageSize, sort);
		Page<Person> page = personRepsotory.findAll(pageable);
		
		System.out.println("总记录数: " + page.getTotalElements());
		System.out.println("当前第几页: " + (page.getNumber() + 1));
		System.out.println("总页数: " + page.getTotalPages());
		System.out.println("当前页面的 List: " + page.getContent());
		System.out.println("当前页面的记录数: " + page.getNumberOfElements());
	}    
```

#### JpaRepository接口

```java
该接口提供了JPA的相关功能 ， JpaRepository接口是PagingAndSortingRepository接口的字节口
List<T> findAll(); //查找所有实体 
List<T> findAll(Sort sort); //排序、查找所有实体 
List<T> save(Iterable<? extends T> entities);//保存集合 
void flush();//执行缓存与数据库同步 
T saveAndFlush(T entity);//强制执行持久化 ，相当于JPA中的merge()
void deleteInBatch(Iterable<T> entities);//删除一个实体集合 

```

#### JpaSpecificationExecutor接口

![JAVA_SPRINGDATA3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGDATA3.png?raw=true)

```java
不属于Repository体系，实现一组 JPA Criteria 查询相关的方法 
Specification：封装  JPA Criteria 查询条件。通常使用匿名内部类的方式来创建该接口的对象

他的主要作用是用于完成一个通用的分页。
public interface PersonRepsotory extends 
	JpaRepository<Person, Integer>,
JpaSpecificationExecutor<Person>{
    //......
}

/**
	 * 目标: 实现带查询条件的分页. id > 5 的条件
	 * 
	 * 调用 JpaSpecificationExecutor 的 Page<T> findAll(Specification<T> spec, Pageable pageable);
	 * Specification: 封装了 JPA Criteria 查询的查询条件
	 * Pageable: 封装了请求分页的信息: 例如 pageNo, pageSize, Sort
	 */
	@Test
	public void testJpaSpecificationExecutor(){
		int pageNo = 3 - 1;
		int pageSize = 5;
		PageRequest pageable = new PageRequest(pageNo, pageSize);
		
		//通常使用 Specification 的匿名内部类
		Specification<Person> specification = new Specification<Person>() {
			/**
			 * @param *root: 代表查询的实体类. 
			 * @param query: 可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
			 * 来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象. 
			 * @param *cb: CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
			 * @return: *Predicate 类型, 代表一个查询条件. 
			 */
			@Override
			public Predicate toPredicate(Root<Person> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                //导航到我们对应的属性
				Path path = root.get("id");
				Predicate predicate = cb.gt(path, 5);
				return predicate;
			}
		};
		
		Page<Person> page = personRepsotory.findAll(specification, pageable);
		
		System.out.println("总记录数: " + page.getTotalElements());
		System.out.println("当前第几页: " + (page.getNumber() + 1));
		System.out.println("总页数: " + page.getTotalPages());
		System.out.println("当前页面的 List: " + page.getContent());
		System.out.println("当前页面的记录数: " + page.getNumberOfElements());
	}
	
```



#### 自定义 Repository方法

![JAVA_SPRINGDATA4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGDATA4.png?raw=true)

```java
-为某一个 Repository 上添加自定义方法步骤：
定义一个接口: 声明要添加的, 并自实现的方法
public interface PersonDao {	
	void test();	
}
提供该接口的实现类: 类名需在要声明的 Repository 后添加 Impl, 并实现方法
public class PersonRepsotoryImpl implements PersonDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void test() {
		Person person = entityManager.find(Person.class, 11);
		System.out.println("-->" + person);
	}

}
声明 Repository 接口, 并继承 1) 声明的接口使用. 
    public interface PersonRepsotory extends 
	JpaRepository<Person, Integer>,
	JpaSpecificationExecutor<Person>, PersonDao{.....}
注意: 默认情况下, Spring Data 会在 base-package 中查找 "接口名Impl" 作为实现类. 也可以通过　repository-impl-postfix　声明后缀.

使用：
	@Test
	public void testCustomRepositoryMethod(){
		personRepsotory.test();
	}
    
-为所有的 Repository 都添加自实现的方法
步骤：
声明一个接口, 在该接口中声明需要自定义的方法, 且该接口需要继承 Spring Data 的 Repository.
提供 1) 所声明的接口的实现类. 且继承 SimpleJpaRepository, 并提供方法的实现
定义 JpaRepositoryFactoryBean 的实现类, 使其生成 1) 定义的接口实现类的对象
修改 <jpa:repositories /> 节点的 factory-class 属性指向 3) 的全类名
注意: 全局的扩展实现类不要用 Imp 作为后缀名, 或为全局扩展接口添加 @NoRepositoryBean 注解告知  Spring Data: Spring Data 不把其作为 Repository

```



### SpringData 方法定义规范

![JAVA_SPRINGDATA1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGDATA1.png?raw=true)

![JAVA_SPRINGDATA2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_SPRINGDATA2.png?raw=true)

```java
/**
 * 在 Repository 子接口中声明方法
 * 1. 不是随便声明的. 而需要符合一定的规范,即定义的的方法需要以关键字结合
 * 2. 查询方法以 find | read | get 开头
 * 3. 涉及条件查询时，条件的属性用条件关键字连接(By, StartingWith,And,LessThan)
 * 4. 要注意的是：条件属性以首字母大写。(LastName)
 * 5. 支持属性的级联查询. 若当前类有符合条件的属性, 则优先使用, 而不使用级联属性. 
 * 若需要使用级联属性, 则属性之间使用 _ 进行连接. 
 */
    public interface Repository<T, ID extends Serializable> { 
            
//根据 lastName 来获取对应的 Person
	Person getByLastName(String lastName);
        
//WHERE lastName LIKE ?% AND id < ?
	List<Person> getByLastNameStartingWithAndIdLessThan(String lastName, Integer id);
  
//WHERE lastName LIKE %? AND id < ?
List<Person> getByLastNameEndingWithAndIdLessThan(String lastName, Integer id);
        
//WHERE email IN (?, ?, ?) OR birth < ?
	List<Person> getByEmailInAndBirthLessThan(List<String> emails, Date birth);
	
//WHERE a.id > ?
问题： 级联属性容易出现歧义       
//若当前类有符合条件的属性, 则优先使用, 而不使用级联属性
	List<Person> getByAddressIdGreaterThan(Integer id);   
//若需要使用级联属性, 则属性之间使用 _ 进行连接.
        List<Person> getByAddress_IdGreaterThan(Integer id);   
    }  
```



### @Query 注解

```java
使用关键字，属性去写查询方法比较麻烦,
如：List<Person> getByEmailInAndBirthLessThan(List<String> emails, Date birth);
且方法名比较长。并且他还不是足够的灵敏。根据属性和关键字是无法使用sql函数，他只能对简单的类进行简单的操作
所以springData支持@Query 注解来让我们自己写JPQL
//查询 id 值最大的那个 Person
	//使用 @Query 注解可以自定义 JPQL 语句以实现更灵活的查询
	@Query("SELECT p FROM Person p WHERE p.id = (SELECT max(p2.id) FROM Person p2)")
	Person getMaxIdPerson();
	
	//为 @Query 注解传递参数的方式1: 使用占位符. 
	@Query("SELECT p FROM Person p WHERE p.lastName = ?1 AND p.email = ?2")
	List<Person> testQueryAnnotationParams1(String lastName, String email);
	
	//为 @Query 注解传递参数的方式2: 命名参数的方式. 
	@Query("SELECT p FROM Person p WHERE p.lastName = :lastName AND p.email = :email")
	List<Person> testQueryAnnotationParams2(@Param("email") String email, @Param("lastName") String lastName);
	
	//SpringData 允许在占位符上添加 %%. 
	@Query("SELECT p FROM Person p WHERE p.lastName LIKE %?1% OR p.email LIKE %?2%")
	List<Person> testQueryAnnotationLikeParam(String lastName, String email);
	
	//SpringData 允许在占位符上添加 %%. 
	@Query("SELECT p FROM Person p WHERE p.lastName LIKE %:lastName% OR p.email LIKE %:email%")
	List<Person> testQueryAnnotationLikeParam2(@Param("email") String email, @Param("lastName") String lastName);
	
	//设置 nativeQuery=true 即可以使用原生的 SQL 查询
	@Query(value="SELECT count(id) FROM jpa_persons", nativeQuery=true)
	long getTotalCount();

```

### @Modifying 注解

```java
springData能做修改吗？
1：使用repository的子接口提供的方法。
2：使用 @Modifying 注解提供的定制化JPQL的方式来实现删除和修改。
注意，这种方式需要使用事务。 事务的注解通常是加在service层的

	//可以通过自定义的 JPQL 完成 UPDATE 和 DELETE 操作. 注意: JPQL 不支持使用 INSERT
	//在 @Query 注解中编写 JPQL 语句, 但必须使用 @Modifying 进行修饰. 以通知 SpringData, 这是一个 UPDATE 或 DELETE 操作
	//UPDATE 或 DELETE 操作需要使用事务, 此时需要定义 Service 层. 在 Service 层的方法上添加事务操作. 
	//默认情况下, SpringData 的每个方法上有事务, 但都是一个只读事务. 他们不能完成修改操作!
	@Modifying
	@Query("UPDATE Person p SET p.email = :email WHERE id = :id")
	void updatePersonEmail(@Param("id") Integer id, @Param("email") String email);
```




