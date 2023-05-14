package com.controller;

import com.common.exceptionHandle.Result;
import com.service.GoodsService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;


    @GetMapping("/noAuth/goods/list")
    public Map getList(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "vipID", required = false, defaultValue = "0") int vipID
    ) {

        int pageNum1 = Integer.valueOf(pageNum);
        System.out.println("前端传过来的pageNum-------------" + pageNum);
        if (vipID == 1) {
            List<Map> allGoodsList = goodsService.getGoodsList(pageNum1, pageSize);
            return Result.data(allGoodsList);
        }
        List<Map> goodsList = goodsService.getGoodsList(pageNum1, pageSize, vipID);
        return Result.data(goodsList);
    }


}
