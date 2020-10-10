package com.festa.common;

import com.festa.common.commonService.LoginService;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpSession;

/* 세션값 입출력과 관련된 클래스.
 * 유지보수성을 위해 따로 클래스로 분리시킴.
 */
@RequiredArgsConstructor
public class LoginUtil implements LoginService {

    public static final String USER_ID = "userId";

    public final HttpSession httpSession;

    /**
     * 세션에 username 저장하는 메서드
     * @param session
     * @param userId
     */
    @Override
    public void setUserNameSession(HttpSession session, Long userId) {
        session.setAttribute(USER_ID, userId);
    }
}
