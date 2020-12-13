package com.festa.service;

import com.festa.dao.EventDAO;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventDAO eventDAO;

    public List<EventDTO> getListOfEvents(PageInfo pageInfo) {
        return eventDAO.getListOfEvents(pageInfo);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void applyForEvents(Participants participants) {
        eventDAO.applyForEvents(participants);

        EventDTO participantInfo = eventDAO.checkNoOfParticipants(participants.getEventNo());

        if(participantInfo.getParticipantLimit() == participantInfo.getNoOfParticipants()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "선착순 마감완료 한 이벤트 입니다.");
        }

        eventDAO.increaseParticipants(participants.getEventNo());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void cancelEvent(Participants participants) {
        eventDAO.cancelEvent(participants.getUserNo());

        boolean isParticipated = eventDAO.isParticipated(participants.getUserNo());

        if(!isParticipated) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "참여한 이벤트가 아닙니다.");
        }

        eventDAO.reduceParticipants(participants.getEventNo());
    }
}
