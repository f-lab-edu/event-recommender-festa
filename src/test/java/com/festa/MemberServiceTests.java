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

    @DisplayName("이름 미입력시 회원가입 불가")
    @Test
    public void signUpWithoutUserNameTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName("")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertNotEquals(memberInfo.getUserName(), "", "이름을 입력해주세요.");
    }

    @DisplayName("이메일 미입력시 회원가입 불가")
    @Test
    public void signUpWithoutEmailTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName("제인")
                .email("")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertNotEquals(memberInfo.getEmail(), "", "이메일을 입력해주세요.");
    }

    @DisplayName("전화번호 미입력시 회원가입 불가")
    @Test
    public void signUpWithoutPhoneTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertNotEquals(memberInfo.getPhoneNo(), "", "전화번호를 입력해주세요.");
    }

    @DisplayName("전화번호 -(하이픈) 입력시 회원가입 불가")
    @Test
    public void signUpWithoutPhoneRegexTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo("010-3333-4444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertFalse(memberInfo.getPhoneNo().matches("(^02.{0}|^01.{1}|[0-9]{3})([0-9]{4})([0-9]{4})"));
    }

    @DisplayName("비밀번호 특수기호 미포함 회원가입 불가")
    @Test
    public void signUpPasswordWithoutSpecialSymbolTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test12")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertFalse(memberInfo.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,10}"));
    }

    @DisplayName("비밀번호 숫자 미포함 회원가입 불가")
    @Test
    public void signUpPasswordWithoutNumberTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test#")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertFalse(memberInfo.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,10}"));
    }

    @DisplayName("비밀번호 5자 이하일 경우 회원가입 불가")
    @Test
    public void signUpPasswordBelowFiveCharTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("t12#")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertFalse(memberInfo.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,10}"));
    }

    @DisplayName("비밀번호 10자 이상일 경우 회원가입 불가")
    @Test
    public void signUpPasswordOverTenCharTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123123123###")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        assertFalse(memberInfo.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,10}"));
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

    @DisplayName("아이디가 공백일 경우 로그인 실패")
    @Test
    public void loginUserIdBlankTest() {
        MemberLogin memberLogin = new MemberLogin(5, " ", "test123#");

        assertNotEquals(memberLogin.getUserId(), " ", "아이디를 입력해주세요.");
    }
}
