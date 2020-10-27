package com.festa.dao;

import com.festa.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NoticeDAO {

    NoticeDTO sendChangePwNotice(long userId);
}
