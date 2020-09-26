package com.festa.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class MemberDTO {

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(message = "영문, 숫자, 특수문자를 각 1개 이상 포함하여 8자 이상 입력해주세요.",
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private String password;


    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "확인 이메일을 입력해주세요.")
    private String emailCheck;
}
