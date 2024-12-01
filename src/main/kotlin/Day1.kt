import utils.lines
import utils.resource
import kotlin.math.absoluteValue

fun main() {
    val lines = resource("Day1.txt").lines()
    Day1(lines).getResults()
}

class Day1(lines: List<String>) : Day() {
    private val left: List<Int>
    private val right: List<Int>

    init {
        lines
            .map { it.split(Regex("\\s+")).mapNotNull { it.toIntOrNull() } }
            .fold(Pair(emptyList<Int>(), emptyList<Int>())) { list, current ->
                (list.first + current.first()) to (list.second + current.last())
            }
            .let { (l, r) ->
                left = l.sorted()
                right = r.sorted()
            }
    }

    override fun part1() = left
        .zip(right)
        .sumOf { (left, right) ->
            (left - right).absoluteValue
        }

    override fun part2() = left
        .counted()
        .sumOf {
            it.first * it.second * right.counted().toMap().getOrDefault(it.first, 0)
        }

    fun <T> Iterable<T>.counted() = groupBy { it }
        .map { it.key to it.value.size }
}