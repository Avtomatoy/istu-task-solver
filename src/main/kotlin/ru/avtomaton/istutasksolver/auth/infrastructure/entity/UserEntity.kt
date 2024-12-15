package ru.avtomaton.istutasksolver.auth.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.avtomaton.istutasksolver.auth.domain.UserRole
import java.io.Serializable
import java.util.UUID

@Repository
interface UserEntityRepository : CrudRepository<UserEntity, UUID> {
    fun findFirstByLogin(login: String): UserEntity?
}

/**
 * Сущность пользователя для хранения в базе данных.
 */
@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(name = "id")
    val id: UUID,
    @Column(name = "roles")
    val roles: Set<UserRole> = emptySet(),
    @Column(name = "login", unique = true)
    val login: String,
    @Column(name = "password")
    val password: String,
) : Serializable
