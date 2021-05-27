package com.festa.common;

import com.festa.common.commonService.LoginService;
import com.festa.common.firebase.FirebaseTokenManager;
import com.festa.service.AlertService;
import com.festa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

/* 로그인/로그아웃 이라는 특정 도메인에 대한 공통 로직으로
 * 다른 클래스에 종속적으로 동작하지 않게 하면서 이후 확장성도 고려하여
 * 따로 인터페이스와 클래스로 분리시켜 둠.
 */

@Log4j2
@Component
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    public static final String USER_NO = "userNo";
    public final HttpSession httpSession;
    private final FirebaseTokenManager firebaseTokenManager;
    private final MemberService memberService;
    private final AlertService alertService;

    /**
     * 세션에 userNo 저장하고 Firebase 알림여부 전송 메서드 호출
     * @param userNo
     */
    @Override
    public void login(Long userNo, String userId, String password, String token) {
        memberService.isUserIdExist(userId, password);
        httpSession.setAttribute(USER_NO, userNo);
        firebaseLoginAlert(userNo, token);
    }

    /**
     * 로그아웃 시 세션의 userNo 제거, firebase Token 삭제
     * No Param
     * No return
     */
    @Override
    public void logout(long userNo) {
        httpSession.removeAttribute(USER_NO);
        firebaseTokenManager.removeToken(String.valueOf(userNo));
    }

    /**
     * 로그인 확인 여부
     * No Param
     * @return boolean
     */
    @Override
    public boolean isLoginUser() {
        Long userLogin = (Long) httpSession.getAttribute(USER_NO);

        if(userLogin != null) {
            return true;
        }
        return false;
    }

    /**
     * 세션의 저장된 user No 가져오기
     * @return userNo
     */
    @Override
    public Long getUserNo() {
        Optional<Long> userNo = Optional.ofNullable(httpSession.getAttribute(USER_NO))
                .map(no -> (Long) no);

        return userNo.orElseThrow(NoSuchElementException::new);
    }

    /**
     * 로그인 후 firebase token 저장 및 알림전송의 비즈니스 로직 처리
     * @param userNo
     * @param token
     */
    private void firebaseLoginAlert(long userNo, String token) {
        firebaseTokenManager.register(String.valueOf(userNo), token);
        alertService.eventStartNotice(userNo, LocalDate.now());
        alertService.changePasswordNotice(userNo);
    }
}
