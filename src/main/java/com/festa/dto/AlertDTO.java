package com.festa.dto;

import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class AlertDTO {

    int userNo;

    String title;

    String content;

    String pwLastModified;

    String token;
}
