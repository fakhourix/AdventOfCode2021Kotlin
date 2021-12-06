package day5

import PuzzleTemplate
import java.awt.geom.Line2D
import java.awt.geom.Point2D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Puzzle5 : PuzzleTemplate(day = 5) {

    private val lines = mutableListOf<Line2D.Double>()
    private val points = mutableSetOf<Point2D>()

    override fun puzzleOne(answer: Int?.() -> Unit) {
        readLines(includeDiagonals = false)
        answer(countOverlappingPoints())
    }

    override fun puzzleTwo(answer: Int?.() -> Unit) {
        readLines(includeDiagonals = true)
        answer(countOverlappingPoints())
    }

    private fun readLines(includeDiagonals: Boolean) {
        lines.clear()
        points.clear()
        val regex = "\\d+".toRegex()
        inputAsStrings.forEach {
            val res = regex.findAll(it)
            val x0 = res.elementAt(0).value.toDouble()
            val y0 = res.elementAt(1).value.toDouble()
            val x1 = res.elementAt(2).value.toDouble()
            val y1 = res.elementAt(3).value.toDouble()
            if (includeDiagonals || (x0 == x1 || y0 == y1)) {
                val line = Line2D.Double(Point2D.Double(x0, y0), Point2D.Double(x1, y1))
                lines.add(line)
                points.addAll(getPointsOnLine(line))
            }
        }
    }

    private fun countOverlappingPoints(): Int {
        val overlappingPointsCount = mutableMapOf<Point2D, Int>()
        points.forEach { point ->
            lines.forEach { line ->
                if (line.containsPoint(point)) {
                    overlappingPointsCount[point] =
                        overlappingPointsCount.getOrDefault(point, 0) + 1
                }
            }
        }
        return overlappingPointsCount.filter { it.value >= 2 }.size
    }

    private fun getPointsOnLine(line: Line2D): List<Point2D> {
        val pointsInLine = mutableListOf<Point2D>()

        val p1 = line.p1
        val p2 = line.p2
        pointsInLine.add(p1)
        pointsInLine.add(p2)

        val slope = getSlope(p1, p2)
        val x1 = p1.x
        val y1 = p1.y
        val x2 = p2.x
        val y2 = p2.y

        when {
            slope == 0 -> {
                val yStart = min(y1, y2).toInt()
                val ySteps = abs(y2 - y1).toInt()
                val yCoef = if (y1 == y2) 0 else 1

                val xStart = min(x1, x2).toInt()
                val xSteps = abs(x2 - x1).toInt()
                val xCoef = if (x1 == x2) 0 else 1

                val steps = max(xSteps, ySteps) - 1
                for (step in 1..steps) {
                    pointsInLine.add(
                        getPoint(
                            x = (xStart + step * xCoef).toDouble(),
                            y = (yStart + step * yCoef).toDouble(),
                        )
                    )
                }
            }
            slope > 0 -> {
                val steps = abs((p2.x - p1.x)).toInt()
                for (i in 1..steps) {
                    pointsInLine.add(getPoint(x1 + i, y1 + i))
                }
            }
            slope < 0 -> {
                val steps = abs((p2.x - p1.x)).toInt()
                val xCoef = if (x1 < x2) 1 else -1
                val yCoef = if (y1 > y2) -1 else 1
                for (i in 1..steps) {
                    pointsInLine.add(getPoint(x1 + i * xCoef, y1 + i * yCoef))
                }
            }
        }
        return pointsInLine
    }

    private fun getSlope(p1: Point2D, p2: Point2D): Int {
        val denominator = p2.x - p1.x
        if (denominator == 0.0) return 0
        return ((p2.y - p1.y) / denominator).toInt()
    }

    private fun getPoint(x: Double, y: Double) = Point2D.Double(x, y)

}

private fun Line2D.containsPoint(point: Point2D) =
    abs(p1.distance(p2).round(5)) == abs((p1.distance(point) + p2.distance(point)).round(5))

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}