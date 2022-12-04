class Day4(input: List<String>) {
    private val pairs = input.map { it.split(',', '-').map{it.toInt()} }
        .map { (it[0] .. it[1]).toSet() to  (it[2] .. it[3]).toSet() }
    fun solvePart1(): Int {
        return pairs.map {pair ->
            val union = pair.first.union(pair.second)
            if(union == pair.first || union == pair.second) {
                1
            } else {
                0
            }
        }.sum()
    }
    fun solvePart2(): Int {
        return pairs.map {pair ->
            val intersect = pair.first.intersect(pair.second)
            if(intersect.isNotEmpty()) {
                1
            } else {
                0
            }
        }.sum()
    }
}