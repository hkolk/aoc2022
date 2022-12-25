import kotlin.IllegalStateException

class Day25(val input: List<String>) {

    class SnafuNumber(val value: String) {
        override fun toString() = value
        private fun Int.pow(exponent: Long): Long {
            return (1L..exponent).fold(1L){acc, _ -> acc * this}
        }
        fun toLong(): Long {
            return this.value.map { it.snafuDigitToInt() }.reversed().foldIndexed(0L) { idx, acc, it ->
                (5).pow(idx.toLong()) * it+ acc
            }
        }
        private fun Char.snafuDigitToInt() = when(this) {
            '=' -> -2
            '-' -> -1
            '0' -> 0
            '1' -> 1
            '2' -> 2
            else -> throw IllegalStateException("$this is not a snafu digit")
        }
    }

    private fun String.toSnafu() = SnafuNumber(this)

    private fun Long.toSnafu(): SnafuNumber {
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
        }.reversed().joinToString("").toSnafu()
    }


    fun solvePart1() = input.sumOf { it.toSnafu().toLong() }.toSnafu().toString()

    fun solvePart2() = 1 // 1 free star
}