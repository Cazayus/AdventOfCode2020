data class ValidRange(val firstRange: IntRange, val secondRange: IntRange) {
    fun isValueInRange(value: Int): Boolean {
        return value in firstRange || value in secondRange
    }
}

data class Rule(val key: String, val validRange: ValidRange)

fun main() {
    val input = Utils.getInputAsList("Day16")
    val iterator = input.iterator()
    val regex = """(.*): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()
    val rules = mutableListOf<Rule>()
    var next = iterator.next()
    while (regex.matches(next)) {
        val (field, firstRangeStart, firstRangeEnd, secondRangeStart, secondRangeEnd) = regex.matchEntire(next)!!.destructured
        rules.add(Rule(field, ValidRange(firstRangeStart.toInt()..firstRangeEnd.toInt(), secondRangeStart.toInt()..secondRangeEnd.toInt())))
        next = iterator.next()
    }
    iterator.next()
    val yourTicket = iterator.next().split(',').map { it.toInt() }
    iterator.next()
    iterator.next()
    val nearbyTickets = mutableListOf<List<Int>>()
    while (iterator.hasNext()) {
        nearbyTickets.add(iterator.next().split(',').map { it.toInt() })
    }
    println("Puzzle 1 : " + nearbyTickets.flatMap { rules.getValuesNotMatchingAnyRules(it) }.sum())
    val validNearbyTickets = nearbyTickets.filter { nearbyTicket -> rules.matchesAnyRules(nearbyTicket) }
    val indexToValidRules = mutableMapOf<Int, List<Rule>>()
    (0..19).forEach { index ->
        val validRulesForThisIndex = rules.filter { (_, validRange) ->
            validNearbyTickets.map { it[index] }.all { validRange.isValueInRange(it) }
        }
        indexToValidRules[index] = validRulesForThisIndex
    }
    while (indexToValidRules.any { it.value.size > 1 }) {
        val foundIndexToValidRules = indexToValidRules.filterValues { it.size == 1 }
        (0..19).forEach { index ->
            if (!foundIndexToValidRules.containsKey(index)) {
                indexToValidRules[index] = indexToValidRules[index]!!.filterNot { possibleRule -> foundIndexToValidRules.any { possibleRule in it.value } }
            }
        }
    }
    println("Puzzle 2 : " + indexToValidRules.filterValues { it[0].key.startsWith("departure") }.map { it.key }
        .fold(1L) { acc, index -> acc * yourTicket[index] })
}

fun List<Rule>.matchesAnyRules(value: Int): Boolean {
    return any { (_, validRange) -> validRange.isValueInRange(value) }
}

fun List<Rule>.getValuesNotMatchingAnyRules(values: List<Int>): List<Int> {
    return values.filter { value -> !matchesAnyRules(value) }
}

fun List<Rule>.matchesAnyRules(values: List<Int>): Boolean {
    return values.all { value -> matchesAnyRules(value) }
}
