package ru.avtomaton.istutasksolver.auth.application

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.auth.domain.User
import ru.avtomaton.istutasksolver.error.UnauthorizedException

@Component
class AuthenticationService(
    private val userRepository: UserRepository,
) {

    suspend fun register(request: HttpServletRequest, login: String, password: String) {
        val newUser = User.newUser()
        userRepository.save(newUser, login, password)

        request.session.setAttribute("userId", newUser.id.toString())
    }

    suspend fun login(request: HttpServletRequest, login: String, password: String) {
        val user = userRepository.findByLoginAndPassword(login, password)
            ?: throw UnauthorizedException()

        request.session.setAttribute("userId", user.id.toString())
    }

    suspend fun logout(request: HttpServletRequest) {
        request.session.invalidate()
    }
}