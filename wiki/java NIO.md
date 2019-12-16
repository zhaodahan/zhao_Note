#                                                 java  NIO

------



## 什么是NIO

Java NIO（ New IO） 是从Java 1.4版本开始引入的一个新的IO API，可以替代标准的Java IO API。NIO与原来的IO有同样的作用和目的，但是使用的方式完全不同， NIO支持**面向缓冲区**的、**基于通道**的IO操作。 NIO将以更加高效的方式进行文件的读写操作。

## NIO与普通IO的主要区别

| IO                      | NIO                         |
| ----------------------- | --------------------------- |
| 面向流(Stream Oriented) | 面向缓冲区(Buffer Oriented) |
| 阻塞IO(Blocking IO)     | 非阻塞IO(Non Blocking IO)   |
| (无)                    | 选择器(Selectors)           |

> - Channels and Buffers（通道和缓冲区）：标准的IO基于字节流和字符流进行操作的，而NIO是基于通道（Channel）和缓冲区（Buffer）进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。
> - Asynchronous IO（异步IO）：Java NIO可以让你异步的使用IO，例如：当线程从通道读取数据到缓冲区时，线程还是可以进行其他事情。当数据被写入到缓冲区时，线程可以继续处理它。从缓冲区写入通道也类似。
> - Selectors（选择器）：Java NIO引入了选择器的概念，选择器用于监听多个通道的事件（比如：连接打开，数据到达）。因此，单个的线程可以监听多个数据通道。

## 缓冲区（Buffer）

通过上面NIO与普通IO的主要区别也可以看到在基本的IO操作中所有的操作都是基于流进行操作的，而在NIO中所有的操作都是基于缓冲区继续操作的，所有的读写操作都是通过缓存区来进行完成，缓冲区（Buffer）是一个线性的、有序的数据集，只能容纳特定的数据类型（基本就是基本数据类型对应的Buffer或者起子类）。

### 各各数据类型的缓存区类

| 缓存区类     | 相关描述               |
| ------------ | ---------------------- |
| ByteBuffer   | 存储字节的Buffer       |
| CharBuffer   | 存储字符的Buffer       |
| ShortBuffer  | 存储短整型的Buffer     |
| IntBuffer    | 存储整型的Buffer       |
| LongBuffer   | 存储长整型的Buffer     |
| FloatBuffer  | 存储单精度浮点型Buffer |
| DoubleBuffer | 存储双精度浮点型Buffer |

> **备注：**看到上面这几类是不是想起了JAVA的8种基本数据类型，**唯一缺少boolean**对于的类型。
>
> **第一问：为什么boolean不需要缓存呢？** 从二进制来看数字的内部表示和存储，boolean所占位数1bit（取值只有true或者false），由于字节(byte)是操作系统和所有I/O设备使用的基本数据类型，所以基本都是以字节或者连续的一段字节来存储表示，所以就没有boolean，感觉也没有必要有boolean类型的缓存操作。



### 缓冲区的基本属性

(缓存区的数据结构本质就是数组，为了方便其操作而带有一些特定的指针)

**容量 (capacity)**:表示 Buffer 最大数据容量，缓冲区容量不能为负，并且创建后不能更改。 (它代表着数组的长度)

**限制 (limit)**:第一个不应该读取或写入的数据的索引，即位于 **limit 后的数据不可读写**。缓冲区的限制不能为负，并且不能大于其容量。

**位置 (position)**:下一个要读取或写入的数据的索引。缓冲区的位置不能为负，并且不能大于其限制。

> **备注：**标记、 位置、 限制、 容量遵守以下不变式： 0 <= position <= limit <= capacity。

```
//第一步，获取IntBuffer，通过IntBuffer.allocate操作
IntBuffer buf = IntBuffer.allocate(10) ;	// 准备出10个大小的缓冲区

//第二步未操作前输出属性值
System.out.println("position = " + buf.position() + "，limit = " + buf.limit() + "，capacty = " + buf.capacity()) ;

//第三步进行设置数据
buf.put(6) ;	// 设置一个数据
buf.put(16) ;	// 设置二个数据

//第四步操作后输出属性值
System.out.println("position = " + buf.position() + "，limit = " + buf.limit() + "，capacty = " + buf.capacity()) ;

//第五步将Buffer从写模式切换到读模式 postion = 0 ,limit = 原本position
buf.flip() ;	 

//第六步操作后输出属性值
System.out.println("position = " + buf.position() + "，limit = " + buf.limit() + "，capacty = " + buf.capacity()) ;
```

程序输出结果：

```
position = 0，limit = 10，capacty = 10
position = 2，limit = 10，capacty = 10
position = 0，limit = 2，capacty = 10
```

查看下图来进行说明：

![Java_NIO.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_NIO.png?raw=true)

### Buffer使用

**读数据：**

```
flip()方法

- 将Buffer从写模式切换到读模式
- 调用flip()方法会将position设回0，并将limit设置成之前position的值。
- buf.flip();

buf.get()

- 读取数据

Buffer.rewind()

- 将position设回0，所以你可以重读Buffer中的所有数据
- limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）

Buffer.mark()方法，可以标记Buffer中的一个特定position。之后可以通过调用。

Buffer.reset()方法，恢复到Buffer.mark()标记时的position。

clear()方法会：

- 清空整个缓冲区。
- position将被设回0，limit被设置成 capacity的值

compact()方法：

- 只会清除已经读过的数据；任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
- 将position设到最后一个未读元素正后面，limit被设置成 capacity的值。
```

**写数据：**

```
 buf.put(127);
```





## 通道（Channel）

通道表示**打开到 IO 设备(例如：文件、套接字)的连接**。(通道是个连接，连接IO设备)

若需要使用 NIO 系统，需要获取用于连接 IO 设备的通道以及用于容纳数据的缓冲区。然后操作缓冲区，对数据进行处理。**Channel 负责传输， Buffer 负责存储**。通道是由 `java.nio.channels` 包定义的。 Channel 表示 IO 源与目标打开的连接。Channel 类似于传统的“流”。只不过 **Channel本身不能直接访问数据**， Channel 只能与Buffer 进行交互。

**通道都是操作缓存区完成全部的功能的。**

### Java中所有已知 Channel 实现类：

- ```
  - AbstractInterruptibleChannel
  - AbstractSelectableChannel
  - DatagramChannel
  - FileChannel
  - Pipe.SinkChannel
  - Pipe.SourceChannel
  - SelectableChannel
  - ServerSocketChannel
  - SocketChannel
  ```

**常用的有入下几个：**

- FileChannel：用于读取、写入、映射和操作文件的通道。
- DatagramChannel：通过 UDP 读写网络中的数据通道。
- SocketChannel：通过 TCP 读写网络中的数据。
- ServerSocketChannel：可以监听新进来的 TCP 连接，对每一个新进来的连接都会创建一个 SocketChannel。

### 获取通道

获取通道的一种方式是对支持通道的对象调用getChannel() 方法。支持通道的类如下：

- FileInputStream
- FileOutputStream
- RandomAccessFile
- DatagramSocket
- Socket
- ServerSocket

获取通道的其他方式是使用 Files 类的静态方法 newByteChannel() 获取字节通道。或者通过通道的静态方法 open() 打开并返回指定通道。

### FileChannel

- 为了更形象解释说明的Channel，以FileChannel的一些简单代码进行说明

- 准备以FileOutputStream类为准，这两个类都是支持通道操作的。

  ```java
  String info[] = {"欢迎","关注","匠心零度","的","公众号","谢谢！！"} ;
  File file = new File("d:" + File.separator + "testfilechannel.txt") ; 
  FileOutputStream output = null ;
  FileChannel fout = null;
  try {
    output = new FileOutputStream(file) ;
    fout = null;
    fout = output.getChannel() ;	// 得到输出的通道
    ByteBuffer buf = ByteBuffer.allocate(1024) ; 
    for(int i=0;i<info.length;i++){
        buf.put(info[i].getBytes()) ;	// 字符串变为字节数组放进缓冲区之中
    }
    buf.flip() ;
    fout.write(buf) ;	// 输出缓冲区的内容
  } catch (Exception e) {
    e.printStackTrace();
  }finally{
    if(fout!=null){
        try {
            fout.close() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if(output!=null){
        try {
            output.close() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }
  ```

  程序运行效果：

  ![Java_NIO2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_NIO2.png?raw=true)

------



## 文件锁（FileLock）

文件锁和其他我们了解并发里面的锁很多概念类似，当多个人同时操作一个文件的时候，只有第一个人可以进行编辑，其他要么关闭（等第一个人操作完成之后可以操作），要么以只读的方式进行打开。

> 在java nio中提供了新的锁文件功能，当一个线程将文件锁定之后，其他线程无法操作此文件，文件的锁操作是使用FileLock类来进行完成的，此类对象需要依赖FileChannel进行实例化。

**文件锁方式**：

- 共享锁：允许多个线程进行文件读取。
- 独占锁：只允许一个线程进行文件的读写操作。

Java文件依赖FileChannel的主要涉及如下4个方法：

| 方法                                                         | 说明                                              |
| ------------------------------------------------------------ | ------------------------------------------------- |
| lock()                                                       | 获取对此通道的文件的独占锁定。                    |
| lock(long position, long size, boolean shared)               | 获取此通道的文件给定区域上的锁定。                |
| tryLock() throws IOException                                 | 试图获取对此通道的文件的独占锁定。                |
| tryLock(long position, long size, boolean shared) throws IOException | 试图获取对此通道的文件给定区域的锁定。            |
| 无                                                           | lock()等同于lock(0L, Long.MAX_VALUE, false)       |
| 无                                                           | tryLock()等同于tryLock(0L, Long.MAX_VALUE, false) |

![Java_NIO_filelock.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_NIO_filelock.png?raw=true)

**lock()和tryLock()的区别**：

- lock()阻塞的方法，锁定范围可以随着文件的增大而增加。无参lock()默认为独占锁；有参`lock(0L, Long.MAX_VALUE, true)`为共享锁。
- tryLock()非阻塞,当未获得锁时,返回null。无参tryLock()默认为独占锁；有参tryLock(0L, Long.MAX_VALUE, true)为共享锁。

**简单实例代码：**

```java
File file = new File("d:" + File.separator + "test.txt") ;
FileOutputStream output = null ;
FileChannel fout = null ;
try {
    output = new FileOutputStream(file,true) ;
    fout = output.getChannel() ;// 得到通道
    FileLock lock = fout.tryLock() ; // 进行独占锁的操作
    if(lock!=null){
	System.out.println(file.getName() + "文件锁定") ;
	Thread.sleep(5000) ;
	lock.release() ;	// 释放
	System.out.println(file.getName() + "文件解除锁定。") ;
    }
} catch (IOException e) {
    e.printStackTrace();
} catch (InterruptedException e) {
    e.printStackTrace();
} finally {
    if(fout!=null){
	try {
	    fout.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    if(output!=null){
	try {
	    output.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
```

**运行结果：**

![Java_NIO_filelock2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_NIO_filelock2.png?raw=true)



## Selector

> **说明：**
>
> - FileChannel是可读可写的Channel，它必须阻塞，**不能用在非阻塞模式中**。
> - SocketChannel与FileChannel不同：新的Socket Channel能在**非阻塞模式下**运行并且是可选择的。不再需要为每个socket连接指派线程了。使用新的NIO类，一个或多个线程能管理成百上千个活动的socket连接，使用**Selector**对象可以选择可用的Socket Channel。

以前的Socket程序是阻塞的，服务器必须始终等待客户端的连接，而NIO可以通过Selector完成非阻塞操作。

> **备注：其实NIO主要的功能是解决服务端的通讯性能。** 

**Selector一些主要方法：**

| 方法           | 说明                                            |
| -------------- | ----------------------------------------------- |
| open()         | 打开一个选择器。                                |
| select()       | 选择一组键，其相应的通道已为 I/O 操作准备就绪。 |
| selectedKeys() | 返回此选择器的已选择键集。                      |

**SelectionKey的四个重要常量：**

| 字段       | 说明                               |
| ---------- | ---------------------------------- |
| OP_ACCEPT  | 用于套接字**接受操作**的操作集位。 |
| OP_CONNECT | 用于套接字**连接**操作的操作集位。 |
| OP_READ    | 用于**读取**操作的操作集位。       |
| OP_WRITE   | 用于**写入**操作的操作集位。       |

> **说明：**其实四个常量就是Selector监听SocketChannel四种不同类型的事件。
>
> 如果你对不止一种事件感兴趣，那么可以用”位或”操作符将常量连接起来，如下： int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

### Selector NIO 简单实例代码

**服务端代码：**

```java
int port = 8000;
// 通过open()方法找到Selector
Selector selector = Selector.open();
// 打开服务器的通道
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
// 服务器配置为非阻塞
serverSocketChannel.configureBlocking(false);
ServerSocket serverSocket = serverSocketChannel.socket();
InetSocketAddress address = new InetSocketAddress(port);
// 进行服务的绑定
serverSocket.bind(address);
// 注册到selector，等待连接
serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
System.out.println("服务器运行，端口：" + 8000);

// 数据缓冲区
ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

while (true) {
if ((selector.select()) > 0) { // 选择一组键，并且相应的通道已经准备就绪
	Set<SelectionKey> selectedKeys = selector.selectedKeys();// 取出全部生成的key
	Iterator<SelectionKey> iter = selectedKeys.iterator();
	while (iter.hasNext()) {
		SelectionKey key = iter.next(); // 取出每一个key
		if (key.isAcceptable()) {
			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			// 接收新连接 和BIO写法类是都是accept
			SocketChannel client = server.accept();
			// 配置为非阻塞
			client.configureBlocking(false);
			byteBuffer.clear();
			// 向缓冲区中设置内容
			byteBuffer.put(("当前的时间为：" + new Date()).getBytes());
			byteBuffer.flip();
			// 输出内容
			client.write(byteBuffer);
			client.register(selector, SelectionKey.OP_READ);
		} else if (key.isReadable() && key.isValid()) {
			SocketChannel client = (SocketChannel) key.channel();
			byteBuffer.clear();
			// 读取内容到缓冲区中
			int readSize = client.read(byteBuffer);
			if (readSize > 0) {
				System.out.println("服务器端接受客户端数据:" + new String(byteBuffer.array(), 0, readSize));
				client.register(selector, SelectionKey.OP_WRITE);
			}
		} else if (key.isWritable() && key.isValid()) {
			SocketChannel client = (SocketChannel) key.channel();
			byteBuffer.clear();
			// 向缓冲区中设置内容
			byteBuffer.put(("欢迎关注匠心零度，已经收到您的反馈，会第一时间回复您。感谢支持！！！").getBytes());
			byteBuffer.flip();
			// 输出内容
			client.write(byteBuffer);
			client.register(selector, SelectionKey.OP_READ);
		}
	}
	selectedKeys.clear(); // 清楚全部的key
}
}
```

**客户端代码：**

```java
// 打开socket通道
SocketChannel socketChannel = SocketChannel.open();
// 设置为非阻塞方式
socketChannel.configureBlocking(false);
// 通过open()方法找到Selector
Selector selector = Selector.open();
// 注册连接服务端socket动作
socketChannel.register(selector, SelectionKey.OP_CONNECT);
// 连接
socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));

/* 数据缓冲区 */
ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

while (true) {
if ((selector.select()) > 0) { // 选择一组键，并且相应的通道已经准备就绪
	Set<SelectionKey> selectedKeys = selector.selectedKeys();// 取出全部生成的key
	Iterator<SelectionKey> iter = selectedKeys.iterator();
	while (iter.hasNext()) {
		SelectionKey key = iter.next(); // 取出每一个key
		if (key.isConnectable()) {
			SocketChannel client = (SocketChannel) key.channel();
			if (client.isConnectionPending()) {
				client.finishConnect();
				byteBuffer.clear();
				// 向缓冲区中设置内容
				byteBuffer.put(("isConnect,当前的时间为：" + new Date()).getBytes());
				byteBuffer.flip();
				// 输出内容
				client.write(byteBuffer);
			}
			client.register(selector, SelectionKey.OP_READ);
		} else if (key.isReadable() && key.isValid()) {
			SocketChannel client = (SocketChannel) key.channel();
			byteBuffer.clear();
			// 读取内容到缓冲区中
			int readSize = client.read(byteBuffer);
			if (readSize > 0) {
				System.out.println("客户端接受服务器端数据:" + new String(byteBuffer.array(), 0, readSize));
				client.register(selector, SelectionKey.OP_WRITE);
			}
		} else if (key.isWritable() && key.isValid()) {
			SocketChannel client = (SocketChannel) key.channel();
			byteBuffer.clear();
			// 向缓冲区中设置内容
			byteBuffer.put(("nio文章学习很多！").getBytes());
			byteBuffer.flip();
			// 输出内容
			client.write(byteBuffer);
			client.register(selector, SelectionKey.OP_READ);
		}
	}
	selectedKeys.clear(); // 清楚全部的key
}
}
```

**程序运行结果截图：**

![Java_NIO_selector.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/Java_NIO_selector.png?raw=true)

虽然NIO在网络操作中提供了非阻塞方法，但是NIO的IO行为还是同步的，对于NIO来说，我们的业务线程是在IO操作准备好时，才得到通知，接着就有这个线程自行完成IO操作，但是IO操作的本身其实还是同步的。

------



　
