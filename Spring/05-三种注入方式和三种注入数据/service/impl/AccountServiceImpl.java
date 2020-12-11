package com.itheima.service.impl;

import com.itheima.service.IAccountService;

import java.util.Date;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    //private IAccountDao ad = new AccountDaoImpl();

    //如果是经常变化的数据，并不适用于注入的方式
    private String name;//String
    private Integer age;//基本类型包装类
    private Date birthday;//其他bean类型

    public AccountServiceImpl(String name, Integer age, Date birthday){
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public void  saveAccount(){
        System.out.println("service中的saveAccount方法执行。。"+name+","+age+","+birthday);
    }

}
