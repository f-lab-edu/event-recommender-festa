package com.festa.controller;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

import com.festa.common.commonService.CurrentLoginUserNo;
import com.festa.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    /**
     * 비밀번호 변경 알람서비스 기능
     * @param userId
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @PostMapping
    public ResponseEntity<HttpStatus> sendChangePwNotice(@CurrentLoginUserNo long userId) {
        alertService.sendChangePwNotice(userId);

        return RESPONSE_ENTITY_OK;
    }
}
