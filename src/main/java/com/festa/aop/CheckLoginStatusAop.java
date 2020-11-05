package com.festa.aop;

import com.festa.common.commonService.LoginService;
import com.festa.service.MemberService;
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
public class CheckLoginStatusAop {

    private final LoginService loginService;
    private final MemberService memberService;

    /**
     * 모든 사용자 로그인 여부 확인
     * No param
     * @throws HttpStatusCodeException
     */
    @Before(value = "@annotation(com.festa.aop.CheckLoginStatus)")
    public void allUserLoginStatus() throws HttpStatusCodeException {
        boolean isLoginUser = loginService.isLoginUser();

        log.debug("Started to check user authentication");

        if(!isLoginUser) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "User is not authorized") {};
        }
    }
}
