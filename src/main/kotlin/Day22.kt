import kotlin.math.absoluteValue

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
        var pos = start
        var direction = 0 // start going EAST
        for(step in path) {
            when(step) {
                "R" -> direction = (direction+1) % 4
                "L" -> direction = (4 + direction - 1) % 4
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
        return((1000 * (pos.y+1)) + (4 * (pos.x+1)) + direction)
    }
    fun extractMapFrom(start: Point2D, sideSize:Int) : Map<Point2D, Point2D> {
        return map.filter {
            it.key.isIn(start, Point2D(x=start.x+sideSize-1, y=start.y+sideSize-1))
        }.map {
            it.key - start to it.key
        }.toMap()
    }
    data class Side(val points: Map<Point2D, Point2D>, var east: Side?, var west: Side?, var South: Side?, var North: Side?) {}
    fun solvePart2(): Int {
        // calculate all sides from base map
        val sideSize = Math.sqrt((map.size / 6).toDouble()).toInt()
        println(sideSize)

        // cheating a little. Start is always assumed to be the corner
        val topMap = extractMapFrom(start, sideSize)

        val facingStart = start.copy(y = start.y + sideSize)
        val facingMap = extractMapFrom(facingStart, sideSize)

        val fromFacingToLeft: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(y = from.y, x = sideSize-1) to 3}
        val fromLeftToFacing: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(y = from.y, x = 0) to 2}

        val leftStart = facingStart.copy(x = facingStart.y - sideSize)
        val leftMap = extractMapFrom(leftStart, sideSize)

        val fromTopToLeft: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x = from.y, y=0) to 3}
        val fromLeftToTop: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x = 0, y = from.x) to 1}
        val fromTopToFacing: (Point2D) -> Pair<Point2D, Int> = {from -> Point2D(x = from.x, y=0) to 2}
        val fromFacingToTop: (Point2D) -> Pair<Point2D, Int> = {from -> Point2D(x = from.x, y=sideSize - 1) to 1}

        val backStart = leftStart.copy(x = leftStart.y - sideSize)
        val backMap = extractMapFrom(backStart, sideSize)

        val fromLeftToBack: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x=sideSize-1, y=from.y) to 4}
        val fromBackToLeft: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x=0, y=from.y) to 3}
        val fromTopToBack: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=0) to 4}
        val fromBackToTop: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=0) to 1}

        val bottomStart = facingStart.copy(y=facingStart.y+sideSize)
        val bottomMap = extractMapFrom(bottomStart, sideSize)

        val fromFacingToBottom: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x = from.x, y=0) to 5 }
        val fromBottomToFacing: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x = from.x, y=sideSize-1) to 2}
        // hell starts here TODO()
        TODO()
        /*
        val fromLeftToBottom: (Point2D) -> Pair<Point2D, Int> = { from -> Point2D(x)}
        val fromTopToBack: (Point2D) -> Pair<Point2D, Int> = { from -> }
        val fromTopToBack: (Point2D) -> Pair<Point2D, Int> = { from -> }
        val fromTopToBack: (Point2D) -> Pair<Point2D, Int> = { from -> }
        TODO()
        // thoughts
        // need to turn correctly when warping

         */
    }
    fun Point2D.isIn(a: Point2D, b: Point2D) = this.x >= a.x.coerceAtMost(b.x) &&
                this.x <= a.x.coerceAtLeast(b.x) &&
                this.y >= a.y.coerceAtMost(b.x) &&
                this.y <= a.y.coerceAtLeast(b.y)

}