import utils.resource

fun main() {
    val counts = resource("Day9.txt").toCharArray().map { it.digitToInt() }

    Day9(counts).getResults()
}

class Day9(private val counts: List<Int>) : Day() {
    override fun part1(): Long {
        val store = counts.flatMapIndexed { idx, count ->
            List(count) {
                if (idx % 2 == 0) idx / 2 else null
            }
        }.toMutableList()

        while (null in store) {
            store.indexOfFirst { it == null }.let { idx ->
                store[idx] = store.removeLast()
            }
        }

        return store
            .filterNotNull()
            .withIndex()
            .sumOf { (idx, num) -> (num * idx).toLong() }
    }

    override fun part2(): Long {
        var store = counts.mapIndexed { idx, count ->
            count to if (idx % 2 == 0) idx / 2 else null
        }.toMutableList()

        var i = store.lastIndex

        while (i >= 0) {
            val next = store[i]

            val slot = store.indexOfFirst { it.first >= next.first && it.second == null }

            if (next.second == null) {
                i--
                continue
            }

            if (slot >= i || slot == -1) {
                i--
                continue
            }

            store[slot] = store[slot].first - next.first to null
            store.add(slot, next)

            store[i + 1] = next.first to null
        }

        return store.flatMap {
            List(it.first) { _ ->
                (it.second?.toLong() ?: 0L)
            }
        }.withIndex().sumOf {
            it.index * it.value
        }
    }
}