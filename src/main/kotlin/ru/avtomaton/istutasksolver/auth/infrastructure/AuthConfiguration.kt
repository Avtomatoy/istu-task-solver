package ru.avtomaton.istutasksolver.auth.infrastructure

import kotlinx.coroutines.Dispatchers
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.CoroutinesUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod
import java.lang.reflect.Method

@Configuration
class AuthConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@Component
class ContextConfig : WebMvcRegistrations {
    override fun getRequestMappingHandlerAdapter(): RequestMappingHandlerAdapter =
        CoroutineContextAwareMappingHandler()
}

class CoroutineContextAwareMappingHandler(): RequestMappingHandlerAdapter() {
    override fun createInvocableHandlerMethod(handlerMethod: HandlerMethod): ServletInvocableHandlerMethod =
        CoroutineContextAwareInvocableMethod(handlerMethod)
}

class CoroutineContextAwareInvocableMethod(handlerMethod: HandlerMethod): ServletInvocableHandlerMethod(handlerMethod) {

    @Suppress("ReactiveStreamsUnusedPublisher")
    override fun invokeSuspendingFunction(method: Method, target: Any, args: Array<out Any>): Any {
//        val coroutineContext = <initalize your context here>
        return CoroutinesUtils.invokeSuspendingFunction(Dispatchers.Unconfined, method, target, *args)
    }
}