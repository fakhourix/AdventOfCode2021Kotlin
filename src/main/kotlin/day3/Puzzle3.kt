package day3

import PuzzleTemplate

class Puzzle3 : PuzzleTemplate(
    day = 3,
    inputPath = "${Root.Dir}\\day3\\input"
) {

    override fun puzzleOne(answer: Int?.() -> Unit) {
        val cols = inputAsStrings[0].length
        var gamma = ""
        var alfa = ""
        for (col in 0 until cols) {
            val bitsCount = getBitsCountInColumn(inputAsStrings, col)
            val hasMoreOnes = bitsCount.ones.count > bitsCount.zeros.count
            gamma += if (hasMoreOnes) BitOne else BitZero
            alfa += if (hasMoreOnes) BitZero else BitOne
        }
        answer(gamma.toDecimal() * alfa.toDecimal())
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        var oxygenReport = inputAsStrings.toMutableList()
        var scrubberReport = inputAsStrings.toMutableList()

        var row = 0
        while (oxygenReport.size > 1) {
            val bitsCount = getBitsCountInColumn(oxygenReport, row)
            val bitCriteria = if (bitsCount.ones.count >= bitsCount.zeros.count) BitOne else BitZero
            oxygenReport.removeAll { it[row] != bitCriteria }
            row++
        }

        row = 0
        while (scrubberReport.size > 1) {
            val bitsCount = getBitsCountInColumn(scrubberReport, row)
            val bitCriteria = if (bitsCount.ones.count >= bitsCount.zeros.count) BitZero else BitOne
            scrubberReport.removeAll { it[row] != bitCriteria }
            row++
        }

        answer(oxygenReport.first().toDecimal() * scrubberReport.first().toDecimal())
    }

    private fun getBitsCountInColumn(report: List<String>, col: Int): BitsCount {
        val bitsCount = BitsCount.init()
        report.forEach {
            if (it[col] == BitOne) bitsCount.ones.inc() else bitsCount.zeros.inc()
        }
        return bitsCount
    }
}

private const val BitOne = '1'
private const val BitZero = '0'

private data class BitCount(
    val bit: Char,
    var count: Int = 0
) {
    fun inc() {
        count += 1
    }
}

private data class BitsCount(
    val ones: BitCount,
    val zeros: BitCount,
) {
    companion object {
        fun init() = BitsCount(
            ones = BitCount(BitOne),
            zeros = BitCount(BitZero)
        )
    }
}

private fun String.toDecimal() = Integer.parseInt(this, 2)