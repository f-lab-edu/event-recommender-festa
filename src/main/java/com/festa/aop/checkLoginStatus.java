package com.festa.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    메서드가 런타임 때에 해당 서비스를 실행하기 전 로그인 여부 체크
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface checkLoginStatus {
}
