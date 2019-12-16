# MyBatisPlus 学习笔记

[官方地址](http://mp.baomidou.com/)[:](http://mp.baomidou.com/)

http://mp.baomidou.com/

代码发[布地址](https://github.com/baomidou/mybatis-plus)[:](https://github.com/baomidou/mybatis-plus)

Github：https://github.com/baomidou/mybatis-plus

Gitee: https://gitee.com/baomidou/mybatis-plus

文档发布地址

http://mp.baomidou.com/#/?id=%E7%AE%80%E4%BB%8B

MyBatisPlus是一款非常强大的MyBatis增强工具包,只做增强不做改变.在不用编写任何SQL语句的情况下即可以极其方便的实现单一、批量、分页等操作。简化了开发效率。

MyBatisPlus 是mybatis的好基友，他们一起搭配才能更好的发挥效率。  MyBatisPlus 的使用时基于mybatis的

## MyBatisPlus 基础使用

### 环境搭建

Maven依赖

```xml
	<!-- mp依赖
  			 mybatisPlus 会自动的维护Mybatis 以及MyBatis-spring相关的依赖
			所以无需加入Mybatis 以及MyBatis-spring 的相关依赖，以避免版本冲突
  		 -->
		<dependency>
		    <groupId>com.baomidou</groupId>
		    <artifactId>mybatis-plus</artifactId>
		    <version>2.3</version>
		</dependency>	
```

类路径下加入 MyBatis 的全局配置文件mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	
</configuration>
```

如果与spring整合，在spring配置文件中加入mybatis配置

Mybatis-Plus 的集成非常简单，对于 Spring，我们仅仅需要把 Mybatis 自带的SqlSessionFactoryBean 替换为 MP 自带的MybatisSqlSessionFactoryBean即可

```xml
	<!--  配置SqlSessionFactoryBean 
		Mybatis提供的: org.mybatis.spring.SqlSessionFactoryBean
		MP提供的:com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean
	 -->
	<bean id="sqlSessionFactoryBean" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
		<!-- 数据源 -->
		<property name="dataSource" ref="dataSource"></property>
		<property name="configLocation" value="classpath:mybatis-config.xml"></property>
		<!-- 别名处理 -->
		<property name="typeAliasesPackage" value="com.atguigu.mp.beans"></property>		
		
		<!-- 注入全局MP策略配置 -->
		<property name="globalConfig" ref="globalConfiguration"></property>
	</bean>


	<!-- 定义MybatisPlus的全局策略配置-->
	<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
		<!-- 在2.3版本以后，dbColumnUnderline 默认值就是true -->
		<property name="dbColumnUnderline" value="true"></property>
		
		<!-- 全局的主键策略 -->
		<property name="idType" value="0"></property>
		
		<!-- 全局的表前缀策略配置 -->
		<property name="tablePrefix" value="tbl_"></property>	
	
	</bean>

  
  <!-- 
		配置mybatis 扫描mapper接口的路径
	 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.atguigu.mp.mapper"></property>
	</bean>
```

### 基本的CRUD

```java
实现一个po的基本CRUD
基于 Mybatis
需要编写 EmployeeMapper 接口，并手动编写 CRUD 方法
提供 EmployeeMapper.xml 映射文件，并手动编写每个方法对应的 SQL 语句.
基于 MP
只需要创建 EmployeeMapper 接口, 并继承 BaseMapp
BaseMapper<T> : 泛型指定的就是当前Mapper接口所操作的实体类类型 

BaseMapper 怎么帮我们实现CRUD
在BaseMapper 已经帮我们定义好了各式各样的增删改查方法
(BaseMapper 需要结合实体上 @TableName注解，@TableId 指定主键自增策略  @TableField 映射对应数据库的字段名
(加入能够提供更丰富功能,建议都加上，更灵活) )
且使用MP最好要遵守驼峰命名

/*
 * MybatisPlus会默认使用实体类的类名到数据中找对应的表. 
 * 
 */
@TableName(value="tbl_employee")
public class Employee {
	/*
	 * @TableId:
	 * 	 value: 指定表中的主键列的列名， 如果实体属性名与列名一致，可以省略不指定. 
	 *   type: 指定主键策略. 
	 */
	//@TableId(value="id" , type =IdType.AUTO)
	private Integer id ;   //  int 
	
	@TableField(value = "last_name")
	private String  lastName; 
	private String  email ;
	private Integer gender; 
	private Integer age ;
	
	@TableField(exist=false)
	private Double salary ; 
}	
```

![JAVA_MYBATIS18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS18.png?raw=true)

MP支持以下4中主键策略：

| **值**           | **描述**                                                     |
| :--------------- | ------------------------------------------------------------ |
| IdType.AUTO      | 数据库ID自增(mysql)                                          |
| IdType.INPUT     | 用户输入ID                                                   |
| IdType.ID_WORKER | 全局唯一ID，内容为空自动填充（默认配置）简单来说就是一个分布式高效有序ID（默认） |
| IdType.UUID      | 全局唯一ID，内容为空自动填充                                 |

#### 插入

```java
1： 设置主键自增策略
2： 获取自增的主键值
支持主键自增的数据库插入数据获取主键值
Mybatis: 需要通过 useGeneratedKeys 以及 keyProperty 来设置
MP: 自动将主键值回写到实体类中
直接通过实体获取即可

	/**
	 * 通用 插入操作
	 */
	@Test
	public void testCommonInsert() {
		
		//初始化Employee对象
		Employee employee  = new Employee();
		employee.setLastName("MP");
		employee.setEmail("mp@atguigu.com");
		//employee.setGender(1);
		//employee.setAge(22);
		employee.setSalary(20000.0);
		//插入到数据库   
	// insert方法在插入时， 会根据实体类的每个属性进行非空判断，只有非空的属性对应的字段才会出现到SQL语句中
	//Integer result = employeeMapper.insert(employee);  
		
	//insertAllColumn方法在插入时， 不管属性是否非空， 属性所对应的字段都会出现到SQL语句中.给的值是null值 
    //这两个方法对数据库里面的结果没有什么区别，只是在发送的sql语句中存在所有字段
		Integer result = employeeMapper.insertAllColumn(employee);
		
		System.out.println("result: " + result );
		
		//获取当前数据在数据库中的主键值
		Integer key = employee.getId();
		System.out.println("key:" + key );
	}
```

#### 更新

```java
    /**
	 * 通用 更新操作
	 * 修改对象是根据id来更新的，所以必须传入id
	 */
	@Test
	public void testCommonUpdate() {
		//初始化修改对象
		Employee employee = new Employee();
		employee.setId(7);
		employee.setLastName("小泽老师");
		employee.setEmail("xz@sina.com");
		employee.setGender(0);
		//employee.setAge(33);
		
        //同插入insert一样，updateById 对实体的属性值做了非空判断
	    Integer result = employeeMapper.updateById(employee);
        
        //updateAllColumnById同insertAllColumn一样会在sql语句中带上所有的属性，没有则传入null
        // 与插入不同的是，在更新sql中传入null值会改变原来的记录值，这个方法慎用
		Integer result = employeeMapper.updateAllColumnById(employee);
		
		System.out.println("result: " + result );
	}
```

#### 查询

```java
1) T selectById(Serializable id);
2) T selectOne(@Param("ew") T entity);
3) List<T> selectBatchIds(List<? extends Serializable> idList);
4) List<T> selectByMap(@Param("cm") Map<String, Object> columnMap);
5) List<T> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<T> wrapper);

	/**
	 * 通用 查询操作
	 */
	@Test
	public void  testCommonSelect() {
		//1. 通过id查询
		Employee employee = employeeMapper.selectById(7);
		System.out.println(employee);
		
		//2. 通过多个列进行查询    id  +  lastName
		Employee  employee = new Employee();
		//employee.setId(7);
		employee.setLastName("小泽老师");
		employee.setGender(0);
	    //如果根据查询条件会返回多个值，这个方法就会报错
		Employee result = employeeMapper.selectOne(employee);
		System.out.println("result: " +result );
		
		
		//3. 通过多个id进行查询    <foreach> 但是他有局限性就是只能传入ids
		List<Integer> idList = new ArrayList<>();
		idList.add(4);
		idList.add(5);
		List<Employee> emps = employeeMapper.selectBatchIds(idList);
		System.out.println(emps);
		
		//4. 通过Map封装条件查询
		Map<String,Object> columnMap = new HashMap<>();
        //这里需要注意key 需要写数据库列名，而不是实体属性
		columnMap.put("last_name", "Tom");
		columnMap.put("gender", 1);
		
		List<Employee> emps = employeeMapper.selectByMap(columnMap);
     	System.out.println(emps);
		
		//5. 分页查询 。他没有使用真正的limit 数据库分页，而是使用的内存分页(发送的sql语句中没有limit)
        //如果要使用真正的物理上的limit分页，则需要借助分页插件PageHelper或者mybatisPlus提供的分页插件，或自己写sql (这个分页方法一般不使用)
		List<Employee> emps = employeeMapper.selectPage(new Page<>(3, 2), null);
		System.out.println(emps);
	}
	
```

#### 删除

```java
1) Integer deleteById(Serializable id);
2) Integer deleteByMap(@Param("cm") Map<String, Object> columnMap);
3) Integer deleteBatchIds(List<? extends Serializable> idList);
	/**
	 * 通用 删除操作
	 */
	@Test
	public void testCommonDelete() {
		//1 .根据id进行删除
		Integer result = employeeMapper.deleteById(13);
		System.out.println("result: " + result );
		//2. 根据 条件进行删除
		Map<String,Object> columnMap = new HashMap<>();
		columnMap.put("last_name", "MP");
		columnMap.put("email", "mp@atguigu.com");
		Integer result = employeeMapper.deleteByMap(columnMap);
		System.out.println("result: " + result );
		
		//3. 批量删除
		List<Integer> idList = new ArrayList<>();
		idList.add(3);
		idList.add(4);
		idList.add(5);
		Integer result = employeeMapper.deleteBatchIds(idList);
		System.out.println("result: " + result );
	}
```

#### **MP** 启动注入 **SQL** 完成CRUD原理

```
1) 问题: xxxMapper 继承了 BaseMapper<T>, BaseMapper 中提供了通用的 CRUD 方法,方法来源于 BaseMapper, 有方法就必须有 SQL, 因为 MyBatis 最终还是需要通过SQL 语句操作数据.
他到底是怎么帮我们解决sql的问题的，就是在启动的时候帮我们注入了sql
MyBatis 源码中比较重要的一些对象， MyBatis 框架的执行流程
Configuration
MappedStatement
……..
2) 通过现象看到本质 (通过能看到的现象反推其原理)
A． employeeMapper 的本质 org.apache.ibatis.binding.MapperProxy
B． MapperProxy 中 sqlSession –>SqlSessionFactory
C． SqlSessionFacotry 中 → Configuration→ MappedStatements
每一个 mappedStatement 都表示 Mapper 接口中的一个方法与 Mapper 映射文件中的一个 SQL。

MP 在启动的时候就会挨个分析 xxxMapper 中的方法，并且将对应的 SQL 语句处理好，保存到 configuration 对象中的 mappedStatements 中.

D． 本质:
Configuration： MyBatis 或者 MP 全局配置对象
MappedStatement：一个 MappedStatement 对象对应 Mapper 配置文件中的一个
select/update/insert/delete 节点，主要描述的是一条 SQL 语句
SqlMethod : 枚举对象 ，MP 支持的 SQL 方法
TableInfo：数据库表反射信息 ，可以获取到数据库表相关的信息
SqlSource: SQL 语句处理对象
MapperBuilderAssistant： 用于缓存、SQL 参数、查询方剂结果集处理等.
通过 MapperBuilderAssistant 将每一个 mappedStatement添加到 configuration 中的 mappedstatements 中
```

![JAVA_MYBATIS20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS20.png?raw=true)

**MyBatisPlus 是怎么帮我们自动注入sql的**

![JAVA_MYBATIS21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS21.png?raw=true)

```java
    /**
     * <p>
     * 注入删除 SQL 语句
     * </p>
     *
     * @param mapperClass
     * @param modelClass
     * @param table
     */
    protected void injectDeleteByIdSql(boolean batch, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
//DELETE_BY_ID("deleteById", "根据ID 删除一条数据", "<script>DELETE FROM %s WHERE %s=#{%s}</script>"),
        SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID;
        SqlSource sqlSource;
        // 因为后面要通过get方法获取类型，所以这里要获取key的属性值
        String idStr = table.getKeyProperty();
        if (batch) {
            sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
            StringBuilder ids = new StringBuilder();
            ids.append("\n<foreach item=\"item\" index=\"index\" collection=\"coll\" separator=\",\">");
            ids.append("#{item}");
            ids.append("\n</foreach>");
            idStr = ids.toString();
        }
        //DELETE_BY_ID("deleteById", "根据ID 删除一条数据", "<script>DELETE FROM %s WHERE %s=#{%s}</script>"),
        String sql = String.format(sqlMethod.getSql(), table.getTableName(), table.getKeyColumn(), idStr);
        sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
    }
```

![JAVA_MYBATIS22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS22.png?raw=true)

### MyBatisPlus 全局策略配置

```xml
在spring整合的配置文件中
<!-- 定义MybatisPlus的全局策略配置-->
	<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
		<!-- 在2.3版本以后，dbColumnUnderline 默认值就是true -->
        <!-- 处理java类中驼峰命名到下划线的映射 -->
		<property name="dbColumnUnderline" value="true"></property>
		
		<!-- 全局的主键策略 -->
		<property name="idType" value="0"></property>
		
		<!-- 全局的表前缀策略配置 -->
		<property name="tablePrefix" value="tbl_"></property>	
	
	</bean>


```

![JAVA_MYBATIS19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS19.png?raw=true)



### MyBatisPlus条件构造器

MP 提供了功能强大的条件构造器 EntityWrapper

MP: EntityWrapper Condition 条件构造器

MyBatis MBG :xxxExample→Criteria : QBC( Query By Criteria)

Hibernate 、 通用 Mapper： QBC

```
1) Mybatis-Plus 通过 EntityWrapper（简称 EW，MP 封装的一个查询条件构造器）或者Condition（与 EW 类似） 来让用户自由的构建查询条件，简单便捷，没有额外的负担，能够有效提高开发效率
2) 实体包装器，主要用于处理 sql 拼接，排序，实体参数查询等
3) 注意: 使用的是数据库字段，不是 Java 属性!
```

![JAVA_MYBATIS23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS23.png?raw=true)

```java
使用的是数据库字段，不是 Java 属性!
/**
	 * 条件构造器   查询操作
	 */
    带条件的查询
1) List<T> selectList(@Param("ew") Wrapper<T> wrapper);
	@Test
	public void testEntityWrapperSelect() {
		//我们需要分页查询tbl_employee表中，年龄在18~50之间且性别为男且姓名为Tom的所有用户
		
		List<Employee> emps =employeeMapper.selectPage(new Page<Employee>(1, 2),
					new EntityWrapper<Employee>()
					.between("age", 18, 50)
					.eq("gender", 1)
					.eq("last_name", "Tom")
				);
		System.out.println(emps);
	
		
		List<Employee > emps = employeeMapper.selectPage(
							new Page<Employee>(1,2), 
							Condition.create()
							.between("age", 18, 50)
							.eq("gender", "1")
							.eq("last_name", "Tom")
							
							);
		
		System.out.println(emps);			
		
		
		// 查询tbl_employee表中， 性别为女并且名字中带有"老师" 或者  邮箱中带有"a"
		
		List<Employee> emps = employeeMapper.selectList(
				new EntityWrapper<Employee>()
				.eq("gender", 0)
				.like("last_name", "老师")
				//.or()    // SQL: (gender = ? AND last_name LIKE ? OR email LIKE ?)    
				.orNew()   // SQL: (gender = ? AND last_name LIKE ?) OR (email LIKE ?) 
				.like("email", "a")
				);
        //一般情况下or() 和orNew对于数据库的效果类型，无特殊要求使用or（）
		System.out.println(emps);
		
		
		// 查询性别为女的, 根据age进行排序(asc/desc), 简单分页
		
		List<Employee> emps  = employeeMapper.selectList(
				new EntityWrapper<Employee>()
				.eq("gender", 0)
				.orderBy("age")  //默认使用的是升序排序
				//.orderDesc(Arrays.asList(new String [] {"age"}))
				.last("desc limit 1,3")
				);
		System.out.println(emps);
		
	}

/**
	 * 条件构造器  删除操作
	 */
带条件的删除
1)Integer delete(@Param("ew") Wrapper<T> wrapper);
	@Test
	public void testEntityWrapperDelete() {
		employeeMapper.delete(
					new EntityWrapper<Employee>()
					.eq("last_name", "Tom")
					.eq("age", 22)
				);
	}
	
	
	/**
	 * 条件构造器  修改操作
	 */
带条件的修改
1) Integer update(@Param("et") T entity, @Param("ew") Wrapper<T> wrapper);
	@Test
	public void testEntityWrapperUpdate() {	
		Employee employee = new Employee();
		employee.setLastName("苍老师");
		employee.setEmail("cls@sina.com");
		employee.setGender(0);
		
		employeeMapper.update(employee, 
					new EntityWrapper<Employee>()
					.eq("last_name", "Tom")
					.eq("age", 44)
					);
	}
	
```

## **ActiveRecord(**活动记录)

一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表中的一行记录。

AR 模式提供了一种更加便捷的方式实现 CRUD 操作，其本质还是调用的 Mybatis 底层对应的方法，相当于对Mybatis底层的一些方法进行了封装

### 如何使用AR模式

```java
 仅仅需要让实体类继承 Model 类且实现主键指定方法，即可开启 AR 之旅.
 public class Employee extends Model<Employee>{
    // .. fields
    // .. getter and setter
    @Override
    protected Serializable pkVal() {
    return this.id;
}

```

![JAVA_MYBATIS24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS24.png?raw=true)

```java
	/**
	 * AR  插入操作
	 */
	@Test
	public void  testARInsert() {
		Employee employee = new Employee();
		employee.setLastName("宋老师");
		employee.setEmail("sls@atguigu.com");
		employee.setGender(1);
		employee.setAge(35);
		
		boolean result = employee.insert();
		System.out.println("result:" +result );
	}

```

## MyBatisPlus的代码生成器

```
MP 的代码生成器 和 Mybatis MBG 代码生成器:
MP 的代码生成器都是基于 java 代码来生成。MBG 基于 xml 文件进行代码生成 (MyBatisPlus代码生成器功能强大的多)
MyBatis 的代码生成器可生成: 实体类、Mapper 接口、Mapper 映射文件
MP 的代码生成器可生成: 实体类(可以选择是否支持 AR)、Mapper 接口、Mapper 映射文件、 Service 层、Controller 层.

表及字段命名策略选择
在 MP 中，我们建议数据库表名 和 表字段名采用驼峰命名方式， (数据库表名我们一般)采用下划线命名方式 就需要开启全局下划线开关，如果表名字段名命名方式不一致请注解指定(最好一致)。

这么做的原因是为了避免在对应实体类时产生的性能损耗，这样字段不用做映射就能直接和实体类对应。当然如果项目里不用考虑这点性能损耗，那么你采用下滑线也是没问题的，只需要在生成代码时配置 dbColumnUnderline 属性就可以(2.3以后这个配置默认开启)

```

使用代码生成器需要加入的依赖

```xml
1) 模板引擎:MP 的代码生成器默认使用的是 Apache 的 Velocity 模板
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.0</version>
</dependency>

2) 加入 slf4j ,查看日志输出信息
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.7</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.7</version>
</dependency>

```

**MP** 代码生成器示例代码

```java
/**
	 * 代码生成    示例代码   
	 */
	@Test
	public void  testGenerator() {
		//1. 全局配置
        //MyBatisPlus代码生成器没有配置文件，所以需要将所有的配置信息通过代码展现
		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(true) // 是否支持AR模式
			  .setAuthor("weiyunhui") // 作者
			  .setOutputDir("D:\\workspace_mp\\mp03\\src\\main\\java") // 生成路径
			  .setFileOverride(true)  // 文件覆盖
			  .setIdType(IdType.AUTO) // 主键策略
			  .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
			  					   // IEmployeeService
 			  .setBaseResultMap(true)
 			  .setBaseColumnList(true);
		
		//2. 数据源配置
		DataSourceConfig  dsConfig  = new DataSourceConfig();
		dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
				.setDriverName("com.mysql.jdbc.Driver")
				.setUrl("jdbc:mysql://localhost:3306/mp")
				.setUsername("root")
				.setPassword("1234");
		 
		//3. 策略配置
		StrategyConfig stConfig = new StrategyConfig();
		stConfig.setCapitalMode(true) //全局大写命名
				.setDbColumnUnderline(true)  // 指定数据库表名 字段名是否使用下划线
				.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
				.setTablePrefix("tbl_")
				.setInclude("tbl_employee");  // 根据这个数据库表来生成对应代码
		
		//4. 包名策略配置 
		PackageConfig pkConfig = new PackageConfig();
		pkConfig.setParent("com.atguigu.mp")
				.setMapper("mapper")
				.setService("service")
				.setController("controller")
				.setEntity("beans")
				.setXml("mapper");
		
		//5. 整合配置
		AutoGenerator  ag = new AutoGenerator();
		
		ag.setGlobalConfig(config)
		  .setDataSource(dsConfig)
		  .setStrategy(stConfig)
		  .setPackageInfo(pkConfig);
		
		//6. 执行
		ag.execute();
	}

```

生成的代码

![JAVA_MYBATIS25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS25.png?raw=true)

```java
EmployeeServiceImpl 继承了 ServiceImpl 类,mybatis-plus 通过这种方式为我们注入了 EmployeeMapper,这样可以使用 service 层默认为我们提供的很多方法,也可以调用我们自己在 dao 层编写的操作数据库的方法

生成的服类
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
	
	//不用再进行mapper的注入.
	
	/**
	 * EmployeeServiceImpl  继承了ServiceImpl
	 * 1. 在ServiceImpl中已经完成Mapper对象的注入,直接在EmployeeServiceImpl中进行使用
	 * 2. 在ServiceImpl中也帮我们提供了常用的CRUD方法， 基本的一些CRUD方法在Service中不需要我们自己定义.
	 * 
	 * 
	 */
}

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService; 
	
//	public String  login() {
//		
//		//employeeService.select
//	}
}

```




## 插件扩展

### Mybatis插件机制

```
1) 插件机制:
Mybatis 通过插件(Interceptor) 可以做到拦截四大对象相关方法的执行,根据需求，完
成相关数据的动态改变。
Executor
StatementHandler
ParameterHandler
ResultSetHandler
2) 插件原理
四大对象的每个对象在创建时，都会执行 interceptorChain.pluginAll()，会经过每个插
件对象的 plugin()方法，目的是为当前的四大对象创建代理。代理对象就可以拦截到四
大对象相关方法的执行，因为要执行四大对象的方法需要经过代理.
```

想要使用插件，需要先注册

```xml
两种方式：
1： 在Mybatis全局配置文件mybatis-config.xml中注册
<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	 <plugins>
		<plugin interceptor="com.baomidou.mybatisplus.plugins.PaginationInterceptor"></plugin>
	</plugins> 

</configuration>

2: 与spring整合，或与springBoot整合，将其配置在spring的配置文件中
<!--  配置SqlSessionFactoryBean 
		Mybatis提供的: org.mybatis.spring.SqlSessionFactoryBean
		MP提供的:com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean
	 -->
	<bean id="sqlSessionFactoryBean" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
		<!-- 数据源 -->
		<property name="dataSource" ref="dataSource"></property>
		<property name="configLocation" value="classpath:mybatis-config.xml"></property>
		<!-- 别名处理 -->
		<property name="typeAliasesPackage" value="com.atguigu.mp.beans"></property>		
		
		<!-- 注入全局MP策略配置 -->
		<property name="globalConfig" ref="globalConfiguration"></property>
		
		<!-- 插件注册 -->
		<property name="plugins">
			<list>
				<!-- 注册分页插件 -->
				<bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor"></bean>
				
				<!-- 注册执行分析插件 -->
				<bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
					<property name="stopProceed" value="true"></property>
				</bean>
				
				<!-- 注册性能分析插件 -->
				<bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
					<property name="format" value="true"></property>
					<!-- <property name="maxTime" value="5"></property> -->
				</bean>
				
				<!-- 注册乐观锁插件 -->
				<bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor">
				</bean>
			
			</list>
			
		</property>
		
	</bean>
```


### 分页插件

```java
1) com.baomidou.mybatisplus.plugins.PaginationInterceptor

	/**
	 * 测试分页插件
	 */
	@Test
	public void testPage() {
	//selectPage 这个方法在注册分页插件之前虽然也能实现分页效果，但是他只是进行的内存分页而不是真正意义上的物理分页(没有limit)，只有在配置了分页插件之后，他才会真正的使用物理分页
		Page<Employee> page = new Page<>(1,1);
		List<Employee > emps = 
				employeeMapper.selectPage(page, null);
		System.out.println(emps);
		System.out.println("===============获取分页相关的一些信息======================");
		System.out.println("总条数:" +page.getTotal());
		System.out.println("当前页码: "+  page.getCurrent());
		System.out.println("总页码:" + page.getPages());
		System.out.println("每页显示的条数:" + page.getSize());
		System.out.println("是否有上一页: " + page.hasPrevious());
		System.out.println("是否有下一页: " + page.hasNext());
		//将查询的结果封装到page对象中
		page.setRecords(emps);
	}

```



### 执行分析插件

```java
1) com.baomidou.mybatisplus.plugins.SqlExplainInterceptor
2) SQL 执行分析拦截器，只支持 MySQL5.6.3 以上版本
3) 该插件的作用是分析 DELETE UPDATE 语句,防止小白或者恶意进行 DELETE UPDATE 全表操作
4) 只建议在开发环境中使用，不建议在生产环境使用
5) 在插件的底层 通过 SQL 语句分析命令:Explain 分析当前的 SQL 语句，根据结果集中的 Extra 列来断定当前是否全表操作。

/**
	 * 测试SQL执行分析插件
	 */
	@Test
	public void testSQLExplain() {
		
		employeeMapper.delete(null);  // 全表删除
	}
```



### 性能分析插件

```java
1) com.baomidou.mybatisplus.plugins.PerformanceInterceptor
2) 性能分析拦截器，用于输出每条 SQL 语句及其执行时间
3) SQL 性能执行分析,开发环境使用，超过指定时间，停止运行。有助于发现问题

	/**
	 * 测试 性能分析插件
	 */
	@Test
	public void testPerformance() {
		Employee employee = new Employee();
		employee.setLastName("玛利亚老师");
		employee.setEmail("mly@sina.com");
		employee.setGender("0");
		employee.setAge(22);
		
		employeeMapper.insert(employee);
		
	}
```



### 乐观锁插件

```java
1) com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor
2) 如果想实现如下需求: 当要更新一条记录的时候，希望这条记录没有被别人更新
如果直接加锁，则会在性能上有写影响，所以希望是个乐观锁
3) 乐观锁的实现原理:
取出记录时，获取当前 version2
更新时，带上这个 version2
执行更新时， set version = yourVersion+1 where version = yourVersion
如果 version 不对，就更新失败
4) @Version 用于注解实体字段，必须要有(实体上要有这个属性字段)。 且表中要有version这个字段来一一对应

	/**
	 * 测试 乐观锁插件
	 */
	
	@Test
	public void testOptimisticLocker() {
		//更新操作
		Employee employee = new Employee();
		employee.setId(15);
		employee.setLastName("TomAA");
		employee.setEmail("tomAA@sina.com");
		employee.setGender("1");
		employee.setAge(22);
		employee.setVersion(3); //这个version就必须设置
		employeeMapper.updateById(employee);	
	}
```

## MyBatisPlus自定义全局操作

```java
根据 MybatisPlus 的 AutoSqlInjector 可以自定义各种你想要的 sql ,注入到全局中，相当于自定义 Mybatisplus 自动注入的方法。之前需要在 xml 中进行配置的 SQL 语句，现在通过扩展 AutoSqlInjector 在加载 mybatis 环境时就注入

实现步骤:
AutoSqlInjector
1) 在 Mapper 接口中定义相关的全局操作方法
2) 扩展 AutoSqlInjectorinject 方法，实现 Mapper 接口中方法要注入的 SQL

/**
 * 自定义全局操作
 */
public class MySqlInjector  extends AutoSqlInjector{
	
	/**
	 * 扩展inject 方法，完成自定义全局操作
	 */
	@Override
public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass,Class<?> modelClass, TableInfo table) {
	//将EmployeeMapper中定义的deleteAll， 处理成对应的MappedStatement对象，加入到configuration对象中。
		
		//注入的SQL语句
		String sql = "delete from " +table.getTableName();
		//注入的方法名   一定要与EmployeeMapper接口中的方法名一致
		String method = "deleteAll" ;
		
		//构造SqlSource对象
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		
		//构造一个删除的MappedStatement
		this.addDeleteMappedStatement(mapperClass, method, sqlSource);
		
	}
}

3) 在 MP 全局策略中，配置 自定义注入器
在spring配置文件中
	<!-- 定义自定义注入器 -->
	<bean id="mySqlInjector" class="com.atguigu.mp.injector.MySqlInjector"></bean>
	
将其装载在全局配置策略中
<!-- 定义MybatisPlus的全局策略配置-->
	<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">	
		<!-- Mysql 全局的主键策略 -->
		<!-- <property name="idType" value="0"></property> -->
		
		<!--注入自定义全局操作 -->
		<property name="sqlInjector" ref="mySqlInjector"></property>

	</bean>

```



### 自定义注入器的应用之 逻辑删除

```java
假删除、逻辑删除: 并不会真正的从数据库中将数据删除掉，而是将当前被删除的这条数据
中的一个逻辑删除字段置为删除状态.tbl_user logic_flag = 1

1) com.baomidou.mybatisplus.mapper.LogicSqlInjector (MyBatisPlus为为我们提供的逻辑删除注入器)
2) logicDeleteValue逻辑删除全局值 (LogicSqlInjector中需要配置)
3) logicNotDeleteValue 逻辑未删除全局值
<!-- 逻辑删除 -->
	<bean id="logicSqlInjector" class="com.baomidou.mybatisplus.mapper.LogicSqlInjector"></bean>
	<!-- 定义MybatisPlus的全局策略配置-->
	<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
	 	<!-- 注入逻辑删除 -->
	 	<property name="sqlInjector" ref="logicSqlInjector"></property>
	 	
	 	<!-- 注入逻辑删除全局值 -->
	 	<property name="logicDeleteValue" value = "-1"></property>
	 	<property name="logicNotDeleteValue" value="1"></property>
	</bean>
	
4) 在 POJO 的逻辑删除字段 添加 @TableLogic 注解
public class User extends Parent {
	//@TableId(type=IdType.INPUT)
	private Integer id  ;
	
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private String name ;
	
	@TableLogic   // 逻辑删除属性
	private Integer logicFlag ;
}
5) 会在 mp 自带查询和更新方法的 sql 后面，追加『逻辑删除字段』=『LogicNotDeleteValue默认值』 删除方法: deleteById()和其他 delete 方法, 底层 SQL 调用的是 update tbl_xxxset 『逻辑删除字段』=『logicDeleteValue默认值』
	/**
	 * 测试逻辑删除
	 */
	@Test
	public void testLogicDelete() {	
		Integer result = userMapper.deleteById(1); //这里自动会进行逻辑删除
		System.out.println("result:" +result );
		
		User user = userMapper.selectById(1); 
		//加入逻辑删除之后，在查询的时候会查询，『逻辑删除字段』=『LogicNotDeleteValue默认值』
		System.out.println(user);
	}
```

## 公共字段自动填充

在插入和修改的时候，我们某些字段没有设置值，我们希望公共字段进行默认填充

### 元数据处理器接口

```java
com.baomidou.mybatisplus.mapper.MetaObjectHandler
insertFill(MetaObject metaObject)
updateFill(MetaObject metaObject)
    
metaobject: 元对象. 是 Mybatis 提供的一个用于更加方便，更加优雅的访问对象的属性,给对象的属性设置值 的一个对象. 还会用于包装对象. 支持对 Object 、Map、Collection等对象进行包装

本质上 metaObject 获取对象的属性值或者是给对象的属性设置值，最终是要通过 Reflector 获取到属性的对应方法的 Invoker, 最终 invoke. (通过反射设值不太优雅，Mybatis使用MetaObject对这种方式进行一个封装)
    
共字段进行默认填充实现步骤：
1) 注解填充字段 @TableFile(fill = FieldFill.INSERT) 查看 FieldFill
	@TableField(fill=FieldFill.INSERT_UPDATE)
    private String name ;
2) 自定义公共字段填充处理器
/**
 * 自定义公共字段填充处理器
 */
public class MyMetaObjectHandler extends MetaObjectHandler {
	
	/**
	 * 插入操作 自动填充
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		//获取到需要被填充的字段的值
		Object fieldValue = getFieldValByName("name", metaObject);
		if(fieldValue == null) {
			System.out.println("*******插入操作 满足填充条件*********");
			setFieldValByName("name", "weiyunhui", metaObject);
		}
		
	}
	/**
	 * 修改操作 自动填充
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		Object fieldValue = getFieldValByName("name", metaObject);
		if(fieldValue == null) {
			System.out.println("*******修改操作 满足填充条件*********");
			setFieldValByName("name", "weiyh", metaObject);
		}
	}

}
3) MP 全局注入 自定义公共字段填充处理器
<!-- 公共字段填充 处理器 -->
	<bean id="myMetaObjectHandler" class="com.atguigu.mp.metaObjectHandler.MyMetaObjectHandler"> </bean>
<!-- 定义MybatisPlus的全局策略配置-->
	<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
	 	<!-- 注入公共字段填充处理器 -->
	 	<property name="metaObjectHandler" ref="myMetaObjectHandler"></property>
	</bean>
	
```



## Oracle 主键 Sequence

```java
MySQL: 支持主键自增。 IdType.Auto
Oracle: 序列(Sequence)
1) 实体类配置主键 Sequence @KeySequence(value=”序列名”，clazz=xxx.class 主键属性类型)
@KeySequence(value="seq_user",clazz=Integer.class)
public class User{} 
2) 全局 MP 主键生成策略为 IdType.INPUT
	<!-- 定义MybatisPlus的全局策略配置-->
	<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
		<!-- Oracle全局主键策略 -->
		<property name="idType" value="1"></property>

	 	<!-- 注入Oracle主键Sequence -->
	 	<property name="keyGenerator" ref="oracleKeyGenerator"></property>
	</bean>
3) 全局 MP 中配置 Oracle 主键 Sequence
com.baomidou.mybatisplus.incrementer.OracleKeyGenerator
<!-- 配置Oracle主键Sequence -->
	<bean id="oracleKeyGenerator" class="com.baomidou.mybatisplus.incrementer.OracleKeyGenerator"></bean>
4) 可以将@keySequence 定义在父类中，可实现多个子类对应的多个表公用一个 Sequence

```