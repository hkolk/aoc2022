import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 14")
class Day14Test {

    val testInput = """
498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day14.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day14(testInput).solvePart1()
            assertThat(answer).isEqualTo(24)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day14(realInput).solvePart1()
            assertThat(answer).isEqualTo(805)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day14(testInput).solvePart2()
            assertThat(answer).isEqualTo(93)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day14(realInput).solvePart2()
            assertThat(answer).isEqualTo(25161)
        }
    }
}