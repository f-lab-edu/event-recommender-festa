package com.festa.service;

import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    /**
     * 회원 가입
     *
     * id를 이용하여 중복 확인 후 이상이 없을 경우 회원 가입.
     * @param memberDTO
     */
    public void signUpMember(MemberDTO memberDTO) {
        checkMemberID(memberDTO.getEmail());
        // 비밀번호 암호화 설정 추가할것.
        int signUpFlag = memberDAO.signUpMember(memberDTO);

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
    public void checkMemberID(String email) {
        List<MemberDTO> checkMemberID = memberDAO.checkMemberID(email);
        if (!checkMemberID.isEmpty()) {
            throw new IllegalStateException("이미 등록된 이메일입니다.");
        }
    }
}
