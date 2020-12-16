fun main() {
    val inputList = listOf(2, 20, 0, 4, 1, 17)
    println("Puzzle 1 : ${getNthValueSpoken(inputList, 2020)}")
    println("Puzzle 1 : ${getNthValueSpoken(inputList, 30_000_000)}")
}

fun getNthValueSpoken(inputList: List<Int>, wantedTurn: Int): Int {
    var lastSpoken = inputList.last()
    val inputListSize = inputList.size
    val mapValueToIndexPair = inputList.foldIndexed(mutableMapOf<Int, Pair<Int, Int?>>()) { index, turnListTemp, value ->
        turnListTemp[value] = index to null
        turnListTemp
    }
    generateSequence { lastSpoken }.onEachIndexed { index, value ->
        val realIndex = index + inputListSize
        val indexToPreviousIndex = mapValueToIndexPair[value] ?: throw UnsupportedOperationException()
        lastSpoken = if (indexToPreviousIndex.second != null) {
            indexToPreviousIndex.first - indexToPreviousIndex.second!!
        } else {
            0
        }
        mapValueToIndexPair[lastSpoken] = realIndex to mapValueToIndexPair[lastSpoken]?.first
    }.take(wantedTurn - inputListSize).last()
    return lastSpoken
}