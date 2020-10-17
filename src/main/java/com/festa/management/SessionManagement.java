package com.festa.management;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

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
}
