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
        //println(sideSize)

        val cube = if(sideSize == 4 ) {
            createExampleCube(sideSize)
        } else {
            createRealCube(sideSize)
        }


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
                            if(side.points[pos]!!.y == 5000 && side.points[pos]!!.x == 0) {
                                println(side.points.size)
                                println("= from ${side.name} to ${cube[warp.nextSide - 1].name}")
                                println(" = From $pos to $next")
                                println(" = Actual: ${side.points[pos]} to ${cube[warp.nextSide - 1].points[next]}")
                                println(" = From $direction to ${warp.nextDirection}")
                                //TODO()
                            }
                            if(map[cube[warp.nextSide-1].points[next]!!]!! == '.') {
                                pos = next
                                side = cube[warp.nextSide-1]
                                direction = warp.nextDirection
                                //println("${side.points[pos]!!.y+1}.0 ${side.points[pos]!!.x+1}.0 $direction")
                            }
                        } else {
                            if(map[side.points[next]!!]!! == '.') {
                                pos = next
                                //println("${side.points[pos]!!.y+1}.0 ${side.points[pos]!!.x+1}.0 $direction")
                            } // else it's #, so don't move
                        }
                    }
                }
            }
        }
        //println("= End ${side.name}")
        //println(" = At $pos")
        //println(" = Actual: ${side.points[pos]}")
        //println(" = Dir $direction")
        return((1000 * (side.points[pos]!!.y+1)) + (4 * (side.points[pos]!!.x+1)) + direction)
    }



    private fun createExampleCube(sideSize: Int): List<Side> {

        // cheating a little. Start is always assumed to be the corner
        val topMap = extractMapFrom(start, sideSize)

        val facingStart = start.copy(y = start.y + sideSize)
        val facingMap = extractMapFrom(facingStart, sideSize)
        val fromTopToFacing = Warp({ from -> Point2D(x = from.x, y = 0) }, 2, SOUTH) // SOUTH to SOUTH
        val fromFacingToTop = Warp({ from -> Point2D(x = from.x, y = sideSize - 1) }, 1, NORTH) // NORTH to NORTH


        val leftStart = facingStart.copy(x = facingStart.x - sideSize)
        val leftMap = extractMapFrom(leftStart, sideSize)
        //println(leftStart)
        //leftMap.forEach { println(it)}

        val fromTopToLeft = Warp({ from -> Point2D(x = from.y, y = 0) }, 3, SOUTH) // WEST to SOUTH
        val fromLeftToTop = Warp({ from -> Point2D(x = 0, y = from.x) }, 1, EAST) // NORTH to EAST
        val fromFacingToLeft = Warp({ from -> Point2D(y = from.y, x = sideSize - 1) }, 3, WEST) // WEST to WEST
        val fromLeftToFacing = Warp({ from -> Point2D(y = from.y, x = 0) }, 2, EAST) // EAST to EAST

        val backStart = leftStart.copy(x = leftStart.y - sideSize)
        val backMap = extractMapFrom(backStart, sideSize)

        val fromLeftToBack = Warp({ from -> Point2D(x = sideSize - 1, y = from.y) }, 4, WEST) // WEST to WEST
        val fromBackToLeft = Warp({ from -> Point2D(x = 0, y = from.y) }, 3, EAST) // EAST to EAST
        val fromTopToBack = Warp({ from -> Point2D(x = (from.x - sideSize + 1).absoluteValue, y = 0) }, 4, SOUTH) // NORTH to SOUTH
        val fromBackToTop = Warp({ from -> Point2D(x = (from.x - sideSize + 1).absoluteValue, y = 0) }, 1, SOUTH) // NORTH to SOUTH

        val bottomStart = facingStart.copy(y = facingStart.y + sideSize)
        val bottomMap = extractMapFrom(bottomStart, sideSize)

        val fromFacingToBottom = Warp({ from -> Point2D(x = from.x, y = 0) }, 5, SOUTH) // SOUTH to SOUTH
        val fromBottomToFacing = Warp({ from -> Point2D(x = from.x, y = sideSize - 1) }, 2, NORTH) // NORTH to NORTH
        val fromLeftToBottom = Warp({ from -> Point2D(x = 0, y = (from.x - sideSize + 1).absoluteValue) }, 5, EAST) // SOUTH to EAST
        val fromBottomToLeft = Warp({ from -> Point2D(x = (from.y - sideSize + 1).absoluteValue, y = sideSize - 1) }, 3, NORTH) // WEST to NORTH
        val fromBackToBottom = Warp({ from -> Point2D(x = (from.x - sideSize + 1).absoluteValue, y = sideSize - 1) }, 5, NORTH) // SOUTH to NORTH
        val fromBottomToBack = Warp({ from -> Point2D(x = (from.x - sideSize + 1).absoluteValue, y = sideSize - 1) }, 4, NORTH) // SOUTH to NORTH

        val rightStart = bottomStart.copy(x = facingStart.x + sideSize)
        val rightMap = extractMapFrom(rightStart, sideSize)

        val fromTopToRight = Warp({ from -> Point2D(x = (from.x - sideSize + 1).absoluteValue, y = sideSize - 1) }, 6, WEST) // EAST to WEST
        val fromRightToTop = Warp({ from -> Point2D(x = (from.x - sideSize + 1).absoluteValue, y = sideSize - 1) }, 1, WEST) // EAST to WEST
        val fromFacingToRight = Warp({ from -> Point2D(x = (from.y - sideSize + 1).absoluteValue, y = 0) }, 6, SOUTH) // EAST to SOUTH
        val fromRightToFacing = Warp({ from -> Point2D(x = sideSize - 1, y = (from.x - sideSize + 1).absoluteValue) }, 2, WEST) // NORTH to WEST
        val fromBackToRight = Warp({ from -> Point2D(x = (from.y - sideSize + 1).absoluteValue, y = sideSize - 1) }, 6, NORTH) // WEST to NORTH
        val fromRightToBack =  Warp({ from -> Point2D(x = 0, y = (from.x - sideSize + 1).absoluteValue) }, 4, EAST) // SOUTH to EAST
        val fromBottomToRight = Warp({ from -> Point2D(y = from.y, x = 0) }, 6, EAST) // EAST to EAST
        val fromRightToBottom = Warp({ from -> Point2D(x = sideSize - 1, y = from.y) }, 5, WEST) // WEST to WEST

        val topSide = Side(
            "top",
            topMap,
            west = fromTopToLeft,
            east = fromTopToRight,
            north = fromTopToBack,
            south = fromTopToFacing
        )
        val facingSide = Side(
            "facing",
            facingMap,
            west = fromFacingToLeft,
            east = fromFacingToRight,
            north = fromFacingToTop,
            south = fromFacingToBottom
        )
        val leftSide = Side(
            "left",
            leftMap,
            west = fromLeftToBack,
            east = fromLeftToFacing,
            north = fromLeftToTop,
            south = fromLeftToBottom
        )
        val backSide = Side(
            "back",
            backMap,
            west = fromBackToRight,
            east = fromBackToLeft,
            north = fromBackToTop,
            south = fromBackToBottom
        )
        val bottomSide = Side(
            "bottom",
            bottomMap,
            west = fromBottomToLeft,
            east = fromBottomToRight,
            north = fromBottomToFacing,
            south = fromBottomToBack
        )
        val rightSide = Side(
            "right",
            rightMap,
            west = fromRightToBottom,
            east = fromRightToTop,
            north = fromRightToFacing,
            south = fromRightToBack
        )
        return listOf(topSide, facingSide, leftSide, backSide, bottomSide, rightSide)
    }

    private fun createRealCube(sideSize: Int): List<Side> {
        val conversions = mapOf<String, (Point2D) -> Point2D>(
            "EastToEast" to { from -> Point2D(x = 0, y = from.y) }, // Verified
            "WestToWest" to { from -> Point2D(x = sideSize - 1, y = from.y) }, // verified
            "SouthToSouth" to { from -> Point2D(x = from.x, y = 0) }, // verified
            "NorthToNorth" to { from -> Point2D(x = from.x, y = sideSize - 1) }, // verified
            "SouthToWest" to { from -> Point2D(x = sideSize-1, y = from.x) }, //  verified
            "EastToNorth" to { from -> Point2D(x=from.y, y=sideSize-1)}, // verified
            "EastToWest" to { from -> Point2D(x = sideSize - 1, y = (from.y - sideSize + 1).absoluteValue ) }, // verified
            "WestToEast" to { from -> Point2D(x = 0, y = (from.y - sideSize + 1).absoluteValue ) }, // verified
            "WestToSouth" to { from -> Point2D(x = from.y, y = 0) }, // verified
            "NorthToEast" to { from -> Point2D(x = 0, y = from.x) }, // verified

        )
        // cheating a little. Start is always assumed to be the corner

        val topMap = extractMapFrom(start, sideSize)
        val fromRightToTop = Warp(conversions["WestToWest"]!!, 1, WEST)
        val fromFacingToTop = Warp(conversions["NorthToNorth"]!!, 1, NORTH)
        val fromLeftToTop = Warp(conversions["WestToEast"]!!, 1, EAST)
        val fromBackToTop = Warp(conversions["WestToSouth"]!!, 1, SOUTH)


        val rightStart = start.copy(x = start.x + sideSize)
        val rightMap = extractMapFrom(rightStart, sideSize)
        val fromTopToRight = Warp(conversions["EastToEast"]!!, 2, EAST)
        val fromFacingToRight = Warp(conversions["EastToNorth"]!!, 2, NORTH)
        val fromBottomToRight = Warp(conversions["EastToWest"]!!, 2, WEST)
        val fromBackToRight = Warp(conversions["SouthToSouth"]!!, 2, SOUTH)


        val facingStart = start.copy(y = start.y + sideSize)
        val facingMap = extractMapFrom(facingStart, sideSize)
        val fromTopToFacing = Warp(conversions["SouthToSouth"]!!, 3, SOUTH)
        val fromRightToFacing = Warp(conversions["SouthToWest"]!!, 3, WEST)
        val fromBottomToFacing = Warp(conversions["NorthToNorth"]!!, 3, NORTH)
        val fromLeftToFacing = Warp(conversions["NorthToEast"]!!, 3, EAST)

        val bottomStart = facingStart.copy(y = facingStart.y+sideSize)
        val bottomMap = extractMapFrom(bottomStart, sideSize)
        val fromFacingToBottom = Warp(conversions["SouthToSouth"]!!, 4, SOUTH)
        val fromRightToBottom = Warp(conversions["EastToWest"]!!, 4, WEST)
        val fromLeftToBottom = Warp(conversions["EastToEast"]!!, 4, EAST)
        val fromBackToBottom = Warp(conversions["EastToNorth"]!!, 4, NORTH)

        val leftStart = bottomStart.copy(x = bottomStart.x - sideSize)
        val leftMap = extractMapFrom(leftStart, sideSize)
        val fromTopToLeft = Warp(conversions["WestToEast"]!!, 5, EAST)
        val fromFacingToLeft = Warp(conversions["WestToSouth"]!!, 5, SOUTH)
        val fromBottomToLeft = Warp(conversions["WestToWest"]!!, 5, WEST )
        val fromBackToLeft = Warp(conversions["NorthToNorth"]!!, 5, NORTH)

        val backStart = leftStart.copy(y = leftStart.y + sideSize)
        val backMap = extractMapFrom(backStart, sideSize)
        val fromTopToBack = Warp(conversions["NorthToEast"]!!, 6, EAST)
        val fromRightToBack = Warp(conversions["NorthToNorth"]!!, 6, NORTH)
        val fromBottomToBack = Warp(conversions["SouthToWest"]!!, 6, WEST)
        val fromLeftToBack = Warp(conversions["SouthToSouth"]!!, 6, SOUTH)

        val topSide = Side(
            "top",
            topMap,
            west = fromTopToLeft,
            east = fromTopToRight,
            north = fromTopToBack,
            south = fromTopToFacing
        )
        val facingSide = Side(
            "facing",
            facingMap,
            west = fromFacingToLeft,
            east = fromFacingToRight,
            north = fromFacingToTop,
            south = fromFacingToBottom
        )
        val leftSide = Side(
            "left",
            leftMap,
            west = fromLeftToTop,
            east = fromLeftToBottom,
            north = fromLeftToFacing,
            south = fromLeftToBack
        )
        val backSide = Side(
            "back",
            backMap,
            west = fromBackToTop,
            east = fromBackToBottom,
            north = fromBackToLeft,
            south = fromBackToRight
        )
        val bottomSide = Side(
            "bottom",
            bottomMap,
            west = fromBottomToLeft,
            east = fromBottomToRight,
            north = fromBottomToFacing,
            south = fromBottomToBack
        )
        val rightSide = Side(
            "right",
            rightMap,
            west = fromRightToTop,
            east = fromRightToBottom,
            north = fromRightToBack,
            south = fromRightToFacing
        )

        assert(topSide.points[Point2D(0, 0)] == Point2D(50, 0))
        assert(rightSide.points[Point2D(0, 0)] == Point2D(100, 0))
        assert(facingSide.points[Point2D(0, 0)] == Point2D(50, 50))
        assert(bottomSide.points[Point2D(0, 0)] == Point2D(50, 100))
        assert(leftSide.points[Point2D(0, 0)] == Point2D(0, 100))
        assert(backSide.points[Point2D(0, 0)] == Point2D(0, 150))

        return listOf(topSide, rightSide, facingSide, bottomSide, leftSide, backSide)

    }
}