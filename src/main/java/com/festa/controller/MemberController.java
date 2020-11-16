package com.festa.controller;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.dto.MemberDTO;
import com.festa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_BAD_REQUEST;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_BAD_REQUEST_NO_USER;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_CONFLICT;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;

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

    private final MemberService memberService;
    private final LoginService loginService;

    /**
     * 사용자 회원가입 기능
     * @param memberDTO
     * @return {@literal ResponseEntity<MemberDTO>}
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<MemberDTO> signUp(@RequestBody @Valid MemberDTO memberDTO) {
        memberService.insertMemberInfo(memberDTO);

        URI uri = WebMvcLinkBuilder.linkTo(MemberController.class).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * 사용자 회원정보 조회 기능
     * @param userId
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping(value = "/{userId}")
    public ResponseEntity<HttpStatus> getUser(@PathVariable long userId) {
        MemberDTO memberInfo = memberService.getUser(userId);

        if(memberInfo == null) {
            return RESPONSE_ENTITY_BAD_REQUEST;
        }
        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 회원정보 수정 기능
     * @param memberDTO
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PutMapping(value = "/{userId}")
    public ResponseEntity<HttpStatus> modifyMemberInfo(@RequestBody @Valid MemberDTO memberDTO) {
        memberService.modifyMemberInfo(memberDTO);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 중복 아이디 체크
     * @param userId
     * @return {@literal ResponseEntity<HttpStatus>}
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
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberDTO memberDTO) {
        long userId = memberDTO.getUserId();

        boolean isIdExist = memberService.isUserIdExist(userId);

        //잘못된 요청, 또는 존재하지 않는 값으로 로그인에 실패했을 때 httpSession에 저장하지 않고 400 status code를 return한다.
        if(!isIdExist) {
            return RESPONSE_ENTITY_BAD_REQUEST_NO_USER;
        }
        loginService.setUserId(userId);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 로그아웃 기능
     * No Param
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PostMapping(value = "/logout")
    public ResponseEntity<HttpStatus> logout() {
        loginService.removeUserId();

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 비밀번호 변경 기능
     * @Param userId
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PatchMapping("/{userId}/password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody @Valid MemberDTO memberDTO) {
        long userId = loginService.getUserId();

        memberService.changeUserPw(userId, memberDTO.getPassword());

        return RESPONSE_ENTITY_OK;
    }
}
