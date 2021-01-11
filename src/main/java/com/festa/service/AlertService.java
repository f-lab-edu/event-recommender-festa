package com.festa.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AlertService {

    /**
     * 사용자에게 알람을 전송한다.
     * @param userNo
     * @param token
     * @param title
     * @param contents
     */
    public void send(long userNo, String token, String title, String contents) {
        // setToken 혹은 setTopic을 이용해 메세지의 타겟을 정한다.
        Message message = Message.builder()
                .setToken(token)
                .setWebpushConfig(WebpushConfig.builder().putHeader("HeaderKey", "HeaderValue")
                        .setNotification(new WebpushNotification(title, contents))
                        .build()).build();

        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalStateException("알림 전송에 실패하였습니다.");
        }

    }

}
