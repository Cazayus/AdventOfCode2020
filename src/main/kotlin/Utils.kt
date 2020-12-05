class Utils {
    companion object {
        fun getInput(fileName: String): List<String> {
            val bufferedReader = javaClass.getResourceAsStream("$fileName.txt").bufferedReader()
            return bufferedReader.useLines {
                it.iterator().asSequence().toList()
            }
        }
    }
}