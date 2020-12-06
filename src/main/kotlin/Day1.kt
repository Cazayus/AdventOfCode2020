fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val input = Utils.getInputAsList("Day1")
    val sortedInput = input.map { it.toInt() }.sorted()
    val reversedInput = sortedInput.reversed()
    for (descendingValue in reversedInput) {
        for (ascendingValue in sortedInput) {
            if (descendingValue + ascendingValue > 2020) {
                break
            } else if (descendingValue + ascendingValue == 2020) {
                println(descendingValue * ascendingValue)
                return
            }
        }
    }
}

private fun partTwo() {
    val input = Utils.getInputAsList("Day1")
    val sortedInput = input.map { it.toInt() }.sorted()
    val reversedInput = sortedInput.reversed()
    for (descendingValue in reversedInput) {
        for (ascendingValue in sortedInput) {
            if (descendingValue + ascendingValue > 2020) {
                break
            }
            for (ascendingValue2 in sortedInput) {
                if (descendingValue + ascendingValue + ascendingValue2 > 2020) {
                    break
                } else if (descendingValue + ascendingValue + ascendingValue2 == 2020) {
                    println(descendingValue * ascendingValue * ascendingValue2)
                    return
                }
            }
        }
    }
}