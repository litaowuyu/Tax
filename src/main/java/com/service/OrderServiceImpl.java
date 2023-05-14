package com.service;

import com.alibaba.fastjson.parser.JSONToken;
import com.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public int selectOrderState(int user_id) {
        System.out.println(orderMapper.selectOrderState(user_id));
        List<Map> list= orderMapper.selectOrderState(user_id);
        Set stateSet= new HashSet<>();
        /**
        * 遍历List拿到所有状态
         * */
        for ( Map state: list) {
            //拿到的state是个map
            System.out.println(state);
            int stateCode= (int) state.get("state");
            System.out.println( stateCode);
            stateSet.add(stateCode);
        }
        for (Object state: stateSet) {
            //存在订单了，就不能创建订单
            if((int)state<2){
            return 1;
            }
        }
        return 0;
    }

    @Override
    public int buyGoods(Map map) {

//        System.out.println("要更新的金额------->" +map.get("balance"));
//        System.out.println("要更新的用户uni_id"+map.get("uni_id"));
        return orderMapper.updateBalance(map);
    }

    @Override
    public int createOrder(Map map) {

        return orderMapper.createOrder(map) > 0 ? 1 : 0;
    }

    @Override
    public Map getGoodsInfo(int user_id) {
        return orderMapper.findByID(user_id);
    }

    @Override
    public List getProcessingOrder(Map map) {
        return orderMapper.findProcessingOrder(map);
    }

    @Override
    public List getAllProcessingOrder(Map map) {
        return  orderMapper.findAllProcessingOrder(map);
    }

    @Override
    public int completedOrder(Map map) {
        return orderMapper.completedOrder(map);
    }
}
