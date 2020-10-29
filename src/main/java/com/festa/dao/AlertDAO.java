package com.festa.dao;

import com.festa.dto.AlertDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlertDAO {

    AlertDTO sendChangePwNotice(long userId);
}
