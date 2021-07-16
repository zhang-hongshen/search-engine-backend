package com.zhanghongshen.soo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Zhang Hongshen
 * @description 跨域问题解决
 * @date 2021/5/25
 */
@Configuration
public class CrosConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")//设置允许来自浏览器的跨域请求的来源
                .allowedMethods("GET","POST","HEAD","PUT","DELETE","OPTIONS")//设置允许的HTTP方法
                .allowCredentials(false)
                .allowedHeaders("*");
    }
}
