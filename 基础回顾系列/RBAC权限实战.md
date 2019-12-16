# RBAC权限实战

```
RBAC权限模型的设计、以及在项目中的应用 。
1. 使用Maven进行项目构建 。
2. 页面设计采用响应式前端框架BootStrap 。
3. 采用多种方式展现用户数据：树形结构（ztree）、图表(echarts) 等 。
4. 基础业务功能采用异步数据操作，增强用户体验效果 。
```

项目结构：

![JAVA_RBAC1.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC1.png?raw=true)

![JAVA_RBAC2.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC2.png?raw=true)

添加关系

![JAVA_RBAC3.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC3.png?raw=true)

## 权限模型

```
什么是权限？   就是用户登录后的权利(哪些功能可以方位)和限制(哪些功能不能访问)

RBAC——基于角色的访问控制
用户通过角色与权限进行关联。简单地说，一个用户拥有若干角色，每一个角色拥有若干权限。这样，就构造成“用户-角色-权限”的授权模型。在这种模型中，用户与角色之间，角色与权限（功能）之间，一般都是多对多的关系。
```

![JAVA_RBAC4.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC4.png?raw=true)

实际的工作中，权限模型设计的表会比较复杂

![JAVA_RBAC5.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC5.png?raw=true)

![JAVA_RBAC6.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC6.png?raw=true)

将RBAC权限模型应用到项目中

![JAVA_RBAC7.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC7.png?raw=true)

## 相对路径和绝对路径

```java
绝对路径：不可改变的路径
	本地绝对路径：增加盘符的路径（e:/test/test.html）
	网络绝对路径：增加协议，IP地址，端口号的路径（http://localhost:8080/test/test.html）
相对路径：可以改变的路径，但是以基准路径为参考，查找其他路径
	默认情况下，相对路径的基准路径是以当前资源的访问路径为基准

路径以斜杠开头，表示的特殊的相对路径，在不同的场景中，相对的位置会发生变化。

    url : http://localhost:8080/atcrowdfunding-web/test/test.html

	前台路径：<a href=”/sssss”><img src=””>
		相对服务器的根 ： http://localhost:8080/sssss
	后台路径：forward（”/user.jsp”）, xml
		相对web应用的根：http://localhost:8080/atcrowdfunding-web/user.jsp
/atcrowdfunding-web 这个路径可以动态获取。${pageContext.request.contextPath}

可以增加一个监听器解决路径问题:在容器启动的时候将${pageContext.request.contextPath}放入到全局变量中

public class ServerStartupListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent sce) {
		// 将web应用名称（路径）保存到application范围中
		ServletContext application = sce.getServletContext();
		String path = application.getContextPath();
		application.setAttribute("APP_PATH", path);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}
}
```

![JAVA_RBAC8.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC8.png?raw=true)

权限拦截的本质就是拦截器

获取到权限对应的访问路径，判断是否进行拦截

##  模糊查询

```sql
select * from  where  XX like '%#{XX}%' 
这样是不能查询对应数据，因为#{XX} 会在编译的时候被处理？又因为前面有单引号会被处理成一个字符串 '%？%'
所以如果想使用模糊查询就需要使用concat() 函数
select * from  where  XX like concat('%',#{XX},'%');
```



## ztree树模型表设计

![JAVA_RBAC9.png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/JAVA_RBAC9.png?raw=true)

对应的实体：

```java
public class Permission {

	private Integer id;
	private String name;
	private String url;
	private Integer pid;
	private boolean open = true;
	private boolean checked = false;
	private String icon;
	private List<Permission> children = new ArrayList<Permission>();
}

```

页面使用的目标数据

```json
var zNodes =[
					{ name:"父节点1 - 展开11111", open:true,
						children: [
							{ name:"父节点11 - 折叠",
								children: [
									{ name:"叶子节点111"},
									{ name:"叶子节点112"},
									{ name:"叶子节点113"},
									{ name:"叶子节点114"}
								]},
							{ name:"父节点12 - 折叠",
								children: [
									{ name:"叶子节点121"},
									{ name:"叶子节点122"},
									{ name:"叶子节点123"},
									{ name:"叶子节点124"}
								]},
							{ name:"父节点13 - 没有子节点", isParent:true}
						]},
					{ name:"父节点2 - 折叠",
						children: [
							{ name:"父节点21 - 展开", open:true,
								children: [
									{ name:"叶子节点211"},
									{ name:"叶子节点212"},
									{ name:"叶子节点213"},
									{ name:"叶子节点214"}
								]},
							{ name:"父节点22 - 折叠",
								children: [
									{ name:"叶子节点221"},
									{ name:"叶子节点222"},
									{ name:"叶子节点223"},
									{ name:"叶子节点224"}
								]},
							{ name:"父节点23 - 折叠",
								children: [
									{ name:"叶子节点231"},
									{ name:"叶子节点232"},
									{ name:"叶子节点233"},
									{ name:"叶子节点234"}
								]}
						]},
					{ name:"父节点3 - 没有子节点", isParent:true}

				];
```

查询树形结构的逻辑： 递归查询

```java

	@ResponseBody
	@RequestMapping("/loadAssignData")
	public Object loadAssignData( Integer roleid ) {
		List<Permission> permissions = new ArrayList<Permission>();
		List<Permission> ps = permissionService.queryAll();
		
		// 获取当前角色已经分配的许可信息
		List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);
		
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		for (Permission p : ps) {
			if ( permissionids.contains(p.getId()) ) {
				p.setChecked(true);
			} else {
				p.setChecked(false);
			}
			permissionMap.put(p.getId(), p);
		}
		for ( Permission p : ps ) {
			Permission child = p;
			if ( child.getPid() == 0 ) {
				permissions.add(p);
			} else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		
		return permissions;
	}
	
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData() {
		List<Permission> permissions = new ArrayList<Permission>();	
		// 查询所有的许可数据
		List<Permission> ps = permissionService.queryAll();

		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		for (Permission p : ps) {
			permissionMap.put(p.getId(), p);
		}
		for ( Permission p : ps ) {
			Permission child = p;
			if ( child.getPid() == 0 ) {
				permissions.add(p);
			} else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		
		return permissions;
	}
	
	/**
	 * 递归查询许可信息
	 * 1） 方法自己调用自己
	 * 2）方法一定要存在跳出逻辑
	 * 3）方法调用时，参数之间应该有规律
	 * 4） 递归算法，效率比较低
	 * @param parent
	 */
	private void queryChildPermissions( Permission parent ) {
		List<Permission> childPermissions = permissionService.queryChildPermissions(parent.getId());
		//这里的跳出逻辑是： childPermissions为null就会跳跳出去
		for ( Permission permission : childPermissions ) {
			queryChildPermissions(permission);
		}
		
		parent.setChildren(childPermissions);
	}
```


