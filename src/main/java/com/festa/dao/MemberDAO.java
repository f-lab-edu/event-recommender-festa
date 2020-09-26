package com.festa.dao;

import com.festa.dto.MemberDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDAO {
    /**
     * 회원 가입
     * @param memberDTO
     * @return
     */
    int signUpMember(MemberDTO memberDTO);

    /**
     * id 중복 확인
     * @param id
     * @return
     */
    List<MemberDTO> checkMemberID(String id);
}
