# Navicat以csv文件导入导出数据库表



# 导入的时候没有自动生成的id 解决方法

```
1: 先将制定字段导入

2 在id中生成uuid

UPDATE test SET ID= UUID();
注意，生成id时，不要直接将uuid()产生的序列中的''-"通过Replace方法去掉，不然生成的id的将全部相同。 

 3 将生成的id中的"-"替换掉

UPDATE test SET  ID = (SELECT REPLACE(ID,'-',''));
    5、生成时间

UPDATE test SET UPDATE_TIME = CURRENT_TIMESTAMP();
    6、生成标准化的数据库

首先，将已经导入到test表中的数据通过sql转储，进行保存。然后将test表删除，然后建立标准的数据库test表（比如主键为id,创建时间为非空等），最后将sql转储的数据导入的数据库表中，完成数据的导入。
```



# MySQL导入含有中文字段(内容)CSV文件乱码解决方法

Encoding特别重要，要选择10008（MAC-Simplified Chinese GB 2312）不能使用默认的utf-8，因为含有中文的CSV文件一般都是采用10008这种编码方式而不是utf8。



![img](https://img-blog.csdn.net/20160504112929906?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)



