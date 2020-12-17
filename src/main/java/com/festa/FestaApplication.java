package com.festa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy
public class FestaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FestaApplication.class, args);
    }

}
