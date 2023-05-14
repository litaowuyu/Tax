package com.controller;

import com.common.exceptionHandle.Result;
import com.common.utils.JWTUtil;
import com.common.utils.SendSMS;
import com.service.UserService;
import com.service.VipLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UntilController {
    @Autowired
    private UserService userService;
    @Autowired
    SendSMS sendSMS;
    @Autowired
    HttpServletRequest request;



    //打印日志
    private static final Logger logger = LoggerFactory.getLogger(UntilController.class);

//    @RequestMapping("/lang/setLang")
//    public void setLang(@RequestParam(value = "lang") String lang, HttpSession httpSession) {
//        request.setAttribute("app_lang", lang);
//        System.out.println(lang);
//    }

    //通过手机号获取验证码
    @GetMapping("/noAuth/utils/getVerifyCode")
    public Map getVerify_Code(@RequestParam("phone") String phone) {
        /**
         * 生成验证码
         * Math.random()生成一个[0.0,1.0)之间的一个随机数
         * 验证码的时效性只有六十秒
         **/
        int VerifyCode = (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println("生成的VerifyCode" + VerifyCode);
        String finalVerifyCode = String.valueOf(VerifyCode);
        System.out.println("转换成String类型的finalVerifyCode" + finalVerifyCode);
        //在token的payload中存放验证码
        Map payloadData = new HashMap<>();
        payloadData.put("phone", phone);
        payloadData.put("verifyCode", finalVerifyCode);
        request.setAttribute("verifyCode",finalVerifyCode);
        System.out.println(request.getAttribute("verifyCode"));
        /**
         * 设置token的过期时间
         *  Date expTime = new Date(new Date().getTime() + (1000 * 60 * 30))
         * 等价于
         * Date expTime=new Date();
         * expTime= new Date().getTime() + (1000 * 60 * 30);
         * */
        Date expTime = new Date(new Date().getTime() + (1000 * 60 * 30));
        //生成token
        String token = JWTUtil.getToken(expTime, payloadData);
        //打印验证码
        logger.info(finalVerifyCode);

        return true ? Result.data(token) : Result.error(1, "SMS_SEND_FAIL");


/**
 //        //获取当前的时间，单位为毫秒，1min=1000ms
 //       // long nowTime = new Date().getTime();
 //        //如果session中存放的有发送验证码的时间
 //        if (request.getAttribute("send_message_time") != null) {
 //            long lastTime = (long) request.getAttribute("send_message_time");
 //            if ((nowTime - lastTime) < (60 * 1000)) {
 //                System.out.println("b");
 //                return Result.error(1, "REQUEST_FREQUENT");
 //            }
 //            System.out.println("a");
 //        }
 * 实现原理
 * Math.random()生成一个[0.0,1.0)之间的一个随机数
 * 验证码的时效性只有六十秒

 //        String identify_code = String.valueOf(((Math.random() * 9 + 1) * 100000));
 //        request.setAttribute("send_message_time", nowTime);
 //        request.setAttribute("send_identify_code", identify_code);
 //        return Result.data(identify_code);
 **/
    }

    //用户注册
    @PostMapping("/register")
    public Map register(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password, @RequestParam("identify_code") String identify_code) {
        /**
         * trim()删除字符串首尾的空白符
         * 判断用户的输入账号或密码是否为空
         * 如果为空返回错误ACCOUNT_BLANK
         * **/
        if (phoneNumber.trim().equals("") || password.trim().equals("")) {
            System.out.println("11111111111111");
            return Result.error(1, "ACCOUNT_BLANK");
        }
        /**
         * 判断session中验证码是否为空
         * 判断是否给用户发送验证码
         * **/
        if (request.getAttribute("send_identify_code") == null) {
            return Result.error(1, "IDENTIFY_CODE_BLANK");
        }
        /**
         * 判断用户输入的验证码和session中存放的验证码是否一致
         * 通过拿map的键得到验证码然后进行匹配
         * **/
        if (!identify_code.equals(String.valueOf(request.getAttribute("send_identify_code")))) {

            return Result.error(1, "IDENTIFY_CODE_ERROR");
        }
        return userService.register(phoneNumber, password) == true ? Result.success() : Result.error(1, "REGISTER_ERROR");
    }


}
