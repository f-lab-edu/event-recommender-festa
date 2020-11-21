package com.festa.service;

import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO memberDAO;

    public void insertMemberInfo(MemberDTO memberDTO) {
        memberDAO.insertMemberInfo(memberDTO);
    }

    public boolean isUserIdExist(long userId) {
        return memberDAO.isUserIdExist(userId);
    }

    public void modifyMemberInfo(MemberDTO memberDTO) {
        memberDAO.modifyMemberInfo(memberDTO);
    }

    public MemberDTO getUser(long userId) {
        return memberDAO.getUserById(userId);
    }

    public void changeUserPw(long userId, String password) {
        memberDAO.changeUserPw(userId, password);
    }

    public void memberWithdraw(MemberDTO memberDTO) {
        memberDAO.modifyMemberInfoForWithdraw(memberDTO);

    }
}
