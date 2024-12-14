package ru.avtomaton.istutasksolver.auth.application

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.awaitSession
import ru.avtomaton.istutasksolver.auth.domain.User
import ru.avtomaton.istutasksolver.error.UnauthorizedException

@Component
class AuthenticationService(
    private val userRepository: UserRepository,
) {

    suspend fun register(exchange: ServerWebExchange, login: String, password: String) {
        val newUser = User.newUser()
        userRepository.save(newUser, login, password)

        exchange.continueWithUser(newUser)
    }

    suspend fun login(exchange: ServerWebExchange, login: String, password: String) {
        val user = userRepository.findByLoginAndPassword(login, password)
            ?: throw UnauthorizedException()

        exchange.continueWithUser(user)
    }

    suspend fun logout(exchange: ServerWebExchange) {
        exchange.awaitSession().invalidate().awaitFirstOrNull()
    }

    private suspend fun ServerWebExchange.continueWithUser(user: User) {
        val session = awaitSession()
        session.attributes["userId"] = user.id.toString()
        session.save().awaitFirstOrNull()
    }
}