# java数据结构与算法



## 树

### 树结构的基础

**为什么需要树这种数据结构？**

1) 数组存储方式的分析
优点：通过下标方式访问元素，速度快。对于有序数组，还可使用二分查找提高检索速度。
缺点：如果要检索具体某个值，或者插入值(按一定顺序)会整体移动，效率较低、

![DATA_STRUCTURES_ALGORITHMS71.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS71.png?raw=true)

2) 链式存储方式的分析
优点：在一定程度上对数组存储方式有优化(比如：插入一个数值节点，只需要将插入节点，链接到链表中即可，
删除效率也很好)。
缺点：在进行检索时，效率仍然较低，比如(检索某个值，需要从头节点开始遍历)

![DATA_STRUCTURES_ALGORITHMS72.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS72.png?raw=true)

3) 树存储方式的分析
能提高数据存储，读取的效率, 比如利用 二叉排序树(Binary Sort Tree)，既可以保证数据的检索速度，同时也
可以保证数据的插入，删除，修改的速度

![DATA_STRUCTURES_ALGORITHMS73.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS73.png?raw=true)

树的底层实现其实也是链表。(进一步说明，数组与链表是组成数据结构的基石)

#### 二叉树的概率和术语

![DATA_STRUCTURES_ALGORITHMS74.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS74.png?raw=true)

二叉树： 节点的子结点数不大于两个的树

#### 二叉树的遍历

```
使用前序，中序和后序对下面的二叉树进行遍历.
1) 前序遍历: 先输出父节点，再遍历左子树和右子树
2) 中序遍历: 先遍历左子树，再输出父节点，再遍历右子树
3) 后序遍历: 先遍历左子树，再遍历右子树，最后输出父节点
4) 小结: 看输出父节点的顺序，就确定是前序，中序还是后序
(中序遍历就是我们的顺序遍历)
```

![DATA_STRUCTURES_ALGORITHMS75.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS75.png?raw=true)

```java
 //先创建HeroNode 结点
class HeroNode {
	private int no;
	private String name;
	private HeroNode left; //默认null
	private HeroNode right; //默认null
	public HeroNode(int no, String name) {
		this.no = no;
		this.name = name;
	}
	//编写前序遍历的方法
	public void preOrder() {
		System.out.println(this); //先输出父结点
		//递归向左子树前序遍历
		if(this.left != null) {
			this.left.preOrder();
		}
		//递归向右子树前序遍历
		if(this.right != null) {
			this.right.preOrder();
		}
	}
	//中序遍历
	public void infixOrder() {
		//递归向左子树中序遍历
		if(this.left != null) {
			this.left.infixOrder();
		}
		//输出父结点
		System.out.println(this);
		//递归向右子树中序遍历
		if(this.right != null) {
			this.right.infixOrder();
		}
	}
	//后序遍历
	public void postOrder() {
		if(this.left != null) {
			this.left.postOrder();
		}
		if(this.right != null) {
			this.right.postOrder();
		}
		System.out.println(this);
	}	
}
```

节点是对遍历方法的真正实现，而二叉树是对遍历方法的调用

```java
//定义BinaryTree 二叉树
class BinaryTree {
	private HeroNode root;
	public void setRoot(HeroNode root) {
		this.root = root;
	}
	//删除结点
	public void delNode(int no) {
		if(root != null) {
			//如果只有一个root结点, 这里立即判断root是不是就是要删除结点
			if(root.getNo() == no) {
				root = null;
			} else {
				//递归删除
				root.delNode(no);
			}
		}else{
			System.out.println("空树，不能删除~");
		}
	}
	//前序遍历
	public void preOrder() {
		if(this.root != null) {
			this.root.preOrder();
		}else {
			System.out.println("二叉树为空，无法遍历");
		}
	}
	
	//中序遍历
	public void infixOrder() {
		if(this.root != null) {
			this.root.infixOrder();
		}else {
			System.out.println("二叉树为空，无法遍历");
		}
	}
	//后序遍历
	public void postOrder() {
		if(this.root != null) {
			this.root.postOrder();
		}else {
			System.out.println("二叉树为空，无法遍历");
		}
	}
	
	//前序遍历
	public HeroNode preOrderSearch(int no) {
		if(root != null) {
			return root.preOrderSearch(no);
		} else {
			return null;
		}
	}
	//中序遍历
	public HeroNode infixOrderSearch(int no) {
		if(root != null) {
			return root.infixOrderSearch(no);
		}else {
			return null;
		}
	}
	//后序遍历
	public HeroNode postOrderSearch(int no) {
		if(root != null) {
			return this.root.postOrderSearch(no);
		}else {
			return null;
		}
	}
}
```

对二叉树遍历的测试：

```java
	public static void main(String[] args) {
		//先需要创建一颗二叉树
		BinaryTree binaryTree = new BinaryTree();
		//创建需要的结点
		HeroNode root = new HeroNode(1, "宋江");
		HeroNode node2 = new HeroNode(2, "吴用");
		HeroNode node3 = new HeroNode(3, "卢俊义");
		HeroNode node4 = new HeroNode(4, "林冲");
		HeroNode node5 = new HeroNode(5, "关胜");
		
		//说明，我们先手动创建该二叉树，后面我们学习递归的方式创建二叉树
		root.setLeft(node2);
		root.setRight(node3);
		node3.setRight(node4);
		node3.setLeft(node5);
		binaryTree.setRoot(root);
//测试
		System.out.println("前序遍历"); // 1,2,3,5,4
     	binaryTree.preOrder();
		
		System.out.println("中序遍历");
		binaryTree.infixOrder(); // 2,1,5,3,4
        
		System.out.println("后序遍历");
		binaryTree.postOrder(); // 2,5,4,3,1
    }
```



#### 二叉树前中后序查找

树查找思路：

![DATA_STRUCTURES_ALGORITHMS76.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS76.png?raw=true)

```java
//前序遍历查找
	/**
	 * 
	 * @param no 查找no
	 * @return 如果找到就返回该Node ,如果没有找到返回 null
	 */
	public HeroNode preOrderSearch(int no) {
		System.out.println("进入前序遍历");
		//比较当前结点是不是
		if(this.no == no) {
			return this;
		}
		//1.则判断当前结点的左子节点是否为空，如果不为空，则递归前序查找
		//2.如果左递归前序查找，找到结点，则返回
		HeroNode resNode = null;
		if(this.left != null) {
			resNode = this.left.preOrderSearch(no);
		}
		if(resNode != null) {//说明我们左子树找到
			return resNode;
		}
		//1.左递归前序查找，找到结点，则返回，否继续判断，
		//2.当前的结点的右子节点是否为空，如果不空，则继续向右递归前序查找
		if(this.right != null) {
			resNode = this.right.preOrderSearch(no);
		}
		return resNode;
	}
	
	//中序遍历查找
	public HeroNode infixOrderSearch(int no) {
		//判断当前结点的左子节点是否为空，如果不为空，则递归中序查找
		HeroNode resNode = null;
		if(this.left != null) {
			resNode = this.left.infixOrderSearch(no);
		}
		if(resNode != null) {
			return resNode;
		}
		System.out.println("进入中序查找");
		//如果找到，则返回，如果没有找到，就和当前结点比较，如果是则返回当前结点
		if(this.no == no) {
			return this;
		}
		//否则继续进行右递归的中序查找
		if(this.right != null) {
			resNode = this.right.infixOrderSearch(no);
		}
		return resNode;
		
	}
	
	//后序遍历查找
	public HeroNode postOrderSearch(int no) {
		
		//判断当前结点的左子节点是否为空，如果不为空，则递归后序查找
		HeroNode resNode = null;
		if(this.left != null) {
			resNode = this.left.postOrderSearch(no);
		}
		if(resNode != null) {//说明在左子树找到
			return resNode;
		}
		
		//如果左子树没有找到，则向右子树递归进行后序遍历查找
		if(this.right != null) {
			resNode = this.right.postOrderSearch(no);
		}
		if(resNode != null) {
			return resNode;
		}
		System.out.println("进入后序查找");
		//如果左右子树都没有找到，就比较当前结点是不是
		if(this.no == no) {
			return this;
		}
		return resNode;
	}

// tree 中：
//前序遍历
	public HeroNode preOrderSearch(int no) {
		if(root != null) {
			return root.preOrderSearch(no);
		} else {
			return null;
		}
	}
	//中序遍历
	public HeroNode infixOrderSearch(int no) {
		if(root != null) {
			return root.infixOrderSearch(no);
		}else {
			return null;
		}
	}
	//后序遍历
	public HeroNode postOrderSearch(int no) {
		if(root != null) {
			return this.root.postOrderSearch(no);
		}else {
			return null;
		}
	}
```

#### 二叉树的删除

##### 简单删除

(先简单删除， 即删除非叶子节点的时候规定将删除其子树。 因为如果是非规则树，删除非叶子节点，无论是将其左右子节点提上去都没有太大的意义)、

```
 要求
1) 如果删除的节点是叶子节点，则删除该节点
2) 如果删除的节点是非叶子节点，则删除该子树
```

![DATA_STRUCTURES_ALGORITHMS77.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS77.png?raw=true)

```java
HeroNode中：
//递归删除结点
	//1.如果删除的节点是叶子节点，则删除该节点
	//2.如果删除的节点是非叶子节点，则删除该子树
	public void delNode(int no) {
		
		//思路
		/*
		 * 	1. 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要删除结点，而不能去判断当前这个结点是不是需要删除结点.
			2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将this.left = null; 并且就返回(结束递归删除)
			3. 如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null ;并且就返回(结束递归删除)
			4. 如果第2和第3步没有删除结点，那么我们就需要向左子树进行递归删除
			5.  如果第4步也没有删除结点，则应当向右子树进行递归删除.

		 */
		//2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将this.left = null; 并且就返回(结束递归删除)
		if(this.left != null && this.left.no == no) {
			this.left = null;
			return;
		}
		//3.如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null ;并且就返回(结束递归删除)
		if(this.right != null && this.right.no == no) {
			this.right = null;
			return;
		}
		//4.我们就需要向左子树进行递归删除
		if(this.left != null) {
			this.left.delNode(no);
		}
		//5.则应当向右子树进行递归删除
		if(this.right != null) {
			this.right.delNode(no);
		}
	}

// tree 中：
//删除结点
	public void delNode(int no) {
		if(root != null) {
			//如果只有一个root结点, 这里立即判断root是不是就是要删除结点
			if(root.getNo() == no) {
				root = null;
			} else {
				//递归删除
				root.delNode(no);
			}
		}else{
			System.out.println("空树，不能删除~");
		}
	}
```



#### 顺序存储二叉树

![DATA_STRUCTURES_ALGORITHMS78.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS78.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS79.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS79.png?raw=true)

```java
//编写一个ArrayBinaryTree, 实现顺序存储二叉树遍历
int[] arr = { 1, 2, 3, 4, 5, 6, 7 };
//创建一个 ArrBinaryTree
ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
arrBinaryTree.preOrder(); // 1,2,4,5,3,6,7
class ArrBinaryTree {
	private int[] arr;//存储数据结点的数组

	public ArrBinaryTree(int[] arr) {
		this.arr = arr;
	}
	
	//重载preOrder
	public void preOrder() {
		this.preOrder(0);
	}
	
	//编写一个方法，完成顺序存储二叉树的前序遍历
	/**
	 * 
	 * @param index 数组的下标 
	 */
	public void preOrder(int index) {
		//如果数组为空，或者 arr.length = 0
		if(arr == null || arr.length == 0) {
			System.out.println("数组为空，不能按照二叉树的前序遍历");
		}
		//输出当前这个元素
		System.out.println(arr[index]); 
		//向左递归遍历
		if((index * 2 + 1) < arr.length) {
			preOrder(2 * index + 1 );
		}
		//向右递归遍历
		if((index * 2 + 2) < arr.length) {
			preOrder(2 * index + 2);
		}
	}
	
}
```

```
顺序存储二叉树应用实例
八大排序算法中的堆排序，就会使用到顺序存储二叉树， 关于堆排序，我们放在<<树结构实际应用>>
```



#### 线索化二叉树

![DATA_STRUCTURES_ALGORITHMS80.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS80.png?raw=true)

```
线索二叉树基本介绍
1) n 个结点的二叉链表中含有 n+1 【公式 2n-(n-1)=n+1】 个空指针域。利用二叉链表中的空指针域，存放指向
该结点在某种遍历次序下的前驱和后继结点的指针（这种附加的指针称为"线索"）
2) 这种加上了线索的二叉链表称为线索链表，相应的二叉树称为线索二叉树(Threaded BinaryTree)。根据线索性质
的不同，线索二叉树可分为前序线索二叉树、中序线索二叉树和后序线索二叉树三种
3) 一个结点的前一个结点，称为前驱结点
4) 一个结点的后一个结点，称为后继结点
```

![DATA_STRUCTURES_ALGORITHMS81.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS81.png?raw=true)

中序线索化二叉树：

```java
//先创建HeroNode 结点
class HeroNode {
	private int no;
	private String name;
	private HeroNode left; //默认null
	private HeroNode right; //默认null
	//说明
	//1. 如果leftType == 0 表示指向的是左子树, 如果 1 则表示指向前驱结点
	//2. 如果rightType == 0 表示指向是右子树, 如果 1表示指向后继结点
	private int leftType;
	private int rightType;
}

//定义ThreadedBinaryTree 实现了线索化功能的二叉树
class ThreadedBinaryTree {
	private HeroNode root;
	
	//为了实现线索化，需要创建要给指向当前结点的前驱结点的指针
	//在递归进行线索化时，pre 总是保留前一个结点
	private HeroNode pre = null;
	//重载一把threadedNodes方法
	public void threadedNodes() {
		this.threadedNodes(root);
	}
	
	//编写对二叉树进行中序线索化的方法
	/**
	 * 
	 * @param node 就是当前需要线索化的结点
	 */
	public void threadedNodes(HeroNode node) {
		
		//如果node==null, 不能线索化
		if(node == null) {
			return;
		}
		
		//(一)先线索化左子树
		threadedNodes(node.getLeft());
		//(二)线索化当前结点[有难度]
		
		//处理当前结点的前驱结点
		//以8结点来理解
		//8结点的.left = null , 8结点的.leftType = 1
		if(node.getLeft() == null) {
			//让当前结点的左指针指向前驱结点 
			node.setLeft(pre); 
			//修改当前结点的左指针的类型,指向前驱结点
			node.setLeftType(1);
		}
		
		//处理后继结点
		if (pre != null && pre.getRight() == null) {
			//让前驱结点的右指针指向当前结点
			pre.setRight(node);
			//修改前驱结点的右指针类型
			pre.setRightType(1);
		}
		//!!! 每处理一个结点后，让当前结点是下一个结点的前驱结点
		pre = node;
		
		//(三)在线索化右子树
		threadedNodes(node.getRight());
	}
    
    //线索化后各个节点的指向有变化了，原来的遍历方式无法使用了。
	//遍历线索化二叉树的方法
	public void threadedList() {
		//定义一个变量，存储当前遍历的结点，从root开始
		HeroNode node = root;
		while(node != null) {
			//循环的找到leftType == 1的结点，第一个找到就是8结点
			//后面随着遍历而变化,因为当leftType==1时，说明该结点是按照线索化
			//处理后的有效结点
			while(node.getLeftType() == 0) {
				node = node.getLeft();
			}
			
			//打印当前这个结点
			System.out.println(node);
			//如果当前结点的右指针指向的是后继结点,就一直输出
			while(node.getRightType() == 1) {
				//获取到当前结点的后继结点
				node = node.getRight();
				System.out.println(node);
			}
			//替换这个遍历的结点
			node = node.getRight();
			
		}
	}
   
```

```
遍历线索化二叉树
1) 说明：对前面的中序线索化的二叉树， 进行遍历
2) 分析：因为线索化后，“各个结点指向有变化，因此原来的遍历方式不能使用”，这时需要使用新的方式遍历
线索化二叉树，各个节点可以通过线型方式遍历，因此无需使用递归方式，这样也提高了遍历的效率。 遍历的次
序应当和中序遍历保持一致。
```



### 树结构的实际应用

#### 堆排序

堆是比较特殊的二叉树。

```
堆排序基本介绍
1) 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复
杂度均为 O(nlogn)，它也是不稳定排序。
2) 堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆, 注意 : 没有
要求结点的左孩子的值和右孩子的值的大小关系。
3) 每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆
6) 在进行堆排序的时候一般升序采用大顶堆，降序采用小顶堆
```

![DATA_STRUCTURES_ALGORITHMS82.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS82.png?raw=true)

```
堆排序基本思想
堆排序的基本思想是：
1) 将待排序序列构造成一个大顶堆  (数组，在这里树是以数组的形式存放的)
2) 此时，整个序列的最大值就是堆顶的根节点。
3) 将其与末尾元素进行交换，此时末尾就为最大值。
4) 然后将剩余 n-1 个元素重新构造成一个堆，这样会得到 n 个元素的次小值。如此反复执行，便能得到一个有序
序列了。
可以看到在构建大顶堆的过程中，元素的个数逐渐减少，最后就得到一个有序序列了
```

堆排序示例图：

![DATA_STRUCTURES_ALGORITHMS83.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS83.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS84.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS84.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS85.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS85.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS86.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS86.png?raw=true)

```
再简单总结下堆排序的基本思路：
1).将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆;
2).将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
3).重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，
直到整个序列有序。
```

代码实现： 

```java
//要求将数组进行升序排序
int arr[] = {4, 6, 8, 5, 9};
//将一个数组(二叉树), 调整成一个大顶堆
	/**
	 * 功能： 完成 将 以 i 对应的非叶子结点的树调整成大顶堆
	 * 举例  int arr[] = {4, 6, 8, 5, 9}; => i = 1 => adjustHeap => 得到 {4, 9, 8, 5, 6} 
	 * 就是将i节点的子树调整为大顶堆
	 * 如果我们再次调用  adjustHeap 传入的是 i = 0 => 得到 {4, 9, 8, 5, 6} => {9,6,8,5, 4}
	 * @param arr 待调整的数组
	 * @param i 表示非叶子结点在数组中索引
	 * @param lenght 表示对多少个元素继续调整， length 是在逐渐的减少
	 */
	public  static void adjustHeap(int arr[], int i, int lenght) {
		
		int temp = arr[i];//先取出当前元素的值，保存在临时变量
		//开始调整
		//说明
		//1. k = i * 2 + 1 k 是 i结点的左子结点
        //k = k * 2 + 1 下次再调整的就是该节点的下一个左子节点
		for(int k = i * 2 + 1; k < lenght; k = k * 2 + 1) {
			if(k+1 < lenght && arr[k] < arr[k+1]) { //说明左子结点的值小于右子结点的值
				k++; // k 指向右子结点
			}
            //此时k已经指向了子节点中的最大值。
			if(arr[k] > temp) { //如果子结点大于父结点
				arr[i] = arr[k]; //把较大的值赋给当前结点
				i = k; //!!! i 指向 k，就是活动节点下移,继续循环比较
			} else {
				break;
             //! 这里可以break的原因是在处理的时候我们是从左到右，从下至上调整。节点的下面其实已经调整过了
			}
		}
		//当for 循环结束后，我们已经将以i 为父结点的树的最大值，放在了 最顶(局部)
        //到这里的时候i已经改变了，他已经是k了。
		arr[i] = temp;//将temp值放到调整后的位置
	}

//编写一个堆排序的方法
	public static void heapSort(int arr[]) {
		int temp = 0;
		System.out.println("堆排序!!");
		
//		//分步完成
//		adjustHeap(arr, 1, arr.length);
//		System.out.println("第一次" + Arrays.toString(arr)); // 4, 9, 8, 5, 6
//		
//		adjustHeap(arr, 0, arr.length);
//		System.out.println("第2次" + Arrays.toString(arr)); // 9,6,8,5,4
		
		//完成我们最终代码
		//将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
		for(int i = arr.length / 2 -1; i >=0; i--) {
			adjustHeap(arr, i, arr.length);
		}
		
		/*
		 * 2).将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
　　			3).重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
		 */
		for(int j = arr.length-1;j >0; j--) {
			//交换
			temp = arr[j];
			arr[j] = arr[0];
			arr[0] = temp;
			adjustHeap(arr, 0, j); 
		}
		
		//System.out.println("数组=" + Arrays.toString(arr)); 
		
	}
```

![DATA_STRUCTURES_ALGORITHMS87.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS87.png?raw=true)

#### 赫夫曼树

哈夫曼树就是带权最短树

```
基本介绍
1) 给定 n 个权值作为 n 个叶子结点，构造一棵二叉树，若该树的带权路径长度(wpl)达到最小，称这样的二叉树为
最优二叉树，也称为哈夫曼树(Huffman Tree), 还有的书翻译为霍夫曼树。
2) 赫夫曼树是带权路径长度最短的树，权值较大的结点离根较近
赫夫曼树几个重要概念和举例说明
1) 路径和路径长度：在一棵树中，从一个结点往下可以达到的孩子或孙子结点之间的通路，称为路径。通路
中分支的数目称为路径长度。若规定根结点的层数为 1，则从根结点到第 L 层结点的路径长度为 L-1
就是根节点到目标节点的边的条数。
2) 结点的权及带权路径长度：若将树中结点赋给一个有着某种含义的数值，则这个数值称为该结点的权。结
点的带权路径长度为：从根结点到该结点之间的路径长度与该结点的权的乘积
3) 树的带权路径长度：树的带权路径长度规定为所有“叶子结点”的带权路径长度之和，记为 WPL(weighted path
length) ,权值越大的结点离根结点越近的二叉树才是最优二叉树。
```

![DATA_STRUCTURES_ALGORITHMS88.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS88.png?raw=true)

```
赫夫曼树创建思路图解
给你一个数列 {13, 7, 8, 3, 29, 6, 1}，要求转成一颗赫夫曼树.
思路分析：
{13, 7, 8, 3, 29, 6, 1}
构成赫夫曼树的步骤：
1) 从小到大进行排序, 将每一个数据，每个数据都是一个节点 ， 每个节点可以看成是一颗最简单的二叉树（左右子节点为null）
2) 取出根节点权值最小的两颗二叉树（因为之前已经排序，就是取出数组第一个和第二个）
3) 组成一颗新的二叉树, 该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
4) 再将这颗新的二叉树，以根节点的权值大小 再次排序， 不断重复 1-2-3-4 的步骤，直到数列中，所有的数
据都被处理，就得到一颗赫夫曼树
```

![DATA_STRUCTURES_ALGORITHMS89.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS89.png?raw=true)

代码实现：

```java
// 创建结点类
// 为了让Node 对象持续排序Collections集合排序
// 让Node 实现Comparable接口
class Node implements Comparable<Node> {
	int value; // 结点权值
	char c; //字符
	Node left; // 指向左子结点
	Node right; // 指向右子结点

	//写一个前序遍历
	public void preOrder() {
		System.out.println(this);
		if(this.left != null) {
			this.left.preOrder();
		}
		if(this.right != null) {
			this.right.preOrder();
		}
	}
	
	public Node(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}

	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		// 表示从小到大排序
		return this.value - o.value;
	}

}

// 创建赫夫曼树的方法
	/**
	 * 
	 * @param arr 需要创建成哈夫曼树的数组
	 * @return 创建好后的赫夫曼树的root结点
	 */
	public static Node createHuffmanTree(int[] arr) {
		// 第一步为了操作方便
		// 1. 遍历 arr 数组
		// 2. 将arr的每个元素构成成一个Node
		// 3. 将Node 放入到ArrayList中
		List<Node> nodes = new ArrayList<Node>();
		for (int value : arr) {
			nodes.add(new Node(value));
		}
		//我们处理的过程是一个循环的过程
		while(nodes.size() > 1) {
			//排序 从小到大 
			Collections.sort(nodes);
			System.out.println("nodes =" + nodes);
			//取出根节点权值最小的两颗二叉树 
			//(1) 取出权值最小的结点（二叉树）
			Node leftNode = nodes.get(0);
			//(2) 取出权值第二小的结点（二叉树）
			Node rightNode = nodes.get(1);
			
			//(3)构建一颗新的二叉树
			Node parent = new Node(leftNode.value + rightNode.value);
			parent.left = leftNode;
			parent.right = rightNode;
			
			//(4)从ArrayList删除处理过的二叉树
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			//(5)将parent加入到nodes
			nodes.add(parent);
		}
		//返回哈夫曼树的root结点
		return nodes.get(0);	
	}

```



#### 赫夫曼编码

利用赫夫曼树特性形成的编码。 他是属于一种算法。赫夫曼编码是赫哈夫曼树在电讯通信中的经典的应用之一。

赫夫曼编码广泛地用于数据文件压缩解压。其压缩率通常在 20%～90%之间。其内容重复的越多，压缩率越大
赫夫曼码是可变字长编码(VLC)的一种。Huffman 于 1952 年提出一种编码方法，称之为最佳编码

原理剖析：

![DATA_STRUCTURES_ALGORITHMS90.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS90.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS91.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS91.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS92.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS92.png?raw=true)

注意事项
注意, 这个赫夫曼树根据排序方法不同，也可能不太一样，这样对应的赫夫曼编码也不完全一样，但是 wpl 是
一样的，都是最小的, 最后生成的赫夫曼编码的长度是一样，比如: 如果我们让每次生成的新的二叉树总是排在权
值相同的二叉树的最后一个，则生成的二叉树为:

![DATA_STRUCTURES_ALGORITHMS93.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS93.png?raw=true)

##### 最佳实践-数据压缩(创建赫夫曼树)	

将给出的一段文本，比如 "i like like like java do you like a java" ， 根据前面的讲的赫夫曼编码原理，对其进行数
据压缩处理，形式如
"1010100110111101111010011011110111101001101111011110100001100001110011001111000011001111000100100100110111101111011100100001100001110"
步骤 1：根据赫夫曼编码压缩数据的原理，需要创建 "i like like like java do you like a java" 对应的赫夫曼树.

![DATA_STRUCTURES_ALGORITHMS94.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS94.png?raw=true)

代码实现：

第一步：创建node节点：

```java
//创建Node ,待数据和权值
class Node implements Comparable<Node>  {
	Byte data; // 存放数据(字符)本身，比如'a' => 97 ' ' => 32
	int weight; //权值, 表示字符出现的次数
	Node left;//
	Node right;
	public Node(Byte data, int weight) {
		
		this.data = data;
		this.weight = weight;
	}
	@Override
	public int compareTo(Node o) {
		// 从小到大排序
		return this.weight - o.weight;
	}
	
	public String toString() {
		return "Node [data = " + data + " weight=" + weight + "]";
	}
	
	//前序遍历
	public void preOrder() {
		System.out.println(this);
		if(this.left != null) {
			this.left.preOrder();
		}
		if(this.right != null) {
			this.right.preOrder();
		}
	}
}
```

将字符串转换成字节数组：

```java
		String content = "i like like like java do you like a java";
		byte[] contentBytes = content.getBytes();
		System.out.println(contentBytes.length); //40
```

根据字节数组生成List<node>

```java
/**
	 * 
	 * @param bytes 接收字节数组
	 * @return 返回的就是 List 形式   [Node[date=97 ,weight = 5], Node[]date=32,weight = 9]......],
	 */
	private static List<Node> getNodes(byte[] bytes) {
		//1创建一个ArrayList
		ArrayList<Node> nodes = new ArrayList<Node>();
		//遍历 bytes , 统计 每一个byte出现的次数->map[key,value]
		Map<Byte, Integer> counts = new HashMap<>();
		for (byte b : bytes) {
			Integer count = counts.get(b);
			if (count == null) { // Map还没有这个字符数据,第一次
				counts.put(b, 1);
			} else {
				counts.put(b, count + 1);
			}
		}

		//把每一个键值对转成一个Node 对象，并加入到nodes集合
		//遍历map
		for(Map.Entry<Byte, Integer> entry: counts.entrySet()) {
			nodes.add(new Node(entry.getKey(), entry.getValue()));
		}
		return nodes;
		
	}
```

通过list生成赫夫曼树:

```java
//可以通过List 创建对应的赫夫曼树
	private static Node createHuffmanTree(List<Node> nodes) {	
		while(nodes.size() > 1) {
			//排序, 从小到大
			Collections.sort(nodes);
			//取出第一颗最小的二叉树
			Node leftNode = nodes.get(0);
			//取出第二颗最小的二叉树
			Node rightNode = nodes.get(1);
			//创建一颗新的二叉树,它的根节点 没有data, 只有权值
			Node parent = new Node(null, leftNode.weight + rightNode.weight);
			parent.left = leftNode;
			parent.right = rightNode;
			
			//将已经处理的两颗二叉树从nodes删除
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			//将新的二叉树，加入到nodes
			nodes.add(parent);	
		}
		//nodes 最后的结点，就是赫夫曼树的根结点
		return nodes.get(0);
		
	}
```

根据生成的赫夫曼树生成对应的赫夫曼编码表：左边路径为0，右边路径为1

![DATA_STRUCTURES_ALGORITHMS95.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS95.png?raw=true)

```java
/生成赫夫曼树对应的赫夫曼编码
	//思路:
	//1. 将赫夫曼编码表存放在 Map<Byte,String> 形式
	//   生成的赫夫曼编码表{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
	static Map<Byte, String> huffmanCodes = new HashMap<Byte,String>();
	//2. 在生成赫夫曼编码表示，需要去拼接路径, 定义一个StringBuilder 存储某个叶子结点的路径
	static StringBuilder stringBuilder = new StringBuilder();
	
	/**
	 * 功能：将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
	 * @param node  传入结点
	 * @param code  路径： 左子结点是 0, 右子结点 1
	 * @param stringBuilder 用于拼接路径
	 */
	private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
		StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
		//将code 加入到 stringBuilder2
		stringBuilder2.append(code);
		if(node != null) { //如果node == null不处理
			//判断当前node 是叶子结点还是非叶子结点
			if(node.data == null) { //非叶子结点
				//递归处理
				//向左递归
				getCodes(node.left, "0", stringBuilder2);
				//向右递归
				getCodes(node.right, "1", stringBuilder2);
			} else { //说明是一个叶子结点
				//就表示找到某个叶子结点的最后
				huffmanCodes.put(node.data, stringBuilder2.toString());
			}
		}
	}

	//为了调用方便，我们重载 getCodes，给我们一个根节点就给你返回赫夫曼编码表。
   //否则就要： getCodes(root, "", stringBuilder);
	private static Map<Byte, String> getCodes(Node root) {
		if(root == null) {
			return null;
		}
		//处理root的左子树
		getCodes(root.left, "0", stringBuilder);
		//处理root的右子树
		getCodes(root.right, "1", stringBuilder);
		return huffmanCodes;
	}
```

用赫夫曼编码表来生成对应的赫夫曼编码数据：
![DATA_STRUCTURES_ALGORITHMS96.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS96.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS97.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS97.png?raw=true)

```java
//编写一个方法，将字符串对应的byte[] 数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码 压缩后的byte[]
	/**
	 * 
	 * @param bytes 这时原始的字符串对应的 byte[]
	 * @param huffmanCodes 生成的赫夫曼编码map
	 * @return 返回赫夫曼编码处理后的 byte[] 
	 * 举例： String content = "i like like like java do you like a java"; =》 byte[] contentBytes = content.getBytes();
	 * 返回的是 字符串 "1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
	 * => 对应的 byte[] huffmanCodeBytes  ，即 8位对应一个 byte,放入到 huffmanCodeBytes
	 * huffmanCodeBytes[0] =  10101000(补码) => byte  [推导  10101000=> 10101000 - 1 => 10100111(反码)=> 11011000= -88 ]
	 * huffmanCodeBytes[1] = -88
	 这里如果不以8位进行一个处理就变得更长了，就更加的不划算了(主要目的是为了节省空间)
	 */
	private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
		
		//1.利用 huffmanCodes 将  bytes 转成  赫夫曼编码对应的字符串
		StringBuilder stringBuilder = new StringBuilder();
		//遍历bytes 数组 
		for(byte b: bytes) {
			stringBuilder.append(huffmanCodes.get(b));
		}
		
		//System.out.println("测试 stringBuilder~~~=" + stringBuilder.toString());
		//将 "1010100010111111110..." 转成 byte[]
		
		//统计返回  byte[] huffmanCodeBytes 长度
		//一句话 int len = (stringBuilder.length() + 7) / 8;
		int len;
		if(stringBuilder.length() % 8 == 0) {
			len = stringBuilder.length() / 8;
		} else {
			len = stringBuilder.length() / 8 + 1;
		}
		//创建 存储压缩后的 byte数组
		byte[] huffmanCodeBytes = new byte[len];
		int index = 0;//记录是第几个byte
		for (int i = 0; i < stringBuilder.length(); i += 8) { //因为是每8位对应一个byte,所以步长 +8
				String strByte;
				if(i+8 > stringBuilder.length()) {//不够8位
					strByte = stringBuilder.substring(i);
				}else{
					strByte = stringBuilder.substring(i, i + 8);
				}	
				//将strByte 转成一个byte,放入到 huffmanCodeBytes
				huffmanCodeBytes[index] = (byte)Integer.parseInt(strByte, 2);
				index++;
		}
		return huffmanCodeBytes;
	}
	
```

 封装整个上面一系列的操作：

```java
//使用一个方法，将前面的方法封装起来，便于我们的调用.
	/**
	 * 
	 * @param bytes 原始的字符串对应的字节数组
	 * @return 是经过 赫夫曼编码处理后的字节数组(压缩后的数组)
	 */
	private static byte[] huffmanZip(byte[] bytes) {
		List<Node> nodes = getNodes(bytes);
		//根据 nodes 创建的赫夫曼树
		Node huffmanTreeRoot = createHuffmanTree(nodes);
		//对应的赫夫曼编码(根据 赫夫曼树)
		Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
		//根据生成的赫夫曼编码，压缩得到压缩后的赫夫曼编码字节数组
		byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);
		return huffmanCodeBytes;
	}
```

数据解码(解压缩)：

![DATA_STRUCTURES_ALGORITHMS98.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS98.png?raw=true)

```java
/**
	 * 将一个byte 转成一个二进制的字符串, 参考：二进制的原码，反码，补码
	 * @param b 传入的 byte
	 * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位 .因为最后一个可能不需要安装8位进行处理。
	 * @return 是该b 对应的二进制的字符串，（注意是按补码返回） 原来进行编码的时候也是按照补码进行处理的
	 */
	private static String byteToBitString(boolean flag, byte b) {
		//使用变量保存 b
		int temp = b; //将 b 转成 int
		//如果是正数我们还存在补高位
		if(flag) {
			temp |= 256; //按位与 256  1 0000 0000  | 0000 0001 => 1 0000 0001
		}
		String str = Integer.toBinaryString(temp); //返回的是temp对应的二进制的补码
		if(flag) {
			return str.substring(str.length() - 8);
		} else {
			return str;
		}
	}
```

解压缩(解码)：

```java
//完成数据的解压
	//思路
	//1. 将huffmanCodeBytes [-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
	//   重写先转成 赫夫曼编码对应的二进制的字符串 "1010100010111..."
	//2.  赫夫曼编码对应的二进制的字符串 "1010100010111..." =》 对照 赫夫曼编码  =》 "i like like like java do you like a java"
	
	
	//编写一个方法，完成对压缩数据的解码
	/**
	 * 
	 * @param huffmanCodes 赫夫曼编码表 map
	 * @param huffmanBytes 赫夫曼编码得到的字节数组
	 * @return 就是原来的字符串对应的数组
	 */
	private static byte[] decode(Map<Byte,String> huffmanCodes, byte[] huffmanBytes) {
		
		//1. 先得到 huffmanBytes 对应的 二进制的字符串 ， 形式 1010100010111...
		StringBuilder stringBuilder = new StringBuilder();
		//将byte数组转成二进制的字符串
		for(int i = 0; i < huffmanBytes.length; i++) {
			byte b = huffmanBytes[i];
			//判断是不是最后一个字节
			boolean flag = (i == huffmanBytes.length - 1);
			stringBuilder.append(byteToBitString(!flag, b));
		}
		//把字符串安装指定的赫夫曼编码进行解码
		//把赫夫曼编码表进行调换，因为反向查询 a->100 100->a
		Map<String, Byte>  map = new HashMap<String,Byte>();
		for(Map.Entry<Byte, String> entry: huffmanCodes.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		
		//创建要给集合，存放byte
		List<Byte> list = new ArrayList<>();
		//i 可以理解成就是索引,扫描 stringBuilder 
		for(int  i = 0; i < stringBuilder.length(); ) {
			int count = 1; // 小的计数器
			boolean flag = true;
			Byte b = null;
			
			while(flag) {
				//1010100010111...
				//递增的取出 key 1 
				String key = stringBuilder.substring(i, i+count);//i 不动，让count移动，指定匹配到一个字符
				b = map.get(key);
				if(b == null) {//说明没有匹配到
					count++;
				}else {
					//匹配到
					flag = false;
				}
			}
			list.add(b);
			i += count;//i 直接移动到 count	
		}
		//当for循环结束后，我们list中就存放了所有的字符  "i like like like java do you like a java"
		//把list 中的数据放入到byte[] 并返回
		byte b[] = new byte[list.size()];
		for(int i = 0;i < b.length; i++) {
			b[i] = list.get(i);
		}
		return b;
		
	}
```



##### 最佳实践-文件压缩解压

![DATA_STRUCTURES_ALGORITHMS99.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS99.png?raw=true)

对文件进行压缩：

```java
//编写方法，将一个文件进行压缩
	/**
	 * 
	 * @param srcFile 你传入的希望压缩的文件的全路径
	 * @param dstFile 我们压缩后将压缩文件放到哪个目录
	 */
	public static void zipFile(String srcFile, String dstFile) {
		
		//创建输出流
		OutputStream os = null;
		ObjectOutputStream oos = null;
		//创建文件的输入流
		FileInputStream is = null;
		try {
			//创建文件的输入流
			is = new FileInputStream(srcFile);
			//创建一个和源文件大小一样的byte[]
			byte[] b = new byte[is.available()];
			//读取文件
			is.read(b);
			//直接对源文件压缩
			byte[] huffmanBytes = huffmanZip(b);
			//创建文件的输出流, 存放压缩文件
			os = new FileOutputStream(dstFile);
			//创建一个和文件输出流关联的ObjectOutputStream  这里使用对象流的原因是为了方便解压的时候读取到压缩后的字节数组和编码表，如果使用其他流我们在读取的时候就无法知道那个是哪个了
			oos = new ObjectOutputStream(os);
			//把 赫夫曼编码后的字节数组写入压缩文件
			oos.writeObject(huffmanBytes); //我们是把
			//这里我们以对象流的方式写入 赫夫曼编码，是为了以后我们恢复源文件时使用
			//注意一定要把赫夫曼编码 写入压缩文件
			oos.writeObject(huffmanCodes);
			
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally {
			try {
				is.close();
				oos.close();
				os.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
		
	}
```

对文件进行解压：

![DATA_STRUCTURES_ALGORITHMS100.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS100.png?raw=true)

```java
//编写一个方法，完成对压缩文件的解压
	/**
	 * 
	 * @param zipFile 准备解压的文件
	 * @param dstFile 将文件解压到哪个路径
	 */
	public static void unZipFile(String zipFile, String dstFile) {
		
		//定义文件输入流
		InputStream is = null;
		//定义一个对象输入流
		ObjectInputStream ois = null;
		//定义文件的输出流
		OutputStream os = null;
		try {
			//创建文件输入流
			is = new FileInputStream(zipFile);
			//创建一个和  is关联的对象输入流
			ois = new ObjectInputStream(is);
			//读取byte数组  huffmanBytes
			byte[] huffmanBytes = (byte[])ois.readObject();
			//读取赫夫曼编码表
			Map<Byte,String> huffmanCodes = (Map<Byte,String>)ois.readObject();
			
			//解码
			byte[] bytes = decode(huffmanCodes, huffmanBytes);
			//将bytes 数组写入到目标文件
			os = new FileOutputStream(dstFile);
			//写数据到 dstFile 文件
			os.write(bytes);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			
			try {
				os.close();
				ois.close();
				is.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2.getMessage());
			}
			
		}
	}
```

测试：

```java
		//测试压缩文件
		String srcFile = "d://Uninstall.xml";
		String dstFile = "d://Uninstall.zip";		
		zipFile(srcFile, dstFile);
		System.out.println("压缩文件ok~~");	
		
		//测试解压文件
		String zipFile = "d://Uninstall.zip";
		String dstFile = "d://Uninstall2.xml";
		unZipFile(zipFile, dstFile);
		System.out.println("解压成功!");
```

##### 注意事项

```
1) 如果文件本身就是经过压缩处理的，那么使用赫夫曼编码再压缩效率不会有明显变化, 比如视频,ppt 等等文件
[举例压一个 .ppt]
2) 赫夫曼编码是按字节来处理的，因此可以处理所有的文件(二进制文件、文本文件) [举例压一个.xml 文件]
3) 如果一个文件中的内容，重复的数据不多，压缩效果也不会很明显.
```



#### 二叉排序树

**为什么需要二叉排序树？**

需求： 对于一个给定的数列{21，3，8,5,556} 要求我们能对其进行**高效**的查询和增删。

从前面的分析我们可以知道有序的数组能够快速的查询但是无法高效的增加，链表能够高效的增删，但是其查询效率不够高效，这时就需要使用树去进行存储。

**为什么使用树的形式去存储更加高效**

这就需要我们引出二叉排序树

**什么是二叉排序树：**

```
二叉排序树：BST: (Binary Sort(Search) Tree), 对于二叉排序树的任何一个非叶子节点，要求左子节点的值比当
前节点的值小，右子节点的值比当前节点的值大。
特别说明：如果有相同的值，可以将该节点放在左子节点或右子节点
```

![DATA_STRUCTURES_ALGORITHMS101.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS101.png?raw=true)

##### 二叉排序树的创建及遍历

节点：

```java
//创建Node结点
class Node {
	int value;
	Node left;
	Node right;
	public Node(int value) {
		
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}


	//添加结点的方法
	//递归的形式添加结点，注意需要满足二叉排序树的要求
	public void add(Node node) {
		if(node == null) {
			return;
		}
		
		//判断传入的结点的值，和当前子树的根结点的值关系
		if(node.value < this.value) {
			//如果当前结点左子结点为null
			if(this.left == null) {
				this.left = node;
			} else {
				//递归的向左子树添加
				this.left.add(node);
			}
		} else { //添加的结点的值大于 当前结点的值
			if(this.right == null) {
				this.right = node;
			} else {
				//递归的向右子树添加
				this.right.add(node);
			}
			
		}
	}
	
	//中序遍历
	public void infixOrder() {
		if(this.left != null) {
			this.left.infixOrder();
		}
		System.out.println(this);
		if(this.right != null) {
			this.right.infixOrder();
		}
	}
	
}

```

二叉排序树：

```java
//创建二叉排序树
class BinarySortTree {
	private Node root;	
	public Node getRoot() {
		return root;
	}
	
	//添加结点的方法
	public void add(Node node) {
		if(root == null) {
			root = node;//如果root为空则直接让root指向node
		} else {
			root.add(node);
		}
	}
	//中序遍历
	public void infixOrder() {
		if(root != null) {
			root.infixOrder();
		} else {
			System.out.println("二叉排序树为空，不能遍历");
		}
	}
}

```

测试：

```java
public static void main(String[] args) {
		int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
		BinarySortTree binarySortTree = new BinarySortTree();
		//循环的添加结点到二叉排序树
		for(int i = 0; i< arr.length; i++) {
			binarySortTree.add(new Node(arr[i]));
        }
		//中序遍历二叉排序树
		System.out.println("中序遍历二叉排序树~");
		binarySortTree.infixOrder(); // 1, 3, 5, 7, 9, 10, 12
	}

```

##### 二叉排序树的删除

![DATA_STRUCTURES_ALGORITHMS102.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS102.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS103.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS103.png?raw=true)

node中：

```java
从删除的思路中可以看出，我们要删除节点必须要先找到要删除的节点和要删除节点的父节点 


//查找要删除的结点
	/**
	 * 
	 * @param value 希望删除的结点的值
	 * @return 如果找到返回该结点，否则返回null
	 */
	public Node search(int value) {
		if(value == this.value) { //找到就是该结点
			return this;
		} else if(value < this.value) {//如果查找的值小于当前结点，向左子树递归查找
			//如果左子结点为空
			if(this.left  == null) {
				return null;
			}
			return this.left.search(value);
		} else { //如果查找的值不小于当前结点，向右子树递归查找
			if(this.right == null) {
				return null;
			}
			return this.right.search(value);
		}
		
	}
	//查找要删除结点的父结点
	/**
	 * 
	 * @param value 要找到的结点的值
	 * @return 返回的是要删除的结点的父结点，如果没有就返回null
	 */
	public Node searchParent(int value) {
		//如果当前结点就是要删除的结点的父结点，就返回
		if((this.left != null && this.left.value == value) || 
				(this.right != null && this.right.value == value)) {
			return this;
		} else {
			//如果查找的值小于当前结点的值, 并且当前结点的左子结点不为空
			if(value < this.value && this.left != null) {
				return this.left.searchParent(value); //向左子树递归查找
			} else if (value >= this.value && this.right != null) {
				return this.right.searchParent(value); //向右子树递归查找
			} else {
				return null; // 没有找到父结点
			}
		}
		
	}
```

tree中：

```java

	//查找要删除的结点
	public Node search(int value) {
		if(root == null) {
			return null;
		} else {
			return root.search(value);
		}
	}
	
	//查找父结点
	public Node searchParent(int value) {
		if(root == null) {
			return null;
		} else {
			return root.searchParent(value);
		}
	}
	
	//删除结点
	public void delNode(int value) {
		if(root == null) {
			return;
		}else {
			//1.需求先去找到要删除的结点  targetNode
			Node targetNode = search(value);
			//如果没有找到要删除的结点
			if(targetNode == null) {
				return;
			}
			//如果我们发现当前这颗二叉排序树只有一个结点
			if(root.left == null && root.right == null) {
				root = null;
				return;
			}
			
			//去找到targetNode的父结点
			Node parent = searchParent(value);
			//如果要删除的结点是叶子结点
			if(targetNode.left == null && targetNode.right == null) {
				//判断targetNode 是父结点的左子结点，还是右子结点
				if(parent.left != null && parent.left.value == value) { //是左子结点
					parent.left = null;
				} else if (parent.right != null && parent.right.value == value) {//是由子结点
					parent.right = null;
				}
			} else if (targetNode.left != null && targetNode.right != null) {
                //删除有两颗子树的节点
                //这里先写有两个子节点的节点是因为这种方法比较好些。且总共有3中情况，排除了这两种剩下的就是只有一个字节点的节点。
				int minVal = delRightTreeMin(targetNode.right);
				targetNode.value = minVal;
					
			} else { // 删除只有一颗子树的结点
				//如果要删除的结点有左子结点 
				if(targetNode.left != null) {
					if(parent != null) {
						//如果 targetNode 是 parent 的左子结点
						if(parent.left.value == value) {
							parent.left = targetNode.left;
						} else { //  targetNode 是 parent 的右子结点
							parent.right = targetNode.left;
						} 
					} else {
						root = targetNode.left;
					}
				} else { //如果要删除的结点有右子结点 
					if(parent != null) {
						//如果 targetNode 是 parent 的左子结点
						if(parent.left.value == value) {
							parent.left = targetNode.right;
						} else { //如果 targetNode 是 parent 的右子结点
							parent.right = targetNode.right;
						}
					} else {
						root = targetNode.right;
					}
				}
				
			}
			
		}
	}

//编写方法: 
	//1. 返回的 以node 为根结点的二叉排序树的最小结点的值
	//2. 删除node 为根结点的二叉排序树的最小结点
	/**
	 * 
	 * @param node 传入的结点(当做二叉排序树的根结点)
	 * @return 返回的 以node 为根结点的二叉排序树的最小结点的值
	 */
	public int delRightTreeMin(Node node) {
		Node target = node;
		//循环的查找左子节点，就会找到最小值
		while(target.left != null) {
			target = target.left;
		}
		//这时 target就指向了最小结点
		//删除最小结点
		delNode(target.value);
		return target.value;
	}
```



#### 平衡二叉树（AVL）

平衡二叉树的存在是因为二叉排序树可能存在的一些问题，他是对二叉排序树的一种优化。

![DATA_STRUCTURES_ALGORITHMS104.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS104.png?raw=true)

**什么是平衡二叉树**

![DATA_STRUCTURES_ALGORITHMS105.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS105.png?raw=true)

平衡二叉树的前提是一颗二叉排序树，他是在二叉排序树的基础上发展而来的

![DATA_STRUCTURES_ALGORITHMS106.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS106.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS107.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS107.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS108.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS108.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS109.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS109.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS110.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS110.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS111.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS111.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS112.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS112.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS113.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS113.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS114.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS114.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS115.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS115.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS116.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS116.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS117.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS117.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS118.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS118.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS119.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS119.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS120.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS120.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS121.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS121.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS122.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS122.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS123.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS123.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS124.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS124.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS125.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS125.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS126.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS126.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS127.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS127.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS128.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS128.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS129.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS129.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS130.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS130.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS131.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS131.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS132.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS132.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS133.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS133.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS134.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS134.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS135.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS135.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS136.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS136.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS137.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS137.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS138.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS138.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS139.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS139.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS140.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS140.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS141.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS141.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS142.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS142.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS143.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS143.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS144.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS144.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS145.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS145.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS146.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS146.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS147.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS147.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS148.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS148.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS149.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS149.png?raw=true)
![DATA_STRUCTURES_ALGORITHMS150.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS150.png?raw=true)