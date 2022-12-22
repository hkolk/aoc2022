class Day21(val input: List<String>) {

    private val monkeys = input.mapNotNull { line ->
        line.splitIgnoreEmpty(" ", ":").let {
            if (it[1].toIntOrNull() != null) {
                Monkey.IntMonkey(it[0], it[1].toLong())
            } else {
                Monkey.EquationMonkey(it[0], it[1], it[3], it[2][0])
            }
        }
    }.associateBy { it.name }

    sealed class Monkey(open val name: String){
        class IntMonkey(override val name: String, val value: Long): Monkey(name)
        class EquationMonkey(override val name: String, val a: String, val b: String, val operation: Char): Monkey(name) {
            fun solve(aVal: Long, bVal: Long): Long {
                return when(operation) {
                    '+' -> Math.addExact(aVal, bVal)
                    '-' -> Math.subtractExact(aVal, bVal)
                    '*' -> Math.multiplyExact(aVal, bVal)
                    '/' -> Math.divideExact(aVal, bVal)
                    else -> throw IllegalStateException("Unknown operation: $operation")
                }
            }
        }
    }
    fun solvePart1(): Long {
        val numbers = monkeys.mapNotNull { (key, value) ->
            if (value is Monkey.IntMonkey) {
                key to value.value
            } else {
                null
            }
        }.toMap().toMutableMap()
        val equations = monkeys.mapNotNull {
            if(it.value is Monkey.EquationMonkey) {
                it.value as Monkey.EquationMonkey
            } else {
                null
            }
        }


        while(!numbers.containsKey("root")) {
            equations.filter { !numbers.containsKey(it.name) }.forEach {monkey ->
                if(numbers.containsKey(monkey.a) && numbers.containsKey(monkey.b)) {
                    numbers[monkey.name] = monkey.solve(numbers[monkey.a]!!, numbers[monkey.b]!!)
                }
            }
        }
        return numbers["root"]!!

        TODO()

    }
    fun solve(numbers: Map<String, Long>): Int {
        val numbers = numbers.toMutableMap()
        val equations = monkeys.mapNotNull {
            if(it.value is Monkey.EquationMonkey) {
                it.value as Monkey.EquationMonkey
            } else {
                null
            }
        }

        val rootMonkey = monkeys["root"]!! as Monkey.EquationMonkey
        while(numbers[rootMonkey.a] == null || numbers[rootMonkey.b] == null ) {
            equations.filter { !numbers.containsKey(it.name) }.forEach {monkey ->
                if(numbers.containsKey(monkey.a) && numbers.containsKey(monkey.b)) {
                    numbers[monkey.name] = monkey.solve(numbers[monkey.a]!!, numbers[monkey.b]!!)
                }
            }
        }
        println("${numbers["humn"]} = ${numbers[rootMonkey.a]} == ${numbers[rootMonkey.b]}, ${numbers[rootMonkey.a]!! - numbers[rootMonkey.b]!!}")
        val cmp = numbers[rootMonkey.a]!! - numbers[rootMonkey.b]!!
        return if(cmp == 0L) {
            0
        } else if(cmp < 0) {
            -1
        } else {
            1
        }
    }

    fun solvePart2(): Long {
        val numbers = monkeys.mapNotNull { (key, value) ->
            if (value is Monkey.IntMonkey) {
                key to value.value
            } else {
                null
            }
        }.toMap().toMutableMap()
        for (i in 1L..1000L step 1L) {
            numbers["humn"] = i
            if (solve(numbers) == 0) {
                return i
            }
        }
        var low = 0L
        var high = Long.MAX_VALUE
        while(true) {
            val mid = low + ((high-low)/2)
            println("mid: $mid")
            numbers["humn"] = mid
            var cmp = -1
            try {
                cmp = solve(numbers)
            } catch (e:ArithmeticException) {
                // assume if it doesn't fit in a Long... its not the answer :)
                println("exception")
            }
            if(cmp == 0) {
                return mid
            } else if (cmp < 0) {
                println("[$high, $low, $mid]")
                high = mid - 1
            } else {
                low = mid + 1
            }
        }
        TODO()
    }
}