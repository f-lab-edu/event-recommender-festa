package com.festa.dao;

import com.festa.dto.LoginDTO;
import com.festa.dto.SignUpDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountsDAO {
    int signUp(SignUpDTO signUpDTO);

    LoginDTO login(LoginDTO loginDTO);

    boolean existedEmail(String email);

    boolean existedID(String userID);
}
