package ru.avtomaton.istutasksolver.auth.presentation

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.avtomaton.istutasksolver.auth.application.AuthenticationService
import ru.avtomaton.istutasksolver.auth.infrastructure.Users
import ru.avtomaton.istutasksolver.auth.presentation.model.Credentials
import ru.avtomaton.istutasksolver.error.UnauthorizedException

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
) {

    @PostMapping("/register")
    suspend fun register(request: HttpServletRequest, @RequestBody credentials: Credentials) {
        authenticationService.register(request, credentials.login, credentials.password)
    }

    @PostMapping("/login")
    suspend fun login(request: HttpServletRequest, @RequestBody credentials: Credentials) {
        authenticationService.login(request, credentials.login, credentials.password)
    }

    @PostMapping("/logout")
    suspend fun logout(request: HttpServletRequest) {
        authenticationService.logout(request)
    }

    @GetMapping("check")
    suspend fun check() {
        Users.currentOrNull()
            ?: throw UnauthorizedException()
    }
}