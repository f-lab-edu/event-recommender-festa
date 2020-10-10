package com.festa.common.commonService;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/*
 * 인터페이스를 선언하여 관심을 분리하고
 * 자유로운 확장성을 위해 생성함.
 */
@Service
public interface LoginService {

    void setUserNameSession(HttpSession session, Long userId);


}
