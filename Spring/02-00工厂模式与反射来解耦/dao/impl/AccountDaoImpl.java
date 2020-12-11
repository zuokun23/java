package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;

public class AccountDaoImpl implements IAccountDao {

    /**
     * 账户持久层实现类
     */
    @Override
    public void saveAccount() {
        System.out.println("保存了账户");
    }
}
