package com.festa.dao;

import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import com.festa.model.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDAO {

    void insertMemberInfo(MemberDTO memberDTO);

    void insertMemberAddress(MemberDTO memberDTO);

    boolean isUserIdExist(String userId);

    void modifyMemberInfo(MemberInfo memberInfo);

    void modifyMemberAddress(MemberInfo memberInfo);

    MemberDTO getUserByNo(long userNo);

    void changeUserPw(long userNo, String password);

    void modifyMemberInfoForWithdraw(MemberDTO memberDTO);

    void modifyParticipantInfo(MemberInfo memberInfo);

    int getUserNoById(String userId);

}
