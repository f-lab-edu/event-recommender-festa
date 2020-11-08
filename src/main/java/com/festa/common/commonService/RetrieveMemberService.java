package com.festa.common.commonService;

import com.festa.dto.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface RetrieveMemberService {

    MemberDTO retrieveMemberInfo();
}
