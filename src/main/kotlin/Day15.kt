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
        val detectedOnLine = detected.filter { it.y == detectLine }.filter{!beacons.contains(it) }
        return detectedOnLine.size
        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}