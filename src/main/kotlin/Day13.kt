class Day13(val input: List<String>) {

    abstract sealed class PacketItem() {
        companion object {
            fun read(input: String): PacketList {
                val ret = innerRead(input).first.inner.first() as PacketList
                // safety check
                if(ret.toString() != input) {
                    throw IllegalStateException("$ret is not the same as input $input")
                }
                return ret
            }
            private fun innerRead(input: String): Pair<PacketList, Int> {
                val ret = mutableListOf<PacketItem>()
                var pointer = 0
                while(pointer < input.length ) {
                    when(input[pointer]) {
                        '[' -> {
                            //println("In list")
                            val (list, read) = innerRead(input.drop(pointer+1))
                            ret.add(list)
                            pointer += read+2
                        }
                        ']' -> {
                            //println("Out list: $input, $pointer")
                            return PacketList(ret) to pointer
                        }
                        ',' -> {
                            //println("In comma")
                            pointer++
                        }
                        else -> {
                            //println("In Integer")

                            var integer = input[pointer].toString()
                            while(true) {
                                pointer++
                                if(input[pointer].isDigit()) {
                                    integer += input[pointer]
                                } else {
                                    ret.add(PacketInteger(integer.toInt()))
                                    break
                                }
                            }
                        }
                    }
                }
                return PacketList(ret) to pointer
            }
        }
    }



    data class PacketList(val inner: List<PacketItem>) : PacketItem(), Comparable<PacketList> {

        override fun toString(): String {
            return "[${inner.joinToString(",")}]"
        }

        override fun compareTo(other: PacketList): Int {
            val zipped: List<Pair<PacketItem?, PacketItem?>> = this.inner.zipAll(other.inner)
            zipped.forEach { (first, second) ->
                when(first) {
                    is PacketInteger -> {
                        when(second) {
                            is PacketInteger -> {
                                if (first.value < second.value) {
                                    return -1 // this has a value smaller than other
                                } else if (second.value < first.value) {
                                    return 1 // other has a value smaller than this
                                }
                            }
                            is PacketList -> {
                                val ret =  PacketList(listOf(first)).compareTo(second)
                                if(ret != 0) {
                                    return ret
                                }
                            }
                            null -> return 1 // this list is longer than the other list

                        }
                    }
                    is PacketList -> {
                        when(second) {
                            is PacketInteger -> {
                                val ret =  first.compareTo(PacketList(listOf(second)))
                                if(ret != 0) {
                                    return ret
                                }
                            }
                            is PacketList -> {
                                val ret = first.compareTo(second)
                                if(ret != 0) {
                                    return ret
                                }
                            }
                            null -> {
                                return 1 // this list is longer than the other list
                            }
                        }
                    }
                    null -> {
                        return -1 // this list is smaller than the other list
                    }
                }
            }
            return 0 // equal
        }
    }
    data class PacketInteger(val value: Int) : PacketItem() {
        override fun toString(): String {
            return value.toString()
        }
    }
    fun solvePart1(): Int {
        val comms = input.chunked(3).map { PacketItem.read(it[0]) to PacketItem.read(it[1])}
        val results = comms.mapIndexed { index, (first, second) ->
            val compare = first.compareTo(second)
            index+1 to compare
        }
        return results.filter { it.second == -1 }.sumOf{it.first}
    }
    fun solvePart2(): Int {
        var ret = 1
        val packet2 = PacketItem.read("[[2]]")
        val packet6 = PacketItem.read("[[6]]")
        val packets = input.filter { it.isNotEmpty() }.map { PacketItem.read(it) } + listOf(packet2, packet6)
        packets.sorted().forEachIndexed { index, item ->
            //println(item)
            if(item == packet2 || item == packet6) {
                ret *= (index+1)
            }
        }
        return ret
    }

}


