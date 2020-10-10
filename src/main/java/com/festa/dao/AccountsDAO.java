package com.festa.dao;

import com.festa.dto.SignUpDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountsDAO {
    int signUp(SignUpDTO signUpDTO);

    boolean existedEmail(String email);

    boolean existedID(String userID);
}
