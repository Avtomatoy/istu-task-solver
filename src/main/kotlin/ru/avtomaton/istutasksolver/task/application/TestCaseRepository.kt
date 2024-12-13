package ru.avtomaton.istutasksolver.task.application

import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.task.domain.TestCase
import ru.avtomaton.istutasksolver.error.TestCaseAlreadyExistException

interface TaskTestCaseRepository {

    suspend fun getAll(taskId: String): List<TestCase>

    suspend fun getById(taskId: String, testCaseId: String): TestCase?

    suspend fun save(taskId: String, testCase: TestCase)

    suspend fun delete(taskId: String, testCaseId: String)
}

@Component
class InMemoryTaskTestCaseRepository : TaskTestCaseRepository {

    private val taskTestCases = mutableMapOf<String, List<TestCase>>()

    override suspend fun getAll(taskId: String): List<TestCase> {
        return taskTestCases[taskId].orEmpty()
    }

    override suspend fun getById(taskId: String, testCaseId: String): TestCase? {
        return taskTestCases[taskId]
            ?.let { it.firstOrNull { testCase -> testCase.id == testCaseId } }
    }

    override suspend fun save(taskId: String, testCase: TestCase) {
        val cases = taskTestCases.getOrPut(taskId) { listOf() }

        if (cases.any { it.id == testCase.id }) {
            throw TestCaseAlreadyExistException(taskId, testCase.id)
        }

        taskTestCases[taskId] = cases.plus(testCase)
    }

    override suspend fun delete(taskId: String, testCaseId: String) {
        val cases = taskTestCases[taskId].orEmpty()

        val updatedCases = mutableListOf<TestCase>()
        updatedCases.addAll(cases.filter { it.id != testCaseId })

        taskTestCases[taskId] = updatedCases
    }
}
