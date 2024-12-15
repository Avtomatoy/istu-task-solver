package ru.avtomaton.istutasksolver.auth.infrastructure

import ru.avtomaton.istutasksolver.auth.domain.User
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * Инфраструктурный класс для сохранения пользователя в контекст корутины.
 */
class Users(
    val user: User?,
) : CoroutineContext.Element {

    override val key: CoroutineContext.Key<*>
        get() = Users

    companion object : CoroutineContext.Key<Users> {
        /**
         * Достать из контекста корутины текущего пользователя.
         */
        suspend fun currentOrNull(): User? = coroutineContext[Users]?.user
    }
}
