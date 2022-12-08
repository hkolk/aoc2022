import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 8")
class Day8Test {

    val testInput = """
30373
25512
65332
33549
35390
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day8.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day8(testInput).solvePart1()
            assertThat(answer).isEqualTo(21)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day8(realInput).solvePart1()
            assertThat(answer).isEqualTo(1796)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day8(testInput).solvePart2()
            assertThat(answer).isEqualTo(8)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day8(realInput).solvePart2()
            assertThat(answer).isEqualTo(288120)
        }
    }
}