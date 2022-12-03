import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 3")
class Day3Test {

    val testInput = """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().lines()
    val realInput = Resources.resourceAsList("day3.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day3(testInput).solvePart1()
            assertThat(answer).isEqualTo(157)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day3(realInput).solvePart1()
            assertThat(answer).isEqualTo(7446)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day3(testInput).solvePart2()
            assertThat(answer).isEqualTo(70)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day3(realInput).solvePart2()
            assertThat(answer).isEqualTo(2646)
        }
    }
}