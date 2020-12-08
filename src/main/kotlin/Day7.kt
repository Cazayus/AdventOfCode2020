fun main() {
    val input = Utils.getInputAsList("Day7")
    val regex = Regex(" bag[s\\.]*")
    val parsedInput: Map<String, Map<String, Int>> =
        input.map { it.replace(regex, "") }.map { it.split(" contain ") }.associateBy({ it[0] }) {
            when {
                it[1].contains("no other") -> emptyMap()
                it[1].contains(", ") -> it[1].split(", ").associate { split -> split.split(" ", limit = 2)[1] to split.split(" ", limit = 2)[0].toInt() }
                else -> mapOf(it[1].split(" ", limit = 2)[1] to it[1].split(" ", limit = 2)[0].toInt())
            }
        }
    println("Puzzle 1 : " + findContainingBag("shiny gold", parsedInput).size)
    println("Puzzle 2 : " + countBagContent("shiny gold", parsedInput))
}

fun findContainingBag(bagToFind: String, parsedInput: Map<String, Map<String, Int>>): Set<String> {
    val setDirectlyContainingBagToFind = parsedInput.mapNotNull { (key, value) -> if (value.containsKey(bagToFind)) key else null }.toSet()
    return setDirectlyContainingBagToFind + (setDirectlyContainingBagToFind.fold(mutableSetOf()) { acc, directlyContainsBagToFind ->
        acc.addAll(findContainingBag(directlyContainsBagToFind, parsedInput))
        acc
    })
}

fun countBagContent(bagToFind: String, parsedInput: Map<String, Map<String, Int>>): Int {
    return parsedInput[bagToFind]?.map { (key, value) -> value + value * countBagContent(key, parsedInput) }?.sum() ?: throw UnsupportedOperationException()
}