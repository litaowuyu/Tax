package com.service;

import java.util.List;

/**
 * author:LC
 * version:jdk-8u301
 */
public interface TransactionService {

    List queryUserTeam(String uni_id);

    List queryUserOrder(String uni_id);

    List queryUserWithdraw(String uni_id);

    List queryRechargeRecord(int id);

}
