package com.festa.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlertResponse {

    long targetNo;

    String targetTitle;

    boolean isAlertNeed;
}
