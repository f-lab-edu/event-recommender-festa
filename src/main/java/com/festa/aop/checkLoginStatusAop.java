package com.festa.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@RequiredArgsConstructor
public class checkLoginStatusAop {

    /**
     * 로그인 확인 여부
     * No Param
     */
    @Before(value = "@annotation(checkLoginStatus)")
    public void loginStatus() {

    }
}
