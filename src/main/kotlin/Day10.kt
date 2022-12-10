class Day10(val input: List<String>) {
    fun solvePart1(): Int {
        var round = 1
        var register = 1
        val history = mutableMapOf(round to register)
        input.forEach { line ->
            // execute
            val parts = line.split(" ")
            when(parts[0]) {
                "addx" -> {
                    history[round+1] = register
                    register += parts[1].toInt()
                    round += 2
                    history[round] = register
                }
                "noop" -> {
                    round += 1
                    history[round] = register
                }
            }
        }
        return (20..220 step 40).map { history[it]!! * it}.sum()


        TODO()
    }

    fun runProgram(): Map<Int, Int> {
        var round = 1
        var register = 1
        val history = mutableMapOf(round to register)
        input.forEach { line ->
            // execute
            val parts = line.split(" ")
            when(parts[0]) {
                "addx" -> {
                    history[round+1] = register
                    register += parts[1].toInt()
                    round += 2
                    history[round] = register
                }
                "noop" -> {
                    round += 1
                    history[round] = register
                }
            }
        }
        return history
    }
    fun solvePart2(): Int {
        var round = 1
        var register = 1
        val history = mutableMapOf(round to register)
        input.forEach { line ->
            // execute
            val parts = line.split(" ")
            when(parts[0]) {
                "addx" -> {
                    history[round+1] = register
                    register += parts[1].toInt()
                    round += 2
                    history[round] = register
                }
                "noop" -> {
                    round += 1
                    history[round] = register
                }
            }
        }

        var drawn = 0
        for(drawCycle in 1..240) {
            val value = history[drawCycle]
            if(drawCycle == 10) {
                //println("")
                //println("$drawCycle, $value")
            }
            if(value in (drawCycle % 40)-2 .. (drawCycle % 40)) {
                drawn++
                print("░")
            } else {
                print(" ")
            }
            if(drawCycle % 40 == 0) {
                println()
            }
        }
        return drawn
    }

}