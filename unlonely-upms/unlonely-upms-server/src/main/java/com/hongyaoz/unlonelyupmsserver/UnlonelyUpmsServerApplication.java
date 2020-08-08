package com.hongyaoz.unlonelyupmsserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hongyaoz.unlonelyupmsserver.dao")
public class UnlonelyUpmsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnlonelyUpmsServerApplication.class, args);
    }

}
