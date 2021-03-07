package com.festa.service;

import com.festa.dao.EventDAO;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
import com.festa.model.Participants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Spy
    private EventDAO eventDAO;

    public EventDTO generateEvent(long eventNo) {
        return EventDTO.builder()
                .userNo(30)
                .eventNo(eventNo)
                .eventTitle("세계여행 박람회")
                .eventContent("박람회에 어서오세요!")
                .startDate("2021-05-08")
                .endDate("2021-05-30")
                .categoryCode(5)
                .participantLimit(300)
                .noOfParticipants(243)
                .registerDate(new Date())
                .cityName("서울")
                .districtName("강남구")
                .streetCode("57204")
                .streetName("가로수길")
                .detail("feata빌딩 5층")
                .build();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("정상적인 이벤트 목록 조회")
    void getEventsListTest() {
        // given 이벤트 목록 생성
        List<EventDTO> eventsList = new ArrayList<>();
        eventsList.add(generateEvent(1));
        eventsList.add(generateEvent(2));

        PageInfo pageInfo = new PageInfo(5, 5);

        when(eventDAO.getListOfEvents(any(PageInfo.class), anyInt())).thenReturn(eventsList);

        // when
        List<EventDTO> resultEventList = eventService.getListOfEvents(pageInfo, 5);

        // then
        assertEquals(eventsList, resultEventList);
    }

    @Test
    @DisplayName("정상적인 이벤트 상세 조회")
    void getInfoOfEventsTest() {
        // given 이벤트 생성
        EventDTO event = generateEvent(555);

        when(eventDAO.getInfoOfEvent(anyLong())).thenReturn(event);

        // when
        EventDTO resultEvent = eventService.getInfoOfEvent(555);

        // then
        assertEquals(event, resultEvent);
    }

    @Test
    @DisplayName("정상적인 이벤트 등록")
    void registerEventsTest() {
        // given
        EventDTO event = generateEvent(555);
        EventDTO eventInfo = event.toEntityForInfo();
        EventDTO eventAddress =  EventDTO.builder()
                .eventNo(eventInfo.getEventNo())
                .cityName(event.getCityName())
                .districtName(event.getDistrictName())
                .streetCode(event.getStreetCode())
                .streetName(event.getStreetName())
                .detail(event.getDetail())
                .build();

        doNothing().when(eventDAO).registerEvents(eventInfo);

        doNothing().when(eventDAO).registerEventsAddress(eventAddress);

        // when
        eventService.registerEvents(event, 5);

        // then
        verify(eventDAO).registerEvents(any(EventDTO.class));
        verify(eventDAO).registerEventsAddress(any(EventDTO.class));
    }

    @Test
    @DisplayName("이벤트 수정 성공")
    void modifyEventsInfoTest() {
        // given
        EventDTO event = generateEvent(555);

        doNothing().when(eventDAO).modifyEventsInfo(event.toEntityForInfo());

        doNothing().when(eventDAO).modifyEventsAddress(event.toEntityForEventAddress());

        // when
       eventService.modifyEventsInfo(event,30);

        // then
        verify(eventDAO).modifyEventsInfo(any(EventDTO.class));
        verify(eventDAO).modifyEventsAddress(any(EventDTO.class));
    }

    @Test
    @DisplayName("이벤트를 등록한 사용자가 아니라면 수정 실패")
    public void modifyEventsInfoFalseTest() {
        EventDTO event = generateEvent(555);

        assertThrows(IllegalStateException.class, () -> {
            eventService.modifyEventsInfo(event, 50);
        });
    }

    @Test
    @DisplayName("이벤트 존재 여부 확인 - 존재")
    void isEventExistsTrueTest() {
        // given
        when(eventDAO.isEventExists("즐거운 컴퓨터 세계", "2021-03-05")).thenReturn(true);

        // when
        boolean result = eventService.isEventExists("즐거운 컴퓨터 세계", "2021-03-05");

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("이벤트 존재 여부 확인 - 존재하지 않음")
    void isEventExistsFalseTest() {
        // given
        when(eventDAO.isEventExists("즐거운 어린이날", "2021-05-05")).thenReturn(false);

        // when
        boolean result = eventService.isEventExists("즐거운 컴퓨터 세계", "2021-03-05");

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("정상적인 이벤트 신청")
    void applyForEventsTest() {
        // given
        EventDTO participantsInfo = EventDTO.builder()
                .noOfParticipants(30)
                .participantLimit(200)
                .build();

        Participants participants = Participants.builder()
                .userNo(35)
                .eventNo(555)
                .cityName("서울")
                .districtName("강남구")
                .streetCode("45321")
                .streetName("신사동")
                .detail("32-12")
                .build();

        when(eventDAO.checkNoOfParticipants(participants.getEventNo())).thenReturn(participantsInfo);

        doNothing().when(eventDAO).applyForEvents(participants);

        doNothing().when(eventDAO).insertParticipantAddress(participants.toEntityForAddress());

        doNothing().when(eventDAO).increaseParticipants(participants.getEventNo());

        // when
        eventService.applyForEvents(participants);

        // then
        verify(eventDAO).applyForEvents(participants);
        verify(eventDAO).insertParticipantAddress(participants.toEntityForAddress());
        verify(eventDAO).increaseParticipants(participants.getEventNo());
    }

    @Test()
    @DisplayName("마감된 이벤트 신청")
    void applyForFullEventsTest() {
        // given
        EventDTO eventParticipants = EventDTO.builder()
                .noOfParticipants(200)
                .participantLimit(200)
                .build();

        Participants participants = Participants.builder()
                .userNo(35)
                .eventNo(555)
                .cityName("서울")
                .districtName("강남구")
                .streetCode("45321")
                .streetName("신사동")
                .detail("32-12")
                .build();

        // when
        when(eventDAO.checkNoOfParticipants(anyLong())).thenReturn(eventParticipants);

        assertThrows(IllegalStateException.class, () -> {
            eventService.applyForEvents(participants);
        });
    }

    @Test
    @DisplayName("이벤트 신청 취소")
    void cancelEventTest() {
        // given
        Participants participants = Participants.builder()
                .userNo(35)
                .eventNo(555)
                .cityName("서울")
                .districtName("강남구")
                .streetCode("45321")
                .streetName("신사동")
                .detail("32-12")
                .build();

        doNothing().when(eventDAO).cancelEvent(participants.getUserNo());

        when(eventDAO.isParticipated(participants.getUserNo())).thenReturn(true);

        doNothing().when(eventDAO).reduceParticipants(participants.getEventNo());

        // when
        eventService.cancelEvent(participants);

        // then
        verify(eventDAO).cancelEvent(participants.getUserNo());
        verify(eventDAO).reduceParticipants(participants.getEventNo());
    }

    @Test
    @DisplayName("이벤트 신청 취소 실페 - 접수한 이벤트가 아님")
    void cancelNoAppliedEventTest() {
        // given
        Participants participants = Participants.builder()
                .userNo(35)
                .eventNo(555)
                .cityName("서울")
                .districtName("강남구")
                .streetCode("45321")
                .streetName("신사동")
                .detail("32-12")
                .build();

        doNothing().when(eventDAO).cancelEvent(participants.getUserNo());

        when(eventDAO.isParticipated(participants.getUserNo())).thenReturn(false);

        // when
        assertThrows(IllegalStateException.class, () -> {
            eventService.cancelEvent(participants);
            verify(eventDAO).cancelEvent(participants.getUserNo());
        });
    }

}