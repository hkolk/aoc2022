class Day3(val input: List<String>) {
    fun solvePart1(): Int {
        return input.sumOf { line ->
            val left = line.take(line.length/2)
            val right = line.takeLast(line.length/2)
            val intersect = left.toSet().intersect(right.toSet())
            intersect.first().priority()
        }
    }
    fun solvePart2(): Int {
        return input.chunked(3).sumOf { lines ->
            lines.map { it.toSet() }.reduce{ acc, item -> acc.intersect(item) }.first().priority()
        }
    }

    private fun Char.priority(): Int {
        return if(this.isLowerCase()) {
            this.code - 'a'.code + 1
        } else {
            this.code - 'A'.code + 27
        }
    }
}