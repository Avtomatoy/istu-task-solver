package ru.avtomaton.istutasksolver.auth.application

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.auth.infrastructure.Users
import java.util.UUID

@Component
class SessionWebFilter(
    private val userRepository: UserRepository,
) : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        runBlocking {
            val user  = request.tryGetUserId()?.let {
                userRepository.findById(userId = it)
            }

            withContext(Users(user)) {
                val d = coroutineContext
                println(d)
                chain.doFilter(request, response)
            }
        }
    }

    suspend fun ServletRequest.tryGetUserId(): UUID? {
        val userId = (this as? HttpServletRequest)?.session?.getAttribute("userId")
        return userId?.toString()?.let {
            try {
                UUID.fromString(it)
            } catch (_: Exception) {
                null
            }
        }
    }
}