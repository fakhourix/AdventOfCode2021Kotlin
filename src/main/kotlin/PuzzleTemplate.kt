import java.io.File

abstract class PuzzleTemplate(
    private val day: Int,
    private val inputPath: String = "${Root.Dir}\\day${day}\\input",
) {
    open fun puzzleOne(answer: Int?.() -> Unit) = answer(null)
    open fun puzzleTwo(answer: Int?.() -> Unit) = answer(null)

    open fun puzzleOneLong(answer: Long?.() -> Unit) = answer(null)
    open fun puzzleTwoLong(answer: Long?.() -> Unit) = answer(null)

    val inputAsStrings = File(inputPath).bufferedReader().readLines()
    val inputAsInts: List<Int> by lazy { inputAsStrings.map { it.toInt() } }

    fun printInput() = inputAsStrings.forEach { println(it) }

    fun runPuzzle() {
        puzzleOne { this?.let { println("Puzzle $day.1: Answer: $this") } }
        puzzleTwo { this?.let { println("Puzzle $day.2: Answer: $this") } }
        puzzleOneLong { this?.let { println("Puzzle $day.1: Answer: $this") } }
        puzzleTwoLong { this?.let { println("Puzzle $day.2: Answer: $this") } }
        println("------------------------------------")
    }
}