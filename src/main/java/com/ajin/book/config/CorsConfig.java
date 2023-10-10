package com.ajin.book.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ajin
 * @create 2023-06-03 19:01
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    //开启跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路由
       registry.addMapping("/**")
               //设置允许跨域请求的域名
//               .allowedOrigins("*")
               .allowedOriginPatterns("*")
               //设置允许的方法
               .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
               //是否允许证书
               .allowCredentials(true)
               //跨域允许时间
               .maxAge(3600)
               .allowedHeaders("*");
    }
}
