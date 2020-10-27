package com.festa.dto;

import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class NoticeDTO {

    long userId;

    String title;

    String content;

    Timestamp pwLastModified;
}
