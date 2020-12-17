private data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int)

private enum class State { ACTIVE, INACTIVE }

fun main() {
    val input = Utils.getInputAsList("Day17")
    println("Puzzle 1 : ${computeGameStateAtCycleCount(input).count { it.value == State.ACTIVE }}")
    println("Puzzle 2 : ${computeGameStateAtCycleCount(input, wVar = 1).count { it.value == State.ACTIVE }}")
}

private fun computeGameStateAtCycleCount(input: List<String>, wVar: Int = 0): HashMap<Point4D, State> {
    var gameState = HashMap<Point4D, State>()
    var minX = 0
    var maxX = input[0].length
    var minY = 0
    var maxY = input.size
    var minZ = 0
    var maxZ = 0
    var minW = 0
    var maxW = 0
    input.forEachIndexed { rowIndex, line ->
        line.forEachIndexed { columnIndex, char ->
            gameState[Point4D(columnIndex, rowIndex, 0, 0)] = if (char == '#') State.ACTIVE else State.INACTIVE
        }
    }
    repeat(6) {
        minX -= 1
        maxX += 1
        minY -= 1
        maxY += 1
        minZ -= 1
        maxZ += 1
        minW -= wVar
        maxW += wVar
        val newGameState = HashMap<Point4D, State>()
        (minW..maxW).forEach { w ->
            (minZ..maxZ).forEach { z ->
                (minY..maxY).forEach { y ->
                    (minX..maxX).forEach { x ->
                        val key = Point4D(x, y, z, w)
                        newGameState[key] = when (gameState[key]) {
                            State.ACTIVE -> if (gameState.countActiveNeighbors4D(key, wVar = wVar) in 2..3) State.ACTIVE else State.INACTIVE
                            else -> if (gameState.countActiveNeighbors4D(key, wVar = wVar) == 3) State.ACTIVE else State.INACTIVE
                        }
                    }
                }
            }
        }
        gameState = newGameState
    }
    return gameState
}

private val bufferPoint4DNeighbors = mutableListOf<Point4D>()

private fun HashMap<Point4D, State>.countActiveNeighbors4D(point4D: Point4D, wVar: Int = 0): Int {
    bufferPoint4DNeighbors.clear()
    (point4D.x - 1..point4D.x + 1).forEach { x ->
        (point4D.y - 1..point4D.y + 1).forEach { y ->
            (point4D.z - 1..point4D.z + 1).forEach { z ->
                (point4D.w - wVar..point4D.w + wVar).forEach { w ->
                    bufferPoint4DNeighbors.add(Point4D(x, y, z, w))
                }
            }
        }
    }
    bufferPoint4DNeighbors.remove(point4D)
    return bufferPoint4DNeighbors.count { this[it] == State.ACTIVE }
}