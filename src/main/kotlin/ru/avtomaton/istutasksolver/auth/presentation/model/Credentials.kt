package ru.avtomaton.istutasksolver.auth.presentation.model

/**
 * Тело запроса с логином и паролем пользователя.
 */
data class Credentials(
    val login: String,
    val password: String,
)
