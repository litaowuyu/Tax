package com.controller;

import com.common.exceptionHandle.Result;
import com.service.TransactionService;
import com.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * author:LC
 * version:jdk-8u301
 */
@RestController
@ResponseBody
public class TransactionController {

    @Resource
    private TransactionService transactionService;

    @Resource
    private UserService userService;

    @GetMapping("/Auth/getUserTeam")
    public Map getUserTeam(){
        Map map = userService.userInfo();
        String uni_id = (String) map.get("uni_id");
        System.out.println("uni_id" + uni_id);
        List userTeam = transactionService.queryUserTeam(uni_id);
        return Result.data(userTeam);
    }

    @GetMapping("/Auth/getUserOrder")
    public Map getUserOrder(){
        Map map = userService.userInfo();
        String uni_id = (String) map.get("uni_id");
        List userOrder = transactionService.queryUserOrder(uni_id);
        return Result.data(userOrder);
    }

    @GetMapping("/Auth/getUserWithdraw")
    public Map getUserWithdraw(){
        Map map = userService.userInfo();
        String uni_id = (String) map.get("uni_id");
        List userWithdraw = transactionService.queryUserWithdraw(uni_id);
        return Result.data(userWithdraw);
    }

    @GetMapping("/Auth/getRechargeRecord")
    public Map getRechargeRecord(){
        Map user= userService.userInfo();
        int id= (int) user.get("id");
        List rechargeList = transactionService.queryRechargeRecord(id);
        return Result.data(rechargeList);
    }
}
