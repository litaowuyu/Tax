package com.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//拦截器配置，让拦截器生效
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private CorsInterceptor corsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //跨域拦截器放在最上边
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        //核验token是否正确,拦截的路径是注意大小的
        registry.addInterceptor(loginInterceptor).addPathPatterns("/Auth/**");
    }
}
