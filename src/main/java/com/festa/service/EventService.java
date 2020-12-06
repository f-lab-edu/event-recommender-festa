package com.festa.service;

import com.festa.dao.EventDAO;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventDAO eventDAO;

    public List<EventDTO> getListOfEvents(PageInfo pageInfo) {

        return eventDAO.getListOfEvents(pageInfo);
    }

    public void applyForEvents(Participants participants) {

        eventDAO.applyForEvents(participants);
    }

    public void cancelEvent(long userNo) {

        eventDAO.cancelEvent(userNo);
    }
}
