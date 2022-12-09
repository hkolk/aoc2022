import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 9")
class Day9Test {

    val testInput = """
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
""".trimIndent().lines()
    val testInput2 = """
R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day9.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day9(testInput).solvePart1()
            assertThat(answer).isEqualTo(13)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day9(realInput).solvePart1()
            assertThat(answer).isEqualTo(6406)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day9(testInput).solvePart2()
            assertThat(answer).isEqualTo(1)
        }

        @Test
        fun `Matches Second Example`() {
            val answer = Day9(testInput2).solvePart2()
            assertThat(answer).isEqualTo(36)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day9(realInput).solvePart2()
            assertThat(answer).isEqualTo(2643)
        }
    }
}