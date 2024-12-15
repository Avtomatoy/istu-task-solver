package ru.avtomaton.istutasksolver.auth.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity // включает в целом авторизацию
class AuthConfiguration {
    /**
     * Создание кодировщика паролей.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    /**
     * Настройка системы безопасности Spring.
     * Базовые политики здесь отключаются, т.к. у нас собственная система авторизации.
     */
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .httpBasic { it.disable() }
            .authorizeExchange { it.anyExchange().permitAll() }
            .csrf { it.disable() }
            .build()
    }
}
