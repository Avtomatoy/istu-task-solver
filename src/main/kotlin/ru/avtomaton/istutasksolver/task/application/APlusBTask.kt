package ru.avtomaton.istutasksolver.task.application

import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.task.domain.Answer
import ru.avtomaton.istutasksolver.task.domain.ExpectedArguments
import ru.avtomaton.istutasksolver.task.domain.InputArguments
import ru.avtomaton.istutasksolver.task.domain.ValidationResult

@Component
class APlusBTask : Task {

    override val id: String
        get() = "aPlusB"

    override val description: String
        get() = "A + B"

    override val expectedArguments: ExpectedArguments
        get() = ExpectedArguments(listOf(
            "A", "B",
        ))

    override fun validateArguments(args: InputArguments): ValidationResult {
        val errors = mutableListOf<String>()

        val a = args.values["A"]
        if (a == null || a == "") {
            errors += "аргумент A должен быть указан"
        } else if (a.toIntOrNull() == null) {
            errors += "агрумент A должен быть числом"
        }

        val b = args.values["B"]
        if (b == null || b == "") {
            errors += "аргумент B должен быть указан"
        } else if (b.toIntOrNull() == null) {
            errors += "агрумент B должен быть числом"
        }

        return ValidationResult.from(errors)
    }

    override fun solve(arguments: InputArguments): Answer {
        val a = arguments.values["A"]!!.toInt()
        val b = arguments.values["B"]!!.toInt()

        val c = a + b

        return Answer(
            value = c.toString(),
        )
    }
}