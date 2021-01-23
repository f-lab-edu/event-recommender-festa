package com.festa.controller;

import com.festa.aop.CheckLoginStatus;
import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.common.commonService.CurrentLoginUserNo;
import com.festa.common.firebase.FirebaseTokenManager;
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

import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_MEMBER_NULL;
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
    private final FirebaseTokenManager firebaseTokenManager;

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
            return RESPONSE_ENTITY_MEMBER_NULL;
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
    public ResponseEntity<HttpStatus> modifyMemberInfo(@RequestBody MemberInfo memberInfo) {
        boolean isUserModifyInfo = memberInfo.isUserModifyInfo();

        if(isUserModifyInfo) {
            memberService.modifyParticipantInfo(memberInfo);
            memberService.modifyMemberInfo(memberInfo);

        } else {
            memberService.modifyParticipantInfo(memberInfo);
        }

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 탈퇴한 사용자 아이디 체크
     * @param userId
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @GetMapping("/{userId}/delete")
    public ResponseEntity<HttpStatus> isIdDeleted(@RequestParam String userId, @RequestParam String password) {
        memberService.isUserIdExist(userId, password);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 로그인 기능
     * @param memberLogin
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLogin memberLogin) {
        String userId = memberLogin.getUserId();
        String password = memberLogin.getPassword();

        memberService.isUserIdExist(userId, password);
        loginService.setUserNo(memberLogin.getUserNo());

        firebaseTokenManager.makeAccessToken(memberDTO.getUserNo());

        return ResponseEntity.ok(memberService.getChangePwDateDiff(memberDTO.getUserNo()));
    }

    /**
     * 사용자 로그아웃 기능
     * No Param
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@CurrentLoginUserNo String userNo) {
        loginService.removeUserNo();

        firebaseTokenManager.removeToken(userNo);

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 사용자 비밀번호 변경 기능
     * @Param userNo
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @PatchMapping("/{userId}/password")
    public ResponseEntity<HttpStatus> changePassword(@CurrentLoginUserNo long userNo, @RequestBody MemberLogin memberLogin) {
        memberService.changeUserPw(userNo, memberLogin.getPassword());

        return RESPONSE_ENTITY_OK;
    }

    /**
     * 회원 탈퇴 기능
     * @Param userNo
     * @return {@literal ResponseEntity<HttpStatus>}
     */
    @CheckLoginStatus(auth = UserLevel.USER)
    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> memberWithdraw(@CurrentLoginUserNo long userNo, String password) {
        memberService.memberWithdraw(userNo);
        
        return RESPONSE_ENTITY_OK;
    }
}
