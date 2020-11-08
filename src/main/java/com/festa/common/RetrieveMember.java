package com.festa.common;

import com.festa.common.commonService.LoginService;
import com.festa.common.commonService.RetrieveMemberService;
import com.festa.dto.MemberDTO;
import com.festa.service.MemberService;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_UNAUTHORIZED;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

/*
 *  checkLoginStatus 에서 세션에 userId를 가져오고 해당 ID로 회원정보를 조회하는
 *  로직이 여러 메서드에서 중복으로 발견되어 따로 클래스로 분리함.
 */

@RequiredArgsConstructor
public class RetrieveMember implements RetrieveMemberService {

    private final LoginService loginService;
    private final MemberService memberService;
    private static final int userNotLogin = 0;

    /**
     * 세션에서 가져오는 userId를 받아 회원 조회하는 기능
     * @return memberInfo
     */
    @Override
    public MemberDTO retrieveMemberInfo() {
        long userId = loginService.getUserId();

        if(userId == userNotLogin) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "not Login User") {};
        }

        return memberService.getUser(userId);
    }
}
