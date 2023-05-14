package com.controller;

import com.common.exceptionHandle.Result;
import com.service.OrderService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    final double VIP0 = 0.025;
    final double CONSTANT = 0.005;

    final int state1 = 1;
    final int state2 = 2;
    final int state3 = 3;

    @PostMapping("/Auth/buyGoods")
    public Map buyGoods(@RequestParam("goods_id") int goods_id) {
        /**
         * 点击购买，获取当前用户信息
         * 查看当前用户是否有正在进行中的订单，标志是state
         * 获取当前用户信息放入集合user
         * 声明一个Map放生成订单需要的参数
         * 获取商品的VIP等级，加上相对应的完成时间
         * */
        //获取商品信息
        System.out.println("goods_id" + goods_id);

        Map goods = orderService.getGoodsInfo(goods_id);
        System.out.println(goods);

        int reward = (int) goods.get("reward");
        int price = (int) goods.get("price");
        int hours = (int) goods.get("hours");
        System.out.println("price" + price + " reward" + reward);
        int goods_vip_level = ((int) goods.get("vip_level") - 2);
//        System.out.println("当前商品的VIP等级" + goods_vip_level);
        //获取当前用户信息
        Map user = userService.userInfo();
        int user_id = (int) user.get("id");
        String uni_id= (String) user.get("uni_id");
//        System.out.println("当前用户的ID" + user_id);
        //获取用户VIP等级
        int user_vip_level = (int) user.get("vip_level");
//        System.out.println("当前用户的VIP等级" + user_vip_level);
        //查询用户是否有订单在进行
        /**
         * 需要查询当前用户的所有订单信息只要订单中出现有小于2的状态就不能继续
         */
//        System.out.println("当前用户的订单状态" + orderService.selectOrderState(user_id));
        int orderState = orderService.selectOrderState(user_id);
//        System.out.println("当前用户的订单状态" + orderState);
        /**
         * 订单状态为2时才能进行够买
         * 获取购买商品的VIP等级，根据相应的等级来加入对应的完成时间时间
         */
        if (orderState == 0) {
//            System.out.println("在OrderController的判断中...");
//            System.out.println(user);
//            System.out.println("用户账号余额" + user.get("balance"));
            //将数据类型改为int

            long balance = (long) user.get("balance");
//            System.out.println("可能存在问题的Balance"+balance);
            Map order = new HashMap<>();
            order.put("user_id", user_id);
            order.put("uni_id",uni_id);
            order.put("goods_id", goods_id);
            order.put("reward", reward);
            order.put("create_time", new Date().getTime());
            order.put("finish_time", new Date().getTime() + ((long) hours * 60 * 60 * 1000));
            /**
             * 购买商品条件
             * 对应等级的VIP只能购买相应等级的商品
             * 账户的余额大于购买商品的价格
             * 用户的VIP等级和商品的VIP等级一致
             * 购买成功给前端返回0
             * 账户余额不足导致的购买失败给前端返回1
             * VIP等级不符合导致的购买失败给前端返回2
             * 订单正在进行中导致的购买失败给前端返回3
             */
            if (goods_vip_level <= user_vip_level) {
                if (balance >= price) {
                    balance = balance - price;
//                    System.out.println("用户购买商品之后的余额" + balance);
                    order.put("balance", balance);
                    //创建订单
                    orderService.createOrder(order);
                    //扣除金额，更新余额
                    orderService.buyGoods(order);
                    return Result.data(0);
                } else {
                    return Result.data(1);
                }
            } else {
                return Result.data(2);
            }
        }
        return Result.data(3);
    }

    @GetMapping("/Auth/processingOrderList")
    public Map processingOrderList(@RequestParam("vip_level") String vip_level, String btnIndex) {
//        System.out.println("btnIndex:" + btnIndex);
        Map user = userService.userInfo();
        int user_id = (int) user.get("id");
        Map map = new HashMap();
        map.put("user_id", user_id);
        map.put("vip_level", vip_level);
        if (btnIndex.equals("1")) {
            if (vip_level.equals("1")) {
                map.put("state", state1);
                return Result.data(orderService.getAllProcessingOrder(map));
            }
            map.put("state", state1);
        } else if (btnIndex.equals("2")) {
            if (vip_level.equals("1")) {
                map.put("state", state2);
                return Result.data(orderService.getAllProcessingOrder(map));
            }
            map.put("state", state2);
        }
//        System.out.println("前端传的vip_level：" + vip_level);
//        System.out.println("后端拉取的数据：" + orderService.getProcessingOrder(map));
        return Result.data(orderService.getProcessingOrder(map));

    }

    /**
     * 完成订单
     * 修改订单状态2--->3
     * 前端传过来订单的商品号，后端获取当前用户信息，两个标志作为数据库中的唯一标志
     */
    @PostMapping("/Auth/competeOrder")
    public Map competeOrder(@RequestParam("goods_id") int goods_id) {

//        System.out.println("前端传入的goods_id" + goods_id);
        Map user = userService.userInfo();
        int user_id = (int) user.get("id");
        int user_vip_level = (int) user.get("vip_level");
        double balance = (long) user.get("balance");
        String uni_id = (String) user.get("uni_id");
        /*
        1.VIP收益等级的常量：VIP0-->2.5%=0.025，随等级增长依次增加0.5%
        2.获取商品的hours字段，转化”天“为单位
        3.goods.price * VIP常量 * (hours/24) * 100 = reward
        4.更新tax_user.balance：balance = balance + reward
        */
        Map goodsInfo = orderService.getGoodsInfo(goods_id);//通过user_id查询tax_order中商品信息
//        System.out.println("获取商品信息：----------------" + goodsInfo);
        int price = (int) goodsInfo.get("price");
        double hours = (int) goodsInfo.get("hours");
        //(balance + price * (VIP0 + CONSTANT * user_vip_level) * (hours / 24) 计算reward
        balance = (balance + price * (VIP0 + CONSTANT * user_vip_level) * Math.ceil(hours / 24) + price);

        Map map = new HashMap();
        map.put("user_id", user_id);
        map.put("goods_id", goods_id);
        map.put("balance", balance);
        map.put("uni_id", uni_id);
        int i = orderService.buyGoods(map);
        int flag = orderService.completedOrder(map);
        if (flag > 0 && i > 0) {
            return Result.success();
        }
        return Result.error(7, "CompleteOrder Fail");

    }

}
