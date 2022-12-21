import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 21")
class Day21Test {

    val testInput = """
root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day21.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day21(testInput).solvePart1()
            assertThat(answer).isEqualTo(152)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day21(realInput).solvePart1()
            assertThat(answer).isEqualTo(158661812617812L)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day21(testInput).solvePart2()
            assertThat(answer).isEqualTo(301)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day21(realInput).solvePart2()
            assertThat(answer).isEqualTo(7496649006261L)
        }
    }
}