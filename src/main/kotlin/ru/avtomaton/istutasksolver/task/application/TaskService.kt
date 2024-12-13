package ru.avtomaton.istutasksolver.task.application

import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.task.domain.*
import ru.avtomaton.istutasksolver.error.InputArgumentsNotValidException
import ru.avtomaton.istutasksolver.error.TaskNotFoundException
import ru.avtomaton.istutasksolver.error.TestCaseNotFoundException

@Component
class TaskService(
    taskList: List<Task>,
    private val testCaseRepository: TaskTestCaseRepository,
) {
    private val taskMap = taskList.associateBy { it.id }

    fun getAll(): List<Task> {
        return taskMap.values.toList()
    }

    fun getExpectedArgs(taskId: String): ExpectedArguments {
        return getTaskOrThrow(taskId).expectedArguments
    }

    fun validate(taskId: String, args: InputArguments): ValidationResult {
        return getTaskOrThrow(taskId)
            .validateArguments(args)
    }

    fun solve(taskId: String, args: InputArguments): Answer {
        return getTaskOrThrow(taskId).solve(args)
    }

    suspend fun getAllTestCases(taskId: String): List<TestCase> {
        return testCaseRepository.getAll(taskId)
            .sortedBy { it.id }
    }

    suspend fun addTestCase(taskId: String, testCase: TestCase) {
        val validation = validate(taskId, testCase.arguments)
        if (!validation.isValid) {
            throw InputArgumentsNotValidException(taskId, validation)
        }
        testCaseRepository.save(taskId, testCase)
    }

    suspend fun deleteTestCase(taskId: String, testCaseId: String) {
        testCaseRepository.delete(taskId, testCaseId)
    }

    suspend fun checkTestCase(taskId: String, testCaseId: String): TestCaseCheck {
        val testCase = testCaseRepository.getById(taskId, testCaseId)
            ?: throw TestCaseNotFoundException(taskId, testCaseId)

        val answer = getTaskOrThrow(taskId).solve(testCase.arguments)

        return TestCaseCheck(
            testCase = testCase,
            actualAnswer = answer,
        )
    }

    private fun getTaskOrThrow(taskId: String): Task {
        return taskMap[taskId] ?: throw TaskNotFoundException(taskId)
    }
}
