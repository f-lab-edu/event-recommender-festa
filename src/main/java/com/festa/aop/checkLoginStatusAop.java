package com.festa.aop;

import com.festa.common.commonService.SessionLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Aspect
@Component
@RequiredArgsConstructor
@Log4j2
public class checkLoginStatusAop {

    private final SessionLoginService sessionLoginService;

    /**
     * 로그인 확인 여부
     * No Param
     */
    @Before(value = "@annotation(checkLoginStatus)")
    public void loginStatus() throws HttpStatusCodeException {

        boolean isLoginUser = sessionLoginService.isLoginUser();

        log.debug("Started to check user authentication");

        if(!isLoginUser) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "User is not authorized") {};
        }
    }
}
