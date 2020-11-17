package com.fehead.Iot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启声明式事务
@MapperScan("com.fehead.Iot.mapper")
public class IotMain8239 {
    public static void main( String[] args )
    {
        SpringApplication.run(IotMain8239.class,args);
    }
}
