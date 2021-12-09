package day8

import PuzzleTemplate

class Puzzle8 : PuzzleTemplate(day = 8) {

    override fun puzzleOne(answer: Int?.() -> Unit) {
        val uniqueNrSegments = inputAsStrings.map {
            it.split(" | ")[1].split(' ').filter { p -> isUniqueSinglePattern(p) }.size
        }.sum()
        answer(uniqueNrSegments)
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        val sumOfOutputs = inputAsStrings.map { line ->
            val parts = line.split(" | ")
            val nums = parts[0].split(' ').map { it.sort() }
            val output = parts[1].split(' ').map { it.sort() }
            val input = (nums + " " + output).map { it.sort() }.toSet()

            val sixSegments = input.filter { it.length == 6 }
            val fiveSegments = input.filter { it.length == 5 }

            val one = input.first { it.length == 2 }
            val four = input.first { it.length == 4 }
            val seven = input.first { it.length == 3 }
            val eight = input.first { it.length == 7 }

            val nine = sixSegments.first { nine -> four.pluz(nine) == nine }
            val six = sixSegments.first { six -> !six.contains(one[0]) || !six.contains(one[1]) }
            val zero = sixSegments.first { it != six && it != nine }

            val two = fiveSegments.first { two -> four.pluz(two) == eight }
            val three = fiveSegments.first { three -> seven.pluz(three) == three }
            val five = fiveSegments.first { five -> one.pluz(five) == nine }

            val numMap = mapOf(
                zero to 0,
                one to 1,
                two to 2,
                three to 3,
                four to 4,
                five to 5,
                six to 6,
                seven to 7,
                eight to 8,
                nine to 9
            )

            output.map { numMap[it].toString() }.reduce { acc, s -> acc + s }.toInt()
        }.sum()
        answer(sumOfOutputs)
    }

    private fun isUniqueSinglePattern(s: String) = s.length == 2 || s.length == 4 || s.length == 3 || s.length == 7

    private fun String.sort() = this.toCharArray().sorted().toSet().joinToString("")

    private fun String.pluz(s: String) = (this + s).sort()

}