#                                                   多线程相关笔记

# 1. 为什么需要并行（多线程）
1. 业务需求
eg: 一个http服务器同一时刻需要去处理多个客户端的请求。 通常的做法是为每个客户端去开启一个线程。
2. 性能需求
由于硬件的原因，芯片发展的计算速度限制（4Hz）,我们为了性能而折中使用的是一个cpu中插入多个芯片（多核）

# 2. 并行重要的几个概念
## 同步和异步
![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/%E5%90%8C%E6%AD%A5%E5%BC%82%E6%AD%A5.JPG)
1. 同步和异步是对方法的调用而言的。如果一个方法是同步的，在时间轴上，同步调用会等待方法的返回。
1. 异步调用他会立即返回(但是这并不表示方法调用已经结束了，而是会在后台起一个线程慢慢的进行方法的执行)。
1. 异步调用在调用后会立即得到方法的返回，并不会做等待，这就表示不会影响调用者后面做什么事。

## 并发和并行
![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/%E5%B9%B6%E5%8F%91%E4%B8%8E%E5%B9%B6%E8%A1%8C.JPG)
1. 并行指的是两个线程同时执行。
1. 并发指的是两个线程交替执行。
1. 并行存在于多个CPU中，单个CPU只能并发。(虽然这两者的外在表现形式是相同的，我们看到的都是两个任务在同时执行)
***

## 临界区
临界区用来表示一种公共的资源和数据，可以被多个线程使用(所有的线程都能访问临界区)。	临界区需要被保护，同一时间只允许一个线程来访问他。

## 阻塞和非阻塞
1. 阻塞和非阻塞是用来形容多线程间的相互影响。比如： 两个线程都需要使用临界区的资源，一个线程在访问的时候，另一个线程无法访问
另个一个线程被迫等待，这就是阻塞。阻塞的含义是这个线程在操作系统层面别挂起，这就导致阻塞的性能一般不会太好。
	
1. 非阻塞就是允许多个线程同时进入阻塞区(只要保证不把临界区资源弄坏)
	
## 锁 ，饥饿 和活锁
![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/%E6%AD%BB%E9%94%81.JPG)
1. 对于阻塞的程序来讲，进入临界区可能发生死锁的情况。如图中四辆车辆发生了死锁，每辆小车锁占有的路线视为资源。每辆车各占一条路，A车需要B车锁占得路，都堵住了无法行走，除非挪走一辆车。------这就是死锁。
	
1. 与死锁对应的就是活锁。就类似电梯遇人的情况。电梯门开了，里面的人要出来，外面的人要进去，两人迎面碰撞，这是，为了下次再相撞，里面的人往左让，外面的人往右让，这是两人又相撞了，这时你发现不对，就往右让，他往左让，因为你们是相对的，如此总是会把门堵住。。。如此反复。-----这就是活锁。(如果是人的话，会停下来，让一个人先过，但是程序不会，他会一直执行)。如果是发生在线程上面的，一个线程如果在抢占到资源后发现另外的资源他无法拿到，(死锁的必要条件是抢占了资源而不释放它，如果抢占了资源释放了它，就不会产生死锁) 因为这时候没有办法拿到所有的资源，这时候为避免死锁，就释放掉已经拿到的资源，然而这时候另外的线程也做了同样的事情。他们需要相同的资源（比如说都需要资源A 和资源B ,这时候一个线程抢占了A,另一个线程抢占了资源B,但是这个时候两个线程都释放了资源，这个时候发现A,B 都有了，又进行新一波的抢占和释放）。这就是活锁，就是资源在线程之间跳来跳去且任务无法继续下去，活锁这样的问题是动态的，死锁是静态问题。
	​	
	
1. 饥饿时在一种情况下： 在等待队列中，一个线程的优先级很低，在线程抢占调度过程总是无法调用到这个线程，就会导致这个线程无法抢占资源而饿死。
    ​	
## 并发的级别

1. 阻塞
	
1. 非阻塞
	
1. 无障碍 ：最弱的非阻塞调度(和阻塞调度相比，阻塞调度室一种悲观的策略，它会认为大家一起修改数据会导致把数据改坏，所以每次只允许一个人去修改，而非阻塞调度相比而言比较乐观，可以大家一起修改，放开了锁都可以进，但它是一种宽进严出的策略，如果它发现一个线程操作临界区数据与其他线程操作发生了冲突，它会回滚重试)，
	
1. 无锁
无锁是无障碍的升级版，无锁首要条件是无障碍的，它在无障碍的基础上加了一个条件就是保证在临界区的竞争中保证会有一个胜出。(无锁的使用比较广泛)
	
1. 无等待
无锁
	​			
要求所有线程必须在有限步数里完成
	​				
无饥饿
	​				
(无等待的典型案例：所有的线程只是读线程，不是写线程必然是无等待的。）


# 3.并行的两个重要的定律

## 1. Amdahl定律（阿姆达尔定律）
![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/%E9%98%BF%E5%A7%86%E8%BE%BE%E5%B0%94%E5%AE%9A%E5%BE%8B(%E5%8A%A0%E9%80%9F%E6%AF%94).JPG)

阿姆达尔定律定义了串行系统并行化后的加速比( 加速比=优化前系统耗时/优化后系统耗时)--理论上限


## 2. Gustafson定律（古斯塔夫森定律）

只要有足够的并行化，加速比和CPU个数成正比

# 4 多线程基础

（1）Java中线程的概念和操作系统的线程是类似的。事实上Java中会把Java的线程映射到操作系统的线程中去。就是说如果在Java中开启了一个线程就等同于在windows中调用了开启线程的方法。

（2）线程的start() 方法和run() 方法的区别： run() 实现于runnable接口(==每个线程都是runnable接口的一个实现==)，start()只是在一个新的操作系统的线程上去调用run()方法，实际上调用run()而调用start(),两者实质上做的事情都一样start()就是去调用run方法，但是调用run()方法并不会开启一个新的线程，而是在调用的当前线程去执行操作；只有使用了start()才是真的开了一个新的线程。 


(3) 线程的操作
###### 创建

Thread.start() 和run() 方法

###### 终止

Thread.stop()方法(++不推荐使用，这个API太暴力，会导致线程没有执行完的时候就释放了资源，会导致多线程的数据的不一致性++)。

###### 线程的中断

线程中断的三个方法

```
(Thread)
public void interrupt() //中断线程
public Boolean  isInterrupted //判断是否被中断
public static boolean interrupted //判断是否被中断，并清除当前中断状态

```
什么是线程中断？

> 就是在线程运行时，对它打了个招呼，它就会把自己的一个所谓的中断标志位置上。这样他在运行的时候就知道有人需要它在运行的时候做一些响应。这样他就会做一些额外的操作。

> 因为Thread.stop()太过于暴力会影响数据的一致性。可以使用线程的中断来较为温和的停止线程。

==中断并不会让线程执行，他只是打了一个招呼，如果要让线程做额外的操作，需要再额外的填添加一些操作逻辑==

线程中断另一种常用的方法：


```
public static  native void sleep(long milis) throw InterruptedException // 线程休眠
```
如果我们希望一个线程不要走太快我们一般会调用sleep()方法让其休眠几秒钟。

> sleep()等让线程等待方法或抛出InterruptedException。(是因为线程希望在等待的过程中被打招呼中断他也能被结束，因为在等的过程中对业务来说是没有任何意义的，在等的过程中是应该要对别人打招呼作出响应的)。 sleep方法一般是要被try catch的。只有这样，当别人给我打招呼中断的时候才能立即的通过interruptedException来进行响应来做出一些动作。 (==这里需要注意一点抛出interruptedException后会中断异常标记==。--如果是在一个循环中，想要后续的仍能检测到中断标志需要重新调用一次interrupt()方法。如下图
> ![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/sleep%E4%B8%AD%E6%96%AD.JPG)

###### 线程让步

==static== Thread.yield( ) 方法：使当前线程从运行就绪状态。cpu会从众多的可执行态里选择，也就是说，当前也就是刚刚的那个线程还是有可能会被再次执行到的，并不是说一定会执行其他线程而该线程在下一次中不会执行到了。

###### 等待线程结束

join() 等待县城结束：
在我们使用多线程的时候我，我们并不知道线程在什么时候执行完了。往往我们希望等到线程结束后，拿到一些信息才能进行下去。（命名为join的原因：两个线程在不同的路上的走，要join加到一条路上就要等另一个走完。a.join()就是要等a线程走完）

join(long milins)等待多少时间

###### 线程的挂起(suspend)和线程的继续执行(resune)

> 当一个线程在执行过程中吗，如果你希望它能暂停一下，可以使用suspend()方法将线程挂起，当你希望线程继续执行的时候可以调用resume()方法让线程继续执行。 ==但是这两个方法不推荐使用==。 不推荐使用的原因是：如果线程加了锁后执行suspend()方法挂起，在调用resume()之前它不会释放临界区的资源，这样会导致其他线程无法获取到临界区资源而无法执行。
如下图：
![img](https://raw.githubusercontent.com/zhaodahan/zhao_Note/master/wiki_img/%E7%BA%BF%E7%A8%8B%E6%B0%B8%E4%B9%85%E8%A2%AB%E6%8C%82%E8%B5%B7.JPG)
在多线程情况下：如果另一线程执行resume()在线程执行suspend()之前，将会导致需要锁的线程被永久挂起。

---
==1.如果安装了jdk，在cmd中可以使用jps命令查看系统中的Java进程
2. 使用jstack pid(进程id) 可以导出该进程的所有线程。==
---

（4） 守护线程
在后台默默的完成一些系统维护（如： 垃圾回收）的一些线程。 一个Java程序中珠现场执行完，不管还有没有守护线程，系统就认为已无用，就会关闭。

Thread t=new Thread();
t.setDeamon(true); //设置线程为守护线程。必须设置在start()之前；

（5）线程的优先级
线程是可以抢占到资源的，优先级高的线程是更有可能抢占到资源的，更快的执行。

```
设置线程优先级的方法是在线程上调用setPriority()设置优先级
```

(6) 线程的同步，通信

多线程执行过程中十分重要的一点就是—多线程之间如何通信。一个线程挂起了，别人怎么来唤醒我。还有几个线程彼此竞争，如何来协调竞争了。

Java对这些情况提供了synchronized 关键字来协调线程竞争

synchronize需要注意的一点就是多个线程一定是要操作同一个锁；（特别是当synchronize加到实力方法上，它锁的对象就是当前执行线程传进来的实例）

![synchronized.png](https://github.com/zhaodahan/zhao_Note/blob/master/img-storage/synchronized.png?raw=true)

~~~java
Java提供Ojbect.wait()  //线程等待在当前对象(Object --synchronized(object)监视器)上
Object.notify() 用于线程通信 //通知等待在object这个对象上的线程从wait()上返回
~~~

也就是说一个线程调用了wait()方法就会进入wait状态，而不会再接着往下执行。

wait()这个方法的执行，必须要获得object这个监视器对象。这个方法的大致做的事情是在内存中建立一个键值对表，object监视器作为key,等待在当前监视器上的线程队列作为value。 这个线程队列中可以存在许多线程。当调用object。notify()的时候，会通过object这个监视器对象作为key，在键值对表中查询出它对应的等待线程队列。 然后随机的竞争一个线程来唤醒。

![wait，notify.JPG](https://github.com/zhaodahan/zhao_Note/blob/master/img-storage/wait%EF%BC%8Cnotify.JPG?raw=true)

在使用这两个方法的时候需要注意

![wait,notify 注意事项.JPG](https://github.com/zhaodahan/zhao_Note/blob/master/img-storage/wait,notify%20%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9.JPG?raw=true)

无论是wait()还是notify(),==在调用之前都必须先获得这个监视器的这个锁==，即需要在synchronized 修饰代码块中才能使用。(十分正常的逻辑，你需要先拿到他，才能调用他的方法)。且调用了object.wait()方法后， 线程的会释放object这个监视器的所有权，然后等待其他线程将他唤醒。

# 5 无锁

1.什么是无锁？

无锁就是“无障碍”的运行且每次竞争的时候必然会决出一个优胜者。“无障碍” 就是可以任意的访问临界区的资源，不用等待获取锁，多个线程可以同时的进入临界区。

2.无锁的原理：

说到了无锁，那就要说我们为什么要有锁。并发编程的加锁是为了保证共享对象的安全访问。 加锁的本质就是变“并发”为“串行”， 一次只允许一个访问就安全了。 并发变成了串行必然的访问速度的就降低了。 要提高速度就只能不加锁。 无锁由此诞生。

无锁的实现是使用了CAS算法。

3. 什么是CAS算法

CAS（Compare-and-Swap），即比较并替换，是一种实现并发算法时常用到的技术，Java并发包中的很多类都使用了CAS技术。

简而言之： CAS算法就是一种比synchronized关键字更加优秀的能保证并发访问时共享对象安全的策略。

CAS算法

要讲cas算法就要讲无锁

1. 什么是无锁

   相对于线程加锁来保证线程访问临界区的安全访问这种悲观策略。 无锁是一种相对乐观的策略，他认为对临界区的访问不存在冲突，无需阻塞。

2. 无锁的时候多线程共同访问临界区的资源怎么办？

   无锁就是通过CAS比较交换技术来鉴别线程冲突。一旦检测到有异常就会重试当前操作，直到没有异常为止。

CAS的原理

1. 具体内容

   一个CAS方法包含三个参数CAS(V,E,N)。V表示要更新的变量，E表示预期的值，N表示新值。只有当V的值等于E时，才会将V的值修改为N。如果V的值不等于E，说明已经被其他线程修改了，当前线程可以放弃此操作，也可以再次尝试次操作直至修改成功。基于这样的算法，CAS操作即使没有锁，也可以发现其他线程对当前线程的干扰（临界区值的修改），并进行恰当的处理

   说人话就是：CAS算法会在一个线程视图操作临界区变量的值的时候判断一下要修改的值是否是我们需要的预期的值，即它是否被其他的线程给修改了。 否则就重试，直至成功。 CAS算法是cpu指令级的原子操作(避免了异常)。

   **CAS 的含义是“我认为原有的值应该是什么，如果是，则将原有的值更新为新值，否则不做修改，并告诉我原来的值是多少”**。


**cas**

 当多个线程同时使用CAS操作一个变量时，只有一个会胜出，并成功更新。 其余都失败，失败的不会被挂起，仅被告知失败，并允许再次尝试，和放弃操作。cas只是由一条CPU指令完成，是指令级的操作。

# 6. 多线程工具类

都是synchronized关键字的升级版

## java 线程的中断机制

什么是中断？ 就是讲线程==从阻塞等待状态中唤醒==，并做出相应的响应处理。

有时想让主线程启动的一个子线程结束运行，我们就需要让这个子线程中断，不再继续执行。线程是有中断机制的，我们可以对每个线程进行中断标记，注意只是标记，中断与否还是虚拟机自己的事情，虚拟机自己家的事情，我们也就说说，不能实际操作控制他家

### 中断时一种协作机制

中断时一种协作机制，当一个线程需要中断另一个线程的时候，只是一种通知和商量，告诉被中断线程，请你中断操作。 他不是要求线程立即做中断操作，他只是设置了中断标志，线程检测中断标志后可以，可以在自己方便的时候中断线程，中断操作是被中断线程自己做的逻辑，所以他有很大的主动性，他可以释放掉一些资源后才中断，也可以什么都不做

### 中断状态

每个Java线程都维护着一个Boolean interrupted属性的中断状态值。（true 表示需要进行中断）

| public static boolean**interrupted**() | 这个方法是用来清除中断状态的值得，清除成功返回true，反之false |
| :------------------------------------: | ------------------------------------------------------------ |
|   **public boolean isInterrupted()**   | 判断线程是否已经中断，不对中断值做任何操作                   |
|      public void **interrupt**()       | 中断线程， 将interrupted 设置为true                          |

 线程调用了 Interrupt()并不会立即的就停止线程，它只是设置了线程的阻塞状态是true，当线程在调用阻塞方法的时候，阻塞方法轮询interrupted，检测是否中断，如果为true，就抛出InterruptedException。

### 中断的处理

作为一种协作机制，中断机制不强求中断线程一定要在某个点进行出路。

在JDK中，很多阻塞方法的声明中有抛出InterruptedException异常，这暗示该方法是可中断的，这些方法会检测当前线程是否被中断，如果是，则立刻结束阻塞方法，并抛出InterruptedException异常

程序捕获到这些可中断的阻塞方法抛出的InterruptedException或检测到中断后，该如何处理：

1. 如果是可中断的阻塞方法抛出InterruptedException，可以继续的往上层抛异常，由上层调用异常。
2. 如果是检测到中断，则可以清除中断并抛出中断异常使当前方法也成为一个可中断的方法
3. 如果 在当前方法中不方便抛出异常这时就可以捕获可中断方法的InterruptedException并通过Thread.currentThread.interrupt()来重新设置中断状态

由上面可以在抛出InterruptedException之前都是清除了中断标志了的。这是因为抛出InterruptedException就是已经对检测到中断标志做了一次处理了。已经处理过了一次，标志也就无效了。 如果需要再做一次处理就需要重新设置一次标志位。

==这里抛出异常使为了让线程从阻塞的状态清醒过来，且抛出中断异常后中断标志就清除了==。

设置标志位只是为引起中断线程的注意，被中断线程可以决定如何应对中断

## AQS(AbstractQueuedSynchronizer)

AQS）作为java.util.concurrent包的基础，它提供了一套完整的同步编程框架，开发人员只需要实现其中几个简单的方法就能自由的使用诸如独占，共享，条件队列等多种同步模式。我们常用的比如ReentrantLock，CountDownLatch等等基础类库都是基于AQS实现的

AQS通过内部实现的FIFO等待队列来完成资源获取线程的等待工作，如果当前线程获取资源失败，AQS则会将当前线程以及等待状态等信息构造成一个Node结构的节点，并将其加入等待队列中，同时会阻塞当前线程；当其它获取到资源的线程释放持有的资源时，则会把等待队列节点中的线程唤醒，使其再次尝试获取对应资源。

AQS 的基本属性  ：

~~~java
// 头结点，你直接把它当做 当前持有锁的线程 可能是最好理解的
private transient volatile Node head;
// 阻塞的尾节点，每个新的节点进来，都插入到最后，也就形成了一个隐视的链表
private transient volatile Node tail;
// 这个是最重要的，不过也是最简单的，代表当前锁的状态，0代表没有被占用，大于0代表有线程持有当前锁
// 之所以说大于0，而不是等于1，是因为锁可以重入嘛，每次重入都加上1
private volatile int state;
// 代表当前持有独占锁的线程，举个最重要的使用例子，因为锁可以重入
// reentrantLock.lock()可以嵌套调用多次，所以每次用这个来判断当前线程是否已经拥有了锁
// if (currentThread == getExclusiveOwnerThread()) {state++}
private transient Thread exclusiveOwnerThread; //继承自AbstractOwnableSynchronizer

~~~



内部定义的Node**类的代码**

~~~java
static final class Node {
        //声明共享模式下的等待节点
        static final Node SHARED = new Node();
        //声明独占模式下的等待节点
        static final Node EXCLUSIVE = null;

     // ======== 下面的几个int常量是给waitStatus用的 ===========
        //waitStatus的一常量值，代表此线程取消了争抢这个锁
        static final int CANCELLED =  1;
        //表示当前node的后继节点对应的线程需要被唤醒
        static final int SIGNAL    = -1;
        //waitStatus的一常量值，表示线程正在等待条件
        static final int CONDITION = -2;
        //waitStatus的一常量值，表示下一个acquireShared应无条件传播
        static final int PROPAGATE = -3;

         //waitStatus,其值只能为CANCELLED、SIGNAL、CONDITION、PROPAGATE或0
        //初始值为0
       // 这么理解，暂时只需要知道如果这个值 大于0 代表此线程取消了等待，
      // 也许就是说半天抢不到锁，不抢了，ReentrantLock是可以指定timeouot的。。。
        volatile int waitStatus;

        //前驱节点
        volatile Node prev;

        //后继节点
        volatile Node next;

        //当前节点的线程，在节点初始化时赋值，使用后为null
        volatile Thread thread;

        //下一个等待节点
        Node nextWaiter;
    //nextWaiter 这个属性在这个时候是没用的，因为它用来实现 Condition
        Node() { 
        }

        Node(Thread thread, Node mode) { // Used by addWaiter，将当前线程创建为一个什么模式的节点
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }
~~~

![img](https://images2018.cnblogs.com/blog/1400011/201805/1400011-20180513211820725-389872507.png)

![aqs-0](https://javadoop.com/blogimages/AbstractQueuedSynchronizer/aqs-0.png)

（阻塞队列不包含 head，head节点标识持有锁的线程，所在阻塞队列中）

AbstractQueuedSynchronizer的三个重要属性

~~~java
  //等待队列的头结点
    private transient volatile Node head;
    //等待队列的尾节点
    private transient volatile Node tail;
    //同步状态，这个很重要
    private volatile int state;
~~~

可以得到同步队列的基本结构：

![img](https://images2018.cnblogs.com/blog/1400011/201805/1400011-20180513211830066-167429002.png)

AbstractQueuedSynchronizer类中其它方法主要是用于插入节点、释放节点

插入节点过程如下图所示：

![img](https://images2018.cnblogs.com/blog/1400011/201805/1400011-20180513211836170-1249096077.png)

释放头结点过程如下图所示：

![img](https://images2018.cnblogs.com/blog/1400011/201805/1400011-20180513211842294-1124199004.png)

AQS的实现依赖的是由Node节点构建的内部先进先出的双向链表队列。

**线程获取和释放锁的本质是去修改AQS内部代表同步变量的值 state  不为0代表线程获取了锁 0带表线程未获取锁**

AQS 中维护着两个队列，一个同步队列（获取锁失败暂时阻塞），一个等待队列(因为某些条件主动调用await()进行阻塞，必须要被主动唤醒)。

ReentrantLock 的使用方式：

```java
// 我用个web开发中的service概念吧
public class OrderService {
    // 使用static，这样每个线程拿到的是同一把锁，当然，spring mvc中service默认就是单例，别纠结这个
    private static ReentrantLock reentrantLock = new ReentrantLock(true);

    public void createOrder() {
        // 比如我们同一时间，只允许一个线程创建订单
        reentrantLock.lock();
        // 通常，lock 之后紧跟着 try 语句
        try {
            // 这块代码同一时间只能有一个线程进来(获取到锁的线程)，
            // 其他的线程在lock()方法上阻塞，等待获取到锁，再进来
            // 执行代码...
            // 执行代码...
            // 执行代码...
        } finally {
            // 释放锁
            reentrantLock.unlock();
        }
    }
}
```

ReentrantLock 在内部用了内部类 Sync 来管理锁，所以真正的获取锁和释放锁是由 Sync 的实现类来控制的。

```java
abstract static class Sync extends AbstractQueuedSynchronizer{}
```

Sync 有两个实现，分别为 NonfairSync（非公平锁）和 FairSync（公平锁），我们看 FairSync 部分。

```java
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

**线程抢锁**

~~~java
static final class FairSync extends Sync {
      // 争锁
    //ReentrantLock 具有排他性，lock() 方法要么阻塞，要么顺利拿到锁返回
    final void lock() {
        acquire(1);
    }
    // 我们看到，这个方法，如果tryAcquire(arg) 返回true, 也就结束了。
    // 否则，acquireQueued方法会将线程压到队列中
    public final void acquire(int arg) { // 此时 arg == 1
        // 首先调用tryAcquire(1)一下，名字上就知道，这个只是试一试
        // 因为有可能直接就成功了呢，也就不需要进队列排队了，
        if (!tryAcquire(arg) &&
            // tryAcquire(arg)没有成功，这个时候需要把当前线程挂起，放到阻塞队列中。
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) {
              selfInterrupt();
        }
    }
//acquireQueued 一般会返回false。 acquireQueued 返回值代表的是：是否被中断过。但是，不管是否被中断过，acquireQueued 出来以后，线程的中断状态一定是 false，所以如果发生过中断，要重新设置中断状态。虽然 acquire(int arg) 确实是不关心中断的，但是它会保持这个状态，如果客户端想要知道是否发生过中断的话，还是可以知道的。因为中断情况下，中断状态虽然中间丢过，但是 selfInterrupt() 会设置回去。会实际受到中断影响的是另一个方法 acquireInterruptibly(int arg)，这个方法会通过抛出异常的方式告诉客户端。
    
    /**
     *  Don't grant access unless recursive call or no waiters or is first.
     *  不允许访问，除非 递归调用(重入锁)或 无等待线程 或是 是第一个线程。
     */
    // 尝试直接获取锁，返回值是boolean，代表是否获取到锁
    // 返回true：1.没有线程在等待锁；2.重入锁，线程本来就持有锁，也就可以理所当然可以直接获取
    protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        // state == 0 此时此刻没有线程持有锁
        if (c == 0) {
            // 虽然此时此刻锁是可以用的，但是这是公平锁，既然是公平，就得讲究先来后到，
            // 看看有没有别人在队列中等了半天了
            if (!hasQueuedPredecessors() &&
                // 如果没有线程在等待，那就用CAS尝试一下，成功了就获取到锁了，
                  // 不成功的话，只能说明一个问题，就在刚刚几乎同一时刻有个线程抢先了 =_=
                // 因为刚刚还没人的，我判断过了
                compareAndSetState(0, acquires)) {

                // 到这里就是获取到锁了，标记一下，告诉大家，现在是我占用了锁
                setExclusiveOwnerThread(current);
                return true;
            }
        }
          // 会进入这个else if分支，说明是重入了，需要操作：state=state+1
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        // 如果到这里，说明前面的if和else if都没有返回true，说明没有获取到锁
        // 回到上面一个外层调用方法继续看:
        // if (!tryAcquire(arg) 
        //        && acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) 
        //     selfInterrupt();
        return false;
    }

    // 假设tryAcquire(arg) 返回false，没获取到锁，加入等待队列，那么代码将执行：
      //        acquireQueued(addWaiter(Node.EXCLUSIVE), arg)，
    // 这个方法，首先需要执行：addWaiter(Node.EXCLUSIVE)

    /**
     * Creates and enqueues node for current thread and given mode.
     *
     * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
     * @return the new node
     */
    // 此方法的作用是把线程包装成node，同时进入到队列中
    // 参数mode此时是Node.EXCLUSIVE，代表独占模式
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        //尝试EnQ的快速路径；在失败时备份到完全eNQ
        // 以下几行代码想把当前node加到链表的最后面去，也就是进到阻塞队列的最后
        Node pred = tail;

        // tail!=null => 队列不为空(tail==head的时候，其实队列是空的，不过不管这个吧)
        //pred==null，队列没有初始化时(第一次有节点进入等待队列的时候)
        if (pred != null) { 
            // 设置自己的前驱 为当前的队尾节点
            node.prev = pred; 
            // 用CAS把自己设置为队尾, 如果成功后，tail == node了
            if (compareAndSetTail(pred, node)) { 
                // 进到这里说明设置成功，当前node==tail, 将自己与之前的队尾相连，
                // 上面已经有 node.prev = pred
                // 加上下面这句，也就实现了和之前的尾节点双向连接了
                pred.next = node;
                // 线程入队了，可以返回了
                return node;
            }
        }
        // 仔细看看上面的代码，如果会到这里，
        // 说明 pred==null(队列是空的) 或者 CAS失败(有线程在竞争入队)
        enq(node);
        return node;
    }

    /**
     * Inserts node into queue, initializing if necessary.
     * @param node the node to insert
     * @return node's predecessor 返回插入节点的前置节点(这个返回在addWaiter()中并没有被使用，但返回值在transferForSignal() 中被使用 )
     */
    // 采用自旋的方式入队
    // 之前说过，到这个方法只有两种可能：等待队列为空，或者有线程竞争入队，
    // 自旋在这边的语义是：CAS设置tail过程中，竞争一次竞争不到，我就多次竞争，总会排到的
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            // 之前说过，队列为空也会进来这里
            if (t == null) { // Must initialize
                // 初始化head节点
                // 细心的读者会知道原来head和tail初始化的时候都是null
                // 还是一步CAS，你懂的，现在可能是很多线程同时进来呢
                if (compareAndSetHead(new Node()))
                    // 给后面用：这个时候head节点的waitStatus==0, 看new Node()构造方法就知道了

                    // 这个时候有了head，但是tail还是null，设置一下，
                    // 把tail指向head，放心，马上就有线程要来了，到时候tail就要被抢了
                    // 注意：这里只是设置了tail=head，这里可没return哦，没有return，没有return
                    // 所以，设置完了以后，继续for循环，下次就到下面的else分支了
                    tail = head;
            } else {
                // 下面几行，和上一个方法 addWaiter 是一样的，
                // 只是这个套在无限循环里，反正就是将当前线程排到队尾，有线程竞争的话排不上重复排
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }


    // 现在，又回到这段代码了
    // if (!tryAcquire(arg) 
    //        && acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) 
    //     selfInterrupt();

    // 下面这个方法，参数node，经过addWaiter(Node.EXCLUSIVE)，此时已经进入阻塞队列
    // 注意一下：如果acquireQueued(addWaiter(Node.EXCLUSIVE), arg))返回true的话，
    // 意味着上面这段代码将进入selfInterrupt()，所以正常情况下，下面应该返回false
    // 这个方法非常重要，应该说真正的线程挂起，然后被唤醒后去获取锁，都在这个方法里了
    通过tryAcquire()和addWaiter()，该线程获取资源失败，已经被放入等待队列尾部了。线程下一部该干什么了？：---（进入等待状态--线程在前两个方法之中虽然将线程封装成Node节点，但是并没有设置具体的状态，这一步就是要设置node节点的waitStatus状态的值并将线程挂去）--休息，直到其他线程彻底释放资源后唤醒自己，自己再拿到资源，然后就可以去干自己想干的事了。跟医院排队拿号有点相似~~acquireQueued()就是干这件事：在等待队列中排队拿号（中间没其它事干可以休息），直到拿到号后再返回。
    final boolean acquireQueued(final Node node, int arg) { 
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                // p == head 说明当前节点虽然进到了阻塞队列，但是是阻塞队列的第一个，因为它的前驱是head
                // 注意，阻塞队列不包含head节点，head一般指的是占有锁的线程，head后面的才称为阻塞队列
                // 所以当前节点可以去试抢一下锁
                // 这里说一下，为什么可以去试试：
                // 首先，它是队头，这个是第一个条件，其次，当前的head有可能是刚刚初始化的node，
                // enq(node) 方法里面有提到，head是延时初始化的，而且new Node()的时候没有设置任何线程
                // 也就是说，当前的head不属于任何一个线程，所以作为队头，可以去试一试，
                // tryAcquire已经分析过了，就是简单用CAS试操作一下state
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                // 到这里，说明上面的if分支没有成功，要么当前node本来就不是队头，
                // 要么就是tryAcquire(arg)没有抢赢别人，继续往下看
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Checks and updates status for a node that failed to acquire.
     * Returns true if thread should block. This is the main signal
     * control in all acquire loops.  Requires that pred == node.prev
     *
     * @param pred node's predecessor holding status
     * @param node the node
     * @return {@code true} if thread should block
     */
    // 刚刚说过，会到这里就是没有抢到锁呗，这个方法说的是："当前线程没有抢到锁，是否需要挂起当前线程？"
    // 第一个参数是前驱节点，第二个参数才是代表当前线程的节点
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        // 前驱节点的 waitStatus == -1 ，说明前驱节点状态正常，当前线程需要挂起，直接可以返回true
        if (ws == Node.SIGNAL)
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             * 这个线程已经请求释放
             */
            return true;

        // 前驱节点 waitStatus大于0 ，之前说过，大于0 说明前驱节点取消了排队。这里需要知道这点：
        // 进入阻塞队列排队的线程会被挂起，而唤醒的操作是由前驱节点完成的。
        // 所以下面这块代码说的是将当前节点的prev指向waitStatus<=0的节点，
        // 简单说，就是为了找个好爹，因为你还得依赖它来唤醒呢，如果前驱节点取消了排队，
        // 找前驱节点的前驱节点做爹，往前循环总能找到一个好爹的
        if (ws > 0) {
            /*
             * Predecessor was cancelled. Skip over predecessors and
             * indicate retry.
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
             */
            // 仔细想想，如果进入到这个分支意味着什么
            // 前驱节点的waitStatus不等于-1和1，那也就是只可能是0，-2，-3
            // 在我们前面的源码中，都没有看到有设置waitStatus的，所以每个新的node入队时，waitStatu都是0
            // 用CAS将前驱节点的waitStatus设置为Node.SIGNAL(也就是-1)，就是说设置这个节点是可以唤醒的
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    // private static boolean shouldParkAfterFailedAcquire(Node pred, Node node)
    // 这个方法结束根据返回值我们简单分析下：
    // 如果返回true, 说明前驱节点的waitStatus==-1，是正常情况，那么当前线程需要被挂起，等待以后被唤醒
    //        我们也说过，以后是被前驱节点唤醒，就等着前驱节点拿到锁，然后释放锁的时候叫你好了
    // 如果返回false, 说明当前不需要被挂起，为什么呢？往后看

    // 跳回到前面是这个方法
    // if (shouldParkAfterFailedAcquire(p, node) &&
    //                parkAndCheckInterrupt())
    //                interrupted = true;

    // 1. 如果shouldParkAfterFailedAcquire(p, node)返回true，
    // 那么需要执行parkAndCheckInterrupt():

    // 这个方法很简单，因为前面返回true，所以需要挂起线程，这个方法就是负责挂起线程的
    // 这里用了LockSupport.park(this)来挂起线程，然后就停在这里了，等待被唤醒=======
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    // 2. 接下来说说如果shouldParkAfterFailedAcquire(p, node)返回false的情况

   // 仔细看shouldParkAfterFailedAcquire(p, node)，我们可以发现，其实第一次进来的时候，一般都不会返回true的，原因很简单，前驱节点的waitStatus=-1是依赖于后继节点设置的。也就是说，我都还没给前驱设置-1呢，怎么可能是true呢，但是要看到，这个方法是套在循环里的，所以第二次进来的时候状态就是-1了。
}
=================================================
    waitStatus 中 SIGNAL(-1) 状态的意思，Doug Lea 注释的是：代表后继节点需要被唤醒。也就是说这个 waitStatus 其实代表的不是自己的状态，而是后继节点的状态，我们知道，每个 node 在入队的时候，都会把前驱节点的状态改为 SIGNAL，然后阻塞，等待被前驱唤醒
    https://javadoop.com/post/AbstractQueuedSynchronizer
===============================
//acquireQueued(final Node node, int arg)方法的疑问
boolean failed = true;
try {
    boolean interrupted = false;
    for (;;) {
        final Node p = node.predecessor();
        if (p == head && tryAcquire(arg)) {
            setHead(node);
            p.next = null; // help GC
            failed = false;
            return interrupted;
        }
        if (shouldParkAfterFailedAcquire(p, node) &&
            parkAndCheckInterrupt())
            interrupted = true;
    }
} finally {
   // 第一个问题：这个finally里的cancelAcquire 似乎永远都不会被执行吧
  //答:这部分其实是用于响应中断或超时的
    if (failed)
        cancelAcquire(node);
}
//unparkSuccessor(Node node)
Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null;
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
  //第二个问题：为什么都是要从tail往前找第一个状态是非CANCEL的节点呢，如果从head往后找第一个状态是非CANCEL的话效率会不会高一点
    }
    if (s != null)
        LockSupport.unpark(s.thread);

// 答：首先，第一行代码先检测 head 的后继节点，只有当此时的后继节点不存在或者这个后继节点取消了才开始从后往前找，所以大部分情况下，其实不会发生从后往前遍历整个队列的情况。（后继节点取消很正常，但是某节点在入队的时候，如果发现前驱是取消状态，前驱节点是会被请出队列的）
//addWaiter(Node mode)加节点是加在尾部的 存在并发问题：从前往后寻找不一定能找到刚刚加入队列的后继节点
 Node pred = tail;
    if (pred != null) {
        node.prev = pred;
        // 1. 先设置的 tail
        if (compareAndSetTail(pred, node)) {
            // 2. 设置前驱节点的后继节点为当前节点
            pred.next = node;
            return node;
        }
    }
//源码作者如此设计，巧妙的解决了并发问题。因为先node.prev = pred; 再compareAndSetTail(pred, node) 再 pred.next = node; 这种操作步奏 是安全的，以此为前提，如果从前往后找的话 如果执行完成 compareAndSetTail(pred, node)  而pred.next = node; 还未来的及执行的话 新加的tail是无法被遍历到的。


~~~

**解锁操作**

正常情况下，如果线程没获取到锁，线程会被 `LockSupport.park(this);` 挂起停止，等待被唤醒。

```java
// 唤醒的代码还是比较简单的，你如果上面加锁的都看懂了，下面都不需要看就知道怎么回事了
public void unlock() {
    sync.release(1);
}

public final boolean release(int arg) {
    // 往后看吧
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
}

// 回到ReentrantLock看tryRelease方法
protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    // 是否完全释放锁
    boolean free = false;
    // 其实就是重入的问题，如果c==0，也就是说没有嵌套锁了，可以释放了，否则还不能释放掉
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    setState(c);
    return free;
}

/**
 * Wakes up node's successor, if one exists.
 * @param node the node
 */
// 唤醒后继节点 ： 1：修改节点状态 2 实际的唤醒线程
// 从上面调用处知道，参数node是head头结点
private void unparkSuccessor(Node node) {
    int ws = node.waitStatus;
    // 如果head节点当前waitStatus<0, 将其修改为0
    if (ws < 0) //这里状态值通常是-1
        compareAndSetWaitStatus(node, ws, 0);
    /*
     * Thread to unpark is held in successor, which is normally
     * just the next node.  But if cancelled or apparently null,
     * traverse backwards from tail to find the actual
     * non-cancelled successor.
     */
    // 下面的代码就是唤醒后继节点，但是有可能后继节点取消了等待（waitStatus==1）
    // 从队尾往前找，找到waitStatus<=0的所有节点中排在最前面的
    Node s = node.next;
    if (s == null || s.waitStatus > 0) { //s.waitStatus > 0 线程放弃挣抢锁
        s = null;
        // 从后往前找，仔细看代码，不必担心中间有节点取消(waitStatus==1)的情况
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    if (s != null)
        // 唤醒线程
        LockSupport.unpark(s.thread);
}
```

唤醒线程以后，被唤醒的线程将从以下代码中继续往前走：

```java
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this); // 刚刚线程被挂起在这里了
    return Thread.interrupted();
}
// 又回到这个方法了：acquireQueued(final Node node, int arg)，这个时候，node的前驱是head了，接着执行下面的代码，又去释放节点并挣抢锁。
     if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
```

**总结**

在并发环境下，加锁和解锁需要以下三个部件的协调：

1. 锁状态。我们要知道锁是不是被别的线程占有了，这个就是 state 的作用，它为 0 的时候代表没有线程占有锁，可以去争抢这个锁，用 CAS 将 state 设为 1，如果 CAS 成功，说明抢到了锁，这样其他线程就抢不到了，如果锁重入的话，state进行+1 就可以，解锁就是减 1，直到 state 又变为 0，代表释放锁，所以 lock() 和 unlock() 必须要配对啊。然后唤醒等待队列中的第一个线程，让其来占有锁。
2. 线程的阻塞和解除阻塞。AQS 中采用了 LockSupport.park(thread) 来挂起线程，用 unpark 来唤醒线程。
3. 阻塞队列。因为争抢锁的线程可能很多，但是只能有一个线程拿到锁，其他的线程都必须等待，这个时候就需要一个 queue 来管理这些线程，AQS 用的是一个 FIFO 的队列，就是一个链表，每个 node 都持有后继节点的引用。

**非公平锁**

ReentrantLock 默认采用非公平锁，除非你在构造方法中传入参数 true 。

非公平锁的 lock 方法：

```java
static final class NonfairSync extends Sync {
    final void lock() {
        // 2. 和公平锁相比，这里会直接先进行一次CAS，成功就返回了,这里就体现了“抢”的含义
        if (compareAndSetState(0, 1))
            setExclusiveOwnerThread(Thread.currentThread());
        else
            acquire(1);
    }
    // AbstractQueuedSynchronizer.acquire(int arg)
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
    protected final boolean tryAcquire(int acquires) {
        return nonfairTryAcquire(acquires);
    }
}
/**
 * Performs non-fair tryLock.  tryAcquire is implemented in
 * subclasses, but both need nonfair try for trylock method.
 */
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        //发现锁这个时候被释放了（state == 0），非公平锁会直接 CAS 抢锁
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

总结：公平锁和非公平锁只有两处不同：

1. 非公平锁在调用 lock 后，首先就会调用 CAS 进行一次抢锁，如果这个时候恰巧锁没有被占用，那么直接就获取到锁返回了。
2. 非公平锁在 CAS 失败后，和公平锁一样都会进入到 tryAcquire 方法，在 tryAcquire 方法中，如果发现锁这个时候被释放了（state == 0），非公平锁会直接 CAS 抢锁，但是公平锁会判断等待队列是否有线程处于等待状态，如果有则不去抢锁，乖乖排到后面。



### 深入浅出AQS之独占锁模式

AQS独占锁的执行逻辑:

**获取锁的过程：**

1. 当线程调用acquire()申请获取锁资源，如果成功，则进入临界区。
2. 当获取锁失败时，则进入一个FIFO等待队列，然后被挂起等待唤醒。
3. 当队列中的等待线程被唤醒以后就重新尝试获取锁资源，如果成功则进入临界区，否则继续挂起等待。

释放锁过程：

1. 当线程调用release()进行锁资源释放时，如果没有其他线程在等待锁资源，则释放完成。
2. 如果队列中有其他等待锁资源的线程需要唤醒，则唤醒队列中的第一个等待节点（先入先出）

~~~java
   public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
首先是调用开发人员自己实现的tryAcquire() 方法尝试获取锁资源，如果成功则整个acquire()方法执行完毕，即当前线程获得锁资源，可以进入临界区。
如果获取锁失败，则开始进入后面的逻辑，加入等待队列

~~~

首先是addWaiter(Node.EXCLUSIVE)方法 : 这里是独占锁模式，所以节点模式为Node.EXCLUSIVE

线程要包装为Node对象的主要原因，除了用Node构造供虚拟队列外，还用Node包装了各种线程状态

SIGNAL(-1) ：线程的后继线程正/已被阻塞，当该线程release或cancel时要重新这个后继线程(unpark)

CANCELLED(1)：因为超时或中断，该线程已经被取消

CONDITION(-2)：表明该线程被处于条件队列，就是因为调用了Condition.await而被阻塞

PROPAGATE(-3)：传播共享锁

0：0代表无状态

~~~java
//注意：该入队方法的返回值就是新创建的节点
    private Node addWaiter(Node mode) {
        //基于当前线程，节点类型（Node.EXCLUSIVE）创建新的节点
        //由于这里是独占模式，因此节点类型就是Node.EXCLUSIVE
        Node node = new Node(Thread.currentThread(), mode);
        Node pred = tail;
        //这里为了提搞性能，首先执行一次快速入队操作，即直接尝试将新节点加入队尾
        if (pred != null) {
            node.prev = pred;
            //这里根据CAS的逻辑，即使并发操作也只能有一个线程成功并返回，其余的都要执行后面的入队操作。即enq()方法
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    //完整的入队操作
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail; // tail 尾节点， t就是尾节点
            //如果队列还没有初始化，则进行初始化，即创建一个空的头节点
            if (t == null) {  
                //同样是CAS，只有一个线程可以初始化“头结点”成功，其余的都要重复执行循环体
                if (compareAndSetHead(new Node())) 
                    tail = head; //初始化的时候头结点和尾节点指向同一个节点
            } else {
                //新创建的节点指向队列尾节点，毫无疑问并发情况下这里会有多个新创建的节点指向队列尾节点
                node.prev = t; //简单来说就是将新节点放在原来链表的尾节点后
                //基于这一步的CAS，不管前一步有多少新节点都指向了尾节点，这一步只有一个能真正入队成功，其他的都必须重新执行循环体
                if (compareAndSetTail(t, node)) { 
                  //这里只是将尾指针指向新节点，将新节点变成尾节点并没有修改t指向的值。t只是作为一个比较值
                    t.next = node;  
                    //该循环体唯一退出的操作，就是入队成功（否则就要无限重试）
                    return t;
                }
            }
        }
    }
========================================================================
一： 初始化队列的触发条件就是当前已经有线程占有了锁资源，因此上面创建的空的头节点可以认为就是当前占有锁资源的节点（虽然它并没有设置任何属性）。
二： 注意整个代码是处在一个死循环中，知道入队成功。如果失败了就会不断进行重试。 
三： 代码中使用无线循环的原因, 因为这是无锁的，在高并发的情况下，可能存在多个线程同时执行这个方法，但是无锁CAS只会允许一个线程执行成功，所以使用了无线循环让其他线程的任务在下次循环过程中能被执行成功，如果执行成功就会退出循环。
~~~



经过上面的操作，我们申请获取锁的线程已经成功加入了等待队列，那么节点接下来就要被挂起，等待被唤醒

（这里挂起就是线程被中断     Thread.currentThread().interrupt(); ）

~~~java
final boolean acquireQueued(final Node node, int arg) { //node就是刚入队的包含当前线程信息的节点
        //锁资源获取失败标记位
        boolean failed = true;
        try {
            //等待线程被中断标记位
            boolean interrupted = false;
            //这个循环体执行的时机包括新节点入队和队列中等待节点被唤醒两个地方
            for (;;) {
                //获取当前节点的前置节点
                final Node p = node.predecessor();
                //如果前置节点就是头结点，则尝试获取锁资源
                if (p == head && tryAcquire(arg)) {
                    //当前节点获得锁资源以后设置为头节点，这里继续理解我上面说的那句话
                    //头结点就表示当前正占有锁资源的节点
                    setHead(node);
                    p.next = null; //帮助GC
                    //表示锁资源成功获取，因此把failed置为false
                    failed = false;
                    //返回中断标记，表示当前节点是被正常唤醒还是被中断唤醒
                    return interrupted;
                }
                如果没有获取锁成功，则进入挂起逻辑
                if (shouldParkAfterFailedAcquire(p, node) && //先判断线程获取锁失败后是否应该被挂起
                    parkAndCheckInterrupt()) //如果应该被挂起就执行挂起逻辑并检查中断
                    interrupted = true;
            }
        } finally {
            //最后会分析获取锁失败处理逻辑
            if (failed)
                cancelAcquire(node);
        }
    }
~~~

~~~java
//这个方法就是判断该线程是否应该被挂起
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {//node是当前线程的节点，pred是它的前置节点
        //获取前置节点的waitStatus
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
       //如果前置节点的waitStatus是Node.SIGNAL则返回true，然后会执行parkAndCheckInterrupt()方法进行挂起
       //这里有点难以理解，但是线程在定义节点--- SIGNAL(-1) ：线程的后继线程正/已被阻塞(下一个线程需要被挂起)
            return true;
        if (ws > 0) {
            //由waitStatus的几个取值可以判断这里表示前置节点被取消
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            //这里我们由当前节点的前置节点开始，一直向前找最近的一个没有被取消的节点
            //注，由于头结点head是通过new Node()创建，它的waitStatus为0,因此这里不会出现空指针问题，也就是说最多就是找到头节点上面的循环就退出了
            pred.next = node;
        } else {
            //根据waitStatus的取值限定，这里pre的waitStatus的值只能是0或者PROPAGATE(共享锁模式)，那么我们把前置节点的waitStatus设为Node.SIGNAL然后重新进入该方法进行判断
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

---------------------------------------------------
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this); //挂起
        return Thread.interrupted();
    }

~~~

释放锁的过程：

~~~javascript
public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }
tryRelease()方法是用户自定义的释放锁逻辑
如果成功，就判断等待队列中有没有需要被唤醒的节点（waitStatus为0表示没有需要被唤醒的节点,0是无效节点）
---------------------
private void unparkSuccessor(Node node) { //此处的节点是头结点， 头结点是获取了锁的节点，他的下一个节点是等待中的节点，也就是需要被唤醒的节点
        int ws = node.waitStatus;
        if (ws < 0)
            //把标记为设置为0，表示唤醒操作已经开始进行，提高并发环境下性能
            compareAndSetWaitStatus(node, ws, 0);

        Node s = node.next;
        //如果当前节点的后继节点为null，或者已经被取消
        if (s == null || s.waitStatus > 0) {
            s = null;  //这里s可能不为null，只是一个废弃节点
            //注意这个循环没有break，也就是说它是从后往前找，一直找到离当前节点最近的一个等待唤醒的节点
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        //执行唤醒操作
        if (s != null)
            LockSupport.unpark(s.thread);
    }


~~~

#### AQS独占锁的取消排队

怎么取消对锁的竞争？

最重要的方法是这个，我们要在这里面找答案：

```java
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

首先，到这个方法的时候，节点一定是入队成功的。

我把 parkAndCheckInterrupt() 代码贴过来：

```java
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);
    return Thread.interrupted();
}
```

这两段代码联系起来看，是不是就清楚了。

如果我们要取消一个线程的排队，我们需要在另外一个线程中对其进行中断。比如某线程调用 lock() 老久不返回，我想中断它。一旦对其进行中断，此线程会从 `LockSupport.park(this);` 中唤醒，然后 `Thread.interrupted();` 返回 true。

我们发现一个问题，即使是中断唤醒了这个线程，也就只是设置了 `interrupted = true` 然后继续下一次循环。而且，由于 `Thread.interrupted();` 会清除中断状态，第二次进 parkAndCheckInterrupt 的时候，返回会是 false。

所以，我们要看到，在这个方法中，interrupted 只是用来记录是否发生了中断，然后用于方法返回值，其他没有做任何相关事情。

所以，我们看外层方法怎么处理 acquireQueued 返回 false 的情况。

```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
static void selfInterrupt() {
    Thread.currentThread().interrupt();
}
```

所以说，lock() 方法处理中断的方法就是，你中断归中断，我抢锁还是照样抢锁，几乎没关系，只是我抢到锁了以后，设置线程的中断状态而已，也不抛出任何异常出来。调用者获取锁后，可以去检查是否发生过中断，也可以不理会。

我们来看 ReentrantLock 的另一个 lock 方法：

```java
public void lockInterruptibly() throws InterruptedException {
    sync.acquireInterruptibly(1);
}
```

方法上多了个 `throws InterruptedException` 。

```java
public final void acquireInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    if (!tryAcquire(arg))
        doAcquireInterruptibly(arg);
}
```

继续往里：

```java
private void doAcquireInterruptibly(int arg) throws InterruptedException {
    final Node node = addWaiter(Node.EXCLUSIVE);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return;
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                // 就是这里了，一旦异常，马上结束这个方法，抛出异常。
                // 这里不再只是标记这个方法的返回值代表中断状态
                // 而是直接抛出异常，而且外层也不捕获，一直往外抛到 lockInterruptibly
                throw new InterruptedException();
        }
    } finally {
        // 如果通过 InterruptedException 异常出去，那么 failed 就是 true 了
        if (failed)
            cancelAcquire(node);
    }
}
```

### 深入浅出AQS之共享锁模式

**执行过程**

获取锁的过程：

1. 当线程调用acquireShared()申请获取锁资源时，如果成功，则进入临界区。
2. 当获取锁失败时，则创建一个共享类型的节点并进入一个FIFO等待队列，然后被挂起等待唤醒。
3. 当队列中的等待线程被唤醒以后就重新尝试获取锁资源，如果成功则**唤醒后面还在等待的共享节点并把该唤醒事件传递下去，即会依次唤醒在该节点后面的所有共享节点**，然后进入临界区，否则继续挂起等待。

释放锁过程：

1. 当线程调用releaseShared()进行锁资源释放时，如果释放成功，则唤醒队列中等待的节点，如果有的话。

~~~java
获取共享锁的方法acquireShared()
public final void acquireShared(int arg) {
        //尝试获取共享锁，返回值小于0表示获取失败
        if (tryAcquireShared(arg) < 0)
            //执行获取锁失败以后的方法
            doAcquireShared(arg);
 }

----------------------------------------------------
    private void doAcquireShared(int arg) { //获取锁失败后挂起
        //添加等待节点的方法跟独占锁一样，唯一区别就是节点类型变为了共享型，不再赘述
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                //表示前面的节点已经获取到锁，自己会尝试获取锁
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    //注意上面说的， 等于0表示不用唤醒后继节点，大于0，获取锁成功，唤醒后继节点
                    if (r >= 0) {
                        //这里是重点，获取到锁以后的唤醒操作，后面详细说
                        setHeadAndPropagate(node, r);
                        p.next = null;
                        //如果是因为中断醒来则设置中断标记位
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                //挂起逻辑跟独占锁一样，不再赘述
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            //获取失败的取消逻辑跟独占锁一样，不再赘述
            if (failed)
                cancelAcquire(node);
        }
    }
~~~



### 深入浅出AQS之条件队列

## 1.ReentrantLock

ReentrantLock（重入锁）是synchronized的升级版，但是两者在性能上并无太大的差异，只是ReentantLock具有更完善的功能：可中断响应、锁申请等待限时、公平锁等。 如果并发量较少就不是一定需要使用ReentrantLock。

~~~java
public static ReentrantLock lock = new ReentrantLock();
public void run() {
        for (int j = 0; j < 10000; j++) {
            lock.lock();  // 看这里就可以
            //lock.lock(); ①
            try {
                i++;
            } finally { //try --finnally这是一种标准写法
                lock.unlock(); // 看这里就可以
                //lock.unlock();②
            }
        }
    }
使用重入锁加锁是一种显式的操作。其对逻辑控制的灵活性远大于synchronized关键字，但是他并不像synchronized关键字那样会自己unLock。 ReentrantLock 需要自己显式的来解锁。并且加锁与解锁的次数要一样，这里就引出了“重”入的概念。重入锁对一个线程可以加几次锁(但是需要注意的是： 加锁次数需要和解锁次数一样，加了多少道锁就需要解多少锁)。

~~~



### 中断

相对于synchronized关键字来说，要么获取到锁执行到底，要么持续等待，中间不能中断执行。但是如果加锁之后这个线程陷入死循环，这时导致其他的线程无法获取资源而陷入死锁，这时就需要中断线程，synchronized关键字是无法解决这种情况的。而重入锁的中断响应就能解决这种情况。譬如一个正在等待锁的线程被“告知”无需再继续等待下去，停止工作。

~~~java
public class KillDeadlock implements Runnable{
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public KillDeadlock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                //t1线程的代码
                lock1.lockInterruptibly();  // 以可以响应中断的方式加锁
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                lock2.lockInterruptibly();
            } else {
                 //t2线程的代码
                lock2.lockInterruptibly();  // 以可以响应中断的方式加锁
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) lock1.unlock();  // 注意判断方式
            if (lock2.isHeldByCurrentThread()) lock2.unlock();
            System.err.println(Thread.currentThread().getId() + "退出！");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        KillDeadlock deadLock1 = new KillDeadlock(1);
        KillDeadlock deadLock2 = new KillDeadlock(2);
        Thread t1 = new Thread(deadLock1);
        Thread t2 = new Thread(deadLock2);
        t1.start();t2.start();
        Thread.sleep(1000);
        t2.interrupt(); // ③
    }
}

---------------------
 注意： 
1：lock1.lockInterruptibly(); 只有加这种可中断锁才能监听到线程中断信号，跑出InterruptedException异常，进而结束线程。
2： 这里需要注意的事，重入锁可以加多次锁是有条件的。
重入锁ReentrantLock，顾名思义，就是支持重进入的锁，它表示该锁能够支持一个线程对资源的重复加锁。
一个线程中能对同一把锁加多次，但是如果要加其他的锁，则其他的锁则应该处于解锁(未加锁状态)
------------------------------------------------------------------------
    
    源码解释：
       final void lock() {
            if (compareAndSetState(0, 1)) //初始加锁，锁的状态是0，加了锁之后修改为1
                setExclusiveOwnerThread(Thread.currentThread()); //取锁成功，当前线程加锁， setExclusiveOwnerThread 排他独有的线程，多个线程竞争，只有一个线程能竞争到锁。
            else
                acquire(1); //锁已经被占用，取锁失败，请求标志加一
        }
----
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
// tryAcquire(arg) 再去试着请求一次锁，万一锁被释放了，就加锁，不执行后面的代码
   protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
      final boolean nonfairTryAcquire(int acquires) { // acquires=1
            final Thread current = Thread.currentThread();
            int c = getState(); //获取加锁的状态
            if (c == 0) { //c=0 ，锁的资源已经被释放
                if (compareAndSetState(0, acquires)) { //重新尝试加锁，如果成功就返回
                    setExclusiveOwnerThread(current); 
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) { 
            //锁未被释放就去判断一下被锁的线程是否是当前线程，如果是同一个线程将申请标志加1，这个标志随着重入次数而递增，在解锁的时候回不短的递减，直至解锁为0后才能完全释放完锁。这里也解释了上面的问题重入锁限制的问题。
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false; //如果不是当前线程且未获取到锁则返回false
        }
//addWaiter(Node.EXCLUSIVE)： 此处Node.EXCLUSIVE是null,      
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node; 
    }//将当前线程构建成一个双向链表的节点并返回
  //acquireQueued(addWaiter(Node.EXCLUSIVE), arg) ==acquireQueued(Node, 1) 
  //acquireQueued 请求排队方法
      final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) { 
  //当前节点是head节点的next节点且尝试请求加锁成功，则释放当前节点
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
//此处是做Node节点线程的自旋过程，自旋过程主要检查当前节点是不是head节点的next节点，如果是，则尝试获取锁，如果获取成功，那么释放当前节点，同时返回。  cancelAcquire(node) 和 selfInterrupt();

~~~

### 锁申请等待限时

可以使用 tryLock()或者tryLock(long timeout, TimeUtil unit) 方法进行一次限时的锁等待。

在指定时长内获取到锁则继续执行，如果等待指定时长后还没有获取到锁则返回false

~~~java
public void run() {
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) { // 等待1秒
                Thread.sleep(2000);  //休眠2秒
            } else {
                System.err.println(Thread.currentThread().getName() + "获取锁失败！");
            }
        } catch (Exception e) {
            if (lock.isHeldByCurrentThread()) lock.unlock();
        }
    }

~~~

### 公平锁

所谓公平锁，就是按照时间先后顺序，使先等待的线程先得到锁，而且，公平锁不会产生饥饿锁，也就是只要排队等待，最终能等待到获取锁的机会。使用重入锁（默认是非公平锁）创建公平锁：（我们默认使用的就不公平锁）

~~~java
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
~~~



## 2.condition

通常在开发并发程序的时候，会碰到需要停止正在执行业务A，来执行另一个业务B，当业务B执行完成后业务A继续执行。ReentrantLock通过Condtion等待/唤醒这样的机制.

condition 等价于synchronized关键字中使用的Object的wait()和notify方法,Condition 是基于 ReentrantLock 实现的。

condition 是依赖于 ReentrantLock 的，不管是调用 await 进入等待还是 signal 唤醒，都必须获取到锁才能进行操作。

它和ReentrantLock结合起来用于线程的等待和唤醒，但是它更加的灵活，与Object下的wait() 只能有一个等待队列不同，Condition可以实现由多个条件下的等待队列，condition顾名思义可知，在不同的条件下可以创建不同的等待队列，调用一次lock.newCondition()就为lock下生成一个等待队列。

基本内容：

1.Condition提供了await()方法将当前线程阻塞，并提供signal()方法支持另外一个线程将已经阻塞的线程唤醒。

2.Condition需要结合Lock使用

3 线程调用await()方法前必须获取锁，调用await()方法时，将线程构造成节点加入等待队列，同时释放锁，并挂起当前线程

4 其他线程调用signal()方法前也必须获取锁，当执行signal()方法时将等待队列的节点移入到同步队列，当线程退出临界区释放锁的时候，唤醒同步队列的首个节点

![img](https://upload-images.jianshu.io/upload_images/5507455-37635d0723174712.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/923/format/webp)

每个 ReentrantLock 实例可以通过调用多次 newCondition 产生多个 ConditionObject 的实例：

```java
final ConditionObject newCondition() {
    return new ConditionObject();
}
```

 Condition 的实现类 `AbstractQueuedSynchronizer` 类中的 `ConditionObject`。

~~~java
public class ConditionObject implements Condition, java.io.Serializable {
        private static final long serialVersionUID = 1173984872572414699L;
        // 条件队列的第一个节点
      // 不要管这里的关键字 transient，是不参与序列化的意思
        private transient Node firstWaiter;
        // 条件队列的最后一个节点
        private transient Node lastWaiter;    

Condition内部维护了一个由线程封装的Node节点组成的单向链表(等待队列)，这个链表的作用是存放等待signal信号的线程。
~~~

 AQS 的时候，我们有一个**阻塞队列**（同步队列），用于保存等待获取锁的线程的队列。引入另一个概念，叫**条件队列**（condition queue）

![condition-2](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-2/aqs2-2.png)

简单回顾下 AQS中Node 的属性：

```java
volatile int waitStatus; // 可取值 0、CANCELLED(1)、SIGNAL(-1)、CONDITION(-2)、PROPAGATE(-3)
volatile Node prev;
volatile Node next;
volatile Thread thread;
Node nextWaiter;
```

prev 和 next 用于实现阻塞队列的双向链表，**nextWaiter 用于实现条件队列的单向链表**；

1. 我们知道一个 ReentrantLock 实例可以通过多次调用 newCondition() 来产生多个 Condition 实例，这里对应 condition1 和 condition2。注意，ConditionObject 只有两个属性 firstWaiter 和 lastWaiter；
2. 每个 condition 有一个关联的**条件队列**，如线程 1 调用 condition1.await() 方法即可将当前线程 1 包装成 Node 后加入到条件队列中，然后阻塞在这里，不继续往下执行，条件队列是一个单向链表；调用 condition1.signal() 会将condition1 对应的**条件队列**的 firstWaiter 移到**阻塞队列**的队尾，等待获取锁，获取锁后 await 方法返回，继续往下执行。

Condition是一个接口，它主要是由awiat和singal方法组成，awiat方法是放弃自身锁，进入阻塞状态，等待信号进行唤醒，singal是唤醒线程，让线程去重新竞争锁。它和Object的wait和notify方法是一样的。

### Condition 的await()方法： 

他做两件事，将当前线程加入到等待队列，和完全地解开加在线程上的锁。线程放弃共享资源的所有权(且线程暂时不挣抢资源)，进入等待	

~~~java
// 首先，这个方法是可被中断的，不可被中断的是另一个方法 awaitUninterruptibly()
// 这个方法会阻塞，直到调用 signal 方法（指 signal() 和 signalAll()，下同），或被中断
public final void await() throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    // 添加到 condition 的条件队列中
    Node node = addConditionWaiter();
    // 释放锁，返回值是释放锁之前的 state 值
    int savedState = fullyRelease(node);
    int interruptMode = 0;
    // 这里退出循环有两种情况，之后再仔细分析
    // 1. isOnSyncQueue(node) 返回 true，即当前 node 已经转移到阻塞队列了
    // 2. checkInterruptWhileWaiting(node) != 0 会到 break，然后退出循环，代表的是线程中断
    while (!isOnSyncQueue(node)) {
        LockSupport.park(this);
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
    // 被唤醒后，将进入阻塞队列，等待获取锁
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}

//将节点加入到条件队列
// 将当前线程对应的节点入队，插入队尾
private Node addConditionWaiter() {
    Node t = lastWaiter;
    // 如果条件队列的最后一个节点取消了，将其清除出去
    if (t != null && t.waitStatus != Node.CONDITION) {
        // 这个方法会遍历整个条件队列，然后会将已取消的所有节点清除出队列
        unlinkCancelledWaiters();
        t = lastWaiter;
    }
    Node node = new Node(Thread.currentThread(), Node.CONDITION);
    // 如果队列为空
    if (t == null)
        firstWaiter = node;
    else
        t.nextWaiter = node;
    lastWaiter = node;
    return node;
}
//unlinkCancelledWaiters() 方法，该方法用于清除队列中已经取消等待的节点
//当 await 的时候如果发生了取消操作，或者是在节点入队的时候，发现最后一个节点是被取消的，会调用一次这个方法。
// 等待队列是一个单向链表，遍历链表将已经取消等待的节点清除出去
// 纯属链表操作，很好理解，看不懂多看几遍就可以了
private void unlinkCancelledWaiters() {
    Node t = firstWaiter;
    Node trail = null; //追踪节点
    while (t != null) {
        Node next = t.nextWaiter;
        // 如果节点的状态不是 Node.CONDITION 的话，这个节点就是被取消的
        if (t.waitStatus != Node.CONDITION) {
            //这里t是需要被移除的节点
            t.nextWaiter = null;
            if (trail == null)
                firstWaiter = next;
            else
                trail.nextWaiter = next;
            if (next == null)
                lastWaiter = trail;
        }
        else
            trail = t;
        t = next;
        
        // 在循环中 Node next = t.nextWaiter和 t != null ， t = next;结合起来遍历队列
    }
}

//完全释放独占锁

//回到 wait 方法，节点入队了以后，会调用 int savedState = fullyRelease(node); 方法释放锁，注意，这里是完全释放独占锁，因为 ReentrantLock 是可以重入的

// 首先，我们要先观察到返回值 savedState 代表 release 之前的 state 值
// 对于最简单的操作：先 lock.lock()，然后 condition1.await()。
//         那么 state 经过这个方法由 1 变为 0，锁释放，此方法返回 1
//         相应的，如果 lock 重入了 n 次，savedState == n
// 如果这个方法失败，会将节点设置为"取消"状态，并抛出异常 IllegalMonitorStateException
final int fullyRelease(Node node) {
    boolean failed = true;
    try {
        int savedState = getState();
        // 这里使用了当前的 state 作为 release 的参数，也就是完全释放掉锁，将 state 置为 0
        if (release(savedState)) {
            failed = false;
            return savedState;
        } else {
            throw new IllegalMonitorStateException();
        }
    } finally {
        if (failed)
            node.waitStatus = Node.CANCELLED;
    }
}
//这里release（savedState） 回去调用tryRelease(arg),如果savedState是因为重入锁变成了n，那么这里的tryRelease的参数也是n，
 public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }
     protected final boolean tryRelease(int releases) { //这里参数是n
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int nextc = getState() - releases; //这里必定会变成0 savedState = getState()
            boolean free = exclusiveCount(nextc) == 0;
            if (free)
                setExclusiveOwnerThread(null);
            setState(nextc);
            return free;
        }

//等待进入阻塞队列
// 回到上面的代码
int interruptMode = 0;
while (!isOnSyncQueue(node)) {
    // 线程挂起
    LockSupport.park(this);

    if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
        break;
}
//释放掉锁以后（所有阻塞在这个条件队列都要被移到阻塞队列中,但是一次只唤醒一个，因为isOnSyncQueue(Node node)返回false后会挂起当前线程），接下来是这段，这边会自旋，如果发现自己还没到阻塞队列，那么挂起，等待被转移到阻塞队列

//isOnSyncQueue(Node node) 用于判断节点是否已经转移到阻塞队列了：
// 在节点被移入到条件队列的时候，初始化时设置了 waitStatus = Node.CONDITION
// 前面我提到，signal 的时候需要将节点从条件队列移到阻塞队列，
// 这个方法就是判断 node 是否已经移动到阻塞队列了
final boolean isOnSyncQueue(Node node) {
    // 移动过去的时候，node 的 waitStatus 会置为 0，这个之后在说 signal 方法的时候会说到
    // 如果 waitStatus 还是 Node.CONDITION，也就是 -2，那肯定就是还在条件队列中
    // 如果 node 的前驱 prev 指向还是 null，说明肯定没有在 阻塞队列
    if (node.waitStatus == Node.CONDITION || node.prev == null)
        return false;
    // 如果 node 已经有后继节点 next 的时候，那肯定是在阻塞队列了，因为条件队列中的节点在初始化的时候next是null，他们是用nextWaiter来连接链表的
    if (node.next != null) 
        return true;

    // 这个方法从阻塞队列的队尾开始从后往前遍历找，如果找到相等的，说明在阻塞队列，否则就是不在阻塞队列

    // 可以通过判断 node.prev() != null 来推断出 node 在阻塞队列吗？答案是：不能。
    // 这个可以看AQS 的入队方法，首先设置的是 node.prev 指向 tail，
    // 然后是 CAS 操作将自己设置为新的 tail，可是这次的 CAS 是可能失败的。

    // 调用这个方法的时候，往往我们需要的就在队尾的部分，所以一般都不需要完全遍历整个队列的
    return findNodeFromTail(node);
}

// 从同步队列的队尾往前遍历，如果找到，返回 true
private boolean findNodeFromTail(Node node) {
    Node t = tail;
    for (;;) {
        if (t == node)
            return true;
        if (t == null) //第一次是不会走这里的
            return false;
        t = t.prev;
    }
}

//回到前面的循环，isOnSyncQueue(node) 返回 false 的话，那么进到 LockSupport.park(this); 这里线程挂起
~~~

### signal 唤醒线程，转移到阻塞队列

因为刚刚到 LockSupport.park(this); 把线程挂起了，等待唤醒。

唤醒操作通常由另一个线程来操作，就像生产者-消费者模式中，如果线程因为等待消费而挂起，那么当生产者生产了一个东西后，会调用 signal 唤醒正在等待的线程来消费。

```java
// 唤醒等待了最久的线程，条件队列的头结点
// 其实就是，将这个线程对应的 node 从条件队列转移到阻塞队列
public final void signal() {
    // 调用 signal 方法的线程必须持有当前的独占锁
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
    Node first = firstWaiter;
    if (first != null)
        doSignal(first);
}

// 从条件队列队头往后遍历，找出第一个需要转移的 node
// 因为前面我们说过，有些线程会取消排队，但是还在队列中
private void doSignal(Node first) {
    do {
          // 将 firstWaiter 指向 first 节点后面的第一个
        // 如果将队头移除后，后面没有节点在等待了，那么需要将 lastWaiter 置为 null
        if ( (firstWaiter = first.nextWaiter) == null)
            lastWaiter = null;
        // 因为 first 马上要被移到阻塞队列了，和条件队列的链接关系在这里断掉
        first.nextWaiter = null;
    } while (!transferForSignal(first) &&
             (first = firstWaiter) != null);
      // 这里 while 循环，如果 first 转移不成功，那么选择 first 后面的第一个节点进行转移，依此类推
}

// 将节点从条件队列转移到阻塞队列
// true 代表成功转移
// false 代表在 signal 之前，节点已经取消了
final boolean transferForSignal(Node node) {

    // CAS 如果失败，说明此 node 的 waitStatus 已不是 Node.CONDITION，说明节点已经取消，
    // 既然已经取消，也就不需要转移了，方法返回，转移后面一个节点
    // 否则，将 waitStatus 置为 0
    if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
        return false;

    // enq(node): 自旋进入阻塞队列的队尾
    // 注意，这里的返回值 p 是 node 在阻塞队列的前驱节点
    Node p = enq(node);
    int ws = p.waitStatus;
    // ws > 0 说明 node 在阻塞队列中的前驱节点取消了等待锁，直接唤醒 node 对应的线程。唤醒之后会怎么样，后面再解释
    // 如果 ws <= 0, 那么 compareAndSetWaitStatus 将会被调用，上篇介绍的时候说过，节点入队后，需要把前驱节点的状态设为 Node.SIGNAL(-1)
    if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
        // 如果前驱节点取消或者 CAS 失败，会进到这里唤醒线程，之后的操作后面再讲解
        LockSupport.unpark(node.thread);
    return true;
}
```

正常情况下，`ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL)` 这句中，ws <= 0，而且 compareAndSetWaitStatus(p, ws, Node.SIGNAL) 会返回 true，所以一般也不会进去 if 语句块中唤醒 node 对应的线程。然后这个方法返回 true，也就意味着 signal 方法结束了，节点进入了阻塞队列。

假设发生了阻塞队列中的前驱节点取消等待，或者 CAS 失败，只要唤醒线程，让其进到下一步即可。

**唤醒后检查中断状态**

上一步 signal 之后，我们的线程由条件队列转移到了阻塞队列，之后就准备获取锁了。只要重新获取到锁了以后，继续往下执行。等线程从挂起中恢复过来继续往下看

```java
int interruptMode = 0;
while (!isOnSyncQueue(node)) {
    // 线程挂起
    LockSupport.park(this);
    if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
        break;
}
```

解释下 interruptMode。interruptMode 可以取值为 REINTERRUPT再中断（1），THROW_IE（-1），0

- REINTERRUPT： 代表 await 返回的时候，需要重新设置中断状态
- THROW_IE： 代表 await 返回的时候，需要抛出 InterruptedException 异常
- 0 ：说明在 await 期间，没有发生中断

有以下三种情况会让 LockSupport.park(this); 这句返回继续往下执行：

1. 常规路径。signal -> 转移节点到阻塞队列 -> 获取了锁（unpark）

2. 线程中断。在 park 的时候，另外一个线程对这个线程进行了中断

3. signal 的时候我们说过，转移以后的前驱节点取消了，或者对前驱节点的CAS操作失败了

   ~~~java
   if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
           // 如果前驱节点取消或者 CAS 失败，会进到这里唤醒线程，之后的操作后面再讲解
           LockSupport.unpark(node.thread);
   ~~~

4. 假唤醒。这个也是存在的，和 Object.wait() 类似，都有这个问题

线程唤醒后第一步是调用 checkInterruptWhileWaiting(node) 这个方法，此方法用于判断是否在线程挂起期间发生了中断，如果发生了中断，是 signal 调用之前中断的，还是 signal 之后发生的中断。

```java
// 1. 如果在 signal 之前已经中断，返回 THROW_IE
// 2. 如果是 signal 之后中断，返回 REINTERRUPT
// 3. 没有发生中断，返回 0
private int checkInterruptWhileWaiting(Node node) {
    return Thread.interrupted() ?
        (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
        0;
}
```

> Thread.interrupted()：如果当前线程已经处于中断状态，那么该方法返回 true，同时将中断状态重置为 false，所以，才有后续的 `重新中断（REINTERRUPT）` 的使用。

怎么判断是 signal 之前还是之后发生的中断：

```java
// 只有线程处于中断状态，才会调用此方法
// 如果需要的话，将这个已经取消等待的节点转移到阻塞队列
// 返回 true：如果此线程在 signal 之前被取消，
final boolean transferAfterCancelledWait(Node node) {
    // 用 CAS 将节点状态设置为 0 
    // 如果这步 CAS 成功，说明是 signal 方法之前发生的中断，因为如果 signal 先发生的话，signal 中会将 waitStatus 设置为 0
    if (compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
        // 将节点放入阻塞队列
        // 这里我们看到，即使中断了，依然会转移到阻塞队列
        enq(node);
        return true;
    }

    // 到这里是因为 CAS 失败，肯定是因为 signal 方法已经将 waitStatus 设置为了 0
    // signal 方法会将节点转移到阻塞队列，但是可能还没完成，这边自旋等待其完成
    // 当然，这种事情还是比较少的吧：signal 调用之后，没完成转移之前，发生了中断
    while (!isOnSyncQueue(node))
        Thread.yield();
    return false;
}
```

> 即使发生了中断，节点依然会转移到阻塞队列。

到这里，大家应该都知道这个 while 循环怎么退出了吧。要么中断，要么转移成功。

**获取独占锁**

while 循环出来以后，下面是这段代码：

```java
if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
    interruptMode = REINTERRUPT;
```

由于 while 出来后，我们确定节点已经进入了阻塞队列，准备获取锁。

这里的 acquireQueued(node, savedState) 的第一个参数 node 之前已经经过 enq(node) 进入了队列，参数 savedState 是之前释放锁前的 state，这个方法返回的时候，代表当前线程获取了锁，而且 state == savedState了。

注意，前面我们说过，不管有没有发生中断，都会进入到阻塞队列，而 acquireQueued(node, savedState) 的返回值就是代表线程是否被中断。如果返回 true，说明被中断了，而且 interruptMode != THROW_IE，说明在 signal 之后断了，这里将 interruptMode 设置为 REINTERRUPT，用于待会重新中断。

继续往下：

```java
if (node.nextWaiter != null) // clean up if cancelled
    unlinkCancelledWaiters();
if (interruptMode != 0)
    reportInterruptAfterWait(interruptMode);
本着一丝不苟的精神，这边说说 `node.nextWaiter != null` 怎么满足。我前面也说了 signal 的时候会将节点转移到阻塞队列，有一步是 node.nextWaiter = null，将断开节点和条件队列的联系。
可是，`在判断发生中断的情况下，是 signal 之前还是之后发生的？` 这部分的时候，也介绍了，如果 signal 之前就中断了，也需要将节点进行转移到阻塞队列，这部分转移的时候，是没有设置 node.nextWaiter = null 的。
之前说过，如果有节点取消，也会调用 unlinkCancelledWaiters 这个方法，就是这里了。
```

**处理中断状态**

到这里，我们终于可以好好说下这个 interruptMode 干嘛用了。

- 0：什么都不做。
- THROW_IE：await 方法抛出 InterruptedException 异常
- REINTERRUPT：重新中断当前线程

```java
private void reportInterruptAfterWait(int interruptMode)
    throws InterruptedException {
    if (interruptMode == THROW_IE)
        throw new InterruptedException();
    else if (interruptMode == REINTERRUPT)
        selfInterrupt();
}
```

**带超时机制的 await**

简单分析下带超时机制的 await 方法 

~~~java
public final boolean await(long time, TimeUnit unit)
        throws InterruptedException {
    // 等待这么多纳秒
    long nanosTimeout = unit.toNanos(time);
    if (Thread.interrupted())
        throw new InterruptedException();
    Node node = addConditionWaiter();
    int savedState = fullyRelease(node);
    // 当前时间 + 等待时长 = 过期时间
    final long deadline = System.nanoTime() + nanosTimeout;
    // 用于返回 await 是否超时
    boolean timedout = false;
    int interruptMode = 0;
    while (!isOnSyncQueue(node)) {
        // 时间到啦
        if (nanosTimeout <= 0L) {
            // 这里因为要 break 取消等待了。取消等待的话一定要调用 transferAfterCancelledWait(node) 这个方法
            // 如果这个方法返回 true，在这个方法内，将节点转移到阻塞队列成功
            // 返回 false 的话，说明 signal 已经发生，signal 方法将节点转移了。也就是说没有超时嘛
            timedout = transferAfterCancelledWait(node);
            break;
        }
        // spinForTimeoutThreshold 的值是 1000 纳秒，也就是 1 毫秒
        // 也就是说，如果不到 1 毫秒了，那就不要选择 parkNanos 了，自旋的性能反而更好
        if (nanosTimeout >= spinForTimeoutThreshold)
            LockSupport.parkNanos(this, nanosTimeout);
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
        // 得到剩余时间
        nanosTimeout = deadline - System.nanoTime();
    }
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null)
        unlinkCancelledWaiters();
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
    return !timedout;
}
//超时的思路还是很简单的:就是调用 parkNanos 方法来休眠指定的时间，醒来后判断是否 signal 调用了，调用了就是没有超时，否则就是超时了。超时的话，自己来进行转移到阻塞队列，然后抢锁。
~~~



相比较synchronize的wait()和notify()/notifAll()的机制而言，Condition具有更高的灵活性，这个很关键。Conditon可以实现多路通知和选择性通知。当使用notify()/notifAll()时，JVM时随机通知线程的，具有很大的不可控性，所以建议使用Condition。Condition使用起来也非常方便，只需要注册到ReentrantLock下面即可。

~~~java
public class MyService {

    // 实例化一个ReentrantLock对象
    private ReentrantLock lock = new ReentrantLock();
    // 为线程A注册一个Condition
    public Condition conditionA = lock.newCondition();
    // 为线程B注册一个Condition
    public Condition conditionB = lock.newCondition();

    public void awaitA() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "进入了awaitA方法");
            long timeBefore = System.currentTimeMillis();
            // 执行conditionA等待
            conditionA.await();
            long timeAfter = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+"被唤醒");
            System.out.println(Thread.currentThread().getName() + "等待了: " + (timeAfter - timeBefore)/1000+"s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void awaitB() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "进入了awaitB方法");
            long timeBefore = System.currentTimeMillis();
            // 执行conditionB等待
            conditionB.await();
            long timeAfter = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+"被唤醒");
            System.out.println(Thread.currentThread().getName() + "等待了: " + (timeAfter - timeBefore)/1000+"s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signallA() {
        try {
            lock.lock();
            System.out.println("启动唤醒程序");
            // 唤醒所有注册conditionA的线程
            conditionA.signalAll();
        } finally {
            lock.unlock();
        }
    }
    
    public void signallB() {
        try {
            lock.lock();
            System.out.println("启动唤醒程序");
            // 唤醒所有注册conditionA的线程
            conditionB.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

================================================
 注意：
 分别实例化了两个Condition对象，都是使用同一个lock注册。注意conditionA对象的等待和唤醒只对使用了conditionA的线程有用，同理conditionB对象的等待和唤醒只对使用了conditionB的线程有用。
~~~



## 3.semaphore (信号量)

Semaphore 是什么呢？它类似一个资源池（可以类比线程池），每个线程需要调用 acquire() 方法获取资源，然后才能执行，执行完后，需要 release 资源，让给其他的线程用。Semaphore 其实也是 AQS 中共享锁的使用，因为所有线程共享一个池。

套路解读：创建 Semaphore 实例的时候，需要一个参数 permits，这个基本上可以确定是设置给 AQS 的 state 的，然后每个线程调用 acquire 的时候，执行 state = state - 1，release 的时候执行 state = state + 1，当然，acquire 的时候，如果 state = 0，说明没有资源了，需要等待其他线程 release。

构造方法：

```java
public Semaphore(int permits) {
    sync = new NonfairSync(permits);
}

public Semaphore(int permits, boolean fair) {
    sync = fair ? new FairSync(permits) : new NonfairSync(permits);
}
```

这里和 ReentrantLock 类似，用了公平策略和非公平策略。

看 acquire 方法：

```java
public void acquire() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
}
public void acquireUninterruptibly() {
    sync.acquireShared(1);
}
public void acquire(int permits) throws InterruptedException {
    if (permits < 0) throw new IllegalArgumentException();
    sync.acquireSharedInterruptibly(permits);
}
public void acquireUninterruptibly(int permits) {
    if (permits < 0) throw new IllegalArgumentException();
    sync.acquireShared(permits);
}
```

看不抛出 InterruptedException 异常的 acquireUninterruptibly() 方法吧：

```java
public void acquireUninterruptibly() {
    sync.acquireShared(1);
}
public final void acquireShared(int arg) {
    if (tryAcquireShared(arg) < 0)
        doAcquireShared(arg);
}
```

Semaphore 分公平策略和非公平策略，我们对比一下两个 tryAcquireShared 方法：

```java
// 公平策略：
protected int tryAcquireShared(int acquires) {
    for (;;) {
        // 区别就在于是不是会先判断是否有线程在排队，然后才进行 CAS 减操作
        if (hasQueuedPredecessors())
            return -1;
        int available = getState();
        int remaining = available - acquires;
        if (remaining < 0 ||
            compareAndSetState(available, remaining))
            return remaining;
    }
}
// 非公平策略：
protected int tryAcquireShared(int acquires) {
    return nonfairTryAcquireShared(acquires);
}
final int nonfairTryAcquireShared(int acquires) {
    for (;;) {
        int available = getState();
        int remaining = available - acquires;
        if (remaining < 0 ||
            compareAndSetState(available, remaining))
            return remaining;
    }
}
```

再回到 acquireShared 方法，

```java
public final void acquireShared(int arg) {
    if (tryAcquireShared(arg) < 0)
        doAcquireShared(arg);
}
```

由于 tryAcquireShared(arg) 返回小于 0 的时候，说明 state 已经小于 0 了（没资源了），此时 acquire 不能立马拿到资源，需要进入到阻塞队列等待：

```java
private void doAcquireShared(int arg) {
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    if (interrupted)
                        selfInterrupt();
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

线程挂起后等待有资源被 release 出来。接下来，我们就要看 release 的方法了：

```java
// 任务介绍，释放一个资源
public void release() {
    sync.releaseShared(1);
}
public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}

protected final boolean tryReleaseShared(int releases) {
    for (;;) {
        int current = getState();
        int next = current + releases;
        // 溢出，当然，我们一般也不会用这么大的数
        if (next < current) // overflow
            throw new Error("Maximum permit count exceeded");
        if (compareAndSetState(current, next))
            return true;
    }
}
```

tryReleaseShared 方法总是会返回 true，然后是 doReleaseShared，这个方法用于唤醒所有的等待线程：

```java
private void doReleaseShared() {
    for (;;) {
        Node h = head;
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            if (ws == Node.SIGNAL) {
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // loop to recheck cases
                unparkSuccessor(h);
            }
            else if (ws == 0 &&
                     !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                continue;                // loop on failed CAS
        }
        if (h == head)                   // loop if head changed
            break;
    }
}
```



Semaphore也叫信号量，在JDK1.5被引入，可以**用来控制同时访问特定资源的线程数量**，通过协调各个线程，以保证合理的使用资源。

Semaphore内部维护了一组虚拟的许可，许可的数量可以通过构造函数的参数指定

- 访问特定资源前，必须使用acquire方法获得许可，如果许可数量为0，该线程则一直阻塞，直到有可用许可。
- 访问资源后，使用release释放许可。

Semaphore管理一系列许可证。

Semaphore和ReentrantLock类似，获取许可有公平策略和非公平许可策略，默认情况下使用非公平策略。

每个acquire方法阻塞，直到有一个许可证可以获得然后拿走一个许可证；每个release方法增加一个许可证，这可能会释放一个阻塞的acquire方法。然而，其实并没有实际的许可证这个对象，Semaphore只是维持了一个可获得许可证的数量。 

### 应用场景 1

Semaphore经常用于限制获取某种资源的线程数量。下面举个例子，比如说操场上有5个跑道，一个跑道一次只能有一个学生在上面跑步，一旦所有跑道在使用，那么后面的学生就需要等待，直到有一个学生不跑了。

~~~java
使用实例：
class SDTask extends Thread
{
	private Semaphore s;
	public SDTask(Semaphore s,String name)
	{
		super(name);
		this.s = s;
	}
	public void run()
	{
		try
		{
			System.out.println(Thread.currentThread().getName()+" 尝试获取3个信号!!!");
			s.acquire(3);
			System.out.println(Thread.currentThread().getName()+" 获取了3个信号!!!");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}finally
		{
			System.out.println(Thread.currentThread().getName()+" 释放了3个信号!!!");
			s.release(3);
		}
	}
}
public class SemaphoreDemo2
{
	public static void main(String[] args)
	{
		Semaphore s = new Semaphore(7);
		for(int i=0; i<3; i++)
		{
			new SDTask(s,"thread"+i).start();;
		}
	}
}

~~~

Semaphore是一个计数信号量，采用的是共享锁的方式来控制

release(int)用来释放信号量，将信号量数量返回给Semaphore

### 应用场景 2

1 Semaphore可以用来做流量分流，特别是对公共资源有限的场景，比如数据库连接。

假设有这个的需求，读取几万个文件的数据到数据库中，由于文件读取是IO密集型任务，可以启动几十个线程并发读取，但是数据库连接数只有10个，这时就必须控制最多只有10个线程能够拿到数据库连接进行操作。这个时候，就可以使用Semaphore做流量控制。

~~~java
public class SemaphoreTest {
    private static final int COUNT = 40;
    private static Executor executor = Executors.newFixedThreadPool(COUNT);
    private static Semaphore semaphore = new Semaphore(10);
    public static void main(String[] args) {
        for (int i=0; i< COUNT; i++) {
            executor.execute(new ThreadTest.Task());
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                //读取文件操作
                semaphore.acquire();
                // 存数据过程
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
~~~



## 4.LockSupport

LockSupport 实际做的是情和Object的wait和notify/notifyAll是类似的。但是wait和notify有个重要的限制是**它们必须放在同步块中执行**，而LockSupport可以单独执行。其次是LockSupport具有较高的灵活性，wait和notify的使用具有顺序性，需要先wait再notify，如果先notify在调用wait就会一直阻塞。 换做LockSupportt就支持线程先调用unpark后，再调用park而不被阻塞。

**总结一下，LockSupport比Object的wait/notify有两大优势**：

①LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。

②unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。



LockSupport 和 CAS 是Java并发包中很多并发工具控制机制的基础，它们底层其实都是依赖Unsafe实现是用来创建锁和其他同步类的基本**线程阻塞**  "原语"。LockSupport的pack挂起线程，unpack唤醒被挂起的线程

 LockSupport 很类似于二元信号量(只有1个许可证可供使用， 信号量(0,1),默认是0.)，如果这个许可还没有被占用，当前线程获取许可并继 续 执行；如果许可已经被占用，当前线 程阻塞，等待获取许可。默认许可是被占用的，所以线程占用了许可能正常运行。

~~~java
LockSupport.park();
运行该代码，可以发现线程一直处于阻塞状态。因为 许可默认是被占用的，信号量默认是0 ，调用park()时获取不到许可，所以进入阻塞状态。
------------------
Thread thread = Thread.currentThread();
     LockSupport.unpark(thread);//释放许可，这里信号量+1 变成1.
     LockSupport.park();// 获取许可，这时信号量是1，所以线程能正常运行，不会阻塞。他会消耗掉一个信号量，这个方法执行后信号量会变成0，所以Locksupport是不支持重入的
     System.out.println("b");
这里主线程是能够正常的运行的

--------------------
public static void t2() throws Exception
{
	Thread t = new Thread(new Runnable()
	{
		public void run()
		{
			.................
		//等待或许许可
			LockSupport.park();
		    System.out.println("thread over." + ......);
		}
	});
	t.start();
	Thread.sleep(2000);
	// 中断线程
	t.interrupt();
	System.out.println("main over");
}

---------------------
最终线程会打印出thread over.true。这说明 线程如果因为调用park而阻塞的话，能够响应中断请求(中断状态被设置成true)，但是不会抛出InterruptedException 。
~~~

**源码分析**

park主要功能：

如果许可存在，那么将这个许可使”用掉“，并且立即返回。如果许可不存在，那么挂起当前线程，直到以下任意一件事情发生：

~~~java
public static void park(Object blocker) {
        //获取当前线程
        Thread t = Thread.currentThread();
        //设置线程的blocker对象
        setBlocker(t, blocker);
        //通过UNSAFE调用，挂起线程
        UNSAFE.park(false, 0L);  // park（） 无参park也是调用的这句代码
        //挂起的线程被唤醒以后，需要将阻塞的Blocker清理掉。
        setBlocker(t, null);
    }
~~~



## 5.ReadWriteLock(读写锁)

ReadWriteLock管理一组锁，一个是只读的锁，一个是写锁。读锁(共享锁)可以在没有写锁的时候被多个线程同时持有，写锁(独占锁)是独占的。 

一个获得了读锁的线程必须能看到前一个释放的写锁所更新的内容。 

读写锁比互斥锁允许对于共享数据更大程度的并发。每次只能有一个写线程，但是同时可以有多个线程并发地读数据。ReadWriteLock适用于读多写少的并发情况。 

~~~java
public class ReadAndWriteLock {
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public void get(Thread thread) {
		lock.readLock().lock();
		try{
			System.out.println("start time:"+System.currentTimeMillis());
			for(int i=0; i<5; i++){
			  。。。。。。。。。。。
				System.out.println(thread.getName() + ":正在进行读操作……");
			}
			System.out.println(thread.getName() + ":读操作完毕！");
			System.out.println("end time:"+System.currentTimeMillis());
		}finally{
			lock.readLock().unlock();
		}
	}
	
	public static void main(String[] args) {
        //开启线程
	}
}
=============================
    验证下读写锁的互斥关系
    public class ReadAndWriteLock {
   ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public static void main(String[] args) {
		final ReadAndWriteLock lock = new ReadAndWriteLock();
         // 建N个线程，同时读
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Runnable() {
			@Override
			public void run() {
				lock.readFile(Thread.currentThread());
			}
		});
		// 建N个线程，同时写
		ExecutorService service1 = Executors.newCachedThreadPool();
		service1.execute(new Runnable() {
			@Override
			public void run() {
				lock.writeFile(Thread.currentThread());
			}
		});
	}
	// 读操作
	public void readFile(Thread thread){
		lock.readLock().lock();
		boolean readLock = lock.isWriteLocked();
		if(!readLock){
			System.out.println("当前为读锁！");
		}
		try{
			for(int i=0; i<5; i++){
	。。。。。。。。。。。。。。。。。。。。。
				System.out.println(thread.getName() + ":正在进行读操作……");
			}
			System.out.println(thread.getName() + ":读操作完毕！");
		}finally{
         System.out.println("释放读锁！");
			lock.readLock().unlock();
		}
	}
	// 写操作
	public void writeFile(Thread thread){
		lock.writeLock().lock();
		boolean writeLock = lock.isWriteLocked();
		if(writeLock){
			System.out.println("当前为写锁！");
		}
		try{
			for(int i=0; i<5; i++){
		。。。。。。。。。。。。。。。。
				System.out.println(thread.getName() + ":正在进行写操作……");
			}
			System.out.println(thread.getName() + ":写操作完毕！");
		}finally{
         System.out.println("释放写锁！");
			lock.writeLock().unlock();
		}
	}
}

~~~



## 6.CountDownLatch(倒数计时器)

**CountDownLatch是什么？**

CountDownLatch也叫闭锁，在JDK1.5被引入，允许一个或多个线程等待其他线程完成操作后再执行。CountDownLatch是一个同步工具类，协调多个线程之间的同步，或者说起到线程之间的通信。

CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。（ReentrantLock +Condition 也可以做到）

CountDownLatch内部会维护一个初始值为线程数量的计数器，主线程执行await方法，如果计数器大于0，则阻塞等待。当一个线程完成任务后，计数器值减1。当计数器为0时，表示所有的线程已经完成任务，等待的主线程被唤醒继续执行。

![img](https://upload-images.jianshu.io/upload_images/2184951-8a570622b8297310.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/353/format/webp)

使用一个计数器进行实现。计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。当计数器的值为0时，表示所有的线程都已经完成了任务，然后在CountDownLatch上等待的线程就可以恢复执行任务。

**CountDownLatch的用法**

~~~java
class Application {
    private CountDownLatch latch;
    public void startUp() throws Exception {
        latch = new CountDownLatch(2); 
        List<Service> services = new ArrayList<>();
        services.add(new DatabaseCheckerService(latch)); //这里对应上图在服务线程中会进行latch.countDown(); 这样最后才能让latch为0唤醒主线程
        services.add(new HealthCheckService(latch));
        Executor executor = Executors.newFixedThreadPool(services.size());
        for (Service service : services) {
            executor.execute(service);
        }
        latch.await(); //这里对应着上图就是主线程阻塞。 
        System.out.println("all service is start up");
    }
}
~~~

CountDownLatch 这个类是比较典型的 AQS 的共享模式的使用，这是一个高频使用的类。latch 的中文意思是**门栓、栅栏**

Doug Lea 在 java doc 中给出的例子，这个例子非常实用，经常会写这个代码

假设我们有 N ( N > 0 ) 个任务，那么我们会用 N 来初始化一个 CountDownLatch，然后将这个 latch 的引用传递到各个线程中，在每个线程完成了任务后，调用 latch.countDown() 代表完成了一个任务。

~~~java
class Driver2 { // ...
    void main() throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e = Executors.newFixedThreadPool(8);

        // 创建 N 个任务，提交给线程池来执行
        for (int i = 0; i < N; ++i) // create and start threads
            e.execute(new WorkerRunnable(doneSignal, i));

        // 等待所有的任务完成，这个方法才会返回
        doneSignal.await();           // wait for all to finish
    }
}

class WorkerRunnable implements Runnable {
    private final CountDownLatch doneSignal;
    private final int i;

    WorkerRunnable(CountDownLatch doneSignal, int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    public void run() {
        try {
            doWork(i);
            // 这个线程的任务完成了，调用 countDown 方法
            doneSignal.countDown();
        } catch (InterruptedException ex) {
        } // return;
    }

    void doWork() { ...}
}
所以说 CountDownLatch 非常实用，我们常常会将一个比较大的任务进行拆分，然后开启多个线程来执行，等所有线程都执行完了以后，再往下执行其他操作。这里例子中，只有 main 线程调用了 await 方法。
~~~

~~~java
这个例子很典型，用了两个 CountDownLatch：
class Driver { // ...
    void main() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; ++i) // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();

        // 这边插入一些代码，确保上面的每个线程先启动起来，才执行下面的代码。
        doSomethingElse();            // don't let run yet
        // 因为这里 N == 1，所以，只要调用一次，那么所有的 await 方法都可以通过
        startSignal.countDown();      // let all threads proceed
        doSomethingElse();
        // 等待所有任务结束
        doneSignal.await();           // wait for all to finish
    }
}

class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    public void run() {
        try {
            // 为了让所有线程同时开始任务，我们让所有线程先阻塞在这里
            // 等大家都准备好了，再打开这个门栓
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException ex) {
        } // return;
    }

    void doWork() { ...}
}

CountDownLatch 无需和ReentrantLock结合使用。
~~~

![5](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-3/5.png)

#### 源码分析

构造方法，需要传入一个不小于 0 的整数：

```java
public CountDownLatch(int count) {
    if (count < 0) throw new IllegalArgumentException("count < 0");
    this.sync = new Sync(count);
}
// 老套路了，内部封装一个 Sync 类继承自 AQS
private static final class Sync extends AbstractQueuedSynchronizer {
    Sync(int count) {
        // 这样就 state == count 了
        setState(count);
    }
    ...
}
先分析套路：AQS 里面的 state 是一个整数值，这边用一个 int count 参数其实初始化就是设置了这个值，所有调用了 await 方法的等待线程会挂起，然后有其他一些线程会做 state = state - 1 操作，当 state 减到 0 的同时，那个线程会负责唤醒调用了 await 方法的所有线程。
```

对于 CountDownLatch，我们仅仅需要关心两个方法，一个是 countDown() 方法，另一个是 await() 方法。

countDown() 方法每次调用都会将 state 减 1，直到 state 的值为 0；而 await 是一个阻塞方法，当 state 减为 0 的时候，await 方法才会返回。await 可以被多个线程调用，所有调用了 await 方法的线程阻塞在 AQS 的阻塞队列中，等待条件满足（state == 0），将线程从队列中一个个唤醒过来。

**以下程序来分析源码，t1 和 t2 负责调用 countDown() 方法，t3 和 t4 调用 await 方法阻塞**：

```java
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {
                }
                // 休息 5 秒后(模拟线程工作了 5 秒)，调用 countDown()
                latch.countDown();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignore) {
                }
                // 休息 10 秒后(模拟线程工作了 10 秒)，调用 countDown()
                latch.countDown();
            }
        }, "t2");

        t1.start();
        t2.start();

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 阻塞，等待 state 减为 0
                    latch.await();
                    System.out.println("线程 t3 从 await 中返回了");
                } catch (InterruptedException e) {
                    System.out.println("线程 t3 await 被中断");
                    Thread.currentThread().interrupt();
                }
            }
        }, "t3");
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 阻塞，等待 state 减为 0
                    latch.await();
                    System.out.println("线程 t4 从 await 中返回了");
                } catch (InterruptedException e) {
                    System.out.println("线程 t4 await 被中断");
                    Thread.currentThread().interrupt();
                }
            }
        }, "t4");

        t3.start();
        t4.start();
    }
}
```

上述程序，大概在过了 10 秒左右的时候，会输出：

```java
线程 t3 从 await 中返回了
线程 t4 从 await 中返回了
// 这两条输出，顺序不是绝对的
// 后面的分析，我们假设 t3 先进入阻塞队列
```

接下来，我们按照流程一步一步走：先 await 等待，然后被唤醒，await 方法返回。

首先，我们来看 await() 方法，它代表线程阻塞，等待 state 的值减为 0。

```java
public void await() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
}
public final void acquireSharedInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    // t3 和 t4 调用 await 的时候，state 都大于 0。
    // 也就是说，这个 if 返回 true，然后往里看
    if (tryAcquireShared(arg) < 0)  
        doAcquireSharedInterruptibly(arg); //t3,t4调用await时，返回的是-1，才能执行这个方法。
}
// 只有当 state == 0 的时候，这个方法才会返回 1
protected int tryAcquireShared(int acquires) {
    return (getState() == 0) ? 1 : -1;
}
```

从方法名我们就可以看出，这个方法是获取共享锁，并且此方法是可中断的（中断的时候抛出 InterruptedException 退出这个方法）。

```java
private void doAcquireSharedInterruptibly(int arg)
    throws InterruptedException {
    // 1. 入队
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
            if (p == head) {
                // 同上，只要 state 不等于 0，那么这个方法返回 -1
                int r = tryAcquireShared(arg);
                if (r >= 0) { //这里表示state=0
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            // 2
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

我们来仔细分析这个方法，线程 t3 经过第 1 步 addWaiter 入队以后，我们应该可以得到这个：

![2](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-3/2.png)

由于 tryAcquireShared 这个方法会返回 -1，所以 if (r >= 0) 这个分支不会进去。到 shouldParkAfterFailedAcquire 的时候，t3 将 head 的 waitStatus 值设置为 -1，如下：

![3](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-3/3.png)

然后进入到 parkAndCheckInterrupt 的时候，t3 挂起。

我们再分析 t4 入队，t4 会将前驱节点 t3 所在节点的 waitStatus 设置为 -1，t4 入队后，应该是这样的：

![4](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-3/4.png)

然后，t4 也挂起。接下来，t3 和 t4 就等待唤醒了。

接下来，我们来看唤醒的流程，我们假设用 10 初始化 CountDownLatch。

![1](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-3/1.png)

当然，我们的例子中，其实没有 10 个线程，只有 2 个线程 t1 和 t2。

我们再一步步看具体的流程。首先，我们看 countDown() 方法:

```java
public void countDown() {
    sync.releaseShared(1);
}
public final boolean releaseShared(int arg) {
    // 只有当 state 减为 0 的时候，tryReleaseShared 才返回 true
    // 否则只是简单的 state = state - 1 那么 countDown 方法就结束了
    if (tryReleaseShared(arg)) {
        // 唤醒 await 的线程
        doReleaseShared();
        return true;
    }
    return false;
}
// 这个方法很简单，用自旋的方法实现 state 减 1
protected boolean tryReleaseShared(int releases) {
    for (;;) {
        int c = getState();
        if (c == 0)
            return false;
        int nextc = c-1;
        if (compareAndSetState(c, nextc))
            return nextc == 0;
    }
}
```

countDown 方法就是每次调用都将 state 值减 1，如果 state 减到 0 了，那么就调用下面的方法进行唤醒阻塞队列中的线程：

```java
// 调用这个方法的时候，state == 0
private void doReleaseShared() {
    for (;;) {
        Node h = head;
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            // t3 入队的时候，已经将头节点的 waitStatus 设置为 Node.SIGNAL（-1） 了
            if (ws == Node.SIGNAL) {
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // CAS失败才会执行continue
                // 就是这里，唤醒 head 的后继节点，也就是阻塞队列中的第一个节点
                // 在这里，也就是唤醒 t3
                unparkSuccessor(h);
            }
            else if (ws == 0 &&
                     !compareAndSetWaitStatus(h, 0, Node.PROPAGATE)) // todo
                continue;                // loop on failed CAS
        }
        if (h == head)                   // loop if head changed
            break;
    }
}
```

一旦 t3 被唤醒后，我们继续回到 await 的这段代码，parkAndCheckInterrupt 返回，我们先不考虑中断的情况：

```java
private void doAcquireSharedInterruptibly(int arg)
    throws InterruptedException {
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r); // 2. 这里是下一步
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                // 1. 唤醒后这个方法返回
                parkAndCheckInterrupt())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

接下来，t3 会进到 setHeadAndPropagate(node, r) 这个方法，先把 head 给占了，然后唤醒队列中其他的线程：

```java
private void setHeadAndPropagate(Node node, int propagate) { //这里propagate是1
    Node h = head; // Record old head for check below
    setHead(node);

    // 下面说的是，唤醒当前 node 之后的节点，即 t3 已经醒了，马上唤醒 t4
    // 类似的，如果 t4 后面还有 t5，那么 t4 醒了以后，马上将 t5 给唤醒了
    if (propagate > 0 || h == null || h.waitStatus < 0 ||
        (h = head) == null || h.waitStatus < 0) {
        Node s = node.next;
        if (s == null || s.isShared())
            // 又是这个方法，只是现在的 head 已经不是原来的空节点了，是 t3 的节点了
            doReleaseShared();  //使用这个个方法递归调用来唤醒阻塞队列中的后继节点
    }
}
```

又回到这个方法了，那么接下来，我们好好分析 doReleaseShared 这个方法，我们根据流程，头节点 head 此时是 t3 节点了：

```java
// 调用这个方法的时候，state == 0
private void doReleaseShared() {
    for (;;) {
        Node h = head;
        // 1. h == null: 说明阻塞队列为空---》3
        // 2. h == tail: 说明头结点可能是刚刚初始化的头节点，
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            // t4 将头节点(此时是 t3)的 waitStatus 设置为 Node.SIGNAL（-1） 了
            if (ws == Node.SIGNAL) {
                // 这里 CAS 失败的场景请看下面的解读
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // loop to recheck cases
                // 就是这里，唤醒 head 的后继节点，也就是阻塞队列中的第一个节点
                // 在这里，也就是唤醒 t4
                unparkSuccessor(h  
            }
            else if (ws == 0 &&
                     // 这个 CAS 失败的场景是：执行到这里的时候，刚好有一个节点入队，入队会将这个 ws 设置为 -1
                     !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                continue;                // loop on failed CAS
        } //----3
        
        // 如果到这里的时候，前面唤醒的线程已经占领了 head，那么再循环
        // 否则，就是 head 没变，那么退出循环，
        // 退出循环是不是意味着阻塞队列中的其他节点就不唤醒了？当然不是，唤醒的线程之后还是会调用这个方法的
        if (h == head)                   // loop if head changed
            break;
    }
}

我们分析下最后一个 if 语句，然后才能解释第一个 CAS 为什么可能会失败：

1. h == head：说明头节点还没有被刚刚用 unparkSuccessor 唤醒的线程（这里可以理解为 t4）占有，此时 break 退出循环。
2. h != head：头节点被刚刚唤醒的线程（这里可以理解为 t4）占有，那么这里重新进入下一轮循环，唤醒下一个节点（这里是 t4 ）。我们知道，等到 t4 被唤醒后，其实是会主动唤醒 t5、t6、t7...，那为什么这里要进行下一个循环来唤醒 t5 呢？我觉得是出于吞吐量的考虑。

满足上面的 2 的场景，那么我们就能知道为什么上面的 CAS 操作 compareAndSetWaitStatus(h, Node.SIGNAL, 0) 会失败了？

因为当前进行 for 循环的线程到这里的时候，可能刚刚唤醒的线程 t4 也刚刚好到这里了，那么就有可能 CAS 失败了。

for 循环第一轮的时候会唤醒 t4，t4 醒后会将自己设置为头节点，如果在 t4 设置头节点后，for 循环才跑到 if (h == head)，那么此时会返回 false，for 循环会进入下一轮。t4 唤醒后也会进入到这个方法里面，那么 for 循环第二轮和 t4 就有可能在这个 CAS 相遇，那么就只会有一个成功了。

```



## 7.CyclicBarrier（循环栅栏）

### CyclicBarrier是什么

字面意思是“**可重复使用的栅栏**”。它是 ReentrantLock 和 Condition 的组合使用。CyclicBarrier 和 CountDownLatch 是不是很像，只是 CyclicBarrier 可以有不止一个栅栏，因为它的栅栏（Barrier）可以重复使用（Cyclic）。

![cyclicbarrier-2](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-3/cyclicbarrier-2.png)

CountDownLatch 基于 AQS 的共享模式的使用，而 CyclicBarrier 基于 Condition 来实现。

基本属性和构造方法：

```java
public class CyclicBarrier {
    // 我们说了，CyclicBarrier 是可以重复使用的，我们把每次从开始使用到穿过栅栏当做"一代"
    private static class Generation {
        boolean broken = false;
    }

    /** The lock for guarding barrier entry */
    private final ReentrantLock lock = new ReentrantLock();
    // CyclicBarrier 是基于 Condition 的
    // Condition 是“条件”的意思，CyclicBarrier 的等待线程通过 barrier 的“条件”是大家都到了栅栏上
    private final Condition trip = lock.newCondition();

    // 参与的线程数
    private final int parties;

    // 如果设置了这个，代表越过栅栏之前，要执行相应的操作
    private final Runnable barrierCommand;

    // 当前所处的“代”
    private Generation generation = new Generation();

    // 还没有到栅栏的线程数，这个值初始为 parties，然后递减
    // 还没有到栅栏的线程数 = parties - 已经到栅栏的数量
    private int count;

    public CyclicBarrier(int parties, Runnable barrierAction) {
        if (parties <= 0) throw new IllegalArgumentException();
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }

    public CyclicBarrier(int parties) {
        this(parties, null);
    }
```

描绘下 CyclicBarrier 里面的一些概念：

![cyclicbarrier-3](https://javadoop.com/blogimages/AbstractQueuedSynchronizer-3/cyclicbarrier-3.png)

看图我们也知道了，CyclicBarrier 的源码最重要的就是 await() 方法了。

首先，先看怎么开启新的一代：

```java
// 开启新的一代，当最后一个线程到达栅栏上的时候，调用这个方法来唤醒其他线程，同时初始化“下一代”
private void nextGeneration() {
    // 首先，需要唤醒所有的在栅栏上等待的线程
    trip.signalAll();
    // 更新 count 的值
    count = parties;
    // 重新生成“新一代”
    generation = new Generation();
}
```

看看怎么打破一个栅栏：

```java
private void breakBarrier() {
    // 设置状态 broken 为 true
    generation.broken = true;
    // 重置 count 为初始值 parties
    count = parties;
    // 唤醒所有已经在等待的线程
    trip.signalAll();
}
```

这两个方法之后用得到，现在开始分析最重要的等待通过栅栏方法 await 方法：

```java
// 不带超时机制
public int await() throws InterruptedException, BrokenBarrierException {
    try {
        return dowait(false, 0L);
    } catch (TimeoutException toe) {
        throw new Error(toe); // cannot happen
    }
}
// 带超时机制，如果超时抛出TimeoutException 异常
public int await(long timeout, TimeUnit unit)
    throws InterruptedException,
           BrokenBarrierException,
           TimeoutException {
    return dowait(true, unit.toNanos(timeout));
}
```

继续往里看：

```java
private int dowait(boolean timed, long nanos)
        throws InterruptedException, BrokenBarrierException,TimeoutException {
    final ReentrantLock lock = this.lock;
    // 先要获取到锁，然后在 finally 中要记得释放锁
    //Condition 部分的话，我们知道 condition 的 await 会释放锁，signal 的时候需要重新获取锁
    lock.lock();
    try {
        final Generation g = generation;
        // 检查栅栏是否被打破，如果被打破，抛出 BrokenBarrierException 异常
        if (g.broken)
            throw new BrokenBarrierException();
        // 检查中断状态，如果中断了，抛出 InterruptedException 异常
        if (Thread.interrupted()) {
            breakBarrier();
            throw new InterruptedException();
        }
        // index 是这个 await 方法的返回值
        // 注意到这里，这个是从 count 递减后得到的值
        int index = --count;

        // 如果等于 0，说明所有的线程都到栅栏上了，准备通过
        if (index == 0) {  // tripped
            boolean ranAction = false;
            try {
                // 如果在初始化的时候，指定了通过栅栏前需要执行的操作，在这里会得到执行
                final Runnable command = barrierCommand;
                if (command != null)
                    command.run();
                // 如果 ranAction 为 true，说明执行 command.run() 的时候，没有发生异常退出的情况
                ranAction = true;
                // 唤醒等待的线程，然后开启新的一代
                nextGeneration();
                return 0;
            } finally {
                if (!ranAction)
                    // 进到这里，说明执行指定操作的时候，发生了异常，那么需要打破栅栏
                    // 之前我们说了，打破栅栏意味着唤醒所有等待的线程，设置 broken 为 true，重置 count 为 parties
                    breakBarrier();
            }
        }

        // loop until tripped, broken, interrupted, or timed out
        // 如果是最后一个线程调用 await，那么上面就返回了，否则之执行下面的代码
        // 下面的操作是给那些不是最后一个到达栅栏的线程执行的
        for (;;) {
            try {
                // 如果带有超时机制，调用带超时的 Condition 的 await 方法等待，直到最后一个线程调用 await
                if (!timed)
                    trip.await();
                else if (nanos > 0L)
                    nanos = trip.awaitNanos(nanos);
            } catch (InterruptedException ie) {
                // 如果到这里，说明等待的线程在 await（是 Condition 的 await）的时候被中断
                if (g == generation && ! g.broken) {
                    // 打破栅栏
                    breakBarrier();
                    // 打破栅栏后，重新抛出这个 InterruptedException 异常给外层调用的方法
                    throw ie;
                } else {
                    // 到这里，说明 g != generation, 说明新的一代已经产生，即最后一个线程 await 执行完成，
                    // 那么此时没有必要再抛出 InterruptedException 异常，记录下来这个中断信息即可
                    // 或者是栅栏已经被打破了，那么也不应该抛出 InterruptedException 异常，
                    // 而是之后抛出 BrokenBarrierException 异常
                    Thread.currentThread().interrupt();
                }
            }

              // 唤醒后，检查栅栏是否是“破的”
            if (g.broken)
                throw new BrokenBarrierException();

            // 这个 for 循环除了异常，就是要从这里退出了
            // 我们要清楚，最后一个线程在执行完指定任务(如果有的话)，会调用 nextGeneration 来开启一个新的代
            // 然后释放掉锁，其他线程从 Condition 的 await 方法中得到锁并返回，然后到这里的时候，其实就会满足 g != generation 的
            // 那什么时候不满足呢？barrierCommand 执行过程中抛出了异常，那么会执行打破栅栏操作，
            // 设置 broken 为true，然后唤醒这些线程。这些线程会从上面的 if (g.broken) 这个分支抛 BrokenBarrierException 异常返回
            // 当然，还有最后一种可能，那就是 await 超时，此种情况不会从上面的 if 分支异常返回，也不会从这里返回，会执行后面的代码
            if (g != generation)
                return index;

            // 如果醒来发现超时了，打破栅栏，抛出异常
            if (timed && nanos <= 0L) {
                breakBarrier();
                throw new TimeoutException();
            }
        }
    } finally {
        lock.unlock();
    }
}
```

看看怎么得到有多少个线程到了栅栏上，处于等待状态：

```java
public int getNumberWaiting() {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        return parties - count;
    } finally {
        lock.unlock();
    }
}
```

判断一个栅栏是否被打破了，这个很简单，直接看 broken 的值即可：

```java
public boolean isBroken() {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        return generation.broken;
    } finally {
        lock.unlock();
    }
}
```

前面我们在说 await 的时候也几乎说清楚了，什么时候栅栏会被打破，总结如下：

1. 中断，我们说了，如果某个等待的线程发生了中断，那么会打破栅栏，同时抛出 InterruptedException 异常；
2. 超时，打破栅栏，同时抛出 TimeoutException 异常；
3. 指定执行的操作抛出了异常，这个我们前面也说过。

最后，我们来看看怎么重置一个栅栏：

```java
public void reset() {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        breakBarrier();   // break the current generation
        nextGeneration(); // start a new generation
    } finally {
        lock.unlock();
    }
}
```

我们设想一下，如果初始化时，指定了线程 parties = 4，前面有 3 个线程调用了 await 等待，在第 4 个线程调用 await 之前，我们调用 reset 方法，那么会发生什么？

首先，打破栅栏，那意味着所有等待的线程（3个等待的线程）会唤醒，await 方法会通过抛出 BrokenBarrierException 异常返回。然后开启新的一代，重置了 count 和 generation，相当于一切归零了。

------



CyclicBarrier也叫同步屏障，在JDK1.5被引入，可以让一组线程达到一个屏障时被阻塞，直到最后一个线程达到屏障时，所以被阻塞的线程才能继续执行。(相当于为一组线程设置一排栅栏，直到所有线程都到了来开放栅栏通道)、

**构造方法**

1. 默认的构造方法是CyclicBarrier(int parties)，其参数表示屏障拦截的线程数量，**每个线程调用await方法告诉CyclicBarrier已经到达屏障位置**，线程被阻塞。
2. 另外一个构造方法CyclicBarrier(int parties, Runnable barrierAction)，其中barrierAction任务会在所有线程到达屏障后执行。



![img](https:////upload-images.jianshu.io/upload_images/2184951-b972911b7debef14.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/566/format/webp)

### 应用场景

~~~java
public class CyclicBarrierDemo {
    public static final int INIT_SIZE = 4;
    private static CyclicBarrier barrier;
    public static void main(String[] args) {
        System.out.println("开启CyclicBarrier屏障（裁判员就位）");
        //初始化CyclicBarrier
        barrier = new CyclicBarrier(INIT_SIZE, new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName()+"线程通知:所有线程都已经准备好了,CyclicBarrier屏障去除（所有运动员都准备完毕，发信号枪）");
            }
        });
        //开启4个线程，充当运动员
        for (int i=0;i<INIT_SIZE;i++){
            new ThreadDemo().start();
        }

    }

    static class ThreadDemo extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"线程准备好了,等待CyclicBarrier屏障去除（一名运动员准备好了）");
                barrier.await(); // 通过调用barrier栅栏的等待方法来停止线程，只有栅栏开放了才接着执行下面的方法
                System.out.println(Thread.currentThread().getName()+"线程继续运行（开始跑）");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}

---------------------
    执行结果：
    开启CyclicBarrier屏障（裁判员就位）
Thread-0线程准备好了,等待CyclicBarrier屏障去除（一名运动员准备好了）
Thread-1线程准备好了,等待CyclicBarrier屏障去除（一名运动员准备好了）
Thread-3线程准备好了,等待CyclicBarrier屏障去除（一名运动员准备好了）
Thread-2线程准备好了,等待CyclicBarrier屏障去除（一名运动员准备好了）
Thread-2线程通知:所有线程都已经准备好了,CyclicBarrier屏障去除（所有运动员都准备完毕，发信号枪）
Thread-2线程继续运行（开始跑）
Thread-1线程继续运行（开始跑）
Thread-0线程继续运行（开始跑）
Thread-3线程继续运行（开始跑）

---------------------


~~~

### CyclicBarrier和CountDownLatch区别

| CountDownLatch                                               | CyclicBarrier                                                |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 减计数方式                                                   | 加计数方式                                                   |
| 计算为0时释放所有等待的线程                                  | 计数达到指定值时释放所有等待线程                             |
| 计数为0时，无法重置                                          | 计数达到指定值时，计数置为0重新开始                          |
| 调用countDown()方法计数减一，调用await()方法只进行阻塞，对计数没任何影响 | 调用await()方法计数加1，若加1后的值不等于构造方法的值，则线程阻塞 |
| 不可重复利用                                                 | 可重复利用                                                   |

**线程在countDown()之后，会继续执行自己的任务，而CyclicBarrier会在所有线程任务结束之后，才会进行后续任务**

# 7 并发容器



java 5.0增加了两种新的容器类型，Queue和BlockingQueue，队列的特点是先进先出，插入和移除操作分别仅能在队列的两端操作。BlockingQueue比Queue多了put、take、offer和poll，他们分别是阻塞插入、阻塞移除、定时阻塞插入和定时阻塞移除。 

事实上，Queue是通过LinkedList实现的（队列的修改操作仅限首尾端，用链表比数组更好），因为它能去掉List的随机访问功能，从而实现了更高效的开发，封装性更好。 

## 1.ConcurrentHashMap

ConcurrentHashMap是HashMap的线程安全版本，ConcurrentSkipListMap是TreeMap的线程安全版本。(hashtable过时)

![ConcurrentMap API](http://www.blogjava.net/images/blogjava_net/xylz/WindowsLiveWriter/JavaConcurrency16part1ConcurrentMap1_10A52/image_thumb_1.png)  除了实现Map接口里面对象的方法外，ConcurrentHashMap还实现了ConcurrentMap里面的四个方法

**V putIfAbsent(K key,V value)**

如果不存在key对应的值，则将key--value加入Map，否则返回key对应的旧值

~~~java
//putIfAbsent()等价于下面的代码
if (!map.containsKey(key)) 
   return map.put(key, value);
else
   return map.get(key);
~~~

**boolean remove(Object key,Object value)**

只有在map中key对应的值是value才能删除对应的key-value	

~~~java
if (map.containsKey(key) && map.get(key).equals(value)) {
   map.remove(key);
   return true;
}
return false;
~~~

**boolean replace(K key,V oldValue,V newValue)**

只有在map中key对应的值是oldValue,才能替换成newValue

~~~java
if (map.containsKey(key) && map.get(key).equals(oldValue)) {
   map.put(key, newValue);
   return true;
}
return false;
~~~

**V replace(K key,V value)**

只有当前键存在的时候更新此键对于的值

~~~java
if (map.containsKey(key)) {
   return map.put(key, value);
}
return null;
~~~

**HashMap原理**

从头设想。要将对象存放在一起，如何设计这个容器。目前只有两条路可以走，一种是采用分格技术（数组的概念），每一个对象存放于一个格子中，这样通过对格子的编号就能取到或者遍历对象；另一种技术就是采用串联的方式（链表的概念），将各个对象串联起来，这需要各个对象至少带有下一个对象的索引（或者指针）。所有的容器的实现其实都是基于这两种方式的，不管是数组还是链表，或者二者俱有。HashMap采用的就是数组的方式。

有了存取对象的容器后还需要以下两个条件才能完成Map所需要的条件。

- 能够快速定位元素：Map的需求就是能够根据一个查询条件尽可能快速得到需要的结果（哈希算法）。
- 能够自动扩充容量：显然对于容器而然，不需要人工的去控制容器的容量是最好的，这样对于外部使用者来说越少知道底部细节越好，不仅使用方便，也越安全。

**HashMap为什么不安全**

HashMap底层维护一个数组，数组中的每一项都是一个Entry

~~~java
transient Entry<K,V>[] table;
~~~

向 HashMap 中所放置的对象实际上是存储在该数组当中； 而Map中的key，value则以Entry的形式存放在数组中

~~~java
static class Entry<K,V> implements Map.Entry<K,V> {
        final K key;
        V value;
        Entry<K,V> next;
        int hash;
~~~

而这个Entry应该放在数组的哪一个位置上（这个位置通常称为位桶或者hash桶，即hash值相同的Entry会放在同一位置，用链表相连），是通过key的hashCode来计算的

通过hash计算出来的值将会使用indexFor方法找到它应该所在的table下标

当两个key通过hashCode计算相同时，则发生了hash冲突(碰撞)，HashMap解决hash冲突的方式是用链表。

当发生hash冲突时，则将存放在数组中的Entry设置为新值的next（这里要注意的是，比如A和B都hash后都映射到下标i中，之前已经有A了，当map.put(B)时，将B放到下标i中，A则为B的next，所以新值存放在数组中，旧值在新值的链表上）

**map.put后的过程：**

当向 HashMap 中 put 一对键值时，它会根据 key的 hashCode 值计算出一个位置， 该位置就是此对象准备往数组中存放的位置。

如果该位置没有对象存在，就将此对象直接放进数组当中；如果该位置已经有对象存在了，则顺着此存在的对象的链开始寻找(为了判断是否值相同，map不允许<key,value>键值对重复)， 如果此链上有对象的话，再去使用 equals方法进行比较，如果对此链上的每个对象的 equals 方法比较都为 false，则将该对象放到数组当中，然后将数组中该位置以前存在的那个对象链接到此对象的后面。

值得注意的是，当key为null时，都放到table[0]中

**为什么说HashMap是线程不安全的**

主要是在resize调用了transfer方法的时候，rehash在同一位置上的节点转移到新table上的时候会从原来的1->2,变成2->1在多线程情况下如此操作链表会导致死循环。

**ConcurrentHashMap**

ConcurrentHashMap是一个经常被使用的数据结构，相比于Hashtable以及Collections.synchronizedMap()，ConcurrentHashMap在线程安全的基础上提供了更好的写并发能力。ConcurrentHashMap比hashMap线程安全，顺着上面的思路如果在resize（）和transfer（）这两个方法调用的时候加上同步不就可以解决

### **ConcurrentHashMap原理分析**

HashTable是一个线程安全的类，它使用synchronized来锁住整张Hash表来实现线程安全，即每次锁住整张表让线程独占。ConcurrentHashMap允许多个修改操作并发进行，其关键在于使用了锁分离技术。它使用了多个锁来控制对hash表的不同部分进行的修改。ConcurrentHashMap内部使用段(Segment)来表示这些不同的部分，每个段其实就是一个小的Hashtable，它们有自己的锁。只要多个修改操作发生在不同的段上，它们就可以并发进行。

有些方法需要跨段，比如size()和containsValue()，它们可能需要锁定整个表而而不仅仅是某个段，这需要按顺序锁定所有段，操作完毕后，又按顺序释放所有段的锁。这里“按顺序”是很重要的，否则极有可能出现死锁。

ConcurrentHashMap使用分段锁技术，将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问，能够实现真正的并发访问。

ConcurrentHashMap内部分为很多个Segment，每一个Segment拥有一把锁，然后每个Segment（继承ReentrantLock）

~~~java
static final class Segment<K,V> extends ReentrantLock implements Serializable
~~~

Segment继承了ReentrantLock，表明每个segment都可以当做一个锁。

Segment下面包含很多个HashEntry列表数组。对于一个key，需要经过三次（为什么要hash三次下文会详细讲解）hash操作，才能最终定位这个元素的位置，这三次hash分别为：

1. 对于一个key，先进行一次hash操作，得到hash值h1，也即h1 = hash1(key)；
2. 将得到的h1的高几位进行第二次hash，得到hash值h2，也即h2 = hash2(h1高几位)，通过h2能够确定该元素的放在哪个Segment；
3. 将得到的h1进行第三次hash，得到hash值h3，也即h3 = hash3(h1)，通过h3能够确定该元素放置在哪个HashEntry。

ConcurrentHashMap中主要实体类就是三个：ConcurrentHashMap（整个Hash表）,Segment（段），HashEntry（节点）

简单理解就是，ConcurrentHashMap 是一个 Segment 数组，Segment 通过继承 ReentrantLock 来进行加锁，所以每次需要加锁的操作锁住的是一个 segment，这样只要保证每个 Segment 是线程安全的，也就实现了全局的线程安全。

![img](https://pic3.zhimg.com/v2-2541e2932c5390dc549a6ea05bfb97a6_b.jpg)

**concurrencyLevel**：(等价于hashmap中的threhold)并行级别、并发数、Segment 数。默认是 16，也就是说 ConcurrentHashMap 有 16 个 Segments，所以理论上，这个时候，最多可以同时支持 16 个线程并发写，只要它们的操作分别分布在不同的 Segment 上。这个值可以在初始化的时候设置为其他值，但是一旦初始化以后，它是不可以扩容的。

具体到每个 Segment 内部，其实每个 Segment 很像之前介绍的 HashMap,Segment 内部是由 **数组+链表** 组成的



## 2 BlockingQueue



  在新增的Concurrent包中，BlockingQueue很好的解决了多线程中，如何高效安全“传输”数据的问题.通过这些高效并且线程安全的队列类，为我们快速搭建高质量的多线程程序带来极大的便利。

阻塞队列，顾名思义，首先它是一个队列，最基本的来说， BlockingQueue 是一个**先进先出**的队列（Queue）。

为什么说BlockingQueue是阻塞的？

阻塞队列（BlockingQueue）**支持**两个附加操作的队列。这两个附加的操作是：在队列为空时，获取元素的线程会等待队列变为非空。当队列满时，存储元素的线程会等待队列可用。阻塞队列常用于生产者和消费者的场景，生产者是往队列里添加元素的线程，消费者是从队列里拿元素的线程。阻塞队列就是生产者存放元素的容器，而消费者也只从容器里拿元素。

![img](https://pic002.cnblogs.com/images/2010/161940/2010112414472791.jpg)

 多线程环境中，通过队列可以很容易实现数据共享，比如经典的“生产者”和“消费者”模型。

在concurrent包发布以前，在多线程环境下的生产消费模型中，如果生产者产出数据的速度大于消费者消费的速度，并且当生产出来的数据累积到一定程度的时候，那么生产者必须暂停等待一下（阻塞生产者线程），以便等待消费者线程把累积的数据处理完毕，反之亦然。程序员都必须去自己控制这些细节，尤其还要兼顾效率和线程安全。BlockingQueue出现后，就无需我们自己来解决这些问题（在多线程领域：所谓阻塞，在某些情况下会挂起线程（即阻塞），一旦条件满足，被挂起的线程又会自动被唤醒）。作为BlockingQueue的使用者，我们再也不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了。

**BlockingQueue的核心方法**：

放入数据：
　　offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,
　　　　则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
　　offer(E o, long timeout, TimeUnit unit),可以设定等待的时间，如果在指定的时间内，还不能加入BlockingQueue，则返回失败。
　　put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断
　　　　直到BlockingQueue里面有空间再继续.
获取数据：
　　poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,
　　　　取不到时返回null;
　　poll(long timeout, TimeUnit unit)：从BlockingQueue取出一个队首的对象，如果在指定时间内，
　　　　队列一旦有数据可取，则立即返回队列中的数据。否则知道时间超时还没有数据可取，返回失败。
　　take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到
　　　　BlockingQueue有新的数据被加入; 
　　drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数）， 
　　　　通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。

| 方法\处理方式 | 抛出异常  | 返回特殊值 | 一直阻塞   | 超时退出           |
| :-----------: | --------- | ---------- | ---------- | ------------------ |
|   插入方法    | add(e)    | offer(e)   | **put(e)** | offer(e,time,unit) |
|   移除方法    | remove()  | poll()     | **take()** | poll(time,unit)    |
|   检查方法    | element() | peek()     | 不可用     | 不可用             |

对于 BlockingQueue，我们的关注点应该在 put(e) 和 take() 这两个方法，因为这两个方法是带阻塞的。

BlockingQueue 不接受 null 值的插入，相应的方法在碰到 null 的插入时会抛出 NullPointerException 异常。null 值在这里通常用于作为特殊值返回（表格中的第三列），代表 poll 失败。所以，如果允许插入 null 值的话，那获取的时候，就不能很好地用 null 来判断到底是代表失败，还是获取的值就是 null 值。

一个 BlockingQueue 可能是有界的，如果在插入的时候，发现队列满了，那么 put 操作将会阻塞。通常，在这里我们说的无界队列也不是说真正的无界，而是它的容量是 Integer.MAX_VALUE。

BlockingQueue 是设计用来实现生产者-消费者队列的，当然，你也可以将它当做普通的 Collection 来用(BlockingQueue 是一个接口，继承自 Queue，所以其实现类也可以作为 Queue 的实现来使用，而 Queue 又继承自 Collection 接口。)

BlockingQueue 的实现都是**线程安全**的，但是批量的集合操作如 `addAll`, `containsAll`, `retainAll` 和 `removeAll` 不一定是原子操作。如 addAll(c) 有可能在添加了一些元素后中途抛出异常，此时 BlockingQueue 中已经添加了部分元素，这个是允许的，取决于具体的实现。

最后，BlockingQueue 在生产者-消费者的场景中，是支持多消费者和多生产者的，说的其实就是线程安全问题。

###  ArrayBlockingQueue

 ArrayBlockingQueue 是 BlockingQueue 接口的有界队列实现类，底层采用数组来实现。、

其并发控制采用可重入锁来控制，不管是插入操作还是读取操作，都需要获取到锁才能进行操作。

它采用一个 ReentrantLock 和相应的两个 Condition 来实现。

ArrayBlockingQueue 共有以下几个属性：

~~~java
// 用于存放元素的数组
final Object[] items;
// 下一次读取操作的位置
int takeIndex;
// 下一次写入操作的位置
int putIndex;
// 队列中的元素数量
int count;

// 以下几个就是控制并发用的同步器
final ReentrantLock lock;
private final Condition notEmpty;
private final Condition notFull;
~~~

![array-blocking-queue](https://javadoop.com/blogimages/java-concurrent-queue/array-blocking-queue.png)

ArrayBlockingQueue 实现并发同步的原理就是，读操作和写操作都需要获取到 AQS 独占锁才能进行操作(这样才能保证并发的安全性)。

如果队列为空，这个时候读操作的线程进入到**读线程队列***==排队==*，等待写线程写入新的元素，然后唤醒读线程队列的第一个等待线程。

如果队列已满，这个时候写操作的线程进入到**写线程队列**==排队==，等待读线程将队列元素移除腾出空间，然后唤醒写线程队列的第一个等待线程。

对于 ArrayBlockingQueue，我们可以在构造的时候指定以下三个参数：

1. 队列容量，其限制了队列中最多允许的元素个数；
2. 指定独占锁是公平锁还是非公平锁。非公平锁的吞吐量比较高，公平锁可以保证每次都是等待最久的线程获取到锁；
3. 可以指定用一个集合来初始化，将此集合中的元素在构造方法期间就先添加到队列中。

###  LinkedBlockingQueue

底层基于单向链表实现的阻塞队列，可以当做无界队列也可以当做有界队列来使用

```java
// 传说中的无界队列
public LinkedBlockingQueue() {
    this(Integer.MAX_VALUE);
}
// 传说中的有界队列
public LinkedBlockingQueue(int capacity) {
    if (capacity <= 0) throw new IllegalArgumentException();
    this.capacity = capacity; //capacity意思是容量
    last = head = new Node<E>(null);
}
```

这个类属性：

~~~java
// 队列容量
private final int capacity;
// 队列中的元素数量
private final AtomicInteger count = new AtomicInteger(0);
// 队头
private transient Node<E> head;
// 队尾
private transient Node<E> last;

// take, poll(take,poll 移除), peek 等读操作的方法需要获取到这个锁
private final ReentrantLock takeLock = new ReentrantLock();

// 如果读操作的时候队列是空的，那么等待 notEmpty 条件
private final Condition notEmpty = takeLock.newCondition();

// put, offer 等写操作的方法需要获取到这个锁
private final ReentrantLock putLock = new ReentrantLock();

// 如果写操作的时候队列是满的，那么等待 notFull 条件
private final Condition notFull = putLock.newCondition();
~~~

**takeLock 和 notEmpty 怎么搭配：**如果要获取（take）一个元素，需要获取 takeLock 锁，但是获取了锁还不够，如果队列此时为空，还需要队列不为空（notEmpty）这个条件（Condition）。

**putLock 需要和 notFull 搭配：**如果要插入（put）一个元素，需要获取 putLock 锁，但是获取了锁还不够，如果队列此时已满，还需要队列不是满的（notFull）这个条件（Condition）。

![linked-blocking-queue](https://javadoop.com/blogimages/java-concurrent-queue/linked-blocking-queue.png)

读操作是排好队的，写操作也是排好队的，唯一的并发问题在于一个写操作和一个读操作同时进行

构造方法：

~~~java
public LinkedBlockingQueue(int capacity) {
    if (capacity <= 0) throw new IllegalArgumentException();
    this.capacity = capacity;
    last = head = new Node<E>(null); //整个队列初始化时都执行一个属性为null的新节点。
}
这里会初始化一个空的头结点，那么第一个元素入队的时候，队列中就会有两个元素。读取元素时，也总是获取头节点后面的一个节点。count 的计数值不包括这个头节点。
~~~

put 方法是怎么将元素插入到队尾的：

```java
public void put(E e) throws InterruptedException {
    if (e == null) throw new NullPointerException();
    // 这里为什么是 -1，可以看看 offer 方法。这就是个标识成功、失败的标志而已。
    int c = -1;
    Node<E> node = new Node(e);
    final ReentrantLock putLock = this.putLock;
    final AtomicInteger count = this.count;
    // 必须要获取到 putLock 才可以进行插入操作
    putLock.lockInterruptibly();  //如果加锁不成功会进行中断
    try {
        // 如果队列满，等待 notFull 的条件满足。
        while (count.get() == capacity) {
            notFull.await();
        }
        // 入队
        enqueue(node);
        // count 原子加 1，c 还是加 1 前的值
        c = count.getAndIncrement();
        // 如果这个元素入队后，还有至少一个槽可以使用，调用 notFull.signal() 唤醒等待线程
        if (c + 1 < capacity)
            notFull.signal();
    } finally {
        // 入队后，释放掉 putLock
        putLock.unlock();
    }
    // 如果 c == 0，那么代表队列在这个元素入队前是空的（不包括head空节点），
    // 那么所有的读线程都在等待 notEmpty 这个条件，等待唤醒，这里做一次唤醒操作
    if (c == 0)
        signalNotEmpty();
}

// 入队的代码非常简单，就是将 last 属性指向这个新元素，并且让原队尾的 next 指向这个元素
// 这里入队没有并发问题，因为只有获取到 putLock 独占锁以后，才可以进行此操作
private void enqueue(Node<E> node) {
    // assert putLock.isHeldByCurrentThread();
    // assert last.next == null;
    last = last.next = node;
}

// 元素入队后，如果需要，调用这个方法唤醒读线程来读
private void signalNotEmpty() {
    final ReentrantLock takeLock = this.takeLock;
    takeLock.lock();
    try {
        notEmpty.signal();
    } finally {
        takeLock.unlock();
    }
}
```

我们再看看 take (从队列中读出一个元素)方法：

```java
public E take() throws InterruptedException {
    E x;
    int c = -1;
    final AtomicInteger count = this.count;
    final ReentrantLock takeLock = this.takeLock;
    // 首先，需要获取到 takeLock 才能进行出队操作
    takeLock.lockInterruptibly();
    try {
        // 如果队列为空，等待 notEmpty 这个条件满足再继续执行
        while (count.get() == 0) {
            notEmpty.await();
        }
        // 出队
        x = dequeue();
        // count 进行原子减 1
        c = count.getAndDecrement();
        // 如果这次出队后，队列中至少还有一个元素，那么调用 notEmpty.signal() 唤醒其他的读线程
        if (c > 1)
            notEmpty.signal();
    } finally {
        // 出队后释放掉 takeLock
        takeLock.unlock();
    }
    // 如果 c == capacity，那么说明在这个 take 方法发生的时候，队列是满的
    // 既然出队了一个，那么意味着队列不满了，唤醒写线程去写
    if (c == capacity)
        signalNotFull();
    return x;
}
// 取队头，出队
private E dequeue() {
    // assert takeLock.isHeldByCurrentThread();
    // assert head.item == null;
    // 之前说了，头结点是空的
    Node<E> h = head;
    Node<E> first = h.next;
    h.next = h; // help GC
    // 设置这个为新的头结点
    head = first;
    E x = first.item;
    first.item = null;
    return x;
}
// 元素出队后，如果需要，调用这个方法唤醒写线程来写
private void signalNotFull() {
    final ReentrantLock putLock = this.putLock;
    putLock.lock();
    try {
        notFull.signal();
    } finally {
        putLock.unlock();
    }
}
```

###  SynchronousQueue

它是一个特殊的队列，它的名字其实就蕴含了它的特征 - - 同步的队列。为什么说是同步的呢？这里说的并不是多线程的并发问题，而是因为当一个线程往队列中写入一个元素时，写入操作不会立即返回，需要等待另一个线程来将这个元素拿走；同理，当一个读线程做读操作的时候，同样需要一个相匹配的写线程的写操作。这里的 Synchronous 指的就是读线程和写线程需要同步，一个读线程匹配一个写线程。

较少使用到 SynchronousQueue 这个类，不过它在线程池的实现类 ScheduledThreadPoolExecutor 中得到了应用。

SynchronousQueue 的队列其实是虚的，其不提供任何空间（一个都没有）来存储元素。数据必须从某个写线程交给某个读线程，而不是写到某个队列中等待被消费。

```java
// 构造时，我们可以指定公平模式还是非公平模式，区别之后再说
public SynchronousQueue(boolean fair) {
    transferer = fair ? new TransferQueue() : new TransferStack();
}
abstract static class Transferer {
    // 从方法名上大概就知道，这个方法用于转移元素，从生产者手上转到消费者手上
    // 也可以被动地，消费者调用这个方法来从生产者手上取元素
    // 第一个参数 e 如果不是 null，代表场景为：将元素从生产者转移给消费者
    // 如果是 null，代表消费者等待生产者提供元素，然后返回值就是相应的生产者提供的元素
    // 第二个参数代表是否设置超时，如果设置超时，超时时间是第三个参数的值
    // 返回值如果是 null，代表超时，或者中断。具体是哪个，可以通过检测中断状态得到。
    abstract Object transfer(Object e, boolean timed, long nanos);
}
```

Transferer 有两个内部实现类，是因为构造 SynchronousQueue 的时候，我们可以指定公平策略。公平模式意味着，所有的读写线程都遵守先来后到，FIFO 嘛，对应 TransferQueue。而非公平模式则对应 TransferStack。

```java
// 构造时，我们可以指定公平模式还是非公平模式，区别之后再说
public SynchronousQueue(boolean fair) {
    transferer = fair ? new TransferQueue() : new TransferStack();
}
abstract static class Transferer {
    // 从方法名上大概就知道，这个方法用于转移元素，从生产者手上转到消费者手上
    // 也可以被动地，消费者调用这个方法来从生产者手上取元素
    // 第一个参数 e 如果不是 null，代表场景为：将元素从生产者转移给消费者
    // 如果是 null，代表消费者等待生产者提供元素，然后返回值就是相应的生产者提供的元素
    // 第二个参数代表是否设置超时，如果设置超时，超时时间是第三个参数的值
    // 返回值如果是 null，代表超时，或者中断。具体是哪个，可以通过检测中断状态得到。
    abstract Object transfer(Object e, boolean timed, long nanos);
}
```

Transferer 有两个内部实现类，是因为构造 SynchronousQueue 的时候，我们可以指定公平策略。公平模式意味着，所有的读写线程都遵守先来后到，FIFO 嘛，对应 TransferQueue。而非公平模式则对应 TransferStack。

![synchronous-queue](https://javadoop.com/blogimages/java-concurrent-queue/synchronous-queue.png)

 公平模式分析源码

看看 put 方法和 take 方法：

```java
// 写入值
public void put(E o) throws InterruptedException {
    if (o == null) throw new NullPointerException();
    if (transferer.transfer(o, false, 0) == null) { // 1
        Thread.interrupted();
        throw new InterruptedException();
    }
}
// 读取值并移除
public E take() throws InterruptedException {
    Object e = transferer.transfer(null, false, 0); // 2
    if (e != null)
        return (E)e;
    Thread.interrupted();
    throw new InterruptedException();
}
写操作 put(E o) 和读操作 take() 都是调用 Transferer.transfer(…) 方法，区别在于第一个参数是否为 null 值。

transfer 的设计思路，其基本算法如下：

1. 当调用这个方法时，如果队列是空的，或者队列中的节点和当前的线程操作类型一致（如当前操作是 put 操作，而队列中的元素也都是写线程）。将当前线程加入到等待队列即可。
2. 如果队列中有等待节点，而且与当前操作可以匹配（如队列中都是读操作线程，当前线程是写操作线程，反之亦然）。这种情况下，匹配等待队列的队头，出队，返回相应数据。

其实这里有个隐含的条件被满足了，队列如果不为空，肯定都是同种类型的节点，要么都是读操作，要么都是写操作。
```

我们可以假设出一个男女配对的场景：一个男的过来，如果一个人都没有，那么他需要等待；如果发现有一堆男的在等待，那么他需要排到队列后面；如果发现是一堆女的在排队，那么他直接牵走队头的那个女的。

既然这里说到了等待队列，我们先看看其实现，也就是 QNode:

```java
static final class QNode {
    volatile QNode next;          // 可以看出来，等待队列是单向链表
    volatile Object item;         // CAS'ed to or from null ，节点中操作的对象
    volatile Thread waiter;       // 将线程对象保存在这里，用于挂起和唤醒
    final boolean isData;         // 用于判断是写线程节点(isData == true)，还是读线程节点

    QNode(Object item, boolean isData) {
        this.item = item;
        this.isData = isData;
    }
  ......
```



```java
/**
 * Puts or takes an item.
 */
Object transfer(Object e, boolean timed, long nanos) {

    QNode s = null; // constructed(构造)/reused as needed
    boolean isData = (e != null); // isData=true表示是写线程，反之是读线程

    for (;;) {
        QNode t = tail;
        QNode h = head;
        if (t == null || h == null)         // saw uninitialized value
            continue;                       // spin

        // 队列空，或队列中节点类型和当前节点一致，
        // 即我们说的第一种情况，将节点入队即可.要想着这块 if 里面方法其实就是入队
        if (h == t || t.isData == isData) { // empty or same-mode
             QNode tn = t.next;
            // t != tail 说明刚刚有节点入队，continue 即可
            if (t != tail)                  // inconsistent read
                continue;
            // 有其他节点入队，但是 tail 还是指向原来的，此时设置 tail 即可
            if (tn != null) {               // lagging tail
                // 这个方法就是：如果 tail 此时为 t 的话，设置为 tn
                advanceTail(t, tn);
                continue;
            }
            // 
            if (timed && nanos <= 0)        // can't wait
                return null;
            if (s == null)
                s = new QNode(e, isData);
            // 将当前节点，插入到 tail 的后面
            if (!t.casNext(null, s))        // failed to link in
                continue;

            // 将当前节点设置为新的 tail
            advanceTail(t, s);              // swing tail and wait
            // 看到这里，请读者先往下滑到这个方法，看完了以后再回来这里，思路也就不会断了
            Object x = awaitFulfill(s, e, timed, nanos);
            // 到这里，说明之前入队的线程被唤醒了，准备往下执行
            if (x == s) {                   // wait was cancelled
                clean(t, s);
                return null;
            }

            if (!s.isOffList()) {           // not already unlinked
                advanceHead(t, s);          // unlink if head
                if (x != null)              // and forget fields
                    s.item = s;
                s.waiter = null;
            }
            return (x != null) ? x : e;

        // 这里的 else 分支就是上面说的第二种情况，有相应的读或写相匹配的情况
        } else {                            // complementary-mode
            QNode m = h.next;               // node to fulfill
            if (t != tail || m == null || h != head)
                continue;                   // inconsistent read

            Object x = m.item;
            if (isData == (x != null) ||    // m already fulfilled
                x == m ||                   // m cancelled
                !m.casItem(x, e)) {         // lost CAS
                advanceHead(h, m);          // dequeue and retry
                continue;
            }

            advanceHead(h, m);              // successfully fulfilled
            LockSupport.unpark(m.waiter);
            return (x != null) ? x : e;
        }
    }
}

void advanceTail(QNode t, QNode nt) {
    if (tail == t)
        UNSAFE.compareAndSwapObject(this, tailOffset, t, nt);
}
// 自旋或阻塞，直到满足条件，这个方法返回
Object awaitFulfill(QNode s, Object e, boolean timed, long nanos) {

    long lastTime = timed ? System.nanoTime() : 0;
    Thread w = Thread.currentThread();
    // 判断需要自旋的次数，
    int spins = ((head.next == s) ?
                 (timed ? maxTimedSpins : maxUntimedSpins) : 0);
    for (;;) {
        // 如果被中断了，那么取消这个节点
        if (w.isInterrupted())
            // 就是将当前节点 s 中的 item 属性设置为 this
            s.tryCancel(e);
        Object x = s.item;
        // 这里是这个方法的唯一的出口
        if (x != e)
            return x;
        // 如果需要，检测是否超时
        if (timed) {
            long now = System.nanoTime();
            nanos -= now - lastTime;
            lastTime = now;
            if (nanos <= 0) {
                s.tryCancel(e);
                continue;
            }
        }
        if (spins > 0)
            --spins;
        // 如果自旋达到了最大的次数，那么检测
        else if (s.waiter == null)
            s.waiter = w;
        // 如果自旋到了最大的次数，那么线程挂起，等待唤醒
        else if (!timed)
            LockSupport.park(this);
        // spinForTimeoutThreshold 这个之前讲 AQS 的时候其实也说过，剩余时间小于这个阈值的时候，就
        // 不要进行挂起了，自旋的性能会比较好
        else if (nanos > spinForTimeoutThreshold)
            LockSupport.parkNanos(this, nanos);
    }
}
```

### PriorityBlockingQueue (优先级阻塞队列)

带排序的 BlockingQueue 实现，其并发控制采用的是 ReentrantLock，队列为无界队列（ArrayBlockingQueue 是有界队列，LinkedBlockingQueue 也可以通过在构造函数中传入 capacity 指定队列最大的容量，但是 PriorityBlockingQueue 只能指定初始的队列大小，后面插入元素的时候，如果空间不够的话会自动扩容）。

简单地说，它就是 PriorityQueue 的线程安全版本。不可以插入 null 值，同时，插入队列的对象必须是可比较大小的（comparable），否则报 ClassCastException 异常。

其核心源码部分。

属性：

```java
// 构造方法中，如果不指定大小的话，默认大小为 11
private static final int DEFAULT_INITIAL_CAPACITY = 11;
// 数组的最大容量
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

// 这个就是存放数据的数组
private transient Object[] queue;

// 队列当前大小
private transient int size;

// 大小比较器，如果按照自然序排序，那么此属性可设置为 null
private transient Comparator<? super E> comparator;

// 并发控制所用的锁，所有的 public 且涉及到线程安全的方法，都必须先获取到这个锁
private final ReentrantLock lock;

// 这个很好理解，其实例由上面的 lock 属性创建
private final Condition notEmpty;

// 这个也是用于锁，用于数组扩容的时候，需要先获取到这个锁，才能进行扩容操作
// 其使用 CAS 操作
private transient volatile int allocationSpinLock;

// 用于序列化和反序列化的时候用，对于 PriorityBlockingQueue 我们应该比较少使用到序列化
private PriorityQueue q;
```

此类实现了 Collection 和 Iterator 接口中的所有接口方法，对其对象进行迭代并遍历时，不能保证有序性。如果你想要实现有序遍历，建议采用 Arrays.sort(queue.toArray()) 进行处理。

PriorityBlockingQueue 提供了 drainTo 方法用于将部分或全部元素有序地填充（准确说是转移，会删除原队列中的元素）到另一个集合中。

PriorityBlockingQueue 使用了基于数组的**二叉堆**来存放元素，所有的 public 方法采用同一个 lock 进行并发控制。

二叉堆：一颗完全二叉树，它非常适合用数组进行存储，对于数组中的元素 a[i]，其左子节点为 a[2*i+1]，其右子节点为 a[2\*i + 2]，其父节点为 a[(i-1)/2]，其堆序性质为，每个节点的值都小于其左右子节点的值。二叉堆中最小的值就是根节点，但是删除根节点是比较麻烦的，因为需要调整树。

图解释一下二叉堆，最小的元素一定是根元素，它是一棵满的树，除了最后一层，最后一层的节点从左到右紧密排列。

![priority-blocking-queue-1](https://javadoop.com/blogimages/java-concurrent-queue/priority-blocking-queue-1.png)

 PriorityBlockingQueue 的源码分析，构造方法:

```java
// 默认构造方法，采用默认值(11)来进行初始化
public PriorityBlockingQueue() {
    this(DEFAULT_INITIAL_CAPACITY, null);
}
// 指定数组的初始大小
public PriorityBlockingQueue(int initialCapacity) {
    this(initialCapacity, null);
}
// 指定比较器
public PriorityBlockingQueue(int initialCapacity,
                             Comparator<? super E> comparator) {
    if (initialCapacity < 1)
        throw new IllegalArgumentException();
    this.lock = new ReentrantLock();
    this.notEmpty = lock.newCondition();
    this.comparator = comparator;
    this.queue = new Object[initialCapacity];
}
// 在构造方法中就先填充指定的集合中的元素
public PriorityBlockingQueue(Collection<? extends E> c) {
    this.lock = new ReentrantLock();
    this.notEmpty = lock.newCondition();
    // 
    boolean heapify = true; // true if not known to be in heap order
    boolean screen = true;  // true if must screen for nulls
    if (c instanceof SortedSet<?>) {
        SortedSet<? extends E> ss = (SortedSet<? extends E>) c;
        this.comparator = (Comparator<? super E>) ss.comparator();
        heapify = false;
    }
    else if (c instanceof PriorityBlockingQueue<?>) {
        PriorityBlockingQueue<? extends E> pq =
            (PriorityBlockingQueue<? extends E>) c;
        this.comparator = (Comparator<? super E>) pq.comparator();
        screen = false;
        if (pq.getClass() == PriorityBlockingQueue.class) // exact match
            heapify = false;
    }
    Object[] a = c.toArray();
    int n = a.length;
    // If c.toArray incorrectly doesn't return Object[], copy it.
    if (a.getClass() != Object[].class)
        a = Arrays.copyOf(a, n, Object[].class);
    if (screen && (n == 1 || this.comparator != null)) {
        for (int i = 0; i < n; ++i)
            if (a[i] == null)
                throw new NullPointerException();
    }
    this.queue = a;
    this.size = n;
    if (heapify)
        heapify();
}
```

内部的自动扩容实现：

```java
private void tryGrow(Object[] array, int oldCap) {
    // 这边做了释放锁的操作
    lock.unlock(); // must release and then re-acquire main lock
    Object[] newArray = null;
    // 用 CAS 操作将 allocationSpinLock 由 0 变为 1，也算是获取锁
    if (allocationSpinLock == 0 &&
        UNSAFE.compareAndSwapInt(this, allocationSpinLockOffset,
                                 0, 1)) {
        try {
            // 如果节点个数小于 64，那么增加的 oldCap + 2 的容量
            // 如果节点数大于等于 64，那么增加 oldCap 的一半
            // 所以节点数较小时，增长得快一些
            int newCap = oldCap + ((oldCap < 64) ?
                                   (oldCap + 2) :
                                   (oldCap >> 1));
            // 这里有可能溢出
            if (newCap - MAX_ARRAY_SIZE > 0) {    // possible overflow
                int minCap = oldCap + 1;
                if (minCap < 0 || minCap > MAX_ARRAY_SIZE)
                    throw new OutOfMemoryError();
                newCap = MAX_ARRAY_SIZE;
            }
            // 如果 queue != array，那么说明有其他线程给 queue 分配了其他的空间
            if (newCap > oldCap && queue == array)
                // 分配一个新的大数组
                newArray = new Object[newCap];
        } finally {
            // 重置，也就是释放锁
            allocationSpinLock = 0;
        }
    }
    // 如果有其他的线程也在做扩容的操作
    if (newArray == null) // back off if another thread is allocating
        Thread.yield();
    // 重新获取锁
    lock.lock();
    // 将原来数组中的元素复制到新分配的大数组中
    if (newArray != null && queue == array) {
        queue = newArray;
        System.arraycopy(array, 0, newArray, 0, oldCap);
    }
}
```

扩容方法对并发的控制也非常的巧妙，释放了原来的独占锁 lock，这样的话，扩容操作和读操作可以同时进行，提高吞吐量。

写操作 put 方法和读操作 take 方法。

```java
public void put(E e) {
    // 直接调用 offer 方法，因为前面我们也说了，在这里，put 方法不会阻塞
    offer(e); 
}
public boolean offer(E e) {
    if (e == null)
        throw new NullPointerException();
    final ReentrantLock lock = this.lock;
    // 首先获取到独占锁
    lock.lock();
    int n, cap;
    Object[] array;
    // 如果当前队列中的元素个数 >= 数组的大小，那么需要扩容了
    while ((n = size) >= (cap = (array = queue).length))
        tryGrow(array, cap);
    try {
        Comparator<? super E> cmp = comparator;
        // 节点添加到二叉堆中
        if (cmp == null)
            siftUpComparable(n, e, array);
        else
            siftUpUsingComparator(n, e, array, cmp);
        // 更新 size
        size = n + 1;
        // 唤醒等待的读线程
        notEmpty.signal();
    } finally {
        lock.unlock();
    }
    return true;
}
```

对于二叉堆而言，插入一个节点是简单的，插入的节点如果比父节点小，交换它们，然后继续和父节点比较。

```java
// 这个方法就是将数据 x 插入到数组 array 的位置 k 处，然后再调整树
private static <T> void siftUpComparable(int k, T x, Object[] array) {
    Comparable<? super T> key = (Comparable<? super T>) x;
    while (k > 0) {
        // 二叉堆中 a[k] 节点的父节点位置
        int parent = (k - 1) >>> 1;
        Object e = array[parent];
        if (key.compareTo((T) e) >= 0)
            break;
        array[k] = e;
        k = parent;
    }
    array[k] = key;
}
```

我们用图来示意一下，我们接下来要将 **11** 插入到队列中，看看 siftUp 是怎么操作的。

![priority-blocking-queue-2](https://javadoop.com/blogimages/java-concurrent-queue/priority-blocking-queue-2.png)

我们再看看 take 方法：

```java
public E take() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    // 独占锁
    lock.lockInterruptibly();
    E result;
    try {
        // dequeue 出队
        while ( (result = dequeue()) == null)
            notEmpty.await();
    } finally {
        lock.unlock();
    }
    return result;
}
private E dequeue() {
    int n = size - 1;
    if (n < 0)
        return null;
    else {
        Object[] array = queue;
        // 队头，用于返回
        E result = (E) array[0];
        // 队尾元素先取出
        E x = (E) array[n];
        // 队尾置空
        array[n] = null;
        Comparator<? super E> cmp = comparator;
        if (cmp == null)
            siftDownComparable(0, x, array, n);
        else
            siftDownUsingComparator(0, x, array, n, cmp);
        size = n;
        return result;
    }
}
```

dequeue 方法返回队头，并调整二叉堆的树，调用这个方法必须先获取独占锁。

废话不多说，出队是非常简单的，因为队头就是最小的元素，对应的是数组的第一个元素。难点是队头出队后，需要调整树。

```java
private static <T> void siftDownComparable(int k, T x, Object[] array,
                                           int n) {
    if (n > 0) {
        Comparable<? super T> key = (Comparable<? super T>)x;
        // 这里得到的 half 肯定是非叶节点
        // a[n] 是最后一个元素，其父节点是 a[(n-1)/2]。所以 n >>> 1 代表的节点肯定不是叶子节点
        // 下面，我们结合图来一行行分析，这样比较直观简单
        // 此时 k 为 0, x 为 17，n 为 9
        int half = n >>> 1; // 得到 half = 4
        while (k < half) {
            // 先取左子节点
            int child = (k << 1) + 1; // 得到 child = 1
            Object c = array[child];  // c = 12
            int right = child + 1;  // right = 2
            // 如果右子节点存在，而且比左子节点小
            // 此时 array[right] = 20，所以条件不满足
            if (right < n &&
                ((Comparable<? super T>) c).compareTo((T) array[right]) > 0)
                c = array[child = right];
            // key = 17, c = 12，所以条件不满足
            if (key.compareTo((T) c) <= 0)
                break;
            // 把 12 填充到根节点
            array[k] = c;
            // k 赋值后为 1
            k = child;
            // 一轮过后，我们发现，12 左边的子树和刚刚的差不多，都是缺少根节点，接下来处理就简单了
        }
        array[k] = key;
    }
}
```

![priority-blocking-queue-3](https://javadoop.com/blogimages/java-concurrent-queue/priority-blocking-queue-3.png)

记住二叉堆是一棵完全二叉树，那么根节点 10 拿掉后，最后面的元素 17 必须找到合适的地方放置。首先，17 和 10 不能直接交换，那么先将根节点 10 的左右子节点中较小的节点往上滑，即 12 往上滑，然后原来 12 留下了一个空节点，然后再把这个空节点的较小的子节点往上滑，即 13 往上滑，最后，留出了位子，17 补上即可。

我稍微调整下这个树，以便读者能更明白：

![priority-blocking-queue-4](https://javadoop.com/blogimages/java-concurrent-queue/priority-blocking-queue-4.png)

**总结**

ArrayBlockingQueue 底层是数组，有界队列，如果我们要使用生产者-消费者模式，这是非常好的选择。

LinkedBlockingQueue 底层是链表，可以当做无界和有界队列来使用，不要以为它就是无界队列。

SynchronousQueue 本身不带有空间来存储任何元素，使用上可以选择公平模式和非公平模式。

PriorityBlockingQueue 是无界队列，基于数组，数据结构为二叉堆，数组第一个也是树的根节点总是最小值。

## 3ConcurrentLinkQueue

```

顾名思义： 同步链表队列，一个基于链接节点的无界线程安全队列。此队列按照 FIFO（先进先出）原则对元素进行排序。队列的头部 是队列中时间最长的元素。队列的尾部 是队列中时间最短的元素。
新的元素插入到队列的尾部，队列获取操作从队列头部获得元素。当多个线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。此队列不允许使用 null 元素。
```

















