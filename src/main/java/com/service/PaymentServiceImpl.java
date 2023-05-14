package com.service;

import com.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public List getPayment() {
        return paymentMapper.getList();

    }

    @Override
    public boolean recharge(Map map) {

        return paymentMapper.recharge(map) > 0 ? true : false;

    }

    @Override
    public int addUserBalance(Map map) {
       return paymentMapper.addUserBalance(map);
    }

    @Override
    public List<Map> getUserAccount(String uni_id) {
        Map map =new HashMap<>();
        map.put("uni_id",uni_id);
        return  paymentMapper.getUserAccount(map);
    }

    @Override
    public int deleteAccount(int id) {
        Map map=new HashMap<>();
        map.put("id",id);
        return paymentMapper.deleteAccount(map);
    }

    @Override
    public int addAccount(String uni_id ,String account,String address) {
        Map map=new HashMap();
        map.put("uni_id",uni_id);
        map.put("account",account);
        map.put("address",address);
        return paymentMapper.addAccount(map);
    }
}
