import kotlin.math.pow

fun main() {
    val input = Utils.getInputAsList("Day10").map { it.toInt() }.sorted().toMutableList()
    input.add(0, 0)
    input.add((input.maxOrNull() ?: return) + 3)
    var oneDifference = 0L
    var threeDifference = 0L
    input.zipWithNext().forEach {
        when (it.second - it.first) {
            1 -> oneDifference++
            3 -> threeDifference++
            else -> throw UnsupportedOperationException()
        }
    }
    println("Puzzle 1 : $oneDifference * $threeDifference = ${oneDifference * threeDifference}")
    // on a aucun écart de 2 entre nos valeurs, seulement du 1 ou du 3, donc :
    //  -soit on est écarté de 3 et ya pas le choix
    //  -soit on est une suite d'écartés de 1 et ya plusieurs possibilités
    // Suite de 2 qui se suivent -> 1 arrangement valide
    // Suite de 3 qui se suivent -> 2 arrangement valide
    // Suite de 4 qui se suivent -> 4 arrangement valide
    // Suite de 5 qui se suivent -> 7 arrangement valide
    // Suite de 6 qui se suivent -> 13 arrangement valide
    // Hypothèse : suite de x qui se suivent  -> ???? choix possibles
    val map = mutableMapOf<Int, Int>() // in this map we store the number of sublists containing successive values with their size as key
    var currentSubListSize = 1
    input.zipWithNext().forEach {
        when (it.second - it.first) {
            1 -> currentSubListSize++
            3 -> {
                map[currentSubListSize] = map.getOrDefault(currentSubListSize, 0) + 1
                currentSubListSize = 1
            }
            else -> throw UnsupportedOperationException()
        }
    }
    println("Puzzle 2 : " + map.map { (key, value) ->
        when (key) {
            1 -> 1.0.pow(value)
            2 -> 1.0.pow(value)
            3 -> 2.0.pow(value)
            4 -> 4.0.pow(value)
            5 -> 7.0.pow(value)
            6 -> 13.0.pow(value)
            else -> throw UnsupportedOperationException()
        }
    }.reduce { a, b -> a * b }.toLong())
}