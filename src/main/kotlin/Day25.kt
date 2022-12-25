import kotlin.IllegalStateException

class Day25(val input: List<String>) {

    private fun Int.pow(exponent: Long): Long {
        return (1L..exponent).fold(1L){acc, _ -> acc * this}
    }

    private fun String.fromSnafuToLong(): Long {
        return this.map { it.snafuDigitToInt() }.reversed().foldIndexed(0L) { idx, acc, it ->
            (5).pow(idx.toLong()) * it+ acc
        }
    }

    private fun Long.toSnafu(): String {
        var acc = 0
        return this.toString(5).reversed().map { c ->
            when(c.digitToInt()+acc) {
                0 -> { acc = 0; '0' }
                1 -> { acc = 0; '1' }
                2 -> { acc = 0; '2' }
                3 -> { acc = 1; '=' }
                4 -> { acc = 1; '-' }
                5 -> { acc = 1; '0' } // safe because the radix convert cannot return this
                else -> throw IllegalStateException("Unknown snafu digit: $c")
            }
        }.reversed().joinToString("")
    }

    private fun Char.snafuDigitToInt() = when(this) {
        '=' -> -2
        '-' -> -1
        '0' -> 0
        '1' -> 1
        '2' -> 2
        else -> throw IllegalStateException("$this is not a snafu digit")
    }
    fun solvePart1() = input.sumOf { it.fromSnafuToLong() }.toSnafu()

    fun solvePart2() = 1 // 1 free star
}