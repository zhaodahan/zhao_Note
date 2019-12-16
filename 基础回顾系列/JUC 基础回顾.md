# JUC 多线程



## Java JUC 简介

我们使用多线程的原因就是为了加快效率。 但是如果使用的不好反而会降低效率，因为多线程的开销更大。

## volatile 关键字

### 内存可见性

为了线程的执行效率，jvm会为每个线程开启一个共享变量的缓存。

![JAVA_JUC1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC1.png?raw=true)

![JAVA_JUC2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC2.png?raw=true)

volatile  关键字的实现原理就是使用系统底层实现实时的将线程中的缓存修改写入到主存。这样共享变量的读取的就是最新的值。 但是正因为是这样，他的速度比使用缓存会降低，但是他的速度比加锁要快的多，锁具有互斥性。而且一旦使用volatile这种轻量级的同步方式，jvm的优化指令重排序就不能够启用。

## 原子变量-CAS算法

### 原子性

![JAVA_JUC3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC3.png?raw=true)

解决： 原子变量 (类似于原来的包装类)

![JAVA_JUC4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC4.png?raw=true)

cas 算法要比普通的同步锁效率高，因为在他比较失败的时候他不会进行阻塞，而是紧接着再去执行cas 操作。直至成功。

![JAVA_JUC5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC5.png?raw=true)

cas算法是底层硬件提供的支持。



## ConcurrentHashMap 锁分段机制

![JAVA_JUC6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC6.png?raw=true)

![JAVA_JUC7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC7.png?raw=true)

![JAVA_JUC8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC8.png?raw=true)

![JAVA_JUC9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC9.png?raw=true)

![JAVA_JUC10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC10.png?raw=true)

## CountDownLatch 闭锁

当前完成一些运算，如果其他运算没有完成，就等待。如果其他的完成了，当前这个再去执行。

![JAVA_JUC11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC11.png?raw=true)

```java

/*
 * CountDownLatch ：闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {

	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(50);
		LatchDemo ld = new LatchDemo(latch);
//如果没有闭锁无法计算出这些线程的执行时间，应为子线程和主线程是同步执行的。
		long start = System.currentTimeMillis();
		for (int i = 0; i < 50; i++) {
			new Thread(ld).start();
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
		}
        
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}

}

class LatchDemo implements Runnable {
	private CountDownLatch latch;
	public LatchDemo(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
        //---加锁
		try {
			for (int i = 0; i < 50000; i++) {
				if (i % 2 == 0) {
					System.out.println(i);
				}
			}
		} finally {
			latch.countDown(); // -1 每个线程执行完这个操作必须执行 ，当latch =0 时主线程才会执行
		}

	}

}
```



## 实现 Callable 接口

![JAVA_JUC12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC12.png?raw=true)

![JAVA_JUC13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC13.png?raw=true)

![JAVA_JUC14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC14.png?raw=true)


## Lock 同步锁

```java
/*
 * 一、用于解决多线程安全问题的方式：
 * 
 * synchronized:隐式锁
 * 1. 同步代码块
 * 
 * 2. 同步方法
 * 
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
```

```java
public void run() {	
		while(true){
			synchronized(this)
				if(tick > 0){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
				}
		    }
		}
	}
```

使用之后

```java
class Ticket implements Runnable{
	
	private int tick = 100;
	
	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		while(true){
			
			lock.lock(); //上锁
			
			try{
				if(tick > 0){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}	
			System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
				}
			}finally{
				lock.unlock(); //释放锁 这里必须是写在finally中。 必须释放
			}
		}
	}
	
}
```

### 生产消费者案例： 等待唤醒机制

![JAVA_JUC15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC15.png?raw=true)

使用synchronized 

```java
/*
 * 生产者和消费者案例
 */
public class TestProductorAndConsumer {

	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		Productor pro = new Productor(clerk);
		Consumer cus = new Consumer(clerk);
		
		new Thread(pro, "生产者 A").start();
		new Thread(cus, "消费者 B").start();
		
		new Thread(pro, "生产者 C").start();
		new Thread(cus, "消费者 D").start();
	}
	
}

//店员
class Clerk{
	private int product = 0;
	
	//进货
	public synchronized void get(){//循环次数：0
		while(product >= 1){//为了避免虚假唤醒问题，应该总是使用在循环中
			System.out.println("产品已满！");
			
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
			
		}
		
		System.out.println(Thread.currentThread().getName() + " : " + ++product);
		this.notifyAll();
	}
	
	//卖货
	public synchronized void sale(){//product = 0; 循环次数：0
		while(product <= 0){
			System.out.println("缺货！");
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println(Thread.currentThread().getName() + " : " + --product);
		this.notifyAll();
	}
}

//生产者
class Productor implements Runnable{
	private Clerk clerk;

	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			
			clerk.get();
		}
	}
}

//消费者
class Consumer implements Runnable{
	private Clerk clerk;

	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}
}
```

使用lock

```java
/*
 * 生产者消费者案例：
 */
public class TestProductorAndConsumerForLock {

	public static void main(String[] args) {
		Clerk clerk = new Clerk();

		Productor pro = new Productor(clerk);
		Consumer con = new Consumer(clerk);

		new Thread(pro, "生产者 A").start();
		new Thread(con, "消费者 B").start();

//		 new Thread(pro, "生产者 C").start();
//		 new Thread(con, "消费者 D").start();
	}

}

class Clerk {
	private int product = 0;

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	// 进货
	public void get() {
		lock.lock();

		try {
			if (product >= 1) { // 为了避免虚假唤醒，应该总是使用在循环中。
				System.out.println("产品已满！");
				try {
					condition.await();
				} catch (InterruptedException e) {
				}

			}
			System.out.println(Thread.currentThread().getName() + " : "
					+ ++product);

			condition.signalAll();
		} finally {
			lock.unlock();
		}

	}

	// 卖货
	public void sale() {
		lock.lock();

		try {
			if (product <= 0) {
				System.out.println("缺货！");
				try {
					condition.await();
				} catch (InterruptedException e) {
				}
			}

			System.out.println(Thread.currentThread().getName() + " : "
					+ --product);

			condition.signalAll();

		} finally {
			lock.unlock();
		}
	}
}

// 生产者
class Productor implements Runnable {

	private Clerk clerk;

	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			clerk.get();
		}
	}
}

// 消费者
class Consumer implements Runnable {

	private Clerk clerk;

	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}

}
```



## Condition 控制线程通信

condition就是原来synchronized关键字中使用的wait(), notify（）的一种优化。 具体使用可以查看上面的生产者消费者案例。

![JAVA_JUC16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC16.png?raw=true)


## 线程按序交替

```java
/*
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 *	如：ABCABCABC…… 依次递归
 */
public class TestABCAlternate {	
	public static void main(String[] args) {
		AlternateDemo ad = new AlternateDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {	
				for (int i = 1; i <= 20; i++) {
					ad.loopA(i);
				}	
			}
		}, "A").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				for (int i = 1; i <= 20; i++) {
					ad.loopB(i);
				}
				
			}
		}, "B").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				for (int i = 1; i <= 20; i++) {
					ad.loopC(i);
					System.out.println("-----------------------------------");
				}
				
			}
		}, "C").start();
	}

}

class AlternateDemo{
	
	private int number = 1; //当前正在执行线程的标记
	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();
	
	/**
	 * @param totalLoop : 循环第几轮
	 */
	public void loopA(int totalLoop){
		lock.lock();
		
		try {
			//1. 判断
			if(number != 1){
				condition1.await();
			}		
			//2. 打印
			for (int i = 1; i <= 1; i++) {
		     System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			
			//3. 唤醒
			number = 2;
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void loopB(int totalLoop){
		lock.lock();	
		try {
			//1. 判断
			if(number != 2){
				condition2.await();
			}
			
			//2. 打印
			for (int i = 1; i <= 1; i++) {
              System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			
			//3. 唤醒
			number = 3;
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void loopC(int totalLoop){
		lock.lock();	
		try {
			//1. 判断
			if(number != 3){
				condition3.await();
			}
			
			//2. 打印
			for (int i = 1; i <= 1; i++) {
	         System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			
			//3. 唤醒
			number = 1;
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
}
```



## ReadWriteLock 读写锁

读写分离：读取数据不需要线程安全。  (读写锁是乐观锁)

以前的锁，无论读写一次都只能有一个操作，但是读写锁，写，每次都只能有一个进行操作，读，可以多个人进行操作。这样就能比独占锁提高效率。

```java
/*
 * 1. ReadWriteLock : 读写锁
 * 
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 * 
 */
```

![JAVA_JUC17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC17.png?raw=true)

```java
public class TestReadWriteLock {

	public static void main(String[] args) {
		ReadWriteLockDemo rw = new ReadWriteLockDemo();
 // 一个线程去写，100个线程去读		
		new Thread(new Runnable() {
			@Override
			public void run() {
				rw.set((int)(Math.random() * 101));
			}
		}, "Write:").start();
		
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {		
				@Override
				public void run() {
					rw.get();
				}
			}).start();
		}
	}
	
}

class ReadWriteLockDemo{
	private int number = 0;	
	private ReadWriteLock lock = new ReentrantReadWriteLock();	
	//读
	public void get(){
		lock.readLock().lock(); //上锁
		
		try{
			System.out.println(Thread.currentThread().getName() + " : " + number);
		}finally{
			lock.readLock().unlock(); //释放锁
		}
	}
	
	//写
	public void set(int number){
		lock.writeLock().lock();
		try{
			System.out.println(Thread.currentThread().getName());
			this.number = number;
		}finally{
			lock.writeLock().unlock();
		}
	}
}
```



## 线程八锁

```
 * 线程八锁的关键：
 * ①非静态方法的锁默认为  this,  静态方法的锁为 对应的 Class 实例
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法。
```



## 线程池

类似于数据库连接池。

```java
/*
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 
 * 二、线程池的体系结构：
 * 	java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * 		|--**ExecutorService 子接口: 线程池的主要接口
 * 			|--ThreadPoolExecutor 线程池的实现类
 * 			|--ScheduledExecutorService 子接口：负责线程的调度
 * 				|--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * 
 * 三、工具类 : Executors 
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 */
public class TestThreadPool {
	public static void main(String[] args) throws Exception {
		//1. 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);
		List<Future<Integer>> list = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			Future<Integer> future = pool.submit(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					int sum = 0;
					for (int i = 0; i <= 100; i++) {
						sum += i;
					}
					
					return sum;
				}
				
			});

			list.add(future);
		}

		pool.shutdown(); // 他会以一种平和的方式来关闭线程池，不再接收任务，等线程池中的线程执行完后关闭
		for (Future<Integer> future : list) {
			System.out.println(future.get());
		}
		
		
		
		/*ThreadPoolDemo tpd = new ThreadPoolDemo();
		
		//2. 为线程池中的线程分配任务
		for (int i = 0; i < 10; i++) {
			pool.submit(tpd);
		}
		
		//3. 关闭线程池
		pool.shutdown();*/
	}
	
//	new Thread(tpd).start();
//	new Thread(tpd).start();

}

class ThreadPoolDemo implements Runnable{
	private int i = 0;
	@Override
	public void run() {
		while(i <= 100){
			System.out.println(Thread.currentThread().getName() + " : " + i++);
		}
	}
	
}
```



## 线程调度

```java
public class TestScheduledThreadPool {

	public static void main(String[] args) throws Exception {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);	
		for (int i = 0; i < 5; i++) {
			Future<Integer> result = pool.schedule(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					int num = new Random().nextInt(100);//生成随机数
					System.out.println(Thread.currentThread().getName() + " : " + num);
					return num;
				}		
			}, 3, TimeUnit.SECONDS); 
            // 这里参数的意义是由线程池控制延迟3个单位时间调度一个线程任务
            //看起来就是每隔3个单位时间就执行一个任务
			System.out.println(result.get());
		}
		pool.shutdown();
	}
	
}
```



## ForkJoinPool 

分支合并框架

![JAVA_JUC18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC18.png?raw=true)

工作窃取模式

![JAVA_JUC19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_JUC19.png?raw=true)

```java
public class TestForkJoinPool {
	
	public static void main(String[] args) {
		Instant start = Instant.now();
		ForkJoinPool pool = new ForkJoinPool(); // fork join框架需要ForkJoinPool的支持
		ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 50000000000L);
		Long sum = pool.invoke(task); //执行任务
		System.out.println(sum);
		Instant end = Instant.now();
	System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//166-1996-10590
	}
	
	@Test
	public void test1(){
		Instant start = Instant.now();
		long sum = 0L;	
		for (long i = 0L; i <= 50000000000L; i++) {
			sum += i;
		}
		System.out.println(sum);
		Instant end = Instant.now();
	System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//35-3142-15704
	}
	
	//java8 新特性 ： 是对fork join (1.7)的改进 ,效率更高
	@Test
	public void test2(){
		Instant start = Instant.now();	
		Long sum = LongStream.rangeClosed(0L, 50000000000L)
							 .parallel()
							 .reduce(0L, Long::sum);
		
		System.out.println(sum);
		Instant end = Instant.now();
		System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//1536-8118
	}

}

// 递归拆分任务
class ForkJoinSumCalculate extends RecursiveTask<Long>{ // RecursiveTask 有返回值
	private static final long serialVersionUID = -259195479995561737L; //序列化
	private long start;
	private long end;
	private static final long THURSHOLD = 10000L;  //临界值
	
	public ForkJoinSumCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long length = end - start;
		if(length <= THURSHOLD){ // 小于临界值不在拆分
			long sum = 0L;	
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		}else{
			long middle = (start + end) / 2;
			
			ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle); 
			left.fork(); //进行拆分，同时压入线程队列
			
			ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle+1, end);
			right.fork(); //
			
			return left.join() + right.join();
		}
	}
	
}

```


