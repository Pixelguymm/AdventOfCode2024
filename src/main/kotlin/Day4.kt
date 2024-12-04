import utils.indexed
import utils.lines
import utils.resource

fun main() {
    val input = resource("Day4.txt")
    Day4(input).getResults()
}

class Day4(input: String) : Day() {
    val indexed = input.lines().map {
        it.toCharArray().toList()
    }.indexed().flatten()
    val breadth = indexed.maxOf { it.first.second }

    val rows = indexed.groupBy { it.first.first }
    val cols = indexed.groupBy { it.first.second }
    val diag1 = indexed.groupBy { it.first.first - it.first.second }
    val diag2 = indexed.groupBy { breadth - it.first.first - it.first.second }

    override fun part1() = listOf(rows, cols, diag1, diag2).sumOf { dim ->
        dim.values.sumOf { line ->
            Regex("(?=XMAS|SAMX)").findAll(line.joinToString("") { it.second.toString() }).count()
        }
    }

    override fun part2(): Any {
        fun Map<Int, List<Pair<Pair<Int, Int>, Char>>>.getCenters() = values.flatMap { line ->
            Regex("(?=MAS|SAM)")
                .findAll(line.joinToString("") { it.second.toString() })
                .map { line[it.range.first + 1].first }
        }

        val centers = diag2.getCenters()

        return diag1.getCenters().count { center ->
            center in centers
        }
    }
}