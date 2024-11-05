package ru.avtomaton.istutasksolver.task.domain

data class TestCase(
    val id: String,
    val arguments: InputArguments,
    val answer: Answer,
)

