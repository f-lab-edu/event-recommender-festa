package com.festa.service;

import com.festa.common.util.ConvertDataType;
import com.festa.dao.EventDAO;
import com.festa.dao.MemberDAO;
import com.festa.dto.EventDTO;
import com.festa.model.AlertResponse;
import com.festa.model.Participants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final MemberDAO memberDAO;
    private final EventDAO eventDAO;

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

    public List<AlertResponse> eventStartNotice(long userNo, LocalDate todayDate) {

        List<AlertResponse> response = new LinkedList<>();
        List<Long> appliedEvents = eventDAO.getAppliedEvent(userNo);

        appliedEvents.forEach(eventNo -> {
            EventDTO eventInfo = eventDAO.getInfoOfEvent(eventNo);

            if(ConvertDataType.dateFormatter(todayDate).equals(eventInfo.getStartDate())) {
                AlertResponse sendAlert = AlertResponse.builder()
                        .alertType("eventStartAlert")
                        .targetNo(eventInfo.getEventNo())
                        .isAlertNeed(true)
                        .build();

                response.add(sendAlert);

            } else {
                AlertResponse notSendAlert = AlertResponse.builder()
                        .alertType("eventStartAlert")
                        .targetNo(eventInfo.getEventNo())
                        .isAlertNeed(false)
                        .build();

                response.add(notSendAlert);
            }
        });

        return response;
    }

    public List<AlertResponse> eventModifyNotice(long eventNo) {
        List<AlertResponse> response = new LinkedList<>();
        List<Participants> participants = eventDAO.getParticipantList(eventNo);

        Stream<Long> userNo = participants.stream().map(Participants::getUserNo);

        userNo.forEach(participantsUserNo -> {
            AlertResponse sendAlert = AlertResponse.builder()
                    .alertType("eventModifyAlertToParticipants")
                    .targetNo(participantsUserNo)
                    .isAlertNeed(true)
                    .build();

            response.add(sendAlert);
        });

        return response;
    }

    public AlertResponse changePasswordNotice(long userNo) {

        return AlertResponse.builder()
                .alertType("sendChangePwToUser")
                .targetNo(userNo)
                .isAlertNeed(memberDAO.getChangePwDateDiff(userNo))
                .build();
    }
}
