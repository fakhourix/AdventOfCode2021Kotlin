package day1

import PuzzleTemplate

class Puzzle1 : PuzzleTemplate(day = 1) {
    override fun puzzleOne(answer: Int?.() -> Unit) {
        val nrTimesDepthIncreased = inputAsInts.windowed(2).filter { (a, b) ->
            a < b
        }.count()
        answer(nrTimesDepthIncreased)
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        val nrTimesDepthIncreased = inputAsInts
            .windowed(3)
            .windowed(2)
            .filter { (a, b) ->
                a.sum() < b.sum()
            }.count()
        answer(nrTimesDepthIncreased)
    }
}