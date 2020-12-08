fun main() {
    val input = Utils.getInputAsList("Day7")
    val regex = Regex(" bags?")
    val parsedInput: Map<String, Map<String, Int>> =
        input.map { it.dropLast(1) }.map { it.replace(regex, "") }.map { it.split(" contain ") }.associateBy({ it[0] }) {
            when {
                it[1].contains("no other") -> emptyMap()
                it[1].contains(", ") -> it[1].split(", ").associate { split -> split.split(" ", limit = 2)[1] to split.split(" ", limit = 2)[0].toInt() }
                else -> mapOf(it[1].split(" ", limit = 2)[1] to it[1].split(" ", limit = 2)[0].toInt())
            }
        }
    println("Puzzle 1 : " + findContainingBag("shiny gold", parsedInput).size)
    println("Puzzle 2 : " + countBagContent("shiny gold", parsedInput))
}

//val regex = Regex("([a-z]+ [a-z]+) bags contain (([0-9]+) ([a-z]+ [a-z]+) bag[s,.\\s]*)+")
fun findContainingBag(bagToFind: String, parsedInput: Map<String, Map<String, Int>>): Set<String> {
    val directlyContainsBagToFindSet = parsedInput.mapNotNull { (key, value) -> if (value.containsKey(bagToFind)) key else null }.toSet()
    return directlyContainsBagToFindSet + (directlyContainsBagToFindSet.fold(mutableSetOf<String>()) { acc, directlyContainsBagToFind ->
        acc.addAll(findContainingBag(directlyContainsBagToFind, parsedInput))
        acc
    }.ifEmpty { emptySet() })
}

fun countBagContent(bagToFind: String, parsedInput: Map<String, Map<String, Int>>): Int {
    return parsedInput[bagToFind]?.map { (key, value) -> value + value * countBagContent(key, parsedInput) }?.sum() ?: 1
}