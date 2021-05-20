package com.festa.controller;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.common.commonService.CurrentLoginUserNo;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import com.festa.model.MemberInfo;
import com.festa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_NOT_FOUND;
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
    @PostMapping("/signUp")
    public ResponseEntity<MemberDTO> signUp(@RequestBody MemberDTO memberDTO) {
        memberService.insertMemberInfo(memberDTO);

        URI uri = WebMvcLinkBuilder.linkTo(MemberController.class).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * 사용자 회원정보 조회 기능
     * @param userNo
     * @return {@literal ResponseEntity<MemberDTO>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @GetMapping("/{userNo}")
    public ResponseEntity<HttpStatus> getUser(@RequestParam long userNo) {
        MemberDTO memberInfo = memberService.getUser(userNo);

        if(memberInfo == null) {
            return RESPONSE_ENTITY_NOT_FOUND;
        }
        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 회원정보 수정 기능
     * @param memberInfo
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PutMapping("/{userNo}")
    public void modifyMemberInfo(@RequestBody MemberInfo memberInfo) {
        boolean isUserModifyInfo = memberInfo.isUserModifyInfo();

        if(isUserModifyInfo) {
            memberService.modifyParticipantInfo(memberInfo);
            memberService.modifyMemberInfo(memberInfo);

        } else {
            memberService.modifyParticipantInfo(memberInfo);
        }
    }

    /**
     * 탈퇴한 사용자 아이디 체크
     * @param userId
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @GetMapping("/{userId}/delete")
    public void isIdDeleted(@RequestParam String userId, @RequestParam String password) {
        memberService.isUserIdExist(userId, password);
    }

    /**
     * 사용자 로그인 기능
     * Firebase Token 생성 후 로그인한 회원에게 보내야 할 알림여부를 응답을 보냄
     * @param memberLogin
     */
    @PostMapping("/login")
    public void login(@RequestBody MemberLogin memberLogin) {
        String userId = memberLogin.getUserId();
        String password = memberLogin.getPassword();
        String token = memberLogin.getToken();
        long userNo = memberService.getUserNo(userId);

        memberService.isUserIdExist(userId, password);
        loginService.setUserNo(userNo);
        loginService.successLogin(userNo, token);
    }

    /**
     * 사용자 로그아웃 기능
     * 로그인 시 생성 된 Firebase Token을 로그아웃과 동시에 삭제함
     * No Param
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PostMapping("/logout")
    public void logout(@CurrentLoginUserNo long userNo) {
        loginService.logout(userNo);
    }

    /**
     * 사용자 비밀번호 변경 기능
     * @Param userNo
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PatchMapping("/{userId}/password")
    public void changePassword(@CurrentLoginUserNo long userNo, @RequestBody MemberLogin memberLogin) {
        memberService.changeUserPw(userNo, memberLogin.getPassword());
    }

    /**
     * 회원 탈퇴 기능
     * @Param userNo
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @DeleteMapping("/")
    public void memberWithdraw(@CurrentLoginUserNo long userNo, String password) {
        memberService.memberWithdraw(userNo);
    }
}
