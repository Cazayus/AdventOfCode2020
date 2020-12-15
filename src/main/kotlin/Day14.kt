fun main() {
    val input = Utils.getInputAsList("Day14")
    var mask = ""
    val regex = Regex("mem\\[(.+)\\] = (.+)")
    val memory = mutableMapOf<String, String>()
    input.forEach {
        if (it.startsWith("mask")) {
            mask = it.substringAfter("= ")
        } else {
            val (memoryAddress, value) = regex.find(it)!!.destructured
            val valueBeforeMask = value.toLong().toString(2).padStart(36, '0')
            memory[memoryAddress] = mask.zip(valueBeforeMask) { maskBit, valueBit -> if (maskBit != 'X') maskBit else valueBit }.joinToString(separator = "")
        }
    }
    println("Puzzle 1 : " + memory.values.map { it.toLong(2) }.sum())
    memory.clear()
    input.forEach {
        if (it.startsWith("mask")) {
            mask = it.substringAfter("= ")
        } else {
            val (index, value) = regex.find(it)!!.destructured
            val valueBeforeMask = index.toLong().toString(2).padStart(36, '0')
            val memoryAddresses = mask.zip(valueBeforeMask) { maskBit, valueBit -> if (maskBit == '0') valueBit else maskBit }.joinToString(separator = "")
            computeAllCombinations(memoryAddresses).map { combination -> memory[combination] = value }
        }
    }
    println("Puzzle 2 : " + memory.values.map { it.toLong() }.sum())
}

fun computeAllCombinations(inputString: String): List<String> {
    return if (inputString.contains('X'))
        computeAllCombinations(inputString.replaceFirst('X', '0')) + computeAllCombinations(inputString.replaceFirst('X', '1'))
    else
        listOf(inputString)
}