package ru.avtomaton.istutasksolver.task.application

import ru.avtomaton.istutasksolver.task.domain.Answer
import ru.avtomaton.istutasksolver.task.domain.ExpectedArguments
import ru.avtomaton.istutasksolver.task.domain.InputArguments
import ru.avtomaton.istutasksolver.task.domain.ValidationResult

interface Task {

    val id: String

    val description: String

    val expectedArguments: ExpectedArguments

    fun validateArguments(args: InputArguments): ValidationResult

    fun solve(arguments: InputArguments): Answer
}