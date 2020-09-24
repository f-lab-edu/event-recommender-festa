package com.festa.dao;

import com.festa.dto.MemberDTO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@MapperScan
@Repository
public interface MemberDAO {

    int insertMemberInfo(MemberDTO memberDTO);
}
