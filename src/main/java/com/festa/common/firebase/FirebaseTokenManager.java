package com.festa.common.firebase;

import com.festa.exception.FcmTokenException;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class FirebaseTokenManager {

    @Value("${firebase.firebaseConfigPath}")
    private String firebaseConfigPath;

    private RedisTemplate<String, String> redisTemplate;

    /**
     * 토큰을 레디스에 저장하는 메서드
     * 토큰을 userNo와 함께 세션과 함께 저장하기에는 토큰의 주기가 다르기때문에 별도로 저장
     * @param userNo
     * @param token
     */
    public void register(String userNo, String token) {
        redisTemplate.opsForValue().set(userNo, token);
    }

    /**
     * userNo를 이용해 토큰을 얻어오는 메서드
     * @param userNo
     * @return
     */
    public String getToken(String userNo) {
        return redisTemplate.opsForValue().get(userNo);
    }

    /**
     * 로그아웃시 토큰 삭제하는 메서드
     * @param userNo
     */
    public void removeToken(String userNo) {
        redisTemplate.delete(userNo);
    }

    /**
     * 접근을 위한 Token을 얻어온 후 Map에 저장하는 메서드
     */
    public void makeAccessToken(long userNo) {

        GoogleCredentials googleCredentials = null;

        try {
            googleCredentials = GoogleCredentials
                    .fromStream(new FileInputStream(firebaseConfigPath))
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.remoteconfig"));


            googleCredentials.refreshIfExpired();
        } catch (IOException e) {
            throw new FcmTokenException("Token 생성에 실패하였습니다.");
        }
        String token = googleCredentials.getAccessToken().getTokenValue();

        register(String.valueOf(userNo), token);
    }
}
