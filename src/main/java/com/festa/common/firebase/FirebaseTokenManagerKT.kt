package com.festa.common.firebase

import lombok.extern.log4j.Log4j2
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
@Log4j2
class FirebaseTokenManagerKT {

    @Value("\${firebase.firebaseConfigPath}")
    lateinit var firebaseConfigPath: String

    @Resource
    lateinit var redisTemplate: RedisTemplate<String, String>

    /**
     * 토큰을 레디스에 저장하는 메서드
     * 토큰을 userNo와 함께 세션과 함께 저장하기에는 토큰의 주기가 다르기때문에 별도로 저장
     * @param userNo
     * @param token
     */
    fun register(userNo: String, token: String) {
        redisTemplate.opsForValue().set(userNo, token)
    }

    /**
     * userNo를 이용해 토큰을 얻어오는 메서드
     * @param userNo
     * @return
     */
    fun getToken(userNo: String?): String {
        return redisTemplate.opsForValue().get(userNo)
    }

    /**
     * 로그아웃시 토큰 삭제하는 메서드
     * @param userNo
     */
    fun removeToken(userNo: String) {
        redisTemplate.delete(userNo)
    }
}