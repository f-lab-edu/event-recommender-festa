package com.festa.common.commonService

import org.springframework.stereotype.Service

@Service
interface LoginServiceKT {

    fun login(userNo: Long, userId: String, password: String, token: String)

    fun logout(userNo: Long)

    fun isLoginUser(): Boolean

    fun getUserNo(): Long

}