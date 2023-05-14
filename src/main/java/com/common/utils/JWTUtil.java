package com.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.exceptionHandle.iException;

import java.util.Date;
import java.util.Map;

public class JWTUtil {
    //签名中使用的密钥
    private final static String TOKEN_SECRET = "root";
    /**
     * 生成一个token，导入依赖之后会生成一个JWT的类
     * token由三部分组成：header.payload.signature
     * header用来存放头类信息，编码方式为base64（），header一般只存放一个加密方式和一个类型，默认值为下：
     * .header{
     *      "alg":"HS256",
     *     "typ":"JWT"
     * }
     * payload用于存放数据信息，才用base64（)编码方式，payload中存放领个部分不要存放两个部分，一个是withClaim（），
     * 另一个是withExpiresAt（），用户的敏感信息如：密码
     *.payload{
     *
     * }
     * signature是一个签名，是对前边的header和payload的组合，采用HAMC256加密（不可逆加密），
     * 其中包含了一个密钥，这个密钥是私钥，只能用户自己知道，千万不能泄露
     * {
     *     header
     *     payload
     *     secretKey
     * }
     * */
    public static String getToken(Date expTime, Map data ) {

        JWTCreator.Builder jwt=JWT.create();
        //将需要存储的数据放置在Map集合中
        jwt.withClaim("data",data);
        //设置过期时间
        jwt.withExpiresAt(expTime);
        //设置密钥

        String token= jwt.sign(Algorithm.HMAC256(TOKEN_SECRET));
        return token;
/*        Date expTime = new Date((new Date().getTime() + 1000 * 60 * 30));
        String  token= JWT
//                构造JWT,生成一个JWT，也就是token令牌
                .create()
//                设置header
                .withHeader(new HashMap<>())
//                设置payload的过期时间
                .withExpiresAt(expTime)
//                设置payload中存放的数据
                .withClaim("userID",userID)
//                设置secret（密钥）
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
        return token;

 */
    }
    //判断token中携带的密钥是否和设置的一致
    public static DecodedJWT decryptToken(String token){
        DecodedJWT decodedJWT;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            decodedJWT = jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new iException(5, "TOKEN_ERROR");

        }
        return decodedJWT;
    }
}
