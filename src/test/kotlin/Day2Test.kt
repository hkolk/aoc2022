import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 2")
class Day2Test {

    val testInput = """
        A Y
        B X
        C Z
    """.trimIndent().lines()
    val realInput = Resources.resourceAsList("day2.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day2(testInput).solvePart1()
            assertThat(answer).isEqualTo(15)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day2(realInput).solvePart1()
            assertThat(answer).isEqualTo(13052)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day2(testInput).solvePart2()
            assertThat(answer).isEqualTo(12)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day2(realInput).solvePart2()
            assertThat(answer).isEqualTo(13693)
        }
    }
}