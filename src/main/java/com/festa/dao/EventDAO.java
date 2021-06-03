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

    List<EventDTO> getListOfEvents(@Param("pageInfo") PageInfo pageInfo, @Param("categoryCode") int categoryCode);

    List<Long> getAppliedEvent(@Param("userNo") long userNo);

    EventDTO getInfoOfEvent(@Param("eventNo") long eventNo);

    void applyForEvents(Participants participants);

    void insertParticipantAddress(Participants participants);

    void registerEvents(EventDTO eventDTO);

    void registerEventsAddress(EventDTO eventDTO);

    void modifyEventsInfo(EventDTO eventDTO);

    void modifyEventsAddress(EventDTO eventDTO);

    boolean isEventExists(@Param("eventTitle") String eventTitle, @Param("startDate") String startDate);

    void cancelEvent(Participants participants);

    void increaseParticipants(@Param("eventNo") long eventNo);

    void reduceParticipants(@Param("eventNo") long eventNo);

    EventDTO checkNoOfParticipants(@Param("eventNo") long eventNo);

    boolean isParticipated(Participants participants);

    List<Participants> getParticipantList(@Param("eventNo") long eventNo);

    void deleteEvent(@Param("eventNo") long eventNo);

    void deleteEventAddress(@Param("eventNo") long eventNo);
}
