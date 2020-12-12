private val input = Utils.getInputAsList("Day11").mapIndexed { yIndex, string ->
    var xIndex = 0
    string.associateBy { Pair(xIndex++, yIndex) }
}.fold(mutableMapOf<Pair<Int, Int>, Char>()) { acc, current ->
    acc += current
    acc
}
private val width = input.maxOfOrNull { (key, _) -> key.first }!!
private val height = input.maxOfOrNull { (key, _) -> key.second }!!

private typealias Seat = Pair<Int, Int>
private typealias Seats = Map<Seat, Char>

fun main() {
    val current = mutableMapOf<Seat, Char>()
    val next = input.toMutableMap()
    do {
        current.clear()
        current.putAll(next)
        current.forEach { (pair, char) ->
            when (char) {
                '.' -> next[pair] = '.'
                'L' -> if (current.countAdjacent(pair) == 0) next[pair] = '#' else next[pair] = 'L'
                '#' -> if (current.countAdjacent(pair) >= 4) next[pair] = 'L' else next[pair] = '#'
            }
        }
    } while (current != next)
    println("Puzzle 1 : " + current.count { (_, value) -> value == '#' })
    next.clear()
    next.putAll(input)
    do {
        current.clear()
        current.putAll(next)
        current.forEach { (pair, char) ->
            when (char) {
                '.' -> next[pair] = '.'
                'L' -> if (current.countAdjacentLineOfSight(pair) == 0) next[pair] = '#' else next[pair] = 'L'
                '#' -> if (current.countAdjacentLineOfSight(pair) >= 5) next[pair] = 'L' else next[pair] = '#'
            }
        }
    } while (current != next)
    println("Puzzle 2 : " + current.count { (_, value) -> value == '#' })
}

private fun debugPrintln(seats: Seats) {
    var currentLine: Int? = null
    seats.forEach {
        if (currentLine == null) {
            currentLine = it.key.second
        }
        if (it.key.second != currentLine) {
            currentLine = currentLine!! + 1
            println()
        }
        print(it.value)
    }
    println()
}

private fun Seat.getTopLeftOrNull(): Seat? = if (this.first != 0 && this.second != 0) this.first - 1 to this.second - 1 else null
private fun Seat.getTopOrNull(): Seat? = if (this.second != 0) this.first to this.second - 1 else null
private fun Seat.getTopRightOrNull(): Seat? = if (this.first != width && this.second != 0) this.first + 1 to this.second - 1 else null
private fun Seat.getRightOrNull(): Seat? = if (this.first != width) this.first + 1 to this.second else null
private fun Seat.getBottomRightOrNull(): Seat? = if (this.first != width && this.second != height) this.first + 1 to this.second + 1 else null
private fun Seat.getBottomOrNull(): Seat? = if (this.second != height) this.first to this.second + 1 else null
private fun Seat.getBottomLeftOrNull(): Seat? = if (this.first != 0 && this.second != height) this.first - 1 to this.second + 1 else null
private fun Seat.getLeftOrNull(): Seat? = if (this.first != 0) this.first - 1 to this.second else null

private fun Seats.countAdjacent(pair: Seat): Int {
    var output = 0
    if (this[pair.getTopLeftOrNull()] == '#') output++
    if (this[pair.getTopOrNull()] == '#') output++
    if (this[pair.getTopRightOrNull()] == '#') output++
    if (this[pair.getRightOrNull()] == '#') output++
    if (this[pair.getBottomRightOrNull()] == '#') output++
    if (this[pair.getBottomOrNull()] == '#') output++
    if (this[pair.getBottomLeftOrNull()] == '#') output++
    if (this[pair.getLeftOrNull()] == '#') output++
    return output
}

private fun getNextChairSeen(
    map: Seats,
    pair: Seat,
    function: Seat. () -> Seat?
): Seat? {
    var temp = pair.function()
    while (map[temp] == '.') {
        temp = temp?.function()
    }
    return temp
}

private fun Seats.countAdjacentLineOfSight(pair: Seat): Int {
    var output = 0
    if (this[getNextChairSeen(this, pair) { getTopLeftOrNull() }] == '#') output++
    if (this[getNextChairSeen(this, pair) { getTopOrNull() }] == '#') output++
    if (this[getNextChairSeen(this, pair) { getTopRightOrNull() }] == '#') output++
    if (this[getNextChairSeen(this, pair) { getRightOrNull() }] == '#') output++
    if (this[getNextChairSeen(this, pair) { getBottomRightOrNull() }] == '#') output++
    if (this[getNextChairSeen(this, pair) { getBottomOrNull() }] == '#') output++
    if (this[getNextChairSeen(this, pair) { getBottomLeftOrNull() }] == '#') output++
    if (this[getNextChairSeen(this, pair) { getLeftOrNull() }] == '#') output++
    return output
}
