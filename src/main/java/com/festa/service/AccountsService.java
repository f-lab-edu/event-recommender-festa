package com.festa.service;

import com.festa.dao.AccountsDAO;
import com.festa.dto.SignUpDTO;
import com.festa.exception.DuplicatedIDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {

    @Autowired
    private AccountsDAO accountsDAO;

    /**
     * 회원 가입
     *
     * id를 이용하여 중복 확인 후 이상이 없을 경우 회원 가입.
     * @param signUpDTO
     */
    public void signUp(SignUpDTO signUpDTO) {
        // ID 중복 확인
        validateID(signUpDTO.getEmail());

        // 비밀번호 암호화 설정 추가할것.

        int signUpFlag = accountsDAO.signUp(signUpDTO);

        if (signUpFlag <= 0) {
            throw new IllegalStateException("회원가입에 실패하였습니다.");
        }
    }

    /**
     * 중복 가입 확인
     *
     * 중복시 IllegalStateException 발생하며 중단
     * IllegalStateException : 부적합한 때 메서드가 사용되었음.
     * @param email
     */
    public void validateID(String email) {
        boolean checkMemberID = accountsDAO.validateID(email);
        if (!checkMemberID) {
            throw new DuplicatedIDException("이미 등록된 이메일입니다.");
        }
    }
}
