package com.festa.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseEntityConstantsKT {

    companion object {
        val RESPONSE_ENTITY_CONFLICT = ResponseEntity.status(HttpStatus.CONFLICT).build<HttpStatus>()
        val RESPONSE_ENTITY_OK = ResponseEntity.status(HttpStatus.OK).build<HttpStatus>()
        val RESPONSE_ENTITY_NO_CONTENT = ResponseEntity.status(HttpStatus.NO_CONTENT).build<HttpStatus>()
        val RESPONSE_ENTITY_BAD_REQUEST = ResponseEntity.status(HttpStatus.BAD_REQUEST).build<HttpStatus>()
        val RESPONSE_ENTITY_BAD_REQUEST_NO_USER = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("미가입 회원입니다.")
        val RESPONSE_ENTITY_NOT_FOUND = ResponseEntity.status(HttpStatus.NOT_FOUND).build<HttpStatus>()
    }

}