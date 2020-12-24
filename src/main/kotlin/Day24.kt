fun main() {
    val input = Utils.getInputAsList("Day24")
    var mapTurnedTiles = mutableMapOf<Pair<Int, Int>, Boolean>()
    input.forEach { line ->
        var remaining = line
        var position = 0 to 0
        while (remaining.isNotEmpty()) {
            when (remaining.take(2)) {
                "nw" -> position += (-1 to -1).also { remaining = remaining.drop(2) }
                "ne" -> position += (1 to -1).also { remaining = remaining.drop(2) }
                "sw" -> position += (-1 to 1).also { remaining = remaining.drop(2) }
                "se" -> position += (1 to 1).also { remaining = remaining.drop(2) }
                else -> when (remaining.take(1)) {
                    "w" -> position += -2 to 0.also { remaining = remaining.drop(1) }
                    "e" -> position += 2 to 0.also { remaining = remaining.drop(1) }
                }
            }
        }
        mapTurnedTiles[position] = mapTurnedTiles.getOrDefault(position, false).not()
    }
    mapTurnedTiles = mapTurnedTiles.filterValues { it }.toMutableMap()
    println("Puzzle 1 : ${mapTurnedTiles.size}")
    repeat(100) {
        mapTurnedTiles = mapTurnedTiles.doOneRound()
    }
    println("Puzzle 2 : ${mapTurnedTiles.size}")
}

private fun MutableMap<Pair<Int, Int>, Boolean>.doOneRound(): MutableMap<Pair<Int, Int>, Boolean> {
    val output = mutableMapOf<Pair<Int, Int>, Boolean>()
    val minX = this.minByOrNull { it.key.first }!!.key.first
    val minY = this.minByOrNull { it.key.second }!!.key.second
    val maxX = this.maxByOrNull { it.key.first }!!.key.first
    val maxY = this.maxByOrNull { it.key.second }!!.key.second
    (minX - 2..maxX + 2).forEach { x ->
        (minY - 1..maxY + 1).forEach { y ->
            val currentPosition = x to y
            val adjacentBlackCount = this.countAdjacentBlack(currentPosition)
            val temp = when (this.getOrDefault(currentPosition, false)) {
                true -> !(adjacentBlackCount > 2 || adjacentBlackCount == 0)
                false -> adjacentBlackCount == 2
            }
            if (temp) {
                output[currentPosition] = true
            }
        }
    }
    return output
}

private fun MutableMap<Pair<Int, Int>, Boolean>.countAdjacentBlack(currentPosition: Pair<Int, Int>): Int {
    val nw = if (this.getOrDefault(currentPosition.first - 1 to currentPosition.second - 1, false)) 1 else 0
    val ne = if (this.getOrDefault(currentPosition.first + 1 to currentPosition.second - 1, false)) 1 else 0
    val sw = if (this.getOrDefault(currentPosition.first - 1 to currentPosition.second + 1, false)) 1 else 0
    val se = if (this.getOrDefault(currentPosition.first + 1 to currentPosition.second + 1, false)) 1 else 0
    val w = if (this.getOrDefault(currentPosition.first - 2 to currentPosition.second, false)) 1 else 0
    val e = if (this.getOrDefault(currentPosition.first + 2 to currentPosition.second, false)) 1 else 0
    return nw + ne + sw + se + w + e
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + other.first, second + other.second)
}