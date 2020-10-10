package com.festa.generator;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionWriter {

    /**
     * session을 만들어준다.
     * 이미 존재하는 session이라면 false를 반환한다.
     * @param httpSession
     * @param sessionKey
     * @param object
     * @return boolean
     */
    public boolean makeSession(HttpSession httpSession, String sessionKey, Object object){
        if(httpSession.getAttribute(sessionKey) != null){
            return false;
        }

        httpSession.setAttribute(sessionKey, object);
        return true;
    }
}
