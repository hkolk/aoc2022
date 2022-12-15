import kotlin.math.absoluteValue

class Day15(val input: List<String>) {
    private val sensors = input.map{line ->
        val parts = line.split(" ", "=", ",", ":").mapNotNull { it.toIntOrNull() }
        Point2D(parts[0], parts[1]) to Point2D(parts[2], parts[3])
    }

    fun solvePart1(detectLine: Int): Int {
        val beacons = sensors.map {it.second}.toSet()
        val detected = mutableSetOf<Point2D>()
        sensors.forEach { (sensor, beacon) ->
            val distance = sensor.distance(beacon)
            //println("$sensor -> $distance")
            for(y in -distance .. distance) {
                if(detectLine == sensor.y + y) {
                    val xDist = distance - y.absoluteValue
                    for(x in -xDist .. xDist) {
                        val spot = sensor.move(Point2D(x, y))
                        if(!beacons.contains(spot)) {
                            detected.add(spot)
                            //println("$sensor, $distance, $spot, $x, $y")
                        }
                    }
                }
            }
        }
        return detected.size
    }
    fun solvePart2(gridSize: Int): Long {
        val ranges = (0 .. gridSize).map { it to listOf(0 .. gridSize) }.toMap().toMutableMap()
        println((0..100).cutOut(50..75))
        println((0..100).cutOut(-10..50))
        println((0..100).cutOut(10..150))
        println((0..100).cutOut(-10..150))
        println((0..100).cutOut(200..250))

        sensors.forEach { (sensor, beacon) ->
            val distance = sensor.distance(beacon)
            //println("$sensor -> $distance")
            for (y in -distance..distance) {
                val xDist = distance - y.absoluteValue
                val cutOut = sensor.x - xDist..sensor.x + xDist
                val actualY = sensor.y + y

                if(ranges.containsKey(actualY)) {
                    ranges[actualY] = ranges[actualY]!!.flatMap {
                        it.cutOut(cutOut)
                    }
                }
                if(actualY == 10) {
                    for(x in -xDist .. xDist) {
                        val spot = sensor.move(Point2D(x, y))
                        //println("$sensor, $distance, $spot, $x, $y")
                    }
                    //println(ranges[actualY])
                }
            }
        }
        val map = ranges.flatMap { (y, list) ->
            list.flatMap { range ->
                range.map { x -> Point2D(x, y) }
            }
        }
        map.print()
        val results = ranges.filter { it.value.isNotEmpty() }
        println(results.size)
        if(results.size == 1) {
            println(results)
            results.map { (y, range) -> return (range.first().first() * 4_000_000L) + y }

        }
        TODO()
    }
    fun IntRange.cutOut(other: IntRange): List<IntRange> {
        if(this.first < other.first && this.last > other.last ) {
            return listOf(this.first..other.first-1, other.last+1 .. this.last)
        } else if (this.first < other.first && this.last <= other.last) {
            return listOf(this.first..this.last.coerceAtMost(other.first-1))
        } else if (this.first >= other.first && this.last > other.last) {
            return listOf(this.first.coerceAtLeast(other.last + 1)..this.last)
        } else {
            return listOf()
        }
    }
}

