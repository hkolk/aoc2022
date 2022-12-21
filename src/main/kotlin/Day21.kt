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
    fun solve(numbers: Map<String, Long>): Boolean {
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
        return numbers[rootMonkey.a]!! == numbers[rootMonkey.b]!!
    }
    fun solvePart2(): Long {
        val numbers = monkeys.mapNotNull { (key, value) ->
            if (value is Monkey.IntMonkey) {
                key to value.value
            } else {
                null
            }
        }.toMap().toMutableMap()
        for(i in 3352886133500..3352886134000 step 1L) {
            numbers["humn"] = i.toLong()
            if(solve(numbers)) {
                return i
            }
        }
        TODO()
    }
}