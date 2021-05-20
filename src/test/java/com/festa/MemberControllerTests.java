package com.festa;

import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.common.firebase.FirebaseTokenManager;
import com.festa.controller.MemberController;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberInfo;
import com.festa.model.MemberLogin;
import com.festa.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
class MemberControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private FirebaseTokenManager firebaseTokenManager;

    private MockHttpSession mockHttpSession;

    @BeforeEach
    public void setUp() {
        mockHttpSession = new MockHttpSession();
    }

    private static final long USER_NO = 1;

    public MemberDTO existedMemberInfo() {
        return MemberDTO.builder()
                .userNo(1)
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

    public MemberInfo modifyMemberInfoTrue() {
        return MemberInfo.builder()
                .userNo(1)
                .userName("제인")
                .phoneNo("01033334444")
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("세종대로")
                .detail("청운동 1번지")
                .isDeleted(false)
                .isUserModifyInfo(true)
                .build();
    }

    public MemberInfo modifyMemberInfoFalse() {
        return MemberInfo.builder()
                .userNo(1)
                .userName("제인")
                .phoneNo("01033334444")
                .cityName("서울")
                .districtName("종로구")
                .streetCode("1")
                .streetName("세종대로")
                .detail("청운동 1번지")
                .isDeleted(false)
                .isUserModifyInfo(false)
                .build();
    }

    @DisplayName("존재하는 사용자정보를 조회하는 경우 200 상태코드를 리턴한다.")
    @Test
    public void whenUserInfoIsExistedThenResponseOKGetUserTest() throws Exception {
        given(memberService.getUser(USER_NO)).willReturn(existedMemberInfo());

        this.mockMvc.perform(get("/members/{userNo}", "{userNo}")
                .param("userNo", "1"))
                .andExpect(status().isOk());

        then(memberService).should().getUser(USER_NO);
    }

    @DisplayName("삭제된 사용자정보를 조회하는 경우 404 상태코드를 리턴한다.")
    @Test
    public void whenUserIdIsDeletedThenResponseEmptyBodyGetUserTest() throws Exception {
        given(memberService.getUser(2)).willReturn(null);

        this.mockMvc.perform(get("/members/{userNo}", "{userNo}")
                .param("userNo", "2"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("탈퇴하지 않은 사용자라면 service layer에서 예외가 발생하지 않으므로 200 상태코드를 리턴한다.")
    @Test
    public void whenUserIdNotDeletedThenReturnOKIsUserIdExistTest() throws Exception {
        doNothing().when(memberService).isUserIdExist("jes7077", "123##test");

        this.mockMvc.perform(get("/members/{userId}/delete", "{userId}")
                .param("userId", "jes7077")
                .param("password", "123##test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("존재하는 회원이 로그인 요청을 하면 정상적으로 로그인한다.")
    @Test
    public void whenExistedMemberRequestLoginThenSuccessLoginTest() throws Exception {
        MemberLogin loginInfo = new MemberLogin("rbdl879", "test123##", "abc123");

        this.mockMvc.perform(post("/members/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content( "{\"userId\":\"rbdl879\"," +
                                          "\"password\":\"test123##\"," +
                                          "\"token\":\"abc123\"}"))

                                .andExpect(status().isOk());

        long userNo = memberService.getUserNo(loginInfo.getUserId());
        doNothing().when(memberService).isUserIdExist(loginInfo.getUserId(), loginInfo.getPassword());

        then(loginService).should().setUserNo(userNo);
        then(loginService).should().successLogin(userNo, loginInfo.getToken());
    }

    @DisplayName("회원 식별번호인 userNo가 null이라면 로그인한 회원이 아니기 때문에 비밀번호 변경에 실패한다.")
    @Test
    public void whenUserNoNullThenFailChangeUserPwTest() throws Exception {
        Long userNo = (Long) mockHttpSession.getAttribute("USER_NO");
        given(loginService.getUserNo()).willReturn(userNo);

        this.mockMvc.perform(patch("/members/{userId}/password", "{userId}/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( "{\"userId\":\"rbdl879\"," +
                          "\"password\":\"test123##\"," +
                          "\"token\":\"abc123\"}"));

        assertNull(userNo);
    }

    @DisplayName("비밀번호를 입력하지 않는다면 비밀번호 변경에 실패한다.")
    @Test
    public void whenPasswordNullThenFailChangeUserPwTest() throws Exception {
        given(loginService.getUserNo()).willReturn(USER_NO);
        MemberLogin loginInfo = new MemberLogin("rbdl879", "", "abc123");

        this.mockMvc.perform(patch("/members/{userId}/password", "{userId}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( "{\"userId\":\"rbdl879\"," +
                          "\"password\":\" \"," +
                          "\"token\":\"abc123\"}"));

        then(memberService).should(never()).changeUserPw(USER_NO, loginInfo.getPassword());
    }

    @DisplayName("로그인한 아이디와 비밀번호를 입력하면 비밀번호 변경에 성공한다.")
    @Test
    public void whenUserIdAndPasswordIsNotNullThenSuccessChangePwTest() throws Exception {
        given(loginService.getUserNo()).willReturn(USER_NO);
        MemberLogin loginInfo = new MemberLogin("rbdl879", "test123##", "abc123");

        this.mockMvc.perform(patch("/members/{userId}/password", "{userId}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( "{\"userId\":\"rbdl879\"," +
                          "\"password\":\"test123##\"," +
                          "\"token\":\"abc123\"}"))
                .andExpect(status().isOk());

        then(memberService).should().changeUserPw(USER_NO, loginInfo.getPassword());
    }

    @DisplayName("회원의 식별번호인 userNo가 null이라면 로그인한 회원이 아니기 때문에 회원탈퇴에 실패한다.")
    @Test
    public void whenUserNoNullThenFailMemberWithdrawTest() throws Exception {
        Long userNo = (Long) mockHttpSession.getAttribute("USER_NO");
        given(loginService.getUserNo()).willReturn(userNo);

        this.mockMvc.perform(delete("/members", "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userNo\":\" \"}"));

        assertNull(userNo);
    }

    @DisplayName("로그인한 상태라면 세션에서 회원번호를 읽어와 해당 회원의 탈퇴에 성공한다.")
    @Test
    public void whenUserNoIsNotNullThenSuccessMemberWithdrawTest() throws Exception {
        given(loginService.getUserNo()).willReturn(USER_NO);

        this.mockMvc.perform(delete("/members/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userNo\":\"1\"}"))
                .andExpect(status().isOk());

        then(memberService).should().memberWithdraw(USER_NO);
    }

    @DisplayName("회원 식별번호인 userNo가 null이라면 로그인한 상태가 아니기 때문에 로그아웃에 실패한다.")
    @Test
    public void whenUserNoIsNullThenFailLogoutTest() throws Exception {
        String userNo = (String) mockHttpSession.getAttribute("USER_NO");

        this.mockMvc.perform(post("/members/logout")
                .param("userNo", userNo)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userNo\":\" \"}"));

        assertNull(userNo);
    }

    @DisplayName("로그인한 상태라면 세션에서 회원번호를 읽어와 로그아웃에 성공한다.")
    @Test
    public void whenUserNoIsNotNullThenSuccessLogoutTest() throws Exception {
        given(loginService.getUserNo()).willReturn(USER_NO);

        this.mockMvc.perform(post("/members/logout")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userNo\":\"1\"}"))
                .andExpect(status().isOk());

        then(loginService).should().logout(USER_NO);
        firebaseTokenManager.removeToken(String.valueOf(USER_NO));
    }

    @DisplayName("참여자 정보 수정 시 해당 회원정보도 함께 수정할지에 대한 여부가 True 라면 해당 회원의 회원정보와 이벤트 참여자 정보 모두 수정한다.")
    @Test
    public void whenIsUserModifyInfoTrueThenModifyMemberAndParticipantInfo() throws Exception {
        boolean isUserModifyInfo = modifyMemberInfoTrue().isUserModifyInfo();

        this.mockMvc.perform(put("/members/{userNo}", "{userNo}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userNo\":\"1\"," +
                          "\"userName\":\"제인\"," +
                          "\"phoneNo\":\"01033334444\"," +
                          "\"isDeleted\":\"false\"," +
                          "\"cityName\":\"서울\"," +
                          "\"districtName\":\"종로구\"," +
                          "\"streetCode\":\"1\"," +
                          "\"streetName\":\"세종대로\"," +
                          "\"detail\":\"청운동 1번지\"," +
                          "\"isUserModifyInfo\":\"true\"}"))
                .andExpect(status().isOk());

        assertTrue(isUserModifyInfo);

        then(memberService).should().modifyParticipantInfo(modifyMemberInfoTrue());
        then(memberService).should().modifyMemberInfo(modifyMemberInfoTrue());
    }

    @DisplayName("참여자 정보 수정 시 회원정보도 함께 수정할지에 대한 여부가 False 라면 해당 회원의 이벤트 참여자 정보만 수정한다.")
    @Test
    public void whenIsUserModifyInfoFalseThenOnlyModifyParticipantInfo() throws Exception {
        boolean isUserModifyInfo = modifyMemberInfoFalse().isUserModifyInfo();

        this.mockMvc.perform(put("/members/{userNo}", "{userNo}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userNo\":\"1\"," +
                          "\"userName\":\"제인\"," +
                          "\"phoneNo\":\"01033334444\"," +
                          "\"isDeleted\":\"false\"," +
                          "\"cityName\":\"서울\"," +
                          "\"districtName\":\"종로구\"," +
                          "\"streetCode\":\"1\"," +
                          "\"streetName\":\"세종대로\"," +
                          "\"detail\":\"청운동 1번지\"," +
                          "\"isUserModifyInfo\":\"false\"}"))
                .andExpect(status().isOk());

        assertFalse(isUserModifyInfo);

        then(memberService).should().modifyParticipantInfo(modifyMemberInfoFalse());
        then(memberService).should(never()).modifyMemberInfo(modifyMemberInfoFalse());
    }
}
