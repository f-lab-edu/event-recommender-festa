package com.festa.aop;

import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.dto.MemberDTO;
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
     * 권한에 따른 분기처리를 위한 메서드
     * No Param
     * No return
     */
    @Before(value = "@annotation(CheckLoginStatus) && @annotation(checkLoginStatus)")
    public void checkStatus(CheckLoginStatus checkLoginStatus) {
        if(checkLoginStatus.auth() == UserLevel.ALL_USERS) {
            allUserLoginStatus();

        } else if (checkLoginStatus.auth() == UserLevel.HOST) {
            hostLoginStatus();

        } else if(checkLoginStatus.auth() == UserLevel.USER) {
            userLoginStatus();

        }
    }

    /**
     * 모든 사용자 로그인 여부 확인
     * No param
     * No return
     * @throws HttpStatusCodeException
     */
    public void allUserLoginStatus() {
        boolean isLoginUser = loginService.isLoginUser();

        log.debug("Started to check all-users authentication");

        if(!isLoginUser) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "is not authorized") {};
        }
    }

    /**
     * 주최자 권한 사용자의 로그인 여부 확인
     * No param
     * No return
     * @throws HttpStatusCodeException
     */
    public void hostLoginStatus() {
        long userId = loginService.getUserId();
        MemberDTO memberInfo = memberService.getUser(userId);

        log.debug("Started to check Host-user authentication");

        if(memberInfo.getUserLevel() != UserLevel.HOST) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "Host is not authorized") {};
        }
    }

    /**
     * 일반 권한의 사용자 로그인 여부 확인
     * No param
     * No return
     * @throws HttpStatusCodeException
     */
    public void userLoginStatus() {
        long userId = loginService.getUserId();
        MemberDTO memberInfo = memberService.getUser(userId);

        log.debug("Started to check User authentication");

        if(memberInfo.getUserLevel() != UserLevel.USER) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "User is not authorized") {};
        }
    }
}
