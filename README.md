# 1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理
 动态sql指根据实体类的不同取值，使用不同的SQL语句来进行查询。
## 动态sql:
* <if />
* <choose />
* <when />
* <otherwise />
* <trim />
* <where />
* <set />
* <foreach />
* <bind />
动态sql执行原理：根据条件来动态拼接sql。

# 2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？
支持延迟加载。
实现原理：MyBatis延迟加载是通过动态代理实现的，当调用配直为延迟加载的属性方法时，动态代理的操作会被触发，这些额外的操作就是通过MyBatis 的 SqlSession去执行嵌套SQL的。

# 3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？
BatchExecutor：重用语句和批量更新，针对批量专用
ReuseExecutor：重用预处理语句
SimpleExecutor：简易执行器，默认配置

# 4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？
一级缓存	
存储结构：HashMap 范围：SqlSession 失效场景：进行增删改并提交事务或调用sqlSession.clearCache()会清空缓存
二级缓存	
存储结构：HashMap 范围：SqlSessionFactory	失效场景：进行增删改并提交事务

# 5、简述Mybatis的插件运行原理，以及如何编写一个插件？
   插件的初始化是在加载核心配置文件的时候完成的，其被保存在一个list集合中。之后使用责任链模式为四大对象生成代理对象并返回，                        当代理对象调用方法时就会进入invoke()方法中，在invoke方法中，如果存在签名的拦截方法，这时就会调用插件的intercept()
   
自定义插件：
```bash
   @Intercepts({
        @Signature(type= StatementHandler.class,
                  method = "prepare",
                  args = {Connection.class,Integer.class})
})
public class MyPlugin implements Interceptor {

    /*
        拦截方法：只要被拦截的目标对象的目标方法被执行时，每次都会执行intercept方法
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("对方法进行了增强....");
        return invocation.proceed(); //原方法执行
    }

    /*
       主要为了把当前的拦截器生成代理存到拦截器链中
     */
    @Override
    public Object plugin(Object target) {
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    /*
        获取配置文件的参数
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("获取到的配置文件的参数是："+properties);
    }
}   
```
