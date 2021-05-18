package com.festa.common.commonService;

import org.springframework.stereotype.Service;

/*
 * 인터페이스를 선언하여 관심을 분리하고
 * 추후 수정이 필요할 때 시간을 절약하고 새로운 기능을 추가할 때
 * 기존 소스코드의 변경사항을 최소화 하기 위해 생성함.
 */

@Service
public interface LoginService {

    void setUserNo(Long userNo);

    void removeUserNo();

    boolean isLoginUser();

    Long getUserNo();

    void afterLogin(long userNo, String token);
}
