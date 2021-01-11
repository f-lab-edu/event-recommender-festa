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

    public MemberDTO getUser(long userNo) {
        return memberDAO.getUserByNo(userNo);
    }

    public void changeUserPw(long userNo, String password) {
        memberDAO.changeUserPw(userNo, password);
    }

    public void memberWithdraw(MemberDTO memberDTO) {
        memberDAO.modifyMemberInfoForWithdraw(memberDTO);
    }

    public int getUserNo(long userId) {
        return memberDAO.getUserNoById(userId);
    }

    public boolean getChangePwDateDiff(long userNo) {
        String overChangePwDate = memberDAO.getChangePwDateDiff(userNo);

        return "1".equals(overChangePwDate) ? true : false;
    }
}
