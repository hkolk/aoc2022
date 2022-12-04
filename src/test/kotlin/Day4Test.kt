import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 4")
class Day4Test {

    val testInput = """
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
    """.trimIndent().lines()
    val realInput = Resources.resourceAsList("day4.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day4(testInput).solvePart1()
            assertThat(answer).isEqualTo(2)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day4(realInput).solvePart1()
            assertThat(answer).isEqualTo(513)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day4(testInput).solvePart2()
            assertThat(answer).isEqualTo(4)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day4(realInput).solvePart2()
            assertThat(answer).isEqualTo(878)
        }
    }
}