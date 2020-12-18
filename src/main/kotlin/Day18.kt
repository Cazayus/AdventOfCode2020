private enum class Operator { PLUS, TIMES, NONE }

fun main() {
    val input = Utils.getInputAsList("Day18")
    println("Puzzle 1 : ${input.map { evaluate(toList(it).toMutableList()) }.sum()}")
    println("Puzzle 2 : ${input.map { evaluatePlusFirst(toList(it).toMutableList()) }.sum()}")
}

fun toList(untrimmedString: String): Array<Any> {
    val string = untrimmedString.trim()
    if (string.contains('(')) {
        var count = 1
        val stringBeforeParenthesis = string.takeWhile { it != '(' }
        val stringBetweenParenthesis = string.dropWhile { it != '(' }.drop(1).takeWhile {
            if (it == '(') {
                count++
            } else if (it == ')') {
                count--
            }
            it != ')' || count == 1
        }
        val stringAfterParenthesis = string.substring(stringBeforeParenthesis.length + stringBetweenParenthesis.length + 2)
        return arrayOf(*toList(stringBeforeParenthesis), toList(stringBetweenParenthesis), *toList(stringAfterParenthesis))
    } else {
        return string.split(' ').mapNotNull {
            when (it) {
                "*" -> Operator.TIMES
                "+" -> Operator.PLUS
                else -> it.toLongOrNull()
            }
        }.toTypedArray()
    }
}

fun evaluate(content: MutableList<*>): Long {
    var currentOperator = Operator.NONE
    return content.fold(0L) { output, value ->
        when (value) {
            is Long -> {
                when (currentOperator) {
                    Operator.PLUS -> output + value
                    Operator.TIMES -> output * value
                    Operator.NONE -> value
                }
            }
            is Operator -> output.also { currentOperator = value }
            is Array<*> -> {
                when (currentOperator) {
                    Operator.PLUS -> output + evaluate(value.toMutableList())
                    Operator.TIMES -> output * evaluate(value.toMutableList())
                    Operator.NONE -> evaluate(value.toMutableList())
                }
            }
            else -> throw UnsupportedOperationException()
        }
    }
}

fun evaluatePlusFirst(content: MutableList<Any?>): Long {
    while (content.indexOf(Operator.PLUS) != -1) {
        val plusIndex = content.indexOf(Operator.PLUS)
        val beforePlus = content[plusIndex - 1]!!
        val afterPlus = content[plusIndex + 1]!!
        if (beforePlus is Long && afterPlus is Long) {
            content[plusIndex - 1] = beforePlus + afterPlus
        } else if (beforePlus is Long && afterPlus is Array<*>) {
            content[plusIndex - 1] = evaluatePlusFirst(mutableListOf(beforePlus, Operator.PLUS, evaluatePlusFirst(afterPlus.toMutableList())))
        } else if (beforePlus is Array<*> && afterPlus is Long) {
            content[plusIndex - 1] = evaluatePlusFirst(mutableListOf(evaluatePlusFirst(beforePlus.toMutableList()), Operator.PLUS, afterPlus))
        } else if (beforePlus is Array<*> && afterPlus is Array<*>) {
            content[plusIndex - 1] =
                evaluatePlusFirst(mutableListOf(evaluatePlusFirst(beforePlus.toMutableList()), Operator.PLUS, evaluatePlusFirst(afterPlus.toMutableList())))
        }
        content.removeAt(plusIndex + 1)
        content.removeAt(plusIndex)
    }
    var currentOperator = Operator.NONE
    return content.fold(0L) { output, value ->
        when (value) {
            is Long -> {
                when (currentOperator) {
                    Operator.PLUS -> output + value
                    Operator.TIMES -> output * value
                    Operator.NONE -> value
                }
            }
            is Operator -> output.also { currentOperator = value }
            is Array<*> -> {
                when (currentOperator) {
                    Operator.PLUS -> output + evaluatePlusFirst(value.toMutableList())
                    Operator.TIMES -> output * evaluatePlusFirst(value.toMutableList())
                    Operator.NONE -> evaluatePlusFirst(value.toMutableList())
                }
            }
            else -> throw UnsupportedOperationException()
        }
    }
}