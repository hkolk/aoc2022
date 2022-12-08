class Day7(val input: List<String>) {

    abstract class Node() {
        abstract fun print(indent: Int)
        abstract fun getSize(): Long
    }
    class Directory(val parent: Directory?, val name: String): Node() {
        val contents = mutableListOf<Node>()
        fun addNode(node: Node) {
            contents.add(node)
        }
        fun getDirectory(name: String): Directory {
            return contents.first { it is Directory && it.name == name} as Directory
        }
        override fun print(indent: Int) {
            val indentString = " ".repeat(indent)
            println("$indentString- $name (dir)")
            contents.forEach { it.print(indent + 2) }
        }
        override fun getSize(): Long {
            return contents.sumOf{it.getSize()}
        }
        fun getDirectories(): List<Directory> {
            return contents.filterIsInstance<Directory>().flatMap { it.getDirectories() }.plus(this)
        }
    }
    class File(val parent: Directory, val name: String, private val size: Long): Node() {
        override fun print(indent: Int) {
            val indentString = " ".repeat(indent)
            println("$indentString- $name (file, size: $size)")
        }
        override fun getSize(): Long {
            return size
        }
    }
    fun solvePart1(): Long {
        val root = parseInput()
        return root.getDirectories().filter { it.getSize() < 100_000 }.sumOf { it.getSize() }

    }
    fun solvePart2(): Long {
        val root = parseInput()
        val freeSpace = 70_000_000 - root.getSize()
        val find = 30_000_000 - freeSpace
        return root.getDirectories().sortedBy { it.getSize() }.first { it.getSize() > find}.getSize()
    }

    private fun parseInput(): Directory {
        val root = Directory(null, "/")
        var curDir = root
        input.forEach { line ->
            val parts = line.split(" ")
            if (parts[0] == "${'$'}") {
                if (parts[1] == "cd") {
                    if (parts[2] == "..") {
                        curDir = curDir.parent!!
                    } else if (parts[2] != "/") {
                        curDir = curDir.getDirectory(parts[2])
                    }
                }
            } else if (parts[0] == "dir") {
                val node = Directory(curDir, parts[1])
                curDir.addNode(node)
            } else {
                val node = File(curDir, parts[1], parts[0].toLong())
                curDir.addNode(node)

            }
        }
        return root
    }
}