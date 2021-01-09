package com.festa;

import com.festa.common.UserLevel;
import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import com.festa.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @DisplayName("아이디 미입력시 회원가입 불가")
    @Test
    public void signUpWithoutIdTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("")
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

        assertNotEquals(memberInfo.getUserId(), "", "아이디를 입력해주세요.");
    }

    @DisplayName("비밀번호 미입력 시 회원가입 불가")
    @Test
    public void signUpWithoutPasswordTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertNotEquals(memberInfo.getPassword(), "", "비밀번호를 입력해주세요.");
    }

    @DisplayName("회원 로그인 성공")
    @Test
    public void loginTest() {
        MemberLogin memberLogin = new MemberLogin(5, "jejeje111", "test123#");

        when(memberDAO.isUserIdExist(memberLogin.getUserId())).thenReturn(false);

        boolean isUserIdExists = memberDAO.isUserIdExist(memberLogin.getUserId());

        assertFalse(isUserIdExists);
    }

    @DisplayName("아이디 중복 시 로그인 실패")
    @Test
    public void loginDulicatedIdTest() {
        MemberLogin memberLogin = new MemberLogin(5, "rbdl879", "test123#");

        when(memberDAO.isUserIdExist(memberLogin.getUserId())).thenReturn(true);

        boolean isUserIdExists = memberDAO.isUserIdExist(memberLogin.getUserId());

        assertTrue(isUserIdExists);
    }

    @DisplayName("아이디가 null일 경우 로그인 실패")
    @Test
    public void loginUserIdNullTest() {
        MemberLogin memberLogin = new MemberLogin(5, null, "test123#");

        assertNotNull(memberLogin.getUserId());
    }
}
