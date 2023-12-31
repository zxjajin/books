package com.ajin.book;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sun.rmi.runtime.Log;


// Generated by https://start.springboot.io
// 优质的 spring/boot/data/security/cloud 框架中文文档尽在 => https://springdoc.cn
@SpringBootApplication
@MapperScan("com.ajin.book.mapper")
public class BookApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BookApplication.class, args);
        System.out.println(11);
    }

}
