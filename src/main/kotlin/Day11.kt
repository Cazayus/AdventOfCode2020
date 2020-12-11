import kotlin.properties.Delegates

private var width by Delegates.notNull<Int>()
private var height by Delegates.notNull<Int>()

fun main() {
    val input = Utils.getInputAsList("Day11").mapIndexed { yIndex, string ->
        var xIndex = 0
        string.associateBy { Pair(xIndex++, yIndex) }
    }.fold(mutableMapOf<Pair<Int, Int>, Char>()) { acc, current ->
        acc += current
        acc
    }
    width = input.maxOfOrNull { (key, _) -> key.first }!!
    height = input.maxOfOrNull { (key, _) -> key.second }!!
    val current = mutableMapOf<Pair<Int, Int>, Char>()
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
        println("Current")
        println(current)
        current.forEach { (pair, char) ->
            when (char) {
                '.' -> next[pair] = '.'
                'L' -> if (current.countAdjacentLineOfSight(pair) == 0) next[pair] = '#' else next[pair] = 'L'
                '#' -> if (current.countAdjacentLineOfSight(pair) >= 5) next[pair] = 'L' else next[pair] = '#'
            }
        }
        println("Next")
        println(next)
    } while (current != next)
    println("Puzzle 2 : " + current.count { (_, value) -> value == '#' })
}

private fun println(map: Map<Pair<Int, Int>, Char>) {
    var currentLine: Int? = null
    map.forEach {
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

private fun Pair<Int, Int>.getTopOrNull(): Pair<Int, Int>? = if (this.second != 0) this.first to this.second - 1 else null
private fun Pair<Int, Int>.getTopRightOrNull(): Pair<Int, Int>? = if (this.first != width && this.second != 0) this.first + 1 to this.second - 1 else null
private fun Pair<Int, Int>.getRightOrNull(): Pair<Int, Int>? = if (this.first != width) this.first + 1 to this.second else null
private fun Pair<Int, Int>.getBottomRightOrNull(): Pair<Int, Int>? =
    if (this.first != width && this.second != height) this.first + 1 to this.second + 1 else null

private fun Pair<Int, Int>.getBottomOrNull(): Pair<Int, Int>? = if (this.second != height) this.first to this.second + 1 else null
private fun Pair<Int, Int>.getBottomLeftOrNull(): Pair<Int, Int>? = if (this.first != 0 && this.second != height) this.first - 1 to this.second + 1 else null
private fun Pair<Int, Int>.getLeftOrNull(): Pair<Int, Int>? = if (this.first != 0) this.first - 1 to this.second else null
private fun Pair<Int, Int>.getTopLeftOrNull(): Pair<Int, Int>? = if (this.first != 0 && this.second != 0) this.first - 1 to this.second - 1 else null

private fun MutableMap<Pair<Int, Int>, Char>.countAdjacent(pair: Pair<Int, Int>): Int {
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
    map: MutableMap<Pair<Int, Int>, Char>,
    pair: Pair<Int, Int>,
    function: Pair<Int, Int>. () -> Pair<Int, Int>?
): Pair<Int, Int>? {
    var temp = pair.function()
    while (map[temp] == '.') {
        temp = temp?.function()
    }
    return temp
}

private fun MutableMap<Pair<Int, Int>, Char>.countAdjacentLineOfSight(pair: Pair<Int, Int>): Int {
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
