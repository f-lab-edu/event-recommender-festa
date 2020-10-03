package com.festa.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/*
 setter가 있다면 객체가 언제, 어디서나 변경 가능하기 때문에 일관성을 유지하기 힘들다.
 대신 @AllArgsConstructor 모든 멤버를 매개변수로 받는 생성자를 만들 수 있다.
 */
@AllArgsConstructor
@Getter
public class SignUpDTO {

    /*
    @Notnull : null 불가능. "" 가능
    @NotEmpty : null, "" 불가능, " " 가능
    @NotBlank : null, "", " " 불가능
     */
    @NotBlank(message = "이름을 입력해주세요.")
    private final String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(message = "영문, 숫자, 특수문자를 각 1개 이상 포함하여 8자 이상 입력해주세요.",
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private final String password;


    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일을 입력해주세요.")
    private final String email;

    @NotBlank(message = "확인 이메일을 입력해주세요.")
    @Email(message = "확인 이메일을 입력해주세요.")
    private final String emailCheck;

    @Pattern(message = "- 없이 숫자만 입력해주세요.",
            regexp = "/^\\d{3}\\d{3,4}\\d{4}$/;")
    private String phoneNo;

    private String address;

    @NotBlank(message = "사용자인지, 주최자인지 선택해 주세요.")
    private final String userLevel;
}
