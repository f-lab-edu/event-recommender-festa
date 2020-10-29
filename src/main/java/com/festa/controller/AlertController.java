package com.festa.controller;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

import com.festa.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    /**
     * 비밀번호 변경 알람서비스 기능
     * @param session
     * @return ResponseEntity<HttpStatus>
     */
    @GetMapping(value = "/changePwNotice")
    public ResponseEntity<HttpStatus> sendChangePwNotice(HttpSession session) {
        long userId = (long) session.getAttribute("userId");

        //3개월간 바꾸지 않은 비밀번호에 대해 기간을 체크하는 배치 프로그램을 만들어 적용할 예정
        alertService.sendChangePwNotice(userId);

        return RESPONSE_ENTITY_OK;
    }
}
