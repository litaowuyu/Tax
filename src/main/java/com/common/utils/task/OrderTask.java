package com.common.utils.task;

import com.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class OrderTask {
    private final static Logger log = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    OrderMapper orderMapper;

    //一分钟一次（在上次执行完后）
    @Scheduled(fixedDelay = 60000)
    public void orderStateHandler() {
        log.info("开始执行订单查询-----");
        List orders = orderMapper.findByWhereWithList(new HashMap() {
            {
                put("state", 1);
                put("finish_time", new Date().getTime() - 60000);
            }
        });
        if (!orders.isEmpty()) {
            orderMapper.updateState(orders);
        }
        log.info("本轮查询完毕，共完成：" + orders.size() + "个");
    }
}
