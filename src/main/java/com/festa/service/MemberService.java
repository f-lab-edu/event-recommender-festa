package com.festa.service;

import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberInfo;
import com.festa.model.MemberNewPw;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO memberDAO;

    @Transactional
    public void insertMemberInfo(MemberDTO memberDTO) {
        MemberDTO memberInfo = memberDTO.toEntityForInfo();
        memberDAO.insertMemberInfo(memberInfo);

        MemberDTO memberAddress = MemberDTO.builder()
                .userNo(memberInfo.getUserNo())
                .cityName(memberDTO.getCityName())
                .districtName(memberDTO.getDistrictName())
                .streetCode(memberDTO.getStreetCode())
                .streetName(memberDTO.getStreetName())
                .build();

        memberDAO.insertMemberAddress(memberAddress);
    }

    public void isUserIdExist(String userId, String password) {
        boolean isUserIdExist = memberDAO.isUserIdExist(userId, password);

        if(!isUserIdExist) {
            throw new IllegalStateException("탈퇴한 아이디 입니다.");
        }
    }

    @Transactional
    public void modifyMemberInfo(MemberInfo memberInfo) {
        MemberInfo modifyInfoList = memberInfo.toEntityForInfo();
        memberDAO.modifyMemberInfo(modifyInfoList);

        MemberInfo memberAddress = memberInfo.toEntityForAddress();
        memberDAO.modifyMemberAddress(memberAddress);
    }

    public void modifyParticipantInfo(MemberInfo memberInfo) {
        MemberInfo participantAddress = memberInfo.toEntityParticipantAddress();

        memberDAO.modifyParticipantInfo(participantAddress);
    }

    @Transactional(readOnly = true)
    public MemberDTO getUser(long userNo) {
        return memberDAO.getUserByNo(userNo);
    }

    public void changeUserPw(long userNo, MemberNewPw memberNewPw) {

        if(!memberDAO.isUserPasswordExist(userNo, memberNewPw.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        memberDAO.changeUserPw(userNo, memberNewPw.getNewPassword());
    }

    public void memberWithdraw(long userNo) {

        if(memberDAO.getUserByNo(userNo) == null) {
            throw new IllegalStateException("일치하는 사용자정보가 없습니다.");
        }

        memberDAO.modifyMemberInfoForWithdraw(userNo);
    }

    @Transactional(readOnly = true)
    public long getUserNo(String userId) {
        return memberDAO.getUserNoById(userId);
    }
}
