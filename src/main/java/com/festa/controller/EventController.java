package com.festa.controller;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.dto.EventDTO;
import com.festa.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * 이벤트 상세보기
     *
     * @param eventNo
     * @return EventDTO
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping("/{eventNo}")
    public EventDTO getInfoOfEvent(@PathVariable int eventNo) {
        EventDTO infoOfEvent = eventService.getInfoOfEvent(eventNo);
        if (infoOfEvent == null) {
            throw new IllegalArgumentException("존재하지 않는 이벤트입니다.");
        }
        return infoOfEvent;
    }
}
