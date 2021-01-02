package com.festa;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableEncryptableProperties
public class FestaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FestaApplication.class, args);
    }

}
