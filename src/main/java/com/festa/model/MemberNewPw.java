package com.festa.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class MemberNewPw {

    @NotBlank(message = "비밀번호를 입력해주세요")
    String password;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요")
    String newPassword;
}
