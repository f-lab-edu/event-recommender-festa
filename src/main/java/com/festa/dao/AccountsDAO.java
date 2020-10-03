package com.festa.dao;

import com.festa.dto.SignUpDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountsDAO {
    int signUp(SignUpDTO signUpDTO);

    List<SignUpDTO> validateID(String id);
}
