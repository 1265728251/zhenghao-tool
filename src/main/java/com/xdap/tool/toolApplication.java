package com.xdap.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.definesys.mpaas", "com.xdap.*"})
public class toolApplication {

    public static void main(String[] args) {
        SpringApplication.run(toolApplication.class, args);
    }

}
