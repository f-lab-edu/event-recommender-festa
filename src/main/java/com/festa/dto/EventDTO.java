package com.festa.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class EventDTO {

    int eventNo;

    String eventTitle;

    String eventContent;

    String period;

    Date registerDate;

    String participants;

    String writer;
}