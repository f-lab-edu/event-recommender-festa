package com.festa.service;

import com.festa.dao.NoticeDAO;
import com.festa.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    public NoticeDTO sendChangePwNotice(long userId) {
        return noticeDAO.sendChangePwNotice(userId);
    }
}
