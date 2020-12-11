package com.festa.controller;

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

import javax.validation.constraints.NotNull;
import java.util.List;
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
    public Optional<List<EventDTO>> getListOfEvents(long cursorUserNo, int size) {

        return Optional.ofNullable(eventService.getListOfEvents(PageInfo.paging(cursorUserNo, size)));
    }

    /**
     * 이벤트 접수 기능
     * @param participants
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PostMapping("/{eventNo}/applies")
    public ResponseEntity<HttpStatus> applyForEvents(@RequestBody @NotNull Participants participants) {
        eventService.applyForEvents(participants);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 접수한 이벤트 취소 기능
     * @param participants
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PatchMapping("/cancel")
    public ResponseEntity<HttpStatus> cancelEvent(@RequestBody @NotNull Participants participants) {
        eventService.cancelEvent(participants);

        return RESPONSE_ENTITY_OK;
    }
}
