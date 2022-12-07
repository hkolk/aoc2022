import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 7")
class Day7Test {

    val testInput = """
${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
""".trimIndent().lines()
    val realInput = Resources.resourceAsList("day7.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day7(testInput).solvePart1()
            assertThat(answer).isEqualTo(95437)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day7(realInput).solvePart1()
            assertThat(answer).isEqualTo(1391690)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day7(testInput).solvePart2()
            assertThat(answer).isEqualTo(24933642)
        }

        @Test
        fun `Actual Answer`() {
            val answer = Day7(realInput).solvePart2()
            assertThat(answer).isEqualTo(5469168)
        }
    }
}