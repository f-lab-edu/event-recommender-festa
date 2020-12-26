package com.festa.dao;

import com.festa.dto.MemberDTO;
import com.festa.model.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDAO {

    void insertMemberInfo(MemberDTO memberDTO);

    boolean isUserIdExist(long userId);

    void modifyMemberInfo(MemberInfo memberInfo);

    void modifyMemberAddress(MemberInfo memberInfo);

    MemberDTO getUserByNo(long userNo);

    void changeUserPw(long userNo, String password);

    void modifyMemberInfoForWithdraw(MemberDTO memberDTO);

    int getUserNoById(long userId);

}
