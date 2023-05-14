package com.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordUtil {
    //密码加密
    public static String encode(String content) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeContent = encoder.encodeToString(content.getBytes(StandardCharsets.UTF_8));
        return encodeContent;
    }

    //密码解密
    public static String decode(String content){
        Base64.Decoder decoder=Base64.getDecoder();
        String decodeContent =null;
        try {
            decodeContent =new String(decoder.decode(content),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeContent;
    }
}
