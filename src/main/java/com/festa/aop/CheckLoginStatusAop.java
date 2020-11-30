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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

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
        UserLevel auth = checkLoginStatus.auth();

        switch(auth) {
            case USER:
                allUserLoginStatus();
                break;

            case HOST:
                hostLoginStatus();
                break;

            default:
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

        if(!isLoginUser) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "user is not authorized") {};
        }
    }

    /**
     * 주최자 권한 사용자의 로그인 여부 확인
     * No param
     * @return {@literal ResponseEntity<HttpStatus>}
     * @throws HttpStatusCodeException
    */
    public ResponseEntity<HttpStatus> hostLoginStatus() {
        allUserLoginStatus();

        int userNo = loginService.getUserNo();
        MemberDTO memberInfo = memberService.getUser(userNo);

        log.debug(userNo + ": Started to check Host-user authentication");

        if(memberInfo.getUserLevel() != UserLevel.HOST) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, userNo + " is not a Host") {};
        }

        return RESPONSE_ENTITY_OK;
    }
}
