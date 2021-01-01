package com.festa.dao;

import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EventDAO {

    List<EventDTO> getListOfEvents(PageInfo pageInfo, int categoryCode);

    EventDTO getInfoOfEvent(int eventNo);

    void applyForEvents(Participants participants);

    void insertParticipantAddress(Participants participants);

    void registerEvents(EventDTO eventDTO);

    void modifyEventsInfo(EventDTO eventDTO);

    void modifyEventsAddress(EventDTO eventDTO);

    boolean isEventExists(String eventTitle, String startDate);

    void cancelEvent(long userNo);

    void increaseParticipants(int eventNo);

    void reduceParticipants(int eventNo);

    EventDTO checkNoOfParticipants(int eventNo);

    boolean isParticipated(long userNo);

    Participants getParticipantList(Participants participants);

}
