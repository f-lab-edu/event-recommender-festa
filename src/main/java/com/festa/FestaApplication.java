package com.festa;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FestaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FestaApplication.class, args);
//        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
//        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
//        standardPBEStringEncryptor.setPassword("KEY");
//        String encodedPass = standardPBEStringEncryptor.encrypt("festa");
//        System.out.println("Encrypted Password for admin is : "+encodedPass);
    }

}
