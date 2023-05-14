package com.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PaymentMapper extends BaseMapper{
    //充值
    public int recharge(Map map);
    //获取用户账号信息
    public List<Map> getUserAccount(Map map);
    //删除用户账号
    public int deleteAccount(Map map);
    //添加账户
    public int addAccount(Map map);
    //更新账户余额
    public int addUserBalance(Map map);
}
