package ru.avtomaton.istutasksolver.task.domain

data class ValidationResult(
    val isValid: Boolean,
    val errorMessages: List<String>,
) {

    override fun toString(): String {
        return errorMessages.joinToString(separator = "\n")
    }

    companion object {

        fun from(errorMessages: List<String>): ValidationResult {
            val isValid = errorMessages.isEmpty()
            return ValidationResult(isValid, errorMessages)
        }

        fun ok(): ValidationResult {
            return ValidationResult(isValid = true, emptyList())
        }
    }
}
