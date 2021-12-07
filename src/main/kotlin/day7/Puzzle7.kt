package day7

import PuzzleTemplate
import kotlin.math.abs

class Puzzle7 : PuzzleTemplate(day = 7) {

    private val inputInts = inputAsStrings[0].split(',').map { it.toInt() }

    override fun puzzleOne(answer: Int?.() -> Unit) {
        answer(getMinimumFuelCost(inputInts, this::calculateFuelConstantRate))
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        answer(getMinimumFuelCost(inputInts, this::calculateFuelIncreasingRate))
    }

    private fun getMinimumFuelCost(
        positions: List<Int>,
        costFunction: (list: List<Int>, pos: Int) -> Int,
    ): Int {
        val sorted = positions.sorted()
        val min = sorted[0]
        val max = sorted[sorted.size - 1]
        var minCost = Integer.MAX_VALUE
        var timesIncreased = 0
        for (position in min..max) {
            val cost = costFunction(inputInts, position)
            if (cost < minCost) {
                minCost = cost
                timesIncreased = 0
            } else {
                timesIncreased++
            }
            if (timesIncreased == 3) {
                // No point for searching further if the rate is increasing
                break
            }
        }
        return minCost
    }

    private fun calculateFuelConstantRate(positions: List<Int>, pos: Int) =
        positions.map { abs(it - pos) }.sum()

    private fun calculateFuelIncreasingRate(positions: List<Int>, pos: Int) =
        positions.map { abs(it - pos).sumOfInts() }.sum()

}

private fun Int.sumOfInts() = (this * (this + 1)) / 2