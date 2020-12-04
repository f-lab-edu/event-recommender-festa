package com.festa.service;

import com.festa.dao.EventDAO;
import com.festa.dto.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventDAO eventDAO;

    public EventDTO getInfoOfEvent(int eventNo) {
        EventDTO infoOfEvent = eventDAO.getInfoOfEvent(eventNo);

        return infoOfEvent;
    }
}