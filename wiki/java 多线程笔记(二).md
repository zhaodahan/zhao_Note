# 8 线程池

线程的创建及销毁需要较大的代价，然而对于业务来说，它关心的只是线程所执行的任务，它希望把更多的cpu用在线程的任务上，而不是辅助性的线程的创建及销毁。 所以线程池应运而生，它主要的任务就是线程的复用，避免每执行一个线程都要开启和销毁一次线程。

线程池创建的核心是要把所有的线程保留下来用以复用，而非一执行完之后就销毁线程。

线程池设计的思想就是要把所有的活动线程保留下来(会有一个list 来保存这些线程)

为什么要使用线程池？

1： 降低资源损耗，线程的创建和销毁都有性能消耗

2： 提高响应处理任务的速度。 一个做和几个做，当然是几个人做快，而且还减少了不断换人和喊人的时间，任务可以不需要的等到线程创建就能立即执行。

3：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控

线程池（Thread Pool）对于限制应用程序中同一时刻运行的线程数很有用。因为每启动一个新线程都会有相应的性能开销，每个线程都需要给栈分配一些内存等等。



## 基本线程池

![img](https://upload-images.jianshu.io/upload_images/1958298-1442fe24be61cbf2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/752/format/webp)



### 线程池的创建

通过ThreadPoolExecutor来创建一个线程池

~~~java
new ThreadPoolExecutor(corePoolSize, maximumPoolSize,keepAliveTime, milliseconds,runnableTaskQueue, threadFactory,handler);

~~~

![Javaçº¿ç¨æ± ä¸"è¦å·¥ä½æµç¨](http://ifeve.com/wp-content/uploads/2012/12/Java%E7%BA%BF%E7%A8%8B%E6%B1%A0%E4%B8%BB%E8%A6%81%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B.jpg)

**源码分析**

~~~java
 public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        /*
         * Proceed in 3 steps:
         *
         * 1. If fewer than corePoolSize threads are running, try to
         * start a new thread with the given command as its first
         * task.  The call to addWorker atomically checks runState and
         * workerCount, and so prevents false alarms that would add
         * threads when it shouldn't, by returning false.
         *
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. If we cannot queue task, then we try to add a new
         * thread.  If it fails, we know we are shut down or saturated
         * and so reject the task.
         */
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            //如果正在运行的线程数小于核心线程数，尝试用command作为第一个任务开启一个新线程。
            if (addWorker(command, true))
             // addWorker check  runState and workerCount 来检测是否添加成功
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }

~~~

 java提供了四种线程池的实现：

​     （1）newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

    Executors 是一个线程池工厂，他提供了四个方法去构造线程池
    （1）newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    （2）newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    （3）newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
    （4）newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
## 扩展和增强线程池

~~~Java
public interface Executor{
    void executor(Runnable command);
}
// 线程池的本质就是一个执行者，他能执行一个Runable的任务
~~~

### 线程池的扩展之回调API

ThreadPoolExecutor 是一个可以扩展的线程池，它提供了 beforeExecute()、afterExecute()和terminated()3个接口

~~~java
 ExcutorServicer es =new ThreadPoolExecutor（.....）{

	protected void beforeExecute(Thread t, Runnable r) {
		System.out.println("准备执行前");
	}
 
	protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("执行完以后");
	}
     protected void terminated() {
        System.out.println("线程池退出");
	}
     
}
~~~

### 拒绝策略

#### 什么是拒绝策略

正常情况下，如果我们的任务不是很繁重，那么任务一提交就给执行了。但是有时候，我们的系统任务特别的繁重, 已经严重影响了系统的性能了。

而且我们一般不会准备一个无限大小的队列，这对于我们系统是没有好处的，因为如果有一大片的任务都进来，这时就会放在队列中会导致内存占有递增，且没有释放，导致内存溢出。

但是有时候是会出现这种任务的疯狂提交，导致系统负载，处理不过来，最终爆掉。 所以时候我们应该选择丢掉任务，而不是选择将任务放在内存中。但并不是直接丢了，而是以一种方式记录下来，然后进行一些处理，这就是我们的拒绝策略

#### 什么时候需要使用拒绝策略呢？ 

当任务数量超过系统实际承载能力的时候就要用到拒绝策略了，可以说它是系统超负荷运行的补救措施。简言之，就是线程用完，队列已满，无法为新任务服务，则需一套机制来合理的处理这些问题。

(常见的有界队列有：ArrayBlockingQueue 基于数组实现的阻塞队列，SynchronousQueue 内部容量为零)

JDK 提供了四种内置拒绝策略： 
1、DiscardPolicy:  dscard--丢弃 默默丢弃无法处理的任务，不予任何处理 
2、DiscardOldestPolicy: 丢弃队列中最老的任务, 尝试再次提交当前任务 
3、AbortPolicy:  abort-终止， 直接抛异常，阻止系统正常工作。 

4、CallerRunsPolicy:     caller-Runs 让提交者自己来做这件事 将任务分给调用线 行,运行当前被丢弃的任务，这样做不会真的丢弃任务，但是提交的线程性能有可能急剧下降。

线程池的构造方法

~~~java
public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
        BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler);
//线程池的构造方法中的RejectedExecutionHandler指定的就是我们的拒绝策略
~~~



通常的使用方式：

~~~java
int corePoolSize = 1;
int maximumPoolSize = 1;
BlockingQueue queue = new  ArrayBlockingQueue<Runnable>(1);
ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,  maximumPoolSize,0, TimeUnit.SECONDS, queue ) ;
pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy ());
pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
--------------
    
~~~

有时候我们需要对改接口进行扩展，来满足特别的需求就需要自定义了

~~~java
//拒绝策略的接口实现
public interface RejectedExecutionHandler {
    void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
}
~~~

自定义的拒绝策略的代码示例：

```java
  ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L,
            TimeUnit.SECONDS, 
            new LinkedBlockingDeque<Runnable>(10),   
            Executors.defaultThreadFactory()
                , new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //打印丢弃的任务
                //这里的r 是被拒绝的线程
                System.out.println(r.toString() + " is discard");
            }
        });
        for (int i = 0; i < 100; i++) {
            executorService.submit(myTask);
            Thread.sleep(10);
        }
--------------------- 
```



### 自定义ThreadFactory

工厂模式是最常用的模式之一，在创建线程的时候使用工厂模式来生产Thread，这样就能替代默认的new THread，而且在自定义工厂里面，我们能创建自定义化的Thread，并且计数，或则限制创建Thread的数量，或给每个Thread设置对应的名字(以便于调试)

**自定义ThreadFactory可以根治线程池究竟何时创建了多少线程，也可以自定义线程的名称、组以及优先级等信息，甚至可以任性的将线程设置为守护线程。总之，自定义ThreadFactory可以更加自由的设置线程池中所有线程的状态。**

例子：工厂模式来创建自己的Thread

```java
public class MyThreadFactory implements ThreadFactory {
private int counter;
private String name;
private List<String> stats;
public MyThreadFactory(String name) {
	counter = 0;
	this.name = name;
	stats = new ArrayList<String>();
}
@Override
public Thread newThread(Runnable run) {
	Thread t = new Thread(run, name + "-Thread-" + counter);
	counter++;
	stats.add(....);
	return t;
}
public static void main(String[] args) {
	MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
	Task task = new Task();
	Thread thread = null;
	for(int i = 0; i < 10; i++) {
		thread = factory.newThread(task);
		thread.start();
	}
}
```

## 线程池及其源码的分析

## ForkJoin

### 什么是fork/join

Fork/Join 框架：就是在必要的情况下，将一个大任务，进行拆分(fork)成 若干个小任务（拆到不可再拆时），再将一个个的小任务运算的结果进行 join 汇总。

~~~java
public class CaculatorForkAndJoin extends RecursiveTask<Long> {

	/**
	 * 创建serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private long start;
	private long end;
	private static final long THURSHOLD = 10000L; // 临界值

	CaculatorForkAndJoin(long start, long end) {
		this.start = start;
		this.end = end;
	}

	// 重写方法
	@Override
	protected Long compute() {
		long length = end - start;

		if (length <= THURSHOLD) {
			long sum = new Long(0);

			for (long i = start; i <= end; i++) {
				sum += i;
			}

			return sum;
		} else {
			// 中间值
			long mid = (start + end) / 2;
			CaculatorForkAndJoin left = new CaculatorForkAndJoin(start, mid);
			left.fork();// 进行拆分，同时压入现线程队列

			CaculatorForkAndJoin right = new CaculatorForkAndJoin(mid + 1, end);
			right.fork();// 进行拆分，同时压入现线程队列

			return left.join() + right.join();
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// 创建 线程池
		ForkJoinPool pool = new ForkJoinPool();
		// 创建任务
		CaculatorForkAndJoin task = new CaculatorForkAndJoin(0L, 100000000L);

		// 添加任务到线程池，获得返回值
		long sum = pool.invoke(task);

 		long end = System.currentTimeMillis();
		System.out.println(sum + "spend:" + (end - start));
	}
}
~~~



### Fork/Join框架要完成两件事情：

1.**任务分割**：首先Fork/Join框架需要把大的任务分割成足够小的子任务，如果子任务比较大的话还要对子任务进行继续分割

2.**执行任务并合并结果**：分割的子任务分别放到**双端队列**里，然后**几个启动线程**分别**从双端队列里获取任务执行**。**子任务执行完的结果都放在另外一个队列里**，启动一个线程从队列里取数据，然后合并这些数据。

Java的Fork/Join框架中，使用两个类完成:

1.ForkJoinTask:我们要使用Fork/Join框架，首先需要创建一个ForkJoin任务。该类提供了在任务中执行fork和join的机制。通常情况下我们不需要直接集成ForkJoinTask类，只需要继承它的子类，Fork/Join框架提供了两个子类：

　　　　`a.RecursiveAction：用于没有返回结果的任务`

　　　　`b.RecursiveTask:用于有返回结果的任务`

2.ForkJoinPool:ForkJoinTask需要通过ForkJoinPool来执行

任务分割出的子任务会添加到当前工作线程所维护的双端队列中，进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务(工作窃取算法)。

 ForkJoinTask的Fork方法的实现原理：
　　当我们调用ForkJoinTask的fork(推**送子任务，把子任务推送到线程池中去执行**)方法时，程序会把任务放在ForkJoinWorkerThread的pushTask的workQueue中，异步地执行这个任务，然后立即返回结果，代码如下：

```java
public final ForkJoinTask<V> fork() {
        Thread t;     
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
            ((ForkJoinWorkerThread)t).workQueue.push(this);
        else
            ForkJoinPool.common.externalPush(this);
        return this;
    }
```

　　pushTask方法把当前任务存放在ForkJoinTask数组队列里。然后再调用ForkJoinPool的signalWork()方法唤醒或创建一个工作线程来执行任务。代码如下：

```java
final void push(ForkJoinTask<?> task) {
            ForkJoinTask<?>[] a; ForkJoinPool p;
            int b = base, s = top, n;
            if ((a = array) != null) {    // ignore if queue removed
                int m = a.length - 1;     // fenced write for task visibility
                U.putOrderedObject(a, ((m & s) << ASHIFT) + ABASE, task);
                U.putOrderedInt(this, QTOP, s + 1);
                if ((n = s - b) <= 1) {
                    if ((p = pool) != null)
                        p.signalWork(p.workQueues, this);
                }
                else if (n >= m)
                    growArray();
            }
        }
```

　　**ForkJoinTask的join方法实现原理**

　　Join方法(**等待子任务结束**，拿到他的数据进行规整)的主要作用是阻塞当前线程并等待获取结果。让我们一起看看ForkJoinTask的join方法的实现，代码如下：

```java
public final V join() {
        int s;
        if ((s = doJoin() & DONE_MASK) != NORMAL)
            reportException(s);
        return getRawResult();
    }
```

　　它首先调用doJoin方法，通过doJoin()方法得到当前任务的状态来判断返回什么结果，任务状态有4种：已完成（NORMAL）、被取消（CANCELLED）、信号（SIGNAL）和出现异常（EXCEPTIONAL）。

　　如果任务状态是已完成，则直接返回任务结果。

　　如果任务状态是被取消，则直接抛出CancellationException

　　如果任务状态是抛出异常，则直接抛出对应的异常

在doJoin()方法里，首先通过查看任务的状态，看任务是否已经执行完成，如果执行完成，则直接返回任务状态；如果没有执行完，则从任务数组里取出任务并执行。如果任务顺利执行完成，则设置任务状态为NORMAL，如果出现异常，则记录异常，并将任务状态设置为EXCEPTIONAL。

进一步了解ForkJoinTask，ForkJoinTask与一般任务的主要区别在于它需要实现compute方法，在这个方法里，首**先需要判断任务是否足够小，如果足够小就直接执行任务**。如果不足够小，就必须分割成两个子任务，每个子任务在调用fork方法时，又会进入compute方法，看看当前子任务是否需要继续分割成子任务，如果不需要继续分割，则执行当前子任务并返回结果。使用join方法会等待子任务执行完并得到其结果。

# 9 并行设计模式

## Future 模式

Future模式的核心在于：去除了主函数的等待时间，并使得原本需要等待的时间段可以用于处理其他业务逻辑。

Future模式有点类似于商品订单。在网上购物时，提交订单后，在收货的这段时间里无需一直在家里等候，可以先干别的事情。类推到程序设计中时，当提交请求时，期望得到答复时，如果这个答复可能很慢。传统的是一直持续等待直到这个答复收到之后再去做别的事情，但如果利用Future模式，其调用方式改为异步，而原先等待返回的时间段，在主调用函数中，则可以用于处理其他事务。（**Future模式就是将同步变异步**）

Future模式是一种并行设计模式，原理是当你申请资源时，立即返回一个虚拟的资源（通常这个时候在后台异步去申请真正资源），当真正需要使用资源的时候，再将对虚拟资源的调用传递给成真正的资源（如果这个时候真正资源依然没有申请到，则阻塞）。



## 不变模式

## 生产者消费者模式



# 10 锁的优化

锁的优化是在多线程中，当有锁的时候尽可能的提升性能。降低因为**锁造成的线程阻塞和挂起**对性能造成的影响。



锁优化的集中方式：

## 减少持有锁的时间

```java
public synchronized void method（）{
    method1();
    mutextMethod(); // 需要避免被其他线程篡改的敏感资源
    method2();
}
```

如果在程序中的方法上加入了synchronized关键字，在进入这个方法之前会拿到这个对象实例的这个锁，锁住这个方法，如果这个方法中药做很多的事情，那么其他线程很可能就需要多些等待。

那么优化的思路就是尽可能的减少其他线程的的等待时间。

```java
优化后：
public  void method（）{
    method1();
    synchronized(this){
     mutextMethod(); // 需要避免被其他线程篡改的敏感资源
    }
    method2();
}
```

这样多个线程同时进入临界区的可能就会降低。

只同步相关的代码，无关的代码不要放在同步区中，尽可能的减少冲突的可能性。

## 减少锁粒度

我们加锁可能会对一个很大，很重的对象（这个对象可能会被很多线程公用）加锁。这时候我们的优化思路就是将一个**大对象拆成若干个小对象**。 增加并行度， 降低锁竞争。

eg：

`hashmap-->Collections.synchronizedMap--->ConcurrentHashMap`

## 锁分离

锁分离： 分离读写锁 ------> `ReadWritLock`  

|      | 读锁   | 写锁 |
| ---- | ------ | ---- |
| 读锁 | 可访问 | 不行 |
| 写锁 | 不行   | 不行 |

因为读是不需要改变数据，所以读和读是不需要同步的，所以可以减少加锁隔离。如果多个线程都在读的话，就一起访问，无需限制。在读多写少的情景下可以大幅度提高性能。



**锁分离思想的延伸就是——只要操作互不影响，锁就可以分离**。

例子： LinkedBlockingQueue

在任务队列中线程获取任务一个是从队列头部，一个是从尾部，这样就两者互不影响。符合锁分离的思想。

## 锁粗化

通常情况下，为了保证多线程高效并发，会要求，每个线程持有锁的时间尽量的短，即使用完公共资源后就释放锁，只有这样其他线程才能尽早的获取资源，然后去完成任务。但是仍然存在一些特殊情况，如果始终对一个锁进行不能的请求，释放，会严重消耗资源

eg:

```java
public void method()
{
    synchronized(lock)
    {
        //dosomething1
    }
    //dosomethong2,这里做其他无需同步的工作，但是很快的做完(前提)
      synchronized(lock)
    {
        //dosomething3
    }
}
```

如果中间这些其他的任务是很快的做完的，就可以将两个锁合并成一个，即下面的代码

```java
public void method()
{
    synchronized(lock)
    {
        //dosomething1
       //dosomethong2,这里做其他无需同步的工作，但是很快的做完(前提)
        //dosomething3
    }
}
```

还有就是一些极端的情况，在循环中不断的请求一个锁，这样会极大的消耗资源

```java
for(int i=0;i<n; i++)
{
    synchronized(lock)
    {
        
    }
}
```

这种情况就需要改成

```java
 synchronized(lock)
{
     for(int i=0;i<n; i++)
     {    
     }
}
//将锁加在循环外侧，这样我们就只需要获取一次锁即可。(除非存在一些特殊的情况，循环的耗时十分之久)
```



## 锁消除

锁消除是一个编译器级别的事情。

有些时候我们会对一些完全不可能加锁的代码执行一些锁的操作。有些代码完全不可能加锁，为什么还要加锁了？

有些时候，有些锁不是由开发者的代码产生的，而是jdk的内部产生的。eg: 在我们使用stringBuffer的append（）方法的时候回使用**同步**。所以当我们在使用这些方法的时候，会自然的将这些方法引入进去，而察觉不到。而这些锁可能会被用到完全不能被多线程使用的场景中。基于这种情况，为了提高性能，在某些调节参数下，系统将这些锁优化掉。

如：

```java
public static String craeteStringBuffer(String si, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(sa); //这两个操作都是同步操作
    sb.append(s2);
    return sb.toString();
}

在这个方法中sb是不会被多线程访问到的。sb是局部变量，只会作用在线程内部。其他线程不会访问到
```

jvm 使用service模式，可以开启逃逸分析，发现这个变量并没有逃逸它的作用域，jvm就会去除它的锁。(这是在jvm层面)。

## 虚拟机内对锁的优化

目的: 多线程同步的时候获得更好的性能。

### 对象头Mark

java中有很多的对象，而在虚拟机内部每个对象有个对象头。在32位系统中就是32位。它其实就是一个32位字节的信息，里面存储着描状对象的hash、锁信息，垃圾回收标记，年龄，指向锁记录的指针，指向 monitore的指针，GC标记，偏向锁线无程ID。 总之就是**对象头在32位系统中是一个保存着对象的系统性的信息的32位字节**。

### 偏向锁

线程取锁大部分情况是没有竞争的，所以可以通过偏向来提高性能
所谓的偏向，就是偏心，即锁会偏向于当前已经占有锁的线程(它会判断一线当前请求锁的线程是否已经占有这个锁了，如果已经占有这个锁了，我就认为你已经持有这把锁了，因为有时候会出现一个线程会不停的去请求同一把锁)， 它基于的思想是锁是一种悲观的策略，很多时候其实并没与那么多竞争，这是时候偏向锁就能提高效率。

将对象头Mark的标记设置为偏向，并将线程D写入对象头Mark

只要没有竞争，获得偏向锁的线程，在将来进入同步块，不需要做同步
当其他线程请求相同的锁时，偏向模式结束
XX+Use Locking
 默认启用

在竟争激烈的场合，偏向锁会増加系统负担

### 轻量级锁

### 自旋锁

