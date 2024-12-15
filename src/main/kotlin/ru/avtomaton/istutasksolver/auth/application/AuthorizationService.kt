package ru.avtomaton.istutasksolver.auth.application

import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.auth.domain.UserRole
import ru.avtomaton.istutasksolver.auth.infrastructure.Users
import ru.avtomaton.istutasksolver.error.ForbiddenException

/**
 * Сервис авторизации пользователей.
 */
@Component
class AuthorizationService {

    /**
     * Проверить, что пользователь содержит хотя бы одну из требуемых для выполнения операции ролей.
     * Если таких ролей не найдено, выбрасывается исключение.
     *
     */
    suspend fun authorize(vararg roles: UserRole) {
        val user = Users.currentOrNull() // извлечение текущего пользователя из контекста корутины.
            ?: throw ForbiddenException()

        if (roles.toList().none { user.roles.contains(it) }) {
            throw ForbiddenException()
        }
    }
}
