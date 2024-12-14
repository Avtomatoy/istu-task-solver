package ru.avtomaton.istutasksolver.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(TaskSolverException::class)
    fun handleTaskSolverException(ex: TaskSolverException): ResponseEntity<String> {
        return ResponseEntity
            .status(ex.httpStatus)
            .body(ex.message)
    }
}
