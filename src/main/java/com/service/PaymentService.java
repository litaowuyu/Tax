package com.service;

import java.util.List;
import java.util.Map;

public interface PaymentService {

public List getPayment();

public boolean recharge(Map map);
//获取用户当前所有账户
public List<Map> getUserAccount(String uni_id);
//删除账户
    public int deleteAccount(int id);
    //添加账户
    public int addAccount(String uni_id ,String account,String address);
    //更新账户余额
    public int addUserBalance(Map map);
}
