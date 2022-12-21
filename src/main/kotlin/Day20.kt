import com.ginsberg.cirkle.MutableCircularList

class Day20(input: List<String>) {
    data class Node(val origIdx: Int, val value: Long) {
        override fun toString() = value.toString()
    }
    val input = input.mapIndexed() { idx, value -> Node(idx, value.toLong()) }

    private fun solve(rounds:Int=1, decryptionKey:Long=1): Long {
        val circle = MutableCircularList(input.map {
            it.copy(value = Math.multiplyExact(it.value, decryptionKey))
        }.toMutableList())

        for(round in 1..rounds) {
            for (i in input.indices) {
                val idx = circle.indexOfFirst { it.origIdx == i }
                val node = circle.removeAt(idx)
                val newIdx = ((idx + node.value) % circle.size).toInt()
                if(newIdx == 0) {
                    circle.add(node)
                } else {
                    circle.add(newIdx, node)

                }
            }
        }
        val zeroIdx = circle.indexOfFirst { it.value == 0L }
        return circle[1000+zeroIdx].value+circle[2000+zeroIdx].value+circle[3000+zeroIdx].value
    }

    fun solvePart1() = solve()
    fun solvePart2() = solve(10, 811_589_153)

}