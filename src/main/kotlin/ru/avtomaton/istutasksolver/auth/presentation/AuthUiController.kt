package ru.avtomaton.istutasksolver.auth.presentation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Контроллер с формами авторизации.
 */
@Controller
@RequestMapping("/auth-ui")
class AuthUiController {
    /**
     * Отдать форму для входа в систему.
     */
    @GetMapping("/login")
    fun login(): String = "login"
    /**
     * Отдать форму для регистрации.
     */
    @GetMapping("/register")
    fun register(): String = "register"
}
