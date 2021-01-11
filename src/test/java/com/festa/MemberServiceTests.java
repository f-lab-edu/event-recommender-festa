package com.festa;

import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import com.festa.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDAO memberDAO;

    @Mock
    private LoginService loginService;

    private MockHttpSession mockHttpSession;

    @DisplayName("회원의 신규가입 성공")
    @Test
    public void signUpTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        memberService.insertMemberInfo(memberInfo);
        then(memberDAO).should().insertMemberAddress(any(MemberDTO.class));
    }

    @DisplayName("아이디 중복 시 true를 리턴")
    @Test
    public void dulicatedIdTest() {
        MemberLogin memberLogin = new MemberLogin(5, "rbdl879", "test123#");

        when(memberDAO.isUserIdExist(memberLogin.getUserId())).thenReturn(true);

        boolean isUserIdExists = memberDAO.isUserIdExist(memberLogin.getUserId());

        assertTrue(isUserIdExists);
    }

    @DisplayName("사용자 정보가 없다면 IllegalArgumentException이 발생")
    @Test
    public void memberWithdrawWithoutUserInfoTest() {
        when(memberDAO.getUserByNo(any(Long.class))).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> memberService.memberWithdraw(29));

    }

    @DisplayName("일치하는 비밀번호가 없을 경우 IllegalArgumentException이 발생")
    @Test
    public void memberChangePwMismatchTest() {
        when(memberDAO.getUserPassword(any(Long.class))).thenReturn("");

        assertThrows(IllegalArgumentException.class, () -> memberService.changeUserPw(5, "tt1234##"));
    }

}
