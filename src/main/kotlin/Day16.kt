class Day16(val input: List<String>) {
    private val valves = input.associate { line -> line.split(" ", "=", ";").let { it[1] to it[5].toInt() } }
    private val tunnels = input.associate {
        val parts = it.splitIgnoreEmpty(" ", ",")
        parts[1] to parts.drop(9)
    }
    private val start = valves.keys.first()
    private val timeLimit = 30

    private fun traverse(current: String, visited: Set<String>, timePassed: Int, opened: Set<String>, released: Int, flow: Int): Pair<Set<String>, Int> {
        //println("Traverse: $current, $visited, $timePassed, $opened, $released, $flow")
        val ret = mutableListOf<Pair<Set<String>, Int>>()
        if(timePassed >= timeLimit) {
            //println("$current, $opened, $released")
            return opened to released
        }
        // move without opening
        for(tunnel in tunnels[current]!!) {
            //if(tunnel !in visited) {
                ret += traverse(tunnel, visited + current, timePassed+1, opened, released+flow, flow)
            //}
        }
        if(current !in opened && valves[current]!! != 0) {
            // open the valve
            val flow = flow + valves[current]!!
            val timePassed = timePassed + 1
            val opened = opened + current
            val released = released + flow
            // check if time ran out?
            if(timePassed >= timeLimit) {
                //println("$current, $opened, $released")
                ret += (opened to released)
            } else {
                for (tunnel in tunnels[current]!!) {
                    //if(tunnel !in visited) {
                    ret += traverse(tunnel, visited + current, timePassed + 1, opened, released + flow, flow)
                    //}
                }
            }
        }
        return ret.maxByOrNull { it.second }!!
    }
    fun solvePart1(): Int {
        val solution = traverse(start, setOf(), 0, setOf(), 0, 0)
        println(solution)
        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}