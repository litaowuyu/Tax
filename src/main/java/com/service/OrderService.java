package com.service;

import java.util.List;
import java.util.Map;

public interface OrderService {
    //查看当前用户订单状态
    public int selectOrderState(int user_id);
    //购买商品
    public int buyGoods(Map map);
    //生成订单
    public int createOrder( Map map);
    //获取商品id
    public Map getGoodsInfo(int id);
    //获取正在进行中的订单,根据用户的VIP等级和用户id查询
    public List getProcessingOrder(Map map);
    //获取当前用户所有订单
    public  List getAllProcessingOrder(Map map);
    //完成订单
    public int completedOrder(Map map);
}
