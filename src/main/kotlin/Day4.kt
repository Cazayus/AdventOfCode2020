fun main() {
    partOne()
    partTwo()
}

private val eyeColor = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
private val keyList = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

private fun partOne() {
    val input = Utils.getInputInOneLine("Day4")
    val potentialPassportListAsString = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    val potentialPassportListAsMap = potentialPassportListAsString.map { potentialPassport ->
        potentialPassport.split(System.lineSeparator()).flatMap { it.split(' ') }.associate { pairString ->
            val split = pairString.split(':')
            split[0] to split[1]
        }
    }
    val count = potentialPassportListAsMap.count {
        keyList.all { key -> it.contains(key) }
    }
    println(count)
}

private fun partTwo() {
    val input = Utils.getInputInOneLine("Day4")
    val potentialPassportListAsString = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    val potentialPassportListAsMap = potentialPassportListAsString.map { potentialPassport ->
        potentialPassport.split(System.lineSeparator()).flatMap { it.split(' ') }.associate { pairString ->
            val split = pairString.split(':')
            split[0] to split[1]
        }
    }
    val count = potentialPassportListAsMap.filter { keyList.all { key -> it.contains(key) } }.count { map ->
        map.all { (key, value) ->
            when (key) {
                "byr" -> (1920..2002).contains(value.toInt())
                "iyr" -> (2010..2020).contains(value.toInt())
                "eyr" -> (2020..2030).contains(value.toInt())
                "hgt" -> when {
                    value.endsWith("cm") -> (150..193).contains(value.dropLast(2).toInt())
                    value.endsWith("in") -> (59..76).contains(value.dropLast(2).toInt())
                    else -> false
                }
                "hcl" -> value.matches(Regex("#[0-9a-f]{6}"))
                "ecl" -> eyeColor.contains(value)
                "pid" -> value.matches(Regex("[0-9]{9}"))
                "cid" -> true
                else -> false
            }
        }
    }
    println(count)
}
