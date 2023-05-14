package com.controller;


import com.common.exceptionHandle.Result;
import com.service.PaymentService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserService userService;

    @GetMapping("noAuth/getPayment")
    public Map getPayment() {
        return Result.data(paymentService.getPayment());
    }

    @PostMapping("Auth/recharge")
    public Map recharge(@RequestParam("payment_id") int payment_id,
                        @RequestParam("payment_type") String payment_type,
                        @RequestParam("money") int money) {
        Map map = new HashMap();
        map.put("payment_id", payment_id);
        map.put("payment_type", payment_type);
        map.put("money", money);
        map.put("user_id", userService.getCurrentUser().get("id"));
        map.put("create_time",new Date().getTime());
        map.put("uni_id",userService.getCurrentUser().get("uni_id"));

        paymentService.addUserBalance(map);
       return  paymentService.recharge(map) ? Result.success():Result.error(1,"RECHARGE_FAIL");
    }
    @GetMapping("/Auth/getUserAccount")
    public Map getUserAccount(){
        //获取用户信息
       Map user= userService.userInfo();
        return Result.data(paymentService.getUserAccount((String) user.get("uni_id")));
    }
    @PostMapping("/Auth/deleteAccount")
    public Map deleteAccount(@RequestParam("id") int id){
        if(paymentService.deleteAccount(id)>0){
            return Result.success() ;
        }
        else {
            return Result.error(1,"Delete Fail");
        }
    }
    @PostMapping("Auth/addAccount")
    public Map addAccount(
            @RequestParam("default_account") String default_account,
            @RequestParam("address")String address){
        Map user=userService.userInfo();
        String uni_id= (String) user.get("uni_id");
        if(  paymentService.addAccount(uni_id,default_account,address)>0){
            return Result.success();
        }
        else {
            return Result.error(1,"Failed to add accounts");
        }
    }


}


