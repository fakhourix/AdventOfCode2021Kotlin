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
        var minCost = Integer.MAX_VALUE
        var timesIncreased = 0
        var position = 0
        while (timesIncreased < 2) {
            val cost = costFunction(positions, position)
            if (cost < minCost) {
                minCost = cost
                timesIncreased = 0
            } else {
                timesIncreased++
            }
            position++
        }
        return minCost
    }

    private fun calculateFuelConstantRate(positions: List<Int>, pos: Int) =
        positions.map { abs(it - pos) }.sum()

    private fun calculateFuelIncreasingRate(positions: List<Int>, pos: Int) =
        positions.map { abs(it - pos).sumOfInts() }.sum()

}

private fun Int.sumOfInts() = (this * (this + 1)) / 2