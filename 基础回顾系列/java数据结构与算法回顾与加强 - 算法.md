# java数据结构与算法

希尔排序与快速排序的实现方法需要反复推导，查看。

# 算法

## 排序算法

排序是将一组数据，依指定的顺序进行排列的过程。

![DATA_STRUCTURES_ALGORITHMS39.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS39.png?raw=true)

### 时间复杂度

**时间频度**：   一个算法花费的时间与算法中语句的执行次数成正比例，哪个算法中语句执行次数多，它花费时间就多。一个算法中的语句执行次数称为语句频度或时间频度。记为T(n)

![DATA_STRUCTURES_ALGORITHMS40.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS40.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS41.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS41.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS42.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS42.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS43.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS43.png?raw=true)

总结：随着 n 值变大，T(n) 的常数项可以忽略，低次项可以忽略，系数项可以忽略。

时间复杂度： T(n) 忽略 忽略了上面的项就是代码的时间复杂度。

```
计算时间复杂度的方法：
用常数 1 代替运行时间中的所有加法常数 T(n)=n²+7n+6 => T(n)=n²+7n+1
修改后的运行次数函数中，只保留最高阶项 T(n)=n²+7n+1 => T(n) = n²
去除最高阶项的系数 T(n) = n² => T(n) = n² => O(n²)
```

**常见的时间复杂度**：

![DATA_STRUCTURES_ALGORITHMS44.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS44.png?raw=true)

```
1）常数阶 O(1)
2) 对数阶 O(log2n)
3) 线性阶 O(n)
4) 线性对数阶 O(nlog2n)
5) 平方阶 O(n^2)
6) 立方阶 O(n^3)
7) k 次方阶 O(n^k)
8) 指数阶 O(2^n)
```

![DATA_STRUCTURES_ALGORITHMS45.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS45.png?raw=true)

对数阶： 每次数值变化n倍。这样 2 ^n =1024 ，n=10 , 数据规模1024 ，才执行10次，可以看出对数阶效率很高。

![DATA_STRUCTURES_ALGORITHMS46.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS46.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS47.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS47.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS48.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS48.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS49.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS49.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS50.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS50.png?raw=true)

**平均时间复杂度和最坏时间复杂度**

顾名思义平均时间复杂度就是所有可能的输入实例均以等概率出现的情况下，该算法的运行时间。 最坏时间复杂度就是最坏情况下的时间复杂度。我们一般讨论的就是最坏时间复杂度。 保证了程序的运行时间不会比最坏情况更长。

![DATA_STRUCTURES_ALGORITHMS51.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS51.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS63.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS63.png?raw=true)

### 冒泡排序

冒泡排序（Bubble Sorting）的基本思想是：通过对  **待排序序列**从前向后（从下标较小的元素开始）,依次比较相邻元素的值，若发现逆序则交换，使值较大的元素逐渐从前移向后部，就象水底下的气泡一样逐渐向上冒。

![DATA_STRUCTURES_ALGORITHMS52.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS52.png?raw=true)

冒泡推导：

```java
public static void main(String[] args) {
    	int arr[] = {3, 9, -1, 10, 20};	
		//为了容量理解，我们把冒泡排序的演变过程，给大家展示
		
		int temp=0;	
		//第一趟排序：将醉倒的数排在最后
		for (int j = 0; j < arr.length - 1 ; j++) {
			// 如果前面的数比后面的数大，则交换
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}

		
		// 第二趟排序，就是将第二大的数排在倒数第二位
		
		for (int j = 0; j < arr.length - 1 - 1 ; j++) {
			// 如果前面的数比后面的数大，则交换
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}
		
		System.out.println("第二趟排序后的数组"); 
		System.out.println(Arrays.toString(arr));
		
		
		// 第三趟排序，就是将第三大的数排在倒数第三位
		
		for (int j = 0; j < arr.length - 1 - 2; j++) {
			// 如果前面的数比后面的数大，则交换
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}

		System.out.println("第三趟排序后的数组");
		System.out.println(Arrays.toString(arr));
		
		// 第四趟排序，就是将第4大的数排在倒数第4位

		for (int j = 0; j < arr.length - 1 - 3; j++) {
			// 如果前面的数比后面的数大，则交换
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}

		System.out.println("第四趟排序后的数组");
		System.out.println(Arrays.toString(arr)); 
		
	}
```

前面几个循环是重复的，可以写一个方法将他包起来

```java
	int temp=0;	
		for(i=0;i<arr.length-1;i++)
		{

		//第一趟排序：将醉倒的数排在最后
			for (int j = 0; j < arr.length - 1-i ; j++) {
				// 如果前面的数比后面的数大，则交换
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
			
		System.out.println("第"+i+1+"趟排序后的数组"); 
		System.out.println(Arrays.toString(arr));
		}
```

优化：

```java
// 将前面额冒泡排序算法，优化封装成一个方法
	public static void bubbleSort(int[] arr) {
		// 冒泡排序 的时间复杂度 O(n^2), 自己写出
		int temp = 0; // 临时变量
		boolean flag = false; // 标识变量，表示是否进行过交换
		for (int i = 0; i < arr.length - 1; i++) {

			for (int j = 0; j < arr.length - 1 - i; j++) {
				// 如果前面的数比后面的数大，则交换
				if (arr[j] > arr[j + 1]) {
					flag = true;
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
			//System.out.println("第" + (i + 1) + "趟排序后的数组");
			//System.out.println(Arrays.toString(arr));

			if (!flag) { // 在一趟排序中，一次交换都没有发生过
				break;
			} else {
				flag = false; // 重置flag!!!, 进行下次判断
			}
		}
	}
```



### 选择排序

选择排序与冒泡排序的区别： 冒泡是每次比较都会交换位置。而选择是先比较找出最小，最大值。然后再一次大循环中才进行交换位置。 (就是先选择出最值，然后与指定位置进行交换位置) ， 选择排序理论上应该比冒泡排序快，因为他节省了很多的交换操作。 

![DATA_STRUCTURES_ALGORITHMS53.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS53.png?raw=true)

选择排序推导: 

```java
	//使用逐步推导的方式来，讲解选择排序
		//第1轮
		//原始的数组 ： 	101, 34, 119, 1
		//第一轮排序 :   	1, 34, 119, 101
		//算法 先简单--》 做复杂， 就是可以把一个复杂的算法，拆分成简单的问题-》逐步解决
		
		//第1轮: 先假定最小值就是第一个
		int minIndex = 0;
		int min = arr[0];
		for(int j = 0 + 1; j < arr.length; j++) {
			if (min > arr[j]) { //说明假定的最小值，并不是最小
				min = arr[j]; //重置min
				minIndex = j; //重置minIndex
			}
		}
		
		
		//将最小值，放在arr[0], 即交换
		if(minIndex != 0) {
			arr[minIndex] = arr[0];
			arr[0] = min;
		}	
		System.out.println("第1轮后~~");
		System.out.println(Arrays.toString(arr));// 1, 34, 119, 101
		
		
		//第2轮
		minIndex = 1;
		min = arr[1];
		for (int j = 1 + 1; j < arr.length; j++) {
			if (min > arr[j]) { // 说明假定的最小值，并不是最小
				min = arr[j]; // 重置min
				minIndex = j; // 重置minIndex
			}
		}

		// 将最小值，放在arr[0], 即交换
		if(minIndex != 1) {
			arr[minIndex] = arr[1];
			arr[1] = min;
		}

		System.out.println("第2轮后~~");
		System.out.println(Arrays.toString(arr));// 1, 34, 119, 101
		
		//第3轮
		minIndex = 2;
		min = arr[2];
		for (int j = 2 + 1; j < arr.length; j++) {
			if (min > arr[j]) { // 说明假定的最小值，并不是最小
				min = arr[j]; // 重置min
				minIndex = j; // 重置minIndex
			}
		}

		// 将最小值，放在arr[0], 即交换
		if (minIndex != 2) {
			arr[minIndex] = arr[2];
			arr[2] = min;
		}

		System.out.println("第3轮后~~");
		System.out.println(Arrays.toString(arr));// 1, 34, 101, 119 
```

选择排序:

```java
	public static void selectSort(int[] arr) {
		//在推导的过程，我们发现了规律，因此，可以使用for来解决
		//选择排序时间复杂度是 O(n^2)
		for (int i = 0; i < arr.length - 1; i++) {
			int minIndex = i;
			int min = arr[i];
			for (int j = i + 1; j < arr.length; j++) {
				if (min > arr[j]) { // 说明假定的最小值，并不是最小
					min = arr[j]; // 重置min
					minIndex = j; // 重置minIndex
				} 
			}

			// 将最小值，放在arr[0], 即交换
			if (minIndex != i) {
				arr[minIndex] = arr[i];
				arr[i] = min;
			}

		}
    }
```



### 插入排序

以插入的方式让数据变为有序。将无序表中的数据在有序表中找到合适的位置进行插入。

基本思想是：把 n 个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有 n-1 个元素，排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表

![DATA_STRUCTURES_ALGORITHMS54.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS54.png?raw=true)

推导过程

```java
//使用逐步推导的方式来讲解，便利理解
		//第1轮 {101, 34, 119, 1};  => {34, 101, 119, 1}
		
		//{101, 34, 119, 1}; => {101,101,119,1}
		//定义待插入的数
		int insertVal = arr[1];
         //定义待插入数应该插入的下标，就是待插入数和这个下标的数进行比较，一开始待插入数的前一个
		int insertIndex = 1 - 1; //即arr[1]的前面这个数的下标
		
		//给insertVal 找到插入的位置
		//说明
		//1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
		//2. insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
		//3. 就需要将 arr[insertIndex] 后移
		while(insertIndex >= 0 && insertVal < arr[insertIndex] ) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}
		//当退出while循环时，说明插入的位置找到, insertIndex + 1
	// 举例 	{101, 344, 119, 1}; => {101,344,119,1} 带入上面代码
		arr[insertIndex + 1] = insertVal;
		
		System.out.println("第1轮插入");
		System.out.println(Arrays.toString(arr));
		
		//第2轮
		insertVal = arr[2];
		insertIndex = 2 - 1; 
		
		while(insertIndex >= 0 && insertVal < arr[insertIndex] ) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}
		
		arr[insertIndex + 1] = insertVal;
		System.out.println("第2轮插入");
		System.out.println(Arrays.toString(arr));
		
		
		//第3轮
		insertVal = arr[3];
		insertIndex = 3 - 1;

		while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}

		arr[insertIndex + 1] = insertVal;
		System.out.println("第3轮插入");
		System.out.println(Arrays.toString(arr));
```

插入排序

```java
//插入排序
	public static void insertSort(int[] arr) {
		int insertVal = 0;
		int insertIndex = 0;
		//使用for循环来把代码简化
		for(int i = 1; i < arr.length; i++) {
			//定义待插入的数
			insertVal = arr[i];
			insertIndex = i - 1; // 即arr[1]的前面这个数的下标
	
			// 给insertVal 找到插入的位置
			// 说明
			// 1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
			// 2. insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
			// 3. 就需要将 arr[insertIndex] 后移
			while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
				arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
				insertIndex--;
			}
			// 当退出while循环时，说明插入的位置找到, insertIndex + 1
			//这里我们判断是否需要赋值 (也可以不用判断，对效率并没有很大提升)
			if(insertIndex + 1 != i) { //当前位置就是需要插入的位置，则无需赋值
				arr[insertIndex + 1] = insertVal;
			}
		}
		
```

插入排序在数组存储的情况下会比选择排序慢，因为插入排序在数组插入过程中的插入效率很慢，因为会后移数据，如果是链表方式则会节省很多时间。

### 希尔排序

![DATA_STRUCTURES_ALGORITHMS55.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS55.png?raw=true)

希尔排序也是插入排序的一种，是对上面这种最坏的插入的一种优化。

希尔排序是把记录按下标的一定增量分组，对每组 排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至 1 时，整个文件恰被分成一组，算法便终止

![DATA_STRUCTURES_ALGORITHMS56.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS56.png?raw=true)

交换法效率比较低；

推导： (交换法)--分组内需要做的操作

```java
 // 这里需要实现的是： 将大数组按照指定规则分割成小数组，并让小数组中的数据有序。 但是又不能真的分割数组
// 
  int[] arr = { 8, 9, 1, 7, 2, 3, 5, 4, 6, 0 }; 
         // 从小到大排序
		// 希尔排序的第1轮排序
		// 因为第1轮排序，是将10个数据分成了 5组


//        int temp = 0;
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < arr.length; j += 5) {
//                // 如果当前元素大于加上步长后的那个元素，说明交换
//                if (arr[j] > arr[j + 5]) { //这样会导致j+5 越界
//                    temp = arr[j];
//                    arr[j] = arr[j + 5];
//                    arr[j + 5] = temp;
//                }
//
//            }
//        }

    // 这段代码的实现思路：类似于插入排序的做法，后面的数组都是无序的，需要与前面进行比较
		for (int i = 5; i < arr.length; i++) {
			// 遍历各组中所有的元素(共5组，每组有2个元素), 步长5
			for (int j = i - 5; j >= 0; j -= 5) {
				// 如果当前元素大于加上步长后的那个元素，说明交换
				if (arr[j] > arr[j + 5]) {
					temp = arr[j];
					arr[j] = arr[j + 5];
					arr[j + 5] = temp;
				}
			}
		}
		
		System.out.println("希尔排序1轮后=" + Arrays.toString(arr));//	
		
		// 希尔排序的第2轮排序
		// 因为第2轮排序，是将10个数据分成了 5/2 = 2 组。 每组5个元素

// i-2 步长是2 ，是指每组中的元素，没有明显分组。分组操作可以省略
		for (int i = 2; i < arr.length; i++) {
			// 遍历各组中所有的元素(共5组，每组有2个元素), 步长5
			for (int j = i - 2; j >= 0; j -= 2) {
				// 如果当前元素大于加上步长后的那个元素，说明交换
				if (arr[j] > arr[j + 2]) {
					temp = arr[j];
					arr[j] = arr[j + 2];
					arr[j + 2] = temp;
				}
			}
		}

		System.out.println("希尔排序2轮后=" + Arrays.toString(arr));//

		// 希尔排序的第3轮排序
		// 因为第3轮排序，是将10个数据分成了 2/2 = 1组
		for (int i = 1; i < arr.length; i++) {
			// 遍历各组中所有的元素(共5组，每组有2个元素), 步长5
			for (int j = i - 1; j >= 0; j -= 1) {
				// 如果当前元素大于加上步长后的那个元素，说明交换
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
				arr[j + 1] = temp;
				}
			}
		}

		System.out.println("希尔排序3轮后=" + Arrays.toString(arr));//
```

希尔排序 (交换法)： 因为做了很多交换操作，速度很慢。是为了方便理解位移法。

```java
	// 使用逐步推导的方式来编写希尔排序
	// 希尔排序时， 对有序序列在插入时采用交换法, 
	// 思路(算法) ===> 代码
	public static void shellSort(int[] arr) {
		int temp = 0;
		int count = 0;
		// 根据前面的逐步分析，使用循环处理
		for (int gap = arr.length / 2; gap > 0; gap /= 2) { // 这个是分组操作
			for (int i = gap; i < arr.length; i++) {
				// 遍历各组中所有的元素(共gap组，每组有个元素), 步长gap
				for (int j = i - gap; j >= 0; j -= gap) {
					// 如果当前元素大于加上步长后的那个元素，说明交换
					if (arr[j] > arr[j + gap]) {
						temp = arr[j];
						arr[j] = arr[j + gap];
						arr[j + gap] = temp;
					}
				}
			}
			//System.out.println("希尔排序第" + (++count) + "轮 =" + Arrays.toString(arr));
		}
    }
```

希尔排序 (移动法) 是对交换法的优化

```java
//对交换式的希尔排序进行优化->移位法
	public static void shellSort2(int[] arr) {
		
		// 增量gap, 并逐步的缩小增量
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {
			// 从第gap个元素，逐个对其所在的组进行直接插入排序
			for (int i = gap; i < arr.length; i++) { // 进行直接插入排序
				int j = i;
				int temp = arr[j];
				if (arr[j] < arr[j - gap]) {
					while (j - gap >= 0 && temp < arr[j - gap]) {
						//移动
						arr[j] = arr[j-gap];
						j -= gap;
					}
					//当退出while后，就给temp找到插入的位置
					arr[j] = temp;
				}

			}
		}
	}
```



### 快速排序

快速排序是对冒泡的改进。

```
基本思想是：
通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列
```

![DATA_STRUCTURES_ALGORITHMS57.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS57.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS58.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS58.png?raw=true)

```java
public static void quickSort(int[] arr,int left, int right) {
		int l = left; //左下标
		int r = right; //右下标
		//pivot 中轴值
		int pivot = arr[(left + right) / 2];
		int temp = 0; //临时变量，作为交换时使用
		//while循环的目的是让比pivot 值小放到左边
		//比pivot 值大放到右边
		while( l < r) { 
			//在pivot的左边一直找,找到大于等于pivot值,才退出
			while( arr[l] < pivot) {
				l += 1;
			}
			//在pivot的右边一直找,找到小于等于pivot值,才退出
			while(arr[r] > pivot) {
				r -= 1;
			}
			//如果l >= r说明pivot 的左右两的值，已经按照左边全部是
			//小于等于pivot值，右边全部是大于等于pivot值
			if( l >= r) {
				break;
			}
			
			//交换
			temp = arr[l];
			arr[l] = arr[r];
			arr[r] = temp;
			
			//如果交换完后，发现这个arr[l] == pivot值 相等 r--， 前移
 // 如果不做这个操作，在下一次循环while( arr[l] < pivot)不会执行，如果arr(r) 还是这个值，那么程序就卡死了
			if(arr[l] == pivot) {
				r -= 1;
			}
			//如果交换完后，发现这个arr[r] == pivot值 相等 l++， 后移
			if(arr[r] == pivot) {
				l += 1;
			}
		} //while循环完后完成了第一次的分割。
		
		// 如果 l == r, 必须l++, r--, 否则为出现栈溢出
   // 如果没有下面这段代码，存在，当r=l=0时，下面right > l。quickSort(arr, 0, 8); 又是一个同样的循环。死循环
		if (l == r) {  
			l += 1;
			r -= 1;
		}
		//向左递归
		if(left < r) {
			quickSort(arr, left, r);
		}
		//向右递归
		if(right > l) {
			quickSort(arr, l, right);
		}
		
		
	}
```



### 归并排序

归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，该算法采用经典的分治（divide-and-conquer）
策略（分治法将问题分(divide)成一些小的问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修
补"在一起，即分而治之)。

![DATA_STRUCTURES_ALGORITHMS59.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS59.png?raw=true)

![DATA_STRUCTURES_ALGORITHMS60.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS60.png?raw=true)

先写合并的方法

```java

	//合并的方法
	/**
	 * 
	 * @param arr 排序的原始数组
	 * @param left 左边有序序列的初始索引
	 * @param mid 中间索引，右边有序索引的前一个
	 * @param right 右边有序的索引的开始
	 * @param temp 做中转的数组
	 */
	public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
		
		int i = left; // 初始化i, 左边有序序列的初始索引
		int j = mid + 1; //初始化j, 右边有序序列的初始索引
		int t = 0; // 指向temp数组的当前索引
		
		//(一)
		//先把左右两边(有序)的数据按照规则填充到temp数组
		//直到左右两边的有序序列，有一边处理完毕为止
		while (i <= mid && j <= right) {//继续
			//如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素
			//即将左边的当前元素，填充到 temp数组 
			//然后 t++, i++
			if(arr[i] <= arr[j]) {
				temp[t] = arr[i];
				t += 1;
				i += 1;
			} else { //反之,将右边有序序列的当前元素，填充到temp数组
				temp[t] = arr[j];
				t += 1;
				j += 1;
			}
		}
		
		//(二)
		//把有剩余数据的一边的数据依次全部填充到temp
		while( i <= mid) { //左边的有序序列还有剩余的元素，就全部填充到temp
			temp[t] = arr[i];
			t += 1;
			i += 1;	
		}
		
		while( j <= right) { //右边的有序序列还有剩余的元素，就全部填充到temp
			temp[t] = arr[j];
			t += 1;
			j += 1;	
		}
		
		
		//(三)
		//将temp数组的元素拷贝到arr
		//注意，并不是每次都拷贝所有
		t = 0;
		int tempLeft = left; // 
		//第一次合并 tempLeft = 0 , right = 1 //  tempLeft = 2  right = 3 // tL=0 ri=3
		//最后一次 tempLeft = 0  right = 7
		while(tempLeft <= right) { 
			arr[tempLeft] = temp[t];
			t += 1;
			tempLeft += 1;
		}
		
	}
```

分+合方法

```java
//分+合方法 
   //left ,right 分别为起始终止的下标
	public static void mergeSort(int[] arr, int left, int right, int[] temp) {
		if(left < right) {
			int mid = (left + right) / 2; //中间索引
			//向左递归进行分解
			mergeSort(arr, left, mid, temp);
			//向右递归进行分解
			mergeSort(arr, mid + 1, right, temp);
			//合并
			merge(arr, left, mid, right, temp);
			//按照栈的机制最先合并的是最顶层的栈
		}
	}
```

应用：

```java
	int temp[] = new int[arr.length]; //归并排序需要一个额外空间
 	mergeSort(arr, 0, arr.length - 1, temp);
```

![DATA_STRUCTURES_ALGORITHMS61.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS61.png?raw=true)

### 基数排序

基本思想;将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。
这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。

![DATA_STRUCTURES_ALGORITHMS62.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS62.png?raw=true)

推导代码：

```java
	int arr[] = { 53, 3, 542, 748, 14, 214};
		//定义一个二维数组，表示10个桶, 每个桶就是一个一维数组
		//说明
		//1. 二维数组包含10个一维数组
		//2. 为了防止在放入数的时候，数据溢出，则每个一维数组(桶)，大小定为arr.length
		//3. 名明确，基数排序是使用空间换时间的经典算法
		int[][] bucket = new int[10][arr.length];
		
		//为了记录每个桶中，实际存放了多少个数据,我们定义一个一维数组来记录各个桶的每次放入的数据个数
		//可以这里理解
		//比如：bucketElementCounts[0] , 记录的就是  bucket[0] 桶的放入数据个数
		int[] bucketElementCounts = new int[10];


		//第1轮(针对每个元素的个位进行排序处理)
		for(int j = 0; j < arr.length; j++) {
			//取出每个元素的个位的值
			int digitOfElement = arr[j] / 1 % 10;
			//放入到对应的桶中 
            // 举例 ：digitOfElement=2；  bucket[digitOfElement] 就是放入到第二个大桶中。
            //bucketElementCounts[digitOfElement] 就是取出对应大桶存放个数的值
			bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
			bucketElementCounts[digitOfElement]++;
		}
		//按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
		int index = 0;
		//遍历每一桶，并将桶中是数据，放入到原数组
		for(int k = 0; k < bucketElementCounts.length; k++) {
			//如果桶中，有数据，我们才放入到原数组
			if(bucketElementCounts[k] != 0) {
				//循环该桶即第k个桶(即第k个一维数组), 放入
				for(int l = 0; l < bucketElementCounts[k]; l++) {
					//取出元素放入到arr
					arr[index++] = bucket[k][l];
				}
			}
			//第l轮处理后，需要将每个 bucketElementCounts[k] = 0 ！！！！
            //清空存储个数的数组
			bucketElementCounts[k] = 0;
			
		}
		System.out.println("第1轮，对个位的排序处理 arr =" + Arrays.toString(arr));
		
		
		//==========================================
		
		//第2轮(针对每个元素的十位进行排序处理)
		for (int j = 0; j < arr.length; j++) {
			// 取出每个元素的十位的值 ，
			int digitOfElement = arr[j] / 10  % 10; //748 / 10 => 74 % 10 => 4
			// 放入到对应的桶中
			bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
			bucketElementCounts[digitOfElement]++;
		}
		// 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
		index = 0;
		// 遍历每一桶，并将桶中是数据，放入到原数组
		for (int k = 0; k < bucketElementCounts.length; k++) {
			// 如果桶中，有数据，我们才放入到原数组
			if (bucketElementCounts[k] != 0) {
				// 循环该桶即第k个桶(即第k个一维数组), 放入
				for (int l = 0; l < bucketElementCounts[k]; l++) {
					// 取出元素放入到arr
					arr[index++] = bucket[k][l];
				}
			}
			//第2轮处理后，需要将每个 bucketElementCounts[k] = 0 ！！！！
			bucketElementCounts[k] = 0;
		}
		System.out.println("第2轮，对个位的排序处理 arr =" + Arrays.toString(arr));
		
		
		//第3轮(针对每个元素的百位进行排序处理)
		for (int j = 0; j < arr.length; j++) {
			// 取出每个元素的百位的值
			int digitOfElement = arr[j] / 100 % 10; // 748 / 100 => 7 % 10 = 7
			// 放入到对应的桶中
			bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
			bucketElementCounts[digitOfElement]++;
		}
		// 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
		index = 0;
		// 遍历每一桶，并将桶中是数据，放入到原数组
		for (int k = 0; k < bucketElementCounts.length; k++) {
			// 如果桶中，有数据，我们才放入到原数组
			if (bucketElementCounts[k] != 0) {
				// 循环该桶即第k个桶(即第k个一维数组), 放入
				for (int l = 0; l < bucketElementCounts[k]; l++) {
					// 取出元素放入到arr
					arr[index++] = bucket[k][l];
				}
			}
			//第3轮处理后，需要将每个 bucketElementCounts[k] = 0 ！！！！
			bucketElementCounts[k] = 0;
		}
		System.out.println("第3轮，对个位的排序处理 arr =" + Arrays.toString(arr)); 
```

通过推导我们得出规律： 经过几轮排序是跟数组中的最大位数相关。

基数排序：

```java
//基数排序方法
	public static void radixSort(int[] arr) {
		
		//根据前面的推导过程，我们可以得到最终的基数排序代码
		
		//1. 得到数组中最大的数的位数
		int max = arr[0]; //假设第一数就是最大数
		for(int i = 1; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		//这里巧妙的通过字符串求得到最大数的位数 
		int maxLength = (max + "").length();
		
		
		//定义一个二维数组，表示10个桶, 每个桶就是一个一维数组
		//说明
		//1. 二维数组包含10个一维数组
		//2. 为了防止在放入数的时候，数据溢出，则每个一维数组(桶)，大小定为arr.length
		//3. 名明确，基数排序是使用空间换时间的经典算法
		int[][] bucket = new int[10][arr.length];
		
		//为了记录每个桶中，实际存放了多少个数据,我们定义一个一维数组来记录各个桶的每次放入的数据个数
		//可以这里理解
		//比如：bucketElementCounts[0] , 记录的就是  bucket[0] 桶的放入数据个数
		int[] bucketElementCounts = new int[10];
		
		
		//这里我们使用循环将代码处理
		for(int i = 0 , n = 1; i < maxLength; i++, n *= 10) {
			//(针对每个元素的对应位进行排序处理)， 第一次是个位，第二次是十位，第三次是百位..
			for(int j = 0; j < arr.length; j++) {
				//取出每个元素的对应位的值
				int digitOfElement = arr[j] / n % 10;
                 // 这里的n 可以使用Math.pow(10, i) 通过求次方来得到
				//放入到对应的桶中
				bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
				bucketElementCounts[digitOfElement]++;
			}
			//按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
			int index = 0;
			//遍历每一桶，并将桶中是数据，放入到原数组
			for(int k = 0; k < bucketElementCounts.length; k++) {
				//如果桶中，有数据，我们才放入到原数组
				if(bucketElementCounts[k] != 0) {
					//循环该桶即第k个桶(即第k个一维数组), 放入
					for(int l = 0; l < bucketElementCounts[k]; l++) {
						//取出元素放入到arr
						arr[index++] = bucket[k][l];
					}
				}
				//第i+1轮处理后，需要将每个 bucketElementCounts[k] = 0 ！！！！
				bucketElementCounts[k] = 0;
				
			}
			//System.out.println("第"+(i+1)+"轮，对个位的排序处理 arr =" + Arrays.toString(arr));
			
		}
		
```

基数排序虽然速度很快，但是当我们的数据量大了之后，桶额外占用的空间就很大，当数级达到百万千万级就可能会出现内存溢出。

还有基数排序是稳定性排序。他总是按照顺序往里放。不会改变相等数的顺序。

稳定性：

```
在原序列中，r[i]=r[j]，且 r[i]在 r[j]之前，而在排序后的序列中，r[i]仍在 r[j]之前，则称这种排序算法是稳定的；否则称为不稳定的
```

有负数的数组，我们不用基数排序来进行排序, 如果要支持负数，参考: https://code.i-harness.com/zh-CN/q/e98fa9



## 查找算法

###  顺序(线性)查找

有一个数列： {1,8, 10, 89, 1000, 1234} ，判断数列中是否包含此名称【顺序查找】

**线性查找不在乎数据是否有序**。

```java
/**
	 * 这里我们实现的线性查找是找到一个满足条件的值，就返回
	 * @param arr
	 * @param value
	 * @return
	 */
	public static int seqSearch(int[] arr, int value) {
		// 线性查找是逐一比对，发现有相同值，就返回下标
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] == value) {
				return i;
			}
		}
		return -1;
	}
```



###  二分查找/折半查找

相比于线性查找，**二分查找要求查找的数据源必须是有序的**。所以在绝大部分使用二分查找的情况下，我们都要先对数据进行排序。

二分查找的思路分析：

![DATA_STRUCTURES_ALGORITHMS64.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS64.png?raw=true)

```java
//注意：使用二分查找的前提是 该数组是有序的.
public class BinarySearch {
	public static void main(String[] args) {
	int arr[] = { 1, 8, 10, 89,1000,1000, 1234 };
	int resIndex = binarySearch(arr, 0, arr.length - 1, 1000);
    System.out.println("resIndex=" + resIndex);
	}

	// 二分查找算法
	/**
	 * 
	 * @param arr
	 *            数组
	 * @param left
	 *            左边的索引
	 * @param right
	 *            右边的索引
	 * @param findVal
	 *            要查找的值
	 * @return 如果找到就返回下标，如果没有找到，就返回 -1
	 */
	public static int binarySearch(int[] arr, int left, int right, int findVal) {
		// 当 left > right 时，说明递归整个数组，但是没有找到
		if (left > right) {
			return -1;
		}
		int mid = (left + right) / 2;
		int midVal = arr[mid];

		if (findVal > midVal) { // 向 右递归
			return binarySearch(arr, mid + 1, right, findVal);
		} else if (findVal < midVal) { // 向左递归
			return binarySearch(arr, left, mid - 1, findVal);
		} else {
			
			return mid;
		}

	}
	

}

```

对二分查找进行升级：

```java
//完成一个课后思考题:
	/*
	 * 课后思考题： {1,8, 10, 89, 1000, 1000，1234} 当一个有序数组中，
	 * 有多个相同的数值时，如何将所有的数值都查找到，比如这里的 1000
	 * 
	 * 思路分析
	 * 1. 在找到mid 索引值，不要马上返回
	 * 2. 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
	 * 3. 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
	 * 4. 将Arraylist返回
	 */

	public static List<Integer> binarySearch2(int[] arr, int left, int right, int findVal) {
		System.out.println("hello~");
		// 当 left > right 时，说明递归整个数组，但是没有找到
		if (left > right) {
			return new ArrayList<Integer>();
		}
		int mid = (left + right) / 2;
		int midVal = arr[mid];

		if (findVal > midVal) { // 向 右递归
			return binarySearch2(arr, mid + 1, right, findVal);
		} else if (findVal < midVal) { // 向左递归
			return binarySearch2(arr, left, mid - 1, findVal);
		} else {
//			 * 思路分析
//			 * 1. 在找到mid 索引值，不要马上返回
//			 * 2. 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
//			 * 3. 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
//			 * 4. 将Arraylist返回
			
			List<Integer> resIndexlist = new ArrayList<Integer>();
			//向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
			int temp = mid - 1;
			while(true) {
         // 这里arr[temp] != findVal 就退出是因为二分查找有个前提，数据源是有序的。相同的数据必然是相邻的
				if (temp < 0 || arr[temp] != findVal) {//退出
					break;
				}
				//否则，就temp 放入到 resIndexlist
				resIndexlist.add(temp);
				temp -= 1; //temp左移
			}
			resIndexlist.add(mid);  //
			
			//向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
			temp = mid + 1;
			while(true) {
				if (temp > arr.length - 1 || arr[temp] != findVal) {//退出
					break;
				}
				//否则，就temp 放入到 resIndexlist
				resIndexlist.add(temp);
				temp += 1; //temp右移
			}
			
			return resIndexlist;
		}

	}
```



###  插值查找

二分查找在绝大部分情况速度还是很快的，但是在一些特殊情况下如;

int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 , 11, 12, 13,14,15,16,17,18,19,20 };  查找1；

二分就显得有些不足，能不能每次分割不是对半。 能否对mid的值进行**自适应**。

差值查找就是对二分的优化。其原理类似于二分查找

![DATA_STRUCTURES_ALGORITHMS65.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS65.png?raw=true)

通过插值算法自适应mid，我们可以很快的定位.

![DATA_STRUCTURES_ALGORITHMS66.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS66.png?raw=true)

```java
//编写插值查找算法
	//说明：插值查找算法，也要求数组是有序的
	/**
	 * 
	 * @param arr 数组
	 * @param left 左边索引
	 * @param right 右边索引
	 * @param findVal 查找值
	 * @return 如果找到，就返回对应的下标，如果没有找到，返回-1
	 */
	public static int insertValueSearch(int[] arr, int left, int right, int findVal) { 

		System.out.println("插值查找次数~~");
		
		//注意：findVal < arr[0]  和  findVal > arr[arr.length - 1] 必须需要，
        //否则当我们的findVal是个恨到的值得时候，我们通过下面的公式求的的mid的值可能会越界
        // 插值查找也需要数据源是有序的，如果要查找的小于最小值或者大于最大值，那么久没有查找的必要了
		if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
			return -1;
		}

		// 求出mid, 自适应
		int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
		int midVal = arr[mid];
		if (findVal > midVal) { // 说明应该向右边递归
			return insertValueSearch(arr, mid + 1, right, findVal);
		} else if (findVal < midVal) { // 说明向左递归查找
			return insertValueSearch(arr, left, mid - 1, findVal);
		} else {
			return mid;
		}

	}
```

```
插值查找注意事项：
1) 对于数据量较大，关键字分布比较均匀的查找表来说，采用插值查找, 速度较快.
2) 关键字分布不均匀的情况下，该方法不一定比折半查找要好
```



###  斐波那契查找(黄金分割查找)

![DATA_STRUCTURES_ALGORITHMS67.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS67.png?raw=true)

斐波那契查找原理：同二分与插值类似，只是改变求mid的方式

![DATA_STRUCTURES_ALGORITHMS68.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/DATA_STRUCTURES_ALGORITHMS68.png?raw=true)

注意： 我们在找在进行斐波那契查找时候，有时候为了找到黄金分割节点我们会为源数据进行扩容。

```java
	public static int maxSize = 20;
	public static void main(String[] args) {
		int [] arr = {1,8, 10, 89, 1000, 1234};
		
		System.out.println("index=" + fibSearch(arr, 189));// 0
		
	}

	//因为后面我们mid=low+F(k-1)-1，需要使用到斐波那契数列，因此我们需要先获取到一个斐波那契数列
	//非递归方法得到一个斐波那契数列
	public static int[] fib() {
		int[] f = new int[maxSize];
		f[0] = 1;
		f[1] = 1;
		for (int i = 2; i < maxSize; i++) {
			f[i] = f[i - 1] + f[i - 2];
		}
		return f;
	}
	
	//编写斐波那契查找算法
	//使用非递归的方式编写算法
	/**
	 * 
	 * @param a  数组
	 * @param key 我们需要查找的关键码(值)
	 * @return 返回对应的下标，如果没有-1
	 */
	public static int fibSearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		int k = 0; //表示斐波那契分割数值的下标
		int mid = 0; //存放mid值
		int f[] = fib(); //获取到斐波那契数列
		//获取到斐波那契分割数值的下标
		while(high > f[k] - 1) { 
            // 因为我们要通过斐波那契数列来求黄金分割点，所以数据源数组的必须要<=斐波那契数列，
            //只有这样我们才能在源数据中求出黄金分割点
			k++;
		}
		//因为 f[k] 值 可能大于 a 的 长度，因此我们需要使用Arrays类，构造一个新的数组，并指向temp[]
		//不足的部分会使用0填充
		int[] temp = Arrays.copyOf(a, f[k]);
		//实际上需求使用a数组最后的数填充 temp
		//举例:
		//temp = {1,8, 10, 89, 1000, 1234, 0, 0}  => {1,8, 10, 89, 1000, 1234, 1234, 1234,}
		for(int i = high + 1; i < temp.length; i++) {
			temp[i] = a[high];
		}
		
		// 使用while来循环处理，找到我们的数 key
		while (low <= high) { // 只要这个条件满足，就可以找
			mid = low + f[k - 1] - 1;
			if(key < temp[mid]) { //我们应该继续向数组的前面查找(左边)
				high = mid - 1;
				//为甚是 k--
				//说明
				//1. 全部元素 = 前面的元素 + 后边元素
				//2. f[k] = f[k-1] + f[k-2]
				//因为 前面有 f[k-1]个元素,所以可以继续拆分 f[k-1] = f[k-2] + f[k-3]
				//即 在 f[k-1] 的前面继续查找 k--
				//即下次循环 mid = f[k-1-1]-1
				k--;
			} else if ( key > temp[mid]) { // 我们应该继续向数组的后面查找(右边)
				low = mid + 1;
				//为什么是k -=2
				//说明
				//1. 全部元素 = 前面的元素 + 后边元素
				//2. f[k] = f[k-1] + f[k-2]
				//3. 因为后面我们有f[k-2] 所以可以继续拆分 f[k-1] = f[k-3] + f[k-4]
				//4. 即在f[k-2] 的前面进行查找 k -=2
				//5. 即下次循环 mid = f[k - 1 - 2] - 1
				k -= 2;
			} else { //找到
				//需要确定，返回的是哪个下标
				if(mid <= high) {
					return mid;
				} else {
					return high;
				}
			}
		}
		return -1;
	}
```



