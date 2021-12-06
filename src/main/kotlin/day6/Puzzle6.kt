package day6

import PuzzleTemplate

class Puzzle6 : PuzzleTemplate(day = 6) {

    override fun puzzleOneLong(answer: Long?.() -> Unit) {
        val initialState = inputAsStrings[0].split(',').map { it.toInt() }
        answer(
            getFishCount(
                days = 80,
                initialState = initialState,
            )
        )
    }

    override fun puzzleTwoLong(answer: Long?.() -> Unit) {
        val initialState = inputAsStrings[0].split(',').map { it.toInt() }
        answer(
            getFishCount(
                days = 256,
                initialState = initialState,
            )
        )
    }

    private fun getFishCount(days: Int, initialState: List<Int>): Long {
        var fishTimerCountMap = initialState.groupBy { it }.mapValues { it.value.size.toLong() }
        for (i in 0 until days) {
            var temp = HashMap<Int, Long>()
            fishTimerCountMap.forEach { (timer, count) ->
                when (timer) {
                    0 -> {
                        temp[6] = temp.getOrDefault(6, 0) + count
                        temp[8] = count
                    }
                    else -> {
                        temp[timer - 1] = temp.getOrDefault(timer - 1, 0) + count
                    }
                }
            }
            fishTimerCountMap = HashMap(temp)
        }
        return fishTimerCountMap.values.sum()
    }

}