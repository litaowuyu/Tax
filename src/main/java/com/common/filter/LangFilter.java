package com.common.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LangFilter implements Filter {
    //    定义一个数组存放系统可选择的语言类型
    private final static String[] systemLang = {"EN", "RU", "LT"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//      设置请求和响应的字符集编码
        httpServletRequest.setCharacterEncoding("UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
//      判断前端请求头中是否含有多语言的字段lang
        if (httpServletRequest.getHeader("lang") == null) {
            httpServletRequest.setAttribute("app_lang", "EN");
//            执行拦截完之后，放行
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            boolean hasIn = false;
//          匹配携带的多语言字段
            for (String s : systemLang) {
                if (s.equals(httpServletRequest.getHeader("lang"))) {
                    hasIn = true;
//                  将请求头中的Header放入Attribute中，获得多语言
                    httpServletRequest.setAttribute("app_lang", httpServletRequest.getHeader("lang"));
                    break;
                }
            }
            if(!hasIn){
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setHeader("Cache-Control", "no-cache");
                httpServletResponse.setContentType("application/json");
                Map map=new HashMap<>();
                map.put("code",1);
                map.put("message","unsupported language!");
                httpServletResponse.getWriter().println(JSON.toJSONString(map));
                httpServletResponse.getWriter().flush();
            }else {
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }

    }

}
