fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val input = Utils.getInput("DayTwo")
    val validPassword = input.map {
        val minOccurence = it.takeWhile { char -> char != '-' }.toInt()
        val maxOccurence = it.dropWhile { char -> char != '-' }.takeWhile { char -> char != ' ' }.drop(1).toInt()
        val requiredChar = it.dropWhile { char -> char != ' ' }.toCharArray(1, 2)[0]
        val stringToTest = it.takeLastWhile { char -> char != ' ' }
        val countRequiredChar = stringToTest.count { char -> char == requiredChar }
        (minOccurence..maxOccurence).contains(countRequiredChar)
    }.count { it }
    println(validPassword)
}

private fun partTwo() {
    val input = Utils.getInput("DayTwo")
    val validPassword = input.map {
        val indexOne = it.takeWhile { char -> char != '-' }.toInt()
        val indexTwo = it.dropWhile { char -> char != '-' }.takeWhile { char -> char != ' ' }.drop(1).toInt()
        val requiredChar = it.dropWhile { char -> char != ' ' }.toCharArray(1, 2)[0]
        val stringToTest = it.takeLastWhile { char -> char != ' ' }
        (stringToTest[indexOne - 1] == requiredChar) xor (stringToTest[indexTwo - 1] == requiredChar)
    }.count { it }
    println(validPassword)
}