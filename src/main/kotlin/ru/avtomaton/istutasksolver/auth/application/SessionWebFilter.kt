package ru.avtomaton.istutasksolver.auth.application

import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.awaitSession
import ru.avtomaton.istutasksolver.auth.infrastructure.Users
import java.util.*

@Component
class SessionWebFilter(
    private val userRepository: UserRepository,
) : CoWebFilter() {

    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        val user  = exchange.tryGetUserId()?.let {
            userRepository.findById(userId = it)
        }

        withContext(Users(user)) {
            chain.filter(exchange)
        }
    }

    suspend fun ServerWebExchange.tryGetUserId(): UUID? {
        val userId = awaitSession().attributes["userId"]
        return userId?.toString()?.let {
            try {
                UUID.fromString(it)
            } catch (_: Exception) {
                null
            }
        }
    }
}