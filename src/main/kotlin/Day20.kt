import kotlin.math.sqrt

private class Tile(val id: Long, val content: List<String>) {
    override fun toString(): String {
        return id.toString() + System.lineSeparator() + content.joinToString(separator = System.lineSeparator())
    }
}

private fun Tile.turnClock() = Tile(id, (0..content[0].lastIndex).map { index -> content.reversed().map { it[index] }.joinToString(separator = "") })

private fun Tile.flip() = Tile(id, content.map { it.reversed() })

private fun Tile.getAllVariations(): List<Tile> {
    val output = mutableListOf<Tile>()
    var toAdd = this
    repeat(2) {
        repeat(4) {
            output += toAdd
            toAdd = toAdd.turnClock()
        }
        toAdd = toAdd.flip()
    }
    return output
}

private fun Tile.matchesToNorth(tile: Tile) = content[0].zip(tile.content.last()).all { it.first == it.second }

private fun Tile.matchesToSouth(tile: Tile) = content.last().zip(tile.content[0]).all { it.first == it.second }

private fun Tile.matchesToEast(tile: Tile) = content.map { it.last() }.zip(tile.content.map { it.first() }).all { it.first == it.second }

private fun Tile.matchesToWest(tile: Tile) = content.map { it.first() }.zip(tile.content.map { it.last() }).all { it.first == it.second }

private fun Tile.matchesAny(tile: Tile) = this.matchesToEast(tile) || this.matchesToWest(tile) || this.matchesToNorth(tile) || this.matchesToSouth(tile)

fun main() {
    val input = Utils.getInputAsText("Day20")
    val tilesAsText = input.split(System.lineSeparator() + System.lineSeparator())
    val idToTile = tilesAsText.map {
        val split = it.split(System.lineSeparator())
        Tile(split[0].drop(5).dropLast(1).toLong(), split.drop(1))
    }.associateBy { it.id }
    val idToVariations = idToTile.mapValues { it.value.getAllVariations() }
    val idToMatchingIdsList = idToTile.mapValues { (_, tile) ->
        idToVariations.filterKeys { it != tile.id }.values.flatten().filter { variation -> tile.matchesAny(variation) }.map { it.id }
    }.toList()
    println("Puzzle 1 : " + idToMatchingIdsList.filter { it.second.size == 2 }.map { it.first }.reduce { value1, value2 -> value1 * value2 })
    val squareSide = sqrt(idToTile.size.toDouble()).toInt()
    val (topLeftTileId, neighborsId) = idToMatchingIdsList.first { it.second.size == 2 }
    var topLeftTile = idToTile[topLeftTileId]!!
    val potentialNeighbors = neighborsId.flatMap { idToVariations[it]!! }
    while (potentialNeighbors.none { topLeftTile.matchesToSouth(it) } || potentialNeighbors.none { topLeftTile.matchesToEast(it) }) {
        topLeftTile = topLeftTile.turnClock()
    }
    var puzzle = mutableMapOf<Pair<Int, Int>, Tile>()
    puzzle[0 to 0] = topLeftTile
    (1 until squareSide).forEach { y ->
        val tileAbove = puzzle[0 to y - 1]!!
        val potentialTilesId = idToMatchingIdsList.first { it.first == tileAbove.id }.second
        val potentialTiles = potentialTilesId.flatMap { idToVariations[it]!! }
        puzzle[0 to y] = potentialTiles.first { tileAbove.matchesToSouth(it) }
    }
    (0 until squareSide).forEach { y ->
        (1 until squareSide).forEach { x ->
            val tileToTheLeft = puzzle[x - 1 to y]!!
            val potentialTilesId = idToMatchingIdsList.first { it.first == tileToTheLeft.id }.second
            val potentialTiles = potentialTilesId.flatMap { idToVariations[it]!! }
            puzzle[x to y] = potentialTiles.first { tileToTheLeft.matchesToEast(it) }
        }
    }
    puzzle = puzzle.mapValues { (_, tile) -> Tile(tile.id, tile.content.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }) }.toMutableMap()
    val masterContent = mutableListOf<String>()
    (0 until squareSide).forEach { x ->
        (0 until squareSide).forEach { y ->
            val content = puzzle[x to y]!!.content
            content.forEachIndexed { index, line ->
                if (y * (content.size) + index < masterContent.size) {
                    masterContent[y * (content.size) + index] = masterContent[y * (content.size) + index] + line
                } else {
                    masterContent.add(line)
                }
            }
        }
    }
    val correctSea = Tile(0, masterContent).getAllVariations().first { it.content.countSeaMonsters(seaMonsterOffsets) > 0 }.content
    println("Puzzle 2 : ${correctSea.map { line -> line.count { it == '#' } }.sum() - correctSea.countSeaMonsters(seaMonsterOffsets) * seaMonsterOffsets.size}")
}

fun List<String>.countSeaMonsters(mask: List<Pair<Int, Int>>): Int {
    var count = 0
    val maxWidth = mask.maxByOrNull { it.second }!!.second
    val maxHeight = mask.maxByOrNull { it.first }!!.first
    (0..(this.size - maxHeight)).forEach { x ->
        (0..(this.size - maxWidth)).forEach { y ->
            val currentPoint = x to y
            val actualSpots = mask.map { it + currentPoint }
            if (actualSpots.all { this[it.first][it.second] == '#' }) {
                count++
            }
        }
    }
    return count
}

val seaMonsterOffsets = listOf(
    Pair(0, 18), Pair(1, 0), Pair(1, 5), Pair(1, 6), Pair(1, 11), Pair(1, 12),
    Pair(1, 17), Pair(1, 18), Pair(1, 19), Pair(2, 1), Pair(2, 4), Pair(2, 7), Pair(2, 10), Pair(2, 13), Pair(2, 16)
)