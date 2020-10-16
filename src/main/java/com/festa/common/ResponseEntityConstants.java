package com.festa.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/* 매번 새로운 ResponseEntity 객체를 생성하여 발생하는
 * 메모리의 낭비를 최소화 하기 위해 미리 static 객체를 만들어 놓은
 * 클래스를 생성함.
 */
public class ResponseEntityConstants {

    public static final ResponseEntity<HttpStatus> RESPONSE_ENTITY_CONFLICT = ResponseEntity.status(HttpStatus.CONFLICT).build();
    public static final ResponseEntity<HttpStatus> RESPONSE_ENTITY_OK = ResponseEntity.status(HttpStatus.OK).build();
    public static final ResponseEntity<HttpStatus> RESPONSE_ENTITY_UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    public static final ResponseEntity<String> RESPONSE_ENTITY_BAD_REQUEST_NO_USER = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("미가입 회원입니다.");

}
