import com.ginsberg.cirkle.MutableCircularList

class Day20(input: List<String>) {
    data class Node(val origIdx: Int, val value: Long) {
        override fun toString() = value.toString()
    }
    val input = input.mapIndexed() { idx, value -> Node(idx, value.toLong()) }
    fun solvePart1(): Long {
        val circle = MutableCircularList(input.toMutableList())
        //println(circle)
        for(i in input.indices) {
            val idx = circle.indexOfFirst { it.origIdx == i }
            val node = circle.removeAt(idx)
            var newIdx = ((idx + node.value) % circle.size).toInt()
            //println("[$i] $node has newIdx: $newIdx")
            if(newIdx == 0) {
                circle.add(node)
            } else {
                circle.add(newIdx, node)

            }
            //println(circle)
        }
        val zeroIdx = circle.indexOfFirst { it.value == 0L }
        //println("${circle[1000+zeroIdx].value}+${circle[2000+zeroIdx].value}+${circle[3000+zeroIdx].value}")
        return circle[1000+zeroIdx].value+circle[2000+zeroIdx].value+circle[3000+zeroIdx].value
    }
    fun solvePart2(): Long {
        val circle = MutableCircularList(input.map {it.copy(value = Math.multiplyExact(it.value, 811_589_153)) }.toMutableList())

        //println(circle)
        for(round in 1..10) {
            for (i in input.indices) {
                val idx = circle.indexOfFirst { it.origIdx == i }
                val node = circle.removeAt(idx)
                var newIdx = ((idx + node.value) % circle.size).toInt()
                //println("[$i] $node has newIdx: $newIdx")
                if(newIdx == 0) {
                    circle.add(node)
                } else {
                    circle.add(newIdx, node)

                }
                //println(circle)
            }
        }
        val zeroIdx = circle.indexOfFirst { it.value == 0L }
        println("${circle[1000+zeroIdx].value}+${circle[2000+zeroIdx].value}+${circle[3000+zeroIdx].value}")
        return circle[1000+zeroIdx].value+circle[2000+zeroIdx].value+circle[3000+zeroIdx].value
    }
}