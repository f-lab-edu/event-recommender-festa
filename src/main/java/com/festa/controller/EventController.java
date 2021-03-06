package com.festa.controller;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_BAD_REQUEST;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_CONFLICT;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.common.commonService.CurrentLoginUserNo;
import com.festa.common.commonService.ImageUploader;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import com.festa.service.AlertService;
import com.festa.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AlertService alertService;
    private final ImageUploader imageUploader;

    /**
     * 리스트형 이벤트 목록 조회 기능
     * @return {@literal ResponseEntity<List<EventDTO>>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping
    public Optional<List<EventDTO>> getListOfEvents(long cursorUserNo, int size, int categoryCode) {

        return Optional.ofNullable(eventService.getListOfEvents(new PageInfo(cursorUserNo, size), categoryCode));
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
    @GetMapping("/detail")
    public ResponseEntity<EventDTO> getInfoOfEvent(@RequestParam long eventNo) {
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
    public ResponseEntity<HttpStatus> registerEvents(@RequestBody EventDTO eventDTO, @RequestPart MultipartFile multipartFile) {
        boolean isEventExists = eventService.isEventExists(eventDTO.getEventTitle(), eventDTO.getStartDate());
        int categoryCode = eventDTO.getCategoryCode();

        if(isEventExists) {
            return RESPONSE_ENTITY_CONFLICT;
        }

        String fileName = null;
        if (!multipartFile.isEmpty()) {
            fileName = imageUploader.uploadImage(multipartFile);
        }
        eventService.registerEvents(eventDTO, categoryCode, fileName);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 주최자 이벤트 참여자 목록 조회 기능
     * @param userNo, eventNo
     * @return {@literal List<Participants>}
     * @throws NoSuchElementException (조회된 데이터가 없을 경우)
     */
    @CheckLoginStatus(auth = UserLevel.HOST)
    @GetMapping("/participants")
    public List<Participants> getParticipantList(@CurrentLoginUserNo long userNo, long eventNo) {
        List<Participants> participantsList = eventService.getParticipantList(userNo, eventNo);

        return participantsList;
    }

    /**
     * 주최자 이벤트 수정 기능
     * @param eventDTO
     * @return void
     */
    @CheckLoginStatus(auth = UserLevel.HOST)
    @PutMapping
    public void modifyEventsInfo(@RequestBody EventDTO eventDTO, @RequestPart MultipartFile multipartFile, @CurrentLoginUserNo long userNo) {
        String fileName = null;
        if (!multipartFile.isEmpty()) {
            fileName = imageUploader.uploadImage(multipartFile);
        }

        eventService.modifyEventsInfo(eventDTO, userNo, fileName);

        alertService.getParticipantsNeedAlert(eventDTO.getEventNo());
    }

    /**
     * 주최자 이벤트 삭제 기능
     * @param eventNo
     * @return void
     */
    @CheckLoginStatus(auth = UserLevel.HOST)
    @DeleteMapping
    public void deleteEvent(long eventNo, @CurrentLoginUserNo long userNo) {
        eventService.deleteEventNo(eventNo, userNo);
    }
}
