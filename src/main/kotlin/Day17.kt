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
    fun solvePart1(): Int {
        val chamber = (1..7).map { Point2D(it, 0) }.toMutableSet()
        val windIter = wind.circularIterator()
        val blockIter = blocks.circularIterator()
        for(numRock in 1..2022) {
            val top = chamber.minBy{ it.y }
            val rockLoc = Point2D(3, top.move(Point2D.NORTH, 4).y, )
            var rock = blockIter.next().map { it.move(rockLoc) }
            //println("=========================================")
            //println("Init: $rock")
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
                    //println("Land: $rock")
                    chamber.addAll(rock)
                    //println(chamber.print())
                    break
                } else {
                    rock = rockAfterFall
                    //println("Drop: $rock")
                }
            }
        }
        //println(chamber.print())
        return chamber.minBy { it.y }.y.absoluteValue
        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}

fun <T> List<T>.circularIterator(): Iterator<T> {
    val list = this
    return object : Iterator<T> {
        var i = -1
        override fun hasNext(): Boolean {
            return true
        }

        override fun next(): T {
            i = (i + 1) % list.size
            return list[i]
        }
    }
}