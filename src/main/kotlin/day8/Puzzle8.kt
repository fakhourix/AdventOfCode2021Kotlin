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
            val splitted = line.split(" | ")
            val nums = splitted[0].split(' ').map { it.sort() }
            val output = splitted[1].split(' ').map { it.sort() }
            val input = (nums + " " + output).map { it.sort() }.toMutableSet()

            val one = input.first { it.length == 2 }
            val four = input.first { it.length == 4 }
            val seven = input.first { it.length == 3 }
            val eight = input.first { it.length == 7 }

            val sixDigits = input.filter { it.length == 6 }.toMutableList()
            val fiveDigits = input.filter { it.length == 5 }.toMutableList()

            val six = sixDigits.first { six -> !six.contains(one[0]) || !six.contains(one[1]) }
            sixDigits.remove(six)

            val zero = sixDigits.first { zero -> four.pluz(zero) == eight }
            sixDigits.remove(zero)

            val nine = sixDigits.first()

            val five = fiveDigits.first { five -> one.pluz(five) == nine }
            fiveDigits.remove(five)

            val three = fiveDigits.first { three -> four.pluz(three) == nine }
            fiveDigits.remove(three)

            val two = fiveDigits.first()

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
                nine to 9)

            output.map { numMap[it].toString() }.reduce { acc, s -> acc + s }.toInt()
        }.sum()
        answer(sumOfOutputs)
    }

    private fun isUniqueSinglePattern(s: String) = s.length == 2 || s.length == 4 || s.length == 3 || s.length == 7

    private fun String.sort() = this.toCharArray().sorted().toSet().joinToString("")

    private fun String.pluz(s: String) = (this + s).sort()

}