# 01
- 程序的耦合：程序间的依赖关系
  - 包括 类之间的依赖、方法间的依赖
- 程序的解耦：降低程序间的依赖关系
  - 实际开发中：编译器不依赖，运行期间依赖
  - 解耦的思路：
    - 第一步：使用反射来创建对象，避免使用new关键字
    - 第二步：通过读取配置文件，来获取要创建的对象全限定类名
# 02
- 一个创建Bean对象的工厂
   - Bean:在计算机中，有可重用组件的含义
   - JavaBean:用java语言编写的可重用组件
    - javabean > 实体类
    - 它就是创建我们的service和dao对象的。
    - 第一个：需要一个配置文件来配置我们的service和dao
      - 配置的内容：唯一标识=全限定类名（key=value）
    - 第二个：通过读取配置文件中内容，反射创建对象
    - 我的配置文件可以是xml也可以是properties(此处用properties因为更简单)
# 02 改进：加入单例模式
# 03 
- 获取spring的ioc核心容器，并根据id获取对象
  - ApplicationContext的三个常用实现类：
     - lassPathXmlApplicationContext:它可以加载路径下的配置文件，要求配置文件必须在类路径下。不在的话，加载不了。
     - FileSystemXmlApplicationContext：它可以加载磁盘任意路径下的配置文件（必须有访问权限）
     - AnnotationConfigApplicationContext：它用于读取注解配置创建容器
  - 核心容器的两个接口引发出的问题：
     - ApplicationContext:
       - 单例对象适用时（总共只有一个实例，不如早创建）
          - (它在构建核心容器时，创建对象采取的策略是立即加载的方式。也就是说，只要一读取完配置文件马上就创建文件中配置的对象。）
       - 多例对象时采用延迟加载
       - 这个接口非常智能，所有选择这个
     - BeanFactory:
        - 多例对象适用
         -（反正对象次数不限定，加载xml实例一个没有必要）
         -（它在创建核心容器时，创建对象采取的策略是延迟加载的方式。
           也就是说，什么时候根据id获取对象了，什么时候才真正的创建对象。）
# 04           
- ## 1.创建bean的三种方式
  - [x] 第一种方式：使用默认的构造函数：在spring的配置文件中使用bean标签，使用id和class属性，且没有其他属性和标签时。此时类中没有默认构造函数，则对象无法创建。
    - java中如果在一个类中没有写明任何构造函数的,那么会存在一个无参的构造函数。
    - 但如果写明了一个有参的构造函数,那么无参的构造函数就不复存在了。
    - bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">/bean>
  - [x] 第二种方式：使用工厂中的普通方法创建对象，使用某个类中的方法创建对象，并存入spring容器
    - 模拟一个工厂类，该类可能存在于jar包中的，以class而不是java方式呈现。
    - 我们无法通过修改源码的方式提供构造函数。
    - bean id="instanceFactory" class="com.itheima.factory.InstanceFactory">/bean>
    - bean id="accountService" factory-bean="instanceFactory" factory-method="getAccountService">/bean>
  - [x] 第三种方式：使用工厂中的静态方法创建对象，使用某个类中的静态方法创建对象，并存入spring容器
    - 模拟一个工厂类，该类可能存在于jar包中的，以class而不是java方式呈现。
    - 我们无法通过修改源码的方式提供构造函数。   
    - bean id="accountService" class="com.itheima.factory.StaticFactory" factory-method="getAccountService">/bean>
    
- ## 2.bean对象的作用范围
  - bean标签的scope属性：
    -  作用：用于指定bean的作用范围
    -  取值：
      - [x] singleton：单例的（默认）
      - [x] prototype：多例的
        - bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl" scope="prototype"></bean>
      - [x] request：作用于web应用的请求范围
      - [x] session：作用于web应用的会话范围
      - [x] global-session：作用于集群环境的会话范围（全局会话范围），当不上集群环境时，它就是session
  
- ## 3.bean对象的生命周期
  - 单例对象：当容器创建时对象出生
    - 活着：只要容器还在，对象一直活着
    - 死亡：容器销毁，对象消亡
    - 总结：单例对象的生命周期和容器相同
    - 未使用ac.close()关闭，不会调用destroy方法，因为main结束销毁线程占有一切内存，容器也被销毁，但此时还没有来得及调用销毁方法，就消失了（close方法在子类中，不再父类中）
    - bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl" scope="singleton"init-method="init" destroy-method="destroy">/bean>
  - 多例对象： 
    - 出生：当我们使用时创建
    - 活着：对象只要在使用过程中就一直活着
    - 死亡：当对象长时间不用，且没有别的对象引用时，由java垃圾回收器回收。容器不知道我们使用多久。

# 04 spring中的依赖注入
- 依赖注入（依赖关系的维护）：Dependency Injection
- IOC的作用：降低程序间的耦合（依赖关系）
- 依赖关系的管理：
  - 以后都交给spring来维护
  - 在当前类需要用到其他类的对象，有spring为我们提供，我们只需要在配置文件中说明
  - 能注入的数据：三类
    - 基本类型和String
    - 其他bean类型（在配置文件中活着注解配置过的bean）
    - 复杂类型/集合类型
  - 注入的方式：三种
    - 使用构造函数
      - 使用的标签：constructor-arg
      - 标签出现的位置：bean标签的内部
      - 标签中的属性
        - type:用于指定要注入的数据的数据类型，该数据类型也是构造函数中的某个或某些参数的类型
        - index:用于指定要注入的数据 给 构造函数中指定索引位置的参数赋值
        - name:用于给构造函数中指定名称的参数赋值
===============以上三个用于指定给构造函数中的哪个参数赋值，name更常用=================
        - value:用于提供基本类型和String类型数据。
        - ref:用于提供其他的bean类型。它指的就是在spring的Ioc容器中出现过的bean对象。
        - 优势：在获取bean对象时，注入数据是必须的操作，否则对象无法创建。
        - 弊端： 改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。
    - 使用set方法
    - 使用注解提供（明天内容）
