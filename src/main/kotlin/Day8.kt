import utils.Matrix
import utils.indexed
import utils.lines
import utils.resource

fun main() {
    val field = resource("Day8.txt").lines().map { line ->
        line.toCharArray().toList()
    }

    Day8(field).getResults()
}

class Day8(field: Matrix<Char>) : Day() {
    private val antennae = field.indexed().flatten().filter { it.second != '.' }
    private val dimensions = field.indices to field.first().indices

    override fun part1(): Int {
        val antinodes = mutableSetOf<Position>()

        antennae.groupBy({ it.second }, { it.first }).values.forEach { group ->
            group.forEachIndexed { i, antenna ->
                group.toMutableList().apply { removeAt(i) }.forEach { other ->
                    val vec = antenna - other

                    (other - vec).let {
                        if (it.first in dimensions.first && it.second in dimensions.second) {
                            antinodes.add(it)
                        }
                    }
                }
            }
        }

        return antinodes.size
    }

    override fun part2(): Int {
        val antinodes = mutableSetOf<Position>()

        antennae.groupBy({ it.second }, { it.first }).values.forEach { group ->
            group.forEachIndexed { i, antenna ->
                group.toMutableList().apply { removeAt(i) }.forEach { other ->
                    val vec = antenna - other

                    var next = other

                    while (next.first in dimensions.first && next.second in dimensions.second) {
                        antinodes.add(next)

                        next = next - vec
                    }
                }
            }
        }

        return antinodes.size
    }

    operator fun Position.minus(other: Position) = (this.first - other.first) to (this.second - other.second)
}