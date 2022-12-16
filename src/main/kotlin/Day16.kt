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

    private var searched = 0
    private var searchTotal = 0

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

    private fun findOptions(human: Actor, elephant: Actor, opened: List<String>): List<Pair<Actor, Actor>> {
        if(human.timeToArrive == -1 && elephant.timeToArrive == -1) {
            val humanDestinations = shortestPaths.filter { it.key.first == human.destination }.filter { it.key.second !in opened }.map { it.key.second to it.value.size}
            val elephantDestinations = shortestPaths.filter { it.key.first == elephant.destination }.filter { it.key.second !in opened }.map { it.key.second to it.value.size}
            return humanDestinations.flatMap { humanDest ->
                elephantDestinations.mapNotNull { elephantDest ->
                    if(humanDest.first == elephantDest.first) {
                        null
                    } else {
                        val newHuman = human.copy(destination = humanDest.first, timeToArrive = humanDest.second)
                        val newElephant =
                            elephant.copy(destination = elephantDest.first, timeToArrive = elephantDest.second)
                        newHuman to newElephant
                    }
                }
            }
        } else if(human.timeToArrive == -1) {
            val humanDestinations = shortestPaths.filter { it.key.first == human.destination }.filter { it.key.second !in opened && it.key.second != elephant.destination }.map { it.key.second to it.value.size}
            return humanDestinations.map {  human.copy(destination = it.first, timeToArrive = it.second) to elephant }
        } else if(elephant.timeToArrive == -1) {
            val elephantDestinations = shortestPaths.filter { it.key.first == elephant.destination }.filter { it.key.second !in opened && it.key.second != human.destination }.map { it.key.second to it.value.size}
            return elephantDestinations.map { human to elephant.copy(destination = it.first, timeToArrive = it.second) }
        }
        return listOf()
    }
    data class Actor(val name: String, val destination: String, val timeToArrive: Int )

    fun tick(timePassed: Int, flow: Int, released: Int, human: Actor, elephant: Actor, opened: List<String>): Pair<List<String>, Int> {
        val bestPath = listOf("AA", "AA", "DD", "JJ", "BB", "HH", "CC", "EE")
        //val bestPath = listOf("VR", "SP", "RO", "KZ", "DI", "SO", "SC")

        //println("${bestPath.take(opened.size)} == $opened = $debug")
        // open the valve
        if(human.destination == elephant.destination && human.destination != "AA") {
            println("The Fuck: $opened, $human, $elephant")
            TODO()
        }
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
        if(timePassed == 6) {
            searched++
            println("[$timePassed] [$searched/$searchTotal] opened is now $opened, human = $human, elephant = $elephant")
        }

        val travelOptions = findOptions(human, elephant, opened);
        if(timePassed == 5) {
            travelOptions.forEach{
                println("${it.first.destination}, ${it.second.destination}")
            }
            searchTotal = travelOptions.size
        }
        if(travelOptions.isEmpty()) {
            // this universe survives. Just tick
            return tick(timePassed, flow, released, human, elephant, opened)
        } else {
            if(human.destination == elephant.destination && human.destination != "AA") {
                travelOptions.forEach{
                    println("${it.first.destination}, ${it.second.destination}")
                }
            }
            val universes =  travelOptions.map { pair ->
                if(pair.first.destination == pair.second.destination) {
                    travelOptions.forEach{
                        println("${it.first.destination}, ${it.second.destination}")
                    }
                    println("$human, $elephant, $opened")
                }
                tick(timePassed, flow, released, pair.first, pair.second, opened)
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
        val result = tick(3, 0, 0, Actor("myself", start, 1), Actor("elephant", start, 1) , listOf())
        println(result)
        return result.second
        TODO()
    }
}