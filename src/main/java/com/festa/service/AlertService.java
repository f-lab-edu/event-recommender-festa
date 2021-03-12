package com.festa.service;

import com.festa.common.firebase.FirebaseTokenManager;
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
    private final FirebaseTokenManager firebaseTokenManager;

    /**
     * 사용자에게 알람을 전송한다.
     * @param token
     * @param title
     * @param contents
     */
    public void sendMessage(String token, String title, String contents) {
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

                String userToken = firebaseTokenManager.getToken(ConvertDataType.longToString(userNo));
                sendMessage(userToken, "이벤트 시작 알림이 있습니다!", "이벤트가 곧 시작됩니다! 잊지말고 참여해주세요");

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

    public List<AlertResponse> getParticipantsNeedAlert(long eventNo) {
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

            String userToken = firebaseTokenManager.getToken(ConvertDataType.longToString(participantsUserNo));
            sendMessage(userToken, "이벤트 변경알림이 있습니다!", "참여하신 이벤트의 정보가 변경되었습니다");
        });

        return response;
    }

    public AlertResponse changePasswordNotice(long userNo) {
        boolean isUserNeedToModify = memberDAO.getChangePwDateDiff(userNo);

        AlertResponse response= AlertResponse.builder()
                .alertType("sendChangePwToUser")
                .targetNo(userNo)
                .isAlertNeed(isUserNeedToModify)
                .build();

        if(isUserNeedToModify) {
            String userToken = firebaseTokenManager.getToken(ConvertDataType.longToString(userNo));
            sendMessage(userToken, "비밀번호 변경 알림이 있습니다!", "비밀번호를 변경한지 3개월이 지났습니다, 보안을 위해 비밀번호를 변경해주세요");
        }

        return response;
    }
}
