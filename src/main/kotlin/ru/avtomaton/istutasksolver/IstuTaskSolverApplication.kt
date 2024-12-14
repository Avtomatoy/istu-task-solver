package ru.avtomaton.istutasksolver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [ReactiveUserDetailsServiceAutoConfiguration::class]
)
class IstuTaskSolverApplication

fun main(args: Array<String>) {
    runApplication<IstuTaskSolverApplication>(*args)
}
