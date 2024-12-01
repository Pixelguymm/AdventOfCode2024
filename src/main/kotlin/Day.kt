import kotlin.system.measureTimeMillis

abstract class Day {
    abstract fun part1(): Any

    abstract fun part2(): Any

    fun getResults() {
        measureTimeMillis { println("Part 1: ${part1()}") }
            .also { println("Completed in ${it}ms") }

        measureTimeMillis { println("Part 1: ${part2()}") }
            .also { println("Completed in ${it}ms") }
    }
}
