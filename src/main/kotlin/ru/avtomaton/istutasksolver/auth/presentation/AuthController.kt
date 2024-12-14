package ru.avtomaton.istutasksolver.auth.presentation

import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import ru.avtomaton.istutasksolver.auth.application.AuthenticationService
import ru.avtomaton.istutasksolver.auth.domain.UserRole
import ru.avtomaton.istutasksolver.auth.infrastructure.Users
import ru.avtomaton.istutasksolver.auth.presentation.model.Credentials

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
) {

    @PostMapping("/register")
    suspend fun register(exchange: ServerWebExchange, @RequestBody credentials: Credentials) {
        authenticationService.register(exchange, credentials.login, credentials.password)
    }

    @PostMapping("/login")
    suspend fun login(exchange: ServerWebExchange, @RequestBody credentials: Credentials) {
        authenticationService.login(exchange, credentials.login, credentials.password)
    }

    @PostMapping("/logout")
    suspend fun logout(exchange: ServerWebExchange) {
        authenticationService.logout(exchange)
    }

    @GetMapping("/me/roles")
    suspend fun roles(exchange: ServerWebExchange): Set<UserRole> {
        return Users.currentOrThrow().roles
    }
}
