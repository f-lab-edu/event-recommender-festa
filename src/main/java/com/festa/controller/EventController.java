package com.festa.controller;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import com.festa.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_BAD_REQUEST;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_CONFLICT;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * 리스트형 이벤트 목록 조회 기능
     * @return {@literal ResponseEntity<List<EventDTO>>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping
    public Optional<List<EventDTO>> getListOfEvents(long cursorUserNo, int size, int categoryCode) {

        return Optional.ofNullable(eventService.getListOfEvents(PageInfo.paging(cursorUserNo, size), categoryCode));
    }

    /**
     * 이벤트 접수 기능
     * @param participants
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PostMapping("/{eventNo}/applies")
    public ResponseEntity<HttpStatus> applyForEvents(@RequestBody Participants participants) {
        try {
            eventService.applyForEvents(participants);

        } catch (IllegalStateException e) {
            return RESPONSE_ENTITY_BAD_REQUEST;
        }

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 접수한 이벤트 취소 기능
     * @param participants
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PatchMapping("/cancel")
    public ResponseEntity<HttpStatus> cancelEvent(@RequestBody Participants participants) {
        try {
            eventService.cancelEvent(participants);

        } catch (IllegalStateException e) {
            return RESPONSE_ENTITY_BAD_REQUEST;
        }

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 주최자 이벤트 등록 기능
     * @param eventDTO
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.HOST)
    @PostMapping
    public ResponseEntity<HttpStatus> registerEvents(@RequestBody EventDTO eventDTO) {
        boolean isEventExists = eventService.isEventExists(eventDTO.getEventTitle(), eventDTO.getStartDate());

        if(isEventExists) {
            return RESPONSE_ENTITY_CONFLICT;
        }

        EventDTO eventInfo = eventDTO.toEntityForRegister();
        eventService.registerEvents(eventInfo);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 이벤트 상세보기
     *
     * @param eventNo
     * @return EventDTO
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping("/{eventNo}")
    public ResponseEntity<EventDTO> getInfoOfEvent(@PathVariable int eventNo) {
        EventDTO infoOfEvent = eventService.getInfoOfEvent(eventNo);

        if (infoOfEvent == null) {
            return ResponseEntity.badRequest().body(infoOfEvent);
        }
        return ResponseEntity.ok(infoOfEvent);
    }
}
