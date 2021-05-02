package com.festa.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class MemberLogin {

    long userNo;

    @NotBlank(message = "아이디를 입력해주세요")
    String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    String password;

    String token;
}
