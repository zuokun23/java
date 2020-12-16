package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 账户的业务层实现类
 *
 * 曾经xml的配置：
 * <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"
 *  scope="" init-method="" destory-method="">
 *  <property name=" value=""|ref=""></property>
 * >
 * </bean>
 * 用于创建对象的注解
 *      他们的作用和在xml配置文件中编写一个bean标签实现的功能一样
 *      @Component：
 *          作用：用于把当前类对象存入spring容器中
 *          属性：value--用于指定bean的id。当我们不写，它默认当前类名，且首字母小写。
 *      @Controller：一般用在表现层
 *      @Service：一般用在业务层
 *      @Repository：一般用在持久层
 *      以上三个注解的作用和属性与Component是一模一样的。
 *      他们三个是spring框架为我们提供明确的三层使用的注解，使我们对三层对象更加清晰。
 * 用于注入数据的注解
 *      他们的作用就和在xml配置文件中的bean标签中写一个property标签作用一样
 *      @Autowired:
 *          作用：自动按照类型注入。只要容器中有唯一的一个bean对象类型和要注入的变量类型匹配，就可以注入成功。
 *              如果ioc容器中没有任何bean类型和要注入的变量类型匹配，则报错
 *              如果ioc容器中有多个类型匹配时，按照变量名称和value值进行匹配
 *          出现位置：可以是变量上，也可以是方法上
 *          细节：
 *              在使用注解注入时，set方法不是必须的。
 *      @Qualifier:
 *          作用：在按照类型注入的基础上再按照名称注入。它在给类成员注入时不能单独使用(与@Autowired)，但在给方法参数注入时可以。
 *          属性：
 *              value：用于指定bean的id
 *      @Resource:
 *          作用：直接按照bean的id注入，可以独立使用
 *          属性：
 *              name：用于指定bean的id
 *          使用：pom中加入
 *          <dependency><groupId>javax.annotation</groupId>
 *              <artifactId>javax.annotation-api</artifactId>
 *             <version>1.3.1</version>
 *         </dependency>
 *      以上三种注入只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现。
 *      另外，集合类型的注入只能通过xml来实现。
 *      @Value
 *          作用：用于注入基本类型和String类型的数据
 *          属性：
 *              value用于指定数据的值。它可以使用spring的SpEL（也就是spring的el表达式）
 *              SpEL写法：${表达式}
 *
 * 用于改变作用范围的注解
 *      他们的作用就和bean标签中使用scope属性实现的功能是一样的
 *      @Scope
 *          作用：用于指定bean的作用范围
 *          属性：value指定范围的取值。常用取值：singletion（默认）、prototype（多例）
 * 和生命周期相关
 *      他们的作用和在bean标签中使用init-method和destroy-method作用是一样的
 *      @PreDestory
 *          作用：用于指定销毁方法
 *      @PostConstruct
 *          作用：用于指定初始化方法
 */
//默认类名且首字母小写，属性名称是value时可以省略
@Service(value = "account")
@Scope("singleton")
public class AccountServiceImpl implements IAccountService {
  //  @Autowired
    //@Qualifier("accountDao2")
    @Resource(name = "accountDao2")
    private IAccountDao accountDao1;
    @Value("123")
    private String str;

    @PostConstruct
    public void init(){
        System.out.println("初始化方法执行了");
    }
    @PreDestroy
    public void destory(){
        System.out.println("销毁方法执行了");
    }

    public AccountServiceImpl(){
        System.out.println("对象创建了");
    }

    public void  saveAccount(){
        accountDao1.saveAccount();
        System.out.println(str);
        System.out.println(accountDao1);
    }
}
