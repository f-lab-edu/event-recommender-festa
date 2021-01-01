package com.festa.controller;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_BAD_REQUEST;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_CONFLICT;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.common.commonService.CurrentLoginUserNo;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import com.festa.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
     * 이벤트 상세보기
     * @param eventNo
     * @return EventDTO
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping("/{eventNo}")
    public ResponseEntity<EventDTO> getInfoOfEvent(@PathVariable int eventNo) {
        EventDTO infoOfEvent = eventService.getInfoOfEvent(eventNo);

        return ResponseEntity.ok(infoOfEvent);
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

        EventDTO eventInfo = eventDTO.toEntityForInfo();
        eventService.registerEvents(eventInfo);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 주최자 이벤트 참여자 목록 조회 기능
     * @param participants
     * @return {@literal ResponseEntity<HttpStatus>}
     * @throws NoSuchElementException (조회된 데이터가 없을 경우)
     */
    @CheckLoginStatus(auth = UserLevel.HOST)
    @GetMapping("/{eventNo}/participants")
    public ResponseEntity<HttpStatus> getParticipantList(@CurrentLoginUserNo long userNo, @RequestBody Participants participants) {
        Participants participantsList = eventService.getParticipantList(userNo, participants);

        if(participantsList == null) {
            throw new NoSuchElementException("현재 참여자가 없습니다.");
        }

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 주최자 이벤트 수정 기능
     * @param eventDTO
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.HOST)
    @PutMapping("/{eventNo}")
    public ResponseEntity<HttpStatus> modifyEventsInfo(@RequestBody EventDTO eventDTO) {
        eventService.modifyEventsInfo(eventDTO);

        return RESPONSE_ENTITY_OK;
    }
}
