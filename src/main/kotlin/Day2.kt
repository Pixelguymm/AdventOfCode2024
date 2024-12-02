import utils.lines
import utils.resource
import utils.words
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    val reports = resource("Day2.txt").lines().map { it.words().map { it.toIntOrNull() ?: 0 } }
    Day2(reports).getResults()
}

class Day2(private val reports: List<List<Int>>) : Day() {
    override fun part1() = reports.count { report -> report.isSafe() }

    override fun part2() = reports.count { report ->
        (listOf(report) + report.indices.map { i ->
            report.toMutableList().apply { removeAt(i) }
        }).any { it.isSafe() }
    }

    fun List<Int>.isSafe() = windowed(2).map { (it.first() - it.last()) }.let { diffs ->
        diffs.distinctBy { it.sign }.size == 1 && diffs.all { it.absoluteValue in 1..3 }
    }
}