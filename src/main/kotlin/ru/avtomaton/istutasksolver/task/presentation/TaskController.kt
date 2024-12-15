package ru.avtomaton.istutasksolver.task.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.avtomaton.istutasksolver.auth.application.AuthorizationService
import ru.avtomaton.istutasksolver.auth.domain.UserRole
import ru.avtomaton.istutasksolver.task.application.Task
import ru.avtomaton.istutasksolver.task.application.TaskService
import ru.avtomaton.istutasksolver.task.domain.TestCase
import ru.avtomaton.istutasksolver.task.domain.TestCaseCheck

@RestController
@RequestMapping("/task")
class TaskController(
    private val taskService: TaskService,
    private val authorizationService: AuthorizationService,
) {

    @GetMapping
    fun tasks(): ResponseEntity<List<Task>> {
        val tasks = taskService.getAll()
        return ResponseEntity.ok(tasks)
    }

    @PostMapping("/{taskId}/testcase")
    suspend fun addTestCase(
        @PathVariable taskId: String,
        @RequestBody testCase: TestCase,
    ) {
        authorizationService.authorize(UserRole.SUPPORT, UserRole.ADMIN)
        taskService.addTestCase(taskId, testCase)
    }

    @DeleteMapping("/{taskId}/testcase/{testCaseId}")
    suspend fun deleteTestCase(
        @PathVariable taskId: String,
        @PathVariable testCaseId: String,
    ) {
        authorizationService.authorize(UserRole.ADMIN)
        taskService.deleteTestCase(taskId, testCaseId)
    }

    @PostMapping("/{taskId}/testcase/{testCaseId}/check")
    suspend fun checkTestCases(
        @PathVariable taskId: String,
        @PathVariable testCaseId: String,
    ): ResponseEntity<TestCaseCheck> {
        val check = taskService.checkTestCase(taskId, testCaseId)
        return ResponseEntity.ok(check)
    }
}
