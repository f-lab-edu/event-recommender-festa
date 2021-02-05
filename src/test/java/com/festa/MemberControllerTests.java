package com.festa;

import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.controller.MemberController;
import com.festa.dto.MemberDTO;
import com.festa.model.MemberLogin;
import com.festa.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
class MemberControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private LoginService loginService;

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

    @DisplayName("존재하는 사용자정보를 조회하는 경우 200 상태코드를 리턴한다.")
    @Test
    public void getUserTest() throws Exception {
        when(memberService.getUser(1)).thenReturn(existedMemberInfo());

        this.mockMvc.perform(get("/members/{userNo}", existedMemberInfo().getUserNo())
                .param("userNo", "1"))
                .andExpect(status().isOk());

    }

    @DisplayName("삭제된 사용자정보를 조회하는 경우 200 상태코드와 함께 빈 body를 리턴한다.")
    @Test
    public void getDeletedUserTest() throws Exception {
        when(memberService.getUser(2)).thenReturn(null);

        this.mockMvc.perform(get("/members/{userNo}", "{userNo}")
                .param("userNo", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }

    @DisplayName("탈퇴하지 않은 사용자라면 service layer에서 예외가 발생하지 않으므로 200 상태코드를 리턴한다.")
    @Test
    public void isDeletedIdTest() throws Exception {
        doNothing().when(memberService).isUserIdExist("jes7077", "123##test");

        this.mockMvc.perform(get("/members/{userId}/delete", "{userId}")
                .param("userId", "jes7077")
                .param("password", "123##test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("존재하는 회원이 로그인 요청을 하면 정상적으로 로그인한다.")
    @Test
    public void loginTest() throws Exception {
        MemberLogin loginInfo = new MemberLogin(1L, "rbdl879", "test123##");

        doNothing().when(memberService).isUserIdExist("rbdl879", "test123##");

        this.mockMvc.perform(post("/members/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userNo\":\"1\",\"userId\":\"rbdl879\",\"password\":\"test123##\"}"))
                                .andExpect(status().isOk());

        then(loginService).should().setUserNo(loginInfo.getUserNo());
    }
}
