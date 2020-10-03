package com.festa.dto;

import lombok.Builder;
import lombok.Value;

/* @Value 어노테이션을 이용하게 되면 필드에 자동으로 private final 이 붙게 되고
 * @Getter만 사용하며 @Setter는 생성하지 않기 때문에 각 필드는 getter method만이 생성된다.
 * @Data 어노테이션에서 유용하게 쓰이는 toString(), equals(), hashCode() 메서드 또한 생성시켜준다.
 * 이렇게 반복적인 코드를 줄여주고 불변의 객체로 손쉽게 만들 수 있는 장점이 있어 사용함
 */

@Value
@Builder
public class MemberDTO {

    String username;
    String password;
    String email;
    String confirmEmail;
    int phoneNo;
    String address;

    //정해진 값(Y/N)에 다른 값이 들어오는 것을 막기 위해 enum으로 관리
    UserLevel userLevel;

    public enum UserLevel {
        //주최자
        Y,

        //일반사용자
        N
    }
}
