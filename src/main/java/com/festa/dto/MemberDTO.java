package com.festa.dto;

import com.festa.common.UserLevel;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/* @Value 어노테이션을 이용하게 되면 필드에 자동으로 private final 이 붙게 되고
 * @Getter만 사용하며 @Setter는 생성하지 않기 때문에 각 필드는 getter method만이 생성된다.
 * @Data 어노테이션에서 유용하게 쓰이는 toString(), equals(), hashCode() 메서드 또한 생성시켜준다.
 * 이렇게 반복적인 코드를 줄여주고 불변의 객체로 손쉽게 만들 수 있는 장점이 있어 사용함
 */

@Value
@Builder
public class MemberDTO {

    int userNo;

    @NotBlank(message = "아이디를 입력해주세요")
    long userId;

    @NotBlank(message = "이름을 입력해주세요")
    String userName;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,10}",
             message = "영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다")
    String password;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    String email;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "(^02.{0}|^01.{1}|[0-9]{3})([0-9]{4})([0-9]{4})")
    int phoneNo;

    //정해진 값에 다른 값이 들어오는 것을 막기 위해 enum으로 관리
    @NotNull
    UserLevel userLevel;

}
