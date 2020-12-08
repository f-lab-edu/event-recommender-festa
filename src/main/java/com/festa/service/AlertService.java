package com.festa.service;

import com.festa.dao.AlertDAO;
import com.festa.dto.AlertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    @Autowired
    private AlertDAO alertDAO;

    public AlertDTO sendChangePwNotice(int userNo) {
        return alertDAO.sendChangePwNotice(userNo);
    }
}
