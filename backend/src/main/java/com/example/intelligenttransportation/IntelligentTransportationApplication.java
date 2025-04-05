package com.example.intelligenttransportation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.intelligenttransportation.mapper")
public class IntelligentTransportationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentTransportationApplication.class, args);
    }

} 