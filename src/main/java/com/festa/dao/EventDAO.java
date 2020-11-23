package com.festa.dao;

import com.festa.dto.EventDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDAO {

    List<EventDTO> getListOfEvents();
}
