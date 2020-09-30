package com.festa.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HostDTO {

    String hostName;
    int hostPassword;
    String hostEmail;
    String hostEmailConfirm;
    int hostPhoneNo;
    String hostAddress;
    String isHost; //주최자 여부
}
