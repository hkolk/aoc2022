import kotlin.math.absoluteValue
import kotlin.math.sqrt

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

    companion object {
        const val EAST = 0
        const val SOUTH = 1
        const val WEST = 2
        const val NORTH = 3
    }

    val directionMap = mapOf(
        EAST  to Point2D.EAST,
        SOUTH to Point2D.SOUTH,
        WEST  to Point2D.WEST,
        NORTH to Point2D.NORTH
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
    private fun extractMapFrom(start: Point2D, sideSize:Int) : Map<Point2D, Point2D> {
        return map.filter {
            it.key.isIn(start, Point2D(x=start.x+sideSize-1, y=start.y+sideSize-1))
        }.map {
            it.key - start to it.key
        }.toMap()
    }
    data class Warp(val next: (Point2D) -> Point2D, val nextSide: Int, val nextDirection: Int)
    data class Side(val name: String, val points: Map<Point2D, Point2D>, val east: Warp, val west: Warp, val south: Warp, val north: Warp) {}
    fun solvePart2(): Int {
        // calculate all sides from base map
        val sideSize = sqrt((map.size / 6).toDouble()).toInt()
        println(sideSize)

        // cheating a little. Start is always assumed to be the corner
        val topMap = extractMapFrom(start, sideSize)

        val facingStart = start.copy(y = start.y + sideSize)
        val facingMap = extractMapFrom(facingStart, sideSize)
        val fromTopToFacing = Warp({from -> Point2D(x = from.x, y=0)}, 2, SOUTH) // SOUTH to SOUTH
        val fromFacingToTop = Warp({from -> Point2D(x = from.x, y=sideSize - 1)}, 1, NORTH) // NORTH to NORTH


        val leftStart = facingStart.copy(x = facingStart.y - sideSize)
        val leftMap = extractMapFrom(leftStart, sideSize)

        val fromTopToLeft = Warp({ from -> Point2D(x = from.y, y=0)}, 3, SOUTH ) // WEST to SOUTH
        val fromLeftToTop = Warp({ from -> Point2D(x = 0, y = from.x)}, 1, EAST) // NORTH to EAST
        val fromFacingToLeft = Warp({ from -> Point2D(y = from.y, x = sideSize-1)},3, WEST) // WEST to WEST
        val fromLeftToFacing = Warp({ from -> Point2D(y = from.y, x = 0)}, 2, EAST) // EAST to EAST

        val backStart = leftStart.copy(x = leftStart.y - sideSize)
        val backMap = extractMapFrom(backStart, sideSize)

        val fromLeftToBack = Warp({ from -> Point2D(x=sideSize-1, y=from.y)},  4, WEST) // WEST to WEST
        val fromBackToLeft = Warp({ from -> Point2D(x=0, y=from.y)}, 3, EAST) // EAST to EAST
        val fromTopToBack = Warp({ from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=0)}, 4, SOUTH) // NORTH to SOUTH
        val fromBackToTop = Warp({ from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=0)}, 1, SOUTH) // NORTH to SOUTH

        val bottomStart = facingStart.copy(y=facingStart.y+sideSize)
        val bottomMap = extractMapFrom(bottomStart, sideSize)

        val fromFacingToBottom = Warp({ from -> Point2D(x = from.x, y=0)}, 5, SOUTH ) // SOUTH to SOUTH
        val fromBottomToFacing = Warp({ from -> Point2D(x = from.x, y=sideSize-1)}, 2, NORTH) // NORTH to NORTH
        val fromLeftToBottom = Warp({ from -> Point2D(x=0, y=(from.x - sideSize + 1).absoluteValue)}, 5, EAST) // SOUTH to EAST
        val fromBottomToLeft = Warp({ from -> Point2D(x=(from.y - sideSize + 1).absoluteValue, y=sideSize-1)}, 3, NORTH) // WEST to NORTH
        val fromBackToBottom = Warp({ from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=sideSize-1)}, 5, NORTH) // SOUTH to NORTH
        val fromBottomToBack = Warp({ from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=sideSize-1)}, 4, NORTH) // SOUTH to NORTH

        val rightStart = bottomStart.copy(x=facingStart.x+sideSize)
        val rightMap = extractMapFrom(rightStart, sideSize)

        val fromTopToRight = Warp({ from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=sideSize-1)}, 6, WEST) // EAST to WEST
        val fromRightToTop = Warp({ from -> Point2D(x=(from.x - sideSize + 1).absoluteValue, y=sideSize-1)}, 1, WEST) // EAST to WEST
        val fromFacingToRight = Warp({ from -> Point2D(x=(from.y - sideSize + 1).absoluteValue, y=0)}, 6, SOUTH) // EAST to SOUTH
        val fromRightToFacing = Warp({ from -> Point2D(x=sideSize-1, y=(from.x - sideSize + 1).absoluteValue}, 2, WEST) // NORTH to WEST
        val fromBackToRight = Warp({from -> Point2D(x=(from.y - sideSize + 1).absoluteValue, y=sideSize-1)}, 6, NORTH) // WEST to NORTH
        val fromRightToBack = Warp({ from -> Point2D(x=0, y=(from.x - sideSize + 1).absoluteValue)}, 4, EAST) // SOUTH to EAST
        val fromBottomToRight = Warp({ from -> Point2D(y = from.y, x = 0)}, 6, EAST) // EAST to EAST
        val fromRightToBottom = Warp({ from -> Point2D(x=sideSize-1, y=from.y)},5, WEST) // WEST to WEST

        val topSide = Side("top", topMap, west = fromTopToLeft, east = fromTopToRight, north = fromTopToBack, south = fromTopToFacing)
        val facingSide = Side("facing", facingMap, west = fromFacingToLeft, east = fromFacingToRight, north = fromFacingToTop, south = fromFacingToBottom)
        val leftSide = Side("left", leftMap, west = fromLeftToBack, east = fromLeftToFacing, north = fromLeftToTop, south = fromLeftToBottom)
        val backSide = Side("back", backMap, west = fromBackToRight, east = fromBackToLeft, north = fromBackToTop, south = fromBackToBottom)
        val bottomSide = Side("bottom", bottomMap, west=fromBottomToLeft, east=fromBottomToRight, north=fromBottomToFacing, south=fromBottomToBack)
        val rightSide = Side("right", rightMap, west=fromRightToBottom, east=fromRightToTop, north=fromRightToFacing, south=fromRightToBack)
        val cube = listOf(topSide, facingSide, leftSide, backSide, bottomSide, rightSide)

        // time to simulate
        var pos = Point2D(0, 0)
        var direction = 0 // start going EAST
        var side = cube[0]
        for(step in path) {
            when(step) {
                "R" -> direction = (direction+1) % 4
                "L" -> direction = (4 + direction - 1) % 4
                else -> {
                    for(move in 1..step.toInt()) {
                        var next = pos.move(directionMap[direction]!!)
                        if(!side.points.containsKey(next)) {
                            // fall off
                            val warp = when(direction) {
                                EAST -> side.east
                                WEST -> side.west
                                SOUTH -> side.south
                                NORTH -> side.north
                                else -> throw IllegalStateException("Unknown direction: $direction")
                            }
                            next = warp.next(pos)
                            // check if we can go there
                            println("= from ${side.name} to ${cube[warp.nextSide-1].name}")
                            println(" = From $pos to $next")
                            println(" = Actual: ${side.points[pos]} to ${cube[warp.nextSide-1].points[next]}")
                            println(" = From $direction to ${warp.nextDirection}")
                            if(map[cube[warp.nextSide-1].points[next]!!]!! == '.') {
                                pos = next
                                side = cube[warp.nextSide-1]
                                direction = warp.nextDirection
                            }
                        } else {
                            if(map[side.points[next]!!]!! == '.') {
                                pos = next
                            } // else it's #, so don't move
                        }
                    }
                }
            }
        }
        return((1000 * (pos.y+1)) + (4 * (pos.x+1)) + direction)
        TODO()
        // thoughts
        // need to turn correctly when warping
    }
    fun Point2D.isIn(a: Point2D, b: Point2D) = this.x >= a.x.coerceAtMost(b.x) &&
                this.x <= a.x.coerceAtLeast(b.x) &&
                this.y >= a.y.coerceAtMost(b.x) &&
                this.y <= a.y.coerceAtLeast(b.y)

}