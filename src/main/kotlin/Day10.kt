class Day10(val input: List<String>) {

    fun solvePart1(): Int {
        val history = runProgram()
        return (20..220 step 40).sumOf { history[it]!! * it}
    }


    fun solvePart2(): Int {
        val history = runProgram()
        var drawn = 0
        for(drawCycle in 1..240) {
            if(history[drawCycle] in (drawCycle % 40)-2 .. (drawCycle % 40)) {
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

    private fun runProgram(): Map<Int, Int> {
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
}