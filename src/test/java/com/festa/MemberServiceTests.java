package com.festa;

import com.festa.common.UserLevel;
import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import com.festa.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDAO memberDAO;

    private MockHttpSession mockHttpSession;

    private MemberDTO memberDTO;

    @BeforeEach
    public void setUp() {
        mockHttpSession = new MockHttpSession();
    }

    @BeforeEach
    public void dtoSetUp() {
        memberDTO = MemberDTO.builder()
                .userNo(5)
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
    }

    @DisplayName("회원의 신규가입 성공")
    @Test
    public void signUpTest() {
        memberService.insertMemberInfo(memberDTO);

        then(memberDAO).should().insertMemberAddress(any(MemberDTO.class));
    }

    @DisplayName("회원 로그인 성공")
    @Test
    public void loginTest() {
        MemberLogin memberLogin = new MemberLogin(5, "rbdl879", "test123#");

        when(memberDAO.isUserIdExist(memberLogin.getUserId())).thenReturn(false);
        mockHttpSession.setAttribute("USER_NO", memberLogin.getUserNo());

        assertEquals(memberLogin.getUserNo(), mockHttpSession.getAttribute("USER_NO"));
    }




    @DisplayName("아이디가 중복된다면 true를 리턴한다")
    @Test
    public void dulicatedIdTest() {
        MemberLogin memberLogin = new MemberLogin(5, "rbdl879", "test123#");

        when(memberDAO.isUserIdExist(memberLogin.getUserId())).thenReturn(true);

        boolean isUserIdExists = memberDAO.isUserIdExist(memberLogin.getUserId());

        assertTrue(isUserIdExists);
    }

    @DisplayName("사용자 탈퇴 시 회원정보 일치하면 회원탈퇴에 성공한다")
    @Test
    public void memberWithdrawTest() {
        given(memberDAO.getUserByNo(5)).willReturn(memberDTO);

        memberService.memberWithdraw(5);

        then(memberDAO).should().modifyMemberInfoForWithdraw(5);
    }

    @DisplayName("사용자 탈퇴 시 정보가 없다면 IllegalArgumentException이 발생한다")
    @Test
    public void memberWithdrawWithoutUserInfoTest() {
        given(memberDAO.getUserByNo(5)).willReturn(null);

        memberService.memberWithdraw(5);

        assertThrows(IllegalStateException.class, () -> memberService.memberWithdraw(5));

    }

    @DisplayName("기존 비밀번호가 일치할 경우 비밀번호 변경에 성공한다")
    @Test
    public void memberChangePwTest() {
        given(memberDAO.getUserPassword(5)).willReturn("tt1234##");

        memberService.changeUserPw(5, "tt1234##");

        assertEquals("tt1234##", memberDAO.getUserPassword(5));
    }

    @DisplayName("기존 비밀번호가 불일치할 경우 IllegalArgumentException이 발생한다")
    @Test
    public void memberChangePwMismatchTest() {
        when(memberDAO.getUserPassword(any(Long.class))).thenReturn("");

        assertThrows(IllegalArgumentException.class, () -> memberService.changeUserPw(5, "tt1234##"));
    }
}
