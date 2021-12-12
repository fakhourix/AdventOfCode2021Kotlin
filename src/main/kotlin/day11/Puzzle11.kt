package day11

import PuzzleTemplate
import java.util.*
import kotlin.collections.ArrayList

class Puzzle11 : PuzzleTemplate(day = 11) {

    private val octopusMap = OctopusMap()

    init {
        inputAsStrings.forEach { line -> octopusMap.addRow(ArrayList(line.map { Entry(it.digitToInt()) })) }
        octopusMap.commit()
    }

    override fun puzzleOne(answer: Int?.() -> Unit) {
        var flashes = 0
        for (step in 0 until 100) {
            octopusMap.forEach { e -> e.incEnergyLevel() }
            octopusMap.processFlashedOctopuses()
            octopusMap.resetEnergyLevels()

            flashes += octopusMap.filter { it.energyLevel == 0 }.size
        }
        answer(flashes)
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        var step = 0
        while (step++ < Integer.MAX_VALUE) {
            octopusMap.forEach { e -> e.incEnergyLevel() }
            octopusMap.processFlashedOctopuses()
            octopusMap.resetEnergyLevels()

            if (octopusMap.filter { it.energyLevel != 0 }.isEmpty())
                break
        }
        answer(step)
    }
}

private fun OctopusMap.processFlashedOctopuses() {
    val unprocessedOctopuses = Stack<Entry>()
    this.filter { it.flashed }.forEach { e -> e.neighbors.forEach { unprocessedOctopuses.push(it) } }
    while (unprocessedOctopuses.isNotEmpty()) {
        val octopus = unprocessedOctopuses.pop()
        if (!octopus.flashed) {
            octopus.incEnergyLevel()
            if (octopus.flashed)
                octopus.neighbors.filter { !it.flashed }.forEach { unprocessedOctopuses.push(it) }
        }
    }
}

private fun OctopusMap.resetEnergyLevels() {
    this.forEach { e ->
        if (e.flashed) e.energyLevel = 0
        e.flashed = false
    }
}

private typealias OctopusMap = Matrix

private data class Entry(
    var energyLevel: Int,
    var flashed: Boolean = false,
    var neighbors: List<Entry> = listOf(),
) {
    override fun toString(): String {
        return energyLevel.toString()
    }

    fun incEnergyLevel() {
        energyLevel++
        if (energyLevel > 9)
            flashed = true
    }
}

private class Matrix {

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

    private fun getNeighbors(row: Int, col: Int): List<Entry> {
        val neighbors = ArrayList<Entry>()
        val upperIndex = row - 1
        val lowerIndex = row + 1
        val leftIndex = col - 1
        val rightIndex = col + 1

        if (upperIndex >= 0) neighbors.add(matrix[upperIndex][col])
        if (lowerIndex < rows) neighbors.add(matrix[lowerIndex][col])
        if (leftIndex >= 0) neighbors.add(matrix[row][leftIndex])
        if (rightIndex < cols) neighbors.add(matrix[row][rightIndex])

        if (upperIndex >= 0) {
            if (leftIndex >= 0) neighbors.add(matrix[upperIndex][leftIndex])
            if (rightIndex < cols) neighbors.add(matrix[upperIndex][rightIndex])
        }

        if (lowerIndex < rows) {
            if (leftIndex >= 0) neighbors.add(matrix[lowerIndex][leftIndex])
            if (rightIndex < cols) neighbors.add(matrix[lowerIndex][rightIndex])
        }

        return neighbors
    }

}