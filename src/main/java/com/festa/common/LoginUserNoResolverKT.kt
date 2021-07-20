package com.festa.common

import com.festa.common.commonService.CurrentLoginUserNoKT
import com.festa.common.commonService.LoginServiceKT
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserNoResolverKT(val loginServiceKT: LoginServiceKT) : HandlerMethodArgumentResolver {

    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.hasParameterAnnotation(CurrentLoginUserNoKT::class.java)
    }

    override fun resolveArgument(methodParameter: MethodParameter, modelAndViewContainer: ModelAndViewContainer, nativeWebRequest: NativeWebRequest, webDataBinderFactory: WebDataBinderFactory): Any {
        return loginServiceKT.getUserNo()
    }
}