fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val input = Utils.getInput("DayOne")
    val sortedInput = input.map { it.toInt() }.sorted()
    for (value in sortedInput) {
        val secondValueNeeded = 2020 - value
        if (sortedInput.contains(secondValueNeeded)) {
            println(value * secondValueNeeded)
            return
        }
    }
}

private fun partTwo() {
    val input = Utils.getInput("DayOne")
    val sortedInput = input.map { it.toInt() }.sorted()
    val reversedInput = sortedInput.reversed()
    for (descendingValue in reversedInput) {
        for (ascendingValue in sortedInput) {
            if (descendingValue + ascendingValue > 2020) {
                break
            }
            val thirdValueNeeded = 2020 - descendingValue - ascendingValue
            if (sortedInput.contains(thirdValueNeeded)) {
                println(descendingValue * ascendingValue * thirdValueNeeded)
                return
            }
        }
    }
}