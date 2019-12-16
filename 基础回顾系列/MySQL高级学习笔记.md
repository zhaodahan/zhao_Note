# MySQL高级学习笔记

mysql 高级知识 总的来说就是对mysql的性能优化。

```
最终目的： 了解这些知识，最终写出高效，好用，以及写出和开发相关的sql优化的sql。
“了解”跟开发相关的数据库知识：
数据库的脚本编程
Linux服务下去修改my.ini 等 Config等数据库配置文件
诸多的性能调优
(这些可以不进行细节掌握，但是要有个印象知道)
```



## mysql 架构介绍

就是更深一步的描述高级 mysql 到底是什么？ 干什么？

```
mysql内核
sql优化
mysql服务器的优化
查询语句优化
主从复制
软硬件升级
容灾备份
sql编程
```

### Mysql配置文件

默认情况下mysql的配置文件

windows     my.ini文件

Linux          /etc/my.cnf文件

其中具体的内容：

![JAVA_HIGHMYSQL1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL1.png?raw=true)

```
二进制日志log-bin： 主要的作用,用于主从复制  (他相当于一个小抄写工，他会将主机上的行情变化记录下来，用于主从复制)

错误日志log-error：默认是关闭的,记录严重的警告和错误信息,每次启动和关闭的详细信息等.

查询日志log:默认关闭,记录查询的sql语句，如果开启会减低mysql的整体性能，因为记录日志也是需要消耗系统资源的 
(他主要用于我们在分析慢sql查询时， 我们设定查询时间大于多久的为慢sql，将其记录下来)

主要的是mysql中的各种数据文件：、
存放位置：
windows：D:\ProgramFiles\MySQL\MySQLServer5.5\data目录下可以挑选很多库
linux：看看当前系统中的全部库后再进去，默认路径：/var/lib/mysql

具体文件
frm文件：存放表结构
myd文件：(my data)存放表的数据
myi文件：存放表查找索引
(frm 类似图书馆中的书架， myd存放的具体的书   myi查找书存放位置的目录)


```

### mysql的逻辑架构

如同web有分层概念，mysql同样存在分层 (分层的好处就是隔离了范围之后，那层出了问题，就找那层)

![JAVA_HIGHMYSQL2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL2.png?raw=true)

**插件式的存储引擎架构将查询处理和其他系统任务及数据的存储提取相分离**(这种架构可以根据业务实际需求选择合适的存储引擎)

```
1.	连接层
最上层是一些客户端和连接服务，包含本地sock通信和大多数基干客户端/服务端工具实现的类似于tcp/ip的通信，主要完成一些类似于连接处理、授权认证、及相关的安全方案。在该层上引入了线程池的概念，为通过认证安全接入的客户筠提供线程同样在该层上可以实现基于 SSL的安全链接，限务器也会为安全接入的每个客户端验证它所具有的择作权限，

2.	販务层
第二层架构主要完成大多少的核心現务功能，如SQL接口,并宪成缓存的查询，SQL的分析和优化及部分内置函数的执行。所有跨存储引孥的功能也在这一层实现，如过程、函数等.在该层，服务器会解忻査询并创建相应的内部解忻树，并对其完成相应的优化如确定査询表的项是否利用索引等.最巨生成相应的执行操作。如果是select语句，服务器还会查询内部缓存，如果缓存交间足够大，这样在解决大至读操作的 环埃中能够很好的提升系统的性能.

3.	引擎层
存储引擎层，真正的负责了MySQL中数据的存储和提取，服务器通过API与存储引擎进行通信。不同的存储引擎具有的功能不同，这 样我们可以根据自己的实际芪要迸行法取，常用的是MylSAM和InnoDB

4.	存储层
数据存储层，主要是将数据存储在运行于裸设备的文件系统之上，井完成与存緒引擎的交互。
```

### mysql引擎

![JAVA_HIGHMYSQL3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL3.png?raw=true)



## 索引优化分析(重中之重)

和开发密切相关，开发者主要就是要写出高性能的sql。

### sql 性能下降的原因

sql 执行时间长 ,等待时间长

```
原因：
1：sql语句本身的问题，各种连接，各种子查询。没有用上索引
2：索引失效
3：关联查询太多join(设计缺陷或不得已的需求)
4：服务器调优及各个参数设置(缓冲\线程数等) 的不合理

重点关注 1,2,3
```

### sql 执行加载的顺序

写的sql语句： 我们写的顺序是  `SELECT  XX   FROM ....`

```
SELECT
a.id
FROM
insurance_base AS a
WHERE
a.id = 1
GROUP BY
a.province_id
HAVING ...
ORDER BY
a.id ASC
LIMIT 1, 3
```

![JAVA_HIGHMYSQL5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL5.png?raw=true)

 我们以为的执行顺序是按照sql写的顺序执行， 但是机器自有一套标准 ，来解析和理解sql的

![JAVA_HIGHMYSQL6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL6.png?raw=true)

```
sql执行顺序 ：
(1)from  先找到要操作的主要目标
(3) join  
(2) on   找到要操作的次要目标
(4) where 
(5)group by(开始使用select中的别名，后面的语句中都可以使用)
(6) avg,sum.... 
(7)having 
(8) select 
(9) distinct 
(10) order by 
从这个顺序中我们不难发现，所有的 查询语句都是从from开始执行的，在执行过程中，每个步骤都会为下一个步骤生成一个虚拟表，这个虚拟表将作为下一个执行步骤的输入。  相当于一个漏斗，获取一个输入，过滤后，有一个输出
  
```

![JAVA_HIGHMYSQL7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL7.png?raw=true)

#### 所有的join(sql执行顺序的应用)

![JAVA_HIGHMYSQL8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL8.png?raw=true)

 要理解join ，就需要先理解sql执行顺序，最开始的输入是from 关联的表，后面的都是对这个大的输入的一层一层的削减过滤

![JAVA_HIGHMYSQL9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL9.png?raw=true)

inner join 取交集， full  join 取全集  ，left / right  取偏集(这是自造词语，便于记忆)

注意： mysql 不支持full join 全连接 ，如果想达到类似的效果。就需要使用unnion 变通一下求全集= 左边全占+右独享

(union 天生会直接去重重复记录，就可以直接 左全占+ 右全占)

```sql
SELECT  * FROM 	a LEFT JOIN b on  a.id = b.id
UNION
SELECT  * FROM 	a RIGHT JOIN b on  a.id = b.id
```

全集-交集= A的独占+B的独占

```sql
SELECT  * FROM 	a LEFT JOIN b on  a.id = b.id where b.id is null
UNION
SELECT  * FROM 	a RIGHT JOIN b on  a.id = b.id where a.id is null
```

### 什么是索引

```
索引(Index):mysql官方给出的定义就是:索引是帮助mysql高效获取数据的数据结构,由此我们可以得到索引的本质其实是一种数据结构.可以简单的理解为是排好序的快速查找数据结构.(如果在面试的过程成被问到,千万不要直接说索引就像字典中的前面排好序的目录.)
在mysql中,数据库系统中还维护着满足特定查找算法的数据结构,这些数据结构以某种方式引用(指向)数据(也就是指针的意思),这样我们就可以在这些数据结构中实现高级查找算法,这种数据结构及时索引.

MySQL官方对索引的定义为：索引(Index)是帮助MySQL高效获取数据的数据结构。
可以得到索引的本质：索引是数据结构 ，可以简单理解为"排好序的快速查找数据结构"。
索引的目的在于提高效率，类比字典

```



#### 为什创建索引后查询变快

```
没建索引之前是数据是杂乱无章的，建立索引之后，会对数据进行排序。可以使用查找算法进行查询

数据本身之外,数据库还维护着一个满足特定查找算法的数据结构，这些数据结构以某种方式指向数据，这样就可以在这些数据结构的基础上实现高级查找算法,这种数据结构就是索引（常用的数据结构是B树）。

索引要查的快，做的基本功就是排序。

索引用于排序和快速查找。 所以索引影响两部分：where后面的查找 和  order by后面的排序 (加快这两部分速率)
优势：类似大学图书馆建书目索引，提高数据检索效率，降低数据库的IO成本
     通过索引列对数据进行排序，降低数据排序成本，降低了CPU的消耗
劣势：
1：实际上索引也是一张表，该表保存了主键和索引字段，并指向实体表的记录,所以索引列也是要占用空间的
2：虽然索引大大提高了查询速度，同时却会降低更新表的速度,如果对表INSERT,UPDATE和DELETE。因为更新表时，MySQL不仅要不存数据，还要保存一下索引文件每次更新添加了索引列的字段，都会调整因为更新所带来的键值变化后的索引信息
(就是改数据，不仅改到了数据，还改到了索引)

索引只是提高效率的一个因素，如果你的MySQL有大数据量的表，就需要花时间研究建立优秀的索引，或优化查询语句。索引优化不是一朝一夕，是需要长期优化
```

![JAVA_HIGHMYSQL10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL10.png?raw=true)

```
一般来说索引本身也很大，不可能全部存储在内存中，因此索引往往以文件形式存储在硬盘上
我们平时所说的索引，如果没有特别指明，都是指B树(多路搜索树，并不一定是二叉树)结构组织的索引。其中聚集索引，次要索引，覆盖索引，复合索引，前缀索引，唯一索引默认都是使用B+树索引，统称索引。当然,除了B+树这种类型的索引之外，还有哈希索引(hash index)等。
```

#### 索引的分类

```
单值索引：即一个索引只包含单个列，一个表可以有多个单列索引
唯一索引：索引列的值必须唯一，但允许有空值
复合索引：即一个索引包含多个列

（一般建议单表最多建立5个索引，因为在目前的阶段，我们同一时段只会加载一个sql引擎使用。）
```

#### mysql索引结构

```
BTree索引 (重点，其余了解)

Hash索引

full-text全文索引

R-Tree索引
```

BTree索引：

![JAVA_HIGHMYSQL12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL12.png?raw=true)



### 如何创建索引

![JAVA_HIGHMYSQL4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL4.png?raw=true)

示例：

```
MySQL中可以使用alter table这个SQL语句来为表中的字段添加索引。
使用alter table语句来为表中的字段添加索引的基本语法是：
ALTER TABLE <表名> ADD INDEX (<字段>);

示例：我们来尝试为test中t_name字段添加一个索引。
mysql> alter table test add index(t_name);

在已有的表中去建立索引
在已有的表中建立索引的语法：
 create 【unique|fulltext|spatial】index 索引名 on  表名（长度 【ASC|DESC】）；
例子：
create unique index index_id on sorc4(s_id);
```

#### 基本语法

```sql
创建：
CREATE [UNIQUE] INDEX  indexName ON mytable(columnname(length));

ALTER mytable ADD [UNIQUE]  INDEX [indexName] ON(columnname(length));
如果是CHAR,VARCHAR类型，length可以小于字段实际长度；如果是BLOB和TEXT类型，必须指定length。

删除:
DROP INDEX [indexName] ON mytable;

查看
SHOW INDEX FROM table_name

```

更改：

![JAVA_HIGHMYSQL11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL11.png?raw=true)

### 哪些情况需要创建索引

```
1.主键自动建立唯一索引
2.频繁作为查询的条件的字段应该创建索引 where 后的查询条件
3.查询中与其他表关联的字段，外键关系建立索引
4.查询中排序的字段，排序字段若通过索引去访问将大大提高排序的速度
5.单间/组合索引的选择问题，who？（在高并发下倾向创建组合索引）
6.查询中统计或者分组字段 (分组，统计之前必须排序)
```



### 哪些情况不要创建索引

```
1.经常增删改的表，频繁更新的字段不适合创建索引，因为每次更新不单单是更新了记录还会更新索引，加重IO负担
2.Where条件里用不到的字段不创建索引
3.表记录太少
4.数据重复且分布平均的表字段，因此应该只为经常查询和经常排序的数据列建立索引。注意，如果某个数据列包含许多重复的内容，为它建立索引就没有太大的实际效果。
```



### 如何进行sql性能分析

```
MySQL Query Optimizer:
MySQL Optimizer是一个专门负责优化SELECT 语句的优化器模块，它主要的功能就是通过计算分析系统中收集的各种统计信息，为客户端请求的Query 给出他认为最优的执行计划（但不是DBA认为最优的），也就是他认为最优的数据检索方式。

MySQL常见瓶颈
CPU:CPU在饱和的时候一般发生在数据装入在内存或从磁盘上读取数据时候
IO:磁盘I/O瓶颈发生在装入数据远大于内存容量时
服务器硬件的性能瓶颈：top,free,iostat和vmstat来查看系统的性能状态

```

#### Explain查看执行计划

![JAVA_HIGHMYSQL13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL13.png?raw=true)

```sql
使用EXPLAIN关键字可以模拟优化器执行SQL语句，从而知道MySQL是如何处理你的SQL语句的。分析你的查询语句或是结构的性能瓶颈

Explain能干嘛?
查看表的读取顺序  id 字段的值
数据读取操作的操作类型 select_type
哪些索引可以使用  possible_keys
哪些索引被实际使用 key
表之间的引用
每张表有多少行被优化器查询 rows 

怎么使用？ Explain+SQL语句
EXPLAIN SELECT * from car_brand

```

##### 执行计划包含的信息:各个字段解释

**id**:select查询的序列号，包含<<一组>>数字，表示查询中执行select子句或操作表的顺序

```
 三种情况:
    id相同，mysql内部优化器加载sql执行顺序由上至下
    id不同，如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
    id相同不同，同时存在
```

id 相同

![JAVA_HIGHMYSQL14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL14.png?raw=true)

id 不同

![JAVA_HIGHMYSQL15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL15.png?raw=true)

 id相同不同，同时存在

![JAVA_HIGHMYSQL16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL16.png?raw=true)

**select_type**:查询的类型，主要用于区别普通查询、联合查询、子查询等的复杂查询

![JAVA_HIGHMYSQL17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL17.png?raw=true)

```
1.SIMPLE：简单的select查询，查询中不包含子查询或者UNION
2.PRIMARY：查询中若包含任何复杂的子部分，最外层查询则被标记为主查询
3.SUBQUERY：在SELECT或者WHERE列表中包含了子查询
4.DERIVED：在FROM列表中包含的子查询被标记为DERIVED（衍生）MySQL会递归执行这些子查询，把结果放在临时表里。
5.UNION：若第二个SELECT出现在UNION之后，则被标记为UNION;若UNION包含在FROM子句的子查询中，外层SELECT将被标记为：DERIVED
6.UNION RESULT：从UNION表获取结果的SELECT
```

![JAVA_HIGHMYSQL18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL18.png?raw=true)

**table**:显示这一行的数据是关于哪张表的

**type**:访问类型排列

```
显示查询使用了何种类型
从最好到最差依次是：
system>const>eq_ref>ref>range>index>ALL
system：表只有一行记录（等于系统表），这是const类型的特例，平时不会出现，这个也可以忽略不计
const：表示通过索引一次就找到了，const用于比较primary key或者unique索引(说白了就是根据主键查找)。因为只匹配一行数据，所以很快。如将主键至于where列表中，MySQL就能将该查询转换为一个常量

eq_ref：唯一性索引，对于每个索引键，表中只有一条记录与之匹配，常见于主键或唯一索引扫描 (用到索引，查出来只有一条记录)

ref：非唯一索引扫描，返回匹配某个单独值的所有行。本质上也是一种索引访问，它返回所有匹配某个单独值的行，然而，它可能会找到多个符合条件的行，所以他应该属于查找和扫描的混合体

range：只检索给定范围的行，使用一个索引来选择行。key列显示使用了哪个索引一般就是在你的where语句中出现了between、<、>、in等的查询这种范围扫描索引扫描比全表扫描要好，因为他只需要开始索引的某一点，而结束语另一点，不用扫描全部索引

index：Full Index Scan,index与ALL区别为index类型只遍历索引树。这通常比ALL快，因为索引文件通常比数据文件小。（也就是说虽然all和index都是读全表，但index是从索引中读取的，而all是从硬盘中读的）
all：FullTable Scan,将遍历全表以找到匹配的行

一般来说，得保证查询只是达到range级别，最好达到ref
```

![JAVA_HIGHMYSQL19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL19.png?raw=true)

![JAVA_HIGHMYSQL20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL20.png?raw=true)

**possible_keys**:显示可能应用在这张表中的索引,一个或多个。查询涉及的字段上若存在索引，则该索引将被列出，**但不一定被查询实际使用**

**key:**实际使用的索引。如果为null则没有使用索引

查询中若使用了覆盖索引，则索引和查询的select字段重叠

![JAVA_HIGHMYSQL21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL21.png?raw=true)

**key_len**:表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。在不损失精确性的情况下，长度越短越好

key_len显示的值为索引最大可能长度，并非实际使用长度，即key_len是根据表定义计算而得，不是通过表内检索出的

**ref**:显示索引那一列被使用了，如果可能的话，是一个常数。那些列或常量被用于查找索引列上的值

![JAVA_HIGHMYSQL22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL22.png?raw=true)

![JAVA_HIGHMYSQL23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL23.png?raw=true)

**rows**:根据表统计信息及索引选用情况，大致估算出找到所需的记录所需要读取的行数

![JAVA_HIGHMYSQL24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL24.png?raw=true)

**Extra**：包含不适合在其他列中显示但十分重要的额外信息

```
1.Using filesort(九死无生):说明mysql会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。MySQL中无法利用索引完成排序操作成为“文件排序”. 重点关注"排序"，出现这个表示没有怎么使用内部索引排序

2.Using temporary(十死无生):使用了临时表保存中间结果，MySQL在对查询结果《排序时使用临时表》。常见于排序order by 和分组查询 group by

3.USING index:表示相应的select操作中使用了覆盖索引（Coveing Index）,避免访问了表的数据行，效率不错！
如果同时出现using where，表明索引被用来执行索引键值的查找；
如果没有同时出现using where，表面索引用来读取数据而非执行查找动作

4.Using where：表明使用了where过滤

5.using join buffer：使用了连接缓存

6.impossible where：where子句的值总是false，不能用来获取任何元组

7.select tables optimized away:在没有GROUPBY子句的情况下，基于索引优化MIN/MAX操作或者对于MyISAM存储引擎优化COUNT(*)操作，不必等到执行阶段再进行计算，查询执行计划生成的阶段即完成优化。

8.distinct:优化distinct，在找到第一匹配的元组后即停止找同样值的工作
```

![JAVA_HIGHMYSQL25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL25.png?raw=true)

## 索引优化

### 索引分析

案例： 查看相关教程视频31——44

```
1: 单表建立复合索引，如果where后索引是过滤字段会导致索引失效
2：左右连接外键加索引，左连接对右表的关联建加索引，右连接对左表的关联建加索引
3：劲量减少join中的循环次数，永远是小结果集驱动大结果集
4：多join最先优化最内层的循环，齿轮
```



### 建立索引之后如何保证索引不失效

(就是之前建立的索引顺序用不上了，不然索引失效的sql就是好sql)

```
高效sql的最佳左前缀法则：如果索引了多例，要遵守最左前缀法则。指的是查询从索引的最左前列开始并且不跳过索引的中间的列。(带头大哥不能死，火车头不能掉)
建立联合索引就是给每层楼搭梯子，一层，二层，3层，通过梯子去找，如果没又一层的梯子，没办法上2,3层的楼，不能建空中楼阁

范围之后全失效，但是范围是用到了索引的
```

![JAVA_HIGHMYSQL26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL26.png?raw=true)

![JAVA_HIGHMYSQL27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL27.png?raw=true)

```
不在索引列上做任何操作（计算、函数、（自动or手动）类型转换），会导致索引失效而转向全表扫描
```

![JAVA_HIGHMYSQL28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL28.png?raw=true)

```
不能使用索引中范围条件右边的列， 即范围之后全失效。 在复合索引中使用了范围，范围之后的全部失效
```

![JAVA_HIGHMYSQL29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL29.png?raw=true)

```
sql中尽量使用覆盖索引（只访问索引的查询（索引列和查询列一致）），减少select*
```

![JAVA_HIGHMYSQL30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL30.png?raw=true)

![JAVA_HIGHMYSQL31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL31.png?raw=true)

```
mysql在使用不等于（！=或者<>）的时候无法使用索引会导致全表扫描
```

![JAVA_HIGHMYSQL32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL32.png?raw=true)

```
is null,is not null 也无法使用索引
```

![JAVA_HIGHMYSQL33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL33.png?raw=true)

```
like以通配符开头（'$abc...'）mysql索引失效会变成全表扫描操作，
```

口诀：% like加右边

![JAVA_HIGHMYSQL34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL34.png?raw=true)

```
问题：解决like'%字符串%'索引不被使用的方法？？
推荐使用覆盖索引来解决。
```

![JAVA_HIGHMYSQL35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL35.png?raw=true)

覆盖索引，索引要覆盖select的字段(不包括id),select字段的个数和顺序最好完全一致

![JAVA_HIGHMYSQL36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL36.png?raw=true)

```
字符串不加单引号索引失效 （varchar类型绝对不能不写单引号）
如果varchar类型的没写单引号，如数字，mysql底层会进行隐式的类型转换，违背了mysql引擎不能再索引中进行类型转换
```

![JAVA_HIGHMYSQL37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL37.png?raw=true)

```
少用or,用它连接时会索引失效
```

![JAVA_HIGHMYSQL38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL38.png?raw=true)

总结：

![JAVA_HIGHMYSQL39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL39.png?raw=true)

(mysql架构中有一个sql优化器，他会自动的优化sql，安装他以为的最优执行)

所以这里索引中的即使没有按照索引顺序也使用了索引。 但是我们在书写sql的尽量还是按照索引顺序来，省略优化

![JAVA_HIGHMYSQL40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL40.png?raw=true)

```
一般性建议：
对于单键索引，尽量选择针对当前query过滤性更好的索引
在选择组合索引的时候，当前Query中过滤性最好的字段在索引字段顺序中，位置越靠前越好。(靠左)
在选择组合索引的时候，尽量选择可以能包含当前query中的where子句中更多字段的索引 (where子句中更多字段的索引)
尽可能通过分析统计信息和调整query的写法来达到选择合适索引的目的
```



### order by和group by 又有哪些讲究

```
定值、范围还是排序，一般order by是给个范围
group by 基本上都需要进行排序，会有临时表产生

order by和group by 都会使用到索引，最好是按照索引顺序排队分组，否则不发使用索引
```

#### order by关键字优化

```
ORDER BY子句，尽量使用Index方式排序，避免使用FileSort方式排
尽可能在索引列上完成排序操作，遵照索引建的最佳左前缀
(建立了索引，并按照使用到了索引进行排序，就能使用到索引，不会产生FileSort)
mysql 支持两种方式排序，Filesort 和Index， index效率高，是mysql扫描索引本身完成排序，Filesort效率低
order by 满足两种情况会使用index排序： (满足最佳左前缀原则)
order by 语句使用索引最左前列
使用where字句和order by字句条件组合满足索引最左前列
结论： 尽可能在索引上完成排序操作，满足最佳左前缀原则
```

![JAVA_HIGHMYSQL43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL43.png?raw=true)

![JAVA_HIGHMYSQL44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL44.png?raw=true)

#### GROUP BY关键字优化

```
groupby实质是先排序后进行分组，遵照索引建的最佳左前缀
当无法使用索引列，增大max_length_for_sort_data参数的设置+增大sort_buffer_size参数的设置
where高于having,能写在where限定的条件就不要去having限定了。
(其他的均和order by一样)
```




### 永远小表驱动大表

```
永远小表驱动大表，类似嵌套循环Nested Loop
原因： 
for(5)
{
    for(1000)
}
在数据库中不等价于
for(1000)
{
    for(5)
}
数据库最难搞的就是和你的连接和释放，第一种情况只建立了5次连接， 第二种情况建立了1000次连接
```

![JAVA_HIGHMYSQL42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL42.png?raw=true)


## 查询截取分析

sql 变慢了， 通过这个找出sql变慢的原因，并修正。

![JAVA_HIGHMYSQL41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL41.png?raw=true)

### 慢查询日志

是什么？

```
 MySQL的慢查询日志是MySQL提供的一种日志记录，它用来记录在MySQL中响应时间超过阀值的语句，具体指运行时间超过long_query_time值的SQL，则会被记录到慢查询日志中。long_query_time的默认值为10，意思是运行10S以上的语句。

默认情况下，Mysql数据库并不启动慢查询日志，需要我们手动来设置这个参数，当然，如果不是调优需要的话，一般不建议启动该参数，因为开启慢查询日志会或多或少带来一定的性能影响。慢查询日志支持将日志记录写入文件，也支持将日志记录写入数据库表。

查看默认开启状态
SHOW VARIABLES LIKE '%slow_query_log%'
开启的话，需要在mysql中设置slow_query_log 这个值
set global slow_query_log = 1
(默认只在本次开启中生效，这个功能不应该长期开启)

如果需要永久生效，必须修改配置文件my.cnf，在一启动的时候就加载生效
[mysqld]
slow_query_log=1
slow_query_log_file=/var/lib/mysql/low.log

查看当前多少秒算慢:SHOW VARIABLES LIKE 'long_query_time%';
设置慢的阙值时间:set global long_query_time=3;
(设置后：需要重新连接或者新开一个回话才能看到修改值。SHOW VARIABLES LIKE 'long_query_time%';)

如果你想查询有多少条慢查询记录，可以使用系统变量
show global status like '%slow_queries%'; 
(这个值可以作为一种系统健康检查度来查询)

mysql提供日志分析工具mysqldumpshow：

```

![JAVA_HIGHMYSQL45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL45.png?raw=true)

![JAVA_HIGHMYSQL46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL46.png?raw=true)

### 批量数据脚本

函数和存储过程就是用sql脚本语言写的数据库编程，他们的功能就是完成批次数据的插入

```
大数据的一次性的插入我们都不推荐，我们需要的是分阶段，分批次的提交进去
这样重复的操作，我们就可以使用数据库脚本编程来实现
```

![JAVA_HIGHMYSQL47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL47.png?raw=true)

![JAVA_HIGHMYSQL48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL48.png?raw=true)

### Show profiles进行sql分析

比explain更加细致的分析

```
是什么：是mysql提供可以用来分析当前会话中语句执行的资源消耗情况。可以用于SQL的调优测量 (类似于购物的明细)
默认情况下，参数处于关闭状态，并保存最近15次的运行结果
1.是否支持，看看当前的SQL版本是否支持 show variables like 'have_profiling';
2.开启功能，默认是关闭，使用前需要开启 SET profiling = 1; set profiling=on;
3.运行SQL
4.查看结果，show profiles; ，将展示刚才运行的SQL语句
5.诊断SQL，show profile cpu,block io for query 上一步前面的问题SQL 数字号码；
```

![JAVA_HIGHMYSQL49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL49.png?raw=true)

![JAVA_HIGHMYSQL50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL50.png?raw=true)

![JAVA_HIGHMYSQL51.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL51.png?raw=true)

```
6.日常开发需要注意的结论 (生命周期中出现下面情况就表示sql存在问题)
converting HEAP to MyISAM 查询结果太大，内存都不够用了往磁盘上搬了。
Creating tmp table 创建临时表，拷贝数据到临时表用完再删除
Copying to tmp table on disk 把内存中临时表复制到磁盘，危险！！！
locked
```



### 全局查询日志

只允许在测试环境中使用，绝不可在生产中使用

如何开启使用

![JAVA_HIGHMYSQL52.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL52.png?raw=true)

(不常用)

## Mysql锁机制

锁的本质就是防止挣抢，同一时间只有一个人在操作

![JAVA_HIGHMYSQL53.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL53.png?raw=true)

![JAVA_HIGHMYSQL54.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL54.png?raw=true)



### 表锁(偏读)

MYISM 引擎

```
如何加表锁
Lock tables orders read , order_detail read ;
(orders,order_detail 是表名 read是加锁的形式，read 是读锁，write是写锁)
解锁
Unlock tables;
```



加读锁

![JAVA_HIGHMYSQL55.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL55.png?raw=true)

![JAVA_HIGHMYSQL56.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL56.png?raw=true)

![JAVA_HIGHMYSQL57.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL57.png?raw=true)

加写锁

![JAVA_HIGHMYSQL58.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL58.png?raw=true)

![JAVA_HIGHMYSQL59.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL59.png?raw=true)

![JAVA_HIGHMYSQL60.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL60.png?raw=true)

总结： 简而言之，读锁会阻塞写，但是不会阻塞读，而写锁会把读写都阻塞

![JAVA_HIGHMYSQL61.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL61.png?raw=true)

表锁分析

![JAVA_HIGHMYSQL62.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL62.png?raw=true)

### 行锁(偏写)

InnoDB引擎

```
偏向InnoDB存储引擎，开销大，加锁慢；会出现死锁；锁定粒度最小，发生锁冲突的概率最低，并发度也最高。
 InnoDB与MyISAM的最大不同有两点：一是支持事务（TRANSACTION）;二是采用了行级锁
 
 ACID 中C 一致性: 可以理解事务操作完成后数据的正确性
```

![JAVA_HIGHMYSQL63.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL63.png?raw=true)

![JAVA_HIGHMYSQL64.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL64.png?raw=true)

![JAVA_HIGHMYSQL65.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL65.png?raw=true)

![JAVA_HIGHMYSQL66.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL66.png?raw=true)

无索引行锁升级为表锁

索引建立后使用不当，会导致行锁升级为表锁

![JAVA_HIGHMYSQL67.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL67.png?raw=true)

间隙锁危害

![JAVA_HIGHMYSQL68.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL68.png?raw=true)

面试题：常考如何锁定一行

![JAVA_HIGHMYSQL69.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL69.png?raw=true)

行锁总结

![JAVA_HIGHMYSQL70.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL70.png?raw=true)

```
尽可能让所有数据检索都通过索引来完成，避免无索引行锁升级为表锁
合理设计索引，尽量缩小锁的范围
尽可能较少检索条件，避免间隙锁
尽量控制事务大小，减少锁定资源量和时间长度
尽可能低级别事务隔离
```



### 页锁

开销和加锁时间界于表锁和行锁之间：会出现死锁；锁定粒度界于表锁和行锁之间，并发度一般。

## 主从复制

![JAVA_HIGHMYSQL71.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL71.png?raw=true)

```
复制的基本原理:
slave会从master读取binlog来进行数据同步
master将改变记录到二进制日志（binary log）。这些记录过程叫做二进制日志事件，binary log events
slave将master的binary log ebents拷贝到它的中继日志（relay log）
slave重做中继日志中的时间，将改变应用到自己的数据库中。MySQL复制是异步的且串行化的

复制的基本原则:
每个slave只有一个master
每个slave只能有一个唯一的服务器ID
每个master可以有多个salve

复制最大问题:延时

一主一从常见配置:
```

![JAVA_HIGHMYSQL72.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL72.png?raw=true)

![JAVA_HIGHMYSQL73.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL73.png?raw=true)

![JAVA_HIGHMYSQL74.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL74.png?raw=true)

主从机之间建立关系

```
主机：GRANT REPLICATION SLAVE  ON*.* TO 'zhangsan'@'从机器数据库IP‘ IDENTIFIED BY '123456';
从机：CHANGE MASTER TO MASTER_HOST='主机IP',MASTER_USER='zhangsan'，MASTER_PASSWORD='123456',MASTER_LOG_FILE='File名字'，MASTER_LOG_POS=Position数字；
```

![JAVA_HIGHMYSQL75.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL75.png?raw=true)

![JAVA_HIGHMYSQL76.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_HIGHMYSQL76.png?raw=true)


