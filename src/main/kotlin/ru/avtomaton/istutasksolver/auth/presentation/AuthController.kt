package ru.avtomaton.istutasksolver.auth.presentation

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import ru.avtomaton.istutasksolver.auth.application.AuthenticationService
import ru.avtomaton.istutasksolver.auth.presentation.model.Credentials

/**
 * REST-контроллер для авторизации.
 */
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
) {
    /**
     * Зарегистировать пользователя по логину и паролю.
     */
    @PostMapping("/register")
    suspend fun register(exchange: ServerWebExchange, @RequestBody credentials: Credentials) {
        authenticationService.register(exchange, credentials.login, credentials.password)
    }
    /**
     * Войти в систему по логину и паролю.
     */
    @PostMapping("/login")
    suspend fun login(exchange: ServerWebExchange, @RequestBody credentials: Credentials) {
        authenticationService.login(exchange, credentials.login, credentials.password)
    }
    /**
     * Выйти из системы.
     */
    @PostMapping("/logout")
    suspend fun logout(exchange: ServerWebExchange) {
        authenticationService.logout(exchange)
    }
}
