package com.service;

import com.mapper.WithdrawMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * author:LC
 * version:jdk-8u301
 */
@Service
public class WithdrawServiceImpl implements WithdrawService {

    @Resource
    private WithdrawMapper withdrawMapper;

    @Override
    public int subUserAccountBal(long balance, int id) {

        return withdrawMapper.updateUserAccountBal(balance, id);
    }

    @Override
    public int createWithdrawOrder(String uni_id, int withdraw_money) {
        String name="withdraw";
        Map map=new HashMap<>();
        map.put("uni_id",uni_id);
        map.put("withdraw_money",withdraw_money);
        map.put("name",name);

        return  withdrawMapper.createWithdrawOrder(map);
    }
}
