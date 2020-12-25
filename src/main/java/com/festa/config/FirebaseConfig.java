package com.festa.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Primary
    @Bean
    public void firebaseInit() throws IOException {

//        FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://festa-42bbe.firebaseio.com")
//                .build();
//
//        FirebaseApp.initializeApp(options);
    }
}
