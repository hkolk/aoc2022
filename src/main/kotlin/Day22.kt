class Day22(input: List<String>) {
    private val path = input.takeLast(1).first().let { line -> "([0-9]+|L|R)".toRegex().findAll(line).map { it.value } }.toList()
    private val map = input.dropLast(2).flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            if(c != ' ') {
                Point2D(x, y) to c
            } else {
                null
            }
        }
    }.toMap()
    val start = Point2D(x=input.first().indexOf("."), y=0)

    val directionMap = mapOf(
        0 to Point2D.EAST,
        1 to Point2D.SOUTH,
        2 to Point2D.WEST,
        3 to Point2D.NORTH
    )
    fun solvePart1(): Int {
        //println(path)
        //map.printChars()
        println(start)
        println(-1 % 4)
        var pos = start
        var direction = 0 // start going EAST
        for(step in path) {
            when(step) {
                "R" -> direction = (direction+1) % 4
                "L" -> direction = (direction-1).let { if(it < 0) { 3 } else { it} }
                else -> {
                    for(move in 1..step.toInt()) {
                        var next = pos.move(directionMap[direction]!!)
                        when(map[next]) {
                            null -> {
                                // fell of the map
                                next = when(direction) {
                                    0 -> pos.copy(x = map.filter { it.key.y == pos.y }.minOf { it.key.x })
                                    2 -> pos.copy(x = map.filter { it.key.y == pos.y }.maxOf { it.key.x })
                                    1 -> pos.copy(y = map.filter { it.key.x == pos.x }.minOf { it.key.y })
                                    3 -> pos.copy(y = map.filter { it.key.x == pos.x }.maxOf { it.key.y })
                                    else -> throw IllegalStateException("Direction $direction unknown")
                                }
                                if(map[next] == '.') {
                                    pos = next
                                }
                            }
                            '#' -> {
                                // blocked, do nothing
                            }
                            '.' -> {
                                pos = next
                            }
                        }
                    }
                }
            }
        }
        println(pos)
        println(direction)
        return((1000 * (pos.y+1)) + (4 * (pos.x+1)) + direction)


        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}