class Day1(val input: List<String>) {
    fun solvePart1(): Int {
        val input = input.map { if(it.isNotEmpty()) { it.toInt() } else { 0 }  }

        var elves = mutableListOf<Int>()
        var accu = 0
        input.forEach{
            if(it == 0) {
                elves.add(accu)
                accu = 0
            } else {
                accu += it
            }
        }
        elves.add(accu)
        return elves.max()
    }
    fun solvePart2(): Int {
        val input = input.map { if(it.isNotEmpty()) { it.toInt() } else { 0 }  }

        var elves = mutableListOf<Int>()
        var accu = 0
        input.forEach{
            if(it == 0) {
                elves.add(accu)
                accu = 0
            } else {
                accu += it
            }
        }
        elves.add(accu)
        return elves.sortedDescending().take(3).sum()
    }
}