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

            val six = sixDigits.first { !it.contains(one[0]) || !it.contains(one[1]) }
            sixDigits.remove(six)

            val zero = sixDigits.first { four.pluz(it) == eight }
            sixDigits.remove(zero)

            val nine = sixDigits.first()

            val five = fiveDigits.first { one.pluz(it) == nine }
            fiveDigits.remove(five)

            val three = fiveDigits.first { four.pluz(it) == nine }
            fiveDigits.remove(three)

            val two = fiveDigits.first()

            val numMap = HashMap<String, Int>()
            numMap[zero] = 0
            numMap[one] = 1
            numMap[two] = 2
            numMap[three] = 3
            numMap[four] = 4
            numMap[five] = 5
            numMap[six] = 6
            numMap[seven] = 7
            numMap[eight] = 8
            numMap[nine] = 9

            output.map { numMap[it].toString() }.reduce { acc, s -> acc + s }.toInt()
        }.sum()
        answer(sumOfOutputs)
    }

    private fun isUniqueSinglePattern(s: String) = s.length == 2 || s.length == 4 || s.length == 3 || s.length == 7

    private fun String.sort() = this.toCharArray().sorted().toSet().joinToString("")

    private fun String.pluz(s: String) = (this + s).sort()

}