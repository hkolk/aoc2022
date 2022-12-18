import kotlin.math.abs
import java.util.*

class Day18(val input: List<String>) {
    private val coords = input.map{Point3D.fromString(it)}
    private val outside = Point3D(coords.minOf { it.x }-1, coords.minOf { it.y }-1, coords.minOf { it.z }-1)

    fun solvePart1(): Int {
        return coords.sumOf { coord ->
            coord.adjacent().filter { it !in coords }.size
        }
    }
    fun solvePart2(): Int {
        println(outside)
        return coords.sumOf { coord ->
            coord.adjacent().filter { it !in coords }.filter { findShortestPath(it, outside, coords) != null }.size
        }
    }

    private fun heuristic(a:Point3D, b:Point3D): Int {
        return abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)
    }
    private fun findShortestPath(start: Point3D, goal: Point3D, blocked: List<Point3D>): Pair<List<Point3D>, Int>? {

        fun generatePath(currentPos: Point3D, cameFrom: Map<Point3D, Point3D>): List<Point3D> {
            val path = mutableListOf(currentPos)
            var current = currentPos
            while (cameFrom.containsKey(current)) {
                current = cameFrom.getValue(current)
                path.add(0, current)
            }
            return path.toList()
        }

        //val openVertices = mutableSetOf(start)
        val openVertices = PriorityQueue<Pair<Point3D, Int>>(compareBy { it.second })
        openVertices.add(start to 0)
        val closedVertices = mutableSetOf<Point3D>()
        val costFromStart = mutableMapOf(start to 0)
        val cameFrom = mutableMapOf<Point3D, Point3D>()

        val estimatedTotalCost = mutableMapOf(start to heuristic(start, goal))
        while(openVertices.isNotEmpty()) {
            val current = openVertices.remove().first
            if(current == goal) {
                val path = generatePath(current, cameFrom)
                return Pair(path, estimatedTotalCost.getValue(goal)) // First Route to finish will be optimum route
            }
            closedVertices.add(current)

            for(next in current.adjacent().filter { it !in blocked }.filterNot { closedVertices.contains(it) }) {
                val newCost = costFromStart[current]!! + 1
                if(!costFromStart.containsKey(next) || newCost < costFromStart[next]!!) {
                    costFromStart[next] = newCost
                    estimatedTotalCost[next] = newCost + heuristic(goal, next)
                    openVertices.add(next to (newCost + heuristic(goal, next)))
                    cameFrom[next] = current
                }
            }
        }
        return null
    }
}