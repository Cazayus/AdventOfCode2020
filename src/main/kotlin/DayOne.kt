fun main() {
    partOne()
}

private fun partOne() {
    val input = Utils.getInput("DayOne")
    val sortedInput = input.map { it.toInt() }.sorted()
    val reversedInput = sortedInput.reversed()
    val value1: Int
    val value2: Int
    for (descendingValue in reversedInput) {
        val valueNeeded = 2020 - descendingValue
        for (ascendingValue in sortedInput) {
            if (ascendingValue == valueNeeded) {
                value1 = descendingValue
                value2 = ascendingValue
                println(value1 * value2)
                throw UnsupportedOperationException()
            } else if (ascendingValue > valueNeeded) {
                break
            }
        }
    }
}