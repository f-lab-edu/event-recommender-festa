package com.festa.management;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Session을 관리하는 클래스로, 생성, 삭제, 존재 여부 등 관한 것들을 관리하는 클래스
 */
@Component
public class SessionManagement {

    /**
     * session을 만들어준다.
     * @param httpSession
     * @param sessionKey
     * @param object
     */
    public void makeSession(HttpSession httpSession, String sessionKey, Object object){
        if (object == null){
            throw new IllegalArgumentException();
        }
        httpSession.setAttribute(sessionKey, object);
    }

    public boolean isExistedSession(HttpSession httpSession, String sessionKey){
        if (httpSession.getAttribute(sessionKey) == null){
            return false;
        }
        return true;
    }
    /**
     * session을 삭제한다.
     * @param httpSession
     * @param sessionKey
     */
    public void deleteSession(HttpSession httpSession, String sessionKey){
        httpSession.removeAttribute(sessionKey);
    }
}
