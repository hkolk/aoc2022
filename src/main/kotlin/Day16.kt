class Day16(val input: List<String>) {
    private val valves = input.associate { line -> line.split(" ", "=", ";").let { it[1] to it[5].toInt() } }
    private val tunnels = input.associate {
        val parts = it.splitIgnoreEmpty(" ", ",")
        parts[1] to parts.drop(9)
    }
    private val start = "AA"
    private val timeLimit = 30

    private val importantValves = valves.filter { it.value > 0 }.map { it.key } + start
    private val shortestPaths = importantValves.flatMap { active ->
        (importantValves - active).map { to ->
            (active to to) to pathTo(active, to)!!.drop(1)
        }
    }.associate { it.first to it.second!! }

    private fun pathTo(from: String, to: String, taken:List<String> = listOf()): List<String>? {
        if(from == to) { return taken + from }
        return tunnels[from]!!.filter { it !in taken }.mapNotNull {
            pathTo(it, to, taken + from)
        }.minByOrNull { it.size }
    }

    private fun moveAndOpen(current: String, paths: Map<Pair<String, String>, List<String>>, timePassed: Int, opened: List<String>, flow: Int, released: Int): Pair<List<String>, Int> {
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

    data class Actor(val name: String, val destination: String, val timeToArrive: Int )

    fun tick(timePassed: Int, flow: Int, released: Int, human: Actor, elephant: Actor, opened: List<String>): Pair<List<String>, Int> {
        val bestPath = listOf("AA", "AA", "DD", "JJ", "BB", "HH", "CC", "EE")
        //val bestPath = listOf("VR", "SP", "RO", "KZ", "DI", "SO", "SC")

        //println("${bestPath.take(opened.size)} == $opened = $debug")
        // open the valve

        if(timePassed >= timeLimit) {
            return opened to released
        }
        // tick a minute off
        val timePassed = timePassed + 1
        val released = released + flow
        val human = human.copy(timeToArrive = human.timeToArrive - 1)
        val elephant = elephant.copy(timeToArrive = elephant.timeToArrive - 1)

        var flow = flow
        var opened = opened

        // can anyone open yet?
        if(human.timeToArrive == 0) {
            flow += valves[human.destination]!!
            opened = opened + human.destination
        }
        if(elephant.timeToArrive == 0) {
            flow += valves[elephant.destination]!!
            opened = opened + elephant.destination
        }

        val debug = (bestPath.take(opened.size) == opened)
        if(debug) {
            println("[$timePassed] opened is now $opened, human = $human, elephant = $elephant")
        }

        if(human.timeToArrive == -1) {
            val options =
                shortestPaths.filter { it.key.first == human.destination }.filter { it.key.second !in opened }
            // if options is empty, just keep ticking
            if (options.isEmpty()) {
                tick(timePassed, flow, released, human, elephant, opened)
            } else {
                options.map { option ->
                    val newHuman = human.copy(destination = option.key.second, timeToArrive = option.value.size)
                    tick(timePassed, flow, released, newHuman, elephant, opened)
                }.maxBy { it.second }
            }
        }
        // can anyone move yet?
        val movers = actors.filter { it.timeToArrive == -1 } // people that have arrived
        if(movers.isEmpty()) {
            // this universe survives. Just tick
            return tick(timePassed, flow, released, actors, opened)
        } else {
            // all movers pick an option


            val universes =  movers.map { actor ->
                // options
                val options =
                    shortestPaths.filter { it.key.first == actor.destination }.filter { it.key.second !in opened }
                // if options is empty, just keep ticking
                if (options.isEmpty()) {
                    tick(timePassed, flow, released, actors, opened)
                } else {
                    options.map { option ->
                        val newActor = actor.copy(destination = option.key.second, timeToArrive = option.value.size)
                        val newActors = actors.filter { it.name != newActor.name } + newActor
                        tick(timePassed, flow, released, newActors, opened)
                    }.maxBy { it.second }
                }
                // pick winner
            }
            return universes.maxBy { it.second }
        }
    }
    fun solvePart1(): Int {
        val max = moveAndOpen(start, shortestPaths, 0, listOf(), 0, 0)
        return max.second
    }
    fun solvePart2(): Int {
        val actors = listOf(Actor("myself", start, 1), Actor("elephant", start, 1))
        val result = tick(3, 0, 0, actors, listOf())
        println(result)
        TODO()
    }
}