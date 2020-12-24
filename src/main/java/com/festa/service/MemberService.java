package com.festa.service;

import com.festa.dao.MemberDAO;
import com.festa.dto.EventDTO;
import com.festa.dto.MemberDTO;
import com.festa.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void modifyMemberInfo(MemberDTO memberDTO, Address address) {
        memberDAO.modifyMemberInfo(memberDTO);

        Address memberAddress = address.toEntityForMember();
        memberDAO.modifyMemberAddress(memberAddress);
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
}
