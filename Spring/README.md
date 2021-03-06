# 01
- 程序的耦合：程序间的依赖关系
  - 包括 类之间的依赖、方法间的依赖
- 程序的解耦：降低程序间的依赖关系
  - 实际开发中：编译器不依赖，运行期间依赖
  - 解耦的思路：
    - 第一步：使用反射来创建对象，避免使用new关键字
    - 第二步：通过读取配置文件，来获取要创建的对象全限定类名
# 02
- 00一个创建Bean对象的工厂
   - Bean:在计算机中，有可重用组件的含义
   - JavaBean:用java语言编写的可重用组件
    - javabean > 实体类
    - 它就是创建我们的service和dao对象的。
    - 第一个：需要一个配置文件来配置我们的service和dao
      - 配置的内容：唯一标识=全限定类名（key=value）
    - 第二个：通过读取配置文件中内容，反射创建对象
    - 我的配置文件可以是xml也可以是properties(此处用properties因为更简单)
- 01单例模式
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

# 05 spring中的依赖注入
- 依赖注入（依赖关系的维护）：Dependency Injection
- IOC的作用：降低程序间的耦合（依赖关系）
- 依赖关系的管理：
  - 以后都交给spring来维护
  - 在当前类需要用到其他类的对象，有spring为我们提供，我们只需要在配置文件中说明
  - 能注入的数据：三类
    - 基本类型和String
    - 其他bean类型（在配置文件中或者注解配置过的bean）
    - 复杂类型/集合类型
      - 用于给Collection结构集合注入的标签：
        - list array set
      - 用于给Map结构集合注入的标签
        - map props
      - 结构相同，标签可以互换
  - 注入的方式：三种
    - [x] 使用构造函数
      - 使用的标签：constructor-arg
      - 标签出现的位置：bean标签的内部
      - 标签中的属性
        - type:用于指定要注入的数据的数据类型，该数据类型也是构造函数中的某个或某些参数的类型
        - index:用于指定要注入的数据 给 构造函数中指定索引位置的参数赋值
        - name:用于给构造函数中指定名称的参数赋值(以上三个用于指定给构造函数中的哪个参数赋值，name更常用)
        - value:用于提供基本类型和String类型数据。
        - ref:用于提供其他的bean类型。它指的就是在spring的Ioc容器中出现过的bean对象。
      - 优势：在获取bean对象时，注入数据是必须的操作，否则对象无法创建。
      - 弊端： 改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。
    - [x] 使用set方法
      - 涉及的标签：property
        - 出现的位置：bean标签的内部
        - 标签的属性：
          - name：用于指定注入时所调用的set方法名称
          - value：用于提供基本类型和String类型的数据
          - ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象。
      - 优势：创建对象时没有明确的限制，可以直接使用默认构造函数
      - 弊端：当某个成员必须有值，但获取对象时有可能set方法没有执行，使其不一定有值。
    - [x] 使用注解提供（明天内容）

# 06 Spring中的常见注解
```xml
    曾经xml的配置：
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"
    scope="" init-method="" destory-method="">
    <property name=" value=""|ref=""></property>>
    </bean>
 ```

- ## 1、spring中ioc的常见注解
   
  - 用于创建对象的注解
    - 他们的作用和在xml配置文件中编写一个bean标签实现的功能一样
    - @Component：
      - 作用：用于把当前类对象存入spring容器中
      - 属性：value--用于指定bean的id。当我们不写，它默认当前类名，且首字母小写。
    - @Controller：一般用在表现层
    - @Service：一般用在业务层
    - @Repository：一般用在持久层
    - 以上三个注解的作用和属性与Component是一模一样的
    - 他们三个是spring框架为我们提供明确的三层使用的注解，使我们对三层对象更加清晰。
  - 用于注入数据的注解
    - 他们的作用就和在xml配置文件中的bean标签中写一个property标签作用一样
    - @Autowired:        
      - 作用：自动按照类型注入。
        - 只要容器中有唯一的一个bean对象类型和要注入的变量类型匹配，就可以注入成功。
        - 如果ioc容器中没有任何bean类型和要注入的变量类型匹配，则报错
        - 如果ioc容器中有多个类型匹配时，按照变量名和value值匹配
      
       ![autowired](https://github.com/zuokun23/java/blob/main/Spring/images/autowired.png)
      - 出现位置：可以是变量上，也可以是方法上
      - 细节：
        在使用注解注入时，set方法不是必须的。
      
    - @Qualifier:
      - 作用：在按照类型注入的基础上再按照名称注入。它在给类成员注入时不能单独使用(与@Autowired)，但在给方法参数注入时可以。
      - 属性：
        - value：用于指定bean的id
    - @Resource:
      - 作用：直接按照bean的id注入，可以独立使用
      - 属性：
        name：用于指定bean的id
      - 使用：pom中加入
          ```xml
           <dependency><groupId>javax.annotation</groupId>
             <artifactId>javax.annotation-api</artifactId>
              <version>1.3.1</version>
          </dependency>
          ```
    - 以上三种注入只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现。
      另外，集合类型的注入只能通过xml来实现。
    - @Value
      - 作用：用于注入基本类型和String类型的数据
      - 属性：
        - value用于指定数据的值。它可以使用spring的SpEL（也就是spring的el表达式）
               SpEL写法：${表达式}
  - 用于改变作用范围的注解
    - 他们的作用就和bean标签中使用scope属性实现的功能是一样的
    - @Scope
      - 作用：用于指定bean的作用范围
      - 属性：value指定范围的取值。常用取值：singletion（默认）、prototype（多例）
  - 和生命周期相关
     - 他们的作用和在bean标签中使用init-method和destroy-method作用是一样的
     - @PreDestory
      - 作用：用于指定销毁方法
     - @PostConstruct
      - 作用：用于指定初始化方法

# 07 案例中使用xml方式实现单表的CRUD操作 
 
# 08 案例中使用xml方式和注解方式实现单表的CRUD操作
- 持久层技术选择：dbutils
# 09 改造基于注解的ioc案例，使用纯注解的方式实现
![](https://github.com/zuokun23/java/blob/main/Spring/images/xml_configuration.png)
- spring的一些新注解使用
  - @Configuration
    - 作用：指定当前类是一个配置类
    - 细节1：当配置类作为AnnotationConfigApplicationContext对象创建的参数时，该注解可以不写。
    - 细节2：@Configuration和@ComponentScan两者都要有，new AnnotationConfigApplicationContext(xx.class)可以视为对该类的@Configuration和@ComponnetScan都加上了
  - @ComponentScan
    - 作用：用于通过注解指定spring在创建容器时要扫描的包
    - 属性：value--它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包（@ComponentScan("com.itheima")）我们使用此注解等于在xml中配置了1
  - @Bean
    - 作用：用于把当前方法的返回值作为bean对象存入spring的ioc容器中
    - 属性：name--用于指定bean的id。当不写时，默认值是当前方法的名称。
    - 细节：当我们使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象。查找的方式和Autowired注解的作用是一样的
  - @Import
    - 作用：用于导入其他配置类
    - 属性：用于指定其他配置类的字节码，当我们使用Import的注解后，有Import注解的类就是父配置类，而导入的都是子配置类相当于new AnnotationConfigApplicationContext(xx.class)
 
  - @PropertySource
    - 作用：用于指定propertied文件的位置
    - 属性：value指定文件的名称和路径。关键字：classpath表示类路径下
    
# 10 spring和junit的整合
- 1、应用程序的入口--main方法

- 2、junit单元测试中，没有main方法也能执行
  - junit集成了一个main方法
  - 该方法就会判断当前测试类中哪些方法有@Test注解
  - junit就让有Test注解的方法执行

- 3、junit不会管我们是否采用spring框架
  - 在执行测试方法时，junit根本不知道我们是不是使用了spring框架
  - 所以也就不会为我们读取配置文件/配置类创建spring核型容器

- 4、由以上三点可知
  - 当测试方法执行时，没有ioc容器来读取注解，就算写了@Autowired注解，也无法实现注入。
  
- 5、Spring整合junit的配置
  - 1、导入spring整合junit的jar（坐标）
    ```Java
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.0.5.RELEASE</version>
      <scope>test</scope>
    </dependency>
    ```
  - 2、使用junit提供的注解把原有的main方法替换成spring提供@RunWith()
    ```Java
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = SpringConfiguration.class)
    public class AccountServiceTest {
    ```
  - 3、告知spring运行器，spring和ioc创建是基于xml还是注解的，并且说明位置
    - @ContextConfiguration
      - locations:指定xml文件的位置，加上classpath关键字，表示在类路径下
      - classes：指定注解类所在的位置

  - 4、在test类中直接注入
    ```
    @Autowired
    private IAccountService as;
    ```
# 11
```
第三天
1、完善我们的account案例
2、分析案例中问题
3、回顾之前讲过的一个技术：动态代理
4、动态代理另一种实现方式
5、解决案例中的问题
6、AOP的概念
7、spring中AOP的相关术语
8、spring中基于XML和注解的AOP配置
```
