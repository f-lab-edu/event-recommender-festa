package com.festa.service;

import com.festa.dao.HostDAO;
import com.festa.dto.HostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostService {

    @Autowired
    private HostDAO hostDAO;

    public void insertHostInfo(HostDTO hostDTO) {
        hostDAO.insertHostInfo(hostDTO);
    }

    public int idIsDuplicated(String id) {
        return hostDAO.idIsDuplicated(id);
    }
}
