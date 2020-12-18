package com.itheima.test;

import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import config.JdbcConfig;
import config.SpringConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用junit单元测试，测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {
//
//    1、应用程序的入口--main方法
//    2、junit单元测试中，没有main方法也能执行
//      junit集成了一个main方法
//      该方法就会判断当前测试类中哪些方法有@Test注解
//      junit就让有Test注解的方法执行
//    3、junit不会管我们是否采用spring框架
//      在执行测试方法时，junit根本不知道我们是不是使用了spring框架
//      所以也就不会为我们读取配置文件/配置类创建spring核型容器
//    4、由以上三点可知
//      当测试方法执行时，没有ioc容器来读取注解，就算写了@Autowired注解，也无法实现注入。
//    private ApplicationContext ac;
//    @Autowired
//    private IAccountService as;
//     5、Spring整合junit的配置
//       1、导入spring整合junit的jar（坐标）
//    ```
//    <!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
//<dependency>
//    <groupId>org.springframework</groupId>
//    <artifactId>spring-test</artifactId>
//    <version>5.0.5.RELEASE</version>
//    <scope>test</scope>
//</dependency>
//    ```
//    2、使用junit提供的注解把原有的main方法替换成spring提供@RunWith()
//    3、告知spring运行器，spring和ioc创建是基于xml还是注解的，并且说明位置
//    @ContextConfiguration
//    locations:指定xml文件的位置，加上classpath关键字，表示在类路径下
//    classes：指定注解类所在的位置
    /*
    @Before
    public void init(){
        //1获取容器
        //ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        //2得到业务层对象
        as = ac.getBean("accountService", IAccountService.class);
    }*/

    @Autowired
    private IAccountService as;

    @Test
    public void testFindAll(){
        //3执行方法
        List<Account> accounts = as.findAllAccount();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne(){
        //3执行方法
        Account account = as.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void testSave(){
        //3执行方法
        as.saveAccount(new Account("ddhsoxoxxj", 1200f));
    }

    @Test
    public void testUpdate(){
        //3执行方法
        Account account = as.findAccountById(3);
        account.setMoney(23456f);
        as.updateAccount(account);

    }

    @Test
    public void testDelete(){
        //3执行方法
        as.deleteAccount(4);
    }

}
