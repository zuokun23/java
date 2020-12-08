package com.itheima.ui;

import com.itheima.factory.BeanFactory;
import com.itheima.service.IAccountService;
import com.itheima.service.impl.AccountServiceImpl;

/**
 * 模拟表现层，调用业务层
 */
public class Client {
    public static void main(String[] args) {

        //IAccountService accountService = new AccountServiceImpl();
        IAccountService as = (IAccountService) BeanFactory.getBean("accountService");
        System.out.println("hello");
        as.saveAccount();
    }
}
