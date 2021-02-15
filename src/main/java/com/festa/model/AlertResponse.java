package com.festa.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlertResponse {

    long eventNo;

    String eventTitle;

    boolean isAlertNeed;
}
