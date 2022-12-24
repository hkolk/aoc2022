import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 24")
class Day24Test {

    val testInput = """
#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day24.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day24(testInput).solvePart1()
            assertThat(answer).isEqualTo(18)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day24(realInput).solvePart1()
            assertThat(answer).isEqualTo(271)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day24(testInput).solvePart2()
            assertThat(answer).isEqualTo(20)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day24(realInput).solvePart2()
            assertThat(answer).isEqualTo(3352886133831L)
        }
    }
}