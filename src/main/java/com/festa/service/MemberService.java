package com.festa.service;

import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    public void insertMemberInfo(MemberDTO memberDTO) {
        memberDAO.insertMemberInfo(memberDTO);
    }

    public boolean idIsDuplicated(String id) {
        return memberDAO.idIsDuplicated(id);
    }

    public MemberDTO loginAsMembers(String username, String password) {
        return memberDAO.loginAsMembers(username, password);
    }
}
