class Utils {
    companion object {
        fun getInput(fileName: String): List<String> {
            val input = {}::class.java.getResource("$fileName.txt").readText()
            return input.lines()
        }

        fun getInputInOneLine(fileName: String): String {
            return {}::class.java.getResource("$fileName.txt").readText()
        }
    }
}