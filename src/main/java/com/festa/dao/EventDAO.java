package com.festa.dao;

import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDAO {

    EventDTO getInfoOfEvent(int eventNo);

    List<EventDTO> getListOfEvents(PageInfo pageInfo);

    void applyForEvents(Participants participants);

    void cancelEvent(long userNo);

    void increaseParticipants(int eventNo);

    void reduceParticipants(int eventNo);

    EventDTO checkNoOfParticipants(int eventNo);

    boolean isParticipated(long userNo);

}
