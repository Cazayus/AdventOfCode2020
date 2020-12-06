fun main() {
    val input = Utils.getInputAsText("Day6")
    val listAnswersByGroup = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    println(listAnswersByGroup.sumBy {
        it.lines().joinToString("").toSet().size
    })
    println(listAnswersByGroup.sumBy {
        it.lines().reduce { value1, value2 -> value1.filter { char -> value2.contains(char) } }.count()
    })
}