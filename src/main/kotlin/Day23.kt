fun main() {
    val input = Utils.getInputAsText("Day23")
    val cupsOne = IntArray(10)
    input.map { it.toString().toInt() }.zipWithNext().forEach { cupsOne[it.first] = it.second }
    cupsOne[input.last().toString().toInt()] = input.first().toString().toInt()
    runGame(input, cupsOne, 100, 9)
    var output = ""
    var currentInt = cupsOne[1]
    repeat(8) {
        output += currentInt
        currentInt = cupsOne[currentInt]
    }
    println("Puzzle 1 : $output")
    val cupsTwo = IntArray(1_000_001)
    input.map { it.toString().toInt() }.zipWithNext().forEach { cupsTwo[it.first] = it.second }
    cupsTwo[input.last().toString().toInt()] = 10
    (10..1_000_000).forEach { cupsTwo[it] = it + 1 }
    cupsTwo[1_000_000] = input.first().toString().toInt()
    runGame(input, cupsTwo, 10_000_000, 1_000_000)
    println("Puzzle 2 : ${cupsTwo[1].toLong() * cupsTwo[cupsTwo[1]].toLong()}")
}

private fun runGame(input: String, cupsOne: IntArray, nbRepeat: Int, maxValue: Int) {
    var current = input.first().toString().toInt()
    repeat(nbRepeat) {
        val firstPick = cupsOne[current]
        val secondPick = cupsOne[firstPick]
        val thirdPick = cupsOne[secondPick]
        var destination = current - 1
        while (destination == 0 || destination == firstPick || destination == secondPick || destination == thirdPick) if (destination-- == 0) destination += maxValue + 1
        cupsOne[current] = cupsOne[thirdPick]
        current = cupsOne[current]
        cupsOne[thirdPick] = cupsOne[destination]
        cupsOne[destination] = firstPick
    }
}