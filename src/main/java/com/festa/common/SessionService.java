package com.festa.common;

import com.festa.common.service.LoginService;
import com.festa.dao.AccountsDAO;
import com.festa.dto.LoginDTO;
import com.festa.exception.NotExistedException;
import com.festa.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService implements LoginService {

    @Autowired
    private AccountsDAO accountsDAO;
    @Autowired
    private AccountsService accountsService;

    private String loginSessionKey = "USER_LOGIN_KEY";

    @Override
    public void login(HttpSession httpSession, LoginDTO loginDTO) {
        boolean existedID = accountsService.isExistedID(loginDTO.getUserID());
        if (!existedID){
            throw new NotExistedException("존재하지 않는 아이디입니다.");
        }

        LoginDTO resultLoginDTO = getUserInfoForLogin(httpSession, loginDTO);

        httpSession.setAttribute(loginSessionKey, resultLoginDTO);
    }

    @Override
    public LoginDTO getUserInfoForLogin(HttpSession httpSession, LoginDTO loginDTO) {
        return accountsDAO.getUserInfoForLogin(loginDTO);
    }

    @Override
    public void logout(HttpSession httpSession){
        httpSession.removeAttribute(loginSessionKey);
    }
}
