package com.zjx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zjx.mapper")
public class DataToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataToolsApplication.class, args);
    }

}
