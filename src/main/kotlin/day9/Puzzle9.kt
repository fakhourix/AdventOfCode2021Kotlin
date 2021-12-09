package day9

import PuzzleTemplate
import java.util.*
import kotlin.collections.ArrayList

class Puzzle9 : PuzzleTemplate(day = 9) {

    override fun puzzleOne(answer: Int?.() -> Unit) {
        val matrix = Matrix()
        inputAsStrings.forEach { line ->
            matrix.addRow(
                ArrayList(line.map {
                    Entry(it.digitToInt())
                })
            )
        }
        matrix.commit()
        matrix.forEach { e ->
            val neighbors = e.neighbors
            when {
                neighbors.any { it.isLowPoint } || e.value == 9 -> { /*skip*/ }
                e.value == 0 -> e.isLowPoint = true
                else -> e.isLowPoint = neighbors.all { it.value > e.value }
            }
        }
        answer(matrix.filter { it.isLowPoint }.map { it.value + 1 }.sum())
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        val matrix = Matrix()
        inputAsStrings.forEach { line ->
            matrix.addRow(
                ArrayList(line.map {
                    Entry(it.digitToInt())
                })
            )
        }
        matrix.commit()
        val basinSizes = ArrayList<Int>()
        matrix.forEach { e, row, col ->
            val neighbors = matrix.getNeighbors(row, col)
            when {
                neighbors.any { it.isLowPoint } || e.value == 9 -> { /*skip*/ }
                e.value == 0 -> {
                    e.isLowPoint = true
                    e.visited = true
                    basinSizes.add(getBasinSize(e))
                }
                else -> {
                    e.isLowPoint = neighbors.all { it.value > e.value }
                    if (e.isLowPoint) {
                        e.visited = true
                        basinSizes.add(getBasinSize(e))
                    }
                }
            }
        }
        basinSizes.sort()
        answer(basinSizes.takeLast(3).reduce { acc, i -> acc * i })
    }

    private fun getBasinSize(e: Entry): Int {
        val basin = ArrayList<Entry>()
        basin.add(e)

        val stack = Stack<Entry>()
        e.neighbors.filter { it.value < 9 }.forEach { entry ->
            entry.visited = true
            stack.push(entry)
            basin.add(entry)
        }
        while (stack.isNotEmpty()) {
            val n = stack.pop()
            if (n.value < 9) {
                n.neighbors.filter { it.value < 9 && !it.visited }.forEach { entry ->
                    entry.visited = true
                    basin.add(entry)
                    stack.push(entry)
                }
            }
        }
        return basin.size
    }

}

data class Entry(
    val value: Int,
    var isLowPoint: Boolean = false,
    var visited: Boolean = false,
    var neighbors: List<Entry> = listOf(),
) {
    override fun toString(): String {
        return value.toString()
    }
}

class Matrix {

    private var matrix = ArrayList<ArrayList<Entry>>()
    private var cols: Int = 0
    private var rows: Int = 0

    fun addRow(data: ArrayList<Entry>) {
        matrix.add(data)
    }

    fun commit() {
        rows = matrix.size
        cols = matrix.first().size
        this.forEach { e, r, c ->
            e.neighbors = getNeighbors(r, c)
        }
    }

    fun filter(predicate: (Entry) -> Boolean): List<Entry> {
        return matrix.map { it.filter { e -> predicate(e) } }.reduce { acc, list -> acc + list }
    }

    fun forEach(action: (Entry) -> Unit) {
        matrix.forEach { row ->
            row.forEach { e ->
                action.invoke(e)
            }
        }
    }

    fun forEach(action: (Entry, Int, Int) -> Unit) {
        matrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, entry ->
                action(entry, rowIndex, colIndex)
            }
        }
    }

    fun getNeighbors(row: Int, col: Int): List<Entry> {
        val neighbors = ArrayList<Entry>()
        val upperIndex = row - 1
        val lowerIndex = row + 1
        val leftIndex = col - 1
        val rightIndex = col + 1

        if (upperIndex >= 0) neighbors.add(matrix[upperIndex][col])
        if (lowerIndex < rows) neighbors.add(matrix[lowerIndex][col])
        if (leftIndex >= 0) neighbors.add(matrix[row][leftIndex])
        if (rightIndex < cols) neighbors.add(matrix[row][rightIndex])

        return neighbors
    }

}