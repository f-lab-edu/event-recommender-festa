package com.festa.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlertResponse {

    String alertType;

    long targetNo;

    boolean isAlertNeed;
}
