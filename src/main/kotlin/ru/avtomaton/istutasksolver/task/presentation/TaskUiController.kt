package ru.avtomaton.istutasksolver.task.presentation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.avtomaton.istutasksolver.task.application.TaskService
import ru.avtomaton.istutasksolver.task.domain.Answer
import ru.avtomaton.istutasksolver.task.domain.ExpectedArguments
import ru.avtomaton.istutasksolver.task.domain.InputArguments
import ru.avtomaton.istutasksolver.task.domain.ValidationResult

@Controller
@RequestMapping(path = ["/task-ui"])
class TaskUiController(
    private val taskService: TaskService,
) {

    @GetMapping
    fun index(model: Model): String {
        val tasks = taskService.getAll()
        model.addAttribute("tasks", tasks)
        return "taskList"
    }

    @GetMapping("/{taskId}")
    fun taskForm(@PathVariable taskId: String, model: Model): String {
        val expectedArgs = taskService.getExpectedArgs(taskId)

        val taskFormData = TaskFormData(
            taskId = taskId,
            expectedArgs = expectedArgs,
        )

        model.addAttribute("taskFormData", taskFormData)

        return "taskSolveForm"
    }

    @PostMapping("/{taskId}/solve")
    fun solveTask(
        @PathVariable taskId: String,
        @ModelAttribute("taskFormData") taskFormData: TaskFormData,
        model: Model,
    ): String {
        val validationResult = taskService.validate(taskId, taskFormData.inputArgs)

        var responseFormData = TaskFormData(
            taskId = taskId,
            expectedArgs = taskService.getExpectedArgs(taskId),
            inputArgs = taskFormData.inputArgs,
            validationResult = validationResult,
        )

        if (validationResult.isValid) {
            val answer = taskService.solve(taskId, taskFormData.inputArgs)
            responseFormData = responseFormData.copy(
                answer = answer,
            )
        }

        model.addAttribute("taskFormData", responseFormData)
        return "taskSolveForm"
    }

    @GetMapping("/{taskId}/testcase")
    suspend fun testCaseList(@PathVariable taskId: String, model: Model): String {
        val testCases = taskService.getAllTestCases(taskId)

        model.addAttribute("taskId", taskId)
        model.addAttribute("testCases", testCases)

        return "taskTestCaseList"
    }

    @GetMapping("/{taskId}/testcase/add")
    suspend fun addTestCase(@PathVariable taskId: String, model: Model): String {
        val expectedArgs = taskService.getExpectedArgs(taskId)
        model.addAttribute("taskId", taskId)
        model.addAttribute("expectedArgs", expectedArgs)

        return "taskTestCaseForm"
    }

    data class TaskFormData(
        val taskId: String,
        val expectedArgs: ExpectedArguments = ExpectedArguments(),
        val inputArgs: InputArguments = InputArguments(),
        val validationResult: ValidationResult = ValidationResult.ok(),
        val answer: Answer? = null,
    )
}
