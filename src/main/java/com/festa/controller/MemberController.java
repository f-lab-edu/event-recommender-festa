package com.festa.controller;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_CONFLICT;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_BAD_REQUEST_NO_USER;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_UNAUTHORIZED;

import com.festa.common.commonService.SessionLoginService;
import com.festa.dto.MemberDTO;
import com.festa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import javax.validation.Valid;

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
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    @Autowired
    private MemberService memberService;

    private final SessionLoginService sessionLoginService;

    /**
     * 사용자 회원가입 기능
     * @param memberDTO
     * @return ResponseEntity<MemberDTO>
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<MemberDTO> signUpAsMember(@RequestBody @Valid MemberDTO memberDTO) {
        memberService.insertMemberInfo(memberDTO);

        URI uri = WebMvcLinkBuilder.linkTo(MemberController.class).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * 사용자 중복 아이디 체크
     * @param userId
     * @return ResponseEntity<HttpStatus>
     */
    @GetMapping("/{userId}/duplicate")
    public ResponseEntity<HttpStatus> idIsDuplicated(@PathVariable @Valid long userId) {
        boolean isIdDuplicated = memberService.isUserIdExist(userId);

        //1을 리턴 받았다면 true이므로 id가 존재한다.
        if(isIdDuplicated) {
            return RESPONSE_ENTITY_CONFLICT;
        } else {
            return RESPONSE_ENTITY_OK;
        }
    }

    /**
     * 사용자 로그인 기능
     * @param memberDTO
     * @return ResponseEntity<HttpStatus>
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberDTO memberDTO) {
        long userId = memberDTO.getUserId();

        boolean isIdExist = memberService.isUserIdExist(userId);

        //잘못된 요청, 또는 존재하지 않는 값으로 로그인에 실패했을 때 httpSession에 저장하지 않고 400 status code를 return한다.
        if(!isIdExist) {
            return RESPONSE_ENTITY_BAD_REQUEST_NO_USER;
        }
        sessionLoginService.setUserId(userId);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 로그아웃 기능
     * No Param
     * @return ResponseEntity<HttpStatus>
     */
    @PostMapping(value = "/logout")
    public ResponseEntity<HttpStatus> logout() {
        boolean isLoginUser = sessionLoginService.isLoginUser();

        //세션에 로그인 여부 확인 후 false를 return 했다면 로그인 한 사용자가 아님
        if(!isLoginUser) {
            return RESPONSE_ENTITY_UNAUTHORIZED;
        }
        sessionLoginService.removeUserId();

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 회원 탈퇴 기능
     * @param memberDTO
     * @return
     */
    @DeleteMapping(value = "/")
    public ResponseEntity<HttpStatus> memberWithdraw(@RequestBody @Valid MemberDTO memberDTO) {
        // 로그인 여부 확인
        boolean isLoginUser = sessionLoginService.isLoginUser();
        if(!isLoginUser) {
            return RESPONSE_ENTITY_UNAUTHORIZED;
        }
        memberService.memberWithdraw(memberDTO);
        
        return RESPONSE_ENTITY_OK;
    }
}
