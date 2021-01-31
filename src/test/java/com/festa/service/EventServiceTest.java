package com.festa.service;

import com.festa.dao.EventDAO;
import com.festa.dto.EventDTO;
import com.festa.model.PageInfo;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Spy
    private EventDAO eventDAO;

    public EventDTO generateEvent(long eventNo) {
        return EventDTO.builder()
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
    void getEventsList() {
        // given 이벤트 목록 생성
        List<EventDTO> eventsList = new ArrayList<>();
        eventsList.add(generateEvent(1));
        eventsList.add(generateEvent(2));

        PageInfo pageInfo = new PageInfo(5, 5);

        when(eventDAO.getListOfEvents(Mockito.any(PageInfo.class), Mockito.anyInt())).thenReturn(eventsList);

        // when
        List<EventDTO> resultEventList = eventService.getListOfEvents(pageInfo, 5);

        // then
        assertEquals(eventsList, resultEventList);
    }

    @Test
    @DisplayName("정상적인 이벤트 상세 조회")
    void getInfoOfEvents() {
        // given 이벤트 생성
        EventDTO event = generateEvent(555);

        when(eventDAO.getInfoOfEvent(Mockito.anyLong())).thenReturn(event);

        // when
        EventDTO resultEvent = eventService.getInfoOfEvent(555);

        // then
        assertEquals(event, resultEvent);
    }

    @Test()
    @DisplayName("비정상적인 이벤트 상세 조회")
    void getInfoOfNullEvents() {
        EventDTO event = generateEvent(555);

        when(eventDAO.getInfoOfEvent(Mockito.anyLong())).thenReturn(event);

        assertThrows(NullPointerException.class, () -> {
            eventService.getInfoOfEvent(Mockito.isNull());
        });
    }

}