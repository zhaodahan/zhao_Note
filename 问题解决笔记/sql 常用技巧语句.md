#                                SQL常用技巧语句汇总



## 查询出表中某个字段重复的语句

**以查询出 a 表中 method_name字段重复为例子**

```sql
SELECT
	*
FROM
	a 
WHERE
	method_name IN (
		SELECT
			method_name
		FROM
			a
		GROUP BY
			method_name
		HAVING
			COUNT(*) > 1
	)
```

## 删除重复的记录 并且只保留 id 最小的一条

```sql
--- 删除重复的记录 并且只保留 id 最小的一条
DELETE
FROM
	a
WHERE
	standard_no IN (
		SELECT
			standard_no
		FROM
			a
		GROUP BY
			standard_no
		HAVING
			COUNT(*) > 1
	)
AND id NOT IN (
	SELECT
		min(id)
	FROM
		a
	GROUP BY
		standard_no
	HAVING
		COUNT(*) > 1
);
```

## 三列中的结果 合并到一起

```sql
SELECT SYSUSER.ID,
      SYSUSER.USERID,
      SYSUSER.USERNAME,
      SYSUSER.GROUPID,
      SYSUSER.SYSID,
      decode(SYSUSER.GROUPID,
      '1',(SELECT mc FROM USERJD WHERE ID = sysuser.SYSID),
      '2',(SELECT mc FROM USERJD WHERE ID = sysuser.SYSID),
      '3',(SELECT mc FROM USERYY WHERE ID = sysuser.SYSID),
      '4',(SELECT mc FROM USERGYS WHERE ID = sysuser.SYSID)
      )  as sysms
FROM SYSUSER

===============================================================

SELECT SYSUSER.ID,
      SYSUSER.USERID,
      SYSUSER.USERNAME,
      SYSUSER.GROUPID,
      SYSUSER.SYSID,
      nvl(USERJD.MC, nvl(USERYY.MC,USERGYS.MC)) as sysmc

FROM SYSUSER
LEFT JOIN USERJD
  on SYSUSER.SYSID = USERJD.ID
LEFT JOIN USERYY
  on SYSUSER.SYSID = USERYY.ID
LEFT JOIN USERGYS
  ON SYSUSER.SYSID = USERGYS.ID
  
  
===================================================
nvl()函数简介：
nvl()是oracle中的函数，从两个表达式返回一个非 null 值。

语法
NVL(eExpression1, eExpression2)

如果 eExpression1 的计算结果为 null 值，则 NVL( ) 返回 eExpression2。如果 eExpression1 的计算结果不是 null 值，则返回 eExpression1。eExpression1 和 eExpression2 可以是任意一种数据类型。如果 eExpression1 与 eExpression2 的结果皆为 null 值，则 NVL( ) 返回 .NULL.。

===================================================
```

```sql
===================================================
decode()函数简介：
主要作用：将查询结果翻译成其他值（即以其他形式表现出来，举例说明）；
使用方法：
SELECT
decode（columnname，值1,翻译值1,值2,翻译值2,...值n,翻译值n,缺省值）
FROM
	talbename
WHERE
	…

其中columnname为要选择要翻译的的table中所定义的column
·含义解释：
decode(条件,值1,翻译值1,值2,翻译值2,...值n,翻译值n,缺省值)的理解如下：
if （条件==值1）
 then　　　　
return(翻译值1)
elsif （条件==值2）
then　　　　
return(翻译值2)　　　　
......
elsif （条件==值n）
 then　　　　
return(翻译值n)
else　　　　
return(缺省值)
end if
注：其中缺省值可以是你要选择的column name 本身，也可以是你想定义的其他值，比如Other等；

举例说明：
现定义一table名为output，其中定义两个column分别为monthid（var型）和sale（number型），若sale值=1000时翻译为D，=2000时翻译为C，=3000时翻译为B，=4000时翻译为A，如是其他值则翻译为Other；

SQL如下：
Select monthid , decode (sale,1000,'D',2000,'C',3000,'B',4000,'A',’Other’) sale from output

特殊情况：若只与一个值进行比较
Select monthid ,decode（sale， NULL，‘---’，sale） sale from output

另：decode中可使用其他函数，如nvl函数或sign（）函数等 
NVL(EXPR1,EXPR2)
若EXPR1是NULL,则返回EXPR2,否则返回EXPR1.

SELECT NAME,NVL(TO_CHAR(COMM),'NOT APPLICATION') FROM TABLE1;

如果用到decode函数中就是
select monthid,decode(nvl(sale,6000),6000,'NG','OK') from output
===================================================
```



# 数据库量表来求差集



## 什么是差集

一般地，设A，B是两个 集合，由所有属于A且不属于B的元素组成的集合，叫做集合A减 集合B( 或 集合A与集合B之差)

A={1,2,3,4}

B={1,4,5}

A-B={2,3}

## 实际的应用场景

需求： 有两张数据库表 A（会员表）， B (需要加权限的表) 。现在需要去除掉A表中和B表某一字段相同的一些记录。

 方案一：not in的查询

```sql
SELECT
	id
FROM
	blog 
WHERE
	id NOT IN (SELECT id FROM usertable)
// 这里usertable是B表，blog表示A表
//缺点：not in的查询效率是极度低下的
```



方案二：左链接查询

```sql
SELECT
	id
FROM
	blog 
LEFT JOIN (SELECT id AS i FROM usertable) AS t1 ON usertable.id = t1.i
WHERE
	t1.i IS NULL
//// 这里usertable是B表，blog表示A表
//这里blog的id，也就是t1中的id，还必须换成i，或者其它变量名，不然连接的时候会出现混乱
对上面sql语句解释 
A  1 2 3 4
B  1     4 5
A left join B
  1 2 3 4|
  1     4| 5
然后取B中在左联合中为空那就是2 3 
```



实际例子：

```mysql
SELECT
		*
		FROM
		(
		SELECT
		*
		FROM
		member
		LEFT JOIN (
		SELECT
		USER_MOBILE AS i
		FROM
		databoard_user
		) AS t1 ON member.MOBILE = t1.i
		WHERE
		t1.i IS NULL
		) AS a
		<dynamic prepend="where">
			<isNotEmpty prepend="and" property="mobile">
				a.MOBILE=#mobile#
			</isNotEmpty>
		</dynamic>
```



# 数据库使用UNION 来进行复杂的排序



需求： 数据需要排序，先根据sort 字段进行排序， 如果这个字段不为null ,就根据这个字段排序，如果sort字段相同，就根据updateTime字段进行排序，如果这个字段是空 ， 就根据typename 字段排序。 数字> 字母 >汉字 

思路解析： 先拆分需求， 将数据分成两部分排序，然后union 成一个整体。  第一部分为sort 字段不为空的。 第二部分是sort 字段为null的。   数字> 字母 >汉字  这个排序规则 sql 天然满足。

```mysql
SELECT * FROM (SELECT * FROM car_type ct WHERE ct.`sort` IS NOT NULL AND ct.sort!=''
AND (ct.`sort` REGEXP '[^0-9.]')=0
 ORDER BY  CONVERT(ct.`sort` ,SIGNED) ASC) a
 UNION ALL
SELECT * FROM (SELECT * FROM car_type ct WHERE ct.`sort` IS NOT NULL AND ct.sort!=''
AND (ct.`sort` REGEXP '[^0-9.]')=1
 ORDER BY  CONVERT(ct.`sort` USING gbk) ASC) b
 
UNION ALL
SELECT * FROM (
SELECT * FROM car_type ct WHERE (ct.`sort` IS  NULL OR ct.sort='')

 ORDER BY  CONVERT(ct.car_type_name USING gbk) ASC
) c 
```



```mysql
select
		cb.id as carBrandId,
		cb.car_brand_name as carBrandName,
		cs.id as carSeriesId,
		cs.car_series_name as carSeriesName,
		ct.id as id,
		ct.car_type_name as
		carTypeName,
		ct.car_code as carCode,
		ct.sort as sort,
		ct.is_enable as
		isEnable,
		ct.add_time as addTime,
		ct.update_time as updateTime,
		ct.create_user_id as
		createUserId,
		ct.create_user_name as createUserName,
		d1.dict_value as
		seatNumber,
		cbd.displacement displacementKey,
		cbd.guide_price as
		guidePrice
		from 
		(SELECT * FROM (SELECT * FROM (select * from car_type group by car_type_name) ct  WHERE ct.`sort` IS NOT NULL AND ct.sort!=''
		AND (ct.`sort` REGEXP '[^0-9.]')=0
		 ORDER BY  CONVERT(ct.`sort` ,SIGNED) ASC) a
 		UNION ALL
		SELECT * FROM (SELECT * FROM (select * from car_type group by car_type_name) ct WHERE ct.`sort` IS NOT NULL AND ct.sort!=''
		AND (ct.`sort` REGEXP '[^0-9.]')=1
 		ORDER BY  CONVERT(ct.`sort` USING gbk) ASC) b
		UNION ALL
		SELECT * FROM (
		SELECT * FROM (select * from car_type group by car_type_name) ct WHERE (ct.`sort` IS  NULL OR ct.sort='')
 		ORDER BY  CONVERT(ct.car_type_name USING gbk) ASC
		) c )  ct
		inner join car_series cs on ct.car_series_id = cs.id
		inner join car_brand cb on cb.id = cs.car_brand_id
		left join car_basic_data cbd on ct.id = cbd.car_type_id
		left join dict d1
		on d1.dict_key = cbd.seat_number and d1.dict_type='seat_number'
		<where>
			<if test="carBrandId !=null and carBrandId !='' and carBrandId!='null'">
				and ct.car_brand_id =#{carBrandId}
			</if>
			<if test="carSeriesId !=null and carSeriesId !='' and carSeriesId!='null'">
				and ct.car_series_id =#{carSeriesId}
			</if>
			<if test="carTypeName !=null and carTypeName !='' and carTypeName!='null'">
				and ct.car_type_name like CONCAT(CONCAT('%',
				#{carTypeName}), '%')
			</if>
			<if test="id !=null and id !='' and id!='null'">
				and ct.id =#{id}
			</if>
			<if test="guidePrice !=null ">
				and cbd.guide_price = #{guidePrice}
			</if>
			<if test="displacementKey !=null and displacementKey !=''">
				and cbd.displacement =#{displacementKey}
			</if>
			<if test="seatNumber !=null and seatNumber !=''">
				and d1.dict_key = #{seatNumber}
			</if>
		</where>
```



简单格式： 就是两部分查询合在一起

```mysql
select * from (SELECT 
		cb.id as carBrandId,
		cb.car_brand_name as carBrandName,
		cs.id as carSeriesId,
		cs.car_series_name as carSeriesName,
		ct.id as id,
		ct.car_type_name as carTypeName,
		ct.car_code as carCode,
		ct.sort as sort,
		ct.is_enable as isEnable, 
		ct.add_time as addTime,
		ct.update_time as updateTime,
		ct.create_user_id as createUserId,
		ct.create_user_name as createUserName,
		d1.dict_value as seatNumber,
		cbd.displacement displacementKey,
		cbd.guide_price as guidePrice
from car_type  ct
		inner join car_series cs on ct.car_series_id = cs.id 
		inner join car_brand cb on cb.id = cs.car_brand_id 
		left join car_basic_data cbd on ct.id = cbd.car_type_id
		left join dict d1 on d1.dict_key = cbd.seat_number and d1.dict_type='seat_number'
WHERE ct.`sort` IS NOT NULL AND ct.sort!=''
AND (ct.`sort` REGEXP '[^0-9.]')=0
group by ct.car_type_name

ORDER BY CAST(ct.sort AS SIGNED),ct.update_time DESC
) as a

UNION

select * from 

(SELECT 
		cb.id as carBrandId,
		cb.car_brand_name as carBrandName,
		cs.id as carSeriesId,
		cs.car_series_name as carSeriesName,
		ct.id as id,
		ct.car_type_name as carTypeName,
		ct.car_code as carCode,
		ct.sort as sort,
		ct.is_enable as isEnable, 
		ct.add_time as addTime,
		ct.update_time as updateTime,
		ct.create_user_id as createUserId,
		ct.create_user_name as createUserName,
		d1.dict_value as seatNumber,
		cbd.displacement displacementKey,
		cbd.guide_price as guidePrice
from car_type  ct
		inner join car_series cs on ct.car_series_id = cs.id 
		inner join car_brand cb on cb.id = cs.car_brand_id 
		left join car_basic_data cbd on ct.id = cbd.car_type_id
		left join dict d1 on d1.dict_key = cbd.seat_number and d1.dict_type='seat_number'
WHERE
ct.`sort` IS  NULL OR ct.sort=''
group by ct.car_type_name
ORDER BY CONVERT(ct.car_type_name USING gbk) ASC) as b

UNION

select * from 
(SELECT 
		cb.id as carBrandId,
		cb.car_brand_name as carBrandName,
		cs.id as carSeriesId,
		cs.car_series_name as carSeriesName,
		ct.id as id,
		ct.car_type_name as carTypeName,
		ct.car_code as carCode,
		ct.sort as sort,
		ct.is_enable as isEnable, 
		ct.add_time as addTime,
		ct.update_time as updateTime,
		ct.create_user_id as createUserId,
		ct.create_user_name as createUserName,
		d1.dict_value as seatNumber,
		cbd.displacement displacementKey,
		cbd.guide_price as guidePrice
from car_type  ct
		inner join car_series cs on ct.car_series_id = cs.id 
		inner join car_brand cb on cb.id = cs.car_brand_id 
		left join car_basic_data cbd on ct.id = cbd.car_type_id
		left join dict d1 on d1.dict_key = cbd.seat_number and d1.dict_type='seat_number'
WHERE
ct.`sort` IS  NULL OR ct.sort=''
group by ct.car_type_name
ORDER BY CONVERT(ct.car_type_name USING gbk) ASC) as c
```



# mybatise 根据时间段来查询

```sql
 <if test="beginDate != null and beginDate != ''">
 AND stock_bill.bill_date <![CDATA[>=]]> #{beginDate}
 </if>
 <if test="endDate != null and endDate != ''">
 AND stock_bill.bill_date <![CDATA[<=]]>#{endDate}
 </if>

```

# mybatise实现批量插入和批量更新

批量前提：需要在数据库链接上加上`allowMultiQueries=true`且他需要加在`？`后面，作为第一个

示例：

```
 url: jdbc:mysql://10.64.14.115:3306/test_cb_broker?allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true
```



```xml
    <insert id="addSellerList" parameterType="java.util.List">
         insert into seller
            (phone, name, dealer_id, dealer_name,is_cancel,role_flag,add_user_id,add_time,update_time,id)
         values
         <foreach collection ="list" item="item" index= "index" separator =",">
             (                
                #{item.phone,jdbcType=VARCHAR},
                #{item.name,jdbcType=INTEGER},
                #{item.dealerId,jdbcType=VARCHAR},
                #{item.dealerName,jdbcType=VARCHAR},
                #{item.isCancel,jdbcType=VARCHAR},
                #{item.roleFlag,jdbcType=VARCHAR},
                #{item.addUserId,jdbcType=VARCHAR},
                now(),now(),
                #{item.id,jdbcType=VARCHAR}
             )
         </foreach >
    </insert>

    <update id="updateSellerList" parameterType="java.util.List">
        <foreach collection="list" item="item" index="index" separator=";">
            update seller
            <set>
                <if test="item.name !=null and item.name !=''">
                    name = #{item.name},
                </if>
                <if test="item.dealerId !=null and item.dealerId !=''">
                    dealer_id = #{item.dealerId},
                </if>
                <if test="item.dealerName !=null and item.dealerName !=''">
                    dealer_name = #{item.dealerName},
                </if>
                <if test="item.isCancel !=null and item.isCancel !=''">
                    is_cancel = #{item.isCancel},
                </if>
                <if test="item.isCloud !=null and item.isCloud !=''">
                    is_cloud = #{item.isCloud},
                </if>
                <if test="item.roleFlag !=null and item.roleFlag !=''">
                    role_flag = #{item.roleFlag},
                </if>
                <if test="item.addUserId !=null and item.addUserId !=''">
                    add_user_id = #{item.addUserId},
                </if>
                update_time = now()
            </set>
            where
            phone = #{item.phone}
            <if test="item.id !=null and item.id !=''">
                    and  id = #{item.id}
            </if>
        </foreach>
    </update>
```

# mybatise实现 复杂的分析统计

分步走,每一次统计一项

```xml
 <select id="getSellerStatisticalList" resultType="com.ec.changan.cb.entity.po.SellerPO"
        parameterType="com.ec.changan.cb.entity.dto.SellerParamDTO">
        SELECT  a1.phone
                ,a1.dealerId
                ,IFNULL(a1.recommendTotalNums, "") AS recommendTotalNums
                ,IFNULL(a1.recommendFinishTotalNums,"") AS recommendFinishTotalNums
                ,IFNULL(a1.inviteTotalNums, "") AS inviteTotalNums
        FROM    (
                    SELECT  b1.brokerId AS brokerId
                            ,b1.dealerId
                            ,b1.phone
                            ,b1.recommendTotalNums AS recommendTotalNums
                            ,b1.recommendFinishTotalNums AS recommendFinishTotalNums
                            ,count(b2.id) AS inviteTotalNums
                    FROM    (
                                SELECT  c1.brokerId
                                        ,c1.dealerId
                                        ,c1.phone
                                        ,c1.recommendTotalNums
                                        ,count(c2.id) AS recommendFinishTotalNums
                                FROM    (
                                            SELECT  d1.brokerId
                                                    ,d1.phone
                                                    ,d1.dealerId
                                                    ,count(d2.id) AS recommendTotalNums
                                            FROM    (
                                                        SELECT  e2.id AS brokerId
                                                                ,e1.phone
                                                                ,e1.dealer_id as dealerId
                                                        FROM    seller AS e1
                                                        LEFT JOIN broker AS e2
                                                        ON e1.phone = e2.phone
                                                        <where>
                                                        <if test="sellerIds !=null">
                                                            and e1.id in
                                                            <foreach collection="sellerIds" item="sellerId"
                                                                index="index" open="(" close=")" separator=",">
                                                                #{sellerId}
                                                            </foreach>
                                                        </if>
                                                        <if test="name !=null and name !=''">
                                                            and e1.name like concat('%',#{name},'%')
                                                        </if>
                                                        <if test="phone !=null and phone !=''">
                                                            and e1.phone like concat('%',#{phone},'%')
                                                        </if>
                                                        <if test="dealerName !=null and dealerName !=''">
                                                            and e1.dealer_name like concat('%',#{dealerName},'%')
                                                        </if>
                                                        <if test="dealerId !=null and dealerId !=''">
                                                            and e1.dealer_id = #{dealerId}
                                                        </if>
                                                        <if test="addUserId !=null and addUserId !=''">
                                                            and e1.add_user_id = #{addUserId}
                                                        </if>
                                                        <if test="isCancel !=null and isCancel !=''">
                                                            and e1.is_cancel = #{isCancel}
                                                        </if>
                                                        <if test="isCloud !=null and isCloud !=''">
                                                            and e1.is_cloud = #{isCloud}
                                                        </if>
                                                        <if test="roleFlag !=null and roleFlag !=''">
                                                            and e1.role_flag = #{roleFlag}
                                                        </if>
                                            
                                                        <if test="createTimeStart != null and createTimeStart !=''">
                                                            and e1.add_time >=#{createTimeStart,jdbcType=TIMESTAMP}
                                                        </if>
                                                        <if test="createTimeEnd != null and createTimeEnd !=''">
                                                            and e1.add_time <![CDATA[ < ]]>#{createTimeEnd,jdbcType=TIMESTAMP}
                                                        </if>
                                                        </where>
                                                        GROUP BY
                                                        e1.phone,
                                                        e1.dealer_id
                                                    )d1
                                            LEFT JOIN broker_recommend d2 
                                            ON  d1.brokerId = d2.broker_id
                                            AND d1.dealerId = d2.create_dealer_id
                                            and d2.create_is_seller='Y'
                                            GROUP BY
                                            d1.brokerId,
                                            d1.dealerId
                                        )c1
                                LEFT JOIN   (
                                                SELECT  d3.broker_id AS brokerId
                                                        ,d3.create_dealer_id
                                                        ,d3.id
                                                FROM    broker_recommend d3
                                                WHERE
                                                d3.`status` = '2' 
												and d3.create_is_seller='Y'
                                            )c2 
                                ON c1.brokerId = c2.brokerId 
                                and c1.dealerId=c2.create_dealer_id
                                GROUP BY
                                c1.brokerId,
                                c1.dealerId
                    ) b1
                    LEFT JOIN broker b2 
                    ON  b1.brokerId = b2.recommend_broker_id
                    AND b1.dealerId =b2.create_dealer_id
                    AND b2.create_is_seller='Y'
                    GROUP BY
                    b1.brokerId,
                    b1.dealerId
                    )a1
        GROUP BY
        a1.brokerId,
        a1.dealerId
    </select>
```

# 修改表结构sql语句

```sql
--1.删除一列
alter table TbStudent drop column stuPhone

--2.添加一列
alter table TbStudent add  stuPhone char(11)
alter table broker add create_dealer_id varchar(50) DEFAULT NULL COMMENT '经纪人推荐人所在的部门';
--3.修改字段的数据类型(表中Gender列不能有数据)

alter table TbStudent alter column stuGender nchar(1)
--4.添加主键约束

alter table TbStudent add constraint PK_TbStudent_stuId primary key(stuId)
--5.添加唯一性约束

alter table TbStudent add constraint UK_TbStudent_stuName unique(stuName)
--6.添加check约束

alter table TbStudent add constraint CK_TbStudent_stuAge
 
check(stuAge>=18 and stuAge<=35)
--7.添加非空约束,实际上就是对列的数据类型修改

alter table TbStudent alter column stuPhone char(11) not null
--8.添加外键约束

alter table TbStudent add constraint FK_TbStudent_stuClassId
 
foreign key(stuClassId) references TbClass(clsId)
--9.外键的级联删除/更新

--语法： on delete [no action cascade]

--       on update [no action cascade]

alter table TbStudent add constraint FK_TbStudent_stuClassId
 
foreign key(stuClassId) references TbClass(clsId) on delete cascade
--10.删除约束

alter table TbStudent drop constraint Fk_TbStudent_stuClassId
--11.一条语句删除多条约束

 alter table TbStudent drop constraint Fk_TbStudent_stuClassId,CK_TbStudent_stuAge
 --12.添加一条语句，添加多个约束

  alter table TbStudent add
 
  constraint FK_TbStudent_stuClassId foreign key(stuClassId) references TbClass(clsId)
 
  constraint PK_TbStudent_stuId primary key(stuId)

```

