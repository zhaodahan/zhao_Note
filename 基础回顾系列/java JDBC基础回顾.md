# JDBC

## 什么是jdbc

直接访问数据库的技术。框架中使用的访问数据的库的技术的底层都是jdbc。他们不过是在jdbc的基础上封装了一层。jdbc是访问数据库的基石。他的本质是一组**通用**的访问数据库的**API**.

![JAVA_JDBC1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC1.png?raw=true)

![JAVA_JDBC2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC2.png?raw=true)

## 如何使用jdbc

```java
/**
	 * 通过 JDBC 向指定的数据表中插入一条记录. 
	 * 
	 * 1. Statement: 用于执行 SQL 语句的对象
	 * 1). 通过 Connection 的 createStatement() 方法来获取
	 * 2). 通过 executeUpdate(sql) 可以执行 SQL 语句.
	 * 3). 传入的 SQL 可以是 INSRET, UPDATE 或 DELETE. 但不能是 SELECT
	 * 
	 * 2. Connection、Statement 都是应用程序和数据库服务器的连接资源. 使用后一定要关闭.
	 * 需要在 finally 中关闭 Connection 和 Statement 对象. 
	 * 
	 * 3. 关闭的顺序是: 先关闭后获取的. 即先关闭 Statement 后关闭 Connection
	 */
	@Test
	public void testStatement() throws Exception{
		//1. 获取数据库连接
		Connection conn = null;
		Statement statement = null;		
		try {
			conn = getConnection2();
			//3. 准备插入的 SQL 语句
			String sql = null;
//			sql = "INSERT INTO customers (NAME, EMAIL, BIRTH) " +
//					"VALUES('XYZ', 'xyz@atguigu.com', '1990-12-12')";
//			sql = "DELETE FROM customers WHERE id = 1";
			sql = "UPDATE customers SET name = 'TOM' " +
					"WHERE id = 4";
			System.out.println(sql);	
			//4. 执行插入. 
			//1). 获取操作 SQL 语句的 Statement 对象: 
			//调用 Connection 的 createStatement() 方法来获取
			statement = conn.createStatement();
			
			//2). 调用 Statement 对象的 executeUpdate(sql) 执行 SQL 语句进行插入
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				//5. 关闭 Statement 对象.
				if(statement != null)
					statement.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				//2. 关闭连接
				if(conn != null)
					conn.close();							
			}
		}
		
	}
```



```java
/**
	 * ResultSet: 结果集. 封装了使用 JDBC 进行查询的结果. 
	 * 1. 调用 Statement 对象的 executeQuery(sql) 可以得到结果集.
	 * 2. ResultSet 返回的实际上就是一张数据表. 有一个指针指向数据表的第一样的前面.
	 * 可以调用 next() 方法检测下一行是否有效. 若有效该方法返回 true, 且指针下移. 相当于
	 * Iterator 对象的 hasNext() 和 next() 方法的结合体
	 * 3. 当指针对位到一行时, 可以通过调用 getXxx(index) 或 getXxx(columnName)
	 * 获取每一列的值. 例如: getInt(1), getString("name")
	 * 4. ResultSet 当然也需要进行关闭. 
	 */
	@Test
	public void testResultSet(){
		//获取 id=4 的 customers 数据表的记录, 并打印
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			//1. 获取 Connection
			conn = JDBCTools.getConnection();
			//2. 获取 Statement
			statement = conn.createStatement();
			System.out.println(statement);
			//3. 准备 SQL
			String sql = "SELECT id, name, email, birth " +
					"FROM customers";
			//4. 执行查询, 得到 ResultSet
			rs = statement.executeQuery(sql);
			System.out.println(rs);
			
			//5. 处理 ResultSet
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString("name");
				String email = rs.getString(3);
				Date birth = rs.getDate(4);
				
				System.out.println(id);
				System.out.println(name);
				System.out.println(email);
				System.out.println(birth);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//6. 关闭数据库资源. 
			JDBCTools.release(rs, statement, conn);
		}
		
	}
```

![JAVA_JDBC3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC3.png?raw=true)

![JAVA_JDBC4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC4.png?raw=true)

![JAVA_JDBC5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC5.png?raw=true)

![JAVA_JDBC6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC6.png?raw=true)

![JAVA_JDBC7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC7.png?raw=true)

## jdbc驱动处理元数据

通过connection 我们可以获取到描述数据库的基本信息的DataBaseMetaData。

ResultSetMetaData: 描述结果集的元数据.  可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.

 结合反射可以写出通用的查询方法. 

![JAVA_JDBC8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC8.png?raw=true)

![JAVA_JDBC9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC9.png?raw=true)

```java
            //1. 得到 ResultSetMetaData 对象
			ResultSetMetaData rsmd = resultSet.getMetaData(); 
			//2. 得到列的个数
			int columnCount = rsmd.getColumnCount();
			System.out.println(columnCount);
			for(int i = 0 ; i < columnCount; i++){
				//3. 得到列名
				String columnName = rsmd.getColumnName(i + 1);
				//4. 得到列的别名
				String columnLabel = rsmd.getColumnLabel(i + 1);
				System.out.println(columnName + ", " + columnLabel);
			}
```

## 获取自动生成主键

```java
//使用重载的 prepareStatement(sql, flag) 
			//来生成 PreparedStatement 对象
			preparedStatement = connection.prepareStatement(sql, 
					Statement.RETURN_GENERATED_KEYS);

//通过 getGeneratedKeys() 获取包含了新生成的主键的 ResultSet 对象
			//在 ResultSet 中只有一列 GENERATED_KEY, 用于存放新生成的主键值.
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if(rs.next()){
				System.out.println(rs.getObject(1));
			}
```



## 处理Blob（大数据大对象）

![JAVA_JDBC10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC10.png?raw=true)

```java
/**
	 * 插入 BLOB 类型的数据必须使用 PreparedStatement：因为 BLOB 类型
	 * 的数据时无法使用字符串拼写的。
	 * 调用 setBlob(int index, InputStream inputStream)
	 */
	@Test
	public void testInsertBlob(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;	
		try {
			connection = JDBCTools.getConnection();
            //picture 是Blob类型
			String sql = "INSERT INTO customers(name, email, birth, picture)" 
					+ "VALUES(?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
	            //..........................
			InputStream inputStream = new FileInputStream("Hydrangeas.jpg");
			preparedStatement.setBlob(4, inputStream);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}

========读
	/**
	 * 读取 blob 数据: 
	 * 1. 使用 getBlob 方法读取到 Blob 对象
	 * 2. 调用 Blob 的 getBinaryStream() 方法得到输入流。再使用 IO 操作即可. 
	 */
                 Blob picture = resultSet.getBlob(5);
				InputStream in = picture.getBinaryStream();
```



## 处理事务

```java
/**
	 * Tom 给 Jerry 汇款 500 元.
	 * 
	 * 关于事务: 1. 如果多个操作, 每个操作使用的是自己的单独的连接, 则无法保证事务. 
	 *2. 具体步骤: 1). 事务操作开始前, 开始事务:取消 Connection 的默认提交行为.        connection.setAutoCommit(false); 
	 2). 如果事务的操作都成功, 则提交事务: connection.commit();
     *3). 回滚事务: 若出现异常, 则在 catch 块中回滚事务:
	 */
	@Test
	public void testTransaction() {
		Connection connection = null;
		try {
			connection = JDBCTools.getConnection();
			System.out.println(connection.getAutoCommit());
			// 开始事务: 取消默认提交.
			connection.setAutoCommit(false);

			String sql = "UPDATE users SET balance = "
					+ "balance - 500 WHERE id = 1";
			update(connection, sql);
            
			int i = 10 / 0;
            
			System.out.println(i);
			sql = "UPDATE users SET balance = " + "balance + 500 WHERE id = 2";
			update(connection, sql);
			// 提交事务
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
```

![JAVA_JDBC11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC11.png?raw=true)

### 事务隔离级别

在 JDBC 程序中可以通过 Connection 的 setTransactionIsolation 来设置事务的隔离级别.

```java
//			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
```



## 批量处理

一次性插入多条数据，如何提高执行效率。 类似于缓存，我们积攒一定量后，再一次执行。比每有一条就执行效率更快。

![JAVA_JDBC12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC12.png?raw=true)

![JAVA_JDBC13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC13.png?raw=true)

## 数据库连接池

![JAVA_JDBC14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC14.png?raw=true)

```java
	/**
	 * 1. 加载 dbcp 的 properties 配置文件: 配置文件中的键需要来自 BasicDataSource
	 * 的属性.
	 * 2. 调用 BasicDataSourceFactory 的 createDataSource 方法创建 DataSource
	 * 实例
	 * 3. 从 DataSource 实例中获取数据库连接. 
	 */
```

![JAVA_JDBC15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC15.png?raw=true)

![JAVA_JDBC16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC16.png?raw=true)

![JAVA_JDBC17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC17.png?raw=true)

![JAVA_JDBC18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC18.png?raw=true)

![JAVA_JDBC19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC19.png?raw=true)

## DBUtils

对原生的jdbc进行了封装，不会影响其**性能**。 

![JAVA_JDBC20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC20.png?raw=true)

![JAVA_JDBC21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC21.png?raw=true)

```java
	/**
	 * 1. ResultSetHandler 的作用: QueryRunner 的 query 方法的返回值最终取决于
	 * query 方法的 ResultHandler 参数的 hanlde 方法的返回值. 
	 * 
	 * 2. BeanListHandler: 把结果集转为一个 Bean 的 List, 并返回. Bean 的类型在
	 * 创建 BeanListHanlder 对象时以 Class 对象的方式传入. 可以适应列的别名来映射 
	 * JavaBean 的属性名: 
	 * String sql = "SELECT id, name customerName, email, birth " +
	 *			"FROM customers WHERE id = ?";
	 * 
	 * BeanListHandler(Class<T> type)把结果集转为一个 Bean 的 List. 该 Bean 的类型在创建  
	 *BeanListHandler 对象时传入: new BeanListHandler<>(Customer.class)
	 * 
	 * 
	 * 3. BeanHandler: 把结果集转为一个 Bean, 并返回. Bean 的类型在创建 BeanHandler
	 * 对象时以 Class 对象的方式传入
	 * BeanHandler(Class<T> type) 
	 * 
	 * 4. MapHandler: 把结果集转为一个 Map 对象, 并返回. 若结果集中有多条记录, 仅返回
	 * 第一条记录对应的 Map 对象. Map 的键: 列名(而非列的别名), 值: 列的值
	 * 
	 * 5. MapListHandler: 把结果集转为一个 Map 对象的集合, 并返回. 
	 * Map 的键: 列名(而非列的别名), 值: 列的值
	 * 
	 * 6. ScalarHandler: 可以返回指定列的一个值或返回一个统计函数的值. 
	 */
```

![JAVA_JDBC22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC22.png?raw=true)

![JAVA_JDBC23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC23.png?raw=true)

## 调用函数及存储过程

```java
/**
	 * 如何使用 JDBC 调用存储在数据库中的函数或存储过程
	 */
	@Test
	public void testCallableStatment() {
		Connection connection = null;
		CallableStatement callableStatement = null;

		try {
			connection = JDBCTools.getConnection();

			// 1. 通过 Connection 对象的 prepareCall()方法创建一个 CallableStatement 对象的实例.
			// 在使用 Connection 对象的 preparedCall() 方法时,
			// 需要传入一个 String 类型的字符串, 该字符串用于指明如何调用存储过程.
			String sql = "{?= call sum_salary(?, ?)}";
			callableStatement = connection.prepareCall(sql);

			// 2. 通过 CallableStatement 对象的 reisterOutParameter() 方法注册 OUT 参数.
			callableStatement.registerOutParameter(1, Types.NUMERIC);
			callableStatement.registerOutParameter(3, Types.NUMERIC);
			
// 3. 通过 CallableStatement 对象的 setXxx() 方法设定 IN 或 IN OUT 参数. 若想将参数默认值设为null, 可以使用 setNull() 方法.
			callableStatement.setInt(2, 80);
			
			// 4. 通过 CallableStatement 对象的 execute() 方法执行存储过程
			callableStatement.execute();
			
			// 5. 如果所调用的是带返回参数的存储过程, 
			//还需要通过 CallableStatement 对象的 getXxx() 方法获取其返回值.
			double sumSalary = callableStatement.getDouble(1);
			long empCount = callableStatement.getLong(3);
			System.out.println(sumSalary);
			System.out.println(empCount);	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, callableStatement, connection);
		}
	}
```

![JAVA_JDBC24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JDBC24.png?raw=true)





　
