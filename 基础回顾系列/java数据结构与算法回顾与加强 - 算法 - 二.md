# java数据结构与算法

算法是将思想转换成代码。如果这个思想能解决一类的问题，这个思想就是算法。

# 程序员常用的十大算法

我们在处理一个问题的时候： 首先需要有一个思想====》将思想提炼成一个公式规律====》代码

## 二分查找非递归算法实现

二分查找算法，常使用递归的方式，下面我们讲解二分查找算法的非递归方式

```
二分查找适用于有序的数组
 二分查找法的运行时间为对数时间 O(㏒₂n) ，即查找到需要的目标位置最多只需要㏒₂n 步，假设从[0,99]的
队列(100 个数，即 n=100)中寻到目标数 30，则需要查找步数为㏒₂100 , 即最多需要查找 7 次( 2^6 < 100 < 2^7)
```

不使用递归就只能使用循环。

```java
public class BinarySearchNoRecur {

	public static void main(String[] args) {
		//测试
		int[] arr = {1,3, 8, 10, 11, 67, 100};
		int index = binarySearch(arr, 100);
		System.out.println("index=" + index);//
	}
	
	//二分查找的非递归实现
	/**
	 * 
	 * @param arr 待查找的数组, arr是升序排序
	 * @param target 需要查找的数
	 * @return 返回对应下标，-1表示没有找到
	 */
	public static int binarySearch(int[] arr, int target) {
		
		int left = 0;
		int right = arr.length - 1;
		while(left <= right) { //说明继续查找
			int mid = (left + right) / 2;
			if(arr[mid] == target) {
				return mid;
			} else if ( arr[mid] > target) {
				right = mid - 1;//需要向左边查找
			} else {
				left = mid + 1; //需要向右边查找
			}
		}
		return -1;
	}

}

```



## 分治算法

```
分治法是一种很重要的算法。字面上的解释是“分而治之”，就是把一个复杂的问题分成两个或更多的相同或
相似的子问题，再把子问题分成更小的子问题……直到最后子问题可以简单的直接求解，原问题的解即子问题
的解的合并。这个技巧是很多高效算法的基础，如排序算法(快速排序，归并排序)，傅立叶变换(快速傅立叶变
换)……
==》就是将复杂的问题分解成简单的问题，直到最后一个子问题能够简单求解。复杂问题简单化

分治算法最难点就在于如何将复杂的问题进行拆分：

分治法在每一层递归上都有三个步骤：
1) 分解：将原问题分解为若干个规模较小，相互独立，与原问题形式相同的子问题
2) 解决：若子问题规模较小而容易被解决则直接解，否则递归地解各个子问题
3) 合并：将各个子问题的解合并为原问题的解。
```



#### 分治算法最佳实践-汉诺塔

汉罗塔问题分析:

我们拆分问题： 不管有多少个盘，我们都将其看成两个盘，上面的小盘和最先的最大盘。而两个盘子的移动很好解决。

![DATA_STRUCTURES_ALGORITHMS136.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS136.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS137.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS137.png?raw=true)

```java
public class Hanoitower {

	public static void main(String[] args) {
		hanoiTower(10, 'A', 'B', 'C');
	}
	
	//汉诺塔的移动的方法
	//使用分治算法
	// 这里a代表起始柱，c是目标柱，b 是中间柱 从a移动到c
	public static void hanoiTower(int num, char a, char b, char c) {
		//如果只有一个盘
		if(num == 1) {
			System.out.println("第1个盘从 " + a + "->" + c);
		} else {
			//如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的一个盘 2. 上面的所有盘
			//1. 先把 最上面的所有盘 A->B， 移动过程会使用到 c
			hanoiTower(num - 1, a, c, b);
			//2. 把最下边的盘 A->C
			System.out.println("第" + num + "个盘从 " + a + "->" + c);
			//3. 把B塔的所有盘 从 B->C , 移动过程使用到 a塔  
			hanoiTower(num - 1, b, a, c);
			
		}
	}

}
```



## 动态规划

### 应用场景-背包问题：

![DATA_STRUCTURES_ALGORITHMS138.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS138.png?raw=true)

```
1)动态规划(Dynamic Programming)算法的核心思想是：将大问题划分为小问题进行解决，从而一步步获取最优解
的处理算法
2) 动态规划算法与分治算法类似，其基本思想也是将待求解问题分解成若干个子问题，先求解子问题，然后从这
些子问题的解得到原问题的解。
3) 但是与分治法不同的是，适合于用动态规划求解的问题，经分解得到子问题往往不是互相独立的。 ( 即下一个子
阶段的求解是建立在上一个子阶段的解的基础上，进行进一步的求解 )，而分治算法各个子问题之间往往是相互独立的
如：汉罗塔问题： 移动两个盘与移动3个盘之间是两个独立的问题。
4) 动态规划可以通过填表的方式来逐步推进，得到最优解.
```



```
算法的主要思想，利用动态规划来解决。每次遍历到的第 i 个物品(我们的物品是一个一个往里面放的，通过比较放入后的总价值决定是否要往里面放。 我们现实中也是这样尝试，先放第一个，然后尝试能不能放第二个，然后估计一下价值。不停的测试)，根据 w[i](第i个商品的重量)和 v[i](第i个商品的价值)来确定是否需要将该物品放入背包中。即对于给定的 n 个物品，设 v[i]、w[i]分别为第 i 个物品的价值和重量，C 为背包的容量。

再令 v[i][j]表示在前 i 个物品中能够装入容量为 j 的背包中的最大价值（V[i][j]是在容量为j的情况下，只放入第i个商品和i个之前的商品时的最大价值。）
```

![DATA_STRUCTURES_ALGORITHMS139.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS139.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS140.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS140.png?raw=true)

```
公式解释：
如果新的商品装不进去，就采用原来的装入策略，如果新的商品能够装入到背包中
就要看谁更大，是原先的策略更大，还是我将新的商品装入，再装入我剩余空间对应的最大值
```

```java
public class KnapsackProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] w = {1, 4, 3};//物品的体积
		int[] val = {1500, 3000, 2000}; //物品的价值 这里val[i] 就是前面讲的v[i]
		int m = 4; //背包的容量
		int n = val.length; //物品的个数
		
	
		//创建二维数组，
		//v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
		int[][] v = new int[n+1][m+1];
		//为了记录放入商品的情况，我们定一个二维数组
		int[][] path = new int[n+1][m+1];
		
		//初始化第一行和第一列, 这里在本程序中，可以不去处理，因为默认就是0
		for(int i = 0; i < v.length; i++) {
			v[i][0] = 0; //将第一列设置为0
		}
		for(int i=0; i < v[0].length; i++) {
			v[0][i] = 0; //将第一行设置0
		}
		
		
		//根据前面得到公式来动态规划处理
		for(int i = 1; i < v.length; i++) { //不处理第一行 i是从1开始的
			for(int j=1; j < v[0].length; j++) {//不处理第一列, j是从1开始的
				//公式
				if(w[i-1]> j) { // 因为我们程序i 是从1开始的，因此原来公式中的 w[i] 修改成 w[i-1]
					v[i][j]=v[i-1][j];
				} else {
					//说明:
					//因为我们的i 从1开始的， 因此公式需要调整成
					//v[i][j]=Math.max(v[i-1][j], val[i-1]+v[i-1][j-w[i-1]]);
					//v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);
					//为了记录商品存放到背包的情况，我们不能直接的使用上面的公式，需要使用if-else来体现公式
					if(v[i - 1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]) {
						v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
						//把当前的情况记录到path
						path[i][j] = 1;
					} else {
						v[i][j] = v[i - 1][j];
					}
					
				}
			}
		}
		
		//输出一下v 看看目前的情况
		for(int i =0; i < v.length;i++) {
			for(int j = 0; j < v[i].length;j++) {
				System.out.print(v[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("============================");
		//输出最后我们是放入的哪些商品
		//遍历path, 这样输出会把所有的放入情况都得到, 其实我们只需要最后的放入
//		for(int i = 0; i < path.length; i++) {
//			for(int j=0; j < path[i].length; j++) {
//				if(path[i][j] == 1) {
//					System.out.printf("第%d个商品放入到背包\n", i);
//				}
//			}
//		}
		
		//动脑筋
		int i = path.length - 1; //行的最大下标
		int j = path[0].length - 1;  //列的最大下标
		while(i > 0 && j > 0 ) { //从path的最后开始找
			if(path[i][j] == 1) {
				System.out.printf("第%d个商品放入到背包\n", i); 
				j -= w[i-1]; //w[i-1]
			}
			i--;
		}
		
	}

}

```



## KMP算法

应用场景-字符串匹配问题

![DATA_STRUCTURES_ALGORITHMS141.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS141.png?raw=true)

```java
// 暴力匹配算法实现
	public static int violenceMatch(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();

		int s1Len = s1.length;
		int s2Len = s2.length;

		int i = 0; // i索引指向s1
		int j = 0; // j索引指向s2
		while (i < s1Len && j < s2Len) {// 保证匹配时，不越界

			if(s1[i] == s2[j]) {//匹配ok
				i++;
				j++;
			} else { //没有匹配成功
				//如果失配（即str1[i]! = str2[j]），令i = i - (j - 1)，j = 0。
				i = i - (j - 1);
				j = 0;
			}
		}
		
		//判断是否匹配成功
		if(j == s2Len) {
			return i - j;
		} else {
			return -1;
		}
	}
```



KMP 算法

KMP 是一个解决模式串在文本串是否出现过，如果出现过，最早出现的位置的经典算法

KMP 方法算法利用之前判断过信息，通过一个 next 数组，保存模式串中前后**最长公共子序列的长度**，每次回溯时，通过 next 数组找到，前面匹配过的位置，省去了大量的计算时间

### KMP 算法最佳应用-字符串匹配问题

```
字符串匹配问题：：
1) 有一个字符串 str1= "BBC ABCDAB ABCDABCDABDE"，和一个子串 str2="ABCDABD"
2) 现在要判断 str1 是否含有 str2, 如果存在，就返回第一次出现的位置, 如果没有，则返回-1
3) 要求：使用 KMP 算法完成判断
```

![DATA_STRUCTURES_ALGORITHMS142.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS142.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS143.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS143.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS144.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS144.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS145.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS145.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS146.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS146.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS147.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS147.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS148.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS148.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS149.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS149.png?raw=true)

获取子串匹配值表(这个代码逻辑比较难理解，通过代码调试来帮助理解)

```java
   //获取到一个字符串(子串) 的部分匹配值表
	public static  int[] kmpNext(String dest) {
		//创建一个next 数组保存部分匹配值
		int[] next = new int[dest.length()];
		next[0] = 0; //如果字符串是长度为1 部分匹配值就是0
        //这里是扫描整个子串。所以i<dest.length
 //
		for(int i = 1, j = 0; i < dest.length(); i++) {
			//当dest.charAt(i) != dest.charAt(j) ，我们需要从next[j-1]获取新的j
			//直到我们发现 有  dest.charAt(i) == dest.charAt(j)成立才退出
			//这才是kmp算法的核心点
			while(j > 0 && dest.charAt(i) != dest.charAt(j)) {
				j = next[j-1];
			}
			
			//当dest.charAt(i) == dest.charAt(j) 满足时，部分匹配值就是+1
			if(dest.charAt(i) == dest.charAt(j)) {
				j++;
			}
			next[i] = j;
		}
		return next;
	}
==================调试上面代码的理解=======================================
    String str="ABCDABD";
    int[] kmpNext = kmpNext(str);
在求子串匹配值的过程中，如果子串中没有出现重复的值。for循环中j始终是0
	for(int i = 1, j = 0; i < dest.length(); i++)
即 while(j > 0 && dest.charAt(i) != dest.charAt(j)) 始终不满足，其中的语句始终不执行
这里对应到我们人为的匹配中可以验证
=======================
str=“ABCD” 
    前缀        后缀 
A                D
AB               CD
ABC              BCD
子串匹配值是0
=======================
当匹配子串中出现第一个重复字符时。 if(dest.charAt(i) == dest.charAt(j)) 他才会执行，j的值才会改变，才会执行while循环中的代码
当匹配子串中紧接着第一个重复字符出现第二个重复字符时，这时j因为第一个重复的值发生了改变，j++了。 这时就可能执行while中的代码。
String str="ABCDAB";
此时i=4，j=1；dest.charAt(i)=B,dest.charAt(j)=B
while(j > 0 && dest.charAt(i) != dest.charAt(j)) {
				j = next[j-1];
}
这时候满足j>0, 这里不断的循环知道找到与当前i指定的字符重复的字符为止。但是因为这里是紧接着的重复字符，所以while循环依旧无法进入，仍会执行if(dest.charAt(i) == dest.charAt(j)) ,j++ 
 J变成2了，这时在重复字符结束后出现了不重复的字符，这时就会执行while循环中的代码
 String str="ABCDAB";
此时i=5，j=2；dest.charAt(i)=D,dest.charAt(j)=C
while(j > 0 && dest.charAt(i) != dest.charAt(j)) {
				j = next[j-1];
}
这时执行j=next[j-1]==>(next[1]),j=0 |
这里j变成0后，再执行if(dest.charAt(i) == dest.charAt(j)) 相当于新的字符重新和前缀比较。重新积累j值。 
? ，这里为什么是 j = next[j-1]。 首先next[j-1] 表示j字符前一个子串的最大匹配值。
```



```java
public class KMPAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str1 = "BBC ABCDAB ABCDABCDABDE";
		String str2 = "ABCDABD";
		//String str2 = "BBC";
		
		int[] next = kmpNext("ABCDABD"); //[0, 1, 2, 0]
		System.out.println("next=" + Arrays.toString(next));
		
		int index = kmpSearch(str1, str2, next);
		System.out.println("index=" + index); // 15了
		
		
	}
    //获取到一个字符串(子串) 的部分匹配值表
	public static  int[] kmpNext(String dest) {
		//创建一个next 数组保存部分匹配值
		int[] next = new int[dest.length()];
		next[0] = 0; //如果字符串是长度为1 部分匹配值就是0
        //这里是扫描整个子串。所以i<dest.length
 //
		for(int i = 1, j = 0; i < dest.length(); i++) {
			//当dest.charAt(i) != dest.charAt(j) ，我们需要从next[j-1]获取新的j
			//直到我们发现 有  dest.charAt(i) == dest.charAt(j)成立才退出
			//这时kmp算法的核心点
			while(j > 0 && dest.charAt(i) != dest.charAt(j)) {
				j = next[j-1];
			}
			
			//当dest.charAt(i) == dest.charAt(j) 满足时，部分匹配值就是+1
			if(dest.charAt(i) == dest.charAt(j)) {
				j++;
			}
			next[i] = j;
		}
		return next;
	}
    
	
	//写出我们的kmp搜索算法
	/**
	 * 
	 * @param str1 源字符串
	 * @param str2 子串
	 * @param next 部分匹配表, 是子串对应的部分匹配表
	 * @return 如果是-1就是没有匹配到，否则返回第一个匹配的位置
	 */
	public static int kmpSearch(String str1, String str2, int[] next) {	
		//遍历 
        // 这里i指向str1,j指向str2
		for(int i = 0, j = 0; i < str1.length(); i++) {
			
			//需要处理 str1.charAt(i) ！= str2.charAt(j), 去调整j的大小
			//KMP算法核心点, 可以验证...
			while( j > 0 && str1.charAt(i) != str2.charAt(j)) {
				j = next[j-1]; 
			}
			
			if(str1.charAt(i) == str2.charAt(j)) {
				j++;
			}			
			if(j == str2.length()) {//找到了 // j = 3 i 
				return i - j + 1; //这里+1，是因为j++,i++,但是如果找到了j++了，循环没结束i没++
			}
		}
		return  -1;
	}

}

```



## 贪心算法

![DATA_STRUCTURES_ALGORITHMS150.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS150.png?raw=true)

穷举法的效率不高。

什么是贪心算法：

在对问题求解的时候，**每一步选择中都采取最好或者最优**的选择。他得到的结果不一定是最优解，但是他接近最优解。

```
使用贪婪算法，效率高:
1) 目前并没有算法可以快速计算得到准备的值， 使用贪婪算法，则可以得到非常接近的解，并且效率高。选择
策略上，因为需要覆盖全部地区的最小集合:
2) 遍历所有的广播电台, 找到一个覆盖了最多未覆盖的地区的电台(此电台可能包含一些已覆盖的地区，但没有关
系）
3) 将这个电台加入到一个集合中(比如 ArrayList), 想办法把该电台覆盖的地区在下次比较时去掉。
4) 重复第 1 步直到覆盖了全部的地区
```

![DATA_STRUCTURES_ALGORITHMS151.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS151.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS152.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS152.png?raw=true)

代码实现：

![DATA_STRUCTURES_ALGORITHMS153.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS153.png?raw=true)

```java
public class GreedyAlgorithm {
	public static void main(String[] args) {
		//创建广播电台,放入到Map
		HashMap<String,HashSet<String>> broadcasts = new HashMap<String, HashSet<String>>();
		//将各个电台放入到broadcasts
		HashSet<String> hashSet1 = new HashSet<String>();
		hashSet1.add("北京");
		hashSet1.add("上海");
		hashSet1.add("天津");
		
		HashSet<String> hashSet2 = new HashSet<String>();
		hashSet2.add("广州");
		hashSet2.add("北京");
		hashSet2.add("深圳");
		
		HashSet<String> hashSet3 = new HashSet<String>();
		hashSet3.add("成都");
		hashSet3.add("上海");
		hashSet3.add("杭州");
		
		HashSet<String> hashSet4 = new HashSet<String>();
		hashSet4.add("上海");
		hashSet4.add("天津");
		
		HashSet<String> hashSet5 = new HashSet<String>();
		hashSet5.add("杭州");
		hashSet5.add("大连");
	
		//加入到map
		broadcasts.put("K1", hashSet1);
		broadcasts.put("K2", hashSet2);
		broadcasts.put("K3", hashSet3);
		broadcasts.put("K4", hashSet4);
		broadcasts.put("K5", hashSet5);
		
		//allAreas 存放所有的地区
		HashSet<String> allAreas = new HashSet<String>();
		allAreas.add("北京");
		allAreas.add("上海");
		allAreas.add("天津");
		allAreas.add("广州");
		allAreas.add("深圳");
		allAreas.add("成都");
		allAreas.add("杭州");
		allAreas.add("大连");
		
		//创建ArrayList, 存放选择的电台集合
		ArrayList<String> selects = new ArrayList<String>();
		
		//定义一个临时的集合， 在遍历的过程中，存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
		HashSet<String> tempSet = new HashSet<String>();
		
		//定义给maxKey ， 保存在一次遍历过程中，能够覆盖最大未覆盖的地区对应的电台的key
		//如果maxKey 不为null , 则会加入到 selects
		String maxKey = null;
		while(allAreas.size() != 0) { // 如果allAreas 不为0, 则表示还没有覆盖到所有的地区
			//每进行一次while,需要
			maxKey = null;
			
			//遍历 broadcasts, 取出对应key
			for(String key : broadcasts.keySet()) {
				//每进行一次for
				tempSet.clear();
				//当前这个key能够覆盖的地区
				HashSet<String> areas = broadcasts.get(key);
				tempSet.addAll(areas);
				//求出tempSet 和   allAreas 集合的交集, 交集会赋给 tempSet
				tempSet.retainAll(allAreas);
				//如果当前这个集合包含的未覆盖地区的数量，比maxKey指向的集合地区还多
				//就需要重置maxKey
				// tempSet.size() >broadcasts.get(maxKey).size()) 体现出贪心算法的特点,每次都选择最优的
				if(tempSet.size() > 0 && 
						(maxKey == null || tempSet.size() >broadcasts.get(maxKey).size())){
					maxKey = key;
				}
			}
			//maxKey != null, 就应该将maxKey 加入selects
			if(maxKey != null) {
				selects.add(maxKey);
				//将maxKey指向的广播电台覆盖的地区，从 allAreas 去掉
				allAreas.removeAll(broadcasts.get(maxKey));
			}
			
		}
		
		System.out.println("得到的选择结果是" + selects);//[K1,K2,K3,K5]
		
	}

}
```





## 普利姆算法

### 修路问题

![DATA_STRUCTURES_ALGORITHMS154.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS154.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS155.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS155.png?raw=true)

什么是普利姆算法：

```
普利姆(Prim)算法求最小生成树，也就是在包含 n 个顶点的连通图中，找出只有(n-1)条边包含所有 n 个顶点的
连通子图，也就是所谓的极小连通子图
```

![DATA_STRUCTURES_ALGORITHMS156.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS156.png?raw=true)

代码实现：

```java
public class PrimAlgorithm {

	public static void main(String[] args) {
		//测试看看图是否创建ok
		char[] data = new char[]{'A','B','C','D','E','F','G'};
		int verxs = data.length;
		//邻接矩阵的关系使用二维数组表示,10000这个大数，表示两个点不联通
		int [][]weight=new int[][]{
            {10000,5,7,10000,10000,10000,2},
            {5,10000,10000,9,10000,10000,3},
            {7,10000,10000,10000,8,10000,10000},
            {10000,9,10000,10000,10000,4,10000},
            {10000,10000,8,10000,10000,5,4},
            {10000,10000,10000,4,5,10000,6},
            {2,3,10000,10000,4,6,10000},};
            
        //创建MGraph对象
        MGraph graph = new MGraph(verxs);
        //创建一个MinTree对象
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, verxs, data, weight);
        //输出
        minTree.showGraph(graph);
        //测试普利姆算法
        minTree.prim(graph, 1);// 
	}

}

//创建最小生成树->村庄的图
class MinTree {
	//创建图的邻接矩阵
	/**
	 * 
	 * @param graph 图对象
	 * @param verxs 图对应的顶点个数
	 * @param data 图的各个顶点的值
	 * @param weight 图的邻接矩阵
	 */
	public void createGraph(MGraph graph, int verxs, char data[], int[][] weight) {
		int i, j;
		for(i = 0; i < verxs; i++) {//顶点
			graph.data[i] = data[i];
			for(j = 0; j < verxs; j++) {
				graph.weight[i][j] = weight[i][j];
			}
		}
	}
	
	//显示图的邻接矩阵
	public void showGraph(MGraph graph) {
		for(int[] link: graph.weight) {
			System.out.println(Arrays.toString(link));
		}
	}
	
	//编写prim算法，得到最小生成树
	/**
	 * 
	 * @param graph 图
	 * @param v 表示从图的第几个顶点开始生成'A'->0 'B'->1...
	 */
	public void prim(MGraph graph, int v) {
		//visited[] 标记结点(顶点)是否被访问过
		int visited[] = new int[graph.verxs];
		//visited[] 默认元素的值都是0, 表示没有访问过
//		for(int i =0; i <graph.verxs; i++) {
//			visited[i] = 0;
//		}
		
		//把当前这个结点标记为已访问
		visited[v] = 1;
		//h1 和 h2 记录两个顶点的下标
		int h1 = -1;
		int h2 = -1;
		int minWeight = 10000; //将 minWeight 初始成一个大数，后面在遍历过程中，会被替换
		for(int k = 1; k < graph.verxs; k++) {//因为有 graph.verxs顶点，普利姆算法结束后，有 graph.verxs-1边
			
			//这个是确定每一次生成的子图 ，和哪个结点的距离最近
			for(int i = 0; i < graph.verxs; i++) {// i结点表示被访问过的结点
				for(int j = 0; j< graph.verxs;j++) {//j结点表示还没有访问过的结点
					if(visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
						//替换minWeight(寻找已经访问过的结点和未访问过的结点间的权值最小的边)
						minWeight = graph.weight[i][j];
						h1 = i;
						h2 = j;
					}
				}
			}
			//找到一条边是最小
			System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值:" + minWeight);
			//将当前这个结点标记为已经访问
			visited[h2] = 1;
			//minWeight 重新设置为最大值 10000
			minWeight = 10000;
		}
		
	}
}

class MGraph {
	int verxs; //表示图的节点个数
	char[] data;//存放结点数据
	int[][] weight; //存放边，就是我们的邻接矩阵
	
	public MGraph(int verxs) {
		this.verxs = verxs;
		data = new char[verxs];
		weight = new int[verxs][verxs];
	}
}
```



## 克鲁斯卡尔算法

### 公交站问题

(也是求最小树)

![DATA_STRUCTURES_ALGORITHMS157.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS157.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS158.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS158.png?raw=true)

克鲁斯卡尔算法图解说明：

![DATA_STRUCTURES_ALGORITHMS159.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS159.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS160.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS160.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS164.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS164.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS161.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS161.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS162.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS162.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS163.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS163.png?raw=true)

克鲁斯卡尔算法的难点在于判断回路，判断回路的难点在于判断终点。

代码实现：

第一步：构建图：



```java
public class KruskalCase {

	private int edgeNum; //边的个数
	private char[] vertexs; //顶点数组
	private int[][] matrix; //邻接矩阵
	//使用 INF 表示两个顶点不能连通
	private static final int INF = Integer.MAX_VALUE;
	
	public static void main(String[] args) {
		char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
		//克鲁斯卡尔算法的邻接矩阵  
	      int matrix[][] = {
	      /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
	/*A*/ {   0,  12, INF, INF, INF,  16,  14},
	/*B*/ {  12,   0,  10, INF, INF,   7, INF},
	/*C*/ { INF,  10,   0,   3,   5,   6, INF},
	/*D*/ { INF, INF,   3,   0,   4, INF, INF},
	/*E*/ { INF, INF,   5,   4,   0,   2,   8},
	/*F*/ {  16,   7,   6, INF,   2,   0,   9},
	/*G*/ {  14, INF, INF, INF,   8,   9,   0}}; 
	      //大家可以在去测试其它的邻接矩阵，结果都可以得到最小生成树.
	      
	      //创建KruskalCase 对象实例
	      KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
	      //输出构建的
	      kruskalCase.print();
	      kruskalCase.kruskal();
	      
	}
	
	//构造器
	public KruskalCase(char[] vertexs, int[][] matrix) {
		//初始化顶点数和边的个数
		int vlen = vertexs.length;
		
		//初始化顶点, 复制拷贝的方式
		this.vertexs = new char[vlen];
		for(int i = 0; i < vertexs.length; i++) {
			this.vertexs[i] = vertexs[i];
		}
		
		//初始化边, 使用的是复制拷贝的方式
		this.matrix = new int[vlen][vlen];
		for(int i = 0; i < vlen; i++) {
			for(int j= 0; j < vlen; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
		//统计边的条数
		for(int i =0; i < vlen; i++) {
			for(int j = i+1; j < vlen; j++) {
				if(this.matrix[i][j] != INF) {
					edgeNum++;
				}
			}
		}
		
	}
	
	//打印邻接矩阵
	public void print() {
		System.out.println("邻接矩阵为: \n");
		for(int i = 0; i < vertexs.length; i++) {
			for(int j=0; j < vertexs.length; j++) {
				System.out.printf("%12d", matrix[i][j]);
			}
			System.out.println();//换行
		}
	}

 
}
```

核心：

```java
他的目的啊在于对图的变进行排序
//创建一个类EData ，它的对象实例就表示一条边
class EData {
	char start; //边的一个点
	char end; //边的另外一个点
	int weight; //边的权值
	//构造器
	public EData(char start, char end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	//重写toString, 便于输出边信息
	@Override
	public String toString() {
		return "EData [<" + start + ", " + end + ">= " + weight + "]";
    }
}

/**
	 * 功能：对边进行排序处理, 冒泡排序
	 * @param edges 边的集合
	 */
	private void sortEdges(EData[] edges) {
		for(int i = 0; i < edges.length - 1; i++) {
			for(int j = 0; j < edges.length - 1 - i; j++) {
				if(edges[j].weight > edges[j+1].weight) {//交换
					EData tmp = edges[j];
					edges[j] = edges[j+1];
					edges[j+1] = tmp;
				}
			}
 		}
	}
	/**
	 * 
	 * @param ch 顶点的值，比如'A','B'
	 * @return 返回ch顶点对应的下标，如果找不到，返回-1
	 */
	private int getPosition(char ch) {
		for(int i = 0; i < vertexs.length; i++) {
			if(vertexs[i] == ch) {//找到
				return i;
			}
		}
		//找不到,返回-1
		return -1;
	}
	/**
	 * 功能: 获取图中边，放到EData[] 数组中，后面我们需要遍历该数组
	 * 是通过matrix 邻接矩阵来获取
	 * EData[] 形式 [['A','B', 12], ['B','F',7], .....]
	 * @return
	 */
	private EData[] getEdges() {
		int index = 0;
		EData[] edges = new EData[edgeNum];
		for(int i = 0; i < vertexs.length; i++) {
			for(int j=i+1; j <vertexs.length; j++) {
				if(matrix[i][j] != INF) {
					edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
				}
			}
		}
		return edges;
	}
```



```java
/**
	 * 功能: 获取下标为i的顶点的终点(), 用于后面判断两个顶点的终点是否相同
	 * @param ends ： 数组就是记录了各个顶点对应的终点是哪个,ends 数组是在遍历过程中，逐步形成
	 * @param i : 表示传入的顶点对应的下标
	 * @return 返回的就是 下标为i的这个顶点对应的终点的下标
	 */
	private int getEnd(int[] ends, int i) { // i = 4 [0,0,0,0,5,0,0,0,0,0,0,0]
		while(ends[i] != 0) { //这里不等于0，取出对应下标
			i = ends[i];
		}
        //等于零，还没有加入最小树，就返回自身的下标
		return i;
	}

public void kruskal() {
		int index = 0; //表示最后结果数组的索引
		int[] ends = new int[edgeNum]; //用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点
		//创建结果数组, 保存最后的最小生成树
		EData[] rets = new EData[edgeNum];
		
		//获取图中 所有的边的集合 ， 一共有12边
		EData[] edges = getEdges();
		System.out.println("图的边的集合=" + Arrays.toString(edges) + " 共"+ edges.length); //12
		
		//按照边的权值大小进行排序(从小到大)
		sortEdges(edges);
		
		//遍历edges 数组，将边添加到最小生成树中时，判断是准备加入的边否形成了回路，如果没有，就加入 rets, 否则不能加入
		for(int i=0; i < edgeNum; i++) {
			//获取到第i条边的第一个顶点(起点)
			int p1 = getPosition(edges[i].start); //p1=4
			//获取到第i条边的第2个顶点
			int p2 = getPosition(edges[i].end); //p2 = 5
			
			//获取p1这个顶点在已有最小生成树中的终点
			int m = getEnd(ends, p1); //m = 4
			//获取p2这个顶点在已有最小生成树中的终点
			int n = getEnd(ends, p2); // n = 5
			//是否构成回路
			if(m != n) { //没有构成回路
				ends[m] = n; // 设置m 在"已有最小生成树"中的终点 <E,F> [0,0,0,0,5,0,0,0,0,0,0,0]
				rets[index++] = edges[i]; //有一条边加入到rets数组
			}
		}
		//<E,F> <C,D> <D,E> <B,F> <E,G> <A,B>。
		//统计并打印 "最小生成树", 输出  rets
		System.out.println("最小生成树为");
		for(int i = 0; i < index; i++) {
			System.out.println(rets[i]);
		}
		
	}
```

![DATA_STRUCTURES_ALGORITHMS165.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS165.png?raw=true)

## 迪杰斯特拉算法

### 应用场景-最短路径问题

![DATA_STRUCTURES_ALGORITHMS166.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS166.png?raw=true)

地杰斯特拉图解思路：

![DATA_STRUCTURES_ALGORITHMS167.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS167.png?raw=true)

代码实现：

先构建图：

```java
public class DijkstraAlgorithm {

	public static void main(String[] args) {
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		//邻接矩阵
		int[][] matrix = new int[vertex.length][vertex.length];
		final int N = 65535;// 表示不可以连接
		matrix[0]=new int[]{N,5,7,N,N,N,2};  
        matrix[1]=new int[]{5,N,N,9,N,N,3};  
        matrix[2]=new int[]{7,N,N,N,8,N,N};  
        matrix[3]=new int[]{N,9,N,N,N,4,N};  
        matrix[4]=new int[]{N,N,8,N,N,5,4};  
        matrix[5]=new int[]{N,N,N,4,5,N,6};  
        matrix[6]=new int[]{2,3,N,N,4,6,N};
        //创建 Graph对象
        Graph graph = new Graph(vertex, matrix);
        //测试, 看看图的邻接矩阵是否ok
        graph.showGraph();
        //测试迪杰斯特拉算法
        graph.dsj(2);//C
        graph.showDijkstra();
        
        
	}

}

class Graph {
	private char[] vertex; // 顶点数组
	private int[][] matrix; // 邻接矩阵
	private VisitedVertex vv; //已经访问的顶点的集合

	// 构造器
	public Graph(char[] vertex, int[][] matrix) {
		this.vertex = vertex;
		this.matrix = matrix;
	}
	
	//显示结果
	public void showDijkstra() {
		vv.show();
	}

	// 显示图
	public void showGraph() {
		for (int[] link : matrix) {
			System.out.println(Arrays.toString(link));
		}
	}
	
	//迪杰斯特拉算法实现
	/**
	 * 
	 * @param index 表示出发顶点对应的下标
	 */
	public void dsj(int index) {
		vv = new VisitedVertex(vertex.length, index);
		update(index);//更新index顶点到周围顶点的距离和前驱顶点
		for(int j = 1; j <vertex.length; j++) {
			index = vv.updateArr();// 选择并返回新的访问顶点，每次选择1个点，要遍历所以点就需要选择访问点n次
			update(index); // 更新index顶点到周围顶点的距离和前驱顶点
		} 
	}
	
	
	//更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点,
	private void update(int index) {
		int len = 0;
		//遍历我们的邻接矩阵的 matrix[index]行(存储了当前节点与其连接节点间的信息)，对集合对象进行更新
		for(int j = 0; j < matrix[index].length; j++) {
			// len 含义是 : 出发顶点到index顶点的距离 + 从index顶点到j顶点的距离的和 
            //（因为是广度优先，一层一层遍历的 见图168）
			len = vv.getDis(index) + matrix[index][j];
			// 如果j顶点没有被访问过，并且 len 小于出发顶点到j顶点的距离，就需要更新
			if(!vv.in(j) && len < vv.getDis(j)) { // 见图169
				vv.updatePre(j, index); //更新j顶点的前驱为index顶点
				vv.updateDis(j, len); //更新出发顶点到j顶点的距离
			}
		}
	}
}

// 已访问顶点集合， 这个集合对象是动态更新的
class VisitedVertex {
	// 记录各个顶点是否访问过 1表示访问过,0未访问,会动态更新
	public int[] already_arr;
	// 每个下标对应的值为前一个顶点下标, 会动态更新
	public int[] pre_visited;
	// 记录出发顶点到其他所有顶点的距离,比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
	public int[] dis;
	
	//构造器
	/**
	 * 
	 * @param length :表示顶点的个数 
	 * @param index: 出发顶点对应的下标, 比如G顶点，下标就是6
	 */
	public VisitedVertex(int length, int index) {
		this.already_arr = new int[length];
		this.pre_visited = new int[length];
		this.dis = new int[length];
		//初始化 dis数组
        //（先全部置为最大值，然后将自己置为0）
		Arrays.fill(dis, 65535);
		this.already_arr[index] = 1; //设置出发顶点被访问过
		this.dis[index] = 0;//设置出发顶点的访问距离为0 
				
	}
	/**
	 * 功能: 判断index顶点是否被访问过
	 * @param index
	 * @return 如果访问过，就返回true, 否则访问false
	 */
	public boolean in(int index) {
		return already_arr[index] == 1;
	}
	
	/**
	 * 功能: 更新出发顶点到index顶点的距离
	 * @param index
	 * @param len
	 */
	public void updateDis(int index, int len) {
		dis[index] = len;
	}
	/**
	 * 功能: 更新pre这个顶点的前驱顶点为index顶点
	 * @param pre
	 * @param index
	 * 下标为pre的顶点变为了index
	 */
	public void updatePre(int pre, int index) {
		pre_visited[pre] = index;
	}
	/**
	 * 功能:返回出发顶点到index顶点的距离
	 * @param index
	 */
	public int getDis(int index) {
		return dis[index];
	}
	
	
	/**
	 * 继续选择并返回新的访问顶点， 比如这里的G 完后，就是 A点作为新的访问顶点(注意不是出发顶点)
	 * @return
	 */
	public int updateArr() {
		int min = 65535, index = 0;
		for(int i = 0; i < already_arr.length; i++) {
			if(already_arr[i] == 0 && dis[i] < min ) { 
				min = dis[i];
				index = i;
			}
		} //
		//更新 index 顶点被访问过
		already_arr[index] = 1;
		return index;
	}
	
	//显示最后的结果
	//即将三个数组的情况输出
	public void show() {
		
		System.out.println("==========================");
		//输出already_arr
		for(int i : already_arr) {
			System.out.print(i + " ");
		}
		System.out.println();
		//输出pre_visited
		for(int i : pre_visited) {
			System.out.print(i + " ");
		}
		System.out.println();
		//输出dis
		for(int i : dis) {
			System.out.print(i + " ");
		}
		System.out.println();
		//为了好看最后的最短距离，我们处理
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		int count = 0;
		for (int i : dis) {
			if (i != 65535) {
				System.out.print(vertex[count] + "("+i+") ");
			} else {
				System.out.println("N ");
			}
			count++;
		}
		System.out.println();
		
	}

}
```

![DATA_STRUCTURES_ALGORITHMS168.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS168.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS169.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS169.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS170.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS170.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS171.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS171.png?raw=true)

总结： dijkstra就是广度优先遍历选最短。

在上面的代码中做一些改进，打印出最短路径

```java
class Graph {
    private char[] vertex; // 顶点数组
    private int[][] matrix; // 邻接矩阵
    private VisitedVertex vv; //已经访问的顶点的集合
    public StringBuilder[] path = new StringBuilder[7];
    boolean [] flag = new boolean [7];

    // 构造器
    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
        for (int i = 0; i < 7; i++) {
            path[i] = new StringBuilder();
        }

    }

    // 显示路径
    public void showpath() {
//        System.out.println("最短路径"+path.toString());
//        Object[] paths= path.toArray();
//        for (Object i : paths)
//        {
//            Integer m=(Integer)i;
//            System.out.print(vertex[m] + "-》 ");
//        }
//        System.out.println();
//        path.clear();
//        path.add(6);
//        StringBuilder[] path=vv.path;
        for (int i = 0; i < 7; i++) {
            System.out.println(path[i].toString());
        }
    }

    //迪杰斯特拉算法实现

    /**
     * @param index 表示出发顶点对应的下标
     */
    public void dsj(int index) {
        vv = new VisitedVertex(vertex.length, index);
        update(index);//更新index顶点到周围顶点的距离和前驱顶点
        for (int j = 1; j < vertex.length; j++) {
            index = vv.updateArr();// 选择并返回新的访问顶点
            update(index); // 更新index顶点到周围顶点的距离和前驱顶点
        }
    }


    //更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点,
    private void update(int index) {
        int len = 0;
        //根据遍历我们的邻接矩阵的  matrix[index]行
        StringBuilder tmp = new StringBuilder();
        tmp.append(path[index]);
        for (int j = 0; j < matrix[index].length; j++) {
            // len 含义是 : 出发顶点到index顶点的距离 + 从index顶点到j顶点的距离的和
            len = vv.getDis(index) + matrix[index][j];
            // 如果j顶点没有被访问过，并且 len 小于出发顶点到j顶点的距离，就需要更新
            if (!vv.in(j) && len < vv.getDis(j)) {
                if(flag[j])
                {
                    path[j]=new StringBuilder();
                }
                path[j].append(tmp);

                path[j].append(j);
                flag[j]=true;
                vv.updatePre(j, index); //更新j顶点的前驱为index顶点
                vv.updateDis(j, len); //更新出发顶点到j顶点的距离

            }

        }

    }
}
```

![DATA_STRUCTURES_ALGORITHMS172.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS172.png?raw=true)

update ：每次找到倒是起始点到改点的距离，但是我们起始点在不断的变化，所以我们需要使用tmp存储下上一个起始点到数组中各店的路径，如果path[j].append(j); 说明已经找到了一条从起始点到改点的路径。但是这里我们可能找到一条更优的路径，所以使用一个标记数组。 如果找到了一条后就让flag=true. 如果下次找到一条更好的，就将之前的清掉。



## 弗洛伊德算法

```
和迪杰特斯拉算法一样，他也是用来在加权图中求顶点间的最短路径的算法。
改算法设计巧妙，易于理解，但是同时他的时间复杂度比较高。 
与迪杰特斯拉算法 相比：迪杰特斯拉算法是求一个顶点(出发顶点)到其他各个顶点的最短路径。
弗洛伊德算法是求各个顶点间的最短路径（每个顶点都是出发顶点）
```

![DATA_STRUCTURES_ALGORITHMS173.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS173.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS174.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS174.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS175.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS175.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS176.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS176.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS177.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS177.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS178.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS178.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS179.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS179.png?raw=true)

```java
public class FloydAlgorithm {

	public static void main(String[] args) {
		// 测试看看图是否创建成功
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		//创建邻接矩阵
		int[][] matrix = new int[vertex.length][vertex.length];
		final int N = 65535;
		matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
		matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
		matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
		matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
		matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
		matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
		matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };
		
		//创建 Graph 对象
		Graph graph = new Graph(vertex.length, matrix, vertex);
		//调用弗洛伊德算法
		graph.floyd();
		graph.show();
	}

}

// 创建图
class Graph {
	private char[] vertex; // 存放顶点的数组
	private int[][] dis; // 保存，从各个顶点出发到其它顶点的距离，最后的结果，也是保留在该数组
	private int[][] pre;// 保存到达目标顶点的前驱顶点

	// 构造器
	/**
	 * 
	 * @param length
	 *            大小
	 * @param matrix
	 *            邻接矩阵
	 * @param vertex
	 *            顶点数组
	 */
	public Graph(int length, int[][] matrix, char[] vertex) {
		this.vertex = vertex;
		this.dis = matrix;
		this.pre = new int[length][length];
		// 对pre数组初始化, 注意存放的是前驱顶点的下标
		for (int i = 0; i < length; i++) {
			Arrays.fill(pre[i], i);
		}
	}

	// 显示pre数组和dis数组
	public void show() {

		//为了显示便于阅读，我们优化一下输出
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		for (int k = 0; k < dis.length; k++) {
			// 先将pre数组输出的一行
			for (int i = 0; i < dis.length; i++) {
				System.out.print(vertex[pre[k][i]] + " ");
			}
			System.out.println();
			// 输出dis数组的一行数据
			for (int i = 0; i < dis.length; i++) {
				System.out.print("("+vertex[k]+"到"+vertex[i]+"的最短路径是" + dis[k][i] + ") ");
			}
			System.out.println();
			System.out.println();

		}

	}
	
	//弗洛伊德算法, 比较容易理解，而且容易实现
	public void floyd() {
		int len = 0; //变量保存距离
		//对中间顶点遍历， k 就是中间顶点的下标 [A, B, C, D, E, F, G] 
		for(int k = 0; k < dis.length; k++) { // 
			//从i顶点开始出发 [A, B, C, D, E, F, G]
			for(int i = 0; i < dis.length; i++) {
				//到达j顶点 // [A, B, C, D, E, F, G]
				for(int j = 0; j < dis.length; j++) {
					len = dis[i][k] + dis[k][j];// => 求出从i 顶点出发，经过 k中间顶点，到达 j 顶点距离
					if(len < dis[i][j]) {//如果len小于 dis[i][j]
						dis[i][j] = len;//更新距离
						pre[i][j] = pre[k][j];//更新前驱顶点
					}
				}
			}
		}
	}
}

```




## 骑士周游回溯算法

应用场景，马踏棋盘游戏。

![DATA_STRUCTURES_ALGORITHMS180.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS180.png?raw=true)

马踏棋盘问题(骑士周游问题)实际上是图的深度优先搜索(DFS)的应用

![DATA_STRUCTURES_ALGORITHMS181.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS181.png?raw=true)

代码实现：

```java
public class HorseChessboard {
//创建棋盘
	private static int X; // 棋盘的列数 ，因为马走日，所以x来表示列比较方便
	private static int Y; // 棋盘的行数
	//创建一个数组，标记棋盘的各个位置是否被访问过
	private static boolean visited[]; //（他是一个一维数组）
	//使用一个属性，标记是否棋盘的所有位置都被访问
	private static boolean finished; // 如果为true,表示成功
	
	public static void main(String[] args) {
		System.out.println("骑士周游算法，开始运行~~");
		//测试骑士周游算法是否正确
		X = 8;
		Y = 8;
		int row = 1; //马儿初始位置的行，从1开始编号
		int column = 1; //马儿初始位置的列，从1开始编号
		//创建棋盘
		int[][] chessboard = new int[X][Y];
		visited = new boolean[X * Y];//初始值都是false
		//测试一下耗时
		long start = System.currentTimeMillis();
		traversalChessboard(chessboard, row - 1, column - 1, 1);
		long end = System.currentTimeMillis();
		System.out.println("共耗时: " + (end - start) + " 毫秒");
		
		//输出棋盘的最后情况
		for(int[] rows : chessboard) {
			for(int step: rows) {
				System.out.print(step + "\t");
			}
			System.out.println();
		}
	}
    
    /**
	 * 功能： 根据当前位置(Point对象)，计算马儿还能走哪些位置(Point)，并放入到一个集合中(ArrayList), 最多有8个位置
	 * @param curPoint
	 * @return
	 */
	public static ArrayList<Point> next(Point curPoint) {
		//创建一个ArrayList
		ArrayList<Point> ps = new ArrayList<Point>();
		//创建一个Point
		Point p1 = new Point();
		//表示马儿可以走5这个位置
		if((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y -1) >= 0) {//见图182
			ps.add(new Point(p1));
		}
		//判断马儿可以走6这个位置
		if((p1.x = curPoint.x - 1) >=0 && (p1.y=curPoint.y-2)>=0) {
			ps.add(new Point(p1));
		}
		//判断马儿可以走7这个位置
		if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) {
			ps.add(new Point(p1));
		}
		//判断马儿可以走0这个位置
		if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) {
			ps.add(new Point(p1));
		}
		//判断马儿可以走1这个位置
		if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) {
			ps.add(new Point(p1));
		}
		//判断马儿可以走2这个位置
		if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) {
			ps.add(new Point(p1));
		}
		//判断马儿可以走3这个位置
		if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) {
			ps.add(new Point(p1));
		}
		//判断马儿可以走4这个位置
		if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) {
			ps.add(new Point(p1));
		}
		return ps;
	}
	
	/**
	 * 完成骑士周游问题的算法
	 * @param chessboard 棋盘
	 * @param row 马儿当前的位置的行 从0开始 
	 * @param column 马儿当前的位置的列  从0开始
	 * @param step 是第几步 ,初始位置就是第1步 
	 */
	public static void traversalChessboard(int[][] chessboard, int row, int column, int step) {
		chessboard[row][column] = step;
		//row = 4 X = 8 column = 4 = 4 * 8 + 4 = 36
		visited[row * X + column] = true; //标记该位置已经访问
		//获取当前位置可以走的下一个位置的集合 
		ArrayList<Point> ps = next(new Point(column, row)); //我们point在next()中取的时候x表示对应的列，y表示对应的行，所以这里需要设置对应的值。
		//对ps进行排序,排序的规则就是对ps的所有的Point对象的下一步的位置的数目，进行非递减排序
        //因为选择的越多，回溯的情况就越多
		sort(ps);
		//遍历 ps
		while(!ps.isEmpty()) {
			Point p = ps.remove(0);//取出下一个可以走的位置
			//判断该点是否已经访问过
			if(!visited[p.y * X + p.x]) {//说明还没有访问过
				traversalChessboard(chessboard, p.y, p.x, step + 1);
			}
		}
		//判断马儿是否完成了任务，使用   step 和应该走的步数比较 ， 
		//如果没有达到数量，则表示没有完成任务，将整个棋盘置0
		//说明: step < X * Y  成立的情况有两种
		//1. 棋盘到目前位置,仍然没有走完
		//2. 棋盘已经走完了，但是处于一个回溯过程
		if(step < X * Y && !finished ) { //回溯，让当前这个节点变成没有访问的状态，上面的只是假定他是可以走通的
			chessboard[row][column] = 0;
			visited[row * X + column] = false;
		} else {
			finished = true;
		}
		
	}
	
//贪心算法优化:他的核心就是我们每一次的选择都要是最好的选择
	//根据当前这个一步的所有的下一步的选择位置，进行非递减排序, 减少回溯的次数
	public static void sort(ArrayList<Point> ps) {
		ps.sort(new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				//获取到o1的下一步的所有位置个数
				int count1 = next(o1).size();
				//获取到o2的下一步的所有位置个数
				int count2 = next(o2).size();
				if(count1 < count2) {
					return -1;
				} else if (count1 == count2) {
					return 0;
				} else {
					return 1;
				}
			}
			
		});
	}
}
```



![DATA_STRUCTURES_ALGORITHMS182.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS182.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS183.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS183.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS184.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS184.png?raw=true)