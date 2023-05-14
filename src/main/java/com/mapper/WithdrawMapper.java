package com.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * author:LC
 * version:jdk-8u301
 */
@Repository
public interface WithdrawMapper extends BaseMapper{

    int updateUserAccountBal(long balance, int id);
    //创建提现订单
    int createWithdrawOrder(Map map);
}
