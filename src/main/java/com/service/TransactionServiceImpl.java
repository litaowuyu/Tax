package com.service;

import com.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:LC
 * version:jdk-8u301
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private TransactionMapper transactionMapper;

    @Override
    public List queryUserTeam(String uni_id) {
        return transactionMapper.selectUserTeam(uni_id);
    }

    @Override
    public List queryUserOrder(String uni_id) {
        return transactionMapper.selectUserOrder(uni_id);
    }

    @Override
    public List queryUserWithdraw(String uni_id) {
        return transactionMapper.selectUserWithdraw(uni_id);
    }

    @Override
    public List queryRechargeRecord(int id) {
        return  transactionMapper.selectUserRecharge(id);
    }

}
