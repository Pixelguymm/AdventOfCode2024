import utils.lines
import utils.resource
import utils.words
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
            .map { it.words().map { it.toIntOrNull() ?: 0 } }
            .fold(Pair(emptyList<Int>(), emptyList<Int>())) { pair, current ->
                (pair.first + current.first()) to (pair.second + current.last())
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
        .counted().entries
        .sumOf { (num, count) ->
            num * count * right.counted().toMap().getOrDefault(num, 0)
        }

    fun <T> Iterable<T>.counted() = groupBy { it }.entries
        .associate { it.key to it.value.size }
}