#     优雅写java代码技巧

# List频繁add 简化

## list方法的

```java
List<SellerInfo>  sellerInfoList = new ArrayList<>();
     sellerInfoList.add(new SellerInfo());
     sellerInfoList.add(new SellerInfo());
     sellerInfoList.add(new SellerInfo());
```

简化为

```java
List<SellerInfo> sellerInfoList2 = Arrays.asList(new SellerInfo(),new SellerInfo(),new SellerInfo());
```

list的语法省去了各种add.

# Java 如何避免写嵌套if样式的代码

**Optional**的代码相对更加简洁，当代码量较大时，我们很容易忘记进行null判定，使用Optional类则会避免这类问题。

## if 嵌套代码

下面这是一个嵌套的 if 判断

```java
//业务逻辑是从 httpRequst 中获取 X-Auth-Token 的值。逻辑是如果 header中有值则从 header 中取值否则从 cookie 中取值,取到值后调用一个 http 远程接口 获取用户信息,获取不到则报“获取用户信息失败”,如果 token 都不存在则直接返回 httpRespons 为 401-NoAuth

if (methodNeedAuth) {
    //***身份验证
    String token = request.getHeader("X-Auth-Token");
    if (StringUtils.isEmpty(token)) { // 如果 header 中没有 X-Auth-Token 则从 cookie 中取
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) { //cookie 为 null
            return returnNoAuthResult(response);
        } //这个地方判空,否则下面的 Arrays.stream 回报空指针异常
        token = Arrays.stream(cookies).filter(cookie ->
                "X-Auth-Token".equals(cookie.getName())
        ).collect(Collectors.toList()).get(0).getValue();
        if (token == null) { // cookie 有值但是 cookie 中没有 X-Auth-Token
            return returnNoAuthResult(response);
        }
    }
    if (!StringTool.isNullOrEmpty(token)) {
        userInfo = userService.getUserInfoByToken(token);
    }
    if (userInfo == null || StringTool.isNullOrEmpty(userInfo.getUser_id())) {
        return returnNoAuthResult(response);
    }
}
```

从上面的代码看要实现业务逻辑，需要多层的if 嵌套

## Optional 规避 if 嵌套

Optional 类的使用需要jdk8

```java
if (methodNeedAuth) {
    //***身份验证
    String token = Optional.ofNullable(request.getHeader("X-Auth-Token")).orElseGet(() ->
            getTokenFromCookie(request) //提取出一个方法
    );
    userInfo = Optional.ofNullable(token).map(Try.of(t ->
            userService.getUserInfoByToken(t))
    ).orElse(null);
    if (userInfo == null || StringTool.isNullOrEmpty(userInfo.getUser_id())) {
        response.sendError(401, "no auth");
        return false;
    }
}
/**
 * 从 cookie 中获取 token
 */
private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]); // Optional 强制赋默认值,cookies一定不为 null
        String cookie = Arrays.stream(cookies).filter(item ->
                "X-Auth-Token".equals(item.getName())
        ).findFirst().map(Cookie::getValue).orElse(null);
        return cookie;
}
```

## 注意：

### Java8 Optional 的常规用法

Java8 的 Optional 可以规避所有的空指针异常问题么？答案当然是否定的, `Optional<T>()` 也是对象,他也会为 null, 所以也有可能报空指针异常哟。

**Optional 的三种构造方式: Optional.of(obj), Optional.ofNullable(obj) 和明确的 Optional.empty()**

1. Optional.of(obj): 它要求传入的 obj 不能是 null 值的, 否则还没开始进入角色就倒在了 NullPointerException 异常上了.
2. Optional.ofNullable(obj): 它以一种智能的, 宽容的方式来构造一个 Optional 实例. 来者不拒, 传 null 进到就得到 Optional.empty(), 非 null 就调用 Optional.of(obj).

那是不是我们只要用 Optional.ofNullable(obj) 一劳永逸, 以不变应万变的方式来构造 Optional 实例就行了呢? 那也未必, 否则 Optional.of(obj) 何必如此暴露呢, 私有则可?

1. 当我们非常非常的明确将要传给 Optional.of(obj) 的 obj 参数不可能为 null 时, 比如它是一个刚 new 出来的对象(Optional.of(new User(…))), 或者是一个非 null 常量时;
2. 当想为 obj 断言不为 null 时, 即我们想在万一 obj 为 null 立即报告 NullPointException 异常, 立即修改, 而不是隐藏空指针异常时, 我们就应该果断的用 Optional.of(obj) 来构造 Optional 实例, 而不让任何不可预计的 null 值有可乘之机隐身于 Optional 中.

### Java8 Optional需要小心的地方

1. 调用 Optional.get() 前不事先用 isPresent() 检查值是否可用. 假如 Optional 不包含一个值, get() 将会抛出一个异常
2. 使用任何像 Optional 的类型作为字段或方法参数都是不可取的. Optional 只设计为类库方法的, 可明确表示可能无值情况下的返回类型. Optional 类型不可被序列化, 用作字段类型会出问题的

一句话小结: 使用 Optional 时尽量不直接调用 Optional.get() 方法, Optional.isPresent() 更应该被视为一个私有方法, 应依赖于其他像 Optional.orElse(), Optional.orElseGet(), Optional.map() 等这样的方法。

随笔-58  文章-0  评论-22 

# 判断字符串String是否为空

```java
判断一个字符串str不为空的方法有：

　　1、str == null;

　　2、"".equals（str）；

　　3、str.length <= 0;

　　4、str.isEmpty（）；
```

使用工具类：

```java
StringUtils.isBlank();
```

