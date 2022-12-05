class Day5(private val input: List<String>) {
    private val moves = input.dropWhile { it.isNotEmpty() }.drop(1).map { Move.fromString(it) }

    data class Move(val count:Int, val from:Int, val to:Int) {
        companion object {
            fun fromString(line: String): Move {
                val parts = line.split(' ').mapNotNull { it.toIntOrNull() }
                return Move(parts[0], parts[1], parts[2])
            }
        }
    }
    fun solvePart1(): String {
        val stacks = createStacks(input.takeWhile { it.isNotEmpty() }.reversed().drop(1))


        moves.forEach { move ->
            val subStack = (1 .. move.count).map { stacks[move.from]!!.removeLast() }
            stacks[move.to]!!.addAll(subStack)
        }
        return stacks.map { it.value.last() }.joinToString("")
    }
    fun solvePart2(): String {

        val stacks = createStacks(input.takeWhile { it.isNotEmpty() }.reversed().drop(1))

        moves.forEach { move ->
            val subStack = (1 .. move.count).map { stacks[move.from]!!.removeLast() }
            stacks[move.to]!!.addAll(subStack.reversed())
        }
        return stacks.map { it.value.last() }.joinToString("")
    }

    private fun createStacks(input: List<String>): Map<Int, ArrayDeque<Char>> {
        val numStacks = (input.last().length + 1) / 4
        val stacks = (1..numStacks).associateWith { ArrayDeque<Char>() }
        input.forEach { line ->
            for (i in 1..numStacks) {
                val name = line[i * 4 - 3]
                if (name != ' ') {
                    stacks[i]?.add(name)
                }
            }
        }
        return stacks
    }
}