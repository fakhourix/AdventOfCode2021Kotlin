package day10

import PuzzleTemplate
import java.util.*

class Puzzle10Stack : PuzzleTemplate(day = 10) {

    override fun puzzleOne(answer: Int?.() -> Unit) {
        val sum = inputAsStrings.map { line ->
            val stack = Stack<Char>()
            var score = 0
            line.forEach { c ->
                when {
                    c.isOpening() -> stack.push(c)
                    else -> {
                        if (stack.pop().getClosure() != c) {
                            score = c.getPoints()
                            return@forEach
                        }
                    }
                }
            }
            score
        }.sum()
        answer(sum)
    }

    override fun puzzleTwoLong(answer: Long?.() -> Unit) {
        val scores = inputAsStrings.map { line ->
            var score: Long = 0
            val stack = Stack<Char>()
            var isCorrupted = false
            line.forEach { c ->
                when {
                    c.isOpening() -> stack.push(c)
                    else -> {
                        if (stack.pop().getClosure() != c) {
                            isCorrupted = true
                            return@forEach
                        }
                    }
                }
            }
            if (isCorrupted) 0
            else {
                while (stack.isNotEmpty())
                    score = score * 5 + stack.pop().getClosure().getPoints2()
                score
            }
        }.filter { it > 0 }.sortedBy { it }
        answer(scores[scores.size / 2])
    }
}

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