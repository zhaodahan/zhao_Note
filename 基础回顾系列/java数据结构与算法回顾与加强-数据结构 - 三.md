# java数据结构与算法



## 树



#### 平衡二叉树（AVL）

平衡二叉树的存在是因为二叉排序树可能存在的一些问题，他是对二叉排序树的一种优化。

![DATA_STRUCTURES_ALGORITHMS104.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS104.png?raw=true)

**什么是平衡二叉树**

![DATA_STRUCTURES_ALGORITHMS105.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS105.png?raw=true)

平衡二叉树的前提是一颗二叉排序树，他是在二叉排序树的基础上发展而来的。

##### 构建平衡二叉树

构建一颗平衡二叉树根据不同的情况需要进行左旋转，右旋转，双旋转。

###### 左旋转

![DATA_STRUCTURES_ALGORITHMS106.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS106.png?raw=true)

代码实现：

通过上面的分析，我们发现在构建平衡二叉树的过程中，是否需要进行旋转的判定重要条件是节点的左右子树高度相差大于1. 那么这时候统计节点左子树高度和右子树高度就很重要了

```java
// 创建Node结点
class Node {
	int value;
	Node left;
	Node right;
	public Node(int value) {
		this.value = value;
	}
	// 返回左子树的高度
	public int leftHeight() {
		if (left == null) {
			return 0;
		}
		return left.height();
	}

	// 返回右子树的高度
	public int rightHeight() {
		if (right == null) {
			return 0;
		}
		return right.height();
	}

	// 返回 以该结点为根结点的树的高度
	public int height() {
		return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
  // 这里为什么要+1，Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height())只是统计出了这个节点左右子树的节点的最大值，除了左右子树还要包括自身，所以要+1
	}
```

创建AVL:

```java
// 创建AVLTree
class AVLTree {
	private Node root;
	public Node getRoot() {
		return root;
	}

	// 查找要删除的结点
	public Node search(int value) {
		if (root == null) {
			return null;
		} else {
			return root.search(value);
		}
	}

	// 查找父结点
	public Node searchParent(int value) {
		if (root == null) {
			return null;
		} else {
			return root.searchParent(value);
		}
	}

	// 编写方法:
	// 1. 返回的 以node 为根结点的二叉排序树的最小结点的值
	// 2. 删除node 为根结点的二叉排序树的最小结点
	/**
	 * 
	 * @param node
	 *            传入的结点(当做二叉排序树的根结点)
	 * @return 返回的 以node 为根结点的二叉排序树的最小结点的值
	 */
	public int delRightTreeMin(Node node) {
		Node target = node;
		// 循环的查找左子节点，就会找到最小值
		while (target.left != null) {
			target = target.left;
		}
		// 这时 target就指向了最小结点
		// 删除最小结点
		delNode(target.value);
		return target.value;
	}

	// 删除结点
	public void delNode(int value) {
		if (root == null) {
			return;
		} else {
			// 1.需求先去找到要删除的结点 targetNode
			Node targetNode = search(value);
			// 如果没有找到要删除的结点
			if (targetNode == null) {
				return;
			}
			// 如果我们发现当前这颗二叉排序树只有一个结点
			if (root.left == null && root.right == null) {
				root = null;
				return;
			}

			// 去找到targetNode的父结点
			Node parent = searchParent(value);
			// 如果要删除的结点是叶子结点
			if (targetNode.left == null && targetNode.right == null) {
				// 判断targetNode 是父结点的左子结点，还是右子结点
				if (parent.left != null && parent.left.value == value) { // 是左子结点
					parent.left = null;
				} else if (parent.right != null && parent.right.value == value) {// 是由子结点
					parent.right = null;
				}
			} else if (targetNode.left != null && targetNode.right != null) { // 删除有两颗子树的节点
				int minVal = delRightTreeMin(targetNode.right);
				targetNode.value = minVal;

			} else { // 删除只有一颗子树的结点
				// 如果要删除的结点有左子结点
				if (targetNode.left != null) {
					if (parent != null) {
						// 如果 targetNode 是 parent 的左子结点
						if (parent.left.value == value) {
							parent.left = targetNode.left;
						} else { // targetNode 是 parent 的右子结点
							parent.right = targetNode.left;
						}
					} else {
						root = targetNode.left;
					}
				} else { // 如果要删除的结点有右子结点
					if (parent != null) {
						// 如果 targetNode 是 parent 的左子结点
						if (parent.left.value == value) {
							parent.left = targetNode.right;
						} else { // 如果 targetNode 是 parent 的右子结点
							parent.right = targetNode.right;
						}
					} else {
						root = targetNode.right;
					}
				}

			}

		}
	}

	// 添加结点的方法
	public void add(Node node) {
		if (root == null) {
			root = node;// 如果root为空则直接让root指向node
		} else {
			root.add(node);
		}
	}

	// 中序遍历
	public void infixOrder() {
		if (root != null) {
			root.infixOrder();
		} else {
			System.out.println("二叉排序树为空，不能遍历");
		}
	}
}
```

左旋转的方法我们写在Node中，这样我们tree在添加删除的之后只需要按照之前的方法add，delete即可

```java
//左旋转方法
	private void leftRotate() {
		
		//创建新的结点，以当前根结点的值
		Node newNode = new Node(value);
		//把新的结点的左子树设置成当前结点的左子树
		newNode.left = left;
		//把新的结点的右子树设置成带你过去结点的右子树的左子树
		newNode.right = right.left;
		//把当前结点的值替换成右子结点的值
		value = right.value;
		//把当前结点的右子树设置成当前结点右子树的右子树
		right = right.right;
		//把当前结点的左子树(左子结点)设置成新的结点
		left = newNode;	
	}

// 那么原来的add()方法就需要做一些修改
// 添加结点的方法
	// 递归的形式添加结点，注意需要满足二叉排序树的要求
	public void add(Node node) {
		if (node == null) {
			return;
		}

		// 判断传入的结点的值，和当前子树的根结点的值关系
		if (node.value < this.value) {
			// 如果当前结点左子结点为null
			if (this.left == null) {
				this.left = node;
			} else {
				// 递归的向左子树添加
				this.left.add(node);
			}
		} else { // 添加的结点的值大于 当前结点的值
			if (this.right == null) {
				this.right = node;
			} else {
				// 递归的向右子树添加
				this.right.add(node);
			}

		}
		
		//当添加完一个结点后，如果: (右子树的高度-左子树的高度) > 1 , 左旋转
		if(rightHeight() - leftHeight() > 1) {
		     leftRotate(); // 先暂时直接对该节点进行左旋转
		}
		
	}

```

###### 右旋转

![DATA_STRUCTURES_ALGORITHMS107.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS107.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS108.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS108.png?raw=true)

右旋转代码实现：

```java
//右旋转
	private void rightRotate() {
		Node newNode = new Node(value);
		newNode.right = right;
		newNode.left = left.right;
		value = left.value;
		left = left.left;
		right = newNode;
	}
```

Node中的add()做进一步的完善

```java
//当添加完一个结点后，如果 (左子树的高度 - 右子树的高度) > 1, 右旋转
		if(leftHeight() - rightHeight() > 1) {
				//直接进行右旋转即可
				rightRotate();
		}
```



###### 双旋转

某些情况下，单旋转不能完成平衡二叉树的转换

![DATA_STRUCTURES_ALGORITHMS109.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS109.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS110.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS110.png?raw=true)

add()代码补充

```java
// 添加结点的方法
	// 递归的形式添加结点，注意需要满足二叉排序树的要求
	public void add(Node node) {
		if (node == null) {
			return;
		}

		// 判断传入的结点的值，和当前子树的根结点的值关系
		if (node.value < this.value) {
			// 如果当前结点左子结点为null
			if (this.left == null) {
				this.left = node;
			} else {
				// 递归的向左子树添加
				this.left.add(node);
			}
		} else { // 添加的结点的值大于 当前结点的值
			if (this.right == null) {
				this.right = node;
			} else {
				// 递归的向右子树添加
				this.right.add(node);
			}

		}
		
		//当添加完一个结点后，如果: (右子树的高度-左子树的高度) > 1 , 左旋转
		if(rightHeight() - leftHeight() > 1) {
			//如果它的右子树的左子树的高度大于它的右子树的右子树的高度
			if(right != null && right.leftHeight() > right.rightHeight()) {
				//先对右子结点进行右旋转
				right.rightRotate();
				//然后在对当前结点进行左旋转
				leftRotate(); //左旋转..
			} else {
				//直接进行左旋转即可
				leftRotate();
			}
			return ; //必须要!!!
		}
		
		//当添加完一个结点后，如果 (左子树的高度 - 右子树的高度) > 1, 右旋转
		if(leftHeight() - rightHeight() > 1) {
			//如果它的左子树的右子树高度大于它的左子树的高度
			if(left != null && left.rightHeight() > left.leftHeight()) {
				//先对当前结点的左结点(左子树)->左旋转
				left.leftRotate();
				//再对当前结点进行右旋转
				rightRotate();
			} else {
				//直接进行右旋转即可
				rightRotate();
			}
		}
	}
```

总结： 左旋降低右子树高度，右旋降低左子树高度；



#### 多路查找树

##### 二叉树与B树

(B树就是多叉树)

二叉树虽然操作效率高，但是仍然存在一些问题

![DATA_STRUCTURES_ALGORITHMS111.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS111.png?raw=true)

为了解决这个问题，我们就提出了多叉树：

![DATA_STRUCTURES_ALGORITHMS112.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS112.png?raw=true)

多叉树的优化就是降低树的高度。 数据库中做索引就是使用的B+树。

![DATA_STRUCTURES_ALGORITHMS113.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS113.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS115.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS115.png?raw=true)

###### 2-3树

2-3树是最简单的B树

![DATA_STRUCTURES_ALGORITHMS114.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS114.png?raw=true)

注意： 他仍然满足二叉排序树的，他也是对二叉排序树的一种优化。

 构建2,3树的过程：

![DATA_STRUCTURES_ALGORITHMS116.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS116.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS117.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS117.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS118.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS118.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS119.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS119.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS120.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS120.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS121.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS121.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS122.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS122.png?raw=true)

###### B+，B-树

B-tree 树即 B 树.B 即 Balanced，平衡的意思

![DATA_STRUCTURES_ALGORITHMS123.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS123.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS124.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS124.png?raw=true)

B+树的分段效率比二分更大，一下子就去掉了2/3; 提高检索效率。 并降低了树的高度。

###### B* 树

B* 树是在B+树的基础上做的一个变化。

![DATA_STRUCTURES_ALGORITHMS125.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS125.png?raw=true)

B* 树在B+树的非叶子节点上增加兄弟指针有什么用？

```
B+树的分裂：当一个结点满时，分配一个新的结点，并将原结点中1/2的数据复制到新结点，最后在父结点中增加新结点的指针；B+树的分裂只影响原结点和父结点，而不会影响兄弟结点，所以它不需要指向兄弟的指针；

B*树的分裂：当一个结点满时，如果它的下一个兄弟结点未满，那么将一部分数据移到兄弟结点中，再在原结点插入关键字，最后修改父结点中兄弟结点的关键字（因为兄弟结点的关键字范围改变了）；如果兄弟也满了，则在原结点与兄弟结点之间增加新结点，并各复制1/3的数据到新结点，最后在父结点增加新结点的指针；所以，B*树分配新结点的概率比B+树要低，空间使用率更高；
```



## 图

**为什么要有图**？

前面学习的线性的数据结构只能表示一对一，一对多的关系。 当我们想表述多对多的关系的时候。。。就引出了图。

![DATA_STRUCTURES_ALGORITHMS126.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS126.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS127.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS127.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS128.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS128.png?raw=true)

图的表示方式：

有两种：二维数组表示（邻接矩阵）；链表表示（邻接表）

可以看出图在内存底层的存储方式也是数组和链表，只不过是在他们的基础上做了一层逻辑封装。

![DATA_STRUCTURES_ALGORITHMS129.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS129.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS130.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS130.png?raw=true)

### 图的创建

![DATA_STRUCTURES_ALGORITHMS131.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS131.png?raw=true)

```java
public class Graph {

	private ArrayList<String> vertexList; //存储顶点集合
	private int[][] edges; //存储图对应的邻结矩阵
	private int numOfEdges; //表示边的数目
	//定义给数组boolean[], 记录某个结点是否被访问
	private boolean[] isVisited;
	
	//构造器
	public Graph(int n) {
		//初始化矩阵和vertexList
		edges = new int[n][n];
		vertexList = new ArrayList<String>(n);
		numOfEdges = 0;
		
	}
	
    //插入结点
	public void insertVertex(String vertex) {
		vertexList.add(vertex);
	}
	//添加边
	/**
	 * 
	 * @param v1 表示点的下标即使第几个顶点  "A"-"B" "A"->0 "B"->1
	 * @param v2 第二个顶点对应的下标
	 * @param weight 表示 
	 */
	public void insertEdge(int v1, int v2, int weight) {
		edges[v1][v2] = weight;
		edges[v2][v1] = weight;
		numOfEdges++;
	}
    
    	//图中常用的方法
	//返回结点的个数
	public int getNumOfVertex() {
		return vertexList.size();
	}
	
	//得到边的数目
	public int getNumOfEdges() {
		return numOfEdges;
	}
	//返回结点i(下标)对应的节点的数据 0->"A" 1->"B" 2->"C"
	public String getValueByIndex(int i) {
		return vertexList.get(i);
	}
	//返回v1和v2的权值
	public int getWeight(int v1, int v2) {
		return edges[v1][v2];
	}
    
    //显示图对应的矩阵
	public void showGraph() {
		for(int[] link : edges) {
			System.err.println(Arrays.toString(link));
		}
	}
    
}



	public static void main(String[] args) {
		//测试一把图是否创建ok
		int n = 8;  //结点的个数
		//String Vertexs[] = {"A", "B", "C", "D", "E"};
        		//添加边
		//A-B A-C B-C B-D B-E 
		graph.insertEdge(0, 1, 1); // A-B
		graph.insertEdge(0, 2, 1); // 
		graph.insertEdge(1, 2, 1); // 
		graph.insertEdge(1, 3, 1); // 
		graph.insertEdge(1, 4, 1); // 
		String Vertexs[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
		
		//创建图对象
		Graph graph = new Graph(n);
		//循环的添加顶点
		for(String vertex: Vertexs) {
			graph.insertVertex(vertex);
		}
	
		//更新边的关系
		graph.insertEdge(0, 1, 1);
		graph.insertEdge(0, 2, 1);
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(1, 4, 1);
		graph.insertEdge(3, 7, 1);
		graph.insertEdge(4, 7, 1);
		graph.insertEdge(2, 5, 1);
		graph.insertEdge(2, 6, 1);
		graph.insertEdge(5, 6, 1);		
		
		//显示一把邻结矩阵
		graph.showGraph();
		
		//测试一把，我们的dfs遍历是否ok
		System.out.println("深度遍历");
		graph.dfs(); // A->B->C->D->E [1->2->4->8->5->3->6->7]
//		System.out.println();
		System.out.println("广度优先!");
		graph.bfs(); // A->B->C->D-E [1->2->3->4->5->6->7->8]
		
	}
	
```

### 图的遍历

```
图遍历介绍
所谓图的遍历，即是对结点的访问。一个图有那么多个结点，如何遍历这些结点，
需要特定策略，一般有两种访问策略: (1)深度优先遍历 (2)广度优先遍历
他们都是在图中寻找节点的算法
```



#### 深度优先遍历

dfs:

```
图的深度优先搜索(Depth First Search) 。
1) 深度优先遍历，从初始访问结点出发，初始访问结点可能有多个邻接结点，深度优先遍历的策略就是首先访问
第一个邻接结点，然后再以这个被访问的邻接结点作为初始结点，访问它的第一个邻接结点， 可以这样理解：
每次都在访问完当前结点后首先访问当前结点的第一个邻接结点。
2) 这样的访问策略是优先往纵向挖掘深入，而不是对一个结点的所有邻接结点进行横向访问。
3) 显然，深度优先搜索是一个递归的过程
```

![DATA_STRUCTURES_ALGORITHMS132.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS132.png?raw=true)

(下面代码的理解需要画图参照两个数组节点list , 表示图的邻接矩阵)

![DATA_STRUCTURES_ALGORITHMS133.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS133.png?raw=true)

```
深度优先遍历算法步骤
1) 访问初始结点 v，并标记结点 v 为已访问。
2) 查找结点 v 的第一个邻接结点 w。
3) 若 w 存在，则继续执行 4，如果 w 不存在，则回到第 1 步，将从 v 的下一个结点继续。
4) 若 w 未被访问，对 w 进行深度优先遍历递归（即把 w 当做另一个 v，然后进行步骤 123）。
5) 查找结点 v 的 w 邻接结点的下一个邻接结点，转到步骤 3
```



```java
//得到第一个邻接结点的下标 w 
	/**
	 * 这里对应着的是dfs的第二步，获取当前节点的下一个邻接节点的下标 (对应着存储节点的list中的下标)。 
	 * 什么是邻接节点： 就是与当前节点连接着的边点。
	 * @param index 
	 * @return 如果存在就返回对应的下标，否则返回-1
	 */
//等价于在二维矩阵中根据x轴下标一定的时候， 找到对应y列中
	public int getFirstNeighbor(int index) {
		for(int j = 0; j < vertexList.size(); j++) {
			if(edges[index][j] > 0) {
				return j;
			}
		}
		return -1;
	}

	//根据前一个邻接结点的下标来获取下一个邻接结点
/*
* v1 是当前节点，v2是当前节点的节点，求的是当前节点在v2之后的下一个邻接节点 
*/
	public int getNextNeighbor(int v1, int v2) {
		for(int j = v2 + 1; j < vertexList.size(); j++) {
			if(edges[v1][j] > 0) {
				return j;
			}
		}
		return -1;
	}
	
	//深度优先遍历算法
	//i 第一次就是 0
	private void dfs(boolean[] isVisited, int i) {
		//首先我们访问该结点,输出
		System.out.print(getValueByIndex(i) + "->");
		//将结点设置为已经访问
		isVisited[i] = true;
		//查找结点i的第一个邻接结点w
		int w = getFirstNeighbor(i);
		while(w != -1) {//说明有
			if(!isVisited[w]) {
				dfs(isVisited, w);
			}
			//如果w结点已经被访问过
			w = getNextNeighbor(i, w);
		}
		
	}
	
	//对dfs 进行一个重载, 遍历我们所有的结点，并进行 dfs
//==>实现：则回到第 1 步，将从 v 的下一个结点继续 
	public void dfs() {
		isVisited = new boolean[vertexList.size()];
		//遍历所有的结点，进行dfs[回溯]
		for(int i = 0; i < getNumOfVertex(); i++) {
			if(!isVisited[i]) {
				dfs(isVisited, i);
			}
		}
	}	
```

深度是不断深入，如果一个节点无法深入了，就换另一个节点作为起点接着深入，始终是在一列。直到完全访问结束。

深度优先算法在找邻接节点的时候：举例 A 找到临接B ,然后是在B这一个X 层，从头开始找对一个临街C

#### 广度优先遍历

bfs:

广度优先算法在找邻接节点的时候：举例 A 找到临接B ,然后又是从A这个X层，从B之后找到临街C。如果以A找不到，再以B为初始节点去找。。 所以相当于分层去找。。

```
类似于一个分层搜索的过程，广度优先遍历需要使用一个队列以保持访问过的结点的顺序，以便按这个顺序来
访问这些结点的邻接结点
13.5.2 广度优先遍历算法步骤
1) 访问初始结点 v 并标记结点 v 为已访问。
2) 结点 v 入队列
3) 当队列非空时，继续执行，否则算法结束。
4) 出队列，取得队头结点 u。
5) 查找结点 u 的第一个邻接结点 w。
6) 若结点 u 的邻接结点 w 不存在，则转到步骤 3；否则循环执行以下三个步骤：
6.1 若结点 w 尚未被访问，则访问结点 w 并标记为已访问。
6.2 结点 w 入队列
6.3 查找结点 u 的继 w 邻接结点后的下一个邻接结点 w，转到步骤 6。
```

![DATA_STRUCTURES_ALGORITHMS134.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS134.png?raw=true)

```java
	//对一个结点进行广度优先遍历的方法
	private void bfs(boolean[] isVisited, int i) {
		int u ; // 表示队列的头结点对应下标
		int w ; // 邻接结点的下标w
		//队列，记录结点访问的顺序 (这里使用list简单代替队列)
		LinkedList queue = new LinkedList();
		//访问结点，输出结点信息
		System.out.print(getValueByIndex(i) + "=>");
		//标记为已访问
		isVisited[i] = true;
		//将结点加入队列
		queue.addLast(i);
		
		while( !queue.isEmpty()) {
			//取出队列的头结点下标
			u = (Integer)queue.removeFirst();
			//得到第一个邻接结点的下标 w 
			w = getFirstNeighbor(u);
			while(w != -1) {//找到
				//是否访问过
				if(!isVisited[w]) {
					System.out.print(getValueByIndex(w) + "=>");
					//标记已经访问
					isVisited[w] = true;
					//入队
					queue.addLast(w);
				}
				//以u为前驱点，找w后面的下一个邻结点
				w = getNextNeighbor(u, w); //体现出我们的广度优先
			}
		}
		
	} 
	
	//遍历所有的结点，都进行广度优先搜索
	public void bfs() {
		isVisited = new boolean[vertexList.size()];
		for(int i = 0; i < getNumOfVertex(); i++) {
			if(!isVisited[i]) {
				bfs(isVisited, i);
			}
		}
	}
	
```

![DATA_STRUCTURES_ALGORITHMS135.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS135.png?raw=true)

