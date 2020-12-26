fun main() {
    val input = Utils.getInputAsList("Day25")
    val cardPublicKey = input[0].toLong()
    val doorPublicKey = input[1].toLong()
    val (cardLoopSize, doorLoopSize) = findLoopSizes(7, cardPublicKey, doorPublicKey)
    println("card loop size : $cardLoopSize door loop size : $doorLoopSize")
    println("Puzzle 1 : ${transformSubjectNumber(cardPublicKey, doorLoopSize)} ${transformSubjectNumber(doorPublicKey, cardLoopSize)}")
}

private fun findLoopSizes(subjectNumber: Long, cardPublicKey: Long, doorPublicKey: Long): Pair<Int, Int> {
    var temp = 1L
    var cardLoopSize = 0
    var doorLoopSize = 0
    var loopCount = 0
    while (cardLoopSize == 0 || doorLoopSize == 0) {
        loopCount++
        temp = (temp * subjectNumber).rem(20201227)
        if (temp == cardPublicKey) {
            cardLoopSize = loopCount
        } else if (temp == doorPublicKey) {
            doorLoopSize = loopCount
        }
    }
    return cardLoopSize to doorLoopSize
}

private fun transformSubjectNumber(subjectNumber: Long, nbRepeat: Int = 0): Long {
    var output = 1L
    repeat(nbRepeat) {
        output = (output * subjectNumber).rem(20201227)
    }
    return output
}