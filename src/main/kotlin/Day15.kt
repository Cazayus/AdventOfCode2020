fun main() {
    val inputList = listOf(2, 20, 0, 4, 1, 17)
    var lastSpoken = inputList.last()
    val inputListSize = inputList.size
    var alreadySeenAtIndex = inputList.foldIndexed(mutableMapOf<Int, Pair<Int, Int?>>()) { index, turnListTemp, value ->
        turnListTemp[value] = index to null
        turnListTemp
    }
    generateSequence { lastSpoken }.onEachIndexed { index, value ->
        val realIndex = index + inputListSize
        val indexToPreviousIndex = alreadySeenAtIndex[value] ?: throw UnsupportedOperationException()
        lastSpoken = if (indexToPreviousIndex.second != null) {
            indexToPreviousIndex.first - indexToPreviousIndex.second!!
        } else {
            0
        }
        alreadySeenAtIndex[lastSpoken] = realIndex to alreadySeenAtIndex[lastSpoken]?.first
    }.take(2020 - inputListSize).last()
    println("Puzzle 1 : $lastSpoken")
    lastSpoken = inputList.last()
    alreadySeenAtIndex = inputList.foldIndexed(mutableMapOf()) { index, turnListTemp, value ->
        turnListTemp[value] = index to null
        turnListTemp
    }
    generateSequence { lastSpoken }.onEachIndexed { index, value ->
        val realIndex = index + inputListSize
        val indexToPreviousIndex = alreadySeenAtIndex[value] ?: throw UnsupportedOperationException()
        lastSpoken = if (indexToPreviousIndex.second != null) {
            indexToPreviousIndex.first - indexToPreviousIndex.second!!
        } else {
            0
        }
        alreadySeenAtIndex[lastSpoken] = realIndex to alreadySeenAtIndex[lastSpoken]?.first
    }.take(30000000 - inputListSize).last()
    println("Puzzle 2 : $lastSpoken")
}