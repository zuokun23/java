package com.itheima.test;

import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 使用junit单元测试，测试我们的配置
 */
public class AccountServiceTest {

    @Test
    public void testFindAll(){
        //1获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2得到业务层对象
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        //3执行方法
        List<Account> accounts = as.findAllAccount();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne(){
        //1得到容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2得到业务层对象
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        //3执行方法
        Account account = as.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void testSave(){
        //1得到容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2获得业务层对象
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        //3执行方法
        as.saveAccount(new Account("ddd", 1200f));
    }

    @Test
    public void testUpdate(){
        //1获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2获得业务层对象
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        //3执行方法
        Account account = as.findAccountById(4);
        account.setMoney(23456f);
        as.updateAccount(account);

    }

    @Test
    public void testDelete(){
        //1获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2获得业务层对象
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        //3执行方法
        as.deleteAccount(4);
    }

}
