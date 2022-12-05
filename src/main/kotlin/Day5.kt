class Day5(val input: List<String>) {
    fun solvePart1(): String {
        val stackLines = input.takeWhile { it.isNotEmpty() }
        val moves = input.dropWhile { it.isNotEmpty() }.drop(1)

        val numstacks = (stackLines.last().trim().length+3) / 4
        val stacks = (1..numstacks).associateWith { ArrayDeque<Char>() }
        stackLines.reversed().drop(1).forEach { line ->
            for(i in 1..numstacks) {
                val name = line[i*4-3]
                if(name != ' ') {
                    stacks[i]?.add(name)
                }
            }
        }
        moves.forEach { line ->
            val parts = line.split(' ').mapNotNull { it.toIntOrNull() }
            for(i in 1 .. parts[0]) {
                stacks[parts[2]]!!.add(stacks[parts[1]]!!.removeLast())
            }
        }
        return stacks.map { it.value.last() }.joinToString("")
    }
    fun solvePart2(): String {
        val stackLines = input.takeWhile { it.isNotEmpty() }
        val moves = input.dropWhile { it.isNotEmpty() }.drop(1)

        val numstacks = (stackLines.last().trim().length+3) / 4
        val stacks = (1..numstacks).associateWith { ArrayDeque<Char>() }
        stackLines.reversed().drop(1).forEach { line ->
            for(i in 1..numstacks) {
                val name = line[i*4-3]
                if(name != ' ') {
                    stacks[i]?.add(name)
                }
            }
        }
        moves.forEach { line ->
            val parts = line.split(' ').mapNotNull { it.toIntOrNull() }
            val substack = mutableListOf<Char>()
            println(line)
            println(stacks)
            for(i in 1 .. parts[0]) {
                substack.add(stacks[parts[1]]!!.removeLast())
            }
            stacks[parts[2]]!!.addAll(substack.reversed())
        }
        return stacks.map { it.value.last() }.joinToString("")
    }}