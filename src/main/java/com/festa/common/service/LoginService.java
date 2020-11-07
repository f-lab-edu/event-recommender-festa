package com.festa.common.service;

import com.festa.dao.AccountsDAO;
import com.festa.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/*
인증과 관련된 기능을 따로  분리하여 이후 확장시 편리하게 함
 */
public interface LoginService {
    void login(HttpSession httpSession, LoginDTO loginDTO);
    LoginDTO getUserInfoForLogin(HttpSession httpSession, LoginDTO loginDTO);
    void logout(HttpSession httpSession);
}
