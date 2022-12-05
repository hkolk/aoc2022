import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 5")
class Day5Test {

    val testInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent().lines()
    val realInput = Resources.resourceAsList("day5.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day5(testInput).solvePart1()
            assertThat(answer).isEqualTo("CMZ")
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day5(realInput).solvePart1()
            assertThat(answer).isEqualTo("CNSZFDVLJ")
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day5(testInput).solvePart2()
            assertThat(answer).isEqualTo("MCD")
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day5(realInput).solvePart2()
            assertThat(answer).isEqualTo("")
        }
    }
}