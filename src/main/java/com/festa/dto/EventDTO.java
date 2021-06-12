package com.festa.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EventDTO {

    private long eventNo;

    private long userNo;

    @NotBlank(message = "제목을 입력해주세요")
    private String eventTitle;

    @NotBlank(message = "이벤트 내용을 입력해주세요")
    private String eventContent;

    @NotBlank(message = "이벤트 시작일 입력해주세요")
    @Pattern(regexp  = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$",
             message = "연, 월, 일을 - 을 포함하여 입력해주세요")
    private String startDate;

    @NotBlank(message = "이벤트 종료일 입력해주세요")
    @Pattern(regexp  = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$",
            message = "연, 월, 일을 - 을 포함하여 입력해주세요")
    private String endDate;

    private int categoryCode;

    private int participantLimit;

    private int noOfParticipants;

    private Date registerDate;

    private String cityName;

    private String districtName;

    private String streetCode;

    private String streetName;

    private String detail;

    private String fileName;

    public EventDTO toEntityForInfo(String fileName) {

        return EventDTO.builder()
                .userNo(this.userNo)
                .eventNo(this.eventNo)
                .eventTitle(this.eventTitle)
                .eventContent(this.eventContent)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .participantLimit(this.participantLimit)
                .categoryCode(this.categoryCode)
                .fileName(fileName)
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
