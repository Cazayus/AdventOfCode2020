fun main() {
    val input = Utils.getInputAsText("Day8")
    val regex = """(acc|jmp|nop) (.\d+)""".toRegex(RegexOption.MULTILINE)
    val parsedInput: List<Pair<String, Int>> = regex.findAll(input).mapIndexed { index, matchResult ->
        val (operation, value) = matchResult.destructured
        (operation to value.toInt())
    }.toList()
    var currentLine = 0;
    var accumulator = 0
    val potentiallyCorruptedLines = mutableSetOf<Int>();
    val seenLinesPartOne = mutableSetOf<Int>()
    while (!seenLinesPartOne.contains(currentLine) && currentLine != parsedInput.lastIndex) {
        seenLinesPartOne += currentLine
        when (parsedInput[currentLine].first) {
            "acc" -> accumulator += parsedInput[currentLine].second
            "jmp" -> {
                potentiallyCorruptedLines += currentLine
                currentLine += (parsedInput[currentLine].second - 1)
            }
            "nop" -> potentiallyCorruptedLines += currentLine
        }
        currentLine++
    }
    println("Puzzle 1 : $accumulator")
    potentiallyCorruptedLines.forEach { potentiallyCorruptedLine ->
        currentLine = 0;
        accumulator = 0
        seenLinesPartOne.clear()
        while (!seenLinesPartOne.contains(currentLine) && currentLine != parsedInput.lastIndex) {
            seenLinesPartOne += currentLine
            when (parsedInput[currentLine].first) {
                "acc" -> accumulator += parsedInput[currentLine].second
                "jmp" -> if (currentLine != potentiallyCorruptedLine) currentLine += (parsedInput[currentLine].second - 1)
                "nop" -> if (currentLine == potentiallyCorruptedLine) currentLine += (parsedInput[currentLine].second - 1)
            }
            currentLine++
            if (currentLine == parsedInput.lastIndex) {
                if (parsedInput[currentLine].first == "acc") {
                    accumulator += parsedInput[currentLine].second
                }
                println("Puzzle 2 : $accumulator")
            }
        }
    }
}