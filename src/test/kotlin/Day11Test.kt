import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 11")
class Day11Test {

    val testInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day11.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day11(testInput).solvePart1()
            assertThat(answer).isEqualTo(10605)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day11(realInput).solvePart1()
            assertThat(answer).isEqualTo(72884)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day11(testInput).solvePart2()
            assertThat(answer).isEqualTo(2713310158)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day11(realInput).solvePart2()
            assertThat(answer).isEqualTo(15310845153)
        }
    }
}