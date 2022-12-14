class Day14(input: List<String>) {
    private val initialMap = input.flatMap { line ->
        line.split(" -> ")
            .map { Point2D.fromString(it) }
            .windowed(2, 1)
            .flatMap {points ->
                points[0].allPointsTo(points[1]).map { it to '#' }
            }
    }.toMap()
    val voidLimit = initialMap.keys.map { it.y }.max()
    fun solvePart1(): Int {
        val map = initialMap.toMutableMap()
        //map.printChars()
        for(round in 0.. 100_000) {
            var sand = Point2D(500, 0)
            for( drop in 1 .. 2_000) {

                if(!map.containsKey(sand.move(Point2D.SOUTH))) {
                    sand = sand.move(Point2D.SOUTH)
                } else if(!map.containsKey(sand.move(Point2D.SOUTHWEST))) {
                    sand = sand.move(Point2D.SOUTHWEST)
                } else if(!map.containsKey(sand.move(Point2D.SOUTHEAST))) {
                    sand = sand.move(Point2D.SOUTHEAST)
                } else {
                    map[sand] = 'o'
                    break
                }
                if(sand.y > voidLimit) {
                    return round
                }
            }
            //map.printChars()

        }
        throw IllegalStateException("Could not settle within assigned rounds")
    }
    fun solvePart2(): Int {
        val map = initialMap.toMutableMap()
        //map.printChars()
        for(round in 0.. 100_000) {
            var sand = Point2D(500, 0)
            for( drop in 1 .. 2_000) {

                if(!map.containsKey(sand.move(Point2D.SOUTH))) {
                    sand = sand.move(Point2D.SOUTH)
                } else if(!map.containsKey(sand.move(Point2D.SOUTHWEST))) {
                    sand = sand.move(Point2D.SOUTHWEST)
                } else if(!map.containsKey(sand.move(Point2D.SOUTHEAST))) {
                    sand = sand.move(Point2D.SOUTHEAST)
                } else {
                    map[sand] = 'o'
                    break
                }
                if(sand.y > voidLimit) {
                    map[sand] = 'o'
                    //map.printChars()
                    break
                }
            }
            //map.printChars()

            if(sand == Point2D(500, 0)) {
                //map.printChars()
                return round+1
            }
        }
        throw IllegalStateException("Could not settle within assigned rounds")
    }

}