package com.festa;

import com.festa.common.UserLevel;
import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberInfo;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDAO memberDAO;

    private MockHttpSession mockHttpSession;

    @BeforeEach
    public void setUp() {
        mockHttpSession = new MockHttpSession();
    }

    public MemberDTO memberInfoSetUp() {
        return MemberDTO.builder()
                .userId("rbdl879")
                .password("test123#")
                .userName("testUser")
                .email("aaa@aaa.com")
                .phoneNo("01033334444")
                .userLevel(UserLevel.valueOf("USER"))
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("종로")
                .isDeleted(false)
                .build();
    }

    public MemberInfo modifyMemberInfoSetUp() {
        return MemberInfo.builder()
                .userNo(5)
                .userName("testUser")
                .phoneNo("01033334444")
                .cityName("서울")
                .districtName("은평구")
                .streetCode("2")
                .streetName("은평로")
                .isDeleted(false)
                .build();
    }

    public MemberInfo modifyDeletedMemberInfoSetUp() {
        return MemberInfo.builder()
                .userNo(1)
                .userName("제인")
                .phoneNo("01033334444")
                .cityName("서울")
                .districtName("은평구")
                .streetCode("2")
                .streetName("은평로")
                .isDeleted(true)
                .build();
    }

    @DisplayName("회원의 신규가입 성공")
    @Test
    public void signUpTest() {
        memberService.insertMemberInfo(memberInfoSetUp());
        
        doNothing().when(memberDAO).insertMemberInfo(memberInfoSetUp().toEntityForInfo());

        then(memberDAO).should().insertMemberAddress(
                memberInfoSetUp().builder()
                .userNo(memberInfoSetUp().getUserNo())
                .cityName(memberInfoSetUp().getCityName())
                .districtName(memberInfoSetUp().getDistrictName())
                .streetCode(memberInfoSetUp().getStreetCode())
                .streetName(memberInfoSetUp().getStreetName())
                .build());
    }

    @DisplayName("회원 로그인 성공")
    @Test
    public void loginTest() {
        MemberLogin memberLogin = new MemberLogin(5, "rbdl879", "test123#");

        when(memberDAO.isUserIdExist(memberLogin.getUserId(), memberLogin.getPassword())).thenReturn(false);
        mockHttpSession.setAttribute("USER_NO", memberLogin.getUserNo());

        assertEquals(memberLogin.getUserNo(), mockHttpSession.getAttribute("USER_NO"));
    }

    @DisplayName("탈퇴한 아이디가 로그인 요청이 오면 IllegalStateException이 발생한다")
    @Test
    public void deletedIdLoginTest() {
        MemberLogin memberLogin = new MemberLogin(1, "jes7077", "test123#");

        when(memberDAO.isUserIdExist(memberLogin.getUserId(), memberLogin.getPassword())).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> memberService.isUserIdExist(memberLogin.getUserId(), memberLogin.getPassword()));
    }

    @DisplayName("아이디가 불일치할 경우 로그인에 실패한다")
    @Test
    public void loginUserIdMismatchTest() {
        MemberLogin memberLogin = new MemberLogin(5, "jeje12", "test123#");

        when(memberDAO.getUserNoById(memberLogin.getUserId())).thenReturn(7L);

        assertNotEquals(memberLogin.getUserNo(),7);
    }

    @DisplayName("사용자 탈퇴 시 회원정보 일치하면 회원탈퇴에 성공한다")
    @Test
    public void memberWithdrawTest() {
        given(memberDAO.getUserByNo(5)).willReturn(memberInfoSetUp());

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

    @DisplayName("사용자의 정보를 조회한다")
    @Test
    public void getUserTest() {
        given(memberDAO.getUserByNo(5)).willReturn(memberInfoSetUp());

        MemberDTO memberInfo = memberService.getUser(5);

        assertEquals(memberInfoSetUp().getUserId(), memberInfo.getUserId());
    }

    @DisplayName("회원정보를 요청할때 탈퇴한 회원이면 null을 리턴한다")
    @Test
    public void getUserDeletedTest() {
        given(memberDAO.getUserByNo(1)).willReturn(null);

        MemberDTO memberInfo = memberService.getUser(1);

        assertThat(memberInfo.isDeleted(), is(not(true)));
    }

    @DisplayName("회원정보 수정에 성공한다")
    @Test
    public void modifyMemberInfoTest() {
        memberService.modifyMemberInfo(modifyMemberInfoSetUp());

        doNothing().when(memberDAO).modifyMemberInfo(modifyMemberInfoSetUp());

        assertThat(modifyMemberInfoSetUp().isDeleted(), is(not(true)));

        then(memberDAO).should().modifyMemberInfo(modifyMemberInfoSetUp().toEntityForInfo());
        then(memberDAO).should().modifyMemberAddress(modifyMemberInfoSetUp().toEntityForAddress());
    }

    @DisplayName("회원정보 수정할 때 탈퇴한 회원임이 확인되면 수정을 하지 않는다")
    @Test
    public void modifyDeletedMemberInfoTest() {
        memberService.modifyMemberInfo(modifyMemberInfoSetUp());

        doNothing().when(memberDAO).modifyMemberInfo(modifyDeletedMemberInfoSetUp());

        assertThat(modifyDeletedMemberInfoSetUp().isDeleted(), is(not(true)));
    }
}
