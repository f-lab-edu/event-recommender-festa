package com.festa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlertDAO {

    void sendChangePwNotice(long userNo);
}
