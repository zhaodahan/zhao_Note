# java数据结构与算法



数据结构与算法是程序的灵魂，是效率的保证。

程序 = 算法+ 数据结构 

```
数据结构是数据在内存中的存储方式，数据结构是算法的基石。数据结构偏向于硬件底层。
而算法则是偏向的是逻辑。而算法则是处理数据的思路。
```

要学习好数据结构和算法就要多多考虑如何将生活中遇到的问题,用程序去实现解决。

我们在遇到一些比较困难的问题，或这是需要对程序在时间和空间上做优化，需要结合数据结构和算法，综合处理才能办到。

![DATA_STRUCTURES_ALGORITHMS1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS1.png?raw=true)

# 数据结构

数据结构： 在内存中存储数据的方式。

线性： 数据元素之间的关系是一对一。

非线性： 数据元素之间的关系是一对多，多对多。

硬件底层的数据存储方式只有两种： 数组， 链表。 所有的数据结构都是在这两个基础上进行封装。

![DATA_STRUCTURES_ALGORITHMS2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS2.png?raw=true)

线性结构常见的有：**一维数组、队列、链表和栈**。

非线性结构包括：**二维数组，多维数组，广义表，树结构，图**。

## 稀疏数组和队列

### 稀疏 sparsearray 数组

![DATA_STRUCTURES_ALGORITHMS3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS3.png?raw=true)

稀疏 sparsearray 数组 就是用来对数组节省存储空间。当一个数组中大部分元素为０，或者为同一个值的数组时，可以使用稀疏数组来保存该数组。

![DATA_STRUCTURES_ALGORITHMS4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS4.png?raw=true)

**应用实例**

用稀疏数组，来保留类似前面的二维数组(棋盘、地图等等)，把稀疏数组重新恢复原来的二维数组数

![DATA_STRUCTURES_ALGORITHMS5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS5.png?raw=true)

稀疏数组 代码实现

```java
public class SparseArray {

	public static void main(String[] args) {
		// 创建一个原始的二维数组 11 * 11
		// 0: 表示没有棋子， 1 表示 黑子 2 表蓝子
		int chessArr1[][] = new int[11][11];
		chessArr1[1][2] = 1;
		chessArr1[2][3] = 2;
		chessArr1[4][5] = 2;
		// 输出原始的二维数组
		System.out.println("原始的二维数组~~");
		for (int[] row : chessArr1) {
			for (int data : row) {
				System.out.printf("%d\t", data);
			}
			System.out.println();
		}

		// 将二维数组 转 稀疏数组的思
		// 1. 先遍历二维数组 得到非0数据的个数
		int sum = 0;
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (chessArr1[i][j] != 0) {
					sum++;
				}
			}
		}

		// 2. 创建对应的稀疏数组
		int sparseArr[][] = new int[sum + 1][3];
		// 给稀疏数组赋值
		sparseArr[0][0] = 11;
		sparseArr[0][1] = 11;
		sparseArr[0][2] = sum;
		
		// 遍历二维数组，将非0的值存放到 sparseArr中
		int count = 0; //count 用于记录是第几个非0数据
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (chessArr1[i][j] != 0) {
					count++;
					sparseArr[count][0] = i;
					sparseArr[count][1] = j;
					sparseArr[count][2] = chessArr1[i][j];
				}
			}
		}
		
		// 输出稀疏数组的形式
		System.out.println();
		System.out.println("得到稀疏数组为~~~~");
		for (int i = 0; i < sparseArr.length; i++) {
			System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
		}
		System.out.println();
		
		//将稀疏数组 --》 恢复成 原始的二维数组
		/*
		 *  1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
			2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
		 */
		
		//1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
		
		int chessArr2[][] = new int[sparseArr[0][0]][sparseArr[0][1]];
		
		//2. 在读取稀疏数组后几行的数据(从第二行开始)，并赋给 原始的二维数组 即可
		
		for(int i = 1; i < sparseArr.length; i++) {
			chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
		}
		
		// 输出恢复后的二维数组
		System.out.println();
		System.out.println("恢复后的二维数组");
		
		for (int[] row : chessArr2) {
			for (int data : row) {
				System.out.printf("%d\t", data);
			}
			System.out.println();
		}
	}

}

```



### 队列

```
1）队列是一个有序列表，可以用数组或是链表来实现。
2) 遵循先入先出的原则。即：先存入队列的数据，要先取出。后存入的要后取出
```

![DATA_STRUCTURES_ALGORITHMS6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS6.png?raw=true)

#### **数组模拟队列**

![DATA_STRUCTURES_ALGORITHMS7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS7.png?raw=true)

其中 maxSize 是该队列的最大容量。因为队列的输出、输入是分别从前后端来处理，因此需要两个变量 front 及 rear 分别记录队列前后端的下标，front 会随着数据输出而改变，而 rear 则是随着数据输入而改变

```
addQueue 数据入队 的处理需要有两个步骤：思路分析
1) 将尾指针往后移：rear+1 , 当 front == rear 【空】
2) 若尾指针 rear 小于队列的最大下标 maxSize-1，则将数据存入 rear 所指的数组元素中，否则无法存入数据。
rear == maxSize - 1[队列满]
```



```java
// 使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue {
	private int maxSize; // 表示数组的最大容量
	private int front; // 队列头
	private int rear; // 队列尾
	private int[] arr; // 该数据用于存放数据, 模拟队列 (数据的实际存储位置)

	// 创建队列的构造器
	public ArrayQueue(int arrMaxSize) {
		maxSize = arrMaxSize;
		arr = new int[maxSize];
		front = -1; // 指向队列头部，分析出front是指向队列头的前一个位置.
		rear = -1; // 指向队列尾，指向队列尾的数据(即就是队列最后一个数据)
       //因为这两个下标如果是0的话，那么在读写数据的时候会出现歧义 ，
       //举例： 当第一次插入数据的时候，参照addQueue()和 getQueue()     
	}

	// 判断队列是否满
	public boolean isFull() {
		return rear == maxSize - 1;
	}

	// 判断队列是否为空
	public boolean isEmpty() {
		return rear == front;
	}

	// 添加数据到队列
	public void addQueue(int n) {
		// 判断队列是否满
		if (isFull()) {
			System.out.println("队列满，不能加入数据~");
			return;
		}
		rear++; // 让rear 后移
		arr[rear] = n;
	}

	// 获取队列的数据, 出队列
	public int getQueue() {
		// 判断队列是否空
		if (isEmpty()) {
			// 通过抛出异常
			throw new RuntimeException("队列空，不能取数据");
		}
		front++; // front后移
		return arr[front];

	}

	// 显示队列的所有数据
	public void showQueue() {
		// 遍历
		if (isEmpty()) {
			System.out.println("队列空的，没有数据~~");
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.printf("arr[%d]=%d\n", i, arr[i]);
		}
	}

	// 显示队列的头数据， 注意不是取出数据，就是不实际的修改front指针的值。
	public int headQueue() {
		// 判断
		if (isEmpty()) {
			throw new RuntimeException("队列空的，没有数据~~");
		}
		return arr[front + 1];
	}
}

//验证队列
public class ArrayQueueDemo {
	public static void main(String[] args) {
		//创建一个队列
		ArrayQueue queue = new ArrayQueue(3);
		char key = ' '; //接收用户输入
		Scanner scanner = new Scanner(System.in);//
		boolean loop = true;
		//输出一个菜单
		while(loop) {
			System.out.println("s(show): 显示队列");
			System.out.println("e(exit): 退出程序");
			System.out.println("a(add): 添加数据到队列");
			System.out.println("g(get): 从队列取出数据");
			System.out.println("h(head): 查看队列头的数据");
			key = scanner.next().charAt(0);//接收一个字符
			switch (key) {
			case 's':
				queue.showQueue();
				break;
			case 'a':
				System.out.println("输出一个数");
				int value = scanner.nextInt();
				queue.addQueue(value);
				break;
			case 'g': //取出数据
				try {
					int res = queue.getQueue();
					System.out.printf("取出的数据是%d\n", res);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
				break;
			case 'h': //查看队列头的数据
				try {
					int res = queue.headQueue();
					System.out.printf("队列头的数据是%d\n", res);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
				break;
			case 'e': //退出
				scanner.close();
				loop = false;
				break;
			default:
				break;
			}
		}
		
		System.out.println("程序退出~~");
	}

}

```

上面模仿的队列有一个缺陷： 数组空间浪费严重，数组空间使用一次就无法使用，无法循环利用。

#### **数组模拟环形队列**

对前面的数组模拟队列的优化，充分利用数组. 因此将数组看做是一个环形的。(通过取模的方式来实现)

![DATA_STRUCTURES_ALGORITHMS8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS8.png?raw=true)

```java
//可以循环利用数组空间的队列
class CircleArray {
	private int maxSize; // 表示数组的最大容量
	//front 变量的含义做一个调整： front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素 
	//front 的初始值 = 0
	private int front; 
	//rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定.
	//rear 的初始值 = 0
	private int rear; // 队列尾
	private int[] arr; // 该数据用于存放数据, 模拟队列
	
	public CircleArray(int arrMaxSize) {
		maxSize = arrMaxSize;
		arr = new int[maxSize];
	}
	
	// 判断队列是否满
	public boolean isFull() {
		return (rear  + 1) % maxSize == front;
	}
	
	// 判断队列是否为空
	public boolean isEmpty() {
		return rear == front;
	}
	
	// 添加数据到队列
	public void addQueue(int n) {
		// 判断队列是否满
		if (isFull()) {
			System.out.println("队列满，不能加入数据~");
			return;
		}
		//直接将数据加入
		arr[rear] = n;
		//将 rear 后移, 这里必须考虑取模， 复用数组空间的情况下，当数组尾部满，数组前端的空间会被使用 
		rear = (rear + 1) % maxSize;
	}
	
	// 获取队列的数据, 出队列
	public int getQueue() {
		// 判断队列是否空
		if (isEmpty()) {
			// 通过抛出异常
			throw new RuntimeException("队列空，不能取数据");
		}
		// 这里需要分析出 front是指向队列的第一个元素
		// 1. 先把 front 对应的值保留到一个临时变量  (如果直接返回值得话，就无法对front指针的值进行修改)
		// 2. 将 front 后移, 考虑取模
		// 3. 将临时保存的变量返回
		int value = arr[front];
		front = (front + 1) % maxSize;
		return value;

	}
	
	// 显示队列的所有数据
	public void showQueue() {
		// 遍历
		if (isEmpty()) {
			System.out.println("队列空的，没有数据~~");
			return;
		}
		// 思路：从front开始遍历，遍历多少个元素 ，这里不能直接使用rear，因为rear可能会小于front
		// 动脑筋
		for (int i = front; i < front + size() ; i++) {
            // 这里front + size()的值可能会超过maxsize, 所以这里需要进行取模
			System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
		}
	}
	
	// 求出当前队列有效数据的个数
	public int size() {
		// rear = 2
		// front = 1
		// maxSize = 3 
		return (rear + maxSize - front) % maxSize;   
	}
	
	// 显示队列的头数据， 注意不是取出数据
	public int headQueue() {
		// 判断
		if (isEmpty()) {
			throw new RuntimeException("队列空的，没有数据~~");
		}
		return arr[front];
	}
}
```



## 链表

![DATA_STRUCTURES_ALGORITHMS9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS9.png?raw=true)

```
1) 链表是以节点的方式来存储,是链式存储
2) 每个节点包含 data 域， next 域：指向下一个节点.
3) 链表的各个节点不一定是连续存储.(从上图也可以看出)
4) 链表分带头节点的链表和没有头节点的链表，根据实际的需求来确定
 单链表
```

![DATA_STRUCTURES_ALGORITHMS10.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS10.png?raw=true)

### 单链表

![DATA_STRUCTURES_ALGORITHMS11.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS11.png?raw=true)

节点：

```java
//定义HeroNode ， 每个HeroNode 对象就是一个节点
class HeroNode {
	public int no;
	public String name;
	public String nickname;
	public HeroNode next; //指向下一个节点
	//构造器
	public HeroNode(int no, String name, String nickname) {
		this.no = no;
		this.name = name;
		this.nickname = nickname;
	}
	//为了显示方法，我们重新toString
	@Override
	public String toString() {
		return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
	}
	
}
```



单链表：

编码小技巧： 在处理数据之前，我们需要先判断边界条件。

```java
//定义SingleLinkedList 管理我们的英雄
class SingleLinkedList {
	//先初始化一个头节点, 头节点不要动, 不存放具体的数据
	private HeroNode head = new HeroNode(0, "", "");
	
	//返回头节点
	public HeroNode getHead() {
		return head;
	}

	//添加节点到单向链表
	//思路，当不考虑编号顺序时
	//1. 找到当前链表的最后节点
	//2. 将最后这个节点的next 指向 新的节点
	public void add(HeroNode heroNode) {
		//因为head节点不能动，因此我们需要一个辅助指针 temp
		HeroNode temp = head;
		//遍历链表，找到最后
		while(true) {
			//找到链表的最后
			if(temp.next == null) {//
				break;
			}
			//如果没有找到最后, 将将temp后移
			temp = temp.next;
		}
		//当退出while循环时，temp就指向了链表的最后
		//将最后这个节点的next 指向 新的节点
		temp.next = heroNode;
	}
	
	//修改节点的信息, 根据no编号来修改，即no编号不能改.
	//说明
	//1. 根据 newHeroNode 的 no 来修改即可
	public void update(HeroNode newHeroNode) {
		//判断是否空
		if(head.next == null) {
			System.out.println("链表为空~");
			return;
		}
		//找到需要修改的节点, 根据no编号
		//定义一个辅助变量
		HeroNode temp = head.next;
		boolean flag = false; //表示是否找到该节点
		while(true) {
			if (temp == null) {
				break; //已经遍历完链表
			}
			if(temp.no == newHeroNode.no) {
				//找到
				flag = true;
				break;
			}
			temp = temp.next;
		}
		//根据flag 判断是否找到要修改的节点
		if(flag) {
			temp.name = newHeroNode.name;
			temp.nickname = newHeroNode.nickname;
		} else { //没有找到
			System.out.printf("没有找到 编号 %d 的节点，不能修改\n", newHeroNode.no);
		}
	}
	
	//显示链表[遍历]
	public void list() {
		//判断链表是否为空
		if(head.next == null) {
			System.out.println("链表为空");
			return;
		}
		//因为头节点，不能动，因此我们需要一个辅助变量来遍历
		HeroNode temp = head.next;
		while(true) {
			//判断是否到链表最后
			if(temp == null) {
				break;
			}
			//输出节点的信息
			System.out.println(temp);
			//将temp后移， 一定小心
			temp = temp.next;
		}
	}
}
```



有条件的入队操作

![DATA_STRUCTURES_ALGORITHMS12.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS12.png?raw=true)

```java
//第二种方式在添加英雄时，需求是：根据排名将英雄插入到指定位置(如果有这个排名，则添加失败，并给出提示)
	public void addByOrder(HeroNode heroNode) {
		//因为头节点不能动，因此我们仍然通过一个辅助指针(变量)来帮助找到添加的位置
		//因为单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
		HeroNode temp = head;
		boolean flag = false; // flag标志添加的编号是否存在，默认为false
		while(true) {
			if(temp.next == null) {//说明temp已经在链表的最后
				break; //
			} 
			if(temp.next.no > heroNode.no) { //位置找到，就在temp的后面插入
				break;
			} else if (temp.next.no == heroNode.no) {//说明希望添加的heroNode的编号已然存在
				
				flag = true; //说明编号存在
				break;
			}
			temp = temp.next; //后移，遍历当前链表
		}
		//判断flag 的值
		if(flag) { //不能添加，说明编号存在
			System.out.printf("准备插入的英雄的编号 %d 已经存在了, 不能加入\n", heroNode.no);
		} else {
			//插入到链表中, temp的后面
			heroNode.next = temp.next;
			temp.next = heroNode;
		}
	}
// 这样操作还是有个好处就是我们在内存中就已经将他排序好，这样肯定比在数据库中操作快
```



删除节点

![DATA_STRUCTURES_ALGORITHMS13.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS13.png?raw=true)

```java
//删除节点
	//思路
	//1. head 不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
	//2. 说明我们在比较时，是temp.next.no 和  需要删除的节点的no比较
	public void del(int no) {
		HeroNode temp = head;
		boolean flag = false; // 标志是否找到待删除节点的
		while(true) {
			if(temp.next == null) { //已经到链表的最后
				break;
			}
			if(temp.next.no == no) {
				//找到的待删除节点的前一个节点temp
				flag = true;
				break;
			}
			temp = temp.next; //temp后移，遍历
		}
		//判断flag
		if(flag) { //找到
			//可以删除
			temp.next = temp.next.next;
		}else {
			System.out.printf("要删除的 %d 节点不存在\n", no);
		}
	}
```

其他一些通用的方法

求单链表有效节点个数

```java
	//方法：获取到单链表的节点的个数(如果是带头结点的链表，需求不统计头节点)
	/**
	 * 
	 * @param head 链表的头节点
	 * @return 返回的就是有效节点的个数
	 */
	public static int getLength(HeroNode head) {
		if(head.next == null) { //空链表
			return 0;
		}
		int length = 0;
		//定义一个辅助的变量, 这里我们没有统计头节点
		HeroNode cur = head.next;
		while(cur != null) {
			length++;
			cur = cur.next; //遍历
		}
		return length;
	}
```

查找单链表中的倒数第k个结点 ：

```java
//查找单链表中的倒数第k个结点 【新浪面试题】
	//思路
	//1. 编写一个方法，接收head节点，同时接收一个index 
	//2. index 表示是倒数第index个节点
	//3. 先把链表从头到尾遍历，得到链表的总的长度 getLength
	//4. 得到size 后，我们从链表的第一个开始遍历 (size-index)个，就可以得到
	//5. 如果找到了，则返回该节点，否则返回nulll
	public static HeroNode findLastIndexNode(HeroNode head, int index) {
		//判断如果链表为空，返回null
		if(head.next == null) {
			return null;//没有找到
		}
		//第一个遍历得到链表的长度(节点个数)
		int size = getLength(head);
		//第二次遍历  size-index 位置，就是我们倒数的第K个节点
		//先做一个index的校验
		if(index <=0 || index > size) {
			return null; 
		}
		//定义给辅助变量， for 循环定位到倒数的index
		HeroNode cur = head.next; //3 // 3 - 1 = 2
		for(int i =0; i< size - index; i++) {
			cur = cur.next;
		}
		return cur;
		
	}
```

单链表反转：

![DATA_STRUCTURES_ALGORITHMS14.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS14.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS15.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS15.png?raw=true)

```java
//将单链表反转
	public static void reversetList(HeroNode head) {
		//如果当前链表为空，或者只有一个节点，无需反转，直接返回
		if(head.next == null || head.next.next == null) {
			return ;
		}
		
		//定义一个辅助的指针(变量)，帮助我们遍历原来的链表
		HeroNode cur = head.next;
		HeroNode next = null;// 指向当前节点[cur]的下一个节点
		HeroNode reverseHead = new HeroNode(0, "", "");
		//遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端
		//动脑筋
		while(cur != null) { 
			next = cur.next;//先暂时保存当前节点的下一个节点，因为后面需要使用
			cur.next = reverseHead.next;//将cur的下一个节点指向新的链表的最前端
			reverseHead.next = cur; //将cur 连接到新的链表上
			cur = next;//让cur后移
		}
		//将head.next 指向 reverseHead.next , 实现单链表的反转
		head.next = reverseHead.next;
	}
```



从尾到头打印单链表 【要求方式 1：反向遍历 。 方式 2：Stack 栈】

![DATA_STRUCTURES_ALGORITHMS16.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS16.png?raw=true)

```java
//方式2：
	//可以利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，就实现了逆序打印的效果
	public static void reversePrint(HeroNode head) {
		if(head.next == null) {
			return;//空链表，不能打印
		}
		//创建要给一个栈，将各个节点压入栈
		Stack<HeroNode> stack = new Stack<HeroNode>();
		HeroNode cur = head.next;
		//将链表的所有节点压入栈
		while(cur != null) {
			stack.push(cur);
			cur = cur.next; //cur后移，这样就可以压入下一个节点
		}
		//将栈中的节点进行打印,pop 出栈
		while (stack.size() > 0) {
			System.out.println(stack.pop()); //stack的特点是先进后出
		}
	}
	
```





### 双链表

单链表存在的不足： 他查找的方向只能是一个方向，只能向后查找。无法向前 查找。单向链表不能自我删除，需要靠辅助节点

![DATA_STRUCTURES_ALGORITHMS17.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS17.png?raw=true)

双向链表节点类：

```java
// 定义HeroNode2 ， 每个HeroNode 对象就是一个节点
class HeroNode2 {
	public int no;
	public String name;
	public String nickname;
	public HeroNode2 next; // 指向下一个节点, 默认为null
	public HeroNode2 pre; // 指向前一个节点, 默认为null
	// 构造器
	public HeroNode2(int no, String name, String nickname) {
		this.no = no;
		this.name = name;
		this.nickname = nickname;
	}

	// 为了显示方法，我们重新toString
	@Override
	public String toString() {
		return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
	}

}
```



节点操作类：

```java
// 创建一个双向链表的类
class DoubleLinkedList {

	// 先初始化一个头节点, 头节点不要动, 不存放具体的数据
	private HeroNode2 head = new HeroNode2(0, "", "");

	// 返回头节点
	public HeroNode2 getHead() {
		return head;
	}

	// 遍历双向链表的方法
	// 显示链表[遍历]
	public void list() {
		// 判断链表是否为空
		if (head.next == null) {
			System.out.println("链表为空");
			return;
		}
		// 因为头节点，不能动，因此我们需要一个辅助变量来遍历
		HeroNode2 temp = head.next;
		while (true) {
			// 判断是否到链表最后
			if (temp == null) {
				break;
			}
			// 输出节点的信息
			System.out.println(temp);
			// 将temp后移， 一定小心
			temp = temp.next;
		}
	}

	// 添加一个节点到双向链表的最后.
	public void add(HeroNode2 heroNode) {

		// 因为head节点不能动，因此我们需要一个辅助遍历 temp
		HeroNode2 temp = head;
		// 遍历链表，找到最后
		while (true) {
			// 找到链表的最后
			if (temp.next == null) {//
				break;
			}
			// 如果没有找到最后, 将将temp后移
			temp = temp.next;
		}
		// 当退出while循环时，temp就指向了链表的最后
		// 形成一个双向链表
		temp.next = heroNode;
		heroNode.pre = temp;
	}

	// 修改一个节点的内容, 可以看到双向链表的节点内容修改和单向链表一样
	// 只是 节点类型改成 HeroNode2
	public void update(HeroNode2 newHeroNode) {
		// 判断是否空
		if (head.next == null) {
			System.out.println("链表为空~");
			return;
		}
		// 找到需要修改的节点, 根据no编号
		// 定义一个辅助变量
		HeroNode2 temp = head.next;
		boolean flag = false; // 表示是否找到该节点
		while (true) {
			if (temp == null) {
				break; // 已经遍历完链表
			}
			if (temp.no == newHeroNode.no) {
				// 找到
				flag = true;
				break;
			}
			temp = temp.next;
		}
		// 根据flag 判断是否找到要修改的节点
		if (flag) {
			temp.name = newHeroNode.name;
			temp.nickname = newHeroNode.nickname;
		} else { // 没有找到
			System.out.printf("没有找到 编号 %d 的节点，不能修改\n", newHeroNode.no);
		}
	}

	// 从双向链表中删除一个节点,
	// 说明
	// 1 对于双向链表，我们可以直接找到要删除的这个节点
	// 2 找到后，自我删除即可
	public void del(int no) {

		// 判断当前链表是否为空
		if (head.next == null) {// 空链表
			System.out.println("链表为空，无法删除");
			return;
		}

		HeroNode2 temp = head.next; // 辅助变量(指针)
		boolean flag = false; // 标志是否找到待删除节点的
		while (true) {
			if (temp == null) { // 已经到链表的最后
				break;
			}
			if (temp.no == no) {
				// 找到的待删除节点的前一个节点temp
				flag = true;
				break;
			}
			temp = temp.next; // temp后移，遍历
		}
		// 判断flag
		if (flag) { // 找到
			// 可以删除
			// temp.next = temp.next.next;[单向链表]
			temp.pre.next = temp.next;
			// 这里我们的代码有问题?
			// 如果是最后一个节点，就不需要执行下面这句话，否则出现空指针
			if (temp.next != null) {
				temp.next.pre = temp.pre;
			}
		} else {
			System.out.printf("要删除的 %d 节点不存在\n", no);
		}
	}

}
```



### 单向环形列表(约瑟夫问题)

![DATA_STRUCTURES_ALGORITHMS18.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS18.png?raw=true)

应用场景：约瑟夫问题

![DATA_STRUCTURES_ALGORITHMS19.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS19.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS20.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS20.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS21.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS21.png?raw=true)

代码实现：

分成两部分：

第一步:创建一个环形链表，并能将其显示出来

![DATA_STRUCTURES_ALGORITHMS22.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS22.png?raw=true)

```java
// 创建一个Boy类，表示一个节点
class Boy {
	private int no;// 编号
	private Boy next; // 指向下一个节点,默认null
	public Boy(int no) {
		this.no = no;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public Boy getNext() {
		return next;
	}
	public void setNext(Boy next) {
		this.next = next;
	}

}

```

单向环形链表

```java
// 创建一个环形的单向链表
class CircleSingleLinkedList {
	// 创建一个first节点,当前没有编号
	private Boy first = null;

	// 添加小孩节点，构建成一个环形的链表
	public void addBoy(int nums) {
		// nums 做一个数据校验
		if (nums < 1) {
			System.out.println("nums的值不正确");
			return;
		}
		Boy curBoy = null; // 辅助指针，帮助构建环形链表
		// 使用for来创建我们的环形链表
		for (int i = 1; i <= nums; i++) {
			// 根据编号，创建小孩节点
			Boy boy = new Boy(i);
			// 如果是第一个小孩
			if (i == 1) {
				first = boy; // 第一个节点(头节点)就是有效节点。不存在无效节点
				first.setNext(first); // 构成环
				curBoy = first; // 让curBoy指向第一个小孩
			} else {
				curBoy.setNext(boy);//
				boy.setNext(first);//
				curBoy = boy;
			}
		}
	}

	// 遍历当前的环形链表
	public void showBoy() {
		// 判断链表是否为空
		if (first == null) {
			System.out.println("没有任何小孩~~");
			return;
		}
		// 因为first不能动，因此我们仍然使用一个辅助指针完成遍历
		Boy curBoy = first;
		while (true) {
			System.out.printf("小孩的编号 %d \n", curBoy.getNo());
			if (curBoy.getNext() == first) {// 说明已经遍历完毕
				break;
			}
			curBoy = curBoy.getNext(); // curBoy后移
		}
	}

	
}
```



第二步:获得出圈的队列

![DATA_STRUCTURES_ALGORITHMS23.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS23.png?raw=true)

```java
// 这个方法应该写在上面的CircleSingleLinkedList 中
// 根据用户的输入，计算出小孩出圈的顺序
	/**
	 * 
	 * @param startNo
	 *            表示从第几个小孩开始数数 k
	 * @param countNum
	 *            表示数几下 m
	 * @param nums
	 *            表示最初有多少小孩在圈中 n  
	 */
	public void countBoy(int startNo, int countNum, int nums) {
		// 先对数据进行校验
		if (first == null || startNo < 1 || startNo > nums) {
			System.out.println("参数输入有误， 请重新输入");
			return;
		}
		// 创建要给辅助指针,帮助完成小孩出圈
		Boy helper = first;
		// 需求创建一个辅助指针(变量) helper , 事先应该指向环形链表的最后这个节点
		while (true) {
			if (helper.getNext() == first) { // 说明helper指向最后小孩节点
				break;
			}
			helper = helper.getNext();
		}
		//小孩报数前，先让 first 和  helper 移动 k - 1次
		for(int j = 0; j < startNo - 1; j++) {
			first = first.getNext();
			helper = helper.getNext();
		}
		//当小孩报数时，让first 和 helper 指针同时 的移动  m  - 1 次, 然后出圈
		//这里是一个循环操作，知道圈中只有一个节点
		while(true) {
			if(helper == first) { //说明圈中只有一个节点
				break;
			}
			//让 first 和 helper 指针同时 的移动 countNum - 1
			for(int j = 0; j < countNum - 1; j++) {
				first = first.getNext();
				helper = helper.getNext();
			}
			//这时first指向的节点，就是要出圈的小孩节点
			System.out.printf("小孩%d出圈\n", first.getNo());
			//这时将first指向的小孩节点出圈
			first = first.getNext();
			helper.setNext(first); //
			
		}
		System.out.printf("最后留在圈中的小孩编号%d \n", first.getNo());	
	}
```



## 栈

什么是栈(stack)？

栈是一个先入后出(FILO-First In Last Out)的有序列表。

```
3) 栈(stack)是限制线性表中元素的插入和删除只能在线性表的同一端进行的一种特殊线性表。允许插入和删除的
一端，为变化的一端，称为栈顶(Top)，另一端为固定的一端，称为栈底(Bottom)。
4) 根据栈的定义可知，最先放入栈中元素在栈底，最后放入的元素在栈顶，而删除元素刚好相反，最后放入的元
素最先删除，最先放入的元素最后删除
```

![DATA_STRUCTURES_ALGORITHMS25.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS25.png?raw=true)

实际应用：

![DATA_STRUCTURES_ALGORITHMS24.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS24.png?raw=true)

```
 栈的应用场景
1) 子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，直到子程序执行完后再将地址取出，以
回到原来的程序中。
2) 处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆
栈中。
3) 表达式的转换[中缀表达式转后缀表达式]与求值(实际解决)。
4) 二叉树的遍历。
5) 图形的深度优先(depth 一 first)搜索法。
```



数组模拟栈的入栈和出栈，还有遍历操作

![DATA_STRUCTURES_ALGORITHMS26.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS26.png?raw=true)

```java
//定义一个 ArrayStack 表示栈
class ArrayStack {
	private int maxSize; // 栈的大小
	private int[] stack; // 数组，数组模拟栈，数据就放在该数组
	private int top = -1;// top表示栈顶，初始化为-1
	
	//构造器
	public ArrayStack(int maxSize) {
		this.maxSize = maxSize;
		stack = new int[this.maxSize];
	}
	
	//栈满
	public boolean isFull() {
		return top == maxSize - 1;
	}
	//栈空
	public boolean isEmpty() {
		return top == -1;
	}
	//入栈-push
	public void push(int value) {
		//先判断栈是否满
		if(isFull()) {
			System.out.println("栈满");
			return;
		}
		top++;
		stack[top] = value;
	}
	//出栈-pop, 将栈顶的数据返回
	public int pop() {
		//先判断栈是否空
		if(isEmpty()) {
			//抛出异常
			throw new RuntimeException("栈空，没有数据~");
		}
		int value = stack[top];
		top--;
		return value;
	}
	//显示栈的情况[遍历栈]， 遍历时，需要从栈顶开始显示数据，从栈顶往下遍历
	public void list() {
		if(isEmpty()) {
			System.out.println("栈空，没有数据~~");
			return;
		}
		//需要从栈顶开始显示数据
		for(int i = top; i >= 0 ; i--) {
			System.out.printf("stack[%d]=%d\n", i, stack[i]);
		}
	}
	
}

//使用测试
public static void main(String[] args) {
		//先创建一个ArrayStack对象->表示栈
		ArrayStack stack = new ArrayStack(4);
		String key = "";
		boolean loop = true; //控制是否退出菜单
		Scanner scanner = new Scanner(System.in);
		while(loop) {
			System.out.println("show: 表示显示栈");
			System.out.println("exit: 退出程序");
			System.out.println("push: 表示添加数据到栈(入栈)");
			System.out.println("pop: 表示从栈取出数据(出栈)");
			System.out.println("请输入你的选择");
			key = scanner.next();
			switch (key) {
			case "show":
				stack.list();
				break;
			case "push":
				System.out.println("请输入一个数");
				int value = scanner.nextInt();
				stack.push(value);
				break;
			case "pop":
				try {
					int res = stack.pop();
					System.out.printf("出栈的数据是 %d\n", res);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
				break;
			case "exit":
				scanner.close();
				loop = false;
				break;
			default:
				break;
			}
		}
		System.out.println("程序退出~~~");
	}
```



### 栈实现综合计算器(中缀表达式)

使用栈来实现综合计算器

![DATA_STRUCTURES_ALGORITHMS27.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS27.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS28.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS28.png?raw=true)

```java
//先创建一个栈,直接使用前面创建好
//定义一个 ArrayStack2 表示栈, 需要扩展功能
class ArrayStack2 {
	private int maxSize; // 栈的大小
	private int[] stack; // 数组，数组模拟栈，数据就放在该数组
	private int top = -1;// top表示栈顶，初始化为-1
	
	//构造器
	public ArrayStack2(int maxSize) {
		this.maxSize = maxSize;
		stack = new int[this.maxSize];
	}
	
	//增加一个方法，可以返回当前栈顶的值, 但是不是真正的pop
	public int peek() {
		return stack[top];
	}
	
	//栈满
	public boolean isFull() {
		return top == maxSize - 1;
	}
	//栈空
	public boolean isEmpty() {
		return top == -1;
	}
	//入栈-push
	public void push(int value) {
		//先判断栈是否满
		if(isFull()) {
			System.out.println("栈满");
			return;
		}
		top++;
		stack[top] = value;
	}
	//出栈-pop, 将栈顶的数据返回
	public int pop() {
		//先判断栈是否空
		if(isEmpty()) {
			//抛出异常
			throw new RuntimeException("栈空，没有数据~");
		}
		int value = stack[top];
		top--;
		return value;
	}
	//显示栈的情况[遍历栈]， 遍历时，需要从栈顶开始显示数据
	public void list() {
		if(isEmpty()) {
			System.out.println("栈空，没有数据~~");
			return;
		}
		//需要从栈顶开始显示数据
		for(int i = top; i >= 0 ; i--) {
			System.out.printf("stack[%d]=%d\n", i, stack[i]);
		}
	}
	//返回运算符的优先级，优先级是程序员来确定, 优先级使用数字表示
	//数字越大，则优先级就越高.
	public int priority(int oper) {
		if(oper == '*' || oper == '/'){
			return 1;
		} else if (oper == '+' || oper == '-') {
			return 0;
		} else {
			return -1; // 假定目前的表达式只有 +, - , * , /
		}
	}
	//判断是不是一个运算符
	public boolean isOper(char val) {
		return val == '+' || val == '-' || val == '*' || val == '/';
	}
	//计算方法
	public int cal(int num1, int num2, int oper) {
		int res = 0; // res 用于存放计算的结果
		switch (oper) {
		case '+':
			res = num1 + num2;
			break;
		case '-':
			res = num2 - num1;// 注意顺序
			break;
		case '*':
			res = num1 * num2;
			break;
		case '/':
			res = num2 / num1;
			break;
		default:
			break;
		}
		return res;
	}
	
}
```

使用：

```java
	public static void main(String[] args) {
		//根据前面老师思路，完成表达式的运算
		String expression = "7*2*2-5+1-5+3-4"; // 15//如何处理多位数的问题？
		//创建两个栈，数栈，一个符号栈
		ArrayStack2 numStack = new ArrayStack2(10);
		ArrayStack2 operStack = new ArrayStack2(10);
		//定义需要的相关变量
		int index = 0;//用于扫描
		int num1 = 0; 
		int num2 = 0;
		int oper = 0;
		int res = 0;
		char ch = ' '; //将每次扫描得到char保存到ch
		String keepNum = ""; //用于拼接 多位数
		//开始while循环的扫描expression
		while(true) {
			//依次得到expression 的每一个字符
			ch = expression.substring(index, index+1).charAt(0);
			//判断ch是什么，然后做相应的处理
			if(operStack.isOper(ch)) {//如果是运算符
				//判断当前的符号栈是否为空
				if(!operStack.isEmpty()) {
					//如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符,就需要从数栈中pop出两个数,
					//在从符号栈中pop出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈
					if(operStack.priority(ch) <= operStack.priority(operStack.peek())) {
						num1 = numStack.pop();
						num2 = numStack.pop();
						oper = operStack.pop();
						res = numStack.cal(num1, num2, oper);
						//把运算的结果如数栈
						numStack.push(res);
						//然后将当前的操作符入符号栈
						operStack.push(ch);
					} else {
						//如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈.
						operStack.push(ch);
					}
				}else {
					//如果为空直接入符号栈..
					operStack.push(ch); // 1 + 3
				}
			} else { //如果是数，则直接入数栈
				
				//numStack.push(ch - 48); //? "1+3" '1' => 1
				//分析思路
				//1. 当处理多位数时，不能发现是一个数就立即入栈，因为他可能是多位数
				//2. 在处理数，需要向expression的表达式的index 后再看一位,如果是数就进行扫描，如果是符号才入栈
				//3. 因此我们需要定义一个变量 字符串，用于拼接
				
				//处理多位数
				keepNum += ch;
				
				//如果ch已经是expression的最后一位，就直接入栈
				if (index == expression.length() - 1) {
					numStack.push(Integer.parseInt(keepNum));
				}else{
				
					//判断下一个字符是不是数字，如果是数字，就继续扫描，如果是运算符，则入栈
					//注意是看后一位，不是index++
					if (operStack.isOper(expression.substring(index+1,index+2).charAt(0))) {
						//如果后一位是运算符，则入栈 keepNum = "1" 或者 "123"
						numStack.push(Integer.parseInt(keepNum));
						//重要的!!!!!!, keepNum清空
						keepNum = "";
						
					}
				}
			}
			//让index + 1, 并判断是否扫描到expression最后.
			index++;
			if (index >= expression.length()) {
				break;
			}
		}
		
		//当表达式扫描完毕，就顺序的从 数栈和符号栈中pop出相应的数和符号，并运行.
		while(true) {
			//如果符号栈为空，则计算到最后的结果, 数栈中只有一个数字【结果】
			if(operStack.isEmpty()) {
				break;
			}
			num1 = numStack.pop();
			num2 = numStack.pop();
			oper = operStack.pop();
			res = numStack.cal(num1, num2, oper);
			numStack.push(res);//入栈
		}
		//将数栈的最后数，pop出，就是结果
		int res2 = numStack.pop();
		System.out.printf("表达式 %s = %d", expression, res2);
	}
```



### 前缀后缀中缀表达式介绍

中缀表达式: 就是我们常见的数学表达式 7+5*2

有了中缀表达式，为什么还需要前缀，后缀表达式了？ 因为中缀表达式虽然对于我们人来说比较友好，但是对于计算来说并不友好。前缀，后缀对于计算机来说就很友好 

![DATA_STRUCTURES_ALGORITHMS29.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS29.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS30.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS30.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS31.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS31.png?raw=true)



### 中缀表达式转换为后缀表达式

具体步骤如下：

```
1) 初始化两个栈：运算符栈 s1 和储存中间结果的栈 s2；
2) 从左至右扫描中缀表达式；
3) 遇到操作数时，将其压 s2；
4) 遇到运算符时，比较其与 s1 栈顶运算符的优先级：
1.如果 s1 为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
2.否则，若优先级比栈顶运算符的高，也将运算符压入 s1；
3.否则，将 s1 栈顶的运算符弹出并压入到 s2 中，再次转到(4-1)与 s1 中新的栈顶运算符相比较；
5) 遇到括号时：
(1) 如果是左括号“(”，则直接压入 s1
(2) 如果是右括号“)”，则依次弹出 s1 栈顶的运算符，并压入 s2，直到遇到左括号为止，此时将这一对括号丢弃
6) 重复步骤 2 至 5，直到表达式的最右边
7) 将 s1 中剩余的运算符依次弹出并压入 s2
8) 依次弹出 s2 中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式

```

![DATA_STRUCTURES_ALGORITHMS33.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS33.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS34.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS34.png?raw=true)

```java
public class PolandNotation {
	public static void main(String[] args) {	
		
		//完成将一个中缀表达式转成后缀表达式的功能
		//说明
		//1. 1+((2+3)×4)-5 => 转成  1 2 3 + 4 × + 5 –
		//2. 因为直接对str 进行操作，不方便，因此 先将  "1+((2+3)×4)-5" =》 中缀的表达式对应的List
		//   即 "1+((2+3)×4)-5" => ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
		//3. 将得到的中缀表达式对应的List => 后缀表达式对应的List
		//   即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
		
		String expression = "1+((2+3)*4)-5";//注意表达式 
		List<String> infixExpressionList = toInfixExpressionList(expression);
		System.out.println("中缀表达式对应的List=" + infixExpressionList); 
        // ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
		List<String> suffixExpreesionList = parseSuffixExpreesionList(infixExpressionList);
		System.out.println("后缀表达式对应的List" + suffixExpreesionList); 
        //ArrayList [1,2,3,+,4,*,+,5,–] 
		System.out.printf("expression=%d", calculate(suffixExpreesionList)); // ?

	}
	
	
	
	//即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
	//方法：将得到的中缀表达式对应的List => 后缀表达式对应的List
	public static List<String> parseSuffixExpreesionList(List<String> ls) {
		//定义两个栈
		Stack<String> s1 = new Stack<String>(); // 符号栈
		//说明：因为s2 这个栈，在整个转换过程中，没有pop操作，而且后面我们还需要逆序输出
		//因此比较麻烦，这里我们就不用 Stack<String> 直接使用 List<String> s2
        // 然后正常输出，还节省了逆序
		//Stack<String> s2 = new Stack<String>(); // 储存中间结果的栈s2
		List<String> s2 = new ArrayList<String>(); // 储存中间结果的Lists2
		
		//遍历ls
		for(String item: ls) {
			//如果是一个数，加入s2
			if(item.matches("\\d+")) {
				s2.add(item);
			} else if (item.equals("(")) {
				s1.push(item);
			} else if (item.equals(")")) {
				//如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
				while(!s1.peek().equals("(")) {
					s2.add(s1.pop());
				}
				s1.pop();//!!! 将 ( 弹出 s1栈， 消除小括号
			} else {
				//当item的优先级小于等于s1栈顶运算符, 将s1栈顶的运算符弹出并加入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
				//问题：我们缺少一个比较优先级高低的方法
				while(s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item) ) {
					s2.add(s1.pop());
				}
				//还需要将item压入栈
				s1.push(item);
			}
		}
		
		//将s1中剩余的运算符依次弹出并加入s2
		while(s1.size() != 0) {
			s2.add(s1.pop());
		}

		return s2; //注意因为是存放到List, 因此按顺序输出就是对应的后缀表达式对应的List
		
	}
	
	//方法：将 中缀表达式转成对应的List
	//  s="1+((2+3)×4)-5";
	public static List<String> toInfixExpressionList(String s) {
		//定义一个List,存放中缀表达式 对应的内容
		List<String> ls = new ArrayList<String>();
		int i = 0; //这时是一个指针，用于遍历 中缀表达式字符串
		String str; // 对多位数的拼接
		char c; // 每遍历到一个字符，就放入到c
		do {
			//如果c是一个非数字，我需要加入到ls
			if((c=s.charAt(i)) < 48 ||  (c=s.charAt(i)) > 57) {
				ls.add("" + c);
				i++; //i需要后移
			} else { //如果是一个数，需要考虑多位数
				str = ""; //先将str 置成"" '0'[48]->'9'[57]
				while(i < s.length() && (c=s.charAt(i)) >= 48 && (c=s.charAt(i)) <= 57) {
					str += c;//拼接
					i++;
				}
				ls.add(str);
			}
		}while(i < s.length());
		return ls;//返回
	}
			
}

//编写一个类 Operation 可以返回一个运算符 对应的优先级
class Operation {
	private static int ADD = 1;
	private static int SUB = 1;
	private static int MUL = 2;
	private static int DIV = 2;
	
	//写一个方法，返回对应的优先级数字
	public static int getValue(String operation) {
		int result = 0;
		switch (operation) {
		case "+":
			result = ADD;
			break;
		case "-":
			result = SUB;
			break;
		case "*":
			result = MUL;
			break;
		case "/":
			result = DIV;
			break;
		default:
			System.out.println("不存在该运算符" + operation);
			break;
		}
		return result;
	}
	
}

```



### 逆波兰计算器

要求完成如下任务：

```
1) 输入一个逆波兰表达式(后缀表达式)，使用栈(Stack jdk为我们提供了实现), 计算其结果
2) 简化只支持小括号和多位数“整数”。如果要支持小数，就判断一下小数点就可以了

例如: (3+4)×5-6 对应的后缀表达式就是 3 4 + 5 × 6 - , 针对后缀表达式求值步骤如下:
1．从左至右扫描，将 3 和 4 压入堆栈；
2．遇到+运算符，因此弹出 4 和 3（4 为栈顶元素，3 为次顶元素），计算出 3+4 的值，得 7，再将 7 入栈；
3．将 5 入栈；
4．接下来是×运算符，因此弹出 5 和 7，计算出 7×5=35，将 35 入栈；
5．将 6 入栈；
6．最后是-运算符，计算出 35-6 的值，即 29，由此得出最终结果
```

![DATA_STRUCTURES_ALGORITHMS32.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS32.png?raw=true)

```java
public class PolandNotation {

	public static void main(String[] args) {	
		//先定义给逆波兰表达式
		//(30+4)×5-6  => 30 4 + 5 × 6 - => 164
		// 4 * 5 - 8 + 60 + 8 / 2 => 4 5 * 8 - 60 + 8 2 / + 
		//测试 
		//说明为了方便，逆波兰表达式 的数字和符号使用空格隔开
		//String suffixExpression = "30 4 + 5 * 6 -";
		String suffixExpression = "4 5 * 8 - 60 + 8 2 / +"; // 76
		//思路
		//1. 先将 "3 4 + 5 × 6 - " => 放到ArrayList中
		//2. 将 ArrayList 传递给一个方法，遍历 ArrayList 配合栈 完成计算
		List<String> list = getListString(suffixExpression);
		System.out.println("rpnList=" + list);
		int res = calculate(list);
		System.out.println("计算的结果是=" + res);
	}
	
	//将一个逆波兰表达式， 依次将数据和运算符 放入到 ArrayList中
	public static List<String> getListString(String suffixExpression) {
		//将 suffixExpression 分割
		String[] split = suffixExpression.split(" ");
		List<String> list = new ArrayList<String>();
		for(String ele: split) {
			list.add(ele);
		}
		return list;
		
	}
	
	//完成对逆波兰表达式的运算
	/*
	 * 1)从左至右扫描，将3和4压入堆栈；
		2)遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
		3)将5入栈；
		4)接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
		5)将6入栈；
		6)最后是-运算符，计算出35-6的值，即29，由此得出最终结果
	 */
	
	public static int calculate(List<String> ls) {
		// 创建给栈, 只需要一个栈即可
		Stack<String> stack = new Stack<String>();
		// 遍历 ls
		for (String item : ls) {
			// 这里使用正则表达式来区分是否为数字
			if (item.matches("\\d+")) { // 匹配的是多位数
				// 入栈
				stack.push(item);
			} else {
				// pop出两个数，并运算， 再入栈
				int num2 = Integer.parseInt(stack.pop());
				int num1 = Integer.parseInt(stack.pop());
				int res = 0;
				if (item.equals("+")) {
					res = num1 + num2;
				} else if (item.equals("-")) {
					res = num1 - num2;
				} else if (item.equals("*")) {
					res = num1 * num2;
				} else if (item.equals("/")) {
					res = num1 / num2;
				} else {
					throw new RuntimeException("运算符有误");
				}
				//把res 入栈
				stack.push("" + res);
			}
			
		}
		//最后留在stack中的数据是运算结果
		return Integer.parseInt(stack.pop());
	}
   
}
```



完整版

```java
1) 支持 + - * / ( )
2) 多位数，支持小数,
3) 兼容处理, 过滤任何空白字符，包括空格、制表符、换页符
public class ReversePolishMultiCalc {
	/**
	 * 匹配 + - * / ( ) 运算符
	 */
	static final String SYMBOL = "\\+|-|\\*|/|\\(|\\)";
	static final String LEFT = "(";
	static final String RIGHT = ")";
	static final String ADD = "+";
	static final String MINUS = "-";
	static final String TIMES = "*";
	static final String DIVISION = "/";
	/**
	 * 加減 + -
	 */
	static final int LEVEL_01 = 1;
	/**
	 * 乘除 * /
	 */
	static final int LEVEL_02 = 2;
	/**
	 * 括号
	 */
	static final int LEVEL_HIGH = Integer.MAX_VALUE;
	static Stack<String> stack = new Stack<>();
	static List<String> data = Collections
			.synchronizedList(new ArrayList<String>());

	/**
	 * 去除所有空白符
	 * 
	 * @param s
	 * @return
	 */
	public static String replaceAllBlank(String s) {
		// \\s+ 匹配任何空白字符，包括空格、制表符、换页符等等, 等价于[ \f\n\r\t\v]
		return s.replaceAll("\\s+", "");
	}

	/**
	 * 判断是不是数字 int double long float
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(s).matches();
	}

	/**
	 * 判断是不是运算符
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isSymbol(String s) {
		return s.matches(SYMBOL);
	}

	/**
	 * 匹配运算等级
	 * 
	 * @param s
	 * @return
	 */
	public static int calcLevel(String s) {
		if ("+".equals(s) || "-".equals(s)) {
			return LEVEL_01;
		} else if ("*".equals(s) || "/".equals(s)) {
			return LEVEL_02;
		}
		return LEVEL_HIGH;
	}

	/**
	 * 匹配
	 * 
	 * @param s
	 * @throws Exception
	 */
	public static List<String> doMatch(String s) throws Exception {

		if (s == null || "".equals(s.trim()))
			throw new RuntimeException("data is empty");
		if (!isNumber(s.charAt(0) + ""))
			throw new RuntimeException("data illeagle,start not with a number");
		s = replaceAllBlank(s);
		String each;
		int start = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isSymbol(s.charAt(i) + "")) {
				each = s.charAt(i) + "";
				// 栈为空，(操作符，或者 操作符优先级大于栈顶优先级 && 操作符优先级不是( )的优先级 及是 ) 不能直接入栈
				if (stack.isEmpty()
						|| LEFT.equals(each)
						|| ((calcLevel(each) > calcLevel(stack.peek())) && calcLevel(each) < LEVEL_HIGH)) {
					stack.push(each);
				} else if (!stack.isEmpty()
						&& calcLevel(each) <= calcLevel(stack.peek())) {
					// 栈非空，操作符优先级小于等于栈顶优先级时出栈入列，直到栈为空，或者遇到了(，最后操作符入栈
					while (!stack.isEmpty()
							&& calcLevel(each) <= calcLevel(stack.peek())) {
						if (calcLevel(stack.peek()) == LEVEL_HIGH) {
							break;
						}
						data.add(stack.pop());
					}
					stack.push(each);
				} else if (RIGHT.equals(each)) {
					// ) 操作符，依次出栈入列直到空栈或者遇到了第一个)操作符，此时)出栈
					while (!stack.isEmpty()
							&& LEVEL_HIGH >= calcLevel(stack.peek())) {
						if (LEVEL_HIGH == calcLevel(stack.peek())) {
							stack.pop();
							break;
						}
						data.add(stack.pop());
					}
				}
				start = i;
				// 前一个运算符的位置
			} else if (i == s.length() - 1 || isSymbol(s.charAt(i + 1) + "")) {
				each = start == 0 ? s.substring(start, i + 1) : s.substring(
						start + 1, i + 1);
				if (isNumber(each)) {
					data.add(each);
					continue;
				}
				throw new RuntimeException("data not match number");
			}
		}
		// 如果栈里还有元素，此时元素需要依次出栈入列，可以想象栈里剩下栈顶为/，栈底为+，应该依次出栈入列，可以直接翻转整个 stack 添加到队列
		Collections.reverse(stack);
		data.addAll(new ArrayList<>(stack));
		System.out.println(data);
		return data;
	}

	/**
	 * 算出结果
	 * 
	 * @param list
	 * @return
	 */
	public static Double doCalc(List<String> list) {
		Double d = 0d;
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() == 1) {
			System.out.println(list);
			d = Double.valueOf(list.get(0));
			return d;
		}
		ArrayList<String> list1 = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			list1.add(list.get(i));
			if (isSymbol(list.get(i))) {
				Double d1 = doTheMath(list.get(i - 2), list.get(i - 1),
						list.get(i));
				list1.remove(i);
				list1.remove(i - 1);
				list1.set(i - 2, d1 + "");
				list1.addAll(list.subList(i + 1, list.size()));
				break;
			}
		}
		doCalc(list1);
		return d;
	}

	/**
	 * 运算
	 * 
	 * @param s1
	 * @param s2
	 * @param symbol
	 * @return
	 */
	public static Double doTheMath(String s1, String s2, String symbol) {
		Double result;
		switch (symbol) {
		case ADD:
			result = Double.valueOf(s1) + Double.valueOf(s2);
			break;
		case MINUS:
			result = Double.valueOf(s1) - Double.valueOf(s2);
			break;
		case TIMES:
			result = Double.valueOf(s1) * Double.valueOf(s2);
			break;
		case DIVISION:
			result = Double.valueOf(s1) / Double.valueOf(s2);
			break;
		default:
			result = null;
		}
		return result;
	}

	public static void main(String[] args) {
		// String math = "9+(3-1)*3+10/2";
		String math = "12.8 + (2 - 3.55)*4+10/5.0";
		try {
			doCalc(doMatch(math));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

```



## 递归

递归就是方法自己调用自己,每次调用时传入不同的变量.递归有助于编程者解决复杂的问题

![DATA_STRUCTURES_ALGORITHMS36.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS36.png?raw=true)

递归需要遵守的重要规则：

```
1) 执行一个方法时，就创建一个新的受保护的独立空间(栈空间)--递归的底层其实就是使用的栈。
2) 方法的局部变量是独立的，不会相互影响, 比如 n 变量
3) 如果方法中使用的是引用类型变量(比如数组)，就会共享该引用类型的数据.
4) 递归必须向退出递归的条件逼近，否则就是无限递归,出现 StackOverflowError)
5) 当一个方法执行完毕，或者遇到 return，就会返回，遵守谁调用，就将结果返回给谁，同时当方法执行完毕或
者返回时，该方法也就执行完毕
```

### 迷宫问题

递归应用场景

![DATA_STRUCTURES_ALGORITHMS35.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS35.png?raw=true)

```java
public class MiGong {
	public static void main(String[] args) {
		// 先创建一个二维数组，模拟迷宫
		// 地图
		int[][] map = new int[8][7];
		// 使用1 表示墙
		// 上下全部置为1
		for (int i = 0; i < 7; i++) {
			map[0][i] = 1;
			map[7][i] = 1;
		}

		// 左右全部置为1
		for (int i = 0; i < 8; i++) {
			map[i][0] = 1;
			map[i][6] = 1;
		}
		//设置挡板, 1 表示
		map[3][1] = 1;
		map[3][2] = 1;
        // 将节点堵死，验证回溯
		map[1][2] = 1;
		map[2][2] = 1;
		// 输出地图
		System.out.println("地图的情况");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		
		//使用递归回溯给小球找路
		//setWay(map, 1, 1);
		setWay2(map, 1, 1);
		
		//输出新的地图, 小球走过，并标识过的递归
		System.out.println("小球走过，并标识过的 地图的情况");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	//使用递归回溯来给小球找路
	//说明
	//1. map 表示地图
	//2. i,j 表示从地图的哪个位置开始出发 (1,1)
	//3. 如果小球能到 map[6][5] 位置，则说明通路找到.
	//4. 约定： 当map[i][j] 为 0 表示该点没有走过 当为 1 表示墙  ； 2 表示通路可以走 ； 3 表示该点已经走过，但是走不通
	//5. 在走迷宫时，需要确定一个策略(方法) 下->右->上->左 , 如果该点走不通，再回溯
	/**
	 * 
	 * @param map 表示地图
	 * @param i 从哪个位置开始找
	 * @param j 
	 * @return 如果找到通路，就返回true, 否则返回false
	 */
	public static boolean setWay(int[][] map, int i, int j) {
		if(map[6][5] == 2) { // 通路已经找到ok
			return true;
		} else {
			if(map[i][j] == 0) { //如果当前这个点还没有走过
				//按照策略 下->右->上->左  走
				map[i][j] = 2; // 假定该点是可以走通.
				if(setWay(map, i+1, j)) {//向下走
					return true;
				} else if (setWay(map, i, j+1)) { //向右走
					return true;
				} else if (setWay(map, i-1, j)) { //向上
					return true;
				} else if (setWay(map, i, j-1)){ // 向左走
					return true;
				} else {
					//说明该点是走不通，是死路
					map[i][j] = 3;
					return false;
				}
			} else { // 如果map[i][j] != 0 , 可能是 1， 2， 3
				return false;
			}
		}
	}
	
	//修改找路的策略，改成 上->右->下->左
	public static boolean setWay2(int[][] map, int i, int j) {
		if(map[6][5] == 2) { // 通路已经找到ok
			return true;
		} else {
			if(map[i][j] == 0) { //如果当前这个点还没有走过
				//按照策略 上->右->下->左
				map[i][j] = 2; // 假定该点是可以走通.
				if(setWay2(map, i-1, j)) {//向上走
					return true;
				} else if (setWay2(map, i, j+1)) { //向右走
					return true;
				} else if (setWay2(map, i+1, j)) { //向下
					return true;
				} else if (setWay2(map, i, j-1)){ // 向左走
					return true;
				} else {
					//说明该点是走不通，是死路
					map[i][j] = 3;
					return false;
				}
			} else { // 如果map[i][j] != 0 , 可能是 1， 2， 3
				return false;
			}
		}
	}

}

```



### 八皇后问题

![DATA_STRUCTURES_ALGORITHMS37.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS37.png?raw=true)

在 8×8 格的国际象棋上摆放八个皇后，使其不能互相攻击，即：任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法

八皇后问题算法思路分析：

```
1) 第一个皇后先放第一行第一列
2) 第二个皇后放在第二行第一列、然后判断是否 OK， 如果不 OK，继续放在第二列、第三列、依次把所有列都
放完，找到一个合适
3) 继续第三个皇后，还是第一列、第二列……直到第 8 个皇后也能放在一个不冲突的位置，算是找到了一个正确
解
4) 当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，
全部得到.
5) 然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4 的步骤
```

![DATA_STRUCTURES_ALGORITHMS38.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS38.png?raw=true)

```
说明：
理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题. arr[8] =
{0 , 4, 7, 5, 2, 6, 1, 3} //对应 arr 下标 表示第几行，即第几个皇后，arr[i] = val , val 表示第 i+1 个皇后，放在第 i+1
行的第 val+1 列
```

代码实现：

```java
public class Queue8 {

	//定义一个max表示共有多少个皇后
	int max = 8;
	//定义数组array, 保存皇后放置位置的结果,比如 arr = {0 , 4, 7, 5, 2, 6, 1, 3} 
	int[] array = new int[max];
	static int count = 0;
	static int judgeCount = 0;
	public static void main(String[] args) {
		//测试一把 ， 8皇后是否正确
		Queue8 queue8 = new Queue8();
		queue8.check(0);
		System.out.printf("一共有%d解法", count);
		System.out.printf("一共判断冲突的次数%d次", judgeCount); // 1.5w
		
	}
	
	
	
	//编写一个方法，放置第n个皇后
	//特别注意： check 是 每一次递归时，进入到check中都有  for(int i = 0; i < max; i++)，因此会有回溯
	private void check(int n) {
		if(n == max) {  //n = 8 , 其实8个皇后就既然放好
			print();
			return;
		}
		
		//依次放入皇后，并判断是否冲突
		for(int i = 0; i < max; i++) {
			//先把当前这个皇后 n , 放到该行的第1列
			array[n] = i;
			//判断当放置第n个皇后到i列时，是否冲突
			if(judge(n)) { // 不冲突
				//接着放n+1个皇后,即开始递归
				check(n+1); //  
			}
			//如果冲突，就继续执行 array[n] = i; 即将第n个皇后，放置在本行得 后移的一个位置
		}
	}
	
	//查看当我们放置第n个皇后, 就去检测该皇后是否和前面已经摆放的皇后冲突
	/**
	 * 
	 * @param n 表示第n个皇后
	 * @return
	 */
	private boolean judge(int n) {
		judgeCount++;
		for(int i = 0; i < n; i++) {
			// 说明
			//1. array[i] == array[n]  表示判断 第n个皇后是否和前面的n-1个皇后在同一列
            //Math.abs（x） 返回x的绝对值
			//2. Math.abs(n-i) == Math.abs(array[n] - array[i]) 表示判断第n个皇后是否和第i皇后是否在同一斜线
			// n = 1  放置第 2列 1 n = 1 array[1] = 1
			// Math.abs(1-0) == 1  Math.abs(array[n] - array[i]) = Math.abs(1-0) = 1
			//3. 判断是否在同一行, 没有必要，n 每次都在递增
			if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i]) ) {
				return false;
			}
		}
		return true;
	}
	
	//写一个方法，可以将皇后摆放的位置输出
	private void print() {
		count++;
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

}

```



## 哈希表

```
看一个实际需求，google 公司的一个上机题:
有一个公司,当有新的员工来报道时,要求将该员工的信息加入(id,性别,年龄,住址..),当输入该员工的 id 时,要求查
找到该员工的 所有信息.
要求: 不使用数据库,尽量节省内存,速度越快越好
=>哈希表(散列)
```

![DATA_STRUCTURES_ALGORITHMS69.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS69.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS70.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS70.png?raw=true)

```java
//表示一个雇员
class Emp {
	public int id;
	public String name;
	public Emp next; //next 默认为 null
}

//创建EmpLinkedList ,表示链表
class EmpLinkedList {
	//头指针，执行第一个Emp,因此我们这个链表的head 是直接指向第一个Emp
	private Emp head; //默认null
	
	//添加雇员到链表
	//说明
	//1. 假定，当添加雇员时，id 是自增长，即id的分配总是从小到大
	//   因此我们将该雇员直接加入到本链表的最后即可
	public void add(Emp emp) {
		//如果是添加第一个雇员
		if(head == null) {
			head = emp;
			return;
		}
		//如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
		Emp curEmp = head;
		while(true) {
			if(curEmp.next == null) {//说明到链表最后
				break;
			}
			curEmp = curEmp.next; //后移
		}
		//退出时直接将emp 加入链表
		curEmp.next = emp;
	}
	
	//遍历链表的雇员信息
	public void list(int no) {
		if(head == null) { //说明链表为空
			System.out.println("第 "+(no+1)+" 链表为空");
			return;
		}
		System.out.print("第 "+(no+1)+" 链表的信息为");
		Emp curEmp = head; //辅助指针
		while(true) {
			System.out.printf(" => id=%d name=%s\t", curEmp.id, curEmp.name);
			if(curEmp.next == null) {//说明curEmp已经是最后结点
				break;
			}
			curEmp = curEmp.next; //后移，遍历
		}
		System.out.println();
	}
	
	//根据id查找雇员
	//如果查找到，就返回Emp, 如果没有找到，就返回null
	public Emp findEmpById(int id) {
		//判断链表是否为空
		if(head == null) {
			System.out.println("链表为空");
			return null;
		}
		//辅助指针
		Emp curEmp = head;
		while(true) {
			if(curEmp.id == id) {//找到
				break;//这时curEmp就指向要查找的雇员
			}
			//退出
			if(curEmp.next == null) {//说明遍历当前链表没有找到该雇员
				curEmp = null;
				break;
			}
			curEmp = curEmp.next;//以后
		}
		
		return curEmp;
	}
	
}

//创建HashTab 管理多条链表
// 哈希表之所以可以提高效率就是因为他管理着多条链表
class HashTab {
	private EmpLinkedList[] empLinkedListArray;
	private int size; //表示有多少条链表
	
	//构造器
	public HashTab(int size) {
		this.size = size;
		//初始化empLinkedListArray
		empLinkedListArray = new EmpLinkedList[size];
        // 这里EmpLinkedList中的元素是null ,不能直接调用EmpLinkedList[n].add()
		//？留一个坑, 这时不要忘分别初始化每个链表
		for(int i = 0; i < size; i++) {
			empLinkedListArray[i] = new EmpLinkedList();
		}
	}
	
	//添加雇员
	public void add(Emp emp) {
		//根据员工的id ,得到该员工应当添加到哪条链表
		int empLinkedListNO = hashFun(emp.id);
		//将emp 添加到对应的链表中
		empLinkedListArray[empLinkedListNO].add(emp);
		
	}
	//遍历所有的链表,遍历hashtab
	public void list() {
		for(int i = 0; i < size; i++) {
			empLinkedListArray[i].list(i);
		}
	}
	
	//根据输入的id,查找雇员
	public void findEmpById(int id) {
		//使用散列函数确定到哪条链表查找
		int empLinkedListNO = hashFun(id);
		Emp emp = empLinkedListArray[empLinkedListNO].findEmpById(id);
		if(emp != null) {//找到
			System.out.printf("在第%d条链表中找到 雇员 id = %d\n", (empLinkedListNO + 1), id);
		}else{
			System.out.println("在哈希表中，没有找到该雇员~");
		}
	}
	
	//编写散列函数, 使用一个简单取模法
	public int hashFun(int id) {
		return id % size;
	}	
}
```



