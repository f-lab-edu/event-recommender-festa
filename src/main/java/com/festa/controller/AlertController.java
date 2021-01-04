package com.festa.controller;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.commonService.CurrentLoginUserNo;
import com.festa.dto.AlertDTO;
import com.festa.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    /**
     * 비밀번호 변경 알람서비스 기능
     * @param userNo
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus
    @PostMapping
    public ResponseEntity<HttpStatus> sendChangePwNotice(@CurrentLoginUserNo int userNo) {
        // 사용자의 마지막 비밀번호 변경 시기를 가져온다.
        AlertDTO alertDTO = alertService.sendChangePwNotice(userNo);
        String lastModified = alertDTO.getPwLastModified();
        // 현재 시간 가져오기
        LocalDate nowDate = LocalDate.now();
        // 비번 바꾼 시간 가져오기(일단 임의로 작성)
        LocalDate pwLastModified = LocalDate.of(2020, 01, 01);
        // 현재 날짜와 바꾼 날짜의 차이(달)
        Long month = ChronoUnit.MONTHS.between(pwLastModified, nowDate);

        // 6개월 이상일 경우 메세지 전송
        if (month > 6) {
            alertService.send(alertDTO);
        }
        return RESPONSE_ENTITY_OK;
    }



    /**
     * token 생성 후 서버에서 관리
     * @param token
     * @param userNo
     * @return
     */
    @PostMapping(value="/register")
    public ResponseEntity register(@RequestBody String token, @CurrentLoginUserNo int userNo) {
        alertService.register(userNo, token);
        return ResponseEntity.ok().build();
    }
}
