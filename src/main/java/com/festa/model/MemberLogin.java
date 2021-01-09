package com.festa.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
public class MemberLogin {

    long userNo;

    @NotBlank(message = "아이디를 입력해주세요")
    String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,10}",
            message = "영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다")
    String password;

}
