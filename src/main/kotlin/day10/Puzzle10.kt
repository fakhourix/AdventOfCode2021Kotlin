package day10

import PuzzleTemplate
import java.util.*

class Puzzle10 : PuzzleTemplate(day = 10) {

    override fun puzzleOne(answer: Int?.() -> Unit) {
        val sum = inputAsStrings.map { line ->
            val stack = Stack<Char>()
            var points = 0
            line.forEach { c ->
                when {
                    c.isAnOpening() -> stack.push(c)
                    else -> {
                        if (stack.pop().getClosingFor() != c) {
                            points = c.getPoints()
                            return@forEach
                        }
                    }
                }
            }
            points
        }.sum()
        answer(sum)
    }

    override fun puzzleTwoLong(answer: Long?.() -> Unit) {
        val scores = inputAsStrings.map { line ->
            var score: Long = 0
            val stack = Stack<Char>()
            var isLineCorrupted = false
            line.forEach { c ->
                when {
                    c.isAnOpening() -> stack.push(c)
                    else ->
                        if (stack.pop().getClosingFor() != c) {
                            isLineCorrupted = true
                            return@forEach
                        }
                }
            }
            when {
                isLineCorrupted -> 0
                else -> {
                    while (stack.isNotEmpty())
                        score = score * 5 + stack.pop().getClosingFor().getPoints2()
                    score
                }
            }
        }.filter { it > 0L }.sortedBy { it }
        answer(scores[scores.size / 2])
    }
}

private fun Char.isAnOpening() = listOf('(', '[', '{', '<').any { it == this }

private fun Char.getClosingFor() = when {
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