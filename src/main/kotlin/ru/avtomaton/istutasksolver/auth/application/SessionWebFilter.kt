package ru.avtomaton.istutasksolver.auth.application

import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.awaitSession
import ru.avtomaton.istutasksolver.auth.infrastructure.Users
import java.util.*

/**
 * Фильтр, определяющий авторизованного пользователя по атрибутам сессии.
 * Если из сессии удалось извлечь авторизованного пользователя, то информация о нём складывается в контекст корутины
 * для дальнейшего использования в контроллерах.
 * Фильтр выполняется до того, как запрос попадает в контроллеры.
 */
@Component
class SessionWebFilter(
    private val userRepository: UserRepository,
) : CoWebFilter() {

    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        val user  = exchange.tryGetUserId()?.let { // извлечение информации о пользователе из сессии
            userRepository.findById(userId = it)
        }

        withContext(Users(user)) { // информация о пользователе (или её отсутствие) складывается
            chain.filter(exchange) // в контекст корутины
        }
    }

    /**
     * Извлечь идентификатор текущего пользователя.
     */
    suspend fun ServerWebExchange.tryGetUserId(): UUID? {
        val awaitSession = awaitSession()
        val userId = awaitSession.attributes["userId"]
        return userId?.toString()?.let {
            try {
                UUID.fromString(it)
            } catch (_: Exception) {
                null
            }
        }
    }
}
