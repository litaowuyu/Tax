package com.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.exceptionHandle.Result;
import com.common.utils.JWTUtil;
import com.service.UserService;
import com.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * author:LC
 * version:jdk-8u301
 */
@RestController
@ResponseBody
public class WithdrawController {
    @Autowired
    HttpServletRequest request;

    @Resource
    private UserService userService;

    @Resource
    private WithdrawService withdrawService;

    @GetMapping("/Auth/changeUserBalance")
    public Map changeUserBalance(@RequestParam("balance") String balance, @RequestParam("verifyCode") String verifyCode) {
        System.out.println("前端传过来的验证码"+verifyCode);

        String withdrawToken=request.getHeader("withdraw_token");
//        System.out.println(withdrawToken);
        DecodedJWT decodedJWT = JWTUtil.decryptToken(withdrawToken);
        //拿到payload中的信息
        Claim data = decodedJWT.getClaim("data");
        //对payload中的信息进行解码
        Map map1 =data.asMap();
        String  final_verifyCode= (String) map1.get("verifyCode");
        System.out.println("LoginInterceptor中解码的UserID ----------------"+final_verifyCode);

        if (verifyCode.equals(final_verifyCode)) {
            Map map = userService.userInfo();
            //获取用户的id
            Integer id = (Integer) map.get("id");
            String uni_id= (String) map.get("uni_id");
            //获取用户当前账户余额
            long userAccountBal = (long) map.get("balance");
//            long phone= (long) map.get("phone");
            if (userAccountBal >= Integer.parseInt(balance)) {
                //创建一个提现的记录
                int withdraw_money=Integer.parseInt(balance);
                int row=withdrawService.createWithdrawOrder(uni_id,withdraw_money);
                if(row>0){
                    System.out.println("创建订单成功");
                }else{
                    System.out.println("创建订单失败");
                }
                int flag = withdrawService.subUserAccountBal(Integer.parseInt(balance), id);
                System.out.println("更新账户余额的flag"+flag);
                if (flag > 0) {
//                return Result.success();
                    return Result.data(0);
                } else {
//                return Result.error(7, "withdraw_Fail");
                    return Result.data(7);
                }
            } else {
//            return Result.error(7, "withdraw_Fail");
                return Result.data(7);

            }
        }else{
            //8验证码错误
            return Result.data(8);
        }
    }
}
