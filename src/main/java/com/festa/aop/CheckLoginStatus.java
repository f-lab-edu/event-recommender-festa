package com.festa.aop;

import com.festa.common.UserLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    다수의 서비스에서 로그인 여부를 체크 후 동작해야 하기 때문에
    AOP를 활용하여 중복코드 리팩토링 목적으로 생성함.

    메서드가 런타임 때에 해당 서비스를 실행하기 전 로그인 여부 체크
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckLoginStatus {

    UserLevel auth() default UserLevel.ALL_USERS;
}
