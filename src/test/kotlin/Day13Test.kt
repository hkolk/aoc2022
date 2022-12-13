import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 13")
class Day13Test {

    val testInput = """
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day13.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day13(testInput).solvePart1()
            assertThat(answer).isEqualTo(13)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day13(realInput).solvePart1()
            assertThat(answer).isEqualTo(6070)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day13(testInput).solvePart2()
            assertThat(answer).isEqualTo(29)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day13(realInput).solvePart2()
            assertThat(answer).isEqualTo(454)
        }
    }
}