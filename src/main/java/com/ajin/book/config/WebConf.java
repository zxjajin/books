//package com.ajin.book.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * @author ajin
// * @create 2023-10-15 2:30
// */
//@Configuration
//public class WebConf extends WebMvcConfigurationSupport {
//
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //映射static路径的请求到static目录下
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("doc.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }
//}
