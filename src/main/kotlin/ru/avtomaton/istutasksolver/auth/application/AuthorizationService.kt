package ru.avtomaton.istutasksolver.auth.application

import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.auth.domain.UserRole
import ru.avtomaton.istutasksolver.auth.infrastructure.Users
import ru.avtomaton.istutasksolver.error.ForbiddenException

@Component
class AuthorizationService {

    suspend fun authorize(requiredRole: UserRole) {
        val user = Users.currentOrNull()
            ?: throw ForbiddenException()

        if (!user.roles.contains(requiredRole)) {
            throw ForbiddenException()
        }
    }
}