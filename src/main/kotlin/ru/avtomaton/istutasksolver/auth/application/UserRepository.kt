package ru.avtomaton.istutasksolver.auth.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.auth.domain.User
import ru.avtomaton.istutasksolver.auth.infrastructure.entity.UserEntity
import ru.avtomaton.istutasksolver.error.UserAlreadyExistException
import java.util.*

@Component
class UserRepository(
    private val jpaRepository: JpaUserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    suspend fun findById(userId: UUID): User? = withContext(Dispatchers.IO) {
        jpaRepository.findByIdOrNull(userId)
            ?.let { User(it.id, it.roles) }
    }

    suspend fun findByLoginAndPassword(login: String, password: String): User? = withContext(Dispatchers.IO) {
        jpaRepository.findByLogin(login)
            ?.takeIf { passwordEncoder.matches(password, it.password) }
            ?.let { User(it.id, it.roles) }
    }

    suspend fun save(user: User, login: String, password: String): Unit = withContext(Dispatchers.IO) {
        val encodedPassword = passwordEncoder.encode(password)
        val entity = UserEntity(
            id = user.id,
            roles = user.roles,
            login = login,
            password = encodedPassword,
        )
        try {
            jpaRepository.save(entity)
        } catch (ex: DataIntegrityViolationException) {
            throw UserAlreadyExistException()
        }
    }
}

interface JpaUserRepository : CrudRepository<UserEntity, UUID> {
    fun findByLogin(login: String): UserEntity?
}