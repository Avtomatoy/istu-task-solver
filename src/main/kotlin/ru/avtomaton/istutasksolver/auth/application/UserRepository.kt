package ru.avtomaton.istutasksolver.auth.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.auth.domain.User
import ru.avtomaton.istutasksolver.auth.infrastructure.entity.UserEntity
import ru.avtomaton.istutasksolver.auth.infrastructure.entity.UserEntityRepository
import ru.avtomaton.istutasksolver.error.UserAlreadyExistException
import java.util.*

@Component
class UserRepository(
    private val entityRepository: UserEntityRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    suspend fun findById(userId: UUID): User? = withContext(Dispatchers.IO) {
        entityRepository.findByIdOrNull(userId)
            ?.let { User(it.id, it.roles) }
    }

    suspend fun findByLoginAndPassword(login: String, password: String): User? = withContext(Dispatchers.IO) {
        entityRepository.findByLogin(login)
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
            entityRepository.save(entity)
        } catch (ex: DataIntegrityViolationException) {
            throw UserAlreadyExistException()
        }
    }
}
