fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val input = Utils.getInputAsText("Day6")
    val listAnswersByGroup = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    val count = listAnswersByGroup.map {
        it.fold("") { accumulator, char ->
            if (char.isLetter() && !accumulator.contains(char)) accumulator + char else accumulator
        }
    }.sumBy { it.count() }
    println(count)
}

private fun partTwo() {
    val input = Utils.getInputAsText("Day6")
    val listAnswersByGroup = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    val count = listAnswersByGroup.map {
        val listAnswerByPerson = it.split(System.lineSeparator())
        listAnswerByPerson.reduce { value1, value2 -> value1.filter { char -> value2.contains(char) } }.count()
    }.sum()
    println(count)
}
