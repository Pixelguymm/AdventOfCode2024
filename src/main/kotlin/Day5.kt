import utils.lines
import utils.resource
import utils.splitByWhitespace
import kotlin.text.toIntOrNull

fun main() {
    val (p1, p2) = resource("Day5.txt").splitByWhitespace()
    val rules = p1.lines().map { it.split("|") }.map { (first, second) -> first to second }
    val updates = p2.lines().map { it.split(",") }

    Day5(rules, updates).getResults()
}

typealias Rule = Pair<String, String>

class Day5(private val rules: List<Rule>, private val updates: List<List<String>>) : Day() {

    override fun part1() = updates.filter { update -> rules.all { update follows it } }.sumOf {
        it[it.size / 2].toIntOrNull() ?: 0
    }

    override fun part2() = updates.filter { update -> rules.any { !(update follows it) } }.sumOf { update ->
        var fixed = update

        var i = -1

        while (++i < rules.size) {
            val rule = rules[i]

            if (!(fixed follows rule)) {
                val idx1 = fixed.indexOf(rule.first)
                val idx2 = fixed.indexOf(rule.second)

                fixed = listOf(
                    *fixed.slice(0..<idx2).toTypedArray(),
                    rule.first, rule.second,
                    *fixed.toMutableList().apply { removeAt(idx1) }.slice(idx2+1..<fixed.size-1).toTypedArray()
                )

                i = -1
            }
        }

        fixed[fixed.size / 2].toIntOrNull() ?: 0
    }

    infix fun List<String>.follows(rule: Rule) =
        indexOf(rule.first) < (indexOfOrNull(rule.second) ?: Int.MAX_VALUE)

    fun <T> List<T>.indexOfOrNull(t: T) = indexOf(t).let { if (it == -1) null else it }
}