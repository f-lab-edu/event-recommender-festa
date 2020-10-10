package com.festa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
@AllArgsConstructor
@Getter
public class LoginDTO {

    @NotBlank(message = "아이디를 입력해주세요.")
    String userID;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(message = "영문, 숫자, 특수문자를 각 1개 이상 포함하여 8자 이상 입력해주세요.",
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    String password;

    String userLevel;
}
