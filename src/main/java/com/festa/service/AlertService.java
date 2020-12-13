package com.festa.service;

import com.festa.dao.AlertDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertDAO alertDAO;

    public void sendChangePwNotice(long userNo) {
        alertDAO.sendChangePwNotice(userNo);
    }
}
