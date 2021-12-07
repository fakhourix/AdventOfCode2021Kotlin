import day1.Puzzle1
import day2.Puzzle2
import day3.Puzzle3
import day4.Puzzle4
import day5.Puzzle5
import day6.Puzzle6
import day7.Puzzle7

fun main() {
    listOf(
        Puzzle1(),
        Puzzle2(),
        Puzzle3(),
        Puzzle4(),
        Puzzle5(),
        Puzzle6(),
        Puzzle7(),
    ).forEach {
        it.runPuzzle()
    }
}