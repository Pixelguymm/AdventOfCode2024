import utils.resource

fun main() {
    val input = resource("Day3.txt")
    Day3(input).getResults()
}

class Day3(private val input: String) : Day() {
    override fun part1() = Regex("mul\\((\\d+),(\\d+)\\)").findAll(input).sumOf { op ->
        op.groupValues.drop(1).map { it.toIntOrNull() ?: 0 }.product()
    }

    override fun part2() = Regex("do(?:n't)?\\(\\)|mul\\((\\d+),(\\d+)\\)").findAll(input).let { operations ->
        var sum = 0
        var enabled = true

        operations.forEach { op ->
            when (op.value.substringBefore('(')) {
                "do" -> enabled = true
                "don't" -> enabled = false
                "mul" -> {
                    if (enabled) sum += op.groupValues.drop(1).map { it.toIntOrNull() ?: 0 }.product()
                }
            }
        }

        sum
    }

    fun List<Int>.product() = reduce { acc, i -> acc * i }
}