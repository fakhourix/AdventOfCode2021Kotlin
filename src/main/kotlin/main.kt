import day1.Puzzle1
import day2.Puzzle2

fun main() {
    listOf(
        Puzzle1(),
        Puzzle2(),
    ).forEach {
        it.runPuzzle()
    }
}