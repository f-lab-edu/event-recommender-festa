package com.festa;

import com.festa.common.UserLevel;
import com.festa.common.commonService.LoginService;
import com.festa.controller.MemberController;
import com.festa.dto.MemberDTO;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static com.festa.common.ResponseEntityConstants.RESPONSE_ENTITY_OK;
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
        given(memberService.getUser(1)).willReturn(existedMemberInfo());

        mockMvc.perform(get("/members/{userNo}", "{userNo}")
                .param("userNo", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userNo", is(existedMemberInfo().getUserNo())));
    }
}
