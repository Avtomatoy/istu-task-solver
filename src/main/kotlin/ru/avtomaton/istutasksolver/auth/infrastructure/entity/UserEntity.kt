package ru.avtomaton.istutasksolver.auth.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.avtomaton.istutasksolver.auth.domain.UserRole
import java.io.Serializable
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(

    @Id
    val id: UUID,

    val roles: Set<UserRole> = emptySet(),

    @Column(name = "login", unique = true)
    val login: String,

    val password: String,
) : Serializable