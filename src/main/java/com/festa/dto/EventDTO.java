package com.festa.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Value
@Builder
public class EventDTO {

    int eventNo;

    long userNo;

    @NotBlank(message = "제목을 입력해주세요")
    String eventTitle;

    @NotBlank(message = "이벤트 내용을 입력해주세요")
    String eventContent;

    @NotBlank(message = "이벤트 시작일 입력해주세요")
    @Pattern(regexp  = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$",
             message = "연, 월, 일을 - 을 포함하여 입력해주세요")
    String startDate;

    @NotBlank(message = "이벤트 종료일 입력해주세요")
    @Pattern(regexp  = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$",
            message = "연, 월, 일을 - 을 포함하여 입력해주세요")
    String endDate;

    int categoryCode;

    int participantLimit;

    int noOfParticipants;

    Date registerDate;

    String cityName;

    String districtName;

    String streetCode;

    String streetName;

    String detail;

    public EventDTO toEntityForInfo() {

        return EventDTO.builder()
                .userNo(this.userNo)
                .eventTitle(this.eventTitle)
                .eventContent(this.eventContent)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .participantLimit(this.participantLimit)
                .categoryCode(this.categoryCode)
                .build();
    }

    public EventDTO toEntityForEventAddress() {

        return EventDTO.builder()
                .eventNo(this.eventNo)
                .cityName(this.cityName)
                .districtName(this.districtName)
                .streetCode(this.streetCode)
                .streetName(this.streetName)
                .detail(this.detail)
                .build();
    }
}
