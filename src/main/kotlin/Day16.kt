data class Rule(val key: String, val firstRange: IntRange, val secondRange: IntRange) {
    fun isValueInRange(value: Int): Boolean {
        return value in firstRange || value in secondRange
    }
}

fun main() {
    val input = Utils.getInputAsList("Day16")
    val regex = """(.*): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()
    val rules: List<Rule> = input.takeWhile { it.isNotEmpty() }.map { line ->
        val (field, firstRangeStart, firstRangeEnd, secondRangeStart, secondRangeEnd) = regex.matchEntire(line)!!.destructured
        Rule(field, firstRangeStart.toInt()..firstRangeEnd.toInt(), secondRangeStart.toInt()..secondRangeEnd.toInt())
    }.toList()
    val yourTicket: List<Int> = input.dropWhile { it != "your ticket:" }.drop(1).first().split(",").map { it.toInt() }
    val nearbyTickets: List<List<Int>> = input.dropWhile { it != "nearby tickets:" }.drop(1).map { line ->
        line.split(",").map { it.toInt() }
    }.toList()
    println("Puzzle 1 : " + nearbyTickets.flatMap { rules.getValuesNotMatchingAnyRules(it) }.sum())
    val validNearbyTickets = nearbyTickets.filter { nearbyTicket -> rules.getValuesNotMatchingAnyRules(nearbyTicket).isEmpty() }
    val indexToValidRules = mutableMapOf<Int, List<Rule>>()
    (0..19).forEach { index ->
        val validRulesForThisIndex = rules.filter { rule -> validNearbyTickets.map { it[index] }.all { rule.isValueInRange(it) } }
        indexToValidRules[index] = validRulesForThisIndex
    }
    while (indexToValidRules.any { it.value.size > 1 }) {
        val indexToSingleRule = indexToValidRules.filterValues { it.size == 1 }
        (0..19).forEach { index ->
            if (!indexToSingleRule.containsKey(index)) {
                indexToValidRules[index] = indexToValidRules[index]!!.filterNot { possibleRule -> indexToSingleRule.any { possibleRule in it.value } }
            }
        }
    }
    println("Puzzle 2 : " + indexToValidRules.filterValues { it[0].key.startsWith("departure") }.map { it.key }
        .fold(1L) { acc, index -> acc * yourTicket[index] })
}

fun List<Rule>.matchesAnyRules(value: Int): Boolean {
    return any { it.isValueInRange(value) }
}

fun List<Rule>.getValuesNotMatchingAnyRules(values: List<Int>): List<Int> {
    return values.filter { value -> !matchesAnyRules(value) }
}
