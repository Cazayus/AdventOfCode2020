fun main() {
    partOne()
    partTwo()
}

/**
- byr (Birth Year)
- iyr (Issue Year)
- eyr (Expiration Year)
- hgt (Height)
- hcl (Hair Color)
- ecl (Eye Color)
- pid (Passport ID)
- cid (Country ID)
 */
data class Passport(
    val byr: Int,
    val iyr: Int,
    val eyr: Int,
    val hgt: String,
    val hcl: String,
    val ecl: String,
    val pid: String,
    val cid: Int?
)

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
    val passportList: List<Passport?> = potentialPassportListAsMap.map {
        if (keyList.all { key -> it.contains(key) }) {
            Passport(
                it.getOrDefault("byr", "").toInt(),
                it.getOrDefault("iyr", "").toInt(),
                it.getOrDefault("eyr", "").toInt(),
                it.getOrDefault("hgt", ""),
                it.getOrDefault("hcl", ""),
                it.getOrDefault("ecl", ""),
                it.getOrDefault("pid", ""),
                it["cid"]?.toInt()
            )
        } else {
            null
        }
    }
    println(passportList.count { it != null })
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
    val passportList: List<Passport?> = potentialPassportListAsMap.map {
        if (keyList.all { key -> it.contains(key) }) {
            val byr = it.getOrDefault("byr", "")
            if (byr.length != 4 || !(1920..2002).contains(byr.toInt())) {
                return@map null
            }
            val iyr = it.getOrDefault("iyr", "")
            if (iyr.length != 4 || !(2010..2020).contains(iyr.toInt())) {
                return@map null
            }
            val eyr = it.getOrDefault("eyr", "")
            if (eyr.length != 4 || !(2020..2030).contains(eyr.toInt())) {
                return@map null
            }
            val hgt = it.getOrDefault("hgt", "")
            if (hgt.endsWith("cm") && !(150..193).contains(hgt.dropLast(2).toInt())) {
                return@map null
            } else if (hgt.endsWith("in") && !(59..76).contains(hgt.dropLast(2).toInt())) {
                return@map null
            } else if (!hgt.endsWith("in") && !hgt.endsWith("cm")) {
                return@map null
            }
            val hcl = it.getOrDefault("hcl", "")
            if (!hcl.matches(Regex("#[0-9a-f]{6}"))) {
                return@map null
            }
            val ecl = it.getOrDefault("ecl", "")
            if (!eyeColor.contains(ecl)) {
                return@map null
            }
            val pid = it.getOrDefault("pid", "")
            if (!pid.matches(Regex("[0-9]{9}"))) {
                return@map null
            }
            return@map Passport(
                byr.toInt(),
                iyr.toInt(),
                eyr.toInt(),
                hgt,
                hcl,
                ecl,
                pid,
                it["cid"]?.toInt()
            )
        } else {
            return@map null
        }
    }
    println(passportList.count { it != null })
}
