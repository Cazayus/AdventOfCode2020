import kotlin.math.roundToInt

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val input = Utils.getInputAsList("Day5")
    val maxSeatID = input.map {
        val rows = it.take(7)
        val row = rows.fold(0..127) { total, char ->
            when (char) {
                'F' -> total.first..total.average().toInt()
                'B' -> total.average().roundToInt()..total.last
                else -> TODO()
            }
        }.single()
        val columns = it.takeLast(3)
        val column = columns.fold(0..7) { total, char ->
            when (char) {
                'L' -> total.first..total.average().toInt()
                'R' -> total.average().roundToInt()..total.last
                else -> TODO()
            }
        }.single()
        row * 8 + column
    }.maxOrNull()
    println(maxSeatID)
}

private fun partTwo() {
    val input = Utils.getInputAsList("Day5")
    val mySeatID = input.groupBy({
        val rows = it.take(7)
        rows.fold(0..127) { total, char ->
            when (char) {
                'F' -> total.first..total.average().toInt()
                'B' -> total.average().roundToInt()..total.last
                else -> TODO()
            }
        }.single()
    }, {
        val columns = it.takeLast(3)
        columns.fold(0..7) { total, char ->
            when (char) {
                'L' -> total.first..total.average().toInt()
                'R' -> total.average().roundToInt()..total.last
                else -> TODO()
            }
        }.single()
    }).filter { entry -> entry.value.size == 7 }.map { entry ->
        val row = entry.key
        val column = (0..7).toMutableList().first { !entry.value.contains(it) }
        row * 8 + column
    }.single()
    println(mySeatID)
}
