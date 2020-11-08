package com.festa.aop;

import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.common.commonService.RetrieveMemberService;
import com.festa.dto.MemberDTO;
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

    private final RetrieveMemberService retrieveMemberService;
    private final LoginService loginService;

    /**
     * 권한에 따른 분기처리를 위한 메서드
     * No Param
     * No return
     */
    @Before(value = "@annotation(CheckLoginStatus) && @annotation(checkLoginStatus)")
    public void checkStatus(CheckLoginStatus checkLoginStatus) {
        UserLevel auth = checkLoginStatus.auth();

        switch(auth) {
            case ALL_USERS:
                allUserLoginStatus();
                break;

            case HOST :
                hostLoginStatus();
                break;

            case USER :
                userLoginStatus();
                break;
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
        MemberDTO memberInfo = retrieveMemberService.retrieveMemberInfo();

        log.debug(memberInfo.getUserId()+": Started to check all-users authentication");

        if(!isLoginUser) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, memberInfo.getUserId()+" is not authorized") {};
        }
    }

    /**
     * 주최자 권한 사용자의 로그인 여부 확인
     * No param
     * No return
     * @throws HttpStatusCodeException
     */
    public void hostLoginStatus() {
        MemberDTO memberInfo = retrieveMemberService.retrieveMemberInfo();

        log.debug(memberInfo.getUserId()+": Started to check Host-user authentication");

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
        MemberDTO memberInfo = retrieveMemberService.retrieveMemberInfo();

        log.debug(memberInfo.getUserId()+": Started to check User authentication");

        if(memberInfo.getUserLevel() != UserLevel.USER) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "User is not authorized") {};
        }
    }
}
