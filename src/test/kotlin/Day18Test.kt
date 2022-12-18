import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 18")
class Day18Test {

    val testInput = """
2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day18.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day18(testInput).solvePart1()
            assertThat(answer).isEqualTo(64)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day18(realInput).solvePart1()
            assertThat(answer).isEqualTo(3522)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day18(testInput).solvePart2()
            assertThat(answer).isEqualTo(58)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day18(realInput).solvePart2()
            assertThat(answer).isEqualTo(1572093023267L)
        }
    }
}