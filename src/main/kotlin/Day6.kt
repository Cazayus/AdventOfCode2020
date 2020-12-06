fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val input = Utils.getInputAsText("Day6")
    val listAnswersByGroup = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    val count = listAnswersByGroup.sumBy {
        it.lines().joinToString("").toSet().size
    }
    println(count)
}

private fun partTwo() {
    val input = Utils.getInputAsText("Day6")
    val listAnswersByGroup = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    val count = listAnswersByGroup.sumBy {
        it.lines().reduce { value1, value2 -> value1.filter { char -> value2.contains(char) } }.count()
    }
    println(count)
}
