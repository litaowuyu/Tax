package com.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class LangUtil {

    //  通过拿取字典中的键来翻译成值
    public static String getMessage(String key)  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Properties properties = new Properties();
        //配置文件
        String fileName ="lang/message_"+request.getAttribute("app_lang")+".properties";
        //字符流加载配置文件
        InputStreamReader inputStreamReader=new InputStreamReader(LangUtil.class.getClassLoader().getResourceAsStream(fileName));
        try {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        return properties.getProperty(key,properties.getProperty("UNKNOWN_ERROR"));
    }
}
