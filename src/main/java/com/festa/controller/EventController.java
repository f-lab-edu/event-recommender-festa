package com.festa.controller;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.dto.EventDTO;
import com.festa.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * 이벤트 상세보기
     *
     * Optional : java8에서 도입, null이 될 수도 있는 객체를 감싸 예외가 발생하는것을 방지하거나, 해당 변수가 null일 수 있다는 가능성을 표현해준다.
     *          ofNullable 메서드는 value가 null이더라도 비어있는 Optional을 반환해준다.
     * @param eventNo
     * @return Optional<EventDTO>
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping("/{eventNo}")
    public Optional<EventDTO> getInfoOfEvent(@PathVariable int eventNo) {
        return Optional.ofNullable(eventService.getInfoOfEvent(eventNo));
    }
}
