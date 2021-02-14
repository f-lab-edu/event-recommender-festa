package com.festa.service;

import com.festa.dao.EventDAO;
import com.festa.dao.MemberDAO;
import com.festa.dto.EventDTO;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO memberDAO;
    private final EventDAO eventDAO;

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

    public MemberDTO getUser(long userNo) {
        return memberDAO.getUserByNo(userNo);
    }

    public void changeUserPw(long userNo, String password) {

        if(!memberDAO.getUserPassword(userNo).equals(password)) {
            throw new IllegalArgumentException("일치하는 비밀번호가 없습니다.");
        }

        memberDAO.changeUserPw(userNo, password);
    }

    public Map<String, Boolean> sendEventStartNotice(long userNo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Boolean> response = new HashMap<>();

        List<Long> appliedEvents = eventDAO.getAppliedEvent(userNo);

        for(long eventNo : appliedEvents) {
            EventDTO eventInfo = eventDAO.getInfoOfEvent(eventNo);

            if(dateFormat.format(new Date()).equals(eventInfo.getStartDate())) {
                response.put(eventInfo.getEventTitle(), true);

            } else {
                response.put(eventInfo.getEventTitle(), false);
            }
        }
        return response;
    }

    public void memberWithdraw(long userNo) {

        if(memberDAO.getUserByNo(userNo) == null) {
            throw new IllegalStateException("일치하는 사용자정보가 없습니다.");
        }

        memberDAO.modifyMemberInfoForWithdraw(userNo);
    }

    public long getUserNo(String userId) {
        return memberDAO.getUserNoById(userId);
    }

    public boolean getChangePwDateDiff(long userNo) {
        return memberDAO.getChangePwDateDiff(userNo);
    }
}
