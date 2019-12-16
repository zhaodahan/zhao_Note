# redis基础回顾笔记

Redis是一个key-value存储系统，是当下互联网公司常用的NoSQL数据库之一

# NOSQL

redis是nosql的一种落地实现。

## 我们已经有了关系型数据库，为什么还会有NOSQL

![JAVA_REDIS6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS6.png?raw=true)

**横向扩展和纵向扩展**

```
纵向： 类似于我们计算机中的扩内存， 不断的加内存条
横向： 类似于多核处理器。 我们将几个电脑封装起来(集群)，对外的时候别人看起来就像是一台电脑而已
```

**单机MYSQL**

![JAVA_REDIS1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS1.png?raw=true)

```java
我们做的简单的项目，基本访问量不大，使用单个数据库就足以响应。
这种项目一般就三层结构。 就app——》应用层——》数据库

但是总有一天，当我们的数据量增大到一定程度，成为了瓶颈后。
随着当下互联网的飞速发张，随随便便的数据量都是破百万千万的。关系型数据库已经不堪负重。
当数据量庞大之后，为了优化查询我们会建立数据库索引。这个时候数据库的索引，一般的机器都会放不下。
且当我们的访问量增大了以后，(读写混合)一个实例不能承受。这时候我们常常使用的优化方式就是读写分离，分库分表。
如果我们的系统无法满足访问量递增之后的性能要求，这时候就需要改变我们的架构

```

 **Memcached(缓存)+MySQL+垂直拆分**

![JAVA_REDIS2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS2.png?raw=true)

```java
随着访问量的上升，几乎大部分使用MySQL架构的网站在数据库上都开始出现了性能问题，web程序不再仅仅专注在功能上，同时也在追求性能。程序员们开始大量的使用缓存技术来缓解数据库的压力，优化数据库的结构和索引。

开始比较流行的是通过文件缓存来缓解数据库压力，但是当访问量继续增大的时候，多台web机器通过文件缓存不能共享，大量的小文件缓存也带了了比较高的IO压力。在这个时候，Memcached就自然的成为一个非常时尚的技术产品。

总结： 就是在数据库的前面加一个缓存层
```

**Mysql主从读写分离**

![JAVA_REDIS3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS3.png?raw=true)

```
由于数据库的写入压力增加，Memcached只能缓解数据库的读取压力。读写集中在一个数据库上让数据库不堪重负，大部分网站开始使用主从复制技术来达到读写分离，以提高读写性能和读库的可扩展性。Mysql的master-slave模式成为这个时候的网站标配了。

对于第一个数据库的信息，“写” 我们都放在主库，“读” 我们都放在从库
```

**分表分库+水平拆分+mysql集群**

(主库的写压力出现了瓶颈)

![JAVA_REDIS4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS4.png?raw=true)

```
在Memcached的高速缓存，MySQL的主从复制，读写分离的基础之上，这时MySQL主库的写压力开始出现瓶颈，而数据量的持续猛增，由于MyISAM使用表锁，在高并发下会出现严重的锁问题，大量的高并发MySQL应用开始使用InnoDB引擎代替MyISAM。

同时，开始流行使用分表分库来缓解写压力和数据增长的扩展问题。这个时候，分表分库成了一个热门技术

MySQL推出了MySQL Cluster集群，但性能也不能很好满足互联网的要求，只是在高可靠性上提供了非常大的保证。
```

**MySQL的扩展性瓶颈**

```
即使进行了类似于上面的这么多的优化，mysql任然可能存储瓶颈。如： 大数据，图片的存储。
```

**今天的架构**

![JAVA_REDIS5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS5.png?raw=true)

**为什么使用NoSQL**

```
随着数据量的增长，和关系的越来越复杂。 如：复杂的社交关系 ，如Facebook等软件中的社交关系(全球)

传统的关系型数据库已经不能够满足需求。 

```

## 什么是NOSQL

```
NoSQL(NoSQL = Not Only SQL )，意即“不仅仅是SQL”。泛指非关系型的数据库

这些类型的数据存储不需要固定的模式，无需多余操作就可以横向扩展。

固定的模式：  
我们在关系型数据库中。定义一张表person， 其中包含字段id,name，age...
如果我们想要在这个表总增加一列字段，需要去修改表的结构。 他是十分严格且有限的。一个表不可能有一万个字段。但是对于大数据时代这些修改特点非常巨量，我们的数据也非常的巨量。传统的关系型数据库已经不能满足需求了。

```

### NOSQL 能干什么

```
易扩展
数据之间无关系(对表横向，纵向的扩展无压力)，这样就非常容易扩展。也无形之间，在架构的层面上带来了可扩展的能力。
所以数据之间没有关系，那么我们可以用一个统一的对象，如Map(key-value字符串) 来操作所有的数据。 那么这样程序的扩展性不就更强了吗？ 如： 我们做分页操作，查询不同的数据我们都试用一个方法，一个对象去接收不同的数据就可以了

大数据量高性能
NOSQL 数据库常用来做缓存。可想而知其速度性能。 

多样灵活的数据模型
关系数据库里，增删字段是一件非常麻烦的事情。如果是非常大数据量的表，增加字段简直就是一个噩梦。数据量大后增删数据列对数据表的影响就很大。
但是对于nosql就不存在这样的问题。他们本质上就类似于一个键值对。NoSQL无需事先为要存储的数据建立字段，随时可以存储自定义的数据格式。

常用表现形式
KV
Cache
Persistence
```

**传统关系型数据库与NOSQL比较**

```
RDBMS vs NoSQL
 
RDBMS
- 高度组织化结构化数据
- 结构化查询语言（SQL）
- 数据和关系都存储在单独的表中。
- 数据操纵语言，数据定义语言
- 严格的一致性
- 基础事务
 
NoSQL
- 代表着不仅仅是SQL
- 没有声明性查询语言
- 没有预定义的模式
-键 - 值对存储，列存储，文档存储，图形数据库
- 最终一致性，而非ACID属性
- 非结构化和不可预知的数据
- CAP定理
- 高性能，高可用性和可伸缩性

```

### NOSQL的数据模型

聚合模型

(1) key_value（KV键值）

(2)Bson

(3)列族

![JAVA_REDIS7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS7.png?raw=true)

(4)图形

![JAVA_REDIS8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS8.png?raw=true)

### NoSQL数据库的四大分类

**KV键值**

一般的使用都会使用它。如Redis 

**文档型数据库(bson格式比较多)**

一般的实现就是MongoDB

```
MongoDB 是一个基于分布式文件存储的数据库。由 C++ 语言编写。旨在为 WEB 应用提供可扩展的高性能数据存储解决方案。
 
MongoDB 是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的。
```

**列存储数据库**

```
Cassandra, HBase
分布式文件系统
```

**图关系数据库**

```
它不是放图形的，放的是关系比如:朋友圈社交网络、广告推荐系统
社交网络，推荐系统等。专注于构建关系图谱
Neo4J, InfoGrid
```

### 分布式数据库中CAP原理CAP+BASE

![JAVA_REDIS9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS9.png?raw=true)

```
CAP理论的核心是：一个分布式系统不可能同时很好的满足一致性，可用性和分区容错性这三个需求，
最多只能同时较好的满足两个。
CA - 单点集群，满足一致性，可用性的系统，通常在可扩展性上不太强大。(侧重强一致性)
CP - 满足强一致性，分区容忍必的系统，通常性能不是特别高。  (redis ,Mongodb)
AP - 满足可用性，分区容忍性的系统，通常可能对一致性要求低一些。 (在使用NOSQL的场景，使用的最常用，大多数网站架构的选择，需要高可用+ 最终一致性)

对于我们NOSQL数据库，分区容忍性是我们必须需要实现的

```

![JAVA_REDIS10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS10.png?raw=true)

什么是分区容错性 

```
简而言之：就是多个节点数据备份

一个分布式系统里面，节点组成的网络本来应该是连通的。然而可能因为一些故障，使得有些节点之间不连通了，整个网络就分成了几块区域。数据就散布在了这些不连通的区域中。这就叫分区。

当你一个数据项只在一个节点中保存，那么分区出现后，和这个节点不连通的部分就访问不到这个数据了。这时分区就是无法容忍的。

提高分区容忍性的办法就是一个数据项复制到多个节点上，那么出现分区之后，这一数据项就可能分布到各个区里。容忍性就提高了。

然而，要把数据复制到多个节点，就会带来一致性的问题，就是多个节点上面的数据可能是不一致的。要保证一致，每次写操作就都要等待全部节点写成功，而这等待又会带来可用性的问题。总的来说就是，数据存在的节点越多，分区容忍性越高，但要复制更新的数据就越多，一致性就越难保证。为了保证一致性，更新所有节点数据所需要的时间就越长，可用性就会降低。
```

**Base**

```
BASE其实是下面三个术语的缩写：
    基本可用（Basically Available）
    软状态（Soft state）
    最终一致（Eventually consistent）
它的思想是通过让系统放松对某一时刻数据一致性的要求来换取系统整体伸缩性和性能上改观。 就是牺牲C换取A
```

### 分布式+集群

```
分布式：不同的多台服务器上面部署不同的服务模块（工程），他们之间通过Rpc/Rmi之间通信和调用，对外提供服务和组内协作
分布式就是分工协作

集群：不同的多台服务器上面部署相同的服务模块，通过分布式调度软件进行统一的调度，对外提供服务和访问。
集群就是人多力量大。
```

# Redis

## redis 是什么

Redis是 **分布式** **内存** 数据库

```
Redis:REmote DIctionary Server(远程字典服务器)
完全开源免费的，用C语言编写的，遵守BSD协议，是一个高性能的(key/value)分布式内存数据库，基于内存运行并支持持久化的NoSQL数据库，是当前最热门的NoSql数据库之一,也被人们称为数据结构服务器

三个特点
Redis支持数据的持久化，可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用
————内存存储和持久化：redis支持异步将内存中的数据写到硬盘上，同时不影响继续服务

Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储
Redis支持数据的备份，即master-slave模式的数据备份

redis能做的事：
取最新N个数据的操作，如：可以将最新的10条评论的ID放在Redis的List集合里面
模拟类似于HttpSession这种需要设定过期时间的功能
发布、订阅消息系统
定时器、计数器

redis服务器一般安装在Linux上。(他类似于mysql的模式,他的本质是一个数据库)
```

## Redis启动后杂项基础知识讲解

```
1: Redis单进程 
单进程模型来处理客户端的请求。
对读写等事件的响应，是通过对epoll函数的包装来做到的。Redis的实际处理速度完全依靠主进程的执行效率
(Epoll 是一种高效的管理socket的模型,Epoll是Linux内核为处理大批量文件描述符而作了改进的epoll，是Linux下多路复用IO接口select/poll的增强版本，它能显著提高程序在大量并发连接中只有少量活跃的情况下的系统CPU利用率。)

2：Redis默认有16个库
默认16个数据库，类似数组下表从零开始，初始默认使用零号库 (和数据库服务下有多个数据库类似)
Select命令切换数据库  select n （切换到n-1号库）
Dbsize查看当前数据库的key的数量
Flushdb：清空当前库
Flushall；通杀全部库
统一密码管理，16个库都是同样密码，要么都OK要么一个也连接不上
Redis索引都是从零开始

3：默认端口是6379
```

## Redis的五大数据类型

![JAVA_REDIS11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS11.png?raw=true)

这里所谓的类型是value 对应存储的类型。 

![JAVA_REDIS12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS12.png?raw=true)

```
String（字符串）
string是redis最基本的类型，一个key对应一个value。（最常用）一个redis中字符串value最多可以是512M
string类型是二进制安全的。意思是redis的string可以包含任何数据。比如jpg图片或者序列化的对象 

Hash（哈希，类似java里的Map）
Redis hash 是一个《键值对集合》。
Redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象。类似Java里面的Map<String,Object> 
KV模式不变，但V是一个键值对

List（列表）
Redis 列表是简单的字符串列表(LinkedList,它的底层实际是个链表)，按照插入顺序排序。你可以添加一个元素导列表的头部（左边 lpush）或者尾部（右边 rpush）。 
lrange key 0 -1 查看list中的所有值
push ,pop 这些命令类似于java中操作栈。


Set（集合）
Redis的Set是string类型的无序集合。它是通过HashTable实现实现的(HashTable 的底层就是new了一个hashMap)

Zset(sorted set：有序集合)
Redis zset 和 set 一样也是string类型元素的集合,且不允许重复的成员。
不同的是zset中每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。zset的成员是唯一的,但分数(score)却可以重复。

```



## Redis配置 redis.cof

```
参数说明
redis.conf 配置项说明如下：
1. Redis默认不是以守护进程的方式运行，可以通过该配置项修改，使用yes启用守护进程
  daemonize no
2. 当Redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件，可以通过pidfile指定
  pidfile /var/run/redis.pid
3. 指定Redis监听端口，默认端口为6379，作者在自己的一篇博文中解释了为什么选用6379作为默认端口，因为6379在手机按键上MERZ对应的号码，而MERZ取自意大利歌女Alessia Merz的名字
  port 6379
4. 绑定的主机地址
  bind 127.0.0.1
5.当 客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能
  timeout 300
6. 指定日志记录级别，Redis总共支持四个级别：debug、verbose、notice、warning，默认为verbose
  loglevel verbose
7. 日志记录方式，默认为标准输出，如果配置Redis为守护进程方式运行，而这里又配置为日志记录方式为标准输出，则日志将会发送给/dev/null
  logfile stdout
8. 设置数据库的数量，默认数据库为0，可以使用SELECT <dbid>命令在连接上指定数据库id
  databases 16
9. 指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合
  save <seconds> <changes>
  Redis默认配置文件中提供了三个条件：
  save 900 1
  save 300 10
  save 60 10000
  分别表示900秒（15分钟）内有1个更改，300秒（5分钟）内有10个更改以及60秒内有10000个更改。
 
10. 指定存储至本地数据库时是否压缩数据，默认为yes，Redis采用LZF压缩，如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大
  rdbcompression yes
11. 指定本地数据库文件名，默认值为dump.rdb
  dbfilename dump.rdb
12. 指定本地数据库存放目录
  dir ./
13. 设置当本机为slav服务时，设置master服务的IP地址及端口，在Redis启动时，它会自动从master进行数据同步
  slaveof <masterip> <masterport>
14. 当master服务设置了密码保护时，slav服务连接master的密码
  masterauth <master-password>
15. 设置Redis连接密码，如果配置了连接密码，客户端在连接Redis时需要通过AUTH <password>命令提供密码，默认关闭
  requirepass foobared
16. 设置同一时间最大客户端连接数，默认无限制，Redis可以同时打开的客户端连接数为Redis进程可以打开的最大文件描述符数，如果设置 maxclients 0，表示不作限制。当客户端连接数到达限制时，Redis会关闭新的连接并向客户端返回max number of clients reached错误信息
  maxclients 128
17. 指定Redis最大内存限制，Redis在启动时会把数据加载到内存中，达到最大内存后，Redis会先尝试清除已到期或即将到期的Key，当此方法处理 后，仍然到达最大内存设置，将无法再进行写入操作，但仍然可以进行读取操作。Redis新的vm机制，会把Key存放内存，Value会存放在swap区
  maxmemory <bytes>
18. 指定是否在每次更新操作后进行日志记录，Redis在默认情况下是异步的把数据写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失。因为 redis本身同步数据文件是按上面save条件来同步的，所以有的数据会在一段时间内只存在于内存中。默认为no
  appendonly no
19. 指定更新日志文件名，默认为appendonly.aof
   appendfilename appendonly.aof
20. 指定更新日志条件，共有3个可选值： 
  no：表示等操作系统进行数据缓存同步到磁盘（快） 
  always：表示每次更新操作后手动调用fsync()将数据写到磁盘（慢，安全） 
  everysec：表示每秒同步一次（折衷，默认值）
  appendfsync everysec
 
21. 指定是否启用虚拟内存机制，默认值为no，简单的介绍一下，VM机制将数据分页存放，由Redis将访问量较少的页即冷数据swap到磁盘上，访问多的页面由磁盘自动换出到内存中（在后面的文章我会仔细分析Redis的VM机制）
   vm-enabled no
22. 虚拟内存文件路径，默认值为/tmp/redis.swap，不可多个Redis实例共享
   vm-swap-file /tmp/redis.swap
23. 将所有大于vm-max-memory的数据存入虚拟内存,无论vm-max-memory设置多小,所有索引数据都是内存存储的(Redis的索引数据 就是keys),也就是说,当vm-max-memory设置为0的时候,其实是所有value都存在于磁盘。默认值为0
   vm-max-memory 0
24. Redis swap文件分成了很多的page，一个对象可以保存在多个page上面，但一个page上不能被多个对象共享，vm-page-size是要根据存储的 数据大小来设定的，作者建议如果存储很多小对象，page大小最好设置为32或者64bytes；如果存储很大大对象，则可以使用更大的page，如果不 确定，就使用默认值
   vm-page-size 32
25. 设置swap文件中的page数量，由于页表（一种表示页面空闲或使用的bitmap）是在放在内存中的，，在磁盘上每8个pages将消耗1byte的内存。
   vm-pages 134217728
26. 设置访问swap文件的线程数,最好不要超过机器的核数,如果设置为0,那么所有对swap文件的操作都是串行的，可能会造成比较长时间的延迟。默认值为4
   vm-max-threads 4
27. 设置在向客户端应答时，是否把较小的包合并为一个包发送，默认为开启
  glueoutputbuf yes
28. 指定在超过一定的数量或者最大的元素超过某一临界值时，采用一种特殊的哈希算法
  hash-max-zipmap-entries 64
  hash-max-zipmap-value 512
29. 指定是否激活重置哈希，默认为开启（后面在介绍Redis的哈希算法时具体介绍）
  activerehashing yes
30. 指定包含其它的配置文件，可以在同一主机上多个Redis实例之间使用同一份配置文件，而同时各个实例又拥有自己的特定配置文件
  include /path/to/local.conf

```



## Redis的持久化 rdb aof

Redis的持久化就是red的备份。

### RDB（Redis DataBase）

在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存里

![JAVA_REDIS13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS13.png?raw=true)

```
Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能如果需要进行大规模数据的恢复，且对于《数据恢复的完整性不是非常敏感》，那RDB方式要比AOF方式更加的高效。RDB的缺点是最后一次持久化后的数据可能丢失。

Fork的作用是复制一个与当前进程一样的进程。新进程的所有数据（变量、环境变量、程序计数器等）数值都和原进程一致，但是是一个全新的进程，并作为原进程的子进程

注意，他做的是全量更新。Rdb 保存的是dump.rdb文件

如何触发RDB快照？
1：配置文件中默认的快照配置
2：命令save或者是bgsave
Save：save时只管保存，其它不管，全部阻塞
BGSAVE：Redis会在后台异步进行快照操作，快照同时还可以响应客户端请求。可以通过lastsave命令获取最后一次成功执行快照的时间
3:执行flushall命令，也会产生dump.rdb文件，但里面是空的，无意义

如何恢复?
1:将备份文件 (dump.rdb) 移动到 redis 安装目录并启动服务即可
2:CONFIG GET dir获取目录
```



### AOF（Append Only File）

![JAVA_REDIS14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS14.png?raw=true)

```
AOF的出现是为了弥补RDB可能丢失最后一次备份的问题

AOF是什么？ 

就是类似一个日志脚本，以日志的形式来记录每个写操作，将Redis执行过的所有写指令记录下来(读操作不记录)。
当我们需要进行恢复的时候，就执行日志中记录的操作。这样我们就可以获得一个一样的数据了
只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作

Aof保存的是appendonly.aof文件

AOF的备份策略Appendfsync？
Always：同步持久化 每次发生数据变更会被立即记录到磁盘  性能较差但数据完整性比较好
Everysec：出厂默认推荐，异步操作，每秒记录   如果一秒内宕机，有数据丢失
No

Rewrite 重写
AOF采用文件追加方式，文件会越来越大为避免出现此种情况，新增了重写机制,当AOF文件的大小超过所设定的阈值时，Redis就会启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集.可以使用命令bgrewriteaof

重写原理：
AOF文件持续增长而过大时，会fork出一条新进程来将文件重写(也是先写临时文件最后再rename)，遍历新进程的内存中数据，每条记录有一条的Set语句。重写aof文件的操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照有点类似
(就是对aof中的命令进行优化瘦身)

触发机制
Redis会记录上次重写时的AOF大小，默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M时触发 

优势： 可以灵活配置，每秒同步，尽可能的保证数据的一致性
劣势：相同数据集的数据而言aof文件要远大于rdb文件，恢复速度慢于rdb。Aof运行效率要慢于rdb,每秒同步策略效率较好，不同步效率和rdb相同

```

![JAVA_REDIS15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS15.png?raw=true)

### 总结(Which one)

```
RDB持久化方式能够在指定的时间间隔能对你的数据进行快照存储

AOF持久化方式记录每次对服务器写的操作,当服务器重启的时候会重新执行这些。命令来恢复原始的数据,AOF命令以redis协议追加保存每次写的操作到文件末尾.Redis还能对AOF文件进行后台重写,使得AOF文件的体积不至于过大

只做缓存：如果你只希望你的数据在服务器运行的时候存在,你也可以不使用任何持久化方式

建议：同时开启两种持久化方式：
在这种情况下,当redis重启的时候会优先载入AOF文件来恢复原始的数据,因为在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整
RDB的数据不实时，同时使用两者时服务器重启也只会找AOF文件。那要不要只使用AOF呢？
作者建议不要，因为RDB更适合用于备份数据库(AOF在不断变化不好备份)，快速重启，而且不会有AOF可能潜在的bug，留着作为一个万一的手段。
```

最终的性能建议：(需要配合主从复制使用)

```
因为RDB文件只用作后备用途，建议只在Slave上持久化RDB文件，而且只要15分钟备份一次就够了，只保留save 900 1这条规则。
 
如果Enalbe AOF，好处是在最恶劣情况下也只会丢失不超过两秒数据，启动脚本较简单只load自己的AOF文件就可以了。代价一是带来了持续的IO，二是AOF rewrite的最后将rewrite过程中产生的新数据写到新文件造成的阻塞几乎是不可避免的。只要硬盘许可，应该尽量减少AOF rewrite的频率，AOF重写的基础大小默认值64M太小了，可以设到5G以上。默认超过原大小100%大小时重写可以改到适当的数值。
 
如果不Enable AOF ，仅靠Master-Slave Replication 实现高可用性也可以。能省掉一大笔IO也减少了rewrite时带来的系统波动。代价是如果Master/Slave同时倒掉，会丢失十几分钟的数据，启动脚本也要比较两个Master/Slave中的RDB文件，载入较新的那个。新浪微博就选用了这种架构

```

## 事务

```
事务的本质是一组命令的集合，按顺序地串行化执行而不会被其它命令插入，不许加塞
(redis 的事务就是多个Redis命令一起执行)

Redis支持事务吗，它能干什么？
一个队列中，一次性、顺序性、排他性的执行一系列命令

如何使用Redis事务？
使用MULTI命令(open tranction)输入一个Redis事务。该命令总是返回OK。此时，用户可以发出多个命令。Redis将不执行这些命令，而是对它们进行排队。一旦调用EXEC（commit），命令就会执行相反，调用丢弃将刷新事务队列并退出事务。

```

![JAVA_REDIS16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS16.png?raw=true)

Redis对事务的支持是部分支持。
体现在运行时异常上。如果是在加入事务时运行时抛异常，支持事务。否则不支持


![JAVA_REDIS17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS17.png?raw=true)

### watch监控

```
缓存中的数据，大家都来修改，为了避免冲突和覆盖。为其加上标记

悲观锁/乐观锁/CAS(Check And Set)
悲观锁：对事情的发展很悲观，认为一定会出事。为了避免出事，将整张表都锁住。(表锁，并发性差，但是一致性更好)

乐观锁：很乐观，不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制(在每条记录后面加一个版本号来保证数据的一致性)。一般使用的都是乐观锁
乐观锁策略:提交版本必须大于记录当前版本才能执行更新

watch监控：
先监控再开启multi，保证两笔金额变动在同一个事务内
Watch指令，类似乐观锁，事务提交时，如果Key的值已被别的客户端改变，比如某个list已被别的客户端push/pop过了，整个事务队列都不会被执行。
通过WATCH命令在事务执行之前监控了多个Keys，倘若在WATCH之后有任何Key的值发生了变化，EXEC命令执行的事务都将被放弃，同时返回Nullmulti-bulk应答以通知调用者事务执行失败

一旦执行了exec之前加的监控锁都会被取消掉了
```

![JAVA_REDIS18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS18.png?raw=true)

## Redis的发布订阅

![JAVA_REDIS19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS19.png?raw=true)

```
发布与订阅就类似于消息，要收到消息就需要先订阅
进程间的一种消息通信模式：发送者(pub)发送消息，订阅者(sub)接收消息。

Redis支持消息的发布和订阅，但很少使用，因为他的主业还是分布式的内存数据保存

先订阅后发布后才能收到消息，
1 可以一次性订阅多个，SUBSCRIBE c1 c2 c3
 
2 消息发布，PUBLISH c2 hello-redis
=================================================================================================== 
3 订阅多个，通配符*， PSUBSCRIBE new*
4 收取消息， PUBLISH new1 redis2015

就类似于公共聊天室的效果
```

![JAVA_REDIS20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS20.png?raw=true)



## Redis的复制(Master/Slave)

Redis的主从复制和读写分离

主从复制：主机数据更新后根据配置和策略，自动同步到备机  的master/slaver机制，Master以写为主，Slave以读为主

能干什么？

读写分离，容灾恢复

```
怎么使用主从复制，读写分离？
1：配置我们的主从数据库是哪些
 配从(库)不配主(库)：对指定的从数据库进行配置，告诉它，它是从库。需要观察着主库。同步主库的数据
 从库配置：
 slaveof 主库IP 主库端口。
主从体系搭建完成后，每次从库与master断开之后，都需要配置从库重新连接，除非你配置进redis.conf文件(一般都写进配置文件)

2：主从复制常用3板斧

```

**一主二仆**

![JAVA_REDIS21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS21.png?raw=true)

![JAVA_REDIS22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS22.png?raw=true)

```
(一个Master两个Slave)
从机slave一旦监控上主机，就会将主机上所有的数据备份。(初次连接是全量备份，后面慢慢的是增量)
读写分离，只有主机能写，从机写会报错

默认情况下主机死了，从机会原地待命，不会上位。当主机复活仍然是老大
从机死了再复活后，如果没有将我们的配置信息写入到配置文件，这次复活的机器会是一个原生机器，不在原来的主从体系中。(印证了上面说的从库与master断开之后，都需要配置从库重新连接)
```

![JAVA_REDIS26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS26.png?raw=true)

**薪火相传**

![JAVA_REDIS23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS23.png?raw=true)

```
解决的是去中心话，减轻主机的负担
中途变更转向:会清除之前的数据，重新建立拷贝最新的

```

![JAVA_REDIS24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS24.png?raw=true)

**反客为主**

```
使当前数据库停止与其他数据库的同步，转成主数据库

默认的主从复制都是主机挂了，从机待命。 但是实际的需求是需要我们从机上位(反客为主)，尽快响应。
这时候需要使用新的命令 SLAVEOF no one (主动的让当前从机上位，剩余从机改换门庭)

```

![JAVA_REDIS25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS25.png?raw=true)

### 哨兵模式(sentinel)

反客为主的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库

```
使用步骤：
1：在一主二从的模式下的配置文件所在目录下新建sentinel.conf文件。
2：配置哨兵
sentinel monitor 被监控数据库名字(自己起名字) 127.0.0.1 6379 1
上面最后一个数字1，表示主机挂掉后salve投票看让谁接替成为主机，得票数多少后成为主机
3：启动哨兵 (在多台机器之外单独启动)
edis-sentinel /myredis/sentinel.conf 

一组sentinel能同时监控多个Master
```

![JAVA_REDIS27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_REDIS27.png?raw=true)

哨兵模式的问题

```
如果之前的master重启回来，会不会双master冲突？
不会，以前的老大，复活后一开始已经脱离原有的主从体系。这时候会以一个原生的主机存在。但是他会被哨兵给检测到。 检测到后，因为原生体系中已经有一个新老大了，这时候原来的主机会作为一个新人，重新加入

```



## Redis的Java客户端Jedis

```java
public class JedisPoolUtil {
  
 private static volatile JedisPool jedisPool = null;//被volatile修饰的变量不会被本地线程缓存，对该变量的读写都是直接操作共享内存。
  
  private JedisPoolUtil() {}
  
  public static JedisPool getJedisPoolInstance()
 {
     if(null == jedisPool)
    {
       synchronized (JedisPoolUtil.class)
      {
          if(null == jedisPool)
         {
           JedisPoolConfig poolConfig = new JedisPoolConfig();
           poolConfig.setMaxActive(1000);
           poolConfig.setMaxIdle(32);
           poolConfig.setMaxWait(100*1000);
           poolConfig.setTestOnBorrow(true);
            
            jedisPool = new JedisPool(poolConfig,"127.0.0.1");
         }
      }
    }
     return jedisPool;
 }
  
  public static void release(JedisPool jedisPool,Jedis jedis)
 {
     if(null != jedis)
    {
      jedisPool.returnResourceObject(jedis);
    }
 }
}
 
```

测试

```java
public static void main(String[] args) {
     JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
     Jedis jedis = null;
     
     try 
     {
       jedis = jedisPool.getResource();
       jedis.set("k18","v183");
       
     } catch (Exception e) {
       e.printStackTrace();
     }finally{
       JedisPoolUtil.release(jedisPool, jedis);
     }
  }

 public static void main(String[] args) 
  {
 
     Jedis jedis = new Jedis("127.0.0.1",6379);
     //key
     Set<String> keys = jedis.keys("*");
     for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
       String key = (String) iterator.next();
       System.out.println(key);
     }
     System.out.println("jedis.exists====>"+jedis.exists("k2"));
     System.out.println(jedis.ttl("k1"));
     //String
     //jedis.append("k1","myreids");
     System.out.println(jedis.get("k1"));
     jedis.set("k4","k4_redis");
     System.out.println("----------------------------------------");
     jedis.mset("str1","v1","str2","v2","str3","v3");
     System.out.println(jedis.mget("str1","str2","str3"));
     //list
     System.out.println("----------------------------------------");
     //jedis.lpush("mylist","v1","v2","v3","v4","v5");
     List<String> list = jedis.lrange("mylist",0,-1);
     for (String element : list) {
       System.out.println(element);
     }
     //set
     jedis.sadd("orders","jd001");
     jedis.sadd("orders","jd002");
     jedis.sadd("orders","jd003");
     Set<String> set1 = jedis.smembers("orders");
     for (Iterator iterator = set1.iterator(); iterator.hasNext();) {
       String string = (String) iterator.next();
       System.out.println(string);
     }
     jedis.srem("orders","jd002");
     System.out.println(jedis.smembers("orders").size());
     //hash
     jedis.hset("hash1","userName","lisi");
     System.out.println(jedis.hget("hash1","userName"));
     Map<String,String> map = new HashMap<String,String>();
     map.put("telphone","13811814763");
     map.put("address","atguigu");
     map.put("email","abc@163.com");
     jedis.hmset("hash2",map);
     List<String> result = jedis.hmget("hash2", "telphone","email");
     for (String element : result) {
       System.out.println(element);
     }
     //zset
     jedis.zadd("zset01",60d,"v1");
     jedis.zadd("zset01",70d,"v2");
     jedis.zadd("zset01",80d,"v3");
     jedis.zadd("zset01",90d,"v4");
     
     Set<String> s1 = jedis.zrange("zset01",0,-1);
     for (Iterator iterator = s1.iterator(); iterator.hasNext();) {
       String string = (String) iterator.next();
       System.out.println(string);
     }
 
          
  }

```

事务

```java
 public static void main(String[] args) 
  {
 
     Jedis jedis = new Jedis("127.0.0.1",6379);
     //key
     Set<String> keys = jedis.keys("*");
     for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
       String key = (String) iterator.next();
       System.out.println(key);
     }
     System.out.println("jedis.exists====>"+jedis.exists("k2"));
     System.out.println(jedis.ttl("k1"));
     //String
     //jedis.append("k1","myreids");
     System.out.println(jedis.get("k1"));
     jedis.set("k4","k4_redis");
     System.out.println("----------------------------------------");
     jedis.mset("str1","v1","str2","v2","str3","v3");
     System.out.println(jedis.mget("str1","str2","str3"));
     //list
     System.out.println("----------------------------------------");
     //jedis.lpush("mylist","v1","v2","v3","v4","v5");
     List<String> list = jedis.lrange("mylist",0,-1);
     for (String element : list) {
       System.out.println(element);
     }
     //set
     jedis.sadd("orders","jd001");
     jedis.sadd("orders","jd002");
     jedis.sadd("orders","jd003");
     Set<String> set1 = jedis.smembers("orders");
     for (Iterator iterator = set1.iterator(); iterator.hasNext();) {
       String string = (String) iterator.next();
       System.out.println(string);
     }
     jedis.srem("orders","jd002");
     System.out.println(jedis.smembers("orders").size());
     //hash
     jedis.hset("hash1","userName","lisi");
     System.out.println(jedis.hget("hash1","userName"));
     Map<String,String> map = new HashMap<String,String>();
     map.put("telphone","13811814763");
     map.put("address","atguigu");
     map.put("email","abc@163.com");
     jedis.hmset("hash2",map);
     List<String> result = jedis.hmget("hash2", "telphone","email");
     for (String element : result) {
       System.out.println(element);
     }
     //zset
     jedis.zadd("zset01",60d,"v1");
     jedis.zadd("zset01",70d,"v2");
     jedis.zadd("zset01",80d,"v3");
     jedis.zadd("zset01",90d,"v4");
     
     Set<String> s1 = jedis.zrange("zset01",0,-1);
     for (Iterator iterator = s1.iterator(); iterator.hasNext();) {
       String string = (String) iterator.next();
       System.out.println(string);
     }
 
          
  }

=====加锁
public boolean transMethod() {
     Jedis jedis = new Jedis("127.0.0.1", 6379);
     int balance;// 可用余额
     int debt;// 欠额
     int amtToSubtract = 10;// 实刷额度
 
     jedis.watch("balance");
     //jedis.set("balance","5");//此句不该出现，讲课方便。模拟其他程序已经修改了该条目
     balance = Integer.parseInt(jedis.get("balance"));
     if (balance < amtToSubtract) {
       jedis.unwatch();
       System.out.println("modify");
       return false;
     } else {
       System.out.println("***********transaction");
       Transaction transaction = jedis.multi();
       transaction.decrBy("balance", amtToSubtract);
       transaction.incrBy("debt", amtToSubtract);
       transaction.exec();
       balance = Integer.parseInt(jedis.get("balance"));
       debt = Integer.parseInt(jedis.get("debt"));
 
       System.out.println("*******" + balance);
       System.out.println("*******" + debt);
       return true;
     }
  }
 
  /**
   * 通俗点讲，watch命令就是标记一个键，如果标记了一个键， 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况通常可以在程序中
   * 重新再尝试一次。
   * 首先标记了键balance，然后检查余额是否足够，不足就取消标记，并不做扣减； 足够的话，就启动事务进行更新操作，
   * 如果在此期间键balance被其它人修改， 那在提交事务（执行exec）时就会报错， 程序中通常可以捕获这类错误再重新执行一次，直到成功。
   */
  public static void main(String[] args) {
     TestTransaction test = new TestTransaction();
     boolean retValue = test.transMethod();
     System.out.println("main retValue-------: " + retValue);
  }

```

