# 工程简介

   实时获取从 **WebSocket 服务器** 推送过来的重量等信息，并将其存入数据库。


# 延伸阅读
+ 1、实现Java的WebSocket客户端，首先需要引入`Java-WebSocket`依赖；
+ 2、通过配置application.properties中的websocket.server.ip，可以实现同时连接多个服务器；
+ 3、采用fastjson解析数据；
+ 4、通过lombok简化实体类的get/set方法, 但在idea初次使用Lombok时，还需在给idea 安装其插件——`file -> Setting -> Plugins`

# 问题

+ 1、[手动添加DB2 JDBC驱动](http://m.hangge.com/news/cache/detail_2832.html)

+ 2、[搭建springboot时连接不到数据库Failed to determine a suitable driver class](https://blog.csdn.net/yuajiang/article/details/108739096) 第四点

+ 3、[使用@Autowired 自动装载，报java.lang.NullPointerException错误，解决方案](https://blog.csdn.net/lyf_ldh/article/details/83270454)
    + https://blog.csdn.net/kshon/article/details/102522954
    + https://blog.csdn.net/wenyichuan/article/details/109315211

+ 4、[断开连接自动重连](https://my.oschina.net/u/4283198/blog/4669097)

## SpringBoot WebSocket 使用@Autowired注入JdbcTemplate 时 报java.lang.NullPointerException错误    [参考页面](https://www.itdaan.com/blog/2019/07/05/6f564e1bbb9c8e2a926dcf59bf9a3841.html)
    
+ 原因：spring管理的都是单例（singleton），和 websocket （多对象）相冲突。
+ 解决方式：
    + 上述第三点；
    + 具体实施：
        ①、编写一个工具类`ApplicationContextHelperUtil`；
        ②、在MyWebSocket的onMessage()中，调用信息处理器（MessResolver）之前通过ApplicationContextHelperUtil手动注入，而不使用@Autowired和new;