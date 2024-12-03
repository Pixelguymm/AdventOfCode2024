import utils.resource

fun main() {
    val input = resource("Day3.txt")
    Day3(input).getResults()
}

class Day3(private val input: String) : Day() {
    override fun part1() = Regex("mul\\((\\d+),(\\d+)\\)").findAll(input).sumOf { op ->
        op.groupValues.drop(1).map { it.toIntOrNull() ?: 0 }.product()
    }

    override fun part2() = Regex("do(?:n't)?\\(\\)|mul\\((\\d+),(\\d+)\\)").findAll(input)
        .fold(0 to true) { (sum, enabled), op ->
            val command = op.value.substringBefore('(')

            when {
                command == "do" -> sum to true
                command == "don't" -> sum to false
                command == "mul" && enabled -> {
                    sum + op.groupValues.drop(1).map { it.toIntOrNull() ?: 0 }.product() to true
                }
                // won't happen
                else -> sum to enabled
            }
        }.first

    fun List<Int>.product() = reduce { acc, i -> acc * i }
}