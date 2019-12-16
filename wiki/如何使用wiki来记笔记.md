这里开始做笔记测试

  如何使用gitHub的wiki来记笔记
  
  A： 注册gitHub， 下载git客户端
  （如果是第一次安装git ---这里我使用的是Windows平台的git brash）
    a. 配置git brash: 
	
	编辑C:\Users\Administrator 目录下的（.minttyrc）文件，如果没有自己创建（这样对git brash 的设置才能永久保存）
     
	 配置文件内容如下：
	 Font=Consolas
	 FontHeight=14
	 ForegroundColour=131,148,150
     BackgroundColour=0,43,54
     CursorColour=220,50,47
	 Black=7,54,66
     BoldBlack=0,43,54
     Red=220,50,47
	 BoldRed=203,75,22
	 Green=133,153,0
	 BoldGreen=88,110,117
	 Yellow=181,137,0
	 BoldYellow=101,123,131
	 Blue=38,139,210
	 BoldBlue=131,148,150
	 Magenta=211,54,130
	 BoldMagenta=108,113,196
	 Cyan=42,161,152
	 BoldCyan=147,161,161
	 White=238,232,213
	 BoldWhite=253,246,227
	 BoldAsFont=-1
	 FontSmoothing=full
	 FontWeight=700
	 FontIsBold=yes
	 Locale=C
	 Charset=UTF-8

其他的配置可以通过  选项--option中进行修改

  b: 配置git（让他能与gitHub上的仓库连接） 
    
	 先在本地磁盘中建立一个仓库repository(为避免问题，目录名中不要包含英文)
	 
	 通过cd命令进入仓库目录，然后通过git init把这个文件夹变成Git可管理的仓库 (成功后仓库目录下会有.git 这个隐藏文件夹)
	 
	 配置个人信息
		 git config --global user.name "runoob"
         git config --global user.email test@runoob.com
	 
	 配置 SSH KEY：
	     先看C:\Users\Administrator\.ssh目录下存不存在id_rsa和id_rsa.pub这两个文件
		 如果没有就使用ssh-keygen -t rsa -C "youremail@example.com"命令创建
		 登录Github,点进Settings——》的SSH and GPG KEYS———》New SSH key，
		 然后Title里面随便填，再把刚才id_rsa.pub里面的内容复制到Title下面的Key里。完成了SSH Key的加密。
	 
	 让Github上创建好Git仓库与和本地仓库进行关联
		 git remote add origin https://github.com/zhaodahan/zhao_Note.git
		 
	 后面的就是git 提交这些基本的操作了
	 
         git add .把项目添加到仓库
		 
		 git commit -m "注释内容"把项目提交到仓库
		 
		 git push origin master 把本地仓库的项目推送到远程仓库
		 
		 git pull origin master 从远程获取最新版本并merge到本地
		 
		 git fetch origin master 是从远程获取最新版本到本地，不会自动merge
		 
  
  C : 将gitHub上的wiki clone到本地，本地编辑完之后更新到gitHub repository；
       
         git clone https://github.com/zhaodahan/zhao_Note.wiki.git
          
		 本地操作完之后 push到远程仓库。 (这里需要注意的是当我们更新本地wiki的时候，需要cd 到本地仓库对应的文件夹下去)
  
  
  D: 如果在远程仓库建立的新的文件
			暂时没有想到更好的方法。
		    这里能实现的方法是线解除现有仓库与远程仓库的关联。
			然后在wiki中与wiki.git 进行关联
			最后再更新。
			
			git remote rm origin
			
			git remote add origin https://github.com/zhaodahan/zhao_Note.wiki.git
			
			git pull origin master

			(这样比较麻烦，每次如果要更新zhao_Note.git 下的，就要重新绑定关联)
  
  E: 如何在wiki 笔记中插入图片
		wiki笔记的md文件中存在可以引用图片的规则
        1： md文件中引用的图片不是图片文件而是图片链接
        2： 链接格式是： ！[图片无法显示时文字描述]（引用的图片链接）
		eg:![同步与异步的理解图](https://github.com/zhaodahan/zhao_Note/blob/master/img-storage/%E5%90%8C%E6%AD%A5%E5%BC%82%E6%AD%A5.JPG)		
        
		3：需要注意的这里的图片引用的是相对路径链接--在wiki中需要在wiki.git 下建立存储图片的文件夹
		4：具体的例子：https://blog.csdn.net/ge23456789/article/details/77338242
		5：使用链接需要注意一点：图片链接不能放在详情区域，（需要顶行放置）
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
