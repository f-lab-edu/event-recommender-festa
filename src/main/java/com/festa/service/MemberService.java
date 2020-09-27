package com.festa.service;

import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    public void insertMemberInfo(MemberDTO memberDTO) throws Exception {
        memberDAO.insertMemberInfo(memberDTO);
    }

    public int idIsDuplicated(MemberDTO memberDTO) throws Exception {
        return memberDAO.idIsDuplicated(memberDTO);
    }
}
