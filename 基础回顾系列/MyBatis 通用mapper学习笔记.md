# 通用mapper学习笔记

https://github.com/abel533/Mapper/wiki/1.1-java

https://gitee.com/free/Mapper/wikis/3.config?sort_id=208207

```
本质是Mybatis的一个插件

作用：替我们生成常用增删改查操作的 SQL 语句。(通MyBatisPlus类似)

他与之前Mybatis的逆向工程之间的区别
Mybatis逆向工程生成的XXMapper.xml 在我们的实体属性名发生改变后也需要重新生成。
但是通用mapper可以帮我们省略掉XXMapper.xml文件，Po发生的修改，通用mapper动态搬我们生成的sql也会随之改变。不需要我们手动的进行修改

无论通用mapper还是MyBatisPlus都要求我们entity 在定义字段类型的时候使用包装类型，基本数据类型在 Java 类中都有默认值，会导致 MyBatis 在执行相关操作时很难判断当前字段是否为 null
```

通用mapper的原理是Mybatis，Mybatis的原理是jdbc。

## 集成通用mapper

### 在Mybatis+spring环境中集成通用mapper

原有的Mybatis+spring环境

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">
	
	<!-- 配置数据源 -->
	<context:property-placeholder location="classpath:jdbc.properties"/>

	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"/>
		<property name="password" value="${jdbc.password}"/>
		<property name="jdbcUrl" value="${jdbc.url}"/>
		<property name="driverClass" value="${jdbc.driver}"/>
	</bean>

	<!-- 整合MyBatis -->
	<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configLocation" value="classpath:mybatis-config.xml"/>
		<property name="dataSource" ref="dataSource"/>
	</bean>
	
	<!-- 整合通用Mapper所需要做的配置修改： -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.atguigu.mapper.mappers"/>
	</bean>

	<!-- 配置Service自动扫描的包 -->
	<context:component-scan base-package="com.atguigu.mapper.services"/>

	<!-- 配置声明式事务 -->
	<bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>

	<aop:config>
		<aop:advisor advice-ref="txAdvice" pointcut="execution(* *..*Service.*(..))"/>
	</aop:config>

	<tx:advice id="txAdvice" transaction-manager="dataSourceTransactionManager">
		<tx:attributes>
			<tx:method name="get*" read-only="true"/>
			<tx:method name="save*" rollback-for="java.lang.Exception" propagation="REQUIRES_NEW"/>
			<tx:method name="remove*" rollback-for="java.lang.Exception" propagation="REQUIRES_NEW"/>
			<tx:method name="update*" rollback-for="java.lang.Exception" propagation="REQUIRES_NEW"/>
		</tx:attributes>
	</tx:advice>
</beans>

```

整合通用mapper

```xml
1：加入 Maven 依赖信息
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper</artifactId>
    <version>4.0.0-beta3</version>
</dependency>
2: 修改 Spring 配置文件
<!-- 整合通用 Mapper 所需要做的配置修改： -->
<!-- 原始全类名：org.mybatis.spring.mapper.MapperScannerConfigurer -->
<!-- 通用 Mapper 使用：tk.mybatis.spring.mapper.MapperScannerConfigurer -->
<bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
<property name="basePackage" value="com.atguigu.mapper.mappers"/>
</bean>
他在扫描mapper接口包的时候使用自己的扫描包，将通用的mapper加入进来
```

使用通用mapper

```java
怎么样让我们的mapper使用通用mapper为我们自动生成的动态sql？
1：定义的mapper接口集成Mapper<T>
/**
* 具 体 操 作 数 据 库 的 Mapper 接 口 ， 需 要 继 承 通 用 Mapper 提 供 的 核 心 接 口 ：
Mapper<Employee>
* 泛型类型就是实体类的类型
*/
public interface EmployeeMapper extends Mapper<Employee> {

}
2：在数据库实体中使用@Table 注解声明实体对应的数据库表明
不加注解默认的规则是：实体类类名首字母小写作为表名。Employee 类→employee 表。
@Table(name="tabple_emp")
    public class Employee {
        
    }
3：实体类的属性字段需要使用驼峰命名规则，数据库表对应的字段的命名需要使用对应的下划线命名，
如：private Integer empId;//emp_id
	private String empName;//emp_name
否则就需要使用@Column 注解注解建立实体类属性和数据库表字段之间的对应关系
 	@Column(name="emp_salary_apple")
	private Double empSalary;//emp_salary_apple

4：通用 Mapper 在执行 xxxByPrimaryKey(key)方法时需要使用@Id 注解
在执行 xxxByPrimaryKey(key)方法时，有两种情况
情况 1：没有使用@Id 注解明确指定主键字段
SELECT emp_id,emp_name,emp_salary_apple,emp_age FROM tabple_emp WHERE emp_id = ?
AND emp_name = ? AND emp_salary_apple = ? AND emp_age = ?
生成上面这样的 WHERE 子句是因为通用 Mapper 将实体类中的所有字段都拿来放在一起作为联合主键。
情况 2：使用@Id 主键明确标记和数据库表中主键字段对应的实体类字段。
@Id
private Integer empId;//emp_id

5：如果想让通用 Mapper 在执行 insert 操作之后将数据库自动生成的主键值回写到实体类对象中
需要在实体主键字段上使用@GeneratedValue注解
支持自增的数据库：主键自增的
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer empId;//emp_id
不支持自增如在 Oracle，通过序列和任意 SQL 获取主键值
@Id
@GeneratedValue(
  strategy = GenerationType.IDENTITY,
  generator = "select SEQ_ID.nextval from dual")
private Integer id; 
除此之外也要配置一个 ORDER 全局参数，2.3.2 中提到的 AFTER。这个参数值需要配置为对应的数据库

除了 Oracle 这种外，还有一种更普遍的用法，就是使用 UUID。
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
private String id;

6：实体类中并不是所有属性都会在数据库表中被用到，这是需要使用@Transient注解：用于标记不与数据库表字段对应的实体类字段
@Transient
private String otherThings; //非数据库表中字段
```



### QBC查询

```java
Query By Criteria
Criteria 是 Criterion 的复数形式。意思是：规则、标准、准则。在 SQL 语句中相当于查询条件。
QBC 查询是将查询条件通过 Java 对象进行模块化封装。

@Test
	public void testSelectByExample() {
		
		//目标：WHERE (emp_salary>? AND emp_age<?) OR (emp_salary<? AND emp_age>?)
		//1.创建Example对象
		Example example = new Example(Employee.class);
		
		//***********************
		//i.设置排序信息
		example.orderBy("empSalary").asc().orderBy("empAge").desc();
		
		//ii.设置“去重”
		example.setDistinct(true);
		
		//iii.设置select字段
		example.selectProperties("empName","empSalary");
		
		//***********************
		
		//2.通过Example对象创建Criteria对象
		Criteria criteria01 = example.createCriteria();
		Criteria criteria02 = example.createCriteria();
		
		//3.在两个Criteria对象中分别设置查询条件
		//property参数：实体类的属性名
		//value参数：实体类的属性值
		criteria01.andGreaterThan("empSalary", 3000)
				  .andLessThan("empAge", 25);
		
		criteria02.andLessThan("empSalary", 5000)
				  .andGreaterThan("empAge", 30);
		
		//4.使用OR关键词组装两个Criteria对象
		example.or(criteria02);
		
		//5.执行查询
		List<Employee> empList = employeeService.getEmpListByExample(example);
		
		for (Employee employee : empList) {
			System.out.println(employee);
		}
	}
```



### 分页

selectByRowBounds() : 只是做了内存分页，并没有做物理分页(limit)

如果想真正的使用物理分页，还是需要使用分页插件



## 逆向工程

### 原生 MyBatis 逆向工程和通用 Mapper 逆向工程对比 逆向工程对比

![JAVA_MYBATIS26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS26.png?raw=true)

参考文档地址 参考文档地址
https://github.com/abel533/Mapper/wiki/4.1.mappergenerator

### 使用 Maven 执行MBG

pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.atguigu.mapper.mbg</groupId>
	<artifactId>Pro02MapperMBG</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<properties>
		<!-- ${basedir}引用工程根目录 -->
		<!-- targetJavaProject：声明存放源码的目录位置 -->
		<targetJavaProject>${basedir}/src/main/java</targetJavaProject>

		<!-- targetMapperPackage：声明MBG生成XxxMapper接口后存放的package位置 -->
		<targetMapperPackage>com.atguigu.shop.mappers</targetMapperPackage>

		<!-- targetModelPackage：声明MBG生成实体类后存放的package位置 -->
		<targetModelPackage>com.atguigu.shop.entities</targetModelPackage>

		<!-- targetResourcesProject：声明存放资源文件和XML配置文件的目录位置 -->
		<targetResourcesProject>${basedir}/src/main/resources</targetResourcesProject>

		<!-- targetXMLPackage：声明存放具体XxxMapper.xml文件的目录位置
          当然也可以声明多级目录，不过需要使用${basedir}/src/main/resources/mapper
                                           或者com/atguigu/shop/mappers
         -->
		<targetXMLPackage>mappers</targetXMLPackage>

		<!-- 通用Mapper的版本号 -->
		<mapper.version>4.0.0-beta3</mapper.version>

		<!-- MySQL驱动版本号 -->
		<mysql.version>5.1.37</mysql.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>tk.mybatis</groupId>
			<artifactId>mapper</artifactId>
			<version>4.0.0-beta3</version>
		</dependency>
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.2.8</version>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>${mysql.version}</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
                  <!--MBG工程-->
				<groupId>org.mybatis.generator</groupId>
				<artifactId>mybatis-generator-maven-plugin</artifactId>
				<version>1.3.2</version>

				<!-- 配置generatorConfig.xml配置文件的路径 -->
				<configuration>
					<configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
					<overwrite>true</overwrite>
					<verbose>true</verbose>
				</configuration>

				<!-- MBG插件的依赖信息 -->
				<dependencies>
					<dependency>
						<groupId>mysql</groupId>
						<artifactId>mysql-connector-java</artifactId>
						<version>${mysql.version}</version>
					</dependency>
					<dependency>
						<groupId>tk.mybatis</groupId>
						<artifactId>mapper</artifactId>
						<version>${mapper.version}</version>
					</dependency>
				</dependencies>
			</plugin>
		</plugins>
	</build>

</project>
```



generatorConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
	<!-- 引入外部属性文件 -->
	<properties resource="config.properties" />

	<context id="Mysql" targetRuntime="MyBatis3Simple"
		defaultModelType="flat">
		<property name="beginningDelimiter" value="`" />
		<property name="endingDelimiter" value="`" />

		<!-- 配置通用Mapper的MBG插件相关信息 -->
		<plugin type="${mapper.plugin}">
			<property name="mappers" value="${mapper.Mapper}" />
		</plugin>

		<!-- 配置连接数据库的基本信息 -->
		<jdbcConnection 
			driverClass="${jdbc.driverClass}"
			connectionURL="${jdbc.url}" 
			userId="${jdbc.user}" 
			password="${jdbc.password}">
		</jdbcConnection>
	
		<!-- 配置Java实体类存放位置 -->
		<javaModelGenerator 
			targetPackage="${targetModelPackage}"
			targetProject="${targetJavaProject}" />

		<!-- 配置XxxMapper.xml存放位置 -->
		<sqlMapGenerator 
			targetPackage="${targetXMLPackage}"
			targetProject="${targetResourcesProject}" />

		<!-- 配置XxxMapper.java存放位置 -->
		<javaClientGenerator 
			targetPackage="${targetMapperPackage}"
			targetProject="${targetJavaProject}" 
			type="XMLMAPPER" />

		<!-- 根据数据库表生成Java文件的相关规则 -->
		<!-- tableName="%"表示数据库中所有表都参与逆向工程，此时使用默认规则 -->
		<!-- 默认规则：table_dept→TableDept -->
		<!-- 不符合默认规则时需要使用tableName和domainObjectName两个属性明确指定 -->
		<table tableName="tabple_emp" domainObjectName="Employee">
			<!-- 配置主键生成策略 -->
			<generatedKey column="emp_id" sqlStatement="Mysql" identity="true" />
		</table>
	</context>
</generatorConfiguration>
```



config.properties

```java
# Database connection information
jdbc.driverClass = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/common_mapper
jdbc.user = root
jdbc.password = root

#c3p0
jdbc.maxPoolSize=50
jdbc.minPoolSize=10
jdbc.maxStatements=100
jdbc.testConnection=true

# mapper
mapper.plugin = tk.mybatis.mapper.generator.MapperPlugin
#mapper接口继承的那个通用mapper接口，可修改
mapper.Mapper = tk.mybatis.mapper.common.Mapper
```

执行命令：

```
运行：

在 pom.xml 这一级目录的命令行窗口执行 mvn mybatis-generator:generate即可（前提是配置了mvn）。
```

![JAVA_MYBATIS27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS27.png?raw=true)

## 通用Mapper接口扩展

这里的扩展是指增加通用 Mapper 没有提供的功能。

```java
举例：批量更新
仿照写一个批量 update。假设我们想生成下面这样的 SQL 语句
UPDATE tabple_emp SET emp_name=?,emp_age=?,emp_salary=? where emp_id=? ;
UPDATE tabple_emp SET emp_name=?,emp_age=?,emp_salary=? where emp_id=? ;
UPDATE tabple_emp SET emp_name=?,emp_age=?,emp_salary=? where emp_id=? ;

为了生成上面那样的 SQL 语句，我们需要使用到 MyBatis 的 foreach 标签。
<foreach collection="list" item="record" separator=";" >
    UPDATE tabple_emp
    SET emp_name=#{record.empName},
    emp_age=#{record.empAge},
    emp_salary=#{record.empSalary}
    where emp_id=#{record.empId}
</foreach>

```

![JAVA_MYBATIS28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS28.png?raw=true)

```java
具体代码和步骤
1：自定义我们定义的mapper接口需要继承的通用mapper
public interface MyMapper<T> 
		extends SelectAllMapper<T>,SelectByExampleMapper<T>,MyBatchUpdateMapper<T> {
}
2：让我们的mapper接口继承上面这个mapper接口，并在配置文件配置这个自定义mapper
	<!-- 在整合通用Mapper所需要做的配置修改： -->
	<!-- 在properties 属性中配置mappers-->
	<bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.atguigu.mapper.mappers"/>
		<property name="properties">
			<value>
				mappers=com.atguigu.mapper.mine_mappers.MyMapper
			</value>
		</property>
	</bean>

3：为我们自定义的通用mapper自由组合其需要具有的功能，并定义我们需要扩展的功能
public interface MyBatchUpdateMapper<T> {	
	@UpdateProvider(type=MyBatchUpdateProvider.class, method="dynamicSQL")
	void batchUpdate(List<T> list);

}

4：定义我们扩展的功能实现提供者，拼装特殊sql
public class MyBatchUpdateProvider extends MapperTemplate {

	public MyBatchUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	/*
		<foreach collection="list" item="record" separator=";" >
			UPDATE tabple_emp
			<set>
				emp_name=#{record.empName},
				emp_age=#{record.empAge},
				emp_salary=#{record.empSalary},
			</set>
			where emp_id=#{record.empId}
		</foreach>
	 */
	public String batchUpdate(MappedStatement statement) {
		
		//1.创建StringBuilder用于拼接SQL语句的各个组成部分
		StringBuilder builder = new StringBuilder();
		
		//2.拼接foreach标签
		builder.append("<foreach collection=\"list\" item=\"record\" separator=\";\" >");
		
		//3.获取实体类对应的Class对象
		Class<?> entityClass = super.getEntityClass(statement);
		
		//4.获取实体类在数据库中对应的表名
		String tableName = super.tableName(entityClass);
		
		//5.生成update子句
		String updateClause = SqlHelper.updateTable(entityClass, tableName);
		
		builder.append(updateClause);
		
		builder.append("<set>");
		
		//6.获取所有字段信息
		Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
		
		String idColumn = null;
		String idHolder = null;
		
		for (EntityColumn entityColumn : columns) {
			
			boolean isPrimaryKey = entityColumn.isId();
			
			//7.判断当前字段是否为主键
			if(isPrimaryKey) {
				
				//8.缓存主键的字段名和字段值
				idColumn = entityColumn.getColumn();
				
				//※返回格式如:#{record.age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
				idHolder = entityColumn.getColumnHolder("record");
				
			}else {
				
				//9.使用非主键字段拼接SET子句
				String column = entityColumn.getColumn();
				String columnHolder = entityColumn.getColumnHolder("record");
				
				builder.append(column).append("=").append(columnHolder).append(",");
				
			}
			
		}
		
		builder.append("</set>");
		
		//10.使用前面缓存的主键名、主键值拼接where子句
		builder.append("where ").append(idColumn).append("=").append(idHolder);
		
		builder.append("</foreach>");
		
		//11.将拼接好的字符串返回
		return builder.toString();
	}

}

5:如果要使用批量操作需要修改数据库连接信息
jdbc.url=jdbc:mysql://localhost:3306/common_mapper?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
在jdbc.url 后面加上&allowMultiQueries=true
```

## 通用mapper的二级缓存

只做了解，现在做缓存都使用的是Redis等

```java
1：mybatis-config.xml中设置开启缓存
<settings>
    <setting name="cacheEnabled" value="true"/>
</settings>
2:在 XxxMapper 接口上使用@CacheNamespace 注解
@CacheNamespace
public interface EmployeeMapper extends MyMapper<Employee> {
}
3:实体实现序列化
public class Employee implements Serializable {}
```

## 类型转换器

![JAVA_MYBATIS29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS29.png?raw=true)

 通用mapper默认是不会处理复杂类型(忽略)，在查询，插入等操作的时候传值都是null

```
如果我们需要让通用mapper对复杂类型(实际开发中通常需要)进行处理
1：一种解决方式是： 复杂类型是一张表，两者建立关联关系
2： 另一种解决方式是： 复杂类型只是主表的一个字段(多个值用一个字符串存储，用分隔符分割)就需要自定义类型处理器
```

如何自定义类型处理器

```java
1: 参照通用mapper已有的类型转换器，继承TypeHandler 接口
这个接口有几个抽象方法需要我们来实现
public interface TypeHandler<T> {
    //将 parameter(java类型) 设置到 ps 对象中（jdbc类型），位置是 i
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws
    SQLException;
    //根据列名从 ResultSet 中获取数据，通常是字符串形式
    //将jdbc类型还原为 Java 对象，以 T 类型返回
    T getResult(ResultSet rs, String columnName) throws SQLException;
    T getResult(ResultSet rs, int columnIndex) throws SQLException;
    T getResult(CallableStatement cs, int columnIndex) throws SQLException;
}

2：自己实现这个接口，生成我们需要的类型处理器
public class AddressTypeHandler extends BaseTypeHandler<Address> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Address address, JdbcType jdbcType)
			throws SQLException {
		
		//1.对address对象进行验证
		if(address == null) {
			return ;
		}
		
		//2.从address对象中取出具体数据
		String province = address.getProvince();
		String city = address.getCity();
		String street = address.getStreet();
		
		//3.拼装成一个字符串
		//规则：各个值之间使用“,”分开
		StringBuilder builder = new StringBuilder();
		builder
			.append(province)
			.append(",")
			.append(city)
			.append(",")
			.append(street);
		
		String parameterValue = builder.toString();
		
		//4.设置参数
		ps.setString(i, parameterValue);
		
	}

	@Override
	public Address getNullableResult(ResultSet rs, String columnName) throws SQLException {
		
		//1.根据字段名从rs对象中获取字段值
		String columnValue = rs.getString(columnName);
		
		//2.验证columnValue是否有效
		if(columnValue == null || columnValue.length() == 0 || !columnValue.contains(",")) {
			return null;
		}
		
		//3.根据“,”对columnValue进行拆分
		String[] split = columnValue.split(",");
		
		//4.从拆分结果数组中获取Address需要的具体数据
		String province = split[0];
		String city = split[1];
		String street = split[2];
		
		//5.根据具体对象组装一个Address对象
		Address address = new Address(province, city, street);
		
		return address;
	}

	@Override
	public Address getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		//1.根据字段名从rs对象中获取字段值
		String columnValue = rs.getString(columnIndex);
		
		//2.验证columnValue是否有效
		if(columnValue == null || columnValue.length() == 0 || !columnValue.contains(",")) {
			return null;
		}
		
		//3.根据“,”对columnValue进行拆分
		String[] split = columnValue.split(",");
		
		//4.从拆分结果数组中获取Address需要的具体数据
		String province = split[0];
		String city = split[1];
		String street = split[2];
		
		//5.根据具体对象组装一个Address对象
		Address address = new Address(province, city, street);
		
		return address;
	}

	@Override
	public Address getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		//1.根据字段名从rs对象中获取字段值
		String columnValue = cs.getString(columnIndex);
		
		//2.验证columnValue是否有效
		if(columnValue == null || columnValue.length() == 0 || !columnValue.contains(",")) {
			return null;
		}
		
		//3.根据“,”对columnValue进行拆分
		String[] split = columnValue.split(",");
		
		//4.从拆分结果数组中获取Address需要的具体数据
		String province = split[0];
		String city = split[1];
		String street = split[2];
		
		//5.根据具体对象组装一个Address对象
		Address address = new Address(province, city, street);
		
		return address;
	}

}

3: 注册并使用我们的自定义类型转换器
方法一 字段级别：@ColumnType 注解
@Table(name="table_user")
public class User {
	@Id
	private Integer userId;
	private String userName;	
	@ColumnType(typeHandler=AddressTypeHandler.class)
	private Address address;
}

方法二 全局级别：在 MyBatis 配置文件中配置 typeHandlers
1：在mybatis-config.xml中进行劝解配置
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<typeHandlers>
		<!-- handler属性：指定自定义类型转换器全类名 -->
		<!-- javaType属性：指定需要使用“自定义类型转换器”进行类型处理的实体类型 -->
		<typeHandler 
			handler="com.atguigu.mapper.handlers.AddressTypeHandler"
			javaType="com.atguigu.mapper.entities.Address"/>
	</typeHandlers>
</configuration>

2:在实体类中正常处理字段
@Table(name="table_user")
public class User {
	@Id
	private Integer userId;
	private String userName;
	@Column
	private Address address;
}
```

### 处理枚举类型

```java
通用mapper处理枚举类型有两种方法
一：让通用 Mapper 把枚举类型作为简单类型处理
使用这种方法，需要在spring配置中增加一个通用 Mapper 的配置项
在 Spring 配置文件中找到 MapperScannerConfigurer
	<bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.atguigu.mapper.mappers"/>
		<property name="properties">
			<value>
				enumAsSimpleType=true
                  // 有多个配置，这里配置多行，一行一个配置，都是key=value格式
                  //如果使用默认值就不用在这里配置
			</value>
		</property>
	</bean>
本质上使用了 org.apache.ibatis.type.EnumTypeHandler<E>

二：为枚举类型配置对应的类型处理器(使用自定义的类型处理器或者是内置的类型处理器)
内置的类型处理器：
org.apache.ibatis.type.EnumTypeHandler<E> 在数据库中存储枚举值本身
org.apache.ibatis.type.EnumOrdinalTypeHandler<E> 在数据库中仅仅存储枚举值的索引
内置枚举类型处理器注册
不能使用@ColumnType 注解 注册在对应字段中，泛型异常
需要在 MyBatis 配置文件中配置专门的类型处理器并在字段上使用@Column 注解
加@Column 注解的作用是让通用 Mapper 不忽略枚举类型
<typeHandlers>		
		<typeHandler 
			handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"
			javaType="com.atguigu.mapper.entities.SeasonEnum"/>
			
	</typeHandlers>
```



　
