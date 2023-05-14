package com.common.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.exceptionHandle.iException;
import com.common.utils.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录拦截器
 * 先判断用户登录时是否带有token
 * 若token为空或者不一样就返错误
 * 若token一致则对token中payload进行解密，拿到存放的userID
 * */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getHeader("accessToken") == null) {
            throw new iException(5, "TOKEN_ERROR");
        }
        String token = request.getHeader("accessToken");
        if (token.equals("")) {
            throw new iException(5, "TOKEN_ERROR");
        }
        //核对token是否正确
        DecodedJWT decodedJWT = JWTUtil.decryptToken(token);
        //拿到payload中的信息
        Claim data = decodedJWT.getClaim("data");
        //对payload中的信息进行解码
        Map map =data.asMap();
        int userID= (int) map.get("userID");
        System.out.println("LoginInterceptor中解码的UserID ----------------"+userID);
        //将payload中数据放入内存供调用
        request.setAttribute("userID",userID);
        return true;
    }
}
