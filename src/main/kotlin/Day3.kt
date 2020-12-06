fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val input = Utils.getInput("Day3")
    val lineLength = input[0].length
    val nbTreeEncountered = input.foldIndexed(0) { index, accumulator, line ->
        if (line[(index * 3) % lineLength] == '#') accumulator + 1
        else accumulator
    }
    println(nbTreeEncountered)
}

private fun partTwo() {
    val input = Utils.getInput("Day3")
    val lineLength = input[0].length
    val slope1 = input.foldIndexed(0) { index, accumulator, line ->
        if (line[index % lineLength] == '#') accumulator + 1
        else accumulator
    }.toLong()
    val slope2 = input.foldIndexed(0) { index, accumulator, line ->
        if (line[(index * 3) % lineLength] == '#') accumulator + 1
        else accumulator
    }.toLong()
    val slope3 = input.foldIndexed(0) { index, accumulator, line ->
        if (line[(index * 5) % lineLength] == '#') accumulator + 1
        else accumulator
    }.toLong()
    val slope4 = input.foldIndexed(0) { index, accumulator, line ->
        if (line[(index * 7) % lineLength] == '#') accumulator + 1
        else accumulator
    }.toLong()
    val slope5 = input.foldIndexed(0) { index, accumulator, line ->
        if (index % 2 == 0 && line[(index / 2) % lineLength] == '#') accumulator + 1
        else accumulator
    }.toLong()
    println(slope1 * slope2 * slope3 * slope4 * slope5)
}