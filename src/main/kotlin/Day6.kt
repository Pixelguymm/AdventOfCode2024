import utils.Matrix
import utils.getOrNull
import utils.indexed
import utils.lines
import utils.resource

fun main() {
    val field = resource("Day6.txt").lines().map { it.toCharArray().toList() }


    Day6(field).getResults()
}

enum class Direction(val x: Int = 0, val y: Int = 0) {
    Up(y = -1),
    Right(x = 1),
    Down(y = 1),
    Left(x = -1);

    fun next() = Direction.entries[(ordinal + 1) % 4]
}

typealias Position = Pair<Int, Int>

const val startChar = '^'

class Day6(private val field: Matrix<Char>) : Day() {
    val start = field.indexed().firstOrNull { it.any { it.second == startChar } }
        ?.firstOrNull { it.second == startChar }?.first ?: throw Exception("No starting point found.")

    override fun part1(): Int {
        var current = start
        var direction = Direction.Up

        val traversed = mutableSetOf<Position>()

        while (true) {
            traversed.add(current)

            val next = current.next(direction)

            when (field.getOrNull(next.first, next.second)) {
                '#' -> direction = direction.next()
                null -> break
                else -> current = next
            }
        }

        return traversed.size
    }

    override fun part2(): Int {
        var current = start
        var direction = Direction.Up

        val obstacles = mutableSetOf<Position>()

        while (true) {
            val next = current.next(direction)

            when (field.getOrNull(next.first, next.second)) {
                '#' -> direction = direction.next()
                null -> break
                else -> {
                    current.next(direction).let { n ->
                        if (n != start && n.createsLoop()) obstacles.add(next)
                    }

                    current = next
                }
            }
        }

        return obstacles.size
    }

    fun Position.next(d: Direction) = (first + d.y) to (second + d.x)

    fun Position.createsLoop(): Boolean {
        val traversed = mutableSetOf<Pair<Position, Direction>>()

        var current = start
        var direction = Direction.Up

        while (true) {
            traversed.add(current to direction)

            val next = current.next(direction)
            val nextChar = field.getOrNull(next.first, next.second)

            when {
                nextChar == '#' || next == this -> direction = direction.next()
                nextChar == null -> return false
                else -> {
                    if ((next to direction) in traversed) {
                        return true
                    } else {
                        current = next
                    }
                }
            }
        }
    }
}