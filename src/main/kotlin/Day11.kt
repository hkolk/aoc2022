
class Day11(val input: List<String>) {
    data class Monkey(
        val id: Int,
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val divisibleBy: Long,
        val monkeyTrue: Int,
        val monkeyFalse: Int,
        var inspections: Int = 0) {

        fun throws(decrease: Boolean = true): List<Pair<Long, Int>> {
            val ret =  items.map { item ->
                val level = operation(item)
                val decreased = if(decrease) { level / 3 } else { level }
                if(decreased % divisibleBy == 0L) {
                    decreased to monkeyTrue
                } else {
                    decreased to monkeyFalse
                }
            }
            inspections += items.size
            items.clear()
            return ret
        }

        fun addItem(item: Long) {
            this.items.add(item)
        }

        companion object {
            fun fromInput(input: List<String>): Monkey {
                val id = input[0].split(" ", ":")[1].toInt()
                val items = input[1].splitIgnoreEmpty(" ", ",").drop(2).map { it.toLong() }.toMutableList()
                val operation: (Long) -> Long = input[2].splitIgnoreEmpty(" ").let {
                    when {
                        it[4] == "*" && it[5] == "old" -> {old -> Math.multiplyExact(old, old)}
                        it[4] == "*" -> {old -> Math.multiplyExact(old, it[5].toLong())}
                        it[4] == "+" -> {old -> Math.addExact(old, it[5].toLong())}
                        else -> throw IllegalStateException("Unknown operation: $it")
                    }
                }

                val monkeyTrue = input[4].splitIgnoreEmpty(" ").last().toInt()
                val monkeyFalse = input[5].splitIgnoreEmpty(" ").last().toInt()
                val divisibleBy = input[3].splitIgnoreEmpty(" ").last().toLong()
                return Monkey(id, items, operation, divisibleBy, monkeyTrue, monkeyFalse)
            }
        }
    }
    private fun solve(decrease: Boolean = true, rounds: Int = 20): Long {
        val monkeys = input.chunked(7).map{ Monkey.fromInput(it)}
        val reducer = monkeys.map{it.divisibleBy}.multiply()
        for(round in 1..rounds) {
            monkeys.forEach { monkey ->
                val throws = monkey.throws(decrease)
                throws.forEach { (item, to) ->
                    monkeys.first{ it.id == to }.addItem(item % reducer)
                }
            }
        }

        return monkeys.map{it.inspections}.sortedDescending().take(2).multiply()
    }

    fun solvePart1() = solve(true, 20)
    fun solvePart2() = solve(false, 10_000)
}