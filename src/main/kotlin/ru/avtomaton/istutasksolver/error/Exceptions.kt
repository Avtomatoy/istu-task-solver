package ru.avtomaton.istutasksolver.error

import org.springframework.http.HttpStatus
import ru.avtomaton.istutasksolver.task.domain.ValidationResult

open class TaskSolverException(
    message: String,
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
) : RuntimeException(message)

class TaskNotFoundException(taskId: String) : TaskSolverException(
    message = "Задачи с идентификатором $taskId не существует",
    httpStatus = HttpStatus.NOT_FOUND,
)

class TestCaseNotFoundException(taskId: String, testCaseId: String) : TaskSolverException(
    message = "Тесткейса с идентификатором $testCaseId для задачи $taskId не существует",
    httpStatus = HttpStatus.NOT_FOUND,
)

class TestCaseAlreadyExistException(taskId: String, testCaseId: String) : TaskSolverException(
    message =  "Тесткейс с идентификатором $testCaseId для задачи $taskId уже существует",
    httpStatus = HttpStatus.CONFLICT,
)

class InputArgumentsNotValidException(taskId: String, validationResult: ValidationResult) : TaskSolverException(
    message = "Аргументы тесткейса для задачи $taskId не валидны:\n$validationResult",
    httpStatus = HttpStatus.BAD_REQUEST,
)

class UnauthorizedException : TaskSolverException(
    message = "Пользователь не авторизован",
    httpStatus = HttpStatus.UNAUTHORIZED,
)

class ForbiddenException : TaskSolverException(
    message = "Нет прав для данной операции",
    httpStatus = HttpStatus.FORBIDDEN,
)

class UserAlreadyExistException : TaskSolverException(
    message = "Пользователь с таким логином уже существует",
    httpStatus = HttpStatus.CONFLICT,
)
