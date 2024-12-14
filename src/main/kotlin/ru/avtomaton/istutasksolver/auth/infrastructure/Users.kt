package ru.avtomaton.istutasksolver.auth.infrastructure

import ru.avtomaton.istutasksolver.auth.domain.User
import ru.avtomaton.istutasksolver.error.UnauthorizedException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class Users(
    val user: User?,
) : CoroutineContext.Element {

    override val key: CoroutineContext.Key<*>
        get() = Users

    companion object : CoroutineContext.Key<Users> {

        suspend fun currentOrNull(): User? = coroutineContext[Users]?.user

        suspend fun currentOrThrow(): User = coroutineContext[Users]?.user
                ?: throw UnauthorizedException()
    }
}
