package com.festa.controller;

import com.festa.dto.MemberDTO;
import com.festa.service.MemberService;
import com.sun.istack.internal.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/member")
@Log4j2
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping(value = "/signUpAsMember")
    public ResponseEntity<MemberDTO> signUpAsMember(@RequestBody @NotNull MemberDTO memberDTO) {
        memberService.insertMemberInfo(memberDTO);
        URI uri = WebMvcLinkBuilder.linkTo(MemberController.class).toUri();
        return ResponseEntity.created(uri).build();
    }
}
