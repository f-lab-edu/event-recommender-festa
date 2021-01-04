package com.festa.common.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitializer {
    private String firebaseConfigPath = "firebase/festa-42bbe-firebase-adminsdk-h4kbg-5b386e249d.json";

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
            FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://festa-42bbe.firebaseio.com")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch(IOException e) {

        }
    }

    public Firestore getFirebase() {
        return FirestoreClient.getFirestore();

    }
}
