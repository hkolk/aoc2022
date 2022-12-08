class Day8(val input: List<String>) {
    val map = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, char -> Point2D(x, y) to char.digitToInt() }
    }.toMap()
    val maxY = input.size - 1
    val maxX = input.first().length - 1
    fun solvePart1(): Int {
        val visible = map.mapNotNull { entry ->
            val leftBlockers = (0 .. entry.key.x-1).map {x -> map[Point2D(x, entry.key.y)]!! - entry.value }.filter { it >= 0 }
            val rightBlockers = (entry.key.x + 1 .. maxX).map {x -> map[Point2D(x, entry.key.y)]!! - entry.value }.filter { it >= 0 }
            val topBlockers = (0 .. entry.key.y-1).map {y -> map[Point2D(entry.key.x, y)]!! - entry.value }.filter { it >= 0 }
            val bottomBlockers = (entry.key.y + 1 .. maxY).map {y -> map[Point2D(entry.key.x, y)]!! - entry.value }.filter { it >= 0 }
            if(entry.key == Point2D(2, 2)) {
                println("${leftBlockers.size} || ${rightBlockers.size} || ${topBlockers.size} || ${bottomBlockers.size}")
            }
            if(leftBlockers.isEmpty() || rightBlockers.isEmpty() || topBlockers.isEmpty() || bottomBlockers.isEmpty()) {
                entry.key
            } else {
                null
            }
        }
        return visible.size
    }
    fun solvePart2(): Int {
        val scores = map.map { entry ->
            var left = 0
            for(x in entry.key.x-1 downTo 0) {
                left++
                if(map[Point2D(x, entry.key.y)]!! >= entry.value) {
                    break
                }
            }
            var right = 0
            for(x in entry.key.x+1 .. maxX) {
                right++
                if(map[Point2D(x, entry.key.y)]!! >= entry.value) {
                    break
                }
            }
            var top = 0
            for(y in entry.key.y-1 downTo 0) {
                top++
                if(map[Point2D(entry.key.x, y)]!! >= entry.value) {
                    break
                }
            }
            var down = 0
            for(y in entry.key.y+1 .. maxY) {
                down++
                if(map[Point2D(entry.key.x, y)]!! >= entry.value) {
                    break
                }
            }
            val scenicScore = left * right * top * down
            entry.key to scenicScore
        }
        return scores.maxBy { it.second }.second
    }

}