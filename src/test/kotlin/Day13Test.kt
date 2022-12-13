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
//        [[6],[1,4,5,[[10,6],[],9,6]],[[0,[2,5],[5,9],7],[[0],3,1]],[[10],7],[[[8,4,2,7]],4,7]]
//        [6,4,3,7]
//        [[6,[],[4],[[3,0,9],[4,9],[4,4,6],0,10],9]]
//        [[[[6]],[[],3,0]]]
    val testInput2 = """
        [[6]]
        [6,4,3,7,0]
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
            assertThat(answer).isEqualTo(140)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day13(realInput).solvePart2()
            assertThat(answer).isEqualTo(20758)
        }
    }
}