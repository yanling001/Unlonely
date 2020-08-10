package com.hongyaoz.unlonelyupmsclient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
@MapperScan("com.hongyaoz.unlonelyupmsclient.dao")
public class UnlonelyUpmsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnlonelyUpmsClientApplication.class, args);
    }

}
