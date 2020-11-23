package com.festa.controller;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.dto.EventDTO;
import com.festa.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_NO_CONTENT;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * 리스트형 이벤트 목록 조회 기능
     * @return {@literal List<EventDTO>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping("/lists")
    public ResponseEntity<?> getListOfEvents() {
        List<EventDTO> eventLists = eventService.getListOfEvents();

        if(eventLists != null) {
            return ResponseEntity.ok(eventLists);
        }

        return RESPONSE_ENTITY_NO_CONTENT;
    }
}
