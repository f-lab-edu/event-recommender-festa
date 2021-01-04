package com.festa.service;

import com.festa.dao.AlertDAO;
import com.festa.dto.AlertDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertDAO alertDAO;

    public AlertDTO sendChangePwNotice(int userNo) {
        return alertDAO.sendChangePwNotice(userNo);
    }

    /**
     * 메세지를 보낸다.
     * 받을 상대의 토큰값, 알림 제목, 메세지를 가지고 있다.
     * @param alertDTO
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void send(AlertDTO alertDTO) {
        try {
            // setToken 혹은 setTopic을 이용해 메세지의 타겟을 정한다.
            Message message = Message.builder()
                    .setToken(alertDTO.getToken())
                    .setWebpushConfig(WebpushConfig.builder().putHeader("HeaderKey", "HeaderValue")
                            .setNotification(new WebpushNotification(alertDTO.getTitle(), alertDTO.getContent()))
                            .build()).build();

            FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (ExecutionException | InterruptedException e) {

        }

    }

    private final Map<Integer, String> tokenMap = new HashMap<Integer, String>();

    /**
     * token을 관리해준다.
     * @param userNo
     * @param token
     */
    public void register(final int userNo, final String token) {
        tokenMap.put(userNo, token);
    }
}
