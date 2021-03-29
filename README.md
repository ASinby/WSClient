# 工程简介



# 延伸阅读
+ [手动添加DB2 JDBC驱动](http://m.hangge.com/news/cache/detail_2832.html)

+ [搭建springboot时连接不到数据库Failed to determine a suitable driver class](https://blog.csdn.net/yuajiang/article/details/108739096) 第四点

+ [使用@Autowired 自动装载，报java.lang.NullPointerException错误]()

# 问题

+ SpringBoot WebSocket 使用注入(@Autowired) JdbcTemplate 时 报java.lang.NullPointerException错误    [参考页面](https://www.itdaan.com/blog/2019/07/05/6f564e1bbb9c8e2a926dcf59bf9a3841.html)
    + 原因：spring管理的都是单例（singleton），和 websocket （多对象）相冲突。
    + 解决方式：
    
    https://blog.csdn.net/kshon/article/details/102522954
    https://blog.csdn.net/wenyichuan/article/details/109315211

```java
Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2021-03-19 17:02:58.478 ERROR 7572 --- [  restartedMain] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

A component required a bean of type 'com.sinby.wsclient.Dao.TableDao' that could not be found.


Action:

Consider defining a bean of type 'com.sinby.wsclient.Dao.TableDao' in your configuration.
```