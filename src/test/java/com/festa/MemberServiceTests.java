package com.festa;

import com.festa.common.UserLevel;
import com.festa.dao.MemberDAO;
import com.festa.dto.MemberDTO;
import com.festa.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDAO memberDAO;

    @Test
    @DisplayName("회원의 신규가입 성공")
    public void signUpTest() {

        //given
        MemberDTO memberInfo = MemberDTO.builder()
                .userId("testId")
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

        then(memberDAO).should().insertMemberInfo(any(MemberDTO.class));
        then(memberDAO).should().insertMemberAddress(any(MemberDTO.class));
    }
}
