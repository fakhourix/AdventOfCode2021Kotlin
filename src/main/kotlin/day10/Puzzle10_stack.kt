package day10

import PuzzleTemplate
import java.util.*
import kotlin.collections.ArrayList

class Puzzle10Stack : PuzzleTemplate(day = 10) {

    override fun puzzleOne(answer: Int?.() -> Unit) {
        var sum = 0
        inputAsStrings.forEach { line ->
            val stack = Stack<Char>()
            line.forEach { c ->
                when {
                    c.isAnOpening() -> stack.push(c)
                    else -> {
                        if (stack.pop().getClosingFor() != c) {
                            sum += c.getPoints()
                            return@forEach
                        }
                    }
                }
            }
        }
        answer(sum)
    }

    override fun puzzleTwoLong(answer: Long?.() -> Unit) {
        var scores = ArrayList<Long>()
        inputAsStrings.forEach { line ->
            var score: Long = 0
            val stack = Stack<Char>()
            var isCorrupted = false
            line.forEach { c ->
                when {
                    c.isAnOpening() -> stack.push(c)
                    else -> {
                        if (stack.pop().getClosingFor() != c) {
                            isCorrupted = true
                            return@forEach
                        }
                    }
                }
            }
            if (!isCorrupted) {
                while (stack.isNotEmpty())
                    score = score * 5 + stack.pop().getClosingFor().getPoints2()
                scores.add(score)
                scores.sort()
            }
        }
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