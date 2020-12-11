package com.itheima.service.impl;

import com.itheima.service.IAccountService;

import java.util.Date;

public class AccountServiceImpl2 implements IAccountService {
    //如果是经常变化的数据，并不适用于注入的方式
    private String name;//String
    private Integer age;//基本类型包装类
    private Date birthday;//其他bean类型

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public void  saveAccount(){
        System.out.println("service2中的saveAccount方法执行。。"+name+","+age+","+birthday);
    }
}
