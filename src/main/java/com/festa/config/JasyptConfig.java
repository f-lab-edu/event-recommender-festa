package com.festa.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("KEY"); // 암호화, 복호화에 사용할 키
        config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘 지정
        config.setKeyObtentionIterations("1000"); // 암호화 키를 얻기 위해 적용된 해싱 반복 횟수를 설정한다.
        config.setPoolSize("1"); // 암호키 생성을 위한 pool 크기 지정.
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}
