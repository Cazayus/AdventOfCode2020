fun main() {
    val input = Utils.getInputAsList("Day19")
    val regex = """(\d+): (.*)""".toRegex(RegexOption.MULTILINE)
    val rules = input.takeWhile { it.isNotEmpty() }.associate {
        val (key, value) = regex.matchEntire(it)!!.destructured
        key.toInt() to value.split(" | ")
    }
    val messages = input.dropWhile { it.isNotEmpty() }.drop(1).toList()
    val masterRegex = rules.replace(0).toRegex()
    println("Puzzle 1 : " + messages.count { masterRegex.matchEntire(it) != null })
    val masterRegexList = (1..5).map { rules.replaceWithLoops(0, it).toRegex() }
    println("Puzzle 2 : " + messages.count { masterRegexList.any { regex -> regex.matchEntire(it) != null } })
}

private fun Map<Int, List<String>>.replace(key: Int): String {
    return when (this[key]!![0]) {
        "\"a\"" -> "a"
        "\"b\"" -> "b"
        else -> this[key]!!.joinToString(prefix = "(", separator = "|", postfix = ")") { rule ->
            rule.split(" ").joinToString(separator = "") { this.replace(it.toInt()) }
        }
    }
}

private fun Map<Int, List<String>>.replaceWithLoops(key: Int, repeat: Int): String {
    return when (this[key]!![0]) {
        "\"a\"" -> "a"
        "\"b\"" -> "b"
        else -> when (key) {
            8 -> replaceWithLoops(42, repeat) + "+"
            11 -> {
                var output = ""
                val group42 = replaceWithLoops(42, repeat)
                val group31 = replaceWithLoops(31, repeat)
                //"($group42(?-1)?$group31)" //-> ça c'est la théorie, en pratique, java ne gère pas la récursion dans les regex...
                repeat(repeat) {
                    output += group42
                }
                repeat(repeat) {
                    output += group31
                }
                output
            }
            else -> this[key]!!.joinToString(prefix = "(", separator = "|", postfix = ")") { rule ->
                rule.split(" ").joinToString(separator = "") { this.replaceWithLoops(it.toInt(), repeat) }
            }
        }
    }
}