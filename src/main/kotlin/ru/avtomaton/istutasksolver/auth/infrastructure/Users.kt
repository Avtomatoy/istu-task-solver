package ru.avtomaton.istutasksolver.auth.infrastructure

import ru.avtomaton.istutasksolver.auth.domain.User
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class Users(
    val user: User?,
) : CoroutineContext.Element {

    override val key: CoroutineContext.Key<*>
        get() = Users

    companion object : CoroutineContext.Key<Users> {

        suspend fun currentOrNull(): User? {
            val coroutineContext1 = coroutineContext
            val users = coroutineContext1[Users]
            return users?.user
        }
    }
}
