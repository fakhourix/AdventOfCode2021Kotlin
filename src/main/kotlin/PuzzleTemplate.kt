import java.io.File

abstract class PuzzleTemplate(
    private val day: Int,
    inputPath: String,
) {
    open fun puzzleOne(answer: Int?.() -> Unit) = answer(null)
    open fun puzzleTwo(answer: Int?.() -> Unit) = answer(null)

    val inputAsStrings = File(inputPath).bufferedReader().readLines()
    val inputAsInts: List<Int> by lazy { inputAsStrings.map { it.toInt() } }

    fun runPuzzle() {
        puzzleOne { this?.let { println("Puzzle $day.1: Answer: $this") } }
        puzzleTwo { this?.let { println("Puzzle $day.2: Answer: $this") } }
    }
}