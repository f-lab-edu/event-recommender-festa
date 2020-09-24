package com.festa.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberDTO {

    String username;
    int password;
    String email;
    String confirmEmail;

}
