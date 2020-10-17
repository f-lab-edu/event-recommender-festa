package com.festa.dao;

import com.festa.dto.LoginDTO;
import com.festa.dto.SignUpDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountsDAO {
    int signUp(SignUpDTO signUpDTO);

    LoginDTO getUserInfoForLogin(LoginDTO loginDTO);

    boolean isExistedEmail(String email);

    boolean isExistedID(String userID);
}
