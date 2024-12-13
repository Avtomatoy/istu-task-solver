package ru.avtomaton.istutasksolver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class IstuTaskSolverApplication

fun main(args: Array<String>) {
    runApplication<IstuTaskSolverApplication>(*args)
}

//@Component
//class Listener(
//	val repo: UserRepository,
//) : ApplicationListener<ApplicationReadyEvent> {
//	override fun onApplicationEvent(event: ApplicationReadyEvent) {
//		val id = UUID.randomUUID()
//		val user = User(
//			id = id,
//			roles = setOf(UserRole.COMMON, UserRole.ADMIN),
//		)
//		val userFromDb = runBlocking {
////			repo.save(user, "avtomaton", "oralcumshot")
//			repo.findById(id)
//			repo.findByLoginAndPassword("avtomaton", "oralcumshot")
//		}
//		println(userFromDb)
//	}
//
//}