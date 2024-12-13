import utils.concat
import utils.lines
import utils.resource
import utils.words

fun main() {
    val equations = resource("Day7.txt").lines().map { line ->
        val (res, parts) = line.split(": ")

        res.toLong() to parts.words().map { it.toLong() }
    }

    Day7(equations).getResults()
}

class Day7(private val equations: List<Pair<Long, List<Long>>>) : Day() {
    override fun part1() = equations.sumOf { (res, parts) ->
        var nums = setOf<Long>(parts.first())

        parts.drop(1).forEach { p ->
            nums = nums.flatMap {
                setOf(it + p, it * p)
            }.filter { it <= res }.toSet()
        }

        if (res in nums) res else 0
    }

    override fun part2() = equations.sumOf { (res, parts) ->
        var nums = setOf<Long>(parts.first())

        parts.drop(1).forEach { p ->
            nums = nums.flatMap {
                setOf(it + p, it * p, it concat p)
            }.filter { it <= res }.toSet()
        }

        if (res in nums) res else 0
    }
}