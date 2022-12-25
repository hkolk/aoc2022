import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 25")
class Day25Test {

    val testInput = """
1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day25.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day25(testInput).solvePart1()
            assertThat(answer).isEqualTo("2=-1=0")
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day25(realInput).solvePart1()
            assertThat(answer).isEqualTo("2=-0=01----22-0-1-10")
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day25(testInput).solvePart2()
            assertThat(answer).isEqualTo(1)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day25(realInput).solvePart2()
            assertThat(answer).isEqualTo(1)
        }
    }
}