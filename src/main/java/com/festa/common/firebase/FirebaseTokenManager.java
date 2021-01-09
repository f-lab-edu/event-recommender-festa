package com.festa.common.firebase;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class FirebaseTokenManager {
    @Value("${firebase.firebaseConfigPath}")
    private String firebaseConfigPath;

    // 토큰을 저장해둘 곳
    private Map<Long, String> tokenMap = new HashMap<>();

    /**
     * 토큰을 HashMap에 저장하는 메서드
     * @param userNo
     * @param token
     */
    public void register(long userNo, String token) {
        tokenMap.put(userNo, token);
    }

    /**
     * userNo를 이용해 Map에서 토큰을 얻어오는 메서드
     * @param userNo
     * @return
     */
    public String getToken(long userNo) {
        return tokenMap.get(userNo);
    }

    /**
     * 로그아웃 시 토큰 삭제하는 메서드
     * @param userNo
     */
    public void removeToken(long userNo) {
        tokenMap.remove(userNo);
    }

    /**
     * 접근을 위한 Token을 얻어온 후 Map에 저장하는 메서드
     */
    public void makeAccessToken(long userNo) {
        GoogleCredential googleCredential = null;
        try {
            googleCredential = GoogleCredential
                    .fromStream(new FileInputStream(firebaseConfigPath))
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.remoteconfig"));
            googleCredential.refreshToken();
        } catch (IOException e) {

        }
        String token = googleCredential.getAccessToken();

        register(userNo, token);
    }
}
