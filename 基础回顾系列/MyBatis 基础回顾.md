# MyBatis基础回顾

他是一个优秀的数据库持久化框架

![JAVA_MYBATIS1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS1.png?raw=true)

参考mybatise文档（http://www.mybatis.org/mybatis-3/zh/index.html）

源码库（http://github.com/mybatis/mybatis-3）

## mybatis如何使用

```
java程序与数据库通过SqlSessionFactory(可以理解为java描述的数据库)沟通 ，从 SqlSessionFactory 中获取 SqlSession，session (数据库的一次连接)

1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
2、sql映射文件；配置了每一个sql，以及sql的封装规则等。 
3、将sql映射文件注册在全局配置文件中
4、写代码：
   1）、根据全局配置文件得到SqlSessionFactory；
   2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
	一个sqlSession就是代表和数据库的一次会话，用完关闭
   3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。

定义Mapper接口来申明sql的参数和返回值(接口可以与Mapper.xml 文件动态绑定
通过namespace:名称空间;指定为接口的全类名
可以理解为xml文件就是接口的实现类
)

1、接口式编程
  	原生：		Dao		====>  DaoImpl
  	mybatis：	Mapper	====>  xxMapper.xml
  
 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。（将接口和xml进行绑定）
 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 		(这里生成的empMapper 是一个代理对象)
5、两个重要的配置文件：
 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
		sql映射文件：保存了每一个sql语句的映射信息：将sql抽取出来。
				
```

### mybatis的全局配置文件：mybatis-config.xml

```xml
http://mybatis.org/dtd/mybatis-3-config.dtd (开发时可以在IDE中引入这个dtd约束)
mybatise全局配置文件标签的约束
<!DOCTYPE configuration
 PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!--
		1、mybatis可以使用properties来引入外部properties配置文件的内容；
		resource：引入类路径下的资源
		url：引入网络路径或者磁盘路径下的资源
	  -->
	<properties resource="dbconfig.properties"></properties>
	
	
	<!-- 
		2、settings包含很多重要的设置项
		setting:用来设置每一个设置项
			name：设置项名
			value：设置项取值
	 -->
	<settings>
		<setting name="mapUnderscoreToCamelCase" value="true"/>
	</settings>
	
	
	<!-- 3、typeAliases：别名处理器：可以为我们的java类型起别名，别名不区分大小写 
			 (以后xml中写全类名的地方就可以使用别名代替)
	-->
	<typeAliases>
		<!-- 1、typeAlias:为某个java类型起别名
				type:指定要起别名的类型全类名;默认别名就是类名小写；employee
				alias:指定新的别名
		 -->
		<!-- <typeAlias type="com.atguigu.mybatis.bean.Employee" alias="emp"/> -->
		
		<!-- 2、package:为某个包下的所有类批量起别名 
				name：指定包名（为当前包以及下面所有的后代包的每一个类都起一个默认别名（类名小写），）
		-->
		<package name="com.atguigu.mybatis.bean"/>
		
		<!-- 3、批量起别名的情况下，使用 @Alias注解为某个类型指定新的别名 -->
	</typeAliases>
		
	<!-- 
		4、environments：环境们，mybatis可以配置多种环境 ,default指定使用某种环境。可以达到快速切换环境。
			environment：配置一个具体的环境信息；必须有两个标签；id代表当前环境的唯一标识
				transactionManager：事务管理器；
					type：事务管理器的类型;JDBC(JdbcTransactionFactory)|MANAGED(ManagedTransactionFactory)
						自定义事务管理器：实现TransactionFactory接口.type指定为全类名
				
				dataSource：数据源;
					type:数据源类型;UNPOOLED(UnpooledDataSourceFactory)
								|POOLED(PooledDataSourceFactory)
								|JNDI(JndiDataSourceFactory)
					自定义数据源：实现DataSourceFactory接口，type是全类名
		 -->
		 

		 
	<environments default="dev_mysql">
		<environment id="dev_mysql">
			<transactionManager type="JDBC"></transactionManager>
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driver}" />
				<property name="url" value="${jdbc.url}" />
				<property name="username" value="${jdbc.username}" />
				<property name="password" value="${jdbc.password}" />
			</dataSource>
		</environment>
	
		<environment id="dev_oracle">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="${orcl.driver}" />
				<property name="url" value="${orcl.url}" />
				<property name="username" value="${orcl.username}" />
				<property name="password" value="${orcl.password}" />
			</dataSource>
		</environment>
	</environments>
	
	
	<!-- 5、databaseIdProvider：支持多数据库厂商的；
		 type="DB_VENDOR"：VendorDatabaseIdProvider
		 	作用就是得到数据库厂商的标识(驱动getDatabaseProductName())，mybatis就能根据数据库厂商标识来执行不同的sql;
		 	MySQL，Oracle，SQL Server,xxxx
	  -->
	<databaseIdProvider type="DB_VENDOR">
		<!-- 为不同的数据库厂商起别名 -->
		<property name="MySQL" value="mysql"/>
		<property name="Oracle" value="oracle"/>
		<property name="SQL Server" value="sqlserver"/>
	</databaseIdProvider>
	
	
	<!-- 将我们写好的sql映射文件（EmployeeMapper.xml）一定要注册到全局配置文件（mybatis-config.xml）中 -->
	<!-- 6、mappers：将sql映射注册到全局配置中 -->
	<mappers>
		<!-- 
			mapper:注册一个sql映射 
				注册配置文件
				resource：引用类路径下的sql映射文件
					mybatis/mapper/EmployeeMapper.xml
				url：引用网路路径或者磁盘路径下的sql映射文件
					file:///var/mappers/AuthorMapper.xml
					
				注册接口
				class：引用（注册）接口，
					1、有sql映射文件，映射文件名必须和接口同名，并且放在与接口同一目录下；
					2、没有sql映射文件，所有的sql都是利用注解写在接口上;
					推荐：
						比较重要的，复杂的Dao接口我们来写sql映射文件
						不重要，简单的Dao接口为了开发快速可以使用注解；
		-->
        
		<!-- <mapper resource="mybatis/mapper/EmployeeMapper.xml"/> -->
		<!-- <mapper class="com.atguigu.mybatis.dao.EmployeeMapperAnnotation"/> -->
		
		<!-- 批量注册： -->
		<package name="com.atguigu.mybatis.dao"/>
        
        <!--
      mappers 中通常需要注册接口： 接口和配置文件成对出现，或者是直接扫描包(他也是需要接口和xml在同一个包下)
         	<!-- <mapper resource="mybatis/mapper/EmployeeMapper.xml"/> -->
		   <!-- <mapper class="com.atguigu.mybatis.dao.EmployeeMapperAnnotation"/> -->
      xml也可以单独出现： 但是在使用上就不能用接口调用
      而是 Employee employee = openSession.selectOne(
					"com.atguigu.mybatis.EmployeeMapper.selectEmp", 1);
        -->
	</mappers>
</configuration>
```



### mybatis sql映射文件

**注意： 在各种持久化框架的数据库实体中添加一个有参的构造器一定要确保实体中存在一个无参的构造器。**

```xml
增删改
1、mybatis允许增删改直接定义以下类型返回值
	  		Integer、Long、Boolean、void
增删改返回的是影响多少行，如果大于0行，则返回true
(mybatis会自动进行封装)

2：insert获取自增主键值
	<!-- public void addEmp(Employee employee); -->
	<!-- parameterType：参数类型，可以省略， 
	获取自增主键的值：
		mysql支持自增主键，自增主键值的获取，mybatis也是利用JDBC statement.getGenreatedKeys()；
		useGeneratedKeys="true"；使用自增主键获取主键值策略
		keyProperty；指定对应的主键属性，也就是mybatis获取到主键值以后，将这个值封装给javaBean的哪个属性
	-->
	<insert id="addEmp" parameterType="com.atguigu.mybatis.bean.Employee"
		useGeneratedKeys="true" keyProperty="id" databaseId="mysql">
		insert into tbl_employee(last_name,email,gender) 
		values(#{lastName},#{email},#{gender})
	</insert>
3： 	Oracle不支持自增；Oracle使用序列来模拟自增； 获取主键值
其他非自增主键值的获取也使用该方法。--将查询主键的sql卸载insert中。
	<!-- 
	获取非自增主键的值：
		Oracle不支持自增；Oracle使用序列来模拟自增；
		每次插入的数据的主键是从序列中拿到的值；如何获取到这个值；
	 -->
	<insert id="addEmp" databaseId="oracle">
		<!-- 
		keyProperty:查出的主键值封装给javaBean的哪个属性
		order="BEFORE":当前sql在插入sql之前运行
			   AFTER：当前sql在插入sql之后运行
		resultType:查出的数据的返回值类型
		
		BEFORE运行顺序：
			先运行selectKey查询id的sql；查出id值封装给javaBean的id属性
			在运行插入的sql；就可以取出id属性对应的值
		AFTER运行顺序：
			先运行插入的sql（从序列中取出新值作为id）；
			再运行selectKey查询id的sql；
		 -->
		<selectKey keyProperty="id" order="BEFORE" resultType="Integer">
			<!-- 编写查询主键的sql语句 -->
			<!-- BEFORE-->
			select EMPLOYEES_SEQ.nextval from dual 
			<!-- AFTER：
			 select EMPLOYEES_SEQ.currval from dual -->
		</selectKey>
		
		<!-- 插入时的主键是从序列中拿到的 -->
		<!-- BEFORE:-->
		insert into employees(EMPLOYEE_ID,LAST_NAME,EMAIL) 
		values(#{id},#{lastName},#{email<!-- ,jdbcType=NULL -->}) 
		<!-- AFTER：
		insert into employees(EMPLOYEE_ID,LAST_NAME,EMAIL) 
		values(employees_seq.nextval,#{lastName},#{email}) -->
	</insert>
```



#### mybatis参数处理

```java
单个参数：mybatis不会做特殊处理， 只传入了一个值，#{任意名} 就可以取出传入的值
	#{参数名/任意名}：取出参数值。
	
多个参数：mybatis会做特殊处理。
	多个参数会被封装成 一个map，
		key：param1...paramN,或者参数的索引也可以
		value：传入的参数值
	#{}就是从map中获取指定的key的值；
	
	操作：
		方法：public Employee getEmpByIdAndLastName(Integer id,String lastName);
		取值：#{id},#{lastName}
	异常：
	org.apache.ibatis.binding.BindingException: 
	Parameter 'id' not found. 
	Available parameters are [1, 0, param1, param2]
	
【命名参数】：明确指定封装参数时map的key；@Param("id")
	多个参数会被封装成 一个map，
		key：使用@Param注解指定的值
		value：参数值
	#{指定的key}取出对应的参数值


POJO：
如果多个参数正好是我们业务逻辑的数据模型，我们就可以直接传入pojo；
	#{属性名}：取出传入的pojo的属性值	

Map：
如果多个参数不是业务模型中的数据，没有对应的pojo，不经常使用，为了方便，我们也可以传入map
	#{key}：取出map中对应的值

TO：
如果多个参数不是业务模型中的数据，但是经常要使用，推荐来编写一个TO（Transfer Object）数据传输对象
Page{
	int index;
	int size;
}

========================思考================================	
public Employee getEmp(@Param("id")Integer id,String lastName);
	取值：id==>#{id/param1}   lastName==>#{param2}

public Employee getEmp(Integer id,@Param("e")Employee emp);
	取值：id==>#{param1}    lastName===>#{param2.lastName/e.lastName}
//注意： 这里的Integer id,@Param("e")Employee emp会被封装在map中

##特别注意：如果是Collection（List、Set）类型或者是数组，
		 也会特殊处理。也是把传入的list或者数组封装在map中。
			key：Collection（collection）,如果是List还可以使用这个key(list)
				数组(array)
public Employee getEmpById(List<Integer> ids);
	取值：取出第一个id的值：   #{list[0]}
	
========================结合源码，mybatis怎么处理参数==========================
总结：参数多时会封装map，为了不混乱，我们可以使用@Param来指定封装时使用的key；
#{key}就可以取出map中的值；

(@Param("id")Integer id,@Param("lastName")String lastName);
ParamNameResolver解析参数封装map的；
//1、names：{0=id, 1=lastName}；构造器的时候就确定好了

	确定流程：
	1.获取每个标了param注解的参数的@Param的值：id，lastName；  赋值给name;
	2.每次解析一个参数给map中保存信息：（key：参数索引，value：name的值）
		name的值：
			标注了param注解：注解的值
			没有标注：
				1.全局配置：useActualParamName（jdk1.8）：name=参数名
				2.name=map.size()；相当于当前元素的索引
	{0=id, 1=lastName,2=2}
				

args【1，"Tom",'hello'】:

public Object getNamedParams(Object[] args) {
    final int paramCount = names.size();
    //1、参数为null直接返回
    if (args == null || paramCount == 0) {
      return null;
     
    //2、如果只有一个元素，并且没有Param注解；args[0]：单个参数直接返回
    } else if (!hasParamAnnotation && paramCount == 1) {
      return args[names.firstKey()];
      
    //3、多个元素或者有Param标注
    } else {
      final Map<String, Object> param = new ParamMap<Object>();
      int i = 0;
      
      //4、遍历names集合；{0=id, 1=lastName,2=2}
      for (Map.Entry<Integer, String> entry : names.entrySet()) {
      
      	//names集合的value作为key;  names集合的key又作为取值的参考args[0]:args【1，"Tom"】:
      	//eg:{id=args[0]:1,lastName=args[1]:Tom,2=args[2]}
        param.put(entry.getValue(), args[entry.getKey()]);
        
        
        // add generic param names (param1, param2, ...)param
        //额外的将每一个参数也保存到map中，使用新的key：param1...paramN
        //效果：有Param注解可以#{指定的key}，或者#{param1}
        final String genericParamName = GENERIC_NAME_PREFIX + String.valueOf(i + 1);
        // ensure not to overwrite parameter named with @Param
        if (!names.containsValue(genericParamName)) {
          param.put(genericParamName, args[entry.getKey()]);
        }
        i++;
      }
      return param;
    }
  }
}
===========================参数值的获取======================================
#{}：可以获取map中的值或者pojo对象属性的值；
${}：可以获取map中的值或者pojo对象属性的值；


select * from tbl_employee where id=${id} and last_name=#{lastName}
Preparing: select * from tbl_employee where id=2 and last_name=?
	区别：
		#{}:是以预编译的形式，将参数设置到sql语句中；PreparedStatement；防止sql注入
		${}:取出的值直接拼装在sql语句中；会有安全问题；sql注入
		大多情况下，我们去参数的值都应该去使用#{}；
		
		原生jdbc不支持占位符的地方我们就可以使用${}进行取值
		比如分表、排序。。。；按照年份分表拆分
			select * from ${year}_salary where xxx;
			select * from tbl_employee order by ${f_name} ${order}

#{}:更丰富的用法：
	规定参数的一些规则：
	javaType、 jdbcType、 mode（存储过程）、 numericScale、
	resultMap、 typeHandler、 jdbcTypeName、 expression（未来准备支持的功能）；

	jdbcType通常需要在某种特定的条件下被设置：
		在我们数据为null的时候，有些数据库可能不能识别mybatis对null的默认处理。比如Oracle（报错）；
		
		JdbcType OTHER：无效的类型；因为mybatis对所有的null都映射的是原生Jdbc的OTHER类型，oracle不能正确处理;
		
		由于全局配置中：jdbcTypeForNull=OTHER；oracle不支持；两种办法
		1、#{email,jdbcType=OTHER};
		2、jdbcTypeForNull=NULL
			<setting name="jdbcTypeForNull" value="NULL"/>
```

#### select将查询记录封装为Map返回

```xml
//返回一条记录的map；key就是列名，值就是对应的值
public Map<String, Object> getEmpByIdReturnMap(Integer id);
    
====xml    
 	<!--public Map<String, Object> getEmpByIdReturnMap(Integer id);  -->
 	<select id="getEmpByIdReturnMap" resultType="map">
 		select * from tbl_employee where id=#{id}
 	</select>
    
//多条记录封装一个map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
	//@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
	@MapKey("lastName")
	public Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);
	
====xml
	<!--public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);  -->
 	<select id="getEmpByLastNameLikeReturnMap" resultType="com.atguigu.mybatis.bean.Employee">
 		select * from tbl_employee where last_name like #{lastName}
 	</select>
```

#### `resultMap`自定义结果映射规则

出现的原因： 当mybatis定义的自动结果映射和resultType不能满足我们的复杂要求了

resultMap:自定义结果集映射规则；

```xml
	<!-- public Employee getEmpById(Integer id); -->
	<select id="getEmpById"  resultMap="MySimpleEmp">
		select * from tbl_employee where id=#{id}
	</select>
	<!--自定义某个javaBean的封装规则: 这个bean中那个属性对应那个字段，由自定义决定
	type：自定义规则的Java类型
	id:唯一id方便引用
	  -->
	<resultMap type="com.atguigu.mybatis.bean.Employee" id="MySimpleEmp">
		<!--指定主键列的封装规则
		虽说也可以使用result 定义主键类，但是id定义主键会底层有优化； 
		column：指定哪一列
		property：指定对应的javaBean属性
		  -->
		<id column="id" property="id"/>
		<!-- 定义普通列封装规则 -->
		<result column="last_name" property="lastName"/>
		<!-- 其他不指定的列也会自动封装：我们只要写resultMap就把全部的映射规则都写上。 -->
		<result column="email" property="email"/>
		<result column="gender" property="gender"/>
	</resultMap>
```

resultMap 映射map中不指定其他列会按照默认规则，自动进行封装

![JAVA_MYBATIS2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS2.png?raw=true)

##### 关联查询

```xml
public class Employee {	
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	private Department dept;
}
<!-- 
	场景一：
		查询Employee的同时查询员工对应的部门
		Employee===Department 一对一
		一个员工有与之对应的部门信息；
		id  last_name  gender    d_id     did  dept_name (private Department dept;)
	 -->
	<!--  public Employee getEmpAndDept(Integer id);-->
	<select id="getEmpAndDept" resultMap="MyDifEmp">
		SELECT e.id id
               ,e.last_name last_name
        	  ,e.gender gender
        	  ,e.d_id d_id
       		  ,d.id did
              ,d.dept_name dept_name 
         FROM tbl_employee e,tbl_dept d
		WHERE e.d_id=d.id AND e.id=#{id}
	</select>
方法一：
	<!--
		联合查询：级联属性封装结果集
     id  last_name  gender    d_id     did  dept_name (private Department dept;)
	  -->
	<resultMap type="com.atguigu.mybatis.bean.Employee" id="MyDifEmp">
		<id column="id" property="id"/>
		<result column="last_name" property="lastName"/>
		<result column="gender" property="gender"/>
		<result column="did" property="dept.id"/>
		<result column="dept_name" property="dept.departmentName"/>
	</resultMap>
方法二：
	<!-- 
		使用association定义关联的单个对象的封装规则；
	 -->
	<resultMap type="com.atguigu.mybatis.bean.Employee" id="MyDifEmp2">
		<id column="id" property="id"/>
		<result column="last_name" property="lastName"/>
		<result column="gender" property="gender"/>
		
		<!--  association可以指定联合的javaBean对象
		property="dept"：指定哪个属性是联合的对象
		javaType:指定这个属性对象的类型[不能省略]
		-->
		<association property="dept" javaType="com.atguigu.mybatis.bean.Department">
             //这里的 column 不能也定义为id，会和上面的重复，这里的列名column对应着sql查询出来的列名
			<id column="did" property="id"/>
			<result column="dept_name" property="departmentName"/>
		</association>
	</resultMap>
方法三：  将原来的一步分成几步
 <!--  public Employee getEmpByIdStep(Integer id);-->
	 <select id="getEmpByIdStep" resultMap="MyEmpByStep">
	 	select * from tbl_employee where id=#{id}
	 	<if test="_parameter!=null">
	 		and 1=1
	 	</if>
	 </select>
	<!-- 使用association进行分步查询：
		1、先按照员工id查询员工信息
		2、根据查询员工信息中的d_id值去部门表查出部门信息
		3、部门设置到员工中；
	 -->
	 
	 <!--  id  last_name  email   gender    d_id   -->
	 <resultMap type="com.atguigu.mybatis.bean.Employee" id="MyEmpByStep">
	 	<id column="id" property="id"/>
	 	<result column="last_name" property="lastName"/>
	 	<result column="email" property="email"/>
	 	<result column="gender" property="gender"/>
	 	<!-- association定义关联对象的封装规则
	 		select:表明当前属性是调用select指定的方法查出的结果
	 		column:指定将哪一列的值传给这个方法 (这一列的值是通过外层select查询出来的)
	 	
	 	流程：使用select指定的方法（传入column指定的这列参数的值）查出对象，并封装给property指定的属性
      会发两条sql语句(并不特别推荐，但是他有两个好处： 1： 使用已有的方法来组合查询  2： 可以使用延迟加载)
	 	 -->
 		<association property="dept" 
	 		select="com.atguigu.mybatis.dao.DepartmentMapper.getDeptById"
	 		column="d_id">
 		</association>
	 </resultMap>
    <!-- 可以使用延迟加载（懒加载）；(按需加载)
	 	Employee==>Dept：
	 		我们每次查询Employee对象的时候，都将一起查询出来。
	 		部门信息在我们使用的时候再去查询；
	 		分段查询的基础之上加上两个配置：
	  -->
在全局配置文件中开启懒加载，禁用完整加载：
<setting name="lazyLoadingEnabled" value="true"/>
<setting name="aggressiveLazyLoading" value="false"/>
```

一对多

```xml
<!-- 
	场景二：
		查询部门的时候将部门对应的所有员工信息也查询出来：注释在DepartmentMapper.xml中
         一对多： 一个部门中存在多个员工
	public class Department {
			private Integer id;
			private String departmentName;
			private List<Employee> emps;
	  did  dept_name  ||  eid  last_name  email   gender  
	 -->
	
	<!-- public Department getDeptByIdPlus(Integer id); -->
	<select id="getDeptByIdPlus" resultMap="MyDept">
		SELECT d.id did
        	   ,d.dept_name dept_name
        	   ,e.id eid
        	   ,e.last_name last_name
        	   ,e.email email
        	   ,e.gender gender
		FROM tbl_dept d
		LEFT JOIN tbl_employee e
		ON d.id=e.d_id
		WHERE d.id=#{id}
	</select>

方法一：
	<!--嵌套结果集的方式，使用collection标签定义关联的集合类型的属性封装规则  -->
	<resultMap type="com.atguigu.mybatis.bean.Department" id="MyDept">
		<id column="did" property="id"/>
		<result column="dept_name" property="departmentName"/>
		<!-- 
			collection定义关联集合类型的属性的封装规则 
			ofType:指定集合里面元素的类型
		-->
		<collection property="emps" ofType="com.atguigu.mybatis.bean.Employee">
			<!-- 定义这个集合中元素的封装规则 -->
			<id column="eid" property="id"/>
			<result column="last_name" property="lastName"/>
			<result column="email" property="email"/>
			<result column="gender" property="gender"/>
		</collection>
	</resultMap>
方法二：
   <!-- EmployeeMapperPlus.getEmpsByDeptId -->
	<!-- public List<Employee> getEmpsByDeptId(Integer deptId); -->
	<select id="getEmpsByDeptId" resultType="com.atguigu.mybatis.bean.Employee">
		select * from tbl_employee where d_id=#{deptId}
	</select>
 
   	<!-- public Department getDeptByIdStep(Integer id); -->
	<select id="getDeptByIdStep" resultMap="MyDeptStep">
		select id,dept_name from tbl_dept where id=#{id}
	</select>
	<!-- collection：分段查询 -->
	<resultMap type="com.atguigu.mybatis.bean.Department" id="MyDeptStep">
		<id column="id" property="id"/>
		<id column="dept_name" property="departmentName"/>
		<collection property="emps" 
			select="com.atguigu.mybatis.dao.EmployeeMapperPlus.getEmpsByDeptId"
			column="{id}" fetchType="lazy"></collection>
	</resultMap>

扩展：
	<!-- 扩展：多列的值传递过去：
			将多列的值封装map传递；
			column="{key1=column1,key2=column2}"
              {deptId=id}
		fetchType="lazy"：表示使用延迟加载；
				- lazy：延迟
				- eager：立即
	 -->
```

##### 鉴别器

鉴别器：mybatis可以使用discriminator判断某列的值，然后根据某列的值改变封装行为

类似于if判断逻辑

```xml
<!-- <discriminator javaType=""></discriminator>
		鉴别器：mybatis可以使用discriminator判断某列的值，然后根据某列的值改变封装行为
		封装Employee：
			如果查出的是女生：就把部门信息查询出来，否则不查询；
			如果是男生，把last_name这一列的值赋值给email;
	 -->
	 <resultMap type="com.atguigu.mybatis.bean.Employee" id="MyEmpDis">
	 	<id column="id" property="id"/>
	 	<result column="last_name" property="lastName"/>
	 	<result column="email" property="email"/>
	 	<result column="gender" property="gender"/>
	 	<!--
	 		column：指定判定的列名
	 		javaType：列值对应的java类型  -->
	 	<discriminator javaType="string" column="gender">
	 		<!--女生  resultType:指定封装的结果类型；不能缺少。/resultMap-->
	 		<case value="0" resultType="com.atguigu.mybatis.bean.Employee">
	 			<association property="dept" 
			 		select="com.atguigu.mybatis.dao.DepartmentMapper.getDeptById"
			 		column="d_id">
		 		</association>
	 		</case>
	 		<!--男生 ;如果是男生，把last_name这一列的值赋值给email; -->
	 		<case value="1" resultType="com.atguigu.mybatis.bean.Employee">
		 		<id column="id" property="id"/>
			 	<result column="last_name" property="lastName"/>
			 	<result column="last_name" property="email"/>
			 	<result column="gender" property="gender"/>
	 		</case>
	 	</discriminator>
	 </resultMap>
```



#### 动态sql

解决的痛点： 按照条件拼装sql

```
<!--  动态标签：
• if:判断
• choose (when, otherwise):分支选择；带了break的swtich-case
	如果带了id就用id查，如果带了lastName就用lastName查;只会进入其中一个
• trim 字符串截取(where(封装查询条件), set(封装修改条件))
• foreach 遍历集合
-->
```

if:判断

```xml
 <!-- 查询员工，要求，携带了哪个字段查询条件就带上这个字段的值 -->
	 <!-- public List<Employee> getEmpsByConditionIf(Employee employee); -->
	 <select id="getEmpsByConditionIf" resultType="com.atguigu.mybatis.bean.Employee">
	 	select * from tbl_employee
	 	<!-- where -->
	 	<where>
		 	<!-- test：判断表达式（OGNL）
		 	OGNL参照PPT或者官方文档。
		 	  	 c:if  test
		 	从参数中取值进行判断
		 	
		 	遇见特殊符号应该去写转义字符：
		 	&&：
		 	-->
		 	<if test="id!=null">
		 		id=#{id}
		 	</if>
		 	<if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
		 		and last_name like #{lastName}
		 	</if>
		 	<if test="email!=null and email.trim()!=&quot;&quot;">
		 		and email=#{email}
		 	</if> 
		 	<!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
		 	<if test="gender==0 or gender==1">
		 	 	and gender=#{gender}
		 	</if>
	 	</where>
	 </select>

这里注意：
//查询的时候如果某些条件没带可能sql拼装会有问题
//1、给where后面加上1=1，以后的条件都and xxx.
//2、mybatis使用where标签来将所有的查询条件包括在内。mybatis就会将where标签中拼装的sql，多出来的and或者or去掉
//where只会去掉第一个多出来的and或者or。 有时候也会出问题

另外一种方式： trim标签 (使用的不多，劲量还是使用where标签的规范方法)
 <!--public List<Employee> getEmpsByConditionTrim(Employee employee);  -->
	 <select id="getEmpsByConditionTrim" resultType="com.atguigu.mybatis.bean.Employee">
	 	select * from tbl_employee
	 	<!-- 后面多出的and或者or where标签不能解决 
	 	prefix="":前缀：trim标签体中是整个字符串拼串 后的结果。
	 			prefix给拼串后的整个字符串加一个前缀 
	 	prefixOverrides="":
	 			前缀覆盖： 去掉整个字符串前面多余的字符
	 	suffix="":后缀
	 			suffix给拼串后的整个字符串加一个后缀 
	 	suffixOverrides=""
	 			后缀覆盖：去掉整个字符串后面多余的字符
	 			
	 	-->
	 	<!-- 自定义字符串的截取规则 -->
	 	<trim prefix="where" suffixOverrides="and">
	 		<if test="id!=null">
		 		id=#{id} and
		 	</if>
		 	<if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
		 		last_name like #{lastName} and
		 	</if>
		 	<if test="email!=null and email.trim()!=&quot;&quot;">
		 		email=#{email} and
		 	</if> 
		 	<!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
		 	<if test="gender==0 or gender==1">
		 	 	gender=#{gender}
		 	</if>
		 </trim>
	 </select>
```

choose分支选择

```xml
如果带了id就用id查，如果带了lastName就用lastName查;只会进入其中一个
类似于java中 switch  break 
<!-- public List<Employee> getEmpsByConditionChoose(Employee employee); -->
	 <select id="getEmpsByConditionChoose" resultType="com.atguigu.mybatis.bean.Employee">
	 	select * from tbl_employee 
	 	<where>
	 		<!-- 如果带了id就用id查，如果带了lastName就用lastName查;只会进入其中一个 -->
	 		<choose>
	 			<when test="id!=null">
	 				id=#{id}
	 			</when>
	 			<when test="lastName!=null">
	 				last_name like #{lastName}
	 			</when>
	 			<when test="email!=null">
	 				email = #{email}
	 			</when>
	 			<otherwise>
	 				gender = 0
	 			</otherwise>
	 		</choose>
	 	</where>
	 </select>
```

set(封装修改条件)

```xml
	 <!--public void updateEmp(Employee employee);  -->
	 <update id="updateEmp">
	 	<!-- Set标签的使用 -->
	 	update tbl_employee 
		<set>
			<if test="lastName!=null">
				last_name=#{lastName},
			</if>
			<if test="email!=null">
				email=#{email},
			</if>
			<if test="gender!=null">
				gender=#{gender}
			</if>
		</set>
		where id=#{id} 
<!-- 		
		Trim：更新拼串
		update tbl_employee 
		<trim prefix="set" suffixOverrides=",">
			<if test="lastName!=null">
				last_name=#{lastName},
			</if>
			<if test="email!=null">
				email=#{email},
			</if>
			<if test="gender!=null">
				gender=#{gender}
			</if>
		</trim>
		where id=#{id}  -->
	 </update>
```



foreach 遍历集合

```xml
<!--	//查询员工id'在给定集合中的
	public List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);-->
	 <select id="getEmpsByConditionForeach" resultType="com.atguigu.mybatis.bean.Employee">
	 	select * from tbl_employee
	 	<!--
	 		collection：指定要遍历的集合：
	 			list类型的参数会特殊处理封装在map中，map的key就叫list
                  如果上面没有注解，就collection 的key就是list
	 		item：将当前遍历出的元素赋值给指定的变量
	 		separator:每个元素之间的分隔符
	 		open：遍历出所有结果拼接一个开始的字符
	 		close:遍历出所有结果拼接一个结束的字符
	 		index:索引。遍历list的时候是index就是索引，item就是当前值
	 				      遍历map的时候index表示的就是map的key，item就是map的值
	 		
	 		#{变量名}就能取出变量的值也就是当前遍历出的元素
	 	  -->
	 	<foreach collection="ids" item="item_id" separator=","
	 		open="where id in(" close=")">
	 		#{item_id}
	 	</foreach>
	 </select>
```

foreach 批量插入

```xml
<!-- 批量保存 -->
方法一：
	 <!--public void addEmps(@Param("emps")List<Employee> emps);  -->
	 <!--MySQL下批量保存：可以foreach遍历   mysql支持values(),(),()语法-->
	<insert id="addEmps">
	 	insert into tbl_employee(
	 		<include refid="insertColumn"></include>
	 	) 
		values
		<foreach collection="emps" item="emp" separator=",">
			(#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
		</foreach>
	 </insert>

方法二：
 <!-- 这种方式需要数据库连接属性allowMultiQueries=true；
jdbc.url=jdbc:mysql://localhost:3306/mybatis?allowMultiQueries=true
	 	这种分号分隔多个sql可以用于其他的批量操作（删除，修改） -->
	 <insert id="addEmps">
	 	<foreach collection="emps" item="emp" separator=";">
	 		insert into tbl_employee(last_name,email,gender,d_id)
	 		values(#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
	 	</foreach>
	 </insert> 

<!-- Oracle数据库批量保存： 
	 	Oracle不支持values(),(),()
	 	Oracle支持的批量方式
	 	1、多个insert放在begin - end里面
	 		begin
			    insert into employees(employee_id,last_name,email) 
			    values(employees_seq.nextval,'test_001','test_001@atguigu.com');
			    insert into employees(employee_id,last_name,email) 
			    values(employees_seq.nextval,'test_002','test_002@atguigu.com');
			end;
		2、利用中间表：
			insert into employees(employee_id,last_name,email)
		       select employees_seq.nextval,lastName,email from(
		              select 'test_a_01' lastName,'test_a_e01' email from dual
		              union
		              select 'test_a_02' lastName,'test_a_e02' email from dual
		              union
		              select 'test_a_03' lastName,'test_a_e03' email from dual
		       )
  -->
 <insert id="addEmps" databaseId="oracle">
	 	<!-- oracle第一种批量方式 -->
	 	<!-- <foreach collection="emps" item="emp" open="begin" close="end;">
	 		insert into employees(employee_id,last_name,email) 
			    values(employees_seq.nextval,#{emp.lastName},#{emp.email});
	 	</foreach> -->
	 	
	 	<!-- oracle第二种批量方式  -->
	 	insert into employees(
	 		<!-- 引用外部定义的sql -->
	 		<include refid="insertColumn">
	 			<property name="testColomn" value="abc"/>
	 		</include>
	 	)
	 			<foreach collection="emps" item="emp" separator="union"
	 				open="select employees_seq.nextval,lastName,email from("
	 				close=")">
	 				select #{emp.lastName} lastName,#{emp.email} email from dual
	 			</foreach>
	 </insert>
```

##### 内置参数

mybatis默认有两个内置参数：

```xml
 两个内置参数：
	 	不只是方法传递过来的参数可以被用来判断，取值。。。
	 	mybatis默认还有两个内置参数：
	 	_parameter:代表整个参数
	 		单个参数：_parameter就是这个参数
	 		多个参数：参数会被封装为一个map；_parameter就是代表这个map
	 	
	 	_databaseId:如果配置了databaseIdProvider标签。
	 		_databaseId就是代表当前数据库的别名oracle
	 		
 <!--public List<Employee> getEmpsTestInnerParameter(Employee employee);  -->
	  <select id="getEmpsTestInnerParameter" resultType="com.atguigu.mybatis.bean.Employee">
	  		<!-- bind：可以将OGNL表达式的值绑定到一个变量中，方便后来引用这个变量的值 -->
	  		<bind name="_lastName" value="'%'+lastName+'%'"/>
	  		<if test="_databaseId=='mysql'">
	  			select * from tbl_employee
	  			<if test="_parameter!=null">
	  				where last_name like #{_lastName}
	  			</if>
	  		</if>
	  		<if test="_databaseId=='oracle'">
	  			select * from employees
	  			<if test="_parameter!=null">
	  				where last_name like #{_parameter.lastName}
	  			</if>
	  		</if>
	  </select>
bind ： 应用场景： 在模糊搜索的时候， java代码中不写%XX%,而是通过bind写在sql中
```

##### 抽取可重用的sql片段

```xml
 <!-- 
	  	抽取可重用的sql片段。方便后面引用 
	  	1、sql抽取：经常将要查询的列名，或者插入用的列名抽取出来方便引用
	  	2、include来引用已经抽取的sql：
	  	3、include还可以自定义一些property，sql标签内部就能使用自定义的属性
	  			include-property：取值的正确方式${prop},
	  			#{不能使用这种方式}
	  -->
	  <sql id="insertColumn">
	  		<if test="_databaseId=='oracle'">
	  			employee_id,last_name,email
	  		</if>
	  		<if test="_databaseId=='mysql'">
	  			last_name,email,gender,d_id
	  		</if>
	  </sql>

 <insert id="addEmps" databaseId="oracle">
	 	insert into employees(
	 		<!-- 引用外部定义的sql -->
	 		<include refid="insertColumn">
                  <!-- 引用时还可以传入变量，在引用sql中使用 -->
	 			<property name="testColomn" value="abc"/>
	 		</include>
	 	)
	 			<foreach collection="emps" item="emp" separator="union"
	 				open="select employees_seq.nextval,lastName,email from("
	 				close=")">
	 				select #{emp.lastName} lastName,#{emp.email} email from dual
	 			</foreach>
	 </insert>
```



### 缓存

缓存的作用： 为了提高查询速度

缓存的本质就是一个map

![JAVA_MYBATIS3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS3.png?raw=true)

![JAVA_MYBATIS4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS4.png?raw=true)

```xml
两级缓存：
一级缓存：（本地缓存）：sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个Map
	 		与数据库同一次会话期间查询到的数据会放在本地缓存中。
	  		以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
	 
			一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
	  		1、sqlSession不同。
	  		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
	 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
	  		4、sqlSession相同，手动清除了一级缓存（缓存清空）
	  
 二级缓存：（全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
工作机制：
1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
3、sqlSession===EmployeeMapper==>Employee
  DepartmentMapper===>Department
  不同namespace查出的数据会放在自己对应的缓存中（map）

效果：数据会从二级缓存中获取
查出的数据都会被默认先放在一级缓存中。
只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
  
使用：

1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
2）、去每个mapper.xml中配置使用二级缓存：
<cache></cache> 
当然他存在一些默认的策略
<mapper namespace="com.atguigu.mybatis.dao.EmployeeMapper">


	<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
	<!-- <cache eviction="FIFO" flushInterval="60000" readOnly="false" size="1024"></cache> -->
	<!--  
	eviction:缓存的回收（删除缓存的策略）策略：
		• LRU – 最近最少使用的：移除最长时间不被使用的对象。
		• FIFO – 先进先出：按对象进入缓存的顺序来移除它们。
		• SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。
		• WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。
		• 默认的是 LRU。
	flushInterval：缓存刷新间隔
		缓存多长时间清空一次，默认不清空，设置一个毫秒值
	readOnly:是否只读：
		true：只读；mybatis认为所有从缓存中获取数据的操作都是只读操作，不会修改数据。
				 mybatis为了加快获取速度，直接就会将数据在缓存中的引用交给用户。不安全，速度快
		false：非只读：mybatis觉得获取的数据可能会被修改。
				mybatis会利用序列化&反序列的技术克隆一份新的数据给你。安全，速度慢
	size：缓存存放多少元素；
	type=""：指定自定义缓存的全类名；
			实现Cache接口即可；
	-->
3）、我们的POJO需要实现序列化接口

    
和缓存有关的设置/属性：
1）、全局配置文件中cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
2）、每个select标签都有useCache="true"：
	 false：不使用缓存（一级缓存依然使用，二级缓存不使用）
3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
	 	增删改执行完成后就会清楚缓存；
	 	测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
	  	查询标签：flushCache="false"：
	  	如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
	  								STATEMENT：可以禁用一级缓存；		
	 				
第三方缓存整合：
	 		1）、导入第三方缓存包即可；
	 		2）、导入与第三方缓存整合的适配包；官方有；
	 		3）、mapper.xml中使用自定义缓存
	 		<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
```



## Mybatis和spring的整合

整合的目的是让spring来管理我们所有的组件，利用spring中的IOC来解决容器中各个组件之间的依赖注入

还有就是利用spring来控制事务

```xml
spring整合mybatis扫描mapper.xml文件不用再和接口包名一致，可以通过mybatis-spring 适合包来指定路径

spring配置文件中整合mybatis的模块信息
<!-- 
	整合mybatis 
		目的：1、spring管理所有组件。mapper的实现类。
				service=（调用）=>Dao   @Autowired:自动注入mapper；
                  不用再单独的openSession,getMapper
			2、spring用来管理事务，spring声明式事务
	-->
	<!--spring IOC 一启动 就去创建出SqlSessionFactory对象，里面的属性就是以前写在mybatis全局配置文件中的信息  -->
	<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<!-- configLocation指定全局配置文件的位置 -->
		<property name="configLocation" value="classpath:mybatis-config.xml"></property>
		<!--mapperLocations: 指定mapper文件的位置-->
		<property name="mapperLocations" value="classpath:mybatis/mapper/*.xml"></property>
	</bean>
	
	<!--配置一个可以进行批量执行的sqlSession  -->
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"></constructor-arg>
		<constructor-arg name="executorType" value="BATCH"></constructor-arg>
	</bean>
	
	<!-- 扫描所有的mapper接口的实现，让这些mapper能够自动注入；
	base-package：指定mapper接口的包名
	 -->
	<mybatis-spring:scan base-package="com.atguigu.mybatis.dao"/>
	<!-- <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.atguigu.mybatis.dao"></property>
	</bean> -->
```

## mybatis逆向工程 MBG

正向： 根据数据表我们要手动创建对应的po,  和Mapper 接口

逆向： 根据数据表可以进行分析并自动帮助开发创建 po 生成mapper接口和对应的xml

逆向工程的配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

	<!-- 
		targetRuntime="MyBatis3Simple":生成简单版的CRUD
		MyBatis3:豪华版 
	
	 -->
  <context id="DB2Tables" targetRuntime="MyBatis3Simple">
  	<!-- jdbcConnection：指定如何连接到目标数据库 -->
    <jdbcConnection driverClass="com.mysql.jdbc.Driver"
        connectionURL="jdbc:mysql://localhost:3306/mybatis?allowMultiQueries=true"
        userId="root"
        password="123456">
    </jdbcConnection>

	<!--  -->
    <javaTypeResolver >
      <property name="forceBigDecimals" value="false" />
    </javaTypeResolver>

	<!-- javaModelGenerator：指定javaBean的生成策略 
	targetPackage="test.model"：目标包名
	targetProject="\MBGTestProject\src"：目标工程
	-->
    <javaModelGenerator targetPackage="com.atguigu.mybatis.bean" 
    		targetProject=".\src">
      <property name="enableSubPackages" value="true" />
      <property name="trimStrings" value="true" />
    </javaModelGenerator>

	<!-- sqlMapGenerator：sql映射生成策略： -->
    <sqlMapGenerator targetPackage="com.atguigu.mybatis.dao"  
    	targetProject=".\conf">
      <property name="enableSubPackages" value="true" />
    </sqlMapGenerator>

	<!-- javaClientGenerator:指定mapper接口所在的位置 -->
    <javaClientGenerator type="XMLMAPPER" targetPackage="com.atguigu.mybatis.dao"  
    	targetProject=".\src">
      <property name="enableSubPackages" value="true" />
    </javaClientGenerator>

	<!-- 指定要逆向分析哪些表：根据表要创建javaBean -->
    <table tableName="tbl_dept" domainObjectName="Department"></table>
    <table tableName="tbl_employee" domainObjectName="Employee"></table>
  </context>
</generatorConfiguration>

```

如何运行逆向工程

```java
	public void testMbg() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
        //上面逆向配置文件的名称： 直接放在工程目录下，和src同级
		File configFile = new File("mbg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
	}
```

mybatis逆向工程的jar包Maven导入

```xml
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator</artifactId>
    <version>1.3.6</version>
</dependency>
```

## mybatis工作原理

![JAVA_MYBATIS5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS5.png?raw=true)

（https://www.cnblogs.com/scuury/p/10371246.html）

![JAVA_MYBATIS6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS6.png?raw=true)

```java
	/**
	 * 1、获取sqlSessionFactory对象:
	 * 		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
	 * 		注意：【MappedStatement】：代表一个增删改查标签的详细信息
	 * 
	 * 2、获取sqlSession对象
	 * 		返回一个DefaultSQlSession对象，包含Executor和Configuration;
	 * 		这一步会创建Executor对象；
	 * 
	 * 3、获取接口的代理对象（MapperProxy）
	 * 		getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
	 * 		代理对象里面包含了，DefaultSqlSession（Executor）
	 *       代理对象能进行增删改查时调用了DefaultSqlSession中的Executor
	 * 4、执行增删改查方法
	 * 
	 * 总结：
	 * 	1、根据配置文件（全局，sql映射）初始化出Configuration对象
	 * 	2、创建一个DefaultSqlSession对象，
	 * 		他里面包含Configuration以及
	 * 		Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
	 *  3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
	 *  4、MapperProxy里面有（DefaultSqlSession）；
	 *  5、执行增删改查方法：
	 *  		1）、调用DefaultSqlSession的增删改查（Executor）；
	 *  		2）、会创建一个StatementHandler对象。
	 *  			（同时也会创建出ParameterHandler和ResultSetHandler）
	 *  		3）、调用StatementHandler预编译参数以及设置参数值;
	 *  			使用ParameterHandler来给sql设置参数
	 *  		4）、调用StatementHandler的增删改查方法；
	 *  		5）、ResultSetHandler封装结果
	 *  注意：
	 *  	四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
	 * 
	 * @throws IOException
	 */
```

![JAVA_MYBATIS7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS7.png?raw=true)

![JAVA_MYBATIS8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS8.png?raw=true)

![JAVA_MYBATIS9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS9.png?raw=true)

![JAVA_MYBATIS10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS10.png?raw=true)

![JAVA_MYBATIS11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS11.png?raw=true)

![JAVA_MYBATIS12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS12.png?raw=true)

运行流程：

![JAVA_MYBATIS13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS13.png?raw=true)

![JAVA_MYBATIS14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS14.png?raw=true)



## mybatis 插件

```java
/**
	 * 插件原理
	 * 在四大对象创建的时候
	 * 1、每个创建出来的对象不是直接返回的，而是
	 * 		interceptorChain.pluginAll(parameterHandler);
	 * 2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）；
	 * 		调用interceptor.plugin(target);返回target包装后的对象
	 * 3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP（面向切面）
	 * 		我们的插件可以为四大对象创建出代理对象；
	 * 		代理对象就可以拦截到四大对象的每一个执行；
	 * 
		public Object pluginAll(Object target) {
		    for (Interceptor interceptor : interceptors) {
		      target = interceptor.plugin(target);
		    }
		    return target;
		  }
		
	 */
	/**
	 * 插件编写：
	 * 1、编写Interceptor的实现类
	 * 2、使用@Intercepts注解完成插件签名
	 * 3、将写好的插件注册到全局配置文件中
	 * 
	 */
/**
 * 完成插件签名：
 *		告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts(
		{
			@Signature(type=StatementHandler.class,method="parameterize",args=java.sql.Statement.class)
		})
public class MyFirstPlugin implements Interceptor{

	/**
	 * intercept：拦截：
	 * 		拦截目标对象的目标方法的执行；
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("MyFirstPlugin...intercept:"+invocation.getMethod());
		//动态的改变一下sql运行的参数：以前1号员工，实际从数据库查询3号员工
		Object target = invocation.getTarget();
		System.out.println("当前拦截到的对象："+target);
		//拿到：StatementHandler==>ParameterHandler===>parameterObject
		//拿到target的元数据
		MetaObject metaObject = SystemMetaObject.forObject(target);
		Object value = metaObject.getValue("parameterHandler.parameterObject");
		System.out.println("sql语句用的参数是："+value);
		//修改完sql语句要用的参数
		metaObject.setValue("parameterHandler.parameterObject", 11);
		//执行目标方法
		Object proceed = invocation.proceed();
		//返回执行后的返回值
		return proceed;
	}

	/**
	 * plugin：
	 * 		包装目标对象的：包装：为目标对象创建一个代理对象
	 */
	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		//我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
		System.out.println("MyFirstPlugin...plugin:mybatis将要包装的对象"+target);
		Object wrap = Plugin.wrap(target, this);
		//返回为当前target创建的动态代理
		return wrap;
	}

	/**
	 * setProperties：
	 * 		将插件注册时 的property属性设置进来
	 */
	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		System.out.println("插件配置的信息："+properties);
	}

}

3、将写好的插件注册到全局配置文件中
	<!--plugins：注册插件  -->
	<plugins>
		<plugin interceptor="com.atguigu.mybatis.dao.MyFirstPlugin">
			<property name="username" value="root"/>
			<property name="password" value="123456"/>
		</plugin>
		<plugin interceptor="com.atguigu.mybatis.dao.MySecondPlugin"></plugin>
	</plugins>
```

![JAVA_MYBATIS15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS15.png?raw=true)

插件开发原理：

![JAVA_MYBATIS16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS16.png?raw=true)

## mybatis批量操作

```java
	public void testBatch() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		//可以执行批量操作的sqlSession
		SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		long start = System.currentTimeMillis();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			for (int i = 0; i < 10000; i++) {
				mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0, 5), "b", "1"));
			}
			openSession.commit();
			long end = System.currentTimeMillis();
			//批量：（预编译sql一次==>设置参数===>10000次===>执行（1次））
			//Parameters: 616c1(String), b(String), 1(String)==>4598
			//非批量：（预编译sql=设置参数=执行）==》10000    10200
			System.out.println("执行时长："+(end-start));
		}finally{
			openSession.close();
		}
		
	}

整合Mybatis的时候：
<!--配置一个可以进行批量执行的sqlSession  -->
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"></constructor-arg>
		<constructor-arg name="executorType" value="BATCH"></constructor-arg>
	</bean>
	<!--创建出SqlSessionFactory对象  -->
	<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<!-- configLocation指定全局配置文件的位置 -->
		<property name="configLocation" value="classpath:mybatis-config.xml"></property>
		<!--mapperLocations: 指定mapper文件的位置-->
		<property name="mapperLocations" value="classpath:mybatis/mapper/*.xml"></property>
	</bean>
使用：
    // 注入批量sqlsession
    @Autowired
	private SqlSession sqlSession;
	
	public List<Employee> getEmps(){
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		return employeeMapper.getEmps();
	}
```

## mybatis调用存储过程

![JAVA_MYBATIS17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_MYBATIS17.png?raw=true)

```java
	/**
	 * oracle分页：  测试mybatis调用子查询
	 * 		借助rownum：行号；子查询；
	 * 存储过程包装分页逻辑
	 * @throws IOException 
	 */
	@Test
	public void testProcedure() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			OraclePage page = new OraclePage();
			page.setStart(1);
			page.setEnd(5);
			mapper.getPageByProcedure(page);
			
			System.out.println("总记录数："+page.getCount());
			System.out.println("查出的数据："+page.getEmps().size());
			System.out.println("查出的数据："+page.getEmps());
		}finally{
			openSession.close();
		}
		
	}


/**
 * 封装分页查询数据
 * @author lfy
 *
 */
public class OraclePage {
	
	private int start;
	private int end;
	private int count;
	private List<Employee> emps;
}

<!-- public void getPageByProcedure(); 
public void getPageByProcedure(OraclePage page);
	1、使用select标签定义调用存储过程
	2、statementType="CALLABLE":表示要调用存储过程
	3、{call procedure_name(params)}
	-->
	<select id="getPageByProcedure" statementType="CALLABLE" databaseId="oracle">
		{call hello_test(
			#{start,mode=IN,jdbcType=INTEGER},
			#{end,mode=IN,jdbcType=INTEGER},
			#{count,mode=OUT,jdbcType=INTEGER},
			#{emps,mode=OUT,jdbcType=CURSOR,javaType=ResultSet,resultMap=PageEmp}
		)}
	</select>
		<resultMap type="com.atguigu.mybatis.bean.Employee" id="PageEmp">
		<id column="EMPLOYEE_ID" property="id"/>
		<result column="LAST_NAME" property="email"/>
		<result column="EMAIL" property="email"/>
	</resultMap>
	
mysql存储过程中可以直接使用select语句返回结果集，而且mybatis可以直接使用list接收这个结果集（无需游标）。
Map queryMap = new HashMap();
queryMap.put("obj", param);
List<Map> listIis2 =reportDao.select4MapParam(queryMap,"test123");
<resultMap type="java.util.HashMap" id="resultMap">
       <result column="id" property="id" javaType="java.lang.Integer" jdbcType="INTEGER"/>
       <result column="name" property="name" javaType="java.lang.String" jdbcType="VARCHAR"/>
       <result column="repDate" property="repDate" javaType="java.lang.String" jdbcType="VARCHAR"/>
       <result column="summ" property="summ" javaType="java.lang.Long" jdbcType="BIGINT"/>
</resultMap>
     
<select id="test123" parameterType="java.util.Map"  resultMap="resultMap"                 statementType="CALLABLE" >  
      {call pro_sql_data(
	      #{obj,jdbcType=VARCHAR,mode=IN}
	   )
      }
    </select>  
```



## 自定义类型处理器

TypeHandler在mybatis中担任了数据库类型和java类型映射的工作

### 处理枚举

```java
public enum EmpStatus {
	LOGIN,LOGOUT,REMOVE;
}

/**
	 * 默认mybatis在处理枚举对象的时候保存的是枚举的名字：EnumTypeHandler
	 * 改变使用：EnumOrdinalTypeHandler保存枚举的索引：
	 * @throws IOException
	 */
	 @Test
	public void testEnum() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = new Employee("test_enum", "enum@atguigu.com","1");
			//mapper.addEmp(employee);
			//System.out.println("保存成功"+employee.getId());
			//openSession.commit();
			Employee empById = mapper.getEmpById(30026);
			System.out.println(empById.getEmpStatus());
		}finally{
			openSession.close();
		}
	}
	改变使用：EnumOrdinalTypeHandler保存枚举的索引：
	 在全局文件中声明：
	 	<typeHandlers>
		<!--1、配置我们自定义的TypeHandler  -->
		<typeHandler handler="com.atguigu.mybatis.typehandler.MyEnumEmpStatusTypeHandler" javaType="com.atguigu.mybatis.bean.EmpStatus"/>
		<!--2、也可以在处理某个字段的时候告诉MyBatis用什么类型处理器
				保存：#{empStatus,typeHandler=xxxx}
				查询：
					<resultMap type="com.atguigu.mybatis.bean.Employee" id="MyEmp">
				 		<id column="id" property="id"/>
				 		<result column="empStatus" property="empStatus" typeHandler=""/>
				 	</resultMap>
			注意：如果在参数位置修改TypeHandler，应该保证保存数据和查询数据用的TypeHandler是一样的。
		  -->
	</typeHandlers>
	
自定义类型处理器
**
 * 希望数据库保存的是100,200这些状态码，而不是默认0,1或者枚举的名
 * @author lfy
 *
 */
public enum EmpStatus {
	LOGIN(100,"用户登录"),LOGOUT(200,"用户登出"),REMOVE(300,"用户不存在");
	
	
	private Integer code;
	private String msg;
	private EmpStatus(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//按照状态码返回枚举对象
	public static EmpStatus getEmpStatusByCode(Integer code){
		switch (code) {
			case 100:
				return LOGIN;
			case 200:
				return LOGOUT;	
			case 300:
				return REMOVE;
			default:
				return LOGOUT;
		}
	}	
}

自定义类型处理器
/**
 * 1、实现TypeHandler接口。或者继承BaseTypeHandler
 * @author lfy
 *
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus> {

	/**
	 * 定义当前数据如何保存到数据库中
	 */
	@Override
	public void setParameter(PreparedStatement ps, int i, EmpStatus parameter,
			JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("要保存的状态码："+parameter.getCode());
		ps.setString(i, parameter.getCode().toString());
	}

	@Override
	public EmpStatus getResult(ResultSet rs, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		//需要根据从数据库中拿到的枚举的状态码返回一个枚举对象
		int code = rs.getInt(columnName);
		System.out.println("从数据库中获取的状态码："+code);
		EmpStatus status = EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		int code = rs.getInt(columnIndex);
		System.out.println("从数据库中获取的状态码："+code);
		EmpStatus status = EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		int code = cs.getInt(columnIndex);
		System.out.println("从数据库中获取的状态码："+code);
		EmpStatus status = EmpStatus.getEmpStatusByCode(code);
		return status;
	}

}

```



　
