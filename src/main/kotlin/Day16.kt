class Day16(val input: List<String>) {
    private val valves = input.associate { line -> line.split(" ", "=", ";").let { it[1] to it[5].toInt() } }
    private val tunnels = input.associate {
        val parts = it.splitIgnoreEmpty(" ", ",")
        parts[1] to parts.drop(9)
    }
    private val start = "AA"
    private val timeLimit = 30
    
    fun pathTo(from: String, to: String, taken:List<String> = listOf()): List<String>? {
        if(from == to) { return taken + from }
        return tunnels[from]!!.filter { it !in taken }.mapNotNull {
            pathTo(it, to, taken + from)
        }.minByOrNull { it.size }
        TODO()
    }

    fun moveAndOpen(current: String, paths: Map<Pair<String, String>, List<String>>, timePassed: Int, opened: List<String>, flow: Int, released: Int): Pair<List<String>, Int> {
        //val bestPath = listOf("AA", "DD", "BB", "JJ", "HH", "EE", "CC")
        val bestPath = listOf("VR", "SP", "RO", "KZ", "DI", "SO", "SC")

        //println("${bestPath.take(opened.size)} == $opened = $debug")
        // open the valve
        val opened = opened + current
        val debug = (bestPath.take(opened.size) == opened)
        if(debug) {
            println("[$timePassed] arrived at $current through $opened with flow $flow and released $released")
        }
        val timePassed = timePassed + 1
        val flow = flow + valves[current]!!
        val released = released + flow

        val options = paths.filter { it.key.first == current }.filter { it.key.second !in opened }
        if(options.isEmpty()) {
            if(debug) {
                println("[$timePassed] $opened to ${((timeLimit - timePassed) * flow) + released}")
            }
            // everything has been opened. Let time flow
            return opened to ((timeLimit - timePassed) * flow) + released
        } else {
            return options.mapNotNull { option ->
                if (timePassed + option.value.size + 1 >= timeLimit) {
                    // no time to travel (size) + open (1)
                    null
                } else {
                    moveAndOpen(
                        option.key.second,
                        paths,
                        timePassed + option.value.size,
                        opened,
                        flow,
                        released + (flow * option.value.size) )
                }
            }.maxByOrNull { it.second } ?: (opened to ((timeLimit - timePassed) * flow) + released)
        }
    }

    fun solvePart1(): Int {
        val importantValves = valves.filter { it.value > 0 }.map { it.key } + start
        val shortestPaths = importantValves.flatMap { active ->
            (importantValves - active).map { to ->
                (active to to) to pathTo(active, to)!!.drop(1)
            }
        }.map{it.first to it.second!!}.toMap()
        shortestPaths.forEach { path ->
            println("${path.key} -> ${path.value}")
        }
        val max = moveAndOpen(start, shortestPaths, 0, listOf(), 0, 0)
        println(max)
        return max.second

        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}