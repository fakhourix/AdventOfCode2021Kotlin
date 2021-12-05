package day2

import PuzzleTemplate

class Puzzle2 : PuzzleTemplate(day = 2) {
    override fun puzzleOne(answer: Int?.() -> Unit) {
        var horizontal = 0
        var depth = 0
        inputAsStrings.forEach { command ->
            val steps = command.getSteps()
            when {
                command.isForward() -> horizontal += steps
                command.isUp() -> depth -= steps
                command.isDown() -> depth += steps
            }
        }
        answer(horizontal * depth)
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        var horizontal = 0
        var depth = 0
        var aim = 0
        inputAsStrings.forEach { command ->
            val steps = command.getSteps()
            when {
                command.isUp() -> aim -= steps
                command.isDown() -> aim += steps
                command.isForward() -> {
                    horizontal += steps
                    depth += (aim * steps)
                }
            }
        }
        answer(horizontal * depth)
    }
}

private fun String.isUp() = this.startsWith("up")
private fun String.isDown() = this.startsWith("down")
private fun String.isForward() = this.startsWith("forward")
private fun String.getSteps() = this.split(" ")[1].toInt()