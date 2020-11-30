package com.festa.dao;

import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDAO {

    List<EventDTO> getListOfEvents(PageInfo pageInfo);

    void applyForEvents(EventDTO eventDTO);
}
