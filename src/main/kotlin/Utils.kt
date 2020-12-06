internal object Utils {
    fun getInputAsList(fileName: String): List<String> {
        return Utils.javaClass.getResource("$fileName.txt").readText().lines()
    }

    fun getInputAsText(fileName: String): String {
        return Utils.javaClass.getResource("$fileName.txt").readText()
    }
}