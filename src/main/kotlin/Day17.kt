import kotlin.math.absoluteValue

class Day17(val input: List<String>) {
    val wind = input.first().map {
        when(it) {
            '<' -> Point2D.WEST
            '>' -> Point2D.EAST
            else -> throw IllegalStateException("Unknown wind")
        }
    }
    private fun toBlock(block: String): List<Point2D> {
        val yOffset = block.lines().size - 1
        return block.lines().flatMapIndexed{ y, line ->
            line.mapIndexedNotNull{x, c ->
                if(c != ' ') { Point2D(x, y- yOffset)} else { null }
            }
        }
    }
    val blocks = listOf(
        toBlock("####"),
        toBlock(" # \n###\n # "),
        toBlock("  #\n  #\n###"),
        toBlock("#\n#\n#\n#"),
        toBlock("##\n##")
    )

    data class State(val roof: Set<Point2D>, val windIndex: Int, val blockIndex: Int)

    fun solvePart1() = solve(2022)
    fun solvePart2() = solve(1_000_000_000_000)
    private fun solve(totalRounds: Long): Long {
        val memory = mutableMapOf<State, Pair<Long, Int>>()
        var chamber = (1..7).map { Point2D(it, 0) }.toMutableSet()
        val windIter = wind.circularIterator()
        val rockIter = blocks.circularIterator()
        for(numRock in 1..totalRounds) {
            val top = chamber.minBy{ it.y }
            val rockLoc = Point2D(3, top.move(Point2D.NORTH, 4).y, )
            var rock = rockIter.next().map { it.move(rockLoc) }
            while(true) {
                // process wind
                val wind = windIter.next()
                val rockAfterWind = rock.map(wind)

                // adjust for walls and objects
                if(rockAfterWind.none { it.x <= 0 || it.x >= 8 || it in chamber }) {
                    rock = rockAfterWind
                    //println("Wind: $rock")
                }

                // go down one
                val rockAfterFall = rock.map(Point2D.SOUTH)

                // see if it hits -> if so, discard move and land, otherwise continue
                if(rockAfterFall.any { it in chamber }) {
                    chamber.addAll(rock)

                    // quick prune
                    val minY = chamber.minBy { it.y }.y
                    chamber.removeIf { it.y > minY + 50 }

                    val roof = (1..7).map { x -> chamber.filter { it.x == x }.minBy { it.y} }.map { it.copy(y = it.y - minY)}
                    val state = State(roof.toSet(), windIter.getIndex(), rockIter.getIndex())

                    if(memory.containsKey(state)) {
                        val distance = minY - memory[state]!!.second
                        val roundDiff = numRock - memory[state]!!.first
                        if (totalRounds % roundDiff == numRock % roundDiff) {
                            // exact state as the final round.. just need to manage the offset
                            val offsetTimes = totalRounds / roundDiff - numRock / roundDiff
                            val result = minY + offsetTimes * distance.toLong()
                            return result.absoluteValue
                        }
                    } else {
                        memory[state] = numRock to minY
                    }

                    break
                } else {
                    rock = rockAfterFall
                }
            }
        }
        return chamber.minBy { it.y }.y.absoluteValue.toLong()
    }
}

