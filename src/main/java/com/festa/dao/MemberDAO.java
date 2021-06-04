package com.festa.dao;

import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import com.festa.model.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDAO {

    void insertMemberInfo(MemberDTO memberDTO);

    void insertMemberAddress(MemberDTO memberDTO);

    boolean isUserIdExist(@Param("userId") String userId, @Param("password") String password);

    void modifyMemberInfo(MemberInfo memberInfo);

    void modifyMemberAddress(MemberInfo memberInfo);

    MemberDTO getUserByNo(@Param("userNo") long userNo);

    void changeUserPw(@Param("userNo") long userNo, @Param("password") String password);

    void modifyMemberInfoForWithdraw(@Param("userNo") long userNo);

    void modifyParticipantInfo(MemberInfo memberInfo);

    long getUserNoById(@Param("userId") String userId);

    String getUserPassword(long userNo);

    boolean getChangePwDateDiff(@Param("userNo") long userNo);

}
