package com.festa;

import com.festa.common.UserLevel;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserSignUpValidationTests {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("아이디를 입력해주세요", violation.getMessage());
    }

    @DisplayName("비밀번호 미입력 시 회원가입 불가")
    @Test
    public void signUpWithoutPasswordTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password(" ")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다", violation.getMessage());
    }

    @DisplayName("이름 미입력시 회원가입 불가")
    @Test
    public void signUpWithoutUserNameTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName(" ")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("이름을 입력해주세요", violation.getMessage());
    }

    @DisplayName("이메일 미입력시 회원가입 불가")
    @Test
    public void signUpWithoutEmailTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName("제인")
                .email(" ")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("이메일을 입력해주세요", violation.getMessage());
    }

    @DisplayName("비밀번호 특수기호 미포함 시 회원가입 불가")
    @Test
    public void signUpPasswordWithoutSpecialSymbolTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다", violation.getMessage());
    }

    @DisplayName("비밀번호 숫자 미포함 시 회원가입 불가")
    @Test
    public void signUpPasswordWithoutNumberTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test#")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다", violation.getMessage());
    }

    @DisplayName("비밀번호 영문자 미포함 시 회원가입 불가")
    @Test
    public void signUpPasswordWithoutEnglishCharTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("12345#")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다", violation.getMessage());
    }

    @DisplayName("비밀번호 길이 10자 이상 시 회원가입 불가")
    @Test
    public void signUpPasswordOverTenCharTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("tester123456#")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다", violation.getMessage());
    }

    @DisplayName("비밀번호 길이 5자 이하 시 회원가입 불가")
    @Test
    public void signUpPasswordBelowFiveCharTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("t12#")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("영문 대소문자와 숫자, 특수기호가 1개씩 포함되어있는 5~10자 비밀번호입니다", violation.getMessage());
    }

    @DisplayName("전화번호 미입력 시 회원가입 불가")
    @Test
    public void signUpWithoutPhoneTest() {
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("jes7077")
                .password("test123#")
                .userName("제인")
                .email("aaa@aaa.com")
                .phoneNo(" ")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .build();

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("01012341234 와 같은 형식으로 입력해주세요", violation.getMessage());
    }

    @DisplayName("전화번호 하이픈 입력 시 회원가입 불가")
    @Test
    public void signUpInvalidPhonePatternTest() {
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

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberInfo);

        ConstraintViolation<MemberDTO> violation = violations.iterator().next();
        assertEquals("01012341234 와 같은 형식으로 입력해주세요", violation.getMessage());
    }

    @DisplayName("아이디가 null일 경우 로그인 실패")
    @Test
    public void loginUserIdNullTest() {
        MemberLogin memberLogin = new MemberLogin(null, "test123#", "abc123");

        Set<ConstraintViolation<MemberLogin>> violations = validator.validate(memberLogin);

        ConstraintViolation<MemberLogin> violation = violations.iterator().next();
        assertEquals("아이디를 입력해주세요", violation.getMessage());
    }

    @DisplayName("아이디가 공백일 경우 로그인 실패")
    @Test
    public void loginUserIdBlankTest() {
        MemberLogin memberLogin = new MemberLogin(" ", "test123#", "abc123");

        Set<ConstraintViolation<MemberLogin>> violations = validator.validate(memberLogin);

        ConstraintViolation<MemberLogin> violation = violations.iterator().next();
        assertEquals("아이디를 입력해주세요", violation.getMessage());
    }
}
