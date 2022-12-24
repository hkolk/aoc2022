import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 22")
class Day22Test {

    val testInput = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day22.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day22(testInput).solvePart1()
            assertThat(answer).isEqualTo(6032)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day22(realInput).solvePart1()
            assertThat(answer).isEqualTo(65368)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day22(testInput).solvePart2()
            assertThat(answer).isEqualTo(5031)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day22(realInput).solvePart2()
            assertThat(answer).isEqualTo(156166)
        }
    }
}