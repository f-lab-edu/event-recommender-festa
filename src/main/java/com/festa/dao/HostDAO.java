package com.festa.dao;

import com.festa.dto.HostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HostDAO {

    int insertHostInfo(HostDTO hostDTO);

    int idIsDuplicated(String id);
}
