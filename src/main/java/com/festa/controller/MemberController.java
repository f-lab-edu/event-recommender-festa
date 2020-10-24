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

/*
Controller : View 반환
            Controller -> View -> DispatcherServlet -> Response
RestController : Controller + ResponseBody
            JSON/XML 타입의 HTTP 응답을 할 수 있다.
            RestController -> HTTPResponse
 */
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private AccountsService accountsService;

    /**
     * 일반 사용자 회원가입
     * @param signUpDTO
     * @return HttpStatus
     */
    @PostMapping(value = "/signUp")
    public HttpStatus signUp(@RequestBody @Valid SignUpDTO signUpDTO){
        accountsService.signUp(signUpDTO);
        return HttpStatus.CREATED;
    }

    /**
     * 일반 사용자 로그인
     * @param loginDTO
     * @return HttpStatus
     */
    @PostMapping(value = "/login")
    public HttpStatus login(@RequestBody @Valid LoginDTO loginDTO, HttpSession httpSession){
        accountsService.login(loginDTO, httpSession);
        return HttpStatus.OK;
    }

    /**
     * 일반 사용자 로그아웃
     * @param httpSession
     * @return HttpStatus
     */
    @PostMapping(value = "/logout")
    public void logout(@RequestBody HttpSession httpSession){
        accountsService.logout(httpSession);
    }
}
