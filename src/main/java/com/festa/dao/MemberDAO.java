package com.festa.dao;

import com.festa.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDAO {

    int insertMemberInfo(MemberDTO memberDTO);

    boolean isUserIdExist(long userId);

    void modifyMemberInfo(MemberDTO memberDTO);

    MemberDTO getUserByNo(int userNo);

    void changeUserPw(int userNo, String password);

    void modifyMemberInfoForWithdraw(MemberDTO memberDTO);

    int getUserNoById(long userId);

}
