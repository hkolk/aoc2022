class Day23(val input: List<String>) {
    private val startElves = input.flatMapIndexed{y, line -> line.mapIndexedNotNull{x, c -> if(c == '#') { Point2D(x, y) } else { null } } }

    private val checks = listOf(
        listOf(Point2D.NORTHWEST, Point2D.NORTH, Point2D.NORTHEAST) to Point2D.NORTH,
        listOf(Point2D.SOUTHEAST, Point2D.SOUTH, Point2D.SOUTHWEST) to Point2D.SOUTH,
        listOf(Point2D.SOUTHWEST, Point2D.WEST, Point2D.NORTHWEST) to Point2D.WEST,
        listOf(Point2D.NORTHEAST, Point2D.EAST, Point2D.SOUTHEAST) to Point2D.EAST
    )

    private fun proposeNewLocation(round: Int, elf: Point2D, elves: List<Point2D>): Pair<Point2D, Point2D> {
        //print("New propose: ")
        if(elf.surrounding(false).count{elves.contains(it)} == 0) {
            //println("alone")
            return elf to elf
        }
        for(checkId in round .. (round + 3)) {
            //print(checkId%4)
            if(checks[checkId%4].first.count { elves.contains(it(elf)) } == 0) {
                //println()
                return checks[checkId%4].second(elf) to elf
            }
        }
        //println("surrounded")
        return elf to elf
    }

    fun solvePart1(): Int {
        var elves = startElves
        //println("== Initial State ==")
        //elves.print()
        for(round in 0 until 10) {
            // find proposed locations for all
            val proposals = elves.map { elf ->
                proposeNewLocation(round, elf, elves)
            }
            // check unique moves
            val newElves = mutableListOf<Point2D>()
            val groups = proposals.groupBy { it.first }
            groups.values.forEach{ group ->
                if(group.size == 1) {
                    // move to proposed position
                    newElves.add(group[0].first)
                } else {
                    // retain original position
                    newElves.addAll(group.map { it.second })
                }
            }
            elves = newElves
            //println("== End of Round ${round+1} ==")
            //elves.print()
        }
        val xRange = elves.minAndMaxOf { it.x }.let { it.second - it.first + 1 }
        val yRange = elves.minAndMaxOf { it.y }.let { it.second - it.first + 1 }
        return (xRange * yRange) - elves.size
        TODO()
    }
    fun solvePart2(): Int {
        var elves = startElves
        //println("== Initial State ==")
        //elves.print()
        for(round in 0 until 10_000) {
            // find proposed locations for all
            var moved = false
            val proposals = elves.map { elf ->
                proposeNewLocation(round, elf, elves)
            }
            // check unique moves
            val newElves = mutableListOf<Point2D>()
            val groups = proposals.groupBy { it.first }
            groups.values.forEach{ group ->
                if(group.size == 1) {
                    if(group[0].first != group[0].second) {
                        moved = true
                    }
                    // move to proposed position
                    newElves.add(group[0].first)
                } else {
                    // retain original position
                    newElves.addAll(group.map { it.second })
                }
            }
            elves = newElves
            if(!moved) {
                return round+1
            }
            //println("== End of Round ${round+1} ==")
            //elves.print()
        }
        TODO("Ran out of rounds")
    }
}