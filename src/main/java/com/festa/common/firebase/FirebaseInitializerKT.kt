package com.festa.common.firebase

import com.festa.exception.FcmInitException
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import javax.annotation.PostConstruct

@Configuration
open class FirebaseInitializerKT {

    /**
     * lateinit 초기화를 늦게 하기 위해 Kotlin에서 사용하는 키워드
     * null을 통한 초기화를 허용하지 않기때문에 null관련 문제가 발생하지 않도록 해준다.
     * 그리고 초기화하지 않으면 사용 불가능하다.
     * 기본타입에서는 사용 불가능하며, var에서만 사용 가능하다.
     */
    @Value("\${firebase.firebaseConfigPath}")
    lateinit var firebaseConfigPath: String

    @Value("\${firebase.database.url}")
    lateinit var project: String

    @PostConstruct
    fun firebaseInit() {
        try {
            var serviceAccount = ClassPathResource(firebaseConfigPath)

            var options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.inputStream))
                    .setDatabaseUrl(project)
                    .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: IOException) {
            throw FcmInitException("FCM 서버를 초기화시키지 못했습니다.")
        }
    }
}