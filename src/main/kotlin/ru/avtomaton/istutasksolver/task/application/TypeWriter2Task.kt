package ru.avtomaton.istutasksolver.task.application

import org.springframework.stereotype.Component
import ru.avtomaton.istutasksolver.task.domain.Answer
import ru.avtomaton.istutasksolver.task.domain.ExpectedArguments
import ru.avtomaton.istutasksolver.task.domain.InputArguments
import ru.avtomaton.istutasksolver.task.domain.ValidationResult
import java.lang.StringBuilder

@Component
class TypeWriter2Task : Task {

    override val id: String
        get() = "typeWriter2"

    override val description: String
        get() = "Печатная машинка 2"

    override val expectedArguments: ExpectedArguments
        get() = ExpectedArguments(listOf(
            "N", "M", "TEXT",
        ))

    override fun validateArguments(args: InputArguments): ValidationResult {
        val errors = mutableListOf<String>()

        val n = args.values["N"]
        if (n == null || n == "") {
            errors += "аргумент N должен быть указан"
        } else if (n.toIntOrNull() == null) {
            errors += "аргумент N должен быть числом"
        }

        val m = args.values["M"]
        if (m == null || m == "") {
            errors += "аргумент M должен быть указан"
        } else if (m.toIntOrNull() == null) {
            errors += "аргумент M должен быть числом"
        }

        val text = args.values["TEXT"]
        if (text == null || text == "") {
            errors += "аргумент TEXT должен быть указан"
        }

        return ValidationResult.from(errors)
    }

    override fun solve(arguments: InputArguments): Answer {
        val (n, m, words) = parseArgs(arguments)
        val output = solveInternal(n, m, words)
        return Answer(output)
    }

    private fun parseArgs(arguments: InputArguments): Triple<Int, Int, List<String>> {
        val n = arguments.values["N"]!!.toInt()
        val m = arguments.values["M"]!!.toInt()
        val words = arguments.values["TEXT"]!!.split(" ")

        return Triple(n, m, words)
    }

    private fun solveInternal(n: Int, m: Int, words: List<String>): String {
        var emptySpace = m
        var emptyLines = n
        var note = mutableListOf<String>()
        val answers = mutableListOf<List<Int>>()

        var string = ""

        for (word in words) {
            if (string == "") {
                string += word
                emptySpace -= word.length
            } else if (string.last() == '.') {
                if (word.length + 2 <= emptySpace) {
                    string += "  $word"
                    emptySpace -= (word.length + 2)
                } else {
                    note += (string + " ".repeat(emptySpace))
                    emptyLines -= 1
                    if (emptyLines == 0) {
                        checkColumns(note, m, answers)
                        note = mutableListOf()
                        emptyLines = n
                    }
                    string = word
                    emptySpace = m - word.length
                }
            } else {
                if (word.length + 1 <= emptySpace) {
                    string += " $word"
                    emptySpace -= (word.length + 1)
                } else {
                    note += (string + " ".repeat(emptySpace))
                    emptyLines -= 1
                    if (emptyLines == 0) {
                        checkColumns(note, m, answers)
                        note = mutableListOf()
                        emptyLines = n
                    }
                    string = word
                    emptySpace = m - word.length
                }
            }
        }
        note += (string + " ".repeat(emptySpace))
        checkColumns(note, m, answers)

        val result = StringBuilder().apply {
            append("${answers.size}")
            append("\n")

            for (answer in answers) {
                val items = mutableListOf<Int>()
                items += answer.size
                for (number in answer) {
                    items += (number + 1)
                }
                append(items.joinToString(" "))
                append("\n")
            }
        }.toString()

        return result
    }

    private fun checkColumns(
        note: MutableList<String>,
        m: Int,
        answers: MutableList<List<Int>>
    ) {
        val columns = mutableListOf<Int>()
        for (i in 0..< m) {
            if (note[0][i] == ' ') {
                var isSpaceColumn = true
                for (j in 0..< note.size) {
                    if (note[j][i] != ' ') {
                        isSpaceColumn = false
                        break
                    }
                }
                if (isSpaceColumn) {
                    columns += i
                }
            }
        }
        answers.add(columns)
    }
}