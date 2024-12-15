package ru.avtomaton.istutasksolver.auth.domain

import java.util.UUID

/**
 * Информация о пользователе.
 * @property id     идентификатор пользователя
 * @property roles  набор ролей пользователя
 * @property login  логин пользователя
 */
data class User(
    val id: UUID,
    val roles: Set<UserRole> = emptySet(),
    val login: String,
) {

    companion object {
        /**
         * Создать нового пользователя со случайным id, указанным логином и пустым списком ролей.
         */
        fun newUser(login: String): User {
            return User(
                id = UUID.randomUUID(),
                roles = emptySet(),
                login = login,
            )
        }
    }
}
