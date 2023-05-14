package com.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface OrderMapper extends BaseMapper{
    //查看订单状态
    public List selectOrderState(int id);
    //更新balance
    public int updateBalance(Map map);
    //创建订单
    public int createOrder(Map map);

    //更新订单状态,动态监控，1--------->2
    public int updateState(List orderList);
    //查询正在进行中的订单,根据vip_level和user_id
    public List findProcessingOrder(Map map);
    //查询当前用户所有订单
    public List findAllProcessingOrder(Map map);
    //完成订单
    public int completedOrder(Map map);
}
