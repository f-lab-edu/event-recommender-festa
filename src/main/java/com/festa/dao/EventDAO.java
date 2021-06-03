package com.festa.dao;

import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EventDAO {

    List<EventDTO> getListOfEvents(PageInfo pageInfo, int categoryCode);

    List<Long> getAppliedEvent(long userNo);

    EventDTO getInfoOfEvent(long eventNo);

    void applyForEvents(Participants participants);

    void insertParticipantAddress(Participants participants);

    void registerEvents(EventDTO eventDTO);

    void registerEventsAddress(EventDTO eventDTO);

    void modifyEventsInfo(EventDTO eventDTO);

    void modifyEventsAddress(EventDTO eventDTO);

    boolean isEventExists(String eventTitle, String startDate);

    void cancelEvent(Participants participants);

    void increaseParticipants(long eventNo);

    void reduceParticipants(@Param("eventNo") long eventNo);

    EventDTO checkNoOfParticipants(long eventNo);

    boolean isParticipated(Participants participants);

    List<Participants> getParticipantList(long eventNo);

    void deleteEvent(long eventNo);

    void deleteEventAddress(long eventNo);
}
