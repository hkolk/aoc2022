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
        val memory = mutableMapOf<Int, Int>()
        var chamber = (1..7).map { Point2D(it, 0) }.toMutableSet()
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

                    // quick prune
                    val minY = chamber.minBy { it.y }.y.absoluteValue
                    chamber.removeIf { it.y > minY + 100 }
                    if(memory.containsKey(chamber.hashCode())) {
                        println("Repition at $numRock")
                    } else {
                        memory[chamber.hashCode()] = numRock
                    }

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
    fun solvePart2(): Long {
        val memory = mutableMapOf<State, Pair<Int, Int>>()
        var chamber = (1..7).map { Point2D(it, 0) }.toMutableSet()
        val windIter = wind.circularIterator()
        val blockIter = blocks.circularIterator()
        var distance = 0
        for(numRock in 1..1_000_000) {
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

                    // quick prune
                    val minY = chamber.minBy { it.y }.y
                    chamber.removeIf { it.y > minY + 100 }

                    val roof = (1..7).map { x -> chamber.filter { it.x == x }.minBy { it.y} }.map { it.copy(y = it.y - minY)}
                    val state = State(roof.toSet(), windIter.getIndex(), blockIter.getIndex())
                    if(memory.containsKey(state)) {
                        if (distance == 0) {
                            println("Repition at $numRock with $minY")
                            println(state)
                            println(memory[state])
                            distance = minY - memory[state]!!.second
                        } else {
                            // predict
                            if((minY - memory[state]!!.second) % distance == 0) {
                                println("Prediction correct!")
                                val roundDiff = numRock.toLong() - memory[state]!!.first.toLong()
                                if(1_000_000_000_000 % roundDiff == numRock % roundDiff) {
                                    println("= 1_000_000_000 % $roundDiff = ${1_000_000_000_000 % roundDiff}")
                                    println("= $numRock % $roundDiff = ${numRock % roundDiff}")
                                    // exact state as the final round.. just need to manage the offset
                                    val offsetTimes = 1_000_000_000_000 / roundDiff - numRock.toLong() / roundDiff
                                    println("=  1_000_000_000_000 / $roundDiff - ${numRock.toLong()} / $roundDiff = ${ 1_000_000_000_000 / roundDiff - numRock.toLong() / roundDiff}")
                                    val result = minY + offsetTimes * distance.toLong()
                                    println("= $minY + $offsetTimes * ${distance.toLong()} = ${minY + offsetTimes * distance.toLong()}")
                                    println("${1514285714288 - result.absoluteValue}")
                                    return result.absoluteValue
                                }
                            } else {
                                println("Prediction Wrong!!!!")
                                println("Repition at $numRock with $minY")
                                println(state)
                                println(memory[state])
                            }
                        }
                    } else {
                        memory[state] = numRock to minY
                    }

                    //println(chamber.print())
                    break
                } else {
                    rock = rockAfterFall
                    //println("Drop: $rock")
                }
            }
            if(numRock % 1000 == 0) {
                println("Rocks: $numRock")
            }
        }
        //println(chamber.print())
        return chamber.minBy { it.y }.y.absoluteValue.toLong()
    }
}

data class State(val roof: Set<Point2D>, val windIndex: Int, val blockIndex: Int)

fun <T> List<T>.circularIterator(): CircularIterator<T> {
    return CircularIterator(this)
}

class CircularIterator<T>(val list: List<T>): Iterator<T> {
    private var i = -1
    override fun hasNext() = true

    override fun next(): T {
        i = (i + 1) % list.size
        return list[i]
    }

    fun getIndex() = i
}