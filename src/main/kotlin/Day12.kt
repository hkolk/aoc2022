import java.util.*
import kotlin.math.abs

class Day12(val input: List<String>) {
    private var start: Point2D = Point2D(0, 0)
    private var end: Point2D = Point2D(0, 0)
    val map: Map<Point2D, Int> = input.flatMapIndexed{ y, line -> line.mapIndexed{ x, char ->
        val level = when(char) {
            'S' -> {
                start = Point2D(x, y)
                0
            }
            'E' -> {
                end = Point2D(x, y)
                25
            }
            else -> char.code - 'a'.code
        }
        Point2D(x, y) to level
    }}.toMap()

    private fun heuristic(a:Pair<Point2D, Int>, b:Pair<Point2D, Int>): Int {
        val heightadjustment = if(b.second <= a.second) { 26 } else { 0 }
        return heightadjustment + abs(a.first.x - b.first.x) + abs(a.first.y - b.first.y)
    }

    private fun findShortestPath(start: Point2D, goal: Point2D, map: Map<Point2D, Int>): Pair<List<Point2D>, Int>? {

        fun generatePath(currentPos: Point2D, cameFrom: Map<Point2D, Point2D>): List<Point2D> {
            val path = mutableListOf(currentPos)
            var current = currentPos
            while (cameFrom.containsKey(current)) {
                current = cameFrom.getValue(current)
                path.add(0, current)
            }
            return path.toList()
        }

        //val openVertices = mutableSetOf(start)
        val openVertices = PriorityQueue<Pair<Point2D, Int>>(compareBy { it.second })
        openVertices.add(start to 0)
        val closedVertices = mutableSetOf<Point2D>()
        val costFromStart = mutableMapOf(start to 0)
        val cameFrom = mutableMapOf<Point2D, Point2D>()

        val estimatedTotalCost = mutableMapOf(start to heuristic(start to map[start]!!, goal to map[goal]!!))
        while(openVertices.isNotEmpty()) {
            val current = openVertices.remove().first
            if(current == goal) {
                val path = generatePath(current, cameFrom)
                return Pair(path, estimatedTotalCost.getValue(goal)) // First Route to finish will be optimum route
            }
            closedVertices.add(current)

            for(next in current.adjacent().filter { map.containsKey(it) && map[it]!! <= map[current]!! + 1 }.filterNot { closedVertices.contains(it) }) {
                val newCost = costFromStart[current]!! + 1
                if(!costFromStart.containsKey(next) || newCost < costFromStart[next]!!) {
                    costFromStart[next] = newCost
                    estimatedTotalCost[next] = newCost + heuristic(goal to map[goal]!!, next to map[next]!!)
                    openVertices.add(next to (newCost + heuristic(goal to map[goal]!!, next to map[next]!!)))
                    cameFrom[next] = current
                }
            }
        }
        return null
    }
    fun solvePart1(): Int {
        val path = findShortestPath(start, end, map)!!
        //println(path.second)
        return path.first.size -1
    }
    fun solvePart2(): Int {
        val routes = map.filter { it.value == 0 }.mapNotNull { item ->
            val path = findShortestPath(item.key, end, map)
            if(path != null) {
                item.key to path.first.size - 1
            } else {
                null
            }
        }
        return routes.minByOrNull { it.second }!!.second
    }
}