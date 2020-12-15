package com.festa.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Value
@Builder
public class EventDTO {

    int eventNo;

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

}
