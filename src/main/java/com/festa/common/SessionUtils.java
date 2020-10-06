package com.festa.common;

import com.festa.common.commonService.SessionService;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpSession;

/* 세션값 입출력과 관련된 클래스.
 * 유지보수성을 위해 따로 클래스로 분리시킴.
 */
@RequiredArgsConstructor
public class SessionUtils implements SessionService {

    public static final String USER_NAME = "username";

    public final HttpSession httpSession;

    /**
     * 세션에 username 저장하는 메서드
     * @param session
     * @param username
     */
    @Override
    public void setUserNameSession(HttpSession session, String username) {
        session.setAttribute(USER_NAME, username);
    }
}
