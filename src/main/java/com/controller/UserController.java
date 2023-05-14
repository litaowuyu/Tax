package com.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.exceptionHandle.Result;
import com.common.utils.PasswordUtil;
import com.service.UserService;
import com.common.utils.JWTUtil;
import com.common.utils.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 注册时候需要选择区号、填入手机号，验证码，以及密码
     * 用户输入之后判断token是否一致，一致则注册成功
     */

    @PostMapping("/noAuth/user/register")
    public Map register(
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("verifyCode") String verifyCode,
            @RequestParam("areaCode") String areaCode,
            @RequestParam("accessToken") String accessToken) {

        if (phone.trim().equals("") || password.trim().equals("") || verifyCode.trim().equals("") || accessToken.trim().equals("") || areaCode.trim().equals("")) {
            return Result.error(1, "NO_PARAMETER");
        }
        DecodedJWT decodedJWT = JWTUtil.decryptToken(accessToken);
        Claim claim = decodedJWT.getClaim("data");
        //将存入token中的数据读取出来，转换为Map集合
        Map payloadData = claim.asMap();
        /**
         *  判断用户注册的手机号和发送验证码的手机号是否一致
         * 获取token中携带的phone与输入框中获取的phone进行匹配
         */
        System.out.println("token中的验证码为：" + payloadData.get("verifyCode"));
        if (!payloadData.get("phone").equals(phone)) {
            return Result.error(1, "REGISTER_ERROR");

        }
        if (!payloadData.get("verifyCode").equals(verifyCode)) {

            return Result.error(1, "REGISTER_ERROR");
        }
        String finalPhone = areaCode + phone;
        return userService.register(finalPhone, password) == true ? Result.success() : Result.error(1, "REGISTER_ERROR");
    }


    @PostMapping("/noAuth/login")
    public Map login(@RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("areaCode") String areaCode) {
        if (phone.trim().equals("") || password.trim().equals("") || areaCode.trim().equals("")) {
            return Result.error(1, "NO_PARAMETER");
        }

        String finalPhone = areaCode + phone;
        System.out.println("登录的手机号"+ finalPhone);
        int userID = userService.login(finalPhone, password);
        System.out.println(userID);
        if (userID > 0) {
            Map payloadData= new HashMap();
            payloadData.put("userID",userID);
            System.out.println("UserController中存放的UseID------------"+userID);
            return Result.data(JWTUtil.getToken(new Date((new Date().getTime() + (1000 * 60 * 30))),payloadData));
        }
        return Result.error(44, "LOGIN_FAIL");
    }


    @GetMapping("/lang")
    public String register(HttpServletRequest request) throws IOException {
        System.out.println(request.getAttribute("app_lang"));
        return LangUtil.getMessage("UNKNOWN_ERROR");
    }

    @GetMapping("Auth/me/userInfo")

    public Map getUserInfo() {
        Map user = userService.userInfo();
        user.remove("password");
        return Result.data(user);
    }

    @GetMapping("/Auth/getLv1Member")
    public Map getLv1Member(){
        Map map = userService.userInfo();
        Object uni_id = map.get("uni_id");
        List list = userService.loadMember((String) uni_id);
        return Result.data(list);
    }

    @GetMapping("/Auth/getLv2Member")
    public Map getLv2Member(String[] phoneArr){
        //System.out.println(Arrays.toString(phoneArr));
        ArrayList<Object> objectList = new ArrayList<>();
        for (String str : phoneArr) {
            List list = userService.loadLv2Member(str);
            for(int i = 0; i < list.size(); i++){
                //将用户信息中的密码去除
                Map m = (Map) list.get(i);
                m.remove("password");
                objectList.add(m);
            }
        }
        return Result.data(objectList);
    }
    @PostMapping("noAuth/registerVIP")
    public Map registerVIP(@RequestParam("price") int price,
                           @RequestParam("phoneNumber") String phoneNumber){
        Map<String,String>statusCode = new HashMap();
        if (userService.registerVIP(price, phoneNumber)){
            statusCode.put("statusCode","1");
            return Result.data(statusCode);
        }
        statusCode.put("statusCode","-1");
        return Result.data(statusCode);
    }
    @GetMapping("/Auth/resetPassword")
    public Map resetPassword(@RequestParam("password") String password ,@RequestParam("verifyCode") String verifyCode) {
        System.out.println("前端传过来的验证码"+verifyCode);

        String tempToken=request.getHeader("token");

        DecodedJWT decodedJWT = JWTUtil.decryptToken(tempToken);
        System.out.println(decodedJWT);
//        if(){
//
//        }
        //拿到payload中的信息
        Claim data = decodedJWT.getClaim("data");
        //对payload中的信息进行解码
        Map map1 =data.asMap();
        String  final_verifyCode= (String) map1.get("verifyCode");

        System.out.println("后端获取的验证码"+final_verifyCode);



        //后端获取验证码为null
        if (verifyCode.equals(final_verifyCode)) {

            Map userInfo = userService.userInfo();
            String phone = (String) userInfo.get("phone");

            System.out.println("phone" + phone + " password:" + password);
            String enPassword = PasswordUtil.encode(password);
            int i = userService.resetUserPassword(phone, enPassword);
            if (i > 0) {
                return Result.data("reset password success");
            } else {
                return Result.error(1, "reset password fail");
            }
        }
          return Result.error(1, "reset password fail");
    }
    @PostMapping("/Auth/cancelVIP")
    public Map cancelVIP(){
        Map map = userService.userInfo();
        String uni_id = (String) map.get("uni_id");
        System.out.println("当前登录用户uni_id：" + uni_id);
        int i = userService.cancelUserVIPLevel(uni_id);
        if(i > 0){
            return Result.success();
        }
        return Result.error(10, "cancel VIP fail");
    }
}
