package com.festa.common

import com.festa.common.commonService.LoginServiceKT
import com.festa.common.firebase.FirebaseTokenManagerKT
import com.festa.service.AlertService
import com.festa.service.MemberService
import lombok.extern.log4j.Log4j2
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpSession
import kotlin.NoSuchElementException

@Log4j2
@Component
class SessionLoginServiceKT(val httpSession: HttpSession, val firebaseTokenManagerKT: FirebaseTokenManagerKT, val memberService: MemberService, val alertService: AlertService) :LoginServiceKT {

    val USER_NO = "userNo"

    override fun login(userNo: Long, userId: String, password: String, token: String) {
        memberService.isUserIdExist(userId, password)
        httpSession.setAttribute(USER_NO, userNo)
        firebaseLoginAlert(userNo, token)
    }

    override fun logout(userNo: Long) {
        httpSession.removeAttribute(USER_NO)
        firebaseTokenManagerKT.removeToken(userNo.toString())
    }

    override fun isLoginUser(): Boolean {
        val userLogin = httpSession.getAttribute(USER_NO) as Long

        return userLogin != null
    }

    override fun getUserNo(): Long {
        val userNo = Optional.ofNullable(httpSession.getAttribute(USER_NO))
                .map {
                    no -> no as Long
                }

        return userNo.orElseThrow {
            NoSuchElementException()
        }
    }

    private fun firebaseLoginAlert(userNo: Long, token: String) {
        firebaseTokenManagerKT.register(userNo.toString(), token)
        alertService.eventStartNotice(userNo)
        alertService.changePasswordNotice(userNo)
    }
}