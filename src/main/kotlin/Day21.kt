fun main() {
    val input = Utils.getInputAsList("Day21")
    var mapAllergensToGuess = mutableMapOf<String, Set<String>>()
    val fullIngredientList = input.flatMap { line ->
        val splitString = line.split(" (contains ")
        val ingredients = splitString[0].split(' ').toSet()
        val allergens = splitString[1].dropLast(1).split(", ")
        allergens.forEach { allergen -> mapAllergensToGuess[allergen] = mapAllergensToGuess.getOrDefault(allergen, ingredients).intersect(ingredients) }
        ingredients
    }
    while (mapAllergensToGuess.any { it.value.size > 1 }) {
        val foundMappings = mapAllergensToGuess.filter { it.value.size == 1 }.map { it.value.first() }
        mapAllergensToGuess = mapAllergensToGuess.mapValues { if (it.value.size == 1) it.value else it.value.subtract(foundMappings) }.toMutableMap()
    }
    val actualFinding = mapAllergensToGuess.mapValues { it.value.first() }.toSortedMap()
    println("Puzzle 1 : ${fullIngredientList.count { !actualFinding.containsValue(it) }}")
    println("Puzzle 2: ${actualFinding.values.joinToString(separator = ",")}")
}