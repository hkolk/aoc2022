import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 16")
class Day16Test {

    val testInput = """
Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day16.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day16(testInput).solvePart1()
            assertThat(answer).isEqualTo(26)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day16(realInput).solvePart1()
            assertThat(answer).isEqualTo(5240818)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day16(testInput).solvePart2()
            assertThat(answer).isEqualTo(56000011)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day16(realInput).solvePart2()
            assertThat(answer).isEqualTo(13213086906101)
        }
    }
}