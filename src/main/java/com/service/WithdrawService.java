package com.service;

/**
 * author:LC
 * version:jdk-8u301
 */
public interface WithdrawService {
    int subUserAccountBal(long balance, int id);

    int createWithdrawOrder(String uni_id,int withdraw_money);
}
