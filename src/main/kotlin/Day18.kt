class Day18(val input: List<String>) {
    val coords = input.map{Point3D.fromString(it)}
    fun solvePart1(): Int {
        //println(coords)
        return coords.sumOf { coord ->
            coord.neighbours().filter { it !in coords }.size
        }
        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}