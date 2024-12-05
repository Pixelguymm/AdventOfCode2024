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
        it.middle().toIntOrNull() ?: 0
    }

    override fun part2() = updates.filter { update -> rules.any { update breaks it } }.sumOf { update ->
        var fixed = update

        var i = -1

        while (++i < rules.size) {
            val rule = rules[i]

            if (fixed breaks rule) {
                val idx1 = fixed.indexOf(rule.first)
                val idx2 = fixed.indexOf(rule.second)

                fixed = fixed.toMutableList().apply {
                    val removed = removeAt(idx1)
                    add(idx2, removed)
                }

                i = -1
            }
        }

        fixed.middle().toIntOrNull() ?: 0
    }

    infix fun List<String>.follows(rule: Rule) =
        indexOf(rule.first) < (indexOfOrNull(rule.second) ?: Int.MAX_VALUE)

    infix fun List<String>.breaks(rule: Rule) = !follows(rule)

    fun <T> List<T>.indexOfOrNull(t: T) = indexOf(t).let { if (it == -1) null else it }

    fun <T> List<T>.middle() = get(size / 2)
}