import java.lang.IllegalStateException

class Day6(val input: List<String>) {

    fun solvePart1(): Int = findPacket(4)
    fun solvePart2(): Int = findPacket(14)

    private fun findPacket(length: Int): Int {
        val line = input.first()
        for(i in 0..line.length-length) {
            if(line.drop(i).take(length).toSet().count() == length) {
                return i+length
            }
        }
        throw IllegalStateException("Could not find packet")
    }
}