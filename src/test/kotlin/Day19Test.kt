import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 19")
class Day19Test {

    val testInput = """
Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day19.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day19(testInput).solvePart1()
            assertThat(answer).isEqualTo(33)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day19(realInput).solvePart1()
            assertThat(answer).isEqualTo(978)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day19(testInput).solvePart2()
            assertThat(answer).isEqualTo(3472)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day19(realInput).solvePart2()
            assertThat(answer).isEqualTo(15939)
        }
    }
}