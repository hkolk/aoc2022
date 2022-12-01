import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 1")
class Day1Test {

    val testInput = listOf("1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000")
    val realInput = Resources.resourceAsList("day1.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day1(testInput).solvePart1()
            assertThat(answer).isEqualTo(24000)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day1(realInput).solvePart1()
            assertThat(answer).isEqualTo(74394)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day1(testInput).solvePart2()
            assertThat(answer).isEqualTo(45000)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day1(realInput).solvePart2()
            assertThat(answer).isEqualTo(212836)
        }
    }
}