package ru.avtomaton.istutasksolver.auth.domain

import java.util.UUID

data class User(
    val id: UUID,
    val roles: Set<UserRole> = emptySet(),
) {

    companion object {

        fun newUser(): User {
            return User(
                id = UUID.randomUUID(),
                roles = emptySet(),
            )
        }
    }
}
