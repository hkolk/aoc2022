import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 23")
class Day23Test {

    val testInput = """
....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#..
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day23.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day23(testInput).solvePart1()
            assertThat(answer).isEqualTo(110)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day23(realInput).solvePart1()
            assertThat(answer).isEqualTo(3966)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day23(testInput).solvePart2()
            assertThat(answer).isEqualTo(20)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day23(realInput).solvePart2()
            assertThat(answer).isEqualTo(3352886133831L)
        }
    }
}