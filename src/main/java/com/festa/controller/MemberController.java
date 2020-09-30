package com.festa.controller;

import com.festa.dto.MemberDTO;
import com.festa.service.MemberService;
import com.sun.istack.internal.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.URI;

/*
 * @RestController : @Controller와 @ResponseBody를 포함하고 있는 어노테이션
 *
 * @Controller : 해당클래스가 Controller로 정의되도록 설정
 *
 *               요청받은 URL이 DispatcherServlet에 할당된 것이라면 해당 패턴에 맞는
 *               Controller(@Controller를 스캔)로 전달한다. (URL의 정보는 모두 @RequestMapping이 가지고 있기
 *               때문에 맵핑이 가능)
 *               요청의 프로세스가 모두 완료되면 Controller는 view의 이름과 Model을
 *               DispatcherServlet으로 return 하며, DispatcherServlet은 받은 model data를
 *               기반으로 View에 연결한다.
 */
@RestController
@RequestMapping("/members")
@Log4j2
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 사용자 회원가입 기능
     * @param memberDTO
     * @return ResponseEntity
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<MemberDTO> signUpAsMember(@RequestBody @NotNull MemberDTO memberDTO) {
        memberService.insertMemberInfo(memberDTO);

        URI uri = WebMvcLinkBuilder.linkTo(MemberController.class).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * 사용자 중복 아이디 체크
     * @param id
     * @return HttpStatus
     */
    @GetMapping("/{id}/duplicate")
    public HttpStatus idIsDuplicated(@PathVariable @NotNull String id) {
        int isDuplicated = memberService.idIsDuplicated(id);

        //1을 리턴 받았다면 true이므로 id가 존재한다.
        if(isDuplicated == 1) {
            return HttpStatus.CONFLICT;
        } else {
            return HttpStatus.OK;
        }
    }
}
