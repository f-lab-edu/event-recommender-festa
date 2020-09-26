package com.festa.controller;

import com.festa.dto.MemberDTO;
import com.festa.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 회원가입 메서드
     *
     * HttpStatus
     * CREATED : 201 생성 요청 성공공
     * @param memberDTO
     * @return
     */
    @PostMapping(value = "/signUpMember")
    public HttpStatus signUpMember(@RequestBody @Valid MemberDTO memberDTO){
        memberService.signUpMember(memberDTO);
        return HttpStatus.CREATED;
    }
}
