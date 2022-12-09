class Day9(val input: List<String>) {
    fun solvePart1() = solve(1)
    fun solvePart2() = solve(9)
    private fun solve(numKnots: Int): Int {
        var rope = (0..numKnots).map { Point2D(0, 0) }

        val visited = mutableListOf(Point2D(0, 0))
        input.forEach{ line ->
            val parts = line.split(" ")
            val direction = when(parts[0]) {
                "U" -> Point2D.NORTH
                "D" -> Point2D.SOUTH
                "L" -> Point2D.WEST
                "R" -> Point2D.EAST
                else -> throw IllegalStateException()
            }
            for(moveCount in 0 until parts[1].toInt()) {

                val newRope = mutableListOf<Point2D>()
                newRope.add(rope[0].move(direction))
                for(i in 1 .. numKnots) {
                    if(rope[i] in newRope[i-1].surrounding()) {
                        // knot stays where it is
                        newRope.add(rope[i])
                    } else {
                        // move the knot
                        if(rope[i].x == newRope[i-1].x || rope[i].y == newRope[i-1].y ) {
                            val newDirection =
                                Point2D.DIRECTIONS.first { it(rope[i]) in newRope[i - 1].surrounding() }
                            newRope.add(newDirection(rope[i]))
                        } else {
                            val newDirection =
                                Point2D.DIRECTIONSDIAG.first { it(rope[i]) in newRope[i - 1].surrounding() }
                            newRope.add(newDirection(rope[i]))
                        }
                        if(i == numKnots) {
                            visited.add(newRope[i])
                        }
                    }
                }
                rope = newRope
                for(i in 1 ..numKnots) {
                    if(rope[i] !in rope[i-1].surrounding()) {
                        println("ERROR !! $line - $moveCount")
                    }
                }
            }
        }
        return visited.toSet().size
    }
}