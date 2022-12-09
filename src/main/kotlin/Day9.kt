class Day9(val input: List<String>) {
    data class Rope(val head: Point2D, val tail: Point2D)
    fun solvePart1(): Int {
        var head = Point2D(0, 0)
        var tail = head
        val visited = mutableListOf(tail)
        input.forEach{
            val parts = it.split(" ")
            val direction = when(parts[0]) {
                "U" -> Point2D.NORTH
                "D" -> Point2D.SOUTH
                "L" -> Point2D.WEST
                "R" -> Point2D.EAST
                else -> throw IllegalStateException()
            }
            for(i in 0 until parts[1].toInt()) {
                val newHead = head.move(direction)
                if(tail !in newHead.surrounding()) {
                    // move the tail
                    tail = head
                    visited.add(tail)
                }
                head = newHead
            }
        }
        visited.print()
        return visited.toSet().size
        TODO()
    }

    fun solvePart2(): Int {
        var rope = (1..10).map { Point2D(0, 0) }

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
                for(i in 1 .. 9) {
                    if(rope[i] in newRope[i-1].surrounding()) {
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
                        if(i == 9) {
                            visited.add(newRope[i])
                        }
                    }
                }
                rope = newRope
                for(i in 1 ..9) {
                    if(rope[i] !in rope[i-1].surrounding()) {
                        println("ERROR !! $line - $moveCount")
                    }
                }
                //rope.print()
                //println()
            }
            //println(rope)
            //visited.print()

        }
        return visited.toSet().size
    }
}