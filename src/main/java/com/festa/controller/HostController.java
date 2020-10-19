package com.festa.controller;

import com.festa.dto.LoginDTO;
import com.festa.dto.SignUpDTO;
import com.festa.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/hosts")
public class HostController {

    @Autowired
    private AccountsService accountsService;

    /**
     * 주최자 회원가입
     * @param signUpDTO
     * @return HttpStatus
     */
    @PostMapping(value = "/signUp")
    public HttpStatus signUpHost(@RequestBody @Valid SignUpDTO signUpDTO){
        accountsService.signUp(signUpDTO);
        return HttpStatus.CREATED;
    }

    /**
     * 주최자 로그인
     * @param loginDTO
     * @return HttpStatus
     */
    @PostMapping(value = "/login")
    public HttpStatus loginHost(@RequestBody @Valid LoginDTO loginDTO, HttpSession httpSession){
        accountsService.login(loginDTO, httpSession);
        return HttpStatus.OK;
    }
}
