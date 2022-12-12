import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 12")
class Day12Test {

    val testInput = """
Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day12.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day12(testInput).solvePart1()
            assertThat(answer).isEqualTo(31)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day12(realInput).solvePart1()
            assertThat(answer).isEqualTo(456)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day12(testInput).solvePart2()
            assertThat(answer).isEqualTo(29)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day12(realInput).solvePart2()
            assertThat(answer).isEqualTo(15310845153)
        }
    }
}