package day10

import PuzzleTemplate

class Puzzle10 : PuzzleTemplate(day = 10) {

    private val validChunks = listOf("()", "[]", "{}", "<>")

    override fun puzzleOne(answer: Int?.() -> Unit) {
        val sum = inputAsStrings.map { line ->
            var input = line
            while (validChunks.any { input.contains(it) })
                validChunks.forEach { input = input.replace(it, "") }
            if (input.all { it.isOpening() }) 0
            else input.first { it.isClosure() }.getPoints()
        }.sum()
        answer(sum)
    }

    override fun puzzleTwoLong(answer: Long?.() -> Unit) {
        val scores = inputAsStrings.map { line ->
            var input = line
            while (validChunks.any { input.contains(it) })
                validChunks.forEach { input = input.replace(it, "") }
            if (input.any { it.isClosure() }) 0
            else {
                var score = 0L
                input.map { it.getClosure() }.reversed().joinToString("").forEach { c ->
                    score = score * 5 + c.getPoints2()
                }
                score
            }
        }.filter { it > 0L }.sortedBy { it }
        answer(scores[scores.size / 2])
    }
}

private fun Char.isClosure() = listOf(')', ']', '}', '>').any { it == this }
private fun Char.isOpening() = listOf('(', '[', '{', '<').any { it == this }

private fun Char.getClosure() = when {
    this == '(' -> ')'
    this == '[' -> ']'
    this == '{' -> '}'
    else -> '>'
}

private fun Char.getPoints() = when {
    this == ')' -> 3
    this == ']' -> 57
    this == '}' -> 1197
    else -> 25137
}

private fun Char.getPoints2() = when {
    this == ')' -> 1
    this == ']' -> 2
    this == '}' -> 3
    else -> 4
}