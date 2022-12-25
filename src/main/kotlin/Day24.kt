class Day24(input: List<String>) {
    private val initialMap = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point2D(x, y) to c } }.toMap()
    private val maxY = input.size-1
    private val maxX = input.first().length-1
    private val origStart = initialMap.filter { it.key.y == 0 && it.value == '.' }.keys.first()
    private val origFinish = initialMap.filter { it.key.y == maxY && it.value == '.' }.keys.first()
    private val cleanMap = initialMap.filter { it.value == '#' }.toList()


    class Path(val cur: Point2D, val path: List<Point2D>)
    fun List<Pair<Point2D, Char>>.points() = this.map { it.first }
    fun solvePart1(): Int = solve(origStart, origFinish, initialMap.filter { it.value in listOf('<', '>', 'v', '^') }.toList()).first

    fun solvePart2(): Int {
        var blizzards = initialMap.filter { it.value in listOf('<', '>', 'v', '^') }.toList()
        var minutes = 0
        solve(origStart, origFinish, blizzards).let {
            minutes += it.first
            blizzards = it.second }
        solve(origFinish, origStart, blizzards).let {
            minutes += it.first
            blizzards = it.second }
        solve(origStart, origFinish, blizzards).let {
            minutes += it.first
            blizzards = it.second }
        return minutes
    }

    fun solve(start: Point2D, finish: Point2D, blizzards: List<Pair<Point2D, Char>>): Pair<Int, List<Pair<Point2D, Char>>> {
        var blizzards = blizzards
        var paths = listOf(Path(start, listOf()))
        for(minute in 1..1_000) {
            // move blizzards
            blizzards = blizzards.map { blizzard ->
                when(blizzard.second) {
                    '<' -> blizzard.first.move(Point2D.WEST).let { if(it.x <= 0) { it.copy(x=maxX-1) } else { it } }
                    '>' -> blizzard.first.move(Point2D.EAST).let { if(it.x >= maxX) { it.copy(x=1) } else { it } }
                    '^' -> blizzard.first.move(Point2D.NORTH).let { if(it.y <= 0) { it.copy(y=maxY-1) } else { it } }
                    'v' -> blizzard.first.move(Point2D.SOUTH).let { if(it.y >= maxY) { it.copy(y=1) } else { it } }
                    else -> throw IllegalStateException("asdf")
                } to blizzard.second
            }
            // Calculate moves after the blizzards move. Also check if waiting is(was) an option
            // calculate moves
            val blizzardPoints = blizzards.points().toSet()
            var alreadyThere = mutableSetOf<Point2D>()
            paths = paths.flatMap {path ->
                val options = (path.cur.adjacent() + path.cur).filter {
                    it !in blizzardPoints &&
                            (it.isIn(Point2D(1, 1), Point2D(maxX-1, maxY-1))
                                    || it == start
                                    || it == finish) }
                if(finish in options) {
                    return path.path.size+1 to blizzards
                }
                val remaining = options.mapNotNull {
                    if(it !in alreadyThere) {
                        alreadyThere.add(it)
                        it
                    } else {
                        null
                    }
                }
                remaining.map { Path(it, path.path+path.cur) }
            }
            val best = paths.minOf { it.cur.distance(finish) }
            //paths = paths.filter { it.cur.distance(finish) - 15 <= best }


            //println(paths.toList())
            if(minute % 100 == 0) {
                println("== Minute $minute (options: ${paths.size} - $best)==")
                //(cleanMap + blizzards).toMapHandleDuplicates().printChars()
            }
        }
        throw java.lang.IllegalStateException("No solution found within limits")
    }

    fun List<Pair<Point2D, Char>>.toMapHandleDuplicates(): Map<Point2D, Char> {
        val map = mutableMapOf<Point2D, Char>()
        for(i in this) {
            if(map.containsKey(i.first)) {
                val value = map[i.first]!!
                val newVal = if(value.isDigit()) {
                    (value.digitToInt()+1).digitToChar()
                } else {
                    '2'
                }
                map[i.first] = newVal
            } else {
                map[i.first] = i.second
            }
        }
        return map
    }
}