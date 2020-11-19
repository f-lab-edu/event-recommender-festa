package com.festa.service;

import com.festa.dao.AccountsDAO;
import com.festa.dto.SignUpDTO;
import com.festa.exception.DuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    @Autowired
    private AccountsDAO accountsDAO;

    /**
     * 회원 가입
     *
     * email, id 중복 확인 후 이상이 없을 경우 회원 가입.
     * 중복시 DuplicatedException 발생하며 중단
     * @param signUpDTO
     */
    public void signUp(SignUpDTO signUpDTO) {
        boolean existedEmail = isExistedEmail(signUpDTO.getEmail());
        if (existedEmail) {
            throw new DuplicatedException("이미 등록된 이메일입니다.");
        }

        boolean existedID = isExistedID(signUpDTO.getUserID());
        if (existedID) {
            throw new DuplicatedException("이미 등록된 아이디입니다.");
        }
        
        int signUpFlag = accountsDAO.signUp(signUpDTO);
        if (signUpFlag <= 0) {
            throw new IllegalStateException("회원가입에 실패하였습니다.");
        }
    }

    /**
     * 등록 이메일 여부 확인
     *
     * @param email
     */
    public boolean isExistedEmail(String email) {
        boolean isExistedEmail = accountsDAO.existedEmail(email);
        return isExistedEmail;
    }

    /**
     * 등록 ID 여부 확인
     *
     * @param userID
     */
    public boolean isExistedID(String userID) {
        boolean isExistedID = accountsDAO.existedID(userID);
        return isExistedID;
    }
}
