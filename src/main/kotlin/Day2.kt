class Day2(input: List<String>) {
    val plays = input.map {
        val parts = it.split(" ")
        parts[0] to parts[1]
    }
    fun play(plays: List<Pair<String, String>>): Int {
        return plays.map { play ->
            when(play) {
                "A" to "X" -> 3+1 // rock/rock = draw
                "A" to "Y" -> 6+2 // rock/paper = win
                "A" to "Z" -> 0+3 // rock/scissors = loss
                "B" to "X" -> 0+1 // paper/rock = loss
                "B" to "Y" -> 3+2 // paper/paper = draw
                "B" to "Z" -> 6+3 // paper/scissors = win
                "C" to "X" -> 6+1 // scissors/rock = win
                "C" to "Y" -> 0+2 // scissors/paper = loss
                "C" to "Z" -> 3+3 // scissors/scissors = draw
                else -> throw IllegalStateException()
            }
        }.sum()
    }
    fun solvePart1() = play(plays)
    fun solvePart2(): Int {
        return play(plays.map { play ->
            when (play) {
                "A" to "X" -> "A" to "Z" // rock/scissors = loss
                "A" to "Y" -> "A" to "X" // rock/rock = draw
                "A" to "Z" -> "A" to "Y" // rock/paper = win
                "B" to "X" -> "B" to "X" // paper/rock = loss
                "B" to "Y" -> "B" to "Y" // paper/paper = draw
                "B" to "Z" -> "B" to "Z" // paper/scissors = win
                "C" to "X" -> "C" to "Y" // scissors/paper = loss
                "C" to "Y" -> "C" to "Z" // scissors/scissors = draw
                "C" to "Z" -> "C" to "X" // scissors/rock = win
                else -> throw IllegalStateException()
            }
        })
    }
}