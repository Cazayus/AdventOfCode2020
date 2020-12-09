fun main() {
    val input = Utils.getInputAsList("Day9").map { it.toLong() }
    val answerPartOne = input.windowed(26).first { windowedList -> windowedList.none { windowedList.contains(windowedList.last() - it) } }.last()
    println("Puzzle 1 : $answerPartOne")
    var windowSize = 2
    println("Puzzle 2: " + generateSequence { input.windowed(windowSize++) }.mapNotNull { windowedList -> windowedList.firstOrNull { it.sum() == answerPartOne } }
        .map { it.maxOrNull()!! + it.minOrNull()!! }.first())
}