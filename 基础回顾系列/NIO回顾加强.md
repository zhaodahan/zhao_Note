# NIO巩固与加强

非阻塞IO，new IO。加强读写效率

## Java NIO 与 IO 的主要区别

![JAVA_RNIO1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO1.png?raw=true)
![JAVA_RNIO2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO2.png?raw=true)

![JAVA_RNIO3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO3.png?raw=true)



##  缓冲区(Buffer)和通道(Channel)

![JAVA_RNIO4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO4.png?raw=true)

### 缓冲区

```java
/*
 * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区底层就是数组。用于存储不同数据类型的数据
 *     这里的缓存区与之前的意义不同，是新的api，专用于NIO传输数据.
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 
 * 上述缓冲区的管理方式几乎一致，都通过 allocate() 获取缓冲区
 * 
 * 二、缓冲区存取数据的两个核心方法：
 * put() : 存入数据到缓冲区中
 * get() : 获取缓冲区中的数据
 * 
 * 三、缓冲区中的四个核心属性：
 * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。类似于为数组声明的大小
 * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）实际存储有效数据的位置
 * position : 位置，表示缓冲区中正在操作数据的位置。 (正在操作数据源的指针指向的位置)
 * 
 * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
 * 
 * 0 <= mark <= position <= limit <= capacity
 * 
 */
public class TestBuffer {   
    @Test
	public void test1(){
		String str = "abcde";
		
		//1. 分配一个指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);

		System.out.println("-----------------allocate()----------------");
		System.out.println(buf.position()); // 0
		System.out.println(buf.limit()); // 1024
		System.out.println(buf.capacity()); //1024
		
		//2. 利用 put() 存入数据到缓冲区中
		buf.put(str.getBytes());
		
		System.out.println("-----------------put()----------------");
		System.out.println(buf.position()); // 5
		System.out.println(buf.limit()); // 1024
		System.out.println(buf.capacity()); //1024
		
		//3. 切换读取数据模式
		buf.flip();
		
		System.out.println("-----------------flip()----------------");
		System.out.println(buf.position()); //0
		System.out.println(buf.limit()); //5
		System.out.println(buf.capacity()); //1024
		
		//4. 利用 get() 读取缓冲区中的数据
		byte[] dst = new byte[buf.limit()];
		buf.get(dst);
		System.out.println(new String(dst, 0, dst.length));
		
		System.out.println("-----------------get()----------------");
		System.out.println(buf.position()); //5
		System.out.println(buf.limit()); //5
		System.out.println(buf.capacity()); //1024
		
		//5. rewind() : 可重复读
		buf.rewind();
		
		System.out.println("-----------------rewind()----------------");
		System.out.println(buf.position()); //0
		System.out.println(buf.limit()); //5
		System.out.println(buf.capacity()); //1024
		
		//6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态，界限不存在了，无法正确的读写数据了
		buf.clear();
		
		System.out.println("-----------------clear()----------------");
		System.out.println(buf.position()); //0
		System.out.println(buf.limit()); //1024
		System.out.println(buf.capacity()); //1024 
		
		System.out.println((char)buf.get()); // get() 获取一个字节	
	}

	
	@Test
	public void test2(){
		String str = "abcde";
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put(str.getBytes());
		
		buf.flip();
		
		byte[] dst = new byte[buf.limit()];
		buf.get(dst, 0, 2);
		System.out.println(new String(dst, 0, 2));
		System.out.println(buf.position());//2
		
		//mark() : 标记
		buf.mark();
		
		buf.get(dst, 2, 2);
		System.out.println(new String(dst, 2, 2));
		System.out.println(buf.position());//4
		
		//reset() : 恢复到 mark 的位置 
		buf.reset(); 
		System.out.println(buf.position());//2
		
		//判断缓冲区中是否还有剩余数据
		if(buf.hasRemaining()){
			//获取缓冲区中可以操作的数量
			System.out.println(buf.remaining()); //3 ,limit-position
		}
	}
	
}

```

test1() 图解：

![JAVA_RNIO5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO5.png?raw=true)

```java
/*
* 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。某种情况下可以提高效率
 * 某些情况下会节省下从jvm系统内存copy到内核内存中的过程，到值效率提高,
 * 但是同样的建立直接缓冲区也存在弊端：
 *     1:建立直接缓冲区的消耗比非直接缓冲区消耗大。且只有垃圾回收机制运行的时候才会断开与直接缓冲区的连接
 *     2：更严重的是当jvm将数据写入到直接缓冲区后，数据就不归jvm管理了。后续对缓冲区的操作完全由系统管理了
 */
	@Test
	public void test3(){
		//分配直接缓冲区
		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		System.out.println(buf.isDirect()); //判断分配的缓冲区是否事直接缓冲
	}
```

![JAVA_RNIO6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO6.png?raw=true)

![JAVA_RNIO7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO7.png?raw=true)



### 通道

通道的本质就是IO源与目标之间打开的**连接**。 他是负责传输的铁轨。

![JAVA_RNIO8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO8.png?raw=true)

![JAVA_RNIO9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO9.png?raw=true)

![JAVA_RNIO10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO10.png?raw=true)

```java
/*
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel        用于tcp 套接字
 * 		|--ServerSocketChannel   用于tcp 套接字
 * 		|--DatagramChannel   用于udp
 * 
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO：
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile //（随机访问文件）类 :这里“随机”是指可以跳转到文件的任意位置处读写数据
 * 
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 * 		
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * 
 * 
 * 
 */

```

#### 文件通道

```java
//利用通道完成文件的复制（非直接缓冲区）
	@Test
	public void test1(){//10874-10953
		long start = System.currentTimeMillis();
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		//①获取通道
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("d:/1.mkv");
			fos = new FileOutputStream("d:/2.mkv");
			
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			//②分配指定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//③将通道中的数据存入缓冲区中
			while(inChannel.read(buf) != -1){
				buf.flip(); //切换读取数据的模式
				//④将缓冲区中的数据写入通道中
				outChannel.write(buf);
				buf.clear(); //清空缓冲区
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭通道和流 inChannel outChannel fis fos
		}
		
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
		
	}
```



![JAVA_RNIO11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO11.png?raw=true)

```java
//使用直接缓冲区完成文件的复制(内存映射文件)
	@Test
	public void test2() throws IOException{//2127-1902-1777
		long start = System.currentTimeMillis();
		
		FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
		
		//内存映射文件 只有ByteBuffer支持
		MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		
		//直接对缓冲区进行数据的读写操作
		byte[] dst = new byte[inMappedBuf.limit()];
		inMappedBuf.get(dst);
		outMappedBuf.put(dst);
		
		inChannel.close();
		outChannel.close();
		
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}

/* 
* 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 */
//通道之间的数据传输(直接缓冲区)
	@Test
	public void test3() throws IOException{
		FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
		
//		inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		
		inChannel.close();
		outChannel.close();
	}
```



#### 分散与聚集

![JAVA_RNIO12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO12.png?raw=true)

![JAVA_RNIO13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO13.png?raw=true)

```java
 /*
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 就是原来操作缓冲区，现在操作的是缓冲区数组。
 */

//分散和聚集
	@Test
	public void test4() throws IOException{
		RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");
		
		//1. 获取通道
		FileChannel channel1 = raf1.getChannel();
		
		//2. 分配指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(100);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		//3. 分散读取
		ByteBuffer[] bufs = {buf1, buf2};
		channel1.read(bufs);
		
		for (ByteBuffer byteBuffer : bufs) {
			byteBuffer.flip();
		}
		
		System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
		System.out.println("-----------------");
		System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
		
		//4. 聚集写入
		RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(bufs);
	}
```



#### 字符集

```java
/* 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 */
//字符集
	@Test
	public void test6() throws IOException{
		Charset cs1 = Charset.forName("GBK");
		//获取编码器
		CharsetEncoder ce = cs1.newEncoder();
		//获取解码器
		CharsetDecoder cd = cs1.newDecoder();
		
		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("尚硅谷威武！");
		cBuf.flip();
		
		//编码,字符转换成字节
		ByteBuffer bBuf = ce.encode(cBuf);
		for (int i = 0; i < 12; i++) {
			System.out.println(bBuf.get());
		}
		
		//解码 字节转换成字符
		bBuf.flip();
		CharBuffer cBuf2 = cd.decode(bBuf);
		System.out.println(cBuf2.toString());
	}
```



##  NIO 的非阻塞式网络通信

传统的IO是阻塞的，而NIO 是非阻塞的，而这个非阻塞他是相对于网络通信而言的。

**什么是阻塞? 什么是非阻塞？传统的网络通信与NIO网络通信间的区别？**

![JAVA_RNIO14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO14.png?raw=true)

 将每一个通道都会注册到改选择器上，选择器的作用就是实时监控这些通道的IO的状况。只有选择器监控的某一个通道上的某一个请求完全的准备就绪时，选择器才会将这个通道上的任务分配到服务端的一个或多个线程上，服务端再去运行。这就意味着当通道上的都没有就绪时，这时候服务就可以去做其他的事情。这样非阻塞式服务端就能更好的利用cpu资源。

![JAVA_RNIO15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO15.png?raw=true)

### 网络通信 非阻塞IO

```java
//这里使用的还是阻塞式IO
public class TestBlockingNIO {
	//客户端
	@Test
	public void client() throws IOException{
		//1. 获取通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
		
		//2. 分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//3. 读取本地文件，并发送到服务端
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
		while(inChannel.read(buf) != -1){
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
		
		//4. 关闭通道
		inChannel.close();
		sChannel.close();
	}
	
	//服务端
	@Test
	public void server() throws IOException{
		//1. 获取通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		
		//2. 绑定连接
		ssChannel.bind(new InetSocketAddress(9898));
		
		//3. 获取客户端连接的通道
		SocketChannel sChannel = ssChannel.accept();
		
		//4. 分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//5. 接收客户端的数据，并保存到本地
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		while(sChannel.read(buf) != -1){
			buf.flip();
			outChannel.write(buf);
			buf.clear();
		}
		
		//6. 关闭通道
		sChannel.close();
		outChannel.close();
		ssChannel.close();
	}
	
}

```

阻塞式IO

```java
public class TestBlockingNIO2 {
	
	//客户端
	@Test
	public void client() throws IOException{
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
	
		ByteBuffer buf = ByteBuffer.allocate(1024);
	    FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
		while(inChannel.read(buf) != -1){
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
//说明： 该程序运行后就会导致阻塞，服务端不知道客户端是否发送结束，解决办法有两种，一种是使用shutdownOutPut，另外一种就是换成非阻塞模式。 	
	//	sChannel.shutdownOutput(); 
//禁用此套接字的输出流。对于 TCP 套接字，任何以前写入的数据都将被发送，并且后跟 TCP 的正常连接终止序列。 如果在套接字上调用 shutdownOutput() 后写入套接字输出流，则该流将抛出 IOException。 
		
		//接收服务端的反馈
		int len = 0;
		while((len = sChannel.read(buf)) != -1){
			buf.flip();
			System.out.println(new String(buf.array(), 0, len));
			buf.clear();
		}
		inChannel.close();
		sChannel.close();
	}
	
	//服务端
	@Test
	public void server() throws IOException{
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		
		ssChannel.bind(new InetSocketAddress(9898));
		SocketChannel sChannel = ssChannel.accept();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		while(sChannel.read(buf) != -1){
			buf.flip();
			outChannel.write(buf);
			buf.clear();
		}
		
		//发送反馈给客户端
		buf.put("服务端接收数据成功".getBytes());
		buf.flip();
		sChannel.write(buf);
		sChannel.close();
		outChannel.close();
		ssChannel.close();
	}

}
```

非阻塞是IO

![JAVA_RNIO16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO16.png?raw=true)

```java
/*
 * 一、使用 NIO 完成网络通信的三个核心：
 * 
 * 1. 通道（Channel）：负责连接
 * 		
 * 	   java.nio.channels.Channel 接口：
 * 			|--SelectableChannel
 * 				|--SocketChannel
 * 				|--ServerSocketChannel
 * 				|--DatagramChannel
 * 
 * 				|--Pipe.SinkChannel   管道
 * 				|--Pipe.SourceChannel
 * 这里没有FileChannel, FileChannel 不能切换为非阻塞模式。
 * 2. 缓冲区（Buffer）：负责数据的存取
 * 
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 * 
 */

public class TestNonBlockingNIO {
	
	//客户端
	@Test
	public void client() throws IOException{
		//1. 获取通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
		
		//2. 切换非阻塞模式
		sChannel.configureBlocking(false);
		
		//3. 分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//4. 发送数据给服务端
		Scanner scan = new Scanner(System.in);
		
		while(scan.hasNext()){
			String str = scan.next();
			buf.put((new Date().toString() + "\n" + str).getBytes());
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
		
		//5. 关闭通道
		sChannel.close();
	}

	//服务端
	@Test
	public void server() throws IOException{
		//1. 获取通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		
		//2. 切换非阻塞模式
		ssChannel.configureBlocking(false);
		
		//3. 绑定连接
		ssChannel.bind(new InetSocketAddress(9898));
		
		//4. 获取选择器
		Selector selector = Selector.open();
		
		//5. 将通道注册到选择器上, 并且指定“监听接收事件”
		ssChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		//6. 轮询式的获取选择器上已经“准备就绪”的事件
		while(selector.select() > 0){
            
			//7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			
			while(it.hasNext()){
				//8. 获取准备“就绪”的是事件
				SelectionKey sk = it.next();
				
				//9. 判断具体是什么事件准备就绪
				if(sk.isAcceptable()){  //这里的处理操作(请求)我们可以为其分配多个线程去处理
					//10. 若“接收就绪”，获取客户端连接
					SocketChannel sChannel = ssChannel.accept();
					
					//11. 切换非阻塞模式
					sChannel.configureBlocking(false);
					
					//12. 将该通道注册到选择器上
					sChannel.register(selector, SelectionKey.OP_READ);
				}else if(sk.isReadable()){
					//13. 获取当前选择器上“读就绪”状态的通道
					SocketChannel sChannel = (SocketChannel) sk.channel();
					
					//14. 读取数据
					ByteBuffer buf = ByteBuffer.allocate(1024);
					
					int len = 0;
					while((len = sChannel.read(buf)) > 0 ){
						buf.flip();
						System.out.println(new String(buf.array(), 0, len));
						buf.clear();
					}
				}
				
				//15. 取消选择键 SelectionKey . 这步是必须的，否则这个事件会一直有效
				it.remove();
			}
		}
	}
}

```

### DatagramChannel

```java
public class TestNonBlockingNIO2 {
	
	@Test
	public void send() throws IOException{
		DatagramChannel dc = DatagramChannel.open();
        
		dc.configureBlocking(false);
		ByteBuffer buf = ByteBuffer.allocate(1024);
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			String str = scan.next();
			buf.put((new Date().toString() + ":\n" + str).getBytes());
			buf.flip();
			dc.send(buf, new InetSocketAddress("127.0.0.1", 9898));
			buf.clear();
		}
		dc.close();
	}
	
	@Test
	public void receive() throws IOException{
		DatagramChannel dc = DatagramChannel.open();
		dc.configureBlocking(false);
		dc.bind(new InetSocketAddress(9898));
		Selector selector = Selector.open();
		dc.register(selector, SelectionKey.OP_READ);
		while(selector.select() > 0){
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while(it.hasNext()){
				SelectionKey sk = it.next();
				if(sk.isReadable()){
					ByteBuffer buf = ByteBuffer.allocate(1024);
					dc.receive(buf);
					buf.flip();
					System.out.println(new String(buf.array(), 0, buf.limit()));
					buf.clear();
				}
			}
			it.remove();
		}
	}

}

```

##  管道(Pipe)

![JAVA_RNIO17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RNIO17.png?raw=true)


```java

public class TestPipe {

	@Test
	public void test1() throws IOException{
		//1. 获取管道
		Pipe pipe = Pipe.open();
		
		//2. 将缓冲区中的数据写入管道
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		Pipe.SinkChannel sinkChannel = pipe.sink();
		buf.put("通过单向管道发送数据".getBytes());
		buf.flip();
		sinkChannel.write(buf);
		
		//3. 读取缓冲区中的数据
		Pipe.SourceChannel sourceChannel = pipe.source();
		buf.flip();
		int len = sourceChannel.read(buf);
		System.out.println(new String(buf.array(), 0, len));
		
		sourceChannel.close();
		sinkChannel.close();
	}
	
}
```



　
