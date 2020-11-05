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

    MemberDTO getUserById(long userId);
}
