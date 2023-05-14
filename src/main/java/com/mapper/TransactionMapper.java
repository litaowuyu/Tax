package com.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:LC
 * version:jdk-8u301
 */
@Repository
public interface TransactionMapper extends  BaseMapper{
    //获取tax_tran表的数据
    List selectUserTeam(String uni_id);
    //获取tax_order表的数据
    List selectUserOrder(String uni_id);
    //获取tax_withdraw表的数据
    List selectUserWithdraw(String uni_id);
    //获取tax_recharge表的数据
    List selectUserRecharge(int id);
}
