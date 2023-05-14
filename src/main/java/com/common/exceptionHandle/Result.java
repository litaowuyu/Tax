package com.common.exceptionHandle;

import com.common.utils.LangUtil;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private  static int code = 0;
    private static String message = "success";
    private Object data = null;

    public static Map success() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code",code);
        resultMap.put("message",message);
        return resultMap;
    }
    public static Map error(int code,String key)  {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code",code);
        resultMap.put("message", LangUtil.getMessage(key));
        return resultMap;
    }
    public static Map data(Object  object){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code",code);
        resultMap.put("message",message);
        //往前端返回的数据
        resultMap.put("data",object);
        return resultMap;
    }


    public static Map message(int code, String message) {
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("code",code);
        messageMap.put("message",message);

        return messageMap;
    }
}
