package com.festa.manager;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionManager {

    public void makeSession(HttpSession httpSession, String sessionKey, Object object){
        if (object == null){
            throw new IllegalArgumentException();
        }
        httpSession.setAttribute(sessionKey, object);
    }
}
