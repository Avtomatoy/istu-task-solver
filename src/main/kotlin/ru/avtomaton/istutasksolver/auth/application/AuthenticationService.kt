package ru.avtomaton.istutasksolver.auth.application

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.awaitSession
import ru.avtomaton.istutasksolver.auth.domain.User
import ru.avtomaton.istutasksolver.error.UnauthorizedException

/**
 * Сервис аутентификации пользователей.
 */
@Component
class AuthenticationService(
    private val userRepository: UserRepository,
) {
    /**
     * Зарегистрировать пользователя. При успешной регистрации в сессию сохраняется идентификатор пользователя.
     * Если пользователь с подобным логином уже существует - выбрасывается исключение.
     * @param exchange  http запрос
     * @param login     логин пользователя
     * @param password  пароль пользователя
     */
    suspend fun register(exchange: ServerWebExchange, login: String, password: String) {
        val newUser = User.newUser(login)
        userRepository.save(newUser, login, password)

        exchange.continueWithUser(newUser)
    }
    /**
     * Войти в систему по логину и паролю. Если данные совпадают, то в сессию сохраняется идентификатор пользователя,
     * иначе выбрасывается исключение.
     */
    suspend fun login(exchange: ServerWebExchange, login: String, password: String) {
        val user = userRepository.findByLoginAndPassword(login, password)
            ?: throw UnauthorizedException()

        exchange.continueWithUser(user)
    }
    /**
     * Выйти из системы. Операция стирает все данные из сессии.
     */
    suspend fun logout(exchange: ServerWebExchange) {
        exchange.awaitSession().invalidate().awaitFirstOrNull()
    }
    /**
     * Записать и сохранить в сессию пользователя его идентификатор.
     */
    private suspend fun ServerWebExchange.continueWithUser(user: User) {
        val session = awaitSession()
        session.attributes["userId"] = user.id.toString()
        session.save().awaitFirstOrNull()
    }
}
