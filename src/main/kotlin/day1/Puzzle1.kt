package day1

import PuzzleTemplate

class Puzzle1 : PuzzleTemplate(
    day = 1,
    inputPath = "${Root.Dir}\\day1\\input"
) {
    override fun puzzleOne(answer: Int?.() -> Unit) {
        answer(getNrTimesDepthIncreased(inputAsInts))
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        val windowSize = 3
        val sonarSweepReport = arrayListOf<Int>()
        var index = 0
        while (index < inputAsInts.size - windowSize) {
            ++index
            sonarSweepReport.add(inputAsInts.subList(index, index + windowSize).sum())
        }
        answer(getNrTimesDepthIncreased(sonarSweepReport))
    }
}

private fun getNrTimesDepthIncreased(list: List<Int>): Int {
    var latest = list[0]
    var nrTimesDepthIncreased = 0
    list.forEach {
        if (it > latest) nrTimesDepthIncreased++
        latest = it
    }
    return nrTimesDepthIncreased
}