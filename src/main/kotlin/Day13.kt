fun main() {
    val input = Utils.getInputAsList("Day13")
    val earliestStart = input[0].toInt()
    val offsetToBusesId = input[1].split(',').mapIndexedNotNull { index, char -> if (char != "x") index.toLong() to char.toLong() else null }
    val (minsToWait, offsetToBusId) = offsetToBusesId.associateBy { (_, busId) -> busId - earliestStart % busId }.minByOrNull { it.key }!!
    println("Puzzle 1 : ${minsToWait * offsetToBusId.second}")
    // we want the smallest t so that (t + offset) % busId == 0 for all our input
    // we want the smallest t so that t % busId == busId - offset for all our input
    // we want the smallest t so that busId is a multiple of t + offset
    // Notably, we want the PPCM of
    // First try :
    // notably, for the biggest busId, t % biggestBusId == biggestBusId - bbiOffset
    // so we can start the search at biggestBusId - bbiOffset and jump by biggestBusId before testing our condition
    // => too long to converge
    /*
     * val (offset, biggestBusId) = offsetToBusesId.maxByOrNull { it.second }!!
     * println("Puzzle 2 : ${generateSequence(biggestBusId - offset) { it + biggestBusId }.first { t -> offsetToBusesId.all { (offset, busId) -> (t + offset) % busId == 0L } }}")
     */
    // Second try :
    // We know the minimum value that we can have (100000000000000)
    // We can search for the next bus after this value (similar to part 1)
    // We then start at the moment the next bus is here (minus offset) and jump by this bus ID before testing our condition
    // => too long to converge
    /*
     * val minimumSearchPoint = 100000000000000
     * val (minsToWait2, offsetToBusId2) = offsetToBusesId.associateBy { (_, busId) -> busId - minimumSearchPoint % busId }.minByOrNull { it.key }!!
     * println("Puzzle 2 : ${generateSequence(minimumSearchPoint + minsToWait2 - offsetToBusId2.first) { it + offsetToBusId2.second }.first { t -> offsetToBusesId.all { (offset, busId) -> (t + offset) % busId == 0L } }}")
     */
    // Experiment :
    // only at 779 are the two first buses correctly aligned and then every 1517
    // this means that we only need to check this infinite sequence to find the first correct alignement (and the associated stride) for 3 buses
    // We do this again and again until we find the answer for all our buses
    var offsetToBusesIdSubList = offsetToBusesId.take(2)
    val (offset, busId) = offsetToBusesIdSubList[0]
    var (smallestCorrectAlignement, stride) = findSmallestAlignmentAndStride(busId - offset, busId, offsetToBusesIdSubList)
    for (i in 3..offsetToBusesId.size) {
        offsetToBusesIdSubList = offsetToBusesId.take(i)
        val tempPair = findSmallestAlignmentAndStride(smallestCorrectAlignement, stride, offsetToBusesIdSubList)
        smallestCorrectAlignement = tempPair.first
        stride = tempPair.second
    }
    println("Puzzle 2 : $smallestCorrectAlignement")
}

fun findSmallestAlignmentAndStride(startingPointForSearch: Long, stride: Long, offsetToBusID: List<Pair<Long, Long>>): Pair<Long, Long> {
    return generateSequence(startingPointForSearch) { it + stride }.filter { t -> offsetToBusID.all { (offset, busID) -> (t + offset) % busID == 0L } }.take(2)
        .zipWithNext().map { it.first to it.second - it.first }.first()
}
