package com.festa.common.firebase;

import com.festa.exception.FcmInitException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseInitializer {

    @Value("${firebase.firebaseConfigPath}")
    private String firebaseConfigPath;

    @Value("${firebase.database.url}")
    private String project;

    /**
     * FCM 초기화
     * 어플리케이션 시작 시 한번만 실행된다.
     * @PostConstruct 의존성 주입이 이루어진 후 초기화메서드를 수행하기 위한 어노테이션.
     *                호출을 따로 해주지 않더라도 어플리케이션 시작 시 한번만 실행하도록 보장된다. 파라미터가 존재하면 안된다.
     * setCredentials()에서 키를 이용해 인증한다.
     * GoogleCredentials : OAuth2를 이용해 GoogleApi 호출을 승인하기 위한 객체
     */
    @PostConstruct
    public void firebaseInit() {
        try {
            ClassPathResource serviceAccount = new ClassPathResource(firebaseConfigPath);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setDatabaseUrl(project)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch(IOException e) {
            throw new FcmInitException("FCM 서버를 초기화시키지 못했습니다.");
        }
    }
}
