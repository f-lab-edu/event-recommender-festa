package com.festa.service;

import com.festa.dao.EventDAO;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventDAO eventDAO;

    public List<EventDTO> getListOfEvents(PageInfo pageInfo) {
        return eventDAO.getListOfEvents(pageInfo);
    }

    @Transactional
    public void applyForEvents(Participants participants) throws IllegalAccessException {
        eventDAO.applyForEvents(participants);

        EventDTO participantInfo = eventDAO.checkNoOfParticipants(participants.getEventNo());

        if(participantInfo.getParticipantLimit() == participantInfo.getNoOfParticipants()) {
            throw new IllegalAccessException("이미 선착순 마감된 이벤트 입니다.");
        }

        eventDAO.increaseParticipants(participants.getEventNo());
    }

    @Transactional
    public void cancelEvent(Participants participants) throws IllegalAccessException {
        eventDAO.cancelEvent(participants.getUserNo());

        boolean isParticipated = eventDAO.isParticipated(participants.getUserNo());

        if(!isParticipated) {
            throw new IllegalAccessException("접수한 이벤트가 아닙니다");
        }

        eventDAO.reduceParticipants(participants.getEventNo());
    }
}
