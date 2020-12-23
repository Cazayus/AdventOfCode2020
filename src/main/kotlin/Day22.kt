import java.util.*
import kotlin.collections.ArrayDeque

private enum class WINNER { PLAYER1, PLAYER2 }

fun main() {
    val input = Utils.getInputAsList("Day22")
    val player1Deck = input.drop(1).takeWhile { it.isNotEmpty() }.fold(ArrayDeque<Int>()) { acc, current -> acc.also { it.addLast(current.toInt()) } }
    val player2Deck = input.dropWhile { it.isNotEmpty() }.drop(2).fold(ArrayDeque<Int>()) { acc, current -> acc.also { it.addLast(current.toInt()) } }
    println("Puzzle 1 : " + runGameOne(ArrayDeque(player1Deck), ArrayDeque(player2Deck)))
    println("Puzzle 2 : " + countScore(runGameTwo(player1Deck, player2Deck), player1Deck, player2Deck))
}

private fun runGameOne(player1Deck: ArrayDeque<Int>, player2Deck: ArrayDeque<Int>): Long {
    while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
        val player1Card = player1Deck.removeFirst()
        val player2Card = player2Deck.removeFirst()
        if (player1Card > player2Card) {
            player1Deck.addLast(player1Card)
            player1Deck.addLast(player2Card)
        } else {
            player2Deck.addLast(player2Card)
            player2Deck.addLast(player1Card)
        }
    }
    return if (player1Deck.isNotEmpty()) player1Deck.countScore() else player2Deck.countScore()
}

private fun runGameTwo(player1Deck: ArrayDeque<Int>, player2Deck: ArrayDeque<Int>): WINNER {
    val stateAlreadySeen = mutableSetOf<Int>()
    while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
        val hash = Objects.hash(player1Deck, player2Deck)
        if (hash in stateAlreadySeen) {
            return WINNER.PLAYER1
        } else {
            stateAlreadySeen.add(hash)
        }
        val player1Card = player1Deck.removeFirst()
        val player2Card = player2Deck.removeFirst()
        val winner = if (player1Deck.size < player1Card || player2Deck.size < player2Card) {
            if (player1Card > player2Card) WINNER.PLAYER1 else WINNER.PLAYER2
        } else {
            runGameTwo(ArrayDeque(player1Deck.take(player1Card)), ArrayDeque(player2Deck.take(player2Card)))
        }
        if (winner == WINNER.PLAYER1) {
            player1Deck.addLast(player1Card)
            player1Deck.addLast(player2Card)
        } else {
            player2Deck.addLast(player2Card)
            player2Deck.addLast(player1Card)
        }
    }
    return if (player1Deck.isNotEmpty()) WINNER.PLAYER1 else WINNER.PLAYER2
}

private fun countScore(winner: WINNER, player1Deck: ArrayDeque<Int>, player2Deck: ArrayDeque<Int>): Long {
    return if (winner == WINNER.PLAYER1) player1Deck.countScore() else player2Deck.countScore()
}

private fun ArrayDeque<Int>.countScore(): Long {
    return this.foldIndexed(0L) { index, accumulator, current -> accumulator + (this.size - index) * current }
}