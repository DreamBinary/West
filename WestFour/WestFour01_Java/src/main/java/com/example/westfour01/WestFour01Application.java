package com.example.westfour01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author 16477
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.example.westfour01.dao")
public class WestFour01Application {

    public static void main(String[] args) {
        SpringApplication.run(WestFour01Application.class, args);
    }

    //6

}
